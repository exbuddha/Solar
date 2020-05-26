package performance;

/**
 * {@code Awareness} classifies all data types that possess an awareness of the performance execution model such that they are able to provide details about the snapshot instances that relate to performer or instrument states.
 */
public
interface Awareness
extends performance.system.Type<Awareness>
{
    /**
     * Returns the modeled snapshot instance for the specified state type.
     * <p>
     * This implementation returns the static {@code Snapshot.Instance.Silence} instance.
     *
     * @param state the state type.
     * @return the modeled snapshot.
     */
    public default
    Snapshot.Instance<?> findSnapshotInstance(
        performance.system.Type<State> state
        ) {
        return Snapshot.Instance.Silence;
    }
}