package system.data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * {@code Lambda} defines static members for performing common mathematical or algorithmic operations.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public final
class Lambda
{
    /**
     * Returns true if the two specified objects are mutually non-null and not equal; and false otherwise.
     *
     * @param a the first object.
     * @param b the second object.
     *
     * @return true if the two objects are non-null and non-equal, and false otherwise.
     */
    public static
    boolean areNonNullAndNonEqual(
        final Object a,
        final Object b
        ) {
        return (a != null && !a.equals(b)) ||
               (a == null && b != null);
    }

    /**
     * Returns true if the two specified objects are either mutually null or equal; and false otherwise.
     *
     * @param a the first object.
     * @param b the second object.
     *
     * @return true if the two objects are either null or equal, and false otherwise.
     */
    public static
    boolean areNullOrEqual(
        final Object a,
        final Object b
        ) {
        return (a == null && b == null) ||
               (a != null && a.equals(b)) ||
               (b != null && b.equals(a));
    }

    /**
     * Combines the specified arrays into one array.
     *
     * @param arrays the arrays.
     *
     * @param type the combined array class type.
     * @param <T> the array type.
     *
     * @return the combined array.
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T>
    T[] combineArrays(
        final Class<T[]> type,
        final T[]... arrays
        ) {
        int length = 0;
        for (final T[] array : arrays)
            length += array.length;

        final T[] combo = (T[]) Array.newInstance(type, length);
        int j = 0;
        for (final T[] array : arrays)
            for (final T t : array)
                combo[j++] = t;

        return combo;
    }

    /**
     * Returns the factorial of the number.
     * <p/>
     * This implementation calls {@link Number#intValue()} on the number.
     *
     * @param n the number.
     * @return the factorial.
     */
    public static
    int factorial(
        final Number n
        ) {
        final int m = n.intValue();
        int factorial = 1;
        if (m > 1)
            for (long k = 2; k <= m; k++)
                factorial *= k;

        return factorial;
    }

    /**
     * Returns the first occurrence of the specified item in the array using the specified comparator; or null if item is not found, the array is null, or the comparator is null.
     * <p/>
     * This implementation calls {@link Comparator#compare(Object, Object)} with the item as the first argument and the array elements as second arguments of that method.
     *
     * @param <T> the item, array, and comparator data type.
     * @param item the item.
     * @param array the array.
     * @param comparator the comparator.
     *
     * @return the array element, or null if not found.
     */
    public static <T>
    T findFirst(
        final T item,
        final T[] array,
        final Comparator<? super T> comparator
        ) {
        return array == null
               ? null
               : SequentialFinder.forward(item, array, comparator).element(null);
    }

    /**
     * Returns the first occurrence of the specified item in the array; or null if item is not found or the array is null.
     * <p/>
     * This implementation calls {@link Comparable#compareTo(Object)} on the item and passes each array element as the argument of that method.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param array the array.
     *
     * @return the array element, or null if not found.
     */
    public static <T extends Comparable<? super T>>
    T findFirst(
        final T item,
        final T[] array
        ) {
        return array == null
               ? null
               : SequentialComparableFinder.forward(item, array).element(null);
    }

    /**
     * Returns the first occurrence of element in the array using the specified function as comparator; or null if element is not found, the array is null, or the function is null.
     *
     * @param <T> the array type.
     * @param array the array.
     * @param comparator the function.
     *
     * @return the array element, or null if not found.
     */
    public static <T>
    T findFirst(
        final T[] array,
        final Function<Object, Boolean> comparator
        ) {
        return array == null || comparator == null
               ? null
               : SequentialFinder.forward(null, array, (final T n, final T element) -> comparator.apply(element) ? 0 : 1).element(null);
    }

    /**
     * Returns index of the first occurrence of the specified item in the array using the specified comparator; or null if item is not found, the array is null or the comparator is null.
     * <p/>
     * This implementation calls {@link Comparator#compare(Object, Object)} with the item as the first argument and the array elements as second arguments of that method.
     *
     * @param <T> the item, array, and comparator data type.
     * @param item the item.
     * @param array the array.
     * @param comparator the comparator.
     *
     * @return the array element index, or null if not found.
     */
    public static <T>
    Integer findFirstIndex(
        final T item,
        final T[] array,
        final Comparator<? super T> comparator
        ) {
        return array == null
               ? null
               : SequentialFinder.forward(item, array, comparator).index(null);
    }

    /**
     * Returns index of the first occurrence of the specified item in the array; or null if item is not found or the array is null.
     * <p/>
     * This implementation calls {@link Comparable#compareTo(Object)} on the item and passes each array element as the argument of that method.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param array the array.
     *
     * @return the array element index, or null if not found.
     */
    public static <T extends Comparable<? super T>>
    Integer findFirstIndex(
        final T item,
        final T[] array
        ) {
        return array == null
               ? null
               : SequentialComparableFinder.forward(item, array).index(null);
    }

    /**
     * Returns index of the first occurrence of element in the array using the specified function as comparator; or null if element is not found, the array is null, or the function is null.
     *
     * @param <T> the array type.
     * @param array the array.
     * @param comparator the function.
     *
     * @return the array element, or null if not found.
     */
    public static <T>
    Integer findFirstIndex(
        final T[] array,
        final Function<Object, Boolean> comparator
        ) {
        return array == null || comparator == null
               ? null
               : SequentialFinder.forward(null, array, (final T n, final T element) -> comparator.apply(element) ? 0 : 1).index(null);
    }

    /**
     * Returns the last occurrence of the specified item in the array using the specified comparator; or null if item is not found, the array is null, or the comparator is null.
     * <p/>
     * This implementation calls {@link Comparator#compare(Object, Object)} with the item as the first argument and the array elements as second arguments of that method.
     *
     * @param <T> the item, array, and comparator data type.
     * @param item the item.
     * @param array the array.
     * @param comparator the comparator.
     *
     * @return the array element, or null if not found.
     */
    public static <T>
    T findLast(
        final T item,
        final T[] array,
        final Comparator<? super T> comparator
        ) {
        return array == null
               ? null
               : SequentialFinder.backward(item, array, comparator).element(null);
    }

    /**
     * Returns the last occurrence of the specified item in the array; or null if item is not found or the array is null.
     * <p/>
     * This implementation calls {@link Comparable#compareTo(Object)} on the item and passes each array element as the argument of that method.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param array the array.
     *
     * @return the array element, or null if not found.
     */
    public static <T extends Comparable<? super T>>
    T findLast(
        final T item,
        final T[] array
        ) {
        return array == null
               ? null
               : SequentialComparableFinder.backward(item, array).element(null);
    }

    /**
     * Returns the last occurrence of element in the array using the specified function as comparator; or null if element is not found, the array is null, or the function is null.
     *
     * @param <T> the array type.
     * @param array the array.
     * @param comparator the function.
     *
     * @return the array element, or null if not found.
     */
    public static <T>
    T findLast(
        final T[] array,
        final Function<Object, Boolean> comparator
        ) {
        return array == null || comparator == null
               ? null
               : SequentialFinder.backward(null, array, (final T n, final T element) -> comparator.apply(element) ? 0 : 1).element(null);
    }

    /**
     * Returns index of the last occurrence of the specified item in the array using the specified comparator; or null if item is not found, the array is null or the comparator is null.
     * <p/>
     * This implementation calls {@link Comparator#compare(Object, Object)} with the item as the first argument and the array elements as second arguments of that method.
     *
     * @param <T> the item, array, and comparator data type.
     * @param item the item.
     * @param array the array.
     * @param comparator the comparator.
     *
     * @return the array element index, or null if not found.
     */
    public static <T>
    Integer findLastIndex(
        final T item,
        final T[] array,
        final Comparator<? super T> comparator
        ) {
        return array == null
               ? null
               : SequentialFinder.backward(item, array, comparator).index(null);
    }

    /**
     * Returns index of the last occurrence of the specified item in the array; or null if item is not found or the array is null.
     * <p/>
     * This implementation calls {@link Comparable#compareTo(Object)} on the item and passes each array element as the argument of that method.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param array the array.
     *
     * @return the array element index, or null if not found.
     */
    public static <T extends Comparable<? super T>>
    Integer findLastIndex(
        final T item,
        final T[] array
        ) {
        return array == null
               ? null
               : SequentialComparableFinder.backward(item, array).index(null);
    }

    /**
     * Returns index of the last occurrence of element in the array using the specified function as comparator; or null if element is not found, the array is null, or the function is null.
     *
     * @param <T> the array type.
     * @param array the array.
     * @param comparator the function.
     *
     * @return the array element, or null if not found.
     */
    public static <T>
    Integer findLastIndex(
        final T[] array,
        final Function<Object, Boolean> comparator
        ) {
        return array == null || comparator == null
               ? null
               : SequentialFinder.backward(null, array, (final T n, final T element) -> comparator.apply(element) ? 0 : 1).index(null);
    }

