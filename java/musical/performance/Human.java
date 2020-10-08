package musical.performance;

import system.Type;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Collection;
import java.util.Map;

/**
 * {@code Human} classifies a performer's physical body.
 * <p/>
 * This superclass defines a model of the human anatomy, sufficiently simplified for representing the postures and movements in music performance.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Human
extends Instrument
{
    /**
     * Creates a human with the specified body part classes and orientations.
     * <p/>
     * If a body part class is non-orientational, it is created regardless of the orientations it maps to.
     * Otherwise, if the mapped collection is null or empty, both orientations will be created; and if it is not empty, only the specified orientations will be created.
     *
     * @param bodyPartOrientationMap the map of body part classes to iterable of orientations.
     * @throws NullPointerException if the map is null.
     */
    protected
    Human(
        final Map<Class<? extends BodyPart>, Collection<Orientation>> bodyPartOrientationMap
        ) {
        // For each body part class orientation...
        bodyPartOrientationMap
        .keySet()
        .forEach(bodyPartClass -> {
            // If the part class is orientational...
            if (isOrientational(bodyPartClass)) {
                // Instantiate the body part creator object
                final PartCreator<? extends Part> bodyPartCreator = createPart(bodyPartClass)
                                                                    .withParameterRanges(2);

                // If the part class is mapped to null or an empty list, create both orientations
                final Collection<Orientation> orientations = bodyPartOrientationMap.get(bodyPartClass);
                if (orientations == null || orientations.isEmpty())
                    bodyPartCreator
                    .withValues(Orientation.RIGHT)
                    .withValues(Orientation.LEFT);

                // Otherwise, create the part with specific orientations
                else
                    orientations.forEach(
                        orientation -> bodyPartCreator
                                       .withValues(orientation)
                        );
            }
            // Otherwise, create the part as non-orientational part
            else
                createPart(bodyPartClass);
        });
    }

    /**
     * Creates a human with the specified body part classes.
     * <p/>
     * If a body part class is orientational both orientations will be created.
     * Null entries will be ignored.
     *
     * @param bodyPartClasses the body part classes to be instantiated.
     * @throws NullPointerException if the iterable is null.
     */
    protected
    Human(
        final Iterable<Class<? extends BodyPart>> bodyPartClasses
        ) {
        bodyPartClasses.forEach(
            bodyPartClass -> {
                if (bodyPartClass != null)
                    createBodyPart(bodyPartClass);
        });
    }

    /**
     * Creates a human with the specified body part classes.
     * <p/>
     * If a body part class is orientational both orientations will be created.
     * Null entries will be ignored.
     *
     * @param bodyPartClasses the body part classes to be instantiated.
     */
    protected
    Human(
        final Class<? extends BodyPart>... bodyPartClasses
        ) {
        for (final Class<? extends BodyPart> bodyPartClass : bodyPartClasses)
            if (bodyPartClass != null)
                createBodyPart(bodyPartClass);
    }

    /**
     * Creates a human with all body parts.
     */
    protected
    Human() {
        this(
            Head.class,
            Neck.class,
            Torso.class,
            Shoulder.class,
            Arm.class,
            Hip.class,
            Leg.class
        );
    }

    /**
     * Returns true if the specified body part class, or one of its superclasses, is orientational, and false otherwise.
     *
     * @param <T> the body part class type.
     * @param partClass the body part class.
     * @return true if the body part class represents an orientational part, and false otherwise.
     */
    protected static <T extends BodyPart>
    boolean isOrientational(
        final Class<T> partClass
        ) {
        // Iterate through superclasses of part class up to the BodyPart class type and...
        for (Class<? super T> partSuperClass = partClass; partSuperClass != BodyPart.class; partSuperClass = partSuperClass.getSuperclass())
            // For the implemented interfaces...
            for (final Class<?> i : partSuperClass.getInterfaces())
                // If an interface is Orientational return true
                if (i == Orientational.class)
                    return true;

        return false;
    }

    /**
     * Creates a body part of the specified class.
     *
     * @param bodyPartClass the body part class.
     * @throws NullPointerException if the body part class is null.
     */
    protected
    void createBodyPart(
        final Class<? extends BodyPart> bodyPartClass
        ) {
        // If the part class is orientational, create both orientations
        if (isOrientational(bodyPartClass))
            createPart(bodyPartClass)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);

        // Otherwise, create the part as non-orientational part
        else
            createPart(bodyPartClass);
    }

    /**
     * {@code Abdomen} represents the human abdomen.
     * <p/>
     * The human abdomen is the lower part of the torso also known as the belly.
     * It is the joint in the torso connecting the waist to the thorax.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Human.class)
    @BodyPart.Connection(parts = { Waist.class, Thorax.class })
    public abstract
    class Abdomen
    extends Joint
    implements
        External,
        Mid,
        TorsoPart
    {
        /**
         * Creates an abdomen.
         */
        protected
        Abdomen() {
            super();
        }
    }

    /**
     * {@code AllFingers} represents all fingers of the human hands.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Fingers.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFingers
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<Fingers, Hands>
    {
        /**
         * Creates all fingers.
         */
        protected
        AllFingers() {
            super();

            createPart(Fingers.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code AllFingerJoints} represents all finger joints of the human hands.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FingerJoints.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFingerJoints
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<FingerJoints, Hands>
    {
        /**
         * Creates all finger joints.
         */
        protected
        AllFingerJoints() {
            super();

            createPart(FingerJoints.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code AllFingerKnuckles} represents all finger knuckles of the human hands.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FingerKnuckles.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFingerKnuckles
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<FingerKnuckles, Hands>
    {
        /**
         * Creates all finger knuckles.
         */
        protected
        AllFingerKnuckles() {
            super();

            createPart(FingerKnuckles.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code AllFingerNails} represents all finger nails of the human hands.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FingerNails.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFingerNails
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<FingerNails, Hands>
    {
        /**
         * Creates all finger nails.
         */
        protected
        AllFingerNails() {
            super();

            createPart(FingerNails.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code AllFingerTips} represents all finger tips of the human hands.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FingerTips.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFingerTips
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<FingerTips, Hands>
    {
        /**
         * Creates all finger tips.
         */
        protected
        AllFingerTips() {
            super();

            createPart(FingerTips.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code AllFootKnuckles} represents both knuckles of the human feet.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FootKnuckles.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFootKnuckles
    extends OrientationalBodyPartGroup
    implements
        External,
        FootPart,
        Universal.ComprehensiveGroup<FootKnuckles, Feet>
    {
        /**
         * Creates both foot knuckles.
         */
        protected
        AllFootKnuckles() {
            super();

            createPart(FootKnuckles.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code AllFourFingers} represents all four fingers of the human hands excluding the thumbs.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FourFingers.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFourFingers
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<FourFingers, Hands>
    {
        /**
         * Creates all four fingers.
         */
        protected
        AllFourFingers() {
            super();

            createPart(FourFingers.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code AllFourFingerJoints} represents all four finger joints of the human hands excluding the thumb joints.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FourFingerJoints.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFourFingerJoints
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<FourFingerJoints, Hands>
    {
        /**
         * Creates all four finger joints.
         */
        protected
        AllFourFingerJoints() {
            super();

            createPart(FourFingerJoints.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code AllFourFingerKnuckles} represents all four finger knuckles of the human hands excluding the knuckles connecting to the thumbs.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FourFingerKnuckles.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFourFingerKnuckles
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<FourFingerKnuckles, Hands>
    {
        /**
         * Creates all four finger knuckles.
         */
        protected
        AllFourFingerKnuckles() {
            super();

            createPart(FourFingerKnuckles.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code AllFourFingerNails} represents all four finger nails of the human hands excluding the thumb nails.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FourFingerNails.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFourFingerNails
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<FourFingerNails, Hands>
    {
        /**
         * Creates all four finger nails.
         */
        protected
        AllFourFingerNails() {
            super();

            createPart(FourFingerNails.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code AllFourFingerTips} represents all four finger tips of the human hands excluding the tips of the thumbs.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FourFingerTips.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFourFingerTips
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<FourFingerTips, Hands>
    {
        /**
         * Creates all four finger tips.
         */
        protected
        AllFourFingerTips() {
            super();

            createPart(FourFingerTips.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code AllFourPhalanges} represents all phalanges of four fingers of the human hands excluding the phalanges of the thumbs.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FourPhalanges.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFourPhalanges
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<FourPhalanges, Hands>
    {
        /**
         * Creates all four phalanges.
         */
        protected
        AllFourPhalanges() {
            super();

            createPart(FourPhalanges.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code AllPhalanges} represents all phalanges of the human hands.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Phalanges.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllPhalanges
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<Phalanges, Hands>
    {
        /**
         * Creates all phalanges.
         */
        protected
        AllPhalanges() {
            super();

            createPart(Phalanges.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code AllToes} represents both toes of the human feet.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Toes.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllToes
    extends OrientationalBodyPartGroup
    implements
        External,
        FootPart,
        Universal.ComprehensiveGroup<Toes, Feet>
    {
        /**
         * Creates both toes.
         */
        protected
        AllToes() {
            super();

            createPart(Toes.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Ankle} represents the human ankle.
     * <p/>
     * The human ankle is the joint in the leg connecting the lower leg to the foot.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Leg.class)
    @BodyPart.Connection(parts = { LowerLeg.class, Foot.class })
    public abstract
    class Ankle
    extends OrientationalJoint
    implements
        External,
        LegPart
    {
        /**
         * Creates the ankle with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        Ankle(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Ankles} represents both ankles of the human feet.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Ankle.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class Ankles
    extends OrientationalBodyPartGroup
    implements
        External,
        LegPart,
        Universal.ComprehensiveGroup<Ankle, Human>
    {
        /**
         * Creates both ankles.
         */
        protected
        Ankles() {
            super();

            createPart(Ankle.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Arm} represents the human arm.
     * <p/>
     * The human arm is the part of the body containing the upper arm, elbow, lower arm, wrist, and hand.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Human.class)
    @Category
    @Category.Instance(type = UpperArm.class, values = "orientation -> orientation")
    @Category.Instance(type = Elbow.class, values = "orientation -> orientation")
    @Category.Instance(type = LowerArm.class, values = "orientation -> orientation")
    @Category.Instance(type = Wrist.class, values = "orientation -> orientation")
    @Category.Instance(type = Hand.class, values = "orientation -> orientation")
    public abstract
    class Arm
    extends CompositeOrientationalBodyPart
    implements
        External,
        Ordered.PerOrientation,
        Upper
    {
        /**
         * Creates the arm with the specified orientation and containing the upper arm, elbow, lower arm, wrist, and hand.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        Arm(
            final Orientation orientation
            ) {
            super(orientation);

            createPart(UpperArm.class)
            .withParameterRanges(2)
            .withValues(orientation);

            createPart(Elbow.class)
            .withParameterRanges(2)
            .withValues(orientation);

            createPart(LowerArm.class)
            .withParameterRanges(2)
            .withValues(orientation);

            createPart(Wrist.class)
            .withParameterRanges(2)
            .withValues(orientation);

            createPart(Hand.class)
            .withParameterRanges(2)
            .withValues(orientation);
        }
    }

    /**
     * {@code ArmPart} classifies a part of the human arm.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface ArmPart
    extends
        BodyPart,
        Upper
    {
        @Override
        public default Class<? extends CompositeOrientationalBodyPart> getCorrespondingBodyPartClass() {
            return Arm.class;
        }
    }

    /**
     * {@code Arms} represents both arms of the human body.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Arm.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class Arms
    extends OrientationalBodyPartGroup
    implements
        External,
        Universal.ComprehensiveGroup<Arm, Human>,
        Upper
    {
        /**
         * Creates both arms.
         */
        protected
        Arms() {
            super();

            createPart(Arm.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code AtomicBodyPart} classifies a human body part that has the characteristics of an atomic instrument part.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class AtomicBodyPart
    extends AtomicPart
    implements BodyPart
    {}

    /**
     * {@code BodyPart} classifies a human body part.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected
    interface BodyPart
    extends Part
    {
        @Override
        public default boolean is(final Type<? super Part> type) {
            return type instanceof BodyPart;
        }

        /**
         * Returns the body part class corresponding to this body part classification, or null if it doesn't exist.
         *
         * @return the body part class corresponding to this body part classification, or null if it doesn't exist.
         */
        public
        Class<? extends BodyPart> getCorrespondingBodyPartClass();

        /**
         * {@code Connection} classifies, by annotation, body parts that connect to other parts of the human body.
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

            /** Joint connection indicator. */
            boolean joint()
            default true;

            /** The connecting part instance definition. (constructor parameter-value pairs) */
            String[] instance()
            default "";

            /** The connecting part hash. */
            int hash()
            default Integer.MIN_VALUE;

            /** The connected part types. */
            Class<? extends Part>[] parts();

            /**
             * {@code Definition} classifies, by annotation, body part types that are connected by another part of the human body through the use of the {@link Connection} annotation.
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
    }

    /**
     * {@code BodyPartGroup} classifies a human body part group.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class BodyPartGroup
    extends PartGroup
    {}

    /**
     * {@code CompositeBodyPart} classifies a human body part that has the characteristics of a composite instrument part.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class CompositeBodyPart
    extends CompositePart
    implements BodyPart
    {}

    /**
     * {@code CompositeOrientationalBodyPart} classifies a composite body part that is also orientational.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class CompositeOrientationalBodyPart
    extends CompositeBodyPart
    implements Orientational
    {
        /** The orientation. */
        protected final
        Orientation orientation;

        /**
         * Creates a composite orientational body part.
         *
         * @param orientation the orientation.
         */
        protected
        CompositeOrientationalBodyPart(
            final Orientation orientation
            ) {
            this.orientation = orientation;
        }

        @Override
        public Orientation getOrientation() {
            return orientation;
        }
    }

    /**
     * {@code Ear} represents the human ear.
     * <p/>
     * The human ear is the organ on the head through which sound is primarily sensed.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Head.class)
    public abstract
    class Ear
    extends OrientationalFixture
    implements
        External,
        HeadPart,
        Ordered.PerOrientation
    {
        /**
         * Creates the ear with the specified orientation.
         *
         * @param orientation the orientation.
         */
        protected
        Ear(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Ears} represents both human ears.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    public abstract
    class Ears
    extends OrientationalBodyPartGroup
    implements
        External,
        HeadPart,
        Universal.ComprehensiveGroup<Ear, Human>
    {
        /**
         * Creates both ears.
         */
        protected
        Ears() {
            super();

            createPart(Ear.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Elbow} represents the human elbow.
     * <p/>
     * The human elbow is the joint in the arm connecting the upper arm to the lower arm.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Arm.class)
    @BodyPart.Connection(parts = { UpperArm.class, LowerArm.class })
    public abstract
    class Elbow
    extends OrientationalJoint
    implements
        ArmPart,
        External
    {
        /**
         * Creates the elbow with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        Elbow(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Elbows} represents both human elbows.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Elbow.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class Elbows
    extends OrientationalBodyPartGroup
    implements
        ArmPart,
        External,
        Universal.ComprehensiveGroup<Elbow, Human>
    {
        /**
         * Creates both elbows.
         */
        protected
        Elbows() {
            super();

            createPart(Elbow.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code External} classifies an external human body part.
     * <p/>
     * The external body parts are those that are visible.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface External
    extends Universal.Zone.Characteristic
    {}

    /**
     * {@code Eye} represents the human eye.
     * <p/>
     * The human eye is the primary organ of vision on the head.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Head.class)
    public abstract
    class Eye
    extends OrientationalFixture
    implements
        External,
        HeadPart,
        Ordered.PerOrientation
    {
        /**
         * Creates the eye with the specified orientation.
         *
         * @param orientation the orientation.
         */
        protected
        Eye(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Eyes} represents both human eyes.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    public abstract
    class Eyes
    extends OrientationalBodyPartGroup
    implements
        External,
        HeadPart,
        Universal.ComprehensiveGroup<Eye, Human>
    {
        /**
         * Creates both eyes.
         */
        protected
        Eyes() {
            super();

            createPart(Eye.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Feet} represents both human feet.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Foot.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class Feet
    extends OrientationalBodyPartGroup
    implements
        External,
        LegPart,
        Universal.ComprehensiveGroup<Foot, Human>
    {
        /**
         * Creates both feet.
         */
        protected
        Feet() {
            super();

            createPart(Foot.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Finger} represents the finger of the human hand.
     * <p/>
     * The human finger is the outermost part of the hand attached to the palm via its knuckle joint.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Hand.class, count = 5)
    @Category
    @Category.Instance(type = Phalanx.class, values = { "orientation -> orientation", "fingerOrder -> order", "order", "0", "1", "2" })
    @Category.Instance(type = FingerJoint.class, values = { "orientation -> orientation", "fingerOrder -> order", "order", "0", "1" })
    @Category.Instance(type = FingerTip.class, values = { "orientation -> orientation", "fingerOrder -> order" })
    @Category.Instance(type = FingerNail.class, values = { "orientation -> orientation", "fingerOrder -> order" })
    public abstract
    class Finger
    extends CompositeOrientationalBodyPart
    implements
        External,
        HandPart,
        Ordered<Byte>,
        Ordered.PerMany<Byte>
    {
        /** The finger order. */
        protected final
        byte order;

        /**
         * Creates the finger with the specified orientation containing phalanges, joints, finger tips, and nails.
         * <p/>
         * By convention, the order of the thumb is 0, the index finger is 1, the middle finger is 2, the ring finger is 3, and the pinky is 4.
         * <p/>
         * This constructor calls {@link Number#byteValue()} on the order.
         *
         * @param orientation the orientation.
         * @param order the finger order.
         */
        @Categorized(parameters = { "orientation", "order" }, ranges = { 2, 5 })
        protected
        Finger(
            final Orientation orientation,
            final Number order
            ) {
            super(orientation);
            this.order = order.byteValue();

            // Create all the phalanges
            createPart(Phalanx.class)
            .withParameterRanges(2, 5, 3)
            .withValues(orientation, order, 0)
            .withValues(orientation, order, 1)
            .withValues(orientation, order, 2);

            // Create all the joint
            createPart(FingerJoint.class)
            .withParameterRanges(2, 5, 2)
            .withValues(orientation, order, 0)
            .withValues(orientation, order, 1);

            // Create the finger tip
            createPart(FingerTip.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, order);

            // Create the fingernail
            createPart(FingerNail.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, order);
        }

        /**
         * Returns the finger order within the specified body part group, or null if part class is not known to contain the finger.
         *
         * @param partClass the containing part class.
         */
        @Override
        public Byte getOrder(
            final Class<? extends Unit> partClass
            ) {
            if (partClass == FourFingers.class || partClass == Hand.class || partClass == Arm.class)
                return order;

            if (partClass == Hands.class || partClass == AllFourFingers.class || partClass == Arms.class)
                return  (byte) (orientation.order * 5 + order);

            return null;
        }

        /**
         * Returns the finger order.
         *
         * @return the finger order.
         */
        @Override
        public Byte getOrder() {
            return order;
        }
    }

    /**
     * {@code FingerJoint} represents the joint of the human finger.
     * <p/>
     * The human finger joint is the part of the finger that bends.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Finger.class, count = 2)
    @BodyPart.Connection(id = "0", instance = { "orientation", "RIGHT", "fingerOrder", "0", "order", "0" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "0", index = 0, instance = { "orientation", "RIGHT", "fingerOrder", "0", "order", "0" })
    @BodyPart.Connection.Definition(id = "1", index = 1, instance = { "orientation", "RIGHT", "fingerOrder", "0", "order", "1" })
    @BodyPart.Connection(id = "1", instance = { "orientation", "RIGHT", "fingerOrder", "0", "order", "1" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "2", index = 0, instance = { "orientation", "RIGHT", "fingerOrder", "0", "order", "1" })
    @BodyPart.Connection.Definition(id = "3", index = 1, instance = { "orientation", "RIGHT", "fingerOrder", "0", "order", "2" })
    @BodyPart.Connection(id = "2", instance = { "orientation", "RIGHT", "fingerOrder", "1", "order", "0" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "4", index = 0, instance = { "orientation", "RIGHT", "fingerOrder", "1", "order", "0" })
    @BodyPart.Connection.Definition(id = "5", index = 1, instance = { "orientation", "RIGHT", "fingerOrder", "1", "order", "1" })
    @BodyPart.Connection(id = "3", instance = { "orientation", "RIGHT", "fingerOrder", "1", "order", "1" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "6", index = 0, instance = { "orientation", "RIGHT", "fingerOrder", "1", "order", "1" })
    @BodyPart.Connection.Definition(id = "7", index = 1, instance = { "orientation", "RIGHT", "fingerOrder", "1", "order", "2" })
    @BodyPart.Connection(id = "4", instance = { "orientation", "RIGHT", "fingerOrder", "2", "order", "0" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "8", index = 0, instance = { "orientation", "RIGHT", "fingerOrder", "2", "order", "0" })
    @BodyPart.Connection.Definition(id = "9", index = 1, instance = { "orientation", "RIGHT", "fingerOrder", "2", "order", "1" })
    @BodyPart.Connection(id = "5", instance = { "orientation", "RIGHT", "fingerOrder", "2", "order", "1" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "10", index = 0, instance = { "orientation", "RIGHT", "fingerOrder", "2", "order", "1" })
    @BodyPart.Connection.Definition(id = "11", index = 1, instance = { "orientation", "RIGHT", "fingerOrder", "2", "order", "2" })
    @BodyPart.Connection(id = "6", instance = { "orientation", "RIGHT", "fingerOrder", "3", "order", "0" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "12", index = 0, instance = { "orientation", "RIGHT", "fingerOrder", "3", "order", "0" })
    @BodyPart.Connection.Definition(id = "13", index = 1, instance = { "orientation", "RIGHT", "fingerOrder", "3", "order", "1" })
    @BodyPart.Connection(id = "7", instance = { "orientation", "RIGHT", "fingerOrder", "3", "order", "1" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "14", index = 0, instance = { "orientation", "RIGHT", "fingerOrder", "3", "order", "1" })
    @BodyPart.Connection.Definition(id = "15", index = 1, instance = { "orientation", "RIGHT", "fingerOrder", "3", "order", "2" })
    @BodyPart.Connection(id = "8", instance = { "orientation", "RIGHT", "fingerOrder", "4", "order", "0" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "16", index = 0, instance = { "orientation", "RIGHT", "fingerOrder", "4", "order", "0" })
    @BodyPart.Connection.Definition(id = "17", index = 1, instance = { "orientation", "RIGHT", "fingerOrder", "4", "order", "1" })
    @BodyPart.Connection(id = "9", instance = { "orientation", "RIGHT", "fingerOrder", "4", "order", "1" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "18", index = 0, instance = { "orientation", "RIGHT", "fingerOrder", "4", "order", "1" })
    @BodyPart.Connection.Definition(id = "19", index = 1, instance = { "orientation", "RIGHT", "fingerOrder", "4", "order", "2" })
    @BodyPart.Connection(id = "10", instance = { "orientation", "LEFT", "fingerOrder", "0", "order", "0" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "20", index = 0, instance = { "orientation", "LEFT", "fingerOrder", "0", "order", "0" })
    @BodyPart.Connection.Definition(id = "21", index = 1, instance = { "orientation", "LEFT", "fingerOrder", "0", "order", "1" })
    @BodyPart.Connection(id = "11", instance = { "orientation", "LEFT", "fingerOrder", "0", "order", "1" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "22", index = 0, instance = { "orientation", "LEFT", "fingerOrder", "0", "order", "1" })
    @BodyPart.Connection.Definition(id = "23", index = 1, instance = { "orientation", "LEFT", "fingerOrder", "0", "order", "2" })
    @BodyPart.Connection(id = "12", instance = { "orientation", "LEFT", "fingerOrder", "1", "order", "0" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "24", index = 0, instance = { "orientation", "LEFT", "fingerOrder", "1", "order", "0" })
    @BodyPart.Connection.Definition(id = "25", index = 1, instance = { "orientation", "LEFT", "fingerOrder", "1", "order", "1" })
    @BodyPart.Connection(id = "13", instance = { "orientation", "LEFT", "fingerOrder", "1", "order", "1" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "26", index = 0, instance = { "orientation", "LEFT", "fingerOrder", "1", "order", "1" })
    @BodyPart.Connection.Definition(id = "27", index = 1, instance = { "orientation", "LEFT", "fingerOrder", "1", "order", "2" })
    @BodyPart.Connection(id = "14", instance = { "orientation", "LEFT", "fingerOrder", "2", "order", "0" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "28", index = 0, instance = { "orientation", "LEFT", "fingerOrder", "2", "order", "0" })
    @BodyPart.Connection.Definition(id = "29", index = 1, instance = { "orientation", "LEFT", "fingerOrder", "2", "order", "1" })
    @BodyPart.Connection(id = "15", instance = { "orientation", "LEFT", "fingerOrder", "2", "order", "1" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "30", index = 0, instance = { "orientation", "LEFT", "fingerOrder", "2", "order", "1" })
    @BodyPart.Connection.Definition(id = "31", index = 1, instance = { "orientation", "LEFT", "fingerOrder", "2", "order", "2" })
    @BodyPart.Connection(id = "16", instance = { "orientation", "LEFT", "fingerOrder", "3", "order", "0" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "32", index = 0, instance = { "orientation", "LEFT", "fingerOrder", "3", "order", "0" })
    @BodyPart.Connection.Definition(id = "33", index = 1, instance = { "orientation", "LEFT", "fingerOrder", "3", "order", "1" })
    @BodyPart.Connection(id = "17", instance = { "orientation", "LEFT", "fingerOrder", "3", "order", "1" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "34", index = 0, instance = { "orientation", "LEFT", "fingerOrder", "3", "order", "1" })
    @BodyPart.Connection.Definition(id = "35", index = 1, instance = { "orientation", "LEFT", "fingerOrder", "3", "order", "2" })
    @BodyPart.Connection(id = "18", instance = { "orientation", "LEFT", "fingerOrder", "4", "order", "0" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "36", index = 0, instance = { "orientation", "LEFT", "fingerOrder", "4", "order", "0" })
    @BodyPart.Connection.Definition(id = "37", index = 1, instance = { "orientation", "LEFT", "fingerOrder", "4", "order", "1" })
    @BodyPart.Connection(id = "19", instance = { "orientation", "LEFT", "fingerOrder", "4", "order", "1" }, parts = { Phalanx.class, Phalanx.class })
    @BodyPart.Connection.Definition(id = "38", index = 0, instance = { "orientation", "LEFT", "fingerOrder", "4", "order", "1" })
    @BodyPart.Connection.Definition(id = "39", index = 1, instance = { "orientation", "LEFT", "fingerOrder", "4", "order", "2" })
    public abstract
    class FingerJoint
    extends OrientationalJoint
    implements
        External,
        FingerPart,
        Ordered.PerMany.FingerVaried
    {
        /** The finger order. */
        protected final
        byte fingerOrder;

        /** The finger joint order. */
        protected final
        byte order;

        /**
         * Creates the finger joint with the specified orientation, finger order, and order.
         * <p/>
         * This constructor calls {@link Number#byteValue()} on both orders.
         *
         * @param orientation the orientation.
         * @param fingerOrder the finger order.
         * @param order the order of the joint in the finger.
         */
        @Categorized(parameters = { "orientation", "fingerOrder", "order" })
        protected
        FingerJoint(
            final Orientation orientation,
            final Number fingerOrder,
            final Number order
            ) {
            super(orientation);
            this.fingerOrder = fingerOrder.byteValue();
            this.order = order.byteValue();
        }

        @Override
        public byte count() {
            return 2;
        }

        @Override
        public byte getFingerOrder() {
            return fingerOrder;
        }

        @Override
        public Byte getOrder() {
            return order;
        }
    }

    /**
     * {@code FingerJoints} represents all finger joints of the human hand.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FingerJoint.class, values = { "orientation -> orientation", "fingerOrder", "0", "1", "2", "3", "4", "order", "0", "1" })
    public abstract
    class FingerJoints
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Ordered.PerOrientation,
        Universal.ComprehensiveGroup<FingerJoint, Hand>
    {
        /**
         * Creates all finger joints of the human hand with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        FingerJoints(
            final Orientation orientation
            ) {
            super();

            createPart(FingerJoint.class)
            .withParameterRanges(2, 5, 2)
            .withValues(orientation, 0, 0)
            .withValues(orientation, 0, 1)
            .withValues(orientation, 1, 0)
            .withValues(orientation, 1, 1)
            .withValues(orientation, 2, 0)
            .withValues(orientation, 2, 1)
            .withValues(orientation, 3, 0)
            .withValues(orientation, 3, 1)
            .withValues(orientation, 4, 0)
            .withValues(orientation, 4, 1);
        }
    }

    /**
     * {@code FingerKnuckle} represents the knuckle of the human hand.
     * <p/>
     * The human finger knuckle is the joint in the hand connecting the palm to the finger.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Hand.class, count = 5)
    @BodyPart.Connection(id = "0", instance = { "orientation", "RIGHT", "fingerOrder", "0" }, parts = { Phalanx.class, Palm.class })
    @BodyPart.Connection.Definition(id = "0", index = 0, instance = { "orientation", "RIGHT", "fingerOrder", "0", "order", "0" })
    @BodyPart.Connection(id = "1", instance = { "orientation", "RIGHT", "fingerOrder", "1" }, parts = { Phalanx.class, Palm.class })
    @BodyPart.Connection.Definition(id = "1", index = 0, instance = { "orientation", "RIGHT", "fingerOrder", "1", "order", "0" })
    @BodyPart.Connection(id = "2", instance = { "orientation", "RIGHT", "fingerOrder", "2" }, parts = { Phalanx.class, Palm.class })
    @BodyPart.Connection.Definition(id = "2", index = 0, instance = { "orientation", "RIGHT", "fingerOrder", "2", "order", "0" })
    @BodyPart.Connection(id = "3", instance = { "orientation", "RIGHT", "fingerOrder", "3" }, parts = { Phalanx.class, Palm.class })
    @BodyPart.Connection.Definition(id = "3", index = 0, instance = { "orientation", "RIGHT", "fingerOrder", "3", "order", "0" })
    @BodyPart.Connection(id = "4", instance = { "orientation", "RIGHT", "fingerOrder", "4" }, parts = { Phalanx.class, Palm.class })
    @BodyPart.Connection.Definition(id = "4", index = 0, instance = { "orientation", "RIGHT", "fingerOrder", "4", "order", "0" })
    @BodyPart.Connection(id = "5", instance = { "orientation", "LEFT", "fingerOrder", "0" }, parts = { Phalanx.class, Palm.class })
    @BodyPart.Connection.Definition(id = "5", index = 0, instance = { "orientation", "LEFT", "fingerOrder", "0", "order", "0" })
    @BodyPart.Connection(id = "6", instance = { "orientation", "LEFT", "fingerOrder", "1" }, parts = { Phalanx.class, Palm.class })
    @BodyPart.Connection.Definition(id = "6", index = 0, instance = { "orientation", "LEFT", "fingerOrder", "1", "order", "0" })
    @BodyPart.Connection(id = "7", instance = { "orientation", "LEFT", "fingerOrder", "2" }, parts = { Phalanx.class, Palm.class })
    @BodyPart.Connection.Definition(id = "7", index = 0, instance = { "orientation", "LEFT", "fingerOrder", "2", "order", "0" })
    @BodyPart.Connection(id = "8", instance = { "orientation", "LEFT", "fingerOrder", "3" }, parts = { Phalanx.class, Palm.class })
    @BodyPart.Connection.Definition(id = "8", index = 0, instance = { "orientation", "LEFT", "fingerOrder", "3", "order", "0" })
    @BodyPart.Connection(id = "9", instance = { "orientation", "LEFT", "fingerOrder", "4" }, parts = { Phalanx.class, Palm.class })
    @BodyPart.Connection.Definition(id = "9", index = 0, instance = { "orientation", "LEFT", "fingerOrder", "4", "order", "0" })
    public abstract
    class FingerKnuckle
    extends OrientationalJoint
    implements
        External,
        HandPart,
        Ordered<Byte>,
        Ordered.PerMany.FingerUnique
    {
        /** The finger order. */
        protected final
        byte fingerOrder;

        /**
         * Creates the finger knuckle with the specified orientation and finger order.
         * <p/>
         * This constructor calls {@link Number#byteValue()} on the finger order.
         *
         * @param orientation the orientation.
         * @param fingerOrder the finger order.
         */
        @Categorized(parameters = { "orientation", "fingerOrder" })
        protected
        FingerKnuckle(
            final Orientation orientation,
            final Number fingerOrder
            ) {
            super(orientation);
            this.fingerOrder = fingerOrder.byteValue();
        }

        @Override
        public byte getFingerOrder() {
            return fingerOrder;
        }

        @Override
        public Byte getOrder() {
            return getFingerOrder();
        }
    }

    /**
     * {@code FingerKnuckles} represents all finger knuckles of the human hand.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FingerKnuckle.class, values = { "fingerOrder", "0", "1", "2", "3", "4" })
    public abstract
    class FingerKnuckles
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Ordered.PerOrientation,
        Universal.ComprehensiveGroup<FingerKnuckle, Hand>
    {
        /**
         * Creates all finger knuckles of the human hand with the specified orientation.
         *
         * @param orientation the orientation.
         */
        protected
        FingerKnuckles(
            final Orientation orientation
            ) {
            super();

            createPart(FingerKnuckle.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 0)
            .withValues(orientation, 1)
            .withValues(orientation, 2)
            .withValues(orientation, 3)
            .withValues(orientation, 4);
        }
    }

    /**
     * {@code FingerNail} represents the nail of the human finger.
     * <p/>
     * The finger nail is the flat horn-like part of the finger tip.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Finger.class)
    public abstract
    class FingerNail
    extends OrientationalFixture
    implements
        External,
        FingerPart,
        Ordered<Byte>,
        Ordered.PerMany.FingerUnique
    {
        /** The finger order. */
        protected final
        byte fingerOrder;

        /**
         * Creates the finger nail with the specified orientation and finger order.
         * <p/>
         * This constructor calls {@link Number#byteValue()} on the finger order.
         *
         * @param orientation the orientation.
         * @param fingerOrder the finger order.
         */
        @Categorized(parameters = { "orientation", "fingerOrder" })
        protected
        FingerNail(
            final Orientation orientation,
            final Number fingerOrder
            ) {
            super(orientation);
            this.fingerOrder = fingerOrder.byteValue();
        }

        @Override
        public byte getFingerOrder() {
            return fingerOrder;
        }

        @Override
        public Byte getOrder() {
            return fingerOrder;
        }
    }

    /**
     * {@code FingerNails} represents all finger nails of the human hand.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FingerNail.class, values = { "orientation", "RIGHT", "LEFT", "fingerOrder", "0", "1", "2", "3", "4" })
    public abstract
    class FingerNails
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Ordered.PerOrientation,
        Universal.ComprehensiveGroup<FingerNail, Hand>
    {
        /**
         * Creates all finger nails of the human hand with the specified orientation.
         *
         * @param orientation the orientation.
         */
        protected
        FingerNails(
            final Orientation orientation
            ) {
            super();

            createPart(FingerNail.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 0)
            .withValues(orientation, 1)
            .withValues(orientation, 2)
            .withValues(orientation, 3)
            .withValues(orientation, 4);
        }
    }

    /**
     * {@code FingerPart} classifies a part of the human finger.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface FingerPart
    extends HandPart
    {
        @Override
        public default Class<Finger> getCorrespondingBodyPartClass() {
            return Finger.class;
        }
    }

    /**
     * {@code Fingers} represents all fingers of the human hand.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    public abstract
    class Fingers
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<Finger, Hand>
    {
        /**
         * Creates all fingers of the human hand with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        Fingers(
            final Orientation orientation
            ) {
            super();

            createPart(Finger.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 0)
            .withValues(orientation, 1)
            .withValues(orientation, 2)
            .withValues(orientation, 3)
            .withValues(orientation, 4);
        }
    }

    /**
     * {@code FingerTip} represents the tip of the human finger.
     * <p/>
     * The fingertip is the outermost part of the last finger phalanx.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Finger.class)
    public abstract
    class FingerTip
    extends OrientationalFixture
    implements
        External,
        FingerPart,
        Ordered<Byte>,
        Ordered.PerMany.FingerUnique
    {
        /** The finger order. */
        protected final
        byte fingerOrder;

        /**
         * Creates a finger tip with the specified orientation and finger order.
         * <p/>
         * This constructor calls {@link Number#byteValue()} on the finger order.
         *
         * @param orientation the orientation.
         * @param fingerOrder the finger order.
         */
        @Categorized(parameters = { "orientation", "fingerOrder" })
        protected
        FingerTip(
            final Orientation orientation,
            final Number fingerOrder
            ) {
            super(orientation);
            this.fingerOrder = fingerOrder.byteValue();
        }

        @Override
        public byte getFingerOrder() {
            return fingerOrder;
        }

        @Override
        public Byte getOrder() {
            return fingerOrder;
        }
    }

    /**
     * {@code FingerTips} represents all finger tips of the human hand.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    public abstract
    class FingerTips
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Ordered.PerOrientation,
        Universal.ComprehensiveGroup<FingerTip, Hand>
    {
        /**
         * Creates all finger tips of the human hand with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized
        protected
        FingerTips(
            final Orientation orientation
            ) {
            super();

            createPart(FingerTip.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 0)
            .withValues(orientation, 1)
            .withValues(orientation, 2)
            .withValues(orientation, 3)
            .withValues(orientation, 4);
        }
    }

    /**
     * {@code FirstFinger} represents the human first finger.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Finger.class, values = { "orientation -> orientation", "order", "1" })
    public abstract
    class FirstFinger
    extends CompositeOrientationalBodyPart
    implements
        External,
        HandPart,
        Ordered<Byte>,
        Ordered.PerMany<Byte>
    {
        /**
         * Creates the first finger with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        FirstFinger(
            final Orientation orientation
            ) {
            super(orientation);

            createPart(Finger.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 1);
        }
    }

    /**
     * {@code FirstFingers} represents both human first fingers.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FirstFinger.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class FirstFingers
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<FirstFinger, Hands>
    {
        /**
         * Creates both first fingers.
         */
        protected
        FirstFingers()
        {
            super();

            createPart(FirstFinger.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Fixture} classifies an atomic body part that doesn't, or is not intended to, bend and connects to a joint or multiple joints.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class Fixture
    extends AtomicBodyPart
    {}

    /**
     * {@code Foot} represents the human foot.
     * <p/>
     * The human foot is the lowest part of the leg attached to the lower leg via the ankle.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Leg.class)
    @Category
    @Category.Instance(type = Heel.class, values = "orientation -> orientation")
    @Category.Instance(type = Sole.class, values = "orientation -> orientation")
    @Category.Instance(type = FootKnuckles.class, values = "orientation -> orientation")
    @Category.Instance(type = Toes.class, values = "orientation -> orientation")
    public abstract
    class Foot
    extends CompositeOrientationalBodyPart
    implements
        External,
        LegPart,
        Ordered.PerOrientation
    {
        /**
         * Creates the foot with the specified orientation and containing the heel, sole, knuckles, and toes.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        Foot(
            final Orientation orientation
            ) {
            super(orientation);

            createPart(Heel.class)
            .withParameterRanges(2)
            .withValues(orientation);

            createPart(Sole.class)
            .withParameterRanges(2)
            .withValues(orientation);

            createPart(FootKnuckles.class)
            .withParameterRanges(2)
            .withValues(orientation);

            createPart(Toes.class)
            .withParameterRanges(2)
            .withValues(orientation);
        }
    }

    /**
     * {@code FootKnuckles} represents all the knuckles of the human foot as a single body part.
     * <p/>
     * The human foot knuckles are the joints connecting the sole to the toes.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Foot.class)
    public abstract
    class FootKnuckles
    extends OrientationalJoint
    implements
        External,
        FootPart
    {
        /**
         * Creates the foot knuckles with the specified orientation.
         *
         * @param orientation the orientation.
         */
        protected
        FootKnuckles(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code FootPart} classifies a part of the human foot.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface FootPart
    extends LegPart
    {
        @Override
        public default Class<Foot> getCorrespondingBodyPartClass() {
            return Foot.class;
        }
    }

    /**
     * {@code FourFingerJoints} represents four finger joints of the human hand excluding the thumb.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FingerJoint.class, values = { "orientation -> orientation", "fingerOrder", "1", "2", "3", "4", "order", "0", "1", "2" })
    public abstract
    class FourFingerJoints
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart
    {
        /**
         * Creates all four finger joints with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        FourFingerJoints(
            final Orientation orientation
            ) {
            super();

            createPart(FingerJoint.class)
            .withParameterRanges(2, 5, 3)
            .withValues(orientation, 1, 0)
            .withValues(orientation, 1, 1)
            .withValues(orientation, 1, 2)
            .withValues(orientation, 2, 0)
            .withValues(orientation, 2, 1)
            .withValues(orientation, 2, 2)
            .withValues(orientation, 3, 0)
            .withValues(orientation, 3, 1)
            .withValues(orientation, 3, 2)
            .withValues(orientation, 4, 0)
            .withValues(orientation, 4, 1)
            .withValues(orientation, 4, 2);
        }
    }

    /**
     * {@code FourFingerKnuckles} represents four finger knuckles of the human hand excluding the thumb.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FingerKnuckle.class, values = { "orientation -> orientation", "fingerOrder", "1", "2", "3", "4" })
    public abstract
    class FourFingerKnuckles
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart
    {
        /**
         * Creates all four finger knuckles with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        FourFingerKnuckles(
            final Orientation orientation
            ) {
            super();

            createPart(FingerKnuckle.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 1)
            .withValues(orientation, 2)
            .withValues(orientation, 3)
            .withValues(orientation, 4);
        }
    }

    /**
     * {@code FourFingerNails} represents four finger nails of the human hand excluding the thumb.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FingerNail.class, values = { "orientation -> orientation", "fingerOrder", "1", "2", "3", "4" })
    public abstract
    class FourFingerNails
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart
    {
        /**
         * Creates all four finger nails with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        FourFingerNails(
            final Orientation orientation
            ) {
            super();

            createPart(FingerNail.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 1)
            .withValues(orientation, 2)
            .withValues(orientation, 3)
            .withValues(orientation, 4);
        }
    }

    /**
     * {@code FourFingers} represents four fingers of the human hand excluding the thumb.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Finger.class, values = { "orientation -> orientation", "order", "1", "2", "3", "4" })
    public abstract
    class FourFingers
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart
    {
        /**
         * Creates all four fingers of the human hand with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        FourFingers(
            final Orientation orientation
            ) {
            super();

            createPart(Finger.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 1)
            .withValues(orientation, 2)
            .withValues(orientation, 3)
            .withValues(orientation, 4);
        }
    }

    /**
     * {@code FourFingerTips} represents four finger tips of the human hand excluding the tip of the thumb.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FingerTip.class, values = { "orientation -> orientation", "order", "1", "2", "3", "4" })
    public abstract
    class FourFingerTips
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart
    {
        /**
         * Creates all four finger tips with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        FourFingerTips(
            final Orientation orientation
            ) {
            super();

            createPart(FingerTip.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 1)
            .withValues(orientation, 2)
            .withValues(orientation, 3)
            .withValues(orientation, 4);
        }
    }

    /**
     * {@code FourPhalanges} represents all phalanges of four fingers of the human hand excluding the phalanges of the thumb.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Phalanx.class, values = { "orientation -> orientation", "fingerOrder", "1", "2", "3", "4", "order", "0", "1", "2" })
    public abstract
    class FourPhalanges
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart
    {
        /**
         * Creates all four phalanges of the human hand with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        FourPhalanges(
            final Orientation orientation
            ) {
            super();

            createPart(Phalanx.class)
            .withParameterRanges(2, 5, 3)
            .withValues(orientation, 1, 0)
            .withValues(orientation, 1, 1)
            .withValues(orientation, 1, 2)
            .withValues(orientation, 2, 0)
            .withValues(orientation, 2, 1)
            .withValues(orientation, 2, 2)
            .withValues(orientation, 3, 0)
            .withValues(orientation, 3, 1)
            .withValues(orientation, 3, 2)
            .withValues(orientation, 4, 0)
            .withValues(orientation, 4, 1)
            .withValues(orientation, 4, 2);
        }
    }

    /**
     * {@code FourthFinger} represents the human fourth finger.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Finger.class, values = { "orientation -> orientation", "order", "3" })
    public abstract
    class FourthFinger
    extends CompositeOrientationalBodyPart
    implements
        External,
        HandPart,
        Ordered<Byte>,
        Ordered.PerMany<Byte>
    {
        /**
         * Creates the fourth finger with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        FourthFinger(
            final Orientation orientation
            ) {
            super(orientation);

            createPart(Finger.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 3);
        }
    }

    /**
     * {@code FourthFingers} represents both human fourth fingers.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = FourthFinger.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class FourthFingers
    extends OrientationalBodyPartGroup
    implements Universal.ComprehensiveGroup<FourthFinger, Hands>
    {
        /**
         * Creates both first fingers.
         */
        protected
        FourthFingers()
        {
            super();

            createPart(FourthFinger.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Hand} represents the human hand.
     * <p/>
     * The human hand is the outermost part of the arm attached to the lower arm via the wrist.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Arm.class)
    @Category
    @Category.Instance(type = Palm.class)
    @Category.Instance(type = FingerKnuckle.class, values = { "orientation -> orientation", "fingerOrder", "0", "1", "2", "3", "4" })
    @Category.Instance(type = Finger.class, values = { "orientation -> orientation", "order", "0", "1", "2", "3", "4" })
    public abstract
    class Hand
    extends CompositeOrientationalBodyPart
    implements
        ArmPart,
        External
    {
        /**
         * Creates the hand with the specified orientation and containing the palm, finger knuckles, and fingers.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        Hand(
            final Orientation orientation
            ) {
            super(orientation);

            // Create the palm
            createPart(Palm.class)
            .withParameterRanges(2)
            .withValues(orientation);

            // Create all the finger knuckles
            createPart(FingerKnuckle.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 0)
            .withValues(orientation, 1)
            .withValues(orientation, 2)
            .withValues(orientation, 3)
            .withValues(orientation, 4);

            // Create all the fingers
            createPart(Finger.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 0)
            .withValues(orientation, 1)
            .withValues(orientation, 2)
            .withValues(orientation, 3)
            .withValues(orientation, 4);
        }
    }

    /**
     * {@code Hands} represents both hands of the human arms.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Hand.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class Hands
    extends OrientationalBodyPartGroup
    implements
        ArmPart,
        External,
        Universal.ComprehensiveGroup<Hand, Arms>
    {
        /**
         * Creates both hands.
         */
        protected
        Hands() {
            super();

            createPart(Hand.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code HandPart} classifies a part of the human hand.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface HandPart
    extends ArmPart
    {
        @Override
        public default Class<? extends CompositeOrientationalBodyPart> getCorrespondingBodyPartClass() {
            return Hand.class;
        }
    }

    /**
     * {@code Head} represents the human head.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Human.class)
    @Category
    @Category.Instance(type = Eyes.class)
    @Category.Instance(type = Ears.class)
    @Category.Instance(type = Nose.class)
    @Category.Instance(type = Mouth.class)
    public abstract
    class Head
    extends CompositeBodyPart
    implements
        External,
        Upper
    {
        /**
         * Creates the head containing the eyes, ears, nose, and mouth.
         */
        protected
        Head() {
            super();

            createPart(Eyes.class);
            createPart(Ears.class);
            createPart(Nose.class);
            createPart(Mouth.class);
        }
    }

    /**
     * {@code HeadPart} classifies a part of the human head.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface HeadPart
    extends
        BodyPart,
        Upper
    {
        @Override
        public default Class<? extends CompositeBodyPart> getCorrespondingBodyPartClass() {
            return Head.class;
        }
    }

    /**
     * {@code Heel} represents the heel of the human foot.
     * <p/>
     * The human heel is the part of the foot between the ankle and the sole.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Foot.class)
    public abstract
    class Heel
    extends OrientationalFixture
    implements
        External,
        FootPart
    {
        /**
         * Creates the heel with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        Heel(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Heels} represents both heels of the human feet.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Heel.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class Heels
    extends OrientationalBodyPartGroup
    implements
        External,
        FootPart,
        Universal.ComprehensiveGroup<Heel, Feet>
    {
        /**
         * Creates both heels.
         */
        protected
        Heels() {
            super();

            createPart(Heel.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Hip} represents the human hip.
     * <p/>
     * The human hip is the joint connecting the waist to the leg.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Human.class)
    @BodyPart.Connection(id = "0", instance = { "orientation", "RIGHT" }, parts = { Waist.class, Leg.class })
    @BodyPart.Connection.Definition(id = "0", index = 1, instance = { "orientation -> orientation" })
    @BodyPart.Connection(id = "1", instance = { "orientation", "LEFT" }, parts = { Waist.class, Leg.class })
    @BodyPart.Connection.Definition(id = "1", index = 1, instance = { "orientation -> orientation" })
    public abstract
    class Hip
    extends OrientationalJoint
    implements
        External,
        Lower
    {
        /**
         * Creates the hip with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        Hip(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Hips} represents both hips of the human body.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Hip.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class Hips
    extends OrientationalBodyPartGroup
    implements
        External,
        Lower,
        Universal.ComprehensiveGroup<Hip, Human>
    {
        /**
         * Creates both hips.
         */
        protected
        Hips() {
            super();

            createPart(Hip.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code IndexFinger} represents the human index finger.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Finger.class, values = { "orientation -> orientation", "order", "1" })
    public abstract
    class IndexFinger
    extends CompositeOrientationalBodyPart
    implements
        External,
        HandPart,
        Ordered<Byte>,
        Ordered.PerMany<Byte>
    {
        /**
         * Creates the index finger with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        IndexFinger(
            final Orientation orientation
            ) {
            super(orientation);

            createPart(Finger.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 2);
        }
    }

    /**
     * {@code IndexFingers} represents both human index fingers.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = IndexFinger.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class IndexFingers
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<IndexFinger, Hands>
    {
        /**
         * Creates both index fingers.
         */
        protected
        IndexFingers()
        {
            super();

            createPart(IndexFinger.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Internal} classifies an internal human body part.
     * <p/>
     * The internal body parts are those that are not visible.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Internal
    extends Universal.Zone.Characteristic
    {}

    /**
     * {@code Jaw} represents the human jaw.
     * <p/>
     * The human jaw is the joint inside the mouth also known as the mandible.
     * It is the joint connecting to the head and the lower teeth.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Mouth.class)
    @Category
    @Category.Instance(type = LowerTeeth.class)
    @BodyPart.Connection(parts = Head.class)
    @BodyPart.Connection(parts = LowerTeeth.class, joint = false)
    public abstract
    class Jaw
    extends Joint
    implements
        Internal,
        MouthPart
    {
        /**
         * Creates the jaw.
         */
        protected
        Jaw() {
            super();
            createPart(LowerTeeth.class);
        }
    }

    /**
     * {@code Joint} classifies an atomic body part that bends and connects two parts together.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class Joint
    extends AtomicBodyPart
    {}

    /**
     * {@code Knee} represents the human knee.
     * <p/>
     * The human knee is the joint in the leg connecting the upper leg to the lower leg.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Leg.class)
    @BodyPart.Connection(parts = { UpperLeg.class, LowerLeg.class })
    public abstract
    class Knee
    extends OrientationalJoint
    implements
        External,
        LegPart
    {
        /**
         * Creates the knee with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        Knee(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Knees} represents both knees of the human legs.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Knee.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class Knees
    extends OrientationalBodyPartGroup
    implements
        External,
        LegPart,
        Universal.ComprehensiveGroup<Knee, Legs>
    {
        /**
         * Creates both legs.
         */
        protected
        Knees() {
            super();

            createPart(Knee.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Leg} represents the human leg.
     * <p/>
     * The human leg is the part of the body containing the upper leg, knee, lower leg, ankle, and foot.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Human.class)
    @Category
    @Category.Instance(type = UpperLeg.class, values = "orientation -> orientation")
    @Category.Instance(type = Knee.class, values = "orientation -> orientation")
    @Category.Instance(type = LowerLeg.class, values = "orientation -> orientation")
    @Category.Instance(type = Ankle.class, values = "orientation -> orientation")
    @Category.Instance(type = Foot.class, values = "orientation -> orientation")
    public abstract
    class Leg
    extends CompositeOrientationalBodyPart
    implements
        External,
        Lower
    {
        /**
         * Creates the leg with the specified orientation and containing the upper leg, knee, lower leg, ankle, and foot.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        Leg(
            final Orientation orientation
            ) {
            super(orientation);

            createPart(UpperLeg.class)
            .withParameterRanges(2)
            .withValues(orientation);

            createPart(Knee.class)
            .withParameterRanges(2)
            .withValues(orientation);

            createPart(LowerLeg.class)
            .withParameterRanges(2)
            .withValues(orientation);

            createPart(Ankle.class)
            .withParameterRanges(2)
            .withValues(orientation);

            createPart(Foot.class)
            .withParameterRanges(2)
            .withValues(orientation);
        }
    }

    /**
     * {@code Legs} represents both legs of the human body.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Leg.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class Legs
    extends OrientationalBodyPartGroup
    implements
        External,
        Lower,
        Universal.ComprehensiveGroup<Leg, Human>
    {
        /**
         * Creates both legs.
         */
        protected
        Legs() {
            super();

            createPart(Leg.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code LegPart} classifies a part of the human leg.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface LegPart
    extends
        BodyPart,
        Lower
    {
        @Override
        public default Class<? extends CompositeOrientationalBodyPart> getCorrespondingBodyPartClass() {
            return Leg.class;
        }
    }

    /**
     * {@code Lip} classifies one of the human lips.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Lip
    extends
        External,
        MouthPart
    {}

    /**
     * {@code Lips} represents both lips.
     * <p/>
     * The human lips are the outer part of the mouth, covering the teeth, through which consonants are shaped during speech.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = UpperLip.class)
    @Category.Instance(type = LowerLip.class)
    public abstract
    class Lips
    extends BodyPartGroup
    implements
        External,
        MouthPart,
        Universal.ComprehensiveGroup<Lip, Mouth>
    {
        /**
         * Creates the lips containing the upper and lower lips.
         */
        protected
        Lips() {
            super();
            createPart(UpperLip.class);
            createPart(LowerLip.class);
        }
    }

    /**
     * {@code LittleFinger} represents the human little finger.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Finger.class, values = { "orientation -> orientation", "order", "4" })
    public abstract
    class LittleFinger
    extends CompositeOrientationalBodyPart
    implements
        External,
        HandPart,
        Ordered<Byte>,
        Ordered.PerMany<Byte>
    {
        /**
         * Creates the little finger with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        LittleFinger(
            final Orientation orientation
            ) {
            super(orientation);

            createPart(Finger.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 4);
        }
    }

    /**
     * {@code LittleFingers} represents both human little fingers.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = LittleFinger.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class LittleFingers
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<LittleFinger, Hands>
    {
        /**
         * Creates both little fingers.
         */
        protected
        LittleFingers()
        {
            super();

            createPart(LittleFinger.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Lower} classifies a human lower body part.
     * <p/>
     * The lower body parts are those below the waist.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Lower
    extends Universal.Zone.Characteristic
    {}

    /**
     * {@code LowerArm} represents the lower part of the human arm between the elbow and the wrist.
     */
    @Uniformed(group = Arm.class)
    public abstract
    class LowerArm
    extends OrientationalFixture
    implements
        ArmPart,
        External
    {
        /**
         * Creates the lower leg with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        LowerArm(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code LowerArms} represents both lower arms of the human body.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = LowerArm.class, values = "orientation -> orientation")
    public abstract
    class LowerArms
    extends OrientationalBodyPartGroup
    implements
        External,
        ArmPart,
        Universal.ComprehensiveGroup<LowerArm, Arms>
    {
        /**
         * Creates both lower arms.
         */
        protected
        LowerArms() {
            super();

            createPart(LowerArm.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code LowerLeg} represents the lower part of the human leg between the knee and the ankle.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Leg.class)
    public abstract
    class LowerLeg
    extends OrientationalFixture
    implements
        External,
        LegPart
    {
        /**
         * Creates the lower leg with the specified orientation.
         *
         * @param orientation the orientation.
         */
        protected
        LowerLeg(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code LowerLegs} represents both lower legs of the human body.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = LowerLeg.class, values = "orientation -> orientation")
    public abstract
    class LowerLegs
    extends OrientationalBodyPartGroup
    implements
        External,
        LegPart,
        Universal.ComprehensiveGroup<LowerLeg, Legs>
    {
        /**
         * Creates both lower legs.
         */
        protected
        LowerLegs() {
            super();

            createPart(LowerLeg.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code LowerLip} represents the lower part of the human lips.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Mouth.class)
    public abstract
    class LowerLip
    extends AtomicBodyPart
    implements Lip
    {
        /**
         * Creates the lower lip.
         */
        protected
        LowerLip() {
            super();
        }
    }

    /**
     * {@code LowerTeeth} represents the lower part of the human teeth.
     * <p/>
     * The human lower teeth are the sharp extensions of the jaw bone.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Mouth.class)
    public abstract
    class LowerTeeth
    extends Fixture
    implements
        Internal,
        MouthPart
    {
        /**
         * Creates the lower teeth.
         */
        protected
        LowerTeeth() {
            super();
        }
    }

    /**
     * {@code Lungs} represents the human lungs as a single body part.
     * <p/>
     * The human lungs are the cavities in the thorax where air flows during breathing.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Human.class)
    public abstract
    class Lungs
    extends AtomicBodyPart
    implements
        Internal,
        TorsoPart,
        Upper
    {
        /**
         * Creates the lungs.
         */
        protected
        Lungs() {
            super();
        }
    }

    /**
     * {@code Mid} classifies a human middle body part.
     * <p/>
     * The middle body parts are those above the hips and below the thorax.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Mid
    extends Universal.Zone.Characteristic
    {}

    /**
     * {@code MiddleFinger} represents the human middle finger.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Finger.class, values = { "orientation -> orientation", "order", "2" })
    public abstract
    class MiddleFinger
    extends CompositeOrientationalBodyPart
    implements
        External,
        HandPart,
        Ordered<Byte>,
        Ordered.PerMany<Byte>
    {
        /**
         * Creates the middle finger with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        MiddleFinger(
            final Orientation orientation
            ) {
            super(orientation);

            createPart(Finger.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 2);
        }
    }

    /**
     * {@code MiddleFingers} represents both human middle fingers.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = MiddleFinger.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class MiddleFingers
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<MiddleFinger, Hands>
    {
        /**
         * Creates both middle fingers.
         */
        protected
        MiddleFingers()
        {
            super();

            createPart(MiddleFinger.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Mouth} represents the human mouth.
     * <p/>
     * The human mouth is the cavity in the head containing the lips, tongue, teeth, and the jaw.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Head.class)
    @Category
    @Category.Instance(type = Lips.class)
    @Category.Instance(type = Tongue.class)
    @Category.Instance(type = Teeth.class)
    @Category.Instance(type = Jaw.class)
    public abstract
    class Mouth
    extends CompositeBodyPart
    implements
        External,
        Internal,
        HeadPart
    {
        /**
         * Creates the mouth containing the lips, tongue, teeth, and the jaw.
         */
        protected
        Mouth() {
            super();

            createPart(Lips.class);
            createPart(Tongue.class);
            createPart(Teeth.class);
            createPart(Jaw.class);
        }
    }

    /**
     * {@code MouthPart} classifies a part of the human mouth.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface MouthPart
    extends HeadPart
    {
        @Override
        public default Class<Mouth> getCorrespondingBodyPartClass() {
            return Mouth.class;
        }
    }

    /**
     * {@code Neck} represents the human neck.
     * <p/>
     * The human neck is the joint connecting the thorax to the head and containing the throat.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Human.class)
    @Category
    @Category.Instance(type = Throat.class)
    @BodyPart.Connection(parts = { Thorax.class, Head.class })
    public abstract
    class Neck
    extends Joint
    implements
        External,
        Upper
    {
        /**
         * Creates the neck containing the throat.
         */
        protected
        Neck() {
            super();
            createPart(Throat.class);
        }
    }

    /**
     * {@code NeckPart} classifies a part of the human neck.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface NeckPart
    extends
        BodyPart,
        External,
        Upper
    {
        @Override
        public default Class<Neck> getCorrespondingBodyPartClass() {
            return Neck.class;
        }
    }

    /**
     * {@code Nose} represents the human nose.
     * <p/>
     * The human nose is the organ above the lips through which breath flows regularly.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Head.class)
    public abstract
    class Nose
    extends Fixture
    implements
        External,
        HeadPart
    {
        /**
         * Creates the nose.
         */
        protected
        Nose() {
            super();
        }
    }

    /**
     * {@code Ordered} classifies a human body part, body part group, or grouping that has an order among similar body parts of a composite body part, body part group, or another arbitrary containing unit.
     * <p/>
     * This class implementation is in progress.
     *
     * @param <T> the order type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Ordered<T>
    extends Instrument.Ordered<T>
    {
        /**
         * Returns the order of the body part within its natural containing part or containing unit.
         *
         * @return the body part order.
         */
        T getOrder();

        /**
         * {@code PerFinger} classifies body parts that are ordered per finger.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PerFinger
        extends system.data.Ordered
        {
            /**
             * Returns the finger order of the body part.
             *
             * @return the finger order.
             */
            byte getFingerOrder();
        }

        /**
         * {@code PerOrientation} classifies orientational body parts that can be ordered per orientation.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PerOrientation
        extends
            system.data.Ordered,
            Orientational
        {
            /**
             * Returns the orientation order of the body part.
             * <p/>
             * This implementation calls {@link #getOrientationOrder()} internally.
             *
             * @return the orientation order.
             */
            default
            Byte getOrder() {
                return getOrientationOrder();
            }

            /**
             * Returns the orientation order of the body part.
             * <p/>
             * By convention, the order of the right orientation is 0, the left orientation is 1, and the null orientation has null order.
             *
             * @return the orientation order.
             */
            default
            Byte getOrientationOrder() {
                Orientation orientation = getOrientation();
                return orientation == null
                       ? null
                       : orientation.order;
            }
        }

        /**
         * {@code PerMany} classifies body parts that are ordered per many different body parts, body part groups, or other arbitrary containing units.
         * <p/>
         * This class implementation is in progress.
         *
         * @param <T> the order type.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface PerMany<T>
        extends Instrument.Ordered.PerMany<T>
        {
            /**
             * Returns the order of the body part within a larger unit or part, usually naturally containing the smaller body part; or null if the body part is not ordered within that unit type.
             * <p/>
             * Note that in many cases, such as for all finger parts, the method argument can only accept unit or part types as low in the inheritance hierarchy as the class type defining the part to which the order of this body part belongs to.
             * For example, for all finger phalanges, only the {@code Finger} class will be accepted as the smallest unit of ordering.
             * The {@code IndexFinger} class, in such case, will fail even if this body part is ordered by belonging to the mentioned finger class.
             * This is due to the hidden nature of orderings at some subclass levels that can occur by design.
             *
             * @param partClass the containing unit class.
             * @return the body part order or null if order does not exist.
             */
            @Override
            T getOrder(
                Class<? extends Unit> partClass
                );

            /**
             * {@code FingerUnique} classifies orientational body parts or units that are unique, or have exactly one variation, per individual finger.
             * <p/>
             * This class implementation is in progress.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface FingerUnique
            extends
                Human.Ordered.PerMany<Byte>,
                Orientational,
                PerFinger
            {
                @Override
                default Byte getOrder(final Class<? extends Unit> partClass) {
                    if (partClass == Finger.class)
                        return 0;

                    if (partClass == FourFingers.class ||
                        partClass == Hand.class ||
                        partClass == Arm.class)
                        return getFingerOrder();

                    if (partClass == AllFourFingers.class ||
                        partClass == Hands.class ||
                        partClass == Arms.class ||
                        partClass == Human.class)
                        return (byte) (getOrientation().order * 5 + getFingerOrder());

                    return null;
                }
            }

            /**
             * {@code FingerVaried} classifies orientational body parts or units that are varied, or have more that one variation, per individual finger.
             * <p/>
             * This class implementation is in progress.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface FingerVaried
            extends
                Human.Ordered.PerMany<Byte>,
                Ordered<Byte>,
                Orientational,
                PerFinger
            {
                @Override
                default Byte getOrder(final Class<? extends Unit> partClass) {
                    if (partClass == Finger.class)
                        return getOrder();

                    if (partClass == FourFingers.class ||
                        partClass == Hand.class ||
                        partClass == Arm.class)
                        return (byte) (getFingerOrder() * count() + getOrder());

                    if (partClass == AllFourFingers.class ||
                        partClass == Hands.class ||
                        partClass == Arms.class ||
                        partClass == Human.class)
                        return (byte) (getOrientation().order * 5 + getFingerOrder() * count() + getOrder());

                    return null;
                }

                /**
                 * Returns the count of similar units in a single finger.
                 *
                 * @return the unit count.
                 */
                byte count();
            }
        }
    }

    /**
     * {@code Orientation} represents the human body orientations: right and left.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    enum Orientation
    implements Universal.Orientation
    {
        /** The right side. */
        RIGHT((byte) 0),

        /** The left side. */
        LEFT((byte) 1);

        /**
         * The order of orientation.
         */
        public final
        byte order;

        /**
         * Creates an orientation with the specified order.
         *
         * @param order the order of orientation.
         */
        private
        Orientation(
            final byte order
            ) {
            this.order = order;
        }
    }

    /**
     * {@code Orientational} represents a body part that has an orientation.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Orientational
    {
        /**
         * Returns the orientation of the body part.
         *
         * @return the orientation.
         */
        public
        Orientation getOrientation();
    }

    /**
     * {@code OrientationalBodyPartGroup} classifies a human body part group with individual parts being all orientational.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class OrientationalBodyPartGroup
    extends BodyPartGroup
    {}

    /**
     * {@code OrientationalFixture} classifies a fixture that is also orientational.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class OrientationalFixture
    extends Fixture
    implements Orientational
    {
        /** The orientation. */
        protected
        Orientation orientation;

        /**
         * Creates an orientational fixture with the specified orientation.
         *
         * @param orientation the orientation.
         */
        protected
        OrientationalFixture(
            final Orientation orientation
            ) {
            super();
            this.orientation = orientation;
        }

        @Override
        public Orientation getOrientation() {
            return orientation;
        }
    }

    /**
     * {@code OrientationalJoint} classifies a joint that is also orientational.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class OrientationalJoint
    extends Joint
    implements Orientational
    {
        /** The orientation. */
        protected final
        Orientation orientation;

        /**
         * Creates an orientational joint with the specified orientation.
         *
         * @param orientation the orientation.
         */
        protected
        OrientationalJoint(
            final Orientation orientation
            ) {
            super();
            this.orientation = orientation;
        }

        @Override
        public Orientation getOrientation() {
            return orientation;
        }
    }

    /**
     * {@code Palm} represents the palm of the human hand.
     * <p/>
     * The human palm is the biggest portion of the hand between the wrist and the finger knuckles.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Hand.class)
    public abstract
    class Palm
    extends OrientationalFixture
    implements
        External,
        HandPart
    {
        /**
         * Creates the palm with the specified orientation.
         *
         * @param orientation the orientation.
         */
        protected
        Palm(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Palms} represents both palms of the human hands.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    public abstract
    class Palms
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<Palm, Arm>
    {
        /**
         * Creates both palms.
         */
        protected
        Palms() {
            super();

            createPart(Palm.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Phalanx} represents the finger phalanx in the human hand.
     * <p/>
     * The human finger phalanx is the part of the finger that does not bend.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Finger.class, count = 3)
    public abstract
    class Phalanx
    extends OrientationalFixture
    implements
        External,
        FingerPart,
        Ordered.PerMany.FingerVaried
    {
        /** The finger order. */
        protected final
        byte fingerOrder;

        /** The finger phalanx order. */
        protected final
        byte order;

        /**
         * Creates the finger phalanx with the specified orientation, finger order, and order.
         * <p/>
         * This constructor calls {@link Number#byteValue()} on both orders.
         *
         * @param orientation the orientation.
         * @param fingerOrder the finger order of the phalanx.
         * @param order the order of the phalanx in the finger.
         */
        @Categorized(parameters = { "orientation", "fingerOrder", "order" })
        protected
        Phalanx(
            final Orientation orientation,
            final Number fingerOrder,
            final Number order
            ) {
            super(orientation);
            this.fingerOrder = fingerOrder.byteValue();
            this.order = order.byteValue();
        }

        public byte count() {
            return 3;
        }

        @Override
        public byte getFingerOrder() {
            return fingerOrder;
        }

        @Override
        public Byte getOrder(final Class<? extends Unit> part) {
            if (part == Finger.class)
                return getFingerOrder();

            return null;
        }

        @Override
        public Byte getOrder() {
            return order;
        }
    }

    /**
     * {@code Phalanges} represents all phalanges of the human hand.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Phalanx.class, values = { "orientation -> orientation", "fingerOrder", "0", "1", "2", "3", "4", "order", "0", "1", "2" })
    public abstract
    class Phalanges
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<Phalanx, Hand>
    {
        /**
         * Creates all phalanges of the human hand with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized
        protected
        Phalanges(
            final Orientation orientation
            ) {
            super();

            createPart(Phalanx.class)
            .withParameterRanges(2, 5, 3)
            .withValues(orientation, 0, 0)
            .withValues(orientation, 0, 1)
            .withValues(orientation, 0, 2)
            .withValues(orientation, 1, 0)
            .withValues(orientation, 1, 1)
            .withValues(orientation, 1, 2)
            .withValues(orientation, 2, 0)
            .withValues(orientation, 2, 1)
            .withValues(orientation, 2, 2)
            .withValues(orientation, 3, 0)
            .withValues(orientation, 3, 1)
            .withValues(orientation, 3, 2)
            .withValues(orientation, 4, 0)
            .withValues(orientation, 4, 1)
            .withValues(orientation, 4, 2);
        }
    }

    /**
     * {@code Pinkies} represents both human pinky fingers.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Pinkies
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<Pinky, Hands>
    {
        /**
         * Creates both pinky fingers.
         */
        protected
        Pinkies()
        {
            super();

            createPart(Pinky.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Pinky} represents the human pinky finger.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Finger.class, values = { "order", "4" })
    public abstract
    class Pinky
    extends CompositeOrientationalBodyPart
    implements
        External,
        HandPart,
        Ordered<Byte>,
        Ordered.PerMany<Byte>
    {
        /**
         * Creates the pinky finger with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        Pinky(
            final Orientation orientation
            ) {
            super(orientation);

            createPart(Finger.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 4);
        }
    }

    /**
     * {@code RingFinger} represents the human ring finger.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Finger.class, values = { "orientation -> orientation", "order", "3" })
    public abstract
    class RingFinger
    extends CompositeOrientationalBodyPart
    implements
        External,
        HandPart,
        Ordered<Byte>,
        Ordered.PerMany<Byte>
    {
        /**
         * Creates the ring finger with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        RingFinger(
            final Orientation orientation
            ) {
            super(orientation);

            createPart(Finger.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 3);
        }
    }

    /**
     * {@code RingFingers} represents both human ring fingers.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    public abstract
    class RingFingers
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<RingFinger, Hands>
    {
        /**
         * Creates both ring fingers.
         */
        protected
        RingFingers()
        {
            super();

            createPart(RingFinger.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code SecondFinger} represents the human second finger.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Finger.class, values = { "orientation -> orientation", "order", "2" })
    public abstract
    class SecondFinger
    extends CompositeOrientationalBodyPart
    implements
        External,
        HandPart,
        Ordered<Byte>,
        Ordered.PerMany<Byte>
    {
        /**
         * Creates the second finger with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        SecondFinger(
            final Orientation orientation
            ) {
            super(orientation);

            createPart(Finger.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 2);
        }
    }

    /**
     * {@code SecondFingers} represents both human second fingers.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    public abstract
    class SecondFingers
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<SecondFinger, Hands>
    {
        /**
         * Creates both second fingers.
         */
        protected
        SecondFingers()
        {
            super();

            createPart(SecondFinger.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Shoulder} represents the human shoulder.
     * <p/>
     * The human shoulder is the joint connecting the torso to the arms.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Torso.class)
    @BodyPart.Connection(parts = { Arm.class, Torso.class })
    public abstract
    class Shoulder
    extends OrientationalJoint
    implements
        External,
        TorsoPart,
        Upper
    {
        /**
         * Creates the shoulder with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        Shoulder(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Shoulders} represents both shoulders of the human torso.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Shoulder.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class Shoulders
    extends OrientationalBodyPartGroup
    implements
        External,
        TorsoPart,
        Universal.ComprehensiveGroup<Shoulder, Torso>,
        Upper
    {
        /**
         * Creates both shoulders.
         */
        protected
        Shoulders() {
            super();

            createPart(Shoulder.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Sole} represents the sole of the human foot.
     * <p/>
     * The human sole is the biggest bottom portion of the foot between the heel and knuckles.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Foot.class)
    public abstract
    class Sole
    extends OrientationalFixture
    implements
        External,
        FootPart
    {
        /**
         * Creates the sole with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        Sole(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Soles} represents both soles of the human feet.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Sole.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class Soles
    extends OrientationalBodyPartGroup
    implements
        External,
        FootPart,
        Universal.ComprehensiveGroup<Sole, Feet>
    {
        /**
         * Creates both soles.
         */
        protected
        Soles() {
            super();

            createPart(Sole.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Teeth} represents the human teeth.
     * <p/>
     * The human teeth are the bony extensions of the skull and jaw bone ordered around the tongue.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Mouth.class)
    @Category
    @Category.Instance(type = UpperTeeth.class)
    @Category.Instance(type = LowerTeeth.class)
    public abstract
    class Teeth
    extends BodyPartGroup
    implements
        Internal,
        MouthPart
    {
        /**
         * Creates the teeth containing the upper and lower teeth.
         */
        protected
        Teeth() {
            super();
            createPart(UpperTeeth.class);
            createPart(LowerTeeth.class);
        }
    }

    /**
     * {@code Thorax} represents the upper part of the torso.
     * <p/>
     * The human thorax is the upper part of the torso also known as the chest.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Torso.class)
    @Category
    @Category.Instance(type = Lungs.class)
    public abstract
    class Thorax
    extends Fixture
    implements
        Internal,
        TorsoPart,
        Upper
    {
        /**
         * Creates the thorax containing the lungs.
         */
        protected
        Thorax() {
            super();
            createPart(Lungs.class);
        }
    }

    /**
     * {@code Throat} represents the human throat.
     * <p/>
     * The human throat is the cavity in the neck through which air flows into the lungs.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Human.class)
    public abstract
    class Throat
    extends Joint
    implements
        Internal,
        NeckPart
    {
        /**
         * Creates the throat.
         */
        protected
        Throat() {
            super();
        }
    }

    /**
     * {@code ThirdFinger} represents the human third finger.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Finger.class, values = { "orientation -> orientation", "order", "3" })
    public abstract
    class ThirdFinger
    extends CompositeOrientationalBodyPart
    implements
        External,
        HandPart,
        Ordered<Byte>,
        Ordered.PerMany<Byte>
    {
        /**
         * Creates the third finger with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        ThirdFinger(
            final Orientation orientation
            ) {
            super(orientation);

            createPart(Finger.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 3);
        }
    }

    /**
     * {@code ThirdFingers} represents both human third fingers.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    public abstract
    class ThirdFingers
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<ThirdFinger, Hands>
    {
        /**
         * Creates both third fingers.
         */
        protected
        ThirdFingers()
        {
            super();

            createPart(ThirdFinger.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Thumb} represents the human thumb finger.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Finger.class, values = { "orientation -> orientation", "order", "0" })
    public abstract
    class Thumb
    extends CompositeOrientationalBodyPart
    implements
        External,
        HandPart,
        Ordered<Byte>,
        Ordered.PerMany<Byte>
    {
        /**
         * Creates the thumb finger with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        Thumb(
            final Orientation orientation
            ) {
            super(orientation);

            createPart(Finger.class)
            .withParameterRanges(2, 5)
            .withValues(orientation, 0);
        }
    }

    /**
     * {@code Thumb} represents both human thumb fingers.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Thumb.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class Thumbs
    extends OrientationalBodyPartGroup
    implements Universal.ComprehensiveGroup<Thumb, Hands>
    {
        /**
         * Creates both thumbs.
         */
        protected
        Thumbs()
        {
            super();

            createPart(Thumb.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code Toes} represents all the toes of the human foot as a single body part.
     * <p/>
     * The human foot toes are the outermost part of the foot containing all phalanges attached to the knuckles.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Foot.class)
    public abstract
    class Toes
    extends OrientationalFixture
    implements
        External,
        FootPart
    {
        /**
         * Creates the foot toes with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        Toes(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Tongue} represents the human tongue.
     * <p/>
     * The human tongue is the loose organ inside the mouth with which voice is altered during speech.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Mouth.class)
    public abstract
    class Tongue
    extends AtomicBodyPart
    implements
        Internal,
        MouthPart
    {
        /**
         * Creates the tongue.
         */
        protected
        Tongue() {
            super();
        }
    }

    /**
     * {@code Torso} represents the human torso.
     * <p/>
     * The human torso is the upper middle portion of the body containing the thorax, abdomen, and waist.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Human.class)
    @Category
    @Category.Instance(type = Thorax.class)
    @Category.Instance(type = Abdomen.class)
    @Category.Instance(type = Waist.class)
    public abstract
    class Torso
    extends CompositeBodyPart
    implements
        External,
        Mid,
        Upper
    {
        /**
         * Creates the torso containing the thorax, abdomen, and waist.
         */
        protected
        Torso() {
            super();

            createPart(Thorax.class);
            createPart(Abdomen.class);
            createPart(Waist.class);
        }
    }

    /**
     * {@code TorsoPart} classifies a part of the human torso.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface TorsoPart
    extends BodyPart
    {
        @Override
        public default Class<Torso> getCorrespondingBodyPartClass() {
            return Torso.class;
        }
    }

    /**
     * {@code Universal} identifies human-specific universality.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Universal
    extends Instrument.Universal
    {
        /**
         * {@code Circumduction} classifies the circumduction motion of human body parts.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Circumduction
        extends Motion
        {}

        /**
         * {@code Coronal} classifies the coronal anatomical plane of the human body.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Coronal
        extends Plane
        {
            /**
             * {@code Abduction} classifies the abduction motion of human body parts.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Abduction
            extends Motion
            {}

            /**
             * {@code Adduction} classifies the adduction motion of human body parts.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Adduction
            extends Motion
            {}
        }

        /**
         * {@code Counternutation} classifies the counternutation motion of human body parts.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Counternutation
        extends Motion
        {}

        /**
         * {@code Depression} classifies the depression motion of human body parts.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Depression
        extends Motion
        {}

        /**
         * {@code Dorsal} classifies the dorsal anatomical plane of the human body.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Dorsal
        extends Plane
        {}

        /**
         * {@code Elevation} classifies the elevation motion of human body parts.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Elevation
        extends Motion
        {}

        /**
         * {@code Nutation} classifies the nutation motion of human body parts.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Nutation
        extends Motion
        {}

        /**
         * {@code Occlusion} classifies the occlusion motion of human body parts.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Occlusion
        extends Motion
        {}

        /**
         * {@code Opposition} classifies the opposition motion of human body parts.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Opposition
        extends Motion
        {}

        /**
         * {@code Palmar} classifies the palmar anatomical plane of the human body.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Palmar
        extends Plane
        {}

        /**
         * {@code Parasagittal} classifies the parasagittal anatomical plane of the human body.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Parasagittal
        extends Plane
        {}

        /**
         * {@code Pronation} classifies the pronation motion of human body parts.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Pronation
        extends Motion
        {}

        /**
         * {@code Protraction} classifies the protraction motion of human body parts.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Protraction
        extends Motion
        {}

        /**
         * {@code Protrusion} classifies the protrusion motion of human body parts.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Protrusion
        extends Motion
        {}

        /**
         * {@code Reciprocal} classifies the reciprocal motion of human body parts.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Reciprocal
        extends Motion
        {}

        /**
         * {@code Reduction} classifies the reduction motion of human body parts.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Reduction
        extends Motion
        {}

        /**
         * {@code Reposition} classifies the reposition motion of human body parts.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Reposition
        extends Motion
        {}

        /**
         * {@code Retraction} classifies the retraction motion of human body parts.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Retraction
        extends Motion
        {}

        /**
         * {@code Retrusion} classifies the retrusion motion of human body parts.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Retrusion
        extends Motion
        {}

        /**
         * {@code Rotation} classifies the rotation motion of human body parts.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Rotation
        extends Motion
        {
            /**
             * {@code External} classifies the external rotation motion of human body parts.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface External
            extends Rotation
            {}

            /**
             * {@code Internal} classifies the internal rotation motion of human body parts.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Internal
            extends Rotation
            {}
        }

        /**
         * {@code Sagittal} classifies the sagittal anatomical plane of the human body.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Sagittal
        extends Plane
        {
            /**
             * {@code Eversion} classifies the eversion motion of human body parts on the sagittal plane.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Eversion
            extends Motion
            {}

            /**
             * {@code Extension} classifies the extension motion of human body parts on the sagittal plane.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Extension
            extends Motion
            {}

            /**
             * {@code Flexion} classifies the flexion motion of human body parts on the sagittal plane.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Flexion
            extends Motion
            {
                /**
                 * {@code Dorsi} classifies the dorsiflexion motion of human body parts on the sagittal plane.
                 *
                 * @since 1.8
                 * @author Alireza Kamran
                 */
                interface Dorsi
                extends Flexion
                {}

                /**
                 * {@code Palmar} classifies the palmarflexion motion of human body parts on the sagittal plane.
                 *
                 * @since 1.8
                 * @author Alireza Kamran
                 */
                interface Palmar
                extends Flexion
                {}

                /**
                 * {@code Plantar} classifies the plantarflexion motion of human body parts on the sagittal plane.
                 *
                 * @since 1.8
                 * @author Alireza Kamran
                 */
                interface Plantar
                extends Flexion
                {}
            }

            /**
             * {@code Inversion} classifies the inversion motion of human body parts on the sagittal plane.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Inversion
            extends Motion
            {}
        }

        /**
         * {@code Supination} classifies the supination motion of human body parts on the sagittal plane.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Supination
        extends Motion
        {}

        /**
         * {@code Torsion} classifies the torsion motion of human body parts on the sagittal plane.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Torsion
        extends Motion
        {}

        /**
         * {@code Transverse} classifies the transverse anatomical plane of the human body.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Transverse
        extends Plane
        {}

        /**
         * {@code Version} classifies the version motion of human body parts on the sagittal plane.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Version
        extends Motion
        {}
    }

    /**
     * {@code Upper} classifies a human upper body part.
     * <p/>
     * The upper body parts are those above the abdomen.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Upper
    extends Universal.Zone.Characteristic
    {}

    /**
     * {@code UpperArm} represents the upper part of the human arm.
     * <p/>
     * The human upper arm is the part between the shoulder and the elbow.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Arm.class)
    public abstract
    class UpperArm
    extends OrientationalFixture
    implements
        ArmPart,
        External
    {
        /**
         * Creates the upper arm with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        UpperArm(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code UpperArms} represents both upper arms of the human body.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = UpperArm.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class UpperArms
    extends OrientationalBodyPartGroup
    implements
        ArmPart,
        External,
        Universal.ComprehensiveGroup<UpperArm, Arms>
    {
        /**
         * Creates both upper arms.
         */
        protected
        UpperArms() {
            super();

            createPart(UpperArm.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code UpperLeg} represents the upper part of the human leg.
     * <p/>
     * The human upper leg is the part between the hip and knee also known as the thigh.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Leg.class)
    public abstract
    class UpperLeg
    extends OrientationalFixture
    implements
        External,
        LegPart
    {
        /**
         * Creates the upper leg with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        UpperLeg(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code UpperLegs} represents both upper legs of the human body.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = UpperLeg.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class UpperLegs
    extends OrientationalBodyPartGroup
    implements
        External,
        LegPart,
        Universal.ComprehensiveGroup<UpperLeg, Legs>
    {
        /**
         * Creates both upper legs.
         */
        protected
        UpperLegs() {
            super();

            createPart(UpperLeg.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }

    /**
     * {@code UpperLip} represents the upper part of the human lips.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Mouth.class)
    public abstract
    class UpperLip
    extends AtomicBodyPart
    implements Lip
    {
        /**
         * Creates the upper lip.
         */
        protected
        UpperLip() {
            super();
        }
    }

    /**
     * {@code UpperTeeth} represents the upper part of the human teeth.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Mouth.class)
    public abstract
    class UpperTeeth
    extends Fixture
    implements
        Internal,
        MouthPart
    {
        /**
         * Creates the upper teeth.
         */
        protected
        UpperTeeth() {
            super();
        }
    }

    /**
     * {@code Waist} represents the human waist.
     * <p/>
     * The human waist is the joint in the middle body connecting the abdomen to the hips.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Torso.class)
    public abstract
    class Waist
    extends Joint
    implements
        External,
        Mid,
        TorsoPart
    {
        /**
         * Creates the waist.
         */
        protected
        Waist() {
            super();
        }
    }

    /**
     * {@code Wrist} represents the human wrist.
     * <p/>
     * The human wrist is the joint in the arm connecting the lower arm to the hand.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Arm.class)
    public abstract
    class Wrist
    extends OrientationalJoint
    implements
        ArmPart,
        External
    {
        /**
         * Creates the wrist with the specified orientation.
         *
         * @param orientation the orientation.
         */
        @Categorized(parameters = "orientation")
        protected
        Wrist(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Wrists} represents both wrists of the human arms.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Category
    @Category.Instance(type = Wrist.class, values = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class Wrists
    extends OrientationalBodyPartGroup
    implements
        External,
        Universal.ComprehensiveGroup<Wrist, Arms>,
        Upper
    {
        /**
         * Creates both wrists.
         */
        protected
        Wrists() {
            super();

            createPart(Wrist.class)
            .withParameterRanges(2)
            .withValues(Orientation.RIGHT)
            .withValues(Orientation.LEFT);
        }
    }
}