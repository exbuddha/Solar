package music.system.data;

/**
 * {@code Ordered} classifies musical data types that have order.
 *
 * @param <T> the order type.
 */
public
interface Ordered<T>
extends Visualized
{
    public
    T getOrder();

    public
    interface Converted<T, S>
    extends
        Convertible.By<T, S>,
        Ordered<T>
    {
        public
        S getDelta();

        public
        Class<? super S> getUnit();
    }

    public
    interface OpenVariable<T>
    {
        public
        Class<? super T> getSuperType();
    }

    public
    interface Variable<T>
    {
        public
        Class<? extends T> getType();
    }
}