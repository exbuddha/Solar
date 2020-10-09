package system.data;

import static system.data.Constant.*;
import static system.data.Constant.XML.IndentKey;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Queue;
import java.util.ServiceConfigurationError;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.helpers.DefaultHandler;

import system.data.Format.Hierarchical;
import system.data.Format.Interpretable;

/**
 * {@code XML} represents an XML document.
 * <p/>
 * This class defines static members for generating XML documents or elements from an input stream, writing documents to output streams, wrapping elements and attributes, and traversing them.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class XML
extends Interpretable.Document
implements
    Hierarchical,
    Unwrappable,
    Unwrapping<XML>,
    Wrappable,
    Wrapping<XML>,
    XMLDocument
{
    /** The document builder factory. */
    private static
    DocumentBuilderFactory BUILDER_FACTORY;

    /** The parser factory. */
    private static
    SAXParserFactory PARSER_FACTORY;

    /** The transformer factory. */
    private static
    TransformerFactory TRANSFORMER_FACTORY;

    /** The empty iterable. */
    private static final
    Null.Iterable<Node> EmptyIterable = new Null.Iterable<Node>() {
                                       @Override
                                       public String msg() {
                                           return XmlChildNotFound;
                                       }
                                   };

    /** The document handler. */
    protected
    Document.Handler handler;

    /**
     * Creates an XML document with the specified handler.
     *
     * @param handler the document handler.
     */
    public
    XML(
        final DocumentHandler handler
        ) {
        super();
        this.handler = handler;
    }

    /**
     * Creates an XML document with the specified traditional document and using the standard implementation of {@link DocumentHandler}.
     *
     * @param document the document.
     *
     * @see DocumentHandler.Standard
     */
    public
    XML(
        final org.w3c.dom.Document document
        ) {
        this(new DocumentHandler.Standard(document));
    }

    /**
     * Creates an empty XML document using the standard implementation of {@link DocumentHandler}.
     * <p/>
     * This implementation creates a document element wrapped as an {@link Element} type.
     *
     * @see DocumentHandler.Standard
     * @see Element
     */
    public
    XML() {
        this(new DocumentHandler.Standard((XMLDocument) Element.of(newDocumentBuilder().newDocument())));
    }

    /**
     * Clones the specified source element and returns the copy.
     * <p/>
     * This implementation is recursive.
     *
     * @param source the source element.
     *
     * @return the copy.
     *
     * @see #cloneChild(Node, Node)
     */
    public static
    Node clone(
        final Node source
        ) {
        return source == null
               ? null
               : cloneChildren(source, source.getOwnerDocument().createElement(source.getNodeName()));
    }

    /**
     * Clones the attributes of the source element and adds them as attributes to the target element.
     *
     * @param source the source element.
     * @param target the target element.
     *
     * @return the target element.
     */
    public static
    org.w3c.dom.Element cloneAttributes(
        final Node source,
        final org.w3c.dom.Element target
        ) {
        if (source == null)
            return target;

        final NamedNodeMap attributes = source.getAttributes();
        if (attributes != null)
            for (int i = 0; i < attributes.getLength(); i++) {
                final Attr attribute = (Attr) attributes.item(i);
                if (attribute instanceof Element.Attribute)
                    target.setAttributeNode(Element.Attribute.of(attribute));
                else
                    target.setAttributeNode(attribute);
            }

        return target;
    }

    /**
     * Clones the source node and appends it as a child node to the target element and returns the cloned node.
     * <p/>
     * This implementation does not support namespace URI, {@code Document}, {@code DocumentFragment}, {@code Entity}, {@code EntityReference}, or {@code Notation} source node types.
     * If any of these node types are encountered, an {@code UnsupportedOperationException} will be thrown.
     *
     * @param source the source node.
     * @param target the target element.
     *
     * @return the cloned node.
     *
     * @throws DOMException if an element name is not a valid XML name.
     * @throws UnsupportedOperationException if an unsupported node type is encountered.
     */
    public static
    Node cloneChild(
        final Node source,
        final Node target
        )
    throws
        DOMException,
        UnsupportedOperationException {
        if (source == null)
            return target;

        switch (source.getNodeType())
        {
        case ELEMENT_NODE:
            return target.appendChild(cloneAttributes(source, source instanceof Element
                                                              ? Element.of(target.getOwnerDocument().createElement(source.getNodeName()))
                                                              : target.getOwnerDocument().createElement(source.getNodeName())));

        case TEXT_NODE:
            return target.appendChild(target.getOwnerDocument().createTextNode(source.getNodeValue()));

        case CDATA_SECTION_NODE:
            return target.appendChild(target.getOwnerDocument().createCDATASection(source.getNodeValue()));

        case PROCESSING_INSTRUCTION_NODE:
            return target.appendChild(target.getOwnerDocument().createProcessingInstruction(source.getNodeName(), source.getNodeValue()));

        case COMMENT_NODE:
            return target.appendChild(target.getOwnerDocument().createComment(source.getNodeValue()));
        }

        throw new UnsupportedOperationException(colon(XmlElementUnsupported) + source.getNodeType());
    }

    /**
     * Clones the entire inner structure of the source element and adds them as children to the target element, and returns the target element.
     * <p/>
     * This implementation is recursive.
     *
     * @param source the source element.
     * @param target the target element.
     *
     * @return the target element.
     *
     * @see #cloneChild(Node, Node)
     */
    public static
    Node cloneChildren(
        final Node source,
        final Node target
        )
    throws DOMException {
        if (source != null && source.hasChildNodes())
            for (Node child = source.getFirstChild(); child != null; child = child.getNextSibling())
                cloneChildren(child, cloneChild(child, target));

        return target;
    }

    /**
     * Returns the attribute of the specified element matching the specified attribute condition, or null if the attribute doesn't exist.
     * <p/>
     * The attribute predicate must accept the attribute as argument.
     *
     * @param element the element.
     * @param pred the attribute condition predicate.
     *
     * @return the attribute or null if it doesn't exist.
     *
     * @throws NullPointerException if the element or the predicate is null.
     */
    public static
    Node findAttribute(
        final Node element,
        final Predicate<Node> pred
        ) {
        return findAttribute(element, 0, -1, pred);
    }

    /**
     * Returns the attribute of the specified element, starting from the specified index, and matching the specified attribute condition, or null if the attribute doesn't exist.
     * <p/>
     * The attribute predicate must accept the attribute as argument.
     *
     * @param element the element.
     * @param start the starting attribute index. (inclusive)
     * @param pred the attribute condition predicate.
     *
     * @return the attribute or null if it doesn't exist.
     *
     * @throws NullPointerException if the element or the predicate is null.
     */
    public static
    Node findAttribute(
        final Node element,
        final int start,
        final Predicate<Node> pred
        ) {
        return findAttribute(element, start, -1, pred);
    }

    /**
     * Returns the attribute of the specified element, starting from and ending in the specified indexes, and matching the specified attribute condition, or null if the attribute doesn't exist.
     * <p/>
     * If the ending index is negative, it will be wrapped <b>once</b> around the attributes length.
     * <p/>
     * The attribute predicate must accept the attribute as argument.
     *
     * @param element the element.
     * @param start the starting attribute index. (inclusive)
     * @param end the ending attribute index. (exclusive)
     * @param pred the attribute condition predicate.
     *
     * @return the attribute or null if it doesn't exist.
     *
     * @throws NullPointerException if the element or the predicate is null.
     */
    public static
    Node findAttribute(
        final Node element,
        int start,
        int end,
        final Predicate<Node> pred
        ) {
        final NamedNodeMap attributes = element.getAttributes();
        if (attributes == null)
            return null;

        if (end < 0)
            end = (end + attributes.getLength() + 1) % (attributes.getLength() + 1);

        if (start < 0 || end > attributes.getLength() || start >= end)
            return null;

        for (; start < end; start++) {
            final Node attribute = attributes.item(start);
            if (pred.test(attribute))
                return attribute;
        }

        return null;
    }

    /**
     * Returns the attribute value of the specified element with the specified attribute name, or null if the attribute doesn't exist.
     *
     * @param element the element.
     * @param name the attribute name.
     *
     * @return the attribute value or null if it doesn't exist.
     *
     * @throws NullPointerException if the element is null.
     */
    public static
    String findAttributeValue(
        final Node element,
        final String name
        ) {
        final NamedNodeMap attributes = element.getAttributes();
        if (attributes != null) {
            final Node attribute = attributes.getNamedItem(name);
            if (attribute != null)
                return attribute.getNodeValue();
        }

        return null;
    }

    /**
     * Returns the first child element within the specified parent element and matching the specified child element condition, or null if the child element doesn't exist.
     * <p/>
     * The child element bi-predicate must accept an integer for the child element's order of appearance and the child element as arguments.
     *
     * @param parent the parent element.
     * @param pred the child element condition bi-predicate.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element or the bi-predicate is null.
     */
    public static
    Node findChild(
        final Node parent,
        final BiPredicate<Integer, Node> pred
        ) {
        return findChild(parent, 0, -1, pred);
    }

    /**
     * Returns the first child element within the specified parent element, starting from the specified index, and matching the specified child element condition, or null if the child element doesn't exist.
     * <p/>
     * The child element bi-predicate must accept an integer for the child element's order of appearance and the child element as arguments.
     *
     * @param parent the parent element.
     * @param start the starting child element index. (inclusive)
     * @param pred the child element condition bi-predicate.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element or the bi-predicate is null.
     */
    public static
    Node findChild(
        final Node parent,
        final int start,
        final BiPredicate<Integer, Node> pred
        ) {
        return findChild(parent, start, -1, pred);
    }

    /**
     * Returns the first child element within the specified parent element, starting from and ending in the specified indexes, and matching the specified child element condition, or null if the child element doesn't exist.
     * <p/>
     * If the ending index is negative, it will be wrapped <b>once</b> around the children length.
     * <p/>
     * The child element bi-predicate must accept an integer for the child element's order of appearance and the child element as arguments.
     *
     * @param parent the parent element.
     * @param start the starting child element index. (inclusive)
     * @param end the ending child element index. (exclusive)
     * @param pred the child element condition bi-predicate.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element or the bi-predicate is null.
     */
    public static
    Node findChild(
        final Node parent,
        int start,
        int end,
        final BiPredicate<Integer, Node> pred
        ) {
        final NodeList children = parent.getChildNodes();
        if (end < 0)
            end = (end + children.getLength() + 1) % (children.getLength() + 1);

        if (start < 0 || end > children.getLength() || start >= end)
            return null;

        for (; start < end; start++) {
            final Node child = children.item(start);
            if (pred.test(start, child))
                return child;
        }

        return null;
    }

    /**
     * Returns the first child element within the specified parent element and matching the child element name, or null if the child element doesn't exist.
     *
     * @param parent the parent element.
     * @param name the child element name.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element is null.
     */
    public static
    Node findChild(
        final Node parent,
        final String name
        ) {
        return findChild(parent, 0, -1, name);
    }

    /**
     * Returns the first child element within the specified parent element, starting from the specified index, and matching the child element name, or null if the child element doesn't exist.
     *
     * @param parent the parent element.
     * @param start the starting child element index. (inclusive)
     * @param name the child element name.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element is null.
     */
    public static
    Node findChild(
        final Node parent,
        final int start,
        final String name
        ) {
        return findChild(parent, start, -1, name);
    }

    /**
     * Returns the first child element within the specified parent element, starting from and ending in the specified indexes, and matching the child element name, or null if the child element doesn't exist.
     * <p/>
     * If the ending index is negative, it will be wrapped <b>once</b> around the children length.
     *
     * @param parent the parent element.
     * @param start the starting child element index. (inclusive)
     * @param end the ending child element index. (exclusive)
     * @param name the child element name.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element is null.
     */
    public static
    Node findChild(
        final Node parent,
        final int start,
        final int end,
        final String name
        ) {
        return findChild(parent, start, end, (final Integer i, final Node ch) -> ch.getNodeName().equals(name));
    }

    /**
     * Returns the first child element within the specified parent element and matching the specified element and attribute conditions, or null if the child element doesn't exist.
     * <p/>
     * The child element bi-predicate must accept an integer for the child element's order of appearance and the child element as arguments.
     * The child element attribute predicate must accept the attribute as argument.
     *
     * @param parent the parent element.
     * @param pred the child element condition bi-predicate.
     * @param attrPred the child element attribute condition predicate.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element or a predicate is null.
     */
    public static
    Node findChild(
        final Node parent,
        final BiPredicate<Integer, Node> pred,
        final Predicate<Node> attrPred
        ) {
        return findChild(parent, 0, -1, pred, attrPred);
    }

    /**
     * Returns the first child element within the specified parent element, starting from the specified index, and matching the specified element and attribute conditions, or null if the child element doesn't exist.
     * <p/>
     * The child element bi-predicate must accept an integer for the child element's order of appearance and the child element as arguments.
     * The child element attribute predicate must accept the attribute as argument.
     *
     * @param parent the parent element.
     * @param start the starting child element index. (inclusive)
     * @param pred the child element condition bi-predicate.
     * @param attrPred the child element attribute condition predicate.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element or a predicate is null.
     */
    public static
    Node findChild(
        final Node parent,
        int start,
        final BiPredicate<Integer, Node> pred,
        final Predicate<Node> attrPred
        ) {
        return findChild(parent, start, -1, pred, attrPred);
    }

    /**
     * Returns the first child element within the specified parent element, starting from and ending in the specified indexes, and matching the specified child element and attribute conditions, or null if the child element doesn't exist.
     * <p/>
     * If the ending index is negative, it will be wrapped <b>once</b> around the children length.
     * <p/>
     * The child element bi-predicate must accept an integer for the child element's order of appearance and the child element as arguments.
     * The child element attribute predicate must accept the attribute as argument.
     *
     * @param parent the parent element.
     * @param start the starting child element index. (inclusive)
     * @param end the ending child element index. (exclusive)
     * @param pred the child element condition bi-predicate.
     * @param attrPred the child element attribute condition predicate.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element or a predicate is null.
     */
    public static
    Node findChild(
        final Node parent,
        int start,
        int end,
        final BiPredicate<Integer, Node> pred,
        final Predicate<Node> attrPred
        ) {
        final NodeList children = parent.getChildNodes();
        if (end < 0)
            end = (end + children.getLength() + 1) % (children.getLength() + 1);

        if (start < 0 || end > children.getLength() || start >= end)
            return null;

        for (; start < end; start++) {
            final Node child = children.item(start);
            if (pred.test(start, child) && findAttribute(child, attrPred) != null)
                return child;
        }

        return null;
    }

    /**
     * Returns the first child element within the specified parent element and matching the child element name, attribute name, and value, or null if the child element doesn't exist.
     * <p/>
     * If the child element attribute value is null, any value will be accepted.
     *
     * @param parent the parent element.
     * @param name the child element name.
     * @param attrName the child element attribute name.
     * @param attrValue the child element attribute value.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element is null.
     */
    public static
    Node findChild(
        final Node parent,
        final String name,
        final String attrName,
        final String attrValue
        ) {
        return findChild(parent, 0, -1, name, attrName, attrValue);
    }

    /**
     * Returns the first child element within the specified parent element, starting from the specified index, and matching the child element name, attribute name, and value, or null if the child element doesn't exist.
     * <p/>
     * If the child element attribute value is null, any value will be accepted.
     *
     * @param parent the parent element.
     * @param start the starting child element index. (inclusive)
     * @param name the child element name.
     * @param attrName the child element attribute name.
     * @param attrValue the child element attribute value.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element is null.
     */
    public static
    Node findChild(
        final Node parent,
        final int start,
        final String name,
        final String attrName,
        final String attrValue
        ) {
        return findChild(parent, start, -1, name, attrName, attrValue);
    }

    /**
     * Returns the first child element within the specified parent element, starting from and ending in the specified indexes, and matching the child element name, attribute name, and value, or null if the child element doesn't exist.
     * <p/>
     * If the child element attribute value is null, any value will be accepted.
     *
     * @param parent the parent element.
     * @param start the starting child element index. (inclusive)
     * @param end the ending child element index. (exclusive)
     * @param name the child element name.
     * @param attrName the child element attribute name.
     * @param attrValue the child element attribute value.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element is null.
     */
    public static
    Node findChild(
        final Node parent,
        final int start,
        final int end,
        final String name,
        final String attrName,
        final String attrValue
        ) {
        return findChild(parent, start, end,
            (final Integer i, final Node ch) -> ch.getNodeName().equals(name),
            (final Node attr) -> attr.getNodeName().equals(attrName) &&
                                 (attrValue == null || attr.getNodeValue().equals(attrValue)));
    }

    /**
     * Returns an iterable for all child elements within the specified parent element.
     * <p/>
     * If the parent element is null, an empty iterable will be returned.
     *
     * @param parent the parent element.
     *
     * @return the iterable for child elements.
     */
    public static
    Iterable<Node> findChildren(
        final Node parent
        ) {
        return parent == null || !parent.hasChildNodes()
               ? EmptyIterable
               : () -> new Iterator<Node>() {
                    Node child = parent.getFirstChild();

                    @Override
                    public boolean hasNext() {
                        return child != null;
                    }

                    @Override
                    public Node next() {
                        if (child == null)
                            throw new NoSuchElementException(XmlChildNotFound);

                        final Node ch = child;
                        child = child.getNextSibling();
                        return ch;
                    }
                };
    }

    /**
     * Returns an iterable for child elements within the specified parent element and matching the specified child element condition.
     * <p/>
     * If the parent element is null, an empty iterable will be returned.
     * <p/>
     * The child element bi-predicate must accept an integer for the child element's order of appearance and the child element as arguments.
     *
     * @param parent the parent element.
     * @param pred the child element condition bi-predicate.
     *
     * @return the iterable for child elements.
     *
     * @throws NullPointerException if the bi-predicate is null.
     */
    public static
    Iterable<Node> findChildren(
        final Node parent,
        final BiPredicate<Integer, Node> pred
        ) {
        return parent == null || !parent.hasChildNodes()
               ? EmptyIterable
               : () -> new NodeListIterator(parent.getChildNodes()) {
                   @Override
                   protected void updateHasNext() {
                       for (; i < elements.getLength(); i++)
                           if (pred.test(i, elements.item(i))) {
                               hasNext = true;
                               return;
                           }

                       hasNext = false;
                   }
               };
    }

    /**
     * Returns an iterable for child elements within the specified parent element and matching the specified child element and attribute conditions.
     * <p/>
     * If the parent element is null, an empty iterable will be returned.
     * <p/>
     * The child element bi-predicate must accept an integer for the child element's order of appearance and the child element as arguments.
     * The child element attribute predicate must accept the attribute as argument.
     *
     * @param parent the parent element.
     * @param pred the child element condition bi-predicate.
     * @param attrPred the child element attribute condition predicate.
     *
     * @return the iterable for child elements.
     *
     * @throws NullPointerException if a predicate is null.
     */
    public static
    Iterable<Node> findChildren(
        final Node parent,
        final BiPredicate<Integer, Node> pred,
        final Predicate<Node> attrPred
        ) {
        return parent == null || !parent.hasChildNodes()
               ? EmptyIterable
               : () -> new NodeListIterator(parent.getChildNodes()) {
                   @Override
                   protected void updateHasNext() {
                       for (; i < elements.getLength(); i++) {
                           final Node child = elements.item(i);
                           if (pred.test(i, child) && findAttribute(child, attrPred) != null) {
                               hasNext = true;
                               return;
                           }
                       }

                       hasNext = false;
                   }
               };
    }

    /**
     * Returns an iterable for child elements within the specified parent element and matching the specified child element name.
     * <p/>
     * If the parent element is null, an empty iterable will be returned.
     *
     * @param parent the parent element.
     * @param name the child element name.
     *
     * @return the iterable for child elements.
     */
    public static
    Iterable<Node> findChildren(
        final Node parent,
        final String name
        ) {
        return findChildren(parent, (final Integer i, final Node ch) -> ch.getNodeName().equals(name));
    }

    /**
     * Returns an iterable for child elements within the specified parent element and matching the specified child element name, attribute name, and value.
     * <p/>
     * If the parent element is null, an empty iterable will be returned.
     * <p/>
     * If the child element name is null, all children will be accepted.
     * If the child element attribute value is null, any value will be accepted.
     *
     * @param parent the parent element.
     * @param name the child element name.
     * @param attrName the child element attribute name.
     * @param attrValue the child element attribute value.
     *
     * @return the iterable for child elements.
     */
    public static
    Iterable<Node> findChildren(
        final Node parent,
        final String name,
        final String attrName,
        final String attrValue
        ) {
        return findChildren(parent,
               (final Integer i, final Node ch) -> name == null || ch.getNodeName().equals(name),
               (final Node attr) -> attr.getNodeName().equals(attrName) &&
                                    (attrValue == null || attr.getNodeValue().equals(attrValue)));
    }

    /**
     * Returns the element coordinates.
     * <p/>
     * If the element is not enclosed by a parent element, an empty array will be returned.
     *
     * @param element the element.
     *
     * @return the element coordinates.
     *
     * @throws NullPointerException if the element is null.
     */
    public static
    int[] findCoords(
        Node element
        ) {
        int[] coords = new int[findDepth(element)];
        for (int i = coords.length - 1; i > 0; element = element.getParentNode())
            coords[i--] = findIndex(element);

        return coords;
    }

    /**
     * Returns the element depth.
     *
     * @param element the element.
     *
     * @return the element depth.
     *
     * @throws NullPointerException if the element is null.
     */
    public static
    int findDepth(
        final Node element
        ) {
        int depth = 0;
        for (Node parent = element.getParentNode(); parent != null; parent = parent.getParentNode())
            depth++;

        return depth;
    }

    /**
     * Returns the first grand child element within the specified ancestor element and matching the specified grand child element condition, or null if the grand child element doesn't exist.
     * <p/>
     * The grand child element bi-predicate must accept an integer for the grand child element's order of appearance and the grand child element as arguments.
     *
     * @param ancestor the ancestor element.
     * @param pred the grand child element condition bi-predicate.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element or the bi-predicate is null.
     */
    public static
    Node findGrandChild(
        final Node ancestor,
        final BiPredicate<Integer, Node> pred
        ) {
        return findGrandChild(ancestor, 0, -1, pred);
    }

    /**
     * Returns the first grand child element within the specified ancestor element, starting from the specified parent element index, and matching the specified grand child element condition, or null if the grand child element doesn't exist.
     * <p/>
     * The grand child element bi-predicate must accept an integer for the grand child element's order of appearance and the grand child element as arguments.
     *
     * @param ancestor the ancestor element.
     * @param start the starting parent element index. (inclusive)
     * @param pred the grand child element condition bi-predicate.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element or the bi-predicate is null.
     */
    public static
    Node findGrandChild(
        final Node ancestor,
        final int start,
        final BiPredicate<Integer, Node> pred
        ) {
        return findGrandChild(ancestor, start, -1, pred);
    }

    /**
     * Returns the first grand child element within the specified ancestor element, starting from and ending in the specified parent element indexes, and matching the specified grand child element condition, or null if the grand child element doesn't exist.
     * <p/>
     * If the ending index is negative, it will be wrapped <b>once</b> around the parents length.
     * <p/>
     * The grand child element bi-predicate must accept an integer for the grand child element's order of appearance and the grand child element as arguments.
     *
     * @param ancestor the ancestor element.
     * @param start the starting parent element index. (inclusive)
     * @param end the ending parent element index. (exclusive)
     * @param pred the grand child element condition bi-predicate.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element or the bi-predicate is null.
     */
    public static
    Node findGrandChild(
        final Node ancestor,
        int start,
        int end,
        final BiPredicate<Integer, Node> pred
        ) {
        final NodeList parents = ancestor.getChildNodes();
        if (end < 0)
            end = (end + parents.getLength() + 1) % (parents.getLength() + 1);

        if (start < 0 || end > parents.getLength() || start >= end)
            return null;

        for (; start < end; start++) {
            final NodeList children = parents.item(start).getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                final Node child = children.item(i);
                if (pred.test(i, child))
                    return child;
            }
        }

        return null;
    }

    /**
     * Returns the first grand child element within the specified ancestor element and matching the grand child element name, or null if the grand child element doesn't exist.
     *
     * @param ancestor the ancestor element.
     * @param name the grand child element name.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element is null.
     */
    public static
    Node findGrandChild(
        final Node ancestor,
        final String name
        ) {
        return findGrandChild(ancestor, 0, -1, name);
    }

    /**
     * Returns the first grand child element within the specified ancestor element, starting from the specified parent element index, and matching the grand child element name, or null if the grand child element doesn't exist.
     *
     * @param ancestor the ancestor element.
     * @param start the starting parent element index. (inclusive)
     * @param name the grand child element name.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element is null.
     */
    public static
    Node findGrandChild(
        final Node ancestor,
        final int start,
        final String name
        ) {
        return findGrandChild(ancestor, start, -1, name);
    }

    /**
     * Returns the first grand child element within the specified ancestor element, starting from and ending in the specified parent element indexes, and matching the grand child element name, or null if the grand child element doesn't exist.
     * <p/>
     * If the ending index is negative, it will be wrapped <b>once</b> around the parents length.
     *
     * @param ancestor the ancestor element.
     * @param start the starting parent element index. (inclusive)
     * @param end the ending parent element index. (exclusive)
     * @param name the grand child element name.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element is null.
     */
    public static
    Node findGrandChild(
        final Node ancestor,
        final int start,
        final int end,
        final String name
        ) {
        return findGrandChild(ancestor, start, end, (final Integer i, final Node ch) -> ch.getNodeName().equals(name));
    }

    /**
     * Returns the first grand child element within the specified ancestor element, starting from and ending in the specified parent element indexes, and matching the specified grand child element and attribute conditions, or null if the grand child element doesn't exist.
     * <p/>
     * If the ending index is negative, it will be wrapped <b>once</b> around the parents length.
     * <p/>
     * The grand child element bi-predicate must accept an integer for the grand child element's order of appearance and the grand child element as arguments.
     * The grand child element attribute predicate must accept the attribute as argument.
     *
     * @param ancestor the ancestor element.
     * @param start the starting parent element index. (inclusive)
     * @param end the ending parent element index. (exclusive)
     * @param pred the grand child element condition bi-predicate.
     * @param attrPred the grand child element attribute condition predicate.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element or a predicate is null.
     */
    public static
    Node findGrandChild(
        final Node ancestor,
        int start,
        int end,
        final BiPredicate<Integer, Node> pred,
        final Predicate<Node> attrPred
        ) {
        final NodeList parents = ancestor.getChildNodes();
        if (end < 0)
            end = (end + parents.getLength() + 1) % (parents.getLength() + 1);

        if (start < 0 || end > parents.getLength() || start >= end)
            return null;

        for (; start < end; start++) {
            final NodeList children = parents.item(start).getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                final Node child = children.item(i);
                if (pred.test(i, child) && findAttribute(child, attrPred) != null)
                    return child;
            }
        }

        return null;
    }

    /**
     * Returns the first grand child element within the specified ancestor element and matching the grand child element name, attribute name, and value, or null if the grand child element doesn't exist.
     * <p/>
     * If the grand child element attribute value is null, any value will be accepted.
     *
     * @param ancestor the ancestor element.
     * @param name the grand child element name.
     * @param attrName the grand child element attribute name.
     * @param attrValue the grand child element attribute value.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element is null.
     */
    public static
    Node findGrandChild(
        final Node ancestor,
        final String name,
        final String attrName,
        final String attrValue
        ) {
        return findGrandChild(ancestor, 0, -1, name, attrName, attrValue);
    }

    /**
     * Returns the first grand child element within the specified ancestor element, starting from the specified parent index, and matching the grand child element name, attribute name, and value, or null if the grand child element doesn't exist.
     * <p/>
     * If the ending index is negative, it will be wrapped <b>once</b> around the parents length.
     * <p/>
     * If the grand child element attribute value is null, any value will be accepted.
     *
     * @param ancestor the ancestor element.
     * @param start the starting parent element index. (inclusive)
     * @param name the grand child element name.
     * @param attrName the grand child element attribute name.
     * @param attrValue the grand child element attribute value.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element is null.
     */
    public static
    Node findGrandChild(
        final Node ancestor,
        final int start,
        final String name,
        final String attrName,
        final String attrValue
        ) {
        return findGrandChild(ancestor, start, -1, name, attrName, attrValue);
    }

    /**
     * Returns the first grand child element within the specified ancestor element, starting from and ending in the specified parent element indexes, and matching the grand child element name, attribute name, and value, or null if the grand child element doesn't exist.
     * <p/>
     * If the ending index is negative, it will be wrapped <b>once</b> around the parents length.
     * <p/>
     * If the grand child element attribute value is null, any value will be accepted.
     *
     * @param ancestor the ancestor element.
     * @param start the starting parent element index. (inclusive)
     * @param end the ending parent element index. (exclusive)
     * @param name the grand child element name.
     * @param attrName the grand child element attribute name.
     * @param attrValue the grand child element attribute value.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element is null.
     */
    public static
    Node findGrandChild(
        final Node ancestor,
        final int start,
        final int end,
        final String name,
        final String attrName,
        final String attrValue
        ) {
        return findGrandChild(ancestor, start, end,
            (final Integer i, final Node ch) -> ch.getNodeName().equals(name),
            (final Node attr) -> attr.getNodeName().equals(attrName) &&
                                 (attrValue == null || attr.getNodeValue().equals(attrValue)));
    }

    /**
     * Returns an iterable for all grand child elements within the specified ancestor element.
     * <p/>
     * If the ancestor element is null, an empty iterable will be returned.
     *
     * @param ancestor the ancestor element.
     *
     * @return the iterable for grand child elements.
     */
    public static
    Iterable<Node> findGrandChildren(
        final Node ancestor
        ) {
        return ancestor == null || !ancestor.hasChildNodes()
               ? EmptyIterable
               : () -> new Iterator<Node>() {
                    Node parent = ancestor.getFirstChild();

                    Node child = nextChild(parent.getFirstChild());

                    Node nextChild(
                        Node ch
                        ) {
                        while (ch == null) {
                            parent = parent.getNextSibling();
                            if (parent == null)
                                break;

                            ch = parent.getFirstChild();
                        }

                        return ch;
                    }

                    @Override
                    public boolean hasNext() {
                        return child != null;
                    }

                    @Override
                    public Node next() {
                        if (child == null)
                            throw new NoSuchElementException(XmlChildNotFound);

                        final Node ch = child;
                        child = nextChild(child.getNextSibling());
                        return ch;
                    }
                };
    }

    /**
     * Returns an iterable for grand child elements within the specified ancestor element and matching the specified grand child element condition.
     * <p/>
     * If the ancestor element is null, an empty iterable will be returned.
     * <p/>
     * The grand child element bi-predicate must accept an integer for the grand child element's order of appearance and the grand child element as arguments.
     *
     * @param ancestor the ancestor element.
     * @param pred the grand child element condition bi-predicate.
     *
     * @return the iterable for grand child elements.
     *
     * @throws NullPointerException if the bi-predicate is null.
     */
    public static
    Iterable<Node> findGrandChildren(
        final Node ancestor,
        final BiPredicate<Integer, Node> pred
        ) {
        if (ancestor == null || !ancestor.hasChildNodes())
            return EmptyIterable;

        final Node p = ancestor.getFirstChild();
        return () -> new NodeListIterator(p.getChildNodes()) {
                         Node parent = p;

                         @Override
                         protected void updateHasNext() {
                             while (parent != null) {
                                 for (; i < elements.getLength(); i++)
                                     if (pred.test(i, elements.item(i))) {
                                         hasNext = true;
                                         return;
                                     }

                                 parent = parent.getNextSibling();
                                 elements = parent.getChildNodes();
                             }

                             hasNext = false;
                         }
                     };
    }

    /**
     * Returns an iterable for grand child elements within the specified ancestor element and matching the specified grand child element and attribute conditions.
     * <p/>
     * If the ancestor element is null, an empty iterable will be returned.
     * <p/>
     * The grand child element bi-predicate must accept an integer for the grand child element's order of appearance and the grand child element as arguments.
     * The grand child element attribute predicate must accept the attribute as argument.
     *
     * @param ancestor the ancestor element.
     * @param pred the grand child element condition bi-predicate.
     * @param attrPred the grand child element attribute condition predicate.
     *
     * @return the iterable for grand child elements.
     *
     * @throws NullPointerException if a predicate is null.
     */
    public static
    Iterable<Node> findGrandChildren(
        final Node ancestor,
        final BiPredicate<Integer, Node> pred,
        final Predicate<Node> attrPred
        ) {
        if (ancestor == null || !ancestor.hasChildNodes())
            return EmptyIterable;

        final Node p = ancestor.getFirstChild();
        return () -> new NodeListIterator(p.getChildNodes()) {
                         Node parent = p;

                         @Override
                         protected void updateHasNext() {
                             while (parent != null) {
                                 for (; i < elements.getLength(); i++) {
                                     final Node child = elements.item(i);
                                     if (pred.test(i, child) && findAttribute(child, attrPred) != null) {
                                         hasNext = true;
                                         return;
                                     }
                                 }

                                 parent = parent.getNextSibling();
                                 elements = parent.getChildNodes();
                             }

                             hasNext = false;
                         }
                     };
    }

    /**
     * Returns an iterable for grand child elements within the specified ancestor element and matching the specified grand child element name.
     * <p/>
     * If the ancestor element is null, an empty iterable will be returned.
     *
     * @param ancestor the ancestor element.
     * @param name the grand child element name.
     *
     * @return the iterable for grand child elements.
     */
    public static
    Iterable<Node> findGrandChildren(
        final Node ancestor,
        final String name
        ) {
        return findGrandChildren(ancestor, (final Integer i, final Node ch) -> ch.getNodeName().equals(name));
    }

    /**
     * Returns an iterable for grand child elements within the specified ancestor element and matching the specified grand child element name, attribute name, and value.
     * <p/>
     * If the ancestor element is null, an empty iterable will be returned.
     * <p/>
     * If the grand child element name is null, all grand children will be accepted.
     * If the grand child element attribute value is null, any value will be accepted.
     *
     * @param ancestor the ancestor element.
     * @param name the grand child element name.
     * @param attrName the grand child element attribute name.
     * @param attrValue the grand child element attribute value.
     *
     * @return the iterable for grand child elements.
     */
    public static
    Iterable<Node> findGrandChildren(
        final Node ancestor,
        final String name,
        final String attrName,
        final String attrValue
        ) {
        return findGrandChildren(ancestor,
               (final Integer i, final Node ch) -> name == null || ch.getNodeName().equals(name),
               (final Node attr) -> attr.getNodeName().equals(attrName) &&
                                    (attrValue == null || attr.getNodeValue().equals(attrValue)));
    }

    /**
     * Returns the last child element within the specified parent element and matching the specified child element condition, or null if the child element doesn't exist.
     * <p/>
     * The child element bi-predicate must accept an integer for the child element's order of appearance and the child element as arguments.
     *
     * @param parent the parent element.
     * @param pred the child element condition bi-predicate.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element or the bi-predicate is null.
     */
    public static
    Node findLastChild(
        final Node parent,
        final BiPredicate<Integer, Node> pred
        ) {
        return findLastChild(parent, -1, 0, pred);
    }

    /**
     * Returns the last child element within the specified parent element, starting from the specified index, and matching the specified child element condition, or null if the child element doesn't exist.
     * <p/>
     * This implementation starts looking backwards for child elements from the element located before the ending index.
     * In other words, the ending index is exclusive.
     * If the ending index is negative, it will be wrapped <b>once</b> around the children length.
     * <p/>
     * The child element bi-predicate must accept an integer for the child element's order of appearance and the child element as arguments.
     *
     * @param parent the parent element.
     * @param end the ending child element index. (exclusive)
     * @param pred the child element condition bi-predicate.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element or the bi-predicate is null.
     */
    public static
    Node findLastChild(
        final Node parent,
        final int end,
        final BiPredicate<Integer, Node> pred
        ) {
        return findLastChild(parent, end, 0, pred);
    }

    /**
     * Returns the last child element within the specified parent element, starting from and ending in the specified indexes, and matching the specified child element condition, or null if the child element doesn't exist.
     * <p/>
     * This implementation starts looking backwards for child elements from the element located before the ending index.
     * In other words, the ending index is exclusive.
     * If the ending index is negative, it will be wrapped <b>once</b> around the children length.
     * <p/>
     * The child element bi-predicate must accept an integer for the child element's order of appearance and the child element as arguments.
     *
     * @param parent the parent element.
     * @param end the ending child element index. (exclusive)
     * @param start the starting child element index. (inclusive)
     * @param pred the child element condition bi-predicate.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element or the bi-predicate is null.
     */
    public static
    Node findLastChild(
        final Node parent,
        int end,
        int start,
        final BiPredicate<Integer, Node> pred
        ) {
        final NodeList children = parent.getChildNodes();
        if (end < 0)
            end = (end + children.getLength() + 1) % (children.getLength() + 1);

        if (start < 0 || end > children.getLength() || end <= start)
            return null;

        end--;
        for (; end >= start; end--) {
            final Node child = children.item(end);
            if (pred.test(end, child))
                return child;
        }

        return null;
    }

    /**
     * Returns the last child element within the specified parent element and matching the child element name, or null if the child element doesn't exist.
     *
     * @param parent the parent element.
     * @param name the child element name.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element is null.
     */
    public static
    Node findLastChild(
        final Node parent,
        final String name
        ) {
        return findLastChild(parent, -1, 0, name);
    }

    /**
     * Returns the last child element within the specified parent element, starting from the specified index, and matching the child element name, or null if the child element doesn't exist.
     * <p/>
     * This implementation starts looking backwards for child elements from the element located before the ending index.
     * In other words, the ending index is exclusive.
     * If the ending index is negative, it will be wrapped <b>once</b> around the children length.
     *
     * @param parent the parent element.
     * @param end the ending child element index. (exclusive)
     * @param name the child element name.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element is null.
     */
    public static
    Node findLastChild(
        final Node parent,
        final int end,
        final String name
        ) {
        return findLastChild(parent, end, 0, name);
    }

    /**
     * Returns the last child element within the specified parent element, starting from and ending in the specified indexes, and matching the child element name, or null if the child element doesn't exist.
     * <p/>
     * This implementation starts looking backwards for child elements from the element located before the ending index.
     * In other words, the ending index is exclusive.
     * If the ending index is negative, it will be wrapped <b>once</b> around the children length.
     *
     * @param parent the parent element.
     * @param end the ending child element index. (exclusive)
     * @param start the starting child element index. (inclusive)
     * @param name the child element name.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element is null.
     */
    public static
    Node findLastChild(
        final Node parent,
        final int end,
        final int start,
        final String name
        ) {
        return findLastChild(parent, end, start, (final Integer i, final Node ch) -> ch.getNodeName().equals(name));
    }

    /**
     * Returns the last child element within the specified parent element and matching the specified child element and attribute conditions, or null if the child element doesn't exist.
     * <p/>
     * The child element bi-predicate must accept an integer for the child element's order of appearance and the child element as arguments.
     * The child element attribute predicate must accept the attribute as argument.
     *
     * @param parent the parent element.
     * @param pred the child element condition bi-predicate.
     * @param attrPred the child element attribute condition predicate.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element or a predicate is null.
     */
    public static
    Node findLastChild(
        final Node parent,
        final BiPredicate<Integer, Node> pred,
        final Predicate<Node> attrPred
        ) {
        return findLastChild(parent, 0, -1, pred, attrPred);
    }

    /**
     * Returns the last child element within the specified parent element, starting from the specified index, and matching the specified child element and attribute conditions, or null if the child element doesn't exist.
     * <p/>
     * This implementation starts looking backwards for child elements from the element located before the ending index.
     * In other words, the ending index is exclusive.
     * If the ending index is negative, it will be wrapped <b>once</b> around the children length.
     * <p/>
     * The child element bi-predicate must accept an integer for the child element's order of appearance and the child element as arguments.
     * The child element attribute bi-predicate must accept the attribute as argument.
     *
     * @param parent the parent element.
     * @param end the ending child element index. (exclusive)
     * @param pred the child element condition bi-predicate.
     * @param attrPred the child element attribute condition predicate.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element or a predicate is null.
     */
    public static
    Node findLastChild(
        final Node parent,
        int end,
        final BiPredicate<Integer, Node> pred,
        final Predicate<Node> attrPred
        ) {
        return findLastChild(parent, end, 0, pred, attrPred);
    }

    /**
     * Returns the last child element within the specified parent element, starting from and ending in the specified indexes, and matching the specified child element and attribute conditions, or null if the child element doesn't exist.
     * <p/>
     * This implementation starts looking backwards for child elements from the element located before the ending index.
     * In other words, the ending index is exclusive.
     * If the ending index is negative, it will be wrapped <b>once</b> around the children length.
     * <p/>
     * The child element bi-predicate must accept an integer for the child element's order of appearance and the child element as arguments.
     * The child element attribute predicate must accept the attribute as argument.
     *
     * @param parent the parent element.
     * @param end the ending child element index. (exclusive)
     * @param start the starting child element index. (inclusive)
     * @param pred the child element condition bi-predicate.
     * @param attrPred the child element attribute condition predicate.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element or a predicate is null.
     */
    public static
    Node findLastChild(
        final Node parent,
        int end,
        int start,
        final BiPredicate<Integer, Node> pred,
        final Predicate<Node> attrPred
        ) {
        final NodeList children = parent.getChildNodes();
        if (end < 0)
            end = (end + children.getLength() + 1) % (children.getLength() + 1);

        if (start < 0 || end > children.getLength() || end <= start)
            return null;

        end--;
        for (; end >= start; end--) {
            final Node child = children.item(end);
            if (pred.test(end, child) && findAttribute(child, attrPred) != null)
                return child;
        }

        return null;
    }

    /**
     * Returns the last child element within the specified parent element and matching the child element name, attribute name, and value, or null if the child element doesn't exist.
     * <p/>
     * If the child element attribute value is null, any value will be accepted.
     *
     * @param parent the parent element.
     * @param name the child element name.
     * @param attrName the child element attribute name.
     * @param attrValue the child element attribute value.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element is null.
     */
    public static
    Node findLastChild(
        final Node parent,
        final String name,
        final String attrName,
        final String attrValue
        ) {
        return findLastChild(parent, -1, 0, name, attrName, attrValue);
    }

    /**
     * Returns the last child element within the specified parent element, starting from the specified index, and matching the child element name, attribute name, and value, or null if the child element doesn't exist.
     * <p/>
     * This implementation starts looking backwards for child elements from the element located before the ending index.
     * In other words, the ending index is exclusive.
     * If the ending index is negative, it will be wrapped <b>once</b> around the children length.
     * <p/>
     * If the child element attribute value is null, any value will be accepted.
     *
     * @param parent the parent element.
     * @param end the ending child element index. (exclusive)
     * @param name the child element name.
     * @param attrName the child element attribute name.
     * @param attrValue the child element attribute value.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element is null.
     */
    public static
    Node findLastChild(
        final Node parent,
        final int end,
        final String name,
        final String attrName,
        final String attrValue
        ) {
        return findLastChild(parent, end, 0, name, attrName, attrValue);
    }

    /**
     * Returns the last child element within the specified parent element, starting from and ending in the specified indexes, and matching the child element name, attribute name, and value, or null if the child element doesn't exist.
     * <p/>
     * This implementation starts looking backwards for child elements from the element located before the ending index.
     * In other words, the ending index is exclusive.
     * If the ending index is negative, it will be wrapped <b>once</b> around the children length.
     * <p/>
     * If the child element attribute value is null, any value will be accepted.
     *
     * @param parent the parent element.
     * @param end the ending child element index. (exclusive)
     * @param start the starting child element index. (inclusive)
     * @param name the child element name.
     * @param attrName the child element attribute name.
     * @param attrValue the child element attribute value.
     *
     * @return the child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the parent element is null.
     */
    public static
    Node findLastChild(
        final Node parent,
        final int end,
        final int start,
        final String name,
        final String attrName,
        final String attrValue
        ) {
        return findLastChild(parent, end, start,
            (final Integer i, final Node ch) -> ch.getNodeName().equals(name),
            (final Node attr) -> attr.getNodeName().equals(attrName) &&
                                 (attrValue == null || attr.getNodeValue().equals(attrValue)));
    }

    /**
     * Returns the last grand child element within the specified ancestor element and matching the specified grand child element condition, or null if the grand child element doesn't exist.
     * <p/>
     * The grand child element bi-predicate must accept an integer for the grand child element's order of appearance and the grand child element as arguments.
     *
     * @param ancestor the ancestor element.
     * @param pred the grand child element condition bi-predicate.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element or the bi-predicate is null.
     */
    public static
    Node findLastGrandChild(
        final Node ancestor,
        final BiPredicate<Integer, Node> pred
        ) {
        return findLastGrandChild(ancestor, -1, 0, pred);
    }

    /**
     * Returns the last grand child element within the specified ancestor element, starting from the specified parent element index, and matching the specified grand child element condition, or null if the grand child element doesn't exist.
     * <p/>
     * This implementation starts looking backwards for grand child elements from the parent element located before the ending index.
     * In other words, the ending index is exclusive.
     * If the ending index is negative, it will be wrapped <b>once</b> around the parents length.
     * <p/>
     * The grand child element bi-predicate must accept an integer for the grand child element's order of appearance and the grand child element as arguments.
     *
     * @param ancestor the ancestor element.
     * @param end the ending parent element index. (exclusive)
     * @param pred the grand child element condition bi-predicate.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element or the bi-predicate is null.
     */
    public static
    Node findLastGrandChild(
        final Node ancestor,
        final int end,
        final BiPredicate<Integer, Node> pred
        ) {
        return findLastGrandChild(ancestor, end, 0, pred);
    }

    /**
     * Returns the last grand child element within the specified ancestor element, starting from and ending in the specified parent element indexes, and matching the specified grand child element condition, or null if the grand child element doesn't exist.
     * <p/>
     * This implementation starts looking backwards for grand child elements from the parent element located before the ending index.
     * In other words, the ending index is exclusive.
     * If the ending index is negative, it will be wrapped <b>once</b> around the parents length.
     * <p/>
     * The grand child element bi-predicate must accept an integer for the grand child element's order of appearance and the grand child element as arguments.
     *
     * @param ancestor the ancestor element.
     * @param end the ending parent element index. (exclusive)
     * @param start the starting parent element index. (inclusive)
     * @param pred the grand child element condition bi-predicate.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element or the bi-predicate is null.
     */
    public static
    Node findLastGrandChild(
        final Node ancestor,
        int end,
        int start,
        final BiPredicate<Integer, Node> pred
        ) {
        final NodeList parents = ancestor.getChildNodes();
        if (end < 0)
            end = (end + parents.getLength() + 1) % (parents.getLength() + 1);

        if (start < 0 || end > parents.getLength() || end <= start)
            return null;

        end--;
        for (; end >= start; end--) {
            final NodeList children = parents.item(end).getChildNodes();
            for (int i = children.getLength() - 1; i > 0; i--) {
                final Node child = children.item(i);
                if (pred.test(i, child))
                    return child;
            }
        }

        return null;
    }

    /**
     * Returns the last grand child element within the specified ancestor element and matching the grand child element name, or null if the grand child element doesn't exist.
     *
     * @param ancestor the ancestor element.
     * @param name the grand child element name.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element is null.
     */
    public static
    Node findLastGrandChild(
        final Node ancestor,
        final String name
        ) {
        return findLastGrandChild(ancestor, -1, 0, name);
    }

    /**
     * Returns the last grand child element within the specified ancestor element, starting from the specified parent element index, and matching the grand child element name, or null if the grand child element doesn't exist.
     * <p/>
     * This implementation starts looking backwards for grand child elements from the parent element located before the ending index.
     * In other words, the ending index is exclusive.
     * If the ending index is negative, it will be wrapped <b>once</b> around the parents length.
     *
     * @param ancestor the ancestor element.
     * @param end the ending parent element index. (exclusive)
     * @param name the grand child element name.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element is null.
     */
    public static
    Node findLastGrandChild(
        final Node ancestor,
        final int end,
        final String name
        ) {
        return findLastGrandChild(ancestor, end, 0, name);
    }

    /**
     * Returns the last grand child element within the specified ancestor element, starting from and ending in the specified parent element indexes, and matching the grand child element name, or null if the grand child element doesn't exist.
     * <p/>
     * This implementation starts looking backwards for grand child elements from the parent element located before the ending index.
     * In other words, the ending index is exclusive.
     * If the ending index is negative, it will be wrapped <b>once</b> around the parents length.
     *
     * @param ancestor the ancestor element.
     * @param end the ending parent element index. (exclusive)
     * @param start the starting parent element index. (inclusive)
     * @param name the grand child element name.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element is null.
     */
    public static
    Node findLastGrandChild(
        final Node ancestor,
        final int end,
        final int start,
        final String name
        ) {
        return findLastGrandChild(ancestor, end, start, (final Integer i, final Node ch) -> ch.getNodeName().equals(name));
    }

    /**
     * Returns the last grand child element within the specified ancestor element and matching the specified element and attribute conditions, or null if the grand child element doesn't exist.
     * <p/>
     * The grand child element bi-predicate must accept an integer for the grand child element's order of appearance and the grand child element as arguments.
     * The grand child element attribute bi-predicate must accept the attribute as argument.
     *
     * @param ancestor the ancestor element.
     * @param pred the grand child element condition bi-predicate.
     * @param attrPred the grand child attribute condition predicate.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element or a predicate is null.
     */
    public static
    Node findLastGrandChild(
        final Node ancestor,
        final BiPredicate<Integer, Node> pred,
        final Predicate<Node> attrPred
        ) {
        return findLastGrandChild(ancestor, -1, 0, pred, attrPred);
    }

    /**
     * Returns the last grand child element within the specified ancestor element, starting from the specified parent element index, and matching the specified grand child element and attribute conditions, or null if the grand child element doesn't exist.
     * <p/>
     * This implementation starts looking backwards for grand child elements from the parent element located before the ending index.
     * In other words, the ending index is exclusive.
     * If the ending index is negative, it will be wrapped <b>once</b> around the parents length.
     * <p/>
     * The grand child element bi-predicate must accept an integer for the grand child element's order of appearance and the grand child element as arguments.
     * The grand child element attribute predicate must accept the attribute as argument.
     *
     * @param ancestor the ancestor element.
     * @param end the ending parent element index. (exclusive)
     * @param pred the grand child element condition bi-predicate.
     * @param attrPred the grand child element attribute condition predicate.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element or a predicate is null.
     */
    public static
    Node findLastGrandChild(
        final Node ancestor,
        int end,
        final BiPredicate<Integer, Node> pred,
        final Predicate<Node> attrPred
        ) {
        return findLastGrandChild(ancestor, end, 0, pred, attrPred);
    }

    /**
     * Returns the last grand child element within the specified ancestor element, starting from and ending in the specified parent element indexes, and matching the specified grand child element and attribute conditions, or null if the grand child element doesn't exist.
     * <p/>
     * This implementation starts looking backwards for child elements from the parent element located before the ending index.
     * In other words, the ending index is exclusive.
     * If the ending index is negative, it will be wrapped <b>once</b> around the parents length.
     * <p/>
     * The grand child element bi-predicate must accept an integer for the grand child element's order of appearance and the grand child element as arguments.
     * The grand child element attribute predicate must accept the attribute as argument.
     *
     * @param ancestor the ancestor element.
     * @param end the ending parent element index. (exclusive)
     * @param start the starting parent element index. (inclusive)
     * @param pred the grand child element condition bi-predicate.
     * @param attrPred the grand child element attribute condition predicate.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element or a predicate is null.
     */
    public static
    Node findLastGrandChild(
        final Node ancestor,
        int end,
        int start,
        final BiPredicate<Integer, Node> pred,
        final Predicate<Node> attrPred
        ) {
        final NodeList parents = ancestor.getChildNodes();
        if (end < 0)
            end = (end + parents.getLength() + 1) % (parents.getLength() + 1);

        if (start < 0 || end > parents.getLength() || end <= start)
            return null;

        end--;
        for (; end >= start; end--) {
            final NodeList children = parents.item(end).getChildNodes();
            for (int i = children.getLength() - 1; i > 0; i--) {
                final Node child = children.item(i);
                if (pred.test(i, child) && findAttribute(child, attrPred) != null)
                    return child;
            }
        }

        return null;
    }

    /**
     * Returns the last grand child element within the specified ancestor element and matching the grand child element name, attribute name, and value, or null if the grand child element doesn't exist.
     * <p/>
     * If the grand child element attribute value is null, any value will be accepted.
     *
     * @param ancestor the ancestor element.
     * @param name the grand child element name.
     * @param attrName the grand child element attribute name.
     * @param attrValue the grand child element attribute value.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element is null.
     */
    public static
    Node findLastGrandChild(
        final Node ancestor,
        final String name,
        final String attrName,
        final String attrValue
        ) {
        return findLastGrandChild(ancestor, -1, 0, name, attrName, attrValue);
    }

    /**
     * Returns the last grand child element within the specified ancestor element, starting from the specified parent element index, and matching the grand child element name, attribute name, and value, or null if the grand child element doesn't exist.
     * <p/>
     * This implementation starts looking backwards for grand child elements from the parent element located before the ending index.
     * In other words, the ending index is exclusive.
     * If the ending index is negative, it will be wrapped <b>once</b> around the parents length.
     * <p/>
     * If the grand child element attribute value is null, any value will be accepted.
     *
     * @param ancestor the ancestor element.
     * @param end the ending parent element index. (exclusive)
     * @param name the grand child element name.
     * @param attrName the grand child element attribute name.
     * @param attrValue the grand child element attribute value.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element is null.
     */
    public static
    Node findLastGrandChild(
        final Node ancestor,
        final int end,
        final String name,
        final String attrName,
        final String attrValue
        ) {
        return findLastGrandChild(ancestor, end, 0, name, attrName, attrValue);
    }

    /**
     * Returns the last grand child element within the specified ancestor element, starting from and ending it the specified parent element indexes, and matching the grand child element name, attribute name, and value, or null if the grand child element doesn't exist.
     * <p/>
     * This implementation starts looking backwards for grand child elements from the parent element located before the ending index.
     * In other words, the ending index is exclusive.
     * If the ending index is negative, it will be wrapped <b>once</b> around the parents length.
     * <p/>
     * If the grand child element attribute value is null, any value will be accepted.
     *
     * @param ancestor the ancestor element.
     * @param end the ending parent element index. (exclusive)
     * @param start the starting parent element index. (inclusive)
     * @param name the grand child element name.
     * @param attrName the grand child element attribute name.
     * @param attrValue the grand child element attribute value.
     *
     * @return the grand child element or null if it doesn't exist.
     *
     * @throws NullPointerException if the ancestor element is null.
     */
    public static
    Node findLastGrandChild(
        final Node ancestor,
        final int end,
        final int start,
        final String name,
        final String attrName,
        final String attrValue
        ) {
        return findLastGrandChild(ancestor, end, start,
            (final Integer i, final Node ch) -> ch.getNodeName().equals(name),
            (final Node attr) -> attr.getNodeName().equals(attrName) &&
                                 (attrValue == null || attr.getNodeValue().equals(attrValue)));
    }

    /**
     * Returns the zero-based index of the element within its parent element.
     *
     * @param element the element.
     *
     * @return the element index.
     *
     * @throws NullPointerException if the element is null.
     */
    public static
    int findIndex(
        Node element
        ) {
        if (element == null)
            throw new NullPointerException();

        int order = -1;
        for (; element != null; element = element.getPreviousSibling())
            order++;

        return order;
    }

    /**
     * Returns a new document builder, or null if an error occurs.
     *
     * @return a new document builder, or null if an error occurs.
     *
     * @throws FactoryConfigurationError in case of {@link ServiceConfigurationError service configuration error} or if the implementation is not available or cannot be instantiated.
     *
     * @see DocumentBuilderFactory#newInstance()
     */
    public static
    DocumentBuilder newDocumentBuilder() {
        if (BUILDER_FACTORY == null)
            synchronized (BUILDER_FACTORY) {
                if (BUILDER_FACTORY == null)
                    BUILDER_FACTORY = DocumentBuilderFactory.newInstance();
            }

        try {
            return BUILDER_FACTORY.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            return null;
        }
    }

    /**
     * Returns a new SAX parser, or null if an error occurs.
     *
     * @return a new SAX parser, or null if an error occurs.
     *
     * @throws FactoryConfigurationError in case of {@link ServiceConfigurationError service configuration error} or if the implementation is not available or cannot be instantiated.
     *
     * @see SAXParserFactory#newInstance()
     */
    public static
    SAXParser newParser() {
        if (PARSER_FACTORY == null)
            synchronized (PARSER_FACTORY) {
                if (PARSER_FACTORY == null)
                    PARSER_FACTORY = SAXParserFactory.newInstance();
            }

        try {
            return PARSER_FACTORY.newSAXParser();
        }
        catch (ParserConfigurationException | SAXException e) {
            return null;
        }
    }

    /**
     * Returns a new transformer, or null if an error occurs.
     *
     * @return a new transformer, or null if an error occurs.
     *
     * @throws TransformerFactoryConfigurationError in case of {@link ServiceConfigurationError service configuration error} or if the implementation is not available or cannot be instantiated.
     *
     * @see TransformerFactory#newInstance()
     */
    public static
    Transformer newTransformer() {
        if (TRANSFORMER_FACTORY == null)
            synchronized (TRANSFORMER_FACTORY) {
                if (TRANSFORMER_FACTORY == null)
                    TRANSFORMER_FACTORY = TransformerFactory.newInstance();
            }

        try {
            return TRANSFORMER_FACTORY.newTransformer();
        }
        catch (TransformerConfigurationException e) {
            return null;
        }
    }

    /**
     * Parses the input stream with the specified handler.
     * <p/>
     * If the handler is a {@link Handler} type the document is returned, otherwise null.
     *
     * @param inputStream the input stream.
     * @param handler the handler.
     *
     * @return the document, or null if handler is not a {@code Handler} type.
     *
     * @throws NullPointerException if a parser cannot be instantiated.
     * @throws IllegalArgumentException if the input stream is null.
     * @throws IOException if any I/O errors occur.
     * @throws SAXException if a processing error occurs.
     *
     * @see #newParser()
     * @see SAXParser#parse(InputStream, DefaultHandler)
     */
    public static
    Node parse(
        final InputStream inputStream,
        final DefaultHandler2 handler
        )
    throws
        IOException,
        SAXException {
        newParser().parse(inputStream, handler);
        return handler instanceof Handler
               ? ((Handler<?>) handler).document
               : null;
    }

    /**
     * Parses the input stream using the standard implementation of {@link DocumentHandler}.
     *
     * @param inputStream the input stream.
     *
     * @return the document.
     *
     * @throws NullPointerException if a parser cannot be instantiated.
     * @throws IllegalArgumentException if the input stream is null.
     * @throws IOException if any I/O errors occur.
     * @throws SAXException if a processing error occurs.
     *
     * @see DocumentHandler.Standard
     * @see #parse(InputStream, DefaultHandler2)
     */
    public static
    Node parse(
        final InputStream inputStream
        )
    throws
        IOException,
        SAXException {
        return parse(inputStream, new DocumentHandler.Standard((XMLDocument) Element.of(newDocumentBuilder().newDocument())));
    }

    /**
     * Returns a locator for performing simple searches on the specified element.
     *
     * @param element the locator element.
     *
     * @return the locator.
     *
     * @throws IllegalArgumentException if the element does not belong to the XML document.
     */
    public
    Locator at(
        final Node element
        ) { return null; }

    /**
     * Returns a locator for performing simple searches on the element at the specified index or sequence of nested indexes. (coordinates)
     * <p/>
     * Coordinates must start from the top-most level enumerating the document child elements.
     * In other words, the coordinates for the document element is an empty array.
     *
     * @param coords the element coordinates.
     *
     * @return the locator.
     *
     * @throws IndexOutOfBoundsException if the coordinates are outside of the document boundaries.
     */
    public
    Locator at(
        final Number... coords
        ) { return null; }

    /**
     * Returns an intermediary search type for performing simple searches starting from the specified element.
     *
     * @param element the starting element.
     *
     * @return the search type.
     *
     * @throws IllegalArgumentException if the element does not belong to the XML document.
     */
    public
    Search from(
        final Node element
        ) { return null; }

    /**
     * Returns an intermediary search type for performing simple searches starting from the element at the specified index or sequence of nested indexes. (coordinates)
     * <p/>
     * Coordinates must start from the top-most level enumerating the document child elements.
     * In other words, the coordinates for the document element is an empty array.
     *
     * @param coords the starting element coordinates.
     *
     * @return the search type.
     *
     * @throws IndexOutOfBoundsException if the coordinates are outside of the document boundaries.
     */
    public
    Locator from(
        final Number... coords
        ) { return null; }

    /**
     * Returns an intermediary search type for performing simple searches ending in the specified element.
     *
     * @param element the ending element.
     *
     * @return the search type.
     *
     * @throws IllegalArgumentException if the element does not belong to the XML document.
     */
    public
    Locator until(
        final Node element
        ) { return null; }

    /**
     * Returns an intermediary search type for performing simple searches ending in the element at the specified index or sequence of nested indexes. (coordinates)
     * <p/>
     * Coordinates must start from the top-most level enumerating the document child elements.
     * In other words, the coordinates for the document element is an empty array.
     *
     * @param coords the ending coordinates.
     *
     * @return the search type.
     *
     * @throws IndexOutOfBoundsException if the coordinates are outside of the document boundaries.
     */
    public
    Locator until(
        final Number... coords
        ) { return null; }

    /**
     * Writes the XML document to the specified output stream using the indentation amount.
     *
     * @param outputStream the output stream.
     * @param indent indentation amount.
     *
     * @throws NullPointerException if the output stream is null or a transformer cannot be instantiated due to a serious configuration error.
     * @throws TransformerFactoryConfigurationError in case of {@link ServiceConfigurationError service configuration error} or if the implementation is not available or cannot be instantiated.
     * @throws TransformerException if a transformation error occurs.
     * @throws IllegalStateException if the handler is null or is not a {@link DocumentHandler} type.
     *
     * @see #newTransformer()
     * @see Transformer#transform(Source, Result)
     * @see #getDocument()
     */
    public
    void write(
        final OutputStream outputStream,
        final byte indent
        )
    throws TransformerException {
        // Initialize the transformer factory and create a new transformer
        final Transformer transformer = newTransformer();

        // Set the transformer's properties to use indentation
        final Properties outputFormatProperties = new Properties();
        if (indent > 0) {
            outputFormatProperties.put(OutputKeys.INDENT, "yes");
            outputFormatProperties.put(IndentKey, Byte.toString(indent));
        }
        transformer.setOutputProperties(outputFormatProperties);

        // Transform the document to the output stream
        transformer.transform(new DOMSource(getDocument()), new StreamResult(outputStream));
    }

    /**
     * {@inheritDoc}
     * <p/>
     * This implementation uses a {@code ByteArrayOutputStream} internally.
     *
     * @return the document as character sequence.
     *
     * @throws NullPointerException if a transformer cannot be instantiated due to a serious configuration error.
     * @throws IllegalStateException if a transformation error occurs.
     *
     * @see #write(OutputStream)
     */
    @Override
    public CharSequence getCharSequence() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            write(output);
        }
        catch (TransformerException e) {
            throw new IllegalStateException();
        }

        return new String(output.toByteArray());
    }

    /**
     * {@inheritDoc}
     * <p/>
     * This implementation returns the {@link org.w3c.dom.Element} class.
     *
     * @return the {@code org.w3c.dom.Element} class.
     */
    @Override
    public Class<org.w3c.dom.Element> getOrderClass() {
        return org.w3c.dom.Element.class;
    }

    /**
     * Returns the document object wrapped by this instance.
     *
     * @return the wrapped document object.
     *
     * @throws IllegalStateException if the handler is null or is not a {@link DocumentHandler} type.
     *
     * @see #getDocument()
     */
    @Override
    public org.w3c.dom.Document object() {
        final org.w3c.dom.Document document;
        if (handler instanceof DocumentHandler)
            document = getDocument();
        else
            return null;

        return document instanceof XMLDocument
               ? (org.w3c.dom.Document) ((XMLDocument) document).object()
               : document;
    }

    /**
     * Returns an intermediary search object set at the XML document.
     *
     * @return the intermediary search type.
     *
     * @see #at(Node)
     */
    @Override
    public Locator search() {
        return at(this);
    }

    /**
     * Writes the XML document to the specified output stream.
     *
     * @param outputStream the output stream.
     *
     * @throws NullPointerException if the output stream is null or a transformer cannot be instantiated due to a serious configuration error.
     * @throws TransformerFactoryConfigurationError in case of {@link ServiceConfigurationError service configuration error} or if the implementation is not available or cannot be instantiated.
     * @throws TransformerException if a transformation error occurs.
     *
     * @see #write(OutputStream, byte)
     */
    @Override
    public void write(final OutputStream outputStream) throws TransformerException
    {
        write(outputStream, (byte) 0);
    }

    @Override
    public void unwrap() {}

    @Override
    public XML unwrapped() {
        return this;
    }

    @Override
    public void wrap(final boolean rewrap) {}

    @Override
    public XML wrapped(final boolean rewrap) {
        return this;
    }

    /**
     * Returns the XML document.
     *
     * @return the XML document.
     *
     * @throws IllegalStateException if the handler is null or is not a {@link DocumentHandler} type.
     */
    public
    org.w3c.dom.Document getDocument() {
        try {
            return ((DocumentHandler) handler).document;
        }
        catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    /**
     * Returns the document handler.
     *
     * @return the handler.
     */
    public
    Document.Handler getHandler() {
        return handler;
    }

    /**
     * {@code Coordinates} represents element coordinates in XML documents.
     * <p/>
     * Coordinates must start from the top-most level enumerating the document child elements.
     * In other words, the coordinates for the document element is an empty array.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class Coordinates
    implements
        Comparable<Object>,
        Null.List<Integer>,
        ObjectWrapper
    {
        public static
        Coordinates at(
            final Node element
            ) {
            return null;
        }

        public static
        Coordinates of(
            final int... coords
            ) {
            return null;
        }

        public
        int compareTo(
            final Coordinates coords
            ) {
            return 0;
        }

        public
        int compareTo(
            final Node element
            ) {
            return 0;
        }

        public
        int compareTo(
            final int... coords
            ) {
            return 0;
        }

        public
        boolean equals(
            final Coordinates coords
            ) {
            return false;
        }

        public
        boolean equals(
            final Node element
            ) {
            return false;
        }

        public
        boolean equals(
            final int... coords
            ) {
            return false;
        }

        public
        boolean isAfter(
            final Coordinates coords
            ) {
            return false;
        }

        public
        boolean isAfter(
            final Node element
            ) {
            return false;
        }

        public
        boolean isAfter(
            final int... coords
            ) {
            return false;
        }

        public
        boolean isBefore(
            final Coordinates coords
            ) {
            return false;
        }

        public
        boolean isBefore(
            final Node element
            ) {
            return false;
        }

        public
        boolean isBefore(
            final int... coords
            ) {
            return false;
        }

        public
        boolean isIndide(
            final Coordinates coords
            ) {
            return false;
        }

        public
        boolean isInside(
            final Node element
            ) {
            return false;
        }

        public
        boolean isInside(
            final int... coords
            ) {
            return false;
        }

        public
        boolean isOutside(
            final Coordinates coords
            ) {
            return false;
        }

        public
        boolean isOutside(
            final Node element
            ) {
            return false;
        }

        public
        boolean isOutside(
            final int... coords
            ) {
            return false;
        }

        @Override
        public int compareTo(final Object obj) {
            return obj == null
                   ? Integer.MAX_VALUE
                   : Integer.MIN_VALUE;
        }

        @Override
        public Object object() {
            return null;
        }
    }

    /**
     * {@code DocumentHandler} classifies a handler that is in charge of parsing entire XML documents.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class DocumentHandler
    extends Handler<org.w3c.dom.Document>
    {
        /** The empty input source. */
        protected static final
        InputSource EmptyInputStream = new InputSource(new ByteArrayInputStream(new byte[] {}));

        /**
         * Creates a document handler with the specified document.
         *
         * @param document the document.
         */
        protected
        DocumentHandler(
            final org.w3c.dom.Document document
            ) {
            super(document);
        }

        /**
         * Creates a document handler without any document.
         */
        protected
        DocumentHandler() {
            super();
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation avoids DTD validation.
         *
         * @param name identifies the external entity being resolved. Either "[dtd]" for the external subset, or a name starting with "%" to indicate a parameter entity, or else the name of a general entity. This is never null when invoked by a SAX2 parser.
         * @param publicId the public identifier, or null if none is available.
         * @param systemId the system identifier provided in the XML document.
         *
         * @return the new input source, or null to require the default behavior.
         *
         * @throws SAXException any SAX exception, possibly wrapping another exception.
         * @throws IOException probably indicating a failure to create a new InputStream or Reader, or an illegal URL.
         *
         * @see Handler#resolveEntity(String, String, String, String)
         * @see #getEntityResolver()
         */
        @Override
        public InputSource resolveEntity(final String name, final String publicId, final String baseURI, final String systemId) throws IOException, SAXException {
            return getEntityResolver();
        }

        /**
         * Returns the entity resolver input stream for the handler.
         * <p/>
         * This implementation returns an empty {@link ByteArrayInputStream}.
         *
         * @return the entity resolver input stream.
         */
        protected
        InputSource getEntityResolver() {
            return EmptyInputStream;
        }

        /**
         * Sets the entity resolver input stream for the handler.
         * <p/>
         * This implementation is empty.
         *
         * @param inputStream the input stream.
         */
        public
        void setEntityResolver(
            InputStream inputStream
            ) {}

        /**
         * {@code Basic} is an implementation of a standard document handler that only accepts elements and attributes.
         *
         * @since 1.8
         * @author Alireza Kamran
         *
         * @see Standard
         */
        public static
        class Basic
        extends DocumentHandler
        {
            /** The "document closed" flag. */
            protected
            boolean closed;

            /** The element stack. */
            protected final
            LinkedList<Node> stack = new LinkedList<>();

            /** The document depth. */
            protected
            int depth;

            /**
             * Creates a basic handler with the specified document.
             *
             * @param document the document.
             */
            public
            Basic(
                final org.w3c.dom.Document document
                ) {
                super(document);
            }

            /**
             * Accepts an element text.
             *
             * @param ch the characters.
             * @param start the start position in the character array.
             * @param length the number of characters to use from the character array.
             *
             * @throws IllegalStateException if the document is closed.
             * @throws SAXException if the stack size does not match the document depth.
             */
            @Override
            public void characters(final char[] ch, final int start, final int length) throws SAXException {
                super.characters(ch, start, length);

                if (stack.isEmpty() || stack.size() != depth)
                    throw new SAXException();

                // Append the characters to the element at the top of the stack
                stack.peek().appendChild(document.createTextNode(new String(ch, start, length)));
            }

            /** {@inheritDoc} */
            @Override
            public void close() {
                closed = true;
            }

            /**
             * Ends the document.
             *
             * @throws IllegalStateException if the document is closed.
             *
             * @see Handler#endDocument()
             */
            @Override
            public void endDocument() throws SAXException {
                super.endDocument();
                closed = depth == 0;
            }

            /**
             * Ends an element.
             * <p/>
             * This implementation calls {@link #endDocument()} internally if there is only one element left in the stack.
             *
             * @throws IllegalStateException if the document is closed.
             * @throws SAXException if the stack size does not match the document depth.
             *
             * @see Handler#endElement(String, String, String)
             */
            @Override
            public void endElement(final String uri, final String localName, final String qName) throws SAXException {
                super.endElement(uri, localName, qName);

                // Finalize and close the element in the stack
                if (depth > 0 && stack.size() == depth && qName.equals(((org.w3c.dom.Element) stack.peek()).getTagName()))
                    if (stack.size() == 1) {
                        document.appendChild(stack.pop());
                        depth = 0;
                        endDocument();
                        return;
                    }
                    else
                        stack.peek().appendChild(stack.pop());
                else
                    throw new SAXException();

                depth--;
            }

            /**
             * {@inheritDoc}
             *
             * @return true if the document is closed, and false otherwise.
             */
            @Override
            public boolean isClosed() {
                return closed;
            }

            /**
             * Starts the document.
             * <p/>
             * If the document is null, a new one is created.
             * If the document is not empty, an {@code IllegalStateException} is thrown.
             *
             * @throws IllegalStateException if the document is closed or non-empty.
             *
             * @see Handler#startDocument()
             */
            @Override
            public void startDocument() throws SAXException {
                super.startDocument();

                if (document == null)
                    document = newDocumentBuilder().newDocument();
                else
                    if (document.hasChildNodes()) {
                        closed = true;
                        throw new IllegalStateException();
                    }

                depth = 0;
            }

            /**
             * Starts an element.
             *
             * @throws IllegalStateException if the document is closed.
             *
             * @see Handler#startElement(String, String, String, Attributes)
             */
            @Override
            public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
                super.startElement(uri, localName, qName, attributes);

                // Create a placeholder element
                final org.w3c.dom.Element e = document.createElement(qName);

                // Add the attributes to the element
                for (int i = 0; i < attributes.getLength(); i++)
                    e.setAttribute(attributes.getQName(i), attributes.getValue(i));

                // Push the element to the stack
                stack.push(e);
                depth++;
            }
        }

        /**
         * {@code Standard} is an implementation of a document handler that accepts all standard XML element types.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public static
        class Standard
        extends Basic
        {
            /**
             * Creates a standard handler with the specified document.
             *
             * @param document the document.
             */
            public
            Standard(
                final org.w3c.dom.Document document
                ) {
                super(document);
            }

            /**
             * Accepts an element text.
             *
             * @param ch the characters.
             * @param start the start position in the character array.
             * @param length the number of characters to use from the character array.
             *
             * @throws IllegalStateException if the document is closed.
             * @throws SAXException if the stack size does not match the document depth.
             */
            @Override
            public void characters(final char[] ch, final int start, final int length) throws SAXException {
                super.characters(ch, start, length);

                if (stack.isEmpty() || stack.size() != depth)
                    throw new SAXException();

                // Append the characters to the element at the top of the stack
                final String text = new String(ch, start, length);
                switch (stack.peek().getNodeType())
                {
                case Node.ELEMENT_NODE:
                    stack.peek().appendChild(document.createTextNode(text));
                    break;

                case Node.CDATA_SECTION_NODE:
                    ((CDATASection) stack.peek()).replaceWholeText(text);
                }
            }

            /**
             * {@inheritDoc}
             *
             * @param ch the characters.
             * @param start the starting position in the character array.
             * @param length the number of characters to use from the character array.
             *
             * @throws IllegalStateException if the document is closed.
             * @throws SAXException the application may raise an exception.
             *
             * @see DocumentHandler#characters(char[], int, int)
             */
            @Override
            public void comment(final char[] ch, final int start, final int length) throws SAXException {
                super.comment(ch, start, length);
                stack.push(document.createComment(new String(ch, start, length)));
            }

            /**
             * {@inheritDoc}
             *
             * @throws IllegalStateException if the document is closed.
             * @throws SAXException the application may raise an exception.
             *
             * @see DocumentHandler#endCDATA()
             */
            @Override
            public void endCDATA() throws SAXException {
                super.endCDATA();
                if (stack.peek() == null)
                    document.appendChild(stack.pop());
                else
                    if (stack.peek().getNodeType() == Node.CDATA_SECTION_NODE)
                        stack.get(1).appendChild(stack.pop());
                    else
                        throw new SAXException();

                depth--;
            }

            /**
             * {@inheritDoc}
             *
             * @param target the processing instruction target.
             * @param data The processing instruction data, or null if none is supplied.
             *
             * @throws IllegalStateException if the document is closed.
             * @throws SAXException any SAX exception, possibly wrapping another exception.
             *
             * @see DocumentHandler#processingInstruction(String, String)
             */
            @Override
            public void processingInstruction(final String target, final String data) throws SAXException {
                super.processingInstruction(target, data);
                stack.push(document.createProcessingInstruction(target, data));
            }

            /**
             * {@inheritDoc}
             *
             * @throws IllegalStateException if the document is closed.
             * @throws SAXException any SAX exception, possibly wrapping another exception.
             *
             * @see DocumentHandler#startCDATA()
             */
            @Override
            public void startCDATA() throws SAXException {
                super.startCDATA();
                stack.push(document.createCDATASection(null));
                depth++;
            }
        }
    }

    /**
     * {@code Element} classifies all XML data elements.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Element
    extends
        Format,
        XMLElement
    {
        /**
         * Returns a conventional XML element wrapped around the specified traditional XML element as its target.
         *
         * @param target the traditional XML element.
         *
         * @return the conventional XML element.
         *
         * @throws IllegalArgumentException if the target is not an element or document type.
         */
        static
        XMLElement of(
            final Node target
            ) {
            if (target instanceof XMLDocument) {
                final org.w3c.dom.Document object = (org.w3c.dom.Document) ((XMLDocument) target).object();
                return (XMLDocument) () -> object;
            }

            if (target instanceof org.w3c.dom.Document)
                return (XMLDocument) () -> (org.w3c.dom.Document) target;

            if (target instanceof XMLElement) {
                final org.w3c.dom.Element object = (org.w3c.dom.Element) ((Element) target).object();
                return (Element) () -> object;
            }

            if (target instanceof org.w3c.dom.Element)
                return (Element) () -> (org.w3c.dom.Element) target;

            throw new IllegalArgumentException();
        }

        /**
         * Applies the specified filter to the element and its entire inner structure in breadth-first order and returns the traversal result.
         *
         * @param filter the filter function.
         *
         * @return the traversal result.
         *
         * @throws NullPointerException if the filter is null.
         *
         * @see Traversal#breadthFirst(Node, Function)
         */
        default
        Node breadthFirst(
            final Function<Node, Node> filter
            ) {
            return Traversal.breadthFirst(this, filter);
        }

        /**
         * Applies the specified filter to the element and its entire inner structure in depth-first order and returns the traversal result.
         *
         * @param filter the filter function.
         *
         * @return the traversal result.
         *
         * @throws NullPointerException if the filter is null.
         *
         * @see Traversal#depthFirst(Node, Function)
         */
        default
        Node depthFirst(
            final Function<Node, Node> filter
            ) {
            return Traversal.depthFirst(this, filter);
        }

        /**
         * Returns the attribute matching the specified attribute condition, or null if the attribute doesn't exist.
         *
         * @param pred the attribute condition predicate.
         *
         * @return the attribute or null if it doesn't exist.
         *
         * @throws NullPointerException if the predicate is null.
         *
         * @see Locator#findAttribute(Node, Predicate)
         */
        default
        Node findAttribute(
            final Predicate<Node> pred
            ) {
            return XML.findAttribute(this, pred);
        }

        /**
         * Returns the attribute starting from the specified index and matching the specified attribute condition, or null if the attribute doesn't exist.
         *
         * @param start the starting attribute index. (inclusive)
         * @param pred the attribute condition predicate.
         *
         * @return the attribute or null if it doesn't exist.
         *
         * @throws NullPointerException if the predicate is null.
         *
         * @see Locator#findAttribute(Node, int, Predicate)
         */
        default
        Node findAttribute(
            final int start,
            final Predicate<Node> pred
            ) {
            return XML.findAttribute(this, start, pred);
        }

        /**
         * Returns the attribute starting from and ending in the specified indexes and matching the specified attribute condition, or null if the attribute doesn't exist.
         *
         * @param start the starting attribute index. (inclusive)
         * @param end the ending attribute index. (exclusive)
         * @param pred the attribute condition predicate.
         *
         * @return the attribute or null if it doesn't exist.
         *
         * @throws NullPointerException if the predicate is null.
         *
         * @see Locator#findAttribute(Node, int, int, Predicate)
         */
        default
        Node findAttribute(
            int start,
            int end,
            final Predicate<Node> pred
            ) {
            return XML.findAttribute(this, start, end, pred);
        }

        /**
         * Returns the attribute value with the specified attribute name, or null if the attribute doesn't exist.
         *
         * @param name the attribute name.
         *
         * @return the attribute value or null if it doesn't exist.
         *
         * @see Locator#findAttributeValue(Node, String)
         */
        default
        String findAttributeValue(
            final String name
            ) {
            return XML.findAttributeValue(this, name);
        }

        /**
         * Returns the first child element matching the specified child element condition, or null if the child element doesn't exist.
         *
         * @param pred the child element condition bi-predicate.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @throws NullPointerException if the bi-predicate is null.
         *
         * @see Locator#findChild(Node, BiPredicate)
         */
        default
        Node findChild(
            final BiPredicate<Integer, Node> pred
            ) {
            return XML.findChild(this, pred);
        }

        /**
         * Returns the first child element starting from the specified index and matching the specified child element condition, or null if the child element doesn't exist.
         *
         * @param start the starting child element index. (inclusive)
         * @param pred the child element condition bi-predicate.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @throws NullPointerException if the bi-predicate is null.
         *
         * @see Locator#findChild(Node, int, BiPredicate)
         */
        default
        Node findChild(
                final int start,
                final BiPredicate<Integer, Node> pred
        ) {
            return XML.findChild(this, start, pred);
        }

        /**
         * Returns the first child element starting from and ending in the specified indexes and matching the specified child element condition, or null if the child element doesn't exist.
         *
         * @param start the starting child element index. (inclusive)
         * @param end the ending child element index. (exclusive)
         * @param pred the child element condition bi-predicate.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @throws NullPointerException if the bi-predicate is null.
         *
         * @see Locator#findChild(Node, int, int, BiPredicate)
         */
        default
        Node findChild(
            int start,
            int end,
            final BiPredicate<Integer, Node> pred
            ) {
            return XML.findChild(this, start, end, pred);
        }

        /**
         * Returns the first child element matching the child element name, or null if the child element doesn't exist.
         *
         * @param name the child element name.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @throws NullPointerException if the parent element is null.
         *
         * @see Locator#findChild(Node, String)
         */
        default
        Node findChild(
            final String name
            ) {
            return XML.findChild(this, name);
        }

        /**
         * Returns the first child element starting from the specified index and matching the child element name, or null if the child element doesn't exist.
         *
         * @param start the starting child element index. (inclusive)
         * @param name the child element name.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @see Locator#findChild(Node, int, String)
         */
        default
        Node findChild(
            final int start,
            final String name
            ) {
            return XML.findChild(this, start, name);
        }

        /**
         * Returns the first child element starting from and ending in the specified indexes and matching the child element name, or null if the child element doesn't exist.
         *
         * @param start the starting child element index. (inclusive)
         * @param end the ending child element index. (exclusive)
         * @param name the child element name.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @see Locator#findChild(Node, int, int, String)
         */
        default
        Node findChild(
            final int start,
            final int end,
            final String name
            ) {
            return XML.findChild(this, start, end, name);
        }

        /**
         * Returns the first child element matching the specified element and attribute conditions, or null if the child element doesn't exist.
         *
         * @param pred the child element condition bi-predicate.
         * @param attrPred the child element attribute condition predicate.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @throws NullPointerException if a predicate is null.
         *
         * @see Locator#findChild(Node, BiPredicate, Predicate)
         */
        default
        Node findChild(
            final BiPredicate<Integer, Node> pred,
            final Predicate<Node> attrPred
            ) {
            return XML.findChild(this, pred, attrPred);
        }

        /**
         * Returns the first child element starting from the specified index and matching the specified element and attribute conditions, or null if the child element doesn't exist.
         *
         * @param start the starting child element index. (inclusive)
         * @param pred the child element condition bi-predicate.
         * @param attrPred the child element attribute condition predicate.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @throws NullPointerException if a predicate is null.
         *
         * @see Locator#findChild(Node, int, BiPredicate, Predicate)
         */
        default
        Node findChild(
            int start,
            final BiPredicate<Integer, Node> pred,
            final Predicate<Node> attrPred
            ) {
            return XML.findChild(this, start, pred, attrPred);
        }

        /**
         * Returns the first child element starting from and ending in the specified indexes and matching the specified child element and attribute conditions, or null if the child element doesn't exist.
         *
         * @param start the starting child element index. (inclusive)
         * @param end the ending child element index. (exclusive)
         * @param pred the child element condition bi-predicate.
         * @param attrPred the child element attribute condition predicate.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @throws NullPointerException if a predicate is null.
         *
         * @see Locator#findChild(Node, int, int, BiPredicate, Predicate)
         */
        default
        Node findChild(
            int start,
            int end,
            final BiPredicate<Integer, Node> pred,
            final Predicate<Node> attrPred
            ) {
            return XML.findChild(this, start, end, pred, attrPred);
        }

        /**
         * Returns the first child element matching the child element name, attribute name, and value, or null if the child element doesn't exist.
         *
         * @param name the child element name.
         * @param attrName the child element attribute name.
         * @param attrValue the child element attribute value.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @see Locator#findChild(Node, String, String, String)
         */
        default
        Node findChild(
            final String name,
            final String attrName,
            final String attrValue
            ) {
            return XML.findChild(this, name, attrName, attrValue);
        }

        /**
         * Returns the first child element starting from the specified index and matching the child element name, attribute name, and value, or null if the child element doesn't exist.
         *
         * @param start the starting child element index. (inclusive)
         * @param name the child element name.
         * @param attrName the child element attribute name.
         * @param attrValue the child element attribute value.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @see Locator#findChild(Node, int, String, String, String)
         */
        default
        Node findChild(
            final int start,
            final String name,
            final String attrName,
            final String attrValue
            ) {
            return XML.findChild(this, start, name, attrName, attrValue);
        }

        /**
         * Returns the first child element starting from and ending in the specified indexes and matching the child element name, attribute name, and value, or null if the child element doesn't exist.
         *
         * @param start the starting child element index. (inclusive)
         * @param end the ending child element index. (exclusive)
         * @param name the child element name.
         * @param attrName the child element attribute name.
         * @param attrValue the child element attribute value.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @see Locator#findChild(Node, int, int, String, String, String)
         */
        default
        Node findChild(
            final int start,
            final int end,
            final String name,
            final String attrName,
            final String attrValue
            ) {
            return XML.findChild(this, start, end, name, attrName, attrValue);
        }

        /**
         * Returns an iterable for all child elements.
         *
         * @return the iterable for child elements.
         *
         * @see Locator#findChildren(Node)
         */
        default
        Iterable<Node> findChildren() {
            return XML.findChildren(this);
        }

        /**
         * Returns an iterable for child elements matching the specified child element condition.
         *
         * @param pred the child element condition bi-predicate.
         *
         * @return the iterable for child elements.
         *
         * @throws NullPointerException if the bi-predicate is null.
         *
         * @see Locator#findChildren(Node, BiPredicate)
         */
        default
        Iterable<Node> findChildren(
            final BiPredicate<Integer, Node> pred
            ) {
            return XML.findChildren(this, pred);
        }

        /**
         * Returns an iterable for child elements matching the specified child element and attribute conditions.
         *
         * @param pred the child element condition bi-predicate.
         * @param attrPred the child element attribute condition predicate.
         *
         * @return the iterable for child elements.
         *
         * @throws NullPointerException if a predicate is null.
         *
         * @see Locator#findChildren(Node, BiPredicate, Predicate)
         */
        default
        Iterable<Node> findChildren(
            final BiPredicate<Integer, Node> pred,
            final Predicate<Node> attrPred
            ) {
            return XML.findChildren(this, pred, attrPred);
        }

        /**
         * Returns an iterable for child elements matching the specified child element name.
         *
         * @param name the child element name.
         *
         * @return the iterable for child elements.
         *
         * @see Locator#findChildren(Node, String)
         */
        default
        Iterable<Node> findChildren(
            final String name
            ) {
            return XML.findChildren(this, name);
        }

        /**
         * Returns an iterable for child elements matching the specified child element name, attribute name, and value.
         *
         * @param name the child element name.
         * @param attrName the child element attribute name.
         * @param attrValue the child element attribute value.
         *
         * @return the iterable for child elements.
         *
         * @see Locator#findChildren(Node, String, String, String)
         */
        default
        Iterable<Node> findChildren(
            final String name,
            final String attrName,
            final String attrValue
            ) {
            return XML.findChildren(this, name, attrName, attrValue);
        }

        /**
         * Returns the element coordinates.
         *
         * @return the element coordinates.
         *
         * @see Locator#findCoords(Node)
         */
        default
        int[] findCoords() {
            return XML.findCoords(this);
        }

        /**
         * Returns the element depth.
         *
         * @return the element depth.
         *
         * @see Locator#findDepth(Node)
         */
        default
        int findDepth() {
            return XML.findDepth(this);
        }

        /**
         * Returns the first grand child element matching the specified grand child element condition, or null if the grand child element doesn't exist.
         *
         * @param pred the grand child element condition bi-predicate.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @throws NullPointerException if the bi-predicate is null.
         *
         * @see Locator#findGrandChild(Node, BiPredicate)
         */
        default
        Node findGrandChild(
            final BiPredicate<Integer, Node> pred
            ) {
            return XML.findGrandChild(this, pred);
        }

        /**
         * Returns the first grand child element starting from the specified parent element index and matching the specified grand child element condition, or null if the grand child element doesn't exist.
         *
         * @param start the starting parent element index. (inclusive)
         * @param pred the grand child element condition bi-predicate.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @throws NullPointerException if the bi-predicate is null.
         *
         * @see Locator#findGrandChild(Node, int, BiPredicate)
         */
        default
        Node findGrandChild(
            final int start,
            final BiPredicate<Integer, Node> pred
            ) {
            return XML.findGrandChild(this, start, pred);
        }

        /**
         * Returns the first grand child element starting from and ending in the specified parent element indexes, and matching the specified grand child element condition, or null if the grand child element doesn't exist.
         *
         * @param start the starting parent element index. (inclusive)
         * @param end the ending parent element index. (exclusive)
         * @param pred the grand child element condition bi-predicate.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @throws NullPointerException if the bi-predicate is null.
         *
         * @see Locator#findGrandChild(Node, int, int, BiPredicate)
         */
        default
        Node findGrandChild(
            int start,
            int end,
            final BiPredicate<Integer, Node> pred
            ) {
            return XML.findGrandChild(this, start, end, pred);
        }

        /**
         * Returns the first grand child element matching the grand child element name, or null if the grand child element doesn't exist.
         *
         * @param name the grand child element name.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @see Locator#findGrandChild(Node, String)
         */
        default
        Node findGrandChild(
            final String name
            ) {
            return XML.findGrandChild(this, name);
        }

        /**
         * Returns the first grand child element starting from the specified parent element index and matching the grand child element name, or null if the grand child element doesn't exist.
         *
         * @param start the starting parent element index. (inclusive)
         * @param name the grand child element name.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @see Locator#findGrandChild(Node, int, String)
         */
        default
        Node findGrandChild(
            final int start,
            final String name
            ) {
            return XML.findGrandChild(this, start, name);
        }

        /**
         * Returns the first grand child element starting from and ending in the specified parent element indexes and matching the grand child element name, or null if the grand child element doesn't exist.
         *
         * @param start the starting parent element index. (inclusive)
         * @param end the ending parent element index. (exclusive)
         * @param name the grand child element name.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @see Locator#findGrandChild(Node, int, int, String)
         */
        default
        Node findGrandChild(
            final int start,
            final int end,
            final String name
            ) {
            return XML.findGrandChild(this, start, end, name);
        }

        /**
         * Returns the first grand child element starting from and ending in the specified parent element indexes and matching the specified grand child element and attribute conditions, or null if the grand child element doesn't exist.
         *
         * @param start the starting parent element index. (inclusive)
         * @param end the ending parent element index. (exclusive)
         * @param pred the grand child element condition bi-predicate.
         * @param attrPred the grand child element attribute condition predicate.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @throws NullPointerException if a predicate is null.
         *
         * @see Locator#findGrandChild(Node, int, int, BiPredicate, Predicate)
         */
        default
        Node findGrandChild(
            int start,
            int end,
            final BiPredicate<Integer, Node> pred,
            final Predicate<Node> attrPred
            ) {
            return XML.findGrandChild(this, start, end, pred, attrPred);
        }

        /**
         * Returns the first grand child element matching the grand child element name, attribute name, and value, or null if the grand child element doesn't exist.
         *
         * @param name the grand child element name.
         * @param attrName the grand child element attribute name.
         * @param attrValue the grand child element attribute value.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @see Locator#findGrandChild(Node, String, String, String)
         */
        default
        Node findGrandChild(
            final String name,
            final String attrName,
            final String attrValue
            ) {
            return XML.findGrandChild(this, name, attrName, attrValue);
        }

        /**
         * Returns the first grand child element starting from the specified parent index and matching the grand child element name, attribute name, and value, or null if the grand child element doesn't exist.
         *
         * @param start the starting parent element index. (inclusive)
         * @param name the grand child element name.
         * @param attrName the grand child element attribute name.
         * @param attrValue the grand child element attribute value.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @see Locator#findGrandChild(Node, int, String, String, String)
         */
        default
        Node findGrandChild(
            final int start,
            final String name,
            final String attrName,
            final String attrValue
            ) {
            return XML.findGrandChild(this, start, name, attrName, attrValue);
        }

        /**
         * Returns the first grand child element starting from and ending in the specified parent element indexes and matching the grand child element name, attribute name, and value, or null if the grand child element doesn't exist.
         *
         * @param start the starting parent element index. (inclusive)
         * @param end the ending parent element index. (exclusive)
         * @param name the grand child element name.
         * @param attrName the grand child element attribute name.
         * @param attrValue the grand child element attribute value.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @see Locator#findGrandChild(Node, int, int, String, String, String)
         */
        default
        Node findGrandChild(
            final int start,
            final int end,
            final String name,
            final String attrName,
            final String attrValue
            ) {
            return XML.findGrandChild(this, start, end, name, attrName, attrValue);
        }

        /**
         * Returns an iterable for all grand child elements.
         *
         * @return the iterable for grand child elements.
         *
         * @see Locator#findGrandChildren(Node)
         */
        default
        Iterable<Node> findGrandChildren() {
            return XML.findGrandChildren(this);
        }

        /**
         * Returns an iterable for grand child elements matching the specified grand child element condition.
         *
         * @param pred the grand child element condition bi-predicate.
         *
         * @return the iterable for grand child elements.
         *
         * @throws NullPointerException if the bi-predicate is null.
         *
         * @see Locator#findGrandChildren(Node, BiPredicate)
         */
        default
        Iterable<Node> findGrandChildren(
            final BiPredicate<Integer, Node> pred
            ) {
            return XML.findGrandChildren(this, pred);
        }

        /**
         * Returns an iterable for grand child elements matching the specified grand child element condition.
         *
         * @param pred the grand child element condition bi-predicate.
         * @param attrPred the grand child element attribute condition predicate.
         *
         * @return the iterable for grand child elements.
         *
         * @throws NullPointerException if a predicate is null.
         *
         * @see Locator#findGrandChildren(Node, BiPredicate, Predicate)
         */
        default
        Iterable<Node> findGrandChildren(
            final BiPredicate<Integer, Node> pred,
            final Predicate<Node> attrPred
            ) {
            return XML.findGrandChildren(this, pred, attrPred);
        }

        /**
         * Returns an iterable for grand child elements matching the specified grand child element name.
         *
         * @param name the grand child element name.
         *
         * @return the iterable for grand child elements.
         *
         * @see Locator#findGrandChildren(Node, String)
         */
        default
        Iterable<Node> findGrandChildren(
            final String name
            ) {
            return XML.findGrandChildren(this, name);
        }

        /**
         * Returns an iterable for grand child elements matching the specified grand child element name, attribute name, and value.
         *
         * @param name the grand child element name.
         * @param attrName the grand child element attribute name.
         * @param attrValue the grand child element attribute value.
         *
         * @return the iterable for grand child elements.
         *
         * @see Locator#findGrandChildren(Node, String, String, String)
         */
        default
        Iterable<Node> findGrandChildren(
            final String name,
            final String attrName,
            final String attrValue
            ) {
            return XML.findGrandChildren(this, name, attrName, attrValue);
        }

        /**
         * Returns the last child element matching the specified child element condition, or null if the child element doesn't exist.
         *
         * @param pred the child element condition bi-predicate.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @throws NullPointerException if the bi-predicate is null.
         *
         * @see Locator#findLastChild(Node, BiPredicate)
         */
        default
        Node findLastChild(
            final BiPredicate<Integer, Node> pred
            ) {
            return XML.findLastChild(this, pred);
        }

        /**
         * Returns the last child element starting from the specified index and matching the specified child element condition, or null if the child element doesn't exist.
         *
         * @param end the ending child element index. (exclusive)
         * @param pred the child element condition bi-predicate.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @throws NullPointerException if the bi-predicate is null.
         *
         * @see Locator#findLastChild(Node, int, BiPredicate)
         */
        default
        Node findLastChild(
            final int end,
            final BiPredicate<Integer, Node> pred
            ) {
            return XML.findLastChild(this, end, pred);
        }

        /**
         * Returns the last child element starting from and ending in the specified indexes and matching the specified child element condition, or null if the child element doesn't exist.
         *
         * @param end the ending child element index. (exclusive)
         * @param start the starting child element index. (inclusive)
         * @param pred the child element condition bi-predicate.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @throws NullPointerException if the bi-predicate is null.
         *
         * @see Locator#findLastChild(Node, int, int, BiPredicate)
         */
        default
        Node findLastChild(
            final Node parent,
            int end,
            int start,
            final BiPredicate<Integer, Node> pred
            ) {
            return XML.findLastChild(this, end, start, pred);
        }

        /**
         * Returns the last child element matching the child element name, or null if the child element doesn't exist.
         *
         * @param name the child element name.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @see Locator#findLastChild(Node, String)
         */
        default
        Node findLastChild(
            final String name
            ) {
            return XML.findLastChild(this, name);
        }

        /**
         * Returns the last child element starting from the specified index and matching the child element name, or null if the child element doesn't exist.
         *
         * @param end the ending child element index. (exclusive)
         * @param name the child element name.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @see Locator#findLastChild(Node, int, String)
         */
        default
        Node findLastChild(
            final int end,
            final String name
            ) {
            return XML.findLastChild(this, end, name);
        }

        /**
         * Returns the last child element starting from and ending in the specified indexes and matching the child element name, or null if the child element doesn't exist.
         *
         * @param end the ending child element index. (exclusive)
         * @param start the starting child element index. (inclusive)
         * @param name the child element name.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @see Locator#findLastChild(Node, int, int, String)
         */
        default
        Node findLastChild(
            final int end,
            final int start,
            final String name
            ) {
            return XML.findLastChild(this, end, start, name);
        }

        /**
         * Returns the last child element matching the specified child element and attribute conditions, or null if the child element doesn't exist.
         *
         * @param pred the child element condition bi-predicate.
         * @param attrPred the child element attribute condition predicate.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @throws NullPointerException if a predicate is null.
         *
         * @see Locator#findLastChild(Node, BiPredicate, Predicate)
         */
        default
        Node findLastChild(
            final BiPredicate<Integer, Node> pred,
            final Predicate<Node> attrPred
            ) {
            return XML.findLastChild(this, pred, attrPred);
        }

        /**
         * Returns the last child element starting from the specified index and matching the specified child element and attribute conditions, or null if the child element doesn't exist.
         *
         * @param end the ending child element index. (exclusive)
         * @param pred the child element condition bi-predicate.
         * @param attrPred the child element attribute condition predicate.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @throws NullPointerException if a predicate is null.
         *
         * @see Locator#findLastChild(Node, int, BiPredicate, Predicate)
         */
        default
        Node findLastChild(
            int end,
            final BiPredicate<Integer, Node> pred,
            final Predicate<Node> attrPred
            ) {
            return XML.findLastChild(this, end, pred, attrPred);
        }

        /**
         * Returns the last child element starting from and ending in the specified indexes and matching the specified child element and attribute conditions, or null if the child element doesn't exist.
         *
         * @param end the ending child element index. (exclusive)
         * @param start the starting child element index. (inclusive)
         * @param pred the child element condition bi-predicate.
         * @param attrPred the child element attribute condition predicate.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @throws NullPointerException if a predicate is null.
         *
         * @see Locator#findLastChild(Node, int, int, BiPredicate, Predicate)
         */
        default
        Node findLastChild(
            int end,
            int start,
            final BiPredicate<Integer, Node> pred,
            final Predicate<Node> attrPred
            ) {
            return XML.findLastChild(this, end, start, pred, attrPred);
        }

        /**
         * Returns the last child element matching the child element name, attribute name, and value, or null if the child element doesn't exist.
         *
         * @param name the child element name.
         * @param attrName the child element attribute name.
         * @param attrValue the child element attribute value.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @see Locator#findLastChild(Node, String, String, String)
         */
        default
        Node findLastChild(
            final String name,
            final String attrName,
            final String attrValue
            ) {
            return XML.findLastChild(this, name, attrName, attrValue);
        }

        /**
         * Returns the last child element starting from the specified index and matching the child element name, attribute name, and value, or null if the child element doesn't exist.
         *
         * @param end the ending child element index. (exclusive)
         * @param name the child element name.
         * @param attrName the child element attribute name.
         * @param attrValue the child element attribute value.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @see Locator#findLastChild(Node, int, String, String, String)
         */
        default
        Node findLastChild(
            final int end,
            final String name,
            final String attrName,
            final String attrValue
            ) {
            return XML.findLastChild(this, end, name, attrName, attrValue);
        }

        /**
         * Returns the last child element starting from and ending in the specified indexes and matching the child element name, attribute name, and value, or null if the child element doesn't exist.
         *
         * @param end the ending child element index. (exclusive)
         * @param start the starting child element index. (inclusive)
         * @param name the child element name.
         * @param attrName the child element attribute name.
         * @param attrValue the child element attribute value.
         *
         * @return the child element or null if it doesn't exist.
         *
         * @see Locator#findLastChild(Node, int, int, String, String, String)
         */
        default
        Node findLastChild(
            final int end,
            final int start,
            final String name,
            final String attrName,
            final String attrValue
            ) {
            return XML.findLastChild(this, end, start, name, attrName, attrValue);
        }

        /**
         * Returns the last grand child element matching the specified grand child element condition, or null if the grand child element doesn't exist.
         *
         * @param pred the grand child element condition bi-predicate.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @throws NullPointerException if the bi-predicate is null.
         *
         * @see Locator#findLastGrandChild(Node, BiPredicate)
         */
        default
        Node findLastGrandChild(
            final BiPredicate<Integer, Node> pred
            ) {
            return XML.findLastGrandChild(this, pred);
        }

        /**
         * Returns the last grand child element starting from the specified parent element index and matching the specified grand child element condition, or null if the grand child element doesn't exist.
         *
         * @param end the ending parent element index. (exclusive)
         * @param pred the grand child element condition bi-predicate.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @throws NullPointerException if the bi-predicate is null.
         *
         * @see Locator#findLastGrandChild(Node, int, BiPredicate)
         */
        default
        Node findLastGrandChild(
            final int end,
            final BiPredicate<Integer, Node> pred
            ) {
            return XML.findLastGrandChild(this, end, pred);
        }

        /**
         * Returns the last grand child element starting from and ending in the specified parent element indexes and matching the specified grand child element condition, or null if the grand child element doesn't exist.
         *
         * @param end the ending parent element index. (exclusive)
         * @param start the starting parent element index. (inclusive)
         * @param pred the grand child element condition bi-predicate.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @throws NullPointerException if the bi-predicate is null.
         *
         * @see Locator#findLastGrandChild(Node, int, int, BiPredicate)
         */
        default
        Node findLastGrandChild(
            int start,
            int end,
            final BiPredicate<Integer, Node> pred
            ) {
            return XML.findLastGrandChild(this, end, start, pred);
        }

        /**
         * Returns the last grand child element matching the grand child element name, or null if the grand child element doesn't exist.
         *
         * @param name the grand child element name.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @see Locator#findLastGrandChild(Node, String)
         */
        default
        Node findLastGrandChild(
            final String name
            ) {
            return XML.findLastGrandChild(this, name);
        }

        /**
         * Returns the last grand child element starting from the specified parent element index and matching the grand child element name, or null if the grand child element doesn't exist.
         *
         * @param end the ending parent element index. (exclusive)
         * @param name the grand child element name.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @see Locator#findLastGrandChild(Node, int, String)
         */
        default
        Node findLastGrandChild(
            final int end,
            final String name
            ) {
            return XML.findLastGrandChild(this, end, name);
        }

        /**
         * Returns the last grand child element starting from and ending in the specified parent element indexes and matching the grand child element name, or null if the grand child element doesn't exist.
         *
         * @param end the ending parent element index. (exclusive)
         * @param start the starting parent element index. (inclusive)
         * @param name the grand child element name.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @see Locator#findLastGrandChild(Node, int, int, String)
         */
        default
        Node findLastGrandChild(
            final int end,
            final int start,
            final String name
            ) {
            return XML.findLastGrandChild(this, end, start, name);
        }

        /**
         * Returns the last grand child element matching the specified element and attribute conditions, or null if the grand child element doesn't exist.
         *
         * @param pred the grand child element condition bi-predicate.
         * @param attrPred the grand child attribute condition predicate.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @throws NullPointerException if a predicate is null.
         *
         * @see Locator#findLastGrandChild(Node, BiPredicate, Predicate)
         */
        default
        Node findLastGrandChild(
            final BiPredicate<Integer, Node> pred,
            final Predicate<Node> attrPred
            ) {
            return XML.findLastGrandChild(this, pred, attrPred);
        }

        /**
         * Returns the last grand child element starting from the specified parent element index and matching the specified grand child element and attribute conditions, or null if the grand child element doesn't exist.
         *
         * @param end the ending parent element index. (exclusive)
         * @param pred the grand child element condition bi-predicate.
         * @param attrPred the grand child element attribute condition predicate.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @throws NullPointerException if a predicate is null.
         *
         * @see Locator#findLastGrandChild(Node, int, BiPredicate, Predicate)
         */
        default
        Node findLastGrandChild(
            int end,
            final BiPredicate<Integer, Node> pred,
            final Predicate<Node> attrPred
            ) {
            return XML.findLastGrandChild(this, end, pred, attrPred);
        }

        /**
         * Returns the last grand child element starting from and ending in the specified parent element indexes and matching the specified grand child element and attribute conditions, or null if the grand child element doesn't exist.
         *
         * @param end the ending parent element index. (exclusive)
         * @param start the starting parent element index. (inclusive)
         * @param pred the grand child element condition bi-predicate.
         * @param attrPred the grand child element attribute condition predicate.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @throws NullPointerException if a predicate is null.
         *
         * @see Locator#findLastGrandChild(Node, int, int, BiPredicate, Predicate)
         */
        default
        Node findLastGrandChild(
            int end,
            int start,
            final BiPredicate<Integer, Node> pred,
            final Predicate<Node> attrPred
            ) {
            return XML.findLastGrandChild(this, end, start, pred, attrPred);
        }

        /**
         * Returns the last grand child element matching the grand child element name, attribute name, and value, or null if the grand child element doesn't exist.
         *
         * @param name the grand child element name.
         * @param attrName the grand child element attribute name.
         * @param attrValue the grand child element attribute value.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @see Locator#findLastGrandChild(Node, String, String, String)
         */
        default
        Node findLastGrandChild(
            final String name,
            final String attrName,
            final String attrValue
            ) {
            return XML.findLastGrandChild(this, name, attrName, attrValue);
        }

        /**
         * Returns the last grand child element starting from the specified parent element index and matching the grand child element name, attribute name, and value, or null if the grand child element doesn't exist.
         *
         * @param end the ending parent element index. (exclusive)
         * @param name the grand child element name.
         * @param attrName the grand child element attribute name.
         * @param attrValue the grand child element attribute value.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @see Locator#findLastGrandChild(Node, int, String, String, String)
         */
        default
        Node findLastGrandChild(
            final int end,
            final String name,
            final String attrName,
            final String attrValue
            ) {
            return XML.findLastGrandChild(this, end, name, attrName, attrValue);
        }

        /**
         * Returns the last grand child element starting from and ending in the specified parent element indexes and matching the grand child element name, attribute name, and value, or null if the grand child element doesn't exist.
         *
         * @param end the ending parent element index. (exclusive)
         * @param start the starting parent element index. (inclusive)
         * @param name the grand child element name.
         * @param attrName the grand child element attribute name.
         * @param attrValue the grand child element attribute value.
         *
         * @return the grand child element or null if it doesn't exist.
         *
         * @see Locator#findLastGrandChild(Node, int, int, String, String, String)
         */
        default
        Node findLastGrandChild(
            final int start,
            final int end,
            final String name,
            final String attrName,
            final String attrValue
            ) {
            return XML.findLastGrandChild(this, end, start, name, attrName, attrValue);
        }

        /**
         * Returns the zero-based index of the element within its parent element.
         *
         * @return the element index.
         *
         * @see Locator#findIndex(Node)
         */
        default
        int findIndex() {
            return XML.findIndex(this);
        }

        /**
         * Applies the specified filter to the element and its entire inner structure in reversed breadth-first order and returns the traversal result.
         *
         * @param filter the filter function.
         *
         * @return the traversal result.
         *
         * @throws NullPointerException if the filter is null.
         *
         * @see Traversal#reverseBreadthFirst(Node, Function)
         */
        default
        Node reverseBreadthFirst(
            final Function<Node, Node> filter
            ) {
            return Traversal.reverseBreadthFirst(this, filter);
        }

        /**
         * Applies the specified filter to the element and its entire inner structure in reversed depth-first order and returns the traversal result.
         *
         * @param filter the filter function.
         *
         * @return the traversal result.
         *
         * @throws NullPointerException if the filter is null.
         *
         * @see Traversal#reverseDepthFirst(Node, Function)
         */
        default
        Node reverseDepthFirst(
            final Function<Node, Node> filter
            ) {
            return Traversal.reverseDepthFirst(this, filter);
        }

        /**
         * Returns an intermediary search object set at the element.
         *
         * @return the intermediary search type.
         *
         * @see #at(Node)
         */
        @Override
        default Locator search() {
            return new Locator(){}.at(this);
        }

        /**
         * {@code Attribute} classifies all XML data element attributes.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Attribute
        extends XMLAttribute
        {
            /**
             * Returns a conventional XML element attribute wrapped around the specified traditional XML element attribute as its target.
             *
             * @param target the traditional XML element attribute.
             *
             * @return the conventional XML element attribute.
             */
            static
            Attribute of(
                final Attr target
                ) {
                if (target instanceof Attribute) {
                    final Attr object = (Attr) ((Attribute) target).object();
                    return () -> object;
                }

                return () -> target;
            }

            /**
             * {@code Schematic} classifies XML element attributes containing schema information.
             *
             * @see XML.Schematic
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Schematic
            extends XML.Schematic
            {
                /**
                 * Returns true if the attribute is required in the schema; otherwise returns false.
                 * <p/>
                 * This implementation return false.
                 *
                 * @return true if the attribute is required, and false otherwise.
                 */
                @Override
                default boolean isRequired() {
                    return false;
                }
            }
        }

        /**
         * {@code Path} represents a point on a path of arbitrarily selected XML elements.
         * <p/>
         * By convention, the number of each point must be the index of the element it points to within its parent element.
         * However, paths can choose to provide a different number for the points on them.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        abstract
        class Path
        extends Number
        implements
            Ordered,
            Supplier<org.w3c.dom.Element>,
            java.lang.reflect.Type
        {
            /**
             * Returns the order of the path.
             *
             * @return the order.
             */
            public abstract
            int getOrder();

            /**
             * Returns the next point on the path.
             *
             * @return the next path.
             */
            public abstract
            Path next();

            /**
             * Returns the previous point on the path.
             *
             * @return the previous path.
             */
            public abstract
            Path prev();

            /**
             * {@inheritDoc}
             * <p/>
             * This implementation returns the {@code Integer} class.
             *
             * @return the {@code Integer} class.
             */
            @Override
            public Class<Integer> getOrderClass() {
                return Integer.class;
            }

            /**
             * {@inheritDoc}
             * <p/>
             * This implementation returns {@link #getOrder()}.
             *
             * @return the numeric value represented by this path point after conversion to type {@code double}.
             */
            @Override
            public double doubleValue() {
                return getOrder();
            }

            /**
             * {@inheritDoc}
             * <p/>
             * This implementation returns {@link #getOrder()}.
             *
             * @return the numeric value represented by this path point after conversion to type {@code float}.
             */
            @Override
            public float floatValue() {
                return getOrder();
            }

            /**
             * {@inheritDoc}
             * <p/>
             * This implementation returns {@link #getOrder()}.
             *
             * @return the numeric value represented by this path point after conversion to type {@code int}.
             */
            @Override
            public int intValue() {
                return getOrder();
            }

            /**
             * {@inheritDoc}
             * <p/>
             * This implementation returns {@link #getOrder()}.
             *
             * @return the numeric value represented by this path point after conversion to type {@code long}.
             */
            @Override
            public long longValue() {
                return getOrder();
            }
        }

        /**
         * {@code Queue} is an implementation of an {@code ArrayList} of XML elements which is also a queue and a wrapped XML element type representing the first element in the list/queue.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        class Queue
        extends ArrayList<Node>
        implements
            java.util.Queue<Node>,
            XMLElement
        {
            /**
             * Creates a queue containing the specified elements.
             *
             * @param nodes the elements.
             */
            public
            Queue(
                final Node... nodes
                ) {
                this();
                for (final Node node : nodes)
                    add(node);
            }

            /**
             * Creates a queue containing the specified element.
             *
             * @param node the element.
             */
            public
            Queue(
                final Node node
                ) {
                this();
                add(node);
            }

            /**
             * Creates an empty queue.
             */
            public
            Queue() {
                super();
            }

            /**
             * Validates the queue is not empty; otherwise throws a {@code NoSuchElementException}.
             *
             * @throws NoSuchElementException if the queue is empty.
             */
            private
            void validateNonEmpty() {
                if (isEmpty())
                    throw new NoSuchElementException();
            }

            /**
             * {@inheritDoc}
             *
             * @param node the element.
             *
             * @return true if the element was added to the queue, and false otherwise.
             */
            @Override
            public boolean offer(final Node node) {
                return add(node);
            }

            /**
             * {@inheritDoc}
             *
             * @return the head of the queue.
             *
             * @throws NoSuchElementException if the queue is empty.
             */
            @Override
            public Node remove() {
                validateNonEmpty();
                return remove(0);
            }

            /**
             * {@inheritDoc}
             *
             * @return the head of the queue, or null if the queue is empty.
             */
            @Override
            public Node poll() {
                return remove(0);
            }

            /**
             * {@inheritDoc}
             *
             * @return the head of the queue.
             *
             * @throws NoSuchElementException if the queue is empty.
             */
            @Override
            public Node element() {
                validateNonEmpty();
                return get(0);
            }

            /**
             * {@inheritDoc}
             * <p/>
             * This implementation returns the head of the queue or null if the queue is empty.
             *
             * @return the head of the queue.
             */
            @Override
            public Node object() {
                final Node peek = peek();
                return peek instanceof XMLElement
                       ? (Node) ((XMLElement) peek).object()
                       : peek();
            }

            /**
             * {@inheritDoc}
             *
             * @return the head of the queue, or null if the queue is empty.
             */
            @Override
            public Node peek() {
                return get(0);
            }
        }

        /**
         * {@code Schematic} classifies XML elements containing schema information.
         * <p/>
         * By design, each schematic element type defines an optional array of supported elements as class types and an occurrence character indicator, specified by the {@code occurrence()} method, that defines the element's allowed number of occurrences within its containing element.
         * The accepted values for the occurrence character indicator are: null, *, ?, and +.
         * The null value indicates that the element is uniquely required.
         * <p/>
         * Each schematic element type also defines a sorted array of supported text values (ascending) and attribute values when available.
         * In such cases, the {@code defaultValue()} method returns the default text value.
         * If there is no default value declared for the element type, this method can return null.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Schematic
        extends XML.Schematic
        {
            /**
             * Returns the schematic element's occurrence indicator string defined in the schema file.
             *
             * @return the occurrence string.
             */
            String occurrence();

            /**
             * Returns true if the element is required in the schema; otherwise returns false.
             *
             * @return true if the element is required, and false otherwise.
             */
            @Override
            default boolean isRequired() {
                return occurrence().isEmpty();
            }
        }
    }

    /**
     * {@code Filter} represents data types that are constructed in order to filter out parts of XML documents during traversal.
     * <p/>
     * This class implementation is empty.
     *
     * @see Document.Filter
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class Filter
    extends Document.Filter
    {
        /**
         * {@code Match} represents all data types that make up the conditional expressions for matching elements within XML documents.
         * <p/>
         * Technically speaking, a match condition can be as arbitrarily complex as needed.
         * Any form of programmatic logic can be laid out by match types given that the traversal logic provides enough data to the containing filter type at runtime, such as element index, depth, name, etc.
         * The predefined subclasses inside this class represent only a handful of match conditions that are commonly employed in most forms of XML traversals.
         * <p/>
         * This class implementation is empty.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public static abstract
        class Match
        extends Document.Filter.Match
        {
            /**
             * {@code Ancestor} represents all match types that target XML element ancestor conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Ancestor
            extends Match
            {}

            /**
             * {@code Attribute} represents all match types that target XML element attribute conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Attribute
            extends Match
            {}

            /**
             * {@code AttributeName} represents all match types that target XML element attribute name conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class AttributeName
            extends Attribute
            {}

            /**
             * {@code AttributeIndex} represents all match types that target XML element attribute index conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class AttributeIndex
            extends Attribute
            {}

            /**
             * {@code AttributeValue} represents all match types that target XML element attribute value conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class AttributeValue
            extends Attribute
            {}

            /**
             * {@code Child} represents all match types that target XML element child conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Child
            extends Descendant
            {}

            /**
             * {@code Descendant} represents all match types that target XML element descendant conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Descendant
            extends Match
            {}

            /**
             * {@code Depth} represents all match types that target XML element depth conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Depth
            extends Element
            {}

            /**
             * {@code Element} represents all match types that target XML element conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Element
            extends Match
            {}

            /**
             * {@code Index} represents all match types that target XML element index conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Index
            extends Element
            {}

            /**
             * {@code Name} represents all match types that target XML element name conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Name
            extends Element
            {}

            /**
             * {@code Parent} represents all match types that target XML element parent conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Parent
            extends Ancestor
            {}

            /**
             * {@code Sibling} represents all match types that target XML element sibling conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Sibling
            extends Match
            {}

            /**
             * {@code Text} represents all match types that target XML element text conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Text
            extends Element
            {}
        }
    }

    /**
     * {@code Handler} re-implements the {@link DefaultHandler2} class.
     * <p/>
     * By design, the generic type of this class defines the handler's result or target element type.
     *
     * @param <T> the document type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class Handler<T extends Node>
    extends DefaultHandler2
    implements Document.Handler
    {
        /** the document. (node) */
        protected
        T document;

        /**
         * Creates a handler with the specified document.
         *
         * @param document the document.
         */
        protected
        Handler(
            final T document
            ) {
            this();
            this.document = document;
        }

        /**
         * Creates a handler without any document.
         */
        protected
        Handler() {
            super();
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @param eName the name of the associated element.
         * @param aName the name of the attribute.
         * @param type a string representing the attribute type.
         * @param mode a string representing the attribute defaulting mode ("#IMPLIED", "#REQUIRED", or "#FIXED") or null if none of these applies.
         * @param value a string representing the attribute's default value, or null if there is none.
         *
         * @throws IllegalStateException if the document is closed.
         * @throws SAXException the application may raise an exception.
         *
         * @see DefaultHandler2#attributeDecl(String, String, String, String, String)
         */
        @Override
        public void attributeDecl(final String eName, final String aName, final String type, final String mode, final String value) throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.attributeDecl(eName, aName, type, mode, value);
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @param ch the characters.
         * @param start the starting position in the character array.
         * @param length the number of characters to use from the character array.
         *
         * @throws IllegalStateException if the document is closed.
         * @throws SAXException any SAX exception, possibly wrapping another exception.
         *
         * @see DefaultHandler2#characters(char[], int, int)
         */
        @Override
        public void characters(final char[] ch, final int start, final int length) throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.characters(ch, start,length);
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @param ch the characters.
         * @param start the starting position in the character array.
         * @param length the number of characters to use from the character array.
         *
         * @throws IllegalStateException if the document is closed.
         * @throws SAXException the application may raise an exception.
         *
         * @see DefaultHandler2#characters(char[], int, int)
         */
        @Override
        public void comment(final char[] ch, final int start, final int length) throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.comment(ch, start, length);
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @param name the element type name.
         * @param model the content model as a normalized string.
         *
         * @throws IllegalStateException if the document is closed.
         * @throws SAXException the application may raise an exception.
         *
         * @see DefaultHandler2#elementDecl(String, String)
         */
        @Override
        public void elementDecl(final String name, final String model) throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.elementDecl(name, model);
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @throws IllegalStateException if the document is closed.
         * @throws SAXException the application may raise an exception.
         *
         * @see DefaultHandler2#endCDATA()
         */
        @Override
        public void endCDATA() throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.endCDATA();
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @throws IllegalStateException if the document is closed.
         * @throws SAXException the application may raise an exception.
         *
         * @see DefaultHandler2#endDTD()
         */
        @Override
        public void endDTD() throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.endDTD();
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @param uri the namespace URI.
         * @param localName the local name (without prefix), or the empty string if Namespace processing is not being performed.
         * @param qName the qualified name (with prefix), or the empty string if qualified names are not available.
         *
         * @throws IllegalStateException if the document is closed.
         * @throws SAXException any SAX exception, possibly wrapping another exception.
         *
         * @see DefaultHandler2#endElement(String, String, String)
         */
        @Override
        public void endElement(final String uri, final String localName, final String qName) throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.endElement(uri, localName, qName);
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @param name the name of the entity that is ending.
         *
         * @throws IllegalStateException if the document is closed.
         * @throws SAXException the application may raise an exception.
         *
         * @see DefaultHandler2#endEntity(String)
         */
        @Override
        public void endEntity(final String name) throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.endEntity(name);
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @param name the name of the entity. If it is a parameter entity, the name will begin with '%'.
         * @param publicId the entity's public identifier, or null if none was given.
         * @param systemId the entity's system identifier.
         *
         * @throws IllegalStateException if the document is closed.
         * @throws SAXException the application may raise an exception.
         *
         * @see DefaultHandler2#externalEntityDecl(String, String, String)
         */
        @Override
        public void externalEntityDecl(final String name, final String publicId, final String systemId) throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.externalEntityDecl(name, publicId, systemId);
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @param name the name of the entity. If it is a parameter entity, the name will begin with '%'
         * @param value the replacement text of the entity.
         *
         * @throws IllegalStateException if the document is closed.
         * @throws SAXException the application may raise an exception.
         *
         * @see DefaultHandler2#internalEntityDecl(String, String)
         */
        @Override
        public void internalEntityDecl(final String name, final String value) throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.internalEntityDecl(name, value);
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @param target the processing instruction target.
         * @param data The processing instruction data, or null if none is supplied.
         *
         * @throws IllegalStateException if the document is closed.
         * @throws SAXException any SAX exception, possibly wrapping another exception.
         *
         * @see DefaultHandler2#processingInstruction(String, String)
         */
        @Override
        public void processingInstruction(final String target, final String data) throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.processingInstruction(target, data);
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @throws IllegalStateException if the document is closed.
         * @throws SAXException any SAX exception, possibly wrapping another exception.
         *
         * @see DefaultHandler2#startCDATA()
         */
        @Override
        public void startCDATA() throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.startCDATA();
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @throws IllegalStateException if the document is closed.
         */
        @Override
        public void startDocument() throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.startDocument();
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @param uri the namespace URI.
         * @param localName the local name.
         * @param qName the qualified name.
         * @param attributes the element attributes.
         *
         * @throws IllegalStateException if the document is closed.
         * @throws SAXException any SAX exception, possibly wrapping another exception.
         *
         * @see DefaultHandler2#startElement(String, String, String, Attributes)
         */
        @Override
        public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.startElement(uri, localName, qName, attributes);
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @param name the name of the entity. If it is a parameter entity, the name will begin with '%', and if it is the external DTD subset, it will be "[dtd]".
         *
         * @throws IllegalStateException if the document is closed.
         * @throws SAXException the application may raise an exception.
         *
         * @see DefaultHandler2#startEntity(String)
         */
        @Override
        public void startEntity(final String name) throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.startEntity(name);
        }

        /**
         * Closes the handler.
         */
        public abstract
        void close();

        /**
         * {@inheritDoc}
         * <p/>
         * When a handler is closed it usually indicates that the document is parsed completely or does not accept any data.
         *
         * @return true if the handler is closed, and false otherwise.
         */
        public abstract
        boolean isClosed();

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation refers to the traditional XML node type as the document.
         * Therefore, it can also refer to the XML document element; however, the naming is intended to reflect the role of the object as a documented source of data and it can be any subclass of {@link Node}.
         *
         * @return the XML document.
         */
        @Override
        public T getDocument() {
            return document;
        }

        /**
         * Sets the handler document.
         *
         * @param document the document.
         */
        public
        void setDocument(
            final T document
            ) {
            this.document = document;
        }
    }

    /**
     * {@code Locator} classifies a simple lookup interface for individual nodes and provides default methods for locating relatives of such nodes.
     *
     * @see system.data.Locator
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class Locator
    extends Traversal
    implements
        Element,
        system.data.Locator<Node>,
        Reversible,
        Reversing<Locator>,
        Search
    {
        /** The element director. */
        protected
        Supplier<Node> director;

        /**
         * Sets the locator element and returns this locator.
         *
         * @param element the element.
         *
         * @return the locator.
         *
         * @throws NullPointerException if the element is null.
         */
        public
        Locator at(
            final Node element
            ) { return null; }

        /**
         * Sets the locator element at the specified coordinates and returns this locator.
         *
         * @param coords the element coordinates.
         *
         * @return the locator.
         *
         * @throws NullPointerException if the coordinates is, or contains, null.
         * @throws IndexOutOfBoundsException if the coordinates are outside of the document boundaries.
         */
        public
        Locator at(
            final Coordinates coords
            ) { return null; }

        /**
         * Sets the locator element at the specified index and returns this locator.
         * <p/>
         * Index must start from the top-most level enumerating the document child elements.
         * In other words, the index for the document element is an empty array.
         *
         * @param index the element index.
         *
         * @return the locator.
         *
         * @throws NullPointerException if the index is, or contains, null.
         * @throws IndexOutOfBoundsException if the index is outside of the document boundaries.
         */
        public
        Locator at(
            final Number... index
            ) { return null; }

        /**
         * Advances the locator to the absolute first element and returns this locator.
         *
         * @return the advanced locator.
         */
        public
        Locator first() {
            return this;
        }

        /**
         * Sets the locator search boundaries to the specified neighborhood and returns this locator.
         *
         * @param neighborhood the neighborhood.
         *
         * @return the rebound locator.
         *
         * @throws NullPointerException if the neighborhood is null.
         */
        public
        Locator in(
            final Neighborhood neighborhood
            ) {
            return this;
        }

        /**
         * Advances the locator to the absolute last element and returns this locator.
         *
         * @return the advanced locator.
         */
        public
        Locator last() {
            return this;
        }

        /**
         * Advances the locator to the absolute nth element and returns this locator.
         *
         * @param n the zero-based element index.
         *
         * @return the advanced locator.
         */
        public
        Locator nth(
            final int n
            ) {
            return this;
        }

        /**
         * Resets the locator to its starting point in the iteration and returns this locator.
         */
        public
        void reset() {}

        /**
         * Updates the internal {@code hasNext} flag indicating if there are more elements in the iteration.
         * <p/>
         * This method is called inside {@link #hasNext()} and everywhere else updating is required.
         */
        protected
        void updateHasNext() {}

        /**
         * Validates the specified element coordinates is within the locator boundaries.
         *
         * @param coords the element coordinates.
         *
         * @throws NullPointerException if the element coordinates is null.
         * @throws IndexOutOfBoundsException if the coordinates is outside of the locator boundaries.
         */
        protected
        void validateInBounds(
            final int[] coords
            ) {}

        /**
         * {@inheritDoc}
         *
         * @return the located element.
         *
         * @throws NoSuchElementException if the element is not found or the iteration has no more elements.
         */
        @Override
        public Node element() {
            return null;
        }

        /**
         * {@inheritDoc}
         *
         * @param fallback the fallback.
         *
         * @return the located element or the fallback.
         */
        @Override
        public Node element(final Node fallback) {
            return null;
        }

        /**
         * {@inheritDoc}
         *
         * @return true if the locator element is found, and false otherwise.
         */
        @Override
        public boolean elementFound() {
            return false;
        }

        /**
         * Returns an iterable for the remaining locator nodes that satisfy the match condition.
         *
         * @return the iterable for the remaining matched elements.
         */
        @Override
        public Iterable<Node> elements() {
            return null;
        }

        /**
         * {@inheritDoc}
         *
         * @param action the action to be performed for each element.
         *
         * @throws NullPointerException if the action is null.
         */
        @Override
        public void forEachRemaining(final Consumer<? super Boolean> action) {}

        /**
         * {@inheritDoc}
         *
         * @return the advanced locator.
         */
        @Override
        public Locator found() {
            return this;
        }

        /**
         * Sets the locator starting element and returns this locator.
         * <p/>
         * The relative location of the starting element will also determine the direction of iteration.
         *
         * @param start the starting element.
         *
         * @return the locator.
         */
        @Override
        public Locator from(final Node start) {
            return null;
        }

        /**
         * Sets the locator starting element coordinates and returns this locator.
         * <p/>
         * The relative location of the starting element coordinates will also determine the direction of iteration.
         *
         * @param start the starting element coordinates.
         *
         * @return the locator.
         */
        @Override
        public Locator from(final Coordinates start) {
            return null;
        }

        /**
         * Sets the locator starting element to the specified coordinates and returns this locator.
         * <p/>
         * The relative location of the starting element will also determine the direction of iteration.
         * The coordinates for the root element is an empty array.
         *
         * @param start the starting element coordinates.
         *
         * @return the locator.
         *
         * @throws NullPointerException if the coordinates contains null.
         * @throws IndexOutOfBoundsException if the coordinates are outside of the document boundaries.
         */
        @Override
        public Locator from(final Number... start) {
            return null;
        }

        /**
         * {@inheritDoc}
         *
         * @return the located element index.
         *
         * @throws NoSuchElementException if the element is not found or the iteration has no more elements.
         */
        @Override
        public Integer index() {
            return null;
        }

        /**
         * {@inheritDoc}
         *
         * @param fallback the fallback.
         *
         * @return the located element index or the fallback.
         */
        @Override
        public Integer index(final Integer fallback) {
            return null;
        }

        /**
         * Returns true if there are more elements in the iteration; otherwise returns false.
         * <p/>
         * This implementation calls {@link #updateHasNext()} internally.
         *
         * @return true if there are more elements in the iteration, and false otherwise.
         */
        @Override
        public boolean hasNext() {
            return false;
        }

        /**
         * Returns true if the element is found at the next location; otherwise returns false.
         *
         * @return true if the element is found, and false otherwise.
         *
         * @throws NoSuchElementException if the iteration has no more elements.
         */
        @Override
        public Boolean next() {
            return null;
        }

        /**
         * Returns the located element if found; otherwise returns null.
         *
         * @return the located element, or null if not found.
         */
        @Override
        public Node object() {
            return element(null);
        }

        /**
         * Removes the last found element from the document.
         */
        @Override
        public void remove() {}

        /**
         * Reverses the locator direction.
         */
        @Override
        public void reverse() {}

        /**
         * Reverses the locator direction and returns this locator.
         *
         * @return the reversed locator.
         */
        @Override
        public Locator reversed() {
            return this;
        }

        /**
         * Sets the locator ending element and returns this locator.
         * <p/>
         * The relative location of the ending element will also determine the direction of iteration.
         *
         * @param end the ending element.
         *
         * @return the locator.
         */
        @Override
        public Locator until(final Node end) {
            return null;
        }

        /**
         * Sets the locator ending element coordinates and returns this locator.
         * <p/>
         * The relative location of the ending element coordinates will also determine the direction of iteration.
         *
         * @param end the ending element coordinates.
         *
         * @return the locator.
         */
        @Override
        public Locator until(final Coordinates end) {
            return null;
        }

        /**
         * Sets the locator ending element to the specified coordinates and returns this locator.
         * <p/>
         * The relative location of the ending element will also determine the direction of iteration.
         *
         * @param end the ending element coordinates.
         *
         * @return the locator.
         *
         * @throws NullPointerException if the coordinates contains null.
         * @throws IndexOutOfBoundsException if the coordinates are outside of the document boundaries.
         */
        @Override
        public Locator until(final Number... end) {
            return null;
        }

        @Override
        public BiPredicate<Number, Node> getCondition() {
            return null;
        }

        @Override
        public Integer getEffectiveWidth() {
            return null;
        }

        /**
         * Returns the coordinates of the ending element, or null if none exists.
         *
         * @return the ending element coordinates, or null if none exists.
         */
        @Override
        public Coordinates getEndCoords() {
            return null;
        }

        @Override
        public Node getEndElement() {
            return null;
        }

        /**
         * Returns the locator ending element index, or null if none exists.
         *
         * @return the locator ending element index.
         */
        @Override
        public int[] getEndIndex() {
            return null;
        }

        @Override
        public Integer getMaxDepth() {
            return null;
        }

        @Override
        public Integer getMinDepth() {
            return null;
        }

        /**
         * {@inheritDoc}
         * <p/>
         * This implementation returns the {@code Integer[]} class.
         *
         * @return the {@code Integer[]} class.
         */
        @Override
        public Class<Integer[]> getOrderClass() {
            return Integer[].class;
        }

        /**
         * Returns the coordinates of the starting element, or null if none exists.
         *
         * @return the starting element coordinates, or null if none exists.
         */
        @Override
        public Coordinates getStartCoords() {
            return null;
        }

        @Override
        public Node getStartElement() {
            return null;
        }

        /**
         * Returns the locator starting element index, or null if none exists.
         *
         * @return the locator starting element index.
         */
        @Override
        public int[] getStartIndex() {
            return null;
        }

        /**
         * Returns the total width of the locator regardless of the effective search breadth.
         *
         * @return the locator width.
         */
        @Override
        public Integer getWidth() {
            return null;
        }

        /**
         * Sets the locator's search breadth to the specified min and max element depths. (inclusive)
         *
         * @param minDepth the min depth. (inclusive)
         * @param maxDepth the max depth. (inclusive)
         *
         * @thrown IllegalArgumentException if the min and max depth values are invalid.
         *
         * @see Search#setBreadth(int, int)
         */
        @Override
        public void setBreadth(final int minDepth, final int maxDepth) {}

        @Override
        public void setCondition(BiPredicate<Number, Node> pred) {}

        /**
         * Marks the ending element to be exclusive and returns this search type.
         *
         * @return the search type ending exclusively.
         */
        @Override
        public Locator setExclusiveEnd() {
            return this;
        }

        /**
         * Marks the starting element to be exclusive and returns this search type.
         *
         * @return the search type starting exclusively.
         */
        @Override
        public Locator setExclusiveStart() {
            return this;
        }

        /**
         * Marks the ending element to be inclusive and returns this search type.
         *
         * @return the search type ending inclusively.
         */
        @Override
        public Locator setInclusiveEnd() {
            return this;
        }

        /**
         * Marks the starting element to be inclusive and returns this search type.
         *
         * @return the search type starting inclusively.
         */
        @Override
        public Locator setInclusiveStart() {
            return this;
        }

        @Override
        public void setMaxDepth(final int maxDepth) {}

        @Override
        public void setMinDepth(final int minDepth) {}

        /**
         * Sets the the next element in the iteration.
         *
         * @param next the next element.
         *
         * @throws NullPointerException if the element is null.
         * @throws IndexOutOfBoundsException if the element is outside of the locator boundaries.
         */
        public
        void setNextElement(
            final Node next
            ) {}

        /**
         * Sets the coordinates for the next element in the iteration.
         *
         * @param next the next element coordinates.
         *
         * @throws NullPointerException if the index is null.
         * @throws IndexOutOfBoundsException if the index is outside of the locator boundaries.
         */
        public
        void setNextCoords(
            final Coordinates next
            ) {}

        /**
         * Sets the index of the next element in the iteration.
         * <p/>
         * The index for the root element is an empty array.
         *
         * @param next the next element index.
         *
         * @throws NullPointerException if the index is null.
         * @throws IndexOutOfBoundsException if the index is outside of the locator boundaries.
         */
        public
        void setNextIndex(
            final Number... next
            ) {}
    }

    /**
     * {@code Neighborhood} classifies continuously contained domains consisting of consecutive XML elements.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Neighborhood
    extends Domain
    {
        /**
         * Returns a neighborhood type set to start from the specified element.
         *
         * @param start the starting element.
         *
         * @return the neighborhood type.
         */
        Neighborhood from(
            Node start
            );

        /**
         * Returns a neighborhood type set to start from the specified element coordinates.
         *
         * @param start the starting element coordinates.
         *
         * @return the neighborhood type.
         */
        Neighborhood from(
            Coordinates start
            );

        /**
         * Marks the ending element to be exclusive and returns this neighborhood type.
         *
         * @return the neighborhood type ending exclusively.
         */
        Neighborhood setExclusiveEnd();

        /**
         * Marks the starting element to be exclusive and returns this neighborhood type.
         *
         * @return the neighborhood type starting exclusively.
         */
        Neighborhood setExclusiveStart();

        /**
         * Marks the ending element to be inclusive and returns this neighborhood type.
         *
         * @return the neighborhood type ending inclusively.
         */
        Neighborhood setInclusiveEnd();

        /**
         * Marks the starting element to be inclusive and returns this neighborhood type.
         *
         * @return the neighborhood type starting inclusively.
         */
        Neighborhood setInclusiveStart();

        /**
         * Returns a neighborhood type set to end at the specified element.
         *
         * @param end the ending element.
         *
         * @return the neighborhood type.
         */
        Neighborhood until(
            Node end
            );

        /**
         * Returns a neighborhood type set to end at the specified element coordinates.
         *
         * @param end the ending element coordinates.
         *
         * @return the neighborhood type.
         */
        Neighborhood until(
            Coordinates end
            );

        /**
         * Returns a neighborhood type set to start from the specified index in the document.
         *
         * @param start the starting index.
         *
         * @return the neighborhood type.
         */
        @Override
        Neighborhood from(
            Number... start
            );

        /**
         * Returns a neighborhood type set to end at the specified index in the document.
         *
         * @param end the ending index.
         *
         * @return the neighborhood type.
         */
        @Override
        Neighborhood until(
            Number... end
            );

        /**
         * Returns the ending element coordinates of the neighborhood.
         *
         * @return the ending element coordinates.
         */
        Coordinates getEndCoords();

        /**
         * Returns the ending element of the neighborhood.
         *
         * @return the ending element.
         */
        Node getEndElement();

        /**
         * Returns the ending element index of the neighborhood.
         *
         * @return the ending element index.
         */
        int[] getEndIndex();

        /**
         * Returns the starting element coordinates of the neighborhood.
         *
         * @return the starting element coordinates.
         */
        Coordinates getStartCoords();

        /**
         * Returns the starting element of the neighborhood.
         *
         * @return the starting element.
         */
        Node getStartElement();

        /**
         * Returns the starting element index of the neighborhood.
         *
         * @return the starting element index.
         */
        int[] getStartIndex();
    }

    /**
     * {@code NodeListIterator} represents an XML node list iterator.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected static abstract
    class NodeListIterator
    implements Iterator<Node>
    {
        /** The elements. */
        protected
        NodeList elements;

        /** The index of the last matched element. */
        protected
        int i;

        /** The "next element exists" flag. */
        protected
        boolean hasNext;

        /**
         * Creates an element iterator located at index zero and updates {@code hasNext}.
         *
         * @param elements the elements.
         */
        protected NodeListIterator(
            final NodeList elements
            ) {
            this.elements = elements;
            i = 0;
            updateHasNext();
        }

        /**
         * Updates the internal {@code hasNext} flag indicating if there are more elements in the iteration.
         * <p/>
         * This method is called inside {@link #next()}.
         */
        protected abstract
        void updateHasNext();

        /**
         * {@inheritDoc}
         *
         * @return true if the iteration has more elements, and false otherwise.
         */
        @Override
        public boolean hasNext() {
            return hasNext;
        }

        /**
         * {@inheritDoc}
         *
         * @return the next element in the iteration.
         *
         * @throws NoSuchElementException if the iteration has no more elements.
         */
        @Override
        public Node next() {
            if (!hasNext)
                throw new NoSuchElementException(XmlChildNotFound);

            final Node child = elements.item(i);
            i++;
            updateHasNext();
            return child;
        }
    }

    /**
     * {@code Schematic} is the base interface for all schematic element and attribute types.
     * <p/>
     * Each schematic data type also defines a sorted array of supported values when available.
     * In such cases, the {@code defaultValue()} method returns the default value.
     * <p/>
     * Each schematic data type also defines a required/optional boolean flag specified by the {@code isRequired()} method.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Schematic
    {
        /** The always-false validator. */
        Function<Node, Validation> FalseValidator = data -> () -> false;

        /** The always-true validator. */
        Function<Node, Validation> TrueValidator = data -> () -> true;

        /**
         * Returns the default value for the data type when available.
         * <p/>
         * This implementation returns null.
         *
         * @return the default value.
         */
        default
        String defaultValue() {
            return null;
        }

        /**
         * Returns true if there is a default value available; otherwise returns false.
         *
         * @return true if the default value is available, and false otherwise.
         */
        default
        boolean hasDefaultValue() {
            return defaultValue() != null;
        }

        /**
         * Returns true if the data type is required in the schema; otherwise returns false.
         *
         * @return true if the data type is required, and false otherwise.
         */
        boolean isRequired();

        /**
         * Returns a function for validating the values of the schematic data type.
         * <p/>
         * This implementation returns the {@link #TrueValidator}.
         *
         * @return the validator function.
         */
        default Function<Node, Validation> validator() {
            return TrueValidator;
        }

        /**
         * {@code PerName} classifies schematic data types that are differently represented per names, such as entity, element, or attribute names.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PerName
        {
            /**
             * Restricts context of the schematic data type to the specified name in the schema and returns its class type if found; otherwise returns null.
             *
             * @param name the name.
             *
             * @return the appropriate schematic class type, or null if not found.
             */
            Class<? extends Schematic> per(
                String name
            );
        }

        /**
         * {@code PerType} classifies schematic data types that are differently represented per class types.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PerType
        {
            /**
             * Restricts the context of the schematic data type to the specified class type in the schema and returns its class type if found; otherwise returns null.
             *
             * @param type the class type.
             *
             * @return the appropriate schematic class type, or null if not found.
             */
            Class<? extends Schematic> per(
                Class<?> type
            );
        }
    }

    /**
     * {@code Search} classifies functional selections within neighborhoods.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Search
    extends Neighborhood
    {
        /**
         * Returns the effective width of the domain accounting for the search breadth.
         * <p/>
         * When the effective width of a domain is equal to its normal width, it implies that the domain is a neighborhood continuously consisting of sibling elements.
         *
         * @return the effective width of search domain.
         */
        Integer getEffectiveWidth();

        /**
         * Returns the width of the neighborhood regardless of the search breadth.
         * <p/>
         * When the width of a neighborhood is equal to the difference between the ending and starting indexes, it implies that the neighborhood consists of sibling elements.
         *
         * @return the width of neighborhood.
         */
        Integer getWidth();

        /**
         * Creates and returns an iterable for elements that match the search criteria.
         *
         * @return the iterable for found elements.
         */
        Iterable<Node> elements();

        /**
         * Sets the search breadth to the specified min and max element depths.
         * <p/>
         * The search breadth is defined as the minimum and maximum element depth value where search algorithm will traverse over.
         * Whether any of the depth values are inclusive or exclusive is a matter of implementation.
         *
         * @param minDepth the min depth.
         * @param maxDepth the max depth.
         */
        default
        void setBreadth(
            final int minDepth,
            final int maxDepth
            ) {
            setMinDepth(minDepth);
            setMaxDepth(maxDepth);
        }

        /**
         * Returns the search condition bi-predicate.
         *
         * @return the condition.
         */
        BiPredicate<Number, Node> getCondition();

        /**
         * Returns the search max depth.
         *
         * @return the max depth.
         */
        Integer getMaxDepth();

        /**
         * Returns the search min depth.
         *
         * @return the min depth.
         */
        Integer getMinDepth();

        /**
         * Sets the search condition bi-predicate.
         * <p/>
         * The condition bi-predicate must accept an integer for the element's order of appearance within its parent element and the element as arguments.
         *
         * @param pred the search condition.
         */
        void setCondition(
            BiPredicate<Number, Node> pred
            );

        /**
         * Sets the search max depth.
         *
         * @param maxDepth the max depth.
         */
        void setMaxDepth(
            int maxDepth
            );

        /**
         * Sets the search min depth.
         *
         * @param minDepth the min depth.
         */
        void setMinDepth(
            int minDepth
            );
    }

    /**
     * {@code Traversal} represents an iterative algorithm over a neighborhood of XML nodes such that every node is visited at least once.
     * <p/>
     * Instances of this class can be considered as encapsulated units of logic that are by themselves sufficient for carrying out all the necessary work for filtering by match conditions.
     * For example, if a traversal logic is designed to support concurrency, the work of thread management should ideally be handled inside this class.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class Traversal
    {
        /** The skip flag. */
        public static final
        Node Skip = new Null.XMLNode() {};

        /**
         * Performs a breadth-first traversal on the specified source element and applying the specified filter, and returns the result of the first filter function call.
         * <p/>
         * If a filter function call returns null, the traversal stops and null is returned.
         * If a filter function call returns a sub-type of {@link Traversal.Stop}, the sub-type will be returned.
         * If a filter function call returns {@link Traversal#Skip}, the child elements of the queue element will not be added to the queue.
         * <p/>
         * This implementation uses a {@code LinkedList}.
         *
         * @param source the source element.
         * @param filter the filter function.
         *
         * @return the filtered source, or null if the source is null.
         *
         * @throws NullPointerException if the filter is null.
         *
         * @see #breadthFirstQueue(Queue, Function)
         */
        public static
        Node breadthFirst(
            final Node source,
            final Function<Node, Node> filter
            ) {
            if (source instanceof Stop)
                return source;

            final LinkedList<Node> queue = new LinkedList<>();
            queue.add(source);
            return breadthFirstQueue(queue, filter);
        }

        /**
         * Performs a breadth-first traversal on the specified queue of elements and applying the specified filter, and returns the result of the first filter function call.
         * <p/>
         * If a filter function call returns null, the traversal stops and null is returned.
         * If a filter function call returns a sub-type of {@link Traversal.Stop}, the sub-type will be returned.
         * If a filter function call returns {@link Traversal#Skip}, the child elements of the queue element will not be added to the queue.
         *
         * @param queue the queue.
         * @param filter the filter function.
         *
         * @return the filtered queue element, or null if the queue is empty or traversal stops.
         *
         * @throws NullPointerException if the queue or the filter is null.
         */
        public static
        Node breadthFirstQueue(
            final Queue<Node> queue,
            final Function<Node, Node> filter
            ) {
            Node target = null;
            while (!queue.isEmpty()) {
                final Node source = queue.element();
                if (source instanceof Stop)
                    return source;

                target = filter.apply(source);
                if (target == null)
                    return null;

                if (target instanceof Stop)
                    return target;

                if (target != Skip && source != null) {
                    final NodeList children = source.getChildNodes();
                    for (int i = 0; i < children.getLength(); i++)
                        queue.add(children.item(i));
                }

                queue.remove();
            }

            return target;
        }

        /**
         * Performs a depth-first traversal on the specified source element and applying the specified filter, and returns the result of the first filter function call.
         * <p/>
         * If a filter function call returns null, the traversal stops and null is returned.
         * If a filter function call returns a sub-type of {@link Traversal.Stop}, the sub-type will be returned.
         * If a filter function call returns {@link Traversal#Skip}, the child elements of the source element will not be traversed.
         * <p/>
         * This implementation is recursive.
         *
         * @param source the source element.
         * @param filter the filter function.
         *
         * @return the filtered source.
         *
         * @throws NullPointerException if the filter is null.
         */
        public static
        Node depthFirst(
            final Node source,
            final Function<Node, Node> filter
            ) {
            if (source instanceof Stop)
                return source;

            final Node target = filter.apply(source);
            if (target != null) {
                if (target instanceof Stop)
                    return target;

                if (target != Skip && source != null) {
                    final NodeList children = source.getChildNodes();
                    for (int i = 0; i < children.getLength(); i++) {
                        final Node result = depthFirst(children.item(i), filter);
                        if (result == null)
                            return null;

                        if (result instanceof Stop)
                            return result;
                    }
                }
            }

            return target;
        }

        /**
         * Performs a reverse breadth-first traversal on the specified source element and applying the specified filter, and returns the result of the first filter function call.
         * <p/>
         * If a filter function call returns null, the traversal stops and null is returned.
         * If a filter function call returns a sub-type of {@link Traversal.Stop}, the sub-type will be returned.
         * If a filter function call returns {@link Traversal#Skip}, the child elements of the queue element will not be added to the queue.
         * <p/>
         * This implementation uses a {@code LinkedList}.
         *
         * @param source the source element.
         * @param filter the filter function.
         *
         * @return the filtered source, or null if the source is null.
         *
         * @throws NullPointerException if the filter is null.
         *
         * @see #breadthFirstQueue(Queue, Function)
         */
        public static
        Node reverseBreadthFirst(
            final Node source,
            final Function<Node, Node> filter
            ) {
            if (source instanceof Stop)
                return source;

            final LinkedList<Node> queue = new LinkedList<>();
            queue.add(source);
            return reverseBreadthFirstQueue(queue, filter);
        }

        /**
         * Performs a reverse breadth-first traversal on the specified queue of elements and applying the specified filter, and returns the result of the first filter function call.
         * <p/>
         * If a filter function call returns null, the traversal stops and null is returned.
         * If a filter function call returns a sub-type of {@link Traversal.Stop}, the sub-type will be returned.
         * If a filter function call returns {@link Traversal#Skip}, the child elements of the queue element will not be added to the queue.
         *
         * @param queue the queue.
         * @param filter the filter function.
         *
         * @return the filtered queue element, or null if the queue is empty or traversal stops.
         *
         * @throws NullPointerException if the queue or the filter is null.
         */
        public static
        Node reverseBreadthFirstQueue(
            final Queue<Node> queue,
            final Function<Node, Node> filter
            ) {
            Node target = null;
            while (!queue.isEmpty()) {
                final Node source = queue.element();
                if (source instanceof Stop)
                    return source;

                target = filter.apply(source);
                if (target == null)
                    return null;

                if (target instanceof Stop)
                    return target;

                if (target != Skip && source != null) {
                    final NodeList children = source.getChildNodes();
                    for (int i = children.getLength() - 1; i >= 0; i--)
                        queue.add(children.item(i));
                }

                queue.remove();
            }

            return target;
        }

        /**
         * Performs a reverse depth-first traversal on the specified source element and applying the specified filter, and returns the result of the first filter function call.
         * <p/>
         * If a filter function call returns null, the traversal stops and null is returned.
         * If a filter function call returns a sub-type of {@link Traversal.Stop}, the sub-type will be returned.
         * If a filter function call returns {@link Traversal#Skip}, the child elements of the source element will not be traversed.
         * <p/>
         * This implementation is recursive.
         *
         * @param source the source element.
         * @param filter the filter function.
         *
         * @return the filtered source.
         *
         * @throws NullPointerException if the filter is null.
         */
        public static
        Node reverseDepthFirst(
            final Node source,
            final Function<Node, Node> filter
            ) {
            if (source instanceof Stop)
                return source;

            final Node target = filter.apply(source);
            if (target != null) {
                if (target instanceof Stop)
                    return target;

                if (target != Skip && source != null) {
                    final NodeList children = source.getChildNodes();
                    for (int i = children.getLength() - 1; i >= 0; i--) {
                        final Node result = reverseDepthFirst(children.item(i), filter);
                        if (result == null)
                            return null;

                        if (result instanceof Stop)
                            return result;
                    }
                }
            }

            return target;
        }

        /**
         * {@code Stop} represents the traversal stop flag as a wrapped XML element.
         * <p/>
         * By design, when an instance of this class is returned by a traversal filter function, the traversal will stop and return the instance.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public static
        class Stop
        implements Element
        {
            /** The wrapped element. */
            protected final
            Node element;

            /**
             * Creates a stop flag at the specified element.
             *
             * @param element the element.
             */
            public
            Stop(
                final Node element
                ) {
                this.element = element;
            }

            /**
             * Returns the stop element.
             *
             * @return the stop element.
             */
            @Override
            public Node object() {
                return element;
            }
        }
    }

    /**
     * {@code Validation} is a special meta-data representing the result of analyzing XML documents, elements, or attributes for performance.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Validation
    {
        /**
         * Returns true if the validation is passed or, in other words, the document/element/attribute is valid, and false otherwise.
         *
         * @return true if the document/element/attribute is valid, and false otherwise.
         */
        boolean isPassed();
    }
}