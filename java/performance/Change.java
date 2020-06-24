package performance;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * {@code Change} classifies, by annotation, interactions in performance for all instruments.
 * It acts as the nucleus for creating and arranging all interaction classes that ultimately define atomic pieces for playing a musical instrument and further more define performance techniques.
 * <p>
 * This meta-data encapsulates the masculine and feminine part classes that respectively represent the human body and instrument parts involved in the interactions that are derived from the change. (change targets)
 * It defines the attributes that identify the effect of its target on the human body, the instrument, and the aural field.
 * A group of {@code Snapshot} subclasses can be attached to the target.
 * Coordination classes set functionality for configuring the target within the change graph.
 * An intelligent algorithm can choose from the encapsulated data in {@code Change} targets that an instrument performer can produce to discern specific attributes governed by the needs of the score, ultimately by expanding (permuting) the target interactions in order to reduce to a collection of available and possible interactions for performing a specific instance in the score.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
@Retention(RUNTIME)
@Target(TYPE)
public
@interface Change
{
    /** The change name. */
    String name()
    default "";

    /** The change description. */
    String description()
    default "";

    /** The masculine unit class of change target. */
    Class<? extends Unit>[] masculine()
    default Unit.class;

    /** The feminine unit class of change target. */
    Class<? extends Unit>[] feminine()
    default Unit.class;

    /** The effect types of change target. */
    Effect[] effect()
    default Effect.Undefined;

    /** The action types of change target. */
    Action[] action()
    default Action.Undefined;

    /** The reaction types of change target. */
    Reaction[] reaction()
    default Reaction.Undefined;

    /** The associated snapshot classes. */
    Class<? extends Snapshot>[] associations()
    default Snapshot.class;

    /** The snapshot group coordination classes. */
    Class<? extends Production<? extends Group>>[] coordinations()
    default Instruction.class;

    /**
     * {@code Classification} identifies changes by a classified name.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Retention(RUNTIME)
    @Target(TYPE)
    public
    @interface Classification
    {
        /** The classification name. */
        String name()
        default "";

        /** The change names and/or descriptions. */
        String[] target()
        default "";
    }
}