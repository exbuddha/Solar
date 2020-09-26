package music.classical;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import musical.Constant;
import musical.Interval;
import musical.Standardized;

/**
 * {@code Scale} represents the classical scale.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class Scale
extends musical.Scale
implements
    musical.Scale.Systematic<musical.Scale>,
    Standardized<musical.Scale>
{
    /** The chromatic scale. */
    public static final
    Scale Chromatic
    = new Standard(
        Constant.Scale.ChromaticName,
        MinorSecond,  // S
        MinorSecond,  // S
        MinorSecond,  // S
        MinorSecond,  // S
        MinorSecond,  // S
        MinorSecond,  // S
        MinorSecond,  // S
        MinorSecond,  // S
        MinorSecond,  // S
        MinorSecond,  // S
        MinorSecond,  // S
        MinorSecond   // S
        );

    /** The major scale. */
    public static final
    Scale Major
    = new Standard(
        Constant.Scale.MajorName,
        MajorSecond,  // T
        MajorSecond,  // T
        MinorSecond,  // S
        MajorSecond,  // T
        MajorSecond,  // T
        MajorSecond,  // T
        MinorSecond   // S
        );

    /** The dorian scale. */
    public static final
    Scale Dorian
    = new Standard(
        Constant.Scale.DorianName,
        MajorSecond,  // T
        MinorSecond,  // S
        MajorSecond,  // T
        MajorSecond,  // T
        MajorSecond,  // T
        MinorSecond,  // S
        MajorSecond   // T
        );

    /** The phrygian scale. */
    public static final
    Scale Phrygian
    = new Standard(
        Constant.Scale.PhrygianName,
        MinorSecond,  // S
        MajorSecond,  // T
        MajorSecond,  // T
        MajorSecond,  // T
        MinorSecond,  // S
        MajorSecond,  // T
        MajorSecond   // T
        );

    /** The lydian scale. */
    public static final
    Scale Lydian
    = new Standard(
        Constant.Scale.LydianName,
        MajorSecond,  // T
        MajorSecond,  // T
        MajorSecond,  // T
        MinorSecond,  // S
        MajorSecond,  // T
        MajorSecond,  // T
        MinorSecond   // S
        );

    /** The mixolydian scale. */
    public static final
    Scale Mixolydian
    = new Standard(
        Constant.Scale.MixolydianName,
        MajorSecond,  // T
        MajorSecond,  // T
        MinorSecond,  // S
        MajorSecond,  // T
        MajorSecond,  // T
        MinorSecond,  // S
        MajorSecond   // T
        );

    /** The minor scale. */
    public static final
    Scale Minor
    = new Standard(
        Constant.Scale.MinorName,
        MajorSecond,  // T
        MinorSecond,  // S
        MajorSecond,  // T
        MajorSecond,  // T
        MinorSecond,  // S
        MajorSecond,  // T
        MajorSecond   // T
        );

    /** The locrian scale. */
    public static final
    Scale Locrian
    = new Standard(
        Constant.Scale.LocrianName,
        MinorSecond,  // S
        MajorSecond,  // T
        MajorSecond,  // T
        MinorSecond,  // S
        MajorSecond,  // T
        MajorSecond,  // T
        MajorSecond   // T
        );

    /** The whole tone scale. */
    public static final
    Scale WholeTone
    = new Standard(
        Constant.Scale.WholeToneName,
        MajorSecond,  // T
        MajorSecond,  // T
        MajorSecond,  // T
        MajorSecond,  // T
        MajorSecond,  // T
        MajorSecond   // T
        );

    /** The major pentatonic scale. */
    public static final
    Scale MajorPentatonic
    = new Standard(
        Constant.Scale.MajorPentatonicName,
        MajorSecond,  // T
        MajorSecond,  // T
        MinorThird,   // T + S
        MajorSecond,  // T
        MinorThird    // T + S
        );

    /** The minor pentatonic scale. */
    public static final
    Scale MinorPentatonic
    = new Standard(
        Constant.Scale.MinorPentatonicName,
        MinorThird,   // T + S
        MajorSecond,  // T
        MajorSecond,  // T
        MinorThird,   // T + S
        MajorSecond   // T
        );

    /** The egyptian scale. */
    public static final
    Scale Egyptian
    = new Standard(
        Constant.Scale.EgyptianName,
        MajorSecond,  // T
        MinorThird,   // T + S
        MajorSecond,  // T
        MinorThird,   // T + S
        MajorSecond   // T
        );

    /** The blues major scale. */
    public static final
    Scale BluesMajor
    = new Standard(
        Constant.Scale.BluesMajorName,
        MajorSecond,  // T
        MinorThird,   // T + S
        MajorSecond,  // T
        MajorSecond,  // T
        MinorThird    // T + S
        );

    /** The blues minor scale. */
    public static final
    Scale BluesMinor
    = new Standard(
        Constant.Scale.BluesMinorName,
        MinorThird,   // T + S
        MajorSecond,  // T
        MinorThird,   // T + S
        MajorSecond,  // T
        MajorSecond   // T
        );

    /** The scale symbol. */
    protected
    String symbol;

    /** The root note. */
    protected
    musical.Note root;

    /** The scale interval adjustments. (in cents) */
    protected
    Number[] adjustments;

    /**
     * Creates a scale with the specified symbol, root note, intervals, and interval adjustments, and adjusts the scale.
     *
     * @param symbol the scale symbol.
     * @param root the root note.
     * @param intervals the intervals array.
     * @param adjustments the interval adjustments array.
     */
    public
    Scale(
        final String symbol,
        final Note root,
        final Interval[] intervals,
        final Number[] adjustments
        ) {
        super(intervals);
        this.symbol = symbol;
        this.root = root;
        this.adjustments = adjustments;

        adjust();
    }

    /**
     * Creates a scale with the specified root note, intervals, and interval adjustments, and adjusts the scale.
     *
     * @param root the root note.
     * @param intervals the intervals array.
     * @param adjustments the interval adjustments array.
     */
    public
    Scale(
        final Note root,
        final Interval[] intervals,
        final Number[] adjustments
        ) {
        this(null, root, intervals, adjustments);
    }

    /**
     * Creates a scale with the specified symbol, root note, and intervals, and adjusts the scale.
     * <p/>
     * The intervals are rounded to the nearest semitone and the remainder cents are converted to scale interval adjustment.
     *
     * @param symbol the scale symbol.
     * @param root the root note.
     * @param intervals the intervals array.
     */
    public
    Scale(
        final String symbol,
        final Note root,
        final Interval... intervals
        ) {
        this(symbol, root, getRoundedIntervals(intervals), getAdjustments(intervals));
    }

    /**
     * Creates a scale with the specified root note and intervals, and adjusts the scale.
     * <p/>
     * The intervals are rounded to the nearest semitone and the remainder cents are converted to scale interval adjustment.
     *
     * @param root the root note.
     * @param intervals the intervals array.
     */
    public
    Scale(
        final Note root,
        final Interval... intervals
        ) {
        this(root, getRoundedIntervals(intervals), getAdjustments(intervals));
    }

    /**
     * Creates a scale with the specified symbol and intervals, and adjusts the scale.
     * <p/>
     * The intervals are rounded to the nearest semitone and the remainder cents are converted to scale interval adjustment.
     *
     * @param symbol the scale symbol.
     * @param intervals the intervals array.
     */
    public
    Scale(
        final String symbol,
        final Interval... intervals
        ) {
        this(symbol, null, getRoundedIntervals(intervals), getAdjustments(intervals));
    }

    /**
     * Creates a scale with the specified symbol, root note, and the intervals and interval adjustments arrays of the specified scale, and adjusts the scale.
     * <p/>
     * Scale intervals and adjustments arrays are not cloned.
     *
     * @param symbol the scale symbol.
     * @param root the root note.
     * @param scale the scale.
     */
    public
    Scale(
        final String symbol,
        final Note root,
        final Scale scale
        ) {
        this(symbol, root, scale.intervals, scale.adjustments);
    }

    /**
     * Creates a scale with the specified root note and the intervals and interval adjustments arrays of the specified scale, and adjusts the scale.
     * <p/>
     * Scale intervals and adjustments arrays are not cloned.
     *
     * @param root the root note.
     * @param scale the scale.
     */
    public
    Scale(
        final Note root,
        final Scale scale
        ) {
        this(root, scale.intervals, scale.adjustments);
    }

    /**
     * Creates a scale with the specified symbol and notes, and adjusts the scale.
     * <p/>
     * The intervals are rounded to the nearest semitone and the remainder cents are converted to scale interval adjustment.
     *
     * @param symbol the scale symbol.
     * @param notes the notes.
     */
    public
    Scale(
        final String symbol,
        final musical.Note... notes
        ) {
        this(symbol, null, getIntervals(notes));
        this.root = notes[0];
    }

    /**
     * Creates a scale with the specified notes and adjusts the scale.
     * <p/>
     * The intervals are rounded to the nearest semitone and the remainder cents are converted to scale interval adjustment.
     *
     * @param notes the notes.
     */
    public
    Scale(
        final musical.Note... notes
        ) {
        this(null, notes);
    }

    /**
     * Returns the scale interval adjustment at index {@code i} as integer value, or 0 if either the adjustments array or the element is null.
     *
     * @param i the index.
     * @return the scale interval adjustment.
     */
    public
    int getAdjustment(
        final int i
        ) {
        return adjustments == null ||
               adjustments.length <= i ||
               adjustments[i] == null
               ? 0
               : adjustments[i].intValue();
    }

    /**
     * Returns the total width of the scale intervals in cents including adjustments.
     * <p/>
     * This implementation provides the most accurate result for the total width of the scale in cents.
     *
     * @return the total width of the scale intervals including adjustments.
     */
    protected
    double getAdjustedCents() {
        double min = intervals[0].getCents();
        double max = min;
        double length = min;
        for (int i = 1; i < intervals.length; i++) {
            length += intervals[i].getCents() +
                      (adjustments != null &&
                      i < adjustments.length &&
                      adjustments[i] != null
                      ? adjustments[i].doubleValue()
                      : 0);

            if (length < min)
                min = length;
            else
                if (length > max)
                max = length;
        }

        return max - min;
    }

    /**
     * Adjusts the scale.
     * <p/>
     * This implementation is empty.
     */
    @Override
    public void adjust()
    {}

    /**
     * This implementation returns this scale.
     */
    @Override
    public musical.Scale adjusted(Number... adjustments) {
        return this;
    }

    /**
     * Creates and returns a deep copy of this scale.
     * <p/>
     * This method creates and returns a non-adjusting implementation of {@code Scale}, unless this scale is standard.
     *
     * @return a deep copy of this scale.
     */
    @Override
    public Scale clone() {
        return isStandard(this)
               ? ((Standard) this).clone()
               : new Scale(symbol, (Note) root.clone(), cloneIntervals(intervals), cloneAdjustments(adjustments))
                 {
                     @Override
                     public Scale adjusted(final Number... adjustments) {
                         return this;
                     }
                 };
    }

    @Override
    public musical.Note convert() { return null; }

    @Override
    public musical.Note getNote(final Number i) {
        return new Note(root.getNumber() + i.shortValue()) ;
    }

    /**
     * Returns an iterator over the notes in the scale.
     * <p/>
     * The number of returned notes is exactly one plus the number of the intervals in the scale.
     * <p/>
     * This implementation creates notes with empty adjustment functions.
     *
     * @return the iterator over scale notes.
     */
    @Override
    public Iterator<musical.Note> iterator() {
        return new Iterator<musical.Note>() {
            private Note note;
            private int step = -1;

            @Override
            public boolean hasNext() {
                return step < Scale.this.intervals.length;
            }

            @Override
            public Note next() {
                step++;
                if (step == 0)
                    note = new Note(root);

                else if (step - 1 < intervals.length) {
                    note = new Note(note.getNumber() + intervals[step - 1].getSemitones());
                    if (adjustments != null && step - 1 < adjustments.length)
                        note.setAdjustment(adjustments[step - 1]);
                }
                else
                    throw new NoSuchElementException("There are no more notes in the scale.");

                return note;
            }
        };
    }

    /**
     * Returns true if this scale is standard; otherwise returns false.
     *
     * @return true if this scale is standard, and false otherwise.
     */
    @Override
    public boolean isStandard() {
        return this instanceof Standard;
    }

    /**
     * Returns true if the specified scale is systematic, and false otherwise.
     *
     * @param scale the scale.
     * @return true if scale is systematic, and false otherwise.
     */
    public boolean isSystematic(final Scale scale) {
        return scale instanceof Systematic;
    }

    @Override
    public boolean isSystematic(final musical.Scale scale) {
        return scale instanceof musical.Scale.Systematic;
    }

    @Override
    public Number[] getAdjustments() {
        return adjustments;
    }

    @Override
    public musical.Note getRoot() {
        return root;
    }

    @Override
    public void setRoot(final musical.Note root) {
        super.setRoot(null);
        this.root = root;
    }

    /**
     * Returns the scale intervals array.
     * <p/>
     * This implementation returns a new copy of the array.
     *
     * @return the intervals array.
     */
    public
    Interval[] getIntervals() {
        final Interval[] intervals = new Interval[this.intervals.length];
        for (int i = 0; i < intervals.length; i++)
            intervals[i] = this.intervals[i];

        return intervals;
    }

    /**
     * Sets the scale interval adjustments array.
     * <p/>
     * This implementation sets {@code adjustments} to null if it is empty.
     *
     * @param adjustments the interval adjustments array.
     */
    public
    void setAdjustments(
        final Number... adjustments
        ) {
        this.adjustments = adjustments.length == 0
                         ? null
                         : adjustments;
    }

    /**
     * {@code Standard} represents all standard classical scales.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected static
    class Standard
    extends Systematic
    {
        /**
         * Creates a standard scale with the specified symbol, root note, intervals, and interval adjustments; and adjusts the scale.
         *
         * @param symbol the scale symbol.
         * @param root the root note.
         * @param intervals the intervals array.
         * @param adjustments the interval adjustments array.
         */
        protected
        Standard(
            final String symbol,
            final Note root,
            final Interval[] intervals,
            final Number[] adjustments
            ) {
            super(symbol, root, intervals, adjustments);
            adjust();
        }

        /**
         * Creates a standard scale with the specified symbol, root note, and intervals; and adjusts the scale.
         *
         * @param symbol the scale symbol.
         * @param root the root note.
         * @param intervals the intervals array.
         */
        protected
        Standard(
            final String symbol,
            final Note root,
            final Interval... intervals
            ) {
            this(symbol, root, intervals, null);
        }

        /**
         * Creates a standard scale with the specified symbol and intervals; and adjusts the scale.
         *
         * @param symbol the scale symbol.
         * @param intervals the intervals array.
         */
        protected
        Standard(
            final String symbol,
            final Interval... intervals
            ) {
            this(symbol, null, intervals);
        }

        /**
         * Creates a standard scale with the specified root note and intervals; and adjusts the scale.
         *
         * @param root the root note.
         * @param intervals the intervals array.
         */
        protected
        Standard(
            final Note root,
            final Interval... intervals
            ) {
            this(null, root, intervals);
        }

        /**
         * Given the two specified musical ranges, returns all known chords that have at least one note in each range.
         * <p/>
         * This implementation return null.
         */
        @Override
        public Iterable<? extends Range> apply(musical.Range r1, musical.Range r2) { return null; }

        @Override
        public Standard clone() {
            return root == null
                   ? this
                   : new Standard(symbol, (Note) root.clone(), intervals, adjustments);
        }
    }

    /**
     * {@code Systematic} represents all systematic classical scales.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected static abstract
    class Systematic
    extends Scale
    implements
        BiConsumer<Range, Number>,
        //BiFunction<musical.Range, musical.Range, Iterable<? extends Range>>,
        Consumer<Range>
    {
        /**
         * Creates a systematic scale with the specified symbol, root note, intervals, and interval adjustments; and adjusts the scale.
         *
         * @param symbol the scale symbol.
         * @param root the root note.
         * @param intervals the intervals array.
         * @param adjustments the interval adjustments array.
         */
        protected
        Systematic(
            final String symbol,
            final Note root,
            final Interval[] intervals,
            final Number[] adjustments
            ) {
            super(symbol, root, intervals, adjustments);
        }

        /**
         * Creates a systematic scale with the specified symbol, root note, and intervals; and adjusts the scale.
         *
         * @param symbol the scale symbol.
         * @param root the root note.
         * @param intervals the intervals array.
         */
        protected
        Systematic(
            final String symbol,
            final Note root,
            final Interval... intervals
            ) {
            this(symbol, root, intervals, null);
        }

        /**
         * Creates a systematic scale with the specified symbol and intervals; and adjusts the scale.
         *
         * @param symbol the scale symbol.
         * @param intervals the intervals array.
         */
        protected
        Systematic(
            final String symbol,
            final Interval... intervals
            ) {
            this(symbol, null, intervals);
        }

        /**
         * Creates a systematic classical scale with the specified root and intervals.
         *
         * @param root the root note.
         * @param intervals the intervals array.
         */
        protected
        Systematic(
            final Note root,
            final Interval... intervals
            ) {
            super(null, root, intervals);
        }

        /**
         * Accepts range in the scale at the specified register number, or throws an {@code IllegalArgumentException} if register number is out of range.
         * <p/>
         * By convention, the register numbers starting from 0 up to one less than the scale size are reserved for individual notes in the scale represented as pointer ranges.
         * This is a requirement of some comparison methods such as {@link musical.Scale#contains(musical.Note, int)} and {@link musical.Scale#indexOf(musical.Note)}.
         *
         * @param range the range.
         * @param reg the scale register number.
         * @throws IllegalArgumentException if register number is out of range.
         */
        @Override
        public void accept(
            final Range range,
            final Number reg
            ) {
            final int r = reg.intValue();
            if (r < size())
                throw new IllegalArgumentException("Register number is too small.");
        }

        /**
         * Accepts range in the scale.
         * <p/>
         * This implementation is empty.
         *
         * @param range the range.
         */
        @Override
        public void accept(
            Range range
            ) {}

        /**
         * Given the two specified musical ranges, returns an iterable of scale ranges correlating the two.
         *
         * @param r1 the first range.
         * @param r2 the second range.
         * @return the iterable of related ranges.
         */
        //@Override
        public abstract
        Iterable<? extends Range> apply(
            musical.Range r1,
            musical.Range r2
            );
    }
}