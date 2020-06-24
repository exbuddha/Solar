package performers;

import static performance.Constant.ElectricGuitarPlayer.*;

import java.util.Arrays;
import java.util.Collection;

import instruments.ElectricGuitar;
import musical.Score;
import musical.Score.Interpreter;
import performance.Action;
import performance.Change;
import performance.Effect;
import performance.Production;
import performance.Reaction;
import performance.Interaction.Physical;
import performance.Interaction.Physical.Intervallic;

/**
 * {@code ElectricGuitarPlayer} is a representation of an electric guitar player.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class ElectricGuitarPlayer
extends GuitarPlayer<ElectricGuitar>
{
    /**
     * Creates an electric guitar player with the specified body parts, electric guitar instrument, and interpreter.
     *
     * @param bodyParts the body parts collection.
     * @param instrument the electric guitar instrument.
     * @param interpreter the interpreter.
     */
    protected
    ElectricGuitarPlayer(
        final Collection<Class<? extends BodyPart>> bodyParts,
        final ElectricGuitar instrument,
        final Interpreter interpreter
        ) {
        super(bodyParts, instrument, interpreter);
    }

    /**
     * Creates a default electric guitar player with the specified electric guitar instrument and interpreter.
     * <p>
     * This implementation generates the performer hands.
     *
     * @param instrument the electric guitar instrument.
     * @param interpreter the interpreter.
     */
    protected
    ElectricGuitarPlayer(
        final ElectricGuitar instrument,
        final Score.Interpreter interpreter
        ) {
        this(Arrays.asList(Hands.class), instrument, interpreter);
    }

    /**
     * Groups right-oriented interaction classes for all electric guitar players.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Right
    extends GuitarPlayer<?>.Right
    {
        /**
         * {@code WhammyPull} represents pulling the whammy bar up to raise the guitar pitch.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = WhammyPullName,
            description = WhammyPullDesc,

            effect = {
                Effect.Secondary,
                Effect.Glide
                },

            action = {
                Action.Bilateral,
                Action.Accelerational,
                Action.Positional
                },

            reaction = {
                Reaction.Sustained
                }
            )
        protected abstract
        class WhammyPull
        extends Physical<ElectricGuitarPlayer>
        implements
            Intervallic,
            Production<ElectricGuitar>
        {
            /**
             * Creates a whammy pull interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            WhammyPull(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * {@code WhammyPush} represents pushing the whammy bar down to lower the guitar pitch.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = WhammyPushName,
            description = WhammyPushDesc,

            effect = {
                Effect.Secondary,
                Effect.Glide
                },

            action = {
                Action.Bilateral,
                Action.Accelerational,
                Action.Positional
                },

            reaction = {
                Reaction.Sustained
                }
            )
        protected abstract
        class WhammyPush
        extends Physical<ElectricGuitarPlayer>
        implements
            Intervallic,
            Production<ElectricGuitar>
        {
            /**
             * Creates a whammy push interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            WhammyPush(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * {@code WhammyRelease} represents releasing the whammy bar to its original state.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = WhammyReleaseName,
            description = WhammyReleaseDesc,

            effect = {
                Effect.Secondary,
                Effect.Glide
                },

            action = {
                Action.Bilateral,
                Action.Accelerational,
                Action.Positional
                },

            reaction = {
                Reaction.Lasting
                }
            )
        protected abstract
        class WhammyRelease
        extends Physical<ElectricGuitarPlayer>
        implements
            Intervallic,
            Production<ElectricGuitar>
        {
            /**
             * Creates a whammy release interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            WhammyRelease(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * {@code WhammyVibrato} represents vibrating the whammy bar.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = WhammyVibratoName,
            description = WhammyVibratoDesc,

            effect = {
                Effect.Secondary,
                Effect.Vibrational
                },

            action = {
                Action.Repetitive,
                Action.Bilateral,
                Action.Gradual,
                Action.Accelerational,
                Action.Positional
                },

            reaction = {
                Reaction.Repetitive
                }
            )
        protected abstract
        class WhammyVibrato
        extends Physical<ElectricGuitarPlayer>
        implements
            Intervallic,
            Production<ElectricGuitar>
        {
            /**
             * Creates a whammy vibrato interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            WhammyVibrato(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }
    }

    /**
     * {@code ChangeGraph} is a representation of electric guitar player's performance knowledge.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class ChangeGraph
    extends GuitarPlayer<?>.ChangeGraph
    {}
}