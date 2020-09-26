package musical.instruments;

import system.Type;

/**
 * {@code StruckMembranophone} classifies instruments in which the membrane is set in vibration by being struck.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class StruckMembranophone
extends Membranophone
{
    /**
     * {@code Accessory} classifies a struck membranophone accessory.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class Accessory
    extends Membranophone.Accessory
    {
        @Override
        public boolean is(final Type<? extends Part> type) {
            return type instanceof Accessory;
        }
    }
}