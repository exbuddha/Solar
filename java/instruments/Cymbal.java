package instruments;

/**
 * {@code Cymbal} classifies the most common form of the cymbal instrument.
 */
public abstract
class Cymbal
extends StruckIdiophone
{
    private static final
    byte MaxDiameter = (byte) 80;

    private static final
    byte MinDiameter = (byte) 18;

    /**
     * {@code Accessory} classifies a cymbal accessory.
     */
    protected abstract
    class Accessory
    extends StruckIdiophone.Accessory
    {
        @Override
        public boolean is(final system.Type<Part> type) {
            return type instanceof Accessory;
        }
    }

    public abstract
    class Bell
    extends AtomicPart
    {}

    public abstract
    class Bow
    extends CompositePart
    {}

    public abstract
    class Crash
    extends AtomicPart
    {}

    public abstract
    class Edge
    extends AtomicPart
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
    class Ride
    extends AtomicPart
    {}

    public abstract
    class Rim
    extends AtomicPart
    {}

    public abstract
    class Silencer
    extends Muffle
    {}

    public abstract
    class Sizzler
    extends Accessory
    {}
}