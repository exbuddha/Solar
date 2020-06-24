package instruments;

/**
 * {@code Trombone} classifies the most common form of the trombone instrument.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
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

    /**
     * {@code Bell} represents the trombone bell.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Bell
    extends AtomicPart
    {}

    /**
     * {@code MouthPiece} represents the trombone mouth piece.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class MouthPiece
    extends AtomicPart
    {}

    /**
     * {@code Slide} represents the trombone slide.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Slide
    extends AtomicPart
    {}
}