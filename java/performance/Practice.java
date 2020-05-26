package performance;

import performance.Instrument.Part;

/**
 * {@code Practice} classifies a parameterized sequence of physical interactions or techniques.
 *
 * @param <I> the instrument type.
 */
public abstract
class Practice<I extends Instrument>
extends Interaction.Physical<I>
{
    protected
    Practice(
        final system.Unit masculine,
        final system.Unit feminine
        ) {
        super(masculine, feminine);
    }

    /**
     * Creates a practice with the specified masculine and feminine parts.
     *
     * @param masculine the masculine part.
     * @param feminine the feminine part.
     */
    public
    Practice(
        final Part masculine,
        final Part feminine
        ) {
        super(masculine, feminine);
    }
}