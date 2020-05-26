package performance;

/**
 * {@code Effect} categorizes the types of effects that changes, and their interactions, can produce in the aural field.
 */
public
enum Effect
implements
    Production.EffectType,
    Snapshot
{
    /** Uniquely and authoritatively affects the aural field in a way that no other unitary change (on the same instrument part) can coexist with. */
    Unitary(),

    /** Affects the aural field only when the instrument part produces, or is producing, a unitary change; and not otherwise. */
    Secondary(),

    /** Does not affect the aural field in any way. (Classifies silent changes with actions intended as a preparation for another change) */
    Tertiary(),

    /** Produces new sound and excites the aural field. */
    Productive(),

    /** Reduces active sound and quiets the aural field. */
    Reductive(),

    /** Intended to produce melody by altering pitch of sound. */
    Melodic(),

    /** Alters pitch of sound by itself alone. */
    Pitched(Melodic),

    /** Alters to a pitch that depends on the state of the instrument part or is in collaboration with another change. */
    Copitched(Pitched),

    /** Alters pitch in a sliding manner sounding all available pitches up/down to the final pitch. */
    Glide(Pitched),

    /** Alters pitch in an undetermined manner or is not intended to have an exact pitch. */
    Undetermined(Pitched),

    /** Does not alter pitch of sound by itself alone and the pitch is totally determined by another change or an instrument part state. */
    Unpitched(Melodic),

    /** Alters pitch only if the instrument part or its larger containing body is in a productive state that doesn't end by the effect of this change. */
    Subtle(Unpitched),

    /** Repeats the same effect periodically over time. */
    Repetitive(),

    /** Alternates pitch or quality of sound. */
    Alternative(Repetitive),

    /** Vibrates pitch or quality of sound. */
    Vibrational(Repetitive),

    /** Alters the active tone quality of sound. */
    Tonal(),

    /** Does not alter any physical characteristic of the aural field. */
    NonPhysical(),

    /** Undefined effect. */
    Undefined();

    /** The effect type. */
    private final
    system.Type<Effect> type;

    /**
     * Creates an effect of the specified type.
     *
     * @param type the effect type.
     */
    Effect(
        final Effect type
        ) {
        this.type = type;
    }

    /**
     * Creates an untyped effect.
     */
    Effect() {
        this(null);
    }

    @Override
    public Object apply(Conductor.ExecutionModel model, Unit instrument) { return null; }

    /**
     * Returns true if the specified effect type is of this type, and false otherwise.
     *
     * @param type the other type.
     * @return true if the specified effect type is of this type, and false otherwise.
     */
    @Override
    public boolean is(final system.Type<Effect> type) {
        if (type == null)
            return false;

        if (type == this)
            return true;

        return this.type != null && this.type.is(type);
    }

    /**
     * Returns the type associated with this effect type.
     *
     * @return the effect type.
     */
    public
    system.Type<Effect> getType() {
        return type;
    }
}