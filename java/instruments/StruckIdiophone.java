package instruments;

/**
 * {@code StruckIdiophone} classifies instruments in which the instrument body is set in vibration by being struck.
 */
public abstract
class StruckIdiophone
extends Idiophone
{
    /**
     * {@code Accessory} classifies a struck idiophone accessory.
     */
    protected abstract
    class Accessory
    extends Membranophone /* ? */ .Accessory
    {
        @Override
        public boolean is(final system.Type<Part> type) {
            return type instanceof Accessory;
        }
    }
}