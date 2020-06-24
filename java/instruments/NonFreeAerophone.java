package instruments;

/**
 * {@code NonFreeAerophone} classifies instruments in which the vibrating air is contained within the instrument itself.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class NonFreeAerophone
extends Aerophone
{
    /**
     * {@code Accessory} represents a non-free aerophone instrument accessory.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Accessory
    extends Aerophone.Accessory
    {}

    /**
     * {@code Muffle} represents a non-free aerophone instrument muffle.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Muffle
    extends Accessory
    implements instruments.accessory.Damper
    {}
}