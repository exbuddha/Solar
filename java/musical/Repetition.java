package musical;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import system.data.Fraction;

/**
 * {@code Repetition} represents a time interval in music, relative to the quarter note time value, over which an ordered list of interactions are repeated.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Repetition
extends Partition
{
    /** The number of repeats. */
    protected
    Fraction count;

    /** The rate offset as the relative difference from the first duration in rate and an effective initial interaction point in time. */
    protected
    Offset offset;

    /**
     * Creates a repetition with the specified rate, number of repeats, and offset.
     *
     * @param rate the repetition rate.
     * @param count the number of repeats.
     * @param offset the rate offset.
     */
    public
    Repetition(
        final List<Duration> rate,
        final Fraction count,
        final Offset offset
        ) {
        super(rate);
        this.count = count;
        this.offset = offset;

        // TODO - Re-scale rates to add up to 1
    }

    /**
     * Creates a single repetition with the specified rate and offset.
     *
     * @param rate the repetition rate.
     * @param offset the rate offset.
     */
    public
    Repetition(
        final List<Duration> rate,
        final Offset offset
        ) {
        this(rate, new Fraction((byte) 1), null);
    }

    /**
     * Creates a single repetition with the specified repetition rate.
     *
     * @param rate the repetition rate.
     */
    public
    Repetition(
        final List<Duration> rate
        ) {
        this(rate, new Fraction((byte) 1));
    }

    /**
     * Creates a repetition with the specified rate and number of repeats.
     *
     * @param rate the repetition rate.
     * @param count the number of repeats.
     */
    public
    Repetition(
        final List<Duration> rate,
        final Fraction count
        ) {
        this(rate, count, null);
    }

    /**
     * Creates an empty repetition.
     * <p>
     * This constructor creates an {@code ArrayList} for the repetition rate.
     */
    public
    Repetition() {
        this(new ArrayList<Duration>(0), new Duration(0, (short) 1));
    }

    /**
     * Returns the iterator for the effective interaction points as durations calculated based on repetition rates and offset.
     *
     * @return iterable for interaction points.
     */
    @Override
    public abstract
    Iterator<Duration> iterator();

    /**
     * Returns the number of repeats.
     *
     * @return the number of repeats.
     */
    public
    Fraction getCount() {
        return count;
    }

    /**
     * Sets the number of repeats.
     *
     * @param count the number of repeats.
     */
    public
    void setCount(
        final Fraction count
        ) {
        this.count = count;
    }

    /**
     * Returns the rate offset.
     *
     * @return the offset.
     */
    public
    Offset getOffset() {
        return offset;
    }

    /**
     * Sets the rate offset.
     *
     * @param offset the offset.
     */
    public
    void setOffset(
        final Offset offset
        ) {
        this.offset = offset;
    }

    /**
     * {@code Complex} represents repetition time offsets that are complex.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Complex
    extends Offset
    {}

    /**
     * {@code Intro} represents repetition offsets that occur at the beginning of the repetition.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Intro
    extends Offset
    {}

    /**
     * {@code Offset} represents all repetition time offsets that are simple.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Offset
    extends Duration
    {
        protected
        Offset() {
            super(-1, (short) 1);
        }

        public abstract
        Duration getOrder();
    }

    /**
     * {@code Outro} represents repetition offsets that occur at the end of the repetition.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Outro
    extends Offset
    {}
}