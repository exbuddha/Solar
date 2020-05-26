package musical;

import system.data.Fraction;

/**
 * {@code Duration} represents a time interval in music relative to the quarter note time value.
 */
public
class Duration
extends Fraction
implements
    Cloneable,
    Symbolized<String>,
    music.system.Type<Duration>
{
    public static final
    Duration Immediate;

    /** The octuple whole note. */
    public static final
    Duration OctupleWhole;

    /** The quadruple whole note. */
    public static final
    Duration QuadrupleWhole;

    /** The double whole note. */
    public static final
    Duration DoubleWhole;

    /** The whole note. */
    public static final
    Duration Whole;

    /** The half note. */
    public static final
    Duration Half;

    /** The quarter note. */
    public static final
    Duration Quarter;

    /** The eighth note. */
    public static final
    Duration Eighth;

    /** The sixteenth note. */
    public static final
    Duration Sixteenth;

    /** The thirty-second note. */
    public static final
    Duration ThirtySecond;

    /** The sixty-fourth note. */
    public static final
    Duration SixtyFourth;

    /** The hundred twenty-eighth note. */
    public static final
    Duration HundredTwentyEighth;

    /** The two hundred fifty-sixth note. */
    public static final
    Duration TwoHundredFiftySixth;

    /** The dotted note. */
    public static final
    Duration Dotted;

    /** The double-dotted note. */
    public static final
    Duration DoubleDotted;

    /** The dotted whole note. */
    public static final
    Duration DottedWhole;

    /** The double-dotted whole note. */
    public static final
    Duration DoubleDottedWhole;

    /** The dotted half note. */
    public static final
    Duration DottedHalf;

    /** The double-dotted half note. */
    public static final
    Duration DoubleDottedHalf;

    /** The dotted quarter note. */
    public static final
    Duration DottedQuarter;

    /** The double-dotted quarter note. */
    public static final
    Duration DoubleDottedQuarter;

    /** The dotted eighth note. */
    public static final
    Duration DottedEighth;

    /** The double-dotted eighth note. */
    public static final
    Duration DoubleDottedEighth;

    /** The dotted sixteenth note. */
    public static final
    Duration DottedSixteenth;

    /** The double-dotted sixteenth note. */
    public static final
    Duration DoubleDottedSixteenth;

    /** The dotted thirty-second note. */
    public static final
    Duration DottedThirtySecond;

    /** The dotted sixty-fourth note. */
    public static final
    Duration DottedSixtyFourth;

    static
    {
        Immediate = new Standard("", 0, (short) 1);

        OctupleWhole = new Standard(Constant.Duration.OctupleWhole, 32, (short) 1);

        QuadrupleWhole = new Standard(Constant.Duration.QuadrupleWhole, 16, (short) 1);

        DoubleWhole = new Standard(Constant.Duration.DoubleWhole, 8, (short) 1);

        Whole = new Standard(Constant.Duration.Whole, 4, (short) 1);

        Half = new Standard(Constant.Duration.Half, 2, (short) 1);

        Quarter = new Standard(Constant.Duration.Quarter, 1, (short) 1);

        Eighth = new Standard(Constant.Duration.Eighth, 1, (short) 2);

        Sixteenth = new Standard(Constant.Duration.Sixteenth, 1, (short) 4);

        ThirtySecond = new Standard(Constant.Duration.ThirtySecond, 1, (short) 8);

        SixtyFourth = new Standard(Constant.Duration.SixtyFourth, 1, (short) 16);

        HundredTwentyEighth = new Standard(Constant.Duration.HundredTwentyEighth, 1, (short) 32);

        TwoHundredFiftySixth = new Standard(Constant.Duration.TwoHundredFiftySixth, 1, (short) 64);

        Dotted = new Standard(Constant.Duration.Dotted, 3, (short) 2);

        DoubleDotted = new Standard(Constant.Duration.DoubleDotted, 7, (short) 4);

        DottedWhole = new Standard(Constant.Duration.DottedWhole, 4, (short) 1, Dotted);

        DoubleDottedWhole = new Standard(Constant.Duration.DoubleDottedWhole, 4, (short) 1, DoubleDotted);

        DottedHalf = new Standard(Constant.Duration.DottedHalf, 2, (short) 1, Dotted);

        DoubleDottedHalf = new Standard(Constant.Duration.DoubleDottedHalf, 2, (short) 1, DoubleDotted);

        DottedQuarter = new Standard(Constant.Duration.DottedWhole, 1, (short) 1, Dotted);

        DoubleDottedQuarter = new Standard(Constant.Duration.DoubleDottedWhole, 1, (short) 1, DoubleDotted);

        DottedEighth = new Standard(Constant.Duration.DottedWhole, 1, (short) 2, Dotted);

        DoubleDottedEighth = new Standard(Constant.Duration.DoubleDottedWhole, 1, (short) 2, DoubleDotted);

        DottedSixteenth = new Standard(Constant.Duration.DottedWhole, 1, (short) 4, Dotted);

        DoubleDottedSixteenth = new Standard(Constant.Duration.DoubleDottedWhole, 1, (short) 4, DoubleDotted);

        DottedThirtySecond = new Standard(Constant.Duration.DottedWhole, 1, (short) 8, Dotted);

        DottedSixtyFourth = new Standard(Constant.Duration.DottedWhole, 1, (short) 16, Dotted);
    }

    /** The duration symbol. */
    protected
    String symbol;

    /** The number of beats. */
    protected final
    int beats;

    /** The beat units. */
    protected final
    short units;

    /**
     * Creates a duration, as a fraction, with the specified symbol, beats, and units.
     *
     * @param symbol the symbol.
     * @param beats the number of beat units.
     * @param units the number of units in a beat.
     */
    public
    Duration(
        final String symbol,
        final int beats,
        final short units
        ) {
        super(beats, units);
        this.symbol = symbol;
        this.beats = beats;
        this.units = units;
    }

    /**
     * Creates a duration, as a fraction, with the specified beats and units.
     *
     * @param beats the number of beat units.
     * @param units the number of units in a beat.
     */
    public
    Duration(
        final int beats,
        final short units
        ) {
        this(null, beats, units);
    }

    /**
     * Returns true if the specified duration is standard, and false otherwise.
     *
     * @param duration the duration.
     * @return true if duration is standard, and false otherwise.
     */
    public static
    boolean isStandard(
        final Duration duration
        ) {
        return duration instanceof Standard;
    }

    @Override
    public Duration clone() {
        return isStandard(this)
               ? ((Standard) this).clone()
               : new Duration(symbol, beats, units);
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public boolean is(final system.Type<Duration> type) {
        // Durations are not associated with a different duration type
        return false;
    }

    @Override
    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the number of beats.
     *
     * @return the beats.
     */
    public
    int getBeats() {
        return beats;
    }

    /**
     * Returns the beat units.
     *
     * @return the beat units.
     */
    public
    short getUnits() {
        return units;
    }

    /**
     * {@code Standard} represents all standard durations.
     */
    protected static
    class Standard
    extends Duration
    {
        /** The adjustment coefficient. */
        protected final
        Duration adjustment;

        /**
         * Creates a standard duration with the specified symbol, beats, units, and adjustment.
         *
         * @param symbol the symbol.
         * @param beats the number of beat units.
         * @param units the number of units in a beat.
         * @param adjustment the adjustment duration.
         */
        protected
        Standard(
            final String symbol,
            final int beats,
            final short units,
            final Duration adjustment
            ) {
            super(symbol, beats * adjustment.beats, (short) (units * adjustment.units));
            this.adjustment = adjustment;
        }

        /**
         * Creates a standard duration with the specified symbol, beats, and units.
         *
         * @param symbol the symbol.
         * @param beats the number of beat units.
         * @param units the number of units in a beat.
         */
        protected
        Standard(
            final String symbol,
            final int beats,
            final short units
            ) {
            this(symbol, beats, units, new Duration(1, (short) 1));
        }

        public Standard clone() {
            return this == Quarter ||
                   this == Eighth ||
                   this == Sixteenth ||
                   this == Half ||
                   this == Whole ||
                   this == ThirtySecond ||
                   this == SixtyFourth ||
                   this == DottedQuarter ||
                   this == DottedEighth ||
                   this == DottedSixteenth ||
                   this == DottedHalf ||
                   this == DottedWhole ||
                   this == HundredTwentyEighth ||
                   this == TwoHundredFiftySixth ||
                   this == DoubleWhole ||
                   this == QuadrupleWhole ||
                   this == OctupleWhole ||
                   this == DottedThirtySecond ||
                   this == DottedSixtyFourth ||
                   this == DoubleDottedWhole ||
                   this == DoubleDottedHalf ||
                   this == DoubleDottedQuarter ||
                   this == DoubleDottedEighth ||
                   this == DoubleDottedSixteenth ||
                   this == Dotted ||
                   this == DoubleDotted
                   ? this
                   : new Standard(symbol, beats, units);
        }
    }
}