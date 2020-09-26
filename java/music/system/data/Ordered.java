package music.system.data;

/**
 * {@code Ordered} classifies musical data types that have order.
 *
 * @param <T> the order data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Ordered<T>
extends
    system.data.Ordered,
    Visualized
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

    /**
     * {@code Converted} classifies musical data types that are ordered and can be converted by delta amounts in that order.
     * <p/>
     * This class implementation is in progress.
     *
     * @param <T> the order data type.
     * @param <S> the delta type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Converted<T, S>
    extends
        Convertible.By<S, T>,
        Ordered<T>
    {
        /**
         * Returns the delta amount.
         *
         * @return the delta.
         */
        S getDelta();

        /**
         * Returns the delta unit or measure class.
         *
         * @return the delta unit.
         */
        Class<? super S> getUnit();
    }

    /**
     * {@code OpenVariable} classifies variable types that are of a certain data type or its super-type.
     * <p/>
     * This class implementation is in progress.
     *
     * @param <T> the data type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface OpenVariable<T>
    {
        /**
         * Returns the variable data class.
         *
         * @return the data superclass.
         */
        Class<? super T> getSuperType();
    }

    /**
     * {@code Variable} classifies variable types that are of a certain data type.
     * <p/>
     * This class implementation is in progress.
     *
     * @param <T> the data type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Variable<T>
    {
        /**
         * Returns the variable data class.
         *
         * @return the data type.
         */
        Class<? extends T> getType();
    }
}