    /**
     * Returns the greatest common denominator of the two non-negative integers.
     *
     * @param a the first number.
     * @param b the second number.
     *
     * @return the greatest common denominator of a and b.
     */
    public static
    int gcd(
        final int a,
        final int b
        ) {
        if (b == 0)
            return a;
        else
            return gcd(b, a % b);
    }

    /**
     * Returns the first occurrence of the specified item in the array using the specified comparator; or null if item is not found, the array is null, or the comparator is null.
     * <p/>
     * This implementation calls {@link Comparator#compare(Object, Object)} with the item as the first argument and the array elements as second arguments of that method.
     *
     * @param <T> the item, array, and comparator data type.
     * @param item the item.
     * @param array the array.
     * @param comparator the comparator.
     *
     * @return the array element, or null if not found.
     */
    public static <T>
    T locateFirst(
        final T item,
        final T[] array,
        final Comparator<? super T> comparator
        ) {
        return array == null
               ? null
               : SequentialLocator.forward(item, array, comparator).element(null);
    }

    /**
     * Returns the first occurrence of the specified item in the array; or null if item is not found or the array is null.
     * <p/>
     * This implementation calls {@link Comparable#compareTo(Object)} on the item and passes each array element as the argument of that method.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param array the array.
     *
     * @return the array element, or null if not found.
     */
    public static <T extends Comparable<? super T>>
    T locateFirst(
        final T item,
        final T[] array
        ) {
        return array == null
               ? null
               : SequentialComparableLocator.forward(item, array).element(null);
    }

    /**
     * Returns the first occurrence of element in the array using the specified function as comparator; or null if element is not found, the array is null, or the function is null.
     *
     * @param <T> the array type.
     * @param array the array.
     * @param comparator the function.
     *
     * @return the array element, or null if not found.
     */
    public static <T>
    T locateFirst(
        final T[] array,
        final Function<Object, Boolean> comparator
        ) {
        return array == null || comparator == null
               ? null
               : SequentialLocator.forward(null, array, (final T n, final T element) -> comparator.apply(element) ? 0 : 1).element(null);
    }

    /**
     * Returns index of the first occurrence of the specified item in the array using the specified comparator; or null if item is not found, the array is null or the comparator is null.
     * <p/>
     * This implementation calls {@link Comparator#compare(Object, Object)} with the item as the first argument and the array elements as second arguments of that method.
     *
     * @param <T> the item, array, and comparator data type.
     * @param item the item.
     * @param array the array.
     * @param comparator the comparator.
     *
     * @return the array element index, or null if not found.
     */
    public static <T>
    Integer locateFirstIndex(
        final T item,
        final T[] array,
        final Comparator<? super T> comparator
        ) {
        return array == null
               ? null
               : SequentialLocator.forward(item, array, comparator).index(null);
    }

    /**
     * Returns index of the first occurrence of the specified item in the array; or null if item is not found or the array is null.
     * <p/>
     * This implementation calls {@link Comparable#compareTo(Object)} on the item and passes each array element as the argument of that method.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param array the array.
     *
     * @return the array element index, or null if not found.
     */
    public static <T extends Comparable<? super T>>
    Integer locateFirstIndex(
        final T item,
        final T[] array
        ) {
        return array == null
               ? null
               : SequentialComparableLocator.forward(item, array).index(null);
    }

    /**
     * Returns index of the first occurrence of element in the array using the specified function as comparator; or null if element is not found, the array is null, or the function is null.
     *
     * @param <T> the array type.
     * @param array the array.
     * @param comparator the function.
     *
     * @return the array element, or null if not found.
     */
    public static <T>
    Integer locateFirstIndex(
        final T[] array,
        final Function<Object, Boolean> comparator
        ) {
        return array == null || comparator == null
               ? null
               : SequentialLocator.forward(null, array, (final T n, final T element) -> comparator.apply(element) ? 0 : 1).index(null);
    }

    /**
     * Returns the last occurrence of the specified item in the array using the specified comparator; or null if item is not found, the array is null, or the comparator is null.
     * <p/>
     * This implementation calls {@link Comparator#compare(Object, Object)} with the item as the first argument and the array elements as second arguments of that method.
     *
     * @param <T> the item, array, and comparator data type.
     * @param item the item.
     * @param array the array.
     * @param comparator the comparator.
     *
     * @return the array element, or null if not found.
     */
    public static <T>
    T locateLast(
        final T item,
        final T[] array,
        final Comparator<? super T> comparator
        ) {
        return array == null
               ? null
               : SequentialLocator.backward(item, array, comparator).element(null);
    }

    /**
     * Returns the last occurrence of the specified item in the array; or null if item is not found or the array is null.
     * <p/>
     * This implementation calls {@link Comparable#compareTo(Object)} on the item and passes each array element as the argument of that method.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param array the array.
     *
     * @return the array element, or null if not found.
     */
    public static <T extends Comparable<? super T>>
    T locateLast(
        final T item,
        final T[] array
        ) {
        return array == null
               ? null
               : SequentialComparableLocator.backward(item, array).element(null);
    }

    /**
     * Returns the last occurrence of element in the array using the specified function as comparator; or null if element is not found, the array is null, or the function is null.
     *
     * @param <T> the array type.
     * @param array the array.
     * @param comparator the function.
     *
     * @return the array element, or null if not found.
     */
    public static <T>
    T locateLast(
        final T[] array,
        final Function<Object, Boolean> comparator
        ) {
        return array == null || comparator == null
               ? null
               : SequentialLocator.backward(null, array, (final T n, final T element) -> comparator.apply(element) ? 0 : 1).element(null);
    }

    /**
     * Returns index of the last occurrence of the specified item in the array using the specified comparator; or null if item is not found, the array is null or the comparator is null.
     * <p/>
     * This implementation calls {@link Comparator#compare(Object, Object)} with the item as the first argument and the array elements as second arguments of that method.
     *
     * @param <T> the item, array, and comparator data type.
     * @param item the item.
     * @param array the array.
     * @param comparator the comparator.
     *
     * @return the array element index, or null if not found.
     */
    public static <T>
    Integer locateLastIndex(
        final T item,
        final T[] array,
        final Comparator<? super T> comparator
        ) {
        return array == null
               ? null
               : SequentialLocator.backward(item, array, comparator).index(null);
    }

    /**
     * Returns index of the last occurrence of the specified item in the array; or null if item is not found or the array is null.
     * <p/>
     * This implementation calls {@link Comparable#compareTo(Object)} on the item and passes each array element as the argument of that method.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param array the array.
     *
     * @return the array element index, or null if not found.
     */
    public static <T extends Comparable<? super T>>
    Integer locateLastIndex(
        final T item,
        final T[] array
        ) {
        return array == null
               ? null
               : SequentialComparableLocator.backward(item, array).index(null);
    }

    /**
     * Returns index of the last occurrence of element in the array using the specified function as comparator; or null if element is not found, the array is null, or the function is null.
     *
     * @param <T> the array type.
     * @param array the array.
     * @param comparator the function.
     *
     * @return the array element, or null if not found.
     */
    public static <T>
    Integer locateLastIndex(
        final T[] array,
        final Function<Object, Boolean> comparator
        ) {
        return array == null || comparator == null
               ? null
               : SequentialLocator.backward(null, array, (final T n, final T element) -> comparator.apply(element) ? 0 : 1).index(null);
    }

    /**
     * Rounds the number away from zero and returns it.
     *
     * @param number the number.
     *
     * @return the rounded number.
     */
    public static
    int round(
        final float number
        ) {
        return (int) (Math.signum(number) * Math.round(Math.abs(number) / 100));
    }

    /**
     * Returns the first encountered occurrence of the specified item in the sorted array using the specified comparator; or null if item is not found, the array is null, or the comparator is null.
     * <p/>
     * This implementation calls {@link Comparator#compare(Object, Object)} with the item as the first argument and the array elements as second arguments of that method.
     *
     * @param <T> the item, array, and comparator data type.
     * @param item the item.
     * @param sortedArray the sorted array.
     * @param comparator the comparator.
     *
     * @return the array element, or null if not found.
     */
    public static <T>
    T sortedFind(
        final T item,
        final T[] sortedArray,
        final Comparator<? super T> comparator
        ) {
        return sortedArray == null
               ? null
               : new BinaryFinder<T>(item, sortedArray, comparator).element(null);
    }

