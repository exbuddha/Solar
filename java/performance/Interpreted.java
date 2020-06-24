package performance;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

import system.data.Format.Interpretable.Document.Element;

/**
 * {@code Interpreted} classifies the entity in performance capable of translating elements in the score to effects in the aural field, or actions on the instrument, for performers.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Interpreted
extends musical.Interpreted
{
    /**
     * Returns the ordered set of music elements.
     *
     * @return the ordered set of elements.
     */
    public
    OrderedSet findElements();

    /**
     * Returns the performance class encapsulating the characteristics of a change target that is appropriate for the specified score notes in performance.
     *
     * @param music the score notes.
     * @return the performance class.
     */
    public
    Class<? super performance.system.Type<Interaction.Physical.Performance>> findPerformanceClass(
        Element... music
        );

    /**
     * {@code OrderedSet} classifies a sorted set with interpreted ordering.
     * <p>
     * Ordered sets are equivalent of java standard {@link SortedSet} with the twist that each element in the set can be attached to other adjacent elements creating a group with specific significance.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface OrderedSet
    extends SortedSet<Element>
    {
        /**
         * {@code Empty} is the default implementation of an empty ordered set.
         */
        public final
        OrderedSet Empty = new OrderedSet()
        {
            public final
            Object[] empty = new Object[0];

            @Override
            public Comparator<? super Element> comparator() {
                return new Comparator<Element>() {
                    @Override
                    public int compare(final Element o1, final Element o2) {
                        return 1;
                    }
                };
            }

            @Override
            public SortedSet<Element> subSet(final Element fromElement, final Element toElement) {
                return this;
            }

            @Override
            public SortedSet<Element> headSet(final Element toElement) {
                return this;
            }

            @Override
            public SortedSet<Element> tailSet(final Element fromElement) {
                return this;
            }

            @Override
            public Element first() {
                return null;
            }

            @Override
            public Element last() {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return true;
            }

            @Override
            public boolean contains(final Object obj) {
                return obj == null;
            }

            @Override
            public Iterator<Element> iterator() {
                return new Iterator<Element>()
                {
                    @Override
                    public boolean hasNext() {
                        return false;
                    }

                    @Override
                    public Element next() {
                        return null;
                    }
                };
            }

            @Override
            public Object[] toArray() {
                return empty;
            }

            @Override
            public <T> T[] toArray(final T[] a) {
                return (T[]) empty;
            }

            @Override
            public boolean add(final Element e) {
                return false;
            }

            @Override
            public boolean remove(final Object obj) {
                return false;
            }

            @Override
            public boolean containsAll(final Collection<?> col) {
                return false;
            }

            @Override
            public boolean addAll(final Collection<? extends Element> col) {
                return false;
            }

            @Override
            public boolean retainAll(final Collection<?> col) {
                return false;
            }

            @Override
            public boolean removeAll(final Collection<?> col) {
                return false;
            }

            @Override
            public void clear() {}
        };
    }
}