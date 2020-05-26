package instruments;

/**
 * {@code Drum} classifies all common forms of drum instruments in which sound is produced by a struck membrane.
 */
public abstract
class Drum
extends StruckMembranophone
{
    /**
     * {@code Accessory} classifies a drum accessory.
     */
    protected abstract
    class Accessory
    extends StruckMembranophone.Accessory
    {
        @Override
        public boolean is(final system.Type<Part> type) {
            return type instanceof Accessory;
        }
    }

    public abstract
    class BassReflex
    extends AtomicPart
    {}

    public abstract
    class Center
    extends AtomicPart
    {}

    public abstract
    class Edge
    extends AtomicPart
    {}

    public abstract
    class Head
    extends CompositePart
    {}

    public abstract
    class Heads
    extends PartGroup
    {}

    public abstract
    class Hole
    extends AtomicPart
    {}

    public abstract
    class Muffle
    extends Accessory
    implements instruments.accessory.Damper
    {}

    public abstract
    class Perimeter
    extends AtomicPart
    {}

    public abstract
    class Rim
    extends AtomicPart
    {}

    public abstract
    class Shell
    extends CompositePart
    {}

    public abstract
    class Silencer
    extends Muffle
    {}

    public abstract
    class Snare
    extends AtomicPart
    {
        public abstract
        class Lock
        extends AtomicPart
        {}
    }
}