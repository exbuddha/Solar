package exceptions;

/**
 * Signals that an exception occurred when processing a score part for performance.
 */
public
class PerformanceException
extends RuntimeException
{
    /**
     * Constructs a {@code PerformanceException} with the specified detail message.
     *
     * @param message the message.
     */
    public
    PerformanceException(
        final String message
        ) {
        super(message);
    }
}