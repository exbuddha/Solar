package musical;

import music.system.data.Clockable.ProgressiveDataPoint;
import music.system.data.Clockable.Progressor;
import music.system.data.Clockable.Regressor;
import music.system.data.Clockable.Templator;
import music.system.data.Ordered;

/**
 * {@code Interval} represents a musical interval which is a measure of distance (pitch difference) between notes.
 */
public
class Interval
implements
    Cloneable,
    Comparable<Interval>,
    Ordered<Byte>,
    Regressor,
    Spectrum,
    Symbolized<String>,
    music.system.Type<Interval>
{
    /** The perfect unison interval. (0 cents) */
    public static final
    Interval Unison = new Standard(Constant.Interval.PerfectUnison, (short) 0);

    /** The minor second interval. (100 cents) */
    public static final
    Interval MinorSecond = new Standard(Constant.Interval.MinorSecond, (short) 100);

    /** The major second interval. (200 cents) */
    public static final
    Interval MajorSecond = new Standard(Constant.Interval.MajorSecond, (short) 200);

    /** The minor third interval. (300 cents) */
    public static final
    Interval MinorThird = new Standard(Constant.Interval.MinorThird, (short) 300);

    /** The major third interval. (400 cents) */
    public static final
    Interval MajorThird = new Standard(Constant.Interval.MajorThird, (short) 400);

    /** The perfect fourth interval. (500 cents) */
    public static final
    Interval PerfectFourth = new Standard(Constant.Interval.PerfectFourth, (short) 500);

    /** The augmented fourth interval. (600 cents) */
    public static final
    Interval AugmentedFourth = new Standard(Constant.Interval.AugmentedFourth, (short) 600);

    /** The diminished fifth interval. (600 cents) */
    public static final
    Interval DiminishedFifth = new Standard(Constant.Interval.DiminishedFifth, (short) 600);

    /** The perfect fifth interval. (700 cents) */
    public static final
    Interval PerfectFifth = new Standard(Constant.Interval.PerfectFifth, (short) 700);

    /** The minor sixth interval. (800 cents) */
    public static final
    Interval MinorSixth = new Standard(Constant.Interval.MinorSixth, (short) 800);

    /** The major sixth interval. (900 cents) */
    public static final
    Interval MajorSixth = new Standard(Constant.Interval.MajorSixth, (short) 900);

    /** The minor seventh interval. (1000 cents) */
    public static final
    Interval MinorSeventh = new Standard(Constant.Interval.MinorSeventh, (short) 1000);

    /** The major seventh interval. (1100 cents) */
    public static final
    Interval MajorSeventh = new Standard(Constant.Interval.MajorSeventh, (short) 1100);

    /** The perfect octave interval. (1200 cents) */
    public static final
    Interval Octave = new Standard(Constant.Interval.PerfectOctave, (short) 1200);

    /** The quarter tone interval. (50 cents) */
    public static final
    Interval QuarterTone = new Interval((short) 50);

    static
    {
        Unison.setMode(Mode.Perfect);
        MinorSecond.setMode(Mode.Minor);
        MajorSecond.setMode(Mode.Major);
        MinorThird.setMode(Mode.Minor);
        MajorThird.setMode(Mode.Major);
        PerfectFourth.setMode(Mode.Perfect);
        AugmentedFourth.setMode(Mode.Augmented);
        DiminishedFifth.setMode(Mode.Diminished);
        PerfectFifth.setMode(Mode.Perfect);
        MinorSixth.setMode(Mode.Minor);
        MajorSixth.setMode(Mode.Major);
        MinorSeventh.setMode(Mode.Minor);
        MajorSeventh.setMode(Mode.Major);
        Octave.setMode(Mode.Perfect);

        QuarterTone.setMode(Mode.Quarter);
    }

    /** The interval symbol. */
    protected
    String symbol;

    /** The interval width. (in cents) */
    protected final
    short cents;

    private
    Mode mode;

    /**
     * Creates an interval with the specified symbol and cents.
     *
     * @param symbol the symbol.
     * @param cents the cents.
     */
    protected
    Interval(
        final String symbol,
        final short cents
        ) {
        this.symbol = symbol;
        this.cents = cents;
    }

    /**
     * Creates an interval with the specified cents.
     *
     * @param cents the cents.
     */
    protected
    Interval(
        final short cents
        ) {
        this.cents = cents;
    }

    /**
     * Creates an interval with the specified symbol and cents.
     * <p>
     * This constructor calls {@link Number#shortValue()} on the cents.
     *
     * @param symbol the symbol.
     * @param cents the cents.
     */
    public
    Interval(
        final String symbol,
        final Number cents
        ) {
        this(symbol, cents.shortValue());
    }

    /**
     * Creates an interval with null symbol and the specified cents.
     * <p>
     * This constructor calls {@link Number#shortValue()} on the cents.
     *
     * @param cents the cents.
     */
    public
    Interval(
        final Number cents
        ) {
        this(cents.shortValue());
    }

    /**
     * Returns true if the specified interval is standard, and false otherwise.
     *
     * @param interval the interval.
     * @return true if interval is standard, and false otherwise.
     */
    public static
    boolean isStandard(
        final Interval interval
        ) {
        return interval instanceof Standard;
    }

    /**
     * Creates and returns a new reverse interval with the specified symbol, and width equal to the specified interval.
     *
     * @param symbol the symbol.
     * @param interval the interval.
     * @return the reverse interval.
     */
    public static
    Interval createReverse(
        final String symbol,
        final Interval interval
        ) {
        return new Interval(symbol, -interval.cents);
    }

    /**
     * Creates and returns a new reverse interval with symbol and width equal to the specified interval.
     *
     * @param interval the interval.
     * @return the reverse interval.
     */
    public static
    Interval createReverse(
        final Interval interval
        ) {
        return new Interval(interval.symbol, -interval.cents);
    }

    /**
     * Returns true if the specified interval is equal to this interval, ignoring the direction (sign) of both intervals, and false otherwise.
     *
     * @param interval the interval.
     * @return true if the interval is equal to this interval ignoring direction, and false otherwise.
     */
    public
    boolean equalsIgnoreDirection(
        final Interval interval
        ) {
        return Math.abs(cents) == Math.abs(interval.cents);
    }

    /**
     * Returns the semitones in the interval by rounding the cents amount.
     *
     * @return the semitones.
     */
    public
    byte getSemitones() {
        return (byte) (Integer.signum(cents) * Math.round(Math.abs(cents) / 100.));
    }

    public
    ProgressiveDataPoint<Byte, Templator, Progressor> newDataPoint() {
        return new ProgressiveDataPoint<Byte, Templator, Progressor>()
        {
            @Override
            public Byte x() {
                return Interval.this.getOrder();
            }

            @Override
            public Mode y() {
                return Interval.this.getMode();
            }

            @Override
            public Progressor z() {
                return null;
            }
        };
    }

    /**
     * Creates and returns a deep copy of this interval.
     *
     * @return a deep copy of this interval.
     */
    @Override
    public Interval clone() {
        return isStandard(this)
               ? ((Standard) this).clone()
               : new Interval(symbol, cents);
    }

    @Override
    public int compareTo(final Interval interval) {
        return (int) (cents - interval.cents);
    }

    /**
     * Returns true if the specified object is an instance of the {@code Interval} class and has the same amount of cents as this interval, and false otherwise.
     *
     * @param obj the object.
     * @return true if the object is an equal interval, and false otherwise.
     */
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Interval &&
               cents == ((Interval) obj).cents;
    }

    @Override
    public Byte getOrder() {
        return getSemitones();
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public boolean is(final system.Type<Interval> type) {
        return equals(type);
    }

    @Override
    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol == null
               ? (cents > 0 ? "+" : "") + cents + " cents"
               : symbol;
    }

    /**
     * Returns the cents in the interval.
     *
     * @return the cents in the interval.
     */
    public
    short getCents() {
        return cents;
    }

    public
    Mode getMode() {
        return mode;
    }

    public
    void setMode(
        final Mode mode
        ) {
        this.mode = mode;
    }

    public
    enum Mode
    implements
        Symbolized<String>,
        Templator,
        music.system.Type<Mode>
    {
        Augmented,

        Diminished,

        Major,

        Minor,

        Perfect,

        Quarter;

        @Override
        public boolean is(final system.Type<Mode> type) {
            return this == type;
        }

        @Override
        public String getSymbol() {
            return toString();
        }

        /**
         * This implementation is empty.
         */
        @Override
        public void setSymbol(String symbol) {}
    }

    public
    interface Sequence
    {
        public
        Interval[] getIntervals();
    }

    /**
     * {@code Standard} represents all standard intervals.
     */
    protected static
    class Standard
    extends Interval
    {
        /**
         * Creates a standard interval with the specified symbol and cents.
         *
         * @param symbol the symbol.
         * @param cents the cents.
         */
        protected
        Standard(
            final String symbol,
            final int cents
            ) {
            super(symbol, cents);
        }

        /**
         * Creates and returns a deep copy of this standard interval.
         *
         * @return a deep copy of this standard interval.
         */
        @Override
        public Standard clone() {
            return this == PerfectFifth ||
                   this == Octave ||
                   this == PerfectFourth ||
                   this == MajorThird ||
                   this == MinorThird ||
                   this == DiminishedFifth ||
                   this == AugmentedFourth ||
                   this == MajorSecond ||
                   this == MinorSecond ||
                   this == MajorSeventh ||
                   this == MinorSeventh ||
                   this == MajorSixth ||
                   this == MinorSixth ||
                   this == QuarterTone
                   ? this
                   : new Standard(symbol, cents);
        }

        /**
         * This implementation is empty.
         */
        @Override
        public void setMode(Mode mode) {}
    }
}