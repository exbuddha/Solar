package instruments;

import java.util.ArrayList;
import java.util.List;

import musical.Note;
import musical.Note.Pitch;

/**
 * {@code Cello} classifies the most common form of the cello instrument.
 */
public abstract
class Cello
extends Violin
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

    /** Cello standard tuning: E2, A2, D3, G3. */
    private static final
    List<Note> StandardTuning;

    static
    {
        StandardTuning = new ArrayList<Note>();

        StandardTuning.add(new Note((byte) 3, Pitch.G));
        StandardTuning.add(new Note((byte) 3, Pitch.D));
        StandardTuning.add(new Note((byte) 2, Pitch.A));
        StandardTuning.add(new Note((byte) 2, Pitch.E));
    }

    /**
     * Creates a cello instrument with the specified tuning and number of semitones per string; or throws an {@code IllegalArgumentException} if values are out of range.
     *
     * @param tuning the tuning.
     * @param semitones the number of semitones.
     * @throws IllegalArgumentException if values are out of range.
     */
    protected
    Cello(
        final List<Note> tuning,
        final Number semitones
        ) {
        super();
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
     * {@code Bow} represents the violin bow.
     */
    protected abstract
    class Bow
    extends Violin.Bow
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
     * {@code String} represents the cello string.
     */
    @Uniformed(group = Cello.class)
    public abstract
    class String
    extends Violin.String
    {
        /**
         * Creates a cello string with the specified tuning.
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
         * {@code Point} represents the cello string point.
         */
        @Uniformed(group = String.class)
        public abstract
        class Point
        extends Violin.String.Point
        {
            /**
             * Creates a cello string point.
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