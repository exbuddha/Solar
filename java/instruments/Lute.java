package instruments;

import musical.Note;

/**
 * {@code Lute} classifies musical instruments in which the plane of strings runs parallel with the resonator's surface.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Lute
extends CompositeChordophone
{
    /**
     * {@code String} represents the lute string.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class String
    extends CompositeChordophone.String
    {
        /**
         * Creates a lute string with the specified tuning.
         *
         * @param tuning the tuning.
         */
        public
        String(
            final Note tuning
            ) {
            super(tuning);
        }

        /**
         * {@code FingerSection} represents the lute string's fingering section, mainly located above the fingerboard.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Uniformed(group = String.class)
        public abstract
        class FingerSection
        extends CompositePart
        {}

        /**
         * {@code PickupSection} represents the lute string's pickup section, located above the resonator's surface.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        @Uniformed(group = String.class)
        public abstract
        class PickupSection
        extends CompositePart
        {}
    }
}