package instruments;

/**
 * {@code Drum} classifies all common forms of drum instruments in which sound is produced by a struck membrane.
 */
public abstract
class Drum
extends StruckMembranophone
{
    /**
     * {@code Accessory} classifies the drum accessory.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class Accessory
    extends StruckMembranophone.Accessory
    {
        @Override
        public boolean is(final system.data.Type<? extends Part> type) {
            return type instanceof Accessory;
        }
    }

    /**
     * {@code BassReflex} classifies the drum base reflex.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class BassReflex
    extends AtomicPart
    {}

    /**
     * {@code Center} represents the center of the drum.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Center
    extends AtomicPart
    {}

    /**
     * {@code Edge} represents the edge of the drum.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Edge
    extends AtomicPart
    {}

    /**
     * {@code Head} represents the drum head.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Head
    extends CompositePart
    {}

    /**
     * {@code Heads} represents all of the drum heads.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Heads
    extends PartGroup
    {}

    /**
     * {@code Hole} represents the drum hole.
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
     * {@code Muffle} represents the drum muffle.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Muffle
    extends Accessory
    implements instruments.accessory.Damper
    {}

    /**
     * {@code Perimeter} represents the drum perimeter.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Perimeter
    extends AtomicPart
    {}

    /**
     * {@code Rim} represents the drum rim.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Rim
    extends AtomicPart
    {}

    /**
     * {@code Shell} represents the drum shell.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Shell
    extends CompositePart
    {}

    /**
     * {@code Silencer} represents the drum silencer.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Silencer
    extends Muffle
    {}

    /**
     * {@code Snare} represents the drum snare.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Snare
    extends AtomicPart
    {
        /**
         * {@code Lock} represents the drum snare lock.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public abstract
        class Lock
        extends AtomicPart
        {}
    }
}