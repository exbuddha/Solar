package music.system.data;

/**
 * {@code DataPoint} classifies all music-specific data types.
 *
 * @param <X> the first variable type.
 * @param <Y> the second variable type.
 * @param <Z> the third variable type.
 */
public
interface DataPoint<X, Y, Z>
extends system.Data
{
    public
    X x();

    public
    Y y();

    public
    Z z();
}