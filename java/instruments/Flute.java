package instruments;

import musical.Note;

/**
 * {@code Flute} classifies the most common form of the flute instrument.
 */
public abstract
class Flute
extends NonFreeAerophone
{
    /**
     * Creates a standard flute instrument.
     */
    protected
    Flute() {
        super();
    }

    public abstract
    class Hole
    extends AtomicPart
    {}

    public abstract
    class Key
    extends MusicalInstrument.Key
    {
        protected
        Key(
            final Note tune
            ) {
            super(tune);
        }

        protected
        Key() {
            super(null);
        }
    }
}