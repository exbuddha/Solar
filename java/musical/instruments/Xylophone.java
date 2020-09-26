package musical.instruments;

/**
 * {@code Xylophone} classifies the most common form of the xylophone instrument.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Xylophone
extends StruckIdiophone
{
    /**
     * Creates a standard xylophone instrument.
     */
    protected
    Xylophone() {
        super();
    }

    /**
     * {@code Bar} represents a xylophone bar.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Bar
    extends AtomicPart
    {}

    /**
     * {@code Bars} represents all of the xylophone bars.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Bars
    extends CompositePart
    {}

    /**
     * {@code Mallet} represents a xylophone mallet.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Mallet
    extends Accessory
    implements musical.instruments.accessory.Mallet
    {}
}