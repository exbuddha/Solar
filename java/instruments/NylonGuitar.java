package instruments;

/**
 * {@code NylonGuitar} classifies the most common form of the nylon guitar instrument.
 */
public abstract
class NylonGuitar
extends Guitar
{
    /** The default number of frets. */
    private static final
    byte DefaultFretCount = (byte) 18;

    /** The default number of harmonic node groups. */
    private static final
    byte DefaultHarmonicCount = (byte) 7;

    /**
     * Creates a nylon guitar with the default number of frets (18) default harmonic node groups. (7)
     */
    public
    NylonGuitar() {
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