package musical.instruments;

import musical.Note;

/**
 * {@code Organ} classifies the most common form of the organ instrument.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Organ
extends Electrophone
implements Keyboard
{
    /**
     * {@code Key} represents the organ key.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Organ.class)
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
     * {@code Keys} represents all of the organ keys.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Organ.class)
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
        @Categorized(parameters = { "firstKeyTune", "numOfKeys" })
        public
        Keys(
            final Note firstKeyTune,
            final Number numOfKeys
            ) {
            super();

            final PartCreator<Key> keyCreator
            = createPart(Key.class)
            .withConstructor(Note.class)
            .withParameterRanges(numOfKeys.intValue());

            final short lowestNoteNumber = (short) firstKeyTune.getNumber();
            for (int semitone = 0; semitone < numOfKeys.intValue(); semitone++)
                keyCreator
                .withValues(new Note(lowestNoteNumber + semitone));
        }
    }

    /**
     * {@code Pedal} represents the organ pedal.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Organ.class)
    protected abstract
    class Pedal
    extends Electrophone.Pedal
    {
        /** The pedal tuning. */
        protected
        Note tuning;

        /**
         * Creates an organ pedal with the specified name and symbol.
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
         * Creates an organ pedal with the specified name.
         *
         * @param name the pedal name.
         */
        protected
        Pedal(
            final String name
            ) {
            super(name);
        }

        /**
         * Returns the pedal tuning.
         *
         * @return the pedal tuning.
         */
        public
        Note getTuning() {
            return tuning;
        }

        /**
         * Sets the pedal tuning.
         *
         * @param tuning the pedal tuning.
         */
        public
        void setTuning(
            final Note tuning
            ) {
            this.tuning = tuning;
        }
    }

    /**
     * {@code Pedalboard} represents the organ pedalboard.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    @Uniformed(group = Organ.class)
    protected abstract
    class Pedalboard
    extends Pedals
    {
        /**
         * Creates a pedalboard.
         *
         * @param firstPedalTune the first note.
         * @param numberOfPedals the number of pedals.
         */
        protected
        Pedalboard(
            final Note firstPedalTune,
            final Number numberOfPedals
            ) {}
    }
}