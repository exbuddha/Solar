package exceptions;

/**
 * Signals that an exception occurred when replacing the default instrument for a score part with another unsupported instrument in performance.
 */
public
class NoninterchangeableInstrumentException
extends PerformanceException
{
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