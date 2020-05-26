package instruments;

/**
 * {@code Xylophone} classifies the most common form of the xylophone instrument.
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

    public abstract
    class Bar
    extends AtomicPart
    {}

    public abstract
    class Bars
    extends CompositePart
    {}

    public abstract
    class Mallet
    extends Accessory
    implements instruments.accessory.Mallet
    {}
}