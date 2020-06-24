package instruments;

/**
 * {@code Chimes} classifies the most common form of the chimes instrument as a set of hanging bells suspended from an apex.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Chimes
extends Bell
{
    /** The next bell in order. */
    protected
    Chimes next;

    /** The bell number. */
    protected
    Number number;

    /**
     * Creates a chimes with a single bell with the specified number.
     *
     * @param number the bell number.
     */
    protected
    Chimes(
        final Number number
        ) {
        super();
        this.number = number;
    }

    /**
     * Returns the bell number.
     *
     * @return the bell number.
     */
    public
    Number getNumber() {
        return number;
    }
}