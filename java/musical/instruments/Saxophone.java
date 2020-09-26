package musical.instruments;

import musical.Note;

/**
 * {@code Saxophone} classifies the most common form of the saxophone instrument.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
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

    /**
     * {@code Bell} represents the saxophone bell.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Bell
    extends AtomicPart
    {}

    /**
     * {@code Key} represents the saxophone key.
     * <p/>
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

        /**
         * {@code Arm} represents the saxophone arm lever key.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public abstract
        class Arm
        extends Lever
        {}

        /**
         * {@code Bell} represents the saxophone bell key.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public abstract
        class Bell
        extends Key
        {}

        /**
         * {@code Cup} represents the saxophone cup key.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public abstract
        class Cup
        extends Key
        {}

        /**
         * {@code Level} represents the saxophone lever key.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public abstract
        class Lever
        extends Key
        {}

        /**
         * {@code Pivot} represents the saxophone pivot key.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public abstract
        class Pivot
        extends Key
        {}

        /**
         * {@code Stack} represents the saxophone stack key.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public abstract
        class Stack
        extends Key
        {}

        /**
         * {@code Table} represents the saxophone table key.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public abstract
        class Table
        extends Key
        {}
    }

    /**
     * {@code MouthPiece} represents the saxophone mouth piece.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class MouthPeice
    extends AtomicPart
    {}

    /**
     * {@code Neck} represents the saxophone neck.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Neck
    extends AtomicPart
    {}

    /**
     * {@code Pad} represents the saxophone pad.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Pad
    extends AtomicPart
    {}

    /**
     * {@code Reed} represents the saxophone reed.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Reed
    extends AtomicPart
    {}
}