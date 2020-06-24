package instruments;

import musical.Note;

/**
 * {@code Flute} classifies the most common form of the flute instrument.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
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

    /**
     * {@code Hole} represents the flute hole.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Hole
    extends AtomicPart
    {}

    /**
     * {@code Key} represents the flute key.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
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