package musical;

import music.system.data.Clockable.Progressor;

/**
 * {@code NoteType} classifies all note types.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
interface NoteType
extends
    Progressor,
    music.system.Type<NoteType>
{}