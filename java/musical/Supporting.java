package musical;

/**
 * {@code Supporting} classifies data types exclusively related to other data types for providing systematic support.
 * <p>
 * This class implementation is in progress.
 *
 * @param <T> the supported data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Supporting<T>
{
    /**
     * Returns true if the specified data supports this data, and false otherwise.
     *
     * @param instance the data.
     * @return true if the data supports the specified data, and false otherwise.
     */
    public
    boolean supports(
        T instance
        );
}