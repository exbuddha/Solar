package musical.instruments;

import musical.Note;

/**
 * {@code Harp} classifies the most common form of the harp instrument.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
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

    /**
     * {@code Knee} represents the harp knee.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Knee
    extends AtomicPart
    {}

    /**
     * {@code Lever} represents the harp lever.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Lever
    extends AtomicPart
    {}

    /**
     * {@code Levers} represents all of the harp levers.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Levers
    extends CompositePart
    {}

    /**
     * {@code Neck} represents the harp neck.
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
     * {@code Pedal} represents the harp pedal.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
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

    /**
     * {@code Pedals} represents all of the harp pedals.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Pedals
    extends MusicalInstrument.Pedals
    {}

    /**
     * {@code Resonator} represents the harp resonator.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Resonator
    extends AtomicPart
    {}

    /**
     * {@code String} represents the harp string.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
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

    /**
     * {@code Strings} represents all of the harp strings.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Strings
    extends CompositePart
    {}

    /**
     * {@code Soundboard} represents the harp soundboard.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Soundboard
    extends AtomicPart
    {}
}