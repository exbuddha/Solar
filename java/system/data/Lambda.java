package system.data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * {@code Lambda} defines static members for performing common mathematical or algorithmic operations.
 */
public final
class Lambda
{
    /**
     * Returns the factorial of the number.
     * <p>
     * This implementation calls {@link Number#longValue()} on the number.
     *
     * @param n the number.
     * @return the factorial.
     */
    public static
    long factorial(
        final Number n
        ) {
        final long m = n.longValue();
        long factorial = 1L;
        if (m > 1)
            for (long k = 2L; k <= m; k++)
                factorial *= k;

        return factorial;
    }

    /**
     * Returns the greatest common denominator of the two non-negative long numbers.
     *
     * @param a the first number.
     * @param b the second number.
     * @return the greatest common denominator of a and b.
     */
    public static
    long gcd(
        final long a,
        final long b
        ) {
        if (b == 0)
            return a;
        else
            return gcd(b, a % b);
    }

    /**
     * Returns the minimum of the two specified integers.
     *
     * @param a the first integer.
     * @param b the second integer.
     * @return the minimum value.
     */
    public static
    int min(
        final int a,
        final int b
        ) {
        if (a < b)
            return a;

        return b;
    }

    /**
     * Performs in-place insertion sort on the array.
     *
     * @param <T> the array type.
     * @param array the array.
     */
    public static <T extends Comparable<T>>
    void sort(
        final T[] array
        ) {
        for (int i = 1; i < array.length; i++) {
            final T x = array[i];
            int j = i;
            for (;j > 0 && array[j - 1].compareTo(x) > 0; j--)
                array[j] = array[j - 1];

            array[j] = x;
        }
    }

    /**
     * Converts an iterable to an array, or returns null if iterable is null.
     *
     * @param <T> the array type.
     * @param iterable the iterable.
     * @return the array, or null if iterable is null.
     */
    public static <T>
    T[] toArray(
        final Iterable<? extends T> iterable
        ) {
        return iterable == null
               ? null
               : (T[]) toList(iterable).toArray();
    }

    /**
     * Converts an iterable to the supplied list type, or returns null if iterable is null.
     *
     * @param <T> the list element type.
     * @param iterable the iterable.
     * @param supplier the list supplier.
     * @return the list, or null if iterable is null.
     */
    public static <T>
    List<T> toList(
        final Iterable<? extends T> iterable,
        final Supplier<? extends List<T>> supplier
        ) {
        if (iterable == null)
            return null;

        final List<T> list = supplier.get();
        iterable.forEach(
            item -> list.add(item)
            );

        return list;
    }

    /**
     * Converts an iterable to a list, or returns null if iterable is null.
     * <p>
     * This implementation returns an {@code ArrayList}.
     *
     * @param <T> the list element type.
     * @param iterable the iterable.
     * @return the list, or null if iterable is null.
     */
    public static <T>
    List<T> toList(
        final Iterable<? extends T> iterable
        ) {
        return toList(iterable, ArrayList::new);
    }
}