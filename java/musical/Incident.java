package musical;

/**
 * {@code Incident} classifies all musical events recorded in notations.
 * <p/>
 * This class implementation is in progress.
 *
 * @param <T> the musical data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
interface Incident<T>
{
    public
    T findStartTime(
        Incident<?> incident
        );

    public
    T findStartTime();

    public
    T getDuration();

    public
    Incident<?> getContainer();

    public
    void setDuration(
        T duration
        );
}