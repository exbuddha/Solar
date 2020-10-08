package musical.performance;

import java.time.temporal.Temporal;

import musical.Duration;
import system.Type;

/**
 * {@code Instance} represents an instance of time in music where the intentions of the score demand an action from, or the attention of, the performer.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Instance
implements
    Comparable<Instance>,
    Interpreted,
    Temporal,
    musical.performance.system.Type<Temporal>
{
    /** The set of elements in the score that collectively relate to the instance. */
    protected final
    OrderedSet elements;

    /** The start time of the instance as a duration offset from the beginning of score. */
    protected final
    Duration time;

    /**
     * Creates an instance with empty elements at the specified start time.
     *
     * @param time the start time.
     */
    public
    Instance(
        final Duration time
        ) {
        this.time = time;
        elements = OrderedSet.Empty;
    }

    @Override
    public int compareTo(final Instance instance) {
        if (time == null)
            return instance.time == null
                   ? 0
                   : 1;

        return instance.time == null
               ? -1
               : time.compareTo(instance.time);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Instance))
            return false;

        final Duration objStart = ((Instance) obj).time;
        return objStart == null
               ? time == null
               : objStart.compareTo(time) == 0;
    }

    @Override
    public boolean is(final Type<? super Temporal> type) {
        return this == type;
    }

    /**
     * Returns the music data elements of the instance.
     *
     * @return the elements.
     */
    @Override
    public OrderedSet findElements() {
        return elements;
    }

    /**
     * Returns the start time of the instance.
     *
     * @return the start time.
     */
    public
    Duration getTime() {
        return time;
    }
}