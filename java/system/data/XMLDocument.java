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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

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
    public default Node adoptNode(final Node source) throws DOMException {
        return ((Document) object()).adoptNode(source);
    }

    @Override
    public default Attr createAttribute(final String name) throws DOMException {
        return ((Document) object()).createAttribute(name);
    }

    @Override
    public default Attr createAttributeNS(final String namespaceURI, final String qualifiedName) throws DOMException {
        return ((Document) object()).createAttributeNS(namespaceURI, qualifiedName);
    }

    @Override
    public default CDATASection createCDATASection(final String data) throws DOMException {
        return ((Document) object()).createCDATASection(data);
    }

    @Override
    public default Comment createComment(final String data) {
        return ((Document) object()).createComment(data);
    }

    @Override
    public default DocumentFragment createDocumentFragment() {
        return ((Document) object()).createDocumentFragment();
    }

    @Override
    public default Element createElement(final String tagName) throws DOMException {
        return ((Document) object()).createElement(tagName);
    }

    @Override
    public default Element createElementNS(final String namespaceURI, final String qualifiedName) throws DOMException {
        return ((Document) object()).createElementNS(namespaceURI, qualifiedName);
    }

    @Override
    public default EntityReference createEntityReference(final String name) throws DOMException {
        return ((Document) object()).createEntityReference(name);
    }

    @Override
    public default ProcessingInstruction createProcessingInstruction(final String target, final String data) throws DOMException {
        return ((Document) object()).createProcessingInstruction(target, data);
    }

    @Override
    public default Text createTextNode(final String data) {
        return ((Document) object()).createTextNode(data);
    }

    @Override
    public default DocumentType getDoctype() {
        return ((Document) object()).getDoctype();
    }

    @Override
    public default Element getDocumentElement() {
        return ((Document) object()).getDocumentElement();
    }

    @Override
    public default String getDocumentURI() {
        return ((Document) object()).getDocumentURI();
    }

    @Override
    public default DOMConfiguration getDomConfig() {
        return ((Document) object()).getDomConfig();
    }

    @Override
    public default Element getElementById(final String elementId) {
        return ((Document) object()).getElementById(elementId);
    }

    @Override
    public default NodeList getElementsByTagName(final String name) {
        return ((Document) object()).getElementsByTagName(name);
    }

    public default NodeList getElementsByTagNameNS(final String namespaceURI, final String localName) throws DOMException {
        return ((Document) object()).getElementsByTagNameNS(namespaceURI, localName);
    }

    @Override
    public default String getInputEncoding() {
        return ((Document) object()).getInputEncoding();
    }

    @Override
    public default DOMImplementation getImplementation() {
        return ((Document) object()).getImplementation();
    }

    @Override
    public default boolean getStrictErrorChecking() {
        return ((Document) object()).getStrictErrorChecking();
    }

    @Override
    public default String getXmlEncoding() {
        return ((Document) object()).getXmlEncoding();
    }

    @Override
    public default boolean getXmlStandalone() {
        return ((Document) object()).getXmlStandalone();
    }

    @Override
    public default String getXmlVersion() {
        return ((Document) object()).getXmlVersion();
    }

    @Override
    public default Node importNode(final Node importedNode, final boolean deep) throws DOMException {
        return ((Document) object()).importNode(importedNode, deep);
    }

    @Override
    public default void normalizeDocument() {
        ((Document) object()).normalizeDocument();
    }

    @Override
    public default Node renameNode(final Node n, final String namespaceURI, final String qualifiedName) throws DOMException {
        return ((Document) object()).renameNode(n, namespaceURI, qualifiedName);
    }

    @Override
    public default void setDocumentURI(final String documentURI) {
        ((Document) object()).setDocumentURI(documentURI);
    }

    @Override
    public default void setStrictErrorChecking(final boolean strictErrorChecking) {
        ((Document) object()).setStrictErrorChecking(strictErrorChecking);
    }

    @Override
    public default void setXmlStandalone(final boolean xmlStandalone) throws DOMException {
        ((Document) object()).setXmlStandalone(xmlStandalone);
    }

    @Override
    public default void setXmlVersion(final String xmlVersion) throws DOMException {
        ((Document) object()).setXmlVersion(xmlVersion);
    }
}