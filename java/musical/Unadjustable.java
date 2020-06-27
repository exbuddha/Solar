package musical;

/**
 * {@code Unadjustable} classifies musical data types that can accept in-place unadjustment.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Unadjustable
{
    /**
     * Performs unadjustment.
     */
    void unadjust();
}