package musical;

import static musical.Note.Accidental.Flat;
import static musical.Note.Accidental.Natural;
import static musical.Note.Accidental.Sharp;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import music.system.data.Clockable;
import music.system.data.Ordered;

/**
 * {@code Note} represents the musical note of a certain octave and pitch.
 * <p>
 * <ul>
 * <li>Notes must have a pitch.
 * <li>A note that has a null octave is considered to be a pitch type, for which some functionality will fail.
 * <li>Notes can also have accidentals and adjustments.
 * <li>Pitch has a separate identity from accidental in this class.
 * The A-sharp note defines 'A' for pitch and 'sharp' for accidental.
 * <li>Double-sharp and double-flat accidentals are not supported.
 * This information is maintained by the subclass {@code Scale.Accidental}.
 * Note objects are meant to only represent the tune of instrument sounds or notes in a score.
 * <li>Note adjustment is accounted for in all operations and comparisons.
 * </ul>
 */
public
class Note
implements
    Adjustable,
    Clockable<Note>,
    Cloneable,
    Comparable<Note>,
    Localizable,
    Ordered<Float>,
    Readjusting<Note>,
    Symbolized<String>,
    music.system.Type<Note>
{
    /** The note symbol. */
    protected
    String symbol;

    /** The note octave. */
    protected
    Byte octave;

    /** The note pitch. */
    protected
    Pitch pitch;

    /** The note accidental. */
    protected
    Accidental accidental;

    /** The number of cents added to, or subtracted from, the note to slightly alter its pitch. */
    protected
    short adjustment;

    /**
     * Creates a note with the specified octave, pitch, accidental, and adjustment (in cents), and adjusts the note.
     * <p>
     * Adjustment is the process in which {@code adjustment} values outside of the accepted range [-100, 100] are corrected and the remainder cents are added to, or subtracted from, the note pitch and octave.
     * Notes with uncommon pitch-and-accidental combinations, such as B#, are converted to their common form in the same process.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave; and converts null adjustment to 0, otherwise calls {@link Number#shortValue()} on it.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     * @param adjustment the adjustment. (in cents)
     */
    public
    Note(
        final Number octave,
        final Pitch pitch,
        final Accidental accidental,
        final Number adjustment
        ) {
        if (octave != null)
            this.octave = octave.byteValue();
        this.pitch = pitch;
        this.accidental = accidental;
        this.adjustment = adjustment == null
                          ? 0
                          : adjustment.shortValue();
        adjust();
    }

    public
    Note(
        final Octave octave,
        final Pitch pitch,
        final Accidental accidental,
        final Number adjustment
        ) {
        if (octave != null)
            this.octave = octave.getOrder().byteValue();
        this.pitch = pitch;
        this.accidental = accidental;
        this.adjustment = adjustment == null
                          ? 0
                          : adjustment.shortValue();
        adjust();
    }

    /**
     * Creates a note with the specified octave, pitch, and accidental, and adjusts the note.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     */
    public
    Note(
        final Number octave,
        final Pitch pitch,
        final Accidental accidental
        ) {
        this(octave, pitch, accidental, 0);
    }

    public
    Note(
        final Octave octave,
        final Pitch pitch,
        final Accidental accidental
        ) {
        this(octave, pitch, accidental, 0);
    }

    /**
     * Creates a natural note with the specified octave and pitch.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     */
    public
    Note(
        final Number octave,
        final Pitch pitch
        ) {
        this(octave, pitch, Natural);
    }

    public
    Note(
        final Octave octave,
        final Pitch pitch
        ) {
        this(octave, pitch, Natural);
    }

    /**
     * Creates a pitch type with the specified accidental and adjusts the note.
     *
     * @param pitch the pitch.
     * @param accidental the accidental.
     */
    public
    Note(
        final Pitch pitch,
        final Accidental accidental
        ) {
        this((Byte) null, pitch, accidental);
    }

    /**
     * Creates a natural pitch type.
     *
     * @param pitch the pitch.
     */
    public
    Note(
        final Pitch pitch
        ) {
        this((Byte) null, pitch);
    }

    /**
     * Creates a null note.
     */
    public
    Note() {
        this(null);
    }

    public static final
    short number(byte octave, Pitch pitch, Accidental accidental) {
        return (short) ((octave + 1) * 12 + (pitch.order + accidental.getOrder()) % 12);
    }

    public static final
    short number(byte octave, Pitch pitch) {
        return (short) ((octave + 1) * 12 + pitch.order);
    }

    public static final
    Note tune(Number octave, Pitch pitch, Accidental accidental) {
        if (octave == null)
            return tune(4, pitch, accidental);

        return tune(number(octave.byteValue(), pitch, accidental));
    }

    public static final
    Note tune(Number octave, Pitch pitch) {
        return tune(octave, pitch, Accidental.Natural);
    }

    public static final
    Note tune(Pitch pitch) {
        return tune(4, pitch);
    }

    public static final
    Note tune(Note note) {
        if (note instanceof Table)
            return note;

        return tune(note.octave, note.pitch, note.accidental);
    }

    public static final
    Note tune(
        final Number number
        ) {
        return Table.Order[number.intValue()];
    }

    public static final
    Note tune(
        final CharSequence symbol
        ) {
        if (symbol == null)
            return tune();

        final int length
        = Accidental.isValidNaturalSymbol(symbol.charAt(symbol.length() - 1))
        ? symbol.length() - 1
        : symbol.length();

        if (length > 0)
            if (Pitch.isValid(symbol.charAt(0))) {
               if (length == 1)
                    return tune(Pitch.valueOf(symbol.toString()));

                switch (symbol.charAt(length - 1)) {
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                    case '0':
                        return tune(Byte.parseByte(symbol.subSequence(1, length - 1).toString()), Pitch.valueOf(symbol.charAt(0)));

                    default:
                        if (Accidental.isValidSingleSymbol(symbol.charAt(length - 1))) {
                            final char second = symbol.charAt(length - 2);
                            return tune(Byte.parseByte(symbol.subSequence(1, length - 2).toString()), Pitch.valueOf(symbol.charAt(0)), Accidental.withSymbol(symbol.subSequence(length - (Accidental.isValidDoubleSymbol(second) ? 3 : 2), length - 1)));
                        }
                        return null;
                }
            }
            else
                return null;

        return tune();
    }

    public static final
    Note tune() {
        return Table.A4;
    }

    /**
     * Creates and returns a new note for the specified MIDI note number, or null if number is less than 0.
     * <p>
     * This implementation creates sharp notes.
     *
     * @param number the note MIDI number.
     * @return the note, or null is number is less than 0.
     */
    public static
    Note withNumber(
        final short number
        ) {
        if (number < 0)
            return null;

        final short octave = (short) (number / 12 - 1);
        switch (number - (octave + 1) * 12) {
            case 1:
            case 3:
            case 6:
            case 8:
            case 10:
                return new Note(octave, Pitch.withOrder((byte) number), Sharp);

            default:
                return new Note(octave, Pitch.withOrder((byte) number));
        }
    }

    /**
     * Adds the specified amount of cents to the note.
     * <p>
     * This implementation performs unsafe casts from float and int to short.
     *
     * @param cents the cents.
     */
    public
    void add(
        short cents
        ) {
        // Add the octave amount and continue with the remainder
        int amount = cents / 1200;
        if (amount != 0) {
            octave = (byte) (octave + amount);
            cents %= 1200;
        }

        // Add the semitones amount and continue with the remainder
        amount = cents / 100;
        if (amount != 0) {
            // Correct pitch
            final short order = getOrder().shortValue();
            final short newOrder = (short) (order + amount);
            pitch = Pitch.withOrder((byte) ((newOrder + ((1 - (newOrder / 12)) * 12)) % 12));

            // Correct accidental making sure that the semitones remainder is within range [-1, 1]
            accidental = Accidental.withSemitone((byte) ((amount - (pitch.order - order) + 12) % 12));

            // Correct octave if the result note has crossed over the octave line
            if (amount > 0) {
                if (pitch.order + accidental.semitones < order)
                    octave = (byte) (octave + 1);
            }
            else
                if (pitch.order + accidental.semitones > order)
                    octave = (byte) (octave - 1);

            cents %= 100;
        }

        adjustment = cents;
        adjust();
    }

    /**
     * Adds the specified interval to the note.
     * <p>
     * This implementation performs an unsafe cast from int to short.
     *
     * @param interval the interval.
     */
    public
    void add(
        final Interval interval
        ) {
        add((short) interval.getCents());
    }

    /**
     * Returns true if the specified note has the same pitch, accidental, and adjustment as this note, and false otherwise.
     *
     * @param note the note.
     * @return true if notes are equal ignoring octaves, and false otherwise.
     */
    public
    boolean equalsIgnoreOctave(
        final Note note
        ) {
        return pitch == note.pitch &&
               accidental == note.accidental &&
               adjustment == note.adjustment;
    }

    /**
     * Returns true if the specified note has the same frequency as this note, and false otherwise.
     * <p>
     * This implementation calls {@link #compareTo(Note)} internally.
     *
     * @param note the note.
     * @return true if the notes are equal ignoring pitch variations, and false otherwise.
     */
    public
    boolean equalsIgnorePitch(
        final Note note
        ) {
        return compareTo(note) == 0;
    }

    /**
     * Returns true if the specified note has the same relative note number in its own octave, and adjustment as this note, and false otherwise.
     *
     * @param note the note.
     * @return true if the notes are equal ignoring pitch and octave variations, and false otherwise.
     */
    public
    boolean equalsIgnorePitchAndOctave(
        final Note note
        ) {
        return (getNumber() - note.getNumber()) % 12 + adjustment - note.adjustment == 0;
    }

    /**
     * Returns a string representation of the note adjustment.
     *
     * @return a string representation of the note adjustment.
     */
    protected
    String getAdjustmentString() {
        return (adjustment > 0 ? "+" : "") + adjustment;
    }

    /**
     * Returns the distance from the specified note in cents, or throws a {@code NullPointerException} if pitch or accidental is null.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     * @param adjustment the adjustment.
     * @return the distance in cents.
     * @throws NullPointerException if pitch or accidental is null.
     */
    public
    float getDistance(
        final Byte octave,
        final Pitch pitch,
        final Accidental accidental,
        final short adjustment
        ) {
        return (octave == null ||
                this.octave == null
                ? 0
                : (octave - this.octave) * 1200) +
                (pitch.order - this.pitch.order) * 100 +
                accidental.cents - this.accidental.cents +
                adjustment - this.adjustment;
    }

    /**
     * Returns the distance from the specified natural note in cents, or throws a {@code NullPointerException} if pitch or accidental is null.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param adjustment the adjustment.
     * @return the distance in cents.
     * @throws NullPointerException if pitch or accidental is null.
     */
    public
    float getDistance(
        final Byte octave,
        final Pitch pitch,
        final short adjustment
        ) {
        return getDistance(octave, pitch, Accidental.Natural, adjustment);
    }

    /**
     * Returns the distance from the specified natural note in cents, or throws a {@code NullPointerException} if pitch or accidental is null.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @return the distance in cents.
     * @throws NullPointerException if pitch or accidental is null.
     */
    public
    float getDistance(
        final Byte octave,
        final Pitch pitch
        ) {
        return getDistance(octave, pitch, Accidental.Natural, (short) 0);
    }

    /**
     * Returns the distance from the specified note type in cents, or throws a {@code NullPointerException} if pitch or accidental is null.
     * <p>
     * If this note has a defined octave, the same octave will be used; otherwise null is used.
     *
     * @param pitch the pitch.
     * @param accidental the accidental.
     * @param adjustment the adjustment.
     * @return the distance in cents.
     * @throws NullPointerException if pitch or accidental is null.
     */
    public
    float getDistance(
        final Pitch pitch,
        final Accidental accidental,
        final short adjustment
        ) {
        return getDistance(octave == null ? null : octave, pitch, accidental, adjustment);
    }

    /**
     * Returns the distance from the specified natural note type in cents, or throws a {@code NullPointerException} if pitch or accidental is null.
     * <p>
     * If this note has a defined octave, the same octave will be used; otherwise null is used.
     *
     * @param pitch the pitch.
     * @param adjustment the adjustment.
     * @return the distance in cents.
     * @throws NullPointerException if pitch or accidental is null.
     */
    public
    float getDistance(
        final Pitch pitch,
        final short adjustment
        ) {
        return getDistance(octave == null ? null : octave, pitch, Accidental.Natural, adjustment);
    }

    /**
     * Returns the distance from the specified natural note type in cents, or throws a {@code NullPointerException} if pitch or accidental is null.
     * <p>
     * If this note has a defined octave, the same octave will be used; otherwise null is used.
     *
     * @param pitch the pitch.
     * @return the distance in cents.
     * @throws NullPointerException if pitch or accidental is null.
     */
    public
    float getDistance(
        final Pitch pitch
        ) {
        return getDistance(octave == null ? null : octave, pitch, Accidental.Natural, (short) 0);
    }

    /**
     * Returns the distance from the specified note in cents.
     * <p>
     * If the {@code octave} of one of the notes is null, octaves will not be accounted for in the calculation.
     * This implementation includes the {@code adjustment} value in the calculation.
     *
     * @param note the note.
     * @return the distance in cents.
     */
    public
    float getDistance(
        final Note note
        ) {
        return getDistance(note.octave, note.pitch, note.accidental, note.adjustment);
    }

    /**
     * Returns the frequency of the note.
     * <p>
     * This implementation includes the {@code adjustment} value in the calculation.
     *
     * @return the frequency.
     */
    public
    float getFrequency() {
        return (float) (Math.pow(2F, (getNumber() - 69) / 12) * 440);
    }

    /**
     * Returns the note number based on the MIDI system.
     * <p>
     * By convention, the MIDI note number is between 0 to 127 covering the range from C-1 to G9, however this method returns values as large as {@code float} type for practical reasons.
     * <p>
     * This implementation includes the {@code adjustment} value in the calculation.
     *
     * @return the MIDI note number.
     */
    public
    float getNumber() {
        return (octave + 1) * 12 + pitch.order + accidental.semitones + adjustment / 100F;
    }

    /**
     * Inverts the note by rotating its accidental, and returns this note.
     *
     * @return the inverted note.
     */
    public
    Note invert() {
        if (accidental != Natural) {
            pitch = Pitch.withOrder((byte) (pitch.order - Integer.signum(accidental.semitones) * 2));
            accidental = Accidental.withSemitone((byte) -accidental.semitones);
        }

        return this;
    }

    public
    Scientific.System.DataPoint newDataPoint() {
        return new Scientific.System.DataPoint()
        {
            @Override
            public Note convert() {
                return Note.this;
            }

            @Override
            public Accidentational<?> getAccidental() {
                return Note.this.getAccidental();
            }

            @Override
            public Octave getOctave() {
                return Octave.withOrder(Note.this.getOctave());
            }

            @Override
            public Tabular<?> getPitch() {
                return Note.this.getPitch();
            }

            @Override
            public boolean is(system.Type<Note> type) {
                return x().equals(getPitch()) &&
                       y().equals(getAccidental());
            }

            @Override
            public Tabular<?> x() {
                return getPitch();
            }

            @Override
            public Accidentational<?> y() {
                return getAccidental();
            }

            @Override
            public Octave z() {
                return getOctave();
            }
        };
    }

    /**
     * Returns a string representation of the note ignoring octave.
     *
     * @return a string representation of the note ignoring octave.
     */
    public
    String toStringIgnoreOctave() {
        return pitch.toString() + accidental.toString() + (adjustment == 0 ? "" : " (" + getAdjustmentString() + " cents)");
    }

    /**
     * Adjusts the note, converts uncommon pitch-accidental combinations to the common form, and returns this note.
     *
     * @return the adjusted note.
     */
    @Override
    public void adjust() {
        if (octave != null && (adjustment < -100 || adjustment > 100)) {
            final short adj = adjustment;
            adjustment = 0;
            add(adj);
        }

        if (accidental == null)
            accidental = Natural;
        else
            switch (pitch) {
            case B:
                if (accidental.equals(Sharp)) {
                    pitch = Pitch.C;
                    accidental = Natural;
                }
                break;

            case C:
                if (accidental.equals(Flat)) {
                    pitch = Pitch.B;
                    accidental = Natural;
                }
                break;

            case E:
                if (accidental.equals(Sharp)) {
                    pitch = Pitch.F;
                    accidental = Natural;
                }
                break;

            case F:
                if (accidental.equals(Flat)) {
                    pitch = Pitch.E;
                    accidental = Natural;
                }
            }
    }

    /**
     * Returns the readjusted note from the specified adjustments.
     * <p>
     * This implementation creates a new note from the first value in {@code adjustments} or re-uses this note's adjustment; and calls {@link #adjust()}.
     *
     * @param adjustments the adjustments.
     */
    @Override
    public Note adjusted(final Number... adjustments) {
        final Note note = new Note(octave, pitch, accidental, adjustments.length == 0 ? adjustment : adjustments[0]);
        note.adjust();
        return note;
    }

    /**
     * Creates and returns a deep copy of this note.
     *
     * @return a deep copy of this note.
     */
    @Override
    public Note clone() {
        if (this instanceof Table)
            return this;

        final Note note = new Note(octave.byteValue(), pitch, accidental.clone(), adjustment);
        note.symbol = symbol;
        return note;
    }

    /**
     * Returns 0 if the specified note has the same amount of cents as this note, -1 if this note has a lower amount, and 1 otherwise.
     * <p>
     * This implementation includes the {@code adjustment} value in the calculation.
     *
     * @param note the note.
     * @return 0 if the notes has the same amount of cents, -1 if this note has a lower amount, and 1 otherwise.
     */
    @Override
    public int compareTo(final Note note) {
        return (int) Math.signum(getDistance(note));
    }

    @Override
    public Note convert() {
        return this;
    }

    /**
     * Returns true if the specified object is a note and has the same octave, pitch, accidental, and adjustment as this note, and false otherwise.
     *
     * @param obj the object.
     * @return true if the note is equal to the specified object, and false otherwise.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Note) {
            final Note note = (Note) obj;
            return octave.equals(note.octave) &&
                   pitch == note.pitch &&
                   accidental.equals(note.accidental) &&
                   adjustment == note.adjustment;
        }

        return false;
    }

    /**
     * Returns the note order.
     * <p>
     * Note order is the pitch order corrected by the amount of semitones in {@code adjustment}.
     * The octave of a note does not affect the note order.
     *
     * @return the note order.
     */
    @Override
    public Float getOrder() {
        final double order = pitch.order + accidental.semitones + adjustment / 100F;
        return (float) ((order + ((1 - (order / 12)) * 12)) % 12);
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(octave, pitch.order, accidental.semitones, adjustment);
    }

    @Override
    public boolean is(final system.Type<Note> type) {
        if (type instanceof Note) {
            final Note note = (Note) type;
            return note.octave == octave &&
                   note.pitch.order == pitch.order &&
                   note.accidental == accidental &&
                   note.adjustment == adjustment;
        }

        return false;
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void setSymbol(String symbol) {}

    @Override
    public String toString() {
        return pitch.toString() + accidental + octave + (adjustment == 0 ? "" : " (" + getAdjustmentString() + " cents)");
    }

    /**
     * Returns the accidental of the note.
     *
     * @return the accidental.
     */
    public
    Accidental getAccidental() {
        return accidental;
    }

    /**
     * Returns the adjustment of the note.
     *
     * @return the adjustment.
     */
    public
    Short getAdjustment() {
        return adjustment;
    }

    /**
     * Returns the octave of the note.
     *
     * @return the octave.
     */
    public
    Byte getOctave() {
        return octave;
    }

    /**
     * Returns the pitch of the note.
     *
     * @return the pitch.
     */
    public
    Pitch getPitch() {
        return pitch;
    }

    /**
     * Sets the accidental of the note.
     * <p>
     * This implementation does not adjust the result and retains the accidental even if it produces an uncommon note.
     *
     * @param accidental the accidental.
     */
    public
    void setAccidental(
        final Accidental accidental
        ) {
        this.accidental = accidental;
    }

    /**
     * Sets the adjustment of the note. (in cents)
     * <p>
     * This implementation converts null to 0, calls {@link Number#shortValue()} on the adjustment, and does not adjust the note.
     *
     * @param adjustment the adjustment.
     */
    public
    void setAdjustment(
        final Number adjustment
        ) {
        this.adjustment = adjustment == null
                        ? 0
                        : adjustment.shortValue();
    }

    /**
     * Sets the octave of the note.
     *
     * @param octave the octave.
     */
    public
    void setOctave(
        final Byte octave
        ) {
        this.octave = octave;
    }

    /**
     * Sets the pitch of the note.
     * <p>
     * This method does not adjust the result and retains the pitch even if it produces an uncommon note.
     *
     * @param pitch the pitch.
     */
    public
    void setPitch(
        final Pitch pitch
        ) {
        this.pitch = pitch;
    }

    /**
     * <b>Wikipedia:</b> In music, an accidental is a note whose pitch (or pitch class) is not a member of a scale or mode indicated by the most recently applied key signature.
     * In musical notation, the sharp, flat, and natural symbols are used to mark such notes, and the symbols may themselves be called accidental.
     * <p>
     * This class defines the standard accidentals: sharp, flat, and natural.
     * Double-sharp and double-flat accidentals are defined in {@link Scale.Accidental}.
     */
    public static
    class Accidental
    implements
        Cloneable,
        Comparable<Accidental>,
        Modulus,
        Modulus.Accidentational<Byte>,
        Readjusting<Accidental>,
        Standardized<Accidental>,
        Supporting<Pitch>,
        Symbolized<String>,
        music.system.Type<Accidental>
    {
        /** The flat accidental. */
        public static final
        Accidental Flat;

        /** The natural accidental. */
        public static final
        Accidental Natural;

        /** The sharp accidental. */
        public static final
        Accidental Sharp;

        static
        {
            Flat = new Standard(Constant.Note.Accidental.Flat, (byte) -1, (short) -100);

            Natural = new Standard(Constant.Note.Accidental.Natural, (byte) 0, (short) 0);

            Sharp = new Standard(Constant.Note.Accidental.Sharp, (byte) 1, (short) 100);
        }

        /** The accidental symbol. */
        protected
        String symbol;

        /** The width of the accidental interval in semitones. */
        protected final
        byte semitones;

        /** The width of the accidental interval in cents. */
        protected final
        short cents;

        /**
         * Creates an accidental with the specified symbol, semitones, and cents.
         *
         * @param symbol the symbol.
         * @param semitones the semitones.
         * @param cents the cents.
         */
        protected
        Accidental(
            final String symbol,
            final byte semitones,
            final short cents
            ) {
            this.symbol = symbol;
            this.semitones = semitones;
            this.cents = cents;
        }

        /**
         * Returns the accidental for the specified semitone, or null if the semitones value is out of range.
         *
         * @param semitone the semitone.
         * @return the accidental.
         */
        public static
        Accidental withSemitone(
            final byte semitone
            ) {
            switch (semitone) {
                case 1:
                    return Sharp;

                case -1:
                    return Flat;

                case 0:
                    return Natural;
            }

            return null;
        }

        public static
        Accidental withSymbol(
            final CharSequence symbol
            ) {
            return null;
        }

        @Override
        public Accidental adjusted(final Number... adjustments) {
            if (adjustments.length == 0)
                return this;

            if (this == Sharp)
                switch(adjustments[0].shortValue()) {
                    case 1:
                    case 100:
                        return Scale.Accidental.DoubleSharp;

                    case -1:
                    case -100:
                        return Natural;

                    case -2:
                    case -200:
                        return Flat;

                    case -3:
                    case -300:
                        return Scale.Accidental.DoubleFlat;
                }
            else
                if (this == Flat)
                    switch(adjustments[0].shortValue()) {
                        case 3:
                        case 300:
                            return Scale.Accidental.DoubleSharp;

                        case 2:
                        case 200:
                            return Sharp;

                        case 1:
                        case 100:
                            return Natural;

                        case -1:
                        case -100:
                            return Scale.Accidental.DoubleFlat;
                    }
                else
                    if (this == Natural)
                        switch(adjustments[0].shortValue()) {
                            case 2:
                            case 200:
                                return Scale.Accidental.DoubleSharp;

                            case 1:
                            case 100:
                                return Sharp;

                            case -1:
                            case -100:
                                return Flat;

                            case -2:
                            case -200:
                                return Scale.Accidental.DoubleFlat;
                        }
                    else
                        if (this == Scale.Accidental.DoubleSharp)
                            switch(adjustments[0].shortValue()) {
                                case -1:
                                case -100:
                                    return Sharp;

                                case -2:
                                case -200:
                                    return Natural;

                                case -3:
                                case -300:
                                    return Flat;

                                case -4:
                                case -400:
                                    return Scale.Accidental.DoubleFlat;
                            }
                        else
                            if (this == Scale.Accidental.DoubleFlat)
                                switch(adjustments[0].shortValue()) {
                                    case 4:
                                    case 400:
                                        return Scale.Accidental.DoubleSharp;

                                    case 3:
                                    case 300:
                                        return Sharp;

                                    case 2:
                                    case 200:
                                        return Natural;

                                    case 1:
                                    case 100:
                                        return Flat;
                                }
            return null;
        }

        @Override
        public Accidental clone() {
            return isStandard(this)
                   ? ((Standard) this).clone()
                   : new Accidental(symbol, semitones, cents);
        }

        @Override
        public int compareTo(final Accidental accidental) {
            return cents - accidental.cents;
        }

        @Override
        public Accidental convert() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(final Object obj) {
            return obj instanceof Accidental &&
                   ((obj instanceof Standard
                     && this == obj)
                   || ((Accidental) obj).cents == cents);
        }

        /**
         * Returns the order of the accidental.
         *
         * @return the order.
         */
        @Override
        public Byte getOrder() {
            return semitones;
        }

        @Override
        public String getSymbol() {
            return symbol;
        }

        @Override
        public boolean is(final system.Type<Accidental> type) {
            return type == this;
        }

        /**
         * Returns true if the specified accidental is standard, and false otherwise.
         *
         * @param accidental the accidental.
         * @return true if accidental is standard, and false otherwise.
         */
        @Override
        public boolean isStandard(final Accidental accidental) {
            return accidental instanceof Standard;
        }

        public static
        boolean isValidDoubleSymbol(
            final char symbol
            ) {
            return isValidSingleSymbol(symbol) ||
                   isValidNaturalSymbol(symbol);
        }

        public static
        boolean isValidNaturalSymbol(
            final char symbol
            ) {
            return (!Constant.Note.Accidental.Natural.isEmpty() &&
                   symbol == Constant.Note.Accidental.Natural.charAt(0)) ||
                   symbol == ' ';
        }

        public static
        boolean isValidSingleSymbol(
            final char symbol
            ) {
            return symbol == Constant.Note.Accidental.Sharp.charAt(0) ||
                   symbol == Constant.Note.Accidental.Flat.charAt(0) ||
                   isValidNaturalSymbol(symbol);
        }

        @Override
        public void setSymbol(final String symbol) {
            this.symbol = symbol;
        }

        @Override
        public boolean supports(final Pitch pitch) {
            if (this == Natural)
                return pitch != null;

            switch (pitch) {
                case B:
                    return this == Flat;

                case C:
                    return this == Sharp;

                case E:
                    return this == Flat;

                case F:
                    return this == Sharp;
            }

            return true;
        }

        /**
         * Returns the cents in the accidental.
         *
         * @return the cents.
         */
        public
        short getCents() {
            return cents;
        }

        public
        interface Fall
        extends Modulus.Fall
        {}

        public
        interface Rise
        extends Modulus.Rise
        {}

        /**
         * {@code Standard} represents all standard accidentals.
         */
        protected static
        class Standard
        extends Accidental
        {
            /**
             * Creates a standard accidental with the specified symbol, semitones, and cents.
             *
             * @param symbol the symbol.
             * @param semitones the semitones.
             * @param cents the cents.
             */
            protected
            Standard(
                final String symbol,
                final byte semitones,
                final short cents
                ) {
                super(symbol, semitones, cents);
            }

            @Override
            public Standard clone() {
                return this == Sharp ||
                       this == Flat ||
                       this == Natural
                       ? this
                       : new Standard(symbol, semitones, cents);
            }
        }
    }

    /**
     * In music, dynamics normally refers to the volume of a sound or note, but can also refer to every aspect of the execution of a given piece, either stylistic (staccato, legato, etc.) or functional. (velocity)
     */
    public static
    class Dynamics
    implements
        Cloneable,
        Comparable<Dynamics>,
        Readjusting<Dynamics>,
        Symbolized<String>,
        music.system.Type<Dynamics>
    {
        /** Pianississimo. (very very soft) */
        public static final
        Dynamics PPP;

        /** Pianissimo. (very soft) */
        public static final
        Dynamics PP;

        /** Piano. (soft) */
        public static final
        Dynamics P;

        /** Mezzo-piano. (moderately soft) */
        public static final
        Dynamics MP;

        /** Mezzo-forte. (moderately loud) */
        public static final
        Dynamics MF;

        /** Forte. (loud) */
        public static final
        Dynamics F;

        /** Fortissimo. (very loud) */
        public static final
        Dynamics FF;

        /** Fortississimo. (very very loud) */
        public static final
        Dynamics FFF;

        static
        {
            PPP = new Standard(Constant.Note.Dynamics.PianississimoSym, Constant.Note.Dynamics.PianississimoName, (byte) 16);

            PP = new Standard(Constant.Note.Dynamics.PianissimoSym, Constant.Note.Dynamics.PianissimoName, (byte) 33);

            P = new Standard(Constant.Note.Dynamics.PianoSym, Constant.Note.Dynamics.PianoName, (byte) 49);

            MP = new Standard(Constant.Note.Dynamics.MezzoPianoSym, Constant.Note.Dynamics.MezzoPianoName, (byte) 64);

            MF = new Standard(Constant.Note.Dynamics.MezzoForteSym, Constant.Note.Dynamics.MezzoForteName, (byte) 80);

            F = new Standard(Constant.Note.Dynamics.ForteSym, Constant.Note.Dynamics.ForteName, (byte) 96);

            FF = new Standard(Constant.Note.Dynamics.FortissimoSym, Constant.Note.Dynamics.FortissimoName, (byte) 112);

            FFF = new Standard(Constant.Note.Dynamics.FortississimoSym, Constant.Note.Dynamics.FortississimoName, (byte) 127);
        }

        /** The dynamics symbol. */
        protected
        String symbol;

        /** The dynamics name. */
        protected final
        String name;

        /** The dynamics velocity. (loudness) */
        protected final
        byte velocity;

        /**
         * Creates a dynamics with the specified symbol, name, and velocity.
         *
         * @param symbol the symbol.
         * @param name the name.
         * @param velocity the velocity.
         */
        protected
        Dynamics(
            final String symbol,
            final String name,
            final byte velocity
            ) {
            this.symbol = symbol;
            this.name = name;
            this.velocity = velocity;
        }

        /**
         * Creates a dynamics with the specified symbol and velocity.
         *
         * @param symbol the symbol.
         * @param velocity the velocity.
         */
        protected
        Dynamics(
            final String symbol,
            final byte velocity
            ) {
            this(symbol, null, velocity);
        }

        /**
         * Returns true if the specified dynamics is standard, and false otherwise.
         *
         * @param dynamics the dynamics.
         * @return true if dynamics is standard, and false otherwise.
         */
        public static
        boolean isStandard(
            final Dynamics dynamics
            ) {
            return dynamics instanceof Standard;
        }

        @Override
        public Dynamics adjusted(Number... adjustments) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Dynamics clone() {
            return isStandard(this)
                   ? (this == PPP ||
                     this == PP ||
                     this == P ||
                     this == MP ||
                     this == MF ||
                     this == F ||
                     this == FF ||
                     this == FFF
                     ? this
                     : new Standard(symbol, name, velocity))
                   : new Dynamics(symbol, name, velocity);
        }

        @Override
        public int compareTo(final Dynamics dynamics) {
            return velocity - dynamics.velocity;
        }

        /**
         * Returns true if the specified object is the same instance as this dynamics, or has a non-null symbol similar to this dynamics.
         *
         * @param type the dynamics.
         */
        @Override
        public boolean equals(final Object obj) {
            return this == obj ||
                   (obj instanceof Dynamics &&
                   symbol != null &&
                   symbol.equalsIgnoreCase(((Dynamics) obj).symbol));
        }

        @Override
        public String getSymbol() {
            return symbol;
        }

        @Override
        public boolean is(final system.Type<Dynamics> type) {
            return equals(type);
        }

        @Override
        public void setSymbol(final String symbol) {
            this.symbol = symbol;
        }

        /**
         * Returns the name of the dynamics.
         *
         * @return the name.
         */
        public
        String getName() {
            return name;
        }

        /**
         * {@code Standard} represents all standard dynamics.
         */
        protected static
        class Standard
        extends Dynamics
        {
            /**
             * Creates a standard dynamics with the specified symbol, name, and velocity.
             *
             * @param symbol the symbol.
             * @param name the name.
             * @param velocity the velocity.
             */
            protected
            Standard(
                final String symbol,
                final String name,
                final byte velocity
                ) {
                super(symbol, name, velocity);
            }
        }
    }

    public static abstract
    class Group
    extends Note
    implements
        Set<Note>,
        Supporting<Chord>
    {
        /** The group accompaniment. */
        protected
        LinkedList<Note> accompaniment;

        /**
         * Creates a note group starting with the specified note.
         *
         * @param octave the octave.
         * @param pitch the pitch.
         * @param accidental the accidental.
         * @param adjustment the adjustment. (in cents)
         */
        public
        Group(
            final Number octave,
            final Pitch pitch,
            final Accidental accidental,
            final Number adjustment
            ) {
            super(octave, pitch, accidental, adjustment);
        }

        @Override
        public boolean add(final Note note) {
            if (equals(note) || accompaniment.contains(note))
                return false;

            return accompaniment.add(note);
        }

        @Override
        public boolean addAll(final Collection<? extends Note> notes) {
            if (notes == null)
                return false;

            synchronized (this) {
                int size = size();
                notes.forEach(this::add);
                return size < size();
            }
        }

        /**
         * Returns the readjusted note group from the starting note using the specified adjustments.
         * <p>
         * This implementation creates a new note group from the first value in {@code adjustments} or re-uses the starting note's adjustment; and calls {@link #adjust()} on all notes in the group.
         *
         * @param adjustments the adjustments.
         */
        @Override
        public Note adjusted(final Number... adjustments) {
            final Group group = new Group(octave, pitch, accidental, adjustments.length == 0 ? adjustment : adjustments[0])
            {
                @Override
                public int size() {
                    return adjustments.length + 1;
                }
            };

            if (adjustments.length > 0) {
                group.accompaniment = new LinkedList<Note>();
                for (int i = 1; i < adjustments.length; i++) {
                    final Note note = new Note(octave, pitch, accidental, adjustments[i]);
                    note.adjust();
                }
            }

            group.adjust();
            return group;
        }

        @Override
        public void clear() {
            accompaniment.clear();
        }

        @Override
        public boolean contains(final Object obj) {
            return obj instanceof Note &&
                   (equals((Note) obj) ||
                   accompaniment.contains(obj));
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Iterator<Note> iterator() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean remove(Object o) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public int size() {
            return accompaniment == null
                   ? 1
                   : accompaniment.size() + 1;
        }

        @Override
        public boolean supports(Chord instance) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public Object[] toArray() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public <T> T[] toArray(T[] a) {
            // TODO Auto-generated method stub
            return null;
        }

        /**
         * Returns the note group accompaniment.
         * <p>
         * This implementation returns null if no accompaniment is added.
         *
         * @return the list of accompanied notes.
         */
        public
        List<Note> getAccompaniment() {
            return accompaniment;
        }
    }

    public
    interface Octave
    extends
        Ordered<Byte>,
        Templator
    {
        public final Octave NegativeSecond = new Octave()
        {
            @Override
            public Byte getOrder() {
                return (byte) -2;
            }
        };

        public final Octave NegativeFirst = new Octave()
        {
            @Override
            public Byte getOrder() {
                return (byte) -1;
            }
        };

        public final Octave Zeroth = new Octave()
        {
            @Override
            public Byte getOrder() {
                return (byte) 0;
            }
        };

        public final Octave First = new Octave()
        {
            @Override
            public Byte getOrder() {
                return (byte) 1;
            }
        };

        public final Octave Second = new Octave()
        {
            @Override
            public Byte getOrder() {
                return (byte) 2;
            }
        };

        public final Octave Third = new Octave()
        {
            @Override
            public Byte getOrder() {
                return (byte) 3;
            }
        };

        public final Octave Fourth = new Octave()
        {
            @Override
            public Byte getOrder() {
                return (byte) 4;
            }
        };

        public final Octave Fifth = new Octave()
        {
            @Override
            public Byte getOrder() {
                return (byte) 5;
            }
        };

        public final Octave Sixth = new Octave()
        {
            @Override
            public Byte getOrder() {
                return (byte) 6;
            }
        };

        public final Octave Seventh = new Octave()
        {
            @Override
            public Byte getOrder() {
                return (byte) 7;
            }
        };

        public final Octave Eighth = new Octave()
        {
            @Override
            public Byte getOrder() {
                return (byte) 8;
            }
        };

        public final Octave Ninth = new Octave()
        {
            @Override
            public Byte getOrder() {
                return (byte) 9;
            }
        };

        public final Octave Tenth = new Octave()
        {
            @Override
            public Byte getOrder() {
                return (byte) 10;
            }
        };

        public final Octave Null = null;

        public static
        Octave valueOf(
            final Class<? extends Octave> octave
            ) {
            if (octave.getClass().equals(Fourth.getClass()))
                return Fourth;
            else
            if (octave.getClass().equals(Third.getClass()))
                return Third;
            else
            if (octave.getClass().equals(Fifth.getClass()))
                return Fifth;
            else
            if (octave.getClass().equals(Sixth.getClass()))
                return Sixth;
            else
            if (octave.getClass().equals(Second.getClass()))
                return Second;
            else
            if (octave.getClass().equals(Seventh.getClass()))
                return Seventh;
            else
            if (octave.getClass().equals(Eighth.getClass()))
                return Eighth;
            else
            if (octave.getClass().equals(First.getClass()))
                return First;
            else
            if (octave.getClass().equals(Zeroth.getClass()))
                return Zeroth;
            else
            if (octave.getClass().equals(Ninth.getClass()))
                return Ninth;
            else
            if (octave.getClass().equals(NegativeFirst.getClass()))
                return NegativeFirst;
            else
            if (octave.getClass().equals(Tenth.getClass()))
                return Tenth;
            else
            if (octave.getClass().equals(NegativeSecond.getClass()))
                return NegativeSecond;

            return Null;
        }

        public static
        Octave withOrder(
            final Number order
            ) {
            switch (order.byteValue()) {
                case -2:
                    return NegativeSecond;

                case -1:
                    return NegativeFirst;

                case 0:
                    return Zeroth;

                case 1:
                    return First;

                case 2:
                    return Second;

                case 3:
                    return Third;

                case 4:
                    return Fourth;

                case 5:
                    return Fifth;

                case 6:
                    return Sixth;

                case 7:
                    return Seventh;

                case 8:
                    return Eighth;

                case 9:
                    return Ninth;

                case 10:
                    return Tenth;

                default:
                    throw new IllegalArgumentException("order is out of range.");
            }
        }
    }

    /**
     * {@code Pitch} represents one of the seven standard pitches in Western classical music: A, B, C, D, E, F, and G.
     */
    public
    enum Pitch
    implements
        Modulus,
        Modulus.Tabular<Byte>,
        Readjusting<Pitch>,
        Supporting<Accidental>,
        Symbolized<String>,
        music.system.Type<Pitch>
    {
        /** The A pitch. (9) */
        A(Constant.Note.Pitch.A, (byte) 9),

        /** The B pitch. (11) */
        B(Constant.Note.Pitch.B, (byte) 11),

        /** The C pitch. (0) */
        C(Constant.Note.Pitch.C, (byte) 0),

        /** The D pitch. (2) */
        D(Constant.Note.Pitch.D, (byte) 2),

        /** The E pitch. (4) */
        E(Constant.Note.Pitch.E, (byte) 4),

        /** The F pitch. (5) */
        F(Constant.Note.Pitch.F, (byte) 5),

        /** The G pitch. (7) */
        G(Constant.Note.Pitch.G, (byte) 7);

        /** The pitch symbol. */
        private
        String symbol;

        /** The pitch order.<p>The pitch order must be between 0 and 11. */
        public final
        byte order;

        /**
         * Creates a pitch with the specified symbol and order.
         *
         * @param symbol the symbol.
         * @param order the order.
         */
        private
        Pitch(
            final String symbol,
            final byte order
            ) {
            this.symbol = symbol;
            this.order = order;
        }

        /**
         * Creates a pitch with the specified order and null symbol.
         *
         * @param order the order.
         */
        private
        Pitch(
            final byte order
            ) {
            this(null, order);
        }

        public static
        boolean isValid(
            final char symbol
            ) {
            return Character.isUpperCase(symbol)
                   ? (symbol == Constant.Note.Pitch.A.charAt(0) ||
                     symbol == Constant.Note.Pitch.B.charAt(0) ||
                     symbol == Constant.Note.Pitch.C.charAt(0) ||
                     symbol == Constant.Note.Pitch.D.charAt(0) ||
                     symbol == Constant.Note.Pitch.E.charAt(0) ||
                     symbol == Constant.Note.Pitch.F.charAt(0) ||
                     symbol == Constant.Note.Pitch.G.charAt(0))
                   : symbol == Character.toLowerCase(Constant.Note.Pitch.A.charAt(0)) ||
                     symbol == Character.toLowerCase(Constant.Note.Pitch.B.charAt(0)) ||
                     symbol == Character.toLowerCase(Constant.Note.Pitch.C.charAt(0)) ||
                     symbol == Character.toLowerCase(Constant.Note.Pitch.D.charAt(0)) ||
                     symbol == Character.toLowerCase(Constant.Note.Pitch.E.charAt(0)) ||
                     symbol == Character.toLowerCase(Constant.Note.Pitch.F.charAt(0)) ||
                     symbol == Character.toLowerCase(Constant.Note.Pitch.G.charAt(0));
        }

        public static
        Pitch valueOf(
            final char symbol
            ) {
            return valueOf(symbol + "");
        }

        /**
         * Returns the pitch for the specified order, or throws an {@code IllegalArgumentException} if order is out of range.
         * <p>
         * The pitch order must be between 0 and 11. (inclusive)
         *
         * @param order the order.
         * @return the pitch.
         * @throws IllegalArgumentException if order is out of range.
         */
        public static
        Pitch withOrder(
            final byte order
            ) {
            switch (order) {
            case 0:
            case 1:
                return C;

            case 2:
            case 3:
                return D;

            case 4:
                return E;

            case 5:
            case 6:
                return F;

            case 7:
            case 8:
                return G;

            case 9:
            case 10:
                return A;

            case 11:
                return B;

            default:
                throw new IllegalArgumentException("Pitch order must be between 0 and 11.");
            }
        }

        @Override
        public Pitch adjusted(Number... adjustments) {
            if (adjustments.length == 0)
                return this;

            if (adjustments[0].shortValue() >= 100)
                adjustments[0] = adjustments[0].shortValue() / 100;

            return withOrder((byte) (adjustments[0].shortValue() - (adjustments[0].shortValue() / 12) * 12));
        }

        /**
         * Returns the order of the pitch.
         *
         * @return the order.
         */
        @Override
        public Byte getOrder() {
            return order;
        }

        @Override
        public String getSymbol() {
            return symbol;
        }

        @Override
        public boolean is(final system.Type<Pitch> type) {
            return type == this;
        }

        @Override
        public void setSymbol(final String symbol) {
            this.symbol = symbol;
        }

        @Override
        public boolean supports(final Accidental accidental) {
            switch(this) {
                case B:
                    return accidental != Sharp;

                case C:
                    return accidental != Flat;

                case E:
                    return accidental != Sharp;

                case F:
                    return accidental != Flat;
            }

            return true;
        }

        @Override
        public String toString() {
            return symbol == null
                   ? name()
                   : getSymbol();
        }
    }

    public
    interface Modulus
    extends Regressor
    {
        public
        interface Accidentational<T extends Number>
        extends
            Ordered<T>,
            Rotational<Accidental>
        {
            public
            interface Conversional<T extends Progressor>
            extends Regressional<Accidental, Ordered<?>, T>
            {
                public
                Accidental transform(
                    T progress,
                    Ordered<?>... amount
                    );
            }
        }

        public
        interface Fall
        extends
            Elementary,
            Influence,
            Progressor
        {}

        public
        interface Tabular<T extends Number>
        extends Ordered<T>
        {
            public
            interface Conversional<T extends Progressor>
            extends Regressional<Modulus, Ordered<?>, T>
            {
                public
                Modulus transpose(
                    T progress,
                    Ordered<?>... amount
                    );
            }
        }

        public
        interface Rise
        extends
            Elementary,
            Influence,
            Progressor
        {}
    }

    public
    interface Scientific<E extends Ordered<? extends Number>, X extends Ordered<? extends Number>, Y extends Regressor>
    extends TemplativeRegression<E, X, Y>
    {
        public
        interface Progression<X extends Regressor, Y extends Regressor>
        extends Regressive<X, Y>
        {}

        public
        interface Regression<X extends Regressor, Y extends Progressor>
        extends Regressive<X, Y>
        {}

        public
        interface System
        extends Scientific<Note, Octave, Modulus>
        {
            public
            interface DataPoint
            extends
                Clockable<Note>,
                music.system.data.DataPoint<Modulus.Tabular<?>, Modulus.Accidentational<?>, Octave>,
                Modulus,
                music.system.Type<Note>
            {
                public
                Modulus.Accidentational<?> getAccidental();

                public default
                Octave getOctave() {
                    return Octave.Null;
                }

                public
                Modulus.Tabular<?> getPitch();
            }
        }
    }

    public
    interface Spective
    extends
        Ordered<Short>,
        Templator
    {
        public
        Interval getKind();

        public final static Spective Full = new Spective()
        {
            @Override
            public Short getOrder() {
                return (byte) 200;
            }

            @Override
            public Interval getKind() {
                return Interval.MajorSecond;
            }
        };

        public final static Spective Half = new Spective()
        {
            @Override
            public Short getOrder() {
                return (byte) 100;
            }

            @Override
            public Interval getKind() {
                return Interval.MinorSecond;
            }
        };

        public final static Spective Quarter = new Spective()
        {
            @Override
            public Short getOrder() {
                return (byte) 50;
            }

            @Override
            public Interval getKind() {
                return Interval.QuarterTone;
            }
        };

        public final static Spective Eighth = new Spective()
        {
            private
            Interval kind;

            @Override
            public Short getOrder() {
                return (byte) 25;
            }

            @Override
            public Interval getKind() {
                if (kind == null)
                    synchronized (this) {
                        if (kind == null)
                            kind = new Interval((short) 25);
                    }

                return kind;
            }
        };

        public final static Spective Tenor = new Spective()
        {
            private
            Interval kind;

            @Override
            public Short getOrder() {
                return (byte) 10;
            }

            @Override
            public Interval getKind() {
                if (kind == null)
                    synchronized (this) {
                        if (kind == null)
                            kind = new Interval((short) 10);
                    }

                return kind;
            }
        };
    }

    public static final
    class Table
    extends Note
    implements Scientific.System
    {
        public static final Note C_1 = new Table((byte) 0, "C-1", (byte) -1, Pitch.C, 8.18F);
        public static final Note C_1s = new Table((byte) 1, "C-1#", (byte) -1, Pitch.C, Sharp, 8.66F);
        public static final Note D_1f = new Table((byte) 1, "D-1b", (byte) -1, Pitch.D, Flat, 8.66F);
        public static final Note D_1 = new Table((byte) 2, "D-1", (byte) -1, Pitch.D, 9.18F);
        public static final Note D_1s = new Table((byte) 3, "D-1#", (byte) -1, Pitch.D, Sharp, 9.72F);
        public static final Note E_1f = new Table((byte) 3, "E-1b", (byte) -1, Pitch.E, Flat, 9.72F);
        public static final Note E_1 = new Table((byte) 4, "E-1", (byte) -1, Pitch.E, 10.30F);
        public static final Note F_1 = new Table((byte) 5, "F-1", (byte) -1, Pitch.F, 10.91F);
        public static final Note F_1s = new Table((byte) 6, "F-1#", (byte) -1, Pitch.F, Sharp, 11.56F);
        public static final Note G_1f = new Table((byte) 6, "G-1b", (byte) -1, Pitch.G, Flat, 11.56F);
        public static final Note G_1 = new Table((byte) 7, "G-1", (byte) -1, Pitch.G, 12.25F);
        public static final Note G_1s = new Table((byte) 8, "G-1#", (byte) -1, Pitch.G, Sharp, 12.98F);
        public static final Note A_1f = new Table((byte) 8, "A-1b", (byte) -1, Pitch.A, Flat, 12.98F);

        public static final Note A_1 = new Table((byte) 9, "A-1", (byte) -1, Pitch.A, 13.75F);
        public static final Note A_1s = new Table((byte) 10, "A-1#", (byte) -1, Pitch.A, Sharp, 14.57F);
        public static final Note B_1f = new Table((byte) 10, "B-1b", (byte) -1, Pitch.B, Flat, 14.57F);
        public static final Note B_1 = new Table((byte) 11, "B-1", (byte) -1, Pitch.B, 15.43F);
        public static final Note C0 = new Table((byte) 12, "C0", (byte) 0, Pitch.C, 16.35F);
        public static final Note C0s = new Table((byte) 13, "C0#", (byte) 0, Pitch.C, Sharp, 17.32F);
        public static final Note D0f = new Table((byte) 13, "D0b", (byte) 0, Pitch.D, Flat, 17.32F);
        public static final Note D0 = new Table((byte) 14, "D0", (byte) 0, Pitch.D, 18.35F);
        public static final Note D0s = new Table((byte) 15, "D0#", (byte) 0, Pitch.D, Sharp, 19.45F);
        public static final Note E0f = new Table((byte) 15, "E0b", (byte) 0, Pitch.E, Flat, 19.45F);
        public static final Note E0 = new Table((byte) 16, "E0", (byte) 0, Pitch.E, 20.60F);
        public static final Note F0 = new Table((byte) 17, "F0", (byte) 0, Pitch.F, 21.83F);
        public static final Note F0s = new Table((byte) 18, "F0#", (byte) 0, Pitch.F, Sharp, 23.12F);
        public static final Note G0f = new Table((byte) 18, "G0b", (byte) 0, Pitch.G, Flat, 23.12F);
        public static final Note G0 = new Table((byte) 19, "G0", (byte) 0, Pitch.G, 24.50F);
        public static final Note G0s = new Table((byte) 20, "G0#", (byte) 0, Pitch.G, Sharp, 25.96F);
        public static final Note A0f = new Table((byte) 20, "A0b", (byte) 0, Pitch.A, Flat, 25.96F);

        public static final Note A0 = new Table((byte) 21, "A0", (byte) 0, Pitch.A, 27.50F);
        public static final Note A0s = new Table((byte) 22, "A0#", (byte) 0, Pitch.A, Sharp, 29.14F);
        public static final Note B0f = new Table((byte) 22, "B0b", (byte) 0, Pitch.B, Flat, 29.14F);
        public static final Note B0 = new Table((byte) 23, "B0", (byte) 0, Pitch.B, 30.87F);
        public static final Note C1 = new Table((byte) 24, "C1", (byte) 1, Pitch.C, 32.70F);
        public static final Note C1s = new Table((byte) 25, "C1#", (byte) 1, Pitch.C, Sharp, 34.65F);
        public static final Note D1f = new Table((byte) 25, "D1b", (byte) 1, Pitch.D, Flat, 34.65F);
        public static final Note D1 = new Table((byte) 26, "D1", (byte) 1, Pitch.D, 36.71F);
        public static final Note D1s = new Table((byte) 27, "D1#", (byte) 1, Pitch.D, Sharp, 38.89F);
        public static final Note E1f = new Table((byte) 27, "E1b", (byte) 1, Pitch.E, Flat, 38.89F);
        public static final Note E1 = new Table((byte) 28, "E1", (byte) 1, Pitch.E, 41.20F);
        public static final Note F1 = new Table((byte) 29, "F1", (byte) 1, Pitch.F, 43.65F);
        public static final Note F1s = new Table((byte) 30, "F1#", (byte) 1, Pitch.F, Sharp, 46.25F);
        public static final Note G1f = new Table((byte) 30, "G1b", (byte) 1, Pitch.G, Flat, 46.25F);
        public static final Note G1 = new Table((byte) 31, "G1", (byte) 1, Pitch.G, 49.00F);
        public static final Note G1s = new Table((byte) 32, "G1#", (byte) 1, Pitch.G, Sharp, 51.91F);
        public static final Note A1f = new Table((byte) 32, "A1b", (byte) 1, Pitch.A, Flat, 51.91F);

        public static final Note A1 = new Table((byte) 33, "A1", (byte) 1, Pitch.A, 55.00F);
        public static final Note A1s = new Table((byte) 34, "A1#", (byte) 1, Pitch.A, Sharp, 58.27F);
        public static final Note B1f = new Table((byte) 34, "B1b", (byte) 1, Pitch.B, Flat, 58.27F);
        public static final Note B1 = new Table((byte) 35, "B1", (byte) 1, Pitch.B, 61.74F);
        public static final Note C2 = new Table((byte) 36, "C2", (byte) 2, Pitch.C, 65.41F);
        public static final Note C2s = new Table((byte) 37, "C2#", (byte) 2, Pitch.C, Sharp, 69.30F);
        public static final Note D2f = new Table((byte) 37, "D2b", (byte) 2, Pitch.D, Flat, 69.30F);
        public static final Note D2 = new Table((byte) 38, "D2", (byte) 2, Pitch.D, 73.42F);
        public static final Note D2s = new Table((byte) 39, "D2#", (byte) 2, Pitch.D, Sharp, 77.78F);
        public static final Note E2f = new Table((byte) 39, "E2b", (byte) 2, Pitch.E, Flat, 77.78F);
        public static final Note E2 = new Table((byte) 40, "E2", (byte) 2, Pitch.E, 82.41F);
        public static final Note F2 = new Table((byte) 41, "F2", (byte) 2, Pitch.F, 87.31F);
        public static final Note F2s = new Table((byte) 42, "F2#", (byte) 2, Pitch.F, Sharp, 92.50F);
        public static final Note G2f = new Table((byte) 42, "G2b", (byte) 2, Pitch.G, Flat, 92.50F);
        public static final Note G2 = new Table((byte) 43, "G2", (byte) 2, Pitch.G, 98.00F);
        public static final Note G2s = new Table((byte) 44, "G2#", (byte) 2, Pitch.G, Sharp, 103.83F);
        public static final Note A2f = new Table((byte) 44, "A2b", (byte) 2, Pitch.A, Flat, 103.83F);

        public static final Note A2 = new Table((byte) 45, "A2", (byte) 2, Pitch.A, 110.00F);
        public static final Note A2s = new Table((byte) 46, "A2#", (byte) 2, Pitch.A, Sharp, 116.54F);
        public static final Note B2f = new Table((byte) 46, "B2b", (byte) 2, Pitch.B, Flat, 116.54F);
        public static final Note B2 = new Table((byte) 47, "B2", (byte) 2, Pitch.B, 123.47F);
        public static final Note C3 = new Table((byte) 48, "C3", (byte) 3, Pitch.C, 130.81F);
        public static final Note C3s = new Table((byte) 49, "C3#", (byte) 3, Pitch.C, Sharp, 138.59F);
        public static final Note D3f = new Table((byte) 49, "D3b", (byte) 3, Pitch.D, Flat, 138.59F);
        public static final Note D3 = new Table((byte) 50, "D3", (byte) 3, Pitch.D, 146.83F);
        public static final Note D3s = new Table((byte) 51, "D3#", (byte) 3, Pitch.D, Sharp, 155.56F);
        public static final Note E3f = new Table((byte) 51, "E3b", (byte) 3, Pitch.E, Flat, 155.56F);
        public static final Note E3 = new Table((byte) 52, "E3", (byte) 3, Pitch.E, 164.81F);
        public static final Note F3 = new Table((byte) 53, "F3", (byte) 3, Pitch.F, 174.61F);
        public static final Note F3s = new Table((byte) 54, "F3#", (byte) 3, Pitch.F, Sharp, 185.00F);
        public static final Note G3f = new Table((byte) 54, "G3b", (byte) 3, Pitch.G, Flat, 185.00F);
        public static final Note G3 = new Table((byte) 55, "G3", (byte) 3, Pitch.G, 196.00F);
        public static final Note G3s = new Table((byte) 56, "G3#", (byte) 3, Pitch.G, Sharp, 207.65F);
        public static final Note A3f = new Table((byte) 56, "A3b", (byte) 3, Pitch.A, Flat, 207.65F);

        public static final Note A3 = new Table((byte) 57, "A3", (byte) 3, Pitch.A, 220.00F);
        public static final Note A3s = new Table((byte) 58, "A3#", (byte) 3, Pitch.A, Sharp, 233.08F);
        public static final Note B3f = new Table((byte) 58, "B3b", (byte) 3, Pitch.B, Flat, 233.08F);
        public static final Note B3 = new Table((byte) 59, "B3", (byte) 3, Pitch.B, 246.94F);
        public static final Note C4 = new Table((byte) 59, "C4", (byte) 4, Pitch.C, 261.63F);
        public static final Note C4s = new Table((byte) 60, "C4#", (byte) 4, Pitch.C, Sharp, 277.18F);
        public static final Note D4f = new Table((byte) 61, "D4b", (byte) 4, Pitch.D, Flat, 277.18F);
        public static final Note D4 = new Table((byte) 62, "D4", (byte) 4, Pitch.D, 293.66F);
        public static final Note D4s = new Table((byte) 63, "D4#", (byte) 4, Pitch.D, Sharp, 311.13F);
        public static final Note E4f = new Table((byte) 63, "E4b", (byte) 4, Pitch.E, Flat, 311.13F);
        public static final Note E4 = new Table((byte) 64, "E4", (byte) 4, Pitch.E, 329.63F);
        public static final Note F4 = new Table((byte) 65, "F4", (byte) 4, Pitch.F, 349.23F);
        public static final Note F4s = new Table((byte) 66, "F4#", (byte) 4, Pitch.F, Sharp, 369.99F);
        public static final Note G4f = new Table((byte) 66, "G4b", (byte) 4, Pitch.G, Flat, 369.99F);
        public static final Note G4 = new Table((byte) 67, "G4", (byte) 4, Pitch.G, 392.00F);
        public static final Note G4s = new Table((byte) 68, "G4#", (byte) 4, Pitch.G, Sharp, 415.30F);
        public static final Note A4f = new Table((byte) 68, "A4b", (byte) 4, Pitch.A, Flat, 415.30F);

        public static final Note A4 = new Table((byte) 69, "A4", (byte) 4, Pitch.A, 440F);
        public static final Note A4s = new Table((byte) 70, "A4#", (byte) 4, Pitch.A, Sharp, 466.16F);
        public static final Note B4f = new Table((byte) 70, "B4b", (byte) 4, Pitch.B, Flat, 466.16F);
        public static final Note B4 = new Table((byte) 71, "B4", (byte) 4, Pitch.B, 493.88F);
        public static final Note C5 = new Table((byte) 72, "C5", (byte) 5, Pitch.C, 523.25F);
        public static final Note C5s = new Table((byte) 73, "C5#", (byte) 5, Pitch.C, Sharp, 554.37F);
        public static final Note D5f = new Table((byte) 73, "D5b", (byte) 5, Pitch.D, Flat, 554.37F);
        public static final Note D5 = new Table((byte) 74, "D5", (byte) 5, Pitch.D, 587.33F);
        public static final Note D5s = new Table((byte) 75, "D5#", (byte) 5, Pitch.D, Sharp, 622.25F);
        public static final Note E5f = new Table((byte) 75, "E5b", (byte) 5, Pitch.E, Flat, 622.25F);
        public static final Note E5 = new Table((byte) 76, "E5", (byte) 5, Pitch.E, 659.26F);
        public static final Note F5 = new Table((byte) 77, "F5", (byte) 5, Pitch.F, 698.46F);
        public static final Note F5s = new Table((byte) 78, "F5#", (byte) 5, Pitch.F, Sharp, 739.99F);
        public static final Note G5f = new Table((byte) 78, "G5b", (byte) 5, Pitch.G, Flat, 739.99F);
        public static final Note G5 = new Table((byte) 79, "G5", (byte) 5, Pitch.G, 783.99F);
        public static final Note G5s = new Table((byte) 80, "G5#", (byte) 5, Pitch.G, Sharp, 830.61F);
        public static final Note A5f = new Table((byte) 80, "A5b", (byte) 5, Pitch.A, Flat, 830.61F);

        public static final Note A5 = new Table((byte) 81, "A5", (byte) 5, Pitch.A, 880.00F);
        public static final Note A5s = new Table((byte) 82, "A5#", (byte) 5, Pitch.A, Sharp, 932.33F);
        public static final Note B5f = new Table((byte) 82, "B5b", (byte) 5, Pitch.B, Flat, 932.33F);
        public static final Note B5 = new Table((byte) 83, "B5", (byte) 5, Pitch.B, 987.77F);
        public static final Note C6 = new Table((byte) 84, "C6", (byte) 6, Pitch.C, 1046.50F);
        public static final Note C6s = new Table((byte) 85, "C6#", (byte) 6, Pitch.C, Sharp, 1108.73F);
        public static final Note D6f = new Table((byte) 85, "D6b", (byte) 6, Pitch.D, Flat, 1108.73F);
        public static final Note D6 = new Table((byte) 86, "D6", (byte) 6, Pitch.D, 1174.66F);
        public static final Note D6s = new Table((byte) 87, "D6#", (byte) 6, Pitch.D, Sharp, 1244.51F);
        public static final Note E6f = new Table((byte) 87, "E6b", (byte) 6, Pitch.E, Flat, 1244.51F);
        public static final Note E6 = new Table((byte) 88, "E6", (byte) 6, Pitch.E, 1318.51F);
        public static final Note F6 = new Table((byte) 89, "F6", (byte) 6, Pitch.F, 1396.91F);
        public static final Note F6s = new Table((byte) 90, "F6#", (byte) 6, Pitch.F, Sharp, 1479.98F);
        public static final Note G6f = new Table((byte) 90, "G6b", (byte) 6, Pitch.G, Flat, 1479.98F);
        public static final Note G6 = new Table((byte) 91, "G6", (byte) 6, Pitch.G, 1567.98F);
        public static final Note G6s = new Table((byte) 92, "G6#", (byte) 6, Pitch.G, Sharp, 1661.22F);
        public static final Note A6f = new Table((byte) 92, "A6b", (byte) 6, Pitch.A, Flat, 1661.22F);

        public static final Note A6 = new Table((byte) 93, "A6", (byte) 6, Pitch.A, 1760.00F);
        public static final Note A6s = new Table((byte) 94, "A6#", (byte) 6, Pitch.A, Sharp, 1864.66F);
        public static final Note B6f = new Table((byte) 94, "B6b", (byte) 6, Pitch.B, Flat, 1864.66F);
        public static final Note B6 = new Table((byte) 95, "B6", (byte) 6, Pitch.B, 1975.53F);
        public static final Note C7 = new Table((byte) 96, "C7", (byte) 7, Pitch.C, 2093.00F);
        public static final Note C7s = new Table((byte) 97, "C7#", (byte) 7, Pitch.C, Sharp, 2217.46F);
        public static final Note D7f = new Table((byte) 97, "D7b", (byte) 7, Pitch.D, Flat, 2217.46F);
        public static final Note D7 = new Table((byte) 98, "D7", (byte) 7, Pitch.D, 2349.32F);
        public static final Note D7s = new Table((byte) 99, "D7#", (byte) 7, Pitch.D, Sharp, 2489.02F);
        public static final Note E7f = new Table((byte) 99, "E7b", (byte) 7, Pitch.E, Flat, 2489.02F);
        public static final Note E7 = new Table((byte) 100, "E7", (byte) 7, Pitch.E, 2637.02F);
        public static final Note F7 = new Table((byte) 101, "F7", (byte) 7, Pitch.F, 2793.83F);
        public static final Note F7s = new Table((byte) 102, "F7#", (byte) 7, Pitch.F, Sharp, 2959.96F);
        public static final Note G7f = new Table((byte) 102, "G7b", (byte) 7, Pitch.G, Flat, 2959.96F);
        public static final Note G7 = new Table((byte) 103, "G7", (byte) 7, Pitch.G, 3135.96F);
        public static final Note G7s = new Table((byte) 104, "G7#", (byte) 7, Pitch.G, Sharp, 3322.44F);
        public static final Note A7f = new Table((byte) 104, "A7b", (byte) 7, Pitch.A, Flat, 3322.44F);

        public static final Note A7 = new Table((byte) 105, "A7", (byte) 7, Pitch.A, 3520.00F);
        public static final Note A7s = new Table((byte) 106, "A7#", (byte) 7, Pitch.A, Sharp, 3729.31F);
        public static final Note B7f = new Table((byte) 106, "B7b", (byte) 7, Pitch.B, Flat, 3729.31F);
        public static final Note B7 = new Table((byte) 107, "B7", (byte) 7, Pitch.B, 3951.07F);
        public static final Note C8 = new Table((byte) 108, "C8", (byte) 8, Pitch.C, 4186.01F);
        public static final Note C8s = new Table((byte) 109, "C8#", (byte) 8, Pitch.C, Sharp, 4434.92F);
        public static final Note D8f = new Table((byte) 109, "D8b", (byte) 8, Pitch.D, Flat, 4434.92F);
        public static final Note D8 = new Table((byte) 110, "D8", (byte) 8, Pitch.D, 4698.64F);
        public static final Note D8s = new Table((byte) 111, "D8#", (byte) 8, Pitch.D, Sharp, 4978.03F);
        public static final Note E8f = new Table((byte) 111, "E8b", (byte) 8, Pitch.E, Flat, 4978.03F);
        public static final Note E8 = new Table((byte) 112, "E8", (byte) 8, Pitch.E, 5274.04F);
        public static final Note F8 = new Table((byte) 113, "F8", (byte) 8, Pitch.F, 5587.65F);
        public static final Note F8s = new Table((byte) 114, "F8#", (byte) 8, Pitch.F, Sharp, 5919.91F);
        public static final Note G8f = new Table((byte) 114, "G8b", (byte) 8, Pitch.G, Flat, 5919.91F);
        public static final Note G8 = new Table((byte) 115, "G8", (byte) 8, Pitch.G, 6271.93F);
        public static final Note G8s = new Table((byte) 116, "G8#", (byte) 8, Pitch.G, Sharp, 6644.88F);
        public static final Note A8f = new Table((byte) 116, "A8b", (byte) 8, Pitch.A, Flat, 6644.88F);

        public static final Note A8 = new Table((byte) 117, "A8", (byte) 8, Pitch.A, 7040.00F);
        public static final Note A8s = new Table((byte) 118, "A8#", (byte) 8, Pitch.A, Sharp, 7458.62F);
        public static final Note B8f = new Table((byte) 118, "B8b", (byte) 8, Pitch.B, Flat, 7458.62F);
        public static final Note B8 = new Table((byte) 119, "B8", (byte) 8, Pitch.B, 7902.13F);
        public static final Note C9 = new Table((byte) 120, "C9", (byte) 9, Pitch.C, 8372.02F);
        public static final Note C9s = new Table((byte) 121, "C9#", (byte) 9, Pitch.C, Sharp, 8869.84F);
        public static final Note D9f = new Table((byte) 121, "D9b", (byte) 9, Pitch.D, Flat, 8869.84F);
        public static final Note D9 = new Table((byte) 122, "D9", (byte) 9, Pitch.D, 9397.27F);
        public static final Note D9s = new Table((byte) 123, "D9#", (byte) 9, Pitch.D, Sharp, 9956.06F);
        public static final Note E9f = new Table((byte) 123, "E9b", (byte) 9, Pitch.E, Flat, 9956.06F);
        public static final Note E9 = new Table((byte) 124, "E9", (byte) 9, Pitch.E, 10548.08F);
        public static final Note F9 = new Table((byte) 125, "F9", (byte) 9, Pitch.F, 11175.30F);
        public static final Note F9s = new Table((byte) 126, "F9#", (byte) 9, Pitch.F, Sharp, 11839.82F);
        public static final Note G9f = new Table((byte) 126, "G9b", (byte) 9, Pitch.G, Flat, 11839.82F);
        public static final Note G9 = new Table((byte) 127, "G9", (byte) 9, Pitch.G, 12543.85F);
        public static final Note G9s = new Table((byte) 128, "G9#", (byte) 9, Pitch.G, Sharp, 13289.75F);
        public static final Note A9f = new Table((byte) 128, "A9b", (byte) 9, Pitch.A, Flat, 13289.75F);

        public static final
        Note[] Order = new Table[] {
            (Table) C_1,
            (Table) C_1s,
            (Table) D_1,
            (Table) D_1s,
            (Table) E_1,
            (Table) F_1,
            (Table) F_1s,
            (Table) G_1,
            (Table) G_1s,

            (Table) A_1,
            (Table) A_1s,
            (Table) B_1,
            (Table) C0,
            (Table) C0s,
            (Table) D0,
            (Table) D0s,
            (Table) E0,
            (Table) F0,
            (Table) F0s,
            (Table) G0,
            (Table) G0s,

            (Table) A0,
            (Table) A0s,
            (Table) B0,
            (Table) C1,
            (Table) C1s,
            (Table) D1,
            (Table) D1s,
            (Table) E1,
            (Table) F1,
            (Table) F1s,
            (Table) G1,
            (Table) G1s,

            (Table) A1,
            (Table) A1s,
            (Table) B1,
            (Table) C2,
            (Table) C2s,
            (Table) D2,
            (Table) D2s,
            (Table) E2,
            (Table) F2,
            (Table) F2s,
            (Table) G2,
            (Table) G2s,

            (Table) A2,
            (Table) A2s,
            (Table) B2,
            (Table) C3,
            (Table) C3s,
            (Table) D3,
            (Table) D3s,
            (Table) E3,
            (Table) F3,
            (Table) F3s,
            (Table) G3,
            (Table) G3s,

            (Table) A3,
            (Table) A3s,
            (Table) B3,
            (Table) C4,
            (Table) C4s,
            (Table) D4,
            (Table) D4s,
            (Table) E4,
            (Table) F4,
            (Table) F4s,
            (Table) G4,
            (Table) G4s,

            (Table) A4,
            (Table) A4s,
            (Table) B4,
            (Table) C5,
            (Table) C5s,
            (Table) D5,
            (Table) D5s,
            (Table) E5,
            (Table) F5,
            (Table) F5s,
            (Table) G5,
            (Table) G5s,

            (Table) A5,
            (Table) A5s,
            (Table) B5,
            (Table) C6,
            (Table) C6s,
            (Table) D6,
            (Table) D6s,
            (Table) E6,
            (Table) F6,
            (Table) F6s,
            (Table) G6,
            (Table) G6s,

            (Table) A6,
            (Table) A6s,
            (Table) B6,
            (Table) C7,
            (Table) C7s,
            (Table) D7,
            (Table) D7s,
            (Table) E7,
            (Table) F7,
            (Table) F7s,
            (Table) G7,
            (Table) G7s,

            (Table) A7,
            (Table) A7s,
            (Table) B7,
            (Table) C8,
            (Table) C8s,
            (Table) D8,
            (Table) D8s,
            (Table) E8,
            (Table) F8,
            (Table) F8s,
            (Table) G8,
            (Table) G8s,

            (Table) A8,
            (Table) A8s,
            (Table) B8,
            (Table) C8,
            (Table) C9s,
            (Table) D9,
            (Table) D9s,
            (Table) E9,
            (Table) F9,
            (Table) F9s,
            (Table) G9,
            (Table) G9s
        };

        public final
        float number;

        private final
        float freq;

        private Table(float number, String symbol, byte octave, Pitch pitch, Accidental accidental, float freq) {
            super(octave, pitch, accidental);
            this.number = number;
            this.freq = freq;
            setSymbol(symbol);
        }

        private Table(float number, String symbol, byte octave, Pitch pitch, float freq) {
            this(number, symbol, octave, pitch, Accidental.Natural, freq);
        }

        @Override
        public float getFrequency() {
            return freq;
        }

        @Override
        public float getNumber() {
            return number;
        }
    }
}