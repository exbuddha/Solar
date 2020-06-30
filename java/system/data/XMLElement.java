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

import system.data.Format.Interpretable;

/**
 * {@code XMLElement} implements a wrapper interface for the standard {@link org.w3c.dom.Element} interface.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
interface XMLElement
extends
    Element,
    Interpretable.Document.Element,
    ObjectWrapper
{
    @Override
    default Node appendChild(final Node newChild) throws DOMException {
        return ((Element) object()).appendChild(newChild);
    }

    @Override
    default Node cloneNode(final boolean deep) {
        return ((Element) object()).cloneNode(deep);
    }

    @Override
    default short compareDocumentPosition(final Node other) throws DOMException {
        return ((Element) object()).compareDocumentPosition(other);
    }

    @Override
    default String getAttribute(final String name) {
        return ((Element) object()).getAttribute(name);
    }

    @Override
    default String getAttributeNS(final String namespaceURI, final String localName) throws DOMException {
        return ((Element) object()).getAttributeNS(namespaceURI, localName);
    }

    @Override
    default Attr getAttributeNode(final String name) {
        return ((Element) object()).getAttributeNode(name);
    }

    @Override
    default Attr getAttributeNodeNS(final String namespaceURI, final String localName) throws DOMException {
        return ((Element) object()).getAttributeNodeNS(namespaceURI, localName);
    }

    @Override
    default NamedNodeMap getAttributes() {
        return ((Element) object()).getAttributes();
    }

    @Override
    default String getBaseURI() {
        return ((Element) object()).getBaseURI();
    }

    @Override
    default NodeList getChildNodes() {
        return ((Element) object()).getChildNodes();
    }

    @Override
    default NodeList getElementsByTagName(final String name) {
        return ((Element) object()).getElementsByTagName(name);
    }

    @Override
    default NodeList getElementsByTagNameNS(final String namespaceURI, final String localName) throws DOMException {
        return ((Element) object()).getElementsByTagNameNS(namespaceURI, localName);
    }

    @Override
    default Object getFeature(final String feature, final String version) {
        return ((Element) object()).getFeature(feature, version);
    }

    @Override
    default Node getFirstChild() {
        return ((Element) object()).getFirstChild();
    }

    @Override
    default Node getLastChild() {
        return ((Element) object()).getLastChild();
    }

    @Override
    default String getLocalName() {
        return ((Element) object()).getLocalName();
    }

    @Override
    default String getNamespaceURI() {
        return ((Element) object()).getNamespaceURI();
    }

    @Override
    default Node getNextSibling() {
        return ((Element) object()).getNextSibling();
    }

    @Override
    default String getNodeName() {
        return ((Element) object()).getNodeName();
    }

    @Override
    default short getNodeType() {
        return ((Element) object()).getNodeType();
    }

    @Override
    default String getNodeValue() throws DOMException {
        return ((Element) object()).getNodeValue();
    }

    @Override
    default Document getOwnerDocument() {
        return ((Element) object()).getOwnerDocument();
    }

    @Override
    default Node getParentNode() {
        return ((Element) object()).getParentNode();
    }

    @Override
    default String getPrefix() {
        return ((Element) object()).getPrefix();
    }

    @Override
    default Node getPreviousSibling() {
        return ((Element) object()).getPreviousSibling();
    }

    @Override
    default TypeInfo getSchemaTypeInfo() {
        return ((Element) object()).getSchemaTypeInfo();
    }

    @Override
    default String getTagName() {
        return ((Element) object()).getTagName();
    }

    @Override
    default String getTextContent() throws DOMException {
        return ((Element) object()).getTextContent();
    }

    @Override
    default Object getUserData(final String key) {
        return ((Element) object()).getUserData(key);
    }

    @Override
    default boolean hasAttribute(final String name) {
        return ((Element) object()).hasAttribute(name);
    }

    @Override
    default boolean hasAttributeNS(final String namespaceURI, final String localName) throws DOMException {
        return ((Element) object()).hasAttributeNS(namespaceURI, localName);
    }

    @Override
    default boolean hasAttributes() {
        return ((Element) object()).hasAttributes();
    }

    @Override
    default boolean hasChildNodes() {
        return ((Element) object()).hasChildNodes();
    }

    @Override
    default Node insertBefore(final Node newChild, final Node refChild) throws DOMException {
        return ((Element) object()).insertBefore(newChild, refChild);
    }

    @Override
    default boolean isDefaultNamespace(final String namespaceURI) {
        return ((Element) object()).isDefaultNamespace(namespaceURI);
    }

    @Override
    default boolean isEqualNode(final Node arg) {
        return ((Element) object()).isEqualNode(arg);
    }

    @Override
    default boolean isSameNode(final Node other) {
        return ((Element) object()).isSameNode(other);
    }

    @Override
    default boolean isSupported(final String feature, final String version) {
        return ((Element) object()).isSupported(feature, version);
    }

    @Override
    default String lookupNamespaceURI(final String prefix) {
        return ((Element) object()).lookupNamespaceURI(prefix);
    }

    @Override
    default String lookupPrefix(final String namespaceURI) {
        return ((Element) object()).lookupPrefix(namespaceURI);
    }

    @Override
    default void normalize() {
        ((Element) object()).normalize();
    }

    @Override
    default void removeAttribute(final String name) throws DOMException {
        ((Element) object()).removeAttribute(name);
    }

    @Override
    default void removeAttributeNS(final String namespaceURI, final String localName) throws DOMException {
        ((Element) object()).removeAttributeNS(namespaceURI, localName);
    }

    @Override
    default Attr removeAttributeNode(final Attr oldAttr) throws DOMException {
        return ((Element) object()).removeAttributeNode(oldAttr);
    }

    @Override
    default Node removeChild(final Node oldChild) throws DOMException {
        return ((Element) object()).removeChild(oldChild);
    }

    @Override
    default Node replaceChild(final Node newChild, final Node oldChild) throws DOMException {
        return ((Element) object()).replaceChild(newChild, oldChild);
    }

    @Override
    default void setAttribute(final String name, final String value) throws DOMException {
        ((Element) object()).setAttribute(name, value);
    }

    @Override
    default void setAttributeNS(final String namespaceURI, final String qualifiedName, final String value) throws DOMException {
        ((Element) object()).setAttributeNS(namespaceURI, qualifiedName, value);
    }

    @Override
    default Attr setAttributeNode(final Attr newAttr) throws DOMException {
        return ((Element) object()).setAttributeNode(newAttr);
    }

    @Override
    default Attr setAttributeNodeNS(final Attr newAttr) throws DOMException {
        return ((Element) object()).setAttributeNodeNS(newAttr);
    }

    @Override
    default void setIdAttribute(final String name, final boolean isId) throws DOMException {
        ((Element) object()).setIdAttribute(name, isId);
    }

    @Override
    default void setIdAttributeNS(final String namespaceURI, final String localName, final boolean isId) throws DOMException {
        ((Element) object()).setIdAttributeNS(namespaceURI, localName, isId);
    }

    @Override
    default void setIdAttributeNode(final Attr idAttr, final boolean isId) throws DOMException {
        ((Element) object()).setIdAttributeNode(idAttr, isId);
    }

    @Override
    default void setNodeValue(final String nodeValue) throws DOMException {
        ((Element) object()).setNodeValue(nodeValue);
    }

    @Override
    default void setPrefix(final String prefix) throws DOMException {
        ((Element) object()).setPrefix(prefix);
    }

    @Override
    default void setTextContent(final String textContent) throws DOMException {
        ((Element) object()).setTextContent(textContent);
    }

    @Override
    default Object setUserData(final String key, final Object data, final UserDataHandler handler) {
        return ((Element) object()).setUserData(key, data, handler);
    }
}