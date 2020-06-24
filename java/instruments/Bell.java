package instruments;

import musical.Note;

/**
 * {@code Bell} represents the most common form of the bell instrument where the vibration is weakest near the vertex.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
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
        this(Note.A4);
    }

    /**
     * {@code Crown} represents the bell crown.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Crown
    extends AtomicPart
    {}

    /**
     * {@code Clapper} represents the bell clapper.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Clapper
    extends AtomicPart
    {}

    /**
     * {@code Lip} represents the bell lip.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Lip
    extends AtomicPart
    {}

    /**
     * {@code Mallet} represents the bell mallet.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Mallet
    extends Accessory
    {}

    /**
     * {@code Mouth} represents the bell mouth.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Mouth
    extends AtomicPart
    {}

    /**
     * {@code Shoulder} represents the bell shoulder.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Shoulder
    extends AtomicPart
    {}

    /**
     * {@code SoundBow} represents the bell sound bow.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class SoundBow
    extends AtomicPart
    {}

    /**
     * {@code Waist} represents the bell waist.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Waist
    extends AtomicPart
    {}
}