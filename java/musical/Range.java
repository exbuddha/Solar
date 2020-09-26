package musical;

import java.util.Iterator;
import java.util.function.Supplier;

import music.system.DataPoint;

/**
 * {@code Range} classifies spectrums in music emphasizing a set of selected frequencies from a larger spectrum, with arbitrary degrees of freedom from the time and/or the pitch axis.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Range
extends Spectrum
{
    /**
     * {@code Band} categorizes pitch of musical sounds relative to instrument or hearing frequency ranges.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    enum Band
    {
        Over,

        High,

        Mid,

        Low,

        Below;
    }

    /**
     * {@code Relation} represents linearly recognized relations among consecutive ranges, such as phrases.
     * <p/>
     * By convention, the supplied data point has coordinates that are themselves vectors.
     * Each coordinate identifies the sound quality inter-relation to, or of, the previous, the current, and the following localized range across their respective pulses.
     * Sound qualities combine an arbitrary, though usually small, number of spectrum effects identifying the vibrational characteristics of their target incident.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Relation
    extends Supplier<DataPoint<Iterator<Effect>, Iterator<Effect>, Iterator<Effect>>>
    {}
}