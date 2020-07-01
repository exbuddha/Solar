package musical;

import static musical.Constant.Duration.*;
import static system.data.Constant.NegativeDuration;
import static system.data.Constant.NullAdjustment;
import static system.data.Constant.OperationImpossible;
import static system.data.Constant.StandardObjectInoperable;

import java.time.temporal.TemporalAmount;
import java.util.Comparator;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

import system.data.Constant;
import system.data.Fraction;
import system.data.Lambda;
import system.data.Operable;
import system.data.Symbolized;
import system.data.Unique;

/**
 * {@code Duration} represents a time interval in music relative to the whole note time value.
 * <p>
 * Musical durations are made up of beats and beat units.
 * Beats is the numerator value of the fraction that represents the duration and beat units is the denominator value.
 * Duration values cannot be negative.
 * The standard duration types provide adjustment coefficients representing the dotted notes, triplets, etc.
 * The standard whole note has the beats amount of 1 and the beat units amount of 1, constituting the unit of duration equal to the amount of 1.
 * <p>
 * All durations are Java number types and represent the fractional value of their time interval in music scores.
 * This class defines the well-known stem types in classical music that are statically present as standard durations.
 * These static durations are called singletons.
 * <p>
 * The standard durations, that all singletons are types of, are special forms of durations that behave differently from the regular duration types.
 * There are specialized methods re-implemented for standard durations, for which the results are treated differently than the base implementations of those methods.
 * These methods are named {@code plus}, {@code minus}, {@code times}, {@code by}, {@code inverted}, {@code adjusted}, and {@code unadjust}.
 * Calling these methods on non-static standard durations (non-singletons), as well as regular duration objects, is equivalent to calling {@code add}, {@code subtract}, {@code multiply}, {@code divide}, {@code invert}, {@code adjust}, and {@code unadjust}.
 * If a standard duration is added to, subtracted from, divided, multiplied, or inverted through these special methods, the result will always be a standard type also.
 * If the standard duration, however, is a singleton, a copy of that singleton will be created and returned as the result of the operation.
 * Operating on standard durations only affects the numerator and denominator while the adjustment remains unchanged, except for the {@code adjust} or {@code adjusted} methods where both the numerator and denominator values and the adjustment value are changed.
 * <p>
 * Furthermore, if a standard duration, operated on by the methods mentioned above, matches the value of a statically defined standard type then that static type is returned.
 * This way operating on standard durations will always return values from the domain of statically defined types or singletons, when one is available, or will return the same standard type that can automatically transform into a singleton at any point in logic.
 * <p>
 * Methods in this class implementation are not thread-safe.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class Duration
extends Fraction
implements
    Adjustable,
    Adjusting<Duration, Fraction>,
    Function<Tempo, TemporalAmount>,
    Symbolized<String>,
    music.system.Type<Duration>,
    Unadjustable,
    Unadjusting<Duration>,
    Unit
{
    /** A constant holding the maximum value a {@code Duration} can have, 2<sup>31</sup>-1. */
    public static final
    int MAX_VALUE = Integer.MAX_VALUE;

    /** A constant holding the minimum value a {@code Duration} can have, 0. */
    public static final
    int MIN_VALUE = 0;

    /** The {@code Class} instance representing the type {@code Duration}. */
    public static final
    Class<Duration> TYPE = Duration.class;

    /** The octuple whole note. */
    public static final
    Singleton OctupleWhole = new Singleton(OctupleWholeSym, 8, (short) 1, (short) 1);

    /** The quadruple whole note. */
    public static final
    Singleton QuadrupleWhole = new Singleton(QuadrupleWholeSym, 4, (short) 1, (short) 1);

    /** The double whole note. */
    public static final
    Singleton DoubleWhole = new Singleton(DoubleWholeSym, 2, (short) 1, (short) 1);

    /** The whole note. */
    public static final
    Singleton Whole = new Singleton(WholeSym, 1, (short) 1, (short) 1);

    /** The half note. */
    public static final
    Singleton Half = new Singleton(HalfSym, 1, (short) 2, (short) 2);

    /** The quarter note. */
    public static final
    Singleton Quarter = new Singleton(QuarterSym, 1, (short) 4, (short) 4);

    /** The eighth note. */
    public static final
    Singleton Eighth = new Singleton(EighthSym, 1, (short) 8, (short) 8);

    /** The sixteenth note. */
    public static final
    Singleton Sixteenth = new Singleton(SixteenthSym, 1, (short) 16, (short) 16);

    /** The thirty-second note. */
    public static final
    Singleton ThirtySecond = new Singleton(ThirtySecondSym, 1, (short) 32, (short) 32);

    /** The sixty-fourth note. */
    public static final
    Singleton SixtyFourth = new Singleton(SixtyFourthSym, 1, (short) 64, (short) 64);

    /** The hundred twenty-eighth note. */
    public static final
    Singleton HundredTwentyEighth = new Singleton(HundredTwentyEighthSym, 1, (short) 128, (short) 128);

    /** The two hundred fifty-sixth note. */
    public static final
    Singleton TwoHundredFiftySixth = new Singleton(TwoHundredFiftySixthSym, 1, (short) 256, (short) 256);

    /** Grace note. (instantanous) */
    public static final
    Singleton Grace = new Singleton(GraceSym, 0, (short) 8);

    /** The unit. (adjustment) */
    public static final
    Fraction Unit = new Fraction.Singleton(UnitSym, 1);

    /** The dotted note. (adjustment) */
    public static final
    Fraction.Singleton Dotted = new Fraction.Singleton(DottedSym, 3, (short) 2);

    /** The double-dotted note. (adjustment) */
    public static final
    Fraction.Singleton DoubleDotted = new Fraction.Singleton(DoubleDottedSym, 7, (short) 4);

    /** The triple-dotted note. (adjustment) */
    public static final
    Fraction.Singleton TripleDotted = new Fraction.Singleton(TripleDottedSym, 15, (short) 8);

    /** The triplet note. (adjustment) */
    public static final
    Fraction.Singleton Triplet = new Fraction.Singleton(TripletSym, 1, (short) 3);

    /** The quintuplet note. (adjustment) */
    public static final
    Fraction.Singleton Quintuplet = new Fraction.Singleton(QuintupletSym, 1, (short) 5);

    /** The sextuplet note. (adjustment) */
    public static final
    Fraction.Singleton Sextuplet = new Fraction.Singleton(SextupletSym, 1, (short) 6);

    /** The septuplet note. (adjustment) */
    public static final
    Fraction.Singleton Septuplet = new Fraction.Singleton(SeptupletSym, 1, (short) 7);

    /** The duplet note. (adjustment) */
    public static final
    Fraction.Singleton Duplet = new Fraction.Singleton(DupletSym, 3, (short) 2);

    /** The quadruplet note. (adjustment) */
    public static final
    Fraction.Singleton Quadruplet = new Fraction.Singleton(QuadrupletSym, 3, (short) 4);

    /** The dotted octuple whole note. */
    public static final
    Singleton DottedOctupleWhole = new Singleton(DottedOctupleWholeSym, OctupleWhole, (short) 1, Dotted);

    /** The double-dotted octuple whole note. */
    public static final
    Singleton DoubleDottedOctupleWhole = new Singleton(DoubleDottedOctupleWholeSym, OctupleWhole, (short) 1, DoubleDotted);

    /** The triple-dotted octuple whole note. */
    public static final
    Singleton TripleDottedOctupleWhole = new Singleton(TripleDottedOctupleWholeSym, OctupleWhole, (short) 1, TripleDotted);

    /** The dotted quadruple whole note. */
    public static final
    Singleton DottedQuadrupleWhole = new Singleton(DottedQuadrupleWholeSym, QuadrupleWhole, (short) 1, Dotted);

    /** The double-dotted quadruple whole note. */
    public static final
    Singleton DoubleDottedQuadrupleWhole = new Singleton(DoubleDottedQuadrupleWholeSym, QuadrupleWhole, (short) 1, DoubleDotted);

    /** The triple-dotted quadruple whole note. */
    public static final
    Singleton TripleDottedQuadrupleWhole = new Singleton(TripleDottedQuadrupleWholeSym, QuadrupleWhole, (short) 2, TripleDotted);

    /** The dotted double whole note. */
    public static final
    Singleton DottedDoubleWhole = new Singleton(DottedDoubleWholeSym, DoubleWhole, (short) 1, Dotted);

    /** The double-dotted double whole note. */
    public static final
    Singleton DoubleDottedDoubleWhole = new Singleton(DoubleDottedDoubleWholeSym, DoubleWhole, (short) 2, DoubleDotted);

    /** The triple-dotted double whole note. */
    public static final
    Singleton TripleDottedDoubleWhole = new Singleton(TripleDottedDoubleWholeSym, DoubleWhole, (short) 4, TripleDotted);

    /** The dotted whole note. */
    public static final
    Singleton DottedWhole = new Singleton(DottedWholeSym, Whole, (short) 2, Dotted);

    /** The double-dotted whole note. */
    public static final
    Singleton DoubleDottedWhole = new Singleton(DoubleDottedWholeSym, Whole, (short) 4, DoubleDotted);

    /** The triple-dotted whole note. */
    public static final
    Singleton TripleDottedWhole = new Singleton(TripleDottedWholeSym, Whole, (short) 8, TripleDotted);

    /** The dotted half note. */
    public static final
    Singleton DottedHalf = new Singleton(DottedHalfSym, Half, (short) 4, Dotted);

    /** The double-dotted half note. */
    public static final
    Singleton DoubleDottedHalf = new Singleton(DoubleDottedHalfSym, Half, (short) 8, DoubleDotted);

    /** The triple-dotted half note. */
    public static final
    Singleton TripleDottedHalf = new Singleton(TripleDottedHalfSym, Half, (short) 16, TripleDotted);

    /** The dotted quarter note. */
    public static final
    Singleton DottedQuarter = new Singleton(DottedQuarterSym, Quarter, (short) 8, Dotted);

    /** The double-dotted quarter note. */
    public static final
    Singleton DoubleDottedQuarter = new Singleton(DoubleDottedQuarterSym, Quarter, (short) 16, DoubleDotted);

    /** The triple-dotted quarter note. */
    public static final
    Singleton TripleDottedQuarter = new Singleton(TripleDottedQuarterSym, Quarter, (short) 32, TripleDotted);

    /** The dotted eighth note. */
    public static final
    Singleton DottedEighth = new Singleton(DottedEighthSym, Eighth, (short) 16, Dotted);

    /** The double-dotted eighth note. */
    public static final
    Singleton DoubleDottedEighth = new Singleton(DoubleDottedEighthSym, Eighth, (short) 32, DoubleDotted);

    /** The triple-dotted eighth note. */
    public static final
    Singleton TripleDottedEighth = new Singleton(TripleDottedEighthSym, Eighth, (short) 64, TripleDotted);

    /** The dotted sixteenth note. */
    public static final
    Singleton DottedSixteenth = new Singleton(DottedSixteenthSym, Sixteenth, (short) 32, Dotted);

    /** The double-dotted sixteenth note. */
    public static final
    Singleton DoubleDottedSixteenth = new Singleton(DoubleDottedSixteenthSym, Sixteenth, (short) 64, DoubleDotted);

    /** The triple-dotted sixteenth note. */
    public static final
    Singleton TripleDottedSixteenth = new Singleton(TripleDottedSixteenthSym, Sixteenth, (short) 128, TripleDotted);

    /** The dotted thirty-second note. */
    public static final
    Singleton DottedThirtySecond = new Singleton(DottedThirtySecondSym, ThirtySecond, (short) 64, Dotted);

    /** The double-dotted thirty-second note. */
    public static final
    Singleton DoubleDottedThirtySecond = new Singleton(DoubleDottedThirtySecondSym, ThirtySecond, (short) 128, DoubleDotted);

    /** The triple-dotted thirty-second note. */
    public static final
    Singleton TripleDottedThirtySecond = new Singleton(TripleDottedThirtySecondSym, ThirtySecond, (short) 256, TripleDotted);

    /** The dotted sixty-fourth note. */
    public static final
    Singleton DottedSixtyFourth = new Singleton(DottedSixtyFourthSym, SixtyFourth, (short) 128, Dotted);

    /** The double-dotted sixty-fourth note. */
    public static final
    Singleton DoubleDottedSixtyFourth = new Singleton(DoubleDottedSixtyFourthSym, SixtyFourth, (short) 256, DoubleDotted);

    /** The triple-dotted sixty-fourth note. */
    public static final
    Singleton TripleDottedSixtyFourth = new Singleton(TripleDottedSixtyFourthSym, SixtyFourth, (short) 512, TripleDotted);

    /** The dotted hundred twenty-eighth note. */
    public static final
    Singleton DottedHundredTwentyEighth = new Singleton(DottedHundredTwentyEighthSym, HundredTwentyEighth, (short) 256, Dotted);

    /** The double-dotted hundred twenty-eighth note. */
    public static final
    Singleton DoubleDottedHundredTwentyEighth = new Singleton(DoubleDottedHundredTwentyEighthSym, HundredTwentyEighth, (short) 512, DoubleDotted);

    /** The triple-dotted hundred twenty-eighth note. */
    public static final
    Singleton TripleDottedHundredTwentyEighth = new Singleton(TripleDottedHundredTwentyEighthSym, HundredTwentyEighth, (short) 1024, TripleDotted);

    /** The dotted two hundred fifty-sixth note. */
    public static final
    Singleton DottedTwoHundredFiftySixth = new Singleton(DottedTwoHundredFiftySixthSym, TwoHundredFiftySixth, (short) 512, Dotted);

    /** The double-dotted two hundred fifty-sixth note. */
    public static final
    Singleton DoubleDottedTwoHundredFiftySixth = new Singleton(DoubleDottedTwoHundredFiftySixthSym, TwoHundredFiftySixth, (short) 1024, DoubleDotted);

    /** The triple-dotted two hundred fifty-sixth note. */
    public static final
    Singleton TripleDottedTwoHundredFiftySixth = new Singleton(TripleDottedTwoHundredFiftySixthSym, TwoHundredFiftySixth, (short) 2048, TripleDotted);

    /** The duration symbol. */
    protected
    String symbol;

    /**
     * Creates a duration, as a fraction, with the specified symbol, beats, units, and default denominator.
     *
     * @param symbol the symbol.
     * @param beats the number of beats.
     * @param units the number of units in a beat.
     * @param defaultDenominator the default denominator.
     *
     * @throws IllegalArgumentException if the units is zero.
     * @throws IllegalStateException if the duration value is negative.
     */
    public
    Duration(
       final String symbol,
       final int beats,
       final short units,
       final Short defaultDenominator
       ) {
       super(beats, units, defaultDenominator);
       validateNonNegative();
       this.symbol = symbol;
    }

    /**
     * Creates a duration, as a fraction, with the specified symbol, beats, and units.
     *
     * @param symbol the symbol.
     * @param beats the number of beats.
     * @param units the number of units in a beat.
     *
     * @throws IllegalArgumentException if the units is zero.
     * @throws IllegalStateException if the duration value is negative.
     */
    public
    Duration(
        final String symbol,
        final int beats,
        final short units
        ) {
        super(beats, units);
        validateNonNegative();
        this.symbol = symbol;
    }

    /**
     * Creates a duration, as a fraction, with the specified symbol and beats and 1 for units.
     *
     * @param symbol the symbol.
     * @param beats the number of beats.
     */
    public
    Duration(
        final String symbol,
        final int beats
        ) {
        this(symbol, beats, (short) 1);
    }

    /**
     * Creates a duration, as a fraction, with the specified beats, units, default denominator, and null symbol.
     *
     * @param beats the number of beats.
     * @param units the number of units in a beat.
     * @param defaultDenominator the default denominator.
     *
     * @throws IllegalArgumentException if the units is zero.
     */
    public
    Duration(
       final int beats,
       final short units,
       final Short defaultDenominator
       ) {
       this(null, beats, units, defaultDenominator);
    }

    /**
     * Creates a duration, as a fraction, with the specified beats, units, and null symbol.
     *
     * @param beats the number of beats.
     * @param units the number of units in a beat.
     *
     * @throws IllegalArgumentException if the units is zero.
     */
    public
    Duration(
        final int beats,
        final short units
        ) {
        this(null, beats, units);
    }

    /**
     * Creates a duration, as a fraction, with the specified beats, 1 for units, and null symbol.
     *
     * @param beats the number of beats.
     */
    public
    Duration(
        final int beats
        ) {
        this(null, beats, (short) 1);
    }

    /**
     * Creates a duration, as a fraction, with 1 for beats and units, and null symbol.
     */
    public
    Duration() {
        this(null, 1, (short) 1);
    }

    /**
     * Creates a duration, as a fraction, with the specified symbol and from the specified string value.
     * <p>
     * The string value must be a valid fraction, matching the pattern "a/b", and may contain decimal numerator and denominator parts and leading or trailing whitespace in each part.
     * The numerator and denominator parts are parsed as {@code double} values.
     * The division string between the two fraction parts must match the value set by {@link Constant.Fraction#DividerSym}.
     * <p>
     * The simplification flag is set to off.
     *
     * @param symbol the symbol.
     * @param value the value.
     *
     * @throws NullPointerException if the value is null.
     * @throws NumberFormatException if the numerator or denominator part does not contain a parsable {@code double}.
     * @throws IllegalArgumentException if the denominator part value is zero.
     * @throws IllegalStateException if the duration value is negative.
     */
    public
    Duration(
        final String symbol,
        final String value
        ) {
        super(value);
        validateNonNegative();
        this.symbol = symbol;
    }

    /**
     * Creates a duration, as a fraction, from the specified string value and with a symbol equal to the same value.
     * <p>
     * The string value must be a valid fraction, matching the pattern "a/b", and may contain decimal numerator and denominator parts and leading or trailing whitespace in each part.
     * The numerator and denominator parts are parsed as {@code double} values.
     * The division string between the two fraction parts must match the value set by {@link Constant.Fraction#DividerSym}.
     * <p>
     * The simplification flag is set to off.
     *
     * @param value the symbol and value.
     *
     * @throws NullPointerException if the value is null.
     * @throws NumberFormatException if the numerator or denominator part does not contain a parsable {@code double}.
     * @throws IllegalArgumentException if the denominator part value is zero.
     */
    public
    Duration(
        final String value
        ) {
        this(value, value);
    }

    /**
     * Returns true if the specified duration is a statically defined instance (singleton); otherwise returns false.
     *
     * @param duration the duration.
     *
     * @return true if the duration is a singleton, and false otherwise.
     */
    public static
    boolean isSingleton(
        final Duration duration
        ) {
        return duration instanceof Singleton;
    }

    /**
     * Returns true if the specified duration is standard, and false otherwise.
     *
     * @param duration the duration.
     *
     * @return true if duration is standard, and false otherwise.
     */
    public static
    boolean isStandard(
        final Duration duration
        ) {
        return duration instanceof Standard;
    }

    /**
     * Returns the singleton equivalent to this duration using the specified comparator, or itself if none is found.
     * <p>
     * This implementation calls {@link Comparator#compare(Object, Object)} with this duration as the first argument and the singletons as second arguments of that method.
     *
     * @param comparator the comparator.
     *
     * @return the equivalent singleton or this duration.
     */
    public
    Duration distinct(
        final Comparator<Fraction> comparator
        ) {
        return (Standard) new Lambda.BinaryLocator<Fraction>(this, Singleton.Order, false, comparator).result(this);
    }

    /**
     * Returns the singleton equivalent to this duration using the specified preferred adjustments, or itself if none is found.
     *
     * @param adjustments the preferred adjustments.
     *
     * @return the equivalent singleton or this duration.
     *
     * @see #distinct(Comparator)
     */
    public
    Duration distinct(
        final Fraction... adjustments
        ) {
        return distinct(new Comparator<Fraction>() {
            @Override
            public int compare(final Fraction f1, final Fraction f2) {
                if (f1.compareTo(f2) == 0) {
                    for (final Fraction adj : adjustments)
                        if (((Standard) f2).getAdjustment().equals(adj))
                            return 0;
                }

                return 1;
            }
        });
    }

    /**
     * Returns the singleton equivalent to this duration or itself if none is found.
     *
     * @return the equivalent singleton or this duration.
     */
    public
    Duration distinct() {
        return (Standard) new Lambda.BinaryComparableLocator<Fraction>(this, Singleton.Order, false).result(this);
    }

    /**
     * Validates the duration value is non-negative; otherwise throws an {@code IllegalStateException}.
     *
     * @throws IllegalStateException if the duration value is negative.
     */
    protected
    void validateNonNegative() {
        if (numerator < 0)
            throw new IllegalStateException(NegativeDuration);
    }

    /**
     * Adds the specified fraction to this duration.
     *
     * @param fraction the fraction.
     *
     * @throws NullPointerException if the specified fraction is null.
     * @throws IllegalStateException if the result is negative.
     * @throws UnsupportedOperationException if this duration is a singleton and the fraction is not equal to zero.
     */
    @Override
    public void add(final Fraction fraction) {
        super.add(fraction);
        validateNonNegative();
    }

    /**
     * Adds the specified number to this duration.
     *
     * @param n the number.
     *
     * @throws NullPointerException if the number is null.
     * @throws IllegalStateException if the result is negative.
     * @throws UnsupportedOperationException if this duration is a singleton and the number is not equal to zero.
     */
    @Override
    public void add(final Number n) {
        super.add(n);
        validateNonNegative();
    }

    /**
     * Adjusts the duration by performing simplification.
     */
    @Override
    public void adjust() {
        simplify();
    }

    /**
     * Adjusts the duration with the specified adjustment fractions and returns the adjusted duration.
     * <p>
     * This implementation, unlike its standard type override, operates on the duration numerator and denominator values.
     * If this is a standard duration, the adjustments will be applied to the standard duration adjustment.
     * <p>
     * If this is a standard duration with an equivalent singleton, the singleton is returned.
     *
     * @param adjustments the adjustment fractions.
     *
     * @return the adjusted duration.
     *
     * @throws IllegalStateException if a specified adjustment value is negative.
     */
    @Override
    public Duration adjusted(final Fraction... adjustments) {
        for (final Fraction adjustment : adjustments)
            if (adjustment != null)
                multiply(adjustment);

        adjust();
        return this;
    }

    /**
     * Returns the milliseconds in this duration for the specified tempo.
     * <p>
     * This implementation returns a {@link java.time.Duration}.
     *
     * @param tempo the tempo.
     *
     * @return the milliseconds amount.
     */
    @Override
    public TemporalAmount apply(final Tempo tempo) {
        return java.time.Duration.ofMillis((long) (60000. / tempo.bpm() / tempo.beat().doubleValue() * doubleValue()));
    }

    /**
     * Divides this duration by the specified number and returns this duration.
     * <p>
     * If this is a standard duration the equivalent singleton will be returned, when one exists.
     *
     * @param n the number.
     *
     * @return the divided duration or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the number is null.
     * @throws IllegalStateException if the result is negative.
     */
    @Override
    public Duration by(final Number n) {
        divide(n);
        return this;
    }

    /**
     * Creates and returns a copy of this duration.
     *
     * @return the clone of this duration.
     */
    @Override
    public Duration clone() {
        final Duration duration = new Duration(symbol, numerator, denominator, defaultDenominator);
        duration.setSimplification(simplification);
        return duration;
    }

    /**
     * Divides this duration by the specified fraction.
     *
     * @param fraction the fraction.
     *
     * @throws NullPointerException if the specified fraction is null.
     * @throws IllegalStateException if the result is negative.
     * @throws UnsupportedOperationException if this duration is a singleton and the fraction is not equal to 1.
     */
    @Override
    public void divide(final Fraction fraction) {
        super.divide(fraction);
        validateNonNegative();
    }

    /**
     * Divides this duration by the specified number.
     *
     * @param n the number.
     *
     * @throws NullPointerException if the number is null.
     * @throws IllegalStateException if the result is negative.
     * @throws UnsupportedOperationException if this duration is a singleton and the number is not equal to 1.
     */
    @Override
    public void divide(final Number n) {
        super.divide(n);
        validateNonNegative();
    }

    /**
     * Inverts and returns this duration.
     * <p>
     * If this is a standard duration the equivalent singleton will be returned, when one exists.
     *
     * @return the inverted duration or its equivalent singleton if this is a standard type.
     *
     * @throws IllegalStateException if the numerator is zero.
     */
    @Override
    public Duration inverted() {
        invert();
        return this;
    }

    /**
     * Returns true if this duration is the same runtime instance of the specified type; otherwise returns false.
     *
     * @param type the other duration type.
     *
     * @return true if the runtime instances are the same, or false otherwise.
     */
    @Override
    public boolean is(final system.data.Type<? extends Duration> type) {
        // Durations are not associated with a different duration type
        return this == type;
    }

    /**
     * Subtracts the specified number from this duration and returns this duration.
     * <p>
     * If this is a standard duration the equivalent singleton will be returned, when one exists.
     *
     * @param n the number.
     *
     * @return the subtracted duration or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the number is null.
     * @throws IllegalStateException if the result is negative.
     */
    @Override
    public Duration minus(final Number n) {
        subtract(n);
        return this;
    }

    /**
     * Multiplies this duration by the specified fraction.
     *
     * @param fraction the fraction.
     *
     * @throws NullPointerException if the specified fraction is null.
     * @throws IllegalStateException if the result is negative.
     * @throws UnsupportedOperationException if this duration is a singleton and the fraction is not equal to 1.
     */
    @Override
    public void multiply(final Fraction fraction) {
        super.multiply(fraction);
        validateNonNegative();
    }

    /**
     * Multiplies this duration by the specified number.
     *
     * @param n the number.
     *
     * @throws NullPointerException if the number is null.
     * @throws IllegalStateException if the result is negative.
     * @throws UnsupportedOperationException if this duration is a singleton and the number is not equal to 1.
     */
    @Override
    public void multiply(final Number n) {
        super.multiply(n);
        validateNonNegative();
    }

    /**
     * Adds the specified number to this duration and returns this duration.
     * <p>
     * If this is a standard duration the equivalent singleton will be returned, when one exists.
     *
     * @param n the number.
     *
     * @return the added duration or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the number is null.
     * @throws IllegalStateException if the result is negative.
     */
    @Override
    public Duration plus(final Number n) {
        add(n);
        return this;
    }

    /**
     * This implementation throws an {@code UnsupportedOperationException}.
     */
    @Override
    public void reverse() {
        // Durations are not reversible as there is no negative time in music
        throw new UnsupportedOperationException(OperationImpossible);
    }

    /**
     * This implementation throws an {@code UnsupportedOperationException}.
     */
    @Override
    public Duration reversed() {
        // Durations are not reversible as there is no negative time in music
        throw new UnsupportedOperationException(OperationImpossible);
    }

    /**
     * Subtracts the specified fraction from this duration.
     *
     * @param fraction the fraction.
     *
     * @throws NullPointerException if the specified fraction is null.
     * @throws IllegalStateException if the result is negative.
     * @throws UnsupportedOperationException if this duration is a singleton and the fraction is not equal to zero.
     */
    @Override
    public void subtract(final Fraction fraction) {
        super.subtract(fraction);
        validateNonNegative();
    }

    /**
     * Subtracts the specified number from this duration.
     *
     * @param n the number.
     *
     * @throws NullPointerException if the number is null.
     * @throws IllegalStateException if the result is negative.
     * @throws UnsupportedOperationException if this duration is a singleton and the number is not equal to zero.
     */
    @Override
    public void subtract(final Number n) {
        super.subtract(n);
        validateNonNegative();
    }

    /**
     * Multiplies this duration by the specified number and returns this duration.
     * <p>
     * If this is a standard duration the equivalent singleton will be returned, when one exists.
     *
     * @param n the number.
     *
     * @return the multiplied duration or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the number is null.
     * @throws IllegalStateException if the result is negative.
     */
    @Override
    public Duration times(final Number n) {
        multiply(n);
        return this;
    }

    /**
     * Returns a string representation of the duration.
     * <p>
     * This implementation return the duration symbol.
     *
     * @return the duration string.
     */
    @Override
    public String toString() {
        return getSymbol();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation is empty.
     */
    @Override
    public void unadjust() {}

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns this duration.
     *
     * @return the duration.
     */
    @Override
    public Duration unadjusted() {
        return this;
    }

    /**
     * Returns the symbol of the duration.
     *
     * @return the symbol.
     */
    @Override
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the symbol of the duration.
     *
     * @param symbol the symbol.
     *
     * @throws UnsupportedOperationException if this is a singleton and the specified symbol is not equal to the singleton symbol.
     *
     * @see Symbolized.Singleton#setSymbol(Object)
     */
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
        return numerator;
    }

    /**
     * Returns the beat units.
     *
     * @return the beat units.
     */
    public
    short getUnits() {
        return denominator;
    }

    /**
     * {@code Singleton} represents all standard stem types in classical music.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    private static final
    class Singleton
    extends Standard
    implements
        Operable.Locked<Number>,
        Symbolized.Singleton<String>,
        Unique
    {
        /** A constant holding the maximum value a {@code Singleton} can have, 15. */
        public static final
        float MAX_VALUE = 15F;

        /** A constant holding the minimum value a {@code Singleton} can have, 0. */
        public static final
        float MIN_VALUE = 0F;

        /** The {@code Class} instance representing the type {@code Singleton}. */
        public static final
        Class<Duration.Singleton> TYPE = Duration.Singleton.class;

        /** The array of duration singletons. (descending) */
        public static final
        Duration.Singleton[] Order
        = new Duration.Singleton[] {
            TripleDottedOctupleWhole,
            DoubleDottedOctupleWhole,
            DottedOctupleWhole,
            OctupleWhole,
            TripleDottedQuadrupleWhole,
            DoubleDottedQuadrupleWhole,
            DottedQuadrupleWhole,
            QuadrupleWhole,
            TripleDottedDoubleWhole,
            DoubleDottedDoubleWhole,
            DottedDoubleWhole,
            DoubleWhole,
            TripleDottedWhole,
            DoubleDottedWhole,
            DottedWhole,
            Whole,
            TripleDottedHalf,
            DoubleDottedHalf,
            DottedHalf,
            Half,
            TripleDottedQuarter,
            DoubleDottedQuarter,
            DottedQuarter,
            Quarter,
            TripleDottedEighth,
            DoubleDottedEighth,
            DottedEighth,
            Eighth,
            TripleDottedSixteenth,
            DoubleDottedSixteenth,
            DottedSixteenth,
            Sixteenth,
            TripleDottedThirtySecond,
            DoubleDottedThirtySecond,
            DottedThirtySecond,
            ThirtySecond,
            TripleDottedSixtyFourth,
            DoubleDottedSixtyFourth,
            DottedSixtyFourth,
            SixtyFourth,
            TripleDottedHundredTwentyEighth,
            DoubleDottedHundredTwentyEighth,
            DottedHundredTwentyEighth,
            HundredTwentyEighth,
            TripleDottedTwoHundredFiftySixth,
            DoubleDottedTwoHundredFiftySixth,
            DottedTwoHundredFiftySixth,
            TwoHundredFiftySixth,

            Grace
        };

        /**
         * Creates a singleton duration with the specified symbol, beats and units of the duration, default denominator, and adjustment.
         *
         * @param symbol the symbol.
         * @param duration the duration.
         * @param defaultDenominator the default denominator.
         * @param adjustment the adjustment.
         *
         * @throws NullPointerException if the duration or adjustment is null.
         */
        private
        Singleton(
            final String symbol,
            final Duration duration,
            final Short defaultDenominator,
            final Fraction adjustment
            ) {
            super(symbol, duration.numerator * adjustment.getNumerator(), (short) (duration.denominator * adjustment.getDenominator()), defaultDenominator, adjustment);
        }

        /**
         * Creates a singleton duration with the specified symbol, beats, units, default denominator, and the unit adjustment.
         *
         * @param symbol the symbol.
         * @param beats the number of beats.
         * @param units the number of units in a beat.
         * @param defaultDenominator the default denominator.
         *
         * @throws IllegalArgumentException if the units is zero.
         */
        private
        Singleton(
            final String symbol,
            final int beats,
            final short units,
            final Short defaultDenominator
            ) {
            super(symbol, beats, units, defaultDenominator);
        }

        /**
         * Creates a singleton duration with the specified symbol, beats, units, and the unit adjustment.
         *
         * @param symbol the symbol.
         * @param beats the number of beats.
         * @param units the number of units in a beat.
         *
         * @throws IllegalArgumentException if the units is zero.
         */
        private
        Singleton(
            final String symbol,
            final int beats,
            final short units
            ) {
            super(symbol, beats, units);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the fraction is equal to zero.
         *
         * @param fraction the fraction.
         *
         * @throws NullPointerException if the specified fraction is null.
         * @throws UnsupportedOperationException if the fraction is not equal to zero.
         */
        @Override
        public void add(final Fraction fraction) {
            if (fraction.getNumerator() != 0)
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * Clones this singleton and adjusts the copy with the specified adjustment fractions and returns it or the equivalent singleton if one exists.
         *
         * @param adjustments the adjustment fractions.
         *
         * @return the adjusted standard duration or its equivalent singleton.
         *
         * @throws IllegalStateException if a specified adjustment value is negative.
         */
        @Override
        public Standard adjusted(final Fraction... adjustments) {
            return clone().adjusted(adjustments);
        }

        /**
         * Clones this singleton and divides the copy by the specified number and returns it or the equivalent singleton if one exists.
         *
         * @param n the number.
         *
         * @return the divided standard duration or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         * @throws IllegalStateException if the result is negative.
         */
        @Override
        public Standard by(final Number n) {
            return clone().by(n);
        }

        /**
         * Returns the singleton equivalent with the specified preferred adjustments, or itself if none is found.
         *
         * @param adjustments the preferred adjustments.
         *
         * @return the equivalent singleton or this singleton.
         */
        public
        Duration distinct(
            final Fraction... adjustments
            ) {
            return this;
        }

        /**
         * Returns this singleton.
         *
         * @return the singleton.
         */
        @Override
        public Duration distinct() {
            return this;
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the fraction is equal to 1.
         *
         * @param fraction the fraction.
         *
         * @throws NullPointerException if the specified fraction is null.
         * @throws UnsupportedOperationException if the fraction is not equal to 1.
         */
        @Override
        public void divide(final Fraction fraction) {
            if (fraction.floatValue() != 1F)
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the singleton is a whole note.
         *
         * @throws UnsupportedOperationException if the singleton is other than the whole note.
         */
        @Override
        public void invert() {
            if (this != Duration.Singleton.Whole)
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * Clones this singleton and inverts the copy and returns it or the equivalent singleton if one exists.
         *
         * @return the inverted standard duration or its equivalent singleton.
         *
         * @throws IllegalStateException if the numerator is zero.
         */
        @Override
        public Standard inverted() {
            return clone().inverted();
        }

        /**
         * Clones this singleton and subtracts the specified number from the copy and returns it or the equivalent singleton if one exists.
         *
         * @param n the number.
         *
         * @return the subtracted standard duration or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         * @throws IllegalStateException if the result is negative.
         */
        @Override
        public Standard minus(final Number n) {
            return clone().minus(n);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the fraction is equal to 1.
         *
         * @param fraction the fraction.
         *
         * @throws NullPointerException if the specified fraction is null.
         * @throws UnsupportedOperationException if the fraction is not equal to 1.
         */
        @Override
        public void multiply(final Fraction fraction) {
            if (fraction.floatValue() != 1F)
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * Clones this singleton and adds the specified number to the copy and returns it or the equivalent singleton if one exists.
         *
         * @param n the number.
         *
         * @return the added standard duration or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         * @throws IllegalStateException if the result is negative.
         */
        @Override
        public Standard plus(final Number n) {
            return clone().plus(n);
        }

        /**
         * This implementation returns the singleton.
         */
        @Override
        public Duration.Singleton simplified(short defaultDenominator) {
            return this;
        }

        /**
         * This implementation returns the singleton.
         */
        @Override
        public Duration.Singleton simplified() {
            return this;
        }

        /**
         * This implementation is empty.
         */
        @Override
        public void simplify(short defaultDenominator) {}

        /**
         * This implementation is empty.
         */
        @Override
        public void simplify() {}

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the fraction is equal to zero.
         *
         * @param fraction the fraction.
         *
         * @throws NullPointerException if the specified fraction is null.
         * @throws UnsupportedOperationException if the fraction is not equal to zero.
         */
        @Override
        public void subtract(final Fraction fraction) {
            if (fraction.getNumerator() != 0)
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * Clones this singleton and multiplies the copy by the specified number and returns it or the equivalent singleton if one exists.
         *
         * @param n the number.
         *
         * @return the multiplied standard duration or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         * @throws IllegalStateException if the result is negative.
         */
        @Override
        public Standard times(final Number n) {
            return clone().times(n);
        }

        /**
         * {@inheritDoc}
         * <p>
         * This implementation is empty.
         */
        @Override
        public void unadjust() {}

        /**
         * {@inheritDoc}
         * <p>
         * This implementation returns this singleton.
         *
         * @return the singleton.
         */
        @Override
        public Duration.Singleton unadjusted() {
            return this;
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the specified adjustment is equal to the singleton adjustment.
         *
         * @param adjustment the adjustment.
         *
         * @throws NullPointerException if the specified adjustment is null.
         * @throws UnsupportedOperationException if the specified adjustment is not equal to the singleton adjustment.
         */
        @Override
        public void setAdjustment(final Fraction adjustment) {
            if (!this.adjustment.equals(adjustment))
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * This implementation is empty.
         */
        @Override
        public void setDefaultDenominator(short defaultDenominator) {}

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the specified denominator is equal to the singleton denominator.
         *
         * @param denominator the denominator.
         *
         * @throws NullPointerException if the specified denominator is null.
         * @throws UnsupportedOperationException if the specified denominator is not equal to the singleton denominator.
         */
        @Override
        public void setDenominator(final short denominator) {
            if (denominator != this.denominator)
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the specified numerator is equal to the singleton numerator.
         *
         * @param numerator the numerator.
         *
         * @throws NullPointerException if the specified numerator is null.
         * @throws UnsupportedOperationException if the specified numerator is not equal to the singleton numerator.
         */
        @Override
        public void setNumerator(final int numerator) {
            if (numerator != this.numerator)
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * This implementation is empty.
         */
        @Override
        public void setSimplification(BooleanSupplier simplification) {}
    }

    /**
     * {@code Standard} represents all standard musical durations.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class Standard
    extends Duration
    {
        /** The {@code Class} instance representing the type {@code Standard}. */
        public static final
        Class<Standard> TYPE = Standard.class;

        /** The adjustment coefficient. */
        protected
        Fraction adjustment;

        /**
         * Creates a standard duration with the specified symbol, beats and units of the duration, and adjustment.
         *
         * @param symbol the symbol.
         * @param duration the duration.
         * @param adjustment the adjustment.
         *
         * @throws NullPointerException if the duration or adjustment is null.
         */
        public
        Standard(
            final String symbol,
            final Duration duration,
            final Fraction adjustment
            ) {
            this(symbol, duration.numerator, duration.denominator, duration.defaultDenominator, adjustment);
        }

        /**
         * Creates a standard duration with the specified symbol, beats and units of the duration, and the unit adjustment.
         *
         * @param symbol the symbol.
         * @param duration the duration.
         *
         * @throws NullPointerException if the duration is null.
         */
        public
        Standard(
            final String symbol,
            final Duration duration
            ) {
            this(symbol, duration.numerator, duration.denominator, duration.defaultDenominator);
        }

        /**
         * Creates a standard duration with beats and units of the specified duration, adjustment, and null symbol.
         *
         * @param duration the duration.
         * @param adjustment the adjustment.
         *
         * @throws NullPointerException if the duration or adjustment is null.
         */
        public
        Standard(
            final Duration duration,
            final Fraction adjustment
            ) {
            this(duration.symbol, duration.numerator, duration.denominator, duration.defaultDenominator, adjustment);
        }

        /**
         * Creates a standard duration with beats and units of the specified duration, the unit adjustment, and null symbol.
         *
         * @param duration the duration.
         *
         * @throws NullPointerException if the duration is null.
         */
        public
        Standard(
            final Duration duration
            ) {
            this(duration.symbol, duration.numerator, duration.denominator, duration.defaultDenominator);
        }

        /**
         * Creates a standard duration with the specified symbol, beats, units, default denominator, and adjustment.
         *
         * @param symbol the symbol.
         * @param beats the number of beats.
         * @param units the number of units in a beat.
         * @param defaultDenominator the default denominator.
         * @param adjustment the adjustment.
         *
         * @throws NullPointerException if the adjustment is null.
         * @throws IllegalArgumentException if the units is zero.
         * @throws IllegalStateException if the standard duration value is negative.
         */
        public
        Standard(
            final String symbol,
            final int beats,
            final short units,
            final Short defaultDenominator,
            final Fraction adjustment
            ) {
            super(symbol, beats * adjustment.getNumerator(), (short) (units * adjustment.getDenominator()), defaultDenominator);
            this.adjustment = adjustment;
        }

        /**
         * Creates a standard duration with the specified symbol, beats, units, default denominator, and the unit adjustment.
         *
         * @param symbol the symbol.
         * @param beats the number of beats.
         * @param units the number of units in a beat.
         * @param defaultDenominator the default denominator.
         *
         * @throws IllegalArgumentException if the units is zero.
         * @throws IllegalStateException if the standard duration value is negative.
         */
        public
        Standard(
            final String symbol,
            final int beats,
            final short units,
            final Short defaultDenominator
            ) {
            this(symbol, beats, units, defaultDenominator, Singleton.Unit);
        }

        /**
         * Creates a standard duration with the specified symbol, beats, units, and adjustment.
         *
         * @param symbol the symbol.
         * @param beats the number of beats.
         * @param units the number of units in a beat.
         * @param adjustment the adjustment.
         *
         * @throws NullPointerException if the adjustment is null.
         * @throws IllegalArgumentException if the units is zero.
         * @throws IllegalStateException if the standard duration value is negative.
         */
        public
        Standard(
            final String symbol,
            final int beats,
            final short units,
            final Fraction adjustment
            ) {
            super(symbol, beats * adjustment.getNumerator(), (short) (units * adjustment.getDenominator()));
            this.adjustment = adjustment;
        }

        /**
         * Creates a standard duration with the specified symbol, beats, units, and the unit adjustment.
         *
         * @param symbol the symbol.
         * @param beats the number of beats.
         * @param units the number of units in a beat.
         *
         * @throws IllegalArgumentException if the units is zero.
         * @throws IllegalStateException if the standard duration value is negative.
         */
        public
        Standard(
            final String symbol,
            final int beats,
            final short units
            ) {
            this(symbol, beats, units, Singleton.Unit);
        }

        /**
         * Creates a standard duration with the specified symbol and from the specified string value.
         * <p>
         * The string value must be a valid fraction, matching the pattern "a/b" or "a/b * c/d", and may contain decimal numerator and denominator parts and leading or trailing whitespace in each part.
         * The numerator and denominator parts for both the duration and the optional adjustment are parsed as {@code double} values.
         * The division string between the fraction parts must match the value set by {@link Constant.Fraction#DividerSym}.
         * If adjustment is included in the value, the multiplier string between the two fraction parts must match the value set by {@link Constant.Duration#MultiplierSym}.
         * <p>
         * The simplification flag is set to off.
         *
         * @param symbol the symbol.
         * @param value the value.
         *
         * @throws NullPointerException if the value is null.
         * @throws NumberFormatException if the any part of the value string does not contain a parsable {@code double}.
         * @throws IllegalArgumentException if the denominator or the adjustment denominator part value is zero.
         * @throws IllegalStateException if the standard duration value is negative.
         */
        public
        Standard(
            final String symbol,
            final String value
            ) {
            super();

            final int mulIndex = value.indexOf(MultiplierSym);
            if (mulIndex == -1)
                set(this, value);
            else {
                set(this, value.substring(0, mulIndex));
                set(adjustment, value.substring(mulIndex + MultiplierSym.length()));
            }

            validateNonNegative();
        }

        /**
         * Creates a standard duration from the specified string value with a symbol equal to the same value.
         * <p>
         * The string value must be a valid fraction, matching the pattern "a/b" or "a/b * c/d", and may contain decimal numerator and denominator parts and leading or trailing whitespace in each part.
         * The numerator and denominator parts for both the duration and the optional adjustment are parsed as {@code double} values.
         * The division string between the fraction parts must match the value set by {@link Constant.Fraction#DividerSym}.
         * If adjustment is included in the value, the multiplier string between the two fraction parts must match the value set by {@link Constant.Duration#MultiplierSym}.
         * <p>
         * The simplification flag is set to off.
         *
         * @param value the symbol and value.
         *
         * @throws NullPointerException if the value is null.
         * @throws NumberFormatException if the any part of the value string does not contain a parsable {@code double}.
         * @throws IllegalArgumentException if the denominator or the adjustment denominator part value is zero.
         * @throws IllegalStateException if the standard duration value is negative.
         */
        public
        Standard(
            final String value
            ) {
            this(value, value);
        }

        /**
         * Validates the specified adjustment is not null; otherwise throws a {@code NullPointerException}.
         *
         * @param adjustment the adjustment.
         *
         * @throws NullPointerException if the specified adjustment is null.
         */
        protected static
        void validateAdjustment(
            final Fraction adjustment
            ) {
            if (adjustment == null)
                throw new NullPointerException(NullAdjustment);
        }

        /**
         * Validates the specified standard duration is not a singleton; otherwise throws an {@code UnsupportedOperationException}.
         *
         * @param duration the standard duration.
         *
         * @throws UnsupportedOperationException if the standard duration is a singleton.
         */
        protected static
        void validateNonSingleton(
            final Standard duration
            ) {
            if (isSingleton(duration))
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * Adjusts this standard duration with the specified adjustment fractions and returns this standard duration or the equivalent singleton if one exists.
         *
         * @param adjustments the adjustment fractions.
         *
         * @return the adjusted standard duration or its equivalent singleton.
         *
         * @throws IllegalStateException if a specified adjustment value is negative.
         */
        @Override
        public Standard adjusted(final Fraction... adjustments) {
            final Fraction adj = getAdjustment();
            for (final Fraction adjustment : adjustments)
                if (adjustment != null) {
                    multiply(adjustment);
                    adj.multiply(adjustment);
                }

            adjust();
            return (Standard) distinct();
        }

        /**
         * Divides this standard duration by the specified number and returns this standard duration or its equivalent singleton if one exists.
         *
         * @param n the number.
         *
         * @return the divided standard duration or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         * @throws IllegalStateException if the result is negative.
         */
        @Override
        public Standard by(final Number n) {
            divide(n);
            return (Standard) distinct();
        }

        /**
         * Creates and returns a copy of this standard duration.
         *
         * @return the clone of this standard duration.
         */
        @Override
        public Standard clone() {
            final Standard standard = new Standard(symbol, numerator, denominator, defaultDenominator, adjustment.clone());
            standard.setSimplification(simplification);
            return standard;
        }

        /**
         * Returns true if the standard duration value is equal to the value of the specified numeric object; and false otherwise.
         * <p>
         * If the specified object is a standard duration type, the adjustments are also compared.
         *
         * @param obj the object.
         *
         * @return true if the standard duration value is equal to the object value; and false otherwise.
         */
        @Override
        public boolean equals(final Object obj) {
            return super.equals(obj) &&
                   !(obj instanceof Standard && getAdjustment().compareTo(((Standard) obj).getAdjustment()) == 0);
        }

        /**
         * Inverts and returns this standard duration or its equivalent singleton if one exists.
         *
         * @return the inverted standard duration or its equivalent singleton.
         *
         * @throws IllegalStateException if the numerator is zero.
         */
        @Override
        public Standard inverted() {
            invert();
            return (Standard) distinct();
        }

        /**
         * Subtracts the specified number from this standard duration and returns this standard duration or its equivalent singleton if one exists.
         *
         * @param n the number.
         *
         * @return the subtracted standard duration or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         * @throws IllegalStateException if the result is negative.
         */
        @Override
        public Standard minus(final Number n) {
            subtract(n);
            return (Standard) distinct();
        }

        /**
         * Adds the specified number to this standard duration and returns this standard duration or its equivalent singleton if one exists.
         *
         * @param n the number.
         *
         * @return the added standard duration or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         * @throws IllegalStateException if the result is negative.
         */
        @Override
        public Standard plus(final Number n) {
            add(n);
            return (Standard) distinct();
        }

        /**
         * Multiplies this standard duration by the specified number and returns this standard duration or its equivalent singleton if one exists.
         *
         * @param n the number.
         *
         * @return the multiplied standard duration or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         * @throws IllegalStateException if the result is negative.
         */
        @Override
        public Standard times(final Number n) {
            multiply(n);
            return (Standard) distinct();
        }

        /**
         * Unadjusts the standard duration.
         */
        @Override
        public void unadjust() {
            super.divide(this.adjustment);
            adjustment = Unit;
        }

        /**
         * Unadjusts and returns this standard duration.
         *
         * @return the unadjusted standard duration.
         *
         * @see #unadjust()
         */
        @Override
        public Standard unadjusted() {
            unadjust();
            return (Standard) distinct();
        }

        /**
         * Returns the adjustment.
         *
         * @return the adjustment.
         */
        public
        Fraction getAdjustment() {
            return adjustment;
        }

        /**
         * Sets the adjustment.
         *
         * @param adjustment the adjustment.
         *
         * @throws NullPointerException if the specified adjustment is null.
         * @throws IllegalStateException if the specified adjustment value is negative.
         */
        public
        void setAdjustment(
            final Fraction adjustment
            ) {
            validateAdjustment(adjustment);
            super.divide(this.adjustment);
            super.multiply(adjustment);
            this.adjustment = adjustment;
        }
    }
}