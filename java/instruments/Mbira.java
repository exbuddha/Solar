package instruments;

import musical.Note;

/**
 * {@code Mbira} classifies the most common form of the mbira instrument.
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
    Note MinLowestNote = Note.Table.A2;

    /** The maximum number of keys. */
    private static final
    byte MaxKeyCount = (byte) 36;

    /** The maximum lowest note. */
    private static final
    Note MaxLowestNote = Note.Table.C3;

    public abstract
    class Hole
    extends AtomicPart
    {}

    public abstract
    class Resonator
    extends AtomicPart
    {}

    public abstract
    class Soundboard
    extends AtomicPart
    {}

    public abstract
    class Tine
    extends AtomicPart
    {}

    public abstract
    class Tines
    extends AtomicPart /* ? */
    {}
}