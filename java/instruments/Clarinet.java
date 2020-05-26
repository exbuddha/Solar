package instruments;

import musical.Note;
import performance.Instrument;

/**
 * {@code Clarinet} classifies the most common form of the clarinet instrument.
 */
public abstract
class Clarinet
extends NonFreeAerophone
{
    public static final
    short DefaultSemitones = (short) 19;

    public static final
    Note MaxLowestNote = Note.Table.B2f;

    public static final
    Note MinLowestNote = Note.Table.E2;

    /**
     * Creates a standard clarinet instrument.
     */
    protected
    Clarinet() {
        super();
    }

    public
    interface Altissimo
    {}

    public abstract
    class Barrel
    extends AtomicPart
    {}

    public abstract
    class Bell
    extends AtomicPart
    {}

    public abstract
    class Bore
    extends CompositePart
    {}

    public
    interface Chalumeau
    {}

    public
    interface Clarion
    {}

    public abstract
    class Hole
    extends AtomicPart
    {
        public abstract
        class Tone
        extends Hole
        {}
    }

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
        class Register
        extends Key
        {}

        public abstract
        class Trill
        extends Key
        {}
    }

    public abstract
    class Ligature
    extends AtomicPart
    {}

    public abstract
    class LowerJoint
    extends PartGroup
    {}

    public abstract
    class MouthPeice
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

    public
    interface Universal
    extends Instrument.Universal
    {
        interface Coronal extends performance.Performer.Universal.Transverse {}

        interface Sagittal extends performance.Performer.Universal.Sagittal {}

        interface Transverse extends performance.Performer.Universal.Coronal {}
    }

    public abstract
    class UpperJoint
    extends PartGroup
    {}
}