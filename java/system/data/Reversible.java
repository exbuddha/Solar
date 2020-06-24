package system.data;

/**
 * {@code Reversible} classifies data types that can be reversed in-place.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Reversible
{
    /**
     * Reveres the instance.
     */
    void reverse();
}