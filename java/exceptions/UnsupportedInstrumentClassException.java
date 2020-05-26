package exceptions;

/**
 * Signals that an exception occurred when associating an instrument class with a score part.
 */
public
class UnsupportedInstrumentClassException
extends PerformanceException
{
    /**
     * Constructs an {@code UnsupportedInstrumentClassException} with the specified detail message.
     *
     * @param message the message.
     */
    public
    UnsupportedInstrumentClassException(
        final String message
        ) {
        super(message);
    }
}