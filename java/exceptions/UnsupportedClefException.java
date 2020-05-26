package exceptions;

/**
 * Signals that an exception occurred when associating a clef with a score part.
 */
public
class UnsupportedClefException
extends PerformanceException
{
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