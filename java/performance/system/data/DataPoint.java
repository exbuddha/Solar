package performance.system.data;

/**
 * {@code DataPoint} classifies all performance-specific data types.
 *
 * @param <U> the music-specific data type.
 * @param <T> the additional performance-specific data type.
 */
public
interface DataPoint<U extends music.system.data.DataPoint<?,?,?>, T>
{
    public
    T t();
}