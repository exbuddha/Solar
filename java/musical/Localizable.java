package musical;

/**
 * {@code Localizable} classifies data types that are complex or long, but can be shortened by simplification.
 */
public
interface Localizable
{
    /**
     * Returns true if this data can be considered to be local.
     * <p>
     * This implementation returns false.
     */
    public default
    boolean isLocal() {
        return false;
    }
}