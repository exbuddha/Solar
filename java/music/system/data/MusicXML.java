package music.system.data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Supplier;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import system.data.Cache;
import system.data.XML;

/**
 * {@code MusicXML} classifies a MusicXML document.
 * <p>
 * This class defines static members for validating and traversing MusicXML documents.
 * <p>
 * This class implementation is in progress.
 *
 * @see XML
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class MusicXML
extends XML
{
    /**
     * Creates a MusicXML document with the specified handler.
     *
     * @param handler the handler.
     */
    protected
    MusicXML(
        final Document.Handler handler
        ) {
        super(handler);
    }

    /**
     * Accepts the specified XML document as a MusicXML document.
     *
     * @param xml the XML document.
     */
    public
    MusicXML(
        final XML xml
        ) {
        super(xml.getHandler());
    }

    /**
     * Accepts the specified document as a MusicXML document and validates it using the supplied function.
     *
     * @param document the document.
     * @param validation the validation supplier.
     */
    public
    MusicXML(
        final org.w3c.dom.Document document,
        final Function<org.w3c.dom.Document, Validation> validation
        ) {
        super(Handler.standard(document));
        validation.apply(document);
    }

    /**
     * Accepts the specified document as a MusicXML document and performs basic validation.
     *
     * @param document the document.
     */
    public
    MusicXML(
        final org.w3c.dom.Document document
        ) {
        this(document, Validation::basic);
    }

    /**
     * Creates an empty MusicXML document using the basic implementation of {@link Handler}.
     */
    public
    MusicXML() {
        super(DocumentHandler.basic());
    }

    /**
     * {@code Analysis} classifies any type of analysis done on a music score.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class Analysis
    {
        /** The analysis type. */
        protected
        Type type;

        /**
         * Creates an analysis with the specified type.
         *
         * @param type the type.
         */
        public
        Analysis(
            final Type type
            ) {
            this.type = type;
        }

        /**
         * Creates an analysis with null type.
         */
        public
        Analysis() {
            this(null);
        }

        /**
         * Repairs the analyzed unit.
         */
        public abstract
        void repair();

        /**
         * Returns the analysis type.
         *
         * @return the analysis type.
         */
        public
        Type getType() {
            return type;
        }

        /**
         * {@code Type} is the factor of actionability for score analysis.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        enum Type
        {
            /** Indicates any arbitrary event in the document. */
            Event,

            /** Indicates that analysis is negligible or can be safely disregarded as a potential risk factor in the process or in causing ambiguity later on.  */
            Negligible,

            /** Indicates that analysis is a problem and may be considered for repair; or otherwise, it can break or cause ambiguity later on. */
            Problem,

            /** Indicates that analysis is repairable and should possibly be looked into, but it is not clear or very likely that it will break or cause ambiguity later on. */
            Repairable,

            /** Indicates that analysis carries technical performance-specific value. */
            Technical;
        }
    }

    /**
     * {@code Handler} represents the default handler for MusicXML documents.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class Handler
    extends DocumentHandler
    {
        private static final
        Cache.Table AnalyticLevel2Elements
        = new Cache.Table(
            Constant.WORK,
            Constant.MOVEMENT_TITLE,
            Constant.MOVEMENT_NUMBER,
            Constant.IDENTIFICATION,
            Constant.PART_LIST
            );

        private static final
        Cache.Table AnalyticMusicElements
        = new Cache.Table(
            Constant.ATTRIBUTES,
            Constant.BACKUP,
            Constant.BARLINE,
            Constant.BOOKMARK,
            Constant.DIRECTION,
            Constant.FIGURED_BASS,
            Constant.FORWARD,
            Constant.GROUPING,
            Constant.HARMONY,
            Constant.LINK,
            Constant.NOTE,
            Constant.SOUND
            );

        private static final
        Cache.Table AnalyticUnacceptedAttributes
        = new Cache.Table(
            Constant.BEZIER_OFFSET,
            Constant.BEZIER_OFFSET2,
            Constant.BEZIER_X,
            Constant.BEZIER_X2,
            Constant.BEZIER_Y,
            Constant.BEZIER_Y2,
            Constant.COLOR,
            Constant.DASH_LENGTH,
            Constant.DEFAULT_X,
            Constant.DEFAULT_Y,
            Constant.FONT_FAMILY,
            Constant.FONT_SIZE,
            Constant.FONT_STYLE,
            Constant.FONT_WEIGHT,
            Constant.HEIGHT,
            Constant.LETTER_SPACING,
            Constant.LINE_HEIGHT,
            Constant.PRINT_DOT,
            Constant.PRINT_LYRIC,
            Constant.PRINT_OBJECT,
            Constant.PRINT_SPACING,
            Constant.RELATIVE_X,
            Constant.RELATIVE_Y,
            Constant.SPACE_LENGTH,
            Constant.WIDTH,
            Constant.XML_SPACE
            );

        private static final
        Restriction Standard
        = new Restriction()
        {};

        /**
         * Accepts a subset of data, does not restrict.
         * <p>
         * If a document is not provided, a new document is created and used by the handler.
         * <p>
         * This implementation accepts the minimum number of elements and attributes that are, or can be, used as performance instructions.
         *
         * @param document the optional document.
         * @return the analytic handler.
         */
        public static final
        Handler analytic(
            final org.w3c.dom.Document document
            ) {
            return new Handler()
            {
                boolean completed;

                final
                Stack<org.w3c.dom.Element> stack = new Stack<>();

                int depth;

                protected
                boolean isAcceptableElement(
                    final int depth,
                    final Stack<org.w3c.dom.Element> stack,
                    final String qName
                    ) {
                    if (depth != stack.size())
                        return false;

                    switch (depth) {
                        // ---------- Level 1
                        // acceptable if element is <score-partwise> or <score-timewise>
                        case 0:
                            return qName.equals(Constant.SCORE_PARTWISE) ||
                                   qName.equals(Constant.SCORE_TIMEWISE);

                        // ---------- Level 2
                        // acceptable if element is <work>, <movement-title>, <movement-number>, <identification> or <part-list>
                        //         or if element is <part> or <measure> depending on the the score type
                        case 1:
                            String parent = stack.peek().getTagName();
                            return AnalyticLevel2Elements.contains(qName) ||

                                   (parent.equals(Constant.SCORE_PARTWISE) &&
                                     (qName.equals(Constant.PART)) ||

                                   (parent.equals(Constant.SCORE_TIMEWISE) &&
                                     qName.equals(Constant.MEASURE)));

                        // ---------- Level 3
                        // acceptable if element is <work-title> or <work-number> and the parent element is <work>
                        //         or if element is <creator> or <rights> and the parent element is <identification>
                        //         or if element is <score-part> and the parent element is <part-list>
                        //         or if element is <measure> and the parent element is <part> or vice versa
                        case 2:
                            parent = stack.peek().getTagName();
                            return (parent.equals(Constant.WORK) &&
                                     (qName.equals(Constant.WORK_TITLE) ||
                                     qName.equals(Constant.WORK_NUMBER))) ||

                                   (parent.equals(Constant.IDENTIFICATION) &&
                                     (qName.equals(Constant.CREATOR) ||
                                     qName.equals(Constant.RIGHTS))) ||

                                   (parent.equals(Constant.PART_LIST) &&
                                     qName.equals(Constant.SCORE_PART)) ||

                                   ((parent.equals(Constant.PART) &&
                                     qName.equals(Constant.MEASURE)) ||

                                   (parent.equals(Constant.MEASURE) &&
                                     qName.equals(Constant.PART)));

                        // ---------- Level 4
                        // acceptable if element is <part-name> or <score-instrument> and the parent element is <score-part>
                        //         or if element is not <print> and the parent element is <measure> or <part>
                        case 3:
                            parent = stack.peek().getTagName();
                            return (parent.equals(Constant.SCORE_PART) &&
                                     (qName.equals(Constant.PART_NAME) ||
                                     qName.equals(Constant.SCORE_INSTRUMENT))) ||

                                   ((parent.equals(Constant.MEASURE) ||
                                   parent.equals(Constant.PART)) &&
                                     AnalyticMusicElements.contains(qName));

                        // ---------- Other levels
                        // acceptable if level is 5 and element is <instrument-name> or <instrument-sound> and the parent element is <score-instrument>
                        //         or if the level-4th ancestor of element is an acceptable music data element
                        default:
                            return (depth == 4 &&
                                     stack.peek().getTagName().equals(Constant.SCORE_INSTRUMENT) &&
                                       (qName.equals(Constant.INSTRUMENT_NAME) ||
                                       qName.equals(Constant.INSTRUMENT_SOUND))) ||

                                   AnalyticMusicElements.contains(stack.get(3).getTagName());
                    }
                }

                protected
                boolean isAcceptableAttribute(
                    final int depth,
                    final Stack<org.w3c.dom.Element> stack,
                    final String qName,
                    final String aName,
                    final Attributes attributes,
                    final int n
                    ) {
                    switch (depth) {
                        // ---------- Level 2
                        // acceptable if element is <part> and attribute name is 'id'
                        //            or element is <measure> and attribute name is 'number'
                        case 1:
                            return (qName.equals(Constant.PART) &&
                                     aName.equals(Constant.ID)) ||

                                   (qName.equals(Constant.MEASURE) &&
                                     aName.equals(Constant.NUMBER));

                        // ---------- Level 3
                        // acceptable if element is <creator> and attribute name is 'type'
                        //            or element is <score-part> and attribute name is 'id'
                        case 2:
                            return (qName.equals(Constant.CREATOR) &&
                                     aName.equals(Constant.TYPE)) ||

                                   (qName.equals(Constant.SCORE_PART) &&
                                     aName.equals(Constant.ID)) ||

                                   (qName.equals(Constant.MEASURE) &&
                                     aName.equals(Constant.NUMBER)) ||

                                   (qName.equals(Constant.PART) &&
                                     aName.equals(Constant.ID));

                        // ---------- Level 4
                        // acceptable if element is <score-instrument> and attribute name is 'id'
                        //            or element is <sound> and attribute name is 'tempo'
                        case 3:
                            return (qName.equals(Constant.SCORE_INSTRUMENT) &&
                                     aName.equals(Constant.ID)) ||

                                   (qName.equals(Constant.SOUND) &&
                                     aName.equals(Constant.TEMPO));

                        // ---------- Other levels
                        // filter out the unacceptable attributes
                        default:
                            return ! AnalyticUnacceptedAttributes.contains(aName);
                    }
                }

                @Override
                public void characters(final char[] ch, final int start, final int length) throws SAXException {
                    if (completed || stack.empty() || stack.size() != depth)
                        return;

                    final String parent = stack.peek().getTagName();

                    // If characters are for an acceptable elements at...
                    if ((
                       // ---------- Level 3
                       (depth == 2 &&
                         (parent.equals(Constant.MOVEMENT_TITLE) ||
                         parent.equals(Constant.MOVEMENT_NUMBER))) ||

                       // ---------- Level 4
                       (depth == 3 &&
                         (parent.equals(Constant.WORK_TITLE) ||
                         parent.equals(Constant.WORK_NUMBER) ||
                         parent.equals(Constant.CREATOR) ||
                         parent.equals(Constant.RIGHTS))) ||

                       // ---------- Level 5
                       (depth == 4 &&
                         parent.equals(Constant.PART_NAME)) ||

                       // ---------- Level 6
                       (depth == 5 &&
                         (parent.equals(Constant.INSTRUMENT_NAME) ||
                         parent.equals(Constant.INSTRUMENT_SOUND))) ||

                       // ---------- Level 6 or higher
                       (depth >= 5 &&
                         (stack.get(1).getTagName().equals(Constant.PART) ||
                         stack.get(1).getTagName().equals(Constant.MEASURE))))) {

                        // Append the characters to the element at the top of the stack
                        String s = "";
                        for (int i = start; i < start + length; i++)
                            s += ch[i];
                        stack.peek().appendChild(document.createTextNode(s));
                    }
                }

                @Override
                public void endDocument() {
                    completed = true;
                }

                @Override
                public void endElement(final String uri, final String localName, final String qName) throws SAXException {
                    if (completed)
                        return;

                    // Finalize and close the element in the stack
                    if (stack.size() == depth && qName.equals(stack.peek().getTagName())) {
                        final org.w3c.dom.Element e = stack.pop();

                        if (stack.empty()) {
                            document.appendChild(e);
                            endDocument();
                        }
                        else
                            stack.peek().appendChild(e);
                    }

                    depth--;
                }

                @Override
                public boolean isClosed() {
                    return completed;
                }

                @Override
                public InputSource resolveEntity(final String publicId, final String systemId) throws IOException, SAXException {
                    // To avoid DTD validation
                    return new InputSource(new ByteArrayInputStream(new byte[] {}));
                }

                @Override
                public void startDocument() throws SAXException {
                    depth = 0;
                }

                @Override
                public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
                    if (completed)
                        return;

                    // If the element is acceptable...
                    if (isAcceptableElement(depth, stack, qName)) {

                        // Create a placeholder element
                        final org.w3c.dom.Element e = document.createElement(qName);

                        // Add the acceptable attributes to the element
                        for (int i = 0; i < attributes.getLength(); i++) {
                            final String aName = attributes.getQName(i);
                            if (isAcceptableAttribute(depth, stack, qName, aName, attributes, i))
                                e.setAttribute(aName, attributes.getValue(i));
                        }

                        // Push the element to the stack
                        stack.push(e);
                    }

                    depth++;
                }
            };
        }

        /**
         * Applies custom restriction.
         * <p>
         * If a document is not provided, a new document is created and used by the handler.
         *
         * @param restriction the restriction.
         * @param document the optional document.
         * @return the restrictive handler.
         */
        public static final
        Handler restrictive(
            final Restriction restriction,
            final org.w3c.dom.Document document
            ) {
            return new Handler()
            {
                boolean completed;

                final
                Stack<Element> stack = new Stack<Element>();

                @Override
                public boolean isClosed() {
                    return completed;
                }
            };
        }

        /**
         * Accepts valid data only.
         * <p>
         * If a document is not provided, a new document is created and used by the handler.
         *
         * @param document the optional document.
         * @return the restrictive handler.
         */
        public static final
        Handler standard(
            final org.w3c.dom.Document document
            ) {
            return restrictive(Standard, document);
        }

        /**
         * {@code Analytic} is an implementation of a document handler that accepts all standard MusicXML element types and filters out the cosmetic attributes.
         * <p>
         * This class implementation is in progress.
         *
         * @see Standard
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        protected static
        class Analytic
        extends Standard
        {}

        /**
         * {@code Restriction} represents MusicXML filters that apply restrictions to the document elements and optionally provide validations.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        protected static abstract
        class Restriction
        extends Filter
        {
            /**
             * Creates a new MusicXML restriction.
             */
            protected
            Restriction() {
                super();
            }
        }

        /**
         * {@code Standard} is an implementation of a document handler that accepts all standard MusicXML element types.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        protected static
        class Standard
        extends DocumentHandler.Standard
        {}
    }

    /**
     * {@code Validation} is a special meta-data representing the result of analyzing a music score for performance.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class Validation
    extends Analysis
    implements Supplier<Analysis>
    {
        /** The document. */
        protected final
        org.w3c.dom.Document document;

        /** The object map for analyses. */
        protected
        LinkedList<? extends Analysis> analyses;

        /**
         * Creates a MusicXML score validation for the specified document.
         *
         * @param document the document.
         */
        public
        Validation(
            final org.w3c.dom.Document document
            ) {
            this.document = document;
        }

        /**
         * Passes non-empty documents.
         *
         * @param document the document.
         *
         * @return true if document is not empty.
         */
        public static
        Validation basic(
            final org.w3c.dom.Document document
            ) {
            return new Validation(document)
            {
                @Override
                public Analysis get() { return null; }

                @Override
                public boolean isPassed() {
                    return document != null && document.hasChildNodes();
                }

                @Override
                public void repair() {}
            };
        }

        /**
         * Passes any document.
         *
         * @param document the document.
         * @return true.
         */
        public static
        Validation none(
            final org.w3c.dom.Document document
            ) {
            return new Validation(document)
            {
                @Override
                public Analysis get() { return null; }

                @Override
                public boolean isPassed() {
                    return true;
                }

                @Override
                public void repair() {}
            };
        }

        /**
         * Returns true if the validation is passed or, in other words, the score is valid, and false otherwise.
         *
         * @return true if the score is valid, and false otherwise.
         */
        public abstract
        boolean isPassed();
    }

    /**
     * {@code Constant} holds all standard MusicXML elements, attribute names, and keywords.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static final
    class Constant
    {
        public static final String A = "A";
        public static final String ABBREVIATED = "abbreviated";
        public static final String ABOVE = "above";
        public static final String ACCEL = "accel";
        public static final String ACCELERATE = "accelerate";
        public static final String ACCENT = "accent";
        public static final String ACCIDENTAL = "accidental";
        public static final String ACCIDENTAL_MARK = "accidental-mark";
        public static final String ACCIDENTAL_TEXT = "accidental-text";
        public static final String ACCORD = "accord";
        public static final String ACCORDION_HIGH = "accordion-high";
        public static final String ACCORDION_LOW = "accordion-low";
        public static final String ACCORDION_MIDDLE = "accordion-middle";
        public static final String ACCORDION_REGISTRATION = "accordion-registration";
        public static final String ACTUAL = "actual";
        public static final String ACTUAL_NOTES = "actual-notes";
        public static final String ADDITIONAL = "additional";
        public static final String ADJACENT = "adjacent";
        public static final String AEOLIAN = "aeolian";
        public static final String AFTER_BARLINE = "after-barline";
        public static final String AGOGO = "agogo";
        public static final String ALMGLOCKEN = "almglocken";
        public static final String ALTERNATE = "alternate";
        public static final String ANGLED = "angled";
        public static final String ANTICLOCKWISE = "anticlockwise";
        public static final String ANVIL = "anvil";
        public static final String ANY = "any";
        public static final String APPEARANCE = "appearance";
        public static final String APPROACH = "approach";
        public static final String ARPEGGIATE = "arpeggiate";
        public static final String ARROW = "arrow";
        public static final String ARROW__DOWN = "arrow down";
        public static final String ARROW__UP = "arrow up";
        public static final String ARROW_DIRECTION = "arrow-direction";
        public static final String ARROW_DOWN = "arrow-down";
        public static final String ARROW_STYLE = "arrow-style";
        public static final String ARROW_UP = "arrow-up";
        public static final String ARROWHEAD = "arrowhead";
        public static final String ARTICULATIONS = "articulations";
        public static final String ARTIFICIAL = "artificial";
        public static final String ATTACK = "attack";
        public static final String ATTRIBUTE = "attribute";
        public static final String ATTRIBUTES = "attributes";
        public static final String AUGMENTED = "augmented";
        public static final String AUGMENTED_SEVENTH = "augmented-seventh";
        public static final String AUTO__HORN = "auto horn";
        public static final String B = "B";
        public static final String BACK__SLASHED = "back slashed";
        public static final String BACK_SLASH = "back-slash";
        public static final String BACKUP = "backup";
        public static final String BACKWARD = "backward";
        public static final String BACKWARD__HOOK = "backward hook";
        public static final String BARLINE = "barline";
        public static final String BARRE = "barre";
        public static final String BAR_STYLE = "bar-style";
        public static final String BAMBOO__SCRAPER = "bamboo scraper";
        public static final String BASE_PITCH = "base-pitch";
        public static final String BASELINE = "baseline";
        public static final String BASS = "bass";
        public static final String BASS__DRUM = "bass drum";
        public static final String BASS__DRUM__ON__SIDE = "bass drum on side";
        public static final String BASS_ALTER = "bass-alter";
        public static final String BASS_STEP = "bass-step";
        public static final String BEAM = "beam";
        public static final String BEATER = "beater";
        public static final String BEATS = "beats";
        public static final String BEAT_REPEAT = "beat-repeat";
        public static final String BEAT_TYPE = "beat-type";
        public static final String BEAT_UNIT = "beat-unit";
        public static final String BEAT_UNIT_DOT = "beat-unit-dot";
        public static final String BEAT_UNIT_TIED = "beat-unit-tied";
        public static final String BEFORE_BARLINE = "before-barline";
        public static final String BEGIN = "begin";
        public static final String BELL = "bell";
        public static final String BELL__PLATE = "bell plate";
        public static final String BELL__TREE = "bell tree";
        public static final String BELLTREE = "belltree";
        public static final String BELOW = "below";
        public static final String BEND = "bend";
        public static final String BEND_ALTER = "bend-alter";
        public static final String BEZIER_OFFSET = "bezier-offset";
        public static final String BEZIER_OFFSET2 = "bezier-offset2";
        public static final String BEZIER_X = "bezier-x";
        public static final String BEZIER_X2 = "bezier-x2";
        public static final String BEZIER_Y = "bezier-y";
        public static final String BEZIER_Y2 = "bezier-y2";
        public static final String BIRD__WHISTLE = "bird whistle";
        public static final String BLANK_PAGE = "blank-page";
        public static final String BOARD__CLAPPER = "board clapper";
        public static final String BONGOS = "bongos";
        public static final String BOOKMARK = "bookmark";
        public static final String BOTH = "both";
        public static final String BOTTOM = "bottom";
        public static final String BOTTOM_MARGIN = "bottom-margin";
        public static final String BOTTOM_STAFF = "bottom-staff";
        public static final String BOW = "bow";
        public static final String BRACE = "brace";
        public static final String BRACKET = "bracket";
        public static final String BRACKET_DEGREES = "bracket-degrees";
        public static final String BRAKE__DRUM = "brake drum";
        public static final String BRASS_BEND = "brass-bend";
        public static final String BREATH_MARK = "breath-mark";
        public static final String BREVE = "breve";
        public static final String BUCKET = "bucket";
        public static final String C = "C";
        public static final String C_CLEF = "c-clef";
        public static final String CABASA = "cabasa";
        public static final String CAESURA = "caesura";
        public static final String CANCEL = "cancel";
        public static final String CANNON = "cannon";
        public static final String CAPO = "capo";
        public static final String CASTANETS = "castanets";
        public static final String CASTANETS__WITH__HANDLE = "castanets with handle";
        public static final String CAUTIONARY = "cautionary";
        public static final String CELESTA = "celesta";
        public static final String CENCERRO = "cencerro";
        public static final String CENTER = "center";
        public static final String CHAIN__RATTLE = "chain rattle";
        public static final String CHANGE = "change";
        public static final String CHIME__HAMMER = "chime hammer";
        public static final String CHIMES = "chimes";
        public static final String CHINESE__CYMBAL = "Chinese cymbal";
        public static final String CHINESE__TOMTOM = "Chinese tomtom";
        public static final String CHORD = "chord";
        public static final String CHROMATIC = "chromatic";
        public static final String CIRCLE = "circle";
        public static final String CIRCLE__DOT = "circle dot";
        public static final String CIRCLE_X = "circle-x";
        public static final String CIRCLED = "circled";
        public static final String CIRCULAR_ARROW = "circular-arrow";
        public static final String CLAVES = "claves";
        public static final String CLEF = "clef";
        public static final String CLEF_OCTAVE_CHANGE = "clef-octave-change";
        public static final String CLOCKWISE = "clockwise";
        public static final String CLUSTER = "cluster";
        public static final String CODA = "coda";
        public static final String COIN = "coin";
        public static final String COLOR = "color";
        public static final String CONGA__DRUM = "conga drum";
        public static final String COMBINED = "combined";
        public static final String COMMA = "comma";
        public static final String COMMON = "common";
        public static final String CONTINUE = "continue";
        public static final String COWBELL = "cowbell";
        public static final String CRASH__CYMBALS = "crash cymbals";
        public static final String CREATOR = "creator";
        public static final String CREDIT = "credit";
        public static final String CREDIT_IMAGE = "credit-image";
        public static final String CREDIT_SYMBOL = "credit-symbol";
        public static final String CREDIT_TYPE = "credit-type";
        public static final String CREDIT_WORDS = "credit-words";
        public static final String CRESCENDO = "crescendo";
        public static final String CROSS = "cross";
        public static final String CROTALE = "crotale";
        public static final String CUE = "cue";
        public static final String CUICA = "cuica";
        public static final String CURLEW = "curlew";
        public static final String CURVED = "curved";
        public static final String CUP = "cup";
        public static final String CUT = "cut";
        public static final String CYMBAL__BELL = "cymbal bell";
        public static final String CYMBAL__EDGE = "cymbal edge";
        public static final String CYMBAL__TONGS = "cymbal tongs";
        public static final String D = "D";
        public static final String DACAPO = "dacapo";
        public static final String DALSEGNO = "dalsegno";
        public static final String DAMP = "damp";
        public static final String DAMP_ALL = "damp-all";
        public static final String DAMPER_PEDAL = "damper-pedal";
        public static final String DASH_LENGTH = "dash-length";
        public static final String DASHED = "dashed";
        public static final String DASHED_CIRCLE = "dashed-circle";
        public static final String DASHES = "dashes";
        public static final String DECAGON = "decagon";
        public static final String DEFAULT = "default";
        public static final String DEFAULT_X = "default-x";
        public static final String DEFAULT_Y = "default-y";
        public static final String DEFAULTS = "defaults";
        public static final String DEGREE = "degree";
        public static final String DEGREE_ALTER = "degree-alter";
        public static final String DEGREE_TYPE = "degree-type";
        public static final String DEGREE_VALUE = "degree-value";
        public static final String DELAYED_TURN = "delayed-turn";
        public static final String DELAYED_INVERTED_TURN = "delayed-inverted-turn";
        public static final String DEPARTURE = "departure";
        public static final String DETACHED_LEGATO = "detached-legato";
        public static final String DIAGONAL = "diagonal";
        public static final String DIAMOND = "diamond";
        public static final String DIATONIC = "diatonic";
        public static final String DIMINISHED = "diminished";
        public static final String DIMINISHED_SEVENTH = "diminished-seventh";
        public static final String DIMINUENDO = "diminuendo";
        public static final String DIR = "dir";
        public static final String DIRECTION = "direction";
        public static final String DIRECTION_TYPE = "direction-type";
        public static final String DIRECTIVE = "directive";
        public static final String DISCONTINUE = "discontinue";
        public static final String DISPLAY_OCTAVE = "display-octave";
        public static final String DISPLAY_STEP = "display-step";
        public static final String DISPLAY_TEXT = "display-text";
        public static final String DISTANCE = "distance";
        public static final String DIVISIONS = "divisions";
        public static final String DO = "do";
        public static final String DOIT = "doit";
        public static final String DOMED__GONG = "domed gong";
        public static final String DOMINANT = "dominant";
        public static final String DOMINANT_11TH = "dominant-11th";
        public static final String DOMINANT_13TH = "dominant-13th";
        public static final String DOMINANT_NINTH = "dominant-ninth";
        public static final String DORIAN = "dorian";
        public static final String DOT = "dot";
        public static final String DOTTED = "dotted";
        public static final String DOTTED_NOTE = "dotted-note";
        public static final String DOUBLE = "double";
        public static final String DOUBLE__BASS__DRUM = "double bass drum";
        public static final String DOUBLE_ANGLED = "double-angled";
        public static final String DOUBLE_CURVED = "double-curved";
        public static final String DOUBLE_DOT = "double-dot";
        public static final String DOUBLE_SHARP = "double-sharp";
        public static final String DOUBLE_SHARP_DOWN = "double-sharp-down";
        public static final String DOUBLE_SHARP_UP = "double-sharp-up";
        public static final String DOUBLE_SLASH_FLAT = "double-slash-flat";
        public static final String DOUBLE_SQUARE = "double-square";
        public static final String DOUBLE_STRAIGHT = "double-straight";
        public static final String DOUBLE_TONGUE = "double-tongue";
        public static final String DOWN = "down";
        public static final String DOWN_BOW = "down-bow";
        public static final String DRUM__STICK = "drum stick";
        public static final String DUCK__CALL = "duck call";
        public static final String DURATION = "duration";
        public static final String DYNAMICS = "dynamics";
        public static final String E = "E";
        public static final String ECHO = "echo";
        public static final String EDITORIAL = "editorial";
        public static final String EFFECT = "effect";
        public static final String EIGHTH = "eighth";
        public static final String EQUALS = "equals";
        public static final String ELEMENT = "element";
        public static final String ELEVATION = "elevation";
        public static final String ELISION = "elision";
        public static final String EMBED = "embed";
        public static final String ENCLOSURE = "enclosure";
        public static final String ENCODER = "encoder";
        public static final String ENCODING = "encoding";
        public static final String ENCODING_DATE = "encoding-date";
        public static final String ENCODING_DESCRIPTION = "encoding-description";
        public static final String END = "end";
        public static final String END_DYNAMICS = "end-dynamics";
        public static final String END_LENGTH = "end-length";
        public static final String END_LINE = "end-line";
        public static final String END_PARAGRAPH = "end-paragraph";
        public static final String ENDING = "ending";
        public static final String ENSEMBLE = "ensemble";
        public static final String EVEN = "even";
        public static final String EXCEPT_VOICE = "except-voice";
        public static final String EXPLICIT = "explicit";
        public static final String EXTEND = "extend";
        public static final String EYEGLASSES = "eyeglasses";
        public static final String F = "F";
        public static final String F_CLEF = "f-clef";
        public static final String FA = "fa";
        public static final String FA__UP = "fa up";
        public static final String FALLOFF = "falloff";
        public static final String FAN = "fan";
        public static final String FEATURE = "feature";
        public static final String FERMATA = "fermata";
        public static final String FIFTHS = "fifths";
        public static final String FIGURE = "figure";
        public static final String FIGURE_NUMBER = "figure-number";
        public static final String FIGURED_BASS = "figured-bass";
        public static final String FILLED = "filled";
        public static final String FINE = "fine";
        public static final String FINGER = "finger";
        public static final String FINGER__CYMBALS = "finger cymbals";
        public static final String FINGERING = "fingering";
        public static final String FINGERNAIL = "fingernail";
        public static final String FINGERNAILS = "fingernails";
        public static final String FIRST_BEAT = "first-beat";
        public static final String FIRST_FRET = "first-fret";
        public static final String FIST = "fist";
        public static final String FIVE_HUNDRED_TWELVTH = "512th";
        public static final String FLAT = "flat";
        public static final String FLAT_1 = "flat-1";
        public static final String FLAT_2 = "flat-2";
        public static final String FLAT_3 = "flat-3";
        public static final String FLAT_4 = "flat-4";
        public static final String FLAT_DOWN = "flat-down";
        public static final String FLAT_FLAT = "flat-flat";
        public static final String FLAT_FLAT_DOWN = "flat-flat-down";
        public static final String FLAT_FLAT_UP = "flat-flat-up";
        public static final String FLAT_UP = "flat-up";
        public static final String FLEXATONE = "flexatone";
        public static final String FLIP = "flip";
        public static final String FONT_FAMILY = "font-family";
        public static final String FONT_SIZE = "font-size";
        public static final String FONT_STYLE = "font-style";
        public static final String FONT_WEIGHT = "font-weight";
        public static final String FOOTBALL__RATTLE = "football rattle";
        public static final String FOOTNOTE = "footnote";
        public static final String FORWARD = "forward";
        public static final String FORWARD__HOOK = "forward hook";
        public static final String FORWARD_REPEAT = "forward-repeat";
        public static final String FRAME = "frame";
        public static final String FRAME_FRETS = "frame-frets";
        public static final String FRAME_NOTE = "frame-note";
        public static final String FRAME_STRINGS = "frame-strings";
        public static final String FRENCH = "French";
        public static final String FRET = "fret";
        public static final String FULL = "full";
        public static final String FUNCTION = "function";
        public static final String G = "G";
        public static final String G_CLEF_OTTAVA_BASSA = "g-clef-ottava-bassa";
        public static final String GERMAN = "German";
        public static final String GLASS = "glass";
        public static final String GLASS__HARMONICA = "glass harmonica";
        public static final String GLASS__HARP = "glass harp";
        public static final String GLISSANDO = "glissando";
        public static final String GLOCKENSPIEL = "glockenspiel";
        public static final String GLYPH = "glyph";
        public static final String GOBLET__DRUM = "goblet drum";
        public static final String GOLPE = "golpe";
        public static final String GONG = "gong";
        public static final String GRACE = "grace";
        public static final String GRACE_CUE = "grace-cue";
        public static final String GROUP = "group";
        public static final String GROUP_ABBREVIATION = "group-abbreviation";
        public static final String GROUP_ABBREVIATION_DISPLAY = "group-abbreviation-display";
        public static final String GROUP_BARLINE = "group-barline";
        public static final String GROUP_NAME = "group-name";
        public static final String GROUP_NAME_DISPLAY = "group-name-display";
        public static final String GROUP_SYMBOL = "group-symbol";
        public static final String GROUP_TIME = "group-time";
        public static final String GROUPING = "grouping";
        public static final String GUIRO = "guiro";
        public static final String GUIRO__SCRAPER = "guiro scraper";
        public static final String GUM = "gum";
        public static final String GUN__SHOT = "gun shot";
        public static final String GYRO = "gyro";
        public static final String HALF = "half";
        public static final String HALF_CURVE = "half-curve";
        public static final String HALF_DIMINISHED = "half-diminished";
        public static final String HALF_MUTED = "half-muted";
        public static final String HALIGN = "halign";
        public static final String HAMMER = "hammer";
        public static final String HAMMER_ON = "hammer-on";
        public static final String HAND = "hand";
        public static final String HAND__MARTELLATO = "hand martellato";
        public static final String HANDBELL = "handbell";
        public static final String HARD = "hard";
        public static final String HARMON_CLOSED = "harmon-closed";
        public static final String HARMON_MUTE = "harmon-mute";
        public static final String HARMON_NO_STEM = "harmon-no-stem";
        public static final String HARMON_STEM = "harmon-stem";
        public static final String HARMONIC = "harmonic";
        public static final String HARMONY = "harmony";
        public static final String HARP_PEDALS = "harp-pedals";
        public static final String HAUPTSTIMME = "Hauptstimme";
        public static final String HAT = "hat";
        public static final String HAYDN = "haydn";
        public static final String HEAVY = "heavy";
        public static final String HEAVY__BARLINE = "heavy barline";
        public static final String HEAVY_HEAVY = "heavy-heavy";
        public static final String HEAVY_LIGHT = "heavy-light";
        public static final String HEEL = "heel";
        public static final String HEIGHT = "height";
        public static final String HEPTAGON = "heptagon";
        public static final String HEXAGON = "hexagon";
        public static final String HIGH = "high";
        public static final String HIGH_HAT = "high-hat";
        public static final String HIGH_HAT__CYMBALS = "high-hat cymbals";
        public static final String HOLE = "hole";
        public static final String HOLE_CLOSED = "hole-closed";
        public static final String HOLE_SHAPE = "hole-shape";
        public static final String HOLE_TYPE = "hole-type";
        public static final String HOLLOW = "hollow";
        public static final String HORIZONTAL = "horizontal";
        public static final String HUMMING = "humming";
        public static final String HUNDRED_TWENTY_EIGHTH = "128th";
        public static final String HYPHON = "hyphon";
        public static final String ID = "id";
        public static final String IDENTIFICATION = "identification";
        public static final String IMAGE = "image";
        public static final String IMPLICIT = "implicit";
        public static final String IMPLIED = "implied";
        public static final String INDO_AMERICAN__TOMTOM = "Indo-American tomtom";
        public static final String INTERCHANGEABLE = "interchangeable";
        public static final String INSTRUMENT = "instrument";
        public static final String INSTRUMENT_ABBREVIATION = "instrument-abbreviation";
        public static final String INSTRUMENT_NAME = "instrument-name";
        public static final String INSTRUMENT_SOUND = "instrument-sound";
        public static final String INSTRUMENTS = "instruments";
        public static final String INVERSION = "inversion";
        public static final String INVERTED = "inverted";
        public static final String INVERTED__TRIANGLE = "inverted triangle";
        public static final String INVERTED_MORDENT = "inverted-mordent";
        public static final String INVERTED_TURN = "inverted-turn";
        public static final String INVERTED_VERTICAL_TURN = "inverted-vertical-turn";
        public static final String IONIAN = "ionian";
        public static final String IPA = "ipa";
        public static final String ITALIAN = "Italian";
        public static final String JAPANESE__TOMTOM = "Japanese tomtom";
        public static final String JAW__HARP = "jaw harp";
        public static final String JAZZ__STICK = "jazz stick";
        public static final String JIANPU = "jianpu";
        public static final String JINGLE__BELLS = "jingle bells";
        public static final String JUSTIFY = "justify";
        public static final String KIND = "kind";
        public static final String KLAXON__HORN = "klaxon horn";
        public static final String KNITTING__NEEDLE = "knitting needle";
        public static final String KORON = "koron";
        public static final String LA = "la";
        public static final String LARGE = "large";
        public static final String LAST_BEAT = "last-beat";
        public static final String LAUGHING = "laughing";
        public static final String LEFT = "left";
        public static final String LEFT__RIGHT = "left right";
        public static final String LEFT__TRIANGLE = "left triangle";
        public static final String LEFT_DIVIDER = "left-divider";
        public static final String LEFT_MARGIN = "left-margin";
        public static final String LEGER = "leger";
        public static final String LET_RING = "let-ring";
        public static final String LETTER_SPACING = "letter-spacing";
        public static final String LETTERS = "letters";
        public static final String LEVEL = "level";
        public static final String LIGHT__BARLINE = "light barline";
        public static final String LIGHT_LIGHT = "light-light";
        public static final String LIGHT_HEAVY = "light-heavy";
        public static final String LINE = "line";
        public static final String LINE_END = "line-end";
        public static final String LINE_HEIGHT = "line-height";
        public static final String LINE_LENGTH = "line-length";
        public static final String LINE_SHAPE = "line-shape";
        public static final String LINE_THROUGH = "line-through";
        public static final String LINE_TYPE = "line-type";
        public static final String LINE_WIDTH = "line-width";
        public static final String LINK = "link";
        public static final String LIONS__ROAR = "lions roar";
        public static final String LITHOPHONE = "lithophone";
        public static final String LOCATION = "location";
        public static final String LOCRIAN = "locrian";
        public static final String LOG__DRUM = "log drum";
        public static final String LONG = "long";
        public static final String LOTUS__FLUTE = "lotus flute";
        public static final String LOW = "low";
        public static final String LRO = "lro";
        public static final String LTR = "ltr";
        public static final String LYRIC = "lyric";
        public static final String LYRIC_FONT = "lyric-font";
        public static final String LYRIC_LANGUAGE = "lyric-language";
        public static final String LYDIAN = "lydian";
        public static final String KEY = "key";
        public static final String KEY_ACCIDENTAL = "key-accidental";
        public static final String KEY_ALTER = "key-alter";
        public static final String KEY_OCTAVE = "key-octave";
        public static final String KEY_STEP = "key-step";
        public static final String MAIN = "main";
        public static final String MAJOR = "major";
        public static final String MAJOR_11TH = "major-11th";
        public static final String MAJOR_13TH = "major-13th";
        public static final String MAJOR_MINOR = "major-minor";
        public static final String MAJOR_NINTH = "major-ninth";
        public static final String MAJOR_SEVENTH = "major-seventh";
        public static final String MAJOR_SIXTH = "major-sixth";
        public static final String MAKE_TIME = "make-time";
        public static final String MALLET = "mallet";
        public static final String MALLET__LIFT = "mallet lift";
        public static final String MALLET__TABLE = "mallet table";
        public static final String MARACA = "maraca";
        public static final String MARACAS = "maracas";
        public static final String MARIMBA = "marimba";
        public static final String MARTELLATO = "martellato";
        public static final String MARTELLATO__LIFT = "martellato lift";
        public static final String MAXIMA = "maxima";
        public static final String MEASURE = "measure";
        public static final String MEASURE_DISTANCE = "measure-distance";
        public static final String MEASURE_LAYOUT = "measure-layout";
        public static final String MEASURE_NUMBERING = "measure-numbering";
        public static final String MEASURE_REPEAT = "measure-repeat";
        public static final String MEASURE_REST = "measure-rest";
        public static final String MEASURE_STYLE = "measure-style";
        public static final String MEDIUM = "medium";
        public static final String MEDIUM_HIGH = "medium-high";
        public static final String MEDIUM_LOW = "medium-low";
        public static final String MEGAPHONE = "megaphone";
        public static final String MEMBER_OF = "member-of";
        public static final String MEMBRANE = "membrane";
        public static final String MENSURSTRICH = "Mensurstrich";
        public static final String METAL = "metal";
        public static final String METAL__HAMMER = "metal hammer";
        public static final String METRONOME = "metronome";
        public static final String METRONOME_ARROWS = "metronome-arrows";
        public static final String METRONOME_BEAM = "metronome-beam";
        public static final String METRONOME_DOT = "metronome-dot";
        public static final String METRONOME_NOTE = "metronome-note";
        public static final String METRONOME_RELATION = "metronome-relation";
        public static final String METRONOME_TIED = "metronome-tied";
        public static final String METRONOME_TUPLET = "metronome-tuplet";
        public static final String METRONOME_TYPE = "metronome-type";
        public static final String MI = "mi";
        public static final String MIDDLE = "middle";
        public static final String MIDI_BANK = "midi-bank";
        public static final String MIDI_CHANNEL = "midi-channel";
        public static final String MIDI_DEVICE = "midi-device";
        public static final String MIDI_INSTRUMENT = "midi-instrument";
        public static final String MIDI_PROGRAM = "midi-program";
        public static final String MIDI_NAME = "midi-name";
        public static final String MIDI_UNPITCHED = "midi-unpitched";
        public static final String MILITARY__DRUM = "military drum";
        public static final String MILLIMETERS = "millimeters";
        public static final String MINOR = "minor";
        public static final String MINOR_11TH = "minor-11th";
        public static final String MINOR_13TH = "minor-13th";
        public static final String MINOR_NINTH = "minor-ninth";
        public static final String MINOR_SEVENTH = "minor-seventh";
        public static final String MINOR_SIXTH = "minor-sixth";
        public static final String MISCELLANEOUS = "miscellaneous";
        public static final String MISCELLANEOUS_FIELD = "miscellaneous-field";
        public static final String MIXOLYDIAN = "mixolydian";
        public static final String MODE = "mode";
        public static final String MORDENT = "mordent";
        public static final String MOVEMENT_NUMBER = "movement-number";
        public static final String MOVEMENT_TITLE = "movement-title";
        public static final String MULTIPLE_REST = "multiple-rest";
        public static final String MUSIC_FONT = "music-font";
        public static final String MUSICAL__SAW = "musical saw";
        public static final String MUTE = "mute";
        public static final String MUTED__MARTELLATO = "muted martellato";
        public static final String NAME = "name";
        public static final String NATURAL = "natural";
        public static final String NATURAL_DOWN = "natural-down";
        public static final String NATURAL_FLAT = "natural-flat";
        public static final String NATURAL_SHARP = "natural-sharp";
        public static final String NATURAL_UP = "natural-up";
        public static final String NEAPOLITAN = "Neapolitan";
        public static final String NEBENSTIMME = "Nebenstimme";
        public static final String NEW = "new";
        public static final String NEW_PAGE = "new-page";
        public static final String NEW_SYSTEM = "new-system";
        public static final String NIENTE = "niente";
        public static final String NO = "no";
        public static final String NON_ARPEGGIATE = "non-arpeggiate";
        public static final String NON_CONTROLLING = "non-controlling";
        public static final String NONAGON = "nonagon";
        public static final String NONE = "none";
        public static final String NORMAL = "normal";
        public static final String NORMAL_DOT = "normal-dot";
        public static final String NORMAL_NOTES = "normal-notes";
        public static final String NORMAL_TYPE = "normal-type";
        public static final String NORTHEAST = "northeast";
        public static final String NORTHEAST__SOUTHWEST = "northeast southwest";
        public static final String NORTHWEST = "northwest";
        public static final String NORTHWEST__SOUTHEAST = "northwest southeast";
        public static final String NOTATIONS = "notations";
        public static final String NOTE = "note";
        public static final String NOTE_SIZE = "note-size";
        public static final String NOTEHEAD = "notehead";
        public static final String NOTEHEAD_TEXT = "notehead-text";
        public static final String NUMBER = "number";
        public static final String NUMBERS = "numbers";
        public static final String OCTAGON = "octagon";
        public static final String OCTAVE_SHIFT_CONTINUE_15 = "octave-shift-continue-15";
        public static final String OCTAVE_SHIFT_CONTINUE_22 = "octave-shift-continue-22";
        public static final String OCTAVE_SHIFT_CONTINUE_8 = "octave-shift-continue-8";
        public static final String OCTAVE_SHIFT_DOWN_15 = "octave-shift-down-15";
        public static final String OCTAVE_SHIFT_DOWN_22 = "octave-shift-down-22";
        public static final String OCTAVE_SHIFT_DOWN_8 = "octave-shift-down-8";
        public static final String OCTAVE_SHIFT_UP_15 = "octave-shift-up-15";
        public static final String OCTAVE_SHIFT_UP_22 = "octave-shift-up-22";
        public static final String OCTAVE_SHIFT_UP_8 = "octave-shift-up-8";
        public static final String OCTAVE__SHIFT = "octave shift";
        public static final String OCTAVE_CHANGE = "octave-change";
        public static final String OCTAVE_SHIFT = "octave-shift";
        public static final String ODD = "odd";
        public static final String OFF = "off";
        public static final String OFFSET = "offset";
        public static final String ON = "on";
        public static final String ONLOAD = "onLoad";
        public static final String ONREQUEST = "onRequest";
        public static final String OPEN = "open";
        public static final String OPEN_STRING = "open-string";
        public static final String OPUS = "opus";
        public static final String OPUS_LINK = "opus-link";
        public static final String ORIENTATION = "orientation";
        public static final String ORNAMENTS = "ornaments";
        public static final String OSSIA = "ossia";
        public static final String OTHER = "other";
        public static final String OTHER_APPEARANCE = "other-appearance";
        public static final String OTHER_ARTICULATION = "other-articulation";
        public static final String OTHER_DIRECTION = "other-direction";
        public static final String OTHER_DYNAMICS = "other-dynamics";
        public static final String OTHER_NOTATION = "other-notation";
        public static final String OTHER_ORNAMENT = "other-ornament";
        public static final String OTHER_PERCUSSION = "other-percussion";
        public static final String OTHER_PLAY = "other-play";
        public static final String OTHER_TECHNICAL = "other-technical";
        public static final String OVAL = "oval";
        public static final String OVER = "over";
        public static final String OVERLINE = "overline";
        public static final String PAGE = "page";
        public static final String PAGE_HEIGHT = "page-height";
        public static final String PAGE_LAYOUT = "page-layout";
        public static final String PAGE_MARGINS = "page-margins";
        public static final String PAGE_NUMBER = "page-number";
        public static final String PAGE_WIDTH = "page-width";
        public static final String PALM = "palm";
        public static final String PAN = "pan";
        public static final String PAIRED = "paired";
        public static final String PARENTHESES = "parentheses";
        public static final String PARENTHESES_DEGREES = "parentheses-degrees";
        public static final String PART = "part";
        public static final String PART_ABBREVIATION = "part-abbreviation";
        public static final String PART_ABBREVIATION_DISPLAY = "part-abbreviation-display";
        public static final String PART_LIST = "part-list";
        public static final String PART_GROUP = "part-group";
        public static final String PART_NAME = "part-name";
        public static final String PART_NAME_DISPLAY = "part-name-display";
        public static final String PART_SYMBOL = "part-symbol";
        public static final String PEDAL = "pedal";
        public static final String PEDAL_ALTER = "pedal-alter";
        public static final String PEDAL_STEP = "pedal-step";
        public static final String PEDAL_TUNING = "pedal-tuning";
        public static final String PENTAGON = "pentagon";
        public static final String PER_MINUTE = "per-minute";
        public static final String PERCUSSION = "percussion";
        public static final String PERCUSSION_CLEF = "percussion-clef";
        public static final String PHRYGIAN = "phrygian";
        public static final String PITCH = "pitch";
        public static final String PITCHED = "pitched";
        public static final String PIZZICATO = "pizzicato";
        public static final String PLAIN = "plain";
        public static final String PLAY = "play";
        public static final String PLACEMENT = "placement";
        public static final String PLOP = "plop";
        public static final String PLUCK = "pluck";
        public static final String PLUCK__LIFT = "pluck lift";
        public static final String PLUNGER = "plunger";
        public static final String PLUS = "plus";
        public static final String POLICE__WHISTLE = "police whistle";
        public static final String PORT = "port";
        public static final String POSITION = "position";
        public static final String POWER = "power";
        public static final String PRACTICE = "practice";
        public static final String PREFIX = "prefix";
        public static final String PRESERVE = "preserve";
        public static final String PRE_BEND = "pre-bend";
        public static final String PRIMARY = "primary";
        public static final String PRINCIPAL_VOICE = "principal-voice";
        public static final String PRINT = "print";
        public static final String PRINT_DOT = "print-dot";
        public static final String PRINT_FRAME = "print-frame";
        public static final String PRINT_LEGER = "print-leger";
        public static final String PRINT_LYRIC = "print-lyric";
        public static final String PRINT_OBJECT = "print-object";
        public static final String PRINT_SPACING = "print-spacing";
        public static final String PULL_OFF = "pull-off";
        public static final String QUARTER = "quarter";
        public static final String QUARTER_FLAT = "quarter-flat";
        public static final String QUARTER_REST = "quarter-rest";
        public static final String QUARTER_SHARP = "quarter-sharp";
        public static final String QUIJADA = "quijada";
        public static final String RAINSTICK = "rainstick";
        public static final String RATCHET = "ratchet";
        public static final String RE = "re";
        public static final String RECO_RECO = "reco-reco";
        public static final String RECTANGLE = "rectangle";
        public static final String REFERENCE = "reference";
        public static final String REGULAR = "regular";
        public static final String REHEARSAL = "rehearsal";
        public static final String RELATION = "relation";
        public static final String RELATIVE_X = "relative-x";
        public static final String RELATIVE_Y = "relative-y";
        public static final String RELEASE = "release";
        public static final String REPEAT = "repeat";
        public static final String REPEATER = "repeater";
        public static final String REPLACE = "replace";
        public static final String REST = "rest";
        public static final String RIGHT = "right";
        public static final String RIGHT_DIVIDER = "right-divider";
        public static final String RIGHT_MARGIN = "right-margin";
        public static final String RIGHTS = "rights";
        public static final String RIM = "rim";
        public static final String RIT = "rit";
        public static final String RLO = "rlo";
        public static final String ROOT = "root";
        public static final String ROOT_ALTER = "root-alter";
        public static final String ROOT_STEP = "root-step";
        public static final String ROTATION = "rotation";
        public static final String RTL = "rtl";
        public static final String SALZEDO = "salzedo";
        public static final String SANDPAPER__BLOCKS = "sandpaper blocks";
        public static final String SCALING = "scaling";
        public static final String SCHLEIFER = "schleifer";
        public static final String SCOOP = "scoop";
        public static final String SCORDATURA = "scordatura";
        public static final String SCORE_INSTRUMENT = "score-instrument";
        public static final String SCORE_PART = "score-part";
        public static final String SCORE_PARTWISE = "score-partwise";
        public static final String SCORE_TIMEWISE = "score-timewise";
        public static final String SECOND_BEAT = "second-beat";
        public static final String SEGNO = "segno";
        public static final String SEMI_PITCHED = "semi-pitched";
        public static final String SENZA_MISURA = "senza-misura";
        public static final String SEPARATOR = "separator";
        public static final String SHADED = "shaded";
        public static final String SHAKE = "shake";
        public static final String SHARP = "sharp";
        public static final String SHARP_1 = "sharp-1";
        public static final String SHARP_2 = "sharp-2";
        public static final String SHARP_3 = "sharp-3";
        public static final String SHARP_5 = "sharp-5";
        public static final String SHARP_DOWN = "sharp-down";
        public static final String SHARP_SHARP = "sharp-sharp";
        public static final String SHARP_UP = "sharp-up";
        public static final String SHELL__BELLS = "shell bells";
        public static final String SHORT = "short";
        public static final String SHOW_FRETS = "show-frets";
        public static final String SHOW_NUMBER = "show-number";
        public static final String SHOW_TYPE = "show-type";
        public static final String SIGN = "sign";
        public static final String SIMPLE = "simple";
        public static final String SINGLE = "single";
        public static final String SINGLE_NUMBER = "single-number";
        public static final String SIREN = "siren";
        public static final String SISTRUM = "sistrum";
        public static final String SIXTEENTH = "16th";
        public static final String SIXTY_FOURTH = "64th";
        public static final String SIZE = "size";
        public static final String SIZZLE__CYMBAL = "sizzle cymbal";
        public static final String SLASH = "slash";
        public static final String SLASH_DOT = "slash-dot";
        public static final String SLASH_FLAT = "slash-flat";
        public static final String SLASH_QUARTER_SHARP = "slash-quarter-sharp";
        public static final String SLASH_SHARP = "slash-sharp";
        public static final String SLASH_TYPE = "slash-type";
        public static final String SLASHED = "slashed";
        public static final String SLASHES = "slashes";
        public static final String SLEIGH__BELLS = "sleigh bells";
        public static final String SLIDE = "slide";
        public static final String SLIDE__BRUSH__ON__GONG = "slide brush on gong";
        public static final String SLIDE__WHISTLE = "slide whistle";
        public static final String SLIT__DRUM = "slit drum";
        public static final String SLUR = "slur";
        public static final String SLUR__MIDDLE = "slur middle";
        public static final String SLUR__TIP = "slur tip";
        public static final String SMEAR = "smear";
        public static final String SMUFL = "smufl";
        public static final String SNAP_PIZZICATO = "snap-pizzicato";
        public static final String SNARE__DRUM = "snare drum";
        public static final String SNARE__DRUM__SNARES__OFF = "snare drum snares off";
        public static final String SNARE__STICK = "snare stick";
        public static final String SO = "so";
        public static final String SOFT = "soft";
        public static final String SOFT_ACCENT = "soft-accent";
        public static final String SOFT_PEDAL = "soft-pedal";
        public static final String SOFTWARE = "software";
        public static final String SOLID = "solid";
        public static final String SOLO = "solo";
        public static final String SOLOTONE = "solotone";
        public static final String SORI = "sori";
        public static final String SOSTENUTO = "sostenuto";
        public static final String SOSTENUTO_PEDAL = "sostenuto-pedal";
        public static final String SOUND = "sound";
        public static final String SOUNDING_PITCH = "sounding-pitch";
        public static final String SOUNDS = "sounds";
        public static final String SOURCE = "source";
        public static final String SOUTHEAST = "southeast";
        public static final String SOUTHWEST = "southwest";
        public static final String SPACE = "space";
        public static final String SPACE_LENGTH = "space-length";
        public static final String SPICCATO = "spiccato";
        public static final String SPREAD = "spread";
        public static final String SPOON__MALLET = "spoon mallet";
        public static final String SQUARE = "square";
        public static final String STACCATISSIMO = "staccatissimo";
        public static final String STACCATO = "staccato";
        public static final String STACK_DEGREES = "stack-degrees";
        public static final String STAFF = "staff";
        public static final String STAFF_DETAILS = "staff-details";
        public static final String STAFF_DISTANCE = "staff-distance";
        public static final String STAFF_DIVIDE = "staff-divide";
        public static final String STAFF_LAYOUT = "staff-layout";
        public static final String STAFF_LINES = "staff-lines";
        public static final String STAFF_TUNING = "staff-tuning";
        public static final String STAFF_TYPE = "staff-type";
        public static final String STAFF_SIZE = "staff-size";
        public static final String STAFF_SPACING = "staff-spacing";
        public static final String START = "start";
        public static final String START_NOTE = "start-note";
        public static final String STAVES = "staves";
        public static final String STEAL_TIME_FOLLOWING = "steal-time-following";
        public static final String STEAL_TIME_PREVIOUS = "steal-time-previous";
        public static final String STEEL__DRUMS = "steel drums";
        public static final String STEM = "stem";
        public static final String STICK = "stick";
        public static final String STICK_LOCATION = "stick-location";
        public static final String STICK_MATERIAL = "stick-material";
        public static final String STICK_TYPE = "stick-type";
        public static final String STOP = "stop";
        public static final String STOP_HAND = "stop-hand";
        public static final String STOP_MUTE = "stop-mute";
        public static final String STOPPED = "stopped";
        public static final String STRAIGHT = "straight";
        public static final String STRESS = "stress";
        public static final String STRING = "string";
        public static final String STRING_MUTE = "string-mute";
        public static final String STRONG_ACCENT = "strong-accent";
        public static final String SUBSTITUTION = "substitution";
        public static final String SUFFIX = "suffix";
        public static final String SUPERBALL = "superball";
        public static final String SUPPORTS = "supports";
        public static final String SUSPENDED__CYMBAL = "suspended cymbal";
        public static final String SUSPENDED_FOURTH = "suspended-fourth";
        public static final String SUSPENDED_SECOND = "suspended-second";
        public static final String SWING = "swing";
        public static final String SYLLABIC = "syllabic";
        public static final String SYMBOL = "symbol";
        public static final String SYSTEM_DISTANCE = "system-distance";
        public static final String SYSTEM_DIVIDERS = "system-dividers";
        public static final String SYSTEM_LAYOUT = "system-layout";
        public static final String SYSTEM_MARGINS = "system-margins";
        public static final String TAB = "TAB";
        public static final String TABLA = "tabla";
        public static final String TAM__TAM = "tam tam";
        public static final String TAM__TAM__WITH__BEATER = "tam tam with beater";
        public static final String TAMBOURINE = "tambourine";
        public static final String TAP = "tap";
        public static final String TECHNICAL = "technical";
        public static final String TEMPLE__BLOCK = "temple block";
        public static final String TEMPO = "tempo";
        public static final String TENOR__DRUM = "tenor drum";
        public static final String TENTHS = "tenths";
        public static final String TENUTO = "tenuto";
        public static final String TEXT = "text";
        public static final String TEXT_X = "text-x";
        public static final String TEXT_Y = "text-y";
        public static final String THICK = "thick";
        public static final String THIRTY_SECOND = "32nd";
        public static final String THOUSAND_TWENTY_FOURTH = "1024th";
        public static final String THREE_QUARTERS_FLAT = "three-quarters-flat";
        public static final String THREE_QUARTERS_SHARP = "three-quarters-sharp";
        public static final String THUMB_POSITION = "thumb-position";
        public static final String THUNDER__SHEET = "thunder sheet";
        public static final String TI = "ti";
        public static final String TICK = "tick";
        public static final String TIE = "tie";
        public static final String TIE__MIDDLE = "tie middle";
        public static final String TIE__TIP = "tie tip";
        public static final String TIED = "tied";
        public static final String TIMBALES = "timbales";
        public static final String TIME = "time";
        public static final String TIMES = "times";
        public static final String TIME_MODIFICATION = "time-modification";
        public static final String TIME_ONLY = "time-only";
        public static final String TIME_RELATION = "time-relation";
        public static final String TIME_SYMBOL = "time-symbol";
        public static final String TIMPANI = "timpani";
        public static final String TIP = "tip";
        public static final String TITLE = "title";
        public static final String TOCODA = "tocoda";
        public static final String TOE = "toe";
        public static final String TOMTOM = "tomtom";
        public static final String TOP = "top";
        public static final String TOP_MARGIN = "top-margin";
        public static final String TOP_STAFF = "top-staff";
        public static final String TOP_SYSTEM_DISTANCE = "top-system-distance";
        public static final String TOUCHING_PITCH = "touching-pitch";
        public static final String TRANSPOSE = "transpose";
        public static final String TREMOLO = "tremolo";
        public static final String TRIANGLE = "triangle";
        public static final String TRIANGLE__BEATER = "triangle beater";
        public static final String TRIANGLE__BEATER__PLAIN = "triangle beater plain";
        public static final String TRILL_MARK = "trill-mark";
        public static final String TRILL_STEP = "trill-step";
        public static final String TRIPLE_FLAT = "triple-flat";
        public static final String TRIPLE_SHARP = "triple-sharp";
        public static final String TRIPLE_TONGUE = "triple-tongue";
        public static final String TRISTAN = "Tristan";
        public static final String TUBAPHONE = "tubaphone";
        public static final String TUBULAR__CHIMES = "tubular chimes";
        public static final String TUNING_ALTER = "tuning-alter";
        public static final String TUNING_OCTAVE = "tuning-octave";
        public static final String TUNING_STEP = "tuning-step";
        public static final String TUPLET = "tuplet";
        public static final String TUPLET__BRACKET = "tuplet bracket";
        public static final String TUPLET_ACTUAL = "tuplet-actual";
        public static final String TUPLET_DOT = "tuplet-dot";
        public static final String TUPLET_NORMAL = "tuplet-normal";
        public static final String TUPLET_NUMBER = "tuplet-number";
        public static final String TUPLET_TYPE = "tuplet-type";
        public static final String TURN = "turn";
        public static final String TWO_HUNDRED_FIFTY_SIXTH = "256th";
        public static final String TWO_NOTE_TURN = "two-note-turn";
        public static final String TYPE = "type";
        public static final String UNDER = "under";
        public static final String UNDERLINE = "underline";
        public static final String UNISON = "unison";
        public static final String UNMEASURED = "unmeasured";
        public static final String UNPITCHED = "unpitched";
        public static final String UNPLAYED = "unplayed";
        public static final String UNSTRESS = "unstress";
        public static final String UPRIGHT = "upright";
        public static final String UP = "up";
        public static final String UP__DOWN = "up down";
        public static final String UP_BOW = "up-bow";
        public static final String UP_DOWN = "up-down";
        public static final String UPBOW = "upbow";
        public static final String UPPER = "upper";
        public static final String USE_DOTS = "use-dots";
        public static final String USE_STEMS = "use-stems";
        public static final String USE_SYMBOLS = "use-symbols";
        public static final String VALIGN = "valign";
        public static final String VALIGN_IMAGE = "valign-image";
        public static final String VALUE = "value";
        public static final String VERSION = "version";
        public static final String VERTICAL = "vertical";
        public static final String VERTICAL_TURN = "vertical-turn";
        public static final String VERY_LOW = "very-low";
        public static final String VIBRAPHONE = "vibraphone";
        public static final String VIBRASLAP = "vibraslap";
        public static final String VIETNAMESE__HAT = "Vietnamese hat";
        public static final String VIRTUAL_INSTRUMENT = "virtual-instrument";
        public static final String VIRTUAL_LIBRARY = "virtual-library";
        public static final String VIRTUAL_NAME = "virtual-name";
        public static final String VOICE = "voice";
        public static final String VOLUME = "volume";
        public static final String WAVY = "wavy";
        public static final String WAVY_LINE = "wavy-line";
        public static final String WEDGE = "wedge";
        public static final String WHIP = "whip";
        public static final String WHOLE = "whole";
        public static final String WIDTH = "width";
        public static final String WIND__CHIMES = "wind chimes";
        public static final String WIND__MACHINE = "wind machine";
        public static final String WIND__WHISTLE = "wind whistle";
        public static final String WINGED = "winged";
        public static final String WIRE__BRUSH = "wire brush";
        public static final String WITH_BAR = "with-bar";
        public static final String WOOD = "wood";
        public static final String WOOD__BLOCK = "wood block";
        public static final String WORD_FONT = "word-font";
        public static final String WORDS = "words";
        public static final String WORK = "work";
        public static final String WORK_NUMBER = "work-number";
        public static final String WORK_TITLE = "work-title";
        public static final String WOUND = "wound";
        public static final String X = "x";
        public static final String XLINK_ACTUATE = "xlink:actuate";
        public static final String XLINK_HREF = "xlink:href";
        public static final String XLINK_ROLE = "xlink:role";
        public static final String XLINK_SHOW = "xlink:show";
        public static final String XLINK_TITLE = "xlink:title";
        public static final String XLINK_TYPE = "xlink:type";
        public static final String XML_LANG = "xml:lang";
        public static final String XML_SPACE = "xml:space";
        public static final String XMLNS_XLINK = "xmlns:xlink";
        public static final String XYLOPHONE = "xylophone";
        public static final String YARN = "yarn";
        public static final String YES = "yes";

        /**
         * {@code Dynamics} holds all valid MusicXML dynamics tag names.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Dynamics
        {
            static final String F = "f";
            static final String FF = "ff";
            static final String FP = "fp";
            static final String FZ = "fz";
            static final String FFF = "fff";
            static final String FFFF = "ffff";
            static final String FFFFF = "fffff";
            static final String FFFFFF = "ffffff";
            static final String MF = "mf";
            static final String MP = "mp";
            static final String N = "n";
            static final String P = "p";
            static final String PF = "pf";
            static final String PP = "pp";
            static final String PPP = "ppp";
            static final String PPPP = "pppp";
            static final String PPPPP = "ppppp";
            static final String PPPPPP = "pppppp";
            static final String RF = "rf";
            static final String RFZ = "rfz";
            static final String SF = "sf";
            static final String SFP = "sfp";
            static final String SFPP = "sfpp";
            static final String SFZ = "sfz";
            static final String SFFZ = "sffz";
            static final String SFZP = "sfzp";
        }

        /**
         * {@code Sounds} holds all standard MusicXML sounds.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Sounds
        {
            static final String BRASS_ALPHORN = "brass.alphorn";
            static final String BRASS_ALTO_HORN = "brass.alto-horn";
            static final String BRASS_BARITONE_HORN = "brass.baritone-horn";
            static final String BRASS_BUGLE = "brass.bugle";
            static final String BRASS_BUGLE_ALTO = "brass.bugle.alto";
            static final String BRASS_BUGLE_BARITONE = "brass.bugle.baritone";
            static final String BRASS_BUGLE_CONTRABASS = "brass.bugle.contrabass";
            static final String BRASS_BUGLE_EUPHONIUM_BUGLE = "brass.bugle.euphonium-bugle";
            static final String BRASS_BUGLE_MELLOPHONE_BUGLE = "brass.bugle.mellophone-bugle";
            static final String BRASS_BUGLE_SOPRANO = "brass.bugle.soprano";
            static final String BRASS_CIMBASSO = "brass.cimbasso";
            static final String BRASS_CONCH_SHELL = "brass.conch-shell";
            static final String BRASS_CORNET = "brass.cornet";
            static final String BRASS_CORNET_SOPRANO = "brass.cornet.soprano";
            static final String BRASS_CORNETT = "brass.cornett";
            static final String BRASS_CORNETT_TENOR = "brass.cornett.tenor";
            static final String BRASS_CORNETTINO = "brass.cornettino";
            static final String BRASS_DIDGERIDOO = "brass.didgeridoo";
            static final String BRASS_EUPHONIUM = "brass.euphonium";
            static final String BRASS_FISCORN = "brass.fiscorn";
            static final String BRASS_FLUGELHORN = "brass.flugelhorn";
            static final String BRASS_FRENCH_HORN = "brass.french-horn";
            static final String BRASS_GROUP = "brass.group";
            static final String BRASS_GROUP_SYNTH = "brass.group.synth";
            static final String BRASS_HELICON = "brass.helicon";
            static final String BRASS_HORAGAI = "brass.horagai";
            static final String BRASS_KUHLOHORN = "brass.kuhlohorn";
            static final String BRASS_MELLOPHONE = "brass.mellophone";
            static final String BRASS_NATURAL_HORN = "brass.natural-horn";
            static final String BRASS_OPHICLEIDE = "brass.ophicleide";
            static final String BRASS_POSTHORN = "brass.posthorn";
            static final String BRASS_RAG_DUNG = "brass.rag-dung";
            static final String BRASS_SACKBUTT = "brass.sackbutt";
            static final String BRASS_SACKBUTT_ALTO = "brass.sackbutt.alto";
            static final String BRASS_SACKBUTT_BASS = "brass.sackbutt.bass";
            static final String BRASS_SACKBUTT_TENOR = "brass.sackbutt.tenor";
            static final String BRASS_SAXHORN = "brass.saxhorn";
            static final String BRASS_SERPENT = "brass.serpent";
            static final String BRASS_SHOFAR = "brass.shofar";
            static final String BRASS_SOUSAPHONE = "brass.sousaphone";
            static final String BRASS_TROMBONE = "brass.trombone";
            static final String BRASS_TROMBONE_ALTO = "brass.trombone.alto";
            static final String BRASS_TROMBONE_BASS = "brass.trombone.bass";
            static final String BRASS_TROMBONE_CONTRABASS = "brass.trombone.contrabass";
            static final String BRASS_TROMBONE_TENOR = "brass.trombone.tenor";
            static final String BRASS_TRUMPET = "brass.trumpet";
            static final String BRASS_TRUMPET_BAROQUE = "brass.trumpet.baroque";
            static final String BRASS_TRUMPET_BASS = "brass.trumpet.bass";
            static final String BRASS_TRUMPET_BFLAT = "brass.trumpet.bflat";
            static final String BRASS_TRUMPET_C = "brass.trumpet.c";
            static final String BRASS_TRUMPET_D = "brass.trumpet.d";
            static final String BRASS_TRUMPET_PICCOLO = "brass.trumpet.piccolo";
            static final String BRASS_TRUMPET_POCKET = "brass.trumpet.pocket";
            static final String BRASS_TRUMPET_SLIDE = "brass.trumpet.slide";
            static final String BRASS_TRUMPET_TENOR = "brass.trumpet.tenor";
            static final String BRASS_TUBA = "brass.tuba";
            static final String BRASS_TUBA_BASS = "brass.tuba.bass";
            static final String BRASS_TUBA_SUBCONTRABASS = "brass.tuba.subcontrabass";
            static final String BRASS_VIENNA_HORN = "brass.vienna-horn";
            static final String BRASS_VUVUZELA = "brass.vuvuzela";
            static final String BRASS_WAGNER_TUBA = "brass.wagner-tuba";
            static final String DRUM_APENTEMMA = "drum.apentemma";
            static final String DRUM_ASHIKO = "drum.ashiko";
            static final String DRUM_ATABAQUE = "drum.atabaque";
            static final String DRUM_ATOKE = "drum.atoke";
            static final String DRUM_ATSIMEVU = "drum.atsimevu";
            static final String DRUM_AXATSE = "drum.axatse";
            static final String DRUM_BASS_DRUM = "drum.bass-drum";
            static final String DRUM_BATA = "drum.bata";
            static final String DRUM_BATA_ITOTELE = "drum.bata.itotele";
            static final String DRUM_BATA_IYA = "drum.bata.iya";
            static final String DRUM_BATA_OKONKOLO = "drum.bata.okonkolo";
            static final String DRUM_BENDIR = "drum.bendir";
            static final String DRUM_BODHRAN = "drum.bodhran";
            static final String DRUM_BOMBO = "drum.bombo";
            static final String DRUM_BONGO = "drum.bongo";
            static final String DRUM_BOUGARABOU = "drum.bougarabou";
            static final String DRUM_BUFFALO_DRUM = "drum.buffalo-drum";
            static final String DRUM_CAJON = "drum.cajon";
            static final String DRUM_CHENDA = "drum.chenda";
            static final String DRUM_CHU_DAIKO = "drum.chu-daiko";
            static final String DRUM_CONGA = "drum.conga";
            static final String DRUM_CUICA = "drum.cuica";
            static final String DRUM_DABAKAN = "drum.dabakan";
            static final String DRUM_DAFF = "drum.daff";
            static final String DRUM_DAFLI = "drum.dafli";
            static final String DRUM_DAIBYOSI = "drum.daibyosi";
            static final String DRUM_DAMROO = "drum.damroo";
            static final String DRUM_DARABUKA = "drum.darabuka";
            static final String DRUM_DEF = "drum.def";
            static final String DRUM_DHOL = "drum.dhol";
            static final String DRUM_DHOLAK = "drum.dholak";
            static final String DRUM_DJEMBE = "drum.djembe";
            static final String DRUM_DOIRA = "drum.doira";
            static final String DRUM_DONDO = "drum.dondo";
            static final String DRUM_DOUN_DOUN_BA = "drum.doun-doun-ba";
            static final String DRUM_DUFF = "drum.duff";
            static final String DRUM_DUMBEK = "drum.dumbek";
            static final String DRUM_FONTOMFROM = "drum.fontomfrom";
            static final String DRUM_FRAME_DRUM = "drum.frame-drum";
            static final String DRUM_FRAME_DRUM_ARABIAN = "drum.frame-drum.arabian";
            static final String DRUM_GEDUK = "drum.geduk";
            static final String DRUM_GHATAM = "drum.ghatam";
            static final String DRUM_GOME = "drum.gome";
            static final String DRUM_GROUP = "drum.group";
            static final String DRUM_GROUP_CHINESE = "drum.group.chinese";
            static final String DRUM_GROUP_EWE = "drum.group.ewe";
            static final String DRUM_GROUP_INDIAN = "drum.group.indian";
            static final String DRUM_GROUP_SET = "drum.group.set";
            static final String DRUM_HAND_DRUM = "drum.hand-drum";
            static final String DRUM_HIRA_DAIKO = "drum.hira-daiko";
            static final String DRUM_IBO = "drum.ibo";
            static final String DRUM_IGIHUMURIZO = "drum.igihumurizo";
            static final String DRUM_INYAHURA = "drum.inyahura";
            static final String DRUM_ISHAKWE = "drum.ishakwe";
            static final String DRUM_JANG_GU = "drum.jang-gu";
            static final String DRUM_KAGAN = "drum.kagan";
            static final String DRUM_KAKKO = "drum.kakko";
            static final String DRUM_KANJIRA = "drum.kanjira";
            static final String DRUM_KENDHANG = "drum.kendhang";
            static final String DRUM_KENDHANG_AGENG = "drum.kendhang.ageng";
            static final String DRUM_KENDHANG_CIBLON = "drum.kendhang.ciblon";
            static final String DRUM_KENKENI = "drum.kenkeni";
            static final String DRUM_KHOL = "drum.khol";
            static final String DRUM_KICK_DRUM = "drum.kick-drum";
            static final String DRUM_KIDI = "drum.kidi";
            static final String DRUM_KO_DAIKO = "drum.ko-daiko";
            static final String DRUM_KPANLOGO = "drum.kpanlogo";
            static final String DRUM_KUDUM = "drum.kudum";
            static final String DRUM_LAMBEG = "drum.lambeg";
            static final String DRUM_LION_DRUM = "drum.lion-drum";
            static final String DRUM_LOG_DRUM = "drum.log-drum";
            static final String DRUM_LOG_DRUM_AFRICAN = "drum.log-drum.african";
            static final String DRUM_LOG_DRUM_NATIVE = "drum.log-drum.native";
            static final String DRUM_LOG_DRUM_NIGERIAN = "drum.log-drum.nigerian";
            static final String DRUM_MADAL = "drum.madal";
            static final String DRUM_MADDALE = "drum.maddale";
            static final String DRUM_MRIDANGAM = "drum.mridangam";
            static final String DRUM_NAAL = "drum.naal";
            static final String DRUM_NAGADO_DAIKO = "drum.nagado-daiko";
            static final String DRUM_NAGARA = "drum.nagara";
            static final String DRUM_NAQARA = "drum.naqara";
            static final String DRUM_O_DAIKO = "drum.o-daiko";
            static final String DRUM_OKAWA = "drum.okawa";
            static final String DRUM_OKEDO_DAIKO = "drum.okedo-daiko";
            static final String DRUM_PAHU_HULA = "drum.pahu-hula";
            static final String DRUM_PAKHAWAJ = "drum.pakhawaj";
            static final String DRUM_PANDEIRO = "drum.pandeiro";
            static final String DRUM_PANDERO = "drum.pandero";
            static final String DRUM_POWWOW = "drum.powwow";
            static final String DRUM_PUEBLO = "drum.pueblo";
            static final String DRUM_REPINIQUE = "drum.repinique";
            static final String DRUM_RIQ = "drum.riq";
            static final String DRUM_ROTOTOM = "drum.rototom";
            static final String DRUM_SABAR = "drum.sabar";
            static final String DRUM_SAKARA = "drum.sakara";
            static final String DRUM_SAMPHO = "drum.sampho";
            static final String DRUM_SANGBAN = "drum.sangban";
            static final String DRUM_SHIME_DAIKO = "drum.shime-daiko";
            static final String DRUM_SLIT_DRUM = "drum.slit-drum";
            static final String DRUM_SLIT_DRUM_KRIN = "drum.slit-drum.krin";
            static final String DRUM_SNARE_DRUM = "drum.snare-drum";
            static final String DRUM_SNARE_DRUM_ELECTRIC = "drum.snare-drum.electric";
            static final String DRUM_SOGO = "drum.sogo";
            static final String DRUM_SURDO = "drum.surdo";
            static final String DRUM_TABLA = "drum.tabla";
            static final String DRUM_TABLA_BAYAN = "drum.tabla.bayan";
            static final String DRUM_TABLA_DAYAN = "drum.tabla.dayan";
            static final String DRUM_TAIKO = "drum.taiko";
            static final String DRUM_TALKING = "drum.talking";
            static final String DRUM_TAMA = "drum.tama";
            static final String DRUM_TAMBORITA = "drum.tamborita";
            static final String DRUM_TAMBOURINE = "drum.tambourine";
            static final String DRUM_TAMTE = "drum.tamte";
            static final String DRUM_TANGKU = "drum.tangku";
            static final String DRUM_TAN_TAN = "drum.tan-tan";
            static final String DRUM_TAPHON = "drum.taphon";
            static final String DRUM_TAR = "drum.tar";
            static final String DRUM_TASHA = "drum.tasha";
            static final String DRUM_TENOR_DRUM = "drum.tenor-drum";
            static final String DRUM_TEPONAXTLI = "drum.teponaxtli";
            static final String DRUM_THAVIL = "drum.thavil";
            static final String DRUM_THE_BOX = "drum.the-box";
            static final String DRUM_TIMBALE = "drum.timbale";
            static final String DRUM_TIMPANI = "drum.timpani";
            static final String DRUM_TINAJA = "drum.tinaja";
            static final String DRUM_TOERE = "drum.toere";
            static final String DRUM_TOMBAK = "drum.tombak";
            static final String DRUM_TOM_TOM = "drum.tom-tom";
            static final String DRUM_TOM_TOM_SYNTH = "drum.tom-tom.synth";
            static final String DRUM_TSUZUMI = "drum.tsuzumi";
            static final String DRUM_TUMBAK = "drum.tumbak";
            static final String DRUM_UCHIWA_DAIKO = "drum.uchiwa-daiko";
            static final String DRUM_UDAKU = "drum.udaku";
            static final String DRUM_UDU = "drum.udu";
            static final String DRUM_ZARB = "drum.zarb";
            static final String EFFECT_AEOLIAN_HARP = "effect.aeolian-harp";
            static final String EFFECT_AIR_HORN = "effect.air-horn";
            static final String EFFECT_APPLAUSE = "effect.applause";
            static final String EFFECT_BASS_STRING_SLAP = "effect.bass-string-slap";
            static final String EFFECT_BIRD = "effect.bird";
            static final String EFFECT_BIRD_NIGHTINGALE = "effect.bird.nightingale";
            static final String EFFECT_BIRD_TWEET = "effect.bird.tweet";
            static final String EFFECT_BREATH = "effect.breath";
            static final String EFFECT_BUBBLE = "effect.bubble";
            static final String EFFECT_BULLROARER = "effect.bullroarer";
            static final String EFFECT_BURST = "effect.burst";
            static final String EFFECT_CAR = "effect.car";
            static final String EFFECT_CAR_CRASH = "effect.car.crash";
            static final String EFFECT_CAR_ENGINE = "effect.car.engine";
            static final String EFFECT_CAR_PASS = "effect.car.pass";
            static final String EFFECT_CAR_STOP = "effect.car.stop";
            static final String EFFECT_CRICKETS = "effect.crickets";
            static final String EFFECT_DOG = "effect.dog";
            static final String EFFECT_DOOR_CREAK = "effect.door.creak";
            static final String EFFECT_DOOR_SLAM = "effect.door.slam";
            static final String EFFECT_EXPLOSION = "effect.explosion";
            static final String EFFECT_FLUTE_KEY_CLICK = "effect.flute-key-click";
            static final String EFFECT_FOOTSTEPS = "effect.footsteps";
            static final String EFFECT_FROGS = "effect.frogs";
            static final String EFFECT_GUITAR_CUTTING = "effect.guitar-cutting";
            static final String EFFECT_GUITAR_FRET = "effect.guitar-fret";
            static final String EFFECT_GUNSHOT = "effect.gunshot";
            static final String EFFECT_HAND_CLAP = "effect.hand-clap";
            static final String EFFECT_HEARTBEAT = "effect.heartbeat";
            static final String EFFECT_HELICOPTER = "effect.helicopter";
            static final String EFFECT_HIGH_Q = "effect.high-q";
            static final String EFFECT_HORSE_GALLOP = "effect.horse-gallop";
            static final String EFFECT_JET_PLANE = "effect.jet-plane";
            static final String EFFECT_LASER_GUN = "effect.laser-gun";
            static final String EFFECT_LAUGH = "effect.laugh";
            static final String EFFECT_LIONS_ROAR = "effect.lions-roar";
            static final String EFFECT_MACHINE_GUN = "effect.machine-gun";
            static final String EFFECT_MARCHING_MACHINE = "effect.marching-machine";
            static final String EFFECT_METRONOME_BELL = "effect.metronome-bell";
            static final String EFFECT_METRONOME_CLICK = "effect.metronome-click";
            static final String EFFECT_PAT = "effect.pat";
            static final String EFFECT_PUNCH = "effect.punch";
            static final String EFFECT_RAIN = "effect.rain";
            static final String EFFECT_SCRATCH = "effect.scratch";
            static final String EFFECT_SCREAM = "effect.scream";
            static final String EFFECT_SEASHORE = "effect.seashore";
            static final String EFFECT_SIREN = "effect.siren";
            static final String EFFECT_SLAP = "effect.slap";
            static final String EFFECT_SNAP = "effect.snap";
            static final String EFFECT_STAMP = "effect.stamp";
            static final String EFFECT_STARSHIP = "effect.starship";
            static final String EFFECT_STREAM = "effect.stream";
            static final String EFFECT_TELEPHONE_RING = "effect.telephone-ring";
            static final String EFFECT_THUNDER = "effect.thunder";
            static final String EFFECT_TRAIN = "effect.train";
            static final String EFFECT_TRASH_CAN = "effect.trash-can";
            static final String EFFECT_WHIP = "effect.whip";
            static final String EFFECT_WHISTLE = "effect.whistle";
            static final String EFFECT_WHISTLE_MOUTH_SIREN = "effect.whistle.mouth-siren";
            static final String EFFECT_WHISTLE_POLICE = "effect.whistle.police";
            static final String EFFECT_WHISTLE_SLIDE = "effect.whistle.slide";
            static final String EFFECT_WHISTLE_TRAIN = "effect.whistle.train";
            static final String EFFECT_WIND = "effect.wind";
            static final String KEYBOARD_ACCORDION = "keyboard.accordion";
            static final String KEYBOARD_BANDONEON = "keyboard.bandoneon";
            static final String KEYBOARD_CELESTA = "keyboard.celesta";
            static final String KEYBOARD_CLAVICHORD = "keyboard.clavichord";
            static final String KEYBOARD_CLAVICHORD_SYNTH = "keyboard.clavichord.synth";
            static final String KEYBOARD_CONCERTINA = "keyboard.concertina";
            static final String KEYBOARD_FORTEPIANO = "keyboard.fortepiano";
            static final String KEYBOARD_HARMONIUM = "keyboard.harmonium";
            static final String KEYBOARD_HARPSICHORD = "keyboard.harpsichord";
            static final String KEYBOARD_ONDES_MARTENOT = "keyboard.ondes-martenot";
            static final String KEYBOARD_ORGAN = "keyboard.organ";
            static final String KEYBOARD_ORGAN_DRAWBAR = "keyboard.organ.drawbar";
            static final String KEYBOARD_ORGAN_PERCUSSIVE = "keyboard.organ.percussive";
            static final String KEYBOARD_ORGAN_PIPE = "keyboard.organ.pipe";
            static final String KEYBOARD_ORGAN_REED = "keyboard.organ.reed";
            static final String KEYBOARD_ORGAN_ROTARY = "keyboard.organ.rotary";
            static final String KEYBOARD_PIANO = "keyboard.piano";
            static final String KEYBOARD_PIANO_ELECTRIC = "keyboard.piano.electric";
            static final String KEYBOARD_PIANO_GRAND = "keyboard.piano.grand";
            static final String KEYBOARD_PIANO_HONKY_TONK = "keyboard.piano.honky-tonk";
            static final String KEYBOARD_PIANO_PREPARED = "keyboard.piano.prepared";
            static final String KEYBOARD_PIANO_TOY = "keyboard.piano.toy";
            static final String KEYBOARD_PIANO_UPRIGHT = "keyboard.piano.upright";
            static final String KEYBOARD_VIRGINAL = "keyboard.virginal";
            static final String METAL_ADODO = "metal.adodo";
            static final String METAL_ANVIL = "metal.anvil";
            static final String METAL_BABENDIL = "metal.babendil";
            static final String METAL_BELLS_AGOGO = "metal.bells.agogo";
            static final String METAL_BELLS_ALMGLOCKEN = "metal.bells.almglocken";
            static final String METAL_BELLS_BELL_PLATE = "metal.bells.bell-plate";
            static final String METAL_BELLS_BELL_TREE = "metal.bells.bell-tree";
            static final String METAL_BELLS_CARILLON = "metal.bells.carillon";
            static final String METAL_BELLS_CHIMES = "metal.bells.chimes";
            static final String METAL_BELLS_CHIMTA = "metal.bells.chimta";
            static final String METAL_BELLS_CHIPPLI = "metal.bells.chippli";
            static final String METAL_BELLS_CHURCH = "metal.bells.church";
            static final String METAL_BELLS_COWBELL = "metal.bells.cowbell";
            static final String METAL_BELLS_DAWURO = "metal.bells.dawuro";
            static final String METAL_BELLS_GANKOKWE = "metal.bells.gankokwe";
            static final String METAL_BELLS_GHUNGROO = "metal.bells.ghungroo";
            static final String METAL_BELLS_HATHELI = "metal.bells.hatheli";
            static final String METAL_BELLS_JINGLE_BELL = "metal.bells.jingle-bell";
            static final String METAL_BELLS_KHARTAL = "metal.bells.khartal";
            static final String METAL_BELLS_MARK_TREE = "metal.bells.mark-tree";
            static final String METAL_BELLS_SISTRUM = "metal.bells.sistrum";
            static final String METAL_BELLS_SLEIGH_BELLS = "metal.bells.sleigh-bells";
            static final String METAL_BELLS_TEMPLE = "metal.bells.temple";
            static final String METAL_BELLS_TIBETAN = "metal.bells.tibetan";
            static final String METAL_BELLS_TINKLEBELL = "metal.bells.tinklebell";
            static final String METAL_BELLS_TRYCHEL = "metal.bells.trychel";
            static final String METAL_BELLS_WIND_CHIMES = "metal.bells.wind-chimes";
            static final String METAL_BELLS_ZILLS = "metal.bells.zills";
            static final String METAL_BERIMBAU = "metal.berimbau";
            static final String METAL_BRAKE_DRUMS = "metal.brake-drums";
            static final String METAL_CROTALES = "metal.crotales";
            static final String METAL_CYMBAL_BO = "metal.cymbal.bo";
            static final String METAL_CYMBAL_CENG_CENG = "metal.cymbal.ceng-ceng";
            static final String METAL_CYMBAL_CHABARA = "metal.cymbal.chabara";
            static final String METAL_CYMBAL_CHINESE = "metal.cymbal.chinese";
            static final String METAL_CYMBAL_CHING = "metal.cymbal.ching";
            static final String METAL_CYMBAL_CLASH = "metal.cymbal.clash";
            static final String METAL_CYMBAL_CRASH = "metal.cymbal.crash";
            static final String METAL_CYMBAL_FINGER = "metal.cymbal.finger";
            static final String METAL_CYMBAL_HAND = "metal.cymbal.hand";
            static final String METAL_CYMBAL_KESI = "metal.cymbal.kesi";
            static final String METAL_CYMBAL_MANJEERA = "metal.cymbal.manjeera";
            static final String METAL_CYMBAL_REVERSE = "metal.cymbal.reverse";
            static final String METAL_CYMBAL_RIDE = "metal.cymbal.ride";
            static final String METAL_CYMBAL_SIZZLE = "metal.cymbal.sizzle";
            static final String METAL_CYMBAL_SPLASH = "metal.cymbal.splash";
            static final String METAL_CYMBAL_SUSPENDED = "metal.cymbal.suspended";
            static final String METAL_CYMBAL_TEBYOSHI = "metal.cymbal.tebyoshi";
            static final String METAL_CYMBAL_TIBETAN = "metal.cymbal.tibetan";
            static final String METAL_CYMBAL_TINGSHA = "metal.cymbal.tingsha";
            static final String METAL_FLEXATONE = "metal.flexatone";
            static final String METAL_GONG = "metal.gong";
            static final String METAL_GONG_AGENG = "metal.gong.ageng";
            static final String METAL_GONG_AGUNG = "metal.gong.agung";
            static final String METAL_GONG_CHANCHIKI = "metal.gong.chanchiki";
            static final String METAL_GONG_CHINESE = "metal.gong.chinese";
            static final String METAL_GONG_GANDINGAN = "metal.gong.gandingan";
            static final String METAL_GONG_KEMPUL = "metal.gong.kempul";
            static final String METAL_GONG_KEMPYANG = "metal.gong.kempyang";
            static final String METAL_GONG_KETUK = "metal.gong.ketuk";
            static final String METAL_GONG_KKWENGGWARI = "metal.gong.kkwenggwari";
            static final String METAL_GONG_LUO = "metal.gong.luo";
            static final String METAL_GONG_SINGING = "metal.gong.singing";
            static final String METAL_GONG_THAI = "metal.gong.thai";
            static final String METAL_GUIRA = "metal.guira";
            static final String METAL_HANG = "metal.hang";
            static final String METAL_HI_HAT = "metal.hi-hat";
            static final String METAL_JAW_HARP = "metal.jaw-harp";
            static final String METAL_KENGONG = "metal.kengong";
            static final String METAL_MURCHANG = "metal.murchang";
            static final String METAL_MUSICAL_SAW = "metal.musical-saw";
            static final String METAL_SINGING_BOWL = "metal.singing-bowl";
            static final String METAL_SPOONS = "metal.spoons";
            static final String METAL_STEEL_DRUMS = "metal.steel-drums";
            static final String METAL_TAMTAM = "metal.tamtam";
            static final String METAL_THUNDERSHEET = "metal.thundersheet";
            static final String METAL_TRIANGLE = "metal.triangle";
            static final String METAL_WASHBOARD = "metal.washboard";
            static final String PITCHED_PERCUSSION_ANGKLUNG = "pitched-percussion.angklung";
            static final String PITCHED_PERCUSSION_BALAFON = "pitched-percussion.balafon";
            static final String PITCHED_PERCUSSION_BELL_LYRE = "pitched-percussion.bell-lyre";
            static final String PITCHED_PERCUSSION_BELLS = "pitched-percussion.bells";
            static final String PITCHED_PERCUSSION_BIANQING = "pitched-percussion.bianqing";
            static final String PITCHED_PERCUSSION_BIANZHONG = "pitched-percussion.bianzhong";
            static final String PITCHED_PERCUSSION_BONANG = "pitched-percussion.bonang";
            static final String PITCHED_PERCUSSION_CIMBALOM = "pitched-percussion.cimbalom";
            static final String PITCHED_PERCUSSION_CRYSTAL_GLASSES = "pitched-percussion.crystal-glasses";
            static final String PITCHED_PERCUSSION_DAN_TAM_THAP_LUC = "pitched-percussion.dan-tam-thap-luc";
            static final String PITCHED_PERCUSSION_FANGXIANG = "pitched-percussion.fangxiang";
            static final String PITCHED_PERCUSSION_GANDINGAN_A_KAYO = "pitched-percussion.gandingan-a-kayo";
            static final String PITCHED_PERCUSSION_GANGSA = "pitched-percussion.gangsa";
            static final String PITCHED_PERCUSSION_GENDER = "pitched-percussion.gender";
            static final String PITCHED_PERCUSSION_GIYING = "pitched-percussion.giying";
            static final String PITCHED_PERCUSSION_GLASS_HARMONICA = "pitched-percussion.glass-harmonica";
            static final String PITCHED_PERCUSSION_GLOCKENSPIEL = "pitched-percussion.glockenspiel";
            static final String PITCHED_PERCUSSION_GLOCKENSPIEL_ALTO = "pitched-percussion.glockenspiel.alto";
            static final String PITCHED_PERCUSSION_GLOCKENSPIEL_SOPRANO = "pitched-percussion.glockenspiel.soprano";
            static final String PITCHED_PERCUSSION_GYIL = "pitched-percussion.gyil";
            static final String PITCHED_PERCUSSION_HAMMER_DULCIMER = "pitched-percussion.hammer-dulcimer";
            static final String PITCHED_PERCUSSION_HANDBELLS = "pitched-percussion.handbells";
            static final String PITCHED_PERCUSSION_KALIMBA = "pitched-percussion.kalimba";
            static final String PITCHED_PERCUSSION_KANTIL = "pitched-percussion.kantil";
            static final String PITCHED_PERCUSSION_KHIM = "pitched-percussion.khim";
            static final String PITCHED_PERCUSSION_KULINTANG = "pitched-percussion.kulintang";
            static final String PITCHED_PERCUSSION_KULINTANG_A_KAYO = "pitched-percussion.kulintang-a-kayo";
            static final String PITCHED_PERCUSSION_KULINTANG_A_TINIOK = "pitched-percussion.kulintang-a-tiniok";
            static final String PITCHED_PERCUSSION_LIKEMBE = "pitched-percussion.likembe";
            static final String PITCHED_PERCUSSION_LUNTANG = "pitched-percussion.luntang";
            static final String PITCHED_PERCUSSION_MARIMBA = "pitched-percussion.marimba";
            static final String PITCHED_PERCUSSION_MARIMBA_BASS = "pitched-percussion.marimba.bass";
            static final String PITCHED_PERCUSSION_MBIRA = "pitched-percussion.mbira";
            static final String PITCHED_PERCUSSION_MBIRA_ARRAY = "pitched-percussion.mbira.array";
            static final String PITCHED_PERCUSSION_METALLOPHONE = "pitched-percussion.metallophone";
            static final String PITCHED_PERCUSSION_METALLOPHONE_ALTO = "pitched-percussion.metallophone.alto";
            static final String PITCHED_PERCUSSION_METALLOPHONE_BASS = "pitched-percussion.metallophone.bass";
            static final String PITCHED_PERCUSSION_METALLOPHONE_SOPRANO = "pitched-percussion.metallophone.soprano";
            static final String PITCHED_PERCUSSION_MUSIC_BOX = "pitched-percussion.music-box";
            static final String PITCHED_PERCUSSION_PELOG_PANERUS = "pitched-percussion.pelog-panerus";
            static final String PITCHED_PERCUSSION_PEMADE = "pitched-percussion.pemade";
            static final String PITCHED_PERCUSSION_PENYACAH = "pitched-percussion.penyacah";
            static final String PITCHED_PERCUSSION_RANAT_EK = "pitched-percussion.ranat.ek";
            static final String PITCHED_PERCUSSION_RANAT_EK_LEK = "pitched-percussion.ranat.ek-lek";
            static final String PITCHED_PERCUSSION_RANAT_THUM = "pitched-percussion.ranat.thum";
            static final String PITCHED_PERCUSSION_RANAT_THUM_LEK = "pitched-percussion.ranat.thum-lek";
            static final String PITCHED_PERCUSSION_REYONG = "pitched-percussion.reyong";
            static final String PITCHED_PERCUSSION_SANZA = "pitched-percussion.sanza";
            static final String PITCHED_PERCUSSION_SARON_BARUNG = "pitched-percussion.saron-barung";
            static final String PITCHED_PERCUSSION_SARON_DEMONG = "pitched-percussion.saron-demong";
            static final String PITCHED_PERCUSSION_SARON_PANERUS = "pitched-percussion.saron-panerus";
            static final String PITCHED_PERCUSSION_SLENDRO_PANERUS = "pitched-percussion.slendro-panerus";
            static final String PITCHED_PERCUSSION_SLENTEM = "pitched-percussion.slentem";
            static final String PITCHED_PERCUSSION_TSYMBALY = "pitched-percussion.tsymbaly";
            static final String PITCHED_PERCUSSION_TUBES = "pitched-percussion.tubes";
            static final String PITCHED_PERCUSSION_TUBULAR_BELLS = "pitched-percussion.tubular-bells";
            static final String PITCHED_PERCUSSION_VIBRAPHONE = "pitched-percussion.vibraphone";
            static final String PITCHED_PERCUSSION_XYLOPHONE = "pitched-percussion.xylophone";
            static final String PITCHED_PERCUSSION_XYLOPHONE_ALTO = "pitched-percussion.xylophone.alto";
            static final String PITCHED_PERCUSSION_XYLOPHONE_BASS = "pitched-percussion.xylophone.bass";
            static final String PITCHED_PERCUSSION_XYLOPHONE_SOPRANO = "pitched-percussion.xylophone.soprano";
            static final String PITCHED_PERCUSSION_XYLORIMBA = "pitched-percussion.xylorimba";
            static final String PITCHED_PERCUSSION_YANGQIN = "pitched-percussion.yangqin";
            static final String PLUCK_ARCHLUTE = "pluck.archlute";
            static final String PLUCK_AUTOHARP = "pluck.autoharp";
            static final String PLUCK_BAGLAMA = "pluck.baglama";
            static final String PLUCK_BAJO = "pluck.bajo";
            static final String PLUCK_BALALAIKA = "pluck.balalaika";
            static final String PLUCK_BALALAIKA_ALTO = "pluck.balalaika.alto";
            static final String PLUCK_BALALAIKA_BASS = "pluck.balalaika.bass";
            static final String PLUCK_BALALAIKA_CONTRABASS = "pluck.balalaika.contrabass";
            static final String PLUCK_BALALAIKA_PICCOLO = "pluck.balalaika.piccolo";
            static final String PLUCK_BALALAIKA_PRIMA = "pluck.balalaika.prima";
            static final String PLUCK_BALALAIKA_SECUNDA = "pluck.balalaika.secunda";
            static final String PLUCK_BANDOLA = "pluck.bandola";
            static final String PLUCK_BANDURA = "pluck.bandura";
            static final String PLUCK_BANDURRIA = "pluck.bandurria";
            static final String PLUCK_BANJO = "pluck.banjo";
            static final String PLUCK_BANJO_TENOR = "pluck.banjo.tenor";
            static final String PLUCK_BANJOLELE = "pluck.banjolele";
            static final String PLUCK_BARBAT = "pluck.barbat";
            static final String PLUCK_BASS = "pluck.bass";
            static final String PLUCK_BASS_ACOUSTIC = "pluck.bass.acoustic";
            static final String PLUCK_BASS_BOLON = "pluck.bass.bolon";
            static final String PLUCK_BASS_ELECTRIC = "pluck.bass.electric";
            static final String PLUCK_BASS_FRETLESS = "pluck.bass.fretless";
            static final String PLUCK_BASS_GUITARRON = "pluck.bass.guitarron";
            static final String PLUCK_BASS_SYNTH = "pluck.bass.synth";
            static final String PLUCK_BASS_SYNTH_LEAD = "pluck.bass.synth.lead";
            static final String PLUCK_BASS_WASHTUB = "pluck.bass.washtub";
            static final String PLUCK_BASS_WHAMOLA = "pluck.bass.whamola";
            static final String PLUCK_BEGENA = "pluck.begena";
            static final String PLUCK_BIWA = "pluck.biwa";
            static final String PLUCK_BORDONUA = "pluck.bordonua";
            static final String PLUCK_BOUZOUKI = "pluck.bouzouki";
            static final String PLUCK_BOUZOUKI_IRISH = "pluck.bouzouki.irish";
            static final String PLUCK_CELTIC_HARP = "pluck.celtic-harp";
            static final String PLUCK_CHARANGO = "pluck.charango";
            static final String PLUCK_CHITARRA_BATTENTE = "pluck.chitarra-battente";
            static final String PLUCK_CITHARA = "pluck.cithara";
            static final String PLUCK_CITTERN = "pluck.cittern";
            static final String PLUCK_CUATRO = "pluck.cuatro";
            static final String PLUCK_DAN_BAU = "pluck.dan-bau";
            static final String PLUCK_DAN_NGUYET = "pluck.dan-nguyet";
            static final String PLUCK_DAN_TRANH = "pluck.dan-tranh";
            static final String PLUCK_DAN_TY_BA = "pluck.dan-ty-ba";
            static final String PLUCK_DIDDLEY_BOW = "pluck.diddley-bow";
            static final String PLUCK_DOMRA = "pluck.domra";
            static final String PLUCK_DOMU = "pluck.domu";
            static final String PLUCK_DULCIMER = "pluck.dulcimer";
            static final String PLUCK_DUTAR = "pluck.dutar";
            static final String PLUCK_DUXIANQIN = "pluck.duxianqin";
            static final String PLUCK_EKTARA = "pluck.ektara";
            static final String PLUCK_GEOMUNGO = "pluck.geomungo";
            static final String PLUCK_GOTTUVADHYAM = "pluck.gottuvadhyam";
            static final String PLUCK_GUITAR = "pluck.guitar";
            static final String PLUCK_GUITAR_ACOUSTIC = "pluck.guitar.acoustic";
            static final String PLUCK_GUITAR_ELECTRIC = "pluck.guitar.electric";
            static final String PLUCK_GUITAR_NYLON_STRING = "pluck.guitar.nylon-string";
            static final String PLUCK_GUITAR_PEDAL_STEEL = "pluck.guitar.pedal-steel";
            static final String PLUCK_GUITAR_PORTUGUESE = "pluck.guitar.portuguese";
            static final String PLUCK_GUITAR_REQUINTO = "pluck.guitar.requinto";
            static final String PLUCK_GUITAR_RESONATOR = "pluck.guitar.resonator";
            static final String PLUCK_GUITAR_STEEL_STRING = "pluck.guitar.steel-string";
            static final String PLUCK_GUITJO = "pluck.guitjo";
            static final String PLUCK_GUITJO_DOUBLE_NECK = "pluck.guitjo.double-neck";
            static final String PLUCK_GUQIN = "pluck.guqin";
            static final String PLUCK_GUZHENG = "pluck.guzheng";
            static final String PLUCK_GUZHENG_CHOAZHOU = "pluck.guzheng.choazhou";
            static final String PLUCK_HARP = "pluck.harp";
            static final String PLUCK_HARP_GUITAR = "pluck.harp-guitar";
            static final String PLUCK_HUAPANGUERA = "pluck.huapanguera";
            static final String PLUCK_JARANA_HUASTECA = "pluck.jarana-huasteca";
            static final String PLUCK_JARANA_JAROCHA = "pluck.jarana-jarocha";
            static final String PLUCK_JARANA_JAROCHA_MOSQUITO = "pluck.jarana-jarocha.mosquito";
            static final String PLUCK_JARANA_JAROCHA_PRIMERA = "pluck.jarana-jarocha.primera";
            static final String PLUCK_JARANA_JAROCHA_SEGUNDA = "pluck.jarana-jarocha.segunda";
            static final String PLUCK_JARANA_JAROCHA_TERCERA = "pluck.jarana-jarocha.tercera";
            static final String PLUCK_KABOSY = "pluck.kabosy";
            static final String PLUCK_KANTELE = "pluck.kantele";
            static final String PLUCK_KANUN = "pluck.kanun";
            static final String PLUCK_KAYAGUM = "pluck.kayagum";
            static final String PLUCK_KOBZA = "pluck.kobza";
            static final String PLUCK_KOMUZ = "pluck.komuz";
            static final String PLUCK_KORA = "pluck.kora";
            static final String PLUCK_KOTO = "pluck.koto";
            static final String PLUCK_KUTIYAPI = "pluck.kutiyapi";
            static final String PLUCK_LANGELEIK = "pluck.langeleik";
            static final String PLUCK_LAUD = "pluck.laud";
            static final String PLUCK_LUTE = "pluck.lute";
            static final String PLUCK_LYRE = "pluck.lyre";
            static final String PLUCK_MANDOBASS = "pluck.mandobass";
            static final String PLUCK_MANDOCELLO = "pluck.mandocello";
            static final String PLUCK_MANDOLA = "pluck.mandola";
            static final String PLUCK_MANDOLIN = "pluck.mandolin";
            static final String PLUCK_MANDOLIN_OCTAVE = "pluck.mandolin.octave";
            static final String PLUCK_MANDORA = "pluck.mandora";
            static final String PLUCK_MANDORE = "pluck.mandore";
            static final String PLUCK_MAROVANY = "pluck.marovany";
            static final String PLUCK_MUSICAL_BOW = "pluck.musical-bow";
            static final String PLUCK_NGONI = "pluck.ngoni";
            static final String PLUCK_OUD = "pluck.oud";
            static final String PLUCK_PIPA = "pluck.pipa";
            static final String PLUCK_PSALTERY = "pluck.psaltery";
            static final String PLUCK_RUAN = "pluck.ruan";
            static final String PLUCK_SALLANEH = "pluck.sallaneh";
            static final String PLUCK_SANSHIN = "pluck.sanshin";
            static final String PLUCK_SANTOOR = "pluck.santoor";
            static final String PLUCK_SANXIAN = "pluck.sanxian";
            static final String PLUCK_SAROD = "pluck.sarod";
            static final String PLUCK_SAUNG = "pluck.saung";
            static final String PLUCK_SAZ = "pluck.saz";
            static final String PLUCK_SE = "pluck.se";
            static final String PLUCK_SETAR = "pluck.setar";
            static final String PLUCK_SHAMISEN = "pluck.shamisen";
            static final String PLUCK_SITAR = "pluck.sitar";
            static final String PLUCK_SYNTH = "pluck.synth";
            static final String PLUCK_SYNTH_CHARANG = "pluck.synth.charang";
            static final String PLUCK_SYNTH_CHIFF = "pluck.synth.chiff";
            static final String PLUCK_SYNTH_STICK = "pluck.synth.stick";
            static final String PLUCK_TAMBURA = "pluck.tambura";
            static final String PLUCK_TAMBURA_BULGARIAN = "pluck.tambura.bulgarian";
            static final String PLUCK_TAMBURA_FEMALE = "pluck.tambura.female";
            static final String PLUCK_TAMBURA_MALE = "pluck.tambura.male";
            static final String PLUCK_TAR = "pluck.tar";
            static final String PLUCK_THEORBO = "pluck.theorbo";
            static final String PLUCK_TIMPLE = "pluck.timple";
            static final String PLUCK_TIPLE = "pluck.tiple";
            static final String PLUCK_TRES = "pluck.tres";
            static final String PLUCK_UKULELE = "pluck.ukulele";
            static final String PLUCK_UKULELE_TENOR = "pluck.ukulele.tenor";
            static final String PLUCK_VALIHA = "pluck.valiha";
            static final String PLUCK_VEENA = "pluck.veena";
            static final String PLUCK_VEENA_MOHAN = "pluck.veena.mohan";
            static final String PLUCK_VEENA_RUDRA = "pluck.veena.rudra";
            static final String PLUCK_VEENA_VICHITRA = "pluck.veena.vichitra";
            static final String PLUCK_VIHUELA = "pluck.vihuela";
            static final String PLUCK_VIHUELA_MEXICAN = "pluck.vihuela.mexican";
            static final String PLUCK_XALAM = "pluck.xalam";
            static final String PLUCK_YUEQIN = "pluck.yueqin";
            static final String PLUCK_ZITHER = "pluck.zither";
            static final String PLUCK_ZITHER_OVERTONE = "pluck.zither.overtone";
            static final String RATTLE_AFOXE = "rattle.afoxe";
            static final String RATTLE_BIRDS = "rattle.birds";
            static final String RATTLE_CABASA = "rattle.cabasa";
            static final String RATTLE_CAXIXI = "rattle.caxixi";
            static final String RATTLE_COG = "rattle.cog";
            static final String RATTLE_GANZA = "rattle.ganza";
            static final String RATTLE_HOSHO = "rattle.hosho";
            static final String RATTLE_JAWBONE = "rattle.jawbone";
            static final String RATTLE_KAYAMBA = "rattle.kayamba";
            static final String RATTLE_KPOKO_KPOKO = "rattle.kpoko-kpoko";
            static final String RATTLE_LAVA_STONES = "rattle.lava-stones";
            static final String RATTLE_MARACA = "rattle.maraca";
            static final String RATTLE_RAIN_STICK = "rattle.rain-stick";
            static final String RATTLE_RATCHET = "rattle.ratchet";
            static final String RATTLE_RATTLE = "rattle.rattle";
            static final String RATTLE_SHAKER = "rattle.shaker";
            static final String RATTLE_SHAKER_EGG = "rattle.shaker.egg";
            static final String RATTLE_SHEKERE = "rattle.shekere";
            static final String RATTLE_SISTRE = "rattle.sistre";
            static final String RATTLE_TELEVI = "rattle.televi";
            static final String RATTLE_VIBRASLAP = "rattle.vibraslap";
            static final String RATTLE_WASEMBE = "rattle.wasembe";
            static final String STRINGS_AJAENG = "strings.ajaeng";
            static final String STRINGS_ARPEGGIONE = "strings.arpeggione";
            static final String STRINGS_BARYTON = "strings.baryton";
            static final String STRINGS_CELLO = "strings.cello";
            static final String STRINGS_CELLO_PICCOLO = "strings.cello.piccolo";
            static final String STRINGS_CONTRABASS = "strings.contrabass";
            static final String STRINGS_CRWTH = "strings.crwth";
            static final String STRINGS_DAN_GAO = "strings.dan-gao";
            static final String STRINGS_DIHU = "strings.dihu";
            static final String STRINGS_ERHU = "strings.erhu";
            static final String STRINGS_ERXIAN = "strings.erxian";
            static final String STRINGS_ESRAJ = "strings.esraj";
            static final String STRINGS_FIDDLE = "strings.fiddle";
            static final String STRINGS_FIDDLE_HARDANGER = "strings.fiddle.hardanger";
            static final String STRINGS_GADULKA = "strings.gadulka";
            static final String STRINGS_GAOHU = "strings.gaohu";
            static final String STRINGS_GEHU = "strings.gehu";
            static final String STRINGS_GROUP = "strings.group";
            static final String STRINGS_GROUP_SYNTH = "strings.group.synth";
            static final String STRINGS_HAEGEUM = "strings.haegeum";
            static final String STRINGS_HURDY_GURDY = "strings.hurdy-gurdy";
            static final String STRINGS_IGIL = "strings.igil";
            static final String STRINGS_KAMANCHA = "strings.kamancha";
            static final String STRINGS_KOKYU = "strings.kokyu";
            static final String STRINGS_LARUAN = "strings.laruan";
            static final String STRINGS_LEIQIN = "strings.leiqin";
            static final String STRINGS_LIRONE = "strings.lirone";
            static final String STRINGS_LYRA_BYZANTINE = "strings.lyra.byzantine";
            static final String STRINGS_LYRA_CRETAN = "strings.lyra.cretan";
            static final String STRINGS_MORIN_KHUUR = "strings.morin-khuur";
            static final String STRINGS_NYCKELHARPA = "strings.nyckelharpa";
            static final String STRINGS_OCTOBASS = "strings.octobass";
            static final String STRINGS_REBAB = "strings.rebab";
            static final String STRINGS_REBEC = "strings.rebec";
            static final String STRINGS_SARANGI = "strings.sarangi";
            static final String STRINGS_STROH_VIOLIN = "strings.stroh-violin";
            static final String STRINGS_TROMBA_MARINA = "strings.tromba-marina";
            static final String STRINGS_VIELLE = "strings.vielle";
            static final String STRINGS_VIOL = "strings.viol";
            static final String STRINGS_VIOL_ALTO = "strings.viol.alto";
            static final String STRINGS_VIOL_BASS = "strings.viol.bass";
            static final String STRINGS_VIOL_TENOR = "strings.viol.tenor";
            static final String STRINGS_VIOL_TREBLE = "strings.viol.treble";
            static final String STRINGS_VIOL_VIOLONE = "strings.viol.violone";
            static final String STRINGS_VIOLA = "strings.viola";
            static final String STRINGS_VIOLA_DAMORE = "strings.viola-damore";
            static final String STRINGS_VIOLIN = "strings.violin";
            static final String STRINGS_VIOLONO_PICCOLO = "strings.violono.piccolo";
            static final String STRINGS_VIOLOTTA = "strings.violotta";
            static final String STRINGS_YAYLI_TANBUR = "strings.yayli-tanbur";
            static final String STRINGS_YAZHENG = "strings.yazheng";
            static final String STRINGS_ZHONGHU = "strings.zhonghu";
            static final String SYNTH_EFFECTS = "synth.effects";
            static final String SYNTH_EFFECTS_ATMOSPHERE = "synth.effects.atmosphere";
            static final String SYNTH_EFFECTS_BRIGHTNESS = "synth.effects.brightness";
            static final String SYNTH_EFFECTS_CRYSTAL = "synth.effects.crystal";
            static final String SYNTH_EFFECTS_ECHOES = "synth.effects.echoes";
            static final String SYNTH_EFFECTS_GOBLINS = "synth.effects.goblins";
            static final String SYNTH_EFFECTS_RAIN = "synth.effects.rain";
            static final String SYNTH_EFFECTS_SCI_FI = "synth.effects.sci-fi";
            static final String SYNTH_EFFECTS_SOUNDTRACK = "synth.effects.soundtrack";
            static final String SYNTH_GROUP = "synth.group";
            static final String SYNTH_GROUP_FIFTHS = "synth.group.fifths";
            static final String SYNTH_GROUP_ORCHESTRA = "synth.group.orchestra";
            static final String SYNTH_PAD = "synth.pad";
            static final String SYNTH_PAD_BOWED = "synth.pad.bowed";
            static final String SYNTH_PAD_CHOIR = "synth.pad.choir";
            static final String SYNTH_PAD_HALO = "synth.pad.halo";
            static final String SYNTH_PAD_METALLIC = "synth.pad.metallic";
            static final String SYNTH_PAD_POLYSYNTH = "synth.pad.polysynth";
            static final String SYNTH_PAD_SWEEP = "synth.pad.sweep";
            static final String SYNTH_PAD_WARM = "synth.pad.warm";
            static final String SYNTH_THEREMIN = "synth.theremin";
            static final String SYNTH_TONE_SAWTOOTH = "synth.tone.sawtooth";
            static final String SYNTH_TONE_SINE = "synth.tone.sine";
            static final String SYNTH_TONE_SQUARE = "synth.tone.square";
            static final String VOICE_AA = "voice.aa";
            static final String VOICE_ALTO = "voice.alto";
            static final String VOICE_AW = "voice.aw";
            static final String VOICE_BARITONE = "voice.baritone";
            static final String VOICE_BASS = "voice.bass";
            static final String VOICE_CHILD = "voice.child";
            static final String VOICE_COUNTERTENOR = "voice.countertenor";
            static final String VOICE_DOO = "voice.doo";
            static final String VOICE_EE = "voice.ee";
            static final String VOICE_FEMALE = "voice.female";
            static final String VOICE_KAZOO = "voice.kazoo";
            static final String VOICE_MALE = "voice.male";
            static final String VOICE_MEZZO_SOPRANO = "voice.mezzo-soprano";
            static final String VOICE_MM = "voice.mm";
            static final String VOICE_OO = "voice.oo";
            static final String VOICE_PERCUSSION = "voice.percussion";
            static final String VOICE_PERCUSSION_BEATBOX = "voice.percussion.beatbox";
            static final String VOICE_SOPRANO = "voice.soprano";
            static final String VOICE_SYNTH = "voice.synth";
            static final String VOICE_TALK_BOX = "voice.talk-box";
            static final String VOICE_TENOR = "voice.tenor";
            static final String VOICE_VOCALS = "voice.vocals";
            static final String WIND_FLUTES_BANSURI = "wind.flutes.bansuri";
            static final String WIND_FLUTES_BLOWN_BOTTLE = "wind.flutes.blown-bottle";
            static final String WIND_FLUTES_CALLIOPE = "wind.flutes.calliope";
            static final String WIND_FLUTES_DANSO = "wind.flutes.danso";
            static final String WIND_FLUTES_DI_ZI = "wind.flutes.di-zi";
            static final String WIND_FLUTES_DVOJNICE = "wind.flutes.dvojnice";
            static final String WIND_FLUTES_FIFE = "wind.flutes.fife";
            static final String WIND_FLUTES_FLAGEOLET = "wind.flutes.flageolet";
            static final String WIND_FLUTES_FLUTE = "wind.flutes.flute";
            static final String WIND_FLUTES_FLUTE_ALTO = "wind.flutes.flute.alto";
            static final String WIND_FLUTES_FLUTE_BASS = "wind.flutes.flute.bass";
            static final String WIND_FLUTES_FLUTE_CONTRA_ALTO = "wind.flutes.flute.contra-alto";
            static final String WIND_FLUTES_FLUTE_CONTRABASS = "wind.flutes.flute.contrabass";
            static final String WIND_FLUTES_FLUTE_DOUBLE_CONTRABASS = "wind.flutes.flute.double-contrabass";
            static final String WIND_FLUTES_FLUTE_IRISH = "wind.flutes.flute.irish";
            static final String WIND_FLUTES_FLUTE_PICCOLO = "wind.flutes.flute.piccolo";
            static final String WIND_FLUTES_FLUTE_SUBCONTRABASS = "wind.flutes.flute.subcontrabass";
            static final String WIND_FLUTES_FUJARA = "wind.flutes.fujara";
            static final String WIND_FLUTES_GEMSHORN = "wind.flutes.gemshorn";
            static final String WIND_FLUTES_HOCCHIKU = "wind.flutes.hocchiku";
            static final String WIND_FLUTES_HUN = "wind.flutes.hun";
            static final String WIND_FLUTES_KAVAL = "wind.flutes.kaval";
            static final String WIND_FLUTES_KAWALA = "wind.flutes.kawala";
            static final String WIND_FLUTES_KHLUI = "wind.flutes.khlui";
            static final String WIND_FLUTES_KNOTWEED = "wind.flutes.knotweed";
            static final String WIND_FLUTES_KONCOVKA_ALTO = "wind.flutes.koncovka.alto";
            static final String WIND_FLUTES_KOUDI = "wind.flutes.koudi";
            static final String WIND_FLUTES_NEY = "wind.flutes.ney";
            static final String WIND_FLUTES_NOHKAN = "wind.flutes.nohkan";
            static final String WIND_FLUTES_NOSE = "wind.flutes.nose";
            static final String WIND_FLUTES_OCARINA = "wind.flutes.ocarina";
            static final String WIND_FLUTES_OVERTONE_TENOR = "wind.flutes.overtone.tenor";
            static final String WIND_FLUTES_PALENDAG = "wind.flutes.palendag";
            static final String WIND_FLUTES_PANPIPES = "wind.flutes.panpipes";
            static final String WIND_FLUTES_QUENA = "wind.flutes.quena";
            static final String WIND_FLUTES_RECORDER = "wind.flutes.recorder";
            static final String WIND_FLUTES_RECORDER_ALTO = "wind.flutes.recorder.alto";
            static final String WIND_FLUTES_RECORDER_BASS = "wind.flutes.recorder.bass";
            static final String WIND_FLUTES_RECORDER_CONTRABASS = "wind.flutes.recorder.contrabass";
            static final String WIND_FLUTES_RECORDER_DESCANT = "wind.flutes.recorder.descant";
            static final String WIND_FLUTES_RECORDER_GARKLEIN = "wind.flutes.recorder.garklein";
            static final String WIND_FLUTES_RECORDER_GREAT_BASS = "wind.flutes.recorder.great-bass";
            static final String WIND_FLUTES_RECORDER_SOPRANINO = "wind.flutes.recorder.sopranino";
            static final String WIND_FLUTES_RECORDER_SOPRANO = "wind.flutes.recorder.soprano";
            static final String WIND_FLUTES_RECORDER_TENOR = "wind.flutes.recorder.tenor";
            static final String WIND_FLUTES_RYUTEKI = "wind.flutes.ryuteki";
            static final String WIND_FLUTES_SHAKUHACHI = "wind.flutes.shakuhachi";
            static final String WIND_FLUTES_SHEPHERDS_PIPE = "wind.flutes.shepherds-pipe";
            static final String WIND_FLUTES_SHINOBUE = "wind.flutes.shinobue";
            static final String WIND_FLUTES_SHVI = "wind.flutes.shvi";
            static final String WIND_FLUTES_SULING = "wind.flutes.suling";
            static final String WIND_FLUTES_TARKA = "wind.flutes.tarka";
            static final String WIND_FLUTES_TUMPONG = "wind.flutes.tumpong";
            static final String WIND_FLUTES_VENU = "wind.flutes.venu";
            static final String WIND_FLUTES_WHISTLE = "wind.flutes.whistle";
            static final String WIND_FLUTES_WHISTLE_ALTO = "wind.flutes.whistle.alto";
            static final String WIND_FLUTES_WHISTLE_LOW_IRISH = "wind.flutes.whistle.low-irish";
            static final String WIND_FLUTES_WHISTLE_SHIVA = "wind.flutes.whistle.shiva";
            static final String WIND_FLUTES_WHISTLE_SLIDE = "wind.flutes.whistle.slide";
            static final String WIND_FLUTES_WHISTLE_TIN = "wind.flutes.whistle.tin";
            static final String WIND_FLUTES_WHISTLE_TIN_BFLAT = "wind.flutes.whistle.tin.bflat";
            static final String WIND_FLUTES_WHISTLE_TIN_D = "wind.flutes.whistle.tin.d";
            static final String WIND_FLUTES_XIAO = "wind.flutes.xiao";
            static final String WIND_FLUTES_XUN = "wind.flutes.xun";
            static final String WIND_GROUP = "wind.group";
            static final String WIND_JUG = "wind.jug";
            static final String WIND_PIPES_BAGPIPES = "wind.pipes.bagpipes";
            static final String WIND_PIPES_GAIDA = "wind.pipes.gaida";
            static final String WIND_PIPES_HIGHLAND = "wind.pipes.highland";
            static final String WIND_PIPES_UILLEANN = "wind.pipes.uilleann";
            static final String WIND_PUNGI = "wind.pungi";
            static final String WIND_REED_ALBOGUE = "wind.reed.albogue";
            static final String WIND_REED_ALBOKA = "wind.reed.alboka";
            static final String WIND_REED_ALGAITA = "wind.reed.algaita";
            static final String WIND_REED_ARGHUL = "wind.reed.arghul";
            static final String WIND_REED_BASSET_HORN = "wind.reed.basset-horn";
            static final String WIND_REED_BASSOON = "wind.reed.bassoon";
            static final String WIND_REED_BAWU = "wind.reed.bawu";
            static final String WIND_REED_BIFORA = "wind.reed.bifora";
            static final String WIND_REED_BOMBARDE = "wind.reed.bombarde";
            static final String WIND_REED_CHALUMEAU = "wind.reed.chalumeau";
            static final String WIND_REED_CLARINET = "wind.reed.clarinet";
            static final String WIND_REED_CLARINET_A = "wind.reed.clarinet.a";
            static final String WIND_REED_CLARINET_ALTO = "wind.reed.clarinet.alto";
            static final String WIND_REED_CLARINET_BASS = "wind.reed.clarinet.bass";
            static final String WIND_REED_CLARINET_BASSET = "wind.reed.clarinet.basset";
            static final String WIND_REED_CLARINET_BFLAT = "wind.reed.clarinet.bflat";
            static final String WIND_REED_CLARINET_CONTRA_ALTO = "wind.reed.clarinet.contra-alto";
            static final String WIND_REED_CLARINET_CONTRABASS = "wind.reed.clarinet.contrabass";
            static final String WIND_REED_CLARINET_EFLAT = "wind.reed.clarinet.eflat";
            static final String WIND_REED_CLARINET_PICCOLO_AFLAT = "wind.reed.clarinet.piccolo.aflat";
            static final String WIND_REED_CLARINETTE_DAMOUR = "wind.reed.clarinette-damour";
            static final String WIND_REED_CONTRABASS = "wind.reed.contrabass";
            static final String WIND_REED_CONTRABASSOON = "wind.reed.contrabassoon";
            static final String WIND_REED_CORNAMUSE = "wind.reed.cornamuse";
            static final String WIND_REED_CROMORNE = "wind.reed.cromorne";
            static final String WIND_REED_CRUMHORN = "wind.reed.crumhorn";
            static final String WIND_REED_CRUMHORN_ALTO = "wind.reed.crumhorn.alto";
            static final String WIND_REED_CRUMHORN_BASS = "wind.reed.crumhorn.bass";
            static final String WIND_REED_CRUMHORN_GREAT_BASS = "wind.reed.crumhorn.great-bass";
            static final String WIND_REED_CRUMHORN_SOPRANO = "wind.reed.crumhorn.soprano";
            static final String WIND_REED_CRUMHORN_TENOR = "wind.reed.crumhorn.tenor";
            static final String WIND_REED_DIPLE = "wind.reed.diple";
            static final String WIND_REED_DIPLICA = "wind.reed.diplica";
            static final String WIND_REED_DUDUK = "wind.reed.duduk";
            static final String WIND_REED_DULCIAN = "wind.reed.dulcian";
            static final String WIND_REED_DULZAINA = "wind.reed.dulzaina";
            static final String WIND_REED_ENGLISH_HORN = "wind.reed.english-horn";
            static final String WIND_REED_GUANZI = "wind.reed.guanzi";
            static final String WIND_REED_HARMONICA = "wind.reed.harmonica";
            static final String WIND_REED_HARMONICA_BASS = "wind.reed.harmonica.bass";
            static final String WIND_REED_HECKEL_CLARINA = "wind.reed.heckel-clarina";
            static final String WIND_REED_HECKELPHONE = "wind.reed.heckelphone";
            static final String WIND_REED_HECKELPHONE_PICCOLO = "wind.reed.heckelphone.piccolo";
            static final String WIND_REED_HECKELPHONE_CLARINET = "wind.reed.heckelphone-clarinet";
            static final String WIND_REED_HICHIRIKI = "wind.reed.hichiriki";
            static final String WIND_REED_HIRTENSCHALMEI = "wind.reed.hirtenschalmei";
            static final String WIND_REED_HNE = "wind.reed.hne";
            static final String WIND_REED_HORNPIPE = "wind.reed.hornpipe";
            static final String WIND_REED_HOUGUAN = "wind.reed.houguan";
            static final String WIND_REED_HULUSI = "wind.reed.hulusi";
            static final String WIND_REED_JOGI_BAJA = "wind.reed.jogi-baja";
            static final String WIND_REED_KEN_BAU = "wind.reed.ken-bau";
            static final String WIND_REED_KHAEN_MOUTH_ORGAN = "wind.reed.khaen-mouth-organ";
            static final String WIND_REED_LAUNEDDAS = "wind.reed.launeddas";
            static final String WIND_REED_MAQRUNAH = "wind.reed.maqrunah";
            static final String WIND_REED_MELODICA = "wind.reed.melodica";
            static final String WIND_REED_MIJWIZ = "wind.reed.mijwiz";
            static final String WIND_REED_MIZMAR = "wind.reed.mizmar";
            static final String WIND_REED_NADASWARAM = "wind.reed.nadaswaram";
            static final String WIND_REED_OBOE = "wind.reed.oboe";
            static final String WIND_REED_OBOE_BASS = "wind.reed.oboe.bass";
            static final String WIND_REED_OBOE_PICCOLO = "wind.reed.oboe.piccolo";
            static final String WIND_REED_OBOE_DA_CACCIA = "wind.reed.oboe-da-caccia";
            static final String WIND_REED_OBOE_DAMORE = "wind.reed.oboe-damore";
            static final String WIND_REED_OCTAVIN = "wind.reed.octavin";
            static final String WIND_REED_PI = "wind.reed.pi";
            static final String WIND_REED_PIBGORN = "wind.reed.pibgorn";
            static final String WIND_REED_PIRI = "wind.reed.piri";
            static final String WIND_REED_RACKETT = "wind.reed.rackett";
            static final String WIND_REED_RAUSCHPFEIFE = "wind.reed.rauschpfeife";
            static final String WIND_REED_RHAITA = "wind.reed.rhaita";
            static final String WIND_REED_ROTHPHONE = "wind.reed.rothphone";
            static final String WIND_REED_SARRUSAPHONE = "wind.reed.sarrusaphone";
            static final String WIND_REED_SAXONETTE = "wind.reed.saxonette";
            static final String WIND_REED_SAXOPHONE = "wind.reed.saxophone";
            static final String WIND_REED_SAXOPHONE_ALTO = "wind.reed.saxophone.alto";
            static final String WIND_REED_SAXOPHONE_AULOCHROME = "wind.reed.saxophone.aulochrome";
            static final String WIND_REED_SAXOPHONE_BARITONE = "wind.reed.saxophone.baritone";
            static final String WIND_REED_SAXOPHONE_BASS = "wind.reed.saxophone.bass";
            static final String WIND_REED_SAXOPHONE_CONTRABASS = "wind.reed.saxophone.contrabass";
            static final String WIND_REED_SAXOPHONE_MELODY = "wind.reed.saxophone.melody";
            static final String WIND_REED_SAXOPHONE_MEZZO_SOPRANO = "wind.reed.saxophone.mezzo-soprano";
            static final String WIND_REED_SAXOPHONE_SOPRANINO = "wind.reed.saxophone.sopranino";
            static final String WIND_REED_SAXOPHONE_SOPRANISSIMO = "wind.reed.saxophone.sopranissimo";
            static final String WIND_REED_SAXOPHONE_SOPRANO = "wind.reed.saxophone.soprano";
            static final String WIND_REED_SAXOPHONE_SUBCONTRABASS = "wind.reed.saxophone.subcontrabass";
            static final String WIND_REED_SAXOPHONE_TENOR = "wind.reed.saxophone.tenor";
            static final String WIND_REED_SHAWM = "wind.reed.shawm";
            static final String WIND_REED_SHENAI = "wind.reed.shenai";
            static final String WIND_REED_SHENG = "wind.reed.sheng";
            static final String WIND_REED_SIPSI = "wind.reed.sipsi";
            static final String WIND_REED_SOPILA = "wind.reed.sopila";
            static final String WIND_REED_SORNA = "wind.reed.sorna";
            static final String WIND_REED_SRALAI = "wind.reed.sralai";
            static final String WIND_REED_SUONA = "wind.reed.suona";
            static final String WIND_REED_SURNAI = "wind.reed.surnai";
            static final String WIND_REED_TAEPYEONGSO = "wind.reed.taepyeongso";
            static final String WIND_REED_TAROGATO = "wind.reed.tarogato";
            static final String WIND_REED_TAROGATO_ANCIENT = "wind.reed.tarogato.ancient";
            static final String WIND_REED_TROMPETA_CHINA = "wind.reed.trompeta-china";
            static final String WIND_REED_TUBAX = "wind.reed.tubax";
            static final String WIND_REED_XAPHOON = "wind.reed.xaphoon";
            static final String WIND_REED_ZHALEIKA = "wind.reed.zhaleika";
            static final String WIND_REED_ZURLA = "wind.reed.zurla";
            static final String WIND_REED_ZURNA = "wind.reed.zurna";
            static final String WOOD_AGOGO_BLOCK = "wood.agogo-block";
            static final String WOOD_AGUNG_A_TAMLANG = "wood.agung-a-tamlang";
            static final String WOOD_AHOKO = "wood.ahoko";
            static final String WOOD_BONES = "wood.bones";
            static final String WOOD_CASTANETS = "wood.castanets";
            static final String WOOD_CLAVES = "wood.claves";
            static final String WOOD_DRUM_STICKS = "wood.drum-sticks";
            static final String WOOD_GOURD = "wood.gourd";
            static final String WOOD_GRANITE_BLOCK = "wood.granite-block";
            static final String WOOD_GUBAN = "wood.guban";
            static final String WOOD_GUIRO = "wood.guiro";
            static final String WOOD_HYOUSHIGI = "wood.hyoushigi";
            static final String WOOD_IPU = "wood.ipu";
            static final String WOOD_JAM_BLOCK = "wood.jam-block";
            static final String WOOD_KAEKEEKE = "wood.kaekeeke";
            static final String WOOD_KAGUL = "wood.kagul";
            static final String WOOD_KALAAU = "wood.kalaau";
            static final String WOOD_KASHIKLAR = "wood.kashiklar";
            static final String WOOD_KUBING = "wood.kubing";
            static final String WOOD_PAN_CLAPPERS = "wood.pan-clappers";
            static final String WOOD_SAND_BLOCK = "wood.sand-block";
            static final String WOOD_SLAPSTICK = "wood.slapstick";
            static final String WOOD_STIR_DRUM = "wood.stir-drum";
            static final String WOOD_TEMPLE_BLOCK = "wood.temple-block";
            static final String WOOD_TIC_TOC_BLOCK = "wood.tic-toc-block";
            static final String WOOD_TONETANG = "wood.tonetang";
            static final String WOOD_WOOD_BLOCK = "wood.wood-block";
        }
    }
}