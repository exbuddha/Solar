package system.data;

/**
 * {@code Invertible} classifies data types that can perform in-place inversions.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Invertible
{
    /**
     * Inverts the instance.
     */
    void invert();
}