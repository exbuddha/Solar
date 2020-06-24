package musical;

/**
 * {@code Adjusting} classifies musical data types that are adjustable to a similar or different type by an adjustment type.
 * <p>
 * In all common cases, the adjusted data type is similar to the adjusting data type.
 *
 * @param <T> the adjusted data type.
 * @param <S> the adjustment data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Adjusting<T, S>
{
    /**
     * Adjusts using the specified adjustments and returns the adjusted instance.
     *
     * @param adjustments the adjustments.
     *
     * @return the adjusted instance.
     */
    T adjusted(
        S... adjustments
        );
}