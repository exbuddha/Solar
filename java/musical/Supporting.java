package musical;

/**
 * {@code Supporting} classifies data types exclusively related to other data types for providing systematic support.
 *
 * @param <T> the supported data type.
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