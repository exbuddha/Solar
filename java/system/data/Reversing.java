package system.data;

/**
 * {@code Reversing} classifies data types that are reversible into a similar or different type.
 *
 * @param <T> the reversed data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Reversing<T>
{
    /**
     * Reverses data and returns the reversed instance.
     *
     * @return the reversed instance.
     */
    T reversed();
}