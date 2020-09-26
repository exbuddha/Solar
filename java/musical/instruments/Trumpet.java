package musical.instruments;

/**
 * {@code Trumpet} classifies the most common form of the trumpet instrument.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
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

    /**
     * {@code Bell} represents the trumpet bell.
     * <p/>
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
     * {@code Bell} represents the trumpet mouth piece.
     * <p/>
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
     * {@code Bell} represents the trumpet slide.
     * <p/>
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