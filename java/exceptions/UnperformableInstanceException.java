package exceptions;

/**
 * Signals that an exception occurred in the ASM logic while converting music data into performance instructions.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class UnperformableInstanceException
extends PerformanceException
{
    /**
     * Constructs an {@code UnperformableInstanceException} with the specified detail message and cause.
     *
     * @param message the message.
     * @param cause the cause.
     */
    public
    UnperformableInstanceException(
        final String message,
        final Throwable cause
        ) {
        super(message, cause);
    }

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