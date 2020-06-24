package performers;

import static performance.Constant.KeyboardPlayer.*;

import java.util.Arrays;
import java.util.Collection;

import instruments.Keyboard;
import musical.Score;
import musical.Score.Interpreter;
import performance.Action;
import performance.Change;
import performance.Effect;
import performance.Performer;
import performance.Production;
import performance.Reaction;
import performance.Interaction.Physical;
import performance.Interaction.Physical.Dynamic;
import performance.Interaction.Physical.Gliding;
import performance.Interaction.Physical.Repetitive;

/**
 * {@code KeyboardPlayer} is a representation of a performer who has knowledge of performing a musical instrument played with keyboard.
 * <p>
 * This class implementation is in progress.
 *
 * @param <I> the instrument type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class KeyboardPlayer<I extends Keyboard>
extends Performer<I>
{
    /**
     * Creates a keyboard player with the specified body parts, keyboard instrument, and interpreter.
     *
     * @param bodyParts the body parts collection.
     * @param instrument the keyboard instrument.
     * @param interpreter the interpreter.
     */
    protected
    KeyboardPlayer(
        final Collection<Class<? extends BodyPart>> bodyParts,
        final I instrument,
        final Interpreter interpreter
        ) {
        super(bodyParts, instrument, interpreter);
    }

    /**
     * Creates a default keyboard player with the specified keyboard instrument and interpreter.
     * <p>
     * This implementation generates the performer hands.
     *
     * @param keyboard the keyboard instrument.
     * @param interpreter the interpreter.
     */
    protected
    KeyboardPlayer(
        final I keyboard,
        final Score.Interpreter interpreter
        ) {
        this(Arrays.asList(Hands.class), keyboard, interpreter);
    }

    /**
     * Groups both-oriented interaction classes for all keyboard players.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class Both
    {
        /**
         * {@code Position} represents moving a hand to a new position.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = PositionName,
            description = PositionDesc,

            effect = {
                Effect.Tertiary
                },

            action = {
                Action.Continuous
                },

            reaction = {
                Reaction.Lasting
                }
            )
        protected abstract
        class Position
        extends Physical<PianoPlayer>
        implements
            Gliding,
            Production<Keyboard>
        {
            /**
             * Creates a position interaction with the specified body and instrument parts.
             *
             * @param masculine the masculine part.
             * @param feminine the feminine part.
             */
            protected
            Position(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * {@code Press} represents pressing a key.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = PressName,
            description = PressDesc,

            effect = {
                Effect.Productive,
                Effect.Pitched
                },

            action = {
                Action.Continuous,
                Action.Gradual,
                Action.Positional
                },

            reaction = {
                Reaction.Sustained,
                Reaction.Decaying
                }
            )
        protected abstract
        class Press
        extends Physical<PianoPlayer>
        implements Dynamic
        {
            /**
             * Creates a press interaction with the specified body and instrument parts.
             *
             * @param masculine the masculine part.
             * @param feminine the feminine part.
             */
            protected
            Press(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * {@code Release} represents quietly releasing a finger off of a key.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = ReleaseName,
            description = ReleaseDesc,

            effect = {
                Effect.Unitary,
                Effect.Reductive,
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
        class Release
        extends Physical<PianoPlayer>
        {
            /**
             * Creates a release interaction with the specified body and instrument parts.
             *
             * @param masculine the masculine part.
             * @param feminine the feminine part.
             */
            protected
            Release(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * {@code Slide} represents gliding a finger that is pressing a key to another key.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = SlideName,
            description = SlideDesc,

            effect = {
                Effect.Productive,
                Effect.Glide
                },

            action = {
                Action.Continuous,
                Action.Gradual,
                Action.Accelerational,
                Action.Positional
                },

            reaction = {
                Reaction.Sustained
                }
            )
        protected abstract
        class Slide
        extends Physical<PianoPlayer>
        implements Gliding
        {
            /**
             * Creates a slide interaction with the specified body and instrument parts.
             *
             * @param masculine the masculine part.
             * @param feminine the feminine part.
             */
            protected
            Slide(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * {@code Trill} represents pressing and releasing a key repetitively with a finger.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = TrillName,
            description = TrillDesc,

            effect = {
                Effect.Productive,
                Effect.Pitched,
                Effect.Alternative
                },

            action = {
                Action.Repetitive,
                Action.Gradual,
                Action.Accelerational,
                Action.Positional
                },

            reaction = {
                Reaction.Repetitive
                }
            )
        protected abstract
        class Trill
        extends Physical<PianoPlayer>
        implements Repetitive
        {
            /**
             * Creates a trill interaction with the specified body and instrument parts.
             *
             * @param masculine the masculine part.
             * @param feminine the feminine part.
             */
            protected
            Trill(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }
    }

    /**
     * {@code ChangeGraph} is a representation of keyboard player's performance knowledge.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class ChangeGraph
    extends Performer.ChangeGraph
    {}
}