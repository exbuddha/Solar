package musical.performance;

import system.Type;

import static system.data.Constant.BaseImplementationRestricted;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;

/**
 * {@code Snapshot} encapsulates time-bound states of performer and instrument parts at a given instance in performance.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Snapshot
extends
    Temporal,
    Type.Null
{
    /**
     * This implementation returns 0.
     */
    @Override
    default long getLong(TemporalField field) { return 0; }

    /**
     * This implementation returns false.
     */
    @Override
    default boolean isSupported(TemporalField field) { return false; }

    /**
     * This implementation returns false.
     */
    @Override
    default boolean isSupported(TemporalUnit unit) { return false; }

    /**
     * This implementation returns null.
     */
    @Override
    default Temporal plus(long amountToAdd, TemporalUnit unit) { return null; }

    /**
     * This implementation returns 0.
     */
    @Override
    default long until(Temporal endExclusive, TemporalUnit unit) { return 0; }

    /**
     * This implementation returns null.
     */
    @Override
    default Temporal with(TemporalField field, long newValue) { return null; }

    /**
     * {@code Instance} specifies snapshot instances in performance.
     * <p/>
     * The subclasses of this interface share a functional interrelation similar to production of/on snapshot units.
     * The return types of the bi-functional property of this interface are all a {@link Type} of known classes in existence.
     * This is a highly abstract data type in performance system, best coined by the term "situation".
     * <p/>
     * This class implementation is in progress.
     *
     * @param <T> the production known type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Instance<T>
    extends
        java.util.function.BiFunction<Conductor<?>, Snapshot, Type<T>>,
        Snapshot,
        musical.performance.system.Type<Snapshot>
    {
        /**
         * The most fundamental form of silence in existence.
         */
        Instance<Object> Silence = new Instance<Object>() {};

        /**
         * This implementation throws an {@code UnsupportedOperationException}.
         */
        @Override
        default Type<T> apply(Conductor<?> universe, Snapshot byproduct) {
            throw new UnsupportedOperationException(BaseImplementationRestricted);
        }

        @Override
        default boolean is(final Type<? super Snapshot> type) {
            return this == type;
        }
    }
}