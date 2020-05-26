package instruments;

/**
 * {@code Trumpet} classifies the most common form of the trumpet instrument.
 */
public abstract
class Trumpet
extends NonFreeAerophone
{
    /**
     * Creates a standard trumpet instrument.
     */
    protected
    Trumpet() {
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