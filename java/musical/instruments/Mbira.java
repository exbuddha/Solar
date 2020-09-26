package musical.instruments;

import musical.Note;

/**
 * {@code Mbira} classifies the most common form of the mbira instrument.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Mbira
extends PluckedIdiophone
{
    /**
     * Creates a standard mbira instrument.
     */
    protected
    Mbira() {
        super();
    }

    /** The default number of keys. */
    private static final
    byte DefaultKeyCount = (byte) 23;

    /** The minimum number of keys. */
    private static final
    byte MinKeyCount = (byte) 12;

    /** The minimum lowest note. */
    private static final
    Note MinLowestNote = Note.A2;

    /** The maximum number of keys. */
    private static final
    byte MaxKeyCount = (byte) 36;

    /** The maximum lowest note. */
    private static final
    Note MaxLowestNote = Note.C3;

    /**
     * {@code Hole} represents the mbira hole.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Hole
    extends AtomicPart
    {}

    /**
     * {@code Resonator} represents the mbira resonator.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Resonator
    extends AtomicPart
    {}

    /**
     * {@code Soundboard} represents the mbira soundboard.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Soundboard
    extends AtomicPart
    {}

    /**
     * {@code Tine} represents the mbira tine.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Tine
    extends AtomicPart
    {}

    /**
     * {@code Tines} represents all of the mbira tines.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Tines
    extends CompositePart
    {}
}