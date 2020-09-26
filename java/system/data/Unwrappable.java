package system.data;

/**
 * {@code Unwrappable} classifies system data types that can be unwrapped in-place.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Unwrappable
{
    /**
     * Unwraps the data.
     */
    void unwrap();
}