package system.data;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

/**
 * {@code XMLElement} implements a wrapper interface for the standard {@link org.w3c.dom.Element} interface.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
interface XMLElement
extends
    Element,
    system.data.Format.Interpretable.Document.Element,
    ObjectWrapper
{
    @Override
    public default Node appendChild(final Node newChild) throws DOMException {
        return ((Element) object()).appendChild(newChild);
    }

    @Override
    public default Node cloneNode(final boolean deep) {
        return ((Element) object()).cloneNode(deep);
    }

    @Override
    public default short compareDocumentPosition(final Node other) throws DOMException {
        return ((Element) object()).compareDocumentPosition(other);
    }

    @Override
    public default String getAttribute(final String name) {
        return ((Element) object()).getAttribute(name);
    }

    @Override
    public default String getAttributeNS(final String namespaceURI, final String localName) throws DOMException {
        return ((Element) object()).getAttributeNS(namespaceURI, localName);
    }

    @Override
    public default Attr getAttributeNode(final String name) {
        return ((Element) object()).getAttributeNode(name);
    }

    @Override
    public default Attr getAttributeNodeNS(final String namespaceURI, final String localName) throws DOMException {
        return ((Element) object()).getAttributeNodeNS(namespaceURI, localName);
    }

    @Override
    public default NamedNodeMap getAttributes() {
        return ((Element) object()).getAttributes();
    }

    @Override
    public default String getBaseURI() {
        return ((Element) object()).getBaseURI();
    }

    @Override
    public default NodeList getChildNodes() {
        return ((Element) object()).getChildNodes();
    }

    @Override
    public default NodeList getElementsByTagName(final String name) {
        return ((Element) object()).getElementsByTagName(name);
    }

    @Override
    public default NodeList getElementsByTagNameNS(final String namespaceURI, final String localName) throws DOMException {
        return ((Element) object()).getElementsByTagNameNS(namespaceURI, localName);
    }

    @Override
    public default Object getFeature(final String feature, final String version) {
        return ((Element) object()).getFeature(feature, version);
    }

    @Override
    public default Node getFirstChild() {
        return ((Element) object()).getFirstChild();
    }

    @Override
    public default Node getLastChild() {
        return ((Element) object()).getLastChild();
    }

    @Override
    public default String getLocalName() {
        return ((Element) object()).getLocalName();
    }

    @Override
    public default String getNamespaceURI() {
        return ((Element) object()).getNamespaceURI();
    }

    @Override
    public default Node getNextSibling() {
        return ((Element) object()).getNextSibling();
    }

    @Override
    public default String getNodeName() {
        return ((Element) object()).getNodeName();
    }

    @Override
    public default short getNodeType() {
        return ((Element) object()).getNodeType();
    }

    @Override
    public default String getNodeValue() throws DOMException {
        return ((Element) object()).getNodeValue();
    }

    @Override
    public default Document getOwnerDocument() {
        return ((Element) object()).getOwnerDocument();
    }

    @Override
    public default Node getParentNode() {
        return ((Element) object()).getParentNode();
    }

    @Override
    public default String getPrefix() {
        return ((Element) object()).getPrefix();
    }

    @Override
    public default Node getPreviousSibling() {
        return ((Element) object()).getPreviousSibling();
    }

    @Override
    public default TypeInfo getSchemaTypeInfo() {
        return ((Element) object()).getSchemaTypeInfo();
    }

    @Override
    public default String getTagName() {
        return ((Element) object()).getTagName();
    }

    @Override
    public default String getTextContent() throws DOMException {
        return ((Element) object()).getTextContent();
    }

    @Override
    public default Object getUserData(final String key) {
        return ((Element) object()).getUserData(key);
    }

    @Override
    public default boolean hasAttribute(final String name) {
        return ((Element) object()).hasAttribute(name);
    }

    @Override
    public default boolean hasAttributeNS(final String namespaceURI, final String localName) throws DOMException {
        return ((Element) object()).hasAttributeNS(namespaceURI, localName);
    }

    @Override
    public default boolean hasAttributes() {
        return ((Element) object()).hasAttributes();
    }

    @Override
    public default boolean hasChildNodes() {
        return ((Element) object()).hasChildNodes();
    }

    @Override
    public default Node insertBefore(final Node newChild, final Node refChild) throws DOMException {
        return ((Element) object()).insertBefore(newChild, refChild);
    }

    @Override
    public default boolean isDefaultNamespace(final String namespaceURI) {
        return ((Element) object()).isDefaultNamespace(namespaceURI);
    }

    @Override
    public default boolean isEqualNode(final Node arg) {
        return ((Element) object()).isEqualNode(arg);
    }

    @Override
    public default boolean isSameNode(final Node other) {
        return ((Element) object()).isSameNode(other);
    }

    @Override
    public default boolean isSupported(final String feature, final String version) {
        return ((Element) object()).isSupported(feature, version);
    }

    @Override
    public default String lookupNamespaceURI(final String prefix) {
        return ((Element) object()).lookupNamespaceURI(prefix);
    }

    @Override
    public default String lookupPrefix(final String namespaceURI) {
        return ((Element) object()).lookupPrefix(namespaceURI);
    }

    @Override
    public default void normalize() {
        ((Element) object()).normalize();
    }

    @Override
    public default void removeAttribute(final String name) throws DOMException {
        ((Element) object()).removeAttribute(name);
    }

    @Override
    public default void removeAttributeNS(final String namespaceURI, final String localName) throws DOMException {
        ((Element) object()).removeAttributeNS(namespaceURI, localName);
    }

    @Override
    public default Attr removeAttributeNode(final Attr oldAttr) throws DOMException {
        return ((Element) object()).removeAttributeNode(oldAttr);
    }

    @Override
    public default Node removeChild(final Node oldChild) throws DOMException {
        return ((Element) object()).removeChild(oldChild);
    }

    @Override
    public default Node replaceChild(final Node newChild, final Node oldChild) throws DOMException {
        return ((Element) object()).replaceChild(newChild, oldChild);
    }

    @Override
    public default void setAttribute(final String name, final String value) throws DOMException {
        ((Element) object()).setAttribute(name, value);
    }

    @Override
    public default void setAttributeNS(final String namespaceURI, final String qualifiedName, final String value) throws DOMException {
        ((Element) object()).setAttributeNS(namespaceURI, qualifiedName, value);
    }

    @Override
    public default Attr setAttributeNode(final Attr newAttr) throws DOMException {
        return ((Element) object()).setAttributeNode(newAttr);
    }

    @Override
    public default Attr setAttributeNodeNS(final Attr newAttr) throws DOMException {
        return ((Element) object()).setAttributeNodeNS(newAttr);
    }

    @Override
    public default void setIdAttribute(final String name, final boolean isId) throws DOMException {
        ((Element) object()).setIdAttribute(name, isId);
    }

    @Override
    public default void setIdAttributeNS(final String namespaceURI, final String localName, final boolean isId) throws DOMException {
        ((Element) object()).setIdAttributeNS(namespaceURI, localName, isId);
    }

    @Override
    public default void setIdAttributeNode(final Attr idAttr, final boolean isId) throws DOMException {
        ((Element) object()).setIdAttributeNode(idAttr, isId);
    }

    @Override
    public default void setNodeValue(final String nodeValue) throws DOMException {
        ((Element) object()).setNodeValue(nodeValue);
    }

    @Override
    public default void setPrefix(final String prefix) throws DOMException {
        ((Element) object()).setPrefix(prefix);
    }

    @Override
    public default void setTextContent(final String textContent) throws DOMException {
        ((Element) object()).setTextContent(textContent);
    }

    @Override
    public default Object setUserData(final String key, final Object data, final UserDataHandler handler) {
        return ((Element) object()).setUserData(key, data, handler);
    }
}