    /**
     * Returns the first encountered occurrence of the specified item in the sorted array; or null if the item is not found or the array is null.
     * <p/>
     * This implementation calls {@link Comparable#compareTo(Object)} on the item and passes each array element as the argument of that method.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param sortedArray the sorted array.
     *
     * @return the array element, or null if the item is not found or the array is null.
     */
    public static <T extends Comparable<? super T>>
    T sortedFind(
        final T item,
        final T[] sortedArray
        ) {
        return sortedArray == null
               ? null
               : new BinaryComparableFinder<T>(item, sortedArray).element(null);
    }

    /**
     * Returns the first occurrence of element in the sorted array using the specified function as comparator; or null if element is not found, the array is null, or the function is null.
     *
     * @param <T> the array type.
     * @param sortedArray the sorted array.
     * @param comparator the function.
     *
     * @return the array element, or null if not found.
     */
    public static <T>
    T sortedFind(
        final T[] sortedArray,
        final Function<Object, Boolean> comparator
        ) {
        return sortedArray == null || comparator == null
               ? null
               : new BinaryFinder<T>(null, sortedArray, (final T n, final T element) -> comparator.apply(element) ? 0 : 1).element(null);
    }

    /**
     * Returns index of the first encountered occurrence of the specified item in the sorted array using the specified comparator; or null if the item is not found, the array is null, or the comparator is null.
     * <p/>
     * This implementation internally calls {@link Comparator#compare(Object, Object)} with the item as the first argument and the array elements as the second arguments.
     *
     * @param <T> the item, array, and comparator data type.
     * @param item the item.
     * @param sortedArray the sorted array.
     * @param comparator the comparator.
     *
     * @return the array element index, or null if not found.
     */
    public static <T>
    Integer sortedFindIndex(
        final T item,
        final T[] sortedArray,
        final Comparator<? super T> comparator
        ) {
        return sortedArray == null
               ? null
               : new BinaryFinder<T>(item, sortedArray, comparator).index(null);
    }

    /**
     * Returns index of the first encountered occurrence of the specified item in the sorted array; or null if the item is not found or the array is null.
     * <p/>
     * This implementation calls {@link Comparable#compareTo(Object)} internally.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param sortedArray the sorted array.
     *
     * @return the array element index, or null if not found.
     */
    public static <T extends Comparable<? super T>>
    Integer sortedFindIndex(
        final T item,
        final T[] sortedArray
        ) {
        return sortedArray == null
               ? null
               : new BinaryComparableFinder<T>(item, sortedArray).index(null);
    }

    /**
     * Returns index of the first occurrence of element in the sorted array using the specified function as comparator; or null if element is not found, the array is null, or the function is null.
     *
     * @param <T> the array type.
     * @param sortedArray the sorted array.
     * @param comparator the function.
     *
     * @return the array element index, or null if not found.
     */
    public static <T>
    Integer sortedFindIndex(
        final T[] sortedArray,
        final Function<Object, Boolean> comparator
        ) {
        return sortedArray == null || comparator == null
               ? null
               : new BinaryFinder<T>(null, sortedArray, (final T n, final T element) -> comparator.apply(element) ? 0 : 1).index(null);
    }

    /**
     * Returns the first encountered occurrence of the specified item in the sorted array using the specified comparator; or null if item is not found, the array is null, or the comparator is null.
     * <p/>
     * This implementation calls {@link Comparator#compare(Object, Object)} with the item as the first argument and the array elements as second arguments of that method.
     *
     * @param <T> the item, array, and comparator data type.
     * @param item the item.
     * @param sortedArray the sorted array.
     * @param comparator the comparator.
     *
     * @return the array element, or null if not found.
     */
    public static <T>
    T sortedLocate(
        final T item,
        final T[] sortedArray,
        final Comparator<? super T> comparator
        ) {
        return sortedArray == null
               ? null
               : new BinaryLocator<T>(item, sortedArray, comparator).element(null);
    }

    /**
     * Returns the first encountered occurrence of the specified item in the sorted array; or null if the item is not found or the array is null.
     * <p/>
     * This implementation calls {@link Comparable#compareTo(Object)} on the item and passes each array element as the argument of that method.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param sortedArray the sorted array.
     *
     * @return the array element, or null if the item is not found or the array is null.
     */
    public static <T extends Comparable<? super T>>
    T sortedLocate(
        final T item,
        final T[] sortedArray
        ) {
        return sortedArray == null
               ? null
               : new BinaryComparableLocator<T>(item, sortedArray).element(null);
    }

    /**
     * Returns the first occurrence of element in the sorted array using the specified function as comparator; or null if element is not found, the array is null, or the function is null.
     *
     * @param <T> the array type.
     * @param sortedArray the sorted array.
     * @param comparator the function.
     *
     * @return the array element, or null if not found.
     */
    public static <T>
    T sortedLocate(
        final T[] sortedArray,
        final Function<Object, Boolean> comparator
        ) {
        return sortedArray == null || comparator == null
               ? null
               : new BinaryLocator<T>(null, sortedArray, (final T n, final T element) -> comparator.apply(element) ? 0 : 1).element(null);
    }

    /**
     * Returns index of the first encountered occurrence of the specified item in the sorted array using the specified comparator; or null if the item is not found, the array is null, or the comparator is null.
     * <p/>
     * This implementation internally calls {@link Comparator#compare(Object, Object)} with the item as the first argument and the array elements as the second arguments.
     *
     * @param <T> the item, array, and comparator data type.
     * @param item the item.
     * @param sortedArray the sorted array.
     * @param comparator the comparator.
     *
     * @return the array element index, or null if not found.
     */
    public static <T>
    Integer sortedLocateIndex(
        final T item,
        final T[] sortedArray,
        final Comparator<? super T> comparator
        ) {
        return sortedArray == null
               ? null
               : new BinaryLocator<T>(item, sortedArray, comparator).index(null);
    }

    /**
     * Returns index of the first encountered occurrence of the specified item in the sorted array; or null if the item is not found or the array is null.
     * <p/>
     * This implementation calls {@link Comparable#compareTo(Object)} internally.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param sortedArray the sorted array.
     *
     * @return the array element index, or null if not found.
     */
    public static <T extends Comparable<? super T>>
    Integer sortedLocateIndex(
        final T item,
        final T[] sortedArray
        ) {
        return sortedArray == null
               ? null
               : new BinaryComparableLocator<T>(item, sortedArray).index(null);
    }

    /**
     * Returns index of the first occurrence of element in the sorted array using the specified function as comparator; or null if element is not found, the array is null, or the function is null.
     *
     * @param <T> the array type.
     * @param sortedArray the sorted array.
     * @param comparator the function.
     *
     * @return the array element index, or null if not found.
     */
    public static <T>
    Integer sortedLocateIndex(
        final T[] sortedArray,
        final Function<Object, Boolean> comparator
        ) {
        return sortedArray == null || comparator == null
               ? null
               : new BinaryLocator<T>(null, sortedArray, (final T n, final T element) -> comparator.apply(element) ? 0 : 1).index(null);
    }

    /**
     * Converts an iterable into an array, or returns null if the iterable is null.
     * <p/>
     * This implementation iterates through the elements in the iterable once and returns an {@link ArrayList}.
     *
     * @param <T> the array type.
     * @param iterable the iterable.
     *
     * @return the array of elements, or null if iterable is null.
     */
    @SuppressWarnings("unchecked")
    public static <T>
    T[] toArray(
        final Iterable<? extends T> iterable
        ) {
        return (T[]) toList(iterable).toArray();
    }

    /**
     * Converts an iterable into the supplied list type, or returns null if iterable is null.
     * <p/>
     * This implementation iterates through the elements in the iterable once.
     *
     * @param <T> the list element type.
     * @param iterable the iterable.
     * @param supplier the list supplier.
     *
     * @return the list of elements, or null if the iterable is null.
     *
     * @throws NullPointerException if the supplier is null.
     */
    public static <T>
    List<T> toList(
        final Iterable<? extends T> iterable,
        final Supplier<? extends List<T>> supplier
        ) {
        if (iterable == null)
            return null;

        final List<T> list = supplier.get();
        iterable.forEach(list::add);

        return list;
    }

    /**
     * Converts an iterable into a list, or returns null if the iterable is null.
     * <p/>
     * This implementation iterates through the elements in the iterable once and returns an {@link ArrayList}.
     *
     * @param <T> the list element type.
     * @param iterable the iterable.
     *
     * @return the list of elements, or null if iterable is null.
     */
    public static <T>
    List<T> toList(
        final Iterable<? extends T> iterable
        ) {
        return toList(iterable, ArrayList::new);
    }

