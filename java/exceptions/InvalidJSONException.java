package exceptions;

/**
 * Signals that an exception occurred when parsing a JSON file or processing the data in the document.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class InvalidJSONException
extends Exception
{
    /**
     * Constructs an {@code InvalidJSONException} with the specified detail message and cause.
     *
     * @param message the message.
     * @param cause the cause.
     */
    public
    InvalidJSONException(
        final String message,
        final Throwable cause
        ) {
        super(message, cause);
    }

    /**
     * Constructs an {@code InvalidJSONException} with the specified detail message.
     *
     * @param message the message.
     */
    public
    InvalidJSONException(
        final String message
        ) {
        super(message);
    }
}