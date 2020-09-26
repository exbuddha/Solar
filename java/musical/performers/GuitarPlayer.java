package musical.performers;

import static musical.performance.Constant.GuitarPlayer.*;

import java.util.Arrays;
import java.util.Collection;

import musical.instruments.Guitar;
import musical.Score;
import musical.Score.Interpreter;
import musical.performance.Action;
import musical.performance.Change;
import musical.performance.Effect;
import musical.performance.Reaction;
import musical.performance.Interaction.Physical;
import musical.performance.Interaction.Physical.Directional;
import musical.performance.Interaction.Physical.Dynamic;
import musical.performance.Interaction.Physical.Gliding;
import musical.performance.Interaction.Physical.Intervallic;
import musical.performance.Interaction.Physical.Repetitive;

/**
 * {@code GuitarPlayer} is a representation of a guitar player.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class GuitarPlayer<I extends Guitar>
extends LutePlayer<I>
{
    /**
     * Creates a guitar player with the specified body parts, guitar instrument, and interpreter.
     *
     * @param bodyParts the body parts collection.
     * @param instrument the guitar instrument.
     * @param interpreter the interpreter.
     */
    protected
    GuitarPlayer(
        final Collection<Class<? extends BodyPart>> bodyParts,
        final I instrument,
        final Interpreter interpreter
        ) {
        super(bodyParts, instrument, interpreter);
    }

    /**
     * Creates a default guitar player with the specified guitar instrument and interpreter.
     * <p/>
     * This implementation generates the performer hands.
     *
     * @param instrument the guitar instrument.
     * @param interpreter the interpreter.
     */
    protected
    GuitarPlayer(
        final I instrument,
        final Score.Interpreter interpreter
        ) {
        this(Arrays.asList(Hands.class), instrument, interpreter);
    }

    /**
     * Groups both-oriented interaction classes for all guitar players.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class Both
    {
        /**
         * {@code HammerOn} represents hammering a finger on a fret point.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = HammerOnName,
            description = HammerOnDesc,

            effect = {
                Effect.Productive,
                Effect.Pitched
                },

            action = {
                Action.Continuous,
                Action.Bilateral,
                Action.Gradual,
                Action.Positional
                },

            reaction = {
                Reaction.Sustained,
                Reaction.Decaying
                }
        )
        protected abstract
        class HammerOn
        extends Physical<GuitarPlayer<Guitar>>
        implements Dynamic
        {
            /**
             * Creates a hammer-on interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            HammerOn(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * {@code Mute} represents touching a string lightly to stop the vibration.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = MuteName,
            description =  MuteDesc,

            effect = {
                Effect.Unitary,
                Effect.Reductive
                },

            action = {
                Action.Continuous,
                Action.Bilateral
                },

            reaction = {
                Reaction.Lasting
                }
        )
        protected abstract
        class Mute
        extends Physical<GuitarPlayer<Guitar>>
        {
            /**
             * Creates a mute interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            Mute(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * {@code Pluck} represents plucking the string with a finger or the pick.
         * <p/>
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
                Effect.Unpitched
                },

            action = {
                Action.Instantaneous,
                Action.Bilateral,
                Action.Directional,
                Action.Gradual
                },

            reaction = {
                Reaction.Lasting,
                Reaction.Decaying
                }
        )
        protected abstract
        class Pluck
        extends Physical<GuitarPlayer<Guitar>>
        implements Directional
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

        /**
         * {@code Position} represents moving a hand to a new position.
         * <p/>
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
                Action.Continuous,
                Action.Bilateral
                },

            reaction = {
                Reaction.Lasting
                }
        )
        protected abstract
        class Position
        extends Physical<GuitarPlayer<Guitar>>
        implements Gliding
        {
            /**
             * Creates a position interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
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
         * {@code Press} represents pressing a fret point.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = PressName,
            description = PressDesc,

            effect = {
                Effect.Secondary,
                Effect.Pitched
                },

            action = {
                Action.Continuous,
                Action.Bilateral,
                Action.Positional
                },

            reaction = {
                Reaction.Sustained
                }
        )
        protected abstract
        class Press
        extends Physical<GuitarPlayer<Guitar>>
        {
            /**
             * Creates a press interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
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
         * {@code PullOff} represents pulling a finger off of a fret point.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = PullOffName,
            description = PullOffDesc,

            effect = {
                Effect.Productive,
                Effect.Pitched
                },

            action = {
                Action.Instantaneous,
                Action.Bilateral,
                Action.Gradual,
                Action.Positional
                },

            reaction = {
                Reaction.Lasting,
                Reaction.Decaying
                }
        )
        protected abstract
        class PullOff
        extends Physical<GuitarPlayer<Guitar>>
        {
            /**
             * Creates a pull-off interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            PullOff(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * {@code Release} represents quietly releasing a finger off of a fret point.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = ReleaseName,
            description = ReleaseDesc,

            effect = {
                Effect.Secondary,
                Effect.Reductive,
                Effect.Subtle,
                Effect.Tertiary
                },

            action = {
                Action.Instantaneous,
                Action.Bilateral
                },

            reaction = {
                Reaction.Lasting
                }
        )
        protected abstract
        class Release
        extends Physical<GuitarPlayer<Guitar>>
        {
            /**
             * Creates a release interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
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
         * {@code Slide} represents gliding a finger that is pressing a fret point to another fret point.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = SlideName,
            description = SlideDesc,

            effect = {
                Effect.Secondary,
                Effect.Glide
                },

            action = {
                Action.Continuous,
                Action.Bilateral,
                Action.Accelerational,
                Action.Positional
                },

            reaction = {
                Reaction.Sustained
                }
        )
        protected abstract
        class Slide
        extends Physical<GuitarPlayer<Guitar>>
        implements Gliding
        {
            /**
             * Creates a slide interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
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
         * {@code Trill} represents hammering on and pulling off of a fret point repetitively with a finger.
         * <p/>
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
                Action.Bilateral,
                Action.Gradual,
                Action.Accelerational
                },

            reaction = {
                Reaction.Repetitive
                }
        )
        protected abstract
        class Trill
        extends Physical<GuitarPlayer<Guitar>>
        implements Repetitive
        {
            /**
             * Creates a trill interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            Trill(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * {@code Vibrato} represents vibrating a finger that is pressing a fret point.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = VibratoName,
            description = VibratoDesc,

            effect = {
                Effect.Secondary,
                Effect.Vibrational
                },

            action = {
                Action.Repetitive,
                Action.Bilateral,
                Action.Gradual,
                Action.Accelerational
                },

            reaction = {
                Reaction.Repetitive
                }
        )
        protected abstract
        class Vibrato
        extends Physical<GuitarPlayer<Guitar>>
        implements Repetitive
        {
            /**
             * Creates a vibrato interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            Vibrato(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }
    }

    /**
     * {@code ChangeGraph} is a representation of guitar player's performance knowledge.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class ChangeGraph
    extends LutePlayer<?>.ChangeGraph
    {}

    /**
     * Groups left-oriented interaction classes for all guitar players.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class Left
    {
        /**
         * Bar represents the bar technique.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = BarName,
            description = BarDesc,

            effect = {
                Effect.Secondary,
                Effect.Pitched
                },

            action = {
                Action.Continuous,
                Action.Unilateral,
                Action.Positional
                },

            reaction = {
                Reaction.Sustained
                }
            )
        protected abstract
        class Bar
        extends Physical<GuitarPlayer<Guitar>>
        {
            /**
             * Creates a bar interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            Bar(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * BarRelease represents quietly releasing the bar or the slide off of the strings.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = BarReleaseName,
            description = BarReleaseDesc,

            effect = {
                Effect.Secondary,
                Effect.Reductive,
                Effect.Subtle,
                Effect.Tertiary
                },

            action = {
                Action.Instantaneous,
                Action.Unilateral,
                Action.Positional
                },

            reaction = {
                Reaction.Lasting
                }
            )
        protected abstract
        class BarRelease
        extends Physical<GuitarPlayer<Guitar>>
        {
            /**
             * Creates a bar release interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            BarRelease(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * BarSlide represents moving the bar or the slide from a fret point to another fret point.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = BarSlideName,
            description = BarSlideDesc,

            effect = {
                Effect.Secondary,
                Effect.Glide
                },

            action = {
                Action.Continuous,
                Action.Unilateral,
                Action.Accelerational,
                Action.Positional
                },

            reaction = {
                Reaction.Sustained
                }
            )
        protected abstract
        class BarSlide
        extends Physical<GuitarPlayer<Guitar>>
        implements Gliding
        {
            /**
             * Creates a bar slide interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            BarSlide(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * BarVibrato represents vibrating the bar or the slide on the strings.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = BarVibratoName,
            description = BarVibratoDesc,

            effect = {
                Effect.Secondary,
                Effect.Vibrational
                },

            action = {
                Action.Repetitive,
                Action.Unilateral,
                Action.Gradual,
                Action.Accelerational
                },

            reaction = {
                Reaction.Repetitive
                }
            )
        protected abstract
        class BarVibrato
        extends Physical<GuitarPlayer<Guitar>>
        implements Repetitive
        {
            /**
             * Creates a bar vibrato interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            BarVibrato(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * Bend represents the bend technique.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = BendName,
            description = BendDesc,

            effect = {
                Effect.Secondary,
                Effect.Glide
                },

            action = {
                Action.Continuous,
                Action.Unilateral,
                Action.Directional,
                Action.Accelerational,
                Action.Positional
                },

            reaction = {
                Reaction.Sustained
                }
            )
        protected abstract
        class Bend
        extends Physical<GuitarPlayer<Guitar>>
        implements Intervallic
        {
            /**
             * Creates a bend interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            Bend(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * BendRelease represents the bend release technique.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = BendReleaseName,
            description = BendReleaseDesc,

            effect = {
                Effect.Secondary,
                Effect.Glide
                },

            action = {
                Action.Continuous,
                Action.Unilateral,
                Action.Directional,
                Action.Accelerational,
                Action.Positional
                },

            reaction = {
                Reaction.Sustained
                }
            )
        protected abstract
        class BendRelease
        extends Physical<GuitarPlayer<Guitar>>
        implements Intervallic
        {
            /**
             * Creates a bend release interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            BendRelease(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * Harmonic represents lightly touching a node point for the natural or artificial harmonic technique with a finger or the bar.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = HarmonicName,
            description = HarmonicDesc,

            effect = {
                Effect.Secondary,
                Effect.Unpitched,
                Effect.Tonal
                },

            action = {
                Action.Continuous,
                Action.Unilateral,
                Action.Positional
                },

            reaction = {
                Reaction.Sustained
                }
            )
        protected abstract
        class Harmonic
        extends Physical<GuitarPlayer<Guitar>>
        {
            /**
             * Creates a harmonic interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            Harmonic(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * SlideTap represents lightly touching the string with the slide to make it vibrate.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = SlideTapName,
            description = SlideTapDesc,

            effect = {
                Effect.Productive,
                Effect.Pitched
                },

            action = {
                Action.Instantaneous,
                Action.Unilateral,
                Action.Positional
                },

            reaction = {
                Reaction.Sustained
                }
            )
        protected abstract
        class SlideTap
        extends Physical<GuitarPlayer<Guitar>>
        implements Dynamic
        {
            /**
             * Creates a slide tap interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            SlideTap(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * SlideTouch represents lightly touching the strings with the slide.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = SlideTouchName,
            description = SlideTouchDesc,

            effect = {
                Effect.Secondary,
                Effect.Unpitched
                },

            action = {
                Action.Instantaneous,
                Action.Unilateral,
                Action.Positional
                },

            reaction = {
                Reaction.Sustained
                }
            )
        protected abstract
        class SlideTouch
        extends Physical<GuitarPlayer<Guitar>>
        {
            /**
             * Creates a slide touch interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            SlideTouch(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }
    }

    /**
     * Groups right-oriented interaction classes for all guitar players.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class Right
    extends LutePlayer<?>.Right
    {
        /**
         * PalmMute represents the palm mute technique.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = PalmMuteName,
            description = PalmMuteDesc,

            effect = {
                Effect.Secondary,
                Effect.Tonal
                },

            action = {
                Action.Continuous,
                Action.Unilateral,
                Action.Positional
                },

            reaction = {
                Reaction.Sustained
                }
            )
        protected abstract
        class PalmMute
        extends Physical<GuitarPlayer<Guitar>>
        {
            /**
             * Creates a palm mute interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            PalmMute(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * PalmRelease represents releasing the palm from the strings.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = PalmReleaseName,
            description = PalmReleaseDesc,

            effect = {
                Effect.Secondary
                },

            action = {
                Action.Instantaneous,
                Action.Unilateral,
                Action.Positional
                },

            reaction = {
                Reaction.Lasting
                }
            )
        protected abstract
        class PalmRelease
        extends Physical<GuitarPlayer<Guitar>>
        {
            /**
             * Creates a palm release interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            PalmRelease(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * Pick represents plucking the string with the pick.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = PickName,
            description = PickDesc,

            effect = {
                Effect.Productive,
                Effect.Unpitched
                },

            action = {
                Action.Instantaneous,
                Action.Unilateral,
                Action.Directional,
                Action.Gradual
                },

            reaction = {
                Reaction.Lasting,
                Reaction.Decaying
                }
            )
        protected abstract
        class Pick
        extends Physical<GuitarPlayer<Guitar>>
        implements Directional
        {
            /**
             * Creates a pick interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            Pick(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * PickSlide represents the pick slide technique.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = PickSlideName,
            description = PickSlideDesc,

            effect = {
                Effect.Productive,
                Effect.Copitched,
                Effect.Glide,
                Effect.Undetermined,
                Effect.Tonal
                },

            action = {
                Action.Unilateral,
                Action.Accelerational,
                Action.Positional
            },

            reaction = {
                Reaction.Sustained
                }
            )
        protected abstract
        class PickSlide
        extends Physical<GuitarPlayer<Guitar>>
        implements Gliding
        {
            /**
             * Creates a pick slide interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            PickSlide(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * PinchHarmonic represents plucking the string and immediately touching a node point.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = PinchHarmonicName,
            description = PinchHarmonicDesc,

            effect = {
                Effect.Productive,
                Effect.Copitched,
                Effect.Tonal
                },

            action = {
                Action.Instantaneous,
                Action.Unilateral,
                Action.Directional,
                Action.Gradual,
                Action.Positional
                },

            reaction = {
                Reaction.Lasting,
                Reaction.Decaying
                }
            )
        protected abstract
        class PinchHarmonic
        extends Physical<GuitarPlayer<Guitar>>
        implements Directional
        {
            /**
             * Creates a pinch harmonic interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            PinchHarmonic(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * TapHarmonic represents the tap harmonic technique.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = TapHarmonicName,
            description = TapHarmonicDesc,

            effect = {
                Effect.Productive,
                Effect.Copitched,
                Effect.Tonal
                },

            action = {
                Action.Instantaneous,
                Action.Unilateral,
                Action.Positional
                },

            reaction = {
                Reaction.Lasting,
                Reaction.Decaying
                }
            )
        protected abstract
        class TapHarmonic
        extends Physical<GuitarPlayer<Guitar>>
        {
            /**
             * Creates a tap harmonic interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            TapHarmonic(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }

        /**
         * Tremolo represents repetitively plucking a string with the pick.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Change(
            name = TremoloName,
            description = TremoloDesc,

            effect = {
                Effect.Productive,
                Effect.Repetitive
                },

            action = {
                Action.Repetitive,
                Action.Unilateral,
                Action.Gradual,
                Action.Accelerational
                },

            reaction = {
                Reaction.Repetitive
                }
            )
        protected abstract
        class Tremolo
        extends Physical<GuitarPlayer<Guitar>>
        implements Repetitive
        {
            /**
             * Creates a tremolo interaction with the specified body and instrument parts.
             *
             * @param masculine the body part.
             * @param feminine the instrument part.
             */
            protected
            Tremolo(
                final Part masculine,
                final Part feminine
                ) {
                super(masculine, feminine);
            }
        }
    }
}