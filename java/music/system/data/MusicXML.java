package music.system.data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import system.data.Cache;
import system.data.XML;

/**
 * {@code MusicXML} classifies a MusicXML document.
 * <p>
 * It also holds static members for traversing MusicXML documents.
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
        final DefaultHandler handler
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
        super(Handler.basic(null));
    }

    /**
     * {@code Analysis} classifies any type of analysis done on a music score.
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
     * <p>
     * This implementation accepts the minimum number of elements and attributes that are, or can be, used as performance instructions.
     */
    public static abstract
    class Handler
    extends XML.Handler<org.w3c.dom.Document>
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
        {
            @Override
            public Conditional apply(String name, Stack<Element> stack) {
                return null;
            }
        };

        /**
         * Creates a handler for the specified MusicXML document.
         *
         * @param document the document.
         */
        protected
        Handler(
            final org.w3c.dom.Document document
            ) {
            super(document);
        }

        /**
         * Accepts a subset of data, does not restrict.
         * <p>
         * If a document is not provided, a new document is created and used by the handler.
         *
         * @param document the optional document.
         * @return the analytic handler.
         */
        public static final
        Handler analytic(
            final org.w3c.dom.Document document
            ) {
            return new Handler(
                document == null
                ? newDocumentBuilder().newDocument()
                : document)
            {
                boolean completed;

                final
                Stack<Element> stack = new Stack<Element>();

                int depth;

                protected
                boolean isAcceptableElement(
                    final int depth,
                    final Stack<Element> stack,
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
                    final Stack<Element> stack,
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
                        final Element e = stack.pop();

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
                public boolean isUsed() {
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
                        final Element e = document.createElement(qName);

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
            return new Handler(
                document == null
                ? newDocumentBuilder().newDocument()
                : document)
            {
                boolean completed;

                final
                Stack<Element> stack = new Stack<Element>();

                @Override
                public boolean isUsed() {
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
         * {@code Restriction} represents MusicXML filters that are classically defined as fine-grained bi-functions of element name and stack of parent elements.
         */
        protected static abstract
        class Restriction
        extends Filter
        implements BiFunction<String, Stack<Element>, Filter.Conditional>
        {
            /**
             * Creates a new MusicXML restriction.
             */
            protected
            Restriction() {
                super();
            }
        }
    }

    /**
     * {@code Validation} is a special meta-data representing the result of analyzing a music score for performance.
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
         * <p>
         * This implementation is empty.
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
     */
    public final
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
         */
        public final
        class Dynamics
        {
            public static final String F = "f";
            public static final String FF = "ff";
            public static final String FP = "fp";
            public static final String FZ = "fz";
            public static final String FFF = "fff";
            public static final String FFFF = "ffff";
            public static final String FFFFF = "fffff";
            public static final String FFFFFF = "ffffff";
            public static final String MF = "mf";
            public static final String MP = "mp";
            public static final String N = "n";
            public static final String P = "p";
            public static final String PF = "pf";
            public static final String PP = "pp";
            public static final String PPP = "ppp";
            public static final String PPPP = "pppp";
            public static final String PPPPP = "ppppp";
            public static final String PPPPPP = "pppppp";
            public static final String RF = "rf";
            public static final String RFZ = "rfz";
            public static final String SF = "sf";
            public static final String SFP = "sfp";
            public static final String SFPP = "sfpp";
            public static final String SFZ = "sfz";
            public static final String SFFZ = "sffz";
            public static final String SFZP = "sfzp";
        }

        /**
         * {@code Sounds} holds all standard MusicXML sounds.
         */
        public final
        class Sounds
        {
            public static final String BRASS_ALPHORN = "brass.alphorn";
            public static final String BRASS_ALTO_HORN = "brass.alto-horn";
            public static final String BRASS_BARITONE_HORN = "brass.baritone-horn";
            public static final String BRASS_BUGLE = "brass.bugle";
            public static final String BRASS_BUGLE_ALTO = "brass.bugle.alto";
            public static final String BRASS_BUGLE_BARITONE = "brass.bugle.baritone";
            public static final String BRASS_BUGLE_CONTRABASS = "brass.bugle.contrabass";
            public static final String BRASS_BUGLE_EUPHONIUM_BUGLE = "brass.bugle.euphonium-bugle";
            public static final String BRASS_BUGLE_MELLOPHONE_BUGLE = "brass.bugle.mellophone-bugle";
            public static final String BRASS_BUGLE_SOPRANO = "brass.bugle.soprano";
            public static final String BRASS_CIMBASSO = "brass.cimbasso";
            public static final String BRASS_CONCH_SHELL = "brass.conch-shell";
            public static final String BRASS_CORNET = "brass.cornet";
            public static final String BRASS_CORNET_SOPRANO = "brass.cornet.soprano";
            public static final String BRASS_CORNETT = "brass.cornett";
            public static final String BRASS_CORNETT_TENOR = "brass.cornett.tenor";
            public static final String BRASS_CORNETTINO = "brass.cornettino";
            public static final String BRASS_DIDGERIDOO = "brass.didgeridoo";
            public static final String BRASS_EUPHONIUM = "brass.euphonium";
            public static final String BRASS_FISCORN = "brass.fiscorn";
            public static final String BRASS_FLUGELHORN = "brass.flugelhorn";
            public static final String BRASS_FRENCH_HORN = "brass.french-horn";
            public static final String BRASS_GROUP = "brass.group";
            public static final String BRASS_GROUP_SYNTH = "brass.group.synth";
            public static final String BRASS_HELICON = "brass.helicon";
            public static final String BRASS_HORAGAI = "brass.horagai";
            public static final String BRASS_KUHLOHORN = "brass.kuhlohorn";
            public static final String BRASS_MELLOPHONE = "brass.mellophone";
            public static final String BRASS_NATURAL_HORN = "brass.natural-horn";
            public static final String BRASS_OPHICLEIDE = "brass.ophicleide";
            public static final String BRASS_POSTHORN = "brass.posthorn";
            public static final String BRASS_RAG_DUNG = "brass.rag-dung";
            public static final String BRASS_SACKBUTT = "brass.sackbutt";
            public static final String BRASS_SACKBUTT_ALTO = "brass.sackbutt.alto";
            public static final String BRASS_SACKBUTT_BASS = "brass.sackbutt.bass";
            public static final String BRASS_SACKBUTT_TENOR = "brass.sackbutt.tenor";
            public static final String BRASS_SAXHORN = "brass.saxhorn";
            public static final String BRASS_SERPENT = "brass.serpent";
            public static final String BRASS_SHOFAR = "brass.shofar";
            public static final String BRASS_SOUSAPHONE = "brass.sousaphone";
            public static final String BRASS_TROMBONE = "brass.trombone";
            public static final String BRASS_TROMBONE_ALTO = "brass.trombone.alto";
            public static final String BRASS_TROMBONE_BASS = "brass.trombone.bass";
            public static final String BRASS_TROMBONE_CONTRABASS = "brass.trombone.contrabass";
            public static final String BRASS_TROMBONE_TENOR = "brass.trombone.tenor";
            public static final String BRASS_TRUMPET = "brass.trumpet";
            public static final String BRASS_TRUMPET_BAROQUE = "brass.trumpet.baroque";
            public static final String BRASS_TRUMPET_BASS = "brass.trumpet.bass";
            public static final String BRASS_TRUMPET_BFLAT = "brass.trumpet.bflat";
            public static final String BRASS_TRUMPET_C = "brass.trumpet.c";
            public static final String BRASS_TRUMPET_D = "brass.trumpet.d";
            public static final String BRASS_TRUMPET_PICCOLO = "brass.trumpet.piccolo";
            public static final String BRASS_TRUMPET_POCKET = "brass.trumpet.pocket";
            public static final String BRASS_TRUMPET_SLIDE = "brass.trumpet.slide";
            public static final String BRASS_TRUMPET_TENOR = "brass.trumpet.tenor";
            public static final String BRASS_TUBA = "brass.tuba";
            public static final String BRASS_TUBA_BASS = "brass.tuba.bass";
            public static final String BRASS_TUBA_SUBCONTRABASS = "brass.tuba.subcontrabass";
            public static final String BRASS_VIENNA_HORN = "brass.vienna-horn";
            public static final String BRASS_VUVUZELA = "brass.vuvuzela";
            public static final String BRASS_WAGNER_TUBA = "brass.wagner-tuba";
            public static final String DRUM_APENTEMMA = "drum.apentemma";
            public static final String DRUM_ASHIKO = "drum.ashiko";
            public static final String DRUM_ATABAQUE = "drum.atabaque";
            public static final String DRUM_ATOKE = "drum.atoke";
            public static final String DRUM_ATSIMEVU = "drum.atsimevu";
            public static final String DRUM_AXATSE = "drum.axatse";
            public static final String DRUM_BASS_DRUM = "drum.bass-drum";
            public static final String DRUM_BATA = "drum.bata";
            public static final String DRUM_BATA_ITOTELE = "drum.bata.itotele";
            public static final String DRUM_BATA_IYA = "drum.bata.iya";
            public static final String DRUM_BATA_OKONKOLO = "drum.bata.okonkolo";
            public static final String DRUM_BENDIR = "drum.bendir";
            public static final String DRUM_BODHRAN = "drum.bodhran";
            public static final String DRUM_BOMBO = "drum.bombo";
            public static final String DRUM_BONGO = "drum.bongo";
            public static final String DRUM_BOUGARABOU = "drum.bougarabou";
            public static final String DRUM_BUFFALO_DRUM = "drum.buffalo-drum";
            public static final String DRUM_CAJON = "drum.cajon";
            public static final String DRUM_CHENDA = "drum.chenda";
            public static final String DRUM_CHU_DAIKO = "drum.chu-daiko";
            public static final String DRUM_CONGA = "drum.conga";
            public static final String DRUM_CUICA = "drum.cuica";
            public static final String DRUM_DABAKAN = "drum.dabakan";
            public static final String DRUM_DAFF = "drum.daff";
            public static final String DRUM_DAFLI = "drum.dafli";
            public static final String DRUM_DAIBYOSI = "drum.daibyosi";
            public static final String DRUM_DAMROO = "drum.damroo";
            public static final String DRUM_DARABUKA = "drum.darabuka";
            public static final String DRUM_DEF = "drum.def";
            public static final String DRUM_DHOL = "drum.dhol";
            public static final String DRUM_DHOLAK = "drum.dholak";
            public static final String DRUM_DJEMBE = "drum.djembe";
            public static final String DRUM_DOIRA = "drum.doira";
            public static final String DRUM_DONDO = "drum.dondo";
            public static final String DRUM_DOUN_DOUN_BA = "drum.doun-doun-ba";
            public static final String DRUM_DUFF = "drum.duff";
            public static final String DRUM_DUMBEK = "drum.dumbek";
            public static final String DRUM_FONTOMFROM = "drum.fontomfrom";
            public static final String DRUM_FRAME_DRUM = "drum.frame-drum";
            public static final String DRUM_FRAME_DRUM_ARABIAN = "drum.frame-drum.arabian";
            public static final String DRUM_GEDUK = "drum.geduk";
            public static final String DRUM_GHATAM = "drum.ghatam";
            public static final String DRUM_GOME = "drum.gome";
            public static final String DRUM_GROUP = "drum.group";
            public static final String DRUM_GROUP_CHINESE = "drum.group.chinese";
            public static final String DRUM_GROUP_EWE = "drum.group.ewe";
            public static final String DRUM_GROUP_INDIAN = "drum.group.indian";
            public static final String DRUM_GROUP_SET = "drum.group.set";
            public static final String DRUM_HAND_DRUM = "drum.hand-drum";
            public static final String DRUM_HIRA_DAIKO = "drum.hira-daiko";
            public static final String DRUM_IBO = "drum.ibo";
            public static final String DRUM_IGIHUMURIZO = "drum.igihumurizo";
            public static final String DRUM_INYAHURA = "drum.inyahura";
            public static final String DRUM_ISHAKWE = "drum.ishakwe";
            public static final String DRUM_JANG_GU = "drum.jang-gu";
            public static final String DRUM_KAGAN = "drum.kagan";
            public static final String DRUM_KAKKO = "drum.kakko";
            public static final String DRUM_KANJIRA = "drum.kanjira";
            public static final String DRUM_KENDHANG = "drum.kendhang";
            public static final String DRUM_KENDHANG_AGENG = "drum.kendhang.ageng";
            public static final String DRUM_KENDHANG_CIBLON = "drum.kendhang.ciblon";
            public static final String DRUM_KENKENI = "drum.kenkeni";
            public static final String DRUM_KHOL = "drum.khol";
            public static final String DRUM_KICK_DRUM = "drum.kick-drum";
            public static final String DRUM_KIDI = "drum.kidi";
            public static final String DRUM_KO_DAIKO = "drum.ko-daiko";
            public static final String DRUM_KPANLOGO = "drum.kpanlogo";
            public static final String DRUM_KUDUM = "drum.kudum";
            public static final String DRUM_LAMBEG = "drum.lambeg";
            public static final String DRUM_LION_DRUM = "drum.lion-drum";
            public static final String DRUM_LOG_DRUM = "drum.log-drum";
            public static final String DRUM_LOG_DRUM_AFRICAN = "drum.log-drum.african";
            public static final String DRUM_LOG_DRUM_NATIVE = "drum.log-drum.native";
            public static final String DRUM_LOG_DRUM_NIGERIAN = "drum.log-drum.nigerian";
            public static final String DRUM_MADAL = "drum.madal";
            public static final String DRUM_MADDALE = "drum.maddale";
            public static final String DRUM_MRIDANGAM = "drum.mridangam";
            public static final String DRUM_NAAL = "drum.naal";
            public static final String DRUM_NAGADO_DAIKO = "drum.nagado-daiko";
            public static final String DRUM_NAGARA = "drum.nagara";
            public static final String DRUM_NAQARA = "drum.naqara";
            public static final String DRUM_O_DAIKO = "drum.o-daiko";
            public static final String DRUM_OKAWA = "drum.okawa";
            public static final String DRUM_OKEDO_DAIKO = "drum.okedo-daiko";
            public static final String DRUM_PAHU_HULA = "drum.pahu-hula";
            public static final String DRUM_PAKHAWAJ = "drum.pakhawaj";
            public static final String DRUM_PANDEIRO = "drum.pandeiro";
            public static final String DRUM_PANDERO = "drum.pandero";
            public static final String DRUM_POWWOW = "drum.powwow";
            public static final String DRUM_PUEBLO = "drum.pueblo";
            public static final String DRUM_REPINIQUE = "drum.repinique";
            public static final String DRUM_RIQ = "drum.riq";
            public static final String DRUM_ROTOTOM = "drum.rototom";
            public static final String DRUM_SABAR = "drum.sabar";
            public static final String DRUM_SAKARA = "drum.sakara";
            public static final String DRUM_SAMPHO = "drum.sampho";
            public static final String DRUM_SANGBAN = "drum.sangban";
            public static final String DRUM_SHIME_DAIKO = "drum.shime-daiko";
            public static final String DRUM_SLIT_DRUM = "drum.slit-drum";
            public static final String DRUM_SLIT_DRUM_KRIN = "drum.slit-drum.krin";
            public static final String DRUM_SNARE_DRUM = "drum.snare-drum";
            public static final String DRUM_SNARE_DRUM_ELECTRIC = "drum.snare-drum.electric";
            public static final String DRUM_SOGO = "drum.sogo";
            public static final String DRUM_SURDO = "drum.surdo";
            public static final String DRUM_TABLA = "drum.tabla";
            public static final String DRUM_TABLA_BAYAN = "drum.tabla.bayan";
            public static final String DRUM_TABLA_DAYAN = "drum.tabla.dayan";
            public static final String DRUM_TAIKO = "drum.taiko";
            public static final String DRUM_TALKING = "drum.talking";
            public static final String DRUM_TAMA = "drum.tama";
            public static final String DRUM_TAMBORITA = "drum.tamborita";
            public static final String DRUM_TAMBOURINE = "drum.tambourine";
            public static final String DRUM_TAMTE = "drum.tamte";
            public static final String DRUM_TANGKU = "drum.tangku";
            public static final String DRUM_TAN_TAN = "drum.tan-tan";
            public static final String DRUM_TAPHON = "drum.taphon";
            public static final String DRUM_TAR = "drum.tar";
            public static final String DRUM_TASHA = "drum.tasha";
            public static final String DRUM_TENOR_DRUM = "drum.tenor-drum";
            public static final String DRUM_TEPONAXTLI = "drum.teponaxtli";
            public static final String DRUM_THAVIL = "drum.thavil";
            public static final String DRUM_THE_BOX = "drum.the-box";
            public static final String DRUM_TIMBALE = "drum.timbale";
            public static final String DRUM_TIMPANI = "drum.timpani";
            public static final String DRUM_TINAJA = "drum.tinaja";
            public static final String DRUM_TOERE = "drum.toere";
            public static final String DRUM_TOMBAK = "drum.tombak";
            public static final String DRUM_TOM_TOM = "drum.tom-tom";
            public static final String DRUM_TOM_TOM_SYNTH = "drum.tom-tom.synth";
            public static final String DRUM_TSUZUMI = "drum.tsuzumi";
            public static final String DRUM_TUMBAK = "drum.tumbak";
            public static final String DRUM_UCHIWA_DAIKO = "drum.uchiwa-daiko";
            public static final String DRUM_UDAKU = "drum.udaku";
            public static final String DRUM_UDU = "drum.udu";
            public static final String DRUM_ZARB = "drum.zarb";
            public static final String EFFECT_AEOLIAN_HARP = "effect.aeolian-harp";
            public static final String EFFECT_AIR_HORN = "effect.air-horn";
            public static final String EFFECT_APPLAUSE = "effect.applause";
            public static final String EFFECT_BASS_STRING_SLAP = "effect.bass-string-slap";
            public static final String EFFECT_BIRD = "effect.bird";
            public static final String EFFECT_BIRD_NIGHTINGALE = "effect.bird.nightingale";
            public static final String EFFECT_BIRD_TWEET = "effect.bird.tweet";
            public static final String EFFECT_BREATH = "effect.breath";
            public static final String EFFECT_BUBBLE = "effect.bubble";
            public static final String EFFECT_BULLROARER = "effect.bullroarer";
            public static final String EFFECT_BURST = "effect.burst";
            public static final String EFFECT_CAR = "effect.car";
            public static final String EFFECT_CAR_CRASH = "effect.car.crash";
            public static final String EFFECT_CAR_ENGINE = "effect.car.engine";
            public static final String EFFECT_CAR_PASS = "effect.car.pass";
            public static final String EFFECT_CAR_STOP = "effect.car.stop";
            public static final String EFFECT_CRICKETS = "effect.crickets";
            public static final String EFFECT_DOG = "effect.dog";
            public static final String EFFECT_DOOR_CREAK = "effect.door.creak";
            public static final String EFFECT_DOOR_SLAM = "effect.door.slam";
            public static final String EFFECT_EXPLOSION = "effect.explosion";
            public static final String EFFECT_FLUTE_KEY_CLICK = "effect.flute-key-click";
            public static final String EFFECT_FOOTSTEPS = "effect.footsteps";
            public static final String EFFECT_FROGS = "effect.frogs";
            public static final String EFFECT_GUITAR_CUTTING = "effect.guitar-cutting";
            public static final String EFFECT_GUITAR_FRET = "effect.guitar-fret";
            public static final String EFFECT_GUNSHOT = "effect.gunshot";
            public static final String EFFECT_HAND_CLAP = "effect.hand-clap";
            public static final String EFFECT_HEARTBEAT = "effect.heartbeat";
            public static final String EFFECT_HELICOPTER = "effect.helicopter";
            public static final String EFFECT_HIGH_Q = "effect.high-q";
            public static final String EFFECT_HORSE_GALLOP = "effect.horse-gallop";
            public static final String EFFECT_JET_PLANE = "effect.jet-plane";
            public static final String EFFECT_LASER_GUN = "effect.laser-gun";
            public static final String EFFECT_LAUGH = "effect.laugh";
            public static final String EFFECT_LIONS_ROAR = "effect.lions-roar";
            public static final String EFFECT_MACHINE_GUN = "effect.machine-gun";
            public static final String EFFECT_MARCHING_MACHINE = "effect.marching-machine";
            public static final String EFFECT_METRONOME_BELL = "effect.metronome-bell";
            public static final String EFFECT_METRONOME_CLICK = "effect.metronome-click";
            public static final String EFFECT_PAT = "effect.pat";
            public static final String EFFECT_PUNCH = "effect.punch";
            public static final String EFFECT_RAIN = "effect.rain";
            public static final String EFFECT_SCRATCH = "effect.scratch";
            public static final String EFFECT_SCREAM = "effect.scream";
            public static final String EFFECT_SEASHORE = "effect.seashore";
            public static final String EFFECT_SIREN = "effect.siren";
            public static final String EFFECT_SLAP = "effect.slap";
            public static final String EFFECT_SNAP = "effect.snap";
            public static final String EFFECT_STAMP = "effect.stamp";
            public static final String EFFECT_STARSHIP = "effect.starship";
            public static final String EFFECT_STREAM = "effect.stream";
            public static final String EFFECT_TELEPHONE_RING = "effect.telephone-ring";
            public static final String EFFECT_THUNDER = "effect.thunder";
            public static final String EFFECT_TRAIN = "effect.train";
            public static final String EFFECT_TRASH_CAN = "effect.trash-can";
            public static final String EFFECT_WHIP = "effect.whip";
            public static final String EFFECT_WHISTLE = "effect.whistle";
            public static final String EFFECT_WHISTLE_MOUTH_SIREN = "effect.whistle.mouth-siren";
            public static final String EFFECT_WHISTLE_POLICE = "effect.whistle.police";
            public static final String EFFECT_WHISTLE_SLIDE = "effect.whistle.slide";
            public static final String EFFECT_WHISTLE_TRAIN = "effect.whistle.train";
            public static final String EFFECT_WIND = "effect.wind";
            public static final String KEYBOARD_ACCORDION = "keyboard.accordion";
            public static final String KEYBOARD_BANDONEON = "keyboard.bandoneon";
            public static final String KEYBOARD_CELESTA = "keyboard.celesta";
            public static final String KEYBOARD_CLAVICHORD = "keyboard.clavichord";
            public static final String KEYBOARD_CLAVICHORD_SYNTH = "keyboard.clavichord.synth";
            public static final String KEYBOARD_CONCERTINA = "keyboard.concertina";
            public static final String KEYBOARD_FORTEPIANO = "keyboard.fortepiano";
            public static final String KEYBOARD_HARMONIUM = "keyboard.harmonium";
            public static final String KEYBOARD_HARPSICHORD = "keyboard.harpsichord";
            public static final String KEYBOARD_ONDES_MARTENOT = "keyboard.ondes-martenot";
            public static final String KEYBOARD_ORGAN = "keyboard.organ";
            public static final String KEYBOARD_ORGAN_DRAWBAR = "keyboard.organ.drawbar";
            public static final String KEYBOARD_ORGAN_PERCUSSIVE = "keyboard.organ.percussive";
            public static final String KEYBOARD_ORGAN_PIPE = "keyboard.organ.pipe";
            public static final String KEYBOARD_ORGAN_REED = "keyboard.organ.reed";
            public static final String KEYBOARD_ORGAN_ROTARY = "keyboard.organ.rotary";
            public static final String KEYBOARD_PIANO = "keyboard.piano";
            public static final String KEYBOARD_PIANO_ELECTRIC = "keyboard.piano.electric";
            public static final String KEYBOARD_PIANO_GRAND = "keyboard.piano.grand";
            public static final String KEYBOARD_PIANO_HONKY_TONK = "keyboard.piano.honky-tonk";
            public static final String KEYBOARD_PIANO_PREPARED = "keyboard.piano.prepared";
            public static final String KEYBOARD_PIANO_TOY = "keyboard.piano.toy";
            public static final String KEYBOARD_PIANO_UPRIGHT = "keyboard.piano.upright";
            public static final String KEYBOARD_VIRGINAL = "keyboard.virginal";
            public static final String METAL_ADODO = "metal.adodo";
            public static final String METAL_ANVIL = "metal.anvil";
            public static final String METAL_BABENDIL = "metal.babendil";
            public static final String METAL_BELLS_AGOGO = "metal.bells.agogo";
            public static final String METAL_BELLS_ALMGLOCKEN = "metal.bells.almglocken";
            public static final String METAL_BELLS_BELL_PLATE = "metal.bells.bell-plate";
            public static final String METAL_BELLS_BELL_TREE = "metal.bells.bell-tree";
            public static final String METAL_BELLS_CARILLON = "metal.bells.carillon";
            public static final String METAL_BELLS_CHIMES = "metal.bells.chimes";
            public static final String METAL_BELLS_CHIMTA = "metal.bells.chimta";
            public static final String METAL_BELLS_CHIPPLI = "metal.bells.chippli";
            public static final String METAL_BELLS_CHURCH = "metal.bells.church";
            public static final String METAL_BELLS_COWBELL = "metal.bells.cowbell";
            public static final String METAL_BELLS_DAWURO = "metal.bells.dawuro";
            public static final String METAL_BELLS_GANKOKWE = "metal.bells.gankokwe";
            public static final String METAL_BELLS_GHUNGROO = "metal.bells.ghungroo";
            public static final String METAL_BELLS_HATHELI = "metal.bells.hatheli";
            public static final String METAL_BELLS_JINGLE_BELL = "metal.bells.jingle-bell";
            public static final String METAL_BELLS_KHARTAL = "metal.bells.khartal";
            public static final String METAL_BELLS_MARK_TREE = "metal.bells.mark-tree";
            public static final String METAL_BELLS_SISTRUM = "metal.bells.sistrum";
            public static final String METAL_BELLS_SLEIGH_BELLS = "metal.bells.sleigh-bells";
            public static final String METAL_BELLS_TEMPLE = "metal.bells.temple";
            public static final String METAL_BELLS_TIBETAN = "metal.bells.tibetan";
            public static final String METAL_BELLS_TINKLEBELL = "metal.bells.tinklebell";
            public static final String METAL_BELLS_TRYCHEL = "metal.bells.trychel";
            public static final String METAL_BELLS_WIND_CHIMES = "metal.bells.wind-chimes";
            public static final String METAL_BELLS_ZILLS = "metal.bells.zills";
            public static final String METAL_BERIMBAU = "metal.berimbau";
            public static final String METAL_BRAKE_DRUMS = "metal.brake-drums";
            public static final String METAL_CROTALES = "metal.crotales";
            public static final String METAL_CYMBAL_BO = "metal.cymbal.bo";
            public static final String METAL_CYMBAL_CENG_CENG = "metal.cymbal.ceng-ceng";
            public static final String METAL_CYMBAL_CHABARA = "metal.cymbal.chabara";
            public static final String METAL_CYMBAL_CHINESE = "metal.cymbal.chinese";
            public static final String METAL_CYMBAL_CHING = "metal.cymbal.ching";
            public static final String METAL_CYMBAL_CLASH = "metal.cymbal.clash";
            public static final String METAL_CYMBAL_CRASH = "metal.cymbal.crash";
            public static final String METAL_CYMBAL_FINGER = "metal.cymbal.finger";
            public static final String METAL_CYMBAL_HAND = "metal.cymbal.hand";
            public static final String METAL_CYMBAL_KESI = "metal.cymbal.kesi";
            public static final String METAL_CYMBAL_MANJEERA = "metal.cymbal.manjeera";
            public static final String METAL_CYMBAL_REVERSE = "metal.cymbal.reverse";
            public static final String METAL_CYMBAL_RIDE = "metal.cymbal.ride";
            public static final String METAL_CYMBAL_SIZZLE = "metal.cymbal.sizzle";
            public static final String METAL_CYMBAL_SPLASH = "metal.cymbal.splash";
            public static final String METAL_CYMBAL_SUSPENDED = "metal.cymbal.suspended";
            public static final String METAL_CYMBAL_TEBYOSHI = "metal.cymbal.tebyoshi";
            public static final String METAL_CYMBAL_TIBETAN = "metal.cymbal.tibetan";
            public static final String METAL_CYMBAL_TINGSHA = "metal.cymbal.tingsha";
            public static final String METAL_FLEXATONE = "metal.flexatone";
            public static final String METAL_GONG = "metal.gong";
            public static final String METAL_GONG_AGENG = "metal.gong.ageng";
            public static final String METAL_GONG_AGUNG = "metal.gong.agung";
            public static final String METAL_GONG_CHANCHIKI = "metal.gong.chanchiki";
            public static final String METAL_GONG_CHINESE = "metal.gong.chinese";
            public static final String METAL_GONG_GANDINGAN = "metal.gong.gandingan";
            public static final String METAL_GONG_KEMPUL = "metal.gong.kempul";
            public static final String METAL_GONG_KEMPYANG = "metal.gong.kempyang";
            public static final String METAL_GONG_KETUK = "metal.gong.ketuk";
            public static final String METAL_GONG_KKWENGGWARI = "metal.gong.kkwenggwari";
            public static final String METAL_GONG_LUO = "metal.gong.luo";
            public static final String METAL_GONG_SINGING = "metal.gong.singing";
            public static final String METAL_GONG_THAI = "metal.gong.thai";
            public static final String METAL_GUIRA = "metal.guira";
            public static final String METAL_HANG = "metal.hang";
            public static final String METAL_HI_HAT = "metal.hi-hat";
            public static final String METAL_JAW_HARP = "metal.jaw-harp";
            public static final String METAL_KENGONG = "metal.kengong";
            public static final String METAL_MURCHANG = "metal.murchang";
            public static final String METAL_MUSICAL_SAW = "metal.musical-saw";
            public static final String METAL_SINGING_BOWL = "metal.singing-bowl";
            public static final String METAL_SPOONS = "metal.spoons";
            public static final String METAL_STEEL_DRUMS = "metal.steel-drums";
            public static final String METAL_TAMTAM = "metal.tamtam";
            public static final String METAL_THUNDERSHEET = "metal.thundersheet";
            public static final String METAL_TRIANGLE = "metal.triangle";
            public static final String METAL_WASHBOARD = "metal.washboard";
            public static final String PITCHED_PERCUSSION_ANGKLUNG = "pitched-percussion.angklung";
            public static final String PITCHED_PERCUSSION_BALAFON = "pitched-percussion.balafon";
            public static final String PITCHED_PERCUSSION_BELL_LYRE = "pitched-percussion.bell-lyre";
            public static final String PITCHED_PERCUSSION_BELLS = "pitched-percussion.bells";
            public static final String PITCHED_PERCUSSION_BIANQING = "pitched-percussion.bianqing";
            public static final String PITCHED_PERCUSSION_BIANZHONG = "pitched-percussion.bianzhong";
            public static final String PITCHED_PERCUSSION_BONANG = "pitched-percussion.bonang";
            public static final String PITCHED_PERCUSSION_CIMBALOM = "pitched-percussion.cimbalom";
            public static final String PITCHED_PERCUSSION_CRYSTAL_GLASSES = "pitched-percussion.crystal-glasses";
            public static final String PITCHED_PERCUSSION_DAN_TAM_THAP_LUC = "pitched-percussion.dan-tam-thap-luc";
            public static final String PITCHED_PERCUSSION_FANGXIANG = "pitched-percussion.fangxiang";
            public static final String PITCHED_PERCUSSION_GANDINGAN_A_KAYO = "pitched-percussion.gandingan-a-kayo";
            public static final String PITCHED_PERCUSSION_GANGSA = "pitched-percussion.gangsa";
            public static final String PITCHED_PERCUSSION_GENDER = "pitched-percussion.gender";
            public static final String PITCHED_PERCUSSION_GIYING = "pitched-percussion.giying";
            public static final String PITCHED_PERCUSSION_GLASS_HARMONICA = "pitched-percussion.glass-harmonica";
            public static final String PITCHED_PERCUSSION_GLOCKENSPIEL = "pitched-percussion.glockenspiel";
            public static final String PITCHED_PERCUSSION_GLOCKENSPIEL_ALTO = "pitched-percussion.glockenspiel.alto";
            public static final String PITCHED_PERCUSSION_GLOCKENSPIEL_SOPRANO = "pitched-percussion.glockenspiel.soprano";
            public static final String PITCHED_PERCUSSION_GYIL = "pitched-percussion.gyil";
            public static final String PITCHED_PERCUSSION_HAMMER_DULCIMER = "pitched-percussion.hammer-dulcimer";
            public static final String PITCHED_PERCUSSION_HANDBELLS = "pitched-percussion.handbells";
            public static final String PITCHED_PERCUSSION_KALIMBA = "pitched-percussion.kalimba";
            public static final String PITCHED_PERCUSSION_KANTIL = "pitched-percussion.kantil";
            public static final String PITCHED_PERCUSSION_KHIM = "pitched-percussion.khim";
            public static final String PITCHED_PERCUSSION_KULINTANG = "pitched-percussion.kulintang";
            public static final String PITCHED_PERCUSSION_KULINTANG_A_KAYO = "pitched-percussion.kulintang-a-kayo";
            public static final String PITCHED_PERCUSSION_KULINTANG_A_TINIOK = "pitched-percussion.kulintang-a-tiniok";
            public static final String PITCHED_PERCUSSION_LIKEMBE = "pitched-percussion.likembe";
            public static final String PITCHED_PERCUSSION_LUNTANG = "pitched-percussion.luntang";
            public static final String PITCHED_PERCUSSION_MARIMBA = "pitched-percussion.marimba";
            public static final String PITCHED_PERCUSSION_MARIMBA_BASS = "pitched-percussion.marimba.bass";
            public static final String PITCHED_PERCUSSION_MBIRA = "pitched-percussion.mbira";
            public static final String PITCHED_PERCUSSION_MBIRA_ARRAY = "pitched-percussion.mbira.array";
            public static final String PITCHED_PERCUSSION_METALLOPHONE = "pitched-percussion.metallophone";
            public static final String PITCHED_PERCUSSION_METALLOPHONE_ALTO = "pitched-percussion.metallophone.alto";
            public static final String PITCHED_PERCUSSION_METALLOPHONE_BASS = "pitched-percussion.metallophone.bass";
            public static final String PITCHED_PERCUSSION_METALLOPHONE_SOPRANO = "pitched-percussion.metallophone.soprano";
            public static final String PITCHED_PERCUSSION_MUSIC_BOX = "pitched-percussion.music-box";
            public static final String PITCHED_PERCUSSION_PELOG_PANERUS = "pitched-percussion.pelog-panerus";
            public static final String PITCHED_PERCUSSION_PEMADE = "pitched-percussion.pemade";
            public static final String PITCHED_PERCUSSION_PENYACAH = "pitched-percussion.penyacah";
            public static final String PITCHED_PERCUSSION_RANAT_EK = "pitched-percussion.ranat.ek";
            public static final String PITCHED_PERCUSSION_RANAT_EK_LEK = "pitched-percussion.ranat.ek-lek";
            public static final String PITCHED_PERCUSSION_RANAT_THUM = "pitched-percussion.ranat.thum";
            public static final String PITCHED_PERCUSSION_RANAT_THUM_LEK = "pitched-percussion.ranat.thum-lek";
            public static final String PITCHED_PERCUSSION_REYONG = "pitched-percussion.reyong";
            public static final String PITCHED_PERCUSSION_SANZA = "pitched-percussion.sanza";
            public static final String PITCHED_PERCUSSION_SARON_BARUNG = "pitched-percussion.saron-barung";
            public static final String PITCHED_PERCUSSION_SARON_DEMONG = "pitched-percussion.saron-demong";
            public static final String PITCHED_PERCUSSION_SARON_PANERUS = "pitched-percussion.saron-panerus";
            public static final String PITCHED_PERCUSSION_SLENDRO_PANERUS = "pitched-percussion.slendro-panerus";
            public static final String PITCHED_PERCUSSION_SLENTEM = "pitched-percussion.slentem";
            public static final String PITCHED_PERCUSSION_TSYMBALY = "pitched-percussion.tsymbaly";
            public static final String PITCHED_PERCUSSION_TUBES = "pitched-percussion.tubes";
            public static final String PITCHED_PERCUSSION_TUBULAR_BELLS = "pitched-percussion.tubular-bells";
            public static final String PITCHED_PERCUSSION_VIBRAPHONE = "pitched-percussion.vibraphone";
            public static final String PITCHED_PERCUSSION_XYLOPHONE = "pitched-percussion.xylophone";
            public static final String PITCHED_PERCUSSION_XYLOPHONE_ALTO = "pitched-percussion.xylophone.alto";
            public static final String PITCHED_PERCUSSION_XYLOPHONE_BASS = "pitched-percussion.xylophone.bass";
            public static final String PITCHED_PERCUSSION_XYLOPHONE_SOPRANO = "pitched-percussion.xylophone.soprano";
            public static final String PITCHED_PERCUSSION_XYLORIMBA = "pitched-percussion.xylorimba";
            public static final String PITCHED_PERCUSSION_YANGQIN = "pitched-percussion.yangqin";
            public static final String PLUCK_ARCHLUTE = "pluck.archlute";
            public static final String PLUCK_AUTOHARP = "pluck.autoharp";
            public static final String PLUCK_BAGLAMA = "pluck.baglama";
            public static final String PLUCK_BAJO = "pluck.bajo";
            public static final String PLUCK_BALALAIKA = "pluck.balalaika";
            public static final String PLUCK_BALALAIKA_ALTO = "pluck.balalaika.alto";
            public static final String PLUCK_BALALAIKA_BASS = "pluck.balalaika.bass";
            public static final String PLUCK_BALALAIKA_CONTRABASS = "pluck.balalaika.contrabass";
            public static final String PLUCK_BALALAIKA_PICCOLO = "pluck.balalaika.piccolo";
            public static final String PLUCK_BALALAIKA_PRIMA = "pluck.balalaika.prima";
            public static final String PLUCK_BALALAIKA_SECUNDA = "pluck.balalaika.secunda";
            public static final String PLUCK_BANDOLA = "pluck.bandola";
            public static final String PLUCK_BANDURA = "pluck.bandura";
            public static final String PLUCK_BANDURRIA = "pluck.bandurria";
            public static final String PLUCK_BANJO = "pluck.banjo";
            public static final String PLUCK_BANJO_TENOR = "pluck.banjo.tenor";
            public static final String PLUCK_BANJOLELE = "pluck.banjolele";
            public static final String PLUCK_BARBAT = "pluck.barbat";
            public static final String PLUCK_BASS = "pluck.bass";
            public static final String PLUCK_BASS_ACOUSTIC = "pluck.bass.acoustic";
            public static final String PLUCK_BASS_BOLON = "pluck.bass.bolon";
            public static final String PLUCK_BASS_ELECTRIC = "pluck.bass.electric";
            public static final String PLUCK_BASS_FRETLESS = "pluck.bass.fretless";
            public static final String PLUCK_BASS_GUITARRON = "pluck.bass.guitarron";
            public static final String PLUCK_BASS_SYNTH = "pluck.bass.synth";
            public static final String PLUCK_BASS_SYNTH_LEAD = "pluck.bass.synth.lead";
            public static final String PLUCK_BASS_WASHTUB = "pluck.bass.washtub";
            public static final String PLUCK_BASS_WHAMOLA = "pluck.bass.whamola";
            public static final String PLUCK_BEGENA = "pluck.begena";
            public static final String PLUCK_BIWA = "pluck.biwa";
            public static final String PLUCK_BORDONUA = "pluck.bordonua";
            public static final String PLUCK_BOUZOUKI = "pluck.bouzouki";
            public static final String PLUCK_BOUZOUKI_IRISH = "pluck.bouzouki.irish";
            public static final String PLUCK_CELTIC_HARP = "pluck.celtic-harp";
            public static final String PLUCK_CHARANGO = "pluck.charango";
            public static final String PLUCK_CHITARRA_BATTENTE = "pluck.chitarra-battente";
            public static final String PLUCK_CITHARA = "pluck.cithara";
            public static final String PLUCK_CITTERN = "pluck.cittern";
            public static final String PLUCK_CUATRO = "pluck.cuatro";
            public static final String PLUCK_DAN_BAU = "pluck.dan-bau";
            public static final String PLUCK_DAN_NGUYET = "pluck.dan-nguyet";
            public static final String PLUCK_DAN_TRANH = "pluck.dan-tranh";
            public static final String PLUCK_DAN_TY_BA = "pluck.dan-ty-ba";
            public static final String PLUCK_DIDDLEY_BOW = "pluck.diddley-bow";
            public static final String PLUCK_DOMRA = "pluck.domra";
            public static final String PLUCK_DOMU = "pluck.domu";
            public static final String PLUCK_DULCIMER = "pluck.dulcimer";
            public static final String PLUCK_DUTAR = "pluck.dutar";
            public static final String PLUCK_DUXIANQIN = "pluck.duxianqin";
            public static final String PLUCK_EKTARA = "pluck.ektara";
            public static final String PLUCK_GEOMUNGO = "pluck.geomungo";
            public static final String PLUCK_GOTTUVADHYAM = "pluck.gottuvadhyam";
            public static final String PLUCK_GUITAR = "pluck.guitar";
            public static final String PLUCK_GUITAR_ACOUSTIC = "pluck.guitar.acoustic";
            public static final String PLUCK_GUITAR_ELECTRIC = "pluck.guitar.electric";
            public static final String PLUCK_GUITAR_NYLON_STRING = "pluck.guitar.nylon-string";
            public static final String PLUCK_GUITAR_PEDAL_STEEL = "pluck.guitar.pedal-steel";
            public static final String PLUCK_GUITAR_PORTUGUESE = "pluck.guitar.portuguese";
            public static final String PLUCK_GUITAR_REQUINTO = "pluck.guitar.requinto";
            public static final String PLUCK_GUITAR_RESONATOR = "pluck.guitar.resonator";
            public static final String PLUCK_GUITAR_STEEL_STRING = "pluck.guitar.steel-string";
            public static final String PLUCK_GUITJO = "pluck.guitjo";
            public static final String PLUCK_GUITJO_DOUBLE_NECK = "pluck.guitjo.double-neck";
            public static final String PLUCK_GUQIN = "pluck.guqin";
            public static final String PLUCK_GUZHENG = "pluck.guzheng";
            public static final String PLUCK_GUZHENG_CHOAZHOU = "pluck.guzheng.choazhou";
            public static final String PLUCK_HARP = "pluck.harp";
            public static final String PLUCK_HARP_GUITAR = "pluck.harp-guitar";
            public static final String PLUCK_HUAPANGUERA = "pluck.huapanguera";
            public static final String PLUCK_JARANA_HUASTECA = "pluck.jarana-huasteca";
            public static final String PLUCK_JARANA_JAROCHA = "pluck.jarana-jarocha";
            public static final String PLUCK_JARANA_JAROCHA_MOSQUITO = "pluck.jarana-jarocha.mosquito";
            public static final String PLUCK_JARANA_JAROCHA_PRIMERA = "pluck.jarana-jarocha.primera";
            public static final String PLUCK_JARANA_JAROCHA_SEGUNDA = "pluck.jarana-jarocha.segunda";
            public static final String PLUCK_JARANA_JAROCHA_TERCERA = "pluck.jarana-jarocha.tercera";
            public static final String PLUCK_KABOSY = "pluck.kabosy";
            public static final String PLUCK_KANTELE = "pluck.kantele";
            public static final String PLUCK_KANUN = "pluck.kanun";
            public static final String PLUCK_KAYAGUM = "pluck.kayagum";
            public static final String PLUCK_KOBZA = "pluck.kobza";
            public static final String PLUCK_KOMUZ = "pluck.komuz";
            public static final String PLUCK_KORA = "pluck.kora";
            public static final String PLUCK_KOTO = "pluck.koto";
            public static final String PLUCK_KUTIYAPI = "pluck.kutiyapi";
            public static final String PLUCK_LANGELEIK = "pluck.langeleik";
            public static final String PLUCK_LAUD = "pluck.laud";
            public static final String PLUCK_LUTE = "pluck.lute";
            public static final String PLUCK_LYRE = "pluck.lyre";
            public static final String PLUCK_MANDOBASS = "pluck.mandobass";
            public static final String PLUCK_MANDOCELLO = "pluck.mandocello";
            public static final String PLUCK_MANDOLA = "pluck.mandola";
            public static final String PLUCK_MANDOLIN = "pluck.mandolin";
            public static final String PLUCK_MANDOLIN_OCTAVE = "pluck.mandolin.octave";
            public static final String PLUCK_MANDORA = "pluck.mandora";
            public static final String PLUCK_MANDORE = "pluck.mandore";
            public static final String PLUCK_MAROVANY = "pluck.marovany";
            public static final String PLUCK_MUSICAL_BOW = "pluck.musical-bow";
            public static final String PLUCK_NGONI = "pluck.ngoni";
            public static final String PLUCK_OUD = "pluck.oud";
            public static final String PLUCK_PIPA = "pluck.pipa";
            public static final String PLUCK_PSALTERY = "pluck.psaltery";
            public static final String PLUCK_RUAN = "pluck.ruan";
            public static final String PLUCK_SALLANEH = "pluck.sallaneh";
            public static final String PLUCK_SANSHIN = "pluck.sanshin";
            public static final String PLUCK_SANTOOR = "pluck.santoor";
            public static final String PLUCK_SANXIAN = "pluck.sanxian";
            public static final String PLUCK_SAROD = "pluck.sarod";
            public static final String PLUCK_SAUNG = "pluck.saung";
            public static final String PLUCK_SAZ = "pluck.saz";
            public static final String PLUCK_SE = "pluck.se";
            public static final String PLUCK_SETAR = "pluck.setar";
            public static final String PLUCK_SHAMISEN = "pluck.shamisen";
            public static final String PLUCK_SITAR = "pluck.sitar";
            public static final String PLUCK_SYNTH = "pluck.synth";
            public static final String PLUCK_SYNTH_CHARANG = "pluck.synth.charang";
            public static final String PLUCK_SYNTH_CHIFF = "pluck.synth.chiff";
            public static final String PLUCK_SYNTH_STICK = "pluck.synth.stick";
            public static final String PLUCK_TAMBURA = "pluck.tambura";
            public static final String PLUCK_TAMBURA_BULGARIAN = "pluck.tambura.bulgarian";
            public static final String PLUCK_TAMBURA_FEMALE = "pluck.tambura.female";
            public static final String PLUCK_TAMBURA_MALE = "pluck.tambura.male";
            public static final String PLUCK_TAR = "pluck.tar";
            public static final String PLUCK_THEORBO = "pluck.theorbo";
            public static final String PLUCK_TIMPLE = "pluck.timple";
            public static final String PLUCK_TIPLE = "pluck.tiple";
            public static final String PLUCK_TRES = "pluck.tres";
            public static final String PLUCK_UKULELE = "pluck.ukulele";
            public static final String PLUCK_UKULELE_TENOR = "pluck.ukulele.tenor";
            public static final String PLUCK_VALIHA = "pluck.valiha";
            public static final String PLUCK_VEENA = "pluck.veena";
            public static final String PLUCK_VEENA_MOHAN = "pluck.veena.mohan";
            public static final String PLUCK_VEENA_RUDRA = "pluck.veena.rudra";
            public static final String PLUCK_VEENA_VICHITRA = "pluck.veena.vichitra";
            public static final String PLUCK_VIHUELA = "pluck.vihuela";
            public static final String PLUCK_VIHUELA_MEXICAN = "pluck.vihuela.mexican";
            public static final String PLUCK_XALAM = "pluck.xalam";
            public static final String PLUCK_YUEQIN = "pluck.yueqin";
            public static final String PLUCK_ZITHER = "pluck.zither";
            public static final String PLUCK_ZITHER_OVERTONE = "pluck.zither.overtone";
            public static final String RATTLE_AFOXE = "rattle.afoxe";
            public static final String RATTLE_BIRDS = "rattle.birds";
            public static final String RATTLE_CABASA = "rattle.cabasa";
            public static final String RATTLE_CAXIXI = "rattle.caxixi";
            public static final String RATTLE_COG = "rattle.cog";
            public static final String RATTLE_GANZA = "rattle.ganza";
            public static final String RATTLE_HOSHO = "rattle.hosho";
            public static final String RATTLE_JAWBONE = "rattle.jawbone";
            public static final String RATTLE_KAYAMBA = "rattle.kayamba";
            public static final String RATTLE_KPOKO_KPOKO = "rattle.kpoko-kpoko";
            public static final String RATTLE_LAVA_STONES = "rattle.lava-stones";
            public static final String RATTLE_MARACA = "rattle.maraca";
            public static final String RATTLE_RAIN_STICK = "rattle.rain-stick";
            public static final String RATTLE_RATCHET = "rattle.ratchet";
            public static final String RATTLE_RATTLE = "rattle.rattle";
            public static final String RATTLE_SHAKER = "rattle.shaker";
            public static final String RATTLE_SHAKER_EGG = "rattle.shaker.egg";
            public static final String RATTLE_SHEKERE = "rattle.shekere";
            public static final String RATTLE_SISTRE = "rattle.sistre";
            public static final String RATTLE_TELEVI = "rattle.televi";
            public static final String RATTLE_VIBRASLAP = "rattle.vibraslap";
            public static final String RATTLE_WASEMBE = "rattle.wasembe";
            public static final String STRINGS_AJAENG = "strings.ajaeng";
            public static final String STRINGS_ARPEGGIONE = "strings.arpeggione";
            public static final String STRINGS_BARYTON = "strings.baryton";
            public static final String STRINGS_CELLO = "strings.cello";
            public static final String STRINGS_CELLO_PICCOLO = "strings.cello.piccolo";
            public static final String STRINGS_CONTRABASS = "strings.contrabass";
            public static final String STRINGS_CRWTH = "strings.crwth";
            public static final String STRINGS_DAN_GAO = "strings.dan-gao";
            public static final String STRINGS_DIHU = "strings.dihu";
            public static final String STRINGS_ERHU = "strings.erhu";
            public static final String STRINGS_ERXIAN = "strings.erxian";
            public static final String STRINGS_ESRAJ = "strings.esraj";
            public static final String STRINGS_FIDDLE = "strings.fiddle";
            public static final String STRINGS_FIDDLE_HARDANGER = "strings.fiddle.hardanger";
            public static final String STRINGS_GADULKA = "strings.gadulka";
            public static final String STRINGS_GAOHU = "strings.gaohu";
            public static final String STRINGS_GEHU = "strings.gehu";
            public static final String STRINGS_GROUP = "strings.group";
            public static final String STRINGS_GROUP_SYNTH = "strings.group.synth";
            public static final String STRINGS_HAEGEUM = "strings.haegeum";
            public static final String STRINGS_HURDY_GURDY = "strings.hurdy-gurdy";
            public static final String STRINGS_IGIL = "strings.igil";
            public static final String STRINGS_KAMANCHA = "strings.kamancha";
            public static final String STRINGS_KOKYU = "strings.kokyu";
            public static final String STRINGS_LARUAN = "strings.laruan";
            public static final String STRINGS_LEIQIN = "strings.leiqin";
            public static final String STRINGS_LIRONE = "strings.lirone";
            public static final String STRINGS_LYRA_BYZANTINE = "strings.lyra.byzantine";
            public static final String STRINGS_LYRA_CRETAN = "strings.lyra.cretan";
            public static final String STRINGS_MORIN_KHUUR = "strings.morin-khuur";
            public static final String STRINGS_NYCKELHARPA = "strings.nyckelharpa";
            public static final String STRINGS_OCTOBASS = "strings.octobass";
            public static final String STRINGS_REBAB = "strings.rebab";
            public static final String STRINGS_REBEC = "strings.rebec";
            public static final String STRINGS_SARANGI = "strings.sarangi";
            public static final String STRINGS_STROH_VIOLIN = "strings.stroh-violin";
            public static final String STRINGS_TROMBA_MARINA = "strings.tromba-marina";
            public static final String STRINGS_VIELLE = "strings.vielle";
            public static final String STRINGS_VIOL = "strings.viol";
            public static final String STRINGS_VIOL_ALTO = "strings.viol.alto";
            public static final String STRINGS_VIOL_BASS = "strings.viol.bass";
            public static final String STRINGS_VIOL_TENOR = "strings.viol.tenor";
            public static final String STRINGS_VIOL_TREBLE = "strings.viol.treble";
            public static final String STRINGS_VIOL_VIOLONE = "strings.viol.violone";
            public static final String STRINGS_VIOLA = "strings.viola";
            public static final String STRINGS_VIOLA_DAMORE = "strings.viola-damore";
            public static final String STRINGS_VIOLIN = "strings.violin";
            public static final String STRINGS_VIOLONO_PICCOLO = "strings.violono.piccolo";
            public static final String STRINGS_VIOLOTTA = "strings.violotta";
            public static final String STRINGS_YAYLI_TANBUR = "strings.yayli-tanbur";
            public static final String STRINGS_YAZHENG = "strings.yazheng";
            public static final String STRINGS_ZHONGHU = "strings.zhonghu";
            public static final String SYNTH_EFFECTS = "synth.effects";
            public static final String SYNTH_EFFECTS_ATMOSPHERE = "synth.effects.atmosphere";
            public static final String SYNTH_EFFECTS_BRIGHTNESS = "synth.effects.brightness";
            public static final String SYNTH_EFFECTS_CRYSTAL = "synth.effects.crystal";
            public static final String SYNTH_EFFECTS_ECHOES = "synth.effects.echoes";
            public static final String SYNTH_EFFECTS_GOBLINS = "synth.effects.goblins";
            public static final String SYNTH_EFFECTS_RAIN = "synth.effects.rain";
            public static final String SYNTH_EFFECTS_SCI_FI = "synth.effects.sci-fi";
            public static final String SYNTH_EFFECTS_SOUNDTRACK = "synth.effects.soundtrack";
            public static final String SYNTH_GROUP = "synth.group";
            public static final String SYNTH_GROUP_FIFTHS = "synth.group.fifths";
            public static final String SYNTH_GROUP_ORCHESTRA = "synth.group.orchestra";
            public static final String SYNTH_PAD = "synth.pad";
            public static final String SYNTH_PAD_BOWED = "synth.pad.bowed";
            public static final String SYNTH_PAD_CHOIR = "synth.pad.choir";
            public static final String SYNTH_PAD_HALO = "synth.pad.halo";
            public static final String SYNTH_PAD_METALLIC = "synth.pad.metallic";
            public static final String SYNTH_PAD_POLYSYNTH = "synth.pad.polysynth";
            public static final String SYNTH_PAD_SWEEP = "synth.pad.sweep";
            public static final String SYNTH_PAD_WARM = "synth.pad.warm";
            public static final String SYNTH_THEREMIN = "synth.theremin";
            public static final String SYNTH_TONE_SAWTOOTH = "synth.tone.sawtooth";
            public static final String SYNTH_TONE_SINE = "synth.tone.sine";
            public static final String SYNTH_TONE_SQUARE = "synth.tone.square";
            public static final String VOICE_AA = "voice.aa";
            public static final String VOICE_ALTO = "voice.alto";
            public static final String VOICE_AW = "voice.aw";
            public static final String VOICE_BARITONE = "voice.baritone";
            public static final String VOICE_BASS = "voice.bass";
            public static final String VOICE_CHILD = "voice.child";
            public static final String VOICE_COUNTERTENOR = "voice.countertenor";
            public static final String VOICE_DOO = "voice.doo";
            public static final String VOICE_EE = "voice.ee";
            public static final String VOICE_FEMALE = "voice.female";
            public static final String VOICE_KAZOO = "voice.kazoo";
            public static final String VOICE_MALE = "voice.male";
            public static final String VOICE_MEZZO_SOPRANO = "voice.mezzo-soprano";
            public static final String VOICE_MM = "voice.mm";
            public static final String VOICE_OO = "voice.oo";
            public static final String VOICE_PERCUSSION = "voice.percussion";
            public static final String VOICE_PERCUSSION_BEATBOX = "voice.percussion.beatbox";
            public static final String VOICE_SOPRANO = "voice.soprano";
            public static final String VOICE_SYNTH = "voice.synth";
            public static final String VOICE_TALK_BOX = "voice.talk-box";
            public static final String VOICE_TENOR = "voice.tenor";
            public static final String VOICE_VOCALS = "voice.vocals";
            public static final String WIND_FLUTES_BANSURI = "wind.flutes.bansuri";
            public static final String WIND_FLUTES_BLOWN_BOTTLE = "wind.flutes.blown-bottle";
            public static final String WIND_FLUTES_CALLIOPE = "wind.flutes.calliope";
            public static final String WIND_FLUTES_DANSO = "wind.flutes.danso";
            public static final String WIND_FLUTES_DI_ZI = "wind.flutes.di-zi";
            public static final String WIND_FLUTES_DVOJNICE = "wind.flutes.dvojnice";
            public static final String WIND_FLUTES_FIFE = "wind.flutes.fife";
            public static final String WIND_FLUTES_FLAGEOLET = "wind.flutes.flageolet";
            public static final String WIND_FLUTES_FLUTE = "wind.flutes.flute";
            public static final String WIND_FLUTES_FLUTE_ALTO = "wind.flutes.flute.alto";
            public static final String WIND_FLUTES_FLUTE_BASS = "wind.flutes.flute.bass";
            public static final String WIND_FLUTES_FLUTE_CONTRA_ALTO = "wind.flutes.flute.contra-alto";
            public static final String WIND_FLUTES_FLUTE_CONTRABASS = "wind.flutes.flute.contrabass";
            public static final String WIND_FLUTES_FLUTE_DOUBLE_CONTRABASS = "wind.flutes.flute.double-contrabass";
            public static final String WIND_FLUTES_FLUTE_IRISH = "wind.flutes.flute.irish";
            public static final String WIND_FLUTES_FLUTE_PICCOLO = "wind.flutes.flute.piccolo";
            public static final String WIND_FLUTES_FLUTE_SUBCONTRABASS = "wind.flutes.flute.subcontrabass";
            public static final String WIND_FLUTES_FUJARA = "wind.flutes.fujara";
            public static final String WIND_FLUTES_GEMSHORN = "wind.flutes.gemshorn";
            public static final String WIND_FLUTES_HOCCHIKU = "wind.flutes.hocchiku";
            public static final String WIND_FLUTES_HUN = "wind.flutes.hun";
            public static final String WIND_FLUTES_KAVAL = "wind.flutes.kaval";
            public static final String WIND_FLUTES_KAWALA = "wind.flutes.kawala";
            public static final String WIND_FLUTES_KHLUI = "wind.flutes.khlui";
            public static final String WIND_FLUTES_KNOTWEED = "wind.flutes.knotweed";
            public static final String WIND_FLUTES_KONCOVKA_ALTO = "wind.flutes.koncovka.alto";
            public static final String WIND_FLUTES_KOUDI = "wind.flutes.koudi";
            public static final String WIND_FLUTES_NEY = "wind.flutes.ney";
            public static final String WIND_FLUTES_NOHKAN = "wind.flutes.nohkan";
            public static final String WIND_FLUTES_NOSE = "wind.flutes.nose";
            public static final String WIND_FLUTES_OCARINA = "wind.flutes.ocarina";
            public static final String WIND_FLUTES_OVERTONE_TENOR = "wind.flutes.overtone.tenor";
            public static final String WIND_FLUTES_PALENDAG = "wind.flutes.palendag";
            public static final String WIND_FLUTES_PANPIPES = "wind.flutes.panpipes";
            public static final String WIND_FLUTES_QUENA = "wind.flutes.quena";
            public static final String WIND_FLUTES_RECORDER = "wind.flutes.recorder";
            public static final String WIND_FLUTES_RECORDER_ALTO = "wind.flutes.recorder.alto";
            public static final String WIND_FLUTES_RECORDER_BASS = "wind.flutes.recorder.bass";
            public static final String WIND_FLUTES_RECORDER_CONTRABASS = "wind.flutes.recorder.contrabass";
            public static final String WIND_FLUTES_RECORDER_DESCANT = "wind.flutes.recorder.descant";
            public static final String WIND_FLUTES_RECORDER_GARKLEIN = "wind.flutes.recorder.garklein";
            public static final String WIND_FLUTES_RECORDER_GREAT_BASS = "wind.flutes.recorder.great-bass";
            public static final String WIND_FLUTES_RECORDER_SOPRANINO = "wind.flutes.recorder.sopranino";
            public static final String WIND_FLUTES_RECORDER_SOPRANO = "wind.flutes.recorder.soprano";
            public static final String WIND_FLUTES_RECORDER_TENOR = "wind.flutes.recorder.tenor";
            public static final String WIND_FLUTES_RYUTEKI = "wind.flutes.ryuteki";
            public static final String WIND_FLUTES_SHAKUHACHI = "wind.flutes.shakuhachi";
            public static final String WIND_FLUTES_SHEPHERDS_PIPE = "wind.flutes.shepherds-pipe";
            public static final String WIND_FLUTES_SHINOBUE = "wind.flutes.shinobue";
            public static final String WIND_FLUTES_SHVI = "wind.flutes.shvi";
            public static final String WIND_FLUTES_SULING = "wind.flutes.suling";
            public static final String WIND_FLUTES_TARKA = "wind.flutes.tarka";
            public static final String WIND_FLUTES_TUMPONG = "wind.flutes.tumpong";
            public static final String WIND_FLUTES_VENU = "wind.flutes.venu";
            public static final String WIND_FLUTES_WHISTLE = "wind.flutes.whistle";
            public static final String WIND_FLUTES_WHISTLE_ALTO = "wind.flutes.whistle.alto";
            public static final String WIND_FLUTES_WHISTLE_LOW_IRISH = "wind.flutes.whistle.low-irish";
            public static final String WIND_FLUTES_WHISTLE_SHIVA = "wind.flutes.whistle.shiva";
            public static final String WIND_FLUTES_WHISTLE_SLIDE = "wind.flutes.whistle.slide";
            public static final String WIND_FLUTES_WHISTLE_TIN = "wind.flutes.whistle.tin";
            public static final String WIND_FLUTES_WHISTLE_TIN_BFLAT = "wind.flutes.whistle.tin.bflat";
            public static final String WIND_FLUTES_WHISTLE_TIN_D = "wind.flutes.whistle.tin.d";
            public static final String WIND_FLUTES_XIAO = "wind.flutes.xiao";
            public static final String WIND_FLUTES_XUN = "wind.flutes.xun";
            public static final String WIND_GROUP = "wind.group";
            public static final String WIND_JUG = "wind.jug";
            public static final String WIND_PIPES_BAGPIPES = "wind.pipes.bagpipes";
            public static final String WIND_PIPES_GAIDA = "wind.pipes.gaida";
            public static final String WIND_PIPES_HIGHLAND = "wind.pipes.highland";
            public static final String WIND_PIPES_UILLEANN = "wind.pipes.uilleann";
            public static final String WIND_PUNGI = "wind.pungi";
            public static final String WIND_REED_ALBOGUE = "wind.reed.albogue";
            public static final String WIND_REED_ALBOKA = "wind.reed.alboka";
            public static final String WIND_REED_ALGAITA = "wind.reed.algaita";
            public static final String WIND_REED_ARGHUL = "wind.reed.arghul";
            public static final String WIND_REED_BASSET_HORN = "wind.reed.basset-horn";
            public static final String WIND_REED_BASSOON = "wind.reed.bassoon";
            public static final String WIND_REED_BAWU = "wind.reed.bawu";
            public static final String WIND_REED_BIFORA = "wind.reed.bifora";
            public static final String WIND_REED_BOMBARDE = "wind.reed.bombarde";
            public static final String WIND_REED_CHALUMEAU = "wind.reed.chalumeau";
            public static final String WIND_REED_CLARINET = "wind.reed.clarinet";
            public static final String WIND_REED_CLARINET_A = "wind.reed.clarinet.a";
            public static final String WIND_REED_CLARINET_ALTO = "wind.reed.clarinet.alto";
            public static final String WIND_REED_CLARINET_BASS = "wind.reed.clarinet.bass";
            public static final String WIND_REED_CLARINET_BASSET = "wind.reed.clarinet.basset";
            public static final String WIND_REED_CLARINET_BFLAT = "wind.reed.clarinet.bflat";
            public static final String WIND_REED_CLARINET_CONTRA_ALTO = "wind.reed.clarinet.contra-alto";
            public static final String WIND_REED_CLARINET_CONTRABASS = "wind.reed.clarinet.contrabass";
            public static final String WIND_REED_CLARINET_EFLAT = "wind.reed.clarinet.eflat";
            public static final String WIND_REED_CLARINET_PICCOLO_AFLAT = "wind.reed.clarinet.piccolo.aflat";
            public static final String WIND_REED_CLARINETTE_DAMOUR = "wind.reed.clarinette-damour";
            public static final String WIND_REED_CONTRABASS = "wind.reed.contrabass";
            public static final String WIND_REED_CONTRABASSOON = "wind.reed.contrabassoon";
            public static final String WIND_REED_CORNAMUSE = "wind.reed.cornamuse";
            public static final String WIND_REED_CROMORNE = "wind.reed.cromorne";
            public static final String WIND_REED_CRUMHORN = "wind.reed.crumhorn";
            public static final String WIND_REED_CRUMHORN_ALTO = "wind.reed.crumhorn.alto";
            public static final String WIND_REED_CRUMHORN_BASS = "wind.reed.crumhorn.bass";
            public static final String WIND_REED_CRUMHORN_GREAT_BASS = "wind.reed.crumhorn.great-bass";
            public static final String WIND_REED_CRUMHORN_SOPRANO = "wind.reed.crumhorn.soprano";
            public static final String WIND_REED_CRUMHORN_TENOR = "wind.reed.crumhorn.tenor";
            public static final String WIND_REED_DIPLE = "wind.reed.diple";
            public static final String WIND_REED_DIPLICA = "wind.reed.diplica";
            public static final String WIND_REED_DUDUK = "wind.reed.duduk";
            public static final String WIND_REED_DULCIAN = "wind.reed.dulcian";
            public static final String WIND_REED_DULZAINA = "wind.reed.dulzaina";
            public static final String WIND_REED_ENGLISH_HORN = "wind.reed.english-horn";
            public static final String WIND_REED_GUANZI = "wind.reed.guanzi";
            public static final String WIND_REED_HARMONICA = "wind.reed.harmonica";
            public static final String WIND_REED_HARMONICA_BASS = "wind.reed.harmonica.bass";
            public static final String WIND_REED_HECKEL_CLARINA = "wind.reed.heckel-clarina";
            public static final String WIND_REED_HECKELPHONE = "wind.reed.heckelphone";
            public static final String WIND_REED_HECKELPHONE_PICCOLO = "wind.reed.heckelphone.piccolo";
            public static final String WIND_REED_HECKELPHONE_CLARINET = "wind.reed.heckelphone-clarinet";
            public static final String WIND_REED_HICHIRIKI = "wind.reed.hichiriki";
            public static final String WIND_REED_HIRTENSCHALMEI = "wind.reed.hirtenschalmei";
            public static final String WIND_REED_HNE = "wind.reed.hne";
            public static final String WIND_REED_HORNPIPE = "wind.reed.hornpipe";
            public static final String WIND_REED_HOUGUAN = "wind.reed.houguan";
            public static final String WIND_REED_HULUSI = "wind.reed.hulusi";
            public static final String WIND_REED_JOGI_BAJA = "wind.reed.jogi-baja";
            public static final String WIND_REED_KEN_BAU = "wind.reed.ken-bau";
            public static final String WIND_REED_KHAEN_MOUTH_ORGAN = "wind.reed.khaen-mouth-organ";
            public static final String WIND_REED_LAUNEDDAS = "wind.reed.launeddas";
            public static final String WIND_REED_MAQRUNAH = "wind.reed.maqrunah";
            public static final String WIND_REED_MELODICA = "wind.reed.melodica";
            public static final String WIND_REED_MIJWIZ = "wind.reed.mijwiz";
            public static final String WIND_REED_MIZMAR = "wind.reed.mizmar";
            public static final String WIND_REED_NADASWARAM = "wind.reed.nadaswaram";
            public static final String WIND_REED_OBOE = "wind.reed.oboe";
            public static final String WIND_REED_OBOE_BASS = "wind.reed.oboe.bass";
            public static final String WIND_REED_OBOE_PICCOLO = "wind.reed.oboe.piccolo";
            public static final String WIND_REED_OBOE_DA_CACCIA = "wind.reed.oboe-da-caccia";
            public static final String WIND_REED_OBOE_DAMORE = "wind.reed.oboe-damore";
            public static final String WIND_REED_OCTAVIN = "wind.reed.octavin";
            public static final String WIND_REED_PI = "wind.reed.pi";
            public static final String WIND_REED_PIBGORN = "wind.reed.pibgorn";
            public static final String WIND_REED_PIRI = "wind.reed.piri";
            public static final String WIND_REED_RACKETT = "wind.reed.rackett";
            public static final String WIND_REED_RAUSCHPFEIFE = "wind.reed.rauschpfeife";
            public static final String WIND_REED_RHAITA = "wind.reed.rhaita";
            public static final String WIND_REED_ROTHPHONE = "wind.reed.rothphone";
            public static final String WIND_REED_SARRUSAPHONE = "wind.reed.sarrusaphone";
            public static final String WIND_REED_SAXONETTE = "wind.reed.saxonette";
            public static final String WIND_REED_SAXOPHONE = "wind.reed.saxophone";
            public static final String WIND_REED_SAXOPHONE_ALTO = "wind.reed.saxophone.alto";
            public static final String WIND_REED_SAXOPHONE_AULOCHROME = "wind.reed.saxophone.aulochrome";
            public static final String WIND_REED_SAXOPHONE_BARITONE = "wind.reed.saxophone.baritone";
            public static final String WIND_REED_SAXOPHONE_BASS = "wind.reed.saxophone.bass";
            public static final String WIND_REED_SAXOPHONE_CONTRABASS = "wind.reed.saxophone.contrabass";
            public static final String WIND_REED_SAXOPHONE_MELODY = "wind.reed.saxophone.melody";
            public static final String WIND_REED_SAXOPHONE_MEZZO_SOPRANO = "wind.reed.saxophone.mezzo-soprano";
            public static final String WIND_REED_SAXOPHONE_SOPRANINO = "wind.reed.saxophone.sopranino";
            public static final String WIND_REED_SAXOPHONE_SOPRANISSIMO = "wind.reed.saxophone.sopranissimo";
            public static final String WIND_REED_SAXOPHONE_SOPRANO = "wind.reed.saxophone.soprano";
            public static final String WIND_REED_SAXOPHONE_SUBCONTRABASS = "wind.reed.saxophone.subcontrabass";
            public static final String WIND_REED_SAXOPHONE_TENOR = "wind.reed.saxophone.tenor";
            public static final String WIND_REED_SHAWM = "wind.reed.shawm";
            public static final String WIND_REED_SHENAI = "wind.reed.shenai";
            public static final String WIND_REED_SHENG = "wind.reed.sheng";
            public static final String WIND_REED_SIPSI = "wind.reed.sipsi";
            public static final String WIND_REED_SOPILA = "wind.reed.sopila";
            public static final String WIND_REED_SORNA = "wind.reed.sorna";
            public static final String WIND_REED_SRALAI = "wind.reed.sralai";
            public static final String WIND_REED_SUONA = "wind.reed.suona";
            public static final String WIND_REED_SURNAI = "wind.reed.surnai";
            public static final String WIND_REED_TAEPYEONGSO = "wind.reed.taepyeongso";
            public static final String WIND_REED_TAROGATO = "wind.reed.tarogato";
            public static final String WIND_REED_TAROGATO_ANCIENT = "wind.reed.tarogato.ancient";
            public static final String WIND_REED_TROMPETA_CHINA = "wind.reed.trompeta-china";
            public static final String WIND_REED_TUBAX = "wind.reed.tubax";
            public static final String WIND_REED_XAPHOON = "wind.reed.xaphoon";
            public static final String WIND_REED_ZHALEIKA = "wind.reed.zhaleika";
            public static final String WIND_REED_ZURLA = "wind.reed.zurla";
            public static final String WIND_REED_ZURNA = "wind.reed.zurna";
            public static final String WOOD_AGOGO_BLOCK = "wood.agogo-block";
            public static final String WOOD_AGUNG_A_TAMLANG = "wood.agung-a-tamlang";
            public static final String WOOD_AHOKO = "wood.ahoko";
            public static final String WOOD_BONES = "wood.bones";
            public static final String WOOD_CASTANETS = "wood.castanets";
            public static final String WOOD_CLAVES = "wood.claves";
            public static final String WOOD_DRUM_STICKS = "wood.drum-sticks";
            public static final String WOOD_GOURD = "wood.gourd";
            public static final String WOOD_GRANITE_BLOCK = "wood.granite-block";
            public static final String WOOD_GUBAN = "wood.guban";
            public static final String WOOD_GUIRO = "wood.guiro";
            public static final String WOOD_HYOUSHIGI = "wood.hyoushigi";
            public static final String WOOD_IPU = "wood.ipu";
            public static final String WOOD_JAM_BLOCK = "wood.jam-block";
            public static final String WOOD_KAEKEEKE = "wood.kaekeeke";
            public static final String WOOD_KAGUL = "wood.kagul";
            public static final String WOOD_KALAAU = "wood.kalaau";
            public static final String WOOD_KASHIKLAR = "wood.kashiklar";
            public static final String WOOD_KUBING = "wood.kubing";
            public static final String WOOD_PAN_CLAPPERS = "wood.pan-clappers";
            public static final String WOOD_SAND_BLOCK = "wood.sand-block";
            public static final String WOOD_SLAPSTICK = "wood.slapstick";
            public static final String WOOD_STIR_DRUM = "wood.stir-drum";
            public static final String WOOD_TEMPLE_BLOCK = "wood.temple-block";
            public static final String WOOD_TIC_TOC_BLOCK = "wood.tic-toc-block";
            public static final String WOOD_TONETANG = "wood.tonetang";
            public static final String WOOD_WOOD_BLOCK = "wood.wood-block";
        }
    }
}