package instruments;

import musical.Note;
import musical.Symbolized;
import performance.Instrument;

/**
 * {@code MusicalInstrument} classifies any common musical instrument that can be classified by Hornbostel-Sachs system.
 * <p>
 * Since all classes of musical instrument in Hornbostel-Sachs system include at least one keyboard instrument, this class contains the superclass for all musical instruments played with keyboard.
 */
public abstract
class MusicalInstrument
extends Instrument
{
    /**
     * Creates the keyboard for the musical instrument once.
     */
    public abstract
    void createKeyboard();

    @Override
    public boolean is(final system.Type<Instrument> type) {
        return ! (type instanceof performance.Human);
    }

    /**
     * {@code Accessory} classifies a musical instrument accessory.
     */
    protected abstract
    class Accessory
    extends Instrument.Accessory
    {
        @Override
        public boolean is(final system.Type<Part> type) {
            return type instanceof Accessory;
        }
    }

    /**
     * {@code Bow} represents a musical instrument bow.
     */
    protected abstract
    class Bow
    extends Accessory
    {}

    public abstract
    class Brush
    extends Stick
    {}

    public abstract
    class Damper
    extends Accessory
    implements instruments.accessory.Damper
    {}

    /**
     * {@code Keyboard} classifies the common keyboard for all musical instruments.
     */
    @Uniformed(group = MusicalInstrument.class)
    @Category(types = { Keys.class, Pedals.class })
    protected abstract
    class Keyboard
    extends MusicalInstrument
    {
        /**
         * Creates the keyboard.
         */
        protected
        Keyboard() {
            super();

            createPart(getDefaultConcreteSubclass(Keys.class));
            createPart(getDefaultConcreteSubclass(Pedals.class));
        }
    }

    /**
     * Key classifies a key on the keyboard.
     */
    @Uniformed(group = MusicalInstrument.class)
    protected abstract
    class Key
    extends AtomicPart
    implements
        Colored,
        Tunable
    {
        /** The key tune. */
        private final
        Note tune;

        /** The key color. */
        private
        Color color;

        /**
         * Creates a key with the specified tune and color.
         *
         * @param tune the key tune.
         * @param color the key color.
         */
        protected
        Key(
            final Note tune,
            final Color color
            ) {
            this.tune = tune;
            this.color = color;
        }

        /**
         * Creates a key with the specified tune.
         * The key color is set based on the tuning note's accidental similar to the traditional piano.
         *
         * @param tune the key tune.
         */
        protected
        Key(
            final Note tune
            ) {
            this(tune, tune.getAccidental() == Note.Accidental.Natural ? Color.White : Color.Black);
        }

        /**
         * Returns the key color.
         *
         * @return the key color.
         */
        @Override
        public Color getColor() {
            return color;
        }

        /**
         * Returns the note that the key is tuned to.
         *
         * @return the key note.
         */
        @Override
        public Note getTune() {
            return tune;
        }
    }

    /**
     * {@code Keyboard} classifies instrument parts that are not classified in Hornbostel-Sachs system of musical instruments as an instrument, but is an integral mechanical part of all traditional and modern keyboard instruments that are performed using a set of depressible levers or keys to generate sound.
     * The actual sounding instrument part might be varied or physically separated from the keys, but nevertheless it is the keyboard that controls their action via a mechanical change.
     */
    @Uniformed(group = Keyboard.class)
    @Category
    protected abstract
    class Keys
    extends CompositePart
    implements Universal.ComprehensiveGroup<Key, MusicalInstrument>
    {}

    /**
     * {@code Pedal} represents an instrument pedal.
     */
    @Uniformed(group = MusicalInstrument.class)
    protected abstract
    class Pedal
    extends AtomicPart
    implements Symbolized<String>
    {
        /** The pedal name. */
        protected final
        String name;

        /** The pedal symbol. */
        protected final
        String symbol;

        /**
         * Creates a pedal with the specified name and symbol.
         *
         * @param name the pedal name.
         * @param symbol the pedal symbol.
         */
        public
        Pedal(
            final String name,
            final String symbol
            ) {
            super();
            this.name = name;
            this.symbol = symbol;
        }

        /**
         * Creates a pedal with the specified name.
         *
         * @param name the pedal name.
         */
        protected
        Pedal(
            final String name
            ) {
            this(name, name);
        }

        /**
         * Returns the pedal name.
         *
         * @return the pedal name.
         */
        public
        String getName() {
            return name;
        }

        /**
         * Returns the pedal symbol.
         *
         * @return the pedal symbol.
         */
        @Override
        public String getSymbol() {
            return symbol;
        }

        @Override
        public boolean is(final system.Type<Part> type) {
            return type instanceof Pedal &&
                   ((Pedal) type).getSymbol().equalsIgnoreCase(getSymbol());
        }
    }

    /**
     * {@code Pedals} represents all instrument pedals.
     */
    @Uniformed(group = Keyboard.class)
    @Category
    protected abstract
    class Pedals
    extends PartGroup
    implements Universal.ComprehensiveGroup<Pedal, MusicalInstrument>
    {}

    public abstract
    class Mallet
    extends Accessory
    implements instruments.accessory.Mallet
    {}

    public abstract
    class Rute
    extends Stick
    {}

    public abstract
    class Stick
    extends Mallet
    {}

    /**
     * {@code Tunable} classifies instrument parts that have specific tune or tunes.
     */
    public
    interface Tunable
    extends Part
    {
        /**
         * Returns the note that the part is tuned to.
         *
         * @return the tuning note.
         */
        public
        Note getTune();
    }
}