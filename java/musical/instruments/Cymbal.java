package musical.instruments;

import system.Type;

/**
 * {@code Cymbal} represents the most common form of the cymbal instrument.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
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
     * {@code Accessory} represents the cymbal accessory.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class Accessory
    extends StruckIdiophone.Accessory
    {
        @Override
        public boolean is(final Type<? super Part> type) {
            return type instanceof Accessory;
        }
    }

    /**
     * {@code Bell} represents the cymbal bell.
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
     * {@code Bow} represents the cymbal bow.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Bow
    extends CompositePart
    {}

    /**
     * {@code Crash} represents the crash cymbal.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Crash
    extends AtomicPart
    {}

    /**
     * {@code Edge} represents the edge of the cymbal.
     * <p/>
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
     * {@code Hole} represents the cymbal hole.
     * <p/>
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
     * {@code Muffle} represents the cymbal muffle.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Muffle
    extends Accessory
    implements musical.instruments.accessory.Damper
    {}

    /**
     * {@code Ride} represents the ride cymbal.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Ride
    extends AtomicPart
    {}

    /**
     * {@code Rim} represents the cymbal rim.
     * <p/>
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
     * {@code Silencer} represents the cymbal silencer.
     * <p/>
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
     * {@code Sizzler} represents the cymbal sizzler.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Sizzler
    extends Accessory
    {}
}