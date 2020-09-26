package system.data;

/**
 * {@code Ordered} classifies all data types that are ordered.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Ordered
{
    /**
     * Returns the type of the unit of order.
     *
     * @return the order unit type.
     */
    Class<?> getOrderClass();

    /**
     * {@code PerMany} classifies all data types that are ordered per many sets or axes.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface PerMany
    extends Ordered
    {
        /**
         * Returns the order axes.
         *
         * @return the order axes.
         */
        Class<?>[] getAxes();
    }
}