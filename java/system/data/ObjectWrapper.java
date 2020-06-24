package system.data;

/**
 * {@code ObjectWrapper} classifies wrappers around objects.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface ObjectWrapper
{
    Object object();

    Class<?> objectType();
}