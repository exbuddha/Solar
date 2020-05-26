package music.system.data;

/**
 * {@code Convertible} classifies data types that can be converted into other types.
 *
 * @param <T> the converted type.
 */
public
interface Convertible<T>
{
    /**
     * Converts this data into other type.
     *
     * @return the converted data.
     */
    public
    T convert();

    public
    interface By<S, T>
    {
        public
        Convertible<T> by(
            S instance
            );
    }

    public
    interface Thru<S, R, T>
    {
        public
        Convertible<T> thru(
            S instance,
            R reference
            );
    }
}