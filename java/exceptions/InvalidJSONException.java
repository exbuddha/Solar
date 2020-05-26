package exceptions;

/**
 * Signals that an exception occurred when parsing a JSON file.
 */
public
class InvalidJSONException
extends Exception
{
    /**
     * Constructs an {@code InvalidJSONException} with the specified detail message and original exception.
     *
     * @param message the message.
     * @param e the original exception.
     */
    public
    InvalidJSONException(
        final String message,
        final Exception e
        ) {
        super(message, e);
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