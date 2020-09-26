package musical;

import java.util.Iterator;

/**
 * {@code Pulse} classifies noticeable expressions in sound synthesis and music.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Pulse
extends Iterator<Range>
{
    /**
     * Returns the previous range in pulse.
     *
     * @return the previous range.
     */
    public
    Range prev();
}