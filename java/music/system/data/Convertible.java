package music.system.data;

/**
 * {@code Convertible} classifies data types that are on a continuum and can be converted into other types.
 *
 * @param <T> the converted data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Convertible<T>
{
    /**
     * Converts this data into other type.
     *
     * @return the converted data.
     */
    T convert();

    /**
     * {@code By} classifies data types that are convertible by another type.
     *
     * @param <S> the conversion data type.
     * @param <T> the converting data type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface By<S, T>
    {
        /**
         * Returns an intermediary convertible type that takes into account the specified conversion data type.
         *
         * @param instance the conversion data.
         *
         * @return the convertible type.
         */
        Convertible<T> by(
            S instance
            );
    }

    /**
     * {@code Thru} classifies data types that are convertible thru another type by a reference or amount type.
     *
     * @param <S> the converted data type.
     * @param <R> the conversion data type.
     * @param <T> the reference data type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Thru<S, R, T>
    {
        /**
         * Returns an intermediary convertible type that takes into account the specified conversion and reference types.
         *
         * @param instance the conversion data.
         * @param reference the reference data.
         *
         * @return the convertible type.
         */
        Convertible<T> thru(
            S instance,
            R reference
            );
    }
}