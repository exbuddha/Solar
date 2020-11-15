package system.data;

import static system.data.Constant.StandardObjectInoperable;

/**
 * {@code Operable} classifies numeric data types that are operable, with similar types, by simple mathematical operations.
 *
 * @param <T> the numeric data type.
 *
 * @since 1.8
 * @author Alireza Kamran
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

    /**
     * {@code Locked} classifies operable data types that do not support addition, subtraction, multiplication, or division unless their state remains the same after those operations are performed.
     *
     * @param <T> the numeric data type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Locked<T extends Number>
    extends Operable<T>
    {
        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the number is equal to zero.
         *
         * @param n the number.
         *
         * @throws NullPointerException if the number is null.
         * @throws UnsupportedOperationException if the number is not equal to zero.
         */
        @Override
        default void add(final T n) {
            if (!n.equals(0))
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the number is equal to 1.
         *
         * @param n the number.
         *
         * @throws NullPointerException if the number is null.
         * @throws UnsupportedOperationException if the number is not equal to 1.
         */
        @Override
        default void divide(final T n) {
            if (!n.equals(1))
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the number is equal to 1.
         *
         * @param n the number.
         *
         * @throws NullPointerException if the number is null.
         * @throws UnsupportedOperationException if the number is not equal to 1.
         */
        @Override
        default void multiply(final T n) {
            if (!n.equals(1))
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the number is equal to zero.
         *
         * @param n the number.
         *
         * @throws NullPointerException if the number is null.
         * @throws UnsupportedOperationException if the number is not equal to zero.
         */
        @Override
        default void subtract(final T n) {
            if (!n.equals(0))
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }
    }
}