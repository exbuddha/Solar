package system.data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Stack;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import system.data.Format.Hierarchical;
import system.data.Format.Interpretable;

/**
 * {@code XML} represents an XML document.
 * <p>
 * This class defines static members for generating XML documents or elements from an input stream, writing documents to output streams, and traversing them.
 * It provides two forms of data elements: one uses the traditional {@code org.w3c.dom} object definitions, and the other wraps those objects around additional interfaces.
 * The second approach is useful for extending the functionality of the traditional objects and converting them into special data types, while the first approach is better geared for raw string .
 * <p>
 * The static methods in this class implementation are thread-safe.
 * <p>
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

    /** The XML string. */
    private
    CharSequence string;

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
        final Document.Handler handler
        ) {
        super();
        this.handler = handler;
    }

    /**
     * Creates an empty XML document using the standard implementation of {@link DocumentHandler}.
     */
    public
    XML() {
        this(DocumentHandler.standard());
        ((DocumentHandler) handler).setDocument(newDocumentBuilder().newDocument());
    }

    /**
     * Creates an XML document from the specified document using the standard implementation of {@link DocumentHandler}.
     *
     * @param document the document.
     */
    public
    XML(
        final org.w3c.dom.Document document
        ) {
        this(DocumentHandler.standard());
        ((DocumentHandler) handler).setDocument(document);
    }

    /**
     * Returns an intermediary object for performing simple searches on the specified element.
     *
     * @param element the element.
     *
     * @return the intermediary search type.
     */
    public static
    Search at(
        final Element element
        ) { return null; }

    /**
     * Returns an intermediary object for performing simple searches on the element at the specified index or sequence of nested indexes.
     * <p>
     * Sequences must start from the top-most level enumerating the document child elements.
     *
     * @param index the element index.
     *
     * @return the intermediary search type.
     */
    public static
    Search at(
        final Number... index
        ) { return null; }

    /**
     * Clones the source element and adds it as a child element to the target element and returns the cloned element.
     *
     * @param source the source element.
     * @param target the target element.
     *
     * @return the cloned element.
     *
     * @throws DOMException if a node name is not a valid XML name.
     * @throws UnsupportedOperationException if an unexpected node type other than ELEMENT or TEXT is passed.
     */
    public static
    Element clone(
        final Element source,
        final Element target
        )
    throws
        DOMException,
        UnsupportedOperationException
    {
        if (source == null)
            return target;

        switch (source.getNodeType())
        {
        case Node.ELEMENT_NODE:
            return (Element) target.appendChild(cloneAttributes(source, (Element) Element.of(target.getOwnerDocument().createElement(source.getNodeName()))));

        case Node.TEXT_NODE:
            return (Element) target.appendChild(target.getOwnerDocument().createTextNode(source.getNodeValue()));
        }

        throw new UnsupportedOperationException("Unsupported XML node type: " + source.getNodeType());
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
    Element cloneAttributes(
        final Element source,
        final Element target
        ) {
        if (source == null)
            return target;

        final NamedNodeMap attributes = source.getAttributes();
        if (attributes != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                final Node attribute = attributes.item(i);
                target.setAttribute(attribute.getNodeName(), attribute.getNodeValue());
            }
        }

        return target;
    }

    /**
     * Clones the attributes of the source element and adds them as attributes to the target element if one of the provided conditions pass, and returns the target element.
     * <p>
     * The bi-predicate functions must accept a number for the source attribute's order of appearance and the attribute as arguments.
     *
     * @param source the source node.
     * @param target the target element.
     * @param preds the source attribute pass condition bi-predicates.
     *
     * @return the target element.
     */
    public static
    Element cloneAttributes(
        final Element source,
        final Element target,
        final BiPredicate<Number, Node>... preds
        ) {
        if (source == null)
            return target;

        final NamedNodeMap attributes = source.getAttributes();
        if (attributes != null)
            if (preds.length == 0)
                for (int i = 0; i < attributes.getLength(); i++) {
                    final Node attribute = attributes.item(i);
                    target.setAttribute(attribute.getNodeName(), attribute.getNodeValue());
                }
            else
                for (int i = 0; i < attributes.getLength(); i++) {
                    final Node attribute = attributes.item(i);
                    for (int j = 0; j < preds.length; j++)
                        if (preds[j].test(i, attribute))
                            target.setAttribute(attribute.getNodeName(), attribute.getNodeValue());
                }

        return target;
    }

    /**
     * Clones the entire inner structure of the source element and adds them as children to the target element, and returns the target element.
     * <p>
     * This implementation is recursive.
     *
     * @param source the source element.
     * @param target the target element.
     *
     * @return the target element.
     */
    public static
    Element cloneChildren(
        final Element source,
        final Element target
        )
    throws DOMException
    {
        if (source != null && source.hasChildNodes()) {
            final NodeList innerElements = source.getChildNodes();
            for (int n = 0; n < innerElements.getLength(); n++) {
                final Element innerElement = (Element) Element.of(innerElements.item(n));
                cloneChildren(innerElement, clone(innerElement, target));
            }
        }

        return target;
    }

    /**
     * Clones the entire inner structure of the source element and adds them as children to the target element if one of the provided conditions pass, and returns the target element.
     * <p>
     * This implementation is recursive.
     * The conditions are passed down to all child elements recursively.
     * The bi-predicate functions must accept a number for the child element's order of appearance and the child element as arguments.
     *
     * @param source the source element.
     * @param target the target element.
     * @param preds the child element pass condition bi-predicates.
     *
     * @return the target element.
     */
    public static
    Element cloneChildren(
        final Element source,
        final Element target,
        final BiPredicate<Number, Node>... preds
        )
    throws DOMException
    {
        if (source != null && source.hasChildNodes()) {
            final NodeList innerElements = source.getChildNodes();
            if (preds.length == 0)
                for (int n = 0; n < innerElements.getLength(); n++) {
                    final Element innerElement = (Element) innerElements.item(n);
                    cloneChildren(innerElement, clone(innerElement, target));
                }
            else
                for (int n = 0; n < innerElements.getLength(); n++) {
                    final Element innerElement = (Element) innerElements.item(n);
                    for (int j = 0; j < preds.length; j++)
                        if (preds[j].test(n, innerElement))
                            cloneChildren(innerElement, clone(innerElement, target), preds);
                }
        }

        return target;
    }

    /**
     * Returns an intermediary object for performing simple searches starting from the specified element.
     *
     * @param element the starting element.
     *
     * @return the intermediary search type.
     */
    public static
    Search from(
        final Element element
        ) { return null; }

    /**
     * Returns an intermediary object for performing simple searches starting from the node at the specified index or sequence of nested indexes.
     *
     * @param index the node index.
     *
     * @return the intermediary search type.
     */
    public static
    Search from(
        final Number... index
        ) { return null; }

    /**
     * Returns a new document builder, or null if an error occurs.
     *
     * @return a new document builder, or null if an error occurs.
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

    public static
    XML of(
        final org.w3c.dom.Document document
        ) {
        return null;
    }

    /**
     * Parses the input stream with the specified handler.
     * <p>
     * If the handler is a {@link Handler} type the document is returned, otherwise null.
     *
     * @param inputStream the input stream.
     * @param handler the handler.
     *
     * @return the document, or null if handler is not a {@code Handler} type.
     *
     * @throws IOException if any I/O errors occur.
     * @throws ParserConfigurationException if a serious configuration error occurs.
     * @throws SAXException if a processing error occurs.
     */
    public static
    Node parse(
        final InputStream inputStream,
        final DefaultHandler handler
        )
    throws
        IOException,
        ParserConfigurationException,
        SAXException
    {
        newParser()
        .parse(inputStream, handler);

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
     * @throws IOException if any I/O errors occur.
     * @throws ParserConfigurationException if a serious configuration error occurs.
     * @throws SAXException if a processing error occurs.
     */
    public static
    Node parse(
        final InputStream inputStream
        )
    throws
        IOException,
        ParserConfigurationException,
        SAXException
    {
        return parse(inputStream, DocumentHandler.standard());
    }

    /**
     * Writes the XML document to the specified output stream using the indentation amount.
     *
     * @param outputStream the output stream.
     * @param indent indentation amount.
     *
     * @throws TransformerConfigurationException if a serious configuration error occurs.
     * @throws TransformerException if a transformation error occurs.
     */
    public
    void write(
        final OutputStream outputStream,
        final Byte indent
        )
    throws
        TransformerConfigurationException,
        TransformerException
    {
        // Initialize the transformer factory and create a new transformer
        final Transformer transformer = newTransformer();

        // Set the transformer's properties to use an indent size of 2
        final Properties outputFormatProperties = new Properties();
        if (indent != null) {
            outputFormatProperties.put(OutputKeys.INDENT, "yes");
            outputFormatProperties.put("{http://xml.apache.org/xslt}indent-amount", indent.toString());
        }
        transformer.setOutputProperties(outputFormatProperties);

        // Transform the document to the output stream
        transformer.transform(new DOMSource(getDocument()), new StreamResult(outputStream));
    }

    @Override
    public char charAt(int index) {
        return 0;
    }

    @Override
    public CharSequence getCharSequence() {
        return string;
    }

    @Override
    public Class<?> getOrderClass() {
        return Element.class;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public org.w3c.dom.Document object() {
        return handler instanceof Handler
               ? getDocument()
               : null;
    }

    @Override
    public Class<?> objectType() {
        return handler instanceof Handler
                ? ((Handler<?>) handler).document.getClass()
                : null;
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return null;
    }

    @Override
    public String toString() {
        return getCharSequence().toString();
    }

    /**
     * Writes the XML document to the specified output stream.
     *
     * @param outputStream the output stream.
     *
     * @throws TransformerConfigurationException if a serious configuration error occurs.
     * @throws TransformerException if a transformation error occurs.
     */
    @Override
    public void write(final OutputStream outputStream)
    throws
        TransformerConfigurationException,
        TransformerException
    {
        write(outputStream, null);
    }

    /**
     * Returns the XML document.
     *
     * @return the XML document.
     *
     * @throws IllegalStateException if the handler is null or has an incorrect type.
     */
    public
    org.w3c.dom.Document getDocument() {
        try {
            return (org.w3c.dom.Document) ((Handler<?>) handler).document;
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
     * {@code DocumentHandler} represent a handler that is in charge of parsing entire XML documents.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class DocumentHandler
    extends Handler<org.w3c.dom.Document>
    {
        /**
         * Returns a document handler that accepts only elements, attributes, and text nodes.
         * <p>
         * If a document is not provided, a new document is created and used by this handler.
         *
         * @return the basic handler.
         */
        public static final
        DocumentHandler basic() {
            return new Basic();
        }

        /**
         * Returns a document handler that accepts all node types.
         * <p>
         * If a document is not provided, a new document is created and used by the handler.
         *
         * @return the standard handler.
         */
        public static final
        DocumentHandler standard() {
            return new Standard();
        }

        /**
         * {@code Basic} is an implementation of a document handler that only accepts elements, attributes, and text nodes.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        protected static
        class Basic
        extends DocumentHandler
        {
            /** The "document closed" flag. */
            protected
            boolean closed;

            /** The element stack. */
            protected final
            Stack<org.w3c.dom.Element> stack = new Stack<>();

            /** The document depth. */
            protected
            int depth;

            /**
             * Accepts an element text.
             *
             * @throws IllegalStateException if document is closed.
             * @throws SAXException if the stack size does not match the document depth.
             */
            @Override
            public void characters(final char[] ch, final int start, final int length) throws SAXException {
                super.characters(ch, start, length);

                if (stack.empty() || stack.size() != depth)
                    throw new SAXException();

                // Append the characters to the element at the top of the stack
                stack.peek().appendChild(getDocument().createTextNode(new String(ch, start, length)));
            }

            /**
             * Ends the document.
             *
             * @throws IllegalStateException if document is closed.
             */
            @Override
            public void endDocument() throws SAXException {
                super.endDocument();
                closed = depth == 0;
            }

            /**
             * Ends an element.
             * <p>
             * This implementation calls {@link #endDocument()} internally if there is only one element left in the stack.
             *
             * @throws IllegalStateException if document is closed.
             * @throws SAXException if the stack size does not match the document depth.
             */
            @Override
            public void endElement(final String uri, final String localName, final String qName) throws SAXException {
                super.endElement(uri, localName, qName);

                // Finalize and close the element in the stack
                if (depth > 0 && stack.size() == depth && qName.equals(stack.peek().getTagName()))
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
             */
            @Override
            public boolean isClosed() {
                return closed;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public InputSource resolveEntity(final String publicId, final String systemId) throws IOException, SAXException {
                // To avoid DTD validation
                return new InputSource(new ByteArrayInputStream(new byte[] {}));
            }

            /**
             * Starts the document.
             * <p>
             * If the document is null, a new one is created.
             * If the document is not empty, an {@code IllegalStateException} is thrown.
             *
             * @throws IllegalStateException if document is closed or non-empty.
             */
            @Override
            public void startDocument() throws SAXException {
                super.startDocument();

                final org.w3c.dom.Document doc = getDocument();
                if (doc == null)
                    setDocument(newDocumentBuilder().newDocument());
                else
                    if (doc.hasChildNodes()) {
                        closed = true;
                        throw new IllegalStateException();
                    }

                depth = 0;
            }

            /**
             * Starts an element.
             *
             * @throws IllegalStateException if document is closed.
             */
            @Override
            public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
                super.startElement(uri, localName, qName, attributes);

                // Create a placeholder element
                final org.w3c.dom.Element e = getDocument().createElement(qName);

                // Add the attributes to the element
                final int length = attributes.getLength();
                for (int i = 0; i < length; i++)
                    e.setAttribute(attributes.getQName(i), attributes.getValue(i));

                // Push the element to the stack
                stack.push(e);
                depth++;
            }
        }

        /**
         * {@code Standard} is an implementation of a document handler that accepts all standard XML element types.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        protected static
        class Standard
        extends Basic
        {}
    }

    /**
     * {@code Filter} represents data types that are constructed in order to filter out parts of XML documents during traversal.
     * <p>
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
         * <p>
         * Technically speaking, a match condition can be as arbitrarily complex as needed.
         * Any form of programmatic logic can be laid out by match types given that the traversal logic provides enough data to the containing filter type at runtime, such as element index, depth, name, etc.
         * The predefined subclasses inside this class represent only a handful of match conditions that are commonly employed in most forms of XML traversals.
         * <p>
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
             * <p>
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
             * <p>
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
             * <p>
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
             * <p>
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
             * <p>
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
             * <p>
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
             * <p>
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
             * <p>
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
             * <p>
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
             * <p>
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
             * <p>
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
             * <p>
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
             * <p>
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
             * <p>
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
     * {@code Handler} re-implements the {@link DefaultHandler} class.
     * <p>
     * By design, the generic type of this class defines the handler's result or target element type.
     *
     * @param <T> the document type.
     */
    public static abstract
    class Handler<T extends Node>
    extends DefaultHandler
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
        public
        Handler(
            final T document
            ) {
            this();
            setDocument(document);
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
         * <p>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @throws IllegalStateException if document is closed.
         */
        @Override
        public void characters(final char[] ch, final int start, final int length) throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.characters(ch, start,length);
        }

        /**
         * {@inheritDoc}
         * <p>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @throws IllegalStateException if document is closed.
         */
        @Override
        public void endElement(final String uri, final String localName, final String qName) throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.endElement(uri, localName, qName);
        }

        /**
         * {@inheritDoc}
         * <p>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @throws IllegalStateException if document is closed.
         */
        @Override
        public void startDocument() throws SAXException {
            if (isClosed())
                throw new IllegalStateException();

            super.startDocument();
        }

        /**
         * {@inheritDoc}
         * <p>
         * This implementation throws an {@code IllegalStateException} if it receives notification while the document is closed.
         *
         * @throws IllegalStateException if document is closed.
         */
        @Override
        public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if (isClosed())
                throw new IllegalStateException();
        }

        /**
         * {@inheritDoc}
         * <p>
         * When a handler is closed it usually indicates that the document is parsed completely.
         *
         * @return true if the handler is closed, and false otherwise.
         */
        public abstract
        boolean isClosed();

        /**
         * {@inheritDoc}
         * <p>
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
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Locator
    {
        /**
         * Returns the attribute node of the specified node matching the specified attribute condition, or null if attribute doesn't exist.
         * <p>
         * The bi-predicate function must accept a number for the attribute node's order of appearance and the node as arguments.
         *
         * @param node the node.
         * @param pred the attribute node condition bi-predicate.
         * @return the attribute node, or null if it doesn't exist.
         */
        default
        Node findAttribute(
            final Node node,
            final BiPredicate<Number, Node> pred
            ) {
            if (node == null)
                return null;

            final NamedNodeMap attributes = node.getAttributes();
            if (attributes != null) {
                for (int i = 0; i < attributes.getLength(); i++) {
                    final Node attribute = attributes.item(i);
                    if (pred.test(i, attribute))
                        return attribute;
                }
            }

            return null;
        }

        /**
         * Returns the attribute node of the specified node, starting from n and matching the specified attribute condition, or null if attribute doesn't exist.
         * <p>
         * The bi-predicate function must accept a number for the attribute node's order of appearance and the node as arguments.
         *
         * @param node the node.
         * @param n the starting attribute index.
         * @param pred the attribute node condition bi-predicate.
         * @return the attribute node, or null if it doesn't exist.
         */
        default
        Node findAttribute(
            final Node node,
            final int n,
            final BiPredicate<Number, Node> pred
            ) {
            if (node == null)
                return null;

            final NamedNodeMap attributes = node.getAttributes();
            if (attributes != null) {
                for (int i = n; i < attributes.getLength(); i++) {
                    final Node attribute = attributes.item(i);
                    if (pred.test(i, attribute))
                        return attribute;
                }
            }

            return null;
        }

        /**
         * Returns the attribute node of the specified node, starting from n and ending in m, and matching the specified attribute condition, or null if attribute doesn't exist.
         * <p>
         * The bi-predicate function must accept a number for the attribute node's order of appearance and the node as arguments.
         *
         * @param node the node.
         * @param n the starting attribute index.
         * @param m the ending attribute index.
         * @param attrPred the attribute node condition bi-predicate.
         * @return the attribute node, or null if it doesn't exist.
         */
        default
        Node findAttribute(
            final Node node,
            final int n,
            final int m,
            final BiPredicate<Number, Node> attrPred
            ) {
            if (node == null)
                return null;

            final NamedNodeMap attributes = node.getAttributes();
            if (attributes != null) {
                for (int i = n; i < m && i < attributes.getLength(); i++) {
                    final Node attribute = attributes.item(i);
                    if (attrPred.test(i, attribute))
                        return attribute;
                }
            }

            return null;
        }

        /**
         * Returns the attribute value for the specified attribute name of the specified node, or null if attribute doesn't exist.
         *
         * @param node the node.
         * @param name the attribute name.
         * @return the attribute value, or null if it doesn't exist.
         */
        static
        String findAttributeValue(
            final Node node,
            final String name
            ) {
            if (node == null)
                return null;

            final NamedNodeMap attributes = node.getAttributes();
            if (attributes != null) {
                final Node attribute = attributes.getNamedItem(name);
                if (attribute != null)
                    return attribute.getNodeValue();
            }

            return null;
        }

        /**
         * Returns the first child node matching the name condition, or null if child node doesn't exist.
         *
         * @param childNamePred the child node name condition predicate.
         * @return the child node, or null if it doesn't exist.
         */
        default
        Node findChild(
            final Predicate<String> childNamePred
            ) {
            if (getNode() == null)
                return null;

            final NodeList childNodes = getNode().getChildNodes();
            if (childNodes.getLength() == 0)
                return null;

            for (int n = 0; n < childNodes.getLength(); n++) {
                final Node childNode = childNodes.item(n);
                if (childNamePred.test(childNode.getNodeName()))
                    return childNode;
            }

            return null;
        }

        /**
         * Returns the first child node within the specified node matching the child node name, or null if child node doesn't exist.
         *
         * @param childName the child node name.
         * @return the child node, or null if it doesn't exist.
         */
        default
        Node findChild(
            final String childName
            ) {
            return findChild((final String name) -> name.equals(childName));
        }

        /**
         * Returns the index-th child node within the specified node matching the child node name and attributes condition, or null if child node doesn't exist.
         * <p>
         * Negative indexes are wrapped once.
         * The bi-predicate function must accept a number for the attribute node's order of appearance and the node as arguments.
         *
         * @param index the child node index.
         * @param node the node.
         * @param childNamePred the child node name condition predicate.
         * @param childAttrPred the child attribute condition bi-predicate.
         * @return the child node, or null if it doesn't exist.
         */
        default
        Node findChild(
            final int index,
            final Node node,
            final Predicate<String> childNamePred,
            final BiPredicate<Number, Node> childAttrPred
            ) {
            if (node == null)
                return null;

            final NodeList childNodes = node.getChildNodes();
            if (childNodes.getLength() == 0)
                return null;

            int i;
            if (index >= 0) {
                if (index >= childNodes.getLength())
                    return null;

                i = 0;
                for (int n = i; n < childNodes.getLength(); n++) {
                    final Node childNode = childNodes.item(n);
                    if (childNamePred.test(childNode.getNodeName()) &&
                       findAttribute(childNode, childAttrPred) != null &&
                       i++ == index)
                        return childNode;
                }
            }
            else {
                if (index + childNodes.getLength() >= 0)
                    return null;

                i = childNodes.getLength() - 1;
                for (int n = i; n >= 0; n--) {
                    final Node childNode = childNodes.item(n);
                    if (childNamePred.test(childNode.getNodeName()) &&
                       findAttribute(childNode, childAttrPred) != null &&
                       i-- == index)
                        return childNode;
                }
            }

            return null;
        }

        /**
         * Returns the first child node within the specified node matching the child node name and attributes condition, or null if child node doesn't exist.
         * <p>
         * The bi-predicate function must accept a number for the attribute node's order of appearance and the node as arguments.
         * Attribute order is zero-based.
         * Negative orders are wrapped once.
         *
         * @param node the node.
         * @param childNamePred the child node name condition predicate.
         * @param childAttrPred the child attribute condition bi-predicate.
         * @return the first child node, or null if it doesn't exist.
         */
        default
        Node findChild(
            final Node node,
            final Predicate<String> childNamePred,
            final BiPredicate<Number, Node> childAttrPred
            ) {
            return findChild(0, node, childNamePred, childAttrPred);
        }

        /**
         * Returns the index-th child node within the specified node matching the child node name and attributes condition, or null if child node doesn't exist.
         * <p>
         * The bi-predicate function must accept a number for the attribute node's order of appearance and the node as arguments.
         * Attribute order is zero-based.
         * Negative orders are wrapped once.
         *
         * @param index the child node index.
         * @param node the node.
         * @param childName the child node name.
         * @param childAttrPred the child attribute condition bi-predicate.
         * @return the child node, or null if it doesn't exist.
         */
        default
        Node findChild(
            final int index,
            final Node node,
            final String childName,
            final BiPredicate<Number, Node> childAttrPred
            ) {
            return findChild(index, node, (final String name) -> name.equals(childName), childAttrPred);
        }

        /**
         * Returns the first child node within the specified node matching the child node name and attributes condition, or null if child node doesn't exist.
         * <p>
         * The bi-predicate function must accept a number for the attribute node's order of appearance and the node as arguments.
         * Attribute order is zero-based.
         * Negative orders are wrapped once.
         *
         * @param node the node.
         * @param childName the child node name.
         * @param childAttrPred the child attribute condition bi-predicate.
         * @return the first child node, or null if it doesn't exist.
         */
        default
        Node findChild(
            final Node node,
            final String childName,
            final BiPredicate<Number, Node> childAttrPred
            ) {
            return findChild(0, node, childName, childAttrPred);
        }

        /**
         * Returns the index-th child within the specified node matching the child node name, attribute name, and value, or null if child node doesn't exist.
         * <p>
         * Negative indexes are wrapped once.
         * If attribute value is null, any value is accepted.
         *
         * @param index the child node index.
         * @param node the node.
         * @param childName the child node name.
         * @param childAttrName the child attribute name.
         * @param childAttrValue the child attribute value.
         * @return the child node, or null if it doesn't exist.
         */
        default
        Node findChild(
            final int index,
            final Node node,
            final String childName,
            final String childAttrName,
            final String childAttrValue
            ) {
            return findChild(index, node,
                   (final String name) -> name.equals(childName),
                   (final Number i, final Node attribute)
                   -> childAttrName.equals(attribute.getNodeName()) &&
                      (childAttrValue == null ||
                      childAttrValue.equals(attribute.getNodeValue())));
        }

        /**
         * Returns the first child node within the specified node matching the child node name, attribute name, and value, or null if child node doesn't exist.
         * <p>
         * If attribute value is null, any value is accepted.
         *
         * @param node the node.
         * @param childName the child node name.
         * @param childAttrName the child attribute name.
         * @param childAttrValue the child attribute value.
         * @return the first child node, or null if it doesn't exist.
         */
        default
        Node findChild(
            final Node node,
            final String childName,
            final String childAttrName,
            final String childAttrValue
            ) {
            return findChild(0, node, childName, childAttrName, childAttrValue);
        }

        /**
         * Returns an iterable for child nodes.
         *
         * @return iterable for child nodes.
         */
        default
        Iterable<Node> findChildren() {
            return new Iterable<Node>() {
                @Override
                public Iterator<Node> iterator() {
                    if (getNode() == null)
                        return new Iterator<Node>() {
                            @Override
                            public boolean hasNext() {
                                return false;
                            }

                            @Override
                            public Node next() {
                                throw new NoSuchElementException("There are no child nodes.");
                            }
                        };
                    else
                        return new Iterator<Node>() {
                            private final NodeList childNodes = getNode().getChildNodes();
                            private int i = 0;  // index of the current node

                            @Override
                            public boolean hasNext() {
                                return i < childNodes.getLength();
                            }

                            @Override
                            public Node next() {
                                return childNodes.item(i++);
                            }
                        };
                }
            };
        }

        /**
         * Returns an iterable for child nodes matching the specified name condition.
         * <p>
         * If the name predicate function is null, all nodes are accepted.
         *
         * @param namePred the child node name condition predicate.
         * @return iterable for child nodes.
         */
        default
        Iterable<Node> findChildren(
            final Predicate<String> namePred
            ) {
            return new Iterable<Node>() {
                @Override
                public final Iterator<Node> iterator() {
                    if (getNode() == null)
                        return new Iterator<Node>() {
                            @Override
                            public boolean hasNext() {
                                return false;
                            }

                            @Override
                            public Node next() {
                                throw new NoSuchElementException("There are no child nodes.");
                            }
                        };
                    else
                        return new Iterator<Node>() {
                            private final NodeList childNodes = getNode().getChildNodes();
                            private int i = 0;  // index of the current element
                            private int j = 0;  // index of the last matched element

                            @Override
                            public final boolean hasNext() {
                                for (j = i; j < childNodes.getLength(); j++)
                                    if (namePred == null || namePred.test(childNodes.item(j).getNodeName()))
                                        return true;

                                return false;
                            }

                            @Override
                            public final Node next() {
                                i = j + 1;
                                return childNodes.item(j);
                            }
                        };
                }
            };
        }

        /**
         * Returns an iterable for child nodes with the specified name.
         *
         * @param childName the child node name.
         * @return iterable for child nodes.
         */
        default
        Iterable<Node> findChildren(
            final String childName
            ) {
            return findChildren((final String name) -> name.equals(childName));
        }

        /**
         * Returns an iterable for child nodes matching the specified name and attribute conditions.
         * <p>
         * If a condition is null, nodes matching the conditions up to that point are accepted.
         *
         * @param namePred the child node name condition predicate.
         * @param attrPred the child attribute condition bi-predicate.
         * @return iterable for child nodes.
         */
        default
        Iterable<Node> findChildren(
            final Predicate<String> namePred,
            final BiPredicate<Integer, Node> attrPred
            ) {
            return new Iterable<Node>() {
                @Override
                public final Iterator<Node> iterator() {
                    if (getNode() == null)
                        return new Iterator<Node>() {
                            @Override
                            public boolean hasNext() {
                                return false;
                            }

                            @Override
                            public Node next() {
                                throw new NoSuchElementException("There are no child nodes.");
                            }
                        };
                    else
                        return new Iterator<Node>() {
                            private final NodeList childNodes = getNode().getChildNodes();
                            private int i = 0;  // index of the current element
                            private int j = 0;  // index of the last matched element

                            @Override
                            public final boolean hasNext() {
                                for (j = i; j < childNodes.getLength(); j++) {
                                    if (namePred == null)
                                        return true;

                                    final Node childNode = childNodes.item(j);
                                    if (namePred.test(childNode.getNodeName())) {
                                        if (attrPred == null)
                                            return true;

                                        final NamedNodeMap attributes = childNode.getAttributes();
                                        for (int k = 0; k < attributes.getLength(); k++)
                                            if (attrPred.test(k, attributes.item(k)))
                                                return true;
                                    }
                                }

                                return false;
                            }

                            @Override
                            public final Node next() {
                                i = j + 1;
                                return childNodes.item(j);
                            }
                        };
                }
            };
        }

        /**
         * Returns an iterable for child nodes with the specified name, attribute name, and value.
         * <p>
         * If child node name condition is null, all nodes are considered.
         * If attribute value is null, all considered nodes are accepted.
         *
         * @param childName the child node name.
         * @param attrName the child attribute name.
         * @param attrValue the child attribute value.
         * @return iterable for child nodes.
         */
        default
        Iterable<Node> findChildren(
            final String childName,
            final String attrName,
            final String attrValue
            ) {
            return findChildren(
                   (final String name) -> name.equals(childName),
                   (final Integer index, final Node attribute)
                   -> attribute.getNodeName().equals(attrName) &&
                   (attrValue == null ||
                   attribute.getNodeValue().equals(attrValue)));
        }

        /**
         * Returns the target node of the lookup.
         *
         * @return the target node.
         */
        Node getNode();
    }

    /**
     * {@code Neighborhood} classifies uniquely contained domains consisting of consecutive sibling nodes.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Neighborhood
    extends Domain
    {
        /**
         * Returns a search type set to start from the specified node.
         *
         * @param start the start node.
         * @return the search type.
         */
        Search from(
            Element start
            );

        /**
         * Returns the end index of the neighborhood in order of appearance.
         *
         * @return the end index of neighborhood.
         */
        Integer getEndIndex();

        /**
         * Returns the start index of the neighborhood in order of appearance.
         *
         * @return the start index of neighborhood.
         */
        Integer getStartIndex();

        /**
         * Returns a search type set to end on the specified node.
         *
         * @param end the end node.
         * @return the search type.
         */
        Search until(
            Element end
            );
    }

    /**
     * {@code Element} classifies all XML data elements.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Element
    extends XMLElement
    {
        /**
         * Returns a conventional XML element wrapped around the specified traditional XML element as its target.
         *
         * @param target the traditional XML element.
         *
         * @return the conventional XML element.
         */
        static
        XMLElement of(
            final Node target
            ) {
            if (target instanceof XMLDocument) {
                final org.w3c.dom.Element object = (org.w3c.dom.Element) ((XMLDocument) target).object();
                return new XMLDocument() {
                    @Override
                    public org.w3c.dom.Element object() {
                        return object;
                    }

                    @Override
                    public Class<? extends Node> objectType() {
                        return target.getClass();
                    }
                };
            }

            if (target instanceof org.w3c.dom.Document)
                return new XMLDocument() {
                    @Override
                    public org.w3c.dom.Element object() {
                        return (org.w3c.dom.Element) target;
                    }

                    @Override
                    public Class<? extends Node> objectType() {
                        return target.getClass();
                    }
                };

            if (target instanceof XMLElement) {
                final org.w3c.dom.Element object = (org.w3c.dom.Element) ((Element) target).object();
                return new Element() {
                    @Override
                    public org.w3c.dom.Element object() {
                        return object;
                    }

                    @Override
                    public Class<? extends Node> objectType() {
                        return target.getClass();
                    }
                };
            }

            return new Element() {
                @Override
                public org.w3c.dom.Element object() {
                    return (org.w3c.dom.Element) target;
                }

                @Override
                public Class<? extends Node> objectType() {
                    return target.getClass();
                }
            };
        }

        /**
         * {@code Element} classifies all XML data element attributes.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
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
                    return new Attribute() {
                        @Override
                        public Attr object() {
                            return object;
                        }

                        @Override
                        public Class<? extends Attr> objectType() {
                            return target.getClass();
                        }
                    };
                }

                return new Attribute() {
                    @Override
                    public Attr object() {
                        return target;
                    }

                    @Override
                    public Class<? extends Attr> objectType() {
                        return target.getClass();
                    }
                };
            }
        }
    }

    /**
     * {@code Search} classifies functional selections within neighborhoods such as sorts.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Search
    extends Neighborhood
    {
        /**
         * Finds the collection of nodes that match the search criteria.
         *
         * @return the collection of nodes.
         */
        abstract
        Collection<Element> find();

        /**
         * {@code Binary} classifies forms of binary sort algorithm that employ linear lookup strategies.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Binary
        extends Search
        {
            /**
             * Binds to a single node or a group of subjectively similar nodes and returns the bound search type.
             *
             * @param plan the binary search traversal plan.
             * @param target the target node(s).
             *
             * @return the bound search type.
             */
            Binary bind(
                Filter plan,
                Element... target
                );
        }

        /**
         * {@code Merge} classifies forms of merge sort algorithm that employ divide-and-conquer strategies.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Merge
        extends Binary
        {
            /**
             * Merges with a single node or a group of subjectively arbitrary nodes and returns the merged search type.
             *
             * @param plan the merge traversal plan.
             * @param source the source node(s).
             *
             * @return the merged search type.
             */
            Merge merge(
                Filter plan,
                Element... source
                );
        }
    }

    /**
     * {@code Traversal} represents an iterative algorithm over a neighborhood of XML nodes such that every node is visited at least once.
     * <p>
     * Instances of this class can be considered as encapsulated units of logic that are by themselves sufficient for carrying out all the necessary work for filtering by match conditions.
     * For example, if a traversal logic is designed to support concurrency, the work of thread management should ideally be handled inside this class.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Traversal
    extends Filter
    {}
}