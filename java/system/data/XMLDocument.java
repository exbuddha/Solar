package system.data;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

/**
 * {@code XMLDocument} implements a wrapper interface for the standard {@link Document} interface.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
interface XMLDocument
extends
    Document,
    XMLElement
{
    @Override
    default Node adoptNode(final Node source) throws DOMException {
        return ((Document) object()).adoptNode(source);
    }

    @Override
    default Node appendChild(final Node newChild) throws DOMException {
        return ((Document) object()).appendChild(newChild);
    }

    @Override
    default Node cloneNode(final boolean deep) {
        return ((Document) object()).cloneNode(deep);
    }

    @Override
    default short compareDocumentPosition(final Node other) throws DOMException {
        return ((Document) object()).compareDocumentPosition(other);
    }

    @Override
    default Attr createAttribute(final String name) throws DOMException {
        return ((Document) object()).createAttribute(name);
    }

    @Override
    default Attr createAttributeNS(final String namespaceURI, final String qualifiedName) throws DOMException {
        return ((Document) object()).createAttributeNS(namespaceURI, qualifiedName);
    }

    @Override
    default CDATASection createCDATASection(final String data) throws DOMException {
        return ((Document) object()).createCDATASection(data);
    }

    @Override
    default Comment createComment(final String data) {
        return ((Document) object()).createComment(data);
    }

    @Override
    default DocumentFragment createDocumentFragment() {
        return ((Document) object()).createDocumentFragment();
    }

    @Override
    default Element createElement(final String tagName) throws DOMException {
        return ((Document) object()).createElement(tagName);
    }

    @Override
    default Element createElementNS(final String namespaceURI, final String qualifiedName) throws DOMException {
        return ((Document) object()).createElementNS(namespaceURI, qualifiedName);
    }

    @Override
    default EntityReference createEntityReference(final String name) throws DOMException {
        return ((Document) object()).createEntityReference(name);
    }

    @Override
    default ProcessingInstruction createProcessingInstruction(final String target, final String data) throws DOMException {
        return ((Document) object()).createProcessingInstruction(target, data);
    }

    @Override
    default Text createTextNode(final String data) {
        return ((Document) object()).createTextNode(data);
    }

    @Override
    default String getAttribute(final String name) {
        return ((Document) object()).getDocumentElement().getAttribute(name);
    }

    @Override
    default String getAttributeNS(final String namespaceURI, final String localName) throws DOMException {
        return ((Document) object()).getDocumentElement().getAttributeNS(namespaceURI, localName);
    }

    @Override
    default Attr getAttributeNode(final String name) {
        return ((Document) object()).getDocumentElement().getAttributeNode(name);
    }

    @Override
    default Attr getAttributeNodeNS(final String namespaceURI, final String localName) throws DOMException {
        return ((Document) object()).getDocumentElement().getAttributeNodeNS(namespaceURI, localName);
    }

    @Override
    default NamedNodeMap getAttributes() {
        return ((Document) object()).getAttributes();
    }

    @Override
    default String getBaseURI() {
        return ((Document) object()).getBaseURI();
    }

    @Override
    default NodeList getChildNodes() {
        return ((Document) object()).getChildNodes();
    }

    @Override
    default DocumentType getDoctype() {
        return ((Document) object()).getDoctype();
    }

    @Override
    default Element getDocumentElement() {
        return ((Document) object()).getDocumentElement();
    }

    @Override
    default String getDocumentURI() {
        return ((Document) object()).getDocumentURI();
    }

    @Override
    default DOMConfiguration getDomConfig() {
        return ((Document) object()).getDomConfig();
    }

    @Override
    default Element getElementById(final String elementId) {
        return ((Document) object()).getElementById(elementId);
    }

    @Override
    default NodeList getElementsByTagName(final String name) {
        return ((Document) object()).getElementsByTagName(name);
    }

    default NodeList getElementsByTagNameNS(final String namespaceURI, final String localName) throws DOMException {
        return ((Document) object()).getElementsByTagNameNS(namespaceURI, localName);
    }

    @Override
    default Object getFeature(final String feature, final String version) {
        return ((Document) object()).getFeature(feature, version);
    }

    @Override
    default Node getFirstChild() {
        return ((Document) object()).getFirstChild();
    }

    @Override
    default Node getLastChild() {
        return ((Document) object()).getLastChild();
    }

    @Override
    default String getInputEncoding() {
        return ((Document) object()).getInputEncoding();
    }

    @Override
    default DOMImplementation getImplementation() {
        return ((Document) object()).getImplementation();
    }

    @Override
    default String getLocalName() {
        return ((Document) object()).getLocalName();
    }

    @Override
    default String getNamespaceURI() {
        return ((Document) object()).getNamespaceURI();
    }

    @Override
    default Node getNextSibling() {
        return ((Document) object()).getNextSibling();
    }

    @Override
    default String getNodeName() {
        return ((Document) object()).getNodeName();
    }

    @Override
    default short getNodeType() {
        return ((Document) object()).getNodeType();
    }

    @Override
    default String getNodeValue() throws DOMException {
        return ((Document) object()).getNodeValue();
    }

    @Override
    default Document getOwnerDocument() {
        return ((Document) object()).getOwnerDocument();
    }

    @Override
    default Node getParentNode() {
        return ((Document) object()).getParentNode();
    }

    @Override
    default String getPrefix() {
        return ((Document) object()).getPrefix();
    }

    @Override
    default Node getPreviousSibling() {
        return ((Document) object()).getPreviousSibling();
    }

    @Override
    default TypeInfo getSchemaTypeInfo() {
        return ((Document) object()).getDocumentElement().getSchemaTypeInfo();
    }

    @Override
    default boolean getStrictErrorChecking() {
        return ((Document) object()).getStrictErrorChecking();
    }

    @Override
    default String getTagName() {
        return ((Document) object()).getDocumentElement().getTagName();
    }

    @Override
    default String getTextContent() throws DOMException {
        return ((Document) object()).getTextContent();
    }

    @Override
    default Object getUserData(final String key) {
        return ((Document) object()).getUserData(key);
    }

    @Override
    default String getXmlEncoding() {
        return ((Document) object()).getXmlEncoding();
    }

    @Override
    default boolean getXmlStandalone() {
        return ((Document) object()).getXmlStandalone();
    }

    @Override
    default String getXmlVersion() {
        return ((Document) object()).getXmlVersion();
    }

    @Override
    default boolean hasAttribute(final String name) {
        return ((Document) object()).getDocumentElement().hasAttribute(name);
    }

    @Override
    default boolean hasAttributeNS(final String namespaceURI, final String localName) throws DOMException {
        return ((Document) object()).getDocumentElement().hasAttributeNS(namespaceURI, localName);
    }

    @Override
    default boolean hasAttributes() {
        return ((Document) object()).hasAttributes();
    }

    @Override
    default boolean hasChildNodes() {
        return ((Document) object()).hasChildNodes();
    }

    @Override
    default Node importNode(final Node importedNode, final boolean deep) throws DOMException {
        return ((Document) object()).importNode(importedNode, deep);
    }

    @Override
    default Node insertBefore(final Node newChild, final Node refChild) throws DOMException {
        return ((Document) object()).insertBefore(newChild, refChild);
    }

    @Override
    default boolean isDefaultNamespace(final String namespaceURI) {
        return ((Document) object()).isDefaultNamespace(namespaceURI);
    }

    @Override
    default boolean isEqualNode(final Node arg) {
        return ((Document) object()).isEqualNode(arg);
    }

    @Override
    default boolean isSameNode(final Node other) {
        return ((Document) object()).isSameNode(other);
    }

    @Override
    default boolean isSupported(final String feature, final String version) {
        return ((Document) object()).isSupported(feature, version);
    }

    @Override
    default String lookupNamespaceURI(final String prefix) {
        return ((Document) object()).lookupNamespaceURI(prefix);
    }

    @Override
    default String lookupPrefix(final String namespaceURI) {
        return ((Document) object()).lookupPrefix(namespaceURI);
    }

    @Override
    default void normalize() {
        ((Document) object()).normalize();
    }

    @Override
    default void normalizeDocument() {
        ((Document) object()).normalizeDocument();
    }

    @Override
    default void removeAttribute(final String name) throws DOMException {
        ((Document) object()).getDocumentElement().removeAttribute(name);
    }

    @Override
    default void removeAttributeNS(final String namespaceURI, final String localName) throws DOMException {
        ((Document) object()).getDocumentElement().removeAttributeNS(namespaceURI, localName);
    }

    @Override
    default Attr removeAttributeNode(final Attr oldAttr) throws DOMException {
        return ((Document) object()).getDocumentElement().removeAttributeNode(oldAttr);
    }

    @Override
    default Node removeChild(final Node oldChild) throws DOMException {
        return ((Document) object()).removeChild(oldChild);
    }

    @Override
    default Node renameNode(final Node n, final String namespaceURI, final String qualifiedName) throws DOMException {
        return ((Document) object()).renameNode(n, namespaceURI, qualifiedName);
    }

    @Override
    default Node replaceChild(final Node newChild, final Node oldChild) throws DOMException {
        return ((Document) object()).replaceChild(newChild, oldChild);
    }

    @Override
    default void setAttribute(final String name, final String value) throws DOMException {
        ((Document) object()).getDocumentElement().setAttribute(name, value);
    }

    @Override
    default void setAttributeNS(final String namespaceURI, final String qualifiedName, final String value) throws DOMException {
        ((Document) object()).getDocumentElement().setAttributeNS(namespaceURI, qualifiedName, value);
    }

    @Override
    default Attr setAttributeNode(final Attr newAttr) throws DOMException {
        return ((Document) object()).getDocumentElement().setAttributeNode(newAttr);
    }

    @Override
    default Attr setAttributeNodeNS(final Attr newAttr) throws DOMException {
        return ((Document) object()).getDocumentElement().setAttributeNodeNS(newAttr);
    }

    @Override
    default void setDocumentURI(final String documentURI) {
        ((Document) object()).setDocumentURI(documentURI);
    }

    @Override
    default void setIdAttribute(final String name, final boolean isId) throws DOMException {
        ((Document) object()).getDocumentElement().setIdAttribute(name, isId);
    }

    @Override
    default void setIdAttributeNS(final String namespaceURI, final String localName, final boolean isId) throws DOMException {
        ((Document) object()).getDocumentElement().setIdAttributeNS(namespaceURI, localName, isId);
    }

    @Override
    default void setIdAttributeNode(final Attr idAttr, final boolean isId) throws DOMException {
        ((Document) object()).getDocumentElement().setIdAttributeNode(idAttr, isId);
    }

    @Override
    default void setNodeValue(final String nodeValue) throws DOMException {
        ((Document) object()).setNodeValue(nodeValue);
    }

    @Override
    default void setPrefix(final String prefix) throws DOMException {
        ((Document) object()).setPrefix(prefix);
    }

    @Override
    default void setStrictErrorChecking(final boolean strictErrorChecking) {
        ((Document) object()).setStrictErrorChecking(strictErrorChecking);
    }

    @Override
    default void setTextContent(final String textContent) throws DOMException {
        ((Document) object()).setTextContent(textContent);
    }

    @Override
    default Object setUserData(final String key, final Object data, final UserDataHandler handler) {
        return ((Document) object()).setUserData(key, data, handler);
    }

    @Override
    default void setXmlStandalone(final boolean xmlStandalone) throws DOMException {
        ((Document) object()).setXmlStandalone(xmlStandalone);
    }

    @Override
    default void setXmlVersion(final String xmlVersion) throws DOMException {
        ((Document) object()).setXmlVersion(xmlVersion);
    }
}