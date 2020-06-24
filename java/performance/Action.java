package performance;

import system.data.Type;

/**
 * {@code Action} categorizes the types of actions that body parts can perform on instrument parts or their accessories.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
enum Action
implements
    Production.ActionType,
    Snapshot
{
    /** Burst engagement negligible in time. */
    Instantaneous(),

    /** Sustained engagement noticeable in time required for the entire or a fraction of the duration of the interaction. */
    Continuous(),

    /** Repeats the same action periodically over time. */
    Repetitive(Continuous),

    /** Can be performed only by one of the body orientations. */
    Unilateral(),

    /** Varies in physical performance characteristics. */
    Variational(),

    /** Can be performed uniquely differently by either of the body orientations with one usually, but not necessarily, preferred over the other. */
    Bilateral(Variational),

    /** Can be performed in multiple body movement directions in relation to the instrument part. */
    Directional(Variational),

    /** Varies in physical intensity producing gradual degrees in the effect. (A normal or medium degree is naturally implied) */
    Gradual(Variational),

    /** Can be performed with constant or variable acceleration or repetition rate. */
    Accelerational(Variational),

    /** Can only be performed with constant acceleration or repetition rate. */
    NonAccelerational(Variational),

    /** Requires the body part to be in a certain position (or range) relative to the instrument part. */
    Positional(),

    /** Cannot be associated with any physical body part. */
    NonPhysical(),

    /** Undefined action. */
    Undefined();

    /** The action type. */
    private final
    Type<Action> type;

    /**
     * Creates an action of the specified type.
     *
     * @param type the action type.
     */
    Action(
        final Action type
        ) {
        this.type = type;
    }

    /**
     * Creates an untyped action.
     */
    Action() {
        this(null);
    }

    @Override
    public Object apply(Conductor.ExecutionModel model, Unit instrument) { return null; }

    /**
     * Returns true if the specified action type is of this type, and false otherwise.
     *
     * @param type the other type.
     * @return true if the specified action type is of this type, and false otherwise.
     */
    @Override
    public boolean is(final system.data.Type<? extends Action>type) {
        if (type == null)
            return false;

        if (type == this)
            return true;

        return this.type != null && this.type.is(type);
    }

    /**
     * Returns the type associated with this action type.
     *
     * @return the action type.
     */
    public
    system.data.Type<Action> getType() {
        return type;
    }
}