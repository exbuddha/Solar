package musical.performance;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import music.system.DataPoint;
import musical.Duration;
import musical.Interval;
import musical.Repetition;
import musical.Note.Dynamics;
import musical.performance.Conductor.ExecutionModel;
import musical.performance.Instrument.Direction;
import musical.performance.Instrument.Part;
import musical.performance.system.data.Description;
import system.Type;

/**
 * {@code Interaction} is a representation of an action-reaction between two parts, or many parts of two instruments, with the intention of creating an effect in the aural field.
 * It involves two part types: a masculine part that initiates the action, and a feminine part that reacts to the action.
 * An interaction is an atomic piece of data defining the most basic form of action-reaction for the purpose of creating the desired change, which doesn't require any further breakdown to smaller action-reaction definitions in the context of music performance.
 * For example, pressing a fret or plucking a string with a finger is considered to be an interaction.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Interaction
implements Snapshot
{
    /** The masculine part. */
    protected final
    system.data.Unit masculine;

    /** The feminine part. */
    protected final
    system.data.Unit feminine;

    /**
     * Creates an interaction with the specified masculine and feminine units.
     *
     * @param masculine the masculine unit.
     * @param feminine the feminine unit.
     */
    public
    Interaction(
        final system.data.Unit masculine,
        final system.data.Unit feminine
        ) {
        this.masculine = masculine;
        this.feminine = feminine;
    }

    /**
     * Creates an interaction with the specified masculine and feminine parts.
     *
     * @param masculine the masculine part.
     * @param feminine the feminine part.
     */
    public
    Interaction(
        final Part masculine,
        final Part feminine
        ) {
        this.masculine = masculine;
        this.feminine = feminine;
    }

    /**
     * Returns the masculine part.
     *
     * @return the masculine part.
     */
    public
    system.data.Unit getMasculine() {
        return masculine;
    }

    /**
     * Returns the feminine part.
     *
     * @return the feminine part.
     */
    public
    system.data.Unit getFeminine() {
        return feminine;
    }

    /**
     * {@code Conversion} classifies data types that support interaction point conversions among arbitrary units of performance, such as performers or instruments.
     * <p/>
     * An interaction point is defined as a data point in the three-dimensional space with axes defined as performance action, effect, and reaction types.
     * The aim of this interface is to provide a tabular approach to functionality similar to instrument and interaction interchangeability as sets of conversions.
     * <p/>
     * This class implementation is in progress.
     *
     * @param <T> the performance unit type, or the conversion type.
     * @param <A> the action type.
     * @param <E> the effect type.
     * @param <R> the reaction type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Conversion<T extends Unit, A extends Type<Action>, E extends Type<Effect>, R extends Type<Reaction>>
    extends Decision
    {
        /**
         * Converts the specified data of, or for converting with, the specified unit type into this supported unit data.
         *
         * @param data the data point.
         * @param type the unit type.
         * @return the converted data.
         */
        public
        DataPoint<A, E, R> convert(
            DataPoint<A, E, R> data,
            T type
            );
    }

    /**
     * {@code Physical} classifies instrument-specific interactions that involve physical actions, and acts as the superclass for all interactions in music performance.
     * <p/>
     * This class implementation is in progress.
     *
     * @param <T> the instrument type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class Physical<T extends Unit>
    extends Interaction
    implements musical.performance.system.Type<Physical.Performance>
    {
        /** The motion involved in the interaction. */
        protected
        Motion motion;

        /**
         * Creates a physical interaction with the specified masculine and feminine units.
         *
         * @param masculine the masculine unit.
         * @param feminine the feminine unit.
         */
        protected
        Physical(
            final system.data.Unit masculine,
            final system.data.Unit feminine
            ) {
            super(masculine, feminine);
        }

        /**
         * Creates a physical interaction with the specified masculine and feminine parts.
         *
         * @param masculine the masculine part.
         * @param feminine the feminine part.
         */
        protected
        Physical(
            final Part masculine,
            final Part feminine
            ) {
            super(masculine, feminine);
        }

        /**
         * Returns the motion.
         *
         * @return the motion.
         */
        public
        Motion getMotion() {
            return motion;
        }

        /**
         * Sets the motion.
         *
         * @param motion the motion.
         */
        public
        void setMotion(
            final Motion motion
            ) {
            this.motion = motion;
        }

        /**
         * {@code Accelerational} classifies interactions with accelerational action types.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Accelerational
        extends Performance
        {
            /**
             * Returns the acceleration of interaction.
             *
             * @return the acceleration of interaction.
             */
            public
            Motion.Acceleration getAcceleration();

            /**
             * Sets the acceleration of interaction.
             *
             * @param acceleration the acceleration.
             */
            public default
            void setAcceleration(
                final Motion.Acceleration acceleration
                ) {}
        }

        /**
         * {@code Alternative} classifies repetitive interactions with action types that alter pitch or affect quality of sound.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Alternative
        extends Performance
        {}

        /**
         * {@code Bilateral} classifies interactions with action types that are performed uniquely differently by either of the body orientations with one usually, but not necessarily, preferred over the other.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Bilateral
        extends Performance
        {}

        /**
         * {@code Continuous} classifies interactions for which action and/or reaction types are not negligible in time.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Continuous
        extends Performance
        {}

        /**
         * {@code Coordination} classifies the instrument's awareness of physical interactions.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public static abstract
        class Coordination
        extends Instruction
        {}

        /**
         * {@code Copitched} classifies interactions with effect types altering to a pitch that depends on the state of the instrument part or is in collaboration with another change target.
         * <p/>
         * This classifies the more complex interaction types of an instrument involving more than one part in order to produce a certain effect, or sonically depend on the state of another part or parts.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Copitched
        extends Performance
        {}

        /**
         * {@code Decaying} classifies interactions with reaction types that decrease in loudness after the action is completed.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Decaying
        extends Performance
        {}

        /**
         * {@code Directional} classifies interactions with directional action types.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Directional
        extends Performance
        {
            /**
             * Returns the direction of interaction.
             *
             * @return the direction of interaction.
             */
            public
            musical.performance.system.Type<Direction> getDirection();

            /**
             * Sets the direction of interaction.
             *
             * @param direction the direction.
             */
            public default
            void setDirection(
                musical.performance.system.Type<Direction> direction
                ) {}
        }

        /**
         * {@code Durational} classifies interactions with action types maintained over passage of time.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Durational
        extends Performance
        {
            /**
             * Gets the duration of interaction.
             *
             * @return the duration.
             */
            public
            music.system.Type<Duration> getDuration();

            /**
             * Sets the duration of interaction.
             *
             * @param duration the duration.
             */
            public default
            void setDuration(
                music.system.Type<Duration> duration
                ) {}
        }

        /**
         * {@code Dynamic} classifies interactions with reaction types that are associable with musical dynamics.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Dynamic
        extends Performance
        {
            /**
             * Returns the dynamics of interaction.
             *
             * @return the dynamics.
             */
            public
            music.system.Type<Dynamics> getDynamics();

            /**
             * Sets the dynamics of interaction.
             *
             * @param dynamics the dynamics.
             */
            public default
            void setDynamics(
                music.system.Type<Dynamics> dynamics
                ) {}
        }

        /**
         * {@code Gliding} classifies interactions with action types that produce glide effects, similar to that glissando.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Gliding
        extends Performance
        {
            /**
             * Returns the start part of interaction.
             *
             * @return the start part.
             */
            public
            Part getStartPart();

            /**
             * Sets the start part of interaction.
             *
             * @param startPart the start part.
             */
            public
            void setStartPart(
                Part startPart
                );

            /**
             * Returns the end part of interaction.
             *
             * @return the end part.
             */
            public
            Part getEndPart();

            /**
             * Sets the end part of interaction.
             *
             * @param endPart the end part.
             */
            public
            void setEndPart(
                Part endPart
                );
        }

        /**
         * {@code Gradual} classifies interactions with action types that vary in physical intensity producing gradual degrees in effect.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Gradual
        extends Performance
        {}

        /**
         * {@code Instantaneous} classifies all instantaneous interactions negligible in time.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public abstract
        class Instantaneous
        extends Physical<T>
        implements Negligible
        {
            /**
             * Creates an instantaneous interaction with the specified masculine and feminine parts.
             *
             * @param masculine the masculine part.
             * @param feminine the feminine part.
             */
            protected
            Instantaneous(
                final Part masculine,
                final Part feminine) {
                super(feminine, feminine);
            }
        }

        /**
         * {@code Intervallic} classifies interactions with effect types that alter pitch by a certain musical interval.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Intervallic
        extends Performance
        {
            /**
             * Returns the interval of interaction.
             *
             * @return the interval.
             */
            public
            music.system.Type<Interval> getInterval();

            /**
             * Sets the interval of interaction.
             *
             * @param interval the interval.
             */
            public default
            void setInterval(
                music.system.Type<Interval> interval
                ) {}
        }

        /**
         * {@code Lasting} classifies interactions with reaction types that independently last after the action is completed.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Lasting
        extends Performance
        {}

        /**
         * {@code Melodic} classifies interactions with effect types that produce melody in music by altering pitch of sound.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Melodic
        extends Performance
        {}

        /**
         * {@code Motion} classifies motion types for all physical interactions.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        enum Motion
        implements
            Production<Unit>,
            Snapshot,
            musical.performance.system.Type<Motion>,
            Unit
        {
            /** Simple motion. */
            Simple,

            /** Motion with a positive acceleration. */
            Accelerating,

            /** Motion with a constant acceleration. */
            Constant,

            /** Motion with a negative acceleration. */
            Decelerating,

            /** Motion with variable acceleration. */
            Variable,

            /** Motion with complex sub-motions. */
            Complex,

            /** Oscillatory motion. */
            Oscillation,

            /** Rotary motion. */
            Rotation,

            /** Transitional motion. */
            Transition,

            /** Vibratory motion. */
            Vibration,

            /** Pressure or press motion. */
            Pressure,

            /** Depression or depress motion. */
            Depression,

            /** Relaxation or release motion. */
            Relaxation,

            /** Full motion. (continuous or instantaneous pressure and relaxation) */
            Full,

            /** Touch motion. (subtle pressure and depression) */
            Touch,

            /** Impact motion. (intense continuous pressure) */
            Impact,

            /** Collision motion. (intense pressure and depression/relaxation) */
            Collision,

            /** Pull motion. (continuous pressure) */
            Pull,

            /** Push motion. (continuous depression) */
            Push,

            /** Undefined motion. */
            Undefined;

            @Override
            public Object apply(ExecutionModel model, musical.performance.Unit part) { return null; }

            /**
             * Returns true if the specified motion type is of this type, and false otherwise.
             *
             * @param type the other type.
             * @return true if the specified motion type is of this type, and false otherwise.
             */
            @Override
            public boolean is(final Type<? super Motion> type) {
                return type == this;
            }

            /**
             * {@code Acceleration} classifies motion acceleration types.
             * <p/>
             * This class implementation is in progress.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public
            enum Acceleration
            implements
                Production<Motion>,
                Snapshot,
                musical.performance.system.Type<Acceleration>,
                Unit
            {
                /** Constant acceleration. */
                Constant,

                /** Velocity increasing acceleration. */
                Increasing,

                /** Velocity decreasing acceleration. */
                Decreasing,

                /** Variable acceleration. */
                Variable,

                /** Centrifugal acceleration. */
                Centrifugal,

                /** Centripetal acceleration. */
                Centripetal;

                @Override
                public Object apply(ExecutionModel model, Motion motion) { return null; }

                @Override
                public boolean is(final Type<? super Acceleration> type) {
                    // Accelerations are not associated with another type
                    return false;
                }

            }
        }

        /**
         * {@code Negligible} classifies interactions that are free of time.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Negligible
        extends Performance
        {}

        /**
         * {@code NonAccelerational} classifies interactions with action types that can be performed only with constant speed or repetition rate.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface NonAccelerational
        extends Performance
        {}

        /**
         * {@code Performance} is the super-type for all interaction type classifiers.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Performance
        extends
            DataPoint<Action, Effect, Reaction>,
            Snapshot,
            musical.performance.system.Type<Performance>
        {
            /**
             * Returns the action types associated with this performance identifier.
             *
             * @return the action types.
             */
            musical.performance.system.Type<Action> getAction();

            /**
             * Returns the effect types associated with this performance identifier.
             *
             * @return the effect types.
             */
            musical.performance.system.Type<Effect> getEffect();

            /**
             * Returns the reaction types associated with this performance identifier.
             *
             * @return the reaction types.
             */
            musical.performance.system.Type<Reaction> getReaction();

            /**
             * {@code Style} classifies a music performance style.
             * <p/>
             * This class implementation is in progress.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Style<T extends musical.performance.system.Type<Performance>>
            extends
                Description<T>,
                musical.performance.system.Type<Performance>
            {
                /**
                 * {@code Music} classifies music styles that are formed purely based on variations in performance.
                 * <p/>
                 * This class implementation is in progress.
                 *
                 * @since 1.8
                 * @author Alireza Kamran
                 */
                interface Music
                extends
                    Style<Music>,
                    musical.performance.system.Type<Performance>
                {}
            }
        }

        /**
         * {@code Pitched} classifies interactions with effect types that independently alter pitch of sound on that instrument.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Pitched
        extends Performance
        {}

        /**
         * {@code Positional} classifies interactions with action types requiring the body part to be in a certain position, or range, relative to instrument parts.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Positional
        extends Performance
        {}

        /**
         * {@code Productive} classifies unitary interactions with effect types that produce new sound and excite the aural field.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Productive
        extends Performance
        {}

        /**
         * {@code Reductive} classifies interactions with effect types that reduce active sound and quiet the aural field.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Reductive
        extends Performance
        {}

        /**
         * {@code Repetitive} classifies interactions with repetitive action, reaction, and/or effect types.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Repetitive
        extends Performance
        {
            /**
             * Returns the repetition.
             *
             * @return the repetition.
             */
            public
            Repetition getRepetition();

            /**
             * Sets the repetition.
             *
             * @param repetition the repetition.
             */
            public default
            void setRepeition(
                Repetition repetition
                ) {}
        }

        /**
         * {@code Secondary} classifies interactions with action types affecting the aural field only when instrument part produces, or is producing, a unitary change and not otherwise.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Secondary
        extends Performance
        {}

        /**
         * {@code Subtle} classifies interactions with effect types that alter pitch only when the instrument part or its larger containing body is in a productive state that doesn't end by the effect of this interaction.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Subtle
        extends Performance
        {}

        /**
         * {@code Sustained} classifies interactions with reaction types that diminish as soon as the action stops or is completed.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Sustained
        extends Performance
        {}

        /**
         * {@code Tertiary} classifies interactions with action types that do not affect the aural field in any way.
         * <p/>
         * Subclasses of this interface are the silent interactions with actions intended as preparation for another change.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Tertiary
        extends Performance
        {}

        /**
         * {@code Tonal} classifies interactions with effect types altering the active tone quality of sound.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Tonal
        extends Performance
        {}

        /**
         * {@code Undetermined} classifies interactions with action types that alter pitch in an undetermined manner or are not intended to have an exact pitch.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Undetermined
        extends Performance
        {}

        /**
         * {@code Unilateral} classifies interactions with action types that are performed only by one of the body orientations.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Unilateral
        extends Performance
        {}

        /**
         * {@code Unitary} classifies interactions with effect types uniquely and authoritatively affecting the aural field in a way that no other unitary change on the same instrument part can coexist with.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Unitary
        extends Performance
        {}

        /**
         * {@code Unpitched} classifies interactions with effect types that do not alter pitch of sound by themselves alone, or the pitch is determined by another change or instrument part state.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Unpitched
        extends Performance
        {}

        /**
         * {@code Variational} classifies interactions with action types that vary in physical performance characteristics.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Variational
        extends Performance
        {}

        /**
         * {@code Vibrational} classifies interactions with effect types that vibrate pitch or quality of sound.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Vibrational
        extends Performance
        {}

        /**
         * {@code Form} identifies additive interaction class compositions by name.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Retention(RUNTIME)
        @Target({
            ANNOTATION_TYPE,
            METHOD,
            TYPE
            })
        public
        @interface Form
        {
            /** The form name. */
            String name()
            default "";

            /** The form composition. */
            Class<? super Null>[] composition()
            default Null.class;
        }
    }
}