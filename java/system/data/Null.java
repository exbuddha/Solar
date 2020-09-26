package system.data;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * {@code Null} classifies null data types.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Null
{
    /**
     * {@code List} classifies a null iterable.
     *
     * @param <T> the type of elements returned by the iterator.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Iterable<T>
    extends java.lang.Iterable<T>
    {
        /**
         * The optional {@code NoSuchElementException} message.
         * <p/>
         * This implementation returns null.
         *
         * @return the exception message.
         */
        default String msg() {
            return null;
        }

        @Override
        default Iterator<T> iterator() {
            return new Iterator<T>() {
                       @Override
                       public boolean hasNext() {
                             return false;
                         }

                       @Override
                       public T next() {
                             throw new NoSuchElementException(msg());
                         }
                   };
        }
    }

    /**
     * {@code Iterator} classifies a null iterator.
     *
     * @param <T> the type of elements returned by this iterator.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Iterator<T>
    extends java.util.Iterator<T>
    {
        @Override
        default boolean hasNext() {
            return false;
        }

        @Override
        default T next() {
            throw new NoSuchElementException();
        }
    }

    /**
     * {@code List} classifies a null list.
     *
     * @param <T> the type of elements in the list.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface List<T>
    extends java.util.List<T>
    {
        @Override
        default boolean add(T t) {
            return false;
        }

        @Override
        default void add(int index, T element) {

        }

        @Override
        default boolean addAll(Collection<? extends T> c) {
            return false;
        }

        @Override
        default boolean addAll(int index, Collection<? extends T> c) {
            return false;
        }

        @Override
        default void clear() {

        }

        @Override
        default boolean contains(Object o) {
            return false;
        }

        @Override
        default boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        default boolean equals(Object o) {
            return false;
        }

        @Override
        default T get(int index) {
            return null;
        }

        @Override
        default int hashCode() {
            return 0;
        }

        @Override
        default int indexOf(Object o) {
            return 0;
        }

        @Override
        default boolean isEmpty() {
            return false;
        }

        @Override
        default java.util.Iterator<T> iterator() {
            return new Iterator<T>() {};
        }

        @Override
        default int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        default java.util.ListIterator<T> listIterator() {
            return new ListIterator<T>() {};
        }

        @Override
        default java.util.ListIterator<T> listIterator(int index) {
            return new ListIterator<T>() {};
        }

        @Override
        default boolean remove(Object o) {
            return false;
        }

        @Override
        default T remove(int index) {
            return null;
        }

        @Override
        default boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        default boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        default T set(int index, T element) {
            return null;
        }

        @Override
        default int size() {
            return 0;
        }

        @Override
        default Object[] toArray() {
            return new Object[0];
        }

        @Override
        default <T1> T1[] toArray(T1[] a) {
            return null;
        }

        @Override
        default java.util.List<T> subList(int fromIndex, int toIndex) {
            return this;
        }
    }

    /**
     * {@code ListIterator} classifies a null list iterator.
     *
     * @param <T> the type of elements returned by this iterator.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface ListIterator<T>
    extends
        java.util.Iterator<T>,
        java.util.ListIterator<T>
    {
        @Override
        default void add(T t) {}

        @Override
        default boolean hasNext() {
            return false;
        }

        @Override
        default boolean hasPrevious() {
            return false;
        }

        @Override
        default T next() {
            throw new NoSuchElementException();
        }

        @Override
        default int nextIndex() {
            return 0;
        }

        @Override
        default T previous() {
            throw new NoSuchElementException();
        }

        @Override
        default int previousIndex() {
            return 0;
        }

        @Override
        default void remove() {}

        @Override
        default void set(T t) {}
    }

    /**
     * {@code XMLNode} classifies a null XML node.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface XMLNode
    extends org.w3c.dom.Node
    {
        @Override
        default String getNodeName() {
            return null;
        }

        @Override
        default String getNodeValue() throws org.w3c.dom.DOMException {
            return null;
        }

        @Override
        default void setNodeValue(String nodeValue) throws org.w3c.dom.DOMException {}

        @Override
        default short getNodeType() {
            return 0;
        }

        @Override
        default org.w3c.dom.Node getParentNode() {
            return null;
        }

        @Override
        default org.w3c.dom.NodeList getChildNodes() {
            return null;
        }

        @Override
        default org.w3c.dom.Node getFirstChild() {
            return null;
        }

        @Override
        default org.w3c.dom.Node getLastChild() {
            return null;
        }

        @Override
        default org.w3c.dom.Node getPreviousSibling() {
            return null;
        }

        @Override
        default org.w3c.dom.Node getNextSibling() {
            return null;
        }

        @Override
        default org.w3c.dom.NamedNodeMap getAttributes() {
            return null;
        }

        @Override
        default org.w3c.dom.Document getOwnerDocument() {
            return null;
        }

        @Override
        default org.w3c.dom.Node insertBefore(org.w3c.dom.Node newChild, org.w3c.dom.Node refChild) throws org.w3c.dom.DOMException {
            return null;
        }

        @Override
        default org.w3c.dom.Node replaceChild(org.w3c.dom.Node newChild, org.w3c.dom.Node oldChild) throws org.w3c.dom.DOMException {
            return null;
        }

        @Override
        default org.w3c.dom.Node removeChild(org.w3c.dom.Node oldChild) throws org.w3c.dom.DOMException {
            return null;
        }

        @Override
        default org.w3c.dom.Node appendChild(org.w3c.dom.Node newChild) throws org.w3c.dom.DOMException {
            return null;
        }

        @Override
        default boolean hasChildNodes() {
            return false;
        }

        @Override
        default org.w3c.dom.Node cloneNode(boolean deep) {
            return null;
        }

        @Override
        default void normalize() {}

        @Override
        default boolean isSupported(String feature, String version) {
            return false;
        }

        @Override
        default String getNamespaceURI() {
            return null;
        }

        @Override
        default String getPrefix() {
            return null;
        }

        @Override
        default void setPrefix(String prefix) throws org.w3c.dom.DOMException {}

        @Override
        default String getLocalName() {
            return null;
        }

        @Override
        default boolean hasAttributes() {
            return false;
        }

        @Override
        default String getBaseURI() {
            return null;
        }

        @Override
        default short compareDocumentPosition(org.w3c.dom.Node other) throws org.w3c.dom.DOMException {
            return 0;
        }

        @Override
        default String getTextContent() throws org.w3c.dom.DOMException {
            return null;
        }

        @Override
        default void setTextContent(String textContent) throws org.w3c.dom.DOMException {}

        @Override
        default boolean isSameNode(org.w3c.dom.Node other) {
            return false;
        }

        @Override
        default String lookupPrefix(String namespaceURI) {
            return null;
        }

        @Override
        default boolean isDefaultNamespace(String namespaceURI) {
            return false;
        }

        @Override
        default String lookupNamespaceURI(String prefix) {
            return null;
        }

        @Override
        default boolean isEqualNode(org.w3c.dom.Node arg) {
            return false;
        }

        @Override
        default Object getFeature(String feature, String version) {
            return null;
        }

        @Override
        default Object setUserData(String key, Object data, org.w3c.dom.UserDataHandler handler) {
            return null;
        }

        @Override
        default Object getUserData(String key) {
            return null;
        }
    }
}