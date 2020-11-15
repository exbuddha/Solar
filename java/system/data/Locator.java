package system.data;

import java.util.Iterator;

/**
 * {@code Locator} classifies all element locators in the system.
 * <p/>
 * Locators differ from finders in the way that they do not iterate the elements themselves; and therefore are not appropriate for the use within for loops in the traditional way.
 * The appropriate way to use locators is within while loops:
 * <p/>
 * <pre><code>
 * while (locator.hasNext()) {
 *     if (locator.next()) {
 *         T element = locator.element();
 *         ...
 *     }
 * }
 * </code></pre>
 * Instead, locators iterate through every element match condition and return a true or false value indicating whether the current element satisfies the match conditions or not.
 * As a result of this difference, locators allow enumeration of all the elements in their container type; whereas finders only allow enumeration of the found elements.
 * <p/>
 * Locators are not concurrent objects.
 * If the underlying data structure is changed after creation, the results can be inaccurate or erroneous.
 *
 * @param <T> the type of elements returned by this locator.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Locator<T>
extends
    Finding<T>,
    Iterator<Boolean>
{
    /**
     * Advances the iteration until the next element is found and returns this locator.
     *
     * @return the advanced locator.
     */
    @Override
    Locator<T> found();
}