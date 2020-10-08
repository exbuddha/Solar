package musical.instruments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import musical.Note;
import system.Type;

/**
 * {@code Violin} represents the most common form of the violin instrument.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
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
        StandardTuning.add(Note.G3);
        StandardTuning.add(Note.D3);
        StandardTuning.add(Note.A2);
        StandardTuning.add(Note.E2);
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
        = createPart(String.class)
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
     * <p/>
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
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class Accessory
    extends Lute.Accessory
    {
        @Override
        public boolean is(final Type<? super Part> type) {
            return type instanceof Accessory;
        }
    }

    /**
     * {@code Bow} represents the violin bow.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class Bow
    extends Lute.Bow
    {}

    /**
     * {@code Fingerboard} represents the violin fingerboard.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Fingerboard
    extends PartGroup
    {}

    /**
     * {@code Holes} represents the violin f-shaped holes.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Holes
    extends PartGroup
    {}

    /**
     * {@code Resonator} represents the violin resonator.
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
     * {@code Peg} represents the violin peg.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Peg
    extends PartGroup
    {}

    /**
     * {@code Neck} represents the violin neck.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Neck
    extends PartGroup
    {}

    /**
     * {@code String} represents the violin string.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
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
            = createPart(Point.class)
            .withConstructor(Number.class)
            .withParameterRanges(1);

            for (byte i = (byte) 0; i <= semitones; i++)
                pointCreator
                .withValues(i);
        }

        /**
         * {@code Point} represents the violin string point.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
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

    /**
     * {@code Soundboard} represents the violin soundboard.
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
}