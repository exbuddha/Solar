package instruments;

/**
 * {@code Rattle} classifies the most common form of the rattle instrument.
 */
public abstract
class Rattle
extends StruckIdiophone
{
    public abstract
    class Handle
    extends AtomicPart
    {}

    public abstract
    class Shake
    extends AtomicPart
    {}
}