package instruments;

/**
 * {@code ElectricGuitar} classifies the most common form of the electric guitar instrument.
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

    public abstract
    class Pickup
    extends AtomicPart
    {
        public abstract
        class Selector
        extends Accessory
        {}
    }

    public abstract
    class Tone
    extends Accessory
    {}

    public abstract
    class Volume
    extends Accessory
    {}
}