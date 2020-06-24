package exceptions;

/**
 * Signals that an exception occurred when replacing the default instrument for a score part with another unsupported instrument in performance.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class NoninterchangeableInstrumentException
extends PerformanceException
{
    /**
     * Constructs a {@code NoninterchangeableInstrumentException} with the specified detail message and cause.
     *
     * @param message the message.
     * @param cause the cause.
     */
    public
    NoninterchangeableInstrumentException(
        final String message,
        final Throwable cause
        ) {
        super(message, cause);
    }

    /**
     * Constructs a {@code NoninterchangeableInstrumentException} with the specified detail message.
     *
     * @param message the message.
     */
    public
    NoninterchangeableInstrumentException(
        final String message
        ) {
        super(message);
    }
}