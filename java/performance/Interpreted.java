package performance;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

/**
 * {@code Interpreted} classifies the entity in performance capable of translating elements in the score to effects in the aural field, or actions on the instrument, for performers.
 */
public
interface Interpreted
extends
    musical.Interpreted,
    system.data.JSON.Element
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
        Node... music
        );

    /**
     * {@code OrderedSet} classifies a sorted set with interpreted ordering.
     * <p>
     * Ordered sets are equivalent of java standard {@link SortedSet} with the twist that each element in the set can be attached to other adjacent elements creating a group with specific significance.
     */
    public
    interface OrderedSet
    extends SortedSet<org.w3c.dom.Node>
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
            public Comparator<? super org.w3c.dom.Node> comparator() {
                return new Comparator<org.w3c.dom.Node>() {
                    @Override
                    public int compare(final org.w3c.dom.Node o1, final org.w3c.dom.Node o2) {
                        return 1;
                    }
                };
            }

            @Override
            public SortedSet<org.w3c.dom.Node> subSet(final org.w3c.dom.Node fromElement, final org.w3c.dom.Node toElement) {
                return this;
            }

            @Override
            public SortedSet<org.w3c.dom.Node> headSet(final org.w3c.dom.Node toElement) {
                return this;
            }

            @Override
            public SortedSet<org.w3c.dom.Node> tailSet(final org.w3c.dom.Node fromElement) {
                return this;
            }

            @Override
            public Node first() {
                return null;
            }

            @Override
            public Node last() {
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
            public Iterator<org.w3c.dom.Node> iterator() {
                return new Iterator<org.w3c.dom.Node>()
                {
                    @Override
                    public boolean hasNext() {
                        return false;
                    }

                    @Override
                    public org.w3c.dom.Node next() {
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
            public boolean add(final org.w3c.dom.Node e) {
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
            public boolean addAll(final Collection<? extends org.w3c.dom.Node> col) {
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