    /**
     * {@code ArrayIterable} is an implementation of an iterable over an array.
     *
     * @param <T> the array type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class ArrayIterable<T>
    implements Iterable<T>
    {
        /** The array. */
        protected final
        T[] array;

        /** The iteration start index. (inclusive) */
        protected final
        int start;

        /** The iteration end index. (exclusive) */
        protected final
        int end;

        /** The iteration step. */
        protected final
        int step;

        /**
         * Creates and returns an array iterable that iterates backward over the array element.
         *
         * @param <S> the array type.
         *
         * @param array the array.
         *
         * @return the backward array iterable.
         */
        public static <S>
        ArrayIterable<S> backward(
            final S[] array
            ) {
            return new ArrayIterable<>(array, array.length, 0, -1);
        }

        /**
         * Creates and returns an array iterable that iterates forward over the array element.
         *
         * @param <S> the array type.
         *
         * @param array the array.
         *
         * @return the forward array iterable.
         */
        public static <S>
        ArrayIterable<S> forward(
            final S[] array
            ) {
            return new ArrayIterable<>(array, 0, array.length, 1);
        }

        /**
         * Creates an array iterable with the specified array, iteration start and end index, and step.
         *
         * @param array the array.
         * @param start the iteration start index. (inclusive)
         * @param end the iteration end index. (exclusive)
         * @param step the iteration step.
         */
        public
        ArrayIterable(
            final T[] array,
            final int start,
            final int end,
            final int step
            ) {
            super();
            this.array = array;
            this.start = start;
            this.end = end;
            this.step = step;
        }

