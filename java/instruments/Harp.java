package instruments;

import musical.Note;

/**
 * {@code Harp} classifies the most common form of the harp instrument.
 */
public abstract
class Harp
extends CompositeChordophone
{
    /** The maximum number of strings allowed. */
    private static final
    byte MaxStringCount = (byte) 49;

    public static
    byte getMaxStringCount() {
        return MaxStringCount;
    }

    public abstract
    class Knee
    extends AtomicPart
    {}

    public abstract
    class Lever
    extends AtomicPart
    {}

    public abstract
    class Levers
    extends CompositePart
    {}

    public abstract
    class Neck
    extends AtomicPart
    {}

    public abstract
    class Pedal
    extends MusicalInstrument.Pedal
    {
        protected
        Pedal(
            final java.lang.String name
            ) {
            super(name);
        }
    }

    public abstract
    class Pedals
    extends MusicalInstrument.Pedals
    {}

    public abstract
    class Resonator
    extends AtomicPart
    {}

    public abstract
    class String
    extends CompositeChordophone.String
    {
        public
        String(
            final Note tuning
            ) {
            super(tuning);
        }
    }

    public abstract
    class Strings
    extends CompositePart
    {}

    public abstract
    class Soundboard
    extends AtomicPart
    {}
}