package musical;

import java.util.Iterator;

/**
 * {@code Pulse} classifies noticeable expressions in sound synthesis and music.
 */
public
interface Pulse
extends Iterator<Range>
{
    /**
     * Returns the previous expression in pulse.
     */
    public
    Range prev();
}