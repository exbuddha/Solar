package musical.performance;

import musical.performance.Instrument.Part;

/**
 * {@code Technique} classifies combinations and sequences of interactions tied to each other over time in order to define widely used and well-defined forms of interacting with an instrument.
 * <p/>
 * This class implementation is in progress.
 *
 * @param <P> the performer type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Technique<P extends Performer<?>>
extends Practice<P>
{
    /**
     * Creates a technique with the specified masculine and feminine units.
     *
     * @param masculine the masculine unit.
     * @param feminine the feminine unit.
     */
    protected
    Technique(
        final system.data.Unit masculine,
        final system.data.Unit feminine
        ) {
        super(masculine, feminine);
    }

    /**
     * Creates a technique with the specified masculine and feminine parts.
     *
     * @param masculine the masculine part.
     * @param feminine the feminine part.
     */
    protected
    Technique(
        final Part masculine,
        final Part feminine
        ) {
        super(masculine, feminine);
    }

    /**
     * Creates a technique with the specified interaction.
     *
     * @param interaction the interaction.
     */
    protected
    Technique(
        final Interaction interaction
        ) {
        this(interaction.masculine, interaction.feminine);
    }

    /**
     * Creates a null technique.
     */
    protected
    Technique() {
        this(null, null);
    }
}