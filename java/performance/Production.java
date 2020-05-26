package performance;

import java.util.function.BiFunction;

/**
 * {@code Production} classifies all angles of production in performance.
 * <p>
 * Subclasses of this interface encapsulate performance-specific knowledge in functional language.
 * All production instances are defined as bi-functions of {@code Conductor.ExecutionModel} and a performance subject.
 * The return type of this bi-functional property is the produced object of performance.
 *
 * @param <T> the performance subject type.
 */
public
interface Production<T>
extends BiFunction<Conductor.ExecutionModel, T, Object>
{
    /**
     * {@code ActionType} is the super-type for all action types.
     */
    public
    interface ActionType
    extends
        Production<Unit>,
        performance.system.Type<Action>
    {}

    /**
     * {@code EffectType} is the super-type for all effect types.
     */
    public
    interface EffectType
    extends
        Production<Unit>,
        performance.system.Type<Effect>
    {}

    /**
     * {@code ReactionType} is the super-type for all reaction types.
     */
    public
    interface ReactionType
    extends
        Production<Unit>,
        performance.system.Type<Reaction>
    {}
}