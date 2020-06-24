package system.data;

/**
 * {@code Inverting} classifies data types that are invertible into a similar or different type.
 *
 * @param <T> the inverted data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Inverting<T>
{
    /**
     * Inverts data and returns the inverted instance.
     *
     * @return the inverted instance.
     */
    T inverted();
}