package instruments;

/**
 * {@code AcousticGuitar} classifies the most common form of an acoustic guitar instrument.
 */
public abstract
class AcousticGuitar
extends Guitar
{
    /** The default number of frets. */
    private static final
    byte DefaultFretCount = (byte) 20;

    /** The default number of harmonic node groups. */
    private static final
    byte DefaultHarmonicCount = (byte) 9;

    /**
     * Creates an acoustic guitar with standard tuning (E2, A2, D3, G3, B3, E4), default number of frets (20), and default harmonic node groups. (9)
     */
    public
    AcousticGuitar() {
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
    class Hole
    extends AtomicPart
    {}

    public abstract
    class Resonator
    extends AtomicPart
    {}

    public abstract
    class Soundboard
    extends AtomicPart
    {}
}