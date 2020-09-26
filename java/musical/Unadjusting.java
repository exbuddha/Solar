package musical;

/**
 * {@code Unadjusting} classifies system data types that are unadjustable to a similar or different type.
 * <p/>
 * In all common cases, the unadjusted data type is similar to its adjusting data type.
 *
 * @param <T> the unadjusted data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Unadjusting<T>
{
    /**
     * Unadjusts and returns the unadjusted instance.
     *
     * @return the unadjusted instance.
     */
    T unadjusted();
}