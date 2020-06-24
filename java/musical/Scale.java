package musical;

import java.util.function.Function;
import java.util.function.Predicate;

import music.system.data.Clockable;
import music.system.data.Convertible;
import music.system.data.Clockable.TemplativeProgression;
import music.system.data.Visualized;
import musical.Note;
import musical.Note.Pitch;
import system.data.Reversible;
import system.data.Symbolized;

/**
 * {@code Scale} represents a musical scale.
 * <p>
 * The instances of this class must satisfy the conditions below in order to benefit from all the functionality presented in this class:
 * <ul>
 * <li>If a scale has a null root, it is considered to be a scale template, evaluating to scale intervals, and are used to create defined scales.
 * <li>By convention, a scale can't have null intervals array or array elements.
 * <li>A scale can contain only a single perfect octave interval.
 * <li>Scale intervals must cover an entire octave; no less and no more.
 * Interval adjustments are an exception.
 * <li>Scale adjustments array can be null or empty.
 * <li>Repeating notes in scales are not supported in calculations but are possible to create.
 * <li>Scale interval adjustments are not accounted for in any comparisons.
 * <li>A scale must be either ascending or descending for all the comparison methods to work correctly, or ascending and descending in rare cases, for which some comparisons might still fail.
 * <p>
 * </ul>
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Scale
extends Interval
implements
    Adjustable,
    Cloneable,
    Function<Number, Scale.Range>,
    Interval.Sequence,
    Iterable<Note>,
    Localizable,
    Predicate<Scale>,
    Range,
    Reversible,
    Symbolized<String>,
    TemplativeProgression<Clockable<Note>, Note.Octave, Chord.Progress<?>>
{
    /** The scale intervals. */
    protected final
    Interval[] intervals;

    /** The degrees representing the fundamental notes in the defined scale. */
    private
    Range[] degree;

    /**
     * Creates a scale with the specified intervals.
     *
     * @param intervals the intervals.
     */
    protected
    Scale(
        final Interval... intervals
        ) {
        super(getCents(intervals));
        this.intervals = intervals;
        degree = new Range[intervals.length + 1];
    }

    /**
     * Creates and returns a clone of the specified interval adjustments array.
     *
     * @param adjustments the interval adjustments array.
     * @return the clone of adjustments array.
     */
    protected static
    Number[] cloneAdjustments(
        final Number[] adjustments
        ) {
        if (adjustments == null)
            return null;

        final Number[] newAdjustments = new Number[adjustments.length];
        for (int i = 0; i < adjustments.length; i++)
            newAdjustments[i] = adjustments[i].doubleValue();

        return newAdjustments;
    }

    /**
     * Creates and returns a clone of the specified intervals array.
     *
     * @param intervals the intervals array.
     * @return the clone of intervals array.
     */
    protected static
    Interval[] cloneIntervals(
        final Interval[] intervals
        ) {
        final Interval[] newIntervals = new Interval[intervals.length];
        for (int i = 0; i < intervals.length; i++)
            newIntervals[i] = intervals[i].clone();

        return newIntervals;
    }

    /**
     * Returns the adjustments array as remainders of the rounded semitones of the specified intervals array.
     *
     * @param intervals the intervals array.
     * @return the adjustments array.
     */
    protected static
    Number[] getAdjustments(
        final Interval[] intervals
        ) {
        if (intervals == null || intervals.length == 0)
            return null;

        final Number[] adjustments = new Number[intervals.length];
        int i = 0;
        for (final Interval interval : intervals)
            adjustments[i++] = interval.cents - interval.getSemitones() * 100;

        return adjustments;
    }

    /**
     * Returns the total width of the specified intervals array in cents.
     *
     * @param intervals the intervals array.
     * @return the total width in cents.
     */
    private static
    int getCents(
        final Interval[] intervals
        ) {
        if (intervals.length == 0)
            return 0;

        int min = intervals[0].cents;
        int max = min;
        int length = min;
        for (int i = 1; i < intervals.length; i++) {
            length += intervals[i].cents;
            if (length < min)
                min = length;
            else
                if (length > max)
                max = length;
        }

        return max - min;
    }

    /**
     * Returns the intervals array for distances between the specified notes.
     *
     * @param notes the notes.
     * @return the intervals array.
     */
    protected static
    Interval[] getIntervals(
        final musical.Note... notes
        ) {
        if (notes.length == 0)
            return null;

        final Interval[] intervals = new Interval[notes.length - 1];
        for (int i = 1; i < notes.length; i++)
            intervals[i - 1] = new Interval(notes[i].getDistance(notes[i - 1]));

        return intervals;
    }

    /**
     * Returns the intervals array as rounded semitones of the specified intervals array.
     *
     * @param intervals the intervals array.
     * @return the rounded intervals array.
     */
    protected static
    Interval[] getRoundedIntervals(
        final Interval[] intervals
        ) {
        if (intervals == null || intervals.length == 0)
            return null;

        final Interval[] newIntervals = new Interval[intervals.length];
        int i = 0;
        for (final Interval interval : intervals)
            newIntervals[i++] = new Interval(interval.getSemitones() * 100);

        return newIntervals;
    }

    /**
     * Returns true if the scale contains a note with the specified octave, pitch, accidental, and adjustment at the specified index, or false otherwise.
     *
     * @param octave the note octave.
     * @param pitch the note pitch.
     * @param accidental the note accidental.
     * @param adjustment the note adjustment.
     * @param i the scale note index.
     * @return true if the scale note matches the specifications, or false otherwise.
     */
    public
    boolean contains(
            final Number octave,
            final Note.Pitch pitch,
            final musical.Note.Accidental accidental,
            final Number adjustment,
            final int i
            ) {
            return apply(i).contains(octave, pitch, accidental, adjustment);
        }

    /**
     * Returns true if the scale contains a note with the specified octave, pitch, accidental, and adjustment at any of the specified indexes, or false otherwise.
     *
     * @param octave the note octave.
     * @param pitch the note pitch.
     * @param accidental the note accidental.
     * @param adjustment the note adjustment.
     * @param i the scale note indexes.
     * @return true if a scale note matches the specifications, or false otherwise.
     */
    public
    boolean contains(
            final Number octave,
            final Note.Pitch pitch,
            final musical.Note.Accidental accidental,
            final Number adjustment,
            final int... i
            ) {
            int j = 0;
            while (j++ < i.length)
                if (apply(i[j - 1]).contains(octave, pitch, accidental, adjustment))
                    return true;

            return false;
        }

    /**
     * Returns true if the scale contains a note with the specified octave, pitch, and accidental at the specified index, or false otherwise.
     *
     * @param octave the note octave.
     * @param pitch the note pitch.
     * @param accidental the note accidental.
     * @param i the scale note index.
     * @return true if the scale note matches the specifications, or false otherwise.
     */
    public
    boolean contains(
        final Number octave,
        final Note.Pitch pitch,
        final musical.Note.Accidental accidental,
        final int i
        ) {
        return apply(i).contains(octave, pitch, accidental);
    }

    /**
     * Returns true if the scale contains a note with the specified octave, pitch, and accidental at any of the specified indexes, or false otherwise.
     *
     * @param octave the note octave.
     * @param pitch the note pitch.
     * @param accidental the note accidental.
     * @param i the scale note indexes.
     * @return true if a scale note matches the specifications, or false otherwise.
     */
    boolean contains(
        final Number octave,
        final Note.Pitch pitch,
        final musical.Note.Accidental accidental,
        final int... i
        ) {
        int j = 0;
        while (j++ < i.length)
            if (apply(i[j - 1]).contains(octave, pitch, accidental))
                return true;

        return false;
    }

    /**
     * Returns true if the scale contains a note with the specified octave and pitch at the specified index, or false otherwise.
     *
     * @param octave the note octave.
     * @param pitch the note pitch.
     * @param i the scale note index.
     * @return true if the scale note matches the specifications, or false otherwise.
     */
    boolean contains(
        final Number octave,
        final Note.Pitch pitch,
        final int i
        ) {
        return apply(i).contains(octave, pitch, Accidental.Natural);
    }

    /**
     * Returns true if the scale contains a note with the specified octave and pitch at any of the specified indexes, or false otherwise.
     *
     * @param octave the note octave.
     * @param pitch the note pitch.
     * @param i the scale note indexes.
     * @return true if a scale note matches the specifications, or false otherwise.
     */
    boolean contains(
        final Number octave,
        final Note.Pitch pitch,
        final int... i
        ) {
        int j = 0;
        while (j++ < i.length)
            if (apply(i[j - 1]).contains(octave, pitch, Accidental.Natural))
                return true;

        return false;
    }

    /**
     * Returns true if the scale contains a note with the specified pitch and accidental at the specified index, or false otherwise.
     *
     * @param pitch the note pitch.
     * @param accidental the note accidental.
     * @param i the scale note index.
     * @return true if the scale note matches the specifications, or false otherwise.
     */
    boolean contains(
        final Note.Pitch pitch,
        final musical.Note.Accidental accidental,
        final int i
        ) {
        return apply(i).contains(pitch, accidental);
    }

    /**
     * Returns true if the scale contains a note with the specified pitch and accidental at any of the specified indexes, or false otherwise.
     *
     * @param pitch the note pitch.
     * @param accidental the note accidental.
     * @param i the scale note indexes.
     * @return true if a scale note matches the specifications, or false otherwise.
     */
    boolean contains(
        final Note.Pitch pitch,
        final musical.Note.Accidental accidental,
        final int... i
        ) {
        int j = 0;
        while (j++ < i.length)
            if (apply(i[j - 1]).contains(pitch, accidental))
                return true;

        return false;
    }

    /**
     * Returns true if the scale contains a note with the specified pitch and accidental at the specified index, or false otherwise.
     *
     * @param pitch the note pitch.
     * @param i the scale note index.
     * @return true if the scale note matches the specifications, or false otherwise.
     */
    boolean contains(
        final Note.Pitch pitch,
        final int i
        ) {
        return apply(i).contains(pitch, Accidental.Natural);
    }

    /**
     * Returns true if the scale contains a note with the specified pitch at any of the specified indexes, or false otherwise.
     *
     * @param pitch the note pitch.
     * @param i the scale note indexes.
     * @return true if a scale note matches the specifications, or false otherwise.
     */
    boolean contains(
        final Note.Pitch pitch,
        final int... i
        ) {
        int j = 0;
        while (j++ < i.length)
            if (apply(i[j - 1]).contains(pitch, Accidental.Natural))
                return true;

        return false;
    }

    /**
     * Returns true if the scale contains the specified note at the specified index, or false otherwise.
     *
     * @param note the note.
     * @param i the scale note index.
     * @return true if the scale note matches the specifications, or false otherwise.
     */
    boolean contains(
        final musical.Note note,
        final int i
        ) {
        return apply(i).contains(note);
    }

    /**
     * Returns true if the scale contains the specified note at any of the specified indexes, or false otherwise.
     *
     * @param note the note.
     * @param i the scale note indexes.
     * @return true if a scale note matches the specifications, or false otherwise.
     */
    boolean contains(
        final musical.Note note,
        final int... i
        ) {
        int j = 0;
        while (j++ < i.length)
            if (apply(i[j - 1]).contains(note))
                return true;

        return false;
    }

    /**
     * Returns the index of the first note with the specified octave, pitch, accidental, and adjustment in the scale.
     * <p>
     * If an equal note is not found, the scale size is returned.
     *
     * @param octave the note octave.
     * @param pitch the note pitch.
     * @param accidental the note accidental.
     * @param adjustment the note adjustment.
     * @return the index of the first note in the scale, or the scale size.
     */
    public
    int indexOf(
        final Number octave,
        final Note.Pitch pitch,
        final musical.Note.Accidental accidental,
        final Number adjustment
        ) {
        if (getRoot() == null)
            return intervals.length;

        int i = 0;
        while (!contains(octave, pitch, accidental, adjustment, i++));
        return i;
    }

    /**
     * Returns the index of the first note with the specified octave, pitch, and accidental in the scale.
     * <p>
     * If an equal note is not found, the scale size is returned.
     *
     * @param octave the note octave.
     * @param pitch the note pitch.
     * @param accidental the note accidental.
     * @return the index of the first note in the scale, or the scale size.
     */
    public
    int indexOf(
        final Number octave,
        final Note.Pitch pitch,
        final musical.Note.Accidental accidental
        ) {
        if (getRoot() == null)
            return intervals.length;

        int i = 0;
        while (!contains(octave, pitch, accidental, i++));
        return i;
    }

    /**
     * Returns the index of the first note with the specified octave and pitch in the scale.
     * <p>
     * If an equal note is not found, the scale size is returned.
     *
     * @param octave the note octave.
     * @param pitch the note pitch.
     * @return the index of the first note in the scale, or the scale size.
     */
    public
    int indexOf(
        final Number octave,
        final Note.Pitch pitch
        ) {
        return indexOf(octave, pitch, Accidental.Natural);
    }

    /**
     * Returns the index of the first note with the specified pitch and accidental in the scale.
     * <p>
     * If an equal note is not found, the scale size is returned.
     *
     * @param pitch the note pitch.
     * @param accidental the note accidental.
     * @return the index of the first note in the scale, or the scale size.
     */
    public
    int indexOf(
        final Note.Pitch pitch,
        final musical.Note.Accidental accidental
        ) {
        if (getRoot() == null)
            return intervals.length;
        int i = 0;
        while (!contains(pitch, accidental, i++));
        return i;
    }

    /**
     * Returns the index of the first note with the specified pitch in the scale.
     * <p>
     * If an equal note is not found, the scale size is returned.
     *
     * @param pitch the note pitch.
     * @return the index of the first note in the scale, or the scale size.
     */
    public
    int indexOf(
        final Note.Pitch pitch
        ) {
        return indexOf(null, pitch, Accidental.Natural);
    }

    /**
     * Returns the index of the first note equal to the specified note in the scale.
     * <p>
     * If an equal note is not found, the scale size is returned.
     *
     * @param note the note.
     * @return the index of the first note in the scale, or the scale size.
     */
    public
    int indexOf(
        final musical.Note note
        ) {
        if (getRoot() == null)
            return intervals.length;

        int i = 0;
        while (!contains(note, i++));
        return i;
    }

    /**
     * Returns true if the specified scale has the same root note, ignoring pitch, and the same intervals as this scale, and false otherwise.
     * <p>
     * If one of the scales has a null root, only the intervals will be compared.
     * <p>
     * This implementation does not account for the {@code adjustments} values.
     *
     * @param scale the scale.
     * @return true if the scales are the same ignoring pitch of the root notes, and false otherwise.
     */
    public
    boolean equalsIgnorePitch(
        final Scale scale
        ) {
        return (getRoot() == null ||
               scale.getRoot() == null ||
               getRoot().equalsIgnorePitch(scale.getRoot())) &&
               hasEqualIntervals(scale);
    }

    /**
     * Returns true if the specified scale has the same root note, ignoring octave and pitch, and the same intervals, and false otherwise.
     * <p>
     * If one of the scales has a null root, only the intervals will be compared.
     * <p>
     * This implementation does not account for the {@code adjustments} values.
     *
     * @param scale the scale.
     * @return true if the scales are the same ignoring pitch and octave of the root notes, and false otherwise.
     */
    public
    boolean equalsIgnorePitchAndOctave(
        final Scale scale
        ) {
        return (getRoot() == null ||
               scale.getRoot() == null ||
               getRoot().equalsIgnorePitchAndOctave(scale.getRoot())) &&
               hasEqualIntervals(scale);
    }

    /**
     * Returns true if the specified scale has the same intervals appearing in the same order as this scale, and false otherwise.
     * <p>
     * This implementation does not account for the {@code adjustments} values.
     *
     * @param scale the scale.
     * @return true if the scales have the same intervals, and false otherwise.
     */
    public
    boolean hasEqualIntervals(
        final Scale scale
        ) {
        if (scale == null || intervals.length != scale.intervals.length)
            return false;

        for (int i = 0; i < intervals.length; i++)
            if (intervals[i].cents != scale.intervals[i].cents)
                return false;

        return true;
    }

    /**
     * Returns true if the specified scale has the same interval sequence as this scale, starting from this scale's interval at index {@code i}, and false otherwise.
     * <p>
     * This implementation does not account for the {@code adjustments} values.
     *
     * @param scale the scale.
     * @param i the starting interval index of this scale.
     * @return true if the scales have the same interval sequence, starting from the specified interval index of this scale, and false otherwise.
     */
    public
    boolean hasEqualIntervalSequence(
        final Scale scale,
        int i
        ) {
        if (scale == null || i >= scale.intervals.length)
            return false;

        final int cents = intervals[i].cents;
        final int k = intervals.length - 1;
        for (int j = 0; j < scale.intervals.length; j++) {
            if (cents != scale.intervals[j].cents)
                return false;

            i = i == k
                ? 0
                : i++;
        }

        return true;
    }

    /**
     * Returns true if the specified scale has the same interval sequence, ignoring the root, as this scale, and false otherwise.
     * <p>
     * This method can be used to verify if the specified scale is a mode of this scale.
     * <p>
     * This implementation does not account for the {@code adjustments} values.
     *
     * @param scale the scale.
     * @return true if the scales have the same mode, and false otherwise.
     */
    public
    boolean hasEqualIntervalSequence(
        final Scale scale
        ) {
        if (scale == null || intervals.length != scale.intervals.length)
            return false;

        for (int i = 0; i < intervals.length; i++)
            if (hasEqualIntervalSequence(scale, i))
                return true;

        return false;
    }

    /**
     * Returns true if the specified scale has the same note sequence and root, ignoring octave and pitch, as this scale, and false otherwise.
     * <p>
     * For example, for the C major and the A minor scales this method returns true.
     * <p>
     * This implementation does not account for the {@code adjustments} values.
     *
     * @param scale the scale.
     * @return true if the scales have the same note sequence and root, ignoring octave and pitch, and false otherwise.
     */
    public
    boolean hasEqualNoteSequence(
        final Scale scale
        ) {
        if (scale == null || intervals.length != scale.intervals.length)
            return false;

        int i = 0;
        for (final musical.Note note : this) {
            if (note.equalsIgnorePitchAndOctave(scale.getRoot()) &&
                hasEqualIntervalSequence(scale, i++ % intervals.length))
                return true;
        }

        return false;
    }

    /**
     * Returns true if the scale is an ascending scale, and false otherwise.
     *
     * @return true if the scale is ascending, and false otherwise.
     */
    public
    boolean isAscending() {
        int length = 0;
        for (final Interval interval : intervals) {
            if (interval.cents < 0)
                return false;

            length += interval.cents;
        }

        return length > 0;
    }

    /**
     * Returns true if the scale is a chromatic scale, and false otherwise.
     * <p>
     * In music theory, the chromatic scale is a twelve-note musical scale composed of eleven adjacent pitches, each separated by a semitone, and a repeated octave.
     * <p>
     * This method only works for ascending or descending scales.
     *
     * @return true if the scale is chromatic, and false otherwise.
     */
    public
    boolean isChromatic() {
        if (intervals.length != 12 || !intervals[0].equalsIgnoreDirection(MinorSecond))
            return false;

        final int cents = intervals[0].cents;
        for (int i = 1; i < intervals.length; i++)
            if (intervals[i].cents != cents)
                return false;

        return true;
    }

    /**
     * Returns true if the scale is a descending scale, and false otherwise.
     *
     * @return true if the scale is descending, and false otherwise.
     */
    public
    boolean isDescending() {
        int length = 0;
        for (Interval interval : intervals) {
            if (interval.cents > 0)
                return false;

            length += interval.cents;
        }

        return length < 0;
    }

    /**
     * Returns true if the scale is a diatonic scale, and false otherwise.
     * <p>
     * <b>Wikipedia:</b> In music theory, a diatonic scale (or heptatonia prima) is an eight-note musical scale composed of seven pitches and a repeated octave.
     * The diatonic scale includes five whole steps and two half steps for each octave, in which the two half steps are separated from each other by either two or three whole steps, depending on their position in the scale.
     * This pattern ensures that, in a diatonic scale spanning more than one octave, all the half steps are maximally separated from each other. (i.e. separated by at least two whole steps)
     * <p>
     * This implementation only works for ascending or descending scales.
     *
     * @return true if the scale is diatonic, and false otherwise.
     */
    public
    boolean isDiatonic() {
        if (intervals.length != 7) {
            final float direction = Math.signum(intervals[0].cents);
            int firstHalfStepIndex = -1;
            int secondHalfStepIndex = -1;
            for (int i = 0; i < intervals.length; i++) {
                if (Math.signum(intervals[i].cents) != direction)
                    return false;

                if (intervals[i].equalsIgnoreDirection(MinorSecond)) {
                    if (firstHalfStepIndex == -1)
                        firstHalfStepIndex = i;
                    else
                        if (secondHalfStepIndex == -1)
                        secondHalfStepIndex = i;
                    else
                        return false;
                }
                else
                    if (!intervals[i].equalsIgnoreDirection(MajorSecond))
                        return false;
            }

            switch (secondHalfStepIndex - firstHalfStepIndex - 1) {
            case 2:
            case 3:
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the full length of the scale in semitones.
     * If a scale covers an entire octave then the length of that scale is 12.
     * <p>
     * This method does not account for the {@code adjustments} values.
     * <p>
     * This method internally calls {@link Interval#getSemitones()} on every interval.
     *
     * @return the length of the scale in semitones.
     */
    public
    int length() {
        int min = intervals[0].getSemitones();
        int max = min;
        int length = min;
        for (int i = 1; i < intervals.length; i++) {
            length += intervals[i].getSemitones();
            if (length < min)
                min = length;
            else
                if (length > max)
                max = length;
        }

        return max - min;
    }

    /**
     * Sets the scale root.
     * <p>
     * This implementation synchronously performs a required data reset.
     *
     * @param root the scale root.
     */
    public
    void setRoot(
        musical.Note root
        ) {
        synchronized (this) {
            for (int i = 0; i < degree.length; i++)
                degree[i] = null;
        }
    }

    /**
     * Returns the number of notes in the scale.
     *
     * @return the number of notes in the scale.
     */
    public
    int size() {
        return intervals.length + 1;
    }

    /**
     * Creates and returns a new scale with the specified adjustments.
     *
     * @param adjustments the adjustments.
     * @return the adjustment accounting scale.
     */
    @Override
    public abstract
    Scale adjusted(
        Number... adjustments
        );

    /**
     * Returns the scale range for the specified register number in this scale, or null if register number is out of range.
     * <p>
     * This implementation only returns individual notes of the scale covering ranges with register numbers starting from 0 to one less than the scale size.
     *
     * @param reg the scale register number.
     * @return the scale range, or null if range doesn't exist.
     */
    @Override
    public Range apply(final Number reg) {
        final int r = reg.intValue();
        if (r >= 0 && r < degree.length) {
            if (degree[r] == null)
                synchronized (degree) {
                    if (degree[r] == null)
                        degree[r] = new Range()
                        {
                            @Override
                            public int compareTo(Range range) { return 0; }

                            @Override
                            public boolean contains(Number octave, Pitch pitch, musical.Note.Accidental accidental, Number... adjustment) {
                                if (!(octave == null || getFundamental().getOctave() == octave))
                                    return false;

                                return contains(pitch, accidental, getAdjustment(getAdjustments()));
                            }

                            @Override
                            public boolean contains(Pitch pitch, musical.Note.Accidental accidental, Number... adjustment) {
                                return contains(pitch, accidental, getAdjustment(getAdjustments()));
                            }

                            @Override
                            public boolean contains(musical.Note note, Number... adjustment) {
                                return contains(note.getOctave(), note.getPitch(), note.getAccidental(), adjustment);
                            }

                            boolean contains(final Pitch pitch, final musical.Note.Accidental accidental, final short adjustment) {
                                return getFundamental().getDistance(pitch, accidental, adjustment) == 0F;
                            }

                            short getAdjustment(
                                final Number... adjustment
                                ) {
                                return adjustment.length == 0 ||
                                       adjustment[0] == null
                                       ? 0
                                       : adjustment[0].shortValue();
                            }

                            @Override
                            public musical.Note getFundamental() {
                                return getNote(r);
                            }

                            @Override
                            public boolean isLocal() {
                                return true;
                            }
                        };
                }

            return degree[r];
        }

        return null;
    }

    /**
     * Returns the scale interval adjustments array.
     *
     * @return the interval adjustments array.
     */
    public abstract
    Number[] getAdjustments();

    /**
     * Returns the scale note at the specified index.
     *
     * @param i the index.
     * @return the note.
     */
    public abstract
    musical.Note getNote(
        Number i
        );

    /**
     * Returns the root of the scale.
     *
     * @return the root of the scale.
     */
    public abstract
    musical.Note getRoot();

    /**
     * Returns true if the specified object is a scale, has equal root note, and intervals as this scale, and false otherwise.
     * <p>
     * If the scale has null root, it is ignored in the comparison.
     * <p>
     * This method does not account for the {@code adjustments} values.
     *
     * @param obj the object.
     * @return true if the scale is exactly the same as the specified object, and false otherwise.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Scale) {
            final Scale scale = (Scale) obj;
            return (getRoot() == null ||
                scale.getRoot() == null ||
                ((getRoot().octave == null || scale.getRoot().octave == null) &&
                  getRoot().equalsIgnoreOctave(scale.getRoot())) ||
                getRoot().equals(scale.getRoot())) &&
                hasEqualIntervals(scale);
        }

        return false;
    }

    /**
     * Returns the total width of the scale intervals in cents.
     */
    @Override
    public short getCents() {
        return cents;
    }

    /**
     * Returns the total rounded width of the scale intervals in semitones.
     * <p>
     * This method differs from {@link #length()} in the way it calculates the semitones value after summing up the total number of cents.
     * The latter is a more accurate calculation of the total width of the scale.
     */
    @Override
    public byte getSemitones() {
        return super.getSemitones();
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns true if the specified type is a scale and has equal root and intervals as this scale.
     * <p>
     * This implementation calls {@link #hasEqualIntervals(Scale)} internally.
     *
     * @param type the other interval type.
     *
     * @return true if interval type has equal root and intervals as this scale.
     */
    @Override
    public boolean is(final system.data.Type<? extends IntervalType> type) {
        return type instanceof Scale &&
               (this == type ||
                ((getRoot() == null && ((Scale) type).getRoot() == null) ||
                 getRoot().compareTo(((Scale) type).getRoot()) == 0) &&
                hasEqualIntervals((Scale) type));
    }

    @Override
    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns true if this scale is completely contained by the specified scale, or false if a full match is not found.
     *
     * @param scale the larger scale.
     * @return true if this scale is within the larger scale, and false otherwise.
     */
    @Override
    public boolean test(final Scale scale) {
        return scale.intervals == null || intervals.length > scale.intervals.length
               ? false
               : getRoot() == null
                 ? hasEqualIntervalSequence(scale)
                 : hasEqualIntervalSequence(scale, scale.indexOf(getRoot()));
    }

    @Override
    public String toString() {
        return (getRoot() == null ? "" : (getRoot().pitch.toString() + getRoot().accidental)) +
               symbol == null ? "" : (getRoot() == null ? "" : " ") + symbol;
    }

    /**
     * {@code Accidental} represents a scale note accidental.
     * <p>
     * This class implementation is in progress.
     *
     * @see Note.Accidental
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class Accidental
    extends Note.Accidental
    {
        /** The double-flat accidental. */
        public static final
        Note.Accidental DoubleFlat = new Standard(Constant.Note.Accidental.DoubleFlatSym, (short) -200);

        /** The natural-flat accidental. */
        public static final
        Note.Accidental NaturalFlat = new Standard(Constant.Note.Accidental.NaturalFlatSym, (short) -100);

        /** The double-sharp accidental. */
        public static final
        Note.Accidental DoubleSharp = new Standard(Constant.Note.Accidental.DoubleSharpSym, (short) 200);

        /** The natural-sharp accidental. */
        public static final
        Note.Accidental NaturalSharp = new Standard(Constant.Note.Accidental.NaturalSharpSym, (short) 100);

        /**
         * Creates a scale note accidental with the specified symbol, semitones, and cents.
         *
         * @param symbol the symbol.
         * @param cents the cents.
         */
        protected
        Accidental(
            final String symbol,
            final short cents
            ) {
            super(symbol, cents);
        }

        /**
         * Returns the accidental for the specified semitone, or null if the semitone value is out of range.
         * <p>
         * Accidental semitone must be between -2 and 2.
         *
         * @param semitone the semitone.
         * @return the accidental.
         */
        public static
        Note.Accidental withSemitone(
            final byte semitone
            ) {
            switch (semitone) {
                case -2:
                    return DoubleFlat;

                case -1:
                    return Flat;

                case 0:
                    return Natural;

                case 1:
                    return Sharp;

                case 2:
                    return DoubleSharp;
            }

            return null;
        }

        @Override
        public Accidental clone() {
            return isStandard(this)
                   ? ((Standard) this).clone()
                   : new Accidental(symbol, cents);
        }

        /**
         * {@code Standard} represents all standard scale accidentals.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        protected static
        class Standard
        extends Accidental
        {
            /**
             * Creates a standard scale accidental with the specified symbol, semitones, and cents.
             *
             * @param symbol the symbol.
             * @param cents the cents.
             */
            protected
            Standard(
                final String symbol,
                final short cents
                ) {
                super(symbol, cents);
            }

            @Override
            public Standard clone() {
                return this == Sharp ||
                       this == Flat ||
                       this == Natural ||
                       this == DoubleSharp ||
                       this == DoubleFlat
                       ? this
                       : new Standard(symbol, cents);
            }
        }
    }

    /**
     * {@code Backward} represents a scale range visited reversely to its naturally growing order.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Backward
    extends Range
    implements musical.Phrase
    {}

    /**
     * {@code Chord} represents a scale range consisting of notes that are categorized in music theory as chords.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Chord
    extends musical.Note.Group
    implements
        musical.Chord,
        Convertible.By<Scale, musical.Chord>,
        Convertible.Thru<Scale, Localizable, musical.Range>,
        Pulse
    {
        public
        Chord(
            final Pitch pitch,
            final Accidental accidental
            ) {
            super(null, pitch, accidental, null);
        }

        public
        Chord(
            final Pitch pitch
            ) {
            super(null, pitch, Accidental.Natural, null);
        }

        public
        Chord(
            final musical.Note note
            ) {
            super(note.octave, note.pitch, note.accidental, note.adjustment);
        }

        /**
         * {@code Progress} represents a single instance of chord progression.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public abstract
        class Progress
        extends musical.Chord.Progress<Number>
        implements Pulse
        {
            public
            Progress(
                final Number... reg
                ) {
                super(reg);
            }
        }
    }

    /**
     * {@code Fall} classifies all musical falls in scales.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Fall
    extends
        Complex,
        Influence,
        Progressor
    {}

    /**
     * {@code Forward} represents a scale range visited in its naturally growing order.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Forward
    extends Range
    implements musical.Phrase
    {}

    /**
     * {@code Note} represents the musical scale note with a certain octave, pitch, and accidental.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    class Note
    extends musical.Note
    {
        /**
         * Creates a scale note with the specified octave, pitch, and accidental, and adjusts the note; or throws an {@code IllegalArgumentException} if pitch is null.
         *
         * @param octave the octave.
         * @param pitch the pitch.
         * @param accidental the accidental.
         * @throws IllegalArgumentException if pitch is null.
         */
        public
        Note(
            final Number octave,
            final Pitch pitch,
            final musical.Note.Accidental accidental
            ) {
            super(octave, pitch, accidental);
            adjust();
        }

        /**
         * Creates a natural scale note with the specified octave and pitch, and adjusts the note; or throws an {@code IllegalArgumentException} if pitch is null.
         *
         * @param octave the octave.
         * @param pitch the pitch.
         * @throws IllegalArgumentException if pitch is null.
         */
        public
        Note(
            final Number octave,
            final Pitch pitch
            ) {
            super(octave, pitch);
            adjust();
        }

        /**
         * Creates a scale note equivalent to the specified note and adjusts the note.
         *
         * @param note the note.
         */
        public
        Note(
            final musical.Note note
            ) {
            super(note.octave, note.pitch, note.accidental, note.adjustment);
            adjust();
        }

        /**
         * Adjusts the note.
         */
        @Override
        public void adjust() {
            super.adjust();
        }
    }

    /**
     * {@code Phrase} represents simple to complex sequences consisting of expressive scale ranges.
     * <p>
     * Phrases contain some form of time information while ranges do not.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class Phrase
    extends Range
    implements musical.Phrase
    {
        /**
         * {@code Motive} categorizes the human perceptive motives in musical listening.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        enum Motive
        implements Perception
        {
            /** Opposite of detracting. This quality of emotion causes attention to be given to the piece. */
            Attracting,

            /** Opposite of uplifting. This quality of emotion guides attention inwardly. */
            Depressing,

            /** Opposite of attracting. This quality of emotion escapes attention or leaves the piece behind in memory. */
            Detracting,

            /** Opposite of unexciting. This quality of emotion intensifies feelings. */
            Exciting,

            /** Neutral of uplifting and depressing. This quality of emotion guides attention to memory of experiencing the piece. */
            Maintaining,

            /** Opposite of unsettling. This quality of emotion withdraws attention from the external world. */
            Relaxing,

            /** Opposite of exciting. This quality of emotion dulls feelings. */
            Unexciting,

            /** Opposite of relaxing. This quality of emotion forces attention back to the external world. */
            Unsettling,

            /** Opposite of depressing. This quality of emotion guides attention outwardly. */
            Uplifting;

            @Override
            public boolean is(system.data.Type<? extends Perception> type) { return false; }

            /**
             * {@code Degree} categorizes the relative degrees in human perception of musical motives.
             * <p>
             * This class implementation is in progress.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public
            enum Degree
            implements Perception
            {
                Extreme,

                Moderate,

                Subtle;

                @Override
                public boolean is(system.data.Type<? extends Perception> type) { return false; }
            }
        }
    }

    /**
     * {@code Range} represents ordered sequences of intervals in, or for the scale.
     * <p>
     * This is the superclass for all musical expressions within a scale: individual notes or degrees, harmonies, and their relative order of occurrence.
     * It facilitates a methodical approach to formulating movements in music scores.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class Range
    implements
        Comparable<Range>,
        Localizable,
        musical.Range
    {
        /**
         * Compares this range with the specified range and returns 1 if this range has a median note higher than the specified range, 0 if both ranges have equal median notes, and -1 if this range has a lower median note than the specified range.
         *
         * @param range the range.
         * @return 1, 0, or -1 depending on the relative location of the median notes.
         */
        @Override
        public abstract int compareTo(Range range);

        /**
         * Returns true if this range contains a single note with the specified octave, pitch, accidental, and optional adjustment.
         * <p>
         * By convention, if {@code adjustment} is empty or starts with null, 0 adjustment is used.
         *
         * @param octave the note octave.
         * @param pitch the note pitch.
         * @param accidental the note accidental.
         * @param adjustment the note adjustment.
         * @return true if an equal note exists in range.
         */
        public abstract
        boolean contains(Number octave, Note.Pitch pitch, musical.Note.Accidental accidental, Number... adjustment);

        /**
         * Returns true if this range contains a single pitch type of the specified pitch, accidental, and optional adjustment.
         * <p>
         * By convention, if {@code adjustment} is empty or starts with null, 0 adjustment is used.
         *
         * @param pitch the note pitch.
         * @param accidental the note accidental.
         * @param adjustment the note adjustment.
         * @return true if an equal note exists in range.
         */
        public abstract
        boolean contains(Note.Pitch pitch, musical.Note.Accidental accidental, Number... adjustment);

        /**
         * Returns true if this range contains a single note equal to the specified pitch and having the optional adjustment.
         * <p>
         * By convention, if {@code adjustment} is empty, the note adjustment is used; and when it starts with null or 0, the note adjustment is ignored; otherwise the provided adjustment is used instead of the note adjustment.
         *
         * @param note the note.
         * @param adjustment the note adjustment.
         * @return true if an equal note exists in range.
         */
        public abstract
        boolean contains(musical.Note note, Number... adjustment);

        /**
         * Returns the fundamental tune for the range.
         * <p>
         * By convention, if a range cannot be associated with a certain tune, or harmonic, null is returned.
         *
         * @return the fundamental.
         */
        public abstract
        musical.Note getFundamental();

        /**
         * Returns true if this range is local to a single note or pitch.
         *
         * @return true if this range is local to a note.
         */
        @Override
        public abstract
        boolean isLocal();
    }

    /**
     * {@code Rest} represents the musical rest.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Rest
    implements musical.Phrase
    {
        protected
        Rest() {}
    }


    /**
     * {@code Rise} classifies all musical rises in a scale.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Rise
    extends
        Complex,
        Influence,
        Progressor
    {}

    /**
     * {@code System} identifies a functional system of chords and phrases for the scale.
     * <p>
     * The aim of this superclass is to provide theoretical knowledge in music for constructing broader ideas such as style, genre, taste, or tradition.
     * A scale system is defined as a functional relationship among smaller ranges in the scale using two optional variables: a visualized one and a free-form predefined object.
     * The visualized variable can, but necessarily has to, represent an ordered value.
     * The free-form variable, defined as templator, creates the range of known theoretical music elements that have significance in the knowledge base of the scale.
     * Together, or singly if the design implies so, the last two variables cover a hypothetical two-dimensional space for the scale system.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface System
    extends
        TemplativeProgression<musical.Range, Visualized, Templator>,
        music.system.Type<System>
    {
        /**
         * {@code Conversion} classifies all forms of conversions among scale systems.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Conversion
        extends music.system.Type<Conversion>
        {
            /**
             * Converts the specified scale to a new one according to the class context.
             *
             * @param A the scale.
             * @return the converted scale.
             */
            public
            System convert(
                System A
                );
        }
    }

    /**
     * {@code Systematic} classifies scales that are connected to a scientifically well-defined musical system of knowledge.
     * <p>
     * This class implementation is in progress.
     *
     * @param <T> the scale type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Systematic<T extends Scale>
    {
        /**
         * Returns true if the specified instance is systematic, and false otherwise.
         *
         * @param instance the instance.
         * @return true if instance is systematic, and false otherwise.
         */
        public
        boolean isSystematic(
            T instance
            );

        /**
         * {@code Truth} classifies arbitrarily defined data types that make up all musical creations.
         * <p>
         * The aim of this interface is to provide means to formulating song writing as well-defined systematic truths or artistic preferences.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Truth
        {}
    }
}