package exceptions;

/**
 * Signals that an exception occurred when processing a score part for performance.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class PerformanceException
extends RuntimeException
{
    /**
     * Constructs a {@code PerformanceException} with the specified detail message and cause.
     *
     * @param message the message.
     * @param cause the cause.
     */
    public
    PerformanceException(
        final String message,
        final Throwable cause
        ) {
        super(message, cause);
    }

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