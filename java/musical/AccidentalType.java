package musical;

import music.system.data.Clockable.Regressor;

/**
 * {@code AccidentalType} classifies all note accidental types.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
interface AccidentalType
extends
    Regressor,
    music.system.Type<AccidentalType>
{}