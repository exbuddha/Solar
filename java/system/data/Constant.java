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
    public static final String AdjustmentTypeMismatch = "Unable to perform adjustment with parameter type";
    public static final String BaseImplementationRestricted = "The base implementation cannot be called in any context";
    //public static final String DefaultValueNotAvailable = "Default value is not available";
    public static final String DivisionByZero = "Division by zero";
    public static final String StandardObjectInoperable = "Standard object is inoperable";
    public static final String NegativeDuration = "Duration cannot be negative";
    public static final String NullAdjustment = "Adjustment cannot be null";
    public static final String OperationImpossible = "The operation is impossible";
    public static final String OrderOutOfRange = "Order is out of range";
    public static final String XmlChildNotFound = "There are no child elements";
    public static final String XmlElementUnsupported = "Unsupported XML element type";
    public static final String XmlEntityNameInvalid = "Entity name is invalid";
    public static final String ZeroDenominator = "Denominator is zero";
    public static final String ZeroNumerator = "Numerator is zero";

    public static
    String colon(
        final String msg
        ) {
        if (msg == null)
            return "";

        final int length = msg.length();
        if (msg.substring(length - 2).equals(": "))
            return msg;

        if (msg.charAt(length - 1) == ':')
            return msg + " ";

        return msg + ": ";
    }

    /**
     * {@code Fraction} holds all commonly known symbols used in mathematical fractions.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Fraction
    {
        String DividerSym = "/";
        String ZeroSym = "0";
    }

    /**
     * {@code XML} holds all commonly known names used in XML schema files or other system-related definitions.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface XML
    {
        String IndentKey = "{http://xml.apache.org/xslt}indent-amount";

        /**
         * {@code Occurrence} holds all standard XML entity occurrence indicator values used in the schema files.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Occurrence
        {
            String OneOrMore = "+";
            String OnlyOne = "";
            String ZeroOrMore = "*";
            String ZeroOrOne = "?";
            String ZeroOrOneOrTwo = "??";
        }
    }
}