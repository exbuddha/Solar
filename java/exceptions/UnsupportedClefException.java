package exceptions;

/**
 * Signals that an exception occurred when associating a clef with a score part.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class UnsupportedClefException
extends PerformanceException
{
    /**
     * Constructs an {@code UnsupportedClefException} with the specified detail message and cause.
     *
     * @param message the message.
     * @param cause the cause.
     */
    public
    UnsupportedClefException(
        final String message,
        final Throwable cause
        ) {
        super(message);
    }

    /**
     * Constructs an {@code UnsupportedClefException} with the specified detail message.
     *
     * @param message the message.
     */
    public
    UnsupportedClefException(
        final String message
        ) {
        super(message);
    }
}