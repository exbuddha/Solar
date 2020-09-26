package musical;

import java.util.Deque;
import java.util.Iterator;
import java.util.List;

/**
 * {@code Partition} represents timing in music.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Partition
extends Duration
implements Iterable<Duration>
{
    /** The partition rate as a list of durations representing interaction points. */
    protected final
    List<Duration> rate;

    /**
     * Creates a partition with the specified rate.
     *
     * @param rate the partition rate.
     */
    public
    Partition(
        final List<Duration> rate
        ) {
        super(1, (short) 1);
        this.rate = rate;

        // TODO - Sum up rate durations and validate offset
    }

    /**
     * Returns the iterator for the effective interaction points as durations calculated based on partition rates.
     *
     * @return iterable for interaction points.
     */
    @Override
    public abstract
    Iterator<Duration> iterator();

    /**
     * Returns the partition rate.
     *
     * @return the partition rate.
     */
    public
    List<Duration> getRate() {
        return rate;
    }

    /**
     * {@code Reorder} represents a reordering for partition rates.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Reorder
    extends Duration
    implements Deque<Duration>
    {
        /**
         * Creates a reorder as a single duration.
         *
         * @param beats the number of beats in duration.
         * @param units the number of beat units in duration.
         */
        public
        Reorder(
            final int beats,
            final short units
            ) {
            super(beats, units);
        }
    }
}