package system.data;

import static system.data.Constant.StandardObjectInoperable;
import static system.data.Constant.ZeroDenominator;
import static system.data.Constant.ZeroNumerator;
import static system.data.Constant.Fraction.*;

import java.util.function.BooleanSupplier;

/**
 * {@code Fraction} is a representation of fractional numbers in math.
 * <p>
 * For practical reasons, this class implementation is concerned only with calculations intended for musical durations.
 * Therefore, the numerator and denominator are accepted respectively as {@code int} and {@code short} values at instantiation time and any point in the logic after that.
 * All operations presume unlikeliness of underflow/overflow occurrences meaning that there are no validations done to avoid those cases.
 * <p>
 * All fractions can automatically perform simplification which reduces the numerator and denominator values to the smallest integer amount available.
 * Simplification flag is turned off by default for all operations unless explicitly set to on or when a non-zero default denominator is passed at instantiation time.
 * If a default denominator value is set and the simplification is turned on, after every operation the fraction will be simplified to a denominator value that is equal to the default denominator if possible; otherwise the fraction will be simplified normally.
 * <p>
 * Validation is automatically performed to guarantee that the denominator is not zero.
 * The denominator is converted to its absolute value during instantiation and simplification, and the negative sign is given to the numerator.
 * This means that it is impossible to force a fraction denominator to be negative at any time.
 * <p>
 * All methods in this class implementation are thread-safe.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class Fraction
extends Number
implements
    Comparable<Fraction>,
    Invertible,
    Inverting<Fraction>,
    Operable<Number>,
    Reversible,
    Reversing<Fraction>
{
    /** A constant holding the maximum value a {@code Fraction} can have, 2<sup>7</sup>-1. */
    public static final
    int MAX_VALUE = Integer.MAX_VALUE;

    /** A constant holding the minimum value a {@code Fraction} can have, -2<sup>31</sup>. */
    public static final
    int MIN_VALUE = Integer.MIN_VALUE;

    /** The {@code Class} instance representing the type {@code Fraction}. */
    public static final
    Class<Fraction> TYPE = Fraction.class;

    /** The numerator. */
    protected
    int numerator;

    /** The denominator. */
    protected
    short denominator;

    /** The simplification flag. */
    protected
    BooleanSupplier simplification;

    /** The optional default denominator. */
    protected
    short defaultDenominator;

    /** The operation lock. */
    protected
    Boolean OPERATION_LOCK = false;

    /**
     * Creates a fraction with the specified numerator, denominator, simplification flag, and default denominator.
     * When simplification flag is on, the fraction is simplified automatically.
     * If the flag is null, it will be set to off.
     * <p>
     * This constructor calls {@link Number#intValue()} on the numerator and {@link Number#shortValue()} on both denominators.
     *
     * @param numerator the numerator.
     * @param denominator the denominator.
     * @param simplification the simplification flag.
     * @param defaultDenominator the default denominator.
     *
     * @throws NullPointerException if the numerator or the denominator is null.
     * @throws IllegalArgumentException if the denominator is zero.
     */
    public
    Fraction(
        final Number numerator,
        final Number denominator,
        final BooleanSupplier simplification,
        final Number defaultDenominator
        ) {
        super();
        this.denominator = denominator.shortValue();
        validateDenominator();
        this.numerator = numerator.intValue();
        this.defaultDenominator = defaultDenominator == null
                                  ? (short) 0
                                  : defaultDenominator.shortValue();

        this.simplification = simplification;

        OPERATION_LOCK = true;
        simplifyConditionally();
    }

    /**
     * Creates a fraction with the specified numerator, denominator, and simplification flag.
     * When simplification flag is on, the fraction is simplified automatically.
     * If the flag is null, it will be set to off.
     * <p>
     * This constructor calls {@link Number#intValue()} on the numerator and {@link Number#shortValue()} on the denominator.
     *
     * @param numerator the numerator.
     * @param denominator the denominator.
     * @param simplification the simplification flag.
     *
     * @throws NullPointerException if the numerator or the denominator is null.
     * @throws IllegalArgumentException if the denominator is zero.
     */
    public
    Fraction(
        final Number numerator,
        final Number denominator,
        final BooleanSupplier simplification
        ) {
        this(numerator, denominator, simplification, (short) 0);
    }

    /**
     * Creates a fraction with the specified numerator, denominator, and default denominator.
     * The simplification flag is set to on unless the default denominator is null.
     * <p>
     * This constructor calls {@link Number#intValue()} on the numerator and {@link Number#shortValue()} on both denominators.
     *
     * @param numerator the numerator.
     * @param denominator the denominator.
     * @param defaultDenominator the default denominator.
     *
     * @throws NullPointerException if the numerator or the denominator is null.
     * @throws IllegalArgumentException if the denominator is zero.
     */
    public
    Fraction(
        final Number numerator,
        final Number denominator,
        final Number defaultDenominator
        ) {
        this(numerator, denominator, defaultDenominator == null ? Simplification.Off : Simplification.On, defaultDenominator);
    }

    /**
     * Creates a fraction with the specified numerator and denominator.
     * The simplification flag is set to off.
     * <p>
     * This constructor calls {@link Number#intValue()} on the numerator and {@link Number#shortValue()} on the denominator.
     *
     * @param numerator the numerator.
     * @param denominator the denominator.
     *
     * @throws NullPointerException if the numerator or the denominator is null.
     * @throws IllegalArgumentException if the denominator is zero.
     */
    public
    Fraction(
        final Number numerator,
        final Number denominator
        ) {
        this(numerator, denominator, Simplification.Off);
    }

    /**
     * Creates a fraction equal to the specified number.
     * The simplification flag is set to off unless the number is a fraction with simplification set to on.
     * <p>
     * This constructor calls {@link Number#intValue()} on the number.
     *
     * @param n the number.
     *
     * @throws NullPointerException if the number is null.
     */
    public
    Fraction(
        final Number n
        ) {
        super();
        denominator = (short) 1;
        numerator = n.intValue();
        simplification = Simplification.Off;

        OPERATION_LOCK = true;
        simplifyConditionally();
    }

    /**
     * Creates a fraction equal to the specified fraction.
     *
     * @param fraction the fraction.
     *
     * @throws NullPointerException if the fraction is null.
     */
    public
    Fraction(
        final Fraction fraction
        ) {
        denominator = fraction.denominator;
        validateDenominator();    // it is still possible the fraction is in bad state
        numerator = fraction.numerator;
        simplification = fraction.simplification;
        defaultDenominator = fraction.defaultDenominator;
    }

    /**
     * Creates a zero fraction with simplification flag set to off.
     */
    protected
    Fraction()
    {
        denominator = (short) 1;
        simplification = Simplification.Off;
    }

    /**
     * Creates a fraction from the specified string value.
     * <p>
     * The string value must be a valid fraction, matching the pattern "a/b", and may contain decimal numerator and denominator parts and leading or trailing whitespace in each part.
     * The numerator and denominator parts are parsed as {@code double} values.
     * The division string between the two fraction parts must match the value set by {@link Constant.Fraction#DividerSym}.
     * <p>
     * The simplification flag is set to off.
     * <p>
     * This constructor performs unsafe casts from {@code double} to {@code int}, for the numerator, and to {@code short} for the denominator.
     *
     * @param value the value.
     *
     * @throws NullPointerException if the value is null.
     * @throws NumberFormatException if the numerator or denominator part does not contain a parsable {@code double}.
     * @throws IllegalArgumentException if the denominator part value is zero.
     */
    public
    Fraction(
        final String value
        ) {
        super();
        set(this, value);
        simplification = Simplification.Off;

        OPERATION_LOCK = true;
        simplifyConditionally();
    }

    /**
     * Sets instance variables from the specified string value.
     *
     * @param instance the instance.
     * @param value the value.
     *
     * @throws NumberFormatException if the numerator or denominator part does not contain a parsable {@code double}.
     * @throws IllegalArgumentException if the denominator part value is zero.
     */
    protected static
    void set(
        final Fraction instance,
        final String value
        ) {
        final int divIndex = value.indexOf(DividerSym);
        if (divIndex == -1)
            instance.numerator = (int) Double.parseDouble(value.trim());
        else {
            instance.denominator = (short) Double.parseDouble(value.substring(divIndex + DividerSym.length()).trim());
            instance.validateDenominator();
            instance.numerator = (int) Double.parseDouble(value.substring(0, divIndex).trim());
        }
    }

    /**
     * Adds the specified fraction to this fraction.
     *
     * @param fraction the fraction.
     *
     * @throws NullPointerException if the specified fraction is null.
     */
    public
    void add(
        final Fraction fraction
        ) {
        synchronized (OPERATION_LOCK) {
            if (denominator == fraction.denominator)
                numerator += fraction.numerator;
            else {
                numerator = numerator * fraction.denominator + fraction.numerator * denominator;
                denominator *= fraction.denominator;
            }

            OPERATION_LOCK = true;
        }

        simplifyConditionally();
    }

    /**
     * Compares this fraction with the specified number and returns a negative integer if the fraction is less than the number, zero if they are equal, and a positive integer otherwise.
     * If the specified number is null, {@link Integer#MAX_VALUE} will be returned.
     *
     * @param n the number.
     *
     * @return -1 if the fraction is less than the number, zero if they are equal, and 1 otherwise; or {@link Integer#MAX_VALUE} if the number is null.
     */
    public
    int compareTo(
        final Number n
        ) {
        return n == null
               ? Integer.MAX_VALUE
               : (int) (doubleValue() - n.doubleValue());
    }

    /**
     * Divides this fraction by the specified fraction.
     *
     * @param fraction the fraction.
     *
     * @throws NullPointerException if the specified fraction is null.
     * @throws IllegalArgumentException if the specified fraction is zero.
     */
    public
    void divide(
        final Fraction fraction
        ) {
        synchronized (OPERATION_LOCK) {
            numerator *= fraction.denominator;
            denominator *= fraction.numerator;
            validateDenominator();

            OPERATION_LOCK = true;
        }

        simplifyConditionally();
    }

    /**
     * Returns true if the fraction value is equal to the value of the specified fraction; and false otherwise.
     *
     * @param fraction the fraction.
     *
     * @return true if the fraction value is equal to the fraction value; and false otherwise.
     */
    public
    boolean equals(
        final Fraction fraction
        ) {
        return compareTo(fraction) == 0;
    }

    /**
     * Returns true if the denominator matches the default denominator, and false otherwise.
     *
     * @return true if the denominator matches the default denominator, and false otherwise.
     */
    public
    boolean isSimplifiedByDefaultDenominator() {
        return denominator == Math.abs(defaultDenominator);
    }

    /**
     * Multiplies this fraction by the specified fraction.
     *
     * @param fraction the fraction.
     *
     * @throws NullPointerException if the specified fraction is null.
     */
    public
    void multiply(
        final Fraction fraction
        ) {
        synchronized (OPERATION_LOCK) {
            numerator *= fraction.numerator;
            denominator *= fraction.denominator;
            OPERATION_LOCK = true;
        }

        simplifyConditionally();
    }

    /**
     * Simplifies this fraction to its simplest form using the specified default denominator, and returns this fraction.
     *
     * @param defaultDenominator the default denominator.
     *
     * @return the simplified fraction.
     */
    public
    Fraction simplified(
        final short defaultDenominator
        ) {
        simplify(defaultDenominator);
        return this;
    }

    /**
     * Simplifies this fraction to its simplest form and returns this fraction.
     *
     * @return the simplified fraction.
     */
    public
    Fraction simplified() {
        simplify(defaultDenominator);
        return this;
    }

    /**
     * Simplifies the fraction to its simplest form using the specified default denominator.
     *
     * @param defaultDenominator the default denominator.
     */
    public
    void simplify(
        short defaultDenominator
        ) {
        if (OPERATION_LOCK) {
            if (defaultDenominator < 0)
                defaultDenominator = (short) -defaultDenominator;

            synchronized (OPERATION_LOCK) {
                if (OPERATION_LOCK) {
                    if (denominator < 0) {
                        numerator = -numerator;
                        denominator = (short) -denominator;
                    }

                    if (numerator == 0)
                        denominator = defaultDenominator == 0
                                      ? (short) 1
                                      : defaultDenominator;
                    else
                        if (denominator != defaultDenominator)
                            simplifyToDefaultDenominator(defaultDenominator);

                    OPERATION_LOCK = false;
        }}}
    }

    /**
     * Simplifies the fraction to its simplest form.
     */
    public
    void simplify() {
        simplify(defaultDenominator);
    }

    /**
     * Performs simplification if the simplification flag is set to on.
     */
    private
    void simplifyConditionally() {
        if (simplification.getAsBoolean())
            simplify();
    }

    /**
     * Performs regular simplification regardless of the default denominator value.
     */
    private
    void simplifyRegularly() {
        final int gcd = Lambda.gcd(Integer.signum(numerator) * numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
    }

    /**
     * Performs simplification matching the denominator to the default denominator if possible; otherwise performs regular simplification.
     *
     * @param defaultDenominator the default denominator.
     */
    private
    void simplifyToDefaultDenominator(
        short defaultDenominator
        ) {
        if (defaultDenominator == 0) {
            simplifyRegularly();
            return;
        }

        if (denominator % defaultDenominator == 0 && numerator % defaultDenominator == 0) {
            final int reduction = denominator / defaultDenominator;
            numerator /= reduction;
            denominator /= reduction;
        }
        else
            if (defaultDenominator % denominator == 0 && numerator % denominator == 0) {
                final int increase = defaultDenominator / denominator;
                numerator *= increase;
                denominator *= increase;
            }
            else
                simplifyRegularly();
    }

    /**
     * Subtracts the specified fraction from this fraction.
     *
     * @param fraction the fraction.
     *
     * @throws NullPointerException if the specified fraction is null.
     */
    public
    void subtract(
        final Fraction fraction
        ) {
        synchronized (OPERATION_LOCK) {
            if (denominator == fraction.denominator)
                numerator -= fraction.numerator;
            else {
                numerator = subtractedNumeratorPart(fraction);
                denominator *= fraction.denominator;
            }

            OPERATION_LOCK = true;
        }

        simplifyConditionally();
    }

    /**
     * Returns the non-simplified numerator part value after performing a subtraction from the specified fraction.
     *
     * @param fraction the fraction.
     *
     * @return the numerator part value.
     */
    private
    int subtractedNumeratorPart(
        final Fraction fraction
        ) {
        return numerator * fraction.denominator - denominator * fraction.numerator;
    }

    /**
     * Returns a string representation of the fraction including the denominator even when it is equal to 1.
     *
     * @return a full string representation of the fraction.
     */
    public
    String toStringWithDenominator() {
        return Integer.toString(numerator) + DividerSym + Short.toString(denominator);
    }

    /**
     * Validates the denominator to guarantee that it is non-zero; otherwise throws an {@code IllegalArgumentException}.
     *
     * @throws IllegalArgumentException if the denominator is zero.
     */
    protected
    void validateDenominator() {
        if (denominator == 0)
            throw new IllegalArgumentException(ZeroDenominator);
    }

    /**
     * Adds the specified number to this fraction.
     * <p>
     * This implementation calls {@link Number#intValue()} on the number.
     *
     * @param n the number.
     *
     * @throws NullPointerException if the number is null.
     */
    @Override
    public void add(final Number n) {
        synchronized (OPERATION_LOCK) {
            numerator += n.intValue() * denominator;
            OPERATION_LOCK = true;
        }

        simplifyConditionally();
    }

    /**
     * Divides this fraction by the specified number and returns this fraction.
     *
     * @param n the number.
     *
     * @return the divided fraction.
     *
     * @throws NullPointerException if the number is null.
     *
     * @see #divide(Number)
     */
    @Override
    public Fraction by(final Number n) {
        divide(n);
        return this;
    }

    /**
     * Creates and returns a copy of this fraction.
     *
     * @return a clone of this fraction.
     */
    @Override
    public Fraction clone() {
        return new Fraction(numerator, denominator, simplification, defaultDenominator);
    }

    /**
     * Compares this fraction with the specified fraction and returns a negative integer if this fraction is less than the fraction, zero if they are equal, and a positive integer otherwise.
     * If the specified fraction is null, {@link Integer#MAX_VALUE} will be returned.
     *
     * @param fraction the fraction.
     *
     * @return a negative integer if this fraction is less than the fraction, zero if they are equal, and a positive integer otherwise; or {@link Integer#MAX_VALUE} if the fraction is null.
     */
    @Override
    public int compareTo(final Fraction fraction) {
        return fraction == null
               ? Integer.MAX_VALUE
               : subtractedNumeratorPart(fraction);
    }

    /**
     * Divides this fraction by the specified number.
     *
     * @param n the number.
     *
     * @throws NullPointerException if the number is null.
     * @throws IllegalArgumentException if the number is zero.
     */
    @Override
    public void divide(final Number n) {
        synchronized (OPERATION_LOCK) {
            denominator *= n.intValue();
            validateDenominator();

            OPERATION_LOCK = true;
        }

        simplifyConditionally();
    }

    /**
     * Returns the value of the fraction as a {@code double}, which may involve rounding.
     *
     * @return the numeric value represented by this fraction after conversion to type {@code double}.
     */
    @Override
    public double doubleValue() {
        return (double) numerator / denominator;
    }

    /**
     * Returns true if the fraction value is equal to the value of the specified object; and false otherwise.
     *
     * @param obj the object.
     *
     * @return true if the fraction value is equal to the object value; and false otherwise.
     */
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Number && compareTo((Number) obj) == 0 ||
               (obj != null && obj.equals(this));
    }

    /**
     * Returns the value of the fraction as a {@code float}, which may involve rounding.
     *
     * @return the numeric value represented by this fraction after conversion to type {@code float}.
     */
    @Override
    public float floatValue() {
        return (float) numerator / denominator;
    }

    /**
     * Returns the value of the fraction as an {@code int}, which may involve rounding or truncation.
     *
     * @return the numeric value represented by this fraction after conversion to type {@code int}.
     */
    @Override
    public int intValue() {
        return numerator / denominator;
    }

    /**
     * Inverts the fraction into its reciprocal.
     * <p>
     * This implementation performs an unsafe cast from {@code int} to {@code short}.
     *
     * @throws IllegalStateException if the numerator is zero.
     */
    @Override
    public void invert() {
        synchronized (OPERATION_LOCK) {
            if (numerator == 0)
                throw new IllegalStateException(ZeroNumerator);

            numerator = denominator;
            denominator = (short) numerator;
            OPERATION_LOCK = true;
        }

        simplifyConditionally();
    }

    /**
     * Inverts and returns this fraction.
     *
     * @return the inverted fraction.
     *
     * @throws IllegalStateException if the numerator is zero.
     *
     * @see #invert()
     */
    @Override
    public Fraction inverted() {
        invert();
        return this;
    }

    /**
     * Returns the value of the fraction as an {@code long}, which may involve rounding or truncation.
     *
     * @return the numeric value represented by this fraction after conversion to type {@code long}.
     */
    @Override
    public long longValue() {
        return numerator / denominator;
    }

    /**
     * Subtracts the specified number from this fraction and returns this fraction.
     *
     * @param n the number.
     *
     * @return the subtracted fraction.
     *
     * @throws NullPointerException if the number is null.
     *
     * @see #subtract(Number)
     */
    @Override
    public Fraction minus(final Number n) {
        subtract(n);
        return this;
    }

    /**
     * Multiplies this fraction by the specified number.
     * <p>
     * This implementation call {@link Number#intValue()} on the number.
     *
     * @param n the number.
     *
     * @throws NullPointerException if the number is null.
     */
    @Override
    public void multiply(final Number n) {
        synchronized (OPERATION_LOCK) {
            numerator *= n.intValue();
            OPERATION_LOCK = true;
        }

        simplifyConditionally();
    }

    /**
     * Adds the specified number to this fraction and returns this fraction.
     *
     * @param n the number.
     *
     * @return the added fraction.
     *
     * @throws NullPointerException if the number is null.
     *
     * @see #add(Number)
     */
    @Override
    public Fraction plus(final Number n) {
        add(n);
        return this;
    }

    /**
     * Reverses the fraction by changing its sign.
     */
    @Override
    public void reverse() {
        synchronized (OPERATION_LOCK) {
            numerator = -numerator;
            OPERATION_LOCK = true;
        }
    }

    /**
     * Reverses this fraction, changing its sign, and returns this fraction.
     *
     * @return the reversed fraction.
     */
    @Override
    public Fraction reversed() {
        reverse();
        return this;
    }

    /**
     * Subtracts the specified number from this fraction.
     * <p>
     * This implementation call {@link Number#intValue()} on the number.
     *
     * @param n the number.
     *
     * @throws NullPointerException if the number is null.
     */
    @Override
    public void subtract(final Number n) {
        synchronized (OPERATION_LOCK) {
            numerator -= n.intValue() * denominator;
            OPERATION_LOCK = true;
        }

        simplifyConditionally();
    }

    /**
     * Multiplies this fraction by the specified number and returns this fraction.
     *
     * @param n the number.
     *
     * @return the multiplied fraction.
     *
     * @throws NullPointerException if the number is null.
     *
     * @see #multiply(Number)
     */
    @Override
    public Fraction times(final Number n) {
        multiply(n);
        return this;
    }

    /**
     * Returns a string representation of the fraction excluding the denominator when it is equal to 1.
     *
     * @return a simple string representation of the fraction.
     */
    @Override
    public String toString() {
        return denominator > 1
               ? Integer.toString(numerator) + DividerSym + Short.toString(denominator)
               : Integer.toString(numerator);
    }

    /**
     * Returns the default denominator.
     *
     * @return the default denominator.
     */
    public
    short getDefaultDenominator() {
        return defaultDenominator;
    }

    /**
     * Returns the denominator.
     *
     * @return the denominator.
     */
    public
    short getDenominator() {
        return denominator;
    }

    /**
     * Returns the numerator.
     *
     * @return the numerator.
     */
    public
    int getNumerator() {
        return numerator;
    }

    /**
     * Returns the simplification flag.
     *
     * @return the simplification flag.
     */
    public
    BooleanSupplier getSimplification() {
        return simplification;
    }

    /**
     * Sets the default denominator.
     *
     * @param defaultDenominator the default denominator.
     */
    public
    void setDefaultDenominator(
        final short defaultDenominator
        ) {
        this.defaultDenominator = defaultDenominator;
    }

    /**
     * Sets the denominator and validates it.
     * <p>
     * Negative values are converted to their absolute value.
     *
     * @param denominator the denominator.
     *
     * @throws IllegalArgumentException if the specified denominator is zero.
     */
    public
    void setDenominator(
        final short denominator
        ) {
        this.denominator = denominator;
        validateDenominator();
    }

    /**
     * Sets the numerator.
     *
     * @param numerator the numerator.
     */
    public
    void setNumerator(
        final int numerator
        ) {
        this.numerator = numerator;
    }

    /**
     * Sets the simplification flag.
     * <p>
     * If the specified flag is null the simplification flag is set to off.
     *
     * @param simplification the simplification flag.
     */
    public
    void setSimplification(
        final BooleanSupplier simplification
        ) {
        this.simplification = simplification == null
                              ? Simplification.Off
                              : simplification;
    }

    /**
     * {@code Singleton} represents fractions as system-wide singleton data for which all operations that change their state will throw an exception.
     * Simplifying a singleton, setting the simplification flag, or the default denominator will not affect the state.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class Singleton
    extends Fraction
    implements Symbolized.Singleton<String>
    {
        /** The {@code Class} instance representing the type {@code Singleton}. */
        public static final
        Class<Fraction.Singleton> TYPE = Fraction.Singleton.class;

        /** The zero singleton. */
        public static final
        Fraction.Singleton Zero = new Fraction.Singleton(ZeroSym, 0);

        /** The symbol. */
        private
        String symbol;

        /**
         * Creates a singleton fraction with the specified symbol, numerator, denominator, and default denominator.
         * The simplification flag is set to off.
         * <p>
         * This constructor calls {@link Number#intValue()} on the numerator and {@link Number#shortValue()} on the denominator.
         *
         * @param symbol the symbol.
         * @param numerator the numerator.
         * @param denominator the denominator.
         * @param defaultDenominator the default denominator.
         *
         * @throws NullPointerException if the numerator or the denominator is null.
         * @throws IllegalArgumentException if the denominator is zero.
         */
        public
        Singleton(
            final String symbol,
            final Number numerator,
            final Number denominator,
            final Number defaultDenominator
            ) {
            super(numerator, denominator, Simplification.Off, defaultDenominator);
            this.symbol = symbol;
        }

        /**
         * Creates a singleton fraction with the specified symbol, numerator, and denominator.
         * The simplification flag is set to off.
         * <p>
         * This constructor calls {@link Number#intValue()} on the numerator and {@link Number#shortValue()} on the denominator.
         *
         * @param symbol the symbol.
         * @param numerator the numerator.
         * @param denominator the denominator.
         *
         * @throws NullPointerException if the numerator or the denominator is null.
         * @throws IllegalArgumentException if the denominator is zero.
         */
        public
        Singleton(
            final String symbol,
            final Number numerator,
            final Number denominator
            ) {
            super(numerator, denominator, Simplification.Off);
            this.symbol = symbol;
        }

        /**
         * Creates a singleton fraction with the specified numerator, denominator, default denominator, and null symbol.
         * The simplification flag is set to off.
         * <p>
         * This constructor calls {@link Number#intValue()} on the numerator and {@link Number#shortValue()} on the denominator.
         *
         * @param numerator the numerator.
         * @param denominator the denominator.
         * @param defaultDenominator the default denominator.
         *
         * @throws NullPointerException if the numerator or the denominator is null.
         * @throws IllegalArgumentException if the denominator is zero.
         */
        public
        Singleton(
            final Number numerator,
            final Number denominator,
            final Number defaultDenominator
            ) {
            this((String) null, numerator, denominator, defaultDenominator);
        }

        /**
         * Creates a singleton fraction with the specified numerator and denominator, and null symbol.
         * The simplification flag is set to off.
         * <p>
         * This constructor calls {@link Number#intValue()} on the numerator and {@link Number#shortValue()} on the denominator.
         *
         * @param numerator the numerator.
         * @param denominator the denominator.
         *
         * @throws NullPointerException if the numerator or the denominator is null.
         * @throws IllegalArgumentException if the denominator is zero.
         */
        public
        Singleton(
            final Number numerator,
            final Number denominator
            ) {
            this((String) null, numerator, denominator);
        }

        /**
         * Creates a singleton fraction with the specified symbol and equal to the specified number.
         * The simplification flag is set to off.
         * <p>
         * This constructor calls {@link Number#intValue()} on the number.
         *
         * @param symbol the symbol.
         * @param n the number.
         *
         * @throws NullPointerException if the number is null.
         */
        public
        Singleton(
            final String symbol,
            final Number n
            ) {
            super(n);
            this.symbol = symbol;
        }

        /**
         * Creates a singleton fraction equal to the specified number.
         * The simplification flag is set to off.
         * <p>
         * This constructor calls {@link Number#intValue()} on the number.
         *
         * @param n the number.
         *
         * @throws NullPointerException if the number is null.
         */
        public
        Singleton(
            final Number n
            ) {
            this((String) null, n);
        }

        /**
         * Creates a singleton fraction with the specified symbol and equal to the specified fraction.
         * The simplification flag is set to off.
         *
         * @param symbol the symbol.
         * @param fraction the fraction.
         *
         * @throws NullPointerException if the fraction is null.
         */
        public
        Singleton(
            final String symbol,
            final Fraction fraction
            ) {
            super(fraction);
            simplification = Simplification.Off;
            this.symbol = symbol;
        }

        /**
         * Creates a singleton fraction equal to the specified fraction and null symbol.
         * The simplification flag is set to off.
         *
         * @param fraction the fraction.
         *
         * @throws NullPointerException if the fraction is null.
         */
        public
        Singleton(
            final Fraction fraction
            ) {
            this((String) null, fraction);
        }

        /**
         * Creates a fraction from the specified symbol and string value.
         * <p>
         * The string value must be a valid fraction, matching the pattern "a/b", and may contain decimal numerator and denominator parts and leading or trailing whitespace in each part.
         * The numerator and denominator parts are parsed as {@code double} values.
         * The division string between the two fraction parts must match the value set by {@link Constant.Fraction#DividerSym}.
         * <p>
         * The simplification flag is set to off.
         * <p>
         * This constructor performs unsafe casts from {@code double} to {@code int}, for the numerator, and to {@code short} for the denominator.
         *
         * @param symbol the symbol.
         * @param value the value.
         *
         * @throws NullPointerException if the value is null.
         * @throws NumberFormatException if the numerator or denominator part does not contain a parsable {@code double}.
         * @throws IllegalArgumentException if the denominator part value is zero.
         */
        public
        Singleton(
            final String symbol,
            final String value
            ) {
            super(value);
            this.symbol = symbol;
        }

        /**
         * Creates a fraction from the specified string value and with a symbol equal to the same value.
         * <p>
         * The string value must be a valid fraction, matching the pattern "a/b", and may contain decimal numerator and denominator parts and leading or trailing whitespace in each part.
         * The numerator and denominator parts are parsed as {@code double} values.
         * The division string between the two fraction parts must match the value set by {@link Constant.Fraction#DividerSym}.
         * <p>
         * The simplification flag is set to off.
         * <p>
         * This constructor performs unsafe casts from {@code double} to {@code int}, for the numerator, and to {@code short} for the denominator.
         *
         * @param value the value.
         *
         * @throws NullPointerException if the value is null.
         * @throws NumberFormatException if the numerator or denominator part does not contain a parsable {@code double}.
         * @throws IllegalArgumentException if the denominator part value is zero.
         */
        public
        Singleton(
            final String value
            ) {
            this(value, value);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the specified fraction is equal to zero.
         *
         * @param fraction the fraction.
         *
         * @throws NullPointerException if the specified fraction is null.
         * @throws UnsupportedOperationException if the specified fraction is not equal to zero.
         */
        @Override
        public void add(final Fraction fraction) {
            if (fraction.numerator != 0)
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the specified number is equal to zero.
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
         * Clones this singleton and divides the copy by the specified number and return it.
         *
         * @param n the number.
         *
         * @return the divided fraction.
         *
         * @throws NullPointerException if the number is null.
         *
         * @see Fraction#divide(Number)
         */
        @Override
        public Fraction by(final Number n) {
            return clone().by(n);
        }

        /**
         * Creates and returns a copy of this singleton.
         *
         * @return a clone of this singleton.
         */
        @Override
        public Fraction.Singleton clone() {
            return new Fraction.Singleton(symbol, numerator, denominator, defaultDenominator) {
                @Override
                public void setSymbol(final String symbol) {
                    Fraction.Singleton.this.symbol = symbol;
                }
            };
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the specified fraction is exactly equal to 1.
         *
         * @param fraction the fraction.
         *
         * @throws NullPointerException if the specified fraction is null.
         * @throws UnsupportedOperationException if the specified fraction is not exactly equal to 1.
         */
        @Override
        public void divide(final Fraction fraction) {
            if (fraction.floatValue() != 1F)
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the specified number is equal to 1.
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
         * This implementation throws an {@code UnsupportedOperationException} unless the singleton value is exactly equal to 1.
         *
         * @throws UnsupportedOperationException if the singleton value is not exactly equal to 1.
         */
        @Override
        public void invert() {
            if (numerator != denominator)
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * Clone this singleton and inverts the copy and returns it.
         *
         * @return the inverted fraction.
         *
         * @throws IllegalStateException if the numerator is zero.
         *
         * @see Fraction#invert()
         */
        @Override
        public Fraction inverted() {
            return clone().inverted();
        }

        /**
         * Clones this singleton and subtracts the specified number from the copy and return it.
         *
         * @return the subtracted fraction.
         *
         * @throws NullPointerException if the number is null.
         *
         * @see Fraction#subtract(Number)
         */
        @Override
        public Fraction minus(final Number n) {
            return clone().minus(n);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the specified fraction is exactly equal to 1.
         *
         * @throws NullPointerException if the specified fraction is null.
         * @throws UnsupportedOperationException if the specified fraction is not exactly equal to 1.
         */
        @Override
        public void multiply(final Fraction fraction) {
            if (fraction.floatValue() != 1F)
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the specified number is equal to 1.
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
         * Clones this singleton and adds the specified number to the copy and returns it.
         *
         * @return the added fraction.
         *
         * @throws NullPointerException if the number is null.
         *
         * @see Fraction#add(Number)
         */
        @Override
        public Fraction plus(final Number n) {
            return clone().plus(n);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the singleton value is equal to zero.
         *
         * @throws UnsupportedOperationException if the singleton value is not equal to zero.
         */
        @Override
        public void reverse() {
            if (numerator != 0)
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * Clone this singleton and reverses the copy and returns it.
         *
         * @return the reversed fraction.
         *
         * @see Fraction#reverse()
         */
        @Override
        public Fraction reversed() {
            return clone().reversed();
        }

        /**
         * This implementation returns the singleton.
         */
        @Override
        public Fraction.Singleton simplified(short defaultDenominator) {
            return this;
        }

        /**
         * This implementation returns the singleton.
         */
        @Override
        public Fraction.Singleton simplified() {
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
         * This implementation throws an {@code UnsupportedOperationException} unless the specified fraction is equal to zero.
         *
         * @param fraction the fraction.
         *
         * @throws NullPointerException if the specified fraction is null.
         * @throws UnsupportedOperationException if the specified fraction is not equal to zero.
         */
        @Override
        public void subtract(final Fraction fraction) {
            if (fraction.numerator != 0)
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the specified number is equal to zero.
         *
         * @param n the number.
         *
         * @throws NullPointerException if the number is null.
         * @throws UnsupportedOperationException if the number is not equal to zero.
         */
        @Override
        public void subtract(final Number n) {
            if (!n.equals(0))
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }

        /**
         * Clones this singleton and multiplies the copy by the specified number and returns it.
         *
         * @param n the number.
         *
         * @return the multiplied fraction.
         *
         * @throws NullPointerException if the number is null.
         *
         * @see Fraction#multiply(Number)
         */
        @Override
        public Fraction times(final Number n) {
            return clone().times(n);
        }

        /**
         * Returns the symbol of the singleton.
         *
         * @return the symbol.
         */
        @Override
        public String getSymbol() {
            return symbol;
        }

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
     * {@code Simplification} categorizes the simplification flag.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    enum Simplification
    implements BooleanSupplier
    {
        /** Off. */
        Off,

        /** On. */
        On;

        /**
         * Returns true if the simplification flag is on, and false otherwise.
         *
         * @return true if the flag is on, and false otherwise.
         */
        @Override
        public boolean getAsBoolean() {
            return this == On;
        }
    }
}