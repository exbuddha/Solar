package music.system.data;

/**
 * {@code Delta} classifies musical data types that are distances measured by another unit data type.
 *
 * @param <T> the measure data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Delta<T>
extends system.data.Delta
{
    /**
     * Returns the measure unit class.
     *
     * @return the measure unit class.
     */
    Class<? super T> getUnit();
}