package instruments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import musical.Note;
import system.data.Lambda;

/**
 * {@code Guitar} classifies the most common form of the guitar instrument.
 * <p>
 * This class is the superclass for all other derivatives of this instrument.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Guitar
extends Lute
{
    /** The default number of frets. */
    private static final
    byte DefaultFretCount = (byte) 22;

    /** The default number of harmonic node groups. */
    private static final
    byte DefaultHarmonicCount = (byte) 7;

    /** The minimum number of frets allowed. */
    private static final
    byte MinFretCount = (byte) 0;

    /** The maximum number of frets allowed. */
    private static final
    byte MaxFretCount = (byte) 36;

    /** The minimum number of harmonic node groups allowed. */
    private static final
    byte MinHarmonicCount = (byte) 2;

    /** The maximum number of harmonic node groups allowed. */
    private static final
    byte MaxHarmonicCount = (byte) 12;

    /** The maximum number of strings allowed. */
    private static final
    byte MaxStringCount = (byte) 44;

    /** Guitar standard tuning: E2, A2, D3, G3, B3, E4. */
    protected static final
    List<Note> StandardTuning;

    static
    {
        StandardTuning = new ArrayList<Note>();
        StandardTuning.add(Note.E4);
        StandardTuning.add(Note.B3);
        StandardTuning.add(Note.G3);
        StandardTuning.add(Note.D3);
        StandardTuning.add(Note.A2);
        StandardTuning.add(Note.E2);
    }

    /** The number of strings. */
    protected
    byte stringCount;

    /** The number of frets. */
    protected
    byte fretCount;

    /** The number of harmonic node groups. */
    protected
    byte harmonicCount;

    /**
     * Creates a guitar instrument with the specified tuning, fret count, and harmonic node groups; or throws an {@code IllegalArgumentException} if values are out of range.
     * <p>
     * The size of the {@code tuning} list sets the number of strings for the guitar and must be at least 1.
     * All strings have the same number of frets ({@code fretCount}) and this number cannot be negative.
     * The minimum number of harmonic node groups ({@code harmonicCount}) also must be larger than 1.
     *
     * @param tuning the guitar tuning.
     * @param fretCount the number of frets.
     * @param harmonicCount the maximum harmonic number of audible node groups on the string.
     *
     * @throws IllegalArgumentException if values are out of range.
     */
    protected
    Guitar(
        final List<Note> tuning,
        final Number fretCount,
        final Number harmonicCount
        ) {
        super();

        // Validate argument ranges
        if (tuning == null || tuning.size() == 0)
            throw new IllegalArgumentException("Tuning array size must be at least 1.");

        if (tuning.size() > MaxStringCount)
            throw new IllegalArgumentException("Tuning array size is out of range.");

        if (fretCount.byteValue() < MinFretCount || fretCount.byteValue() > MaxFretCount)
            throw new IllegalArgumentException("Number of frets is out of range.");

        if (harmonicCount.byteValue() < MinHarmonicCount || harmonicCount.byteValue() > MaxHarmonicCount)
            throw new IllegalArgumentException("Number of harmonic node groups is out of range.");

        this.fretCount = fretCount.byteValue();
        this.harmonicCount = harmonicCount.byteValue();

        // Create the strings
        final PartCreator<String> stringCreator
        = createPart(getDefaultConcreteSubclass(String.class))
        .withConstructor(Note.class)
        .withParameterRanges(49);  // 4 octaves

        stringCount = 0;
        for (final Note note : tuning) {
            stringCreator
            .withValues(note);
            stringCount++;
        }
    }

    /**
     * Creates a guitar instrument with the specified tuning, fret count, and the default number of harmonic groups (12); or throws an {@code IllegalArgumentException} if values are out of range.
     * <p>
     * If tuning is null or empty the standard tuning is used.
     *
     * @param tuning the tuning.
     * @param fretCount the number of frets.
     *
     * @throws IllegalArgumentException if values are out of range.
     */
    protected
    Guitar(
        final List<Note> tuning,
        final Number fretCount
        ) {
        this(tuning == null || tuning.size() == 0 ? StandardTuning : tuning, fretCount, DefaultHarmonicCount);
    }

    /**
     * Creates a guitar instrument with the specified tuning, fret count, and the default number of harmonic groups (12); or throws an {@code IllegalArgumentException} if values are out of range.
     * <p>
     * If tuning is null or empty the standard tuning is used.
     *
     * @param tuning the tuning.
     * @param fretCount the number of frets.
     *
     * @throws IllegalArgumentException if values are out of range.
     */
    protected
    Guitar(
        final Note[] tuning,
        final Number fretCount
        ) {
        this(tuning == null || tuning.length == 0 ? StandardTuning : Arrays.asList(tuning), fretCount, DefaultHarmonicCount);
    }

    /**
     * Creates a guitar instrument with the specified tuning and the default number of frets. (22)
     *
     * @param tuning the tuning.
     */
    public
    Guitar(
        final Note... tuning
        ) {
        this(Arrays.asList(tuning), DefaultFretCount, DefaultHarmonicCount);
    }

    /**
     * Creates a standard guitar instrument with standard tuning (E2, A2, D3, G3, B3, E4), default number of frets (22), and default harmonic node groups. (7)
     */
    public
    Guitar() {
        this(StandardTuning, DefaultFretCount, DefaultHarmonicCount);
    }

    /**
     * Returns the default number of frets.
     *
     * @return the default number of frets.
     */
    public static
    byte getDefaultFretCount() {
        return DefaultFretCount;
    }

    /**
     * Returns the default number of harmonic node groups.
     *
     * @return the default number of harmonic node groups.
     */
    public static
    byte getDefaultHarmonicCount() {
        return DefaultHarmonicCount;
    }

    /**
     * Returns the maximum number of frets allowed.
     *
     * @return the maximum number of frets allowed.
     */
    public static
    byte getMaxFretCount() {
        return MaxFretCount;
    }

    /**
     * Returns the maximum number of harmonic node groups allowed.
     *
     * @return the maximum number of harmonic node groups allowed.
     */
    public static
    byte getMaxHarmonicCount() {
        return MaxHarmonicCount;
    }

    /**
     * Returns the minimum number of frets allowed.
     *
     * @return the minimum number of frets allowed.
     */
    public static
    byte getMinFretCount() {
        return MinFretCount;
    }

    /**
     * Returns the minimum number of harmonic node groups allowed.
     *
     * @return the minimum number of harmonic node groups allowed.
     */
    public static
    byte getMinHarmonicCount() {
        return MinHarmonicCount;
    }

    /**
     * Returns the guitar standard tuning.
     *
     * @return the guitar standard tuning.
     */
    public static
    Note[] getStandardTuning() {
        return Lambda.toArray(StandardTuning);
    }

    /**
     * Returns the number of frets.
     *
     * @return the number of frets.
     */
    public
    byte getFretCount() {
        return fretCount;
    }

    /**
     * Returns the number of harmonic node groups.
     *
     * @return the number of harmonic node groups.
     */
    public
    byte getHarmonicCount() {
        return harmonicCount;
    }

    /**
     * {@code Accessory} represents a guitar accessory.
     * <p>
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
        public boolean is(final system.data.Type<? extends Part> type) {
            return type instanceof Accessory;
        }
    }

    /**
     * {@code Capo} represents the guitar capo.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class Capo
    extends Accessory
    implements instruments.accessory.Capo
    {}

    /**
     * {@code Direction} represents all relative directions regarding the guitar instrument.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    enum Direction
    implements MusicalInstrument.Direction
    {
        /** Upward, perpendicular to the strings direction, parallel to the strings plane. */
        Up,

        /** Downward, perpendicular to the strings direction, parallel to the strings plane. */
        Down,

        /** Upward and/or downward. */
        Vertical,

        /** Toward the nut, parallel to the strings plane. */
        Left,

        /** Toward the bridge, parallel to the strings plane. */
        Right,

        /** Left and/or right. */
        Horizontal,

        /** Toward the performer's body, perpendicular to the strings plane. */
        In,

        /** Away from the performer's body, perpendicular to the strings plane. */
        Out,

        /** In and/or out. */
        Perpendicular;

        @Override
        public boolean is(system.data.Type<? extends MusicalInstrument.Direction> type) {
            // Guitar directions are not associated with a different direction type
            return false;
        }
    }

    /**
     * {@code Fretboard} represents the guitar fretboard.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Fretboard
    extends PartGroup
    {}

    /**
     * {@code Peg} represents the guitar peg.
     * <p>
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
     * {@code Pick} represents the guitar pick.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class Pick
    extends Accessory
    implements instruments.accessory.Plectrum
    {}

    /**
     * {@code Neck} represents the guitar neck.
     * <p>
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
     * {@code Slide} represents the guitar slide.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class Slide
    extends Accessory
    implements instruments.accessory.Slide
    {}

    /**
     * {@code String} represents the guitar string.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Guitar.class)
    protected abstract
    class String
    extends Lute.String
    {
        /**
         * Creates a guitar string with the specified tuning.
         *
         * @param tuning the string tuning.
         */
        protected
        String(
            final Note tuning
            ) {
            super(tuning);

            // Create fret points
            final PartCreator<Fret> fretCreator
            = createPart(getDefaultConcreteSubclass(Fret.class))
            .withConstructor(Number.class)
            .withParameterRanges(fretCount + 1);  // the max number of frets

            for (byte i = (byte) 0; i <= fretCount; i++)
                fretCreator
                .withValues(i);
        }

        /**
         * Creates all harmonic nodes on the string given the specified root (pressed) fret.
         *
         * @param rootFret the root fret.
         */
        public
        void createNodes(
            final Fret rootFret
            ) {
            final PartCreator<Node> nodeCreator
            = createPart(getDefaultConcreteSubclass(Node.class))
            .withConstructor(Fret.class, Number.class, Number.class)
            .withParameterRanges(fretCount + 1, (fretCount + 1) * (int) Lambda.factorial(harmonicCount - 1));

            for (byte harmonic = (byte) 2; harmonic <= harmonicCount; harmonic++)
                for (byte nodeNumber = (byte) 1; nodeNumber <= harmonic - 1; nodeNumber++)
                    if (Lambda.gcd(harmonic, nodeNumber) == 1)
                        nodeCreator
                        .withValues(rootFret, nodeNumber, harmonic);
        }

        /**
         * {@code Fret} represents a fret point on the guitar string.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Uniformed(group = String.class)
        protected abstract
        class Fret
        extends Point
        {
            /** The fret number. */
            protected final
            byte number;

            /**
             * Creates a guitar fret point with the specified number.
             *
             * @param number the fret number.
             */
            protected
            Fret(
                final Number number
                ) {
                // The distance calculation is based on the formula mentioned in Wikipedia
                // The distance of fret n from the bridge can be measure by the distance of the 12th fret times the ratio Math.pow(1 / r, n - 12) where r = Math.pow(2, 1 / 12)
                // Therefore, considering the fact that the 12th fret is located exactly in the middle of the scale, its distance from the bridge can be considered to be equal to 0.5 given the scale length of 1
                // From that assumption, it can be derived that the distance of fret n from the nut is equal to 1 - R(n) / 2 where R(n) = Math.pow(1 / Math.pow(2, 1 / 12), n - 12)
                super(number.byteValue() == 0
                      ? (byte) 0
                      : 1 - Math.pow(1 / Math.pow(2, 1D / 12), number.doubleValue() - 12) / 2);

                this.number = number.byteValue();
            }

            @Override
            public Note getTune() {
                return Note.withNumber((short) (String.this.tuning.getNumber() + number));
            }

            @Override
            public float getFretNumber() {
                return number;
            }

            @Override
            public Float getFrequency() {
                return (float) (Math.pow(2D, (String.this.tuning.getNumber() + number - 69) / 12) * 440);
            }
        }

        /**
         * {@code Node} represents a harmonic node point on the guitar string.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Uniformed(group = String.class)
        protected abstract
        class Node
        extends Point
        {
            /** The root fret number. */
            protected final
            Fret rootFret;

            /** The harmonic node number. */
            protected final
            byte number;

            /** The harmonic group number. */
            protected final
            byte harmonic;

            /**
             * Creates a harmonic node given the specified pressed fret that is considered to be the root point for all harmonic nodes on the string.
             * For natural harmonic nodes, the root fret is the zero-th fret (the guitar nut); for artificial harmonic nodes, a positive fret is used.
             *
             * @param rootFret the root fret.
             * @param number the harmonic node number. (numerator)
             * @param harmonic the harmonic group number. (denominator)
             */
            protected
            Node(
                final Fret rootFret,
                final Number number,
                final Number harmonic
                ) {
                super(rootFret.getDistance() + (1 - rootFret.getDistance()) * number.doubleValue() / harmonic.doubleValue());
                this.rootFret = rootFret;
                this.number = number.byteValue();
                this.harmonic = harmonic.byteValue();
            }

            @Override
            public Note getTune() {
                final int cents = getIntervalCents();
                final int rounded = Math.round(cents / 100F);
                final Note note = Note.withNumber((short) (rootFret.getTune().getNumber() + rounded));
                note.setAdjustment(cents - rounded * 100);

                return note;
            }

            @Override
            public float getFretNumber() {
                return (float) (rootFret.getFretNumber() + Math.round(Math.log(1D * harmonic / (harmonic - number)) / Math.log(Math.pow(2, 1D / 12)) * 10) / 10);
            }

            @Override
            protected Float getFrequency() {
                return (float) (Math.pow(2D, ((rootFret.getTune().getNumber() + getIntervalCents() / 100) - 69) / 12) * 440);
            }

            /**
             * Returns the resulting interval (in cents) relative to the root fret when the node is touched.
             *
             * @return the interval from the root fret. (in cents)
             */
            protected
            int getIntervalCents() {
                return (int) Math.round(Math.log(harmonic) / Math.log(Math.pow(2, 1D / 1200)));
            }

            /**
             * Returns the root fret.
             *
             * @return the root fret.
             */
            public
            Fret getRootFret() {
                return rootFret;
            }

            /**
             * Returns the node number.
             *
             * @return the node number.
             */
            public
            int getNumber() {
                return number;
            }

            /**
             * Returns the harmonic group number.
             *
             * @return the harmonic group number.
             */
            public
            int getHarmonic() {
                return harmonic;
            }
        }

        /**
         * {@code Point} classifies interaction points on the guitar string.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Uniformed(group = String.class)
        protected abstract
        class Point
        extends Lute.String.Point
        {
            /**
             * Creates a point on the string at the specified relative distance from the guitar nut.
             *
             * @param distance the relative distance from the nut. (0 being the nut point and 1 being the bridge point)
             */
            protected
            Point(
                final Number distance
                ) {
                super(distance);
            }

            /**
             * Returns the fret number of the point on the fretboard.
             * <p>
             * This number is declared as {@code double} so it can include fractions for points between two frets.
             *
             * @return the fret number.
             */
            protected abstract
            float getFretNumber();
        }
    }

    /**
     * {@code WhammyBar} represents the guitar whammy bar.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class WhammyBar
    extends Accessory
    implements instruments.accessory.PitchBend
    {}
}