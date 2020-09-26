package system.data;

/**
 * {@code Unwrapping} classifies system data types that are unwrappable to a similar or different type.
 * <p/>
 * In all common cases, the unwrapped data type is similar to the unwrapping data type.
 *
 * @param <T> the unwrapped data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Unwrapping<T>
{
    /**
     * Unwraps and returns the instance.
     *
     * @return the unwrapped instance.
     */
    T unwrapped();
}