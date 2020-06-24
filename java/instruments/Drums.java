package instruments;

/**
 * {@code Drums} represents the most common form of the drums instrument.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Drums
extends MusicalInstrument
{
    /**
     * {@code Accessory} represents the drums accessory.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Accessory
    extends MusicalInstrument.Accessory
    {}

    /**
     * {@code Pedal} represents the drums pedal.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Pedal
    extends Accessory
    implements instruments.accessory.Pedal
    {}

    /**
     * {@code Stand} represents the drums stand.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Stand
    extends Accessory
    implements instruments.accessory.Stand
    {}
}