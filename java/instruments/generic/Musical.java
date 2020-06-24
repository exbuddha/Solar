package instruments.generic;

import performance.Group;

/**
 * {@code Musical} classifies instruments from a musical angle based on the type of instrument part that is mainly used to perform the instruments.
 * <p>
 * This interface provides an additional wrapper around the already available instrument classes within the {@code instrument} package.
 * In this design, one instrument could be represented in more than one classification.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Musical
extends Group
{
    /**
     * {@code BoardedInstrument} classifies instruments that are performed via an external board, typically connecting to the sounding instrument parts.
     * This classification contains all electronic instruments that are also boarded.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface BoardedInstrument
    extends Musical
    {}

    /**
     * {@code RhythmicInstrument} classifies instruments that produce rhythm sounds in music.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface RhythmicInstrument
    extends Musical
    {}

    /**
     * {@code StringedInstrument} classifies instruments that produce sound in music by the use of vibrating strings.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface StringedInstrument
    extends Musical
    {}

    /**
     * {@code WindInstrument} classifies instruments that produce sound in music by the use of air flow.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface WindInstrument
    extends Musical
    {}
}