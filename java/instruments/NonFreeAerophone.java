package instruments;

/**
 * {@code NonFreeAerophone} classifies instruments in which the vibrating air is contained within the instrument itself.
 */
public abstract
class NonFreeAerophone
extends Aerophone
{
    public abstract
    class Accessory
    extends Aerophone.Accessory
    {}

    public abstract
    class Muffle
    extends Accessory
    implements instruments.accessory.Damper
    {}
}