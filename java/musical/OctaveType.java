package musical;

import music.system.data.Clockable.Templator;

/**
 * {@code OctaveType} classifies all note octave types.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
interface OctaveType
extends
    Templator,
    music.system.Type<OctaveType>
{}