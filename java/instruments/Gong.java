package instruments;

/**
 * {@code Gong} classifies the most common form of the gong instrument where the vibration is strongest near the vertex.
 */
public abstract
class Gong
extends StruckIdiophone
{
    private static final
    byte MaxDiameter = (byte) 150;

    private static final
    byte MinDiameter = (byte) 50;

    public abstract
    class Center
    extends AtomicPart
    {}

    public abstract
    class Edge
    extends AtomicPart
    {}

    public abstract
    class Perimeter
    extends AtomicPart
    {}

    public abstract
    class Rim
    extends AtomicPart
    {}
}