package system.data;

/**
 * {@code Constant} holds all commonly used messages in libraries and all commonly accepted symbols in data classes.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public final
class Constant
{
    // Exception and error messages
    public static final String AdjustmentTypeMismatch = "Unable to perform adjustment with parameter type.";
    public static final String BaseImplementationRestricted = "The base implementation cannot be called in any context";
    public static final String DivisionByZero = "Division by zero.";
    public static final String StandardObjectInoperable = "Standard object is inoperable";
    public static final String NegativeDuration = "Duration cannot be negative";
    public static final String NullAdjustment = "Adjustment cannot be null";
    public static final String OperationImpossible = "The operation is impossible";
    public static final String OrderOutOfRange = "order is out of range.";
    public static final String ZeroDenominator = "Denominator is zero";
    public static final String ZeroNumerator = "Numerator is zero";

    /**
     * {@code Fraction} holds all commonly known symbols used in mathematical fractions.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Fraction
    {
        static final String DividerSym = "/";
        static final String ZeroSym = "0";
    }
}