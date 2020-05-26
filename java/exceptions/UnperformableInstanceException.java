package exceptions;

/**
 * Signals that an exception occurred in the ASM logic while converting music data into performance instructions.
 */
public
class UnperformableInstanceException
extends PerformanceException
{
    /**
     * Constructs an {@code UnperformableInstanceException} with the specified detail message.
     *
     * @param message the message.
     */
    public
    UnperformableInstanceException(
        final String message
        ) {
        super(message);
    }
}