package performance;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Collection;
import java.util.Map;

/**
 * {@code Human} represents a performer's physical body.
 * <p>
 * This superclass defines a model of the human anatomy, sufficiently simplified for representing the postures and movements in music performance.
 */
public abstract
class Human
extends Instrument
{
    /**
     * Creates a human with the specified body part classes and orientations.
     * <p>
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
     * <p>
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
     * <p>
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
     * <p>
     * The human abdomen is the lower part of the torso also known as the belly.
     */
    @Uniformed(group = Human.class)
    public abstract
    class Abdomen
    extends Joint
    implements
        External,
        Lower,
        TorsoPart
    {
        /**
         * Creates an abdomen.
         */
        protected
        Abdomen() {
            super();
            createPart(Abdomen.class);
        }
    }

    /**
     * {@code AllFingers} represents all fingers of the human hands.
     */
    @Category
    public abstract
    class AllFingers
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<Finger, Human>
    {
        /**
         * Creates all fingers.
         */
        protected
        AllFingers() {
            super();

            createPart(Finger.class)
            .withParameterRanges(2, 5)
            .withValues(Orientation.RIGHT, 0)
            .withValues(Orientation.LEFT, 0)
            .withValues(Orientation.RIGHT, 1)
            .withValues(Orientation.LEFT, 1)
            .withValues(Orientation.RIGHT, 2)
            .withValues(Orientation.LEFT, 2)
            .withValues(Orientation.RIGHT, 3)
            .withValues(Orientation.LEFT, 3)
            .withValues(Orientation.RIGHT, 4)
            .withValues(Orientation.LEFT, 4);
        }
    }

    /**
     * {@code AllFingerJoints} represents all finger joints of the human hands.
     */
    @Category
    public abstract
    class AllFingerJoints
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<FingerJoint, Human>
    {
        /**
         * Creates all finger joints.
         */
        protected
        AllFingerJoints() {
            super();

            createPart(FingerJoint.class)
            .withParameterRanges(2, 5, 2)
            .withValues(Orientation.RIGHT, 0, 0)
            .withValues(Orientation.RIGHT, 0, 1)
            .withValues(Orientation.LEFT, 0, 0)
            .withValues(Orientation.LEFT, 0, 1)
            .withValues(Orientation.RIGHT, 1, 0)
            .withValues(Orientation.RIGHT, 1, 1)
            .withValues(Orientation.LEFT, 1, 0)
            .withValues(Orientation.LEFT, 1, 1)
            .withValues(Orientation.RIGHT, 2, 0)
            .withValues(Orientation.RIGHT, 2, 1)
            .withValues(Orientation.LEFT, 2, 0)
            .withValues(Orientation.LEFT, 2, 1)
            .withValues(Orientation.RIGHT, 3, 0)
            .withValues(Orientation.RIGHT, 3, 1)
            .withValues(Orientation.LEFT, 3, 0)
            .withValues(Orientation.LEFT, 3, 1)
            .withValues(Orientation.RIGHT, 4, 0)
            .withValues(Orientation.RIGHT, 4, 1)
            .withValues(Orientation.LEFT, 4, 0)
            .withValues(Orientation.LEFT, 4, 1);
        }
    }

    /**
     * {@code AllFingerKnuckles} represents all finger knuckles of the human hands.
     */
    @Category
    public abstract
    class AllFingerKnuckles
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<FingerKnuckle, Human>
    {
        /**
         * Creates all finger knuckles.
         */
        protected
        AllFingerKnuckles() {
            super();

            createPart(FingerKnuckle.class)
            .withParameterRanges(2, 5)
            .withValues(Orientation.RIGHT, 0)
            .withValues(Orientation.LEFT, 0)
            .withValues(Orientation.RIGHT, 1)
            .withValues(Orientation.LEFT, 1)
            .withValues(Orientation.RIGHT, 2)
            .withValues(Orientation.LEFT, 2)
            .withValues(Orientation.RIGHT, 3)
            .withValues(Orientation.LEFT, 3)
            .withValues(Orientation.RIGHT, 4)
            .withValues(Orientation.LEFT, 4);
        }
    }

    /**
     * {@code AllFingerNails} represents all finger nails of the human hands.
     */
    @Category
    public abstract
    class AllFingerNails
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<FingerNail, Human>
    {
        /**
         * Creates all finger nails.
         */
        protected
        AllFingerNails() {
            super();

            createPart(FingerNail.class)
            .withParameterRanges(2, 5)
            .withValues(Orientation.RIGHT, 0)
            .withValues(Orientation.LEFT, 0)
            .withValues(Orientation.RIGHT, 1)
            .withValues(Orientation.LEFT, 1)
            .withValues(Orientation.RIGHT, 2)
            .withValues(Orientation.LEFT, 2)
            .withValues(Orientation.RIGHT, 3)
            .withValues(Orientation.LEFT, 3)
            .withValues(Orientation.RIGHT, 4)
            .withValues(Orientation.LEFT, 4);
        }
    }

    /**
     * {@code AllFingerTips} represents all finger tips of the human hands.
     */
    @Category
    public abstract
    class AllFingerTips
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<FingerTip, Human>
    {
        /**
         * Creates all finger tips.
         */
        protected
        AllFingerTips() {
            super();

            createPart(FingerTip.class)
            .withParameterRanges(2, 5)
            .withValues(Orientation.RIGHT, 0)
            .withValues(Orientation.LEFT, 0)
            .withValues(Orientation.RIGHT, 1)
            .withValues(Orientation.LEFT, 1)
            .withValues(Orientation.RIGHT, 2)
            .withValues(Orientation.LEFT, 2)
            .withValues(Orientation.RIGHT, 3)
            .withValues(Orientation.LEFT, 3)
            .withValues(Orientation.RIGHT, 4)
            .withValues(Orientation.LEFT, 4);
        }
    }

    /**
     * {@code AllFingerKnuckles} represents both knuckles of the human feet.
     */
    @Category
    public abstract
    class AllFootKnuckles
    extends OrientationalBodyPartGroup
    implements
        External,
        FootPart,
        Universal.ComprehensiveGroup<FootKnuckles, Human>
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
     */
    @Category(types = FourFingers.class, instances = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFourFingers
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<FourFingers, Human>
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
     */
    @Category(types = FourFingerJoints.class, instances = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFourFingerJoints
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<FourFingerJoints, Human>
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
     */
    @Category(types = FourFingerKnuckles.class, instances = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFourFingerKnuckles
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<FourFingerKnuckles, Human>
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
     */
    @Category(types = FourFingerNails.class, instances = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFourFingerNails
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<FourFingerNails, Human>
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
     */
    @Category(types = FourFingerTips.class, instances = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFourFingerTips
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<FourFingerTips, Human>
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
     */
    @Category(types = FourPhalanges.class, instances = { "orientation", "RIGHT", "LEFT" })
    public abstract
    class AllFourPhalanges
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<FourPhalanges, Human>
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
     */
    @Category
    public abstract
    class AllPhalanges
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<Phalanx, Human>
    {
        /**
         * Creates all phalanges.
         */
        protected
        AllPhalanges() {
            super();

            createPart(Phalanx.class)
            .withParameterRanges(2, 5, 3)
            .withValues(Orientation.RIGHT, 0, 0)
            .withValues(Orientation.LEFT, 0, 0)
            .withValues(Orientation.RIGHT, 0, 1)
            .withValues(Orientation.LEFT, 0, 1)
            .withValues(Orientation.RIGHT, 0, 2)
            .withValues(Orientation.LEFT, 0, 2)
            .withValues(Orientation.RIGHT, 1, 0)
            .withValues(Orientation.LEFT, 1, 0)
            .withValues(Orientation.RIGHT, 1, 1)
            .withValues(Orientation.LEFT, 1, 1)
            .withValues(Orientation.RIGHT, 1, 2)
            .withValues(Orientation.LEFT, 1, 2)
            .withValues(Orientation.RIGHT, 2, 0)
            .withValues(Orientation.LEFT, 2, 0)
            .withValues(Orientation.RIGHT, 2, 1)
            .withValues(Orientation.LEFT, 2, 1)
            .withValues(Orientation.RIGHT, 2, 2)
            .withValues(Orientation.LEFT, 2, 2)
            .withValues(Orientation.RIGHT, 3, 0)
            .withValues(Orientation.LEFT, 3, 0)
            .withValues(Orientation.RIGHT, 3, 1)
            .withValues(Orientation.LEFT, 3, 1)
            .withValues(Orientation.RIGHT, 3, 2)
            .withValues(Orientation.LEFT, 3, 2)
            .withValues(Orientation.RIGHT, 4, 0)
            .withValues(Orientation.LEFT, 4, 0)
            .withValues(Orientation.RIGHT, 4, 1)
            .withValues(Orientation.LEFT, 4, 1)
            .withValues(Orientation.RIGHT, 4, 2)
            .withValues(Orientation.LEFT, 4, 2);
        }
    }

    /**
     * {@code AllToes} represents both toes of the human feet.
     */
    @Category
    public abstract
    class AllToes
    extends OrientationalBodyPartGroup
    implements
        External,
        FootPart,
        Universal.ComprehensiveGroup<Toes, Human>
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
     * <p>
     * The human ankle is the joint in the leg connecting the lower leg to the foot.
     */
    @Uniformed(group = Leg.class)
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
        protected
        Ankle(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Ankles} represents both ankles of the human feet.
     */
    @Category
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
     * <p>
     * The human arm is the part of the body containing the upper arm, elbow, lower arm, wrist, and hand.
     */
    @Uniformed(group = Human.class)
    public abstract
    class Arm
    extends CompositeOrientationalBodyPart
    implements
        External,
        Upper
    {
        /**
         * Creates the arm with the specified orientation and containing the upper arm, elbow, lower arm, wrist, and hand.
         *
         * @param orientation the orientation.
         */
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
     */
    public
    interface ArmPart
    extends
        BodyPart,
        Upper
    {}

    /**
     * {@code Arms} represents both arms of the human body.
     */
    @Category
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
     */
    protected abstract
    class AtomicBodyPart
    extends AtomicPart
    implements BodyPart
    {}

    /**
     * {@code BodyPart} classifies a human body part.
     */
    protected
    interface BodyPart
    extends Part
    {
        @Override
        public default boolean is(final system.Type<Part> type) {
            return type instanceof BodyPart;
        }

        @Retention(RUNTIME)
        @Target(TYPE)
        @Repeatable(Connections.class)
        public
        @interface Connection
        {
            /** Joint connection flag. */
            boolean joint()
            default true;

            /** The first connecting part type. */
            Class<? extends BodyPart> firstPart();

            /** The second connecting part type. */
            Class<? extends BodyPart> secondPart();

            @Retention(RUNTIME)
            @Target(TYPE)
            @Repeatable(Definitions.class)
            public
            @interface Definition
            {
                /** The instance part type. */
                Class<? extends BodyPart> type();

                /** The instance definitions. */
                String[] instances()
                default "";
            }

            @Retention(RUNTIME)
            @Target(TYPE)
            public
            @interface Definitions
            {
                Definition[] value();
            }
        }

        @Retention(RUNTIME)
        @Target(TYPE)
        public
        @interface Connections
        {
            Connection[] value();
        }
    }

    /**
     * {@code BodyPartGroup} classifies a human body part group.
     */
    protected abstract
    class BodyPartGroup
    extends PartGroup
    {}

    /**
     * {@code CompositeBodyPart} classifies a human body part that has the characteristics of a composite instrument part.
     */
    protected abstract
    class CompositeBodyPart
    extends CompositePart
    implements BodyPart
    {}

    /**
     * {@code CompositeOrientationalBodyPart} classifies a composite body part that is also orientational.
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
     * <p>
     * The human ear is the organ on the head through which sound is primarily sensed.
     */
    @Uniformed(group = Head.class)
    public abstract
    class Ear
    extends OrientationalFixture
    implements
        External,
        HeadPart
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
     * <p>
     * The human elbow is the joint in the arm connecting the upper arm to the lower arm.
     */
    @Uniformed(group = Arm.class)
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
        protected
        Elbow(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Elbows} represents both human elbows.
     */
    @Category
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
     * <p>
     * The external body parts are those that are visible.
     */
    public
    interface External
    extends Universal.Zone.Characteristic
    {}

    /**
     * {@code Eye} represents the human eye.
     * <p>
     * The human eye is the primary organ of vision on the head.
     */
    @Uniformed(group = Head.class)
    public abstract
    class Eye
    extends OrientationalFixture
    implements
        External,
        HeadPart
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
     */
    @Category
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
     * <p>
     * The human finger is the outermost part of the hand attached to the palm via its knuckle joint.
     */
    @Uniformed(group = Hand.class, count = 5)
    public abstract
    class Finger
    extends CompositeOrientationalBodyPart
    implements
        External,
        HandPart,
        Ordered
    {
        /** The finger order. */
        protected final
        byte order;

        /**
         * Creates the finger with the specified orientation containing phalanges, joints, finger tips, and nails.
         * <p>
         * This constructor calls {@link Number#byteValue()} on the order.
         *
         * @param orientation the orientation.
         * @param order the finger order.
         */
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
        public Number getOrder(
            final Class<? extends Part> partClass
            ) {
            if (partClass == FourFingers.class || partClass == Hand.class || partClass == Arm.class)
                return order;

            else if (partClass == Hands.class || partClass == AllFourFingers.class || partClass == Arms.class)
                return  orientation.order * 5 + order;

            return null;
        }

        /**
         * Returns the finger order.
         *
         * @return the finger order.
         */
        @Override
        public short getOrder() {
            return order;
        }
    }

    /**
     * {@code FingerJoint} represents the joint of the human finger.
     * <p>
     * The human finger joint is the part of the finger that bends.
     */
    @Uniformed(group = Finger.class, count = 2)
    public abstract
    class FingerJoint
    extends OrientationalJoint
    implements
        External,
        FingerPart,
        Ordered
    {
        /** The finger order. */
        protected final
        byte fingerOrder;

        /** The finger joint order. */
        protected final
        byte order;

        /**
         * Creates the finger joint with the specified orientation, finger order, and order.
         * <p>
         * This constructor calls {@link Number#byteValue()} on both orders.
         *
         * @param orientation the orientation.
         * @param fingerOrder the finger order.
         * @param order the order of the joint in the finger.
         */
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
        public byte getFingerOrder() {
            return fingerOrder;
        }

        @Override
        public Number getOrder(
            final Class<? extends Part> partClass
            ) {
            if (partClass == Finger.class)
                return order;

            else if (partClass == Hand.class || partClass == FourFingers.class || partClass == Arm.class)
                return fingerOrder * 2 + order;

            else if (partClass == Hands.class || partClass == AllFourFingers.class || partClass == Arms.class)
                return  orientation.order * 5 + fingerOrder * 2 + order;

            return null;
        }

        @Override
        public short getOrder() {
            return order;
        }
    }

    /**
     * {@code FingerJoints} represents all finger joints of the human hand.
     */
    @Category
    public abstract
    class FingerJoints
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<FingerJoint, Arm>
    {
        /**
         * Creates all finger joints of the human hand with the specified orientation.
         */
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
     * <p>
     * The human finger knuckle is the joint in the hand connecting the palm to the finger.
     */
    @Uniformed(group = Hand.class, count = 5)
    @BodyPart.Connection(firstPart = Phalanx.class, secondPart = Palm.class)
    @BodyPart.Connection.Definition(type = Phalanx.class, instances = { "order", "0" })
    public abstract
    class FingerKnuckle
    extends OrientationalJoint
    implements
        External,
        HandPart,
        Ordered.PerFinger
    {
        /** The finger order. */
        protected final
        byte fingerOrder;

        /**
         * Creates the finger knuckle with the specified orientation and finger order.
         * <p>
         * This constructor calls {@link Number#byteValue()} on the finger order.
         *
         * @param orientation the orientation.
         * @param fingerOrder the finger order.
         */
        protected
        FingerKnuckle(
            final Orientation orientation,
            final Number fingerOrder
            ) {
            super(orientation);
            this.fingerOrder = fingerOrder.byteValue();
        }

        public
        byte getFingerOrder() {
            return fingerOrder;
        }

        @Override
        public Number getOrder(
            final Class<? extends Part> part
            ) {
            if (part == Finger.class)
                return getFingerOrder();

            return null;
        }

        @Override
        public short getOrder() {
            return getFingerOrder();
        }
    }

    /**
     * {@code FingerKnuckles} represents all finger knuckles of the human hand.
     */
    @Category
    public abstract
    class FingerKnuckles
    extends OrientationalBodyPartGroup
    implements
        External,
        Universal.ComprehensiveGroup<FingerKnuckle, Arm>
    {
        /**
         * Creates all finger knuckles of the human hand with the specified orientation.
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
     * <p>
     * The finger nail is the flat horn-like part of the finger tip.
     */
    @Uniformed(group = Finger.class)
    public abstract
    class FingerNail
    extends OrientationalFixture
    implements
        External,
        FingerPart,
        Ordered
    {
        /** The finger order. */
        protected final
        byte fingerOrder;

        /**
         * Creates the finger nail with the specified orientation and finger order.
         * <p>
         * This constructor calls {@link Number#byteValue()} on the finger order.
         *
         * @param orientation the orientation.
         * @param fingerOrder the finger order.
         */
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
        public Number getOrder(
            final Class<? extends Part> part
            ) {
            if (part == Finger.class)
                return getFingerOrder();

            return null;
        }

        @Override
        public short getOrder() {
            return getFingerOrder();
        }
    }

    /**
     * {@code FingerNails} represents all finger nails of the human hand.
     */
    @Category
    public abstract
    class FingerNails
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<FingerNail, Arm>
    {
        /**
         * Creates all finger nails of the human hand with the specified orientation.
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
     */
    public
    interface FingerPart
    extends HandPart
    {
        /**
         * Returns the finger order.
         *
         * @return the finger order.
         */
        public
        byte getFingerOrder();
    }

    /**
     * {@code Fingers} represents all fingers of the human hand.
     */
    @Category
    public abstract
    class Fingers
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart,
        Universal.ComprehensiveGroup<Finger, Arm>
    {
        /**
         * Creates all fingers of the human hand with the specified orientation.
         */
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
     * <p>
     * The fingertip is the outermost part of the last finger phalanx.
     */
    @Uniformed(group = Finger.class)
    public abstract
    class FingerTip
    extends OrientationalFixture
    implements
        External,
        FingerPart,
        Ordered
    {
        /** The finger order. */
        protected final
        byte fingerOrder;

        /**
         * Creates a finger tip with the specified orientation and finger order.
         * <p>
         * This constructor calls {@link Number#byteValue()} on the finger order.
         *
         * @param orientation the orientation.
         * @param fingerOrder the finger order.
         */
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
        public Number getOrder(
            final Class<? extends Part> part
            ) {
            if (part == Finger.class)
                return getFingerOrder();

            return null;
        }

        @Override
        public short getOrder() {
            return getFingerOrder();
        }
    }

    /**
     * {@code FingerTips} represents all finger tips of the human hand.
     */
    @Category
    public abstract
    class FingerTips
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<FingerTip, Arm>
    {
        /**
         * Creates all finger tips of the human hand with the specified orientation.
         */
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
     */
    @Category(types = Finger.class, instances = { "order", "1" })
    public abstract
    class FirstFinger
    implements Orientational
    {}

    /**
     * {@code FirstFingers} represents both human first fingers.
     */
    @Category
    public abstract
    class FirstFingers
    extends OrientationalBodyPartGroup
    implements Universal.ComprehensiveGroup<FirstFinger, Human>
    {}

    /**
     * {@code Fixture} classifies an atomic body part that doesn't, or is not intended to, bend and connects to a joint or multiple joints.
     */
    protected abstract
    class Fixture
    extends AtomicBodyPart
    {}

    /**
     * {@code Foot} represents the human foot.
     * <p>
     * The human foot is the lowest part of the leg attached to the lower leg via the ankle.
     */
    @Uniformed(group = Leg.class)
    public abstract
    class Foot
    extends CompositeOrientationalBodyPart
    implements
        External,
        LegPart
    {
        /**
         * Creates the foot with the specified orientation and containing the heel, sole, knuckles, and toes.
         *
         * @param orientation the orientation.
         */
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
     * <p>
     * The human foot knuckles are the joints connecting the sole to the toes.
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
     */
    public
    interface FootPart
    extends LegPart
    {}

    /**
     * {@code FourFingerJoints} represents four finger joints of the human hand excluding the thumb.
     */
    @Category(types = FingerJoint.class, instances = { "fingerOrder", "1", "2", "3", "4", "order", "0", "1", "2" })
    public abstract
    class FourFingerJoints
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart
    {
        /**
         * Creates all four finger joints with the specified orientation.
         */
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
     */
    @Category(types = FingerKnuckle.class, instances = { "order", "1", "2", "3", "4" })
    public abstract
    class FourFingerKnuckles
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart
    {
        /**
         * Creates all four finger knuckles with the specified orientation.
         */
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
     */
    @Category(types = FingerNail.class, instances = { "order", "1", "2", "3", "4" })
    public abstract
    class FourFingerNails
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart
    {
        /**
         * Creates all four finger nails with the specified orientation.
         */
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
     */
    @Category(types = Finger.class, instances = { "order", "1", "2", "3", "4" })
    public abstract
    class FourFingers
    extends OrientationalBodyPartGroup
    implements
        External,
        HandPart
    {
        /**
         * Creates all four fingers of the human hand with the specified orientation.
         */
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
     * {@code FourFingerTips} represents four finger tips of the human hand excluding the thumb.
     */
    @Category(types = FingerTip.class, instances = { "order", "1", "2", "3", "4" })
    public abstract
    class FourFingerTips
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart
    {
        /**
         * Creates all four finger tips with the specified orientation.
         */
        protected
        FourFingerTips(
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
     * {@code FourPhalanges} represents all phalanges of four fingers of the human hand excluding the thumb.
     */
    @Category(types = Phalanx.class, instances = { "fingerOrder", "1", "2", "3", "4", "order", "0", "1", "2" })
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
     */
    @Category(types = Finger.class, instances = { "order", "4" })
    public abstract
    class FourthFinger
    implements Orientational
    {}

    /**
     * {@code FourthFingers} represents both human fourth fingers.
     */
    @Category
    public abstract
    class FourthFingers
    extends OrientationalBodyPartGroup
    implements Universal.ComprehensiveGroup<FourthFinger, Human>
    {}

    /**
     * {@code Hand} represents the human hand.
     * <p>
     * The human hand is the outermost part of the arm attached to the lower arm via the wrist.
     */
    @Uniformed(group = Arm.class)
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
     */
    @Category
    public abstract
    class Hands
    extends OrientationalBodyPartGroup
    implements
        ArmPart,
        External,
        Universal.ComprehensiveGroup<Hand, Human>
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
     */
    public
    interface HandPart
    extends ArmPart
    {}

    /**
     * {@code Head} represents the human head.
     */
    @Uniformed(group = Human.class)
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
     */
    public
    interface HeadPart
    extends
        BodyPart,
        Upper
    {}

    /**
     * {@code Heel} represents the heel of the human foot.
     * <p>
     * The human heel is the part of the foot between the ankle and the sole.
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
        protected
        Heel(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Heels} represents both heels of the human feet.
     */
    @Category
    public abstract
    class Heels
    extends OrientationalBodyPartGroup
    implements
        External,
        FootPart,
        Universal.ComprehensiveGroup<Heel, Human>
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
     * <p>
     * The human hip is the joint connecting the waist to the leg.
     */
    @Uniformed(group = Human.class)
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
        protected
        Hip(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Hips} represents both hips of the human body.
     */
    @Category
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
     */
    @Category(types = Finger.class, instances = { "order", "1" })
    public abstract
    class IndexFinger
    implements Orientational
    {}

    /**
     * {@code IndexFingers} represents both human index fingers.
     */
    @Category
    public abstract
    class IndexFingers
    extends OrientationalBodyPartGroup
    implements Universal.ComprehensiveGroup<IndexFinger, Human>
    {}

    /**
     * {@code Internal} classifies an internal human body part.
     * <p>
     * The internal body parts are those that are not visible.
     */
    public
    interface Internal
    extends Universal.Zone.Characteristic
    {}

    /**
     * {@code Jaw} represents the human jaw.
     * <p>
     * The human jaw is the joint inside the mouth containing the lower teeth, also known as the mandible.
     */
    @Uniformed(group = Mouth.class)
    public abstract
    class Jaw
    extends Joint
    implements
        Internal,
        Grouping,
        MouthPart
    {
        /**
         * Creates the jaw containing the lower teeth.
         */
        protected
        Jaw() {
            super();
            createPart(LowerTeeth.class);
        }
    }

    /**
     * {@code Joint} classifies an atomic body part that bends and connects two parts together.
     */
    protected abstract
    class Joint
    extends AtomicBodyPart
    {}

    /**
     * {@code Knee} represents the human knee.
     * <p>
     * The human knee is the joint in the leg connecting the upper leg to the lower leg.
     */
    @Uniformed(group = Leg.class)
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
        protected
        Knee(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Knees} represents both knees of the human legs.
     */
    @Category
    public abstract
    class Knees
    extends OrientationalBodyPartGroup
    implements
        External,
        LegPart,
        Universal.ComprehensiveGroup<Knee, Human>
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
     * <p>
     * The human leg is the part of the body containing the upper leg, knee, lower leg, ankle, and foot.
     */
    @Uniformed(group = Human.class)
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
     */
    @Category
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
     */
    public
    interface LegPart
    extends
        BodyPart,
        Lower
    {}

    /**
     * {@code Lip} classifies one of the human lips.
     */
    public
    interface Lip
    extends
        External,
        HeadPart
    {}

    /**
     * {@code Lips} represents both lips.
     * <p>
     * The human lips are the outer part of the mouth, covering the teeth, through which consonants are shaped during speech.
     */
    @Category(types = { UpperLip.class, LowerLip.class })
    public abstract
    class Lips
    extends BodyPartGroup
    implements
        External,
        MouthPart,
        Universal.ComprehensiveGroup<Lip, Human>
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
     */
    @Category(types = Finger.class, instances = { "order", "4" })
    public abstract
    class LittleFinger
    implements Orientational
    {}

    /**
     * {@code LittleFingers} represents both human little fingers.
     */
    @Category
    public abstract
    class LittleFingers
    extends OrientationalBodyPartGroup
    implements Universal.ComprehensiveGroup<LittleFinger, Human>
    {}

    /**
     * {@code Lower} classifies a human lower body part.
     * <p>
     * The lower body parts are those below the waist.
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
        protected
        LowerArm(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code LowerArms} represents both lower arms of the human body.
     */
    @Category
    public abstract
    class LowerArms
    extends OrientationalBodyPartGroup
    implements
        External,
        LegPart,
        Universal.ComprehensiveGroup<LowerArm, Human>
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
     */
    @Category
    public abstract
    class LowerLegs
    extends OrientationalBodyPartGroup
    implements
        External,
        LegPart,
        Universal.ComprehensiveGroup<LowerLeg, Human>
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
     */
    @Uniformed(group = Mouth.class)
    public abstract
    class LowerLip
    extends AtomicBodyPart
    implements
        External,
        Lip,
        MouthPart
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
     * <p>
     * The human lower teeth are the sharp extensions of the jaw bone.
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
     * <p>
     * The human lungs are the cavities in the thorax where air flows during breathing.
     */
    @Uniformed(group = Thorax.class)
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
     * <p>
     * The middle body parts are those above the hips and below the thorax.
     */
    public
    interface Mid
    extends Universal.Zone.Characteristic
    {}

    /**
     * {@code MiddleFinger} represents the human middle finger.
     */
    @Category(types = Finger.class, instances = { "order", "2" })
    public abstract
    class MiddleFinger
    implements Orientational
    {}

    /**
     * {@code MiddleFingers} represents both human middle fingers.
     */
    @Category
    public abstract
    class MiddleFingers
    extends OrientationalBodyPartGroup
    implements Universal.ComprehensiveGroup<MiddleFinger, Human>
    {}

    /**
     * {@code Mouth} represents the human mouth.
     * <p>
     * The human mouth is the cavity in the head containing the lips, tongue, and jaw.
     */
    @Uniformed(group = Head.class)
    public abstract
    class Mouth
    extends CompositeBodyPart
    implements
        External,
        Internal,
        HeadPart
    {
        /**
         * Creates the mouth containing the upper and lower lips, tongue, teeth, and jaw.
         */
        protected
        Mouth() {
            super();

            createPart(UpperLip.class);
            createPart(LowerLip.class);
            createPart(Tongue.class);
            createPart(Teeth.class);
            createPart(Jaw.class);
        }
    }

    /**
     * {@code MouthPart} classifies a part of the human mouth.
     */
    public
    interface MouthPart
    extends HeadPart
    {}

    /**
     * {@code Neck} represents the human neck.
     * <p>
     * The human neck is the joint connecting the head to the torso.
     */
    @Uniformed(group = Human.class)
    public abstract
    class Neck
    extends CompositeBodyPart
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
     */
    public
    interface NeckPart
    extends
        BodyPart,
        External,
        Upper
    {}

    /**
     * {@code Nose} represents the human nose.
     * <p>
     * The human nose is the organ above the lips through which breath flows regularly.
     */
    @Uniformed(group = Head.class)
    public abstract
    class Nose
    extends AtomicBodyPart
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

    protected
    interface Ordered
    extends Instrument.Ordered
    {
        public
        interface PerFinger
        extends Ordered
        {}
    }

    /**
     * {@code Orientation} represents the human body orientations: right and left.
     */
    public
    enum Orientation
    implements Instrument.Universal.Orientation
    {
        /** The right side. */
        RIGHT((byte) 0),

        /** The left side. */
        LEFT((byte) 1);

        /**
         * The order of orientation.
         */
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
     */
    public
    interface Orientational
    extends BodyPart
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
     */
    protected abstract
    class OrientationalBodyPartGroup
    extends BodyPartGroup
    {}

    /**
     * {@code OrientationalFixture} classifies a fixture that is also orientational.
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
     * <p>
     * The human palm is the biggest portion of the hand between the wrist and the finger knuckles.
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
     * <p>
     * The human finger phalanx is the part of the finger that does not bend.
     */
    @Uniformed(group = Finger.class, count = 3)
    public abstract
    class Phalanx
    extends OrientationalFixture
    implements
        FingerPart,
        Ordered
    {
        /** The finger order. */
        protected final
        byte fingerOrder;

        /** The finger phalanx order. */
        protected final
        byte order;

        /**
         * Creates the finger phalanx with the specified orientation, finger order, and order.
         * <p>
         * This constructor calls {@link Number#byteValue()} on both orders.
         *
         * @param orientation the orientation.
         * @param fingerOrder the finger order of the phalanx.
         * @param order the order of the phalanx in the finger.
         */
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

        @Override
        public
        byte getFingerOrder() {
            return fingerOrder;
        }

        @Override
        public Number getOrder(
            final Class<? extends Part> part
            ) {
            if (part == Finger.class)
                return getFingerOrder();

            return null;
        }

        @Override
        public short getOrder() {
            return order;
        }
    }

    /**
     * {@code Phalanges} represents all phalanges of the human hand.
     */
    @Category
    public abstract
    class Phalanges
    extends OrientationalBodyPartGroup
    implements
        External,
        FingerPart,
        Universal.ComprehensiveGroup<Phalanx, Arm>
    {
        /**
         * Creates all phalanges of the human hand with the specified orientation.
         */
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
     * {@code Pinky} represents the human pinky finger.
     */
    @Category(types = Finger.class, instances = { "order", "4" })
    public abstract
    class Pinky
    implements Orientational
    {}

    /**
     * {@code RingFinger} represents the human ring finger.
     */
    @Category(types = Finger.class, instances = { "order", "3" })
    public abstract
    class RingFinger
    implements Orientational
    {}

    /**
     * {@code RingFingers} represents both human ring fingers.
     */
    @Category
    public abstract
    class RingFingers
    extends OrientationalBodyPartGroup
    implements Universal.ComprehensiveGroup<RingFinger, Human>
    {}

    /**
     * {@code SecondFinger} represents the human second finger.
     */
    @Category(types = Finger.class, instances = { "order", "2" })
    public abstract
    class SecondFinger
    implements Orientational
    {}

    /**
     * {@code SecondFingers} represents both human second fingers.
     */
    @Category
    public abstract
    class SecondFingers
    extends OrientationalBodyPartGroup
    implements Universal.ComprehensiveGroup<SecondFinger, Human>
    {}

    /**
     * {@code Shoulder} represents the human shoulder.
     * <p>
     * The human shoulder is the joint connecting the torso to the arms.
     */
    @Uniformed(group = Torso.class)
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
        protected
        Shoulder(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Shoulders} represents both shoulders of the human torso.
     */
    @Category
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
     * <p>
     * The human sole is the biggest bottom portion of the foot between the heel and knuckles.
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
        protected
        Sole(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Soles} represents both soles of the human feet.
     */
    @Category
    public abstract
    class Soles
    extends OrientationalBodyPartGroup
    implements
        External,
        FootPart,
        Universal.ComprehensiveGroup<Sole, Human>
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
     * <p>
     * The human teeth are the bony extensions of the skull and jaw bone ordered around the tongue.
     */
    @Uniformed(group = Mouth.class)
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
     * <p>
     * The human thorax is the upper part of the torso also known as the chest.
     */
    @Uniformed(group = Torso.class)
    public abstract
    class Thorax
    extends CompositeBodyPart
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
     * <p>
     * The human throat is the cavity in the neck through which air flows into the lungs.
     */
    @Uniformed(group = Neck.class)
    public abstract
    class Throat
    extends Fixture
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
     */
    @Category(types = Finger.class, instances = { "order", "3" })
    public abstract
    class ThirdFinger
    implements Orientational
    {}

    /**
     * {@code ThirdFingers} represents both human third fingers.
     */
    @Category
    public abstract
    class ThirdFingers
    extends OrientationalBodyPartGroup
    implements Universal.ComprehensiveGroup<ThirdFinger, Human>
    {}

    /**
     * {@code Thumb} represents the human thumb finger.
     */
    @Category(types = Finger.class, instances = { "order", "0" })
    public abstract
    class Thumb
    implements Orientational
    {}

    /**
     * {@code Thumb} represents the human thumb finger.
     */
    @Category
    public abstract
    class Thumbs
    extends OrientationalBodyPartGroup
    implements Universal.ComprehensiveGroup<Thumb, Human>
    {}

    /**
     * {@code Toes} represents all the toes of the human foot as a single body part.
     * <p>
     * The human foot toes are the outermost part of the foot containing all phalanges attached to the knuckles.
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
        protected
        Toes(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Tongue} represents the human tongue.
     * <p>
     * The human tongue is the loose organ inside the mouth with which voice is altered during speech.
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
     * <p>
     * The human torso is the upper middle portion of the body containing the thorax, abdomen, and waist.
     */
    @Uniformed(group = Human.class)
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
     */
    public
    interface TorsoPart
    extends BodyPart
    {}

    /**
     * {@code Universal} identifies human-specific universality.
     */
    public
    interface Universal
    extends Instrument.Universal
    {
        interface Circumduction extends Motion {}

        interface Coronal extends Plane
        {
            interface Abduction extends Motion {}

            interface Adduction extends Motion {}
        }

        interface CounterNutation extends Motion {}

        interface Depression extends Motion {}

        interface Dorsal extends Plane {}

        interface Elevation extends Motion {}

        interface Nutation extends Motion {}

        interface Occlusion extends Motion {}

        interface Opposition extends Motion {}

        interface Palmar extends Plane {}

        interface ParaSagittal extends Plane {}

        interface Pronation extends Motion {}

        interface Protraction extends Motion {}

        interface Protrusion extends Motion {}

        interface Reciprocal extends Motion {}

        interface Reduction extends Motion {}

        interface Reposition extends Motion {}

        interface Retraction extends Motion {}

        interface Retrusion extends Motion {}

        interface Rotation extends Motion
        {
            interface External extends Rotation {}

            interface Internal extends Rotation {}
        }

        interface Sagittal extends Plane
        {
            interface Eversion extends Motion {}

            interface Extension extends Motion {}

            interface Flexion extends Motion
            {
                interface Dorsi extends Flexion {}

                interface Palmar extends Flexion {}

                interface Plantar extends Flexion {}
            }

            interface Inversion extends Motion {}
        }

        interface Supination extends Motion {}

        interface Torsion extends Motion {}

        interface Transverse extends Plane {}

        interface Version extends Motion {}
    }

    /**
     * {@code Upper} classifies a human upper body part.
     * <p>
     * The upper body parts are those above the abdomen.
     */
    public
    interface Upper
    extends Universal.Zone.Characteristic
    {}

    /**
     * {@code UpperArm} represents the upper part of the human arm.
     * <p>
     * The human upper arm is the part between the shoulder and the elbow.
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
        protected
        UpperArm(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code UpperArms} represents both upper arms of the human body.
     */
    @Category
    public abstract
    class UpperArms
    extends OrientationalBodyPartGroup
    implements
        ArmPart,
        External,
        Universal.ComprehensiveGroup<UpperArm, Human>
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
     * <p>
     * The human upper leg is the part between the hip and knee also known as the thigh.
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
        protected
        UpperLeg(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code UpperLegs} represents both upper legs of the human body.
     */
    @Category
    public abstract
    class UpperLegs
    extends OrientationalBodyPartGroup
    implements
        External,
        LegPart,
        Universal.ComprehensiveGroup<UpperLeg, Human>
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
     */
    @Uniformed(group = Mouth.class)
    public abstract
    class UpperLip
    extends AtomicBodyPart
    implements
        External,
        Lip,
        MouthPart
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
     * <p>
     * The human waist is the joint in the middle body connecting the abdomen to the hips.
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
     * <p>
     * The human wrist is the joint in the arm connecting the lower arm to the hand.
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
        protected
        Wrist(
            final Orientation orientation
            ) {
            super(orientation);
        }
    }

    /**
     * {@code Wrists} represents both wrists of the human arms.
     */
    @Category
    public abstract
    class Wrists
    extends OrientationalBodyPartGroup
    implements
        External,
        Universal.ComprehensiveGroup<Wrist, Arm>,
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