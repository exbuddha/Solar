package musical.performance;

import system.Type;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * {@code Instrument} classifies a physical body that serves for a specific purpose in performance.
 * <p/>
 * For example, a musical instrument is a body of smaller parts serving the purpose of transferring music to the physical space.
 * The human body is a body of smaller parts serving a variety of purposes like acting upon another instrument or just simply acting.
 * <p/>
 * Instruments are composed of smaller parts.
 * The {@link Part} interface defined in this class acts as the super-type for all instrument part classes.
 * There are three types of part classes that have their specific use and behavior.
 * Atomic parts are the instrument parts that don't require to be broken down into other smaller-sized parts.
 * They represent the most basic form of an instrument part.
 * Composite parts are the instrument parts that themselves are made up of smaller atomic or composite parts.
 * These two part types are collectively called concrete parts.
 * The third and final type of instrument parts is the part group that is theoretically different from the former two.
 * It represents an arbitrary group of abstract or concrete parts that are bundled together for a specific purpose and are given an identity.
 * This last type is used only in certain parts of the performance logic where groups of other two part types are of interest.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Instrument
implements
    Group,
    State.Machine,
    musical.performance.system.Type<Instrument>,
    Unit
{
    /** Map of instrument parts. */
    protected final
    Map<Class<? extends Part>, Map<Object, Part>> parts;

    /**
     * Creates an instrument with the specified parts map.
     *
     * @param parts the parts map.
     */
    protected
    Instrument(
        final Map<Class<? extends Part>, Map<Object, Part>> parts
        ) {
        this.parts = parts;
    }

    /**
     * Creates an instrument using a new {@link HashMap} for parts.
     */
    protected
    Instrument() {
        this(new HashMap<Class<? extends Part>, Map<Object, Part>>(1, 1));
    }

    /**
     * Creates and returns an intermediary {@link PartCreator} object with methods intended for creating an instrument part.
     * <p/>
     * This is the sole method for creating parts within the instrument.
     * The returned part creator object provides three methods for achieving this task.
     * First and foremost, the {@code withConstructor()} method has to be called in order to select the part class constructor that is to be used for creating the part.
     * This call also registers a part signature for the part that is later on used to find the part.
     * The only time this method can be skipped is when the part class has only a single constructor.
     * The second method that has to be called is the {@code withParameterRanges()} method which sets the cardinality of the domain of each parameter for the specified constructor.
     * This method takes an array of non-negative integer numbers counting the maximum number of unique values each constructor parameter can have.
     * If this number is undetermined for a specific parameter, the best educated guess should be used to set that parameter's range.
     * If the guessed number later proves to be inaccurate and a larger number of unique values is used to generate parts, this will not break the code.
     * It will only affect the performance of the search algorithm when looking up parts via {@code getPart()} and {@code getParts()} methods.
     * The only time this method can be skipped is when the part class has only a single constructor with no parameters.
     * The third and final method is the {@code withValues()} method that creates the part using the specified constructor parameters.
     * This method can be chained as many times as needed and it will not recreate an already created part.
     * The only time this method can be skipped is when the part class has only a single constructor with no parameters.
     * <p/>
     * The complete chained method call for creating parts of a certain type looks like:
     * <pre>
     * {@code instrument
     * .createPart(partClass)
     * .withConstructor(parameterTypes)
     * .withParameterRanges(parameterRanges)
     * .withValues(...)
     * .withValues(...)}
     * </pre>
     * <p/>
     * The simplest form of creating a part that contains a single constructor with no parameters looks like:
     * <pre>
     * {@code instrument.createPart(partClass)}
     * </pre>
     * <p/>
     * By convention, all overloads of this method, or its returned intermediary object, must call {@link #getDefaultConcreteSubclass(Class)} on the argument part class in order to use the proper definition of the specified part class within the instrument hierarchy.
     *
     * @param <T> the part class type.
     * @param partClass the part class.
     * @return the part creator object.
     */
    public abstract <T extends Part>
    PartCreator<T> createPart(
        Class<T> partClass
        );

    /**
     * Creates and returns a part creator object for parts specified by the class supplier.
     * <p/>
     * This method calls {@link #createPart(Class)} internally.
     *
     * @param <T> the part class type.
     * @param partClassSupplier the part class supplier.
     * @return the part creator object.
     */
    public <T extends Part>
    PartCreator<T> createPart(
        final Supplier<Class<T>> partClassSupplier
        ) {
        return createPart(partClassSupplier.get());
    }

    /**
     * Creates and returns an intermediary {@link PartFinder} object with methods intended for finding a single instrument part.
     * <p/>
     * This is the sole method for finding a single part within the instrument.
     * A part class, just like any other class, can have multiple constructors.
     * Part objects are created by calling the {@code createPart(Class)} method and, at that point, will have their signature registered in the process as well.
     * From that point on, a created part object can be looked up using the same constructor parameters at creation time, which is named part signature in this context.
     * Therefore, the correct order of chained method calls for finding a single part looks like:
     * <pre>
     * {@code instrument
     * .findPart(partClass)
     * .withConstructor(parameterTypes)
     * .withValues(parameterValues)}
     * </pre>
     * <p/>
     * When a part class has only a single registered signature, there is no need to call the {@code withConstructor()} method, and the chain above can be simplified to:
     * <pre>
     * {@code instrument
     * .findPart(partClass)
     * .withValues(parameterValues)}
     * </pre>
     * <p/>
     * Predicates can be used as part values, passed to the {@code withValues()} method, to indicate criteria under which a specific part must fall in order for the part object to become eligible for selection as the result.
     * See {@link Part.Parameter} for a list of already available predicates.
     * <p/>
     * If a part with the specified signature and values is found, it will be returned.
     * Otherwise, {@code null} will be returned.
     * If multiple parts are found, a {@code RuntimeException} type is thrown.
     *
     * @param <T> the part class type.
     * @param partClass the part class.
     * @return the part finder object.
     */
    public abstract <T extends Part>
    PartFinder<T> findPart(
        Class<T> partClass
        );

    /**
     * Creates and returns a part finder object for parts specified by the class supplier.
     * <p/>
     * This method calls {@link #findPart(Class)} internally.
     *
     * @param <T> the part class type.
     * @param partClassSupplier the part class supplier.
     * @return the part finder object.
     */
    public <T extends Part>
    PartFinder<T> findPart(
        final Supplier<Class<T>> partClassSupplier
        ) {
        return findPart(partClassSupplier.get());
    }

    /**
     * Creates and returns an intermediary {@link PartListFinder} object with methods intended for finding multiple instrument parts.
     * <p/>
     * This is the sole method for finding list of parts within the instrument.
     * A part class, just like any other class, can have multiple constructors.
     * Part objects are created by calling the {@code createPart(Class)} method and, at that point, will have their signature registered in the process as well.
     * From that point on, a created part object can only be looked up using the same constructor parameters used at creation time, which is called part signature in this context.
     * Therefore, the correct order of the chained method calls for finding a list of parts looks like:
     * <pre>
     * {@code instrument
     * .findParts(partClass)
     * .withConstructor(parameterTypes)
     * .withValues(parameterValues)}
     * </pre>
     * <p/>
     * When a part class has only a single registered signature, there is no need to call the {@code withConstructor()} method, and the chain above can be simplified to:
     * <pre>
     * {@code instrument
     * .findParts(partClass)
     * .withValues(parameterValues)}
     * </pre>
     * <p/>
     * Predicates can be used as part values, passed to the {@code withValues()} method, to indicate criteria under which a specific part must fall in order for the part object to become eligible for selection in the result.
     * See {@link Part.Parameter} for a list of already available predicates.
     * <p/>
     * If one or more parts with the specified signature and values are found, a list containing the parts will be returned.
     * Otherwise, an empty list will be returned.
     *
     * @param <T> the part class type.
     * @param partClass the part class.
     * @return the part list finder object.
     */
    public abstract <T extends Part>
    PartListFinder<T> findParts(
        Class<T> partClass
        );

    /**
     * Creates and returns a part list finder object for parts specified by the class supplier.
     * <p/>
     * This method calls {@link #findParts(Class)} internally.
     *
     * @param <T> the part class type.
     * @param partClassSupplier the part class supplier.
     * @return the part list finder object.
     */
    public <T extends Part>
    PartListFinder<T> findParts(
        final Supplier<Class<T>> partClassSupplier
        ) {
        return findParts(partClassSupplier.get());
    }

    /**
     * Returns the default concrete subclass for the specified part class.
     * <p/>
     * This method is an essential part of the design of all instrument types for selection of concrete part class available at runtime.
     * It provides a functional layer of abstraction for instruments that can have more than one part class representing a similar part depending on the instrument's characteristics.
     * It also hides those classes by allowing the use of available abstract part classes within an instrument class, reducing the level of complexity in the decision making process during creation for all parts.
     * <p/>
     * By convention, all calls to {@code createPart(Class)} methods within the score of the {@code instrument} class and its inner classes must internally use this method in order to reach the proper definition of the specified part class within the instrument hierarchy.
     *
     * @param <T> the part class type.
     * @param partClass the part class.
     * @return the default concrete subclass.
     */
    protected abstract <T extends Part>
    Class<? extends T> getDefaultConcreteSubclass(
        Class<T> partClass
        );

    /**
     * Returns the orchestration for this instrument.
     *
     * @return the orchestration.
     */
    public abstract
    Awareness getOrchestration();

    /**
     * {@code Accessory} represents all instrument accessories.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Accessory
    implements
        musical.instruments.Accessory,
        Part
    {}

    /**
     * {@code AtomicPart} represents a part that doesn't require to be broken down to smaller parts.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class AtomicPart
    implements ConcretePart
    {
        @Override
        public int hashCode() {
            return Objects.hash(Instrument.this, this);
        }
    }

    /**
     * {@code Category} classifies, by annotation, part groups containing smaller part types in hierarchical design of instruments.
     * It also can be applied to single part classes that alternatively rename individual parts or instances of part types.
     * <p/>
     * One aim of this annotation type is to resolve runtime unavailability of generated content types within the respective containing part class of a {@link Universal.ComprehensiveGroup} type, when not naturally derivable from inheritance or uniformity in problem space, by revealing those content types.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Retention(RUNTIME)
    @Target(TYPE)
    public
    @interface Category
    {
        /**
         * {@code Instance} classifies, by annotation, category instances individually for each available type and allows binding of instances among the categorized constructor parameters.
         * <p/>
         * By design, the {@code values} strings accept multiple parameter values for a single parameter to indicate that the instance constructor parameter is permuted for all specified values.
         * This saves space by reducing the total number of annotations used but imposes some logical, yet practical, limitations.
         * Also, if a {@code values} string matches the pattern "p1 -&gt; p2" it indicates that the instance constructor parameter (p1) is inclusively bound to the categorized constructor parameter (p2).
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Retention(RUNTIME)
        @Target(TYPE)
        @Repeatable(Instances.class)
        public
        @interface Instance
        {
            /** The similar part type. */
            Class<? extends Part> type();

            /** The instance definition. (constructor parameter-value pairs) */
            String[] values()
            default "";

            /** The categorized part hash. */
            int[] hash()
            default Integer.MIN_VALUE;
        }

        /**
         * {@code Instances} is the container for the repeatable {@link Instance} values.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Retention(RUNTIME)
        @Target(TYPE)
        public
        @interface Instances
        {
            /** The repeating annotated values. */
            Instance[] value();
        }
    }

    /**
     * {@code Color} represents color for all instrument parts.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    enum Color
    implements
        Spectral.Filter,
        musical.performance.system.Type<Color>
    {
        /** White. (1) */
        White((short) 1),

        /** Black. (2) */
        Black((short) 2),

        /** Red. (4) */
        Red((short) 4),

        /** Blue. (8) */
        Blue((short) 8),

        /** Yellow. (16) */
        Yellow((short) 16),

        /** Purple. (32) */
        Purple((short) 32),

        /** Orange. (64) */
        Orange((short) 64),

        /** Green. (128) */
        Green((short) 128),

        /** Gray. (256) */
        Gray((short) 256),

        /** Cyan. (512) */
        Cyan((short) 512),

        /** Pink. (1024) */
        Pink((short) 1024),

        /** Brown. (2048) */
        Brown((short) 2048),

        /** Light. (4095) */
        Light((short) 4095),

        /** Medium. (4096) */
        Medium((short) 4096),

        /** Dark. (4097) */
        Dark((short) 4097),

        /** On. (32765) */
        On((short) 32765),

        /** Off. (32766) */
        Off((short) 32766),

        /** Flash. (32767) */
        Flash((short) 32767),

        /** Default. (0) */
        Default((short) 0);

        /** The color code. */
        private final
        short code;

        /**
         * Creates a color with the specified code.
         *
         * @param code the color code.
         */
        private
        Color(
            final short code
            ) {
            this.code = code;
        }

        @Override
        public Iterable<? extends musical.performance.system.Type<Color>> filter(
            final Number... numbers
            ) { return null; }

        @Override
        public boolean is(final Type<? extends Color> type) {
            return type == this;
        }

        @Override
        public Iterable<? extends Short> decompose() {
            return new Iterable<Short>()
            {
                @Override
                public Iterator<Short> iterator() {
                    return new Iterator<Short>()
                    {
                        @Override
                        public boolean hasNext() { return false; }

                        @Override
                        public Short next() { return null; }
                    };
                }
             };
        }

        /**
         * Returns the color code.
         *
         * @return the color code.
         */
        public
        short getCode() {
            return code;
        }

        /**
         * {@code Palette} classifies a combination of colors.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Palette
        extends java.util.function.Function<musical.performance.system.Type<Color>, Number>
        {}
    }

    /**
     * {@code Colored} characterizes an instrument unit with a certain color.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Colored
    {
        public
        Color getColor();
    }

    /**
     * {@code CompositePart} represents a part that is composed of smaller parts.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class CompositePart
    extends PartGroup
    implements ConcretePart
    {
        /**
         * Creates a composite part.
         */
        public
        CompositePart() {
            super();
        }
    }

    /**
     * {@code ConcretePart} classifies an instrument part that can be individually considered, in the performance logic, as a part having physical shape and characteristics specific to its own class.
     * <p/>
     * This interface is used to distinguish between composite parts and part groups.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface ConcretePart
    extends Part
    {}

    /**
     * {@code Direction} classifies the movement directions of a body part or another instrument part relative to a musical instrument.
     * <p/>
     * This relativity is defined against an axis, or a set of axes, called the performance axis (or axes) of the instrument which is predefined, by convention, for each instrument class in the context of movement. (or change)
     * Performer classes that are in charge of generating interactions and techniques specific to their musical instrument are aware of the physical interpretation of these directions in the context theirs are used in.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Direction
    extends musical.performance.system.Type<Direction>
    {}

    /**
     * {@code Grouping} classifies groups of instruments as a single {@link Group}.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Grouping
    extends musical.performance.Group
    {}

    /**
     * {@code Ordered} classifies an instrument part, part group, or grouping that has an order among similar parts of a composite part, part group, or another arbitrary containing unit.
     *
     * @param <T> the order type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Ordered<T>
    extends system.data.Ordered
    {
        /**
         * Returns the order of the part within its natural containing part or containing unit.
         *
         * @return the part order.
         */
        public
        T getOrder();

        /**
         * {@code PerMany} classifies instrument units that have an order among many parts, part groups, or other arbitrary containing units.
         *
         * @param <T> the order type.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface PerMany<T>
        extends system.data.Ordered.PerMany
        {
            /**
             * Returns the order of the unit within a larger unit or part, usually naturally containing the smaller unit.
             *
             * @param containingUnitClass the containing unit class.
             * @return the unit order or null if order does not exist.
             */
            public
            T getOrder(
                Class<? extends Unit> containingUnitClass
                );
        }
    }

    /**
     * {@code Part} classifies mapped instrument parts.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Part
    extends
        State.Machine,
        Supplier<Integer>,
        musical.performance.system.Type<Part>,
        Unit
    {
        /**
         * Returns the instrument of this part.
         *
         * @return the instrument.
         */
        public
        Instrument getInstrument();

        /**
         * {@code Categorized} classifies, by annotation, constructor parameters giving them pseudo names and ranges that will be used by {@link Category} annotation types or other units of logic.
         * <p/>
         * This annotation aims to simplify identification of constructor parameters and to resolve ambiguities among instances when there exists more than one part type within a {@code Category} type.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Retention(RUNTIME)
        @Target(CONSTRUCTOR)
        public
        @interface Categorized
        {
            /** Constructor parameter pseudo names. */
            String[] parameters()
            default "";

            /** Constructor parameter ranges. */
            int[] ranges()
            default Integer.MIN_VALUE;
        }

        /**
         * {@code Connection} classifies, by annotation, parts that connect to other parts of the instrument.
         * <p/>
         * Since this annotation type is repeatable it is up to the application context to resolve relations among its {@link Definition} annotations by design.
         * This task can be achieved by the proper use of connection IDs.
         * The repeating annotations intend to allow more than one connection type for an individual part type and to identify those connections separately in code.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Retention(RUNTIME)
        @Target(TYPE)
        @Repeatable(Connections.class)
        public
        @interface Connection
        {
            /** The connection ID. */
            String id()
            default "";

            /** The connecting part instance definition. (constructor parameter-value pairs) */
            String[] instance()
            default "";

            /** The connecting part hash. */
            int hash()
            default Integer.MIN_VALUE;

            /** The connected part types. */
            Class<? extends Part>[] parts();

            /**
             * {@code Definition} classifies, by annotation, part types that are connected by another part of the instrument through the use of the {@link Connection} annotation.
             * <p/>
             * Since this annotation type is repeatable it is up to the application context to resolve relation with the {@code Connection} annotation by design.
             * This task can be achieved by the proper use of connection IDs.
             * <p/>
             * This class implementation is in progress.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            @Retention(RUNTIME)
            @Target(TYPE)
            @Repeatable(Definitions.class)
            public
            @interface Definition
            {
                /** The connection definition ID. */
                String id()
                default "";

                /** The connected part type index. */
                int index();

                /** The connected part instance definitions. (constructor parameter-value pairs) */
                String[] instance()
                default "";

                /** The categorized part hash. */
                int hash()
                default Integer.MIN_VALUE;
            }

            /**
             * {@code Definitions} is the container for the repeatable {@link Definition} values.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            @Retention(RUNTIME)
            @Target(TYPE)
            public
            @interface Definitions
            {
                /** The repeating annotated values. */
                Definition[] value();
            }
        }

        /**
         * {@code Connections} is the container for the repeatable {@link Connection} values.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Retention(RUNTIME)
        @Target(TYPE)
        public
        @interface Connections
        {
            /** The repeating annotated values. */
            Connection[] value();
        }

        /**
         * {@code Parameter} classifies a predicate for part constructor parameter values or indexes, that is used during part lookup in {@code findPart()} and {@code findParts()} methods, to identify the condition, specific to instrument part's order of creation, that the parameter must fall in for the part object to become eligible for selection in the result.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public static abstract
        class Parameter
        implements Predicate<Map.Entry<Object, Part>>
        {
            /** Predicate that filters any existing part parameter value. */
            public static
            Parameter Any = new Index(i -> true);

            /** Predicate that filters the first part parameter value in part creation order. */
            public static
            Parameter First = new Index(i -> i == 0);

            /** Predicate that filters the second part parameter value in part creation order. */
            public static
            Parameter Second = new Index(i -> i == 1);

            /** Predicate that filters the third part parameter value in part creation order. */
            public static
            Parameter Third = new Index(i -> i == 2);

            /** Predicate that filters the fourth part parameter value in part creation order. */
            public static
            Parameter Fourth = new Index(i -> i == 3);

            /** Predicate that filters the fifth part parameter value in part creation order. */
            public static
            Parameter Fifth = new Index(i -> i == 4);

            /** Predicate that filters the sixth part parameter value in part creation order. */
            public static
            Parameter Sixth = new Index(i -> i == 5);

            /** Predicate that filters the sixth part parameter value in part creation order. */
            public static
            Parameter Seventh = new Index(i -> i == 6);

            /** Predicate that filters the sixth part parameter value in part creation order. */
            public static
            Parameter Eighth = new Index(i -> i == 7);

            /** Predicate that filters the sixth part parameter value in part creation order. */
            public static
            Parameter Ninth = new Index(i -> i == 8);

            /** Predicate that filters the sixth part parameter value in part creation order. */
            public static
            Parameter Tenth = new Index(i -> i == 9);

            /** Predicate that filters the sixth part parameter value in part creation order. */
            public static
            Parameter Eleventh = new Index(i -> i == 10);

            /** Predicate that filters the sixth part parameter value in part creation order. */
            public static
            Parameter Twelvth = new Index(i -> i == 11);

            /** Predicate that filters the sixth part parameter value in part creation order. */
            public static
            Parameter Last = new Index(i -> i == null);

            /** The part parameter order predicate function. */
            protected final
            Predicate<Map.Entry<Object, Part>> fn;

            /**
             * Creates a parameter with the specified part parameter order predicate function of a map entry of instrument part hashes to an arbitrary order, such as the order of their creation.
             *
             * @param fn the part parameter order predicate function.
             */
            public
            Parameter(
                final Predicate<Map.Entry<Object, Part>> fn
                ) {
                this.fn = fn;
            }

            /**
             * Creates and returns a new value predicate that filters the part parameter values that are among the specified values.
             *
             * @param values the values.
             * @return the value predicate.
             */
            public static
            Parameter in(
                final Object... values
                ) {
                return new Value(v -> {
                    for (final Object value : values)
                        if (value.equals(v))
                            return true;

                    return false;
                });
            }

            /**
             * Creates and returns a new value predicate that filters the part parameter values that are among the collection of values.
             *
             * @param values the collection of values.
             * @return the value predicate.
             */
            public static
            Parameter in(
                final Collection<Object> values
                ) {
                return new Value(v -> values.contains(v));
            }

            /**
             * Creates and returns a new value predicate that filters the part parameter values that aren't among the specified values.
             *
             * @param values the values.
             * @return the value predicate.
             */
            public static
            Parameter nin(
                final Object... values
                ) {
                return new Value(v -> {
                    for (final Object value : values)
                        if (value.equals(v))
                            return false;

                    return true;
                });
            }

            /**
             * Creates and returns a new value predicate that filters the part parameter values that aren't among the collection of values.
             *
             * @param values the collection of values.
             * @return the value predicate.
             */
            public static
            Parameter nin(
                final Collection<Object> values
                ) {
                return new Value(v -> !values.contains(v));
            }

            /**
             * Tests this parameter against the specified part parameter order.
             *
             * @param order the part parameter order.
             */
            @Override
            public boolean test(final Map.Entry<Object, Part> order) {
                return fn.test(order);
            }

            /**
             * {@code Index} represents a parameter index predicate referring to the order that the parameter value was used during part creation.
             * <p/>
             * This class implementation is in progress.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static
            class Index
            extends Parameter
            {
                /**
                 * Creates an index parameter with the specified predicate function.
                 *
                 * @param fn the predicate function.
                 */
                public
                Index(
                    final Predicate<Integer> fn
                    ) {
                    super(entry -> fn.test(entry == null
                                          ? null
                                          : entry.getValue() == null
                                            ? entry.getKey() instanceof Integer
                                              ? (Integer) entry.getKey()
                                              : null
                                            : entry.getValue().get()));
                }
            }

            /**
             * {@code Value} represents a parameter value predicate.
             * <p/>
             * This class implementation is in progress.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static
            class Value
            extends Parameter
            {
                /**
                 * Creates a value parameter with the specified predicate function.
                 *
                 * @param fn the predicate function.
                 */
                public
                Value(
                    final Predicate<Part> fn
                    ) {
                    super(entry -> fn.test(entry.getValue()));
                }
            }
        }
    }

    /**
     * {@code PartCreator} is an intermediary class that facilitates individual part creation.
     * <p/>
     * This class implementation is in progress.
     *
     * @param <T> the part class type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class PartCreator<T extends Part>
    extends PartSignatureWorker<T>
    {
        /**
         * Creates a part creator for the specified part class.
         *
         * @param partClass the part class.
         */
        public
        PartCreator(
            final Class<T> partClass
            ) {
            super(partClass);
        }

        @Override
        public abstract
        PartCreator<T> withConstructor(final Class<?>... parameterTypes);

        /**
         * Sets the part signature parameter ranges, or throws a {@code RuntimeException} type if negative or zero range is passed.
         *
         * @param parameterRanges the parameter ranges.
         * @return the part creator object.
         */
        public abstract
        PartCreator<T> withParameterRanges(
            final int... parameterRanges
            );

        /**
         * Creates the part object with the specified constructor parameter values, or throws a {@code RuntimeException} type if instantiation fails.
         *
         * @param parameterValues the parameter values.
         * @return the part creator object.
         */
        public abstract
        PartCreator<T> withValues(
            final Object... parameterValues
            );
    }

    /**
     * {@code PartFinder} is an intermediary class that facilitates finding an instrument part by its constructor parameter value used at creation time.
     * <p/>
     * This class implementation is in progress.
     *
     * @param <T> the part class type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class PartFinder<T extends Part>
    extends PartSignatureFinder<T>
    {
        /**
         * Creates a part finder for the specified part class.
         *
         * @param partClass the part class.
         */
        public
        PartFinder(
            final Class<T> partClass
            ) {
            super(partClass);
        }

        @Override
        public abstract
        PartFinder<T> withConstructor(final Class<?>... parameterTypes);

        /**
         * Sets the comparator function for parts within the part group.
         *
         * @return the comparator function.
         *
         * @param comparator the comparator function.
         */
        public abstract
        Iterable<? extends ConcretePart> withComparator(
            Comparator<? extends ConcretePart> comparator
            );

        /**
         * Returns the instrument part with matching specified parameter values used at creation time, or null if it doesn't exist.
         *
         * @param parameterValues the constructor parameter values or predicates.
         *
         * @return the matched parts, or null if not found.
         */
        public abstract
        T withValues(
            final Object... parameterValues
            );
    }

    /**
     * {@code PartGroup} represents an arbitrary group of atomic or composite parts that are uniformly applicable in a certain context.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class PartGroup
    implements
        Group,
        Part
    {
        /**
         * Creates a part group.
         */
        public
        PartGroup() {}

        /**
         * Returns an iterable for parts within the part group.
         *
         * @return the iterable of parts.
         */
        public abstract
        Iterable<? extends ConcretePart> getParts();

        /**
         * Returns a part creator object for parts with the specified class type within the part group.
         * <p/>
         * By convention, all overloads of this method, or its returned intermediary object, must call {@link #getDefaultConcreteSubclass(Class)} on the argument part class in order to use the proper definition of the specified part class within the instrument hierarchy.
         *
         * @param <T> the part class type.
         * @param partClass the part class.
         *
         * @return the part creator object.
         */
        public abstract <T extends Part>
        PartCreator<T> createPart(
            Class<T> partClass
            );

        /**
         * Returns a part creator object for parts specified by the class supplier within the part group.
         * <p/>
         * This method calls {@link #createPart(Class)} internally.
         *
         * @param <T> the part class type.
         * @param partClassSupplier the part class supplier.
         *
         * @return the part creator object.
         */
        public <T extends Part>
        PartCreator<T> createPart(
            final Supplier<Class<T>> partClassSupplier
            ) {
            return createPart(partClassSupplier.get());
        }

        /**
         * Returns a part finder object for a part with the specified class type within the part group.
         *
         * @param <T> the part class type.
         * @param partClass the part class.
         *
         * @return the part finder object.
         */
        public abstract <T extends Part>
        PartFinder<T> findPart(
            Class<T> partClass
            );

        /**
         * Returns a part finder object for a part specified by the class supplier within the part group.
         *
         * @param <T> the part class type.
         * @param partClassSupplier the part class supplier.
         *
         * @return the part finder object.
         */
        public <T extends Part>
        PartFinder<T> findPart(
            final Supplier<Class<T>> partClassSupplier
            ) {
            return findPart(partClassSupplier.get());
        }

        /**
         * Returns a part list finder object for parts with the specified class type within the part group.
         *
         * @param <T> the part class type.
         * @param partClass the part class.
         *
         * @return the part list finder object.
         */
        public abstract <T extends Part>
        PartListFinder<T> findParts(
            Class<T> partClass
            );

        /**
         * Returns a part list finder object for a part specified by the class supplier within the part group.
         *
         * @param <T> the part class type.
         * @param partClassSupplier the part class supplier.
         *
         * @return the part list finder object.
         */
        public <T extends Part>
        PartListFinder<T> findParts(
            final Supplier<Class<T>> partClassSupplier
            ) {
            return findParts(partClassSupplier.get());
        }
    }

    /**
     * {@code PartListFinder} is an intermediary class that facilitates finding lists of instrument parts by their constructor parameter values used at creation time.
     * <p/>
     * This class implementation is in progress.
     *
     * @param <T> the part class type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class PartListFinder<T extends Part>
    extends PartSignatureFinder<T>
    {
        /**
         * Creates a part list finder for the specified part class.
         *
         * @param partClass the part class.
         */
        public
        PartListFinder(
            final Class<T> partClass
            ) {
            super(partClass);
        }

        @Override
        public abstract
        PartListFinder<T> withConstructor(final Class<?>... parameterTypes);

        /**
         * Sets the comparator function for parts within the part group.
         *
         * @return the comparator function.
         *
         * @param comparator the comparator function.
         */
        public abstract
        Iterable<? extends ConcretePart> withComparator(
            Comparator<? extends ConcretePart> comparator
            );

        /**
         * Returns a list of instrument parts with matching specified parameter values used at creation time.
         *
         * @param parameterValues the constructor parameter values or predicates.
         * @return the list of matched parts.
         */
        public abstract
        List<T> withValues(
            final Object... parameterValues
            );
    }

    /**
     * {@code PartSignature} is a representation of part class constructors that are called to create part objects.
     * <p/>
     * It holds various meta-data about constructor parameters to facilitate storing parts in the instrument's map of parts.
     * <p/>
     * This class implementation is in progress.
     *
     * @param <T> the part class type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected
    class PartSignature<T extends Part>
    {
        /** The part class. */
        protected final
        Class<? extends Part> partClass;

        /** The part constructor. */
        protected
        Constructor<T> constructor;

        /** The part constructor parameter types. (without the leading instrument class) */
        protected
        Class<?>[] parameterTypes;

        /** The part constructor parameter value ranges. */
        protected
        int[] parameterRanges;

        /**
         * Creates a part signature for the specified part class.
         *
         * @param partClass the part class.
         */
        protected
        PartSignature(
            final Class<? extends Part> partClass
            ) {
            this.partClass = partClass;
        }

        /**
         * Returns true if the specified parameter types match those of the part signature, and false otherwise.
         *
         * @param parameterTypes the parameter types.
         * @return true if parameter types match those of the part signature, and false otherwise.
         */
        boolean hasParameterTypes(
            final Class<?>... parameterTypes
            ) {
            if (this.parameterTypes.length != parameterTypes.length)
                return false;

            int j = 0;
            for (int i = 0; i < parameterTypes.length; i++)
                if (this.parameterTypes[i] != parameterTypes[j++])
                    return false;

            return true;
        }

        /**
         * Returns a new instance of the part represented by the part signature given the specified constructor parameter values.
         *
         * @param parameterValues the constructor parameter values.
         * @return the part object.
         * @throws ExceptionInInitializerError if the initialization provoked by this method fails.
         * @throws IllegalAccessException if this {@code Constructor} object is enforcing Java language access control and the underlying constructor is inaccessible.
         * @throws IllegalArgumentException if the number of actual and formal parameters differ; if an unwrapping conversion for primitive arguments fails; or if, after possible unwrapping, a parameter value cannot be converted to the corresponding formal parameter type by a method invocation conversion; if this constructor pertains to an enum type.
         * @throws InstantiationException if the class that declares the underlying constructor represents an abstract class.
         * @throws InvocationTargetException if the underlying constructor throws an exception.
         */
        T newInstance(
            final Object... parameterValues
            )
        throws
            ExceptionInInitializerError,
            IllegalAccessException,
            IllegalArgumentException,
            InstantiationException,
            InvocationTargetException
        {
            final Object[] newParameterValues = new Object[parameterValues.length];
            newParameterValues[0] = Instrument.this;
            for (int i = 0; i < parameterValues.length; i++)
                newParameterValues[i + 1] = parameterValues[i];

            return constructor.newInstance(newParameterValues);
        }

        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof PartSignature))
                return false;

            final PartSignature<? extends Part> signature = (PartSignature<? extends Part>) obj;
            if (signature.partClass != partClass)
                return false;

            return hasParameterTypes(signature.parameterTypes);
        }
    }

    /**
     * {@code PartSignatureFinder} is the superclass for all intermediary finder classes that are in charge of finding instrument parts.
     * <p/>
     * This class implementation is in progress.
     *
     * @param <T> the part class type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class PartSignatureFinder<T extends Part>
    extends PartSignatureWorker<T>
    {
        /**
         * Creates a part signature finder for the specified part class.
         *
         * @param partClass the part class.
         */
        protected
        PartSignatureFinder(
            final Class<T> partClass
            ) {
            super(partClass);
        }
    }

    /**
     * {@code PartSignatureWorker} is the superclass for all intermediary classes that work with a specific part signature to create or lookup instrument parts, and provides methods that guarantee the correct order in chained method calls.
     * <p/>
     * This class implementation is in progress.
     *
     * @param <T> the part class type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class PartSignatureWorker<T extends Part>
    {
        /** The part class. */
        protected final
        Class<? extends Part> partClass;

        /** The part signature. */
        protected
        PartSignature<T> partSignature;

        /**
         * Creates a part signature worker with the specified part class.
         *
         * @param partClass the part class.
         */
        protected
        PartSignatureWorker(
            final Class<T> partClass
            ) {
            this.partClass = partClass;
        }

        /**
         * Verifies that part constructor parameter value ranges array in part signature can be safely ignored; or otherwise, is set and matches the number of the specified parameter values; or throws a {@code RuntimeException} type if either of the conditions fail.
         *
         * @param parameterValues the parameter values.
         */
        void verifyPartRangesMatch(
            final Object... parameterValues
            ) {
            if (partSignature.parameterRanges == null && partSignature.parameterTypes.length > 0)
                throw new IllegalStateException("withParameterRanges() method has not been called for the part signature yet.");
            else
                if (partSignature.parameterRanges.length != parameterValues.length)
                throw new IllegalArgumentException("The parameter ranges for the part signature shows a different number of parameter values.");
        }

        /**
         * Verifies that part signature is present or can be auto-selected, or throws an {@code IllegalStateException} otherwise.
         */
        abstract
        void verifyPartSignatureExists();

        /**
         * Identifies a part class constructor.
         *
         * @param parameterTypes the constructor parameter types.
         * @return the part signature worker object.
         */
        public abstract
        PartSignatureWorker<T> withConstructor(
            Class<?>... parameterTypes
            );
    }

    /**
     * {@code State} represents all instrument part and group states.
     * <p/>
     * One aim of this interface is to allow the instrument states to be distinguishable quickly by type comparison.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class State
    implements musical.performance.State
    {}

    /**
     * {@code Uniformed} classifies, by annotation, part class types that belong to a larger natural part group.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Retention(RUNTIME)
    @Target(TYPE)
    public
    @interface Uniformed
    {
        /** The part group to which uniformity relates. */
        Class<? extends Group> group();

        /** The number of other similar parts in the group. */
        int count()
        default 1;
    }

    /**
     * {@code Universal} identifies instrument-specific universality.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Universal
    {
        /**
         * {@code ComprehensiveGroup} classifies part groups that contain all similarly identified parts of an instrument with respect to another containing part class of the same instrument.
         * <p/>
         * If a classified respective containing part class doesn't exist in the problem space, the {@code Instrument} type must be used by convention.
         * If the classified respective containing part class is uniformly contained by a larger group or groups itself, the largest uniform group must be selected in order to reflect, and make applicable by common sense, the naturally associative property of the instrument part hierarchies when available.
         * <p/>
         * This class implementation is in progress.
         *
         * @param <T> the identified part class type.
         * @param <S> the respective containing part class type.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface ComprehensiveGroup<T extends Instrument.Part, S extends Group>
        {}

        /**
         * {@code Motion} classifies motion types for all instruments.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Motion
        {}

        /**
         * {@code Orientation} classifies orientations for all instruments.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Orientation
        {}

        /**
         * {@code Plane} classifies geometrical planes for all instruments.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Plane
        {}

        /**
         * {@code Zone} classifies spatial zones for all instruments.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Zone
        {
            /**
             * {@code Characteristic} categorizes all spatial zones.
             * <p/>
             * This class implementation is in progress.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public
            interface Characteristic
            {}
        }
    }
}