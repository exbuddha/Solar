package system.data;

/**
 * {@code Operable} classifies numeric data types that are operable, with similar types, by simple mathematical operations.
 *
 * @param <T> the numeric data type.
 */
public
interface Operable<T extends Number>
{
    /**
     * Adds the specified instance to this instance.
     *
     * @param instance the instance.
     */
    void add(
        T instance
        );

    /**
     * Divides this instance by the specified instance and returns this instance.
     *
     * @param instance the instance.
     *
     * @return the divided instance.
     */
    T by(
        T instance
        );

    /**
     * Divides this instance by the specified instance.
     *
     * @param instance the instance.
     */
    void divide(
        T instance
        );

    /**
     * Subtracts the specified instance from this instance and returns this instance.
     *
     * @param instance the instance.
     *
     * @return the subtracted instance.
     */
    T minus(
        T instance
        );

    /**
     * Multiplies this instance by the specified instance.
     *
     * @param instance the instance.
     */
    void multiply(
        T instance
        );

    /**
     * Adds the specified instance to this instance and returns this instance.
     *
     * @param instance the instance.
     *
     * @return the added instance.
     */
    T plus(
        T instance
        );

    /**
     * Subtracts the specified instance from this instance.
     *
     * @param instance the instance.
     */
    void subtract(
        T instance
        );

    /**
     * Multiplies this instance by the specified instance and returns this instance.
     *
     * @param instance the instance.
     *
     * @return the multiplied instance.
     */
    T times(
        T instance
        );
}