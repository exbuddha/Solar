package performance;

/**
 * {@code Reaction} categorizes the types of reactions that instrument parts can show to the actions of body parts.
 */
public
enum Reaction
implements
    Production.ReactionType,
    Snapshot
{
    /** Burst reaction negligible in time. */
    Instantaneous(),

    /** Lasting reaction noticeable in time until another reaction occurs or the reaction naturally decays. */
    Continuous(),

    /** Lasting reaction that diminishes as soon as the effective action stops or changes. */
    Sustained(Continuous),

    /** Repeats the same reaction periodically over time. */
    Repetitive(Sustained),

    /** Lasting reaction after the effective action stops independently (or semi-independently) of the action type. */
    Lasting(Continuous),

    /** Decreasing in energy output after the effective action stops. (Naturally applies to productive effect types only) */
    Decaying(Continuous),

    /** Cannot be associated with any physical instrument part. */
    NonPhysical(),

    /** Undefined reaction. */
    Undefined();

    /** The reaction type. */
    private final
    system.Type<Reaction> type;

    /**
     * Creates a reaction of the specified type.
     *
     * @param type the reaction type.
     */
    Reaction(
        final Reaction type
        ) {
        this.type = type;
    }

    /**
     * Creates an untyped reaction.
     */
    Reaction() {
        this(null);
    }

    @Override
    public Object apply(Conductor.ExecutionModel model, Unit instrument) { return null; }

    /**
     * Returns true if the specified reaction type is of this type, and false otherwise.
     *
     * @param type the other type.
     * @return true if the specified reaction type is of this type, and false otherwise.
     */
    @Override
    public boolean is(final system.Type<Reaction> type) {
        if (type == null)
            return false;

        if (type == this)
            return true;

        return this.type != null && this.type.is(type);
    }

    /**
     * Returns the type associated with this reaction type.
     *
     * @return the reaction type.
     */
    public
    system.Type<Reaction> getType() {
        return type;
    }
}