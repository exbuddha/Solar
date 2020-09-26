package musical.instruments;

import system.Type;

/**
 * {@code StruckIdiophone} classifies instruments in which the instrument body is set in vibration by being struck.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class StruckIdiophone
extends Idiophone
{
    /**
     * {@code Accessory} classifies a struck idiophone accessory.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class Accessory
    extends Idiophone.Accessory
    {
        @Override
        public boolean is(final Type<? extends Part> type) {
            return type instanceof Accessory;
        }
    }
}