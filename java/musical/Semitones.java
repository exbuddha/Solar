package musical;

import music.system.data.Delta;
import music.system.data.Ordered;

/**
 * {@code Semitones} is the unit of change in hearing scale used in music theory, magnitudinal to cents.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
interface Semitones
extends
    Comparable<Number>,
    Delta<Byte>,
    Ordered<Byte>,
    Unit
{
    /**
     * Returns the amount of cents in this semitone.
     *
     * @return the cents.
     */
    default
    short getCents() {
        return (short) (getOrder() * 100);
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
               : getCents() - n.byteValue();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns the {@code Byte} class.
     *
     * @return the {@code Byte} class.
     */
    @Override
    default Class<Byte> getUnit() {
        return Byte.class;
    }
}