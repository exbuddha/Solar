package musical;

/**
 * {@code Adjustable} classifies the entity that accepts adjustment for routine calculations.
 */
public
interface Adjustable
{
    /**
     * Performs adjustment.
     * <p>
     * The default implementation is empty.
     */
    public default
    void adjust() {}
}