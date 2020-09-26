package musical;

/**
 * {@code Localizable} classifies data types that are complex or long, but can be shortened by simplification.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Localizable
{
    /**
     * Returns true if this data can be considered to be local.
     * <p/>
     * This implementation returns false.
     *
     * @return true if the instance is local.
     */
    public default
    boolean isLocal() {
        return false;
    }
}