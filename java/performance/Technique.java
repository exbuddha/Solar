package performance;

import performance.Instrument.Part;

/**
 * {@code Technique} classifies combinations and sequences of interactions tied to each other over time in order to define widely used and well-defined forms of interacting with an instrument.
 *
 * @param <P> the performer type.
 */
public abstract
class Technique<P extends Performer<?>>
extends Practice<P>
{
    protected
    Technique(
        final system.Unit masculine,
        final system.Unit feminine
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