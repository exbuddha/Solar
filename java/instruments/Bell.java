package instruments;

import musical.Note;

/**
 * {@code Bell} classifies the most common form of the bell instrument where the vibration is weakest near the vertex.
 */
public abstract
class Bell
extends StruckIdiophone
{
    /** The fundamental tuning. */
    protected
    Note fundamental;

    /**
     * Creates a bell with the specified fundamental.
     *
     * @param fundamental the fundamental.
     */
    protected
    Bell(
        final Note fundamental
        ) {
        super();
    }

    /**
     * Creates a default bell tuned to A4.
     */
    public
    Bell() {
        this(Note.Table.A4);
    }

    public abstract
    class Crown
    extends AtomicPart
    {}

    public abstract
    class Clapper
    extends AtomicPart
    {}

    public abstract
    class Lip
    extends AtomicPart
    {}

    public abstract
    class Mallet
    extends Accessory
    {}

    public abstract
    class Mouth
    extends AtomicPart
    {}

    public abstract
    class Shoulder
    extends AtomicPart
    {}

    public abstract
    class SoundBow
    extends AtomicPart
    {}

    public abstract
    class Waist
    extends AtomicPart
    {}
}