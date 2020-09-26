package musical.instruments;

/**
 * {@code Gong} classifies the most common form of the gong instrument where the vibration is strongest near the vertex.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Gong
extends StruckIdiophone
{
    private static final
    byte MaxDiameter = (byte) 150;

    private static final
    byte MinDiameter = (byte) 50;

    /**
     * {@code Center} represents the center of the gong.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Center
    extends AtomicPart
    {}

    /**
     * {@code Edge} represents the edge of the gong.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Edge
    extends AtomicPart
    {}

    /**
     * {@code Perimeter} represents the gong perimeter.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Perimeter
    extends AtomicPart
    {}

    /**
     * {@code Rim} represents the gong rim.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Rim
    extends AtomicPart
    {}
}