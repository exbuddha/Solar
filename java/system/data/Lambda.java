package system.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
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
     * Returns the factorial of the number.
     * <p>
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
     * <p>
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
        final Comparator<T> comparator
        ) {
        return array == null
               ? null
               : SequentialLocator.forward(item, array, comparator).result(null);
    }

    /**
     * Returns the first occurrence of the specified item in the array; or null if item is not found or the array is null.
     * <p>
     * This implementation calls {@link Comparable#compareTo(Object)} on the item and passes each array element as the argument of that method.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param array the array.
     *
     * @return the array element, or null if not found.
     */
    public static <T extends Comparable<T>>
    T findFirst(
        final T item,
        final T[] array
        ) {
        return array == null
               ? null
               : SequentialComparableLocator.forward(item, array).result(null);
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
               : SequentialLocator.forward(null, array, new Comparator<T>() {
                                                            @Override
                                                            public int compare(final T n, final T element) {
                                                                return comparator.apply(element)
                                                                       ? 0
                                                                       : 1;
                                                            }
                                                        })
                 .result(null);
    }

    /**
     * Returns index of the first occurrence of the specified item in the array using the specified comparator; or null if item is not found, the array is null or the comparator is null.
     * <p>
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
        final Comparator<T> comparator
        ) {
        return array == null
               ? null
               : SequentialLocator.forward(item, array, comparator).position(null);
    }

    /**
     * Returns index of the first occurrence of the specified item in the array; or null if item is not found or the array is null.
     * <p>
     * This implementation calls {@link Comparable#compareTo(Object)} on the item and passes each array element as the argument of that method.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param array the array.
     *
     * @return the array element index, or null if not found.
     */
    public static <T extends Comparable<T>>
    Integer findFirstIndex(
        final T item,
        final T[] array
        ) {
        return array == null
               ? null
               : SequentialComparableLocator.forward(item, array).position(null);
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
               : SequentialLocator.forward(null, array, new Comparator<T>() {
                                                            @Override
                                                            public int compare(final T n, final T element) {
                                                                return comparator.apply(element)
                                                                       ? 0
                                                                       : 1;
                                                            }
                                                        })
                 .position(null);
    }

    /**
     * Returns the last occurrence of the specified item in the array using the specified comparator; or null if item is not found, the array is null, or the comparator is null.
     * <p>
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
        final Comparator<T> comparator
        ) {
        return array == null
               ? null
               : SequentialLocator.backward(item, array, comparator).result(null);
    }

    /**
     * Returns the last occurrence of the specified item in the array; or null if item is not found or the array is null.
     * <p>
     * This implementation calls {@link Comparable#compareTo(Object)} on the item and passes each array element as the argument of that method.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param array the array.
     *
     * @return the array element, or null if not found.
     */
    public static <T extends Comparable<T>>
    T findLast(
        final T item,
        final T[] array
        ) {
        return array == null
               ? null
               : SequentialComparableLocator.backward(item, array).result(null);
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
               : SequentialLocator.backward(null, array, new Comparator<T>() {
                                                             @Override
                                                             public int compare(final T n, final T element) {
                                                                 return comparator.apply(element)
                                                                        ? 0
                                                                        : 1;
                                                             }
                                                         })
                 .result(null);
    }

    /**
     * Returns index of the last occurrence of the specified item in the array using the specified comparator; or null if item is not found, the array is null or the comparator is null.
     * <p>
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
        final Comparator<T> comparator
        ) {
        return array == null
               ? null
               : SequentialLocator.backward(item, array, comparator).position(null);
    }

    /**
     * Returns index of the last occurrence of the specified item in the array; or null if item is not found or the array is null.
     * <p>
     * This implementation calls {@link Comparable#compareTo(Object)} on the item and passes each array element as the argument of that method.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param array the array.
     *
     * @return the array element index, or null if not found.
     */
    public static <T extends Comparable<T>>
    Integer findLastIndex(
        final T item,
        final T[] array
        ) {
        return array == null
               ? null
               : SequentialComparableLocator.backward(item, array).position(null);
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
               : SequentialLocator.backward(null, array, new Comparator<T>() {
                                                             @Override
                                                             public int compare(final T n, final T element) {
                                                                 return comparator.apply(element)
                                                                        ? 0
                                                                        : 1;
                                                             }
                                                         })
                 .position(null);
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
     * <p>
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
        final Comparator<T> comparator
        ) {
        return sortedArray == null
               ? null
               : new BinaryLocator<T>(item, sortedArray, comparator::compare).result(null);
    }

    /**
     * Returns the first encountered occurrence of the specified item in the sorted array; or null if the item is not found or the array is null.
     * <p>
     * This implementation calls {@link Comparable#compareTo(Object)} on the item and passes each array element as the argument of that method.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param sortedArray the sorted array.
     *
     * @return the array element, or null if the item is not found or the array is null.
     */
    public static <T extends Comparable<T>>
    T sortedFind(
        final T item,
        final T[] sortedArray
        ) {
        return sortedArray == null
               ? null
               : new BinaryComparableLocator<T>(item, sortedArray).result(null);
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
               : new BinaryLocator<T>(null, sortedArray, new Comparator<T>() {
                                                             @Override
                                                             public int compare(final T n, final T element) {
                                                                 return comparator.apply(element)
                                                                        ? 0
                                                                        : 1;
                                                             }
                                                         })
                 .result(null);
    }

    /**
     * Returns index of the first encountered occurrence of the specified item in the sorted array using the specified comparator; or null if the item is not found, the array is null, or the comparator is null.
     * <p>
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
        final Comparator<T> comparator
        ) {
        return sortedArray == null
               ? null
               : new BinaryLocator<T>(item, sortedArray, comparator::compare).position(null);
    }

    /**
     * Returns index of the first encountered occurrence of the specified item in the sorted array; or null if the item is not found or the array is null.
     * <p>
     * This implementation calls {@link Comparable#compareTo(Object)} internally.
     *
     * @param <T> the item and array type.
     * @param item the item.
     * @param sortedArray the sorted array.
     *
     * @return the array element index, or null if not found.
     */
    public static <T extends Comparable<T>>
    Integer sortedFindIndex(
        final T item,
        final T[] sortedArray
        ) {
        return sortedArray == null
               ? null
               : new BinaryComparableLocator<T>(item, sortedArray).position(null);
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
               : new BinaryLocator<T>(null, sortedArray, new Comparator<T>() {
                                                             @Override
                                                             public int compare(final T n, final T element) {
                                                                 return comparator.apply(element)
                                                                        ? 0
                                                                        : 1;
                                                             }
                                                         })
                 .position(null);
    }

    /**
     * Converts an iterable into an array, or returns null if the iterable is null.
     * <p>
     * This implementation iterates through the elements in the iterable once and returns an {@link ArrayList}.
     *
     * @param <T> the array type.
     * @param iterable the iterable.
     *
     * @return the array of elements, or null if iterable is null.
     */
    public static <T>
    T[] toArray(
        final Iterable<? extends T> iterable
        ) {
        return (T[]) toList(iterable).toArray();
    }

    /**
     * Converts an iterable into the supplied list type, or returns null if iterable is null.
     * <p>
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
        iterable.forEach(
            item -> list.add(item)
            );

        return list;
    }

    /**
     * Converts an iterable into a list, or returns null if the iterable is null.
     * <p>
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
    class BinaryComparableLocator<T extends Comparable<T>>
    extends BinaryLocator<T>
    {
        /**
         * Creates a binary locator with the specified comparable item, sorted array, and ascending flag.
         * <p>
         * This implementation calls the item's {@link Comparable#compareTo(Object)} method internally, unless the item is null; in that case, the same method is called on each array element instead.
         * <p>
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
         * <p>
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
         * This implementation is empty.
         */
        @Override
        protected void validateComparator() {}

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
     * {@code BinaryLocator} is an implementation of a locator that iterates over a sorted array in a binary search manner.
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
        /** The locator item. */
        protected
        T item;

        /** The iteration lower bound. */
        protected
        int lo;

        /** The iteration upper bound. */
        protected
        int hi;

        /** The iteration index. */
        protected
        int mid;

        /** The comparator supplier. */
        private
        Supplier<Integer> comp;

        /** The array sort order. */
        protected final
        Boolean ascending;

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
            setIndex((lo + hi) / 2);
            this.ascending = ascending;
        }

        /**
         * Creates a binary locator with the specified item, sorted array, ascending flag, and comparator.
         * <p>
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
            final Comparator<T> comparator
            ) {
            super(array, comparator);
            setItem(item);
            setStartIndex(0);
            setEndIndex(array.length);
            setIndex((lo + hi) / 2);
            this.ascending = ascending;
            resetComparatorSupplier(comparator);
        }

        /**
         * Creates a binary locator with the specified item, sorted array, and comparator.
         * <p>
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
            final Comparator<T> comparator
            ) {
            this(item, array, array.length > 1 && comparator.compare(array[0], array[array.length - 1]) < 0, comparator);
        }

        /**
         * Creates and sets the comparator supplier based on the sort order of the array.
         *
         * @param comparator the comparator.
         *
         * @throws NullPointerException if the comparator is null.
         */
        protected
        void resetComparatorSupplier(
            final Comparator<T> comparator
            ) {
            if (ascending != null)
                comp = ascending
                       ? new Supplier<Integer>() {
                             @Override
                             public Integer get() {
                                 return comparator.compare(BinaryLocator.this.item, array[getIndex()]);
                             }
                         }
                       : new Supplier<Integer>() {
                             @Override
                             public Integer get() {
                                 return comparator.compare(array[getIndex()], BinaryLocator.this.item);
                             }
                         };
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
                if (result == 0)
                    return true;

                if (result > 0)
                    lo = mid + 1;
                else
                    hi = mid;

                mid = (lo + hi) / 2;
                return false;
            }

            throw new NoSuchElementException();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void updateHasNext() {
            hasNext = lo <= mid && hi > mid && lo < hi;
        }

        /**
         * {@inheritDoc}
         *
         * @return the iteration end index.
         */
        @Override
        public int getEndIndex() {
            return hi;
        }

        /**
         * {@inheritDoc}
         *
         * @return the iteration index.
         */
        @Override
        public int getIndex() {
            return mid;
        }

        /**
         * {@inheritDoc}
         *
         * @return the locator item.
         */
        @Override
        public T getItem() {
            return item;
        }

        /**
         * {@inheritDoc}
         *
         * @return the iteration start index.
         */
        @Override
        public int getStartIndex() {
            return lo;
        }

        /**
         * {@inheritDoc}
         * <p>
         * This implementation resets the comparator to according to the array sort order.
         *
         * @param comparator the comparator.
         *
         * @see #validateComparator()
         * @see #resetComparatorSupplier(Comparator)
         */
        public
        void setComparator(
            Comparator<T> comparator
            ) {
            this.comparator = comparator;
            validateComparator();
            resetComparatorSupplier(this.comparator);
        }

        /**
         * {@inheritDoc}
         * <p>
         * This implementation calls {@link #validateEndIndex()} and {@link #updateHasNext()} internally.
         *
         * @param end the iteration end index.
         */
        @Override
        public void setEndIndex(final int end) {
            hi = end;
            validateEndIndex();
            updateHasNext();
        }

        /**
         * {@inheritDoc}
         * <p>
         * This implementation calls {@link #updateHasNext()} internally.
         *
         * @param index the iteration index.
         */
        @Override
        public void setIndex(final int index) {
            mid = index;
            updateHasNext();
        }

        /**
         * {@inheritDoc}
         *
         * @param item the locator item.
         */
        @Override
        public void setItem(final T item) {
            this.item = item;
        }

        /**
         * {@inheritDoc}
         * <p>
         * This implementation calls {@link #validateStartIndex()} and {@link #updateHasNext()} internally.
         *
         * @param start the iteration start index.
         */
        @Override
        public void setStartIndex(final int start) {
            lo = start;
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
        static <T extends Comparable<T>>
        void reset(
            final Locator<T> locator,
            final T item
            ) {
            locator.comparator = item == null
                                 ? new Comparator<T>() {
                                       @Override
                                       public int compare(final T item, final T element) {
                                           return element == null
                                                  ? 0
                                                  : -element.compareTo(item);
                                       }
                                   }
                                 : new Comparator<T>() {
                                       @Override
                                       public int compare(final T item, final T element) {
                                           return item.compareTo(element);
                                       }
                                   };
        }
    }

    /**
     * {@code Locator} represents a specialized iterator over an array for locating items within the array, using a {@link Comparator#compare(Object, Object)} method, and providing the position or the array element.
     *
     * @param <T> the array type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract static
    class Locator<T>
    implements Iterator<Boolean>
    {
        /** The array. */
        protected final
        T[] array;

        /** The comparator. */
        protected
        Comparator<T> comparator;

        /** The "location found" flag. */
        protected
        boolean found;

        /** The "next element exists" flag. */
        protected
        boolean hasNext;

        /**
         * Creates a locator with the specified array and comparator.
         *
         * @param array the array.
         * @param comparator the comparator.
         *
         * @throws IllegalArgumentException if the array is null.
         */
        public
        Locator(
            final T[] array,
            final Comparator<T> comparator
            ) {
            this.array = array;
            validateArray();
            setComparator(comparator);
            found = false;
        }

        /**
         * Returns the located array element if found; otherwise throws a {@code NoSuchElementException}.
         *
         * @return the located array element.
         *
         * @throws NoSuchElementException if the array element is not found or the iteration has no more elements.
         */
        public
        T element() {
            if (found)
                return array[getIndex()];

            throw new NoSuchElementException();
        }

        /**
         * Advances the iteration until the item is found in the array and returns this locator.
         * If the array or the comparator is null, the locator is returned immediately.
         *
         * @return the advanced locator.
         */
        public
        Locator<T> found() {
            if (array == null || comparator == null)
                return this;

            while (hasNext() && !next());
            return this;
        }

        /**
         * Returns the located array element index if found; otherwise throws a {@code NoSuchElementException}.
         *
         * @return the located array element index.
         *
         * @throws NoSuchElementException if the array element is not found or the iteration has no more elements.
         */
        public
        Integer index() {
            if (found)
                return getIndex();

            throw new NoSuchElementException();
        }

        /**
         * Returns the index of the located array element if found; otherwise returns the specified fallback integer.
         *
         * @param fallback the fallback.
         *
         * @return the located array element index or the fallback.
         */
        public
        Integer position(
            final Integer fallback
            ) {
            found();
            return found ? index() : fallback;
        }

        /**
         * Returns the located array element if found; otherwise returns the specified fallback.
         *
         * @param fallback the fallback.
         *
         * @return the located array element or the fallback.
         */
        public
        T result(
            final T fallback
            ) {
            found();
            return found ? element() : fallback;
        }

        /**
         * Returns true if the locator item is found; otherwise returns false.
         *
         * @return true if the locator item is found, and false otherwise.
         */
        public
        boolean itemFound() {
            return found;
        }

        /**
         * Updates the internal {@code hasNext} flag and return its value indicating if there are more elements in the iteration.
         * <p>
         * This implementation calls {@link #updateHasNext()} internally.
         *
         * @return the hasNext flag value.
         */
        @Override
        public boolean hasNext() {
            updateHasNext();
            return hasNext;
        }

        /**
         * Returns true if the array element matching the item is found at the next location; otherwise return false.
         *
         * @return true if the item is found, and false otherwise.
         */
        @Override
        public abstract Boolean next();

        /**
         * Updates the internal {@code hasNext} flag indicating if there are more elements in the iteration.
         * <p>
         * This method is called inside {@link #hasNext()} and everywhere else updating is required.
         */
        protected abstract
        void updateHasNext();

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
        Comparator<T> getComparator() {
            return comparator;
        }

        /**
         * Returns the iteration end index.
         *
         * @return the iteration end index.
         */
        public abstract
        int getEndIndex();

        /**
         * Returns the iteration index.
         *
         * @return the iteration index.
         */
        public abstract
        int getIndex();

        /**
         * Returns the locator item.
         *
         * @return the locator item.
         */
        public abstract
        T getItem();

        /**
         * Returns the iteration start index.
         *
         * @return the iteration start index.
         */
        public abstract
        int getStartIndex();

        /**
         * Sets and validates the comparator.
         * <p>
         * This implementation calls {@link #validateComparator()} internally.
         *
         * @param comparator the comparator.
         */
        public
        void setComparator(
            Comparator<T> comparator
            ) {
            this.comparator = comparator;
            validateComparator();
        }

        /**
         * Sets the iteration end index. (exclusive)
         *
         * @param end the iteration end index.
         */
        public abstract
        void setEndIndex(
            int end
            );

        /**
         * Sets the iteration index.
         *
         * @param index the iteration index.
         */
        public abstract
        void setIndex(
            int index
            );

        /**
         * Sets the locator item.
         *
         * @param item the locator item.
         */
        public abstract
        void setItem(
            T item
            );

        /**
         * Sets the iteration start index. (inclusive)
         *
         * @param start the iteration start index.
         */
        public abstract
        void setStartIndex(
            int start
            );
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
    class SequentialComparableLocator<T extends Comparable<T>>
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
        public static <S extends Comparable<S>>
        SequentialComparableLocator<S> backward(
            final S item,
            final S[] array
            ) {
            return new SequentialComparableLocator<>(item, array, array.length, 0, -1);
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
        public static <S extends Comparable<S>>
        SequentialComparableLocator<S> forward(
            final S item,
            final S[] array
            ) {
            return new SequentialComparableLocator<>(item, array);
        }

        /**
         * Creates a sequential locator with the specified comparable item, array, iteration start and end index, step, and comparator.
         * <p>
         * This implementation calls the item's {@link Comparable#compareTo(Object)} method internally, unless the item is null; in that case, the same method is called on each array element instead.
         *
         * @param item the locator item.
         * @param array the array.
         * @param start the iteration start index.
         * @param end the iteration end index.
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
         * Creates a sequential locator with the specified comparable item and array.
         * <p>
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
            this(item, array, 0, array.length, 1);
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
        /** The locator item. */
        protected
        T item;

        /** The iteration start index. (inclusive) */
        protected
        int start;

        /** The iteration end index. (exclusive) */
        protected
        int end;

        /** The iteration step. */
        protected
        int step;

        /** The iteration index. */
        protected
        int i;

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
            final Comparator<S> comparator
            ) {
            return new SequentialLocator<>(item, array, array.length, 0, -1, comparator);
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
            final Comparator<S> comparator
            ) {
            return new SequentialLocator<>(item, array, comparator);
        }

        /**
         * Creates a sequential locator with the specified item, array, iteration start and end index, step, and comparator.
         *
         * @param item the locator item.
         * @param array the array.
         * @param start the iteration start index.
         * @param end the iteration end index.
         * @param step the iteration step.
         * @param comparator the comparator.
         *
         * @throws IllegalArgumentException if the array or the comparator is null, or the step is zero.
         */
        public
        SequentialLocator(
            final T item,
            final T[] array,
            final int start,
            final int end,
            final int step,
            final Comparator<T> comparator
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
         *
         * @param item the locator item.
         * @param array the array.
         * @param comparator the comparator.
         *
         * @throws IllegalArgumentException if the array or the comparator is null.
         */
        public
        SequentialLocator(
            final T item,
            final T[] array,
            final Comparator<T> comparator
            ) {
            this(item, array, 0, array.length, 1, comparator);
        }

        /**
         * {@inheritDoc}
         *
         * @return true if the item is found, and false otherwise.
         *
         * @throws NoSuchElementException if the iteration has no more elements.
         * @throws NullPointerException if an argument is null and the comparator does not permit null arguments.
         * @throws ClassCastException if the comparator is null, or the arguments' types prevent them from being compared by the comparator.
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
         * {@inheritDoc}
         *
         * @return the iteration end index.
         */
        @Override
        public int getEndIndex() {
            return end;
        }

        /**
         * {@inheritDoc}
         *
         * @return the iteration index.
         */
        @Override
        public int getIndex() {
            return i;
        }

        /**
         * {@inheritDoc}
         *
         * @return the locator item.
         */
        @Override
        public T getItem() {
            return item;
        }

        /**
         * {@inheritDoc}
         *
         * @return the iteration start index.
         */
        @Override
        public int getStartIndex() {
            return start;
        }

        /**
         * {@inheritDoc}
         * <p>
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
         * <p>
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
         *
         * @param item the locator item.
         */
        @Override
        public void setItem(final T item) {
            this.item = item;
        }

        /**
         * {@inheritDoc}
         * <p>
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