package instruments;

import musical.Note;
import performance.Instrument;
import performance.Performer;

/**
 * {@code Clarinet} classifies the most common form of the clarinet instrument.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Clarinet
extends NonFreeAerophone
{
    public static final
    short DefaultSemitones = (short) 19;

    public static final
    Note MaxLowestNote = Note.B2f;

    public static final
    Note MinLowestNote = Note.E2;

    /**
     * Creates a standard clarinet instrument.
     */
    protected
    Clarinet() {
        super();
    }

    /**
     * {@code Altissimo} classifies the altissimo clarinet.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Altissimo
    {}

    /**
     * {@code Barrel} represents the clarinet barrel.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Barrel
    extends AtomicPart
    {}

    /**
     * {@code Bell} represents the clarinet bell.
     * <p>
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
     * {@code Bore} represents the clarinet bore.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Bore
    extends CompositePart
    {}

    /**
     * {@code Chalumeau} classifies the chalumeau clarinet.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Chalumeau
    {}

    /**
     * {@code Clarion} classifies the clarion clarinet.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Clarion
    {}

    /**
     * {@code Hole} represents the clarinet hole.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Hole
    extends AtomicPart
    {
        /**
         * {@code Tone} represents the clarinet tone hole.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public abstract
        class Tone
        extends Hole
        {}
    }

    /**
     * {@code Key} represents the clarinet key.
     * <p>
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
         * {@code Register} represents the clarinet register key.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public abstract
        class Register
        extends Key
        {}

        /**
         * {@code Trill} represents the clarinet trill key.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public abstract
        class Trill
        extends Key
        {}
    }

    /**
     * {@code Ligature} represents the clarinet ligature.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Ligature
    extends AtomicPart
    {}

    /**
     * {@code LowerJoint} represents the clarinet lower joint.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class LowerJoint
    extends PartGroup
    {}

    /**
     * {@code MouthPiece} represents the clarinet mouth piece.
     * <p>
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
     * {@code Pad} represents the clarinet pad.
     * <p>
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
     * {@code Reed} represents the clarinet reed.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Reed
    extends AtomicPart
    {}

    /**
     * {@code Universal} identifies the clarinet conceptual axes.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Universal
    extends Instrument.Universal
    {
        /**
         * {@code Coronal} classifies the coronal axis of the clarinet.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Coronal
        extends Performer.Universal.Transverse
        {}

        /**
         * {@code Sagittal} classifies the sagittal axis of the clarinet.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Sagittal
        extends Performer.Universal.Sagittal
        {}

        /**
         * {@code Transverse} classifies the transverse axis of the clarinet.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Transverse
        extends Performer.Universal.Coronal
        {}
    }

    /**
     * {@code UpperJoint} represents the clarinet upper joint.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class UpperJoint
    extends PartGroup
    {}
}