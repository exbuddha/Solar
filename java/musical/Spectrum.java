package musical;

/**
 * {@code Spectrum} characterizes any sonic data type that is within the audible frequency ranges of sound.
 */
public
interface Spectrum
{
    /**
     * {@code Effect} categorizes sonic or musical characteristic of spectrums, notes, and groups.
     * <p>
     * This categorization intends to define a handful of characteristics that relate to time-sensitivity of individual incidents within their own maintained line of thought.
     * It focuses only on influence of the recognized timbres and tones present in the line of thought during listening.
     * There are additional points of interest specifically related to the perception of spectrums that will have to accompany the data represented here in order to completely allow for analysis of musical incidents, such as pitch rises and falls or changes in tempo.
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
        public boolean is(final system.Type<Effect> type) {
            return this == type;
        }
    }

    /**
     * {@code Localization} classifies any form of data type that characterizes tone quality of sounds by scientifically recording properties of the spectrum of those sounds.
     */
    public
    interface Localization
    {}
}