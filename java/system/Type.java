package system;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.NullType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

/**
 * {@code Type} classifies singularly associable data types that are of, or can be of, other data types of their own kind, super-kind, or sub-kind.
 * <p>
 * One aim of this interface is to generate context by inheritance and comparison.
 *
 * @param <T> the data type.
 */
public
interface Type<T>
extends java.lang.reflect.Type
{
    /**
     * Returns true if the specified data type is of this type, and false otherwise.
     *
     * @param type the other type.
     * @return true if the specified date type is of this type, and false otherwise.
     */
    public
    boolean is(
        Type<T> type
        );

    /**
     * {@code Null} classifies null data types inherently lacking any specific significance other than locally defined representations that are usually high-level concepts themselves.
     */
    public
    interface Null
    extends NullType
    {
        @Override
        public default <R, P> R accept(TypeVisitor<R, P> v, P p) { return null; }

        @Override
        public default <A extends Annotation> A getAnnotation(Class<A> annotationType) { return null; }

        @Override
        public default List<? extends AnnotationMirror> getAnnotationMirrors() { return null; }

        @Override
        public default <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) { return null; }

        @Override
        public default TypeKind getKind() { return null; }

        /**
         * {@code Node} specifies null data types addressable in XML documents.
         */
        public
        interface Node
        extends
            org.w3c.dom.Node,
            Null
        {
            /**
             * Returns an intermediary node type with the specified XML node as the target.
             *
             * @param target the target node.
             * @return the intermediary node.
             */
            public default Node of(org.w3c.dom.Node target) { return null; }

            @Override
            public default org.w3c.dom.Node appendChild(org.w3c.dom.Node newChild) throws DOMException { return null; }

            @Override
            public default org.w3c.dom.Node cloneNode(boolean deep) { return null; }

            @Override
            public default short compareDocumentPosition(org.w3c.dom.Node other) throws DOMException { return 0; }

            @Override
            public default NamedNodeMap getAttributes() { return null; }

            @Override
            public default String getBaseURI() { return null; }

            @Override
            public default NodeList getChildNodes() { return null; }

            @Override
            public default Object getFeature(String feature, String version) { return null; }

            @Override
            public default org.w3c.dom.Node getFirstChild() { return null; }

            @Override
            public default org.w3c.dom.Node getLastChild() { return null; }

            @Override
            public default String getLocalName() { return null; }

            @Override
            public default String getNamespaceURI() { return null; }

            @Override
            public default org.w3c.dom.Node getNextSibling() { return null; }

            @Override
            public default String getNodeName() { return null; }

            @Override
            public default short getNodeType() { return 0; }

            @Override
            public default String getNodeValue() throws DOMException { return null; }

            @Override
            public default Document getOwnerDocument() { return null; }

            @Override
            public default org.w3c.dom.Node getParentNode() { return null; }

            @Override
            public default String getPrefix() { return null; }

            @Override
            public default org.w3c.dom.Node getPreviousSibling() { return null; }

            @Override
            public default String getTextContent() throws DOMException { return null; }

            @Override
            public default Object getUserData(String key) { return null; }

            @Override
            public default boolean hasAttributes() { return false; }

            @Override
            public default boolean hasChildNodes() { return false; }

            @Override
            public default org.w3c.dom.Node insertBefore(org.w3c.dom.Node newChild, org.w3c.dom.Node refChild) throws DOMException { return null; }

            @Override
            public default boolean isDefaultNamespace(String namespaceURI) { return false; }

            @Override
            public default boolean isEqualNode(org.w3c.dom.Node arg) { return false; }

            @Override
            public default boolean isSameNode(org.w3c.dom.Node other) { return false; }

            @Override
            public default boolean isSupported(String feature, String version) { return false; }

            @Override
            public default String lookupNamespaceURI(String prefix) { return null; }

            @Override
            public default String lookupPrefix(String namespaceURI) { return null; }

            @Override
            public default void normalize() {}

            @Override
            public default org.w3c.dom.Node removeChild(org.w3c.dom.Node oldChild) throws DOMException { return null; }

            @Override
            public default org.w3c.dom.Node replaceChild(org.w3c.dom.Node newChild, org.w3c.dom.Node oldChild) throws DOMException { return null; }

            @Override
            public default void setNodeValue(String nodeValue) throws DOMException {}

            @Override
            public default void setPrefix(String prefix) throws DOMException {}

            @Override
            public default void setTextContent(String textContent) throws DOMException {}

            @Override
            public default Object setUserData(String key, Object data, UserDataHandler handler) { return null; }
        }
    }
}