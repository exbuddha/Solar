package system.data;

/**
 * {@code Wrappable} classifies system data types that can be wrapped in-place.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Wrappable
{
    /**
     * Wraps the data using a true rewrap flag.
     *
     * @see #wrap(boolean)
     */
    default
    void wrap() {
        wrap(true);
    }

    /**
     * Wraps the data.
     * <p/>
     * If the rewrap flag is set to true, the data will be rewrapped which usually means that wrapping will be performed on all data elements even if they are already wrapped.
     * Otherwise, wrapping will only affect the unwrapped data elements.
     *
     * @param rewrap the rewrap flag.
     */
    void wrap(
        boolean rewrap
        );
}