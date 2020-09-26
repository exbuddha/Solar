package musical.performance;

import java.util.function.BiFunction;

/**
 * {@code Production} classifies all angles of production in performance.
 * <p/>
 * Subclasses of this interface encapsulate performance-specific knowledge in functional language.
 * All production instances are defined as bi-functions of {@code Conductor.ExecutionModel} and a performance subject.
 * The return type of this bi-functional property is the produced object of performance.
 * <p/>
 * This class implementation is in progress.
 *
 * @param <T> the performance subject type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Production<T>
extends BiFunction<Conductor.ExecutionModel, T, Object>
{
    /**
     * {@code ActionType} is the super-type for all action types.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface ActionType
    extends
        Production<Unit>,
        musical.performance.system.Type<Action>
    {}

    /**
     * {@code EffectType} is the super-type for all effect types.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface EffectType
    extends
        Production<Unit>,
        musical.performance.system.Type<Effect>
    {}

    /**
     * {@code ReactionType} is the super-type for all reaction types.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface ReactionType
    extends
        Production<Unit>,
        musical.performance.system.Type<Reaction>
    {}
}