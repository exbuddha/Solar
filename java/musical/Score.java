package musical;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import exceptions.InvalidMusicXMLException;
import exceptions.UnsupportedClefException;
import music.system.data.MusicXML;
import performance.Instance;
import performance.Instrument;
import system.data.Dictionary;
import system.data.Fraction;
import system.data.XML;

/**
 * {@code Score} represents a valid MusicXML document.
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
     * @param handler the default handler.
     * @throws InvalidMusicXMLException if the stream content is not a valid MusicXML document.
     * @throws IOException if any I/O errors occur.
     * @throws ParserConfigurationException if a serious configuration error occurs.
     * @throws SAXException if a processing error occurs.
     */
    public
    Score(
        final InputStream inputStream,
        final DefaultHandler handler
        )
    throws
        InvalidMusicXMLException,
        IOException,
        ParserConfigurationException,
        SAXException
    {
        super(handler);
        if (handler instanceof Handler) {
            ((Handler) handler).document = (org.w3c.dom.Document) parse(inputStream, handler);
            Validation.basic(((Handler) handler).document);
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
     * Converts a timewise MusicXML document to a new partwise equivalent document.
     *
     * @throws DOMException if an error occurs while cloning nodes.
     * @throws InvalidMusicXMLException if an unexpected node type is found while cloning nodes.
     * @throws ParserConfigurationException if a serious configuration error occurs.
     */
    public
    org.w3c.dom.Document convertToPartwise()
    throws
        DOMException,
        InvalidMusicXMLException,
        ParserConfigurationException
    {
        // Create a new document and a new root node with all the attributes of the original document's root
        final org.w3c.dom.Document document = MusicXML.newDocumentBuilder().newDocument();
        final Node root = getDocument().getChildNodes().item(0);
        final Node newRoot = document.appendChild(cloneAttributes(root, document.createElement(MusicXML.Constant.SCORE_PARTWISE)));

        // Create a placeholder map for the new parts in the new document
        final Dictionary<Node> newParts = new Dictionary<>();

        // Iterate through the timewise document's nodes and...
        final NodeList nodes = root.getChildNodes();
        for (int n = 0; n < nodes.getLength(); n++) {
            final Node node = nodes.item(n);

            // For the <measure> nodes...
            if (node.getNodeName().equals(MusicXML.Constant.MEASURE)) {
                final NodeList parts = node.getChildNodes();

                // Iterate through all <part> nodes...
                for (int p = 0; p < parts.getLength(); p++) {
                    final Node part = parts.item(p);

                    // Find an already created new <part> with the same part ID in the new document
                    //   or create a new <part> in the new document and append it to the new root
                    final String partId = Locator.findAttributeValue(part, MusicXML.Constant.ID);
                    Node newPart = newParts.find(partId);
                    if (newPart == null) {
                        newPart = cloneAttributes(part, document.createElement(MusicXML.Constant.PART));
                        newParts.add(partId, newRoot.appendChild(newPart));
                    }

                    // Clone the <measure> node and append it to the new <part>
                    //   and recursively clone all the nodes in the child node and append to the cloned <measure>
                    //   and add the cloned <measure> to the new document
                    cloneChildren(part, clone(node, newPart));
                }
            }

            // For other nodes recursively clone and add them to the new document
            else
                cloneChildren(node, clone(node, newRoot));
        }

        return document;
    }

    /**
     * Converts a partwise MusicXML document to a new timewise equivalent document.
     *
     * @throws DOMException if an error occurs while cloning nodes.
     * @throws InvalidMusicXMLException if an unexpected node type is found while cloning nodes.
     * @throws ParserConfigurationException if a serious configuration error occurs.
     */
    public
    org.w3c.dom.Document convertToTimewise()
    throws
        DOMException,
        InvalidMusicXMLException,
        ParserConfigurationException
    {
        // Create a new document and a new root node with all the attributes of the original document's root
        final org.w3c.dom.Document document = MusicXML.newDocumentBuilder().newDocument();
        final Node root = getDocument().getChildNodes().item(0);
        final Node newRoot = document.appendChild(cloneAttributes(root, document.createElement(MusicXML.Constant.SCORE_TIMEWISE)));

        // Create a placeholder map for the new measures in the new document
        final Dictionary<Node> newMeasures = new Dictionary<>();

        // Iterate through the partwise document's nodes and...
        final NodeList nodes = root.getChildNodes();
        for (int n = 0; n < nodes.getLength(); n++) {
            final Node node = nodes.item(n);

            // For the <part> nodes...
            if (node.getNodeName().equals(MusicXML.Constant.PART)) {
                final NodeList measures = node.getChildNodes();

                // Iterate through all <measure> nodes...
                for (int m = 0; m < measures.getLength(); m++) {
                    final Node measure = measures.item(m);

                    // Find an already created new <measure> with the same part ID in the new document
                    //   or create a new <measure> in the new document and append it to the new root
                    final String partId = Locator.findAttributeValue(measure, MusicXML.Constant.ID);
                    Node newMeasure = newMeasures.find(partId);
                    if (newMeasure == null) {
                        newMeasure = cloneAttributes(measure, document.createElement(MusicXML.Constant.MEASURE));
                        newMeasures.add(partId, newRoot.appendChild(newMeasure));
                    }

                    // Clone the <part> node and append it to the new <measure>
                    //   and recursively clone all child nodes in the measure and append to the cloned <part>
                    //   and add the cloned <part> to the new document
                    cloneChildren(measure, clone(node, newMeasure));
                }
            }

            // For other nodes recursively clone and add them to the new document
            else
                cloneChildren(node, clone(node, newRoot));
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
               .equals(MusicXML.Constant.SCORE_PARTWISE);
    }

    /**
     * Returns true if the score is timewise, and false otherwise.
     *
     * @return true if the score is timewise, and false otherwise.
     */
    public
    boolean isTimewise() {
        return getDocument().getDocumentElement().getTagName()
               .equals(MusicXML.Constant.SCORE_TIMEWISE);
    }

    /**
     * Returns the score-specific interpreter.
     *
     * @return the interpreter.
     */
    public abstract
    Interpreter findInterpreter();

    @Override
    public boolean is(final system.Type<Notation> type) {
        // Scores are not associated with a different score type
        return false;
    }

    /**
     * {@code Conductor} represents all handler types that are used for first-pass conduction of musical scores.
     */
    public abstract
    class Conductor
    extends Handler
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
            super(document);
        }

        /**
         * {@code Execution} represents specialized MusicXML filters that are intended for just-in-time processing of score documents.
         */
        public abstract
        class Execution
        extends Handler.Restriction
        {}
    }

    /**
     * {@code Interpreter} represents an intelligent score part in MusicXML documents.
     * <p>
     * The default instances of this class correspond to uniquely coupled pairs of {@code score-part} and {@code part} elements in the score document.
     */
    public abstract
    class Interpreter
    extends Part<Interpreter>
    {
        /** The {@code score-part} element. */
        public final
        org.w3c.dom.Node index;

        /**
         * Creates a default score part for the specified {@code part} and {@code score-part} elements.
         *
         * @param document the {@code part} element.
         * @param index the {@code score-part} element.
         */
        protected
        Interpreter(
            final org.w3c.dom.Node document,
            final org.w3c.dom.Node index
            ) {
            super(document);
            this.index = index;
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
        Iterable<org.w3c.dom.Node> findMusicIgnoreMeasure();

        /**
         * Returns the performance instance for the specified music data in the score considering all the effective {@code attributes} elements.
         *
         * @param music the music data nodes.
         * @return the instance.
         */
        public abstract
        Instance findInstance(
            org.w3c.dom.Node... music
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
        org.w3c.dom.Node getPartNode() {
            return document;
        }

        /**
         * Returns the {@code score-part} element.
         *
         * @return the {@code score-part} element.
         */
        public
        org.w3c.dom.Node getScorePartNode() {
            return index;
        }
    }

    /**
     * {@code Measure} represents a score measure.
     */
    public abstract
    class Measure
    extends Part<Interpreted>
    implements
        Iterator<Instance>,
        Timed,
        Timewise.Search
    {
        /**
         * Creates a part measure with the specified {@code measure} element.
         *
         * @param measure the measure element.
         */
        protected
        Measure(
            final Element measure
            ) {
            super(measure);
        }

        public abstract
        Iterable<org.w3c.dom.Node> findMusic();

        /**
         * Returns the previous interpreted type in part.
         *
         * @return the previous interpreted type.
         */
        public abstract
        Interpreted prev();

        public abstract
        Iterable<Instance> reversed();
    }

    /**
     * {@code Note} represents a score note.
     */
    public abstract
    class Note
    extends Scale.Note
    implements Interpreted
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
     */
    public abstract
    class Part<T extends Interpreted>
    extends XML.Handler<Node>
    implements
        Interpreted,
        Iterable<Instance>,
        Partwise.Search,
        Supplier<T>,
        music.system.Type<Interpreted>
    {
        /**
         * Creates a score part with the specified {@code part} node.
         *
         * @param document the {@code part} element.
         */
        protected
        Part(
            final org.w3c.dom.Node document
            ) {
            super(document);
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
        Instance findInitialInstance();

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
        Fraction findTempo();

        /**
         * Finds the duration of the first effective time signature in the score part, or returns null if data is not found.
         *
         * @return the effective time signature, or null if data is not found.
         */
        @Override
        public abstract
        Duration findTime();

        @Override
        public boolean is(system.Type<Interpreted> type) {
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
     * {@code Partwise} represents all part-wise MusicXML scores.
     */
    public static abstract
    class Partwise
    extends Score
    {
        /**
         * Creates a partwise score for the specified MusicXML document.
         * <p>
         * This implementation converts timewise documents into their partwise equivalent using {@link #convertToPartwise()}.
         *
         * @param document the document.
         * @throws DOMException if an error occurs while cloning nodes.
         * @throws InvalidMusicXMLException if an unexpected node type is found while cloning nodes.
         * @throws ParserConfigurationException if a serious configuration error occurs.
         */
        public
        Partwise(
            final org.w3c.dom.Document document
            )
        throws
            DOMException,
            InvalidMusicXMLException,
            ParserConfigurationException
        {
            super(document);
            if (isTimewise())
                ((XML.Handler<org.w3c.dom.Document>) handler).document = convertToPartwise();
        }

        public
        interface Search
        extends XML.Search
        {}
    }

    /**
     * {@code Rest} represents a score rest.
     */
    public abstract
    class Rest
    extends Scale.Rest
    implements Interpreted
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
     * {@code Timewise} represents all time-wise MusicXML scores.
     */
    public static abstract
    class Timewise
    extends Score
    {
        /**
         * Creates a timewise score for the specified MusicXML document.
         * <p>
         * This implementation converts partwise documents into their timewise equivalent using {@link #convertToTimewise()}.
         *
         * @param document the document.
         * @throws DOMException if an error occurs while cloning nodes.
         * @throws InvalidMusicXMLException if an unexpected node type is found while cloning nodes.
         * @throws ParserConfigurationException if a serious configuration error occurs.
         */
        public
        Timewise(
            final org.w3c.dom.Document document
            )
        throws
            DOMException,
            InvalidMusicXMLException,
            ParserConfigurationException
        {
            super(document);
            if (isPartwise())
                ((XML.Handler<org.w3c.dom.Document>) handler).document = convertToTimewise();
        }

        public
        interface Search
        extends XML.Search
        {}
    }

    public
    interface Timed
    extends system.Data.Sequential<Node>
    {
        @Override
        public
        Timed at(
            Node music
            );
    }
}