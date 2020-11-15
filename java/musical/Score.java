package musical;

import static music.system.data.Constant.MusicXML.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.xml.parsers.FactoryConfigurationError;

import org.xml.sax.ext.DefaultHandler2;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import exceptions.InvalidMusicXMLException;
import exceptions.UnsupportedClefException;
import music.system.data.MusicXML;
import system.Type;
import system.data.Dictionary;
import system.data.Fraction;
import system.data.XML;

/**
 * {@code Score} represents a valid MusicXML document.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Score
extends MusicXML
implements
    Iterable<Score.Part<? extends Node>>,
    Notation,
    music.system.Type<Notation>
{
    /**
     * Creates a score from the specified input stream and default SAX event handler, parses the input, then performs basic validation if handler is {@link MusicXML.Handler} type.
     *
     * @param inputStream the input stream.
     * @param handler the document handler.
     *
     * @throws IOException if any I/O errors occur.
     * @throws SAXException if a processing error occurs.
     * @throws InvalidMusicXMLException if the stream content is not a valid MusicXML document.
     *
     * @see XML#parse(InputStream, DefaultHandler2)
     */
    public
    Score(
        final InputStream inputStream,
        final DocumentHandler handler
        )
    throws
        IOException,
        InvalidMusicXMLException,
        SAXException
    {
        super(handler);
        if (handler instanceof Handler) {
            handler.setDocument((org.w3c.dom.Document) parse(inputStream, handler));
            Validation.basic((org.w3c.dom.Document) ((Handler) handler).getDocument());
        }
        else
            parse(inputStream, handler);
    }

    /**
     * Accepts the specified XML document as a MusicXML score.
     *
     * @param xml the XML document.
     */
    public
    Score(
        final XML xml
        ) {
        super(xml);
    }

    /**
     * Accepts the specified document as a MusicXML score and validates it using the supplied function.
     *
     * @param document the document.
     * @param validation the validation supplier.
     */
    public
    Score(
        final org.w3c.dom.Document document,
        final Function<org.w3c.dom.Document, Validation> validation
        ) {
        super(document, validation);
    }

    /**
     * Accepts the specified document as a MusicXML score and performs basic validation.
     *
     * @param document the document.
     */
    public
    Score(
        final org.w3c.dom.Document document
        ) {
        super(document, Validation::basic);
    }

    /**
     * Creates and returns a partwise MusicXML document equivalent to the specified timewise document.
     *
     * @return the partwise document.
     *
     * @throws FactoryConfigurationError in case of service configuration error or if the implementation is not available or cannot be instantiated.
     * @throws NullPointerException if a document cannot be instantiated.
     * @throws DOMException if an error occurs while cloning nodes.
     * @throws InvalidMusicXMLException if an unexpected element type is found while cloning the partwise document.
     *
     * @see XML#newDocumentBuilder()
     */
    public
    org.w3c.dom.Document convertToPartwise()
    throws
        DOMException,
        InvalidMusicXMLException
    {
        // Create a new document and a new root node with all the attributes of the original document's root
        final org.w3c.dom.Document document = newDocumentBuilder().newDocument();
        final Node root = getDocument().getChildNodes().item(0);
        final Node newRoot = document.appendChild(cloneAttributes(root, document.createElement(SCORE_PARTWISE)));

        // Create a placeholder map for the new parts in the new document
        final Dictionary<Node> newParts = new Dictionary<>();

        // Iterate through the timewise document's nodes and...
        final NodeList nodes = root.getChildNodes();
        for (int n = 0; n < nodes.getLength(); n++) {
            final Node node = nodes.item(n);

            // For the <measure> nodes...
            if (node.getNodeName().equals(MEASURE)) {
                final NodeList parts = node.getChildNodes();

                // Iterate through all <part> nodes...
                for (int p = 0; p < parts.getLength(); p++) {
                    final Node part = parts.item(p);

                    // Find an already created new <part> with the same part ID in the new document
                    //   or create a new <part> in the new document and append it to the new root
                    final String partId = findAttributeValue(part, ID);
                    Node newPart = newParts.find(partId);
                    if (newPart == null) {
                        newPart = cloneAttributes(part, document.createElement(PART));
                        newParts.add(partId, newRoot.appendChild(newPart));
                    }

                    // Clone the <measure> node and append it to the new <part>
                    //   and recursively clone all the nodes in the child node and append to the cloned <measure>
                    //   and add the cloned <measure> to the new document
                    cloneChildren(part, cloneChild(node, newPart));
                }
            }

            // For other nodes recursively clone and add them to the new document
            else
                cloneChildren(node, cloneChild(node, newRoot));
        }

        return document;
    }

    /**
     * Creates and returns a timewise MusicXML document equivalent to the specified partwise document.
     *
     * @return the timewise document.
     *
     * @throws FactoryConfigurationError in case of service configuration error or if the implementation is not available or cannot be instantiated.
     * @throws NullPointerException if a document cannot be instantiated.
     * @throws DOMException if an error occurs while cloning nodes.
     * @throws InvalidMusicXMLException if an unexpected element type is found while cloning the timewise document.
     *
     * @see XML#newDocumentBuilder()
     */
    public
    org.w3c.dom.Document convertToTimewise()
    throws
        DOMException,
        InvalidMusicXMLException
    {
        // Create a new document and a new root node with all the attributes of the original document's root
        final org.w3c.dom.Document document = newDocumentBuilder().newDocument();
        final Node root = getDocument().getChildNodes().item(0);
        final org.w3c.dom.Node newRoot = document.appendChild(cloneAttributes(root, document.createElement(SCORE_TIMEWISE)));

        // Create a placeholder map for the new measures in the new document
        final Dictionary<Node> newMeasures = new Dictionary<>();

        // Iterate through the partwise document's nodes and...
        final NodeList nodes = root.getChildNodes();
        for (int n = 0; n < nodes.getLength(); n++) {
            final Node node = nodes.item(n);

            // For the <part> nodes...
            if (node.getNodeName().equals(PART)) {
                final NodeList measures = node.getChildNodes();

                // Iterate through all <measure> nodes...
                for (int m = 0; m < measures.getLength(); m++) {
                    final Node measure = measures.item(m);

                    // Find an already created new <measure> with the same part ID in the new document
                    //   or create a new <measure> in the new document and append it to the new root
                    final String partId = findAttributeValue(measure, ID);
                    Node newMeasure = newMeasures.find(partId);
                    if (newMeasure == null) {
                        newMeasure = cloneAttributes(measure, document.createElement(MEASURE));
                        newMeasures.add(partId, newRoot.appendChild(newMeasure));
                    }

                    // Clone the <part> node and append it to the new <measure>
                    //   and recursively clone all child nodes in the measure and append to the cloned <part>
                    //   and add the cloned <part> to the new document
                    cloneChildren(measure, cloneChild(node, newMeasure));
                }
            }

            // For other nodes recursively clone and add them to the new document
            else
                cloneChildren(node, cloneChild(node, newRoot));
        }

        return document;
    }

    /**
     * Returns true if the score is partwise, and false otherwise.
     *
     * @return true if the score is partwise, and false otherwise.
     */
    public
    boolean isPartwise() {
        return getDocument().getDocumentElement().getTagName()
               .equals(SCORE_PARTWISE);
    }

    /**
     * Returns true if the score is timewise, and false otherwise.
     *
     * @return true if the score is timewise, and false otherwise.
     */
    public
    boolean isTimewise() {
        return getDocument().getDocumentElement().getTagName()
               .equals(SCORE_TIMEWISE);
    }

    /**
     * Returns the score-specific interpreter.
     *
     * @return the interpreter.
     */
    public abstract
    Interpreter findInterpreter();

    @Override
    public boolean is(final Type<? super Notation> type) {
        // Scores are not associated with a different score type
        return false;
    }

    /**
     * {@code Conductor} represents all handler types that are intended for first-pass conduction of musical scores.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract static
    class Conductor
    extends DocumentHandler
    implements Handler
    {
        /**
         * Creates a score conductor for the specified MusicXML document.
         *
         * @param document the document.
         */
        protected
        Conductor(
            final org.w3c.dom.Document document
            ) {
            super();
        }

        /**
         * {@code Allocation} classifies an initial step in interpretation of scores where a score part is assigned to an instrument and performer class.
         * This step can be considered as a part of score interpretation.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Allocation
        {}

        /**
         * {@code Execution} represents specialized MusicXML handlers that are intended for just-in-time processing of score documents.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public abstract
        class Execution
        implements Handler
        {}
    }

    /**
     * {@code Interpreter} represents an intelligent score part in MusicXML documents.
     * <p/>
     * The default instances of this class correspond to uniquely coupled pairs of {@code score-part} and {@code part} elements in the score document.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Interpreter
    extends Part<Interpreter>
    {
        /** The {@code score-part} element. */
        public final
        org.w3c.dom.Element index;

        /**
         * Creates a default score part for the specified {@code part} and {@code score-part} elements.
         *
         * @param part the {@code part} element.
         * @param scorePart the {@code score-part} element.
         */
        protected
        Interpreter(
            final org.w3c.dom.Element part,
            final org.w3c.dom.Element scorePart
            ) {
            super((MusicXML.Element) MusicXML.Element.of(part));
            this.index = scorePart;
        }

        /**
         * Returns the first measure of the part.
         *
         * @return the first measure.
         */
        public abstract
        Measure findMeasure();

        /**
         * Returns an iterable for music data within all {@code measure} elements in the score part.
         *
         * @return iterable for music data.
         */
        public abstract
        Iterable<org.w3c.dom.Element> findMusicIgnoreMeasure();

        /**
         * Returns the performance instance for the specified music data in the score considering all the effective {@code attributes} elements.
         *
         * @param music the music data nodes.
         * @return the instance.
         */
        public abstract
        Incident<? extends Number> findInstance(
            Node... music
            );

        /**
         * Returns the previous interpreter.
         *
         * @return the previous interpreter.
         */
        public abstract
        Interpreter prev();

        /**
         * Returns the {@code part} element.
         *
         * @return the {@code part} element.
         */
        public
        org.w3c.dom.Element getPartElement() {
            return document;
        }

        /**
         * Returns the {@code score-part} element.
         *
         * @return the {@code score-part} element.
         */
        public
        org.w3c.dom.Element getScorePartElement() {
            return index;
        }
    }

    /**
     * {@code Measure} represents a score measure.
     * <p/>
     * Measures that contain other measure types represent hyper-measures.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Measure
    extends Part<Interpreted>
    implements Timewise.Search
    {
        /**
         * Creates a part measure with the specified {@code measure} element.
         *
         * @param measure the measure element.
         */
        protected
        Measure(
            final org.w3c.dom.Element measure
            ) {
            super((MusicXML.Element) MusicXML.Element.of(measure));
        }

        public abstract
        Iterable<Element> findMusic();

        /**
         * Returns the previous interpreted type in part.
         *
         * @return the previous interpreted type.
         */
        public abstract
        Interpreted prev();

        public abstract
        Iterable<Incident<? extends Number>> reversed();

        /**
         * {@code Segment} represents a measure segment.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public abstract
        class Segment
        extends Measure
        {
            protected
            Segment() {
                super(Measure.this);
            }
        }
    }

    /**
     * {@code Note} represents a score note.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Note
    extends Scale.Note
    implements
        Incident<Duration>,
        Interpreted
    {
        /**
         * Creates a score note for the specified scale, octave, pitch, and accidental.
         *
         * @param scale the score scale.
         * @param octave the note octave.
         * @param pitch the note pitch.
         * @param accidental the note accidental.
         */
        public
        Note(
            final Scale scale,
            final Byte octave,
            final Pitch pitch,
            final musical.Note.Accidental accidental
            ) {
            scale.super(octave, pitch, accidental);
        }

        /**
         * Creates a score note for the specified scale, octave, and pitch.
         *
         * @param scale the score scale.
         * @param octave the note octave.
         * @param pitch the note pitch.
         */
        public
        Note(
            final Scale scale,
            final Byte octave,
            final Pitch pitch
            ) {
            scale.super(octave, pitch);
        }

        /**
         * Creates a score note for the specified scale and similar to the music note.
         *
         * @param scale the score scale.
         * @param note the music note.
         */
        public
        Note(
            final Scale scale,
            final musical.Note note
            ) {
            scale.super(note);
        }

        /**
         * Creates a score note for the specified octave, pitch, and accidental using the standard classical chromatic scale.
         *
         * @param octave the note octave.
         * @param pitch the note pitch.
         * @param accidental the note accidental.
         */
        public
        Note(
            final Byte octave,
            final Pitch pitch,
            final musical.Note.Accidental accidental
            ) {
            music.classical.Scale.Chromatic.super(octave, pitch, accidental);
        }

        /**
         * Creates a score note for the specified octave and pitch using the standard classical chromatic scale.
         *
         * @param octave the note octave.
         * @param pitch the note pitch.
         */
        public
        Note(
            final Byte octave,
            final Pitch pitch
            ) {
            music.classical.Scale.Chromatic.super(octave, pitch);
        }

        /**
         * Creates a score note similar to the specified music note using the standard classical chromatic scale.
         *
         * @param note the music note.
         */
        public
        Note(
            final musical.Note note
            ) {
            music.classical.Scale.Chromatic.super(note);
        }
    }

    /**
     * {@code Part} represents score parts corresponding to {@code part} elements in MusicXML scores.
     * <p/>
     * Parts that contain other part types represent part groups.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Part<T extends Interpreted>
    extends XML.Handler<Element>
    implements
        Handler,
        Incident<Duration>,
        Interpreted,
        Iterable<Incident<? extends Number>>,
        Partwise.Search,
        Supplier<T>,
        Timed,
        music.system.Type<Interpreted>
    {
        /**
         * Creates a score part with the specified {@code part} element.
         *
         * @param part the {@code part} element.
         */
        protected
        Part(
            final MusicXML.Element part
            ) {
            super();
            setDocument(part);
        }

        /**
         * Returns the score part group number.
         *
         * @return the score part group number.
         */
        public abstract
        String findGroupNumber();

        /**
         * Returns the score part ID.
         *
         * @return the score part ID.
         */
        public abstract
        String findId();

        /**
         * Returns the initial instance in the score part.
         *
         * @return the initial instance.
         */
        public abstract
        Incident<? extends Number> findInitialInstance();

        /**
         * Finds the first effective instrument class for the score part, or returns null if data is not found.
         *
         * @return the instrument class, or null if data is not found.
         */
        public abstract
        Class<? extends Instrument> findInstrumentClass();

        /**
         * Finds the default performance instrument name or names for the score part, or returns an empty list if data is not found.
         *
         * @return the list of instrument names, or an empty list if data is not found.
         */
        @Override
        public abstract
        List<String> findInstrumentName();

        /**
         * Finds the scale of the first effective key signature in the score part, or returns null if data is not found.
         *
         * @return the effective key signature, or null if data is not found.
         */
        @Override
        public abstract
        Scale findKey();

        /**
         * Finds the clef type of the first effective staff or staves that define the system of the score part, or returns null if data is not found; or throws an {@code UnsupportedClefException} if a clef type is unknown.
         *
         * @return the effective clef or clefs, or null if data is not found.
         * @throws UnsupportedClefException if a clef type is unknown.
         */
        @Override
        public abstract
        List<Clef> findSystem();

        /**
         * Finds the first effective tempo in the score part, or returns null if data is not found.
         *
         * @return the effective tempo, or null if data is not found.
         */
        @Override
        public abstract
        Tempo findTempo();

        /**
         * Finds the first effective time signature in the score part, or returns null if data is not found.
         *
         * @return the effective time signature, or null if data is not found.
         */
        @Override
        public abstract
        Fraction findTimeSignature();

        @Override
        public boolean is(Type<? super Interpreted> type) {
            // Score parts are not associated with a different score part type
            return false;
        }

        /**
         * Returns the following converted node type in part.
         *
         * @return the following converted node.
         */
        public abstract
        T next();
    }

    /**
     * {@code Partwise} represents all partwise MusicXML scores.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class Partwise
    extends Score
    {
        /**
         * Creates a partwise score for the specified MusicXML document.
         * <p/>
         * This implementation converts timewise documents into their partwise equivalent using {@link #convertToPartwise()}.
         *
         * @param document the document.
         *
         * @throws FactoryConfigurationError in case of service configuration error or if the implementation is not available or cannot be instantiated.
         * @throws NullPointerException if a document cannot be instantiated.
         * @throws DOMException if an error occurs while cloning nodes.
         * @throws InvalidMusicXMLException if an unexpected node type is found while cloning nodes.
         */
        public
        Partwise(
            final XML document
            )
        throws
            DOMException,
            InvalidMusicXMLException
        {
            super(document);
            if (isTimewise())
                ((DocumentHandler) handler).setDocument(convertToPartwise());
        }

        /**
         * {@code Search} classifies search functionality for partwise MusicXML scores
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Search
        extends XML.Search
        {}
    }

    /**
     * {@code Rest} represents a score rest.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Rest
    extends Scale.Rest
    implements
        Incident<Duration>,
        Interpreted
    {
        public
        Rest(
            final Scale scale
            ) {
            scale.super();
        }

        public
        Rest() {
            music.classical.Scale.Chromatic.super();
        }
    }

    /**
     * {@code Tempo} classifies score tempo as data types that can be sequentially located and calculated in score parts, measures, or notes.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Tempo
    extends
       Sequential<XML.Handler<?>>,
       musical.Tempo
    {
        /**
         * Returns the score tempo at the specified MusicXML node within the score document.
         *
         * @param music the music data.
         *
         * @return the score tempo.
         */
        Tempo at(
            Incident<?> music
            );
    }

    /**
     * {@code Timed} classifies score data types as sequentially located and interpreted timed units.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Timed
    extends
        Sequential<Element>,
        system.data.Unit
    {
        @Override
        Timed at(
            MusicXML.Element music
            );
    }

    /**
     * {@code Timewise} represents all timewise MusicXML scores.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class Timewise
    extends Score
    {
        /**
         * Creates a timewise score for the specified MusicXML document.
         * <p/>
         * This implementation converts partwise documents into their timewise equivalent using {@link #convertToTimewise()}.
         *
         * @param document the document.
         *
         * @throws FactoryConfigurationError in case of service configuration error or if the implementation is not available or cannot be instantiated.
         * @throws NullPointerException if a document cannot be instantiated.
         * @throws DOMException if an error occurs while cloning nodes.
         * @throws InvalidMusicXMLException if an unexpected node type is found while cloning nodes.
         */
        public
        Timewise(
            final MusicXML document
            )
        throws
            DOMException,
            InvalidMusicXMLException
        {
            super(document);
            if (isPartwise())
                ((DocumentHandler) handler).setDocument(convertToTimewise());
        }

        /**
         * {@code Search} classifies search functionality for timewise MusicXML scores
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Search
        extends XML.Search
        {}
    }
}