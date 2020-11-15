package system.data;

import java.util.Iterator;

/**
 * {@code Finder} classifies all element finders in the system.
 * <p/>
 * Finders differ from locators in the way that they iterate the elements themselves; and therefore are more appropriate for the use within for loops:
 * <p/>
 * <pre><code>
 * for (T element : finder) { ... }
 * </code></pre>
 * Finders are not concurrent objects.
 * If the underlying data structure is changed after creation, the results can be inaccurate or erroneous.
 *
 * @param <T> the type of elements returned by this finder.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Finder<T>
extends
    Finding<T>,
    Iterator<T>
{
    /**
     * Advances the iteration until the next element is found and returns this finder.
     *
     * @return the advanced finder.
     */
    @Override
    Finder<T> found();
}