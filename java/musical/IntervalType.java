package musical;

import music.system.data.Clockable.Regressor;

/**
 * {@code IntervalType} classifies all interval types.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
interface IntervalType
extends
    Comparable<IntervalType>,
    Regressor,
    music.system.Type<IntervalType>
{
    /**
     * Returns the number of semitones in this interval type by rounding the cents amount.
     * <p>
     * This implementation calls {@link #getCents()} and rounds away from zero.
     *
     * @return the semitones.
     */
    default
    byte getSemitones() {
        final short cents = getCents();
        return (byte) (Integer.signum(cents) * Math.round(Math.abs(cents) / 100D));
    }

    /**
     * Compares this instance with the specified interval type and returns a negative integer if this instance is less than the interval type, zero if they are equal, and a positive integer otherwise.
     * If the specified interval is null, {@link Integer#MAX_VALUE} will be returned.
     *
     * @param interval the interval type.
     *
     * @return a negative integer if this instance is less than the interval type, zero if they are equal, and a positive integer otherwise; or {@link Integer#MAX_VALUE} if the interval type is null.
     */
    @Override
    default int compareTo(final IntervalType interval) {
        return interval == null
               ? Integer.MAX_VALUE
               : (int) (getCents() - interval.getCents());
    }

    /**
     * Returns the number of cents in this interval type.
     *
     * @return the cents.
     */
    short getCents();
}