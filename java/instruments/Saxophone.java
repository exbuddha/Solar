package instruments;

import musical.Note;

/**
 * {@code Saxophone} classifies the most common form of the saxophone instrument.
 */
public abstract
class Saxophone
extends NonFreeAerophone
{
    /**
     * Creates a standard saxophone instrument.
     */
    protected
    Saxophone() {
        super();
    }

    public abstract
    class Bell
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

        public abstract
        class Arm
        extends Lever
        {}

        public abstract
        class Bell
        extends Key
        {}

        public abstract
        class Cup
        extends Key
        {}

        public abstract
        class Lever
        extends Key
        {}

        public abstract
        class Pivot
        extends Key
        {}

        public abstract
        class Stack
        extends Key
        {}

        public abstract
        class Table
        extends Key
        {}
    }

    public abstract
    class MouthPeice
    extends AtomicPart
    {}

    public abstract
    class Neck
    extends AtomicPart
    {}

    public abstract
    class Pad
    extends AtomicPart
    {}

    public abstract
    class Reed
    extends AtomicPart
    {}
}