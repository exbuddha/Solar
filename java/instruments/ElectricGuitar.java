package instruments;

/**
 * {@code ElectricGuitar} classifies the most common form of the electric guitar instrument.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class ElectricGuitar
extends Guitar
{
    /** The default number of frets. */
    private static final
    byte DefaultFretCount = (byte) 22;

    /** The default number of harmonic node groups. */
    private static final
    byte DefaultHarmonicCount = (byte) 9;

    /**
     * Creates an electric guitar with standard tuning (E2, A2, D3, G3, B3, E4), default number of frets (22), and default harmonic node groups. (9)
     */
    public
    ElectricGuitar() {
        super(StandardTuning, DefaultFretCount, DefaultHarmonicCount);
    }

    /**
     * Returns the default number of frets.
     *
     * @return the default number of frets.
     */
    public static
    byte getDefaultFretCount() {
        return DefaultFretCount;
    }

    /**
     * Returns the default number of harmonic node groups.
     *
     * @return the default number of harmonic node groups.
     */
    public static
    byte getDefaultHarmonicCount() {
        return DefaultHarmonicCount;
    }

    /**
     * {@code Pickup} represents the electric guitar pickup.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Pickup
    extends AtomicPart
    {
        /**
         * {@code Selector} represents the electric guitar pickup selector.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public abstract
        class Selector
        extends Accessory
        {}
    }

    /**
     * {@code Tone} represents the electric guitar tone control knob.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Tone
    extends Accessory
    {}

    /**
     * {@code Volume} represents the electric guitar volume control knob.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Volume
    extends Accessory
    {}
}