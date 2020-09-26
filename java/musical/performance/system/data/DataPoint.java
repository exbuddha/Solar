package musical.performance.system.data;

/**
 * {@code DataPoint} classifies all performance-specific data types.
 * <p/>
 * This class implementation is in progress.
 *
 * @param <U> the music-specific data type.
 * @param <T> the additional performance-specific data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface DataPoint<U extends music.system.DataPoint<?,?,?>, T>
{
    public
    T t();
}