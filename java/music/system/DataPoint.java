package music.system;

/**
 * {@code DataPoint} classifies all music-specific data types.
 * <p>
 * This class implementation is in progress.
 *
 * @param <X> the first variable type.
 * @param <Y> the second variable type.
 * @param <Z> the third variable type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface DataPoint<X, Y, Z>
{
    /**
     * Returns the first data coordinate. (x)
     *
     * @return the x coordinate.
     */
    X x();

    /**
     * Returns the second data coordinate. (y)
     *
     * @return the y coordinate.
     */
    Y y();

    /**
     * Returns the third data coordinate. (z)
     *
     * @return the z coordinate.
     */
    Z z();
}