package performers;

import static performance.Constant.PianoPlayer.*;

import java.util.Arrays;
import java.util.Collection;

import instruments.Piano;
import musical.Score;
import musical.Score.Interpreter;
import performance.Action;
import performance.Change;
import performance.Effect;
import performance.Reaction;
import performance.Interaction.Physical;
import performance.Interaction.Physical.Dynamic;

/**
 * {@code PianoPlayer} is a representation of a piano player.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class PianoPlayer
extends KeyboardPlayer<Piano>
{
    /**
     * Creates a piano player with the specified body parts, piano instrument, and interpreter.
     *
     * @param bodyParts the body parts collection.
     * @param instrument the piano instrument.
     * @param interpreter the interpreter.
     */
    protected
    PianoPlayer(
        final Collection<Class<? extends BodyPart>> bodyParts,
        final Piano instrument,
        final Interpreter interpreter
        ) {
        super(bodyParts, instrument, interpreter);
    }

    /**
     * Creates a default piano player with the specified piano instrument and interpreter.
     * <p>
     * This implementation generates the performer hands and toes.
     *
     * @param instrument the piano instrument.
     * @param interpreter the interpreter.
     */
    protected
    PianoPlayer(
        final Piano instrument,
        final Score.Interpreter interpreter
        ) {
        this(Arrays.asList(Hands.class, Toes.class), instrument, interpreter);
    }

    /**
     * Groups both-oriented interaction classes for all piano players.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class Both
    extends KeyboardPlayer<?>.Both
    {
        /**
         * {@code Release} represents partially releasing a finger off of a key without silencing the sound.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = HoldReleaseName,
            description = HoldReleaseDesc,

            effect = {
                Effect.Unitary,
                Effect.Pitched
                },

            action = {
                Action.Instantaneous,
                Action.Positional
                },

            reaction = {
                Reaction.Lasting
                }
            )
        protected abstract
        class HoldRelease
        extends Physical<PianoPlayer>
        {
            /**
             * Creates a hold release interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            HoldRelease(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * {@code PedalLock} represents locking a pedal.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = PedalLockName,
            description = PedalLockDesc,

            effect = {
                Effect.Tertiary
                },

            action = {
                Action.Instantaneous,
                Action.Positional
                },

            reaction = {
                Reaction.Lasting
                }
            )
        protected abstract
        class PedalLock
        extends Physical<PianoPlayer>
        {
            /**
             * Creates a pedal lock interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            PedalLock(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * {@code PedalPress} represents pressing a pedal.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = PedalPressName,
            description = PedalPressDesc,

            effect = {
                Effect.Secondary,
                Effect.Tonal
                },

            action = {
                Action.Continuous,
                Action.Positional
                },

            reaction = {
                Reaction.Sustained
                }
            )
        protected abstract
        class PedalPress
        extends Physical<PianoPlayer>
        {
            /**
             * Creates a pedal press interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            PedalPress(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * {@code PedalRelease} represents releasing a pedal.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = PedalReleaseName,
            description = PedalReleaseDesc,

            effect = {
                Effect.Secondary
                },

            action = {
                Action.Instantaneous,
                Action.Positional
                },

            reaction = {
                Reaction.Lasting
                }
            )
        protected abstract
        class PedalRelease
        extends Physical<PianoPlayer>
        {
            /**
             * Creates a pedal release interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            PedalRelease(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * {@code PedalUnlock} represents unlocking a pedal.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = PedalUnlockName,
            description = PedalUnlockDesc,

            effect = {
                Effect.Tertiary
                },

            action = {
                Action.Instantaneous,
                Action.Positional
                },

            reaction = {
                Reaction.Lasting
                }
            )
        protected abstract
        class PedalUnlock
        extends Physical<PianoPlayer>
        {
            /**
             * Creates a pedal unlock interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            PedalUnlock(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * {@code Pluck} represents plucking the piano string with a finger.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = PluckName,
            description = PluckDesc,

            effect = {
                Effect.Productive,
                Effect.Pitched
                },

            action = {
                Action.Instantaneous,
                Action.Gradual,
                Action.Positional
                },

            reaction = {
                Reaction.Lasting,
                Reaction.Decaying
                }
            )
        protected abstract
        class Pluck
        extends Physical<PianoPlayer>
        implements Dynamic
        {
            /**
             * Creates a pluck interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            Pluck(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }
    }

    /**
     * {@code ChangeGraph} is a representation of piano player's performance knowledge.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class ChangeGraph
    extends KeyboardPlayer<?>.ChangeGraph
    {}
}