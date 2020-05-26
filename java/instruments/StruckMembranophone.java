package instruments;

/**
 * {@code StruckMembranophone} classifies instruments in which the membrane is set in vibration by being struck.
 */
public abstract
class StruckMembranophone
extends Membranophone
{
    /**
     * {@code Accessory} classifies a struck membranophone accessory.
     */
    protected abstract
    class Accessory
    extends Membranophone.Accessory
    {
        @Override
        public boolean is(final system.Type<Part> type) {
            return type instanceof Accessory;
        }
    }
}