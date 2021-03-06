package exceptions;

/**
 * Signals that an exception occurred when associating an instrument class with a score part.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class UnsupportedInstrumentClassException
extends PerformanceException
{
    /**
     * Constructs an {@code UnsupportedInstrumentClassException} with the specified detail message and cause.
     *
     * @param message the message.
     * @param cause the cause.
     */
    public
    UnsupportedInstrumentClassException(
        final String message,
        final Throwable cause
        ) {
        super(message, cause);
    }

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