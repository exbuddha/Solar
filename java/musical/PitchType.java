package musical;

import music.system.data.Clockable.Regressor;

/**
 * {@code PitchType} classifies all note pitch types.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
interface PitchType
extends
    Regressor,
    music.system.Type<PitchType>
{}