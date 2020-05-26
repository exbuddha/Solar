package instruments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import musical.Note;

/**
 * {@code Violin} represents the most common form of the violin instrument.
 */
public abstract
class Violin
extends Lute
{
    /** The default number of semitones per string. */
    private static final
    byte DefaultSemitones = (byte) 10;

    /** The minimum number of semitones allowed. */
    private static final
    byte MinSemitones = (byte) 4;

    /** The maximum number of semitones allowed. */
    private static final
    byte MaxSemitones = (byte) 12;

    /** The maximum number of strings allowed. */
    private static final
    byte MaxStringCount = (byte) 6;

    /** Violin standard tuning: E2, A2, D3, G3. */
    private static final
    List<Note> StandardTuning;

    static
    {
        StandardTuning = new ArrayList<Note>();

        StandardTuning.add(Note.Table.G3);
        StandardTuning.add(Note.Table.D3);
        StandardTuning.add(Note.Table.A2);
        StandardTuning.add(Note.Table.E2);
    }

    /** The number of strings. */
    protected
    byte stringCount;

    /** The number of semitones per string. */
    protected
    byte semitones;

    /**
     * Creates a violin instrument with the specified tuning and number of semitones per string; or throws an {@code IllegalArgumentException} if values are out of range.
     *
     * @param tuning the tuning.
     * @param semitones the number of semitones.
     * @throws IllegalArgumentException if values are out of range.
     */
    protected
    Violin(
        final List<Note> tuning,
        final Number semitones
        ) {
        super();

        // Validate argument ranges
        if (tuning == null || tuning.size() == 0)
            throw new IllegalArgumentException("Tuning array size must be at least 1.");

        if (tuning.size() > MaxStringCount)
            throw new IllegalArgumentException("Tuning array size is out of range.");

        if (semitones.byteValue() < MinSemitones || semitones.byteValue() > MaxSemitones)
            throw new IllegalArgumentException("Number of semitones is out of range.");

        this.semitones = semitones.byteValue();

        // Create the strings
        final PartCreator<String> stringCreator
        = createPart(getDefaultConcreteSubclass(String.class))
        .withConstructor(Note.class)
        .withParameterRanges(49);  // 1 octave

        stringCount = 0;
        for (final Note note : tuning) {
            stringCreator
            .withValues(note);
            stringCount++;
        }
    }

    /**
     * Creates a violin instrument with the specified tuning and the default number of semitones (10); or throws an {@code IllegalArgumentException} if value is out of range.
     *
     * @param tuning the tuning.
     * @throws IllegalArgumentException if values are out of range.
     */
    protected
    Violin(
        final List<Note> tuning
        ) {
        this(tuning, DefaultSemitones);
    }

    /**
     * Creates a violin instrument with the specified tuning and the default number of semitones (10); or throws an {@code IllegalArgumentException} if value is out of range.
     * <p>
     * If tuning is empty the standard tuning is used.
     *
     * @param tuning the tuning.
     * @throws IllegalArgumentException if values are out of range.
     */
    public
    Violin(
        final Note... tuning
        ) {
        this(Arrays.asList(tuning));
    }

    /**
     * Creates a standard violin instrument.
     */
    public
    Violin() {
        this(StandardTuning);
    }

    public static
    byte getDefaultSemitones() {
        return DefaultSemitones;
    }

    public static
    byte getMinSemitones() {
        return MinSemitones;
    }

    public static
    byte getMaxSemitones() {
        return MaxSemitones;
    }

    public static
    byte getMaxStringCount() {
        return MaxStringCount;
    }

    /**
     * Returns the guitar standard tuning.
     *
     * @return the guitar standard tuning.
     */
    public static
    List<Note> getStandardTuning() {
        return StandardTuning;
    }

    /**
     * {@code Accessory} classifies a violin accessory.
     */
    protected abstract
    class Accessory
    extends Lute.Accessory
    {
        @Override
        public boolean is(final system.Type<Part> type) {
            return type instanceof Accessory;
        }
    }

    /**
     * {@code Bow} represents the violin bow.
     */
    protected abstract
    class Bow
    extends Lute.Bow
    {}

    public abstract
    class Fingerboard
    extends PartGroup
    {}

    public abstract
    class Holes
    extends PartGroup
    {}

    public abstract
    class Resonator
    extends AtomicPart
    {}

    public abstract
    class Peg
    extends PartGroup
    {}

    public abstract
    class Neck
    extends PartGroup
    {}

    /**
     * {@code String} represents the violin string.
     */
    @Uniformed(group = Violin.class)
    public abstract
    class String
    extends Lute.String
    {
        /**
         * Creates a violin string with the specified tuning.
         *
         * @param tuning the string tuning.
         */
        protected
        String(
            final Note tuning
            ) {
            super(tuning);

            // Create fingering points
            final PartCreator<Point> pointCreator
            = createPart(getDefaultConcreteSubclass(Point.class))
            .withConstructor(Number.class)
            .withParameterRanges(1);

            for (byte i = (byte) 0; i <= semitones; i++)
                pointCreator
                .withValues(i);
        }

        /**
         * {@code Point} represents the violin string point.
         */
        @Uniformed(group = String.class)
        public abstract
        class Point
        extends Lute.String.Point
        {
            /**
             * Creates a violin string point.
             *
             * @param distance the distance.
             */
            protected
            Point(
                final Number distance
                ) {
                super(distance);
            }
        }
    }

    public abstract
    class Soundboard
    extends AtomicPart
    {}
}