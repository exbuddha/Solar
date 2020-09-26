package system.data;

/**
 * {@code Wrapping} classifies system data types that are wrappable to a similar or different type.
 * <p/>
 * In all common cases, the wrapped data type is similar to the wrapping data type.
 *
 * @param <T> the wrapped data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Wrapping<T>
{
    /**
     * Wraps and returns the instance.
     * <p/>
     * If the rewrap flag is set to true, the data will be rewrapped which usually means that wrapping will be performed on all data elements even if they are already wrapped.
     * Otherwise, wrapping will only affect the unwrapped data elements.
     *
     * @param rewrap the rewrap flag.
     *
     * @return the wrapped instance.
     */
    T wrapped(
        boolean rewrap
        );

    /**
     * Wraps and return the instance using a true rewrap flag.
     *
     * @return the wrapped instance.
     *
     * @see #wrapped(boolean)
     */
    default
    T wrapped() {
        return wrapped(true);
    }
}