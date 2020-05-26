package musical;

/**
 * {@code Standardized} classifies data types that are well-defined in a musical system of knowledge.
 *
 * @param <T> the data type.
 */
public
interface Standardized<T>
{
    /**
     * Returns true if the specified instance is standard, and false otherwise.
     *
     * @param instance the instance.
     * @return true if instance is standard, and false otherwise.
     */
    public
    boolean isStandard(
        T instance
        );
}