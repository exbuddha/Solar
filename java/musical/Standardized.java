package musical;

/**
 * {@code Standardized} classifies data types that are well-defined in a musical system of knowledge.
 * <p/>
 * This class implementation is in progress.
 *
 * @param <T> the data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Standardized<T>
{
    /**
     * Returns true if this instance is standard; otherwise returns false.
     *
     * @return true if this instance is standard, and false otherwise.
     */
    public
    boolean isStandard();
}