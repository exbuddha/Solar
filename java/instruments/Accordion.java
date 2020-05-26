package instruments;

import musical.Note;
import musical.Note.Pitch;

/**
 * {@code Accordion} classifies the most common form of the accordion instrument.
 */
public abstract
class Accordion
extends FreeAerophone
{
    /** The default first key note. */
    private static final
    Note DefaultFirstKeyNote = new Note((byte) 2, Pitch.C);

    /** The default number of keys. */
    private static final
    byte DefaultKeyCount = (byte) 32;

    /** The minimum lowest note. */
    private static final
    Note MinLowestNote = new Note((byte) 0, Pitch.A);

    /** The minimum number of keys. */
    private static final
    byte MinKeyCount = (byte) 12;

    /** The maximum lowest note. */
    private static final
    Note MaxLowestNote = new Note((byte) 4, Pitch.A);

    /** The maximum number of keys. */
    private static final
    byte MaxKeyCount = (byte) 42;

    /**
     * Creates a accordion instrument with the specified first key note and number of keys; or throws an {@code IllegalArgumentException} if values are out of range.
     *
     * @param firstKeyNote the first key note.
     * @param keyCount the number of keys.
     * @throws IllegalArgumentException if values are out of range.
     */
    protected
    Accordion(
        final Note firstKeyNote,
        final Number keyCount
        ) {
        super();

        // Validate argument ranges
        if (firstKeyNote.compareTo(MinLowestNote) < 0 || firstKeyNote.compareTo(MaxLowestNote) > 0)
            throw new IllegalArgumentException("First key note is out of range.");

        if (keyCount.byteValue() < MinKeyCount || keyCount.shortValue() > MaxKeyCount)
            throw new IllegalArgumentException("Number of keys is out of range.");
    }

    public abstract
    class Accompaniment
    extends CompositePart
    {}

    public abstract
    class Bellows
    extends AtomicPart
    {}

    public abstract
    class Button
    extends AtomicPart
    {}

    public abstract
    class Buttons
    extends CompositePart
    {}

    public abstract
    class Key
    extends MusicalInstrument.Key
    {
        protected Key(Note tune) {
            super(tune);
        }
    }

    public abstract
    class Keys
    extends MusicalInstrument.Keys
    {}

    public abstract
    class Manual
    extends CompositePart
    {}
}