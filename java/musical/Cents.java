package musical;

import music.system.data.Delta;
import music.system.data.Ordered;

/**
 * {@code Cents} is the smallest unit of change in hearing scale used in music theory.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
interface Cents
extends
    Comparable<Number>,
    Delta<Short>,
    Ordered<Short>,
    Unit
{
    /**
     * Returns the amount of semitones in this cent.
     *
     * @return the semitones.
     */
    default
    short getSemitones() {
        return (short) (getOrder() / 100);
    }

    /**
     * Compares this instance with the specified number, as cents, and returns a negative integer if this instance is less than the number, zero if they are equal, and a positive integer otherwise.
     * If the specified number is null, {@link Integer#MAX_VALUE} will be returned.
     *
     * @param n the number. (cents)
     *
     * @return a negative integer if this instance is less than the number, zero if they are equal, and a positive integer otherwise; or {@link Integer#MAX_VALUE} if the number is null.
     */
    @Override
    default int compareTo(final Number n) {
        return n == null
               ? Integer.MAX_VALUE
               : getOrder() - n.shortValue();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns the {@code Short} class.
     *
     * @return the {@code Short} class.
     */
    @Override
    default Class<Short> getUnit() {
        return Short.class;
    }
}