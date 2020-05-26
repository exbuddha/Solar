package musical;

/**
 * {@code Readjusting} classifies musical data types that are convertible into their own type by an adjustment operation.
 */
public
interface Readjusting<T>
{
    /**
     * Returns the readjusted instance from the specified adjustments.
     *
     * @param adjustments the adjustments.
     * @return the adjustment accounting instance.
     */
    public
    T adjusted(
        Number... adjustments
        );

    /**
     * Returns the readjusted instance from the specified intervals.
     *
     * @param intervals the intervals.
     * @return the adjustment accounting instance.
     */
    public default
    T adjusted(
        final Interval... intervals
        ) {
        Short[] adjustments = new Short[intervals.length];
        for (int i = 0; i < intervals.length; adjustments[i] = intervals[i++].cents);
        return adjusted(adjustments);
    }

    /**
     * Returns the readjusted instance from the specified objects.
     *
     * @param objects the objects.
     * @return the adjustments accounting instance.
     * @throws IllegalArgumentException if objects contain a non-{@code Number} or -{@code Interval} type.
     */
    public default
    T adjusted(
        final Object... objects
        ) {
        Number[] adjustments = new Number[objects.length];
        for (int i = 0; i < objects.length; i++)
            if (objects[i] instanceof Number)
                adjustments[i] = (Number) objects[i];
            else
                if (objects[i] instanceof Interval)
                    adjustments[i] = ((Interval) objects[i]).cents;
                else
                    throw new IllegalArgumentException("Unable to perform adjustment with objects.");

        return adjusted(adjustments);
    }
}