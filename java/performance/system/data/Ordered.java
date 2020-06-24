package performance.system.data;

/**
 * {@code Ordered} classifies performance data types that are ordered by another type.
 *
 * @param <T> the order data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Ordered<T>
extends
    Actualized,
    system.data.Ordered
{
    /**
     * Returns the order.
     *
     * @return the order.
     */
    T getOrder();

    /**
     * Returns the class of the unit of order.
     *
     * @return the order class.
     */
    Class<? extends T> getOrderClass();
}