package musical;

import static musical.Constant.Interval.*;
import static system.data.Constant.DivisionByZero;
import static system.data.Constant.StandardObjectInoperable;

import java.util.Comparator;
import java.util.function.Function;

import music.system.data.Clockable.ProgressiveDataPoint;
import music.system.data.Clockable.Progressor;
import music.system.data.Delta;
import music.system.data.Ordered;
import system.data.Fraction;
import system.data.Invertible;
import system.data.Inverting;
import system.data.Lambda;
import system.data.Operable;
import system.data.Reversible;
import system.data.Reversing;
import system.data.Symbolized;

/**
 * {@code Interval} represents a musical interval which is a measure of distance (pitch difference) between notes.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class Interval
extends Number
implements
    Adjusting<Interval, Number>,
    Cloneable,
    Delta<Short>,
    IntervalType,
    Invertible,
    Inverting<Interval>,
    Operable<Number>,
    Ordered<Byte>,
    Reversible,
    Reversing<Interval>,
    Symbolized<String>,
    Unit
{
    /** A constant holding the maximum value a {@code Interval} can have, 2<sup>15</sup>-1. */
    public static final
    int MAX_VALUE = Short.MAX_VALUE;

    /** A constant holding the minimum value a {@code Interval} can have, -2<sup>15</sup>. */
    public static final
    int MIN_VALUE = Short.MIN_VALUE;

    /** The {@code Class} instance representing the type {@code Interval}. */
    public static final
    Class<Interval> TYPE = Interval.class;

    /** The perfect unison interval. (0 cents) */
    public static final
    Singleton Unison = new Singleton(PerfectUnisonSym, (short) 0);

    /** The quarter tone interval. (50 cents) */
    public static final
    Singleton QuarterTone = new Singleton(QuarterToneSym, (short) 50);

    /** The minor second interval. (100 cents) */
    public static final
    Singleton MinorSecond = new Singleton(MinorSecondSym, (short) 100);

    /** The major second interval. (200 cents) */
    public static final
    Singleton MajorSecond = new Singleton(MajorSecondSym, (short) 200);

    /** The minor third interval. (300 cents) */
    public static final
    Singleton MinorThird = new Singleton(MinorThirdSym, (short) 300);

    /** The major third interval. (400 cents) */
    public static final
    Singleton MajorThird = new Singleton(MajorThirdSym, (short) 400);

    /** The perfect fourth interval. (500 cents) */
    public static final
    Singleton PerfectFourth = new Singleton(PerfectFourthSym, (short) 500);

    /** The augmented fourth interval. (600 cents) */
    public static final
    Singleton AugmentedFourth = new Singleton(AugmentedFourthSym, (short) 600);

    /** The diminished fifth interval. (600 cents) */
    public static final
    Singleton DiminishedFifth = new Singleton(DiminishedFifthSym, (short) 600);

    /** The perfect fifth interval. (700 cents) */
    public static final
    Singleton PerfectFifth = new Singleton(PerfectFifthSym, (short) 700);

    /** The minor sixth interval. (800 cents) */
    public static final
    Singleton MinorSixth = new Singleton(MinorSixthSym, (short) 800);

    /** The major sixth interval. (900 cents) */
    public static final
    Singleton MajorSixth = new Singleton(MajorSixthSym, (short) 900);

    /** The minor seventh interval. (1000 cents) */
    public static final
    Singleton MinorSeventh = new Singleton(MinorSeventhSym, (short) 1000);

    /** The major seventh interval. (1100 cents) */
    public static final
    Singleton MajorSeventh = new Singleton(MajorSeventhSym, (short) 1100);

    /** The perfect octave interval. (1200 cents) */
    public static final
    Singleton PerfectOctave = new Singleton(PerfectOctaveSym, (short) 1200);

    static
    {
        Unison.setMode(Mode.Perfect);
        QuarterTone.setMode(Mode.Quarter);
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
        PerfectOctave.setMode(Mode.Perfect);
    }

    /** The interval symbol. */
    protected
    String symbol;

    /** The interval width. (cents) */
    protected
    short cents;

    /**
     * Creates an interval with the specified symbol and cents.
     *
     * @param symbol the symbol.
     * @param cents the cents.
     */
    public
    Interval(
        final String symbol,
        final short cents
        ) {
        super();
        setSymbol(symbol);
        setCents(cents);
    }

    /**
     * Creates an interval with the specified cents and null symbol.
     *
     * @param cents the cents.
     */
    public
    Interval(
        final short cents
        ) {
        this(null, cents);
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
     * Creates an interval with null symbol and the specified cents and null symbol.
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
     * Creates an interval with the specified symbol and cents magnitude.
     *
     * @param symbol the symbol.
     * @param cents the cents.
     *
     * @throws NullPointerException if the cents is null.
     */
    public
    Interval(
        final String symbol,
        final Cents cents
        ) {
        this(symbol, cents.getOrder());
    }

    /**
     * Creates an interval with the specified cents magnitude and null symbol.
     *
     * @param cents the cents.
     *
     * @throws NullPointerException if the cents is null.
     */
    public
    Interval(
        final Cents cents
        ) {
        this(null, cents);
    }

    /**
     * Creates an interval with the specified symbol and semitones magnitude.
     * <p>
     * This constructor converts the semitones amount to cents.
     *
     * @param symbol the symbol.
     * @param semitones the semitones.
     *
     * @throws NullPointerException if the semitones is null.
     */
    public
    Interval(
        final String symbol,
        final Semitones semitones
        ) {
        this(symbol, semitones.getCents());
    }

    /**
     * Creates an interval with the specified semitones magnitude and null symbol.
     * <p>
     * This constructor converts the semitones amount to cents.
     *
     * @param semitones the semitones.
     *
     * @throws NullPointerException if the semitones is null.
     */
    public
    Interval(
        final Semitones semitones
        ) {
        this(null, semitones);
    }

    /**
     * Creates an interval with the specified symbol and from the specified string value.
     * <p>
     * The string value must be a valid interval symbol, double, or fraction matching the pattern "a/b", which may contain decimal numerator and denominator parts and leading or trailing whitespace in each part.
     *
     * @param symbol the symbol.
     * @param value the value.
     *
     * @throws NullPointerException if the value is null.
     * @throws NumberFormatException if the value does not contain a parsable {@code double} and is not a valid fraction value.
     *
     * @see Fraction#Fraction(String)
     */
    public
    Interval(
        final String symbol,
        final String value
        ) {
        super();

        if (value == null)
            throw new NullPointerException();

        setSymbol(symbol);
        Interval interval = Lambda.findFirst(null, new Function<Object, Boolean>() {
                                                       @Override
                                                       public Boolean apply(final Object obj) {
                                                           final Interval interval = (Interval) obj;
                                                           return value.equals(interval.getSymbol()) ||
                                                                  value.equals(Short.toString(interval.getCents()));
                                                       }
                                                   });
        if (interval == null)
            set(this, value);
        else
            setCents(interval.getCents());
    }

    /**
     * Creates an interval from the specified string value and with a symbol equal to the same value.
     * <p>
     * The string value must be a valid interval symbol, double, or fraction matching the pattern "a/b", which may contain decimal numerator and denominator parts and leading or trailing whitespace in each part.
     *
     * @param value the value.
     *
     * @throws NullPointerException if the value is null.
     * @throws NumberFormatException if the value does not contain a parsable {@code double} and is not a valid fraction value.
     *
     * @see Fraction#Fraction(String)
     */
    public
    Interval(
        final String value
        ) {
        this(value, value);
    }

    /**
     * Returns true if the specified interval is a statically defined instance (singleton); otherwise returns false.
     *
     * @param interval the interval.
     *
     * @return true if the interval is a singleton, and false otherwise.
     */
    public static
    boolean isSingleton(
        final Interval interval
        ) {
        return interval instanceof Singleton;
    }

    /**
     * Returns true if the specified interval is standard, and false otherwise.
     *
     * @param interval the interval.
     *
     * @return true if interval is standard, and false otherwise.
     */
    public static
    boolean isStandard(
        final Interval interval
        ) {
        return interval instanceof Standard;
    }

    /**
     * Sets instance variables from the specified string value.
     *
     * @param instance the instance.
     * @param value the value.
     *
     * @throws NumberFormatException if the value does not contain a parsable {@code double} and is not a valid fraction value.
     */
    protected static
    void set(
        final Interval instance,
        final String value
        ) {
        Number n;
        try {
            n = Double.parseDouble(value);
        }
        catch (NumberFormatException e) {
            n = new Fraction(value);
        }

        instance.setCents(n);
    }

    /**
     * Returns the specified singleton if it is not null; otherwise returns the specified fallback interval.
     *
     * @param singleton the singleton.
     * @param fallback the fallback.
     *
     * @return the singleton, if it is not null, or the fallback.
     */
    private static
    Interval singleton(
        final Standard singleton,
        final Interval fallback
        ) {
        return singleton == null
               ? fallback
               : singleton;
    }

    /**
     * Adds the specified cents to this interval.
     *
     * @param cents the cents.
     *
     * @throws NullPointerException if the cents is null.
     * @throws UnsupportedOperationException if this interval is a singleton and the cents value is not equal to zero.
     *
     * @see #add(Number)
     */
    public
    void add(
        final Cents cents
        ) {
        add(cents.getOrder());
    }

    /**
     * Adds the specified semitones to this interval.
     *
     * @param semitones the semitones.
     *
     * @throws NullPointerException if the semitones is null.
     * @throws UnsupportedOperationException if this interval is a singleton and the semitones value is not equal to zero.
     *
     * @see #add(Number)
     */
    public
    void add(
        final Semitones semitones
        ) {
        add(semitones.getCents());
    }

    /**
     * Adjusts this interval with the specified cents adjustments and returns the adjusted interval.
     * <p>
     * If this is a standard interval with an equivalent singleton, the singleton is returned.
     *
     * @param adjustments the adjustments. (cents)
     *
     * @return the adjusted interval.
     *
     * @see #adjusted(Number[])
     */
    public
    Interval adjusted(
        final Cents... adjustments
        ) {
        for (final Cents adjustment : adjustments)
            if (adjustment != null)
                add(adjustment.getOrder());

        return this;
    }

    /**
     * Adjusts this interval with the specified semitones adjustments and returns the adjusted interval.
     * <p>
     * If this is a standard interval with an equivalent singleton, the singleton is returned.
     *
     * @param adjustments the adjustments. (semitones)
     *
     * @return the adjusted interval.
     *
     * @see #adjusted(Number[])
     */
    public
    Interval adjusted(
        final Semitones... adjustments
        ) {
        for (final Semitones adjustment : adjustments)
            if (adjustment != null)
                add(adjustment.getCents());

        return this;
    }

    /**
     * Divides this interval by the specified cents and returns this interval.
     * <p>
     * If this is a standard interval the equivalent singleton will be returned, when one exists.
     *
     * @param cents the cents.
     *
     * @return the divided interval or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the cents is null.
     *
     * @see #by(Number)
     */
    public
    Interval by(
        final Cents cents
        ) {
        return by(cents.getOrder());
    }

    /**
     * Divides this interval by the specified number and returns this interval.
     * <p>
     * If this is a standard interval the equivalent singleton will be returned, when one exists.
     *
     * @param semitones the semitones.
     *
     * @return the divided interval or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the semitones is null.
     *
     * @see #by(Number)
     */
    public
    Interval by(
        final Semitones semitones
        ) {
        return by(semitones.getCents());
    }

    /**
     * Returns the singleton equivalent to this interval using the specified comparator, or itself if none is found.
     * <p>
     * This implementation calls {@link Comparator#compare(Object, Object)} with this interval as the first argument and the singletons as second arguments of that method.
     *
     * @param comparator the comparator.
     *
     * @return the equivalent singleton or this interval.
     */
    public
    Interval distinct(
        final Comparator<Interval> comparator
        ) {
        return singleton((Standard) Lambda.sortedFind(this, Singleton.Order, comparator), this);
    }

    /**
     * Returns the singleton equivalent to this interval using the specified preferred modes, or itself if none is found.
     *
     * @param modes the preferred modes.
     *
     * @return the equivalent singleton or this interval.
     *
     * @see #distinct(Comparator)
     */
    public
    Interval distinct(
        final Mode... modes
        ) {
        return distinct(new Comparator<Interval>() {
            @Override
            public int compare(final Interval i1, final Interval i2) {
                return i1.compareTo(i2) == 0 && Lambda.findFirst(((Standard) i2).getMode(), modes) != null
                       ? 0
                       : 1;
            }
        });
    }

    /**
     * Divides this interval by the specified cents.
     *
     * @param cents the cents.
     *
     * @throws NullPointerException if the cents is null.
     * @throws IllegalArgumentException if the cents value is zero.
     * @throws UnsupportedOperationException if this interval is a singleton and the cents value is not equal to 1.
     *
     * @see #divide(Number)
     */
    public
    void divide(
        final Cents cents
        ) {
        divide(cents.getOrder());
    }

    /**
     * Divides this interval by the specified semitones.
     *
     * @param semitones the semitones.
     *
     * @throws NullPointerException if the semitones is null.
     * @throws IllegalArgumentException if the semitones value is zero.
     * @throws UnsupportedOperationException if this interval is a singleton and the semitones value is not equal to 1.
     *
     * @see #divide(Number)
     */
    public
    void divide(
        final Semitones semitones
        ) {
        divide(semitones.getCents());
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
     * Subtracts the specified cents from this interval and returns this interval.
     * <p>
     * If this is a standard interval the equivalent singleton will be returned, when one exists.
     *
     * @param cents the cents.
     *
     * @return the subtracted interval or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the cents is null.
     *
     * @see #minus(Number)
     */
    public
    Interval minus(
        final Cents cents
        ) {
        return minus(cents.getOrder());
    }

    /**
     * Subtracts the specified number from this interval and returns this interval.
     * <p>
     * If this is a standard interval the equivalent singleton will be returned, when one exists.
     *
     * @param semitones the semitones.
     *
     * @return the subtracted interval or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the semitones is null.
     *
     * @see #minus(Number)
     */
    public
    Interval minus(
        final Semitones semitones
        ) {
        return minus(semitones.getCents());
    }

    /**
     * Multiplies this interval by the specified cents.
     *
     * @param cents the cents.
     *
     * @throws NullPointerException if the cents is null.
     * @throws UnsupportedOperationException if this interval is a singleton and the cents value is not equal to 1.
     *
     * @see #multiply(Number)
     */
    public
    void multiply(
        final Cents cents
        ) {
        multiply(cents.getOrder());
    }

    /**
     * Multiplies this interval by the specified semitones.
     *
     * @param semitones the semitones.
     *
     * @throws NullPointerException if the number is null.
     * @throws UnsupportedOperationException if this interval is a singleton and the semitones value is not equal to 1.
     *
     * @see #multiply(Number)
     */
    public
    void multiply(
        final Semitones semitones
        ) {
        multiply(semitones.getCents());
    }

    /**
     * Creates and returns a data point representing this interval.
     * <p>
     * This implementation returns a data point with {@code x} mapped to the interval order, {@code y} mapped to the interval mode, and {@code z} mapped to null.
     * If this is not a standard interval, null is used for the interval mode.
     *
     * @return the interval data point.
     */
    public
    ProgressiveDataPoint<Byte, Progressor, Progressor> newDataPoint() {
        return new ProgressiveDataPoint<Byte, Progressor, Progressor>()
        {
            @Override
            public Byte x() {
                return Interval.this.getOrder();
            }

            @Override
            public Progressor y() {
                return null;
            }

            @Override
            public Progressor z() {
                return null;
            }
        };
    }

    /**
     * Adds the specified cents to this interval and returns this interval.
     * <p>
     * If this is a standard interval the equivalent singleton will be returned, when one exists.
     *
     * @param cents the cents.
     *
     * @return the added interval or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the cents is null.
     *
     * @see #plus(Number)
     */
    public
    Interval plus(
        final Cents cents
        ) {
        return plus(cents.getOrder());
    }

    /**
     * Adds the specified semitones to this interval and returns this interval.
     * <p>
     * If this is a standard interval the equivalent singleton will be returned, when one exists.
     *
     * @param semitones the semitones.
     *
     * @return the added interval or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the semitones is null.
     *
     * @see #plus(Number)
     */
    public
    Interval plus(
        final Semitones semitones
        ) {
        return plus(semitones.getCents());
    }

    /**
     * Subtracts the specified cents from this interval.
     *
     * @param cents the cents.
     *
     * @throws NullPointerException if the cents is null.
     * @throws UnsupportedOperationException if this interval is a singleton and the cents value is not equal to zero.
     *
     * @see #subtract(Number)
     */
    public
    void subtract(
        final Cents cents
        ) {
        subtract(cents.getOrder());
    }

    /**
     * Subtracts the specified semitones from this interval.
     *
     * @param semitones the number.
     *
     * @throws NullPointerException if the semitones is null.
     * @throws UnsupportedOperationException if this interval is a singleton and the semitones value is not equal to zero.
     *
     * @see #subtract(Number)
     */
    public
    void subtract(
        final Semitones semitones
        ) {
        subtract(semitones.getCents());
    }

    /**
     * Multiplies this interval by the specified cents and returns this interval.
     * <p>
     * If this is a standard interval the equivalent singleton will be returned, when one exists.
     *
     * @param cents the cents.
     *
     * @return the multiplied interval or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the cents is null.
     *
     * @see #times(Number)
     */
    public
    Interval times(
        final Cents cents
        ) {
        return times(cents.getOrder());
    }

    /**
     * Multiplies this interval by the specified semitones and returns this interval.
     * <p>
     * If this is a standard interval the equivalent singleton will be returned, when one exists.
     *
     * @param semitones the semitones.
     *
     * @return the multiplied interval or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the semitones is null.
     *
     * @see #times(Number)
     */
    public
    Interval times(
        final Semitones semitones
        ) {
        return times(semitones.getCents());
    }

    /**
     * Adds the specified number, as cents, to this interval.
     * <p>
     * This implementation calls {@link Number#shortValue()} on the number.
     *
     * @param n the number.
     *
     * @throws NullPointerException if the number is null.
     * @throws UnsupportedOperationException if this interval is a singleton and the number is not equal to zero.
     */
    @Override
    public void add(final Number n) {
        setCents(getCents() + n.shortValue());
    }

    /**
     * Adjusts this interval with the specified adjustments, as cents, and returns the adjusted interval.
     * <p>
     * This implementation calls {@link Number#shortValue()} on all the adjustment values.
     * <p>
     * If this is a standard interval with an equivalent singleton, the singleton is returned.
     *
     * @param adjustments the adjustments.
     *
     * @return the adjusted interval.
     */
    @Override
    public Interval adjusted(final Number... adjustments) {
        for (final Number adjustment : adjustments)
            if (adjustment != null)
                add(adjustment);

        return this;
    }

    /**
     * Divides this interval by the specified number and returns this interval.
     * <p>
     * If this is a standard interval the equivalent singleton will be returned, when one exists.
     *
     * @param n the number.
     *
     * @return the divided interval or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the number is null.
     *
     * @see #divide(Number)
     */
    @Override
    public Interval by(final Number n) {
        divide(n);
        return this;
    }

    /**
     * Creates and returns a copy of this interval.
     *
     * @return the copy of interval.
     */
    @Override
    public Interval clone() {
        return isStandard(this)
               ? ((Standard) this).clone()
               : new Interval(symbol, cents);
    }

    /**
     * Returns the singleton equivalent to this interval or itself if none is found.
     *
     * @return the equivalent singleton or this interval.
     */
    @Override
    public Interval distinct() {
        return singleton((Standard) Lambda.sortedFind(this, Singleton.Order), this);
    }

    /**
     * Divides this interval by the specified number.
     * <p>
     * This implementation calls {@link Number#intValue()} on the number.
     *
     * @param n the number.
     *
     * @throws NullPointerException if the number is null.
     * @throws IllegalArgumentException if the number is zero.
     * @throws UnsupportedOperationException if this interval is a singleton and the number is not equal to 1.
     */
    @Override
    public void divide(final Number n) {
        final int d = n.intValue();
        if (d == 0)
            throw new IllegalArgumentException(DivisionByZero);;

        setCents(getCents() / d);
    }

    /**
     * Returns the interval cents as a {@code double}.
     *
     * @return the numeric value represented by this interval cents after conversion to type {@code double}.
     */
    @Override
    public double doubleValue() {
        return getCents();
    }

    /**
     * Returns true if the interval has the same amount of cents as the specified numeric object, and false otherwise.
     *
     * @param obj the object.
     *
     * @return true if the interval is equal to the object, and false otherwise.
     */
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Number &&
               getCents() == ((Number) obj).shortValue() ||
               (obj != null && obj.equals(this));
    }

    /**
     * Returns the interval cents as a {@code float}.
     *
     * @return the numeric value represented by this interval cents after conversion to type {@code float}.
     */
    @Override
    public float floatValue() {
        return getCents();
    }

    /**
     * Returns the order of this interval as the rounded number of semitones.
     *
     * @return the order.
     *
     * @see #getSemitones()
     */
    @Override
    public Byte getOrder() {
        return getSemitones();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns the {@code Byte} class.
     *
     * @return the {@code Byte} class.
     */
    @Override
    public Class<Byte> getOrderClass() {
        return Byte.class;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns the {@code Short} class.
     *
     * @return the {@code Short} class.
     */
    @Override
    public Class<Short> getUnit() {
        return Short.class;
    }

    /**
     * Returns the interval cents as an {@code int}.
     *
     * @return the numeric value represented by this interval cents after conversion to type {@code int}.
     */
    @Override
    public int intValue() {
        return getCents();
    }

    /**
     * Inverts the interval.
     * <p>
     * This implementation returns the smallest interval that completes the octave when added to this interval.
     * The interval direction is preserved.
     *
     * @throws UnsupportedOperationException if this interval is a singleton.
     */
    @Override
    public void invert() {
        short cents = getCents();
        setCents(cents == 0
                 ? 1200
                 : (short) (Integer.signum(cents) * (cents % 1200)));
    }

    /**
     * Inverts and returns this interval.
     * <p>
     * If this is a standard interval the equivalent singleton will be returned, when one exists.
     * <p>
     * If this is one of the {@link #AugmentedFourth} or {@link #DiminishedFifth} singletons, it will be inverted to its other form.
     *
     * @return the inverted interval or its equivalent singleton if this is a standard type.
     */
    @Override
    public Interval inverted() {
        invert();
        return this;
    }

    /**
     * Returns true if this interval is equal to the specified type; otherwise returns false.
     * <p>
     * If this is a singleton, this method returns true if the runtime instances are the same and false otherwise.
     *
     * @param type the other interval type.
     *
     * @return true if this interval is equal to the type, or if this singleton is the same runtime instance as the type.
     */
    @Override
    public boolean is(final system.data.Type<? extends IntervalType> type) {
        return equals(type);
    }

    /**
     * Returns the interval cents as a {@code long}.
     *
     * @return the numeric value represented by this interval cents after conversion to type {@code long}.
     */
    @Override
    public long longValue() {
        return getCents();
    }

    /**
     * Subtracts the specified number from this interval and returns this interval.
     * <p>
     * If this is a standard interval the equivalent singleton will be returned, when one exists.
     *
     * @param n the number.
     *
     * @return the subtracted interval or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the number is null.
     *
     * @see #subtract(Number)
     */
    @Override
    public Interval minus(final Number n) {
        subtract(n);
        return this;
    }

    /**
     * Multiplies this interval by the specified number.
     * <p>
     * This implementation calls {@link Number#intValue()} on the number.
     *
     * @param n the number.
     *
     * @throws NullPointerException if the number is null.
     * @throws UnsupportedOperationException if this interval is a singleton and the number is not equal to 1.
     */
    @Override
    public void multiply(final Number n) {
        setCents(getCents() * n.intValue());
    }

    /**
     * Adds the specified number to this interval and returns this interval.
     * <p>
     * If this is a standard interval the equivalent singleton will be returned, when one exists.
     *
     * @param n the number.
     *
     * @return the added interval or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the number is null.
     *
     * @see #add(Number)
     */
    @Override
    public Interval plus(final Number n) {
        add(n);
        return this;
    }

    /**
     * Reverses the interval.
     */
    @Override
    public void reverse() {
        setCents((short) -getCents());
    }

    /**
     * Reverses and returns this interval.
     * <p>
     * If this is a standard interval the equivalent singleton will be returned, when one exists.
     */
    @Override
    public Interval reversed() {
        reverse();
        return this;
    }

    /**
     * Subtracts the specified number from this interval.
     * <p>
     * This implementation calls {@link Number#shortValue()} on the number.
     *
     * @param n the number.
     *
     * @throws NullPointerException if the number is null.
     * @throws UnsupportedOperationException if this interval is a singleton and the number is not equal to zero.
     */
    @Override
    public void subtract(final Number n) {
        setCents(getCents() - n.shortValue());
    }

    /**
     * Multiplies this interval by the specified number and returns this interval.
     * <p>
     * If this is a standard interval the equivalent singleton will be returned, when one exists.
     *
     * @param n the number.
     *
     * @return the multiplied interval or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the number is null.
     *
     * @see #multiply(Number)
     */
    @Override
    public Interval times(final Number n) {
        multiply(n);
        return this;
    }

    /**
     * Returns a string representation of the interval.
     * <p>
     * This implementation returns the interval symbol if it not null; otherwise the cents amount concatenated with a single whitespace and {@link Constant.Interval#CentsSym} will be returned.
     */
    @Override
    public String toString() {
        final String symbol = getSymbol();
        if (symbol == null)
            return Constant.Interval.adjusted(getCents());
        else
            return symbol;
    }

    /**
     * Returns the number of cents in the interval.
     *
     * @return the cents.
     */
    @Override
    public short getCents() {
        return cents;
    }

    /**
     * Returns the symbol of the interval.
     *
     * @return the symbol.
     */
    @Override
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the number of cents in the interval.
     *
     * @param cents the cents.
     */
    public
    void setCents(
        final short cents
        ) {
        this.cents = cents;
    }

    /**
     * Sets the number of cents in the interval.
     *
     * @param cents the cents.
     */
    public
    void setCents(
        final Number cents
        ) {
        setCents(cents.shortValue());
    }

    /**
     * Sets the number of cents in the interval.
     *
     * @param cents the cents.
     */
    public
    void setCents(
        final Cents cents
        ) {
        setCents(cents.getOrder());
    }

    /**
     * Sets the number of semitones in the interval.
     *
     * @param semitones the semitones.
     */
    public
    void setSemitones(
        final short semitones
        ) {
        setCents((short) (semitones * 100));
    }

    /**
     * Sets the number of semitones in the interval.
     *
     * @param semitones the semitones.
     */
    public
    void setSemitones(
        final Number semitones
        ) {
        setCents((short) (semitones.shortValue() * 100));
    }

    /**
     * Sets the number of semitones in the interval.
     *
     * @param semitones the semitones.
     */
    public
    void setSemitones(
        final Semitones semitones
        ) {
        setCents(semitones.getCents());
    }

    /**
     * Sets the symbol of the interval.
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
     * {@code Mode} categorizes all standard interval modes in classical music.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    enum Mode
    implements
        IntervalModeType,
        Symbolized.Singleton<String>
    {
        /** The augmented mode. */
        Augmented,

        /** The diminished mode. */
        Diminished,

        /** The major mode. */
        Major,

        /** The minor mode. */
        Minor,

        /** The perfect mode. */
        Perfect,

        /** The quarter mode. */
        Quarter;

        /**
         * Returns true if this mode is the same runtime instance of the specified type.
         *
         * @param type the other mode type.
         */
        @Override
        public boolean is(final system.data.Type<? extends IntervalModeType> type) {
            return this == type;
        }

        /**
         * Returns the symbol of the mode.
         *
         * @return the symbol.
         */
        @Override
        public String getSymbol() {
            return toString();
        }
    }

    /**
     * {@code Sequence} classifies interval sequences.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Sequence
    {
        /**
         * Returns the array of intervals in this sequence.
         *
         * @return the intervals array.
         */
        Interval[] getIntervals();
    }

    /**
     * {@code Singleton} represents all standard interval types in classical music.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    private static final
    class Singleton
    extends Standard
    implements Symbolized.Singleton<String>
    {
        /** The {@code Class} instance representing the type {@code Singleton}. */
        public static final
        Class<Interval.Singleton> TYPE = Interval.Singleton.class;

        /** The array of duration singletons. (ascending) */
        private static final
        Interval.Singleton[] Order
        = new Interval.Singleton[] {
            Unison,
            QuarterTone,
            MinorSecond,
            MajorSecond,
            MinorThird,
            MajorThird,
            PerfectFourth,
            AugmentedFourth,
            DiminishedFifth,
            PerfectFifth,
            MinorSixth,
            MajorSixth,
            MinorSeventh,
            MajorSeventh,
            PerfectOctave
        };

        /**
         * Creates a singleton interval with the specified symbol and cents.
         *
         * @param symbol the symbol.
         * @param cents the cents.
         */
        public
        Singleton(
            final String symbol,
            final short cents
            ) {
            super(symbol, cents);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the number is equal to zero.
         *
         * @param n the number.
         *
         * @throws NullPointerException if the number is null.
         * @throws UnsupportedOperationException if the number is not equal to zero.
         */
        @Override
        public void add(final Number n) {
            if (!n.equals(0))
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * Clones this singleton and adjusts the copy with the specified adjustments, as cents, and returns it or the equivalent singleton if one exists.
         * <p>
         * This implementation calls {@link Number#shortValue()} on all the adjustment values.
         *
         * @param adjustments the adjustments.
         *
         * @return the adjusted standard interval or its equivalent singleton.
         */
        @Override
        public Standard adjusted(final Number... adjustments) {
            return (Standard) clone().adjusted(adjustments);
        }

        /**
         * Clones this singleton and divides the copy by the specified number and returns it or the equivalent singleton if one exists.
         *
         * @param n the number.
         *
         * @return the divided standard interval or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         *
         * @see Interval#divide(Number)
         */
        @Override
        public Standard by(final Number n) {
            return clone().by(n);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the number is equal to 1.
         *
         * @param n the number.
         *
         * @throws NullPointerException if the number is null.
         * @throws UnsupportedOperationException if the number is not equal to 1.
         */
        @Override
        public void divide(final Number n) {
            if (!n.equals(1))
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException}.
         *
         * @throws UnsupportedOperationException
         */
        @Override
        public void invert() {
            throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * Clone this singleton and inverts the copy and returns it or the equivalent singleton if one exists.
         * <p>
         * If this is one of the {@link #AugmentedFourth} or {@link #DiminishedFifth} singletons, it will be inverted to its other form.
         *
         * @return the inverted standard interval or its equivalent singleton.
         */
        @Override
        public Standard inverted() {
            return (Standard) invertedSpecially(this).clone().inverted().distinct();
        }

        /**
         * Performs a special inversion if this is one of the {@link #AugmentedFourth} or {@link #DiminishedFifth} singletons; otherwise returns the fallback.
         *
         * @param fallback the fallback.
         *
         * @return the specially inverted singleton or the fallback.
         */
        @Override
        protected Standard invertedSpecially(
            final Standard fallback
            ) {
            if (this == AugmentedFourth)
                return DiminishedFifth;
            else
                if (this == DiminishedFifth)
                    return AugmentedFourth;
                else
                    return fallback;
        }

        /**
         * Returns true if this singleton is the same runtime instance of the specified type.
         *
         * @param type the other interval type.
         *
         * @return true if the runtime instances are same.
         */
        @Override
        public boolean is(final system.data.Type<? extends IntervalType> type) {
            return this == type;
        }

        /**
         * Clones this singleton and subtracts the specified number from the copy and returns it or the equivalent singleton if one exists.
         *
         * @param n the number.
         *
         * @return the subtracted standard interval or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         *
         * @see Interval#subtract(Number)
         */
        @Override
        public Standard minus(final Number n) {
            return clone().minus(n);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the number is equal to 1.
         *
         * @param n the number.
         *
         * @throws NullPointerException if the number is null.
         * @throws UnsupportedOperationException if the number is not equal to 1.
         */
        @Override
        public void multiply(final Number n) {
            if (!n.equals(1))
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * Clones this singleton and adds the specified number to the copy and returns it standard interval or the equivalent singleton if one exists.
         *
         * @param n the number.
         *
         * @return the added standard interval or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         *
         * @see Interval#add(Number)
         */
        @Override
        public Standard plus(final Number n) {
            return clone().plus(n);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException}.
         */
        @Override
        public void reverse() {
            // Singletons are not reversible
            throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * Clones this singleton and reverses the copy and returns it or the equivalent singleton if one exists.
         *
         * @return the reversed standard interval or its equivalent singleton.
         */
        @Override
        public Standard reversed() {
            return clone().reversed();
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the number is equal to 1.
         *
         * @param n the number.
         *
         * @throws NullPointerException if the number is null.
         * @throws UnsupportedOperationException if the number is not equal to 1.
         */
        @Override
        public void subtract(final Number n) {
            if (!n.equals(1))
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * Clones this singleton and multiplies the copy by the specified number and returns it or the equivalent singleton if one exists.
         *
         * @param n the number.
         *
         * @return the multiplied standard interval or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         *
         * @see Interval#multiply(Number)
         */
        @Override
        public Standard times(final Number n) {
            return clone().times(n);
        }
    }

    /**
     * {@code Standard} represents all standard musical intervals.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected static
    class Standard
    extends Interval
    {
        /** A constant holding the maximum value a {@code Standard} can have, 1200. */
        public static final
        int MAX_VALUE = 1200;

        /** A constant holding the minimum value a {@code Standard} can have, 0. */
        public static final
        int MIN_VALUE = 0;

        /** The {@code Class} instance representing the type {@code Standard}. */
        public static final
        Class<Standard> TYPE = Standard.class;

        /** The interval mode. (optional) */
        protected
        Mode mode;

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
         * Creates a standard interval with the specified cents and null symbol.
         *
         * @param cents the cents.
         */
        public
        Standard(
            final short cents
            ) {
            this(null, cents);
        }

        /**
         * Creates a standard interval with the specified symbol and number of cents.
         * <p>
         * This constructor calls {@link Number#shortValue()} on the number.
         *
         * @param symbol the symbol.
         * @param number the number.
         *
         * @throws NullPointerException if the number is null.
         */
        public
        Standard(
            final String symbol,
            final Number number
            ) {
            super(symbol, number);
        }

        /**
         * Creates a standard interval with null symbol and the specified number of cents and null symbol.
         * <p>
         * This constructor calls {@link Number#shortValue()} on the number.
         *
         * @param number the number.
         *
         * @throws NullPointerException if the number is null.
         */
        public
        Standard(
            final Number number
            ) {
            super(number);
        }

        /**
         * Creates a standard interval with the specified symbol and cents magnitude.
         *
         * @param symbol the symbol.
         * @param cents the cents.
         *
         * @throws NullPointerException if the cents is null.
         */
        public
        Standard(
            final String symbol,
            final Cents cents
            ) {
            super(symbol, cents);
        }

        /**
         * Creates a standard interval with the specified cents magnitude and null symbol.
         *
         * @param cents the cents.
         *
         * @throws NullPointerException if the cents is null.
         */
        public
        Standard(
            final Cents cents
            ) {
            this(null, cents);
        }

        /**
         * Creates a standard interval with the specified symbol and semitones magnitude.
         * <p>
         * This constructor converts the semitones amount to cents.
         *
         * @param symbol the symbol.
         * @param semitones the semitones.
         *
         * @throws NullPointerException if the semitones is null.
         */
        public
        Standard(
            final String symbol,
            final Semitones semitones
            ) {
            super(symbol, semitones);
        }

        /**
         * Creates a standard interval with the specified semitones magnitude and null symbol.
         * <p>
         * This constructor converts the semitones amount to cents.
         *
         * @param semitones the semitones.
         *
         * @throws NullPointerException if the semitones is null.
         */
        public
        Standard(
            final Semitones semitones
            ) {
            this(null, semitones);
        }

        /**
         * Creates a standard interval with the specified symbol and from the specified string value.
         * <p>
         * The string value must be a valid interval symbol, double, or fraction matching the pattern "a/b", which may contain decimal numerator and denominator parts and leading or trailing whitespace in each part.
         *
         * @param symbol the symbol.
         * @param value the value.
         *
         * @throws NullPointerException if the value is null.
         * @throws NumberFormatException if the value does not contain a parsable {@code double} and is not a valid fraction value.
         *
         * @see Fraction#Fraction(String)
         */
        public
        Standard(
            final String symbol,
            final String value
            ) {
            super(symbol, value);
        }

        /**
         * Creates a standard interval from the specified string value and with a symbol equal to the same value.
         * <p>
         * The string value must be a valid interval symbol, double, or fraction matching the pattern "a/b", which may contain decimal numerator and denominator parts and leading or trailing whitespace in each part.
         *
         * @param value the value.
         *
         * @throws NullPointerException if the value is null.
         * @throws NumberFormatException if the value does not contain a parsable {@code double} and is not a valid fraction value.
         *
         * @see Fraction#Fraction(String)
         */
        public
        Standard(
            final String value
            ) {
            this(value, value);
        }

        /**
         * Performs a special inversion if this standard interval is equal to one of the {@link #AugmentedFourth} or {@link #DiminishedFifth} singletons; otherwise returns the fallback.
         *
         * @param fallback the fallback.
         *
         * @return the specially inverted standard interval or the fallback.
         */
        protected
        Standard invertedSpecially(
            final Standard fallback
            ) {
            if (equals(AugmentedFourth))
                return DiminishedFifth.clone();
            else
                if (equals(DiminishedFifth))
                    return AugmentedFourth.clone();
                else
                    return fallback;
        }

        /**
         * Adjusts this standard interval with the specified adjustments, as cents, and returns this standard interval or the equivalent singleton if one exists.
         * <p>
         * This implementation calls {@link Number#shortValue()} on all the adjustment values.
         *
         * @param adjustments the adjustments.
         *
         * @return the adjusted standard interval or its equivalent singleton.
         */
        @Override
        public Standard adjusted(final Number... adjustments) {
            return (Standard) super.adjusted(adjustments).distinct();
        }

        /**
         * Divides this standard interval by the specified number and returns this standard interval or its equivalent singleton, when one exists.
         *
         * @param n the number.
         *
         * @return the divided standard interval or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         *
         * @see Interval#divide(Number)
         */
        @Override
        public Standard by(final Number n) {
            return (Standard) super.by(n).distinct();
        }

        /**
         * Creates and returns a copy of this standard interval.
         *
         * @return the copy of the standard interval.
         */
        @Override
        public Standard clone() {
            Standard standard = new Standard(symbol, cents);
            standard.setMode(getMode());
            return standard;
        }

        /**
         * Returns true if the specified object is an instance of the {@code Interval} class and has the same amount of cents as this interval, and false otherwise.
         * <p>
         * If the specified object is a standard duration, the modes will also be compared.
         *
         * @param obj the object.
         *
         * @return true if the object is an equal interval, and false otherwise.
         */
        @Override
        public boolean equals(final Object obj) {
            return super.equals(obj) &&
                   !(obj instanceof Standard && getMode() != ((Standard) obj).getMode());
        }

        /**
         * Inverts and returns this standard interval or its equivalent singleton, when one exists.
         * <p>
         * If this is equal to one of the {@link #AugmentedFourth} or {@link #DiminishedFifth} singletons, it will be inverted to its other form.
         *
         * @return the inverted standard interval or its equivalent singleton.
         */
        @Override
        public Standard inverted() {
            return invertedSpecially((Standard) super.inverted().distinct());
        }

        /**
         * Subtracts the specified number from this standard interval and returns this standard interval or its equivalent singleton, when one exists.
         *
         * @param n the number.
         *
         * @return the subtracted standard interval or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         *
         * @see Interval#subtract(Number)
         */
        @Override
        public Standard minus(final Number n) {
            return (Standard) super.minus(n).distinct();
        }

        /**
         * Creates and returns a data point representing this standard interval.
         * <p>
         * This implementation returns a data point with {@code x} mapped to the interval order, {@code y} mapped to the interval mode, and {@code z} mapped to null.
         *
         * @return the interval data point.
         */
        public
        ProgressiveDataPoint<Byte, Progressor, Progressor> newDataPoint() {
            return new ProgressiveDataPoint<Byte, Progressor, Progressor>()
            {
                @Override
                public Byte x() {
                    return Standard.this.getOrder();
                }

                @Override
                public Progressor y() {
                    return Standard.this.getMode();
                }

                @Override
                public Progressor z() {
                    return null;
                }
            };
        }

        /**
         * Adds the specified number to this standard interval and returns this standard interval or its equivalent singleton, when one exists.
         *
         * @param n the number.
         *
         * @return the added standard interval or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         *
         * @see Interval#add(Number)
         */
        @Override
        public Standard plus(final Number n) {
            return (Standard) super.plus(n).distinct();
        }

        /**
         * Reverses and returns this standard interval or the equivalent singleton if one exists.
         *
         * @return the reversed standard interval or its equivalent singleton.
         */
        @Override
        public Standard reversed() {
            return (Standard) super.reversed().distinct();
        }

        /**
         * Multiplies this standard interval by the specified number and returns this standard interval or its equivalent singleton, when one exists.
         *
         * @param n the number.
         *
         * @return the multiplied standard interval or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         *
         * @see #multiply(Number)
         */
        @Override
        public Standard times(final Number n) {
            return (Standard) super.times(n).distinct();
        }

        /**
         * Returns the interval mode.
         *
         * @return the interval mode.
         */
        public
        Mode getMode() {
            return mode;
        }

        /**
         * Sets the interval mode.
         *
         * @param mode the interval mode.
         */
        public
        void setMode(
            final Mode mode
            ) {
            this.mode = mode;
        }
    }
}