        /**
         * Creates and returns an iterator over the array elements.
         *
         * @return the iterator.
         */
        @Override
        public Iterator<T> iterator() {
            return array != null
                   ? new Iterator<T>() {
                         int i = step > 0
                                 ? start
                                 : end - 1;

                         boolean hasNext = updateHasNext();

                         boolean updateHasNext() {
                             hasNext = i >= start && i < end;
                             return hasNext;
                         }

                         @Override
                         public boolean hasNext() {
                             updateHasNext();
                             return hasNext;
                         }

                         @Override
                         public T next() {
                             if (!hasNext)
                                 throw new NoSuchElementException();

                             final T element = array[i];
                             i += step;
                             return element;
                         }
                     }
                   : new Iterator<T>() {
                         @Override
                         public boolean hasNext() {
                             return false;
                         }

                         @Override
                         public T next() {
                             throw new NoSuchElementException();
                         }
                     };
        }
    }

    /**
     * {@code BinaryComparableFinder} is an implementation of a binary finder that uses the {@link Comparable#compareTo(Object)} method.
     *
     * @param <T> the item and array type.
     *
     * @see BinaryFinder
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class BinaryComparableFinder<T extends Comparable<? super T>>
    extends BinaryFinder<T>
    {
        /**
         * Creates a binary finder with the specified comparable item, sorted array, and ascending flag.
         * <p/>
         * This implementation calls the item's {@link Comparable#compareTo(Object)} method internally, unless the item is null; in that case, the same method is called on each array element instead.
         * <p/>
         * If the ascending flag is null, the comparator will not be reset.
         * If the flag is true, the {@link Comparable#compareTo(Object)} result will be used; otherwise the result will be negated.
         *
         * @param item the finder item.
         * @param array the sorted array.
         * @param ascending the ascending flag.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public
        BinaryComparableFinder(
            final T item,
            final T[] array,
            final Boolean ascending
            ) {
            super(item, array, ascending);
            ComparatorAdjuster.reset(this, item);
            resetComparatorSupplier(comparator);
        }

        /**
         * Creates a binary finder with the specified comparable item and sorted array.
         * <p/>
         * This implementation calls the item's {@link Comparable#compareTo(Object)} method internally, unless the item is null; in that case, the same method is called on each array element instead.
         *
         * @param item the finder item.
         * @param array the sorted array.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public
        BinaryComparableFinder(
            final T item,
            final T[] array
            ) {
            this(item, array, null);
        }

        /**
         * Sets the item and resets the comparator.
         *
         * @see BinaryComparableLocator
         */
        @Override
        public void setItem(final T item) {
            super.setItem(item);
            ComparatorAdjuster.reset(this, item);
        }
    }

    /**
     * {@code BinaryComparableLocator} is an implementation of a binary locator that uses the {@link Comparable#compareTo(Object)} method.
     *
     * @param <T> the item and array type.
     *
     * @see BinaryLocator
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class BinaryComparableLocator<T extends Comparable<? super T>>
    extends BinaryLocator<T>
    {
        /**
         * Creates a binary locator with the specified comparable item, sorted array, and ascending flag.
         * <p/>
         * This implementation calls the item's {@link Comparable#compareTo(Object)} method internally, unless the item is null; in that case, the same method is called on each array element instead.
         * <p/>
         * If the ascending flag is null, the comparator will not be reset.
         * If the flag is true, the {@link Comparable#compareTo(Object)} result will be used; otherwise the result will be negated.
         *
         * @param item the locator item.
         * @param array the sorted array.
         * @param ascending the ascending flag.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public
        BinaryComparableLocator(
            final T item,
            final T[] array,
            final Boolean ascending
            ) {
            super(item, array, ascending);
            ComparatorAdjuster.reset(this, item);
            resetComparatorSupplier(comparator);
        }

        /**
         * Creates a binary locator with the specified comparable item and sorted array.
         * <p/>
         * This implementation calls the item's {@link Comparable#compareTo(Object)} method internally, unless the item is null; in that case, the same method is called on each array element instead.
         *
         * @param item the locator item.
         * @param array the sorted array.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public
        BinaryComparableLocator(
            final T item,
            final T[] array
            ) {
            this(item, array, null);
        }

        /**
         * Sets the item and resets the comparator.
         *
         * @see BinaryComparableLocator
         */
        @Override
        public void setItem(final T item) {
            super.setItem(item);
            ComparatorAdjuster.reset(this, item);
        }
    }

    /**
     * {@code BinaryFinder} is an implementation of a finder that iterates over a sorted array in a binary search manner.
     * <p/>
     * Note that the sort order of the array can be null at creation time but must be set prior to starting the search process; otherwise a {@code NullPointerException} will be thrown.
     *
     * @param <T> the item and array type.
     *
     * @see Finder
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class BinaryFinder<T>
    extends Finder<T>
    {
        // this class required defining an extra index pointer: k
        // functional interfaces were also declared to use this new variable
        // nextMatchShifted to works with two indexes j (the last iteration index) and k
        // the general premise is that iteration must continue until the newly iterated index is not equal to j or k (the iteration index before the last one)

        /** The array sort order. */
        protected final
        Boolean ascending;

        /** The iteration index before the last one. */
        protected
        int k;

        /** The "location found" flag. */
        protected
        boolean found = false;

        /** The comparator supplier. */
        private
        Supplier<Integer> comp;

        /** The advance consumer. */
        private
        Consumer<Integer> advance;

        /**
         * Creates a binary finder with the specified item, sorted array, ascending flag, and null comparator.
         *
         * @param item the item.
         * @param array the sorted array.
         * @param ascending the ascending flag.
         */
        protected
        BinaryFinder(
            final T item,
            final T[] array,
            final Boolean ascending
            ) {
            super(array, null);
            setItem(item);
            setStartIndex(0);
            setEndIndex(array.length);
            setIndex((start + end) / 2);
            this.ascending = ascending;

            j = start;
            k = end;
        }

        /**
         * Creates a binary finder with the specified item, sorted array, ascending flag, and comparator.
         * <p/>
         * If the ascending flag is null, the comparator will not be reset.
         * If the flag is true, the {@link Comparator#compare(Object, Object)} will receive the item as the first argument and the array element as the second argument; otherwise the order is reversed.
         *
         * @param item the finder item.
         * @param array the sorted array.
         * @param ascending the ascending flag.
         * @param comparator the comparator.
         *
         * @throws IllegalArgumentException if the array or comparator is null.
         *
         * @see #resetComparatorSupplier(Comparator)
         */
        public
        BinaryFinder(
            final T item,
            final T[] array,
            final Boolean ascending,
            final Comparator<? super T> comparator
            ) {
            super(array, comparator);
            setItem(item);
            setStartIndex(0);
            setEndIndex(array.length);
            setIndex((start + end) / 2);
            this.ascending = ascending;
            resetComparatorSupplier(comparator);

            j = start;
            k = end;
        }

        /**
         * Creates a binary finder with the specified item, sorted array, and comparator.
         * <p/>
         * This constructor automatically sets the ascending flag by comparing the first and last array elements.
         *
         * @param item the finder item.
         * @param array the sorted array.
         * @param comparator the comparator.
         *
         * @throws IllegalArgumentException if the array or comparator is null.
         */
        public
        BinaryFinder(
            final T item,
            final T[] array,
            final Comparator<? super T> comparator
            ) {
            this(item, array, array.length > 1 && comparator.compare(array[0], array[array.length - 1]) < 0, comparator);
        }

        /**
         * Creates and sets the comparator supplier based on the sort order of the array.
         *
         * @param comparator the comparator.
         */
        protected
        void resetComparatorSupplier(
            final Comparator<? super T> comparator
            ) {
            if (ascending != null)
                if (ascending) {
                    comp = () -> comparator.compare(item, array[j]);
                    advance = (final Integer res) -> {
                        if (res < 0)
                            k = i;
                        else
                            j = i + 1;
                    };
                }
                else {
                    comp = () -> comparator.compare(array[j], item);
                    advance = (final Integer res) -> {
                        if (res < 0)
                            j = i + 1;
                        else
                            k = i;
                    };
                }
        }

        /**
         * {@inheritDoc}
         *
         * @return true if next match exists, or false otherwise.
         */
        @Override
        protected boolean nextMatchExists() {
            i = (j + k) / 2;
            while (!found && i >= start && i < end && j != k /* this check might require some extra work */) {
                final int result = comp.get();
                if (result == 0)
                    found = true;
                else {
                    advance.accept(result);
                    found = false;
                }

                i = (j + k) / 2;
            }

            return found;
        }

        /**
         * {@inheritDoc}
         *
         * @return the found array element.
         *
         * @throws NoSuchElementException if the array element is not found or the iteration has no more elements.
         */
        @Override
        public T next() {
            if (found)
                return array[i];

            throw new NoSuchElementException();
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation resets the comparator to according to the array sort order.
         *
         * @param comparator the comparator.
         *
         * @see #validateComparator()
         * @see #resetComparatorSupplier(Comparator)
         */
        public
        void setComparator(
            Comparator<? super T> comparator
            ) {
            this.comparator = comparator;
            validateComparator();
            resetComparatorSupplier(this.comparator);
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation calls {@link #validateEndIndex()} and {@link #nextMatchExists()} internally.
         *
         * @param end the iteration end index.
         */
        @Override
        public void setEndIndex(final int end) {
            this.end = end;
            validateEndIndex();
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation calls {@link #nextMatchExists()} internally.
         *
         * @param index the iteration index.
         */
        @Override
        public void setIndex(final int index) {
            i = index;
            j = i;
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation calls {@link #validateStartIndex()} and {@link #nextMatchExists()} internally.
         *
         * @param start the iteration start index.
         */
        @Override
        public void setStartIndex(final int start) {
            this.start = start;
            validateStartIndex();
        }
    }

    /**
     * {@code BinaryLocator} is an implementation of a locator that iterates over a sorted array in a binary search manner.
     * <p/>
     * Note that the sort order of the array can be null at creation time but must be set prior to starting the search process; otherwise a {@code NullPointerException} will be thrown.
     *
     * @param <T> the item and array type.
     *
     * @see Locator
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class BinaryLocator<T>
    extends Locator<T>
    {
        /** The array sort order. */
        protected final
        Boolean ascending;

        /** The comparator supplier. */
        private
        Supplier<Integer> comp;

        /**
         * Creates a binary locator with the specified item, sorted array, ascending flag, and null comparator.
         *
         * @param item the item.
         * @param array the sorted array.
         * @param ascending the ascending flag.
         */
        protected
        BinaryLocator(
            final T item,
            final T[] array,
            final Boolean ascending
            ) {
            super(array, null);
            setItem(item);
            setStartIndex(0);
            setEndIndex(array.length);
            setIndex((start + end) / 2);
            this.ascending = ascending;
        }

        /**
         * Creates a binary locator with the specified item, sorted array, ascending flag, and comparator.
         * <p/>
         * If the ascending flag is null, the comparator will not be reset.
         * If the flag is true, the {@link Comparator#compare(Object, Object)} will receive the item as the first argument and the array element as the second argument; otherwise the order is reversed.
         *
         * @param item the locator item.
         * @param array the sorted array.
         * @param ascending the ascending flag.
         * @param comparator the comparator.
         *
         * @throws IllegalArgumentException if the array or comparator is null.
         *
         * @see #resetComparatorSupplier(Comparator)
         */
        public
        BinaryLocator(
            final T item,
            final T[] array,
            final Boolean ascending,
            final Comparator<? super T> comparator
            ) {
            super(array, comparator);
            setItem(item);
            setStartIndex(0);
            setEndIndex(array.length);
            setIndex((start + end) / 2);
            this.ascending = ascending;
            resetComparatorSupplier(comparator);
        }

        /**
         * Creates a binary locator with the specified item, sorted array, and comparator.
         * <p/>
         * This constructor automatically sets the ascending flag by comparing the first and last array elements.
         *
         * @param item the locator item.
         * @param array the sorted array.
         * @param comparator the comparator.
         *
         * @throws IllegalArgumentException if the array or comparator is null.
         */
        public
        BinaryLocator(
            final T item,
            final T[] array,
            final Comparator<? super T> comparator
            ) {
            this(item, array, array.length > 1 && comparator.compare(array[0], array[array.length - 1]) < 0, comparator);
        }

        /**
         * Creates and sets the comparator supplier based on the sort order of the array.
         *
         * @param comparator the comparator.
         */
        protected
        void resetComparatorSupplier(
            final Comparator<? super T> comparator
            ) {
            if (ascending != null)
                comp = ascending
                       ? () -> comparator.compare(item, array[getIndex()])
                       : () -> comparator.compare(array[getIndex()], item);
        }

        /**
         * {@inheritDoc}
         *
         * @return true if the item is found, and false otherwise.
         *
         * @throws NoSuchElementException if the iteration has no more elements.
         * @throws NullPointerException if the comparator is null, or an argument is null and the comparator does not permit null arguments.
         * @throws ClassCastException if the arguments' types prevent them from being compared by the comparator.
         */
        @Override
        public Boolean next() {
            if (hasNext) {
                final int result = comp.get();
                if (result == 0) {
                    found = true;
                    return true;
                }

                if (result > 0)
                    start = i + 1;
                else
                    end = i;

                i = (start + end) / 2;
                found = false;
                return false;
            }

            throw new NoSuchElementException();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void updateHasNext() {
            hasNext = start < end && start <= i && end > i;
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation resets the comparator to according to the array sort order.
         *
         * @param comparator the comparator.
         *
         * @see #validateComparator()
         * @see #resetComparatorSupplier(Comparator)
         */
        public
        void setComparator(
            Comparator<? super T> comparator
            ) {
            this.comparator = comparator;
            validateComparator();
            resetComparatorSupplier(this.comparator);
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation calls {@link #validateEndIndex()} and {@link #updateHasNext()} internally.
         *
         * @param end the iteration end index.
         */
        @Override
        public void setEndIndex(final int end) {
            this.end = end;
            validateEndIndex();
            updateHasNext();
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation calls {@link #updateHasNext()} internally.
         *
         * @param index the iteration index.
         */
        @Override
        public void setIndex(final int index) {
            i = index;
            updateHasNext();
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation calls {@link #validateStartIndex()} and {@link #updateHasNext()} internally.
         *
         * @param start the iteration start index.
         */
        @Override
        public void setStartIndex(final int start) {
            this.start = start;
            validateStartIndex();
            updateHasNext();
        }
    }

    /**
     * {@code ComparatorAdjuster} encapsulates a static method that resets a locator's comparator according to the null status of an item.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    private
    interface ComparatorAdjuster
    {
        /**
         * Creates and sets a comparator for the specified locator based on whether the specified item is null or not.
         *
         * @param item the item.
         */
        static <T extends Comparable<? super T>>
        void reset(
            final Finding<T> finding,
            final T item
            ) {
            finding.comparator = item == null
                                 ? (item1, element) -> element == null
                                   ? 0
                                   : -element.compareTo(item1)
                                 : Comparator.naturalOrder();
        }
    }

    /**
     * {@code Finder} classifies a specialized iterator over an array for finding elements within the array, using a {@link Comparator#compare(Object, Object)} method, and providing the position or the array element.
     *
     * @param <T> the array type.
     *
     * @see system.data.Finder
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract static
    class Finder<T>
    extends Finding<T>
    implements system.data.Finder<T>
    {
        /** The next match index. */
        protected
        int j;

        /**
         * Creates a finder with the specified array and comparator.
         * <p/>
         * This constructor accepts null comparator with the promise that it will be set later on prior to locating an element.
         *
         * @param array the array.
         * @param comparator the comparator.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public Finder(
            final T[] array,
            final Comparator<? super T> comparator
            ) {
            super(array, comparator);
        }

        /**
         * Returns true if the next match exists; otherwise returns false.
         *
         * @return true if next match exists, or false otherwise.
         */
        protected abstract
        boolean nextMatchExists();

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation calls {@link #found()} internally.
         *
         * @return the found array element.
         *
         * @throws NoSuchElementException if the array element is not found or the iteration has no more elements.
         */
        @Override
        public T element() {
            found();
            return array[getIndex()];
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation calls {@link #found()} internally.
         *
         * @param fallback the fallback.
         *
         * @return the found array element or the fallback.
         */
        @Override
        public T element(final T fallback) {
            try {
                return element();
            }
            catch (NoSuchElementException e) {
                return fallback;
            }
        }

        /**
         * {@inheritDoc}
         *
         * @return true if the finder element is found, and false otherwise.
         */
        @Override
        public boolean elementFound() {
            return false;
        }

        /**
         * {@inheritDoc}
         *
         * @return true if the finder element is found, and false otherwise.
         */
        @Override
        public Finder<T> found() {
            if (array == null || comparator == null)
                return this;

            if (hasNext())
                next();
            else
                throw new NoSuchElementException();


            return this;
        }

        /**
         * Returns true if there are more elements in the iteration; otherwise returns false.
         * <p/>
         * This implementation calls {@link #nextMatchExists()} internally after verifying boundaries.
         *
         * @return true if there are more elements in the iteration, and false otherwise.
         */
        @Override
        public boolean hasNext() {
            return start < end && i >= start && i < end && nextMatchExists();
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation calls {@link #found()} internally.
         *
         * @return the found array element index.
         *
         * @throws NoSuchElementException if the array element is not found or the iteration has no more elements.
         */
        @Override
        public Integer index() {
            found();
            return getIndex();
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation calls {@link #found()} internally.
         *
         * @param fallback the fallback.
         *
         * @return the found array element index or the fallback.
         */
        @Override
        public Integer index(final Integer fallback) {
            try {
                return index();
            }
            catch (NoSuchElementException e) {
                return fallback;
            }
        }

        /**
         * Returns the array element matching the item at the next location; otherwise throws a {@code NoSuchElementException}.
         *
         * @return the found array element.
         *
         * @throws NoSuchElementException if the array element is not found or the iteration has no more elements.
         */
        @Override
        public abstract T next();

        /**
         * Sets the iteration index.
         *
         * @param index the iteration index.
         */
        public void setIndex(final int index) {
            i = index;
            j = i;
        }
    }

    /**
     * {@code Finding} is the base class for both {@link Finder} and {@link Locator} classes.
     *
     * @param <T> the array type.
     *
     * @see system.data.Finding
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    private abstract static
    class Finding<T>
    implements system.data.Finding<T>
    {
        /** The array. */
        protected final
        T[] array;

        /** The comparator. */
        protected
        Comparator<? super T> comparator;

        /** The item. */
        protected
        T item;
        /** The iteration lower bound. (inclusive) */
        protected
        int start;

        /** The iteration upper bound. (exclusive) */
        protected
        int end;

        /** The iteration index. */
        protected
        int i;

        /**
         * Creates a finding type with the specified array and comparator.
         * <p/>
         * This constructor accepts null comparator with the promise that it will be set later on prior to locating an element.
         *
         * @param array the array.
         * @param comparator the comparator.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public
        Finding(
            final T[] array,
            final Comparator<? super T> comparator
            ) {
            this.array = array;
            validateArray();
            this.comparator = comparator;
        }

        /**
         * Validates the array and throws an {@code IllegalArgumentException} if the array is null.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        protected
        void validateArray() {
            if (array == null)
                throw new IllegalArgumentException();
        }

        /**
         * Validates the comparator and throws an {@code IllegalArgumentException} if the comparator is null.
         *
         * @throws IllegalArgumentException if the comparator is null.
         */
        protected
        void validateComparator() {
            if (comparator == null)
                throw new IllegalArgumentException();
        }

        /**
         * Validates the iteration end index and resets it to the array length if it is greater than that value.
         */
        protected
        void validateEndIndex() {
            if (getEndIndex() > array.length)
                setEndIndex(array.length);
        }

        /**
         * Validates the iteration start index and resets it to zero if it is negative.
         */
        protected
        void validateStartIndex() {
            if (getStartIndex() < 0)
                setStartIndex(0);
        }

        /**
         * Returns the comparator.
         *
         * @return the comparator.
         */
        public
        Comparator<? super T> getComparator() {
            return comparator;
        }

        /**
         * Returns the iteration end index.
         *
         * @return the iteration end index.
         */
        public int getEndIndex() {
            return end;
        }

        /**
         * Returns the iteration index.
         *
         * @return the iteration index.
         */
        public int getIndex() {
            return i;
        }

        /**
         * Returns the finding type item.
         *
         * @return the item.
         */
        public T getItem() {
            return item;
        }

        /**
         * Returns the iteration start index.
         *
         * @return the iteration start index.
         */
        public int getStartIndex() {
            return start;
        }

        /**
         * Sets and validates the comparator.
         * <p/>
         * This implementation calls {@link #validateComparator()} internally.
         *
         * @param comparator the comparator.
         */
        public
        void setComparator(
            Comparator<? super T> comparator
            ) {
            this.comparator = comparator;
            validateComparator();
        }

        /**
         * Sets the iteration end index. (exclusive)
         * <p/>
         * This implementation calls {@link #validateEndIndex()} internally.
         *
         * @param end the iteration end index.
         */
        public void setEndIndex(final int end) {
            this.end = end;
            validateEndIndex();
        }

        /**
         * Sets the finding type item.
         *
         * @param item the item.
         */
        public void setItem(
            final T item
            ) {
            this.item = item;
        }

        /**
         * Sets the iteration start index. (inclusive)
         * <p/>
         * This implementation calls {@link #validateStartIndex()} internally.
         *
         * @param start the iteration start index.
         */
        public void setStartIndex(final int start) {
            this.start = start;
            validateStartIndex();
        }

        /**
         * Sets the iteration index.
         *
         * @param index the iteration index.
         */
        public abstract
        void setIndex(int index);
    }

    /**
     * {@code Locator} classifies a specialized iterator over an array for locating elements within the array, using a {@link Comparator#compare(Object, Object)} method, and providing the position or the array element.
     *
     * @param <T> the array type.
     *
     * @see system.data.Locator
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract static
    class Locator<T>
    extends Finding<T>
    implements system.data.Locator<T>
    {
        /** The "location found" flag. */
        protected
        boolean found;

        /** The "next element exists" flag. */
        protected
        boolean hasNext;

        /**
         * Creates a locator with the specified array and comparator.
         * <p/>
         * This constructor accepts null comparator with the promise that it will be set later on prior to locating an element.
         *
         * @param array the array.
         * @param comparator the comparator.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public
        Locator(
            final T[] array,
            final Comparator<? super T> comparator
            ) {
            super(array, comparator);
            found = false;
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation calls {@link #found()} internally.
         *
         * @return the located array element.
         *
         * @throws NoSuchElementException if the array element is not located or the iteration has no more elements.
         */
        @Override
        public T element() {
            found();
            if (found)
                return array[getIndex()];

            throw new NoSuchElementException();
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation calls {@link #found()} internally.
         *
         * @param fallback the fallback.
         *
         * @return the located array element or the fallback.
         */
        @Override
        public T element(final T fallback) {
            found();
            return found ? array[getIndex()] : fallback;
        }

        /**
         * {@inheritDoc}
         *
         * @return true if the locator element is found, and false otherwise.
         */
        @Override
        public boolean elementFound() {
            return found;
        }

        /**
         * {@inheritDoc}
         * <p/>
         * If the array or the comparator is null, the locator is returned immediately.
         *
         * @return the advanced locator.
         */
        @Override
        public Locator<T> found() {
            if (array == null || comparator == null)
                return this;

            while (hasNext() && !next());
            return this;
        }

        /**
         * Returns true if there are more elements in the iteration; otherwise returns false.
         * <p/>
         * This implementation calls {@link #updateHasNext()} internally.
         *
         * @return true if there are more elements in the iteration, and false otherwise.
         */
        @Override
        public boolean hasNext() {
            updateHasNext();
            return hasNext;
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation calls {@link #found()} internally.
         *
         * @return the located array element index.
         *
         * @throws NoSuchElementException if the array element is not located or the iteration has no more elements.
         */
        @Override
        public Integer index() {
            found();
            if (found)
                return getIndex();

            throw new NoSuchElementException();
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation calls {@link #found()} internally.
         *
         * @param fallback the fallback.
         *
         * @return the located array element index or the fallback.
         */
        @Override
        public Integer index(final Integer fallback) {
            found();
            return found ? getIndex() : fallback;
        }

        /**
         * Returns true if the array element matching the item is found at the next location; otherwise returns false.
         *
         * @return true if the array element is found, and false otherwise.
         */
        @Override
        public abstract Boolean next();

        /**
         * Updates the internal {@code hasNext} flag indicating if there are more elements in the iteration.
         */
        protected abstract
        void updateHasNext();

        /**
         * Sets the iteration end index. (exclusive)
         * <p/>
         * This implementation calls {@link #validateEndIndex()} and {@link #updateHasNext()} internally.
         *
         * @param end the iteration end index.
         */
        @Override
        public void setEndIndex(final int end) {
            this.end = end;
            validateEndIndex();
            updateHasNext();
        }

        /**
         * Sets the iteration index.
         * <p/>
         * This implementation calls {@link #updateHasNext()} internally.
         *
         * @param index the iteration index.
         */
        @Override
        public void setIndex(final int index) {
            i = index;
            updateHasNext();
        }

        /**
         * Sets the iteration start index. (inclusive)
         * <p/>
         * This implementation calls {@link #validateStartIndex()} and {@link #updateHasNext()} internally.
         *
         * @param start the iteration start index.
         */
        @Override
        public void setStartIndex(final int start) {
            this.start = start;
            validateStartIndex();
            updateHasNext();
        }
    }

    /**
     * {@code SequentialComparableFinder} is an implementation of a sequential finder that uses the {@link Comparable#compareTo(Object)} method.
     *
     * @param <T> the item and array type.
     *
     * @see SequentialFinder
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class SequentialComparableFinder<T extends Comparable<? super T>>
    extends SequentialFinder<T>
    {
        /**
         * Creates and returns a sequential comparable finder that iterates backward over the specified array looking for the specified item.
         *
         * @param <S> the item and array type.
         *
         * @param item the finder item.
         * @param array the array.
         *
         * @return the backward sequential comparable finder.
         */
        public static <S extends Comparable<? super S>>
        SequentialComparableFinder<S> backward(
            final S item,
            final S[] array
            ) {
            return new SequentialComparableFinder<>(item, array, -1);
        }

        /**
         * Creates and returns a sequential comparable finder that iterates forward over the specified array looking for the specified item.
         *
         * @param <S> the item and array type.
         *
         * @param item the finder item.
         * @param array the array.
         *
         * @return the forward sequential comparable finder.
         */
        public static <S extends Comparable<? super S>>
        SequentialComparableFinder<S> forward(
            final S item,
            final S[] array
            ) {
            return new SequentialComparableFinder<>(item, array);
        }

        /**
         * Creates a sequential finder with the specified comparable item, array, iteration start and end index, step, and comparator.
         * <p/>
         * This implementation calls the item's {@link Comparable#compareTo(Object)} method internally, unless the item is null; in that case, the same method is called on each array element instead.
         *
         * @param item the finder item.
         * @param array the array.
         * @param start the iteration start index. (inclusive)
         * @param end the iteration end index. (exclusive)
         * @param step the iteration step.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public
        SequentialComparableFinder(
            final T item,
            final T[] array,
            final int start,
            final int end,
            final int step
            ) {
            super(item, array, start, end, step, null);
            ComparatorAdjuster.reset(this, item);
        }

        /**
         * Creates a sequential finder with the specified comparable item, array, and iteration step.
         * <p/>
         * This implementation calls the item's {@link Comparable#compareTo(Object)} method internally, unless the item is null; in that case, the same method is called on each array element instead.
         *
         * @param item the finder item.
         * @param step the iteration step.
         * @param array the array.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public
        SequentialComparableFinder(
            final T item,
            final T[] array,
            final int step
            ) {
            this(item, array, 0, array.length, step);
        }

        /**
         * Creates a forward linear sequential finder with the specified comparable item and array.
         * <p/>
         * This implementation calls the item's {@link Comparable#compareTo(Object)} method internally, unless the item is null; in that case, the same method is called on each array element instead.
         *
         * @param item the finder item.
         * @param array the array.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public
        SequentialComparableFinder(
            final T item,
            final T[] array
            ) {
            this(item, array, 1);
        }

        /**
         * Sets the item and resets the comparator.
         *
         * @see SequentialComparableFinder
         */
        @Override
        public void setItem(final T item) {
            this.item = item;
            ComparatorAdjuster.reset(this, item);
        }
    }

    /**
     * {@code SequentialComparableLocator} is an implementation of a sequential locator that uses the {@link Comparable#compareTo(Object)} method.
     *
     * @param <T> the item and array type.
     *
     * @see SequentialLocator
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class SequentialComparableLocator<T extends Comparable<? super T>>
    extends SequentialLocator<T>
    {
        /**
         * Creates and returns a sequential comparable locator that iterates backward over the specified array looking for the specified item.
         *
         * @param <S> the item and array type.
         *
         * @param item the locator item.
         * @param array the array.
         *
         * @return the backward sequential comparable locator.
         */
        public static <S extends Comparable<? super S>>
        SequentialComparableLocator<S> backward(
            final S item,
            final S[] array
            ) {
            return new SequentialComparableLocator<>(item, array, -1);
        }

        /**
         * Creates and returns a sequential comparable locator that iterates forward over the specified array looking for the specified item.
         *
         * @param <S> the item and array type.
         *
         * @param item the locator item.
         * @param array the array.
         *
         * @return the forward sequential comparable locator.
         */
        public static <S extends Comparable<? super S>>
        SequentialComparableLocator<S> forward(
            final S item,
            final S[] array
            ) {
            return new SequentialComparableLocator<>(item, array);
        }

        /**
         * Creates a sequential locator with the specified comparable item, array, iteration start and end index, step, and comparator.
         * <p/>
         * This implementation calls the item's {@link Comparable#compareTo(Object)} method internally, unless the item is null; in that case, the same method is called on each array element instead.
         *
         * @param item the locator item.
         * @param array the array.
         * @param start the iteration start index. (inclusive)
         * @param end the iteration end index. (exclusive)
         * @param step the iteration step.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public
        SequentialComparableLocator(
            final T item,
            final T[] array,
            final int start,
            final int end,
            final int step
            ) {
            super(item, array, start, end, step, null);
            ComparatorAdjuster.reset(this, item);
        }

        /**
         * Creates a sequential locator with the specified comparable item, array, and iteration step.
         * <p/>
         * This implementation calls the item's {@link Comparable#compareTo(Object)} method internally, unless the item is null; in that case, the same method is called on each array element instead.
         *
         * @param item the locator item.
         * @param step the iteration step.
         * @param array the array.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public
        SequentialComparableLocator(
            final T item,
            final T[] array,
            final int step
            ) {
            this(item, array, 0, array.length, step);
        }

        /**
         * Creates a forward linear sequential locator with the specified comparable item and array.
         * <p/>
         * This implementation calls the item's {@link Comparable#compareTo(Object)} method internally, unless the item is null; in that case, the same method is called on each array element instead.
         *
         * @param item the locator item.
         * @param array the array.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public
        SequentialComparableLocator(
            final T item,
            final T[] array
            ) {
            this(item, array, 1);
        }

        /**
         * Sets the item and resets the comparator.
         *
         * @see SequentialComparableLocator
         */
        @Override
        public void setItem(final T item) {
            this.item = item;
            ComparatorAdjuster.reset(this, item);
        }
    }

    /**
     * {@code SequentialFinder} is an implementation of a finder that iterates sequentially over an array.
     *
     * @param <T> the item and array type.
     *
     * @see Finder
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class SequentialFinder<T>
    extends Finder<T>
    {
        /** The iteration step. */
        protected
        int step;

        /** The "last match shifted" supplier. */
        protected
        BooleanSupplier lastMatchShifted;

        /** The "next match in bounds" supplier. */
        protected
        BooleanSupplier nextMatchInBounds;

        /**
         * Creates and returns a sequential finder that iterates backward over the specified array looking for the specified item using the specified comparator.
         *
         * @param <S> the item and array type.
         *
         * @param item the finder item.
         * @param array the array.
         * @param comparator the comparator.
         *
         * @return the backward sequential finder.
         */
        public static <S>
        SequentialFinder<S> backward(
            final S item,
            final S[] array,
            final Comparator<? super S> comparator
            ) {
            return new SequentialFinder<>(item, array, -1, comparator);
        }

        /**
         * Creates and returns a sequential finder that iterates forward over the specified array looking for the specified item using the specified comparator.
         *
         * @param <S> the item and array type.
         *
         * @param item the locator item.
         * @param array the array.
         * @param comparator the comparator.
         *
         * @return the forward sequential finder.
         */
        public static <S>
        SequentialFinder<S> forward(
            final S item,
            final S[] array,
            final Comparator<? super S> comparator
            ) {
            return new SequentialFinder<>(item, array, comparator);
        }

        /**
         * Creates a sequential finder with the specified item, array, iteration start and end index, step, and comparator.
         * <p/>
         * This constructor accepts null comparator with the promise that it will be set later on prior to finding an element.
         *
         * @param item the finder item.
         * @param array the array.
         * @param start the iteration start index. (inclusive)
         * @param end the iteration end index. (exclusive)
         * @param step the iteration step.
         * @param comparator the comparator.
         *
         * @throws IllegalArgumentException if the array is null, or the step is zero.
         */
        public
        SequentialFinder(
            final T item,
            final T[] array,
            final int start,
            final int end,
            final int step,
            final Comparator<? super T> comparator
            ) {
            super(array, comparator);
            setItem(item);
            setStartIndex(start);
            setEndIndex(end);
            setStep(step);
            setIndex(step > 0
                     ? start
                     : end - 1);
            j = i;
            resetNextMatchSuppliers();
        }

        /**
         * Creates a sequential finder with the specified item, array, and comparator.
         * <p/>
         * This constructor accepts null comparator with the promise that it will be set later on prior to finding an element.
         *
         * @param item the finder item.
         * @param array the array.
         * @param step the iteration step.
         * @param comparator the comparator.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public
        SequentialFinder(
            final T item,
            final T[] array,
            final int step,
            final Comparator<? super T> comparator
            ) {
            this(item, array, 0, array.length, step, comparator);
        }

        /**
         * Creates a forward linear sequential finder with the specified item, array, and comparator.
         * <p/>
         * This constructor accepts null comparator with the promise that it will be set later on prior to finding an element.
         *
         * @param item the locator item.
         * @param array the array.
         * @param comparator the comparator.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public
        SequentialFinder(
            final T item,
            final T[] array,
            final Comparator<? super T> comparator
            ) {
            this(item, array, 1, comparator);
        }

        /**
         * {@inheritDoc}
         *
         * @return true if next match exists, or false otherwise.
         */
        @Override
        protected
        boolean nextMatchExists() {
            if (lastMatchShifted.getAsBoolean()) {
                boolean found = comparator.compare(item, array[i]) == 0;
                j = i + step;
                while (nextMatchInBounds.getAsBoolean() && !found) {
                    found = comparator.compare(item, array[j]) == 0;
                    j += step;
                }

                return found;
            }

            return false;
        }

        /**
         * {@inheritDoc}
         *
         * @return true if the item is found, and false otherwise.
         *
         * @throws NoSuchElementException if the iteration has no more elements.
         * @throws NullPointerException if the comparator is null, or an argument is null and the comparator does not permit null arguments.
         * @throws ClassCastException if the comparator is null, or the arguments' types prevent them from being compared by the comparator.
         *
         * @see Comparator#compare(Object, Object)
         */
        @Override
        public T next() {
            if (lastMatchShifted.getAsBoolean()) {
                if (nextMatchInBounds.getAsBoolean()) {
                    i = j;
                    if (i >= start && i < end )
                        return array[i];
                }
            }
            else {
                j = i;
                hasNext();
                i += step;
                return next();
            }

            throw new NoSuchElementException();
        }

        /**
         * Resets the 'last match shifted' and 'next match in bounds' suppliers based on the step direction.
         */
        protected
        void resetNextMatchSuppliers() {
            if (step > 0) {
                lastMatchShifted = () -> i <= j;
                nextMatchInBounds = () -> j < end;
            }
            else {
                lastMatchShifted = () -> i >= j;
                nextMatchInBounds = () -> j >= start;
            }
        }

        /**
         * Returns the iteration step.
         *
         * @return the iteration step.
         */
        public
        int getStep() {
            return step;
        }

        /**
         * Sets the iteration step.
         *
         * @param step the iteration step.
         *
         * @throws IllegalArgumentException if the step is zero.
         */
        public
        void setStep(
            final int step
            ) {
            if (step == 0)
                throw new IllegalArgumentException();

            this.step = step;
            resetNextMatchSuppliers();
        }
    }

    /**
     * {@code SequentialLocator} is an implementation of a locator that iterates sequentially over an array.
     *
     * @param <T> the item and array type.
     *
     * @see Locator
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class SequentialLocator<T>
    extends Locator<T>
    {
        /** The iteration step. */
        protected
        int step;

        /**
         * Creates and returns a sequential locator that iterates backward over the specified array looking for the specified item using the specified comparator.
         *
         * @param <S> the item and array type.
         *
         * @param item the locator item.
         * @param array the array.
         * @param comparator the comparator.
         *
         * @return the backward sequential locator.
         */
        public static <S>
        SequentialLocator<S> backward(
            final S item,
            final S[] array,
            final Comparator<? super S> comparator
            ) {
            return new SequentialLocator<>(item, array, -1, comparator);
        }

        /**
         * Creates and returns a sequential locator that iterates forward over the specified array looking for the specified item using the specified comparator.
         *
         * @param <S> the item and array type.
         *
         * @param item the locator item.
         * @param array the array.
         * @param comparator the comparator.
         *
         * @return the forward sequential locator.
         */
        public static <S>
        SequentialLocator<S> forward(
            final S item,
            final S[] array,
            final Comparator<? super S> comparator
            ) {
            return new SequentialLocator<>(item, array, comparator);
        }

        /**
         * Creates a sequential locator with the specified item, array, iteration start and end index, step, and comparator.
         * <p/>
         * This constructor accepts null comparator with the promise that it will be set later on prior to locating an element.
         *
         * @param item the locator item.
         * @param array the array.
         * @param start the iteration start index. (inclusive)
         * @param end the iteration end index. (exclusive)
         * @param step the iteration step.
         * @param comparator the comparator.
         *
         * @throws IllegalArgumentException if the array is null, or the step is zero.
         */
        public
        SequentialLocator(
            final T item,
            final T[] array,
            final int start,
            final int end,
            final int step,
            final Comparator<? super T> comparator
            ) {
            super(array, comparator);
            setItem(item);
            setStartIndex(start);
            setEndIndex(end);
            setStep(step);
            setIndex(step > 0
                     ? start
                     : end - 1);
        }

        /**
         * Creates a sequential locator with the specified item, array, and comparator.
         * <p/>
         * This constructor accepts null comparator with the promise that it will be set later on prior to locating an element.
         *
         * @param item the locator item.
         * @param array the array.
         * @param step the iteration step.
         * @param comparator the comparator.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public
        SequentialLocator(
            final T item,
            final T[] array,
            final int step,
            final Comparator<? super T> comparator
            ) {
            this(item, array, 0, array.length, step, comparator);
        }

        /**
         * Creates a forward linear sequential locator with the specified item, array, and comparator.
         * <p/>
         * This constructor accepts null comparator with the promise that it will be set later on prior to locating an element.
         *
         * @param item the locator item.
         * @param array the array.
         * @param comparator the comparator.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public
        SequentialLocator(
            final T item,
            final T[] array,
            final Comparator<? super T> comparator
            ) {
            this(item, array, 1, comparator);
        }

        /**
         * {@inheritDoc}
         *
         * @return true if the item is found, and false otherwise.
         *
         * @throws NoSuchElementException if the iteration has no more elements.
         * @throws NullPointerException if the comparator is null, or an argument is null and the comparator does not permit null arguments.
         * @throws ClassCastException if the comparator is null, or the arguments' types prevent them from being compared by the comparator.
         *
         * @see Comparator#compare(Object, Object)
         */
        @Override
        public Boolean next() {
            if (hasNext) {
                found = comparator.compare(item, array[i]) == 0;
                i += step;
                return found;
            }

            throw new NoSuchElementException();
        }

        /** {@inheritDoc} */
        @Override
        protected void updateHasNext() {
            hasNext = i >= start && i < end;
        }

        /**
         * Returns the iteration step.
         *
         * @return the iteration step.
         */
        public
        int getStep() {
            return step;
        }

        /**
         * Sets the iteration step.
         *
         * @param step the iteration step.
         *
         * @throws IllegalArgumentException if the step is zero.
         */
        public
        void setStep(
            final int step
            ) {
            if (step == 0)
                throw new IllegalArgumentException();

            this.step = step;
        }
    }
}