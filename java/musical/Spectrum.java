package musical;

import music.system.data.Ordered;
import music.system.data.Clockable.Elementary;
import music.system.data.Clockable.Progressor;
import music.system.data.Clockable.Regressor;
import music.system.data.Clockable.Rotational;
import music.system.data.Clockable.Sinusoidal;

/**
 * {@code Spectrum} characterizes any sonic data type that is within the audible frequency ranges of sound or the hearing scale.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
interface Spectrum
extends Sound
{
    /**
     * {@code Effect} categorizes sonic or musical characteristic of spectrums, notes, and groups.
     * <p>
     * This categorization intends to define a handful of characteristics that relate to time-sensitivity of individual incidents within their own maintained line of thought.
     * It focuses only on influence of the recognized timbres and tones present in the line of thought during listening.
     * There are additional points of interest specifically related to the perception of spectrums that will have to accompany the data represented here in order to completely allow for analysis of musical incidents, such as pitch rises and falls or changes in tempo.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    enum Effect
    implements music.system.Type<Effect>
    {
        /** Arpeggiate effect, similar to trill and arpeggio. (pitch) */
        Arpeggiate,

        /** Decay effect, similar to sustain ending abruptly. (time) */
        Decay,

        /** Fade effect, similar to crescendo and diminuendo. (volume) */
        Fade,

        /** Full effect, similar to note beginning. (time) */
        Full,

        /** Attack effect, similar to grace note. (time) */
        Attack,

        /** Modulate effect, similar to vibrato or band equalization. (timbre) */
        Modulate,

        /** Partial effect, similar to leading or trailing partial note. (time) */
        Partial,

        /** Release effect, similar to note ending. (time) */
        Release,

        /** Slide effect, similar to glissando. (pitch) */
        Slide,

        /** Sustain effect, similar to full sustain. (time) */
        Sustain,

        /** Transient effect, similar to click and noise clip. (time) */
        Transient;

        @Override
        public boolean is(final system.data.Type<? extends Effect> type) {
            return this == type;
        }
    }

    /**
     * {@code Frequency} is the standard unit of hearing scale used in spectrum localization.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Frequency
    extends
        Ordered<Float>,
        Unit
    {}

    /**
     * {@code Localization} classifies any form of data type that characterizes tone quality of sounds by scientifically recording properties of the spectrum of those sounds.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Localization
    {}

    /**
     * {@code Modulus} classifies the significant weights of all notes with two axes: a tabular (pitch-and-octave) and a local (accidental).
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Modulus
    extends Regressor
    {
        /**
         * {@code Local} classifies the axis of note modifiers local to the data points on the tabular axis.
         * <p>
         * This class implementation is in progress.
         *
         * @param <T> the numeric data type.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Local<T extends Number>
        extends
            Ordered<T>,
            Rotational<AccidentalType>
        {
            /**
             * {@code Conversional} classifies entire converting operational spaces on accidentational axes.
             * <p>
             * This class implementation is in progress.
             *
             * @param <T> the transformed data type.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public
            interface Conversional<T extends Progressor>
            extends Regressional<AccidentalType, Ordered<?>, T>
            {
                public
                AccidentalType transform(
                    T progress,
                    Ordered<?>... amount
                    );
            }
        }

        /**
         * {@code Fall} classifies all musical falls in note systems.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Fall
        extends
            Elementary,
            Influence,
            Progressor
        {}

        /**
         * {@code Tabular} classifies the axis of note names as they singularly stand within their defining table.
         * <p>
         * This class implementation is in progress.
         *
         * @param <T> the numeric data type.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Tabular<T extends Number>
        extends
            Ordered<T>,
            Sinusoidal<PitchType, OctaveType>
        {
            /**
             * {@code Conversional} classifies entire converting operational spaces on tabular axes.
             * <p>
             * This class implementation is in progress.
             *
             * @param <T> the transformed data type.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public
            interface Conversional<T extends Progressor>
            extends Regressional<Modulus, Ordered<?>, T>
            {
                public
                Modulus transpose(
                    T progress,
                    Ordered<?>... amount
                    );
            }
        }

        /**
         * {@code Rise} classifies all musical rises in note systems.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Rise
        extends
            Elementary,
            Influence,
            Progressor
        {}
    }
}