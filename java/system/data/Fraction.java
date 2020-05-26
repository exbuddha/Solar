package system.data;

/**
 * {@code Fraction} is a representation of rational numbers in math.
 * <p>
 * <ul>
 * <li>Denominator cannot be zero.
 * <li>Simplification is done by default for all operations including the constructors and setters.
 * <li>Validation is done by default only for operations that change the value of the denominator.
 * <li>For practical reasons, numerator and denominator are defined as {@code long} values and presumes unlikeliness of underflow/overflow occurrences.
 * </ul>
 */
public
class Fraction
extends Number
implements
    Cloneable,
    Comparable<Fraction>
{
    /** The numerator. */
    protected
    long numerator;

    /** The denominator. */
    protected
    long denominator;

    /**
     * Creates a fraction with the specified numerator and denominator.
     * <p>
     * This constructor calls {@link Number#longValue()} on both numbers.
     *
     * @param numerator the numerator.
     * @param denominator the denominator.
     * @throws IllegalArgumentException if the denominator is zero.
     */
    public
    Fraction(
        final Number numerator,
        final Number denominator
        )
    throws IllegalArgumentException {
        this.numerator = numerator.longValue();
        this.denominator = denominator.longValue();
        validate();
        simplify();
    }

    /**
     * Creates a fraction equal to the specified number.
     * <p>
     * This constructor calls {@link Number#longValue()} on the number.
     *
     * @param n the number.
     */
    public
    Fraction(
        final Number n
        ) {
        this(n, 1L);
    }

    /**
     * Adds the specified fraction to this fraction.
     *
     * @param fraction the fraction.
     */
    public
    void add(
        final Fraction fraction
        ) {
        synchronized (this) {
            if (denominator == fraction.denominator)
                numerator += fraction.numerator;
            else {
                numerator = numerator * fraction.denominator + fraction.numerator * denominator;
                denominator *= fraction.denominator;
            }
        }

        simplify();
    }

    /**
     * Adds the specified number to this fraction.
     * <p>
     * This method converts the number into a new {@code Fraction}.
     *
     * @param n the number.
     */
    public
    void add(
        final Number n
        ) {
        add(new Fraction(n));
    }

    /**
     * Compares the specified number with this fraction and returns -1 if the fraction is less than the number, 0 if they are equal, and 1 otherwise.
     *
     * @param n the number.
     * @return -1 if the fraction is less than the number, 0 if they are equal, and 1 otherwise.
     */
    public
    int compareTo(
        final Number n
        ) {
        return (int) Math.signum(doubleValue() - n.doubleValue());
    }

    /**
     * Divides this fraction by the specified fraction.
     *
     * @param fraction the fraction to divide this fraction by.
     */
    public
    void divide(
        final Fraction fraction
        ) {
        synchronized (this) {
            numerator /= fraction.denominator;
            denominator /= fraction.numerator;
        }

        validate();
        simplify();
    }

    /**
     * Divides this fraction by the specified number.
     * <p>
     * This method converts the number into a new {@code Fraction}.
     *
     * @param n the number.
     */
    public
    void divide(
        final Number n
        ) {
        divide(new Fraction(n));
    }

    /**
     * Inverts the fraction.
     *
     * @throws IllegalStateException if the numerator is zero.
     */
    public
    void invert()
    throws IllegalStateException {
        synchronized (this) {
            if (numerator == 0)
                throw new IllegalStateException("Numerator is zero.");

            final long n = numerator;
            numerator = denominator;
            denominator = n;
        }
    }

    /**
     * Multiplies this fraction by the specified fraction.
     *
     * @param fraction the fraction.
     */
    public
    void multiply(
        final Fraction fraction
        ) {
        synchronized (this) {
            numerator *= fraction.numerator;
            denominator *= fraction.denominator;
        }

        simplify();
    }

    /**
     * Multiplies this fraction by the specified number.
     * <p>
     * This method converts the number into a new {@code Fraction}.
     *
     * @param n the number.
     */
    public
    void multiply(
        final Number n
        ) {
        multiply(new Fraction(n));
    }

    /**
     * Simplifies the fraction to its simplest form.
     */
    protected
    void simplify() {
        synchronized (this) {
            if (denominator < 0) {
                numerator = -numerator;
                denominator = -denominator;
            }

            if (numerator == 0)
                denominator = 1L;
            else {
                final long gcd = Lambda.gcd(Long.signum(numerator) * numerator, denominator);
                numerator /= gcd;
                denominator /= gcd;
            }
        }
    }

    /**
     * Subtracts the specified fraction from this fraction.
     *
     * @param fraction the fraction.
     */
    public
    void subtract(
        final Fraction fraction
        ) {
        synchronized (this) {
            if (denominator == fraction.denominator)
                numerator -= fraction.numerator;
            else {
                numerator = numerator * fraction.denominator - fraction.numerator * denominator;
                denominator *= fraction.denominator;
            }
        }

        simplify();
    }

    /**
     * Subtracts the specified number from this fraction.
     * <p>
     * This method converts the number into a new {@code Fraction}.
     *
     * @param n the number.
     */
    public
    void subtract(
        final Number n
        ) {
        subtract(new Fraction(n));
    }

    /**
     * Returns a string representation of the fraction including the denominator even when it is equal to 1.
     */
    public
    String toStringWithDenominator() {
        return numerator + "/" + denominator;
    }

    /**
     * Validates the fraction to make sure the denominator is not zero, or throws an {@code IllegalArgumentException} exception.
     *
     * @throws IllegalArgumentException if the denominator is zero.
     */
    protected
    void validate() {
        if (denominator == 0)
            throw new IllegalArgumentException("Denominator is zero.");
    }

    @Override
    public Fraction clone() {
        return new Fraction(numerator, denominator);
    }

    @Override
    public int compareTo(final Fraction fraction) {
        return Long.signum(numerator * fraction.denominator - fraction.numerator * denominator);
    }

    @Override
    public double doubleValue() {
        return (double) numerator / denominator;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj != null &&
               (
               (obj instanceof Fraction && compareTo((Fraction) obj) == 0) ||
               (obj instanceof Number && compareTo((Number) obj) == 0)
               );
    }

    /**
     * Returns the value of the fraction as a {@code float}, which may involve rounding or truncating; or throws a {@code RuntimeException} type if the fraction cannot be casted to {@code float}.
     */
    @Override
    public float floatValue() {
        return (float) numerator / denominator;
    }

    /**
     * Returns the value of the fraction as an {@code int}, which may involve rounding or truncation; or throws a {@code RuntimeException} type if the fraction cannot be casted to {@code int}.
     */
    @Override
    public int intValue() {
        return (int) (numerator / denominator);
    }

    @Override
    public long longValue() {
        return numerator / denominator;
    }

    /**
     * Returns a simplified string representation of the fraction excluding the denominator when it is equal to 1.
     */
    @Override
    public String toString() {
        return denominator > 1 ? numerator + "/" + denominator : "" + numerator;
    }

    /**
     * Returns the denominator.
     *
     * @return the denominator.
     */
    public
    long getDenominator() {
        return denominator;
    }

    /**
     * Returns the numerator.
     *
     * @return the numerator.
     */
    public
    long getNumerator() {
        return numerator;
    }

    /**
     * Sets the denominator.
     * <p>
     * This method calls {@link Number#longValue()} on the denominator.
     *
     * @param denominator the denominator.
     */
    public
    void setDenominator(
        final Number denominator
        ) {
        this.denominator = denominator.longValue();
        validate();
        simplify();
    }

    /**
     * Sets the numerator.
     * <p>
     * This method calls {@link Number#longValue()} on the numerator.
     *
     * @param numerator the numerator.
     */
    public
    void setNumerator(
        final Number numerator
        ) {
        this.numerator = numerator.longValue();
        simplify();
    }
}