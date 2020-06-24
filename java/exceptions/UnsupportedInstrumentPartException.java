package exceptions;

/**
 * Signals that an exception occurred when associating an instrument part with an instrument.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class UnsupportedInstrumentPartException
extends RuntimeException
{
    /**
     * Constructs an {@code UnsupportedInstrumentPartException} with the specified detail message and cause.
     *
     * @param message the message.
     * @param cause the cause.
     */
    public
    UnsupportedInstrumentPartException(
        final String message,
        final Throwable cause
        ) {
        super(message);
    }

    /**
     * Constructs an {@code UnsupportedInstrumentPartException} with the specified detail message.
     *
     * @param message the message.
     */
    public
    UnsupportedInstrumentPartException(
        final String message
        ) {
        super(message);
    }
}