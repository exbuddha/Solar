package exceptions;

/**
 * Signals that an exception occurred when associating an instrument part with an instrument.
 */
public
class UnsupportedPartException
extends RuntimeException
{
    /**
     * Constructs an {@code UnsupportedPartException} with the specified detail message.
     *
     * @param message the message.
     */
    public
    UnsupportedPartException(
        final String message
        ) {
        super(message);
    }
}