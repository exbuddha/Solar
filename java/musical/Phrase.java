package musical;

import java.util.function.Consumer;
import java.util.function.Supplier;

import music.system.data.Clockable.Templator;
import performance.Instance;

/**
 * {@code Phrase} classifies ranges in music that additively construct melodies.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Phrase
extends
    Iterable<Phrase>,
    Pulse,
    Range
{
    /**
     * Returns true if the phrase is instantaneous.
     * <p>
     * This implementation returns false.
     *
     * @return true if the phrase is instantaneous.
     */
    public default
    boolean isInstantaneous() {
        return false;
    }

    /**
     * {@code Detection} represents the intelligent process of determining phrases in music scores.
     * <p>
     * In music, phrases are considered to be the humanly recognizable ranges that resemble a complete clause in writing or speech.
     * A process of detection involves programmatical construction of the generalization below, pivoting on the cognition of similarity and symmetry:
     * <p>
     * If it is similar, it is a part of phrase.
     * If it is non-similar, it is a new, or a new part of phrase.
     * The rest is a historical lookup in an archived memory of observed phrases in music.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class Detection
    implements
        Consumer<Instance>,
        Supplier<Pulse>
    {
        /**
         * Returns true when a phrase is detected.
         *
         * @return true when a phrase is detected.
         */
        public abstract
        boolean isResolved();
    }

    /**
     * {@code Sensation} categorizes human musical perception of phrases, and their parts, based on universal laws of modulation and tonality.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    enum Sensation
    implements
        Perception,
        Templator
    {
        Neutral,

        Pleasant,

        Restful,

        Unpleasant,

        Unrestful;

        @Override
        public boolean is(system.data.Type<? extends musical.Perception> type) { return false; }
    }
}