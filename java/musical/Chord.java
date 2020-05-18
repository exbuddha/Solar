package musical;

import java.util.SortedSet;

import music.system.data.Clockable.ProgressiveDataPoint;
import music.system.data.Clockable.Progressor;
import music.system.data.Clockable.Templator;
import music.system.data.Convertible;
import system.Type;

/**
 * {@code Chord} classifies musical ranges that connote harmony in music.
 */
public
interface Chord
extends
    Interval.Sequence,
    Localizable,
    Range
{
    /**
     * Returns the chord as ordered set of progresses.
     *
     * @return the progression.
     */
    public
    OrderedSet getProgression();

    /**
     * {@code Progress} represents the measure of change in chord and song construction.
     */
    public abstract
    class Progress<T extends Number>
    extends Interval
    implements
        Convertible<Progress<?>>,
        ProgressiveDataPoint<Byte, Templator, Progressor>,
        Range,
        Templator
    {
        /**
         * Creates a progression with the specified variance and quantification.
         * <p>
         * If quantification is empty, null is used for interval cents amount.
         *
         * @param v the progression variance.
         * @param q the progression quantification.
         */
        public
        Progress(
            final Progressor v,
            final T... q
            ) {
            super(q.length == 0 ? null : q[0]);
        }

        /**
         * Creates a progression with the specified variance and null quantification.
         *
         * @param v the progression variance.
         */
        public
        Progress(
            final Progressor v
            ) {
            super(null);
        }

        /**
         * Creates a progression with null variance and the specified quantification.
         * <p>
         * If quantification is empty, null is used for interval cents amount.
         *
         * @param q the progression quantification.
         */
        public
        Progress(
            final T... q
            ) {
            this(null, q);
        }

        /**
         * Returns the progression quantification.
         *
         * @return the quantification.
         */
        public abstract
        T[] getQuantification();

        /**
         * Sets the progression quantification.
         *
         * @param q the quantification.
         */
        public abstract
        void setQuantification(
            T... q
            );

        /**
         * Returns the progression variance.
         *
         * @return the variance.
         */
        public abstract
        Progressor getVariance();

        /**
         * Sets the progression variance.
         *
         * @param v the variance.
         */
        public abstract
        void setVariance(
            Progressor v
            );

        @Override
        public Byte x() {
            return this.getOrder();
        }

        @Override
        public Templator y() {
            return this.getMode();
        }

        @Override
        public Progressor z() {
            return getVariance();
        }
    }

    /**
     * {@code Mode} categorizes all known chord modes.
     * <p>
     * Chord modes consist of all individual parts that define the structure or mode of a chord excluding the numeric intervallic relations.
     */
    public
    enum Mode
    implements
        Symbolized<String>,
        Templator,
        music.system.Type<Mode>
    {
        Altered,

        Augmented,

        Diminished,

        Dominant,

        DominantAltered,

        Fifth,

        HalfDiminished,

        Lydian,

        LydianDominant,

        Major,

        Minor,

        Suspended;

        String symbol;

        private
        Mode(
            final String symbol
            ) {
            this.symbol = symbol;
        }

        private
        Mode() {
            this("");
        }

        @Override
        public boolean is(final Type<Mode> type) {
            return this == type;
        }

        @Override
        public String getSymbol() {
            return symbol == null
                   ? toString()
                   : symbol;
        }

        @Override
        public void setSymbol(final String symbol) {
            this.symbol = symbol;
        }
    }

    /**
     * {@code OrderedSet} classifies ordered set of progresses for chords.
     * <p>
     * Ordered sets are equivalent of java standard {@link SortedSet} with the twist that each element in the set can be attached to other adjacent elements creating a group with specific significance.
     */
    public
    interface OrderedSet
    extends SortedSet<Progress<?>>
    {}
}