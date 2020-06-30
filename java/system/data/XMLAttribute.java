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
 * {@code XMLAttribute} implements a wrapper interface for the standard {@link org.w3c.dom.Attr} interface.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
interface XMLAttribute
extends
    Attr,
    Interpretable.Document.ElementPart,
    ObjectWrapper
{
    @Override
    default Node appendChild(final Node newChild) throws DOMException {
        return ((Attr) object()).appendChild(newChild);
    }

    @Override
    default Node cloneNode(final boolean deep) {
        return ((Attr) object()).cloneNode(deep);
    }

    @Override
    default short compareDocumentPosition(final Node other) throws DOMException {
        return ((Attr) object()).compareDocumentPosition(other);
    }

    @Override
    default boolean hasAttributes() {
        return ((Attr) object()).hasAttributes();
    }

    @Override
    default NamedNodeMap getAttributes() {
        return ((Attr) object()).getAttributes();
    }

    @Override
    default String getBaseURI() {
        return ((Attr) object()).getBaseURI();
    }

    @Override
    default NodeList getChildNodes() {
        return ((Attr) object()).getChildNodes();
    }

    @Override
    default Object getFeature(final String feature, final String version) {
        return ((Attr) object()).getFeature(feature, version);
    }

    @Override
    default Node getFirstChild() {
        return ((Attr) object()).getFirstChild();
    }

    @Override
    default Node getLastChild() {
        return ((Attr) object()).getLastChild();
    }

    @Override
    default String getLocalName() {
        return ((Attr) object()).getLocalName();
    }

    @Override
    default String getName() {
        return ((Attr) object()).getName();
    }

    @Override
    default String getNamespaceURI() {
        return ((Attr) object()).getNamespaceURI();
    }

    @Override
    default Node getNextSibling() {
        return ((Attr) object()).getNextSibling();
    }

    @Override
    default String getNodeName() {
        return ((Attr) object()).getNodeName();
    }

    @Override
    default short getNodeType() {
        return ((Attr) object()).getNodeType();
    }

    @Override
    default String getNodeValue() throws DOMException {
        return ((Attr) object()).getNodeValue();
    }

    @Override
    default String getPrefix() {
        return ((Attr) object()).getPrefix();
    }

    @Override
    default Node getPreviousSibling() {
        return ((Attr) object()).getPreviousSibling();
    }

    @Override
    default Document getOwnerDocument() {
        return ((Attr) object()).getOwnerDocument();
    }

    @Override
    default Element getOwnerElement() {
        return ((Attr) object()).getOwnerElement();
    }

    @Override
    default Node getParentNode() {
        return ((Attr) object()).getParentNode();
    }

    @Override
    default TypeInfo getSchemaTypeInfo() {
        return ((Attr) object()).getSchemaTypeInfo();
    }

    @Override
    default boolean getSpecified() {
        return ((Attr) object()).getSpecified();
    }

    @Override
    default String getTextContent() throws DOMException {
        return ((Attr) object()).getTextContent();
    }

    @Override
    default Object getUserData(final String key) {
        return ((Attr) object()).getUserData(key);
    }

    @Override
    default String getValue() {
        return ((Attr) object()).getValue();
    }

    @Override
    default boolean hasChildNodes() {
        return ((Attr) object()).hasChildNodes();
    }

    @Override
    default Node insertBefore(final Node newChild, final Node refChild) throws DOMException {
        return ((Attr) object()).insertBefore(newChild, refChild);
    }

    @Override
    default boolean isDefaultNamespace(final String namespaceURI) {
        return ((Attr) object()).isDefaultNamespace(namespaceURI);
    }

    @Override
    default boolean isEqualNode(final Node arg) {
        return ((Attr) object()).isEqualNode(arg);
    }

    @Override
    default boolean isId() {
        return ((Attr) object()).isId();
    }

    @Override
    default boolean isSameNode(final Node other) {
        return ((Attr) object()).isSameNode(other);
    }

    @Override
    default boolean isSupported(final String feature, final String version) {
        return ((Attr) object()).isSupported(feature, version);
    }

    @Override
    default String lookupNamespaceURI(final String prefix) {
        return ((Attr) object()).lookupNamespaceURI(prefix);
    }

    @Override
    default String lookupPrefix(final String namespaceURI) {
        return ((Attr) object()).lookupPrefix(namespaceURI);
    }

    @Override
    default void normalize() {
        ((Attr) object()).normalize();
    }

    @Override
    default Node removeChild(final Node oldChild) throws DOMException {
        return ((Attr) object()).removeChild(oldChild);
    }

    @Override
    default Node replaceChild(final Node newChild, final Node oldChild) throws DOMException {
        return ((Attr) object()).replaceChild(newChild, oldChild);
    }

    @Override
    default void setNodeValue(final String nodeValue) throws DOMException {
        ((Attr) object()).setNodeValue(nodeValue);
    }

    @Override
    default void setPrefix(final String prefix) throws DOMException {
        ((Attr) object()).setPrefix(prefix);
    }

    @Override
    default void setTextContent(final String textContent) throws DOMException {
        ((Attr) object()).setTextContent(textContent);
    }

    @Override
    default Object setUserData(final String key, final Object data, final UserDataHandler handler) {
        return ((Attr) object()).setUserData(key, data, handler);
    }

    @Override
    default void setValue(final String value) throws DOMException {
        ((Attr) object()).setValue(value);
    }
}