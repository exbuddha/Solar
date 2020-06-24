package instruments;

import static performance.Constant.Piano.*;

import java.util.Arrays;
import java.util.List;

import musical.Note;

/**
 * {@code Piano} classifies the most common form of the piano instrument.
 * <p>
 * This class is the superclass for all other derivatives of that instrument.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Piano
extends MusicalInstrument
implements Keyboard
{
    /** The default first key note. */
    private static final
    Note DefaultFirstKeyNote = Note.A0;

    /** The default number of keys. */
    private static final
    byte DefaultKeyCount = (byte) 88;

    /** The default pedal types. */
    private static final
    List<String> DefaultPedalTypes = Arrays.asList(
        SustainPedalName,
        SostenutoPedalName,
        SoftPedalName
        );

    /** The minimum lowest note. */
    private static final
    Note MinLowestNote = Note.A0;

    /** The minimum number of keys. */
    private static final
    byte MinKeyCount = (byte) 12;

    /** The maximum lowest note. */
    private static final
    Note MaxLowestNote = Note.C1;

    /** The maximum number of keys. */
    private static final
    byte MaxKeyCount = (byte) 88;

    /** The maximum number of pedals. */
    private static final
    byte MaxPedalCount = (byte) 4;

    /** The note of the first key. */
    protected final
    Note firstKeyNote;

    /** The total number of keys. */
    protected final
    short keyCount;

    /**
     * Creates a piano instrument with the specified first key note, number of keys, and pedal names, and optional string type; or throws an {@code IllegalArgumentException} if values are out of range.
     *
     * @param firstKeyNote the note of the first key.
     * @param keyCount the number of keys.
     * @param pedals the list of pedal names.
     * @param strings the strings class type.
     * @throws IllegalArgumentException if values are out of range.
     */
    protected
    Piano(
        final Note firstKeyNote,
        final Number keyCount,
        final List<String> pedals,
        final Class<? extends SimpleChordophone.String> strings
        ) {
        super();

        // Validate argument ranges
        if (firstKeyNote.compareTo(MinLowestNote) < 0 || firstKeyNote.compareTo(MaxLowestNote) > 0)
            throw new IllegalArgumentException("First key note is out of range.");

        if (keyCount.byteValue() < MinKeyCount || keyCount.shortValue() > MaxKeyCount)
            throw new IllegalArgumentException("Number of keys is out of range.");

        if (pedals.size() > MaxPedalCount)
            throw new IllegalArgumentException("Number of pedals is out of range.");

        this.firstKeyNote = firstKeyNote;
        this.keyCount = keyCount.shortValue();

        // Create the keys
        createPart(Keys.class)
        .withConstructor(Note.class, Number.class)
        .withValues(firstKeyNote, keyCount);

        // Create the pedals
        if (pedals != null && pedals.size() > 0)
            createPart(getDefaultConcreteSubclass(Pedals.class))
            .withConstructor(String[].class)
            .withValues(pedals.toArray());

        // Create the strings
        if (strings != null) {
            final PartCreator<? extends SimpleChordophone.String> stringCreator
            = createPart(getDefaultConcreteSubclass(strings))
            .withConstructor(Note.class)
            .withParameterRanges(this.keyCount);

            final short lowestNoteNumber = (short) firstKeyNote.getNumber();
            for (short semitone = (short) 0; semitone < this.keyCount; semitone++)
                stringCreator
                .withValues(Note.withNumber((short) (lowestNoteNumber + semitone)));
        }
    }

    /**
     * Creates a piano instrument with the specified first key note, number of keys, and pedal names, and no strings; or throws an {@code IllegalArgumentException} if values are out of range.
     *
     * @param firstKeyNote the note of the first key.
     * @param keyCount the number of keys.
     * @param pedals the list of pedal names.
     * @throws IllegalArgumentException if values are out of range.
     */
    protected
    Piano(
        final Note firstKeyNote,
        final Number keyCount,
        final List<String> pedals
        ) {
        this(firstKeyNote, keyCount, pedals, null);
    }

    /**
     * Creates a piano instrument with the specified lowest note, number of keys, and no pedals; or throws an {@code IllegalArgumentException} if values are out of range.
     *
     * @param firstKeyNote the note of the first key.
     * @param keyCount the number of keys.
     * @throws IllegalArgumentException if values are out of range.
     */
    public
    Piano(
        final Note firstKeyNote,
        final Number keyCount
        ) {
        this(firstKeyNote, keyCount, (List<String>) null);
    }

    /**
     * Creates a piano instrument with the default first key note (A0), number of keys (88), and pedals. (soft, sostenuto, sustain)
     */
    public
    Piano() {
        this(DefaultFirstKeyNote, DefaultKeyCount, DefaultPedalTypes);
    }

    @Override
    public void createKeyboard() {}

    /**
     * Returns the default first key note.
     *
     * @return the default first key note.
     */
    public static
    Note getDefaultFirstKeyNote() {
        return DefaultFirstKeyNote;
    }

    /**
     * Returns the default number of keys.
     *
     * @return the default number of keys.
     */
    public static
    short getDefaultNumberOfKeys() {
        return DefaultKeyCount;
    }

    /**
     * Returns the list of default pedal types.
     *
     * @return the list of default pedal types.
     */
    public static
    List<String> getDefaultPedalTypes() {
        return DefaultPedalTypes;
    }

    /**
     * Returns the maximum lowest note.
     *
     * @return the maximum lowest note.
     */
    public static
    Note getMaxLowestNote() {
        return MaxLowestNote;
    }

    /**
     * Returns the maximum number of keys.
     *
     * @return the maximum number of keys.
     */
    public static
    byte getMaxKeyCount() {
        return MaxKeyCount;
    }

    /**
     * Returns the maximum number of pedals.
     *
     * @return the maximum number of pedals.
     */
    public static
    byte getMaxPedalCount() {
        return MaxPedalCount;
    }

    /**
     * Returns the minimum lowest note.
     *
     * @return the minimum lowest note.
     */
    public static
    Note getMinLowestNote() {
        return MinLowestNote;
    }

    /**
     * Returns the minimum number of keys.
     *
     * @return the minimum number of keys.
     */
    public static
    byte getMinKeyCount() {
        return MinKeyCount;
    }

    /**
     * Returns the note of the first key.
     *
     * @return the note of the first key.
     */
    public
    Note getFirstKeyNote() {
        return firstKeyNote;
    }

    /**
     * Returns the total number of keys.
     *
     * @return the total number of keys.
     */
    public
    short getKeyCount() {
        return keyCount;
    }

    /**
     * {@code Key} represents the piano key.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Piano.class)
    protected abstract
    class Key
    extends MusicalInstrument.Key
    {
        /**
         * Creates the key with the specified tune.
         *
         * @param tune the tuning note.
         */
        @Categorized(parameters = "tune")
        protected
        Key(
            final Note tune
            ) {
            super(tune);
        }
    }

    /**
     * {@code Keys} represents all of the piano keys.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Piano.class)
    @Category
    @Category.Instance(type = Key.class, values = "tune -> firstKeyTune ** numOfKeys")
    protected abstract
    class Keys
    extends MusicalInstrument.Keys
    {
        /**
         * Creates all the keys.
         *
         * @param firstKeyTune the first key note.
         * @param numOfKeys the number of keys.
         */
        public
        Keys(
            final Note firstKeyTune,
            final Number numOfKeys
            ) {
            super();

            final PartCreator<Key> keyCreator
            = createPart(getDefaultConcreteSubclass(Key.class))
            .withConstructor(Note.class)
            .withParameterRanges(numOfKeys.intValue());

            final short lowestNoteNumber = (short) firstKeyTune.getNumber();
            for (int semitone = 0; semitone < numOfKeys.intValue(); semitone++)
                keyCreator
                .withValues(Note.withNumber((short) (lowestNoteNumber + semitone)));
        }
    }

    /**
     * {@code Pedal} represents the piano pedal of type: sustain, sostenuto, soft, or practice.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Piano.class)
    protected abstract
    class Pedal
    extends MusicalInstrument.Pedal
    {
        /**
         * Creates a piano pedal with the specified name and symbol.
         *
         * @param name the pedal name.
         * @param symbol the pedal symbol.
         */
        public
        Pedal(
            final String name,
            final String symbol
            ) {
            super(name, symbol);
        }

        /**
         * Creates a piano pedal with the specified name and null symbol.
         *
         * @param name the pedal name.
         */
        @Categorized(parameters = "name")
        public
        Pedal(
            final String name
            ) {
            this(name, name);
        }
    }

    /**
     * {@code Pedals} represents all of the piano pedals.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Piano.class)
    @Category
    @Category.Instance(type = Pedal.class, values = "name -> names")
    protected abstract
    class Pedals
    extends MusicalInstrument.Pedals
    {
        public
        Pedals(
            final String[] names
            ) {
            super();

            final PartCreator<Pedal> pedalCreator
            = createPart(Pedal.class)
            .withConstructor(String.class)
            .withParameterRanges(DefaultPedalTypes.size());

            for (final String pedal : names)
                if (!pedal.replaceAll("[ \t\n]+", "").isEmpty())
                    pedalCreator
                    .withValues(pedal);
        }
    }
}