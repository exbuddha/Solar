package instruments;

/**
 * {@code Trombone} classifies the most common form of the trombone instrument.
 */
public abstract
class Trombone
extends NonFreeAerophone
{
    /**
     * Creates a standard trombone instrument.
     */
    protected
    Trombone() {
        super();
    }

    public abstract
    class Bell
    extends AtomicPart
    {}

    public abstract
    class MouthPiece
    extends AtomicPart
    {}

    public abstract
    class Slide
    extends AtomicPart
    {}
}