package musical.instruments;

import musical.Note;
import musical.Note.Pitch;

/**
 * {@code Accordion} represents the most common form of the accordion instrument.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
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

    /**
     * {@code Accompaniment} represents the accordion accompaniment.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Accompaniment
    extends CompositePart
    {}

    /**
     * {@code Bellows} represents the accordion bellows.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Bellows
    extends AtomicPart
    {}

    /**
     * {@code Button} represents the accordion button.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Button
    extends AtomicPart
    {}

    /**
     * {@code Buttons} represents all of the accordion buttons.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Buttons
    extends CompositePart
    {}

    /**
     * {@code Key} represents the accordion key.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Key
    extends MusicalInstrument.Key
    {
        protected Key(Note tune) {
            super(tune);
        }
    }

    /**
     * {@code Keys} represents all of the accordion keys.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Keys
    extends MusicalInstrument.Keys
    {}

    /**
     * {@code Manual} represents the accordion manual.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Manual
    extends CompositePart
    {}
}