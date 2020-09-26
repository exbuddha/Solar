package musical.performance;

import musical.performance.Instrument.Part;

/**
 * {@code Practice} classifies a parameterized sequence of physical interactions or techniques.
 * <p/>
 * This class implementation is in progress.
 *
 * @param <I> the instrument type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Practice<I extends Instrument>
extends Interaction.Physical<I>
{
    /**
     * Creates a practice with the specified masculine and feminine units.
     *
     * @param masculine the masculine unit.
     * @param feminine the feminine unit.
     */
    protected
    Practice(
        final system.data.Unit masculine,
        final system.data.Unit feminine
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