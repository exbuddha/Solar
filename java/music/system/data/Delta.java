package music.system.data;

/**
 * {@code Delta} classifies musical data types that are distances measured by a known enumerable type.
 *
 * @param <T> the measure type.
 */
public
interface Delta<T>
extends
    system.Delta,
    Ordered<T>
{
    @Override
    public
    T getOrder();

    public
    Class<? super T> getType();
}