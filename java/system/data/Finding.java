package system.data;

import java.util.NoSuchElementException;

/**
 * {@code Finding} classifies all data types in the system that find elements within element container types.
 *
 * @param <T> the type of elements returned by this finding type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Finding<T>
{
    /**
     * Returns the element if found; otherwise throws a {@code NoSuchElementException}.
     *
     * @return the found element.
     *
     * @throws NoSuchElementException if the element is not found or the iteration has no more elements.
     */
    T element();

    /**
     * Returns the element if found; otherwise returns the specified fallback.
     *
     * @param fallback the fallback.
     *
     * @return the found element or the fallback.
     */
    T element(
        T fallback
        );

    /**
     * Returns true if the element is found; otherwise returns false.
     *
     * @return true if the element is found, and false otherwise.
     */
    boolean elementFound();

    /**
     * Advances the iteration until the next element is found and returns this finding type.
     *
     * @return the advanced finding type.
     */
    Finding<T> found();

    /**
     * Returns the index of the element if found; otherwise throws a {@code NoSuchElementException}.
     *
     * @return the found element index.
     *
     * @throws NoSuchElementException if the element is not found or the iteration has no more elements.
     */
    Integer index();

    /**
     * Returns the index of the element if found; otherwise returns the specified fallback integer.
     *
     * @param fallback the fallback.
     *
     * @return the found element index or the fallback.
     */
    Integer index(
        Integer fallback
        );
}