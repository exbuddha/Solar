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
 * {@code XMLAttribute} implements a wrapper interface for the standard {@link org.w3c.dom.Attr} interface.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
interface XMLAttribute
extends
    Attr,
    system.data.Format.Interpretable.Document.ElementPart,
    ObjectWrapper
{
    @Override
    public default Node appendChild(final Node newChild) throws DOMException {
        return ((Attr) object()).appendChild(newChild);
    }

    @Override
    public default Node cloneNode(final boolean deep) {
        return ((Attr) object()).cloneNode(deep);
    }

    @Override
    public default short compareDocumentPosition(final Node other) throws DOMException {
        return ((Attr) object()).compareDocumentPosition(other);
    }

    @Override
    public default boolean hasAttributes() {
        return ((Attr) object()).hasAttributes();
    }

    @Override
    public default NamedNodeMap getAttributes() {
        return ((Attr) object()).getAttributes();
    }

    @Override
    public default String getBaseURI() {
        return ((Attr) object()).getBaseURI();
    }

    @Override
    public default NodeList getChildNodes() {
        return ((Attr) object()).getChildNodes();
    }

    @Override
    public default Object getFeature(final String feature, final String version) {
        return ((Attr) object()).getFeature(feature, version);
    }

    @Override
    public default Node getFirstChild() {
        return ((Attr) object()).getFirstChild();
    }

    @Override
    public default Node getLastChild() {
        return ((Attr) object()).getLastChild();
    }

    @Override
    public default String getLocalName() {
        return ((Attr) object()).getLocalName();
    }

    @Override
    public default String getName() {
        return ((Attr) object()).getName();
    }

    @Override
    public default String getNamespaceURI() {
        return ((Attr) object()).getNamespaceURI();
    }

    @Override
    public default Node getNextSibling() {
        return ((Attr) object()).getNextSibling();
    }

    @Override
    public default String getNodeName() {
        return ((Attr) object()).getNodeName();
    }

    @Override
    public default short getNodeType() {
        return ((Attr) object()).getNodeType();
    }

    @Override
    public default String getNodeValue() throws DOMException {
        return ((Attr) object()).getNodeValue();
    }

    @Override
    public default String getPrefix() {
        return ((Attr) object()).getPrefix();
    }

    @Override
    public default Node getPreviousSibling() {
        return ((Attr) object()).getPreviousSibling();
    }

    @Override
    public default Document getOwnerDocument() {
        return ((Attr) object()).getOwnerDocument();
    }

    @Override
    public default Element getOwnerElement() {
        return ((Attr) object()).getOwnerElement();
    }

    @Override
    public default Node getParentNode() {
        return ((Attr) object()).getParentNode();
    }

    @Override
    public default TypeInfo getSchemaTypeInfo() {
        return ((Attr) object()).getSchemaTypeInfo();
    }

    @Override
    public default boolean getSpecified() {
        return ((Attr) object()).getSpecified();
    }

    @Override
    public default String getTextContent() throws DOMException {
        return ((Attr) object()).getTextContent();
    }

    @Override
    public default Object getUserData(final String key) {
        return ((Attr) object()).getUserData(key);
    }

    @Override
    public default String getValue() {
        return ((Attr) object()).getValue();
    }

    @Override
    public default boolean hasChildNodes() {
        return ((Attr) object()).hasChildNodes();
    }

    @Override
    public default Node insertBefore(final Node newChild, final Node refChild) throws DOMException {
        return ((Attr) object()).insertBefore(newChild, refChild);
    }

    @Override
    public default boolean isDefaultNamespace(final String namespaceURI) {
        return ((Attr) object()).isDefaultNamespace(namespaceURI);
    }

    @Override
    public default boolean isEqualNode(final Node arg) {
        return ((Attr) object()).isEqualNode(arg);
    }

    @Override
    public default boolean isId() {
        return ((Attr) object()).isId();
    }

    @Override
    public default boolean isSameNode(final Node other) {
        return ((Attr) object()).isSameNode(other);
    }

    @Override
    public default boolean isSupported(final String feature, final String version) {
        return ((Attr) object()).isSupported(feature, version);
    }

    @Override
    public default String lookupNamespaceURI(final String prefix) {
        return ((Attr) object()).lookupNamespaceURI(prefix);
    }

    @Override
    public default String lookupPrefix(final String namespaceURI) {
        return ((Attr) object()).lookupPrefix(namespaceURI);
    }

    @Override
    public default void normalize() {
        ((Attr) object()).normalize();
    }

    @Override
    public default Node removeChild(final Node oldChild) throws DOMException {
        return ((Attr) object()).removeChild(oldChild);
    }

    @Override
    public default Node replaceChild(final Node newChild, final Node oldChild) throws DOMException {
        return ((Attr) object()).replaceChild(newChild, oldChild);
    }

    @Override
    public default void setNodeValue(final String nodeValue) throws DOMException {
        ((Attr) object()).setNodeValue(nodeValue);
    }

    @Override
    public default void setPrefix(final String prefix) throws DOMException {
        ((Attr) object()).setPrefix(prefix);
    }

    @Override
    public default void setTextContent(final String textContent) throws DOMException {
        ((Attr) object()).setTextContent(textContent);
    }

    @Override
    public default Object setUserData(final String key, final Object data, final UserDataHandler handler) {
        return ((Attr) object()).setUserData(key, data, handler);
    }

    @Override
    public default void setValue(final String value) throws DOMException {
        ((Attr) object()).setValue(value);
    }
}