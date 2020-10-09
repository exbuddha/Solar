package music.system.data;

import static music.system.data.Constant.MusicXML.*;
import static system.data.Constant.XML.Entity.*;

import java.util.LinkedList;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import system.data.Cache;
import system.data.Dictionary;
import system.data.Lambda;
import system.data.XML;

/**
 * {@code MusicXML} classifies a MusicXML document.
 * <p/>
 * This class defines static members for validating and traversing MusicXML documents.
 * <p/>
 * Copyright © 2004-2017 the Contributors to the MusicXML Specification, published by the W3C Music Notation Community Group under the W3C Community Final Specification Agreement (FSA):
 * <p/>
 * <a href="https://www.w3.org/community/about/agreements/final/">https://www.w3.org/community/about/agreements/final/</a>
 * <p/>
 * A human-readable summary is available:
 * <p/>
 * <a href="https://www.w3.org/community/about/agreements/fsa-deed/">https://www.w3.org/community/about/agreements/fsa-deed/</a>
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
        final DocumentHandler handler
        ) {
        super(handler);
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
        final java.util.function.Function<org.w3c.dom.Document, Validation> validation
        ) {
        super(new Handler.Standard(document));
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
     * Creates an empty MusicXML document using the standard implementation of {@link Handler}.
     */
    public
    MusicXML() {
        super(new Handler.Standard((org.w3c.dom.Document) Element.of(newDocumentBuilder().newDocument())));
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
     * {@code Element} classifies all MusicXML elements.
     *
     * @see XML.Element.Schematic
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Element
    extends
        XML.Element,
        XML.Element.Schematic
    {
        /** The mapping of element names to class types. */
        Dictionary<Class<? extends Element>> ClassMap = new Dictionary<Class<? extends Element>>()
        .map(ACCENT).to(Accent.class)
        .map(ACCIDENTAL).to(Accidental.class)
        .map(ACCIDENTAL_MARK).to(AccidentalMark.class)
        .map(ACCIDENTAL_TEXT).to(AccidentalText.class)
        .map(ACCORD).to(Accord.class)
        .map(ACCORDION_HIGH).to(AccordionHigh.class)
        .map(ACCORDION_LOW).to(AccordionLow.class)
        .map(ACCORDION_MIDDLE).to(AccordionMiddle.class)
        .map(ACCORDION_REGISTRATION).to(AccordionRegistration.class)
        .map(ACTUAL_NOTES).to(ActualNotes.class)
        .map(ALTER).to(Alter.class)
        .map(APPEARANCE).to(Appearance.class)
        .map(ARPEGGIATE).to(Arpeggiate.class)
        .map(ARROW).to(Arrow.class)
        .map(ARROWHEAD).to(Arrowhead.class)
        .map(ARROW_DIRECTION).to(ArrowDirection.class)
        .map(ARROW_STYLE).to(ArrowStyle.class)
        .map(ARTIFICIAL).to(Artificial.class)
        .map(ATTRIBUTES).to(Attributes.class)
        .map(BACKUP).to(Backup.class)
        .map(BARLINE).to(Barline.class)
        .map(BARRE).to(Barre.class)
        .map(BAR_STYLE).to(BarStyle.class)
        .map(BASE_PITCH).to(BasePitch.class)
        .map(BASS).to(Bass.class)
        .map(BASS_ALTER).to(BassAlter.class)
        .map(BASS_STEP).to(BassStep.class)
        .map(BEAM).to(Beam.class)
        .map(BEATER).to(Beater.class)
        .map(BEATS).to(Beats.class)
        .map(BEAT_REPEAT).to(BeatRepeat.class)
        .map(BEAT_TYPE).to(BeatType.class)
        .map(BEAT_UNIT).to(BeatUnit.class)
        .map(BEAT_UNIT_DOT).to(BeatUnitDot.class)
        .map(BEAT_UNIT_TIED).to(BeatUnitTied.class)
        .map(BEND).to(Bend.class)
        .map(BEND_ALTER).to(BendAlter.class)
        .map(BOOKMARK).to(Bookmark.class)
        .map(BOTTOM_MARGIN).to(BottomMargin.class)
        .map(BRACKET).to(Bracket.class)
        .map(BRASS_BEND).to(BrassBend.class)
        .map(BREATH_MARK).to(BreathMark.class)
        .map(CAESURA).to(Caesura.class)
        .map(CANCEL).to(Cancel.class)
        .map(CHORD).to(Chord.class)
        .map(CHROMATIC).to(Chromatic.class)
        .map(CIRCULAR_ARROW).to(CircularArrow.class)
        .map(CLEF).to(Clef.class)
        .map(CLEF_OCTAVE_CHANGE).to(ClefOctaveChange.class)
        .map(CODA).to(Coda.class)
        .map(CREATOR).to(Creator.class)
        .map(CREDIT).to(Credit.class)
        .map(CREDIT_IMAGE).to(CreditImage.class)
        .map(CREDIT_SYMBOL).to(CreditSymbol.class)
        .map(CREDIT_TYPE).to(CreditType.class)
        .map(CREDIT_WORDS).to(CreditWords.class)
        .map(CUE).to(Cue.class)
        .map(DAMP).to(Damp.class)
        .map(DAMP_ALL).to(DampAll.class)
        .map(DASHES).to(Dashes.class)
        .map(DEFAULTS).to(Defaults.class)
        .map(DEGREE).to(Degree.class)
        .map(DEGREE_ALTER).to(DegreeAlter.class)
        .map(DEGREE_TYPE).to(DegreeType.class)
        .map(DEGREE_VALUE).to(DegreeValue.class)
        .map(DELAYED_INVERTED_TURN).to(DelayedInvertedTurn.class)
        .map(DELAYED_TURN).to(DelayedTurn.class)
        .map(DETACHED_LEGATO).to(DetachedLegato.class)
        .map(DIATONIC).to(Diatonic.class)
        .map(DIRECTION).to(Direction.class)
        .map(DIRECTION_TYPE).to(DirectionType.class)
        .map(DIRECTIVE).to(Directive.class)
        .map(DISPLAY_OCTAVE).to(DisplayOctave.class)
        .map(DISPLAY_STEP).to(DisplayStep.class)
        .map(DISPLAY_TEXT).to(DisplayText.class)
        .map(DISTANCE).to(Distance.class)
        .map(DIVISIONS).to(Divisions.class)
        .map(DOIT).to(Doit.class)
        .map(DOT).to(Dot.class)
        .map(DOUBLE).to(Double.class)
        .map(DOUBLE_TONGUE).to(DoubleTongue.class)
        .map(DOWN_BOW).to(DownBow.class)
        .map(DURATION).to(Duration.class)
        .map(DYNAMICS).to(Dynamics.class)
        .map(EFFECT).to(Effect.class)
        .map(ELEVATION).to(Elevation.class)
        .map(ELISION).to(Elision.class)
        .map(ENCODER).to(Encoder.class)
        .map(ENCODING).to(Encoding.class)
        .map(ENCODING_DATE).to(EncodingDate.class)
        .map(ENCODING_DESCRIPTION).to(EncodingDescription.class)
        .map(ENDING).to(Ending.class)
        .map(END_LINE).to(EndLine.class)
        .map(END_PARAGRAPH).to(EndParagraph.class)
        .map(ENSEMBLE).to(Ensemble.class)
        .map(EXCEPT_VOICE).to(ExceptVoice.class)
        .map(EXTEND).to(Extend.class)
        .map(EYEGLASSES).to(Eyeglasses.class)
        .map(Constant.MusicXML.Dynamics.F).to(F.class)
        .map(FALLOFF).to(Falloff.class)
        .map(FEATURE).to(Feature.class)
        .map(FERMATA).to(Fermata.class)
        .map(Constant.MusicXML.Dynamics.FF).to(FF.class)
        .map(Constant.MusicXML.Dynamics.FFF).to(FFF.class)
        .map(Constant.MusicXML.Dynamics.FFFF).to(FFFF.class)
        .map(Constant.MusicXML.Dynamics.FFFFF).to(FFFFF.class)
        .map(Constant.MusicXML.Dynamics.FFFFFF).to(FFFFFF.class)
        .map(FIFTHS).to(Fifths.class)
        .map(FIGURE).to(Figure.class)
        .map(FIGURED_BASS).to(FiguredBass.class)
        .map(FIGURE_NUMBER).to(FigureNumber.class)
        .map(FINGERING).to(Fingering.class)
        .map(FINGERNAILS).to(Fingernails.class)
        .map(FIRST_FRET).to(FirstFret.class)
        .map(FLIP).to(Flip.class)
        .map(FOOTNOTE).to(Footnote.class)
        .map(FORWARD).to(Forward.class)
        .map(Constant.MusicXML.Dynamics.FP).to(FP.class)
        .map(FRAME).to(Frame.class)
        .map(FRAME_FRETS).to(FrameFrets.class)
        .map(FRAME_NOTE).to(FrameNote.class)
        .map(FRAME_STRINGS).to(FrameStrings.class)
        .map(FUNCTION).to(Function.class)
        .map(Constant.MusicXML.Dynamics.FZ).to(FZ.class)
        .map(GLASS).to(Glass.class)
        .map(GLISSANDO).to(Glissando.class)
        .map(GLYPH).to(Glyph.class)
        .map(GOLPE).to(Golpe.class)
        .map(GRACE).to(Grace.class)
        .map(GROUP).to(Group.class)
        .map(GROUPING).to(Grouping.class)
        .map(GROUP_ABBREVIATION).to(GroupAbbreviation.class)
        .map(GROUP_ABBREVIATION_DISPLAY).to(GroupAbbreviationDisplay.class)
        .map(GROUP_BARLINE).to(GroupBarline.class)
        .map(GROUP_NAME).to(GroupName.class)
        .map(GROUP_NAME_DISPLAY).to(GroupNameDisplay.class)
        .map(GROUP_SYMBOL).to(GroupSymbol.class)
        .map(GROUP_TIME).to(GroupTime.class)
        .map(HALF_MUTED).to(HalfMuted.class)
        .map(HARMONY).to(Harmony.class)
        .map(HARMON_CLOSED).to(HarmonClosed.class)
        .map(HARMON_MUTE).to(HarmonMute.class)
        .map(HAMMER_ON).to(HammerOn.class)
        .map(HANDBELL).to(Handbell.class)
        .map(HARMONIC).to(Harmonic.class)
        .map(HARP_PEDALS).to(HarpPedals.class)
        .map(HAYDN).to(Haydn.class)
        .map(HEEL).to(Heel.class)
        .map(HOLE).to(Hole.class)
        .map(HOLE_CLOSED).to(HoleClosed.class)
        .map(HOLE_SHAPE).to(HoleShape.class)
        .map(HOLE_TYPE).to(HoleType.class)
        .map(HUMMING).to(Humming.class)
        .map(IDENTIFICATION).to(Identification.class)
        .map(IMAGE).to(Image.class)
        .map(INSTRUMENT).to(Instrument.class)
        .map(INSTRUMENTS).to(Instruments.class)
        .map(INSTRUMENT_ABBREVIATION).to(InstrumentAbbreviation.class)
        .map(INSTRUMENT_NAME).to(InstrumentName.class)
        .map(INSTRUMENT_SOUND).to(InstrumentSound.class)
        .map(INTERCHANGEABLE).to(Interchangeable.class)
        .map(INVERSION).to(Inversion.class)
        .map(INVERTED_MORDENT).to(InvertedMordent.class)
        .map(INVERTED_TURN).to(InvertedTurn.class)
        .map(INVERTED_VERTICAL_TURN).to(InvertedVerticalTurn.class)
        .map(IPA).to(IPA.class)
        .map(KEY).to(Key.class)
        .map(KEY_ACCIDENTAL).to(KeyAccidental.class)
        .map(KEY_ALTER).to(KeyAlter.class)
        .map(KEY_OCTAVE).to(KeyOctave.class)
        .map(KEY_STEP).to(KeyStep.class)
        .map(KIND).to(Kind.class)
        .map(LAUGHING).to(Laughing.class)
        .map(LEFT_DIVIDER).to(LeftDivider.class)
        .map(LEFT_MARGIN).to(LeftMargin.class)
        .map(LEVEL).to(Level.class)
        .map(LINE).to(Line.class)
        .map(LINE_WIDTH).to(LineWidth.class)
        .map(LINK).to(Link.class)
        .map(LYRIC).to(Lyric.class)
        .map(LYRIC_FONT).to(LyricFont.class)
        .map(LYRIC_LANGUAGE).to(LyricLanguage.class)
        .map(MEASURE).to(Measure.class)
        .map(MEASURE_DISTANCE).to(MeasureDistance.class)
        .map(MEASURE_LAYOUT).to(MeasureLayout.class)
        .map(MEASURE_NUMBERING).to(MeasureNumbering.class)
        .map(MEASURE_REPEAT).to(MeasureRepeat.class)
        .map(MEASURE_STYLE).to(MeasureStyle.class)
        .map(MEMBRANE).to(Membrane.class)
        .map(METAL).to(Metal.class)
        .map(METRONOME).to(Metronome.class)
        .map(METRONOME_ARROWS).to(MetronomeArrows.class)
        .map(METRONOME_BEAM).to(MetronomeBeam.class)
        .map(METRONOME_DOT).to(MetronomeDot.class)
        .map(METRONOME_NOTE).to(MetronomeNote.class)
        .map(METRONOME_RELATION).to(MetronomeRelation.class)
        .map(METRONOME_TIED).to(MetronomeTied.class)
        .map(METRONOME_TUPLET).to(MetronomeTuplet.class)
        .map(METRONOME_TYPE).to(MetronomeType.class)
        .map(Constant.MusicXML.Dynamics.MF).to(MF.class)
        .map(MIDI_BANK).to(MidiBank.class)
        .map(MIDI_CHANNEL).to(MidiChannel.class)
        .map(MIDI_DEVICE).to(MidiDevice.class)
        .map(MIDI_INSTRUMENT).to(MidiInstrument.class)
        .map(MIDI_NAME).to(MidiName.class)
        .map(MIDI_PROGRAM).to(MidiProgram.class)
        .map(MIDI_UNPITCHED).to(MidiUnpitched.class)
        .map(MILLIMETERS).to(Millimeters.class)
        .map(MISCELLANEOUS).to(Miscellaneous.class)
        .map(MISCELLANEOUS_FIELD).to(MiscellaneousField.class)
        .map(MODE).to(Mode.class)
        .map(MORDENT).to(Mordent.class)
        .map(MOVEMENT_NUMBER).to(MovementNumber.class)
        .map(MOVEMENT_TITLE).to(MovementTitle.class)
        .map(Constant.MusicXML.Dynamics.MP).to(MP.class)
        .map(MULTIPLE_REST).to(MultipleRest.class)
        .map(MUSIC_FONT).to(MusicFont.class)
        .map(MUTE).to(Mute.class)
        .map(Constant.MusicXML.Dynamics.N).to(N.class)
        .map(NATURAL).to(Natural.class)
        .map(NON_ARPEGGIATE).to(NonArpeggiate.class)
        .map(NORMAL_DOT).to(NormalDot.class)
        .map(NORMAL_NOTES).to(NormalNotes.class)
        .map(NORMAL_TYPE).to(NormalType.class)
        .map(NOTATIONS).to(Notations.class)
        .map(NOTE).to(Note.class)
        .map(NOTEHEAD).to(Notehead.class)
        .map(NOTEHEAD_TEXT).to(NoteheadText.class)
        .map(NOTE_SIZE).to(NoteSize.class)
        .map(OCTAVE).to(Octave.class)
        .map(OCTAVE_CHANGE).to(OctaveChange.class)
        .map(OCTAVE_SHIFT).to(OctaveShift.class)
        .map(OPEN).to(Open.class)
        .map(OPEN_STRING).to(OpenString.class)
        .map(OPUS).to(Opus.class)
        .map(OTHER_APPEARANCE).to(OtherAppearance.class)
        .map(OTHER_ARTICULATION).to(OtherArticulation.class)
        .map(OTHER_DIRECTION).to(OtherDirection.class)
        .map(OTHER_DYNAMICS).to(OtherDynamics.class)
        .map(OTHER_NOTATION).to(OtherNotation.class)
        .map(OTHER_ORNAMENT).to(OtherOrnament.class)
        .map(OTHER_PERCUSSION).to(OtherPercussion.class)
        .map(OTHER_PLAY).to(OtherPlay.class)
        .map(OTHER_TECHNICAL).to(OtherTechnical.class)
        .map(Constant.MusicXML.Dynamics.P).to(P.class)
        .map(PAGE_HEIGHT).to(PageHeight.class)
        .map(PAGE_LAYOUT).to(PageLayout.class)
        .map(PAGE_MARGINS).to(PageMargins.class)
        .map(PAGE_WIDTH).to(PageWidth.class)
        .map(PAN).to(Pan.class)
        .map(PART).to(Part.class)
        .map(PART_ABBREVIATION).to(PartAbbreviation.class)
        .map(PART_ABBREVIATION_DISPLAY).to(PartAbbreviationDisplay.class)
        .map(PART_GROUP).to(PartGroup.class)
        .map(PART_LIST).to(PartList.class)
        .map(PART_NAME).to(PartName.class)
        .map(PART_NAME_DISPLAY).to(PartNameDisplay.class)
        .map(PART_SYMBOL).to(PartSymbol.class)
        .map(PEDAL).to(Pedal.class)
        .map(PEDAL_ALTER).to(PedalAlter.class)
        .map(PEDAL_STEP).to(PedalStep.class)
        .map(PEDAL_TUNING).to(PedalTuning.class)
        .map(PERCUSSION).to(Percussion.class)
        .map(PER_MINUTE).to(PerMinute.class)
        .map(Constant.MusicXML.Dynamics.PF).to(PF.class)
        .map(PITCH).to(Pitch.class)
        .map(PITCHED).to(Pitched.class)
        .map(PLAY).to(Play.class)
        .map(PLOP).to(Plop.class)
        .map(PLUCK).to(Pluck.class)
        .map(Constant.MusicXML.Dynamics.PP).to(PP.class)
        .map(Constant.MusicXML.Dynamics.PPP).to(PPP.class)
        .map(Constant.MusicXML.Dynamics.PPPP).to(PPPP.class)
        .map(Constant.MusicXML.Dynamics.PPPPP).to(PPPPP.class)
        .map(Constant.MusicXML.Dynamics.PPPPPP).to(PPPPPP.class)
        .map(PREFIX).to(Prefix.class)
        .map(PRE_BEND).to(PreBend.class)
        .map(PRINCIPAL_VOICE).to(PrincipalVoice.class)
        .map(PRINT).to(Print.class)
        .map(PULL_OFF).to(PullOff.class)
        .map(REHEARSAL).to(Rehearsal.class)
        .map(RELATION).to(Relation.class)
        .map(RELEASE).to(Release.class)
        .map(REPEAT).to(Repeat.class)
        .map(Constant.MusicXML.Dynamics.RF).to(RF.class)
        .map(Constant.MusicXML.Dynamics.RFZ).to(RFZ.class)
        .map(RIGHTS).to(Rights.class)
        .map(RIGHT_DIVIDER).to(RightDivider.class)
        .map(RIGHT_MARGIN).to(RightMargin.class)
        .map(ROOT).to(Root.class)
        .map(ROOT_ALTER).to(RootAlter.class)
        .map(ROOT_STEP).to(RootStep.class)
        .map(SCALING).to(Scaling.class)
        .map(SCHLEIFER).to(Schleifer.class)
        .map(SCOOP).to(Scoop.class)
        .map(SCORDATURA).to(Scordatura.class)
        .map(SCORE_INSTRUMENT).to(ScoreInstrument.class)
        .map(SCORE_PART).to(ScorePart.class)
        .map(SCORE_PARTWISE).to(ScorePartwise.class)
        .map(SCORE_TIMEWISE).to(ScoreTimewise.class)
        .map(SEGNO).to(Segno.class)
        .map(SEMI_PITCHED).to(SemiPitched.class)
        .map(SENZA_MISURA).to(SenzaMisura.class)
        .map(Constant.MusicXML.Dynamics.SF).to(SF.class)
        .map(Constant.MusicXML.Dynamics.SFP).to(SFP.class)
        .map(Constant.MusicXML.Dynamics.SFPP).to(SFPP.class)
        .map(Constant.MusicXML.Dynamics.SFFZ).to(SFFZ.class)
        .map(Constant.MusicXML.Dynamics.SFZ).to(SFZ.class)
        .map(Constant.MusicXML.Dynamics.SFZP).to(SFZP.class)
        .map(SHAKE).to(Shake.class)
        .map(SIGN).to(Sign.class)
        .map(SLASH).to(Slash.class)
        .map(SLASH_DOT).to(SlashDot.class)
        .map(SLASH_TYPE).to(SlashType.class)
        .map(SLIDE).to(Slide.class)
        .map(SLUR).to(Slur.class)
        .map(SMEAR).to(Smear.class)
        .map(SNAP_PIZZICATO).to(SnapPizzicato.class)
        .map(SOFTWARE).to(Software.class)
        .map(SOFT_ACCENT).to(SoftAccent.class)
        .map(SOLO).to(Solo.class)
        .map(SOUND).to(Sound.class)
        .map(SOUNDING_PITCH).to(SoundingPitch.class)
        .map(SOURCE).to(Source.class)
        .map(SPICCATO).to(Spiccato.class)
        .map(STACCATISSIMO).to(Staccatissimo.class)
        .map(STACCATO).to(Staccato.class)
        .map(STAFF).to(Staff.class)
        .map(STAFF_DETAILS).to(StaffDetails.class)
        .map(STAFF_DISTANCE).to(StaffDistance.class)
        .map(STAFF_LAYOUT).to(StaffLayout.class)
        .map(STAFF_SIZE).to(StaffSize.class)
        .map(STAFF_TUNING).to(StaffTuning.class)
        .map(STAFF_TYPE).to(StaffType.class)
        .map(STAFF_LINES).to(StaffLines.class)
        .map(STAVES).to(Staves.class)
        .map(STEM).to(Stem.class)
        .map(STEP).to(Step.class)
        .map(STICK).to(Stick.class)
        .map(STICK_LOCATION).to(StickLocation.class)
        .map(STICK_MATERIAL).to(StickMaterial.class)
        .map(STICK_TYPE).to(StickType.class)
        .map(STOPPED).to(Stopped.class)
        .map(STRESS).to(Stress.class)
        .map(STRING_MUTE).to(StringMute.class)
        .map(STRONG_ACCENT).to(StrongAccent.class)
        .map(SUFFIX).to(Suffix.class)
        .map(SUPPORTS).to(Supports.class)
        .map(SYLLABIC).to(Syllabic.class)
        .map(SYMBOL).to(Symbol.class)
        .map(SYSTEM_DISTANCE).to(SystemDistance.class)
        .map(SYSTEM_DIVIDERS).to(SystemDividers.class)
        .map(SYSTEM_LAYOUT).to(SystemLayout.class)
        .map(SYSTEM_MARGINS).to(SystemMargins.class)
        .map(TECHNICAL).to(Technical.class)
        .map(TEXT).to(Text.class)
        .map(TENTHS).to(Tenths.class)
        .map(TENUTO).to(Tenuto.class)
        .map(THUMB_POSITION).to(ThumbPosition.class)
        .map(TIE).to(Tie.class)
        .map(TIED).to(Tied.class)
        .map(TIME).to(Time.class)
        .map(TIME_MODIFICATION).to(TimeModification.class)
        .map(TIME_RELATION).to(TimeRelation.class)
        .map(TIMPANI).to(Timpani.class)
        .map(TOE).to(Toe.class)
        .map(TOP_MARGIN).to(TopMargin.class)
        .map(TOP_SYSTEM_DISTANCE).to(TopSystemDistance.class)
        .map(TOUCHING_PITCH).to(TouchingPitch.class)
        .map(TRANSPOSE).to(Transpose.class)
        .map(TREMOLO).to(Tremolo.class)
        .map(TRIPLE_TONGUE).to(TripleTongue.class)
        .map(TUNING_ALTER).to(TuningAlter.class)
        .map(TUNING_OCTAVE).to(TuningOctave.class)
        .map(TUNING_STEP).to(TuningStep.class)
        .map(TUPLET).to(Tuplet.class)
        .map(TUPLET_ACTUAL).to(TupletActual.class)
        .map(TUPLET_DOT).to(TupletDot.class)
        .map(TUPLET_NORMAL).to(TupletNormal.class)
        .map(TUPLET_NUMBER).to(TupletNumber.class)
        .map(TUPLET_TYPE).to(TupletType.class)
        .map(TURN).to(Turn.class)
        .map(TYPE).to(Type.class)
        .map(UNPITCHED).to(Unpitched.class)
        .map(UNSTRESS).to(Unstress.class)
        .map(UPBOW).to(UpBow.class)
        .map(VERTICAL_TURN).to(VerticalTurn.class)
        .map(VIRTUAL_INSTRUMENT).to(VirtualInstrument.class)
        .map(VIRTUAL_LIBRARY).to(VirtualLibrary.class)
        .map(VIRTUAL_NAME).to(VirtualName.class)
        .map(VOICE).to(Voice.class)
        .map(VOLUME).to(Volume.class)
        .map(WAVY_LINE).to(WavyLine.class)
        .map(WITH_BAR).to(WithBar.class)
        .map(WEDGE).to(Wedge.class)
        .map(WOOD).to(Wood.class)
        .map(WORDS).to(Words.class)
        .map(WORD_FONT).to(WordFont.class)
        .map(WORK).to(Work.class)
        .map(WORK_NUMBER).to(WorkNumber.class)
        .map(WORK_TITLE).to(WorkTitle.class);

        /** The empty value. */
        java.lang.String[] Empty = new java.lang.String[] {};

        /**
         * Returns a conventional MusicXML element wrapped around the specified traditional XML element as its target.
         *
         * @param target the traditional XML element or a conventional MusicXML element.
         *
         * @return the conventional MusicXML element.
         *
         * @throws IllegalArgumentException if the target is not a standard MusicXML element or document type.
         */
        static
        Element of(
            final Node target
            ) {
            if (target instanceof Document) {
                // make sure to convert to the MusicXML document element type if the document has a root element.
            }
            else {
                // make sure to convert to MusicXML element and to avoid non-MusicXML elements.
            }

            throw new IllegalArgumentException();
        }

        /**
         * Returns true if the element is an analytic type; otherwise returns false.
         *
         * @return true if the element is analytic, or false otherwise.
         */
        default
        boolean isAnalytic() {
            return this instanceof Analytic;
        }

        /**
         * Returns the schematic element type.
         *
         * @return this schematic element type.
         */
        @Override
        default Element object() {
            return this;
        }

        /**
         * {@code Accent} classifies the MusicXML element, {@code accent}.
         * <p/>
         * This element is declared by the element type {@code accent} for the element type {@code articulations} in the note.mod schema file.
         *
         * @see Articulations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Accent
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Accidental} classifies the MusicXML element, {@code accidental}.
         * <p/>
         * This element is declared by the element type {@code accidental} for the element type {@code note} in the note.mod schema file.
         * <p/>
         * Actual notated accidentals.
         * Valid values include: sharp, natural, flat, double-sharp, sharp-sharp, flat-flat, natural-sharp, natural-flat, quarter-flat, quarter-sharp, three-quarters-flat, three-quarters-sharp, sharp-down, sharp-up, natural-down, natural-up, flat-down, flat-up, double-sharp-down, double-sharp-up, flat-flat-down, flat-flat-up, arrow-down, arrow-up, triple-sharp, triple-flat, slash-quarter-sharp, slash-sharp, slash-flat, double-slash-flat, sharp-1, sharp-2, sharp-3, sharp-5, flat-1, flat-2, flat-3, flat-4, sori, koron, and other.
         * <p/>
         * The quarter- and three-quarters- accidentals are Tartini-style quarter-tone accidentals.
         * The -down and -up accidentals are quarter-tone accidentals that include arrows pointing down or up.
         * The slash- accidentals are used in Turkish classical music.
         * The numbered sharp and flat accidentals are superscripted versions of the accidental signs, used in Turkish folk music.
         * The sori and koron accidentals are microtonal sharp and flat accidentals used in Iranian and Persian music.
         * The other accidental covers accidentals other than those listed here.
         * It is usually used in combination with the {@link Element.Attribute.SMuFL smufl} attribute to specify a particular SMuFL accidental.
         * The {@link Element.Attribute.SMuFL smufl} attribute may be used with any accidental value to help specify the appearance of symbols that share the same MusicXML semantics.
         * The attribute value is a SMuFL canonical glyph name that starts with acc.
         * <p/>
         * Editorial and cautionary indications are indicated by attributes.
         * Values for these attributes are "no" if not present.
         * Specific graphic display such as parentheses, brackets, and size are controlled by the {@code level-display} entity defined in the common.mod file.
         *
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Accidental
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Cautionary.class,
                Element.Attribute.Editorial.class,
                Element.Attribute.Parentheses.class,
                Element.Attribute.Bracket.class,
                Element.Attribute.Size.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.SMuFL.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code AccidentalMark} classifies the MusicXML element, {@code accidental-mark}.
         * <p/>
         * This element is declared by the element type {@code accidental-mark} for the element type {@code ornaments} in the note.mod schema file.
         * <p/>
         * An {@code accidental-mark} can be used as a separate notation or as part of an ornament.
         * When used in an ornament, position and placement are relative to the ornament, not relative to the note.
         *
         * @see Ornaments
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface AccidentalMark
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Parentheses.class,
                Element.Attribute.Bracket.class,
                Element.Attribute.Size.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.SMuFL.class,
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code AccidentalText} classifies the MusicXML element, {@code accidental-text}.
         * <p/>
         * This element is declared by the element type {@code accidental-text} for the element types {@code part-name-display} and {@code part-abbreviation-display} in the common.mod schema file; and for the element type {@code notehead-text} in the note.mod schema file.
         *
         * @see NoteheadText
         * @see PartNameDisplay
         * @see PartAbbreviationDisplay
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface AccidentalText
        extends
            Analytic,
            XML.Schematic.PerType
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Justify.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.Underline.class,
                Element.Attribute.Overline.class,
                Element.Attribute.LineThrough.class,
                Element.Attribute.Rotation.class,
                Element.Attribute.LetterSpacing.class,
                Element.Attribute.LineHeight.class,
                Element.Attribute.XmlLang.class,
                Element.Attribute.XmlSpace.class,
                Element.Attribute.Dir.class,
                Element.Attribute.Enclosure.class,
                Element.Attribute.SMuFL.class,
            };

            /** The array of accepted parent element types. */
            Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                NoteheadText.class,
                PartNameDisplay.class,
                PartAbbreviationDisplay.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }

            @Override
            default Class<? extends AccidentalText> per(final Class<?> type) {
                if (type == PartNameDisplay.class ||
                    type == PartAbbreviationDisplay.class)
                    return AccidentalText_PerMany1.class;
                else
                if (type == NoteheadText.class)
                    return AccidentalText_PerNoteheadText.class;

                return null;
            }

            interface AccidentalText_PerNoteheadText
            extends AccidentalText
            {
                @Override
                default java.lang.String occurrence() {
                    return Occurrence.OneOrMore;
                }
            }

            interface AccidentalText_PerMany1
            extends AccidentalText
            {
                @Override
                default java.lang.String occurrence() {
                    return Occurrence.ZeroOrMore;
                }
            }
        }

        /**
         * {@code Accord} classifies the MusicXML element, {@code accord}.
         * <p/>
         * This element is declared by the element type {@code accord} for the element {@code scordatura} in the direction.mod schema file.
         *
         * @see Scordatura
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Accord
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.String.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                TuningStep.class,
                TuningAlter.class,
                TuningOctave.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OneOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OneOrMore;
            }
        }

        /**
         * {@code AccordionRegistration} classifies the MusicXML element, {@code accordion-registration}.
         * <p/>
         * This element is declared by the element type {@code accordion-registration} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * The {@code accordion-registration} element is use for accordion registration symbols.
         * These are circular symbols divided horizontally into high, middle, and low sections that correspond to 4', 8', and 16' pipes.
         * Each {@link AccordionHigh accordion-high}, {@link AccordionMiddle accordion-middle}, and {@link AccordionLow accordion-low} element represents the presence of one or more dots in the registration diagram.
         * The {@link AccordionMiddle accordion-middle} element may have text values of 1, 2, or 3, corresponding to have 1 to 3 dots in the middle section.
         * If no dots are present in a section of the registration diagram, the corresponding element is omitted.
         * An {@code accordion-registration} element needs to have at least one of the child elements present.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface AccordionRegistration
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                AccordionHigh.class,
                AccordionMiddle.class,
                AccordionLow.class,
            };

            /**
             * {@inheritDoc}
             *
             * @return {@link Occurrence#OnlyOne}.
             */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code AccordionHigh} classifies the MusicXML element, {@code accordion-high}.
         * <p/>
         * This element is declared by the element type {@code accordion-high} for the element type {@code accordion-registration} in the direction.mod schema file.
         *
         * @see AccordionRegistration
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface AccordionHigh
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
             * {@inheritDoc}
             *
             * @return {@link Occurrence#ZeroOrOne}.
             */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code AccordionLow} classifies the MusicXML element, {@code accordion-low}.
         * <p/>
         * This element is declared by the element type {@code accordion-low} for the element type {@code accordion-registration} in the direction.mod schema file.
         *
         * @see AccordionRegistration
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface AccordionLow
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
             * {@inheritDoc}
             *
             * @return {@link Occurrence#ZeroOrOne}.
             */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code AccordionMiddle} classifies the MusicXML element, {@code accordion-middle}.
         * <p/>
         * This element is declared by the element type {@code accordion-middle} for the element type {@code accordion-registration} in the direction.mod schema file.
         *
         * @see AccordionRegistration
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface AccordionMiddle
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
             * {@inheritDoc}
             *
             * @return {@link Occurrence#ZeroOrOne}.
             */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code ActualNotes} classifies the MusicXML element, {@code actual-notes}.
         * <p/>
         * This element is declared by the element type {@code actual-notes} in the common.mod schema file, for the element types {@code time-modification} in the note.mod schema file, and {@code metronome-tuplet} in the direction.mode schema file.
         * <p/>
         * This element is used both in the {@link TimeModification time-modification} and {@link MetronomeTuplet metronome-tuplet} elements.
         * The {@code actual-notes} element describes how many notes are played in the time usually occupied by the number of {@link NormalNotes normal-notes}.
         * If the {@link NormalNotes normal-notes} type is different than the current note type (e.g., a quarter note within an eighth note triplet), then the {@link NormalNotes normal-notes} type (e.g. eighth) is specified in the {@link NormalType normal-type} and {@link NormalDot normal-dot} elements.
         * The content of the {@code actual-notes} element is a non-negative integer.
         *
         * @see MetronomeTuplet
         * @see NormalDot
         * @see NormalNotes
         * @see NormalType
         * @see TimeModification
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface ActualNotes
        extends Analytic
        {
            /** The array of accepted parent element types. */
            Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                TimeModification.class,
                MetronomeTuplet.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Alter} classifies the MusicXML element, {@code alter}.
         * <p/>
         * This element is declared by the element type {@code alter} for the element type {@code pitch} in the note.mod schema file.
         * <p/>
         * The {@code alter} element represents chromatic alteration in number of semitones (e.g., -1 for flat, 1 for sharp).
         *
         * @see Pitch
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Alter
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Analytic} classifies an analytic MusicXML element.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Analytic
        extends Element
        {}

        /**
         * {@code Appearance} classifies the MusicXML element, {@code appearance}.
         * <p/>
         * This element is declared by the element type {@code appearance} in the layout.mod schema file, for the element type {@code defaults} in the score.mod schema file.
         * <p/>
         * The {@code appearance} element controls general graphical settings for the music's final form appearance on a printed page of display.
         * This includes support for line widths, definitions for note sizes, and standard distances between notation elements, plus an extension element for other aspects of appearance.
         * <p/>
         * The {@link LineWidth line-width} element indicates the width of a line type in tenths.
         * The {@link Element.Attribute.Type type} attribute defines what type of line is being defined.
         * Values include beam, bracket, dashes, enclosure, ending, extend, heavy barline, leger, light barline, octave shift, pedal, slur middle, slur tip, staff, stem, tie middle, tie tip, tuplet bracket, and wedge.
         * The text content is expressed in tenths.
         * <p/>
         * The {@link NoteSize note-size} element indicates the percentage of the regular note size to use for notes with a cue and large size as defined in the {@link Type type} element.
         * The grace-cue type is used for notes of grace-cue size.
         * The grace type is used for notes of cue size that include a {@link Grace grace} element.
         * The cue type is used for all other notes with cue size, whether defined explicitly or implicitly via a {@link Cue cue} element.
         * The large type is used for notes of large size.
         * The text content represent the numeric percentage.
         * A value of 100 would be identical to the size of a regular note as defined by the music font.
         * <p/>
         * The {@link Distance distance} element represents standard distances between notation elements in tenths.
         * The {@link Element.Attribute.Type type} attribute defines what type of distance is being defined.
         * Values include hyphen (for hyphens in lyrics) and beam.
         * <p/>
         * The {@link Glyph glyph} element represents what SMuFL glyph should be used for different variations of symbols that are semantically identical.
         * The {@link Element.Attribute.Type type} attribute specifies what type of glyph is being defined.
         * The element value specifies what SMuFL glyph to use, including recommended stylistic alternates.
         * <p/>
         * {@link Glyph glyph} {@link Element.Attribute.Type type} attribute values include quarter-rest, g-clef-ottava-bassa, c-clef, f-clef, percussion-clef, octave-shift-up-8, octave-shift-down-8, octave-shift-continue-8, octave-shift-down-15, octave-shift-up-15, octave-shift-continue-15, octave-shift-down-22, octave-shift-up-22, and octave-shift-continue-22.
         * A quarter-rest type specifies the glyph to use when a note has a {@link Rest rest} element and a type value of quarter.
         * The c-clef, f-clef, and percussion-clef types specify the glyph to use when a {@link Clef clef} {@link Sign sign} element value is C, F, or percussion respectively.
         * The g-clef-ottava-bassa type specifies the glyph to use when a {@link Clef clef} {@link Sign sign} element value is G and the {@link ClefOctaveChange clef-octave-change} element value is -1.
         * The octave-shift types specify the glyph to use when an {@link OctaveShift octave-shift} {@link Element.Attribute.Type type} attribute value is up, down, or continue and the {@link OctaveShift octave-shift} {@link Element.Attribute.Size size} attribute value is 8, 15, or 22.
         * <p/>
         * The SMuFL glyph name should match the type.
         * For instance, a type of quarter-rest would use values restQuarter, restQuarterOld, or restQuarterZ.
         * A type of g-clef-ottava-bassa would use values gClef8vb, gClef8vbOld, or gClef8vbCClef.
         * A type of octave-shift-up-8 would use values ottava, ottavaBassa, ottavaBassaBa, ottavaBassaVb, or octaveBassa.
         * <p/>
         * The {@link OtherAppearance other-appearance} element is used to define any graphical settings not yet in the current version of the MusicXML format.
         * This allows extended representation, though without application interoperability.
         *
         * @see Defaults
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Appearance
        extends Element
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                LineWidth.class,
                NoteSize.class,
                Distance.class,
                Glyph.class,
                OtherAppearance.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Arpeggiate} classifies the MusicXML element, {@code arpeggiate}.
         * <p/>
         * This element is declared by the element type {@code arpeggiate} for the element type {@code notations} in the note.mod schema file.
         * <p/>
         * The {@code arpeggiate} element indicates that this note is part of an arpeggiated chord.
         * The {@link Element.Attribute.Number number} attribute can be used to distinguish between two simultaneous chords arpeggiated separately (different numbers) or together (same number).
         * The up-down values in the {@link Element.Attribute.Direction direction} attribute is used if there is an arrow on the arpeggio sign.
         * By default, arpeggios go from the lowest to highest note.
         *
         * @see Notations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Arpeggiate
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Number.class,
                Element.Attribute.Direction.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.Placement.class,
                Element.Attribute.Color.class,
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Arrow} classifies the MusicXML element, {@code arrow}.
         * <p/>
         * This element is declared by the element type {@code arrow} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code arrow} element represents an arrow used for a musical technical indication.
         * Straight arrows are represented with an {@link ArrowDirection arrow-direction} element and an optional {@link ArrowStyle arrow-style} element.
         * Circular arrows are represented with a {@link CircularArrow circular-arrow} element.
         * Descriptive values use Unicode arrow terminology.
         * <p/>
         * Values for the {@link ArrowDirection arrow-direction} element are left, up, right, down, northwest, northeast, southeast, southwest, left right, up down, northwest southeast, northeast southwest, and other.
         * <p/>
         * Values for the {@link ArrowStyle arrow-style} element are single, double, filled, hollow, paired, combined, and other.
         * Filled and hollow arrows indicate polygonal single arrows.
         * Paired arrows are duplicate single arrows in the same direction.
         * Combined arrows apply to double direction arrows like left right, indicating that an arrow in one direction should be combined with an arrow in the other direction.
         * <p/>
         * Values for the {@link CircularArrow circular-arrow} element are clockwise and anticlockwise.
         * <p/>
         * The {@code arrow} element can represent both Unicode and SMuFL arrows.
         * The presence of an {@link Arrowhead arrowhead} element indicates that only the arrowhead is displayed, not the arrow stem.
         * The {@link Element.Attribute.SMuFL smufl} attribute distinguishes different SMuFL glyphs that have an arrow appearance such as arrowBlackUp, guitarStrumUp, or handbellsSwingUp.
         * The specified glyph should match the descriptive representation.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Arrow
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.SMuFL.class
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                ArrowDirection.class,
                ArrowStyle.class,
                Arrowhead.class,
                CircularArrow.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code ArrowDirection} classifies the MusicXML element, {@code arrow-direction}.
         * <p/>
         * This element is declared by the element type {@code arrow-direction} for the entity type {@code arrow} in the note.mod schema file.
         *
         * @see Arrow
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface ArrowDirection
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                DOWN,
                LEFT,
                LEFT__RIGHT,
                NORTHEAST,
                NORTHWEST,
                NORTHWEST__SOUTHEAST,
                NORTHEAST__SOUTHWEST,
                OTHER,
                RIGHT,
                SOUTHWEST,
                SOUTHWEST,
                UP,
                UP__DOWN
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Arrowhead} classifies the MusicXML element, {@code arrowhead}.
         * <p/>
         * This element is declared by the element type {@code arrowhead} for the entity type {@code arrow} in the note.mod schema file.
         *
         * @see Arrow
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Arrowhead
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code ArrowStyle} classifies the MusicXML element, {@code arrow-style}.
         * <p/>
         * This element is declared by the element type {@code arrow-style} for the entity type {@code arrow} in the note.mod schema file.
         *
         * @see Arrow
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface ArrowStyle
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                COMBINED,
                DOUBLE,
                FILLED,
                HOLLOW,
                OTHER,
                PAIRED,
                SINGLE
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Articulations} classifies the MusicXML element, {@code articulations}.
         * <p/>
         * This element is declared by the element type {@code articulations} for the element type {@code notations} in the note.mod schema file.
         *
         * @see Notations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Articulations
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                    Accent.class,
                    StrongAccent.class,
                    Staccato.class,
                    Tenuto.class,
                    DetachedLegato.class,
                    Staccatissimo.class,
                    Spiccato.class,
                    Scoop.class,
                    Plop.class,
                    Doit.class,
                    Falloff.class,
                    BreathMark.class,
                    Caesura.class,
                    Stress.class,
                    Unstress.class,
                    SoftAccent.class,
                    OtherArticulation.class,
                };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Artificial} classifies the MusicXML element, {@code artificial}.
         * <p/>
         * This element is declared by the element type {@code artificial} for the entity type {@code technical} in the note.mod schema file.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Artificial
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Attribute} classifies all MusicXML element attributes.
         *
         * @see XML.Element.Attribute.Schematic
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Attribute
        extends
            XML.Element.Attribute,
            XML.Element.Attribute.Schematic
        {
            /** The mapping of element attribute names to class types. */
            Dictionary<Class<? extends Attribute>> ClassMap = new Dictionary<Class<? extends Attribute>>()
            .map(ABBREVIATED).to(Abbreviated.class)
            .map(ACCELERATE).to(Accelerate.class)
            .map(ADDITIONAL).to(Additional.class)
            .map(AFTER_BARLINE).to(AfterBarline.class)
            .map(ALTERNATE).to(Alternate.class)
            .map(APPROACH).to(Approach.class)
            .map(ATTACK).to(Attack.class)
            .map(ATTRIBUTE).to(Attribute_.class)
            .map(BEATS).to(Beats.class)
            .map(BEZIER_OFFSET).to(BezierOffset.class)
            .map(BEZIER_OFFSET2).to(BezierOffset2.class)
            .map(BEZIER_X).to(BezierX.class)
            .map(BEZIER_X2).to(BezierX2.class)
            .map(BEZIER_Y).to(BezierY.class)
            .map(BEZIER_Y2).to(BezierY2.class)
            .map(BLANK_PAGE).to(BlankPage.class)
            .map(BOTTOM_STAFF).to(BottomStaff.class)
            .map(BRACKET).to(Bracket.class)
            .map(BRACKET_DEGREES).to(BracketDegrees.class)
            .map(CANCEL).to(Cancel.class)
            .map(CAUTIONARY).to(Cautionary.class)
            .map(CODA).to(Coda.class)
            .map(COLOR).to(Color.class)
            .map(DACAPO).to(Dacapo.class)
            .map(DALSEGNO).to(Dalsegno.class)
            .map(DAMPER_PEDAL).to(DamperPedal.class)
            .map(DASHED_CIRCLE).to(DashedCircle.class)
            .map(DASH_LENGTH).to(DashLength.class)
            .map(DEFAULT_X).to(DefaultX.class)
            .map(DEFAULT_Y).to(DefaultY.class)
            .map(DEPARTURE).to(Departure.class)
            .map(DIR).to(Dir.class)
            .map(DIRECTION).to(Direction.class)
            .map(DIRECTIVE).to(Directive.class)
            .map(DIVISIONS).to(Divisions.class)
            .map(DYNAMICS).to(Dynamics.class)
            .map(EDITORIAL).to(Editorial.class)
            .map(ELEMENT).to(Element_.class)
            .map(ELEVATION).to(Elevation.class)
            .map(ENCLOSURE).to(Enclosure.class)
            .map(END_DYNAMICS).to(EndDynamics.class)
            .map(END_LENGTH).to(EndLength.class)
            .map(FAN).to(Fan.class)
            .map(FILLED).to(Filled.class)
            .map(FINE).to(Fine.class)
            .map(FIRST_BEAT).to(FirstBeat.class)
            .map(FONT_FAMILY).to(FontFamily.class)
            .map(FONT_SIZE).to(FontSize.class)
            .map(FONT_STYLE).to(FontStyle.class)
            .map(FONT_WEIGHT).to(FontWeight.class)
            .map(FORWARD_REPEAT).to(ForwardRepeat.class)
            .map(HALIGN).to(HAlign.class)
            .map(HAND).to(Hand.class)
            .map(HEIGHT).to(Height.class)
            .map(music.system.data.Constant.MusicXML.ID).to(ID.class)
            .map(IMPLICIT).to(Implicit.class)
            .map(JUSTIFY).to(Justify.class)
            .map(LAST_BEAT).to(LastBeat.class)
            .map(LETTER_SPACING).to(LetterSpacing.class)
            .map(LINE).to(Line.class)
            .map(LINE_END).to(LineEnd.class)
            .map(LINE_HEIGHT).to(LineHeight.class)
            .map(LINE_LENGTH).to(LineLength.class)
            .map(LINE_SHAPE).to(LineShape.class)
            .map(LINE_THROUGH).to(LineThrough.class)
            .map(LINE_TYPE).to(LineType.class)
            .map(LOCATION).to(Location.class)
            .map(LONG).to(Long.class)
            .map(MAKE_TIME).to(MakeTime.class)
            .map(MEASURE).to(Measure.class)
            .map(NEW_PAGE).to(NewPage.class)
            .map(NEW_SYSTEM).to(NewSystem.class)
            .map(NIENTE).to(Niente.class)
            .map(NON_CONTROLLING).to(NonControlling.class)
            .map(NUMBER).to(Number.class)
            .map(ORIENTATION).to(Orientation.class)
            .map(OVERLINE).to(Overline.class)
            .map(PAGE).to(Page.class)
            .map(PAGE_NUMBER).to(PageNumber.class)
            .map(PAN).to(Pan.class)
            .map(PARENTHESES).to(Parentheses.class)
            .map(PARENTHESES_DEGREES).to(ParenthesesDegrees.class)
            .map(PIZZICATO).to(Pizzicato.class)
            .map(PLACEMENT).to(Placement.class)
            .map(PORT).to(Port.class)
            .map(POSITION).to(Position.class)
            .map(PRINT_DOT).to(PrintDot.class)
            .map(PRINT_FRAME).to(PrintFrame.class)
            .map(PRINT_LEGER).to(PrintLeger.class)
            .map(PRINT_LYRIC).to(PrintLyric.class)
            .map(PRINT_OBJECT).to(PrintObject.class)
            .map(PRINT_SPACING).to(PrintSpacing.class)
            .map(REFERENCE).to(Reference.class)
            .map(RELATIVE_X).to(RelativeX.class)
            .map(RELATIVE_Y).to(RelativeY.class)
            .map(RELEASE).to(Release.class)
            .map(REPEATER).to(Repeater.class)
            .map(ROTATION).to(Rotation.class)
            .map(SECOND_BEAT).to(SecondBeat.class)
            .map(SEGNO).to(Segno.class)
            .map(SEPARATOR).to(Separator.class)
            .map(SHOW_FRETS).to(ShowFrets.class)
            .map(SHOW_NUMBER).to(ShowNumber.class)
            .map(SHOW_TYPE).to(ShowType.class)
            .map(SIGN).to(Sign.class)
            .map(SIZE).to(Size.class)
            .map(SLASH).to(Slash.class)
            .map(SLASHES).to(Slashes.class)
            .map(SMUFL).to(SMuFL.class)
            .map(SOFT_PEDAL).to(SoftPedal.class)
            .map(SOSTENUTO_PEDAL).to(SostenutoPedal.class)
            .map(SOUND).to(Sound.class)
            .map(SOURCE).to(Source.class)
            .map(SPACE_LENGTH).to(SpaceLength.class)
            .map(SPREAD).to(Spread.class)
            .map(STACK_DEGREES).to(StackDegrees.class)
            .map(STAFF_SPACING).to(StaffSpacing.class)
            .map(START_NOTE).to(StartNote.class)
            .map(STEAL_TIME_FOLLOWING).to(StealTimeFollowing.class)
            .map(STEAL_TIME_PREVIOUS).to(StealTimePrevious.class)
            .map(STRING).to(String.class)
            .map(SUBSTITUTION).to(Substitution.class)
            .map(SYMBOL).to(Symbol.class)
            .map(TEMPO).to(Tempo.class)
            .map(TEXT).to(Text.class)
            .map(TEXT_X).to(TextX.class)
            .map(TEXT_Y).to(TextY.class)
            .map(TIMES).to(Times.class)
            .map(TIME_ONLY).to(TimeOnly.class)
            .map(TIP).to(Tip.class)
            .map(TOCODA).to(Tocoda.class)
            .map(TOP_STAFF).to(TopStaff.class)
            .map(TRILL_STEP).to(TrillStep.class)
            .map(TWO_NOTE_TURN).to(TwoNoteTurn.class)
            .map(TYPE).to(Type.class)
            .map(UNDERLINE).to(Underline.class)
            .map(UNPLAYED).to(Unplayed.class)
            .map(USE_DOTS).to(UseDots.class)
            .map(USE_STEMS).to(UseStems.class)
            .map(USE_SYMBOLS).to(UseSymbols.class)
            .map(VALIGN).to(VAlign.class)
            .map(VALUE).to(Value.class)
            .map(VERSION).to(Version.class)
            .map(WIDTH).to(Width.class)
            .map(WINGED).to(Winged.class)
            .map(XLINK_ACTUATE).to(XlinkActuate.class)
            .map(XLINK_HREF).to(XlinkHref.class)
            .map(XLINK_ROLE).to(XlinkRole.class)
            .map(XLINK_SHOW).to(XlinkShow.class)
            .map(XLINK_TITLE).to(XlinkTitle.class)
            .map(XLINK_TYPE).to(XlinkType.class)
            .map(XMLNS_XLINK).to(XmlnsXlink.class)
            .map(XML_LANG).to(XmlLang.class)
            .map(XML_SPACE).to(XmlSpace.class);

            /**
             * Returns a conventional MusicXML element attribute wrapped around the specified traditional XML element attribute as its target.
             *
             * @param target the traditional XML element attribute or a conventional MusicXML element attribute.
             *
             * @return the conventional MusicXML element attribute.
             *
             * @throws IllegalArgumentException if the target is not a standard MusicXML element attribute type.
             */
            static
            Attribute of(
                final Attr target
                ) {
                    if (target instanceof XML.Element.Attribute) {
                        final Attr object = (Attr) ((XML.Element.Attribute) target).object();
                        // make sure to use the appropriate MusicXML attribute.
                        return new Attribute() {
                            @Override
                            public Attr object() {
                                return object;
                            }
                        };
                    }

                    return new Attribute() {
                        @Override
                        public Attr object() {
                            return target;
                        }
                    };
                }

            /**
             * Returns true if the attribute is an analytic type; otherwise returns false.
             *
             * @return true if the attribute is analytic, or false otherwise.
             */
            default
            boolean isAnalytic() {
                return this instanceof Analytic;
            }

            /**
             * Returns the schematic element attribute type.
             *
             * @return this schematic attribute type.
             */
            @Override
            default Attr object() {
                return this;
            }

            /**
             * {@code Abbreviated} classifies the MusicXML element attribute, {@code abbreviated}.
             * <p/>
             * This attribute is declared by the attribute type {@code abbreviated} for the element type {@code pedal} in the direction.mod schema file.
             *
             * @see Pedal
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Abbreviated
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code Accelerate} classifies the MusicXML element attribute, {@code accelerate}.
             * <p/>
             * This attribute is declared for the entity type {@code trill-sound} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Accelerate
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code Additional} classifies the MusicXML element attribute, {@code additional}.
             * <p/>
             * This attribute is declared by the attribute type {@code additional} for the element type {@code clef} in the attributes.mod schema file.
             * The values are declared by the entity type {@code yes-no} in the common.mod schema file.
             *
             * @see Clef
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Additional
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code AfterBarline} classifies the MusicXML element attribute, {@code after-barline}.
             * <p/>
             * This attribute is declared by the attribute type {@code after-barline} for the element type {@code clef} in the attributes.mod schema file.
             * The values are declared by the entity type {@code yes-no} in the common.mod schema file.
             *
             * @see Clef
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface AfterBarline
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code Alternate} classifies the MusicXML element attribute, {@code alternate}.
             * <p/>
             * This attribute is declared by the attribute type {@code alternate} for the element type {@code fingering} in the common.mod schema file.
             *
             * @see Fingering
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Alternate
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code Analytic} classifies an analytic MusicXML element attribute.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Analytic
            extends Attribute
            {}

            /**
             * {@code Approach} classifies the MusicXML element attribute, {@code approach}.
             * <p/>
             * This attribute is declared for the attribute type {@code approach} for the element types {@code inverted-mordent} and {@code mordent} in the note.mod schema file.
             *
             * @see InvertedMordent
             * @see Mordent
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Approach
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.AboveBelow_Values;
            }

            /**
             * {@code Attack} classifies the MusicXML element attribute, {@code attck}.
             * <p/>
             * This attribute is declared by the attribute type {@code attck} for the element type {@code note} in the note.mod schema file.
             *
             * @see Note
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Attack
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Attribute_} classifies the MusicXML element attribute, {@code attribute}.
             * <p/>
             * This attribute is declared for the entity type {@code attribute} for the element type {@code supports} in the identify.mod schema file.
             *
             * @see Supports
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Attribute_
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Beats} classifies the MusicXML element attribute, {@code beats}.
             * <p/>
             * This attribute is declared for the entity type {@code trill-sound} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Beats
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code BezierOffset} classifies the MusicXML element attribute, {@code bezier-offset}.
             * <p/>
             * This attribute is declared by the entity type {@code bezier} in the common.mod schema file.
             * <p/>
             * The {@code bezier-offset} attribute describe the outgoing bezier point for slurs and ties with a start type, and the incoming bezier point for slurs and ties with types of stop or continue.
             * <p/>
             * The {@code bezier-offset} attribute is measured in terms of musical divisions, like the {@link Element.Offset offset} element.
             * <p/>
             * The {@code bezier-offset} attribute is deprecated as of MusicXML 3.1.
             * If both the {@link BezierX bezier-x} and {@code bezier-offset} attributes are present, the {@link BezierX bezier-x} attribute takes priority.
             * The two types of bezier attributes are not additive.
             *
             * @see BezierOffset2
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface BezierOffset
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code BezierOffset2} classifies the MusicXML element attribute, {@code bezier-offset2}.
             * <p/>
             * This attribute is declared by the entity type {@code bezier} in the common.mod schema file.
             * <p/>
             * The {@code bezier-offset2} attribute is only valid with slurs of type continue, and describe the outgoing bezier point.
             * <p/>
             * The {@code bezier-offset2} attribute is measured in terms of musical divisions, like the {@link Element.Offset offset} element.
             * <p/>
             * The {@code bezier-offset2} attribute is deprecated as of MusicXML 3.1.
             * The {@link BezierX2 bezier-x2} attribute takes priority over the {@code bezier-offset2} attribute.
             * The two types of bezier attributes are not additive.
             *
             * @see BezierOffset
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface BezierOffset2
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code BezierX} classifies the MusicXML element attribute, {@code bezier-x}.
             * <p/>
             * This attribute is declared by the entity type {@code bezier} in the common.mod schema file.
             * <p/>
             * The {@code bezier} entity is used to indicate the curvature of slurs and ties, representing the control points for a cubic bezier curve.
             * For ties, the {@code bezier} entity is used with the {@link Tied tied} element.
             * <p/>
             * Normal slurs, S-shaped slurs, and ties need only two bezier points: one associated with the start of the slur or tie, the other with the stop.
             * Complex slurs and slurs divided over system breaks can specify additional bezier data at {@link Slur slur} elements with a continue type.
             * <p/>
             * The {@code bezier-x} attribute describe the outgoing bezier point for slurs and ties with a start type, and the incoming bezier point for slurs and ties with types of stop or continue.
             * <p/>
             * The {@code bezier-x} attribute is specified in tenths, relative to any position settings associated with the {@link Slur slur} or {@link Tied tied} element.
             *
             * @see BezierX2
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface BezierX
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.Tenths_Values;
            }

            /**
             * {@code BezierX2} classifies the MusicXML element attribute, {@code bezier-x2}.
             * <p/>
             * This attribute is declared by the entity type {@code bezier} in the common.mod schema file.
             * <p/>
             * The {@code bezier-x2} attribute is only valid with slurs of type continue, and describe the outgoing bezier point.
             * <p/>
             * The {@code bezier-x2} attribute takes priority over the {@link BezierOffset2 bezier-offset2} attribute.
             * The two types of bezier attributes are not additive.
             *
             * @see BezierX
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface BezierX2
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.Tenths_Values;
            }

            /**
             * {@code BezierY} classifies the MusicXML element attribute, {@code bezier-y}.
             * <p/>
             * This attribute is declared by the entity type {@code bezier} in the common.mod schema file.
             * <p/>
             * The {@code bezier} entity is used to indicate the curvature of slurs and ties, representing the control points for a cubic bezier curve.
             * For ties, the {@code bezier} entity is used with the {@link Tied tied} element.
             * <p/>
             * Normal slurs, S-shaped slurs, and ties need only two bezier points: one associated with the start of the slur or tie, the other with the stop.
             * Complex slurs and slurs divided over system breaks can specify additional bezier data at {@link Slur slur} elements with a continue type.
             * <p/>
             * The {@code bezier-y} attribute describe the outgoing bezier point for slurs and ties with a start type, and the incoming bezier point for slurs and ties with types of stop or continue.
             * <p/>
             * The {@code bezier-y} attribute is specified in tenths, relative to any position settings associated with the {@link Slur slur} or {@link Tied tied} element.
             *
             * @see BezierY2
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface BezierY
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code BezierY2} classifies the MusicXML element attribute, {@code bezier-y2}.
             * <p/>
             * This attribute is declared by the entity type {@code bezier} in the common.mod schema file.
             * <p/>
             * The {@code bezier-y2} attribute is only valid with slurs of type continue, and describe the outgoing bezier point.
             *
             * @see BezierY
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface BezierY2
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code BlankPage} classifies the MusicXML element attribute, {@code blank-page}.
             * <p/>
             * This attribute is declared by the attribute type {@code blank-page} for the element type {@code print} in the direction.mod schema file.
             *
             * @see Print
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface BlankPage
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.NMTOKEN_Literal;
            }

            /**
             * {@code BottomStaff} classifies the MusicXML element attribute, {@code bottom-staff}.
             * <p/>
             * This attribute is declared by the attribute type {@code bottom-staff} for the element types {@code part-symbol} in the attributes.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface BottomStaff
            extends Attribute
            {}

            /**
             * {@code Bracket} classifies the MusicXML element attribute, {@code bracket}.
             * <p/>
             * This attribute is declared by the entity type {@code level-display} in the common.mod schema file, for the element type {@code tuplet} in the note.mod schema file; and for the element type {@code metronome-tuplet} in the direction.mod schema file.
             *
             * @see MetronomeTuplet
             * @see Tuplet
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Bracket
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code BracketDegrees} classifies the MusicXML element attribute, {@code bracket-degrees}.
             * <p/>
             * This attribute is declared by the attribute type {@code bracket-degrees} for the element type {@code kind} in the direction.mod schema file.
             *
             * @see Kind
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface BracketDegrees
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code Cancel} classifies the MusicXML element attribute, {@code cancel}.
             * <p/>
             * This attribute is declared by the attribute type {@code cancel} for the element type {@code key-octave} in the attributes.mod schema file.
             *
             * @see KeyOctave
             * @see Key
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Cancel
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;

                /**
                 * Returns {@link Constant.MusicXML#NO}.
                 *
                 * @return {@link Constant.MusicXML#NO}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return NO;
                }
            }

            /**
             * {@code Cautionary} classifies the MusicXML element attribute, {@code cautionary}.
             * <p/>
             * This attribute is declared by the attribute type {@code cautionary} for the element type {@code accidental} in the note.mod schema file.
             *
             * @see Accidental
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Cautionary
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;

                /**
                 * Returns {@link Constant.MusicXML#NO}.
                 *
                 * @return {@link Constant.MusicXML#NO}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return NO;
                }
            }

            /**
             * {@code Color} classifies the MusicXML element attribute, {@code color}.
             * <p/>
             * This attribute is declared by the entity type {@code color} in the common.mod schema file.
             * <p/>
             * The {@code color} entity indicates the color of an element.
             * Color may be represented as hexadecimal RGB triples, as in HTML, or as hexadecimal ARGB tuples, with the A indicating alpha of transparency.
             * An alpha value of 00 is totally transparent; FF is totally opaque.
             * If RGB is used, the A value is assumed to be FF.
             * <p/>
             * For instance, the RGB value "#800080" represents purple.
             * An ARGB value of "#40800080" would be a transparent purple.
             * <p/>
             * As in SVG 1.1, colors are defined in terms of the sRGB color space (IEC 61966).
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Color
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Coda} classifies the MusicXML element attribute, {@code coda}.
             * <p/>
             * This attribute is declared by the attribute type {@code coda} for the element type {@code sound} in the direction.mod schema file; and for the element type {@code barline} in the barline.mod schema file.
             *
             * @see Barline
             * @see Sound
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Coda
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Dacapo} classifies the MusicXML element attribute, {@code dacapo}.
             * <p/>
             * This attribute is declared by the attribute type {@code dacapo} for the element type {@code sound} in the direction.mod schema file.
             *
             * @see Sound
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Dacapo
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code Dalsegno} classifies the MusicXML element attribute, {@code dalsegno}.
             * <p/>
             * This attribute is declared by the attribute type {@code dalsegno} for the element type {@code sound} in the direction.mod schema file.
             *
             * @see Sound
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Dalsegno
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code DamperPedal} classifies the MusicXML element attribute, {@code damper-pedal}.
             * <p/>
             * This attribute is declared by the attribute type {@code damper-pedal} for the element type {@code sound} in the direction.mod schema file.
             *
             * @see Sound
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface DamperPedal
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNoNumber_Values;
            }

            /**
             * {@code DashedCircle} classifies the MusicXML element attribute, {@code dashed-circle}.
             * <p/>
             * This attribute is declared by the attribute type {@code dashed-circle} for the element types {@code stick} in the direction.mod schema file.
             *
             * @see Stick
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface DashedCircle
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;

                /**
                 * Returns {@link Constant.MusicXML#NO}.
                 *
                 * @return {@link Constant.MusicXML#NO}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return NO;
                }
            }

            /**
             * {@code DashLength} classifies the MusicXML element attribute, {@code dash-length}.
             * <p/>
             * This attribute is declared by the entity type {@code dash-formatting} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface DashLength
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code DefaultX} classifies the MusicXML element attribute, {@code default-x}.
             * <p/>
             * This attribute is declared by the entity type {@code position} in the common.mod schema file.
             * <p/>
             * The {@code default-x} attribute changes the computation of the default position.
             * For most elements, the origin is changed relative to the left-hand side of the note or the musical position within the bar (x) and the top line of the staff (y).
             * <p/>
             * For the following elements, the {@code default-x} value changes the origin relative to the start of the current measure:
             * <p/>
             * &nbsp;&nbsp;&nbsp;&nbsp;- {@link Note note}<br/>
             * &nbsp;&nbsp;&nbsp;&nbsp;- {@link FiguredBass figured-bass}<br/>
             * &nbsp;&nbsp;&nbsp;&nbsp;- {@link Harmony harmony}<br/>
             * &nbsp;&nbsp;&nbsp;&nbsp;- {@link Link link}<br/>
             * &nbsp;&nbsp;&nbsp;&nbsp;- {@link Directive directive}<br/>
             * &nbsp;&nbsp;&nbsp;&nbsp;- {@link MeasureNumbering measure-numbering}<br/>
             * &nbsp;&nbsp;&nbsp;&nbsp;- all descendants of the {@link PartList part-list} element<br/>
             * &nbsp;&nbsp;&nbsp;&nbsp;- all children of the {@link DirectionType direction-type} element
             * <p/>
             * This origin is from the start of the entire measure, at either the left barline or the start of the system.
             * <p/>
             * When the {@code default-x} attribute is used within a child element of the {@link PartNameDisplay part-name-display}, {@link PartAbbreviationDisplay part-abbreviation-display}, {@link GroupNameDisplay group-name-display}, or {@link GroupAbbreviationDisplay group-abbreviation-display} elements, it changes the origin relative to the start of the first measure on the system.
             * These values are used when the current measure or a succeeding measure starts a new system.
             * The same change of origin is used for the {@link GroupSymbol group-symbol} element.
             * <p/>
             * For the note, {@link FiguredBass figured-bass}, and {@link Harmony harmony} elements, the {@code default-x} value is considered to have adjusted the musical position within the bar for its descendant elements.
             * <p/>
             * Since the {@link CreditWords credit-words} and {@link CreditImage credit-image} elements are not related to a measure, in these cases the {@code default-x} attribute adjusts the origin relative to the bottom left-hand corner of the specified page.
             * <p/>
             * Positive x is right, negative x is left.
             * The unit is in tenths of interline space.
             * <p/>
             * The {@code default-x} position attribute provides higher-resolution positioning data than related features such as the {@link Placement placement} attribute and the {@link Element.Offset offset} element.
             * Applications reading a MusicXML file that can understand both features should generally rely on the {@code default-x} attribute for their greater accuracy.
             * <p/>
             * As elsewhere in the MusicXML format, tenths are the global tenths defined by the {@link Scaling scaling} element, not the local tenths of a staff resized by the {@link StaffSize staff-size} element.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface DefaultX
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.Tenths_Values;
            }

            /**
             * {@code DefaultY} classifies the MusicXML element attribute, {@code default-y}.
             * <p/>
             * This attribute is declared by the entity type {@code position} in the common.mod schema file.
             * <p/>
             * The {@code default-y} attribute changes the computation of the default position.
             * For most elements, the origin is changed relative to the left-hand side of the note or the musical position within the bar (x) and the top line of the staff (y).
             * <p/>
             * Since the {@link CreditWords credit-words} and {@link CreditImage credit-image} elements are not related to a measure, in these cases the {@code default-y} attribute adjusts the origin relative to the bottom left-hand corner of the specified page.
             * <p/>
             * Positive y is up, negative y is down.
             * The unit is in tenths of interline space.
             * <p/>
             * The {@code default-y} position attribute provides higher-resolution positioning data than related features such as the {@link Placement placement} attribute and the {@link Element.Offset offset} element.
             * Applications reading a MusicXML file that can understand both features should generally rely on the {@code default-y} attribute for their greater accuracy.
             * <p/>
             * As elsewhere in the MusicXML format, tenths are the global tenths defined by the {@link Scaling scaling} element, not the local tenths of a staff resized by the {@link StaffSize staff-size} element.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface DefaultY
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.Tenths_Values;
            }

            /**
             * {@code Departure} classifies the MusicXML element attribute, {@code departure}.
             * <p/>
             * This attribute is declared for the attribute type {@code departure} for the element types {@code inverted-mordent} and {@code mordent} in the note.mod schema file.
             *
             * @see InvertedMordent
             * @see Mordent
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Departure
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.AboveBelow_Values;
            }

            /**
             * {@code Dir} classifies the MusicXML element attribute, {@code dir}.
             * <p/>
             * This attribute is declared by the entity type {@code text-direction} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Dir
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    LRO,
                    LTR,
                    RLO,
                    RTL
                };
            }

            /**
             * {@code Direction} classifies the MusicXML element attribute, {@code direction}.
             * <p/>
             * This attribute is declared by the attribute type {@code direction} for the element type {@code arpeggiate} in the note.mod schema file; and the element type {@code repeat} in the barline.mod schema file.
             *
             * @see Arpeggiate
             * @see Repeat
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Direction
            extends
                Analytic,
                XML.Schematic.PerType
            {
                /** The array of accepted element types. */
                Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                    Arpeggiate.class,
                    Repeat.class,
                };

                @Override
                default Class<? extends Direction> per(final Class<?> type) {
                    if (type == Arpeggiate.class)
                        return Direction_PerArpeggiate.class;
                    else
                    if (type == Repeat.class)
                        return Direction_PerRepeat.class;

                    return null;
                }

                interface Direction_PerArpeggiate
                extends Direction
                {
                    java.lang.String[] Values = Entity.UpDown_Values;
                }

                interface Direction_PerRepeat
                extends
                    Direction,
                    RequiredAttribute
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        BACKWARD,
                        FORWARD
                    };
                }
            }

            /**
             * {@code Directive} classifies the MusicXML element attribute, {@code directive}.
             * <p/>
             * This attribute is declared by the entity type {@code directive} in the common.mod schema file.
             * <p/>
             * The {@code directive} entity changes the {@link DefaultX default-x} position of a {@link Direction direction}.
             * It indicates that the left-hand side of the {@link Direction direction} is aligned with the left-hand side of the time signature.
             * If no time signature is present, it is aligned with the left-hand side of the first music notational element in the measure.
             * If a {@link DefaultX default-x}, {@link Justify justify}, or {@link HAlign halign} attribute is present, it overrides the {@code directive} entity.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Directive
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code Divisions} classifies the MusicXML element attribute, {@code divisions}.
             * <p/>
             * This attribute is declared by the attribute type {@code divisions} for the element type {@code sound} in the direction.mod schema file; and for the element type {@code barline} in the barline.mod schema file.
             *
             * @see Barline
             * @see Sound
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Divisions
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Dynamics} classifies the MusicXML element attribute, {@code dynamics}.
             * <p/>
             * This attribute is declared by the attribute type {@code dynamics} for the element type {@code note} in the note.mod schema file; and for the element type {@code sound} in the direction.mod schema file.
             *
             * @see Note
             * @see Sound
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Dynamics
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Editorial} classifies the MusicXML element attribute, {@code editorial}.
             * <p/>
             * This attribute is declared by the attribute type {@code editorial} for the element type {@code accidental} in the note.mod schema file.
             *
             * @see Accidental
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Editorial
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;

                /**
                 * Returns {@link Constant.MusicXML#NO}.
                 *
                 * @return {@link Constant.MusicXML#NO}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return NO;
                }
            }

            /**
             * {@code Element_} classifies the MusicXML element attribute, {@code element}.
             * <p/>
             * This attribute is declared for the attribute type {@code element} for the element type {@code supports} in the identify.mod schema file; and for the element types {@code bookmark} and {@code link} in the link.mod schema file.
             *
             * @see Bookmark
             * @see Link
             * @see Supports
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Element_
            extends
                Attribute,
                XML.Schematic.PerType
            {
                /** The array of accepted element types. */
                Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                    Bookmark.class,
                    Link.class,
                    Supports.class,
                };

                @Override
                default Class<? extends Element_> per(final Class<?> type) {
                    if (type == Bookmark.class ||
                        type == Link.class)
                        return Element_PerMany1.class;
                    else
                    if (type == Supports.class)
                        return Element_PerSupports.class;

                    return null;
                }

                interface Element_PerMany1
                extends Element_
                {
                    java.lang.String[] Values = Entity.NMTOKEN_Literal;
                }

                interface Element_PerSupports
                extends
                    Element_,
                    RequiredAttribute
                {
                    java.lang.String[] Values = Entity.CDATA_Literal;
                }
            }

            /**
             * {@code Elevation} classifies the MusicXML element attribute, {@code elevation}.
             * <p/>
             * This attribute is declared by the attribute type {@code elevation} for the element type {@code sound} in the direction.mod schema file.
             *
             * @see Sound
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Elevation
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Enclosure} classifies the MusicXML element attribute, {@code enclosure}.
             * <p/>
             * This attribute is declared by the entity type {@code enclosure} in the common.mod schema file.
             * <p/>
             * The {@code enclosure} entity is used to specify the formatting of an enclosure around text or symbols.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Enclosure
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.EnclosureShape_Values;
            }

            /**
             * {@code EndDynamics} classifies the MusicXML element attribute, {@code end-dynamics}.
             * <p/>
             * This attribute is declared by the attribute type {@code end-dynamics} for the element type {@code note} in the note.mod schema file.
             *
             * @see Note
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface EndDynamics
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code EndLength} classifies the MusicXML element attribute, {@code end-length}.
             * <p/>
             * This attribute is declared by the attribute type {@code end-length} for the element type {@code bracket} in the direction.mod schema file; and for the element type {@code ending} in the barline.mod schema file.
             *
             * @see Bracket
             * @see Ending
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface EndLength
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.Tenths_Values;
            }

            /**
             * {@code Fan} classifies the MusicXML element attribute, {@code fan}.
             * <p/>
             * This attribute is declared for the attribute type {@code fan} for the element type {@code beam} in the note.mod schema file.
             *
             * @see Beam
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Fan
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    ACCEL,
                    NONE,
                    RIT
                };

                /**
                 * Returns {@link Constant.MusicXML#NONE}.
                 *
                 * @return {@link Constant.MusicXML#NONE}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return NONE;
                }
            }

            /**
             * {@code Filled} classifies the MusicXML element attribute, {@code filled}.
             * <p/>
             * This attribute is declared by the attribute type {@code filled} for the element type {@code notehead} in the note.mod schema file.
             *
             * @see Notehead
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Filled
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code Fine} classifies the MusicXML element attribute, {@code fine}.
             * <p/>
             * This attribute is declared by the attribute type {@code fine} for the element type {@code sound} in the direction.mod schema file.
             *
             * @see Sound
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Fine
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code FirstBeat} classifies the MusicXML element attribute, {@code first-beat}.
             * <p/>
             * This attribute is declared for the entity type {@code bend-sound} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface FirstBeat
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code FontFamily} classifies the MusicXML element attribute, {@code font-family}.
             * <p/>
             * This attribute is declared by the entity type {@code font} in the common.mod schema file.
             * <p/>
             * The font entity gathers together attributes for determining the font within a directive or direction.
             * They are based on the text styles for Cascading Style Sheets.
             * The font-family is a comma-separated list of font names.
             * These can be specific font styles such as Maestro or Opus, or one of several generic font styles: music, engraved, handwritten, text, serif, sans-serif, handwritten, cursive, fantasy, and monospace.
             * The music, engraved, and handwritten values refer to music fonts; the rest refer to text fonts.
             * The fantasy style refers to decorative text such as found in older German-style printing.
             * The default is application-dependent, but is a text font vs. a music font.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface FontFamily
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code FontStyle} classifies the MusicXML element attribute, {@code font-style}.
             * <p/>
             * This attribute is declared by the entity type {@code font} in the common.mod schema file.
             * <p/>
             * The {@code font-style} can be normal or italic.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface FontStyle
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    ITALIC,
                    NORMAL
                };
            }

            /**
             * {@code FontSize} classifies the MusicXML element attribute, {@code font-size}.
             * <p/>
             * This attribute is declared by the entity type {@code font} in the common.mod schema file.
             * <p/>
             * The font-size can be one of the CSS sizes (xx-small, x-small, small, medium, large, x-large, xx-large) or a numeric point size.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface FontSize
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code FontWeight} classifies the MusicXML element attribute, {@code font-weight}.
             * <p/>
             * This attribute is declared by the entity type {@code font} in the common.mod schema file.
             * <p/>
             * The {@code font-weight} can be normal or bold.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface FontWeight
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    BOLD,
                    NORMAL
                };
            }

            /**
             * {@code ForwardRepeat} classifies the MusicXML element attribute, {@code forward-repeat}.
             * <p/>
             * This attribute is declared by the attribute type {@code forward-repeat} for the element type {@code sound} in the direction.mod schema file.
             *
             * @see Sound
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface ForwardRepeat
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code HAlign} classifies the MusicXML element attribute, {@code halign}.
             * <p/>
             * This attribute is declared by the entity type {@code halign} in the common.mod schema file.
             * <p/>
             * In cases where text extends over more than one line, horizontal alignment and {@link Justify justify} values can be different.
             * The most typical case is for credits, such as:
             * <p/>
             * <pre><code>
             * Words and music by
             *   Pat Songwriter
             * </code></pre>
             * Typically this type of credit is aligned to the right, so that the position information refers to the right-most part of the text.
             * But in this example, the text is center-justified, not right-justified.
             * <p/>
             * The {@code halign} attribute is used in these situations.
             * If it is not present, its value is the same as for the {@link Justify justify} attribute.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface HAlign
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    CENTER,
                    LEFT,
                    RIGHT
                };
            }

            /**
             * {@code Hand} classifies the MusicXML element attribute, {@code hand}.
             * <p/>
             * This attribute is declared by the attribute type {@code hand} for the element type {@code tap} in the note.mod schema file.
             *
             * @see Tap
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Hand
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    LEFT,
                    RIGHT
                };
            }

            /**
             * {@code Height} classifies the MusicXML element attribute, {@code height}.
             * <p/>
             * This attribute is declared by the attribute type {@code height} for the element type {@code credit-image} in the score.mod schema file; and for the element types {@code frame} and {@code image} in the direction.mod schema file.
             * The attribute values are numeric in tenths.
             *
             * @see CreditImage
             * @see Frame
             * @see Image
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Height
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.Tenths_Values;
            }

            /**
             * {@code ID} classifies the MusicXML element attribute, {@code id}.
             * <p/>
             * This attribute is declared by the entity type {@code optional-unique-id} in the common.mod schema file; for the element types {@code midi-device} and {@code midi-instrument} in the common.mod schema file; and for the element types {@code beam}, {@code instrument}, and {@code notations} in the note.mod schema file; and for element types {@code part}, {@code score-instrument} and {@code score-part} in the score.mod schema file; and for the element {@code direction-type} in the direction.mod schema file; and for the element type {@code barline} in the barline.mod schema file; and for the element type {@code bookmark} in the link.mod schema file.
             *
             * @see Barline
             * @see Beam
             * @see Bookmark
             * @see DirectionType
             * @see Instrument
             * @see MidiDevice
             * @see MidiInstrument
             * @see Notations
             * @see Part
             * @see ScoreInstrument
             * @see ScorePart
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface ID
            extends
                Analytic,
                XML.Schematic.PerType
            {
                /** The array of accepted element types. */
                Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                    Barline.class,
                    Beam.class,
                    Bookmark.class,
                    DirectionType.class,
                    Instrument.class,
                    MidiDevice.class,
                    MidiInstrument.class,
                    Part.class,
                    Play.class,
                    ScoreInstrument.class,
                    ScorePart.class,
                };

                @Override
                default Class<? extends ID> per(final Class<?> type) {
                    if (type == MidiDevice.class ||
                        type == Play.class)
                        return ID_PerMany1.class;
                    else
                    if (type == Instrument.class ||
                        type == MidiInstrument.class ||
                        type == Part.class)
                        return ID_PerMany2.class;
                    else
                    if (type == Barline.class ||
                        type == Beam.class ||
                        type == DirectionType.class)
                        return ID_PerMany3.class;
                    else
                    if (type == Bookmark.class ||
                        type == ScoreInstrument.class ||
                        type == ScorePart.class)
                        return ID_PerMany4.class;

                    return null;
                }

                interface ID_PerMany1
                extends ID
                {
                    java.lang.String[] Values = Entity.IDREF_Literal;
                }

                interface ID_PerMany2
                extends
                    ID,
                    RequiredAttribute
                {
                    java.lang.String[] Values = Entity.IDREF_Literal;

                    @Override
                    default boolean isRequired() {
                        return true;
                    }
                }

                interface ID_PerMany3
                extends ID
                {
                    java.lang.String[] Values = Entity.ID_Literal;
                }

                interface ID_PerMany4
                extends
                    ID,
                    RequiredAttribute
                {
                    java.lang.String[] Values = Entity.ID_Literal;

                    @Override
                    default boolean isRequired() {
                        return true;
                    }
                }
            }

            /**
             * {@code Implicit} classifies the MusicXML element attribute, {@code implicit}.
             * <p/>
             * This attribute is declared by the attribute type {@code implicit} for the element type {@code measure} in the score.mod schema file.
             *
             * @see Measure
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Implicit
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code Justify} classifies the MusicXML element attribute, {@code justify}.
             * <p/>
             * This attribute is declared by the entity type {@code justify} in the common.mod schema file.
             * <p/>
             * The {@code justify} entity is used to indicate left, center, or right justification.
             * The default value varies for different elements.
             * For elements where the {@code justify} attribute is present but the {@link HAlign halign} attribute is not, the {@code justify} attribute indicates horizontal alignment as well as justification.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Justify
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    CENTER,
                    LEFT,
                    RIGHT
                };
            }

            /**
             * {@code LastBeat} classifies the MusicXML element attribute, {@code last-beat}.
             * <p/>
             * This attribute is declared for the entity types {@code bend-sound} and {@code trill-sound} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface LastBeat
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code LetterSpacing} classifies the MusicXML element attribute, {@code letter-spacing}.
             * <p/>
             * This attribute is declared by the entity type {@code letter-spacing} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface LetterSpacing
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Line} classifies the MusicXML element attribute, {@code line}.
             * <p/>
             * This attribute is declared by the attribute type {@code line} for the element type {@code staff-tuning} in the attributes.mod schema file; and for the element type {@code pedal} in the direction.mod schema file.
             *
             * @see Pedal
             * @see StaffTuning
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Line
            extends
                Analytic,
                XML.Schematic.PerType
            {
                /** The array of accepted element types. */
                Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                    Pedal.class,
                    StaffTuning.class,
                };

                @Override
                default Class<? extends Line> per(final Class<?> type) {
                    if (type == Pedal.class)
                        return Line_PerPedal.class;
                    else
                    if (type == StaffTuning.class)
                        return Line_PerStaffTuning.class;

                    return null;
                }

                interface Line_PerPedal
                extends Line
                {
                    java.lang.String[] Values = Entity.YesNo_Values;
                }

                interface Line_PerStaffTuning
                extends Line
                {
                    java.lang.String[] Values = Entity.CDATA_Literal;
                }
            }

            /**
             * {@code LineEnd} classifies the MusicXML element attribute, {@code line-end}.
             * <p/>
             * This attribute is declared by the attribute type {@code line-end} for the element type {@code bracket} in the direction.mod schema file.
             *
             * @see Bracket
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface LineEnd
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    ARROW,
                    BOTH,
                    DOWN,
                    NONE,
                    UP
                };

                /**
                 * Returns true.
                 *
                 * @return true.
                 */
                @Override
                default boolean isRequired() {
                    return true;
                }
            }

            /**
             * {@code LineHeight} classifies the MusicXML element attribute, {@code line-height}.
             * <p/>
             * This attribute is declared by the entity type {@code line-height} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface LineHeight
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code LineLength} classifies the MusicXML element attribute, {@code line-length}.
             * <p/>
             * This attribute is declared by the entity type {@code line-length} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface LineLength
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    LONG,
                    MEDIUM,
                    SHORT
                };
            }

            /**
             * {@code LineShape} classifies the MusicXML element attribute, {@code line-shape}.
             * <p/>
             * This attribute is declared by the entity type {@code line-shape} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface LineShape
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    CURVED,
                    STRAIGHT
                };
            }

            /**
             * {@code LineThrough} classifies the MusicXML element attribute, {@code line-through}.
             * <p/>
             * This attribute is declared by the entity type {@code text-decoration} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface LineThrough
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.NumberOfLines_Values;
            }

            /**
             * {@code LineType} classifies the MusicXML element attribute, {@code line-type}.
             * <p/>
             * This attribute is declared by the entity type {@code line-type} in the common.mod schema file for the element types {@code bracket} and {@code wedge} in the direction.mod schema file.
             *
             * @see Bracket
             * @see Wedge
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface LineType
            extends
                Attribute,
                XML.Schematic.PerType
            {
                /** The array of accepted element types. */
                Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                    Bracket.class,
                    Wedge.class,
                    // find the other types
                };

                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    DASHED,
                    DOTTED,
                    SOLID,
                    WAVY
                };

                @Override
                default Class<? extends LineType> per(final Class<?> type) {
                    if (type == Bracket.class ||
                        type == Wedge.class)
                        return new LineType() {
                            @Override
                            public java.lang.String defaultValue() {
                                return SOLID;
                            }
                        }.getClass();

                    return null;
                }
            }

            /**
             * {@code Location} classifies the MusicXML element attribute, {@code location}.
             * <p/>
             * This attribute is declared by the attribute type {@code location} for the element type {@code cancel} in the attributes.mod schema file; and for the element types {@code harmon-closed} and {@code hole-closed} in the note.mod schema file; and for the element types {@code bass-alter}, {@code first-fret}, and {@code root-alter} in the direction.mod schema file; and for the element type {@code barline} in the barline.mod schema file.
             *
             * @see Barline
             * @see BassAlter
             * @see Element.Cancel
             * @see FirstFret
             * @see HarmonClosed
             * @see HoleClosed
             * @see RootAlter
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Location
            extends
                Analytic,
                XML.Schematic.PerType
            {
                Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                    Barline.class,
                    BassAlter.class,
                    Element.Cancel.class,
                    FirstFret.class,
                    HarmonClosed.class,
                    HoleClosed.class,
                    RootAlter.class
                };

                @Override
                default Class<? extends Location> per(final Class<?> type) {
                    if (type == Barline.class)
                        return Location_PerBarline.class;
                    else
                    if (type == BassAlter.class ||
                        type == FirstFret.class ||
                        type == RootAlter.class)
                        return Location_PerMany1.class;
                    if (type == Element.Cancel.class)
                        return Location_PerCancel.class;
                    else
                    if (type == HarmonClosed.class ||
                        type == HoleClosed.class)
                        return Location_PerMany2.class;

                    return null;
                }

                interface Location_PerBarline
                extends Location
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        LEFT,
                        MIDDLE,
                        RIGHT
                    };

                    @Override
                    default java.lang.String defaultValue() {
                        return RIGHT;
                    }
                }

                interface Location_PerMany1
                extends Location
                {
                    java.lang.String[] Values = Entity.LeftRight_Values;
                }

                interface Location_PerCancel
                extends Location
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        BEFORE_BARLINE,
                        LEFT,
                        RIGHT
                    };
                }

                interface Location_PerMany2
                extends Location
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        BOTTOM,
                        LEFT,
                        RIGHT,
                        TOP
                    };
                }
            }

            /**
             * {@code Long} classifies the MusicXML element attribute, {@code long}.
             * <p/>
             * This attribute is declared for the attribute type {@code long} for the element types {@code inverted-mordent} and {@code mordent} in the note.mod schema file.
             *
             * @see InvertedMordent
             * @see Mordent
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Long
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;

                /**
                 * Returns {@link Constant.MusicXML#NO}.
                 *
                 * @return {@link Constant.MusicXML#NO}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return NO;
                }
            }

            /**
             * {@code MakeTime} classifies the MusicXML element attribute, {@code make-time}.
             * <p/>
             * This attribute is declared by the attribute type {@code make-time} for the element type {@code grace} in the note.mod schema file.
             *
             * @see Grace
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface MakeTime
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Measure} classifies the MusicXML element attribute, {@code measure}.
             * <p/>
             * This attribute is declared by the attribute type {@code measure} for the element type {@code rest} in the note.mod schema file.
             *
             * @see Rest
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Measure
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code MemberOf} classifies the MusicXML element attribute, {@code member-of}.
             * <p/>
             * This attribute is declared by the attribute type {@code member-of} for the element type {@code grouping in the direction.mod schema file.
             *
             * @see Grouping
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface MemberOf
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Name} classifies the MusicXML element attribute, {@code name}.
             * <p/>
             * This attribute is declared by the attribute type {@code name} for the element types {@code lyric} in the note.mod schema file; and for the element types {@code lyric-font} and {@code lyric-language} in the score.mod schema file; and for the element type {@code miscellaneous-field} in the identify.mod schema file; and for the element types {@code bookmark} and {@code link} in the link.mod schema file.
             *
             * @see Bookmark
             * @see Link
             * @see Lyric
             * @see LyricFont
             * @see LyricLanguage
             * @see MiscellaneousField
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Name
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code NewPage} classifies the MusicXML element attribute, {@code new-page}.
             * <p/>
             * This attribute is declared by the attribute type {@code new-page} for the element type {@code print} in the direction.mod schema file.
             *
             * @see Print
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface NewPage
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code NewSystem} classifies the MusicXML element attribute, {@code new-system}.
             * <p/>
             * This attribute is declared by the attribute type {@code new-system} for the element type {@code print} in the direction.mod schema file.
             *
             * @see Print
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface NewSystem
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code Spread} classifies the MusicXML element attribute, {@code spread}.
             * <p/>
             * This attribute is declared by the attribute type {@code spread} for the element type {@code wedge} in the direction.mod schema file.
             *
             * @see Wedge
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Niente
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;

                /**
                 * Returns {@link Constant.MusicXML#NO}.
                 *
                 * @return {@link Constant.MusicXML#NO}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return NO;
                }
            }

            /**
             * {@code NonControlling} classifies the MusicXML element attribute, {@code non-controlling}.
             * <p/>
             * This attribute is declared by the attribute type {@code non-controlling} for the element type {@code measure} in the score.mod schema file.
             *
             * @see Measure
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface NonControlling
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code Number} classifies the MusicXML element attribute, {@code number}.
             * <p/>
             * This attribute is declared by the attribute type {@code number} for the element types {@code clef}, {@code key}, {@code key-octave}, {@code measure-style}, {@code staff-details}, {@code time}, and {@code transpose} in the attributes.mod schema file; and for the element types {@code arpeggiate}, {@code beam}, {@code glissando}, {@code hammer-on}, {@code lyric}, {@code non-arpeggiate}, {@code other-notation}, {@code pull-off}, {@code slide}, {@code slur}, {@code tied}, and {@code tuplet} in the note.mod schema file; and for the element types {@code lyric-font}, {@code lyric-language}, {@code measure}, and {@code part-group} in the score.mod schema file; and for the element types {@code bracket}, {@code dashes}, {@code grouping}, {@code metronome-beam}, {@code octave-shift}, {@code pedal}, and {@code wedge} in the direction.mod schema file; and the element type {@code ending} in the barline.mod schema file.
             *
             * @see Arpeggiate
             * @see Beam
             * @see Bracket
             * @see Clef
             * @see Dashes
             * @see Ending
             * @see Glissando
             * @see Grouping
             * @see HammerOn
             * @see Key
             * @see KeyOctave
             * @see Lyric
             * @see LyricFont
             * @see LyricLanguage
             * @see Measure
             * @see MeasureStyle
             * @see MetronomeBeam
             * @see NonArpeggiate
             * @see OctaveShift
             * @see OtherNotation
             * @see PartGroup
             * @see Pedal
             * @see PullOff
             * @see Slide
             * @see Slur
             * @see StaffDetails
             * @see Tied
             * @see Time
             * @see Transpose
             * @see Tuplet
             * @see Wedge
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Number
            extends
                Analytic,
                XML.Schematic.PerType
            {
                /** The array of accepted element types. */
                Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                    Arpeggiate.class,
                    Beam.class,
                    Bracket.class,
                    Clef.class,
                    Dashes.class,
                    Ending.class,
                    Glissando.class,
                    Grouping.class,
                    HammerOn.class,
                    Key.class,
                    KeyOctave.class,
                    Lyric.class,
                    LyricFont.class,
                    LyricLanguage.class,
                    Measure.class,
                    MeasureStyle.class,
                    MetronomeBeam.class,
                    NonArpeggiate.class,
                    OctaveShift.class,
                    OtherNotation.class,
                    Pedal.class,
                    PullOff.class,
                    Slide.class,
                    Slur.class,
                    StaffDetails.class,
                    Tie.class,
                    Time.class,
                    Transpose.class,
                    Wedge.class,
                };

                /**
                 * Returns the attribute for the specified element type.
                 *
                 * @param type the element class type.
                 *
                 * @return the attribute.
                 */
                @Override
                default Class<? extends Number> per(final Class<?> type) {
                    if (type == Beam.class ||
                        type == MetronomeBeam.class)
                        return Number_PerMany1.class;
                    else
                    if (type == Arpeggiate.class ||
                        type == Bracket.class ||
                        type == Dashes.class ||
                        type == Glissando.class ||
                        type == HammerOn.class ||
                        type == NonArpeggiate.class ||
                        type == OctaveShift.class ||
                        type == OtherNotation.class ||
                        type == Pedal.class ||
                        type == PullOff.class ||
                        type == Slide.class ||
                        type == Slur.class ||
                        type == Wedge.class)
                        return Number_PerMany2.class;
                    else
                    if (type == Tied.class ||
                        type == Tuplet.class)
                        return Number_PerMany3.class;
                    else
                    if (type == KeyOctave.class ||
                        type == Lyric.class ||
                        type == LyricFont.class ||
                        type == LyricLanguage.class)
                        return Number_PerMany4.class;
                    else
                    if (type == Clef.class ||
                        type == Key.class ||
                        type == MeasureStyle.class ||
                        type == StaffDetails.class ||
                        type == Time.class ||
                        type == Transpose.class)
                        return Number_PerMany5.class;
                    else
                    if (type == Ending.class ||
                        type == Measure.class)
                        return Number_PerMany6.class;
                    else
                    if (type == Grouping.class ||
                        type == PartGroup.class)
                        return Number_PerMany7.class;

                    return null;
                }

                interface Number_PerMany1
                extends Number
                {
                    java.lang.String[] Values = Entity.BeamLevel_Values;

                    @Override
                    default java.lang.String defaultValue() {
                        return "1";
                    }
                }

                interface Number_PerMany2
                extends Number
                {
                    java.lang.String[] Values = Entity.NumberLevel_Values;

                    @Override
                    default java.lang.String defaultValue() {
                        return "1";
                    }
                }

                interface Number_PerMany3
                extends Number
                {
                    java.lang.String[] Values = Entity.NumberLevel_Values;
                }

                interface Number_PerMany4
                extends Number
                {
                    java.lang.String[] Values = Entity.NMTOKEN_Literal;
                }

                interface Number_PerMany5
                extends Number
                {
                    java.lang.String[] Values = Entity.CDATA_Literal;
                }

                interface Number_PerMany6
                extends
                    Number,
                    RequiredAttribute
                {
                    java.lang.String[] Values = Entity.CDATA_Literal;
                }

                interface Number_PerMany7
                extends Number
                {
                    java.lang.String[] Values = Entity.CDATA_Literal;

                    @Override
                    default java.lang.String defaultValue() {
                        return "1";
                    }
                }
            }

            /**
             * {@code Orientation} classifies the MusicXML element attribute, {@code orientation}.
             * <p/>
             * This attribute is declared by the entity type {@code orientation} in the common.mod schema file.
             * <p/>
             * The {@code orientation} attribute indicates whether slurs and ties are overhand (tips down) or underhand (tips up).
             * This is distinct from the {@code placement} entity used by any notation type.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Orientation
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.Orientation_Values;
            }

            /**
             * {@code Overline} classifies the MusicXML element attribute, {@code overline}.
             * <p/>
             * This attribute is declared by the entity type {@code text-decoration} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Overline
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.NumberOfLines_Values;
            }

            /**
             * {@code Page} classifies the MusicXML element attribute, {@code page}.
             * <p/>
             * This attribute is declared by the attribute type {@code page} for the element type {@code credit} in the score.mod schema file.
             *
             * @see Credit
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Page
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.NMTOKEN_Literal;
            }

            /**
             * {@code PageNumber} classifies the MusicXML element attribute, {@code page-number}.
             * <p/>
             * This attribute is declared by the attribute type {@code page-number} for the element type {@code print} in the direction.mod schema file.
             *
             * @see Print
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface PageNumber
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Pan} classifies the MusicXML element attribute, {@code pan}.
             * <p/>
             * This attribute is declared by the attribute type {@code pan} for the element type {@code sound} in the direction.mod schema file.
             *
             * @see Sound
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Pan
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Parentheses} classifies the MusicXML element attribute, {@code parentheses}.
             * <p/>
             * This attribute is declared by the entity type {@code level-display} in the common.mod schema file; and by the attribute type {@code parentheses} for the element types {@code figured-bass} and {@code notehead} in the note.mod schema file; and for the element types {@code metronome} and {@code stick} in the direction.mod schema file.
             *
             * @see FiguredBass
             * @see Metronome
             * @see Notehead
             * @see Stick
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Parentheses
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;

                /**
                 * Returns {@link Constant.MusicXML#NO}.
                 *
                 * @return {@link Constant.MusicXML#NO}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return NO;
                }
            }

            /**
             * {@code ParenthesesDegrees} classifies the MusicXML element attribute, {@code parentheses-degrees}.
             * <p/>
             * This attribute is declared by the attribute type {@code parentheses-degrees} for the element type {@code kind} in the direction.mod schema file.
             *
             * @see Kind
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface ParenthesesDegrees
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code Pizzicato} classifies the MusicXML element attribute, {@code pizzicato}.
             * <p/>
             * This attribute is declared by the attribute type {@code pizzicato} for the element type {@code note} in the note.mod schema file; and for the element type {@code sound} in the direction.mod schema file.
             *
             * @see Note
             * @see Sound
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Pizzicato
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code Placement} classifies the MusicXML element attribute, {@code placement}.
             * <p/>
             * This attribute is declared by the entity type {@code placement} in the common.mod schema file.
             * <p/>
             * The {@code placement} attribute indicates whether something is above or below another element, such as a note or a notation.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Placement
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.AboveBelow_Values;
            }

            /**
             * {@code PlusMinus} classifies the MusicXML element attribute, {@code plus-minus}.
             * <p/>
             * This attribute is declared by the attribute type {@code plus-minus} for the element type {@code degree-alter} in the direction.mod schema file.
             *
             * @see DegreeAlter
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface PlusMinus
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;

                /**
                 * Returns {@link Constant.MusicXML#NO}.
                 *
                 * @return {@link Constant.MusicXML#NO}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return NO;
                }
            }

            /**
             * {@code Position} classifies the MusicXML element attribute, {@code position}.
             * <p/>
             * This attribute is declared for the attribute type {@code position} for the element types {@code bookmark} and {@code link} in the link.mod schema file.
             *
             * @see Bookmark
             * @see Link
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Position
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.NMTOKEN_Literal;
            }

            /**
             * {@code Port} classifies the MusicXML element attribute, {@code port}.
             * <p/>
             * This attribute is declared by the attribute type {@code port} for the element type {@code midi-device} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Port
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code PrintDot} classifies the MusicXML element attribute, {@code print-dot}.
             * <p/>
             * This attribute is declared by the entity type {@code printout} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface PrintDot
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code PrintFrame} classifies the MusicXML element attribute, {@code print-frame}.
             * <p/>
             * This attribute is declared by the attribute type {@code print-frame} for the element type {@code harmony} in the direction.mod schema file.
             *
             * @see Harmony
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface PrintFrame
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code PrintLeger} classifies the MusicXML element attribute, {@code print-leger}.
             * <p/>
             * This attribute is declared by the attribute type {@code print-leger} for the element type {@code note} in the note.mod schema file.
             *
             * @see Note
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface PrintLeger
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;

                /**
                 * Returns {@link Constant.MusicXML#YES}.
                 *
                 * @return {@link Constant.MusicXML#YES}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return YES;
                }
            }

            /**
             * {@code PrintLyric} classifies the MusicXML element attribute, {@code print-lyric}.
             * <p/>
             * This attribute is declared by the entity type {@code printout} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface PrintLyric
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code PrintObject} classifies the MusicXML element attribute, {@code print-object}.
             * <p/>
             * This attribute is declared by the entity type {@code print-object} in the common.mod schema file for the element type {@code notations} in the note.mod schema file.
             *
             * @see Notations
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface PrintObject
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code PrintSpacing} classifies the MusicXML element attribute, {@code print-spacing}.
             * <p/>
             * This attribute is declared by the entity type {@code print-spacing} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface PrintSpacing
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code Reference} classifies the MusicXML element attribute, {@code reference}.
             * <p/>
             * This attribute is declared by the attribute type {@code reference} for the element type {@code level} in the common.mod schema file.
             *
             * @see Level
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Reference
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;

                /**
                 * Returns {@link Constant.MusicXML#NO}.
                 *
                 * @return {@link Constant.MusicXML#NO}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return NO;
                }
            }

            /**
             * {@code RelativeX} classifies the MusicXML element attribute, {@code relative-x}.
             * <p/>
             * This attribute is declared by the entity type {@code position} in the common.mod schema file.
             * <p/>
             * The {@code relative-x} attribute changes the position relative to the default position, either as computed by the individual program, or as overridden by the {@link DefaultX default-x} attribute.
             * <p/>
             * For the {@code relative-x} and {@link RelativeY relative-y} attributes, the {@link Element.Offset offset} element, {@link Placement placement} attribute, and {@link Directive directive} attribute provide context for the relative position information, so the two features should be interpreted together.
             * <p/>
             * The unit is in tenths of interline space.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface RelativeX
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.Tenths_Values;
            }

            /**
             * {@code RelativeY} classifies the MusicXML element attribute, {@code relative-y}.
             * <p/>
             * This attribute is declared by the entity type {@code position} in the common.mod schema file.
             * <p/>
             * The {@code relative-y} attribute changes the position relative to the default position, either as computed by the individual program, or as overridden by the {@link DefaultY default-y} attribute.
             * <p/>
             * For the {@link RelativeX relative-x} and {@code relative-y} attributes, the {@link Element.Offset offset} element, {@link Placement placement} attribute, and {@link Directive directive} attribute provide context for the relative position information, so the two features should be interpreted together.
             * <p/>
             * The unit is in tenths of interline space.
             * For stems, positive {@code relative-y} lengthens a stem while negative {@code relative-y} shortens it.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface RelativeY
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.Tenths_Values;
            }

            /**
             * {@code Release} classifies the MusicXML element attribute, {@code release}.
             * <p/>
             * This attribute is declared by the attribute type {@code release} for the element type {@code note} in the note.mod schema file.
             *
             * @see Note
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Release
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Repeater} classifies the MusicXML element attribute, {@code repeater}.
             * <p/>
             * This attribute is declared for the attribute type {@code repeater} for the element type {@code beam} in the note.mod schema file.
             *
             * @see Beam
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Repeater
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code Rotation} classifies the MusicXML element attribute, {@code rotation}.
             * <p/>
             * This attribute is declared by the entity type {@code text-rotation} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Rotation
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code SecondBeat} classifies the MusicXML element attribute, {@code second-beat}.
             * <p/>
             * This attribute is declared for the entity type {@code trill-sound} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface SecondBeat
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Segno} classifies the MusicXML element attribute, {@code segno}.
             * <p/>
             * This attribute is declared by the attribute type {@code segno} for the element type {@code sound} in the direction.mod schema file; and for the element type {@code barline} in the barline.mod schema file.
             *
             * @see Barline
             * @see Sound
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Segno
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Separator} classifies the MusicXML element attribute, {@code separator}.
             * <p/>
             * This attribute is declared by the entity type {@code time-separator} for the element types {@code interchangeable} and {@code time} in the attributes.mod schema file.
             * <p/>
             * The {@code time-separator} entity indicates how to display the arrangement between the {@link Beats beats} and {@link BeatType beat-type} values in a time signature.
             * The default value is none.
             * The horizontal, diagonal, and vertical values represent horizontal, diagonal lower-left to upper-right, and vertical lines respectively.
             * For these values, the {@link Beats beats} and {@link BeatType beat-type} values are arranged on either side of the separator line.
             * The none value represents no separator with the {@link Beats beats} and {@link BeatType beat-type} arranged vertically.
             * The adjacent value represents no separator with the {@link Beats beats} and {@link BeatType beat-type} arranged horizontally.
             *
             * @see Interchangeable
             * @see Time
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Separator
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    ADJACENT,
                    DIAGONAL,
                    HORIZONTAL,
                    NONE,
                    VERTICAL
                };

                /**
                 * Returns {@link Constant.MusicXML#NONE}.
                 *
                 * @return {@link Constant.MusicXML#NONE}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return NONE;
                }
            }

            /**
             * {@code ShowFrets} classifies the MusicXML element attribute, {@code show-frets}.
             * <p/>
             * This attribute is declared by the attribute type {@code show-frets} for the element type {@code staff-details} in the attributes.mod schema file.
             *
             * @see StaffDetails
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface ShowFrets
            extends Attribute
            {}

            /**
             * {@code ShowNumber} classifies the MusicXML element attribute, {@code show-number}.
             * <p/>
             * This attribute is declared by the attribute type {@code show-number} for the element type {@code tuplet} in the note.mod schema file; and for the element type {@code metronome-tuplet} in the direction.mod schema file.
             *
             * @see MetronomeTuplet
             * @see Tuplet
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface ShowNumber
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.ActualBothNone_Values;

                /**
                 * Returns {@link Constant.MusicXML#ACTUAL}.
                 *
                 * @return {@link Constant.MusicXML#ACTUAL}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return ACTUAL;
                }
            }

            /**
             * {@code ShowType} classifies the MusicXML element attribute, {@code show-type}.
             * <p/>
             * This attribute is declared by the attribute type {@code show-type} for the element type {@code tuplet} in the note.mod schema file.
             *
             * @see Tuplet
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface ShowType
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.ActualBothNone_Values;

                /**
                 * Returns {@link Constant.MusicXML#NONE}.
                 *
                 * @return {@link Constant.MusicXML#NONE}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return NONE;
                }
            }

            /**
             * {@code Sign} classifies the MusicXML element attribute, {@code sign}.
             * <p/>
             * This attribute is declared by the attribute type {@code sign} for the element type {@code pedal} in the direction.mod schema file.
             *
             * @see Pedal
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Sign
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;

                /**
                 * Returns {@link Constant.MusicXML#NO}.
                 *
                 * @return {@link Constant.MusicXML#NO}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return NO;
                }
            }

            /**
             * {@code Size} classifies the MusicXML element attribute, {@code size}.
             * <p/>
             * This attribute is declared by the attribute type {@code size} for the element type {@code clef} in the attributes.mod schema file; and for the element type {@code type} in the note.mod schema file; and for the element type (@code octave-shift} in the direction.mod schema file.
             * The values are declared by the entity type {@code symbol-size} in the common.mod schema file.
             *
             * @see Clef
             * @see OctaveShift
             * @see Type
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Size
            extends
                Analytic,
                XML.Schematic.PerType
            {
                /** The array of accepted element types. */
                Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                    Clef.class,
                    OctaveShift.class,
                    Type.class,
                };

                /**
                 * Returns the attribute for the specified element type.
                 *
                 * @param type the element class type.
                 *
                 * @return the attribute.
                 */
                @Override
                default Class<? extends Size> per(final Class<?> type) {
                    if (type == Clef.class ||
                        type == Type.class)
                        return Size_PerMany1.class;
                    else
                    if (type == OctaveShift.class)
                        return Size_PerOctaveShift.class;

                    return null;
                }

                interface Size_PerMany1
                extends Size
                {
                    java.lang.String[] Values = Entity.SymbolSize_Values;
                }

                interface Size_PerOctaveShift
                extends Size
                {
                    java.lang.String[] Values = Entity.CDATA_Literal;
                }
            }

            /**
             * {@code Slash} classifies the MusicXML element attribute, {@code slash}.
             * <p/>
             * This attribute is declared by the attribute type {@code slash} for the element types {@code delayed-turn}, {@code delayed-inverted-turn}, {@code grace}, {@code inverted-turn}, and {@code turn} in the note.mod schema file.
             *
             * @see DelayedTurn
             * @see DelayedInvertedTurn
             * @see Grace
             * @see InvertedTurn
             * @see Turn
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Slash
            extends
                Analytic,
                XML.Schematic.PerType
            {
                /** The array of accepted element types. */
                Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                    DelayedTurn.class,
                    DelayedInvertedTurn.class,
                    Grace.class,
                    InvertedTurn.class,
                    Turn.class,
                };

                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;

                /**
                 * Returns the attribute for the specified element type.
                 *
                 * @param type the element class type.
                 *
                 * @return the attribute.
                 */
                @Override
                default Class<? extends Slash> per(final Class<?> type) {
                    if (type == DelayedTurn.class ||
                        type == DelayedInvertedTurn.class ||
                        type == InvertedTurn.class ||
                        type == Turn.class)
                        return Slash_PerMany1.class;
                    else
                    if (type == Grace.class)
                        return Slash.class;

                    return null;
                }

                interface Slash_PerMany1
                extends Slash
                {
                    @Override
                    default java.lang.String defaultValue() {
                        return NO;
                    }
                }
            }

            /**
             * {@code Slashes} classifies the MusicXML element attribute, {@code slashes}.
             * <p/>
             * This attribute is declared by the attribute type {@code slashes} for the element types {@code beat-repeat}, {@code measure-repeat} in the attributes.mod schema file.
             *
             * @see BeatRepeat
             * @see MeasureRepeat
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Slashes
            extends Analytic
            {}

            /**
             * {@code SMuFL} classifies the MusicXML element attribute, {@code smufl}.
             * <p/>
             * This attribute is declared by the entity type {@code smufl} in the common.mod schema file, for the element type {@code key} in the attributes.mod schema file.
             * The attribute value is declared by the entity type {@code smufl-glyph-name} in the common.mod schema file.
             *
             * @see Key
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface SMuFL
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.SMuFLGlyphName_Values;
            }

            /**
             * {@code SoftPedal} classifies the MusicXML element attribute, {@code soft-pedal}.
             * <p/>
             * This attribute is declared by the attribute type {@code soft-pedal} for the element type {@code sound} in the direction.mod schema file.
             *
             * @see Sound
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface SoftPedal
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNoNumber_Values;
            }

            /**
             * {@code SostenutoPedal} classifies the MusicXML element attribute, {@code sostenuto-pedal}.
             * <p/>
             * This attribute is declared by the attribute type {@code sostenuto-pedal} for the element type {@code sound} in the direction.mod schema file.
             *
             * @see Sound
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface SostenutoPedal
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNoNumber_Values;
            }

            /**
             * {@code Sound} classifies the MusicXML element attribute, {@code sound}.
             * <p/>
             * This attribute is declared by the attribute type {@code sound} for the element type {@code offset} in the direction.mod schema file.
             *
             * @see Offset
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Sound
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code Source} classifies the MusicXML element attribute, {@code source}.
             * <p/>
             * This attribute is declared by the attribute type {@code source} for the element type {@code credit-image} in the score.mod schema file; and for the element type {@code image} in the direction.mod schema file.
             *
             * @see CreditImage
             * @see Image
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Source
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;

                /**
                 * Return true.
                 *
                 * @return true.
                 */
                @Override
                default boolean isRequired() {
                    return true;
                }
            }

            /**
             * {@code SpaceLength} classifies the MusicXML element attribute, {@code space-length}.
             * <p/>
             * This attribute is declared by the entity type {@code dash-formatting} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface SpaceLength
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Spread} classifies the MusicXML element attribute, {@code spread}.
             * <p/>
             * This attribute is declared by the attribute type {@code spread} for the element type {@code wedge} in the direction.mod schema file.
             *
             * @see Wedge
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Spread
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.Tenths_Values;
            }

            /**
             * {@code StackDegrees} classifies the MusicXML element attribute, {@code stack-degrees}.
             * <p/>
             * This attribute is declared by the attribute type {@code stack-degrees} for the element type {@code kind} in the direction.mod schema file.
             *
             * @see Kind
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface StackDegrees
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;
            }

            /**
             * {@code StaffSpacing} classifies the MusicXML element attribute, {@code staff-spacing}.
             * <p/>
             * This attribute is declared by the attribute type {@code staff-spacing} for the element type {@code print} in the direction.mod schema file.
             * The attribute values are numeric in tenths.
             *
             * @see Print
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface StaffSpacing
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.Tenths_Values;
            }

            /**
             * {@code StartNote} classifies the MusicXML element attribute, {@code start-note}.
             * <p/>
             * This attribute is declared for the element type {@code trill-sound} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface StartNote
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    BELOW,
                    MAIN,
                    UPPER
                };
            }

            /**
             * {@code StealTimeFollowing} classifies the MusicXML element attribute, {@code steal-time-following}.
             * <p/>
             * This attribute is declared by the attribute type {@code steal-time-following} for the element type {@code grace} in the note.mod schema file.
             *
             * @see Grace
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface StealTimeFollowing
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code StealTimePrevious} classifies the MusicXML element attribute, {@code steal-time-previous}.
             * <p/>
             * This attribute is declared by the attribute type {@code steal-time-previous} for the element type {@code grace} in the note.mod schema file.
             *
             * @see Grace
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface StealTimePrevious
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code String} classifies the MusicXML element attribute, {@code string}.
             * <p/>
             * This attribute is declared for the attribute type {@code string} for the element type {@code accord} in the direction.mod schema file.
             *
             * @see Accord
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface String
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;

                /**
                 * Return true.
                 *
                 * @return true.
                 */
                @Override
                default boolean isRequired() {
                    return true;
                }
            }

            /**
             * {@code Substitution} classifies the MusicXML element attribute, {@code substitution}.
             * <p/>
             * This attribute is declared by the attribute type {@code substitution} for the element type {@code fingering} in the common.mod schema file; and for the element type {@code heel} in the note.mod schema file.
             *
             * @see Fingering
             * @see Heel
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Substitution
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;

                /**
                 * Returns {@link Constant.MusicXML#NO}.
                 *
                 * @return {@link Constant.MusicXML#NO}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return NO;
                }
            }

            /**
             * {@code Symbol} classifies the MusicXML element attribute, {@code symbol}.
             * <p/>
             * This attribute is declared by the entity type {@code time-symbol} for the element types {@code interchangeable} and {@code time} in the attributes.mod schema file; and for the element types {@code degree-value} and {@code principal-voice} in the direction.mod schema file.
             * <p/>
             * The {@code time-symbol} entity indicates how to display a time signature.
             * The normal value is the usual fractional display, and is the implied symbol type if none is specified.
             * Other options are the common and cut time symbols, as well as a single number with an implied denominator.
             * The note symbol indicates that the {@link BeatType beat-type} should be represented with the corresponding downstem note rather than a number.
             * The dotted-note symbol indicates that the {@link BeatType beat-type} should be represented with a dotted downstem note that corresponds to three times the {@link BeatType beat-type} value, and a numerator that is one third the {@link Beats beats} value.
             *
             * @see DegreeValue
             * @see Interchangeable
             * @see PrincipalVoice
             * @see Time
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Symbol
            extends
                Analytic,
                XML.Schematic.PerType
            {
                /** The array of accepted element types. */
                Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                    DegreeValue.class,
                    Interchangeable.class,
                    PrincipalVoice.class,
                    Time.class,
                };

                @Override
                default Class<? extends Symbol> per(final Class<?> type) {
                    if (type == DegreeValue.class)
                        return Symbol_PerDegreeValue.class;
                    else
                    if (type == Interchangeable.class ||
                        type == Time.class)
                        return Symbol_PerMany1.class;
                    else
                    if (type == PrincipalVoice.class)
                        return Symbol_PerPrincipalVoice.class;

                    return null;
                }

                interface Symbol_PerDegreeValue
                extends Symbol
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        AUGMENTED,
                        DIMINISHED,
                        HALF_DIMINISHED,
                        MAJOR,
                        MINOR
                    };
                }

                interface Symbol_PerMany1
                extends Symbol
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        COMMON,
                        CUT,
                        DOTTED_NOTE,
                        NORMAL,
                        NOTE,
                        SINGLE_NUMBER
                    };

                    @Override
                    default java.lang.String defaultValue() {
                        return NORMAL;
                    }
                }

                interface Symbol_PerPrincipalVoice
                extends
                    RequiredAttribute,
                    Symbol
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        HAUPTSTIMME,
                        NEBENSTIMME,
                        NONE,
                        PLAIN
                    };
                }
            }

            /**
             * {@code Tempo} classifies the MusicXML element attribute, {@code tempo}.
             * <p/>
             * This attribute is declared by the attribute type {@code tempo} for the element type {@code sound} in the direction.mod schema file.
             *
             * @see Sound
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Tempo
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Text} classifies the MusicXML element attribute, {@code text}.
             * <p/>
             * This attribute is declared by the attribute type {@code text} for the element types {@code measure} in the score.mod schema file; and for the element types {@code bass-step}, {@code degree-type}, {@code degree-value}, {@code first-fret}, {@code kind}, and {@code root-step} in the direction.mod schema file.
             *
             * @see BassStep
             * @see DegreeType
             * @see DegreeValue
             * @see FirstFret
             * @see Kind
             * @see Measure
             * @see RootStep
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Text
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code TextX} classifies the MusicXML element attribute, {@code text-x}.
             * <p/>
             * This attribute is declared by the attribute type {@code text-x} for the element type {@code ending} in the barline.mod schema file.
             *
             * @see Ending
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface TextX
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.Tenths_Values;
            }

            /**
             * {@code TextY} classifies the MusicXML element attribute, {@code text-y}.
             * <p/>
             * This attribute is declared by the attribute type {@code text-y} for the element type {@code ending} in the barline.mod schema file.
             *
             * @see Ending
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface TextY
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.Tenths_Values;
            }

            /**
             * {@code TimeOnly} classifies the MusicXML element attribute, {@code time-only}.
             * <p/>
             * This attribute is declared by the entity type {@code time-only} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface TimeOnly
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Times} classifies the MusicXML element attribute, {@code times}.
             * <p/>
             * This attribute is declared for the attribute type {@code times} for the element type {@code repeat} in the barline.mod schema file.
             *
             * @see Repeat
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Times
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Tip} classifies the MusicXML element attribute, {@code tip}.
             * <p/>
             * This attribute is declared by the attribute type {@code tip} for the element types {@code beater} and {@code stick} in the direction.mod schema file.
             *
             * @see Beater
             * @see Stick
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Tip
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.TopDirection_Values;
            }

            /**
             * {@code Tocoda} classifies the MusicXML element attribute, {@code tocoda}.
             * <p/>
             * This attribute is declared by the attribute type {@code tocoda} for the element type {@code sound} in the direction.mod schema file.
             *
             * @see Sound
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Tocoda
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code TopStaff} classifies the MusicXML element attribute, {@code top-staff}.
             * <p/>
             * This attribute is declared by the attribute type {@code top-staff} for the element type {@code part-symbol} in the attributes.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface TopStaff
            extends Attribute
            {}

            /**
             * {@code TrillStep} classifies the MusicXML element attribute, {@code trill-step}.
             * <p/>
             * This attribute is declared for the entity type {@code trill-sound} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface TrillStep
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    HALF,
                    UNISON,
                    WHOLE
                };
            }

            /**
             * {@code TwoNoteTurn} classifies the MusicXML element attribute, {@code two-note-turn}.
             * <p/>
             * This attribute is declared for the entity type {@code trill-sound} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface TwoNoteTurn
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    HALF,
                    NONE,
                    WHOLE
                };
            }

            /**
             * {@code Type} classifies the MusicXML element attribute, {@code type}.
             * <p/>
             * This attribute is declared by the attribute type {@code type} for the element types {@code beat-repeat}, {@code measure-repeat}, and {@code slash} in the attributes.mod schema file; the element types {@code fermata}, {@code other-play}, and {@code wavy-line} in common.mod; and the element types {@code extend}, {@code glissando}, {@code hammer-on}, {@code non-arpeggiate}, {@code other-notation}, {@code pull-off}, {@code slide}, {@code slur}, {@code strong-accent}, {@code tie}, {@code tied}, {@code tremolo}, and {@code tuplet} in note.mod; and for the element types {@code credit-image} and {@code part-group} in the score.mod schema file; and for the element types {@code creator}, {@code encoder}, {@code relation}, {@code rights}, and {@code supports} in the identify.mod schema file; and for the element types {@code barre}, {@code bracket}, {@code dashes}, {@code feature}, {@code grouping}, {@code harmony}, {@code image}, {@code metronome-tied}, {@code metronome-tuplet}, {@code octave-shift}, {@code pedal}, {@code principle-voice}, {@code staff-divide}, {@code string-mute}, and {@code wedge} in the direction.mod schema file; and for the element types {@code distance}, {@code glyph}, {@code line-width}, {@code note-size}, {@code other-appearance}, and {@code page-margins} in the layout.mod schema file; and the element type {@code ending} in the barline.mod schema file.
             *
             * @see Barre
             * @see BeatRepeat
             * @see Bracket
             * @see Creator
             * @see CreditImage
             * @see Dashes
             * @see Distance
             * @see Encoder
             * @see Ending
             * @see Extend
             * @see Feature
             * @see Fermata
             * @see Glissando
             * @see Glyph
             * @see Grouping
             * @see HammerOn
             * @see Harmony
             * @see Image
             * @see LineWidth
             * @see NoteSize
             * @see MeasureRepeat
             * @see MetronomeTied
             * @see MetronomeTuplet
             * @see NonArpeggiate
             * @see OctaveShift
             * @see OtherAppearance
             * @see OtherNotation
             * @see OtherPlay
             * @see PageMargins
             * @see PartGroup
             * @see Pedal
             * @see PrincipalVoice
             * @see PullOff
             * @see Relation
             * @see Rights
             * @see Slash
             * @see Slide
             * @see Slur
             * @see StaffDivide
             * @see StringMute
             * @see StrongAccent
             * @see Supports
             * @see Tie
             * @see Tied
             * @see Tremolo
             * @see Tuplet
             * @see WavyLine
             * @see Wedge
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Type
            extends
                Analytic,
                RequiredAttribute,
                XML.Schematic.PerType
            {
                /** The array of accepted element types. */
                Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                    Barre.class,
                    BeatType.class,
                    Bracket.class,
                    Creator.class,
                    CreditImage.class,
                    Dashes.class,
                    Distance.class,
                    Encoder.class,
                    Ending.class,
                    Extend.class,
                    Feature.class,
                    Fermata.class,
                    Glissando.class,
                    Glyph.class,
                    Grouping.class,
                    HammerOn.class,
                    Harmony.class,
                    Image.class,
                    LineWidth.class,
                    MeasureStyle.class,
                    MetronomeTied.class,
                    MetronomeTuplet.class,
                    NonArpeggiate.class,
                    NoteSize.class,
                    OctaveShift.class,
                    OtherAppearance.class,
                    OtherNotation.class,
                    OtherPlay.class,
                    PartGroup.class,
                    Pedal.class,
                    PrincipalVoice.class,
                    PullOff.class,
                    Relation.class,
                    Rights.class,
                    Slide.class,
                    Slur.class,
                    StaffDivide.class,
                    StringMute.class,
                    StrongAccent.class,
                    Supports.class,
                    Tied.class,
                    Tremolo.class,
                    Tuplet.class,
                    WavyLine.class,
                    Wedge.class,
                };

                /**
                 * Return true.
                 *
                 * @return true.
                 */
                @Override
                default boolean isRequired() {
                    return true;
                }

                /**
                 * Returns the attribute for the specified element type.
                 *
                 * @param type the element class type.
                 *
                 * @return the attribute.
                 */
                @Override
                default Class<? extends Type> per(final Class<?> type) {
                    if (type == Barre.class ||
                        type == BeatType.class ||
                        type == Dashes.class ||
                        type == Glissando.class ||
                        type == HammerOn.class ||
                        type == MeasureRepeat.class ||
                        type == MetronomeTied.class ||
                        type == MetronomeTuplet.class ||
                        type == PartGroup.class ||
                        type == PrincipalVoice.class ||
                        type == PullOff.class ||
                        type == Slide.class ||
                        type == Tie.class ||
                        type == Tuplet.class)
                        return Type_PerMany1.class;
                    else
                    if (type == Bracket.class ||
                        type == Slur.class)
                        return Type_PerMany2.class;
                    else
                    if (type == Creator.class)
                        return Type_PerCreator.class;
                    else
                    if (type == CreditImage.class ||
                        type == Image.class)
                        return Type_PerMany3.class;
                    else
                    if (type == Creator.class ||
                        type == Encoder.class ||
                        type == Feature.class ||
                        type == OtherAppearance.class ||
                        type == OtherPlay.class ||
                        type == Relation.class ||
                        type == Rights.class)
                        return Type_PerMany4.class;
                    else
                    if (type == Distance.class)
                        return Type_PerDistance.class;
                    else
                    if (type == Ending.class)
                        return Type_PerEnding.class;
                    else
                    if (type == Extend.class ||
                        type == WavyLine.class)
                        return Type_PerMany5.class;
                    else
                    if (type == Fermata.class)
                        return Type_PerFermata.class;
                    else
                    if (type == Grouping.class ||
                        type == OtherNotation.class)
                        return Type_PerMany6.class;
                    else
                    if (type == Glyph.class)
                        return Type_PerGlyph.class;
                    else
                    if (type == Harmony.class)
                        return Type_PerHarmony.class;
                    else
                    if (type == LineWidth.class)
                        return Type_PerLineWidth.class;
                    else
                    if (type == NonArpeggiate.class)
                        return Type_NonArpeggiate.class;
                    else
                    if (type == NoteSize.class)
                        return Type_PerNoteSize.class;
                    else
                    if (type == OctaveShift.class)
                        return Type_PerOctaveShift.class;
                    else
                    if (type == Pedal.class)
                        return Type_PerPedal.class;
                    else
                    if (type == Rights.class)
                        return Type_PerRights.class;
                    else
                    if (type == StaffDivide.class)
                        return Type_PerStaffDivide.class;
                    else
                    if (type == StringMute.class)
                        return Type_PerStringMute.class;
                    else
                    if (type == StrongAccent.class)
                        return Type_PerStrongAccent.class;
                    else
                    if (type == Tremolo.class)
                        return Type_PerTremolo.class;
                    else
                    if (type == Supports.class)
                        return Type_PerSupports.class;
                    else
                    if (type == Tied.class)
                        return Type_PerTied.class;
                    else
                    if (type == Wedge.class)
                        return Type_PerWedge.class;

                    return null;
                }

                interface Type_PerMany1
                extends Type
                {
                    java.lang.String[] Values = Entity.StartStop_Values;
                }

                interface Type_PerMany2
                extends Type
                {
                    java.lang.String[] Values = Entity.StartStopContinue_Values;
                }

                interface Type_PerCreator
                extends Type
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        ARRANGER,
                        COMPOSER,
                        LYRICIST
                    };
                }

                interface Type_PerMany3
                extends Type
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        "application/postscript",
                        "image/gif",
                        "image/jpeg",
                        "image/png",
                        "image/tiff"
                    };
                }

                interface Type_PerMany4
                extends Type
                {
                    java.lang.String[] Values = Entity.CDATA_Literal;
                }

                interface Type_PerDistance
                extends Type
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        BEAM,
                        HYPHEN
                    };
                }

                interface Type_PerEnding
                extends Type
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        DISCONTINUE,
                        START,
                        STOP
                    };
                }

                interface Type_PerMany5
                extends Type
                {
                    java.lang.String[] Values = Entity.StartStopContinue_Values;
                }

                interface Type_PerFermata
                extends Type
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        INVERTED,
                        UPRIGHT
                    };
                }

                interface Type_PerMany6
                extends Type
                {
                    java.lang.String[] Values = Entity.StartStopSingle_Values;
                }

                interface Type_PerGlyph
                extends Type
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        C_CLEF,
                        F_CLEF,
                        G_CLEF_OTTAVA_BASSA,
                        OCTAVE_SHIFT_CONTINUE_15,
                        OCTAVE_SHIFT_CONTINUE_22,
                        OCTAVE_SHIFT_CONTINUE_8,
                        OCTAVE_SHIFT_DOWN_15,
                        OCTAVE_SHIFT_DOWN_22,
                        OCTAVE_SHIFT_DOWN_8,
                        OCTAVE_SHIFT_UP_15,
                        OCTAVE_SHIFT_UP_22,
                        OCTAVE_SHIFT_UP_8,
                        PERCUSSION_CLEF,
                        QUARTER_REST
                    };
                }

                interface Type_PerHarmony
                extends Type
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        ALTERNATE,
                        EXPLICIT,
                        IMPLIED
                    };
                }

                interface Type_PerLineWidth
                extends Type
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        BEAM,
                        BRACKET,
                        DASHES,
                        ENCLOSURE,
                        ENDING,
                        EXTEND,
                        HEAVY__BARLINE,
                        LEGER,
                        LIGHT__BARLINE,
                        OCTAVE__SHIFT,
                        PEDAL,
                        SLUR__MIDDLE,
                        SLUR__TIP,
                        STAFF,
                        STEM,
                        TIE__MIDDLE,
                        TIE__TIP,
                        TUPLET__BRACKET,
                        WEDGE
                    };
                }

                interface Type_NonArpeggiate
                extends Type
                {
                    java.lang.String[] Values = Entity.TopBottom_Values;
                }

                interface Type_PerNoteSize
                extends Type
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        CUE,
                        GRACE,
                        GRACE_CUE,
                        LARGE
                    };
                }

                interface Type_PerOctaveShift
                extends Type
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        CONTINUE,
                        DOWN,
                        STOP,
                        UP
                    };
                }

                interface Type_PerPedal
                extends Type
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        CHANGE,
                        CONTINUE,
                        SOSTENUTO,
                        START,
                        STOP
                    };
                }

                interface Type_PerRights
                extends Type
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        ARRANGEMENT,
                        MUSIC,
                        WORDS
                    };
                }

                interface Type_PerStaffDivide
                extends Type
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        DOWN,
                        UP,
                        UP_DOWN
                    };
                }

                interface Type_PerStringMute
                extends Type
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        OFF,
                        ON
                    };
                }

                interface Type_PerStrongAccent
                extends Type
                {
                    java.lang.String[] Values = Entity.UpDown_Values;

                    @Override
                    default java.lang.String defaultValue() {
                        return UP;
                    }
                }

                interface Type_PerTremolo
                extends Type
                {
                    java.lang.String[] Values = Entity.TremoloType_Values;

                    @Override
                    default java.lang.String defaultValue() {
                        return SINGLE;
                    }
                }

                interface Type_PerSupports
                extends Type
                {
                    java.lang.String[] Values = Entity.YesNo_Values;
                }

                interface Type_PerTied
                extends Type
                {
                    java.lang.String[] Values = Entity.TiedType_Values;
                }

                interface Type_PerWedge
                extends Type
                {
                    java.lang.String[] Values = new java.lang.String[] {
                        CONTINUE,
                        CRESCENDO,
                        DIMINUENDO,
                        STOP
                    };
                }
            }

            /**
             * {@code Underline} classifies the MusicXML element attribute, {@code underline}.
             * <p/>
             * This attribute is declared by the entity type {@code text-decoration} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Underline
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.NumberOfLines_Values;
            }

            /**
             * {@code Unplayed} classifies the MusicXML element attribute, {@code unplayed}.
             * <p/>
             * This attribute is declared for the attribute type {@code unplayed} for the element type {@code frame} in the direction.mod schema file.
             *
             * @see Frame
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Unplayed
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    "",
                    X
                };
            }

            /**
             * {@code UseDots} classifies the MusicXML element attribute, {@code use-dots}.
             * <p/>
             * This attribute is declared by the attribute type {@code use-dots} for the element types {@code beat-repeat} and {@code slash} in the attributes.mod schema file.
             *
             * @see BeatRepeat
             * @see Slash
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface UseDots
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;

                /**
                 * Returns {@link Constant.MusicXML#NO}.
                 *
                 * @return {@link Constant.MusicXML#NO}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return NO;
                }
            }

            /**
             * {@code UseStems} classifies the MusicXML element attribute, {@code use-stems}.
             * <p/>
             * This attribute is declared by the attribute type {@code use-stems} for the element type {@code slash} in the attributes.mod schema file.
             *
             * @see Slash
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface UseStems
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;

                /**
                 * Returns {@link Constant.MusicXML#NO}.
                 *
                 * @return {@link Constant.MusicXML#NO}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return NO;
                }
            }

            /**
             * {@code UseSymbols} classifies the MusicXML element attribute, {@code use-symbols}.
             * <p/>
             * This attribute is declared by the attribute type {@code use-symbols} for the element type {@code multiple-rest} in the attributes.mod schema file; and for the element type {@code kind} in the direction.mod schema file.
             *
             * @see Kind
             * @see MultipleRest
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface UseSymbols
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.YesNo_Values;

                /**
                 * Returns {@link Constant.MusicXML#NO}.
                 *
                 * @return {@link Constant.MusicXML#NO}.
                 */
                @Override
                default java.lang.String defaultValue() {
                    return NO;
                }
            }

            /**
             * {@code VAlign} classifies the MusicXML element attribute, {@code valign}.
             * <p/>
             * This attribute is declared by the entity types {@code valign} and {@code valign-image} in the common.mod schema file.
             * <p/>
             * The {@code valign} entity is used to indicate vertical alignment to the top, middle, bottom, or baseline of the text.
             * Defaults are implementation-dependent.
             * <p/>
             * The {@code valign-image} entity is used to indicate vertical alignment for images and graphics, so it removes the baseline value.
             * Defaults are implementation-dependent.
             *
             * @see CreditImage
             * @see Frame
             * @see Image
             * @see Kind
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface VAlign
            extends
                Attribute,
                XML.Schematic.PerName,
                XML.Schematic.PerType
            {
                /** The array of accepted entity names. */
                java.lang.String[] Names = new java.lang.String[] {
                    VALIGN,
                    VALIGN_IMAGE
                };

                /** The array of accepted element types. */
                Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                    CreditImage.class,
                    Frame.class,
                    Image.class,
                    Kind.class,
                };

                /**
                 * Returns the attribute for the specified entity name.
                 *
                 * @param name the entity name.
                 *
                 * @return the attribute.
                 */
                @Override
                default Class<? extends VAlign> per(final java.lang.String name) {
                    switch (name)
                    {
                        case VALIGN:
                        return VAlign_PerMany1.class;

                        case VALIGN_IMAGE:
                        return VAlign_PerKind.class;
                    }

                    return null;
                }

                /**
                 * Returns the attribute for the specified element type.
                 *
                 * @param type the element class type.
                 *
                 * @return the attribute.
                 */
                @Override
                default Class<? extends VAlign> per(final Class<?> type) {
                    if (type == CreditImage.class ||
                        type == Frame.class ||
                        type == Image.class)
                        return VAlign_PerMany1.class;
                    else
                    if (type == Kind.class)
                        return VAlign_PerKind.class;

                    return null;
                }

                interface VAlign_PerMany1
                extends VAlign
                {
                    java.lang.String[] Values = Entity.VAlign_Values;
                }

                interface VAlign_PerKind
                extends VAlign
                {
                    java.lang.String[] Values = Entity.VAlignImage_Values;
                }
            }

            /**
             * {@code Value} classifies the MusicXML element attribute, {@code value}.
             * <p/>
             * This attribute is declared for the attribute type {@code value} for the element type {@code supports} in the identify.mod schema file.
             *
             * @see Supports
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Value
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Version} classifies the MusicXML element attribute, {@code version}.
             * <p/>
             * This attribute is declared by the entity type {@code document-attribute} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Version
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code Width} classifies the MusicXML element attribute, {@code width}.
             * <p/>
             * This attribute is declared by the attribute type {@code width} for the element types {@code credit-image} and {@code measure} in the score.mod schema file; and for the element types {@code frame} and {@code image} in the direction.mod schema file.
             * The attribute values are numeric in tenths.
             *
             * @see CreditImage
             * @see Frame
             * @see Image
             * @see Measure
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Width
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.Tenths_Values;
            }

            /**
             * {@code Winged} classifies the MusicXML element attribute, {@code winged}.
             * <p/>
             * This attribute is declared by the attribute type {@code winged} for the element type {@code repeat} in the barline.mod schema file.
             *
             * @see Repeat
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface Winged
            extends Analytic
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    CURVED,
                    DOUBLE_CURVED,
                    DOUBLE_STRAIGHT,
                    NONE,
                    STRAIGHT
                };
            }

            /**
             * {@code XlinkActuate} classifies the XML element attribute, {@code xlink:actuate}.
             * <p/>
             * This attribute is declared by the entity type {@code link-attributes} in the link.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface XlinkActuate
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    NONE,
                    ONLOAD,
                    ONREQUEST,
                    OTHER
                };

                @Override
                default java.lang.String defaultValue() {
                    return ONREQUEST;
                }
            }

            /**
             * {@code XlinkHref} classifies the XML element attribute, {@code xlink:href}.
             * <p/>
             * This attribute is declared by the entity type {@code link-attributes} in the link.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface XlinkHref
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;

                @Override
                default boolean isRequired() {
                    return true;
                }
            }

            /**
             * {@code XlinkRole} classifies the XML element attribute, {@code xlink:role}.
             * <p/>
             * This attribute is declared by the entity type {@code link-attributes} in the link.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface XlinkRole
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code XlinkShow} classifies the XML element attribute, {@code xlink:show}.
             * <p/>
             * This attribute is declared by the entity type {@code link-attributes} in the link.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface XlinkShow
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    EMBED,
                    NEW,
                    NONE,
                    OTHER,
                    REPLACE
                };

                @Override
                default java.lang.String defaultValue() {
                    return REPLACE;
                }
            }

            /**
             * {@code XlinkTitle} classifies the XML element attribute, {@code xlink:title}.
             * <p/>
             * This attribute is declared by the entity type {@code link-attributes} in the link.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface XlinkTitle
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code XlinkType} classifies the XML element attribute, {@code xlink:type}.
             * <p/>
             * This attribute is declared by the entity type {@code link-attributes} in the link.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface XlinkType
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    SIMPLE
                };

                @Override
                default java.lang.String defaultValue() {
                    return SIMPLE;
                }
            }

            /**
             * {@code XmlLang} classifies the XML element attribute, {@code xml:lang}.
             * <p/>
             * This attribute is declared for the element type {@code directive} in the attributes.mod schema file; and for the element type {@code text} in the note.mod schema file; and for the element type {@code lyric-language} in the score.mod schema file.
             *
             * @see Directive
             * @see LyricLanguage
             * @see Text
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface XmlLang
            extends
                Attribute,
                XML.Schematic.PerType
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.NMTOKEN_Literal;

                /**
                 * Returns the attribute for the specified element type.
                 *
                 * @param type the element class type.
                 *
                 * @return the attribute.
                 */
                @Override
                default Class<?extends XmlLang> per(final Class<?> type) {
                    if (type == Directive.class ||
                        type == Text.class)
                        return XmlLang_PerMany1.class;
                    else
                    if (type == LyricLanguage.class)
                        return XmlLang_PerLyricLanguage.class;

                    return null;
                }

                interface XmlLang_PerMany1
                extends XmlLang
                {
                    @Override
                    default boolean isRequired() {
                        return false;
                    }
                }

                interface XmlLang_PerLyricLanguage
                extends
                    RequiredAttribute,
                    XmlLang
                {}
            }

            /**
             * {@code XmlnsXlink} classifies the XML element attribute, {@code xmlns:xlink}.
             * <p/>
             * This attribute is declared by the entity type {@code link-attributes} in the link.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface XmlnsXlink
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = Entity.CDATA_Literal;
            }

            /**
             * {@code XmlSpace} classifies the XML element attribute, {@code xml:space}.
             * <p/>
             * This attribute is declared for the element type {@code text-formatting} in the common.mod schema file.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            interface XmlSpace
            extends Attribute
            {
                /** The array of accepted values. */
                java.lang.String[] Values = new java.lang.String[] {
                    DEFAULT,
                    PRESERVE
                };
            }
        }

        /**
         * {@code Attributes} classifies the MusicXML element, {@code attributes}.
         * <p/>
         * This element is declared by the element type {@code attributes} in the attributes.mod schema file.
         * <p/>
         * The {@code attributes} element contains musical information that typically changes on measure boundaries.
         * This includes key and time signatures, clefs, transpositions, and staving.
         * When attributes are changed mid-measure, it affects the music in score order, not in MusicXML document order.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Attributes
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Footnote.class,
                Level.class,
                Divisions.class,
                Key.class,
                Time.class,
                Staves.class,
                PartSymbol.class,
                Instruments.class,
                Clef.class,
                StaffDetails.class,
                Transpose.class,
                Directive.class,
                MeasureStyle.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Backup} classifies the MusicXML element, {@code backup}.
         * <p/>
         * This element is declared by the element type {@code backup} in the note.mod schema file, for the entity type {@code music-data} in the score.mod schema file.
         * <p/>
         * The {@code backup} element is required to coordinate multiple voices in one {@link Part part}, including music on multiple staves.
         * The {@code backup} element is generally used to move between voices and staves.
         * Thus the {@code backup} element does not include {@link Voice voice} or {@link Staff staff} elements.
         * {@link Duration} values should always be positive, and should not cross measure boundaries or mid-measure changes in the {@link Divisions divisions} value.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Backup
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Duration.class,
                // editorial
                Element.Footnote.class,
                Element.Level.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Barline} classifies the MusicXML element, {@code barline}.
         * <p/>
         * This element is declared by the element type {@code barline} in the barline.mod schema file, for the entity type {@code music-data} in the score.mod schema file.
         * <p/>
         * If a barline is other than a normal single barline, it should be represented by a {@code barline} element that describes it.
         * This includes information about repeats and multiple endings, as well as line style.
         * Barline data is on the same level as the other musical data in a score - a child of a {@link Measure measure} in a {@link ScorePartwise partwise} score, or a {@link Part part} in a {@link ScoreTimewise timewise} score.
         * This allows for barlines within measures, as in dotted barlines that subdivide measures in complex meters.
         * The two {@link Fermata fermata} elements allow for fermatas on both sides of the barline (the lower one inverted).
         * <p/>
         * Barlines have a {@link Element.Attribute.Location location} attribute to make it easier to process barlines independently of the other musical data in a score.
         * It is often easier to set up measures separately from entering notes.
         * The {@link Element.Attribute.Location location} attribute must match where the {@code barline} element occurs within the rest of the musical data in the score.
         * If location is left, it should be the first element in the {@link Measure measure}, aside from the {@link Print print}, {@link Bookmark bookmark}, and {@link Link link} elements.
         * If location is right, it should be the last element, again with the possible exception of the {@link Print print}, {@link Bookmark bookmark}, and {@link Link link} elements.
         * If no location is specified, the right barline is the default.
         * The {@link Element.Attribute.Segno segno}, {@link Element.Attribute.Coda coda}, and {@link Element.Attribute.Divisions divisions} attributes work the same way as in the {@link Sound sound} element defined in the direction.mod file.
         * They are used for playback when {@code barline} elements contain {@link Segno segno} or {@link Coda coda} child elements.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Barline
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Location.class,
                Element.Attribute.Segno.class,
                Element.Attribute.Coda.class,
                Element.Attribute.Divisions.class,
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                BarStyle.class,
                WavyLine.class,
                Segno.class,
                Coda.class,
                Fermata.class,
                Ending.class,
                Repeat.class,
                Element.Footnote.class,
                Element.Level.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = null;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Barre} classifies the MusicXML element, {@code barre}.
         * <p/>
         * This element is declared by the element type {@code barre} for the element type {@code frame-note} in the direction.mod schema file.
         * <p/>
         * The {@code barre} element indicates placing a finger over multiple strings on a single fret.
         * The {@link Element.Attribute.Type type} is "start" for the lowest pitched string (e.g., the string with the highest MusicXML number) and is "stop" for the highest pitched string.
         *
         * @see FrameNote
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Barre
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Color.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code BarStyle} classifies the MusicXML element, {@code bar-style}.
         * <p/>
         * This element is declared by the element type {@code bar-style} for the element type {@code barline} in the barline.mod schema file.
         * <p/>
         * Bar-style contains style information.
         * Choices are regular, dotted, dashed, heavy, light-light, light-heavy, heavy-light, heavy-heavy, tick (a short stroke through the top line), short (a partial barline between the 2nd and 4th lines), and none.
         *
         * @see Barline
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface BarStyle
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Color.class,
            };

            /** The element definition. */
            java.lang.String Definition = null;

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                DASHED,
                DOTTED,
                HEAVY,
                HEAVY_HEAVY,
                HEAVY_LIGHT,
                LIGHT_HEAVY,
                LIGHT_LIGHT,
                NONE,
                REGULAR,
                SHORT,
                TICK
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code BasePitch} classifies the MusicXML element, {@code base-pitch}.
         * <p/>
         * This element is declared by the element type {@code base-pitch} for the entity type {@code technical} in the note.mod schema file.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface BasePitch
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Bass} classifies the MusicXML element, {@code bass}.
         * <p/>
         * This element is declared by the element type {@code bass} for the entity type {@code harmony-chord} in the direction.mod schema file.
         * <p/>
         * Bass is used to indicate a bass note in popular music chord symbols, e.g. G/C.
         * It is generally not used in functional harmony, as inversion is generally not used in pop chord symbols.
         * As with {@link Root root}, it is divided into {@link BassStep step} and {@link BassAlter alter} elements, similar to pitches.
         * The attributes for {@link BassStep step} and {@link BassAlter alter} work the same way as the corresponding attributes for {@link RootStep root-step} and {@link RootAlter root-alter}.
         *
         * @see Harmony
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Bass
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                BassStep.class,
                BassAlter.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code BassAlter} classifies the MusicXML element, {@code bass-alter}.
         * <p/>
         * This element is declared by the element type {@code bass-alter} for the element type {@code bass} in the direction.mod schema file.
         *
         * @see Bass
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface BassAlter
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Location.class,
                Element.Attribute.PrintObject.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code BassStep} classifies the MusicXML element, {@code bass-step}.
         * <p/>
         * This element is declared by the element type {@code bass-step} for the element type {@code bass} in the direction.mod schema file.
         *
         * @see Bass
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface BassStep
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Text.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Beam} classifies the MusicXML element, {@code beam}.
         * <p/>
         * This element is declared by the element type {@code beam} for the element type {@code note} in the note.mod schema file.
         * <p/>
         * Beam types include begin, continue, end, forward hook, and backward hook.
         * Up to eight concurrent beams are available to cover up to 1024th notes, using an enumerated type defined in the common.mod file.
         * Each beam in a note is represented with a separate {@code beam} element, starting with the eighth note beam using a number attribute of 1.
         * <p/>
         * Note that the beam number does not distinguish sets of beams that overlap, as it does for {@link Slur slur} and other elements.
         * Beaming groups are distinguished by being in different voices and/or the presence or absence of {@link Grace grace} and {@link Cue cue} elements.
         * <p/>
         * Beams that have a begin value can also have a {@link Element.Attribute.Fan fan} attribute to indicate accelerandos and ritardandos using fanned beams.
         * The {@link Element.Attribute.Fan fan} attribute may also be used with a continue value if the fanning direction changes on that note.
         * The value is "none" if not specified.
         * <p/>
         * The {@link Element.Attribute.Repeater repeater} attribute has been deprecated in MusicXML 3.0.
         * Formerly used for tremolos, it needs to be specified with a "yes" value for each beam using it.
         *
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Beam
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Number.class,
                Element.Attribute.Repeater.class,
                Element.Attribute.Fan.class,
                Element.Attribute.Color.class,
                // optional-unique-id
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                BACKWARD__HOOK,
                BEGIN,
                CONTINUE,
                END,
                FORWARD__HOOK
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Beater} classifies the MusicXML element, {@code beater}.
         * <p/>
         * This element is declared by the element type {@code beater} for the element type {@code percussion} in the direction.mod schema file.
         * <p/>
         * The {@code beater} element represents pictograms for beaters, mallets, and sticks that do not have different materials represented in the pictogram.
         * Valid values are bow, chime hammer, coin, drum stick, finger, fingernail, fist, guiro scraper, hammer, hand, jazz stick, knitting needle, metal hammer, slide brush on gong, snare stick, spoon mallet, superball, triangle beater, triangle beater plain, and wire brush.
         * The jazz stick value refers to Stone's plastic tip snare stick.
         * The triangle beater plain value refers to the plain line version of the pictogram.
         * The {@link Element.Attribute.Tip tip} attribute represents the direction in which the tip of a beater points.
         *
         * @see Percussion
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Beater
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Tip.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                BOW,
                CHIME__HAMMER,
                COIN,
                DRUM__STICK,
                FINGER,
                FINGERNAIL,
                FIST,
                GUIRO__SCRAPER,
                HAMMER,
                HAND,
                JAZZ__STICK,
                KNITTING__NEEDLE,
                METAL__HAMMER,
                SLIDE__BRUSH__ON__GONG,
                SNARE__STICK,
                SPOON__MALLET,
                SUPERBALL,
                TRIANGLE__BEATER,
                TRIANGLE__BEATER__PLAIN,
                WIRE__BRUSH
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code BeatRepeat} classifies the MusicXML element, {@code beat-repeat}.
         * <p/>
         * This element is declared by the element type {@code beat-repeat} for the element type {@code measure-style} in the attributes.mod schema file.
         * <p/>
         * The {@code beat-repeat} element is used to indicate that a single beat (but possibly many notes) is repeated.
         * Both the start and stop of the beat being repeated should be specified.
         * The {@link Element.Attribute.Slashes slashes} attribute specifies the number of slashes to use in the symbol.
         * The {@link Element.Attribute.UseDots use-dots} attribute indicates whether or not to use dots as well (for instance, with mixed rhythm patterns).
         * By default, the value for {@link Element.Attribute.Slashes slashes} is 1 and the value for {@link Element.Attribute.UseDots use-dots} is no.
         *
         * @see MeasureStyle
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface BeatRepeat
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Slashes.class,
                Element.Attribute.UseDots.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                SlashType.class,
                SlashDot.class,
                ExceptVoice.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Beats} classifies the MusicXML element, {@code beats}.
         * <p/>
         * This element is declared by the element type {@code beats} for the element type {@code interchangeable} and {@code time} in the attributes.mod schema file.
         *
         * @see Interchangeable
         * @see Time
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Beats
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OneOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OneOrMore;
            }
        }

        /**
         * {@code BeatType} classifies the MusicXML element, {@code beat-type}.
         * <p/>
         * This element is declared by the element type {@code beat-type} for the element type {@code interchangeable} and {@code time} in the attributes.mod schema file.
         *
         * @see Interchangeable
         * @see Time
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface BeatType
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OneOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OneOrMore;
            }
        }

        /**
         * {@code BeatUnit} classifies the MusicXML element, {@code beat-unit}.
         * <p/>
         * This element is declared by the element type {@code beat-unit} for the element types {@code beat-unit-tied} and {@code metronome} in the direction.mod schema file.
         *
         * @see Metronome
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface BeatUnit
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code BeatUnitDot} classifies the MusicXML element, {@code beat-unit-dot}.
         * <p/>
         * This element is declared by the element type {@code beat-unit-dot} for the element types {@code beat-unit-tied} and {@code metronome} in the direction.mod schema file.
         *
         * @see Metronome
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface BeatUnitDot
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code BeatUnitTied} classifies the MusicXML element, {@code beat-unit-tied}.
         * <p/>
         * This element is declared by the element type {@code beat-unit-tied} for the element type {@code metronome} in the direction.mod schema file.
         *
         * @see Metronome
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface BeatUnitTied
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                BeatUnit.class,
                BeatUnitDot.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Bend} classifies the MusicXML element, {@code bend}.
         * <p/>
         * This element is declared by the element type {@code bend} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code bend} element is used in guitar and tablature.
         * The {@link BendAlter bend-alter} element indicates the number of steps in the bend, similar to the {@link Alter alter} element.
         * As with the {@link Alter alter} element, numbers like 0.5 can be used to indicate microtones.
         * Negative numbers indicate pre-bends or releases; the {@link PreBend pre-bend} and {@link Release release} elements are used to distinguish what is intended.
         * A {@link WithBar with-bar} element indicates that the bend is to be done at the bridge with a whammy or vibrato bar.
         * The content of the element indicates how this should be notated.
         * Content values of "scoop" and "dip" refer to the SMuFL guitarVibratoBarScoop and guitarVibratoBarDip glyphs.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Bend
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                // print-style
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                // bend-sound
                Element.Attribute.Accelerate.class,
                Element.Attribute.Beats.class,
                Element.Attribute.FirstBeat.class,
                Element.Attribute.LastBeat.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                BendAlter.class,
                PreBend.class,
                Release.class,
                WithBar.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                DIP,
                SCOOP
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code BendAlter} classifies the MusicXML element, {@code bend-alter}.
         * <p/>
         * This element is declared by the element type {@code bend-alter} for the element type {@code bend} in the note.mod schema file.
         *
         * @see Bend
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface BendAlter
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Bookmark} classifies the MusicXML element, {@code bookmark}.
         * <p/>
         * This element is declared by the element type {@code bookmark} in the link.mod schema file for the entity type {@code music-data} in the score.mod schema file.
         * <p/>
         * The {@link Element.Attribute.Element_ element} and {@link Element.Attribute.Position position} attributes are new as of Version 2.0.
         * They allow for bookmarks to be positioned at higher resolution than the level of music-data elements.
         * When no {@link Element.Attribute.Element_ element} and {@link Element.Attribute.Position position} attributes are present, the {@code bookmark} element refers to the next sibling element in the MusicXML file.
         * The {@link Element.Attribute.Element_ element} attribute specifies an element type for a descendant of the next sibling element that is not a {@link Link link} or {@code bookmark}.
         * The {@link Element.Attribute.Position position} attribute specifies the position of this descendant element, where the first position is 1.
         * The {@link Element.Attribute.Position position} attribute is ignored if the {@link Element.Attribute.Element_ element} attribute is not present.
         * For instance, an {@link Element.Attribute.Element_ element} value of "beam" and a {@link Element.Attribute.Position position} value of "2" defines the {@code bookmark} to refer to the second {@link Beam beam} descendant of the next sibling element that is not a {@link Link link} or {@code bookmark}.
         * This is equivalent to an XPath test of [.//beam[2]] done in the context of the sibling element.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Bookmark
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.ID.class,
                Element.Attribute.Name.class,
                Element.Attribute.Element_.class,
                Element.Attribute.Position.class,
            };

            /** The element definition. */
            java.lang.String Definition = null;

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code BottomMargin} classifies the MusicXML element, {@code bottom-margin}.
         * <p/>
         * This element is declared by the element type {@code bottom-margin} in the layout.mod schema file.
         * <p/>
         * Margin elements are included within many of the larger layout elements.
         *
         * @see PageMargins
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface BottomMargin
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.LayoutTenths_Values;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Bracket} classifies the MusicXML element, {@code bracket}.
         * <p/>
         * This element is declared by the element type {@code bracket} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * Brackets are combined with words in a variety of modern directions.
         * The {@link Element.Attribute.LineEnd line-end} attribute specifies if there is a jog up or down (or both), an arrow, or nothing at the start or end of the {@code bracket}.
         * If the {@link Element.Attribute.LineEnd line-end} is up or down, the length of the jog can be specified using the {@link Element.Attribute.EndLength end-length} attribute.
         * The {@link Element.Attribute.LineType line-type} is solid by default.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Bracket
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
                Element.Attribute.LineEnd.class,
                Element.Attribute.EndLength.class,
                Element.Attribute.LineType.class,
                Element.Attribute.DashLength.class,
                Element.Attribute.SpaceLength.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.Color.class,
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code BrassBend} classifies the MusicXML element, {@code brass-bend}.
         * <p/>
         * This element is declared by the element type {@code brass-bend} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code brass-bend} element represents the u-shaped bend symbol used in brass notation, distinct from the {@link Bend bend} element used in guitar music.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface BrassBend
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code BreathMark} classifies the MusicXML element, {@code breath-mark}.
         * <p/>
         * This element is declared by the element type {@code breath-mark} for the element type {@code articulations} in the note.mod schema file.
         * <p/>
         * The {@code breath-mark} element may have a text value to indicate the symbol used for the mark.
         * Valid values are comma, tick, upbow, salzedo, and an empty string.
         *
         * @see Articulations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface BreathMark
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                "",
                COMMA,
                SALZEDO,
                TICK,
                UPBOW
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Caesura} classifies the MusicXML element, {@code caesura}.
         * <p/>
         * This element is declared by the element type {@code caesura} for the element type {@code articulations} in the note.mod schema file.
         * <p/>
         * The caesura element indicates a slight pause.
         * It is notated using a "railroad tracks" symbol or other variations specified in the element content.
         * Valid values are normal, thick, short, curved, single, and an empty string.
         *
         * @see Articulations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Caesura
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                "",
                CURVED,
                NORMAL,
                SHORT,
                SINGLE,
                THICK
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Cancel} classifies the MusicXML element, {@code cancel}.
         * <p/>
         * This element is declared by the element type {@code cancel} in the attributes.mod schema file.
         *
         * @see Key
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Cancel
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Location.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Capo} classifies the MusicXML element, {@code capo}.
         * <p/>
         * This element is declared by the element type {@code capo} for the element type {@code staff-details} in the attributes.mod schema file.
         * <p/>
         * The {@code capo} element indicates at which fret a capo should be placed on a fretted instrument.
         * This changes the open tuning of the java.lang.Strings specified by {@link StaffTuning staff-tuning} by the specified number of half-steps.
         *
         * @see StaffDetails
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Capo
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Chord} classifies the MusicXML element, {@code chord}.
         * <p/>
         * This element is declared by the element type {@code chord} for the entity type {@code full-note} in the note.mod schema file.
         * <p/>
         * The {@code chord} element indicates that this note is an additional chord tone with the preceding note.
         * The duration of this note can be no longer than the preceding note.
         * In MuseData, a missing duration indicates the same length as the previous note, but the MusicXML format requires a duration for chord notes too.
         *
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Chord
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Chromatic} classifies the MusicXML element, {@code chromatic}.
         * <p/>
         * This element is declared by the element type {@code chromatic} for the element type {@code transpose} in the attributes.mod schema file.
         *
         * @see Transpose
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Chromatic
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code CircularArrow} classifies the MusicXML element, {@code circular-arrow}.
         * <p/>
         * This element is declared by the element type {@code circular-arrow} for the entity type {@code arrow} in the note.mod schema file.
         *
         * @see Arrow
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface CircularArrow
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                ANTICLOCKWISE,
                CLOCKWISE
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Clef} classifies the MusicXML element, {@code clef}.
         * <p/>
         * This element is declared by the element type {@code clef} for the element type {@code attributes} in the attributes.mod schema file.
         * <p/>
         * Clefs are represented by the {@link Sign sign}, {@link Line line}, and {@link ClefOctaveChange clef-octave-change} elements.
         * Sign values include G, F, C, percussion, TAB, jianpu, and none.
         * Line numbers are counted from the bottom of the staff.
         * Standard values are 2 for the G sign (treble clef), 4 for the F sign (bass clef), 3 for the C sign (alto clef) and 5 for TAB (on a 6-line staff).
         * The {@link ClefOctaveChange clef-octave-change} element is used for transposing clefs (e.g., a treble clef for tenors would have a {@link ClefOctaveChange clef-octave-change} value of -1).
         * The optional {@link Element.Attribute.Number number} attribute refers to staff numbers within the part, from top to bottom on the system.
         * A value of 1 is assumed if not present.
         * <p/>
         * The jianpu sign indicates that the music that follows should be in jianpu numbered notation, just as the TAB sign indicates that the music that follows should be in tablature notation.
         * Unlike TAB, a jianpu sign does not correspond to a visual clef notation.
         * <p/>
         * Sometimes clefs are added to the staff in non-standard line positions, either to indicate cue passages, or when there are multiple clefs present simultaneously on one staff.
         * In this situation, the {@link Element.Attribute.Additional additional} attribute is set to "yes" and the {@link Line line} value is ignored.
         * The {@link Element.Attribute.Size size} attribute is used for clefs where the additional attribute is "yes".
         * It is typically used to indicate cue clefs.
         * <p/>
         * Sometimes clefs at the start of a measure need to appear after the barline rather than before, as for cues or for use after a repeated section.
         * The {@link Element.Attribute.AfterBarline after-barline} attribute is set to "yes" in this situation.
         * The attribute is ignored for mid-measure clefs.
         * <p/>
         * Clefs appear at the start of each system unless the {@link Element.Attribute.PrintObject print-object} attribute has been set to "no" or the {@link Element.Attribute.Additional additional} attribute has been set to "yes".
         *
         * @see Attributes
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Clef
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Number.class,
                Element.Attribute.Additional.class,
                Element.Attribute.Size.class,
                Element.Attribute.AfterBarline.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeY.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.PrintObject.class,
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Sign.class,
                Line.class,
                ClefOctaveChange.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code ClefOctaveChange} classifies the MusicXML element, {@code clef-octave-change}.
         * <p/>
         * This element is declared by the element type {@code clef-octave-change} for the element type {@code Clef} in the attributes.mod schema file.
         *
         * @see Clef
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface ClefOctaveChange
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Coda} classifies the MusicXML element, {@code coda}.
         * <p/>
         * This element is declared by the element type {@code coda} for the element type {@code direction-type} in the direction.mod schema file; and for the element type {@code barline} in the barline.mod schema file.
         * <p/>
         * Coda sign can be associated with a measure or a general musical direction.
         * These are visual indicators only; a {@link Sound sound} element is also needed to guide playback applications reliably.
         * The exact glyph can be specified with the {@link Element.Attribute.SMuFL smufl} attribute using a SMuFL canonical glyph name that starts with coda.
         *
         * @see Barline
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Coda
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.ID.class,
                Element.Attribute.SMuFL.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OneOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OneOrMore;
            }
        }

        /**
         * {@code Creator} classifies the MusicXML element, {@code creator}.
         * <p/>
         * This element is declared by the element type {@code creator} for the element type {@code identification} in the identify.mod schema file.
         * <p/>
         * The {@code creator} element is borrowed from Dublin Core.
         * It is used for the creators of the score.
         * The {@link Element.Attribute.Type type} attribute is used to distinguish different creative contributions.
         * Thus, there can be multiple creators within an {@link Identification identification}.
         * Standard {@link Element.Attribute.Type type} values are composer, lyricist, and arranger.
         * Other {@link Element.Attribute.Type type} values may be used for different types of creative roles.
         * The {@link Element.Attribute.Type type} attribute should usually be used even if there is just a single {@code creator} element.
         * The MusicXML format does not use the creator / contributor distinction from Dublin Core.
         *
         * @see Identification
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Creator
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Credit} classifies the MusicXML element, {@code credit}.
         * <p/>
         * This element is declared by the element type {@code credit} for the entity type {@code score-header} in the score.mod schema file.
         * <p/>
         * Credit elements refer to the title, composer, arranger, lyricist, copyright, dedication, and other text, symbols, and graphics that commonly appear on the first page of a score.
         * The {@link CreditWords credit-words}, {@link CreditSymbol credit-symbol}, and {@link CreditImage credit-image} elements are similar to the {@link Words words}, {@link Symbol symbol}, and {@link Image image} elements for {@link DirectionType directions}.
         * However, since the credit is not part of a {@link Measure measure}, the {@link Element.Attribute.DefaultX default-x} and {@link Element.Attribute.DefaultY default-y} attributes adjust the origin relative to the bottom left-hand corner of the page.
         * The enclosure for {@link CreditWords credit-words} and {@link CreditSymbol credit-symbol} is none by default.
         * <p/>
         * By default, a series of {@link CreditWords credit-words} and {@link CreditSymbol credit-symbol} elements within a single {@code credit} element follow one another in sequence visually.
         * Non-positional formatting attributes are carried over from the previous element by default.
         * <p/>
         * The {@link Element.Attribute.Page page} attribute for the {@code credit} element specifies the page number where the credit should appear.
         * This is an integer value that starts with 1 for the first page.
         * Its value is 1 by default.
         * Since credits occur before the music, these page numbers do not refer to the page numbering specified by the {@link Print print} element's {@link Element.Attribute.PageNumber page-number} attribute.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Credit
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Page.class,
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                CreditType.class,
                Link.class,
                Bookmark.class,
                CreditImage.class,
                CreditWords.class,
                CreditSymbol.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code CreditImage} classifies the MusicXML element, {@code credit-image}.
         * <p/>
         * This element is declared by the element type {@code credit-image} for the entity type {@code credit} in the score.mod schema file.
         *
         * @see Credit
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface CreditImage
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Source.class,
                Element.Attribute.Type.class,
                Element.Attribute.Height.class,
                Element.Attribute.Width.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class, // valign-image
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code CreditSymbol} classifies the MusicXML element, {@code credit-symbol}.
         * <p/>
         * This element is declared by the element type {@code credit-symbol} for the element type {@code credit} in the score.mod schema file.
         * <p/>
         * The {@code credit-symbol} element specifies a musical symbol using a canonical SMuFL glyph name.
         *
         * @see Credit
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface CreditSymbol
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Justify.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.Underline.class,
                Element.Attribute.Overline.class,
                Element.Attribute.LineThrough.class,
                Element.Attribute.Rotation.class,
                Element.Attribute.LetterSpacing.class,
                Element.Attribute.LineHeight.class,
                Element.Attribute.XmlLang.class,
                Element.Attribute.XmlSpace.class,
                Element.Attribute.Dir.class,
                Element.Attribute.Enclosure.class,
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code CreditType} classifies the MusicXML element, {@code credit-type}.
         * <p/>
         * This element is declared by the element type {@code credit-type} for the element type {@code credit} in the score.mod schema file.
         * <p/>
         * The {@code credit-type} element indicates the purpose behind a {@link Credit credit}.
         * Multiple types of data may be combined in a single {@link Credit credit}, so multiple elements may be used.
         * Standard values include page number, title, subtitle, composer, arranger, lyricist, and rights.
         *
         * @see Credit
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface CreditType
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                ARRANGER,
                COMPOSER,
                LYRICIST,
                PAGE__NUMBER,
                RIGHTS,
                SUBTITLE,
                TITLE
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code CreditWords} classifies the MusicXML element, {@code credit-words}.
         * <p/>
         * This element is declared by the element type {@code credit-words} for the element type {@code credit} in the score.mod schema file.
         *
         * @see Credit
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface CreditWords
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Justify.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.Underline.class,
                Element.Attribute.Overline.class,
                Element.Attribute.LineThrough.class,
                Element.Attribute.Rotation.class,
                Element.Attribute.LetterSpacing.class,
                Element.Attribute.LineHeight.class,
                Element.Attribute.XmlLang.class,
                Element.Attribute.XmlSpace.class,
                Element.Attribute.Dir.class,
                Element.Attribute.Enclosure.class,
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Cue} classifies the MusicXML element, {@code cue}.
         * <p/>
         * This element is declared by the element type {@code cue} for the element type {@code note} in the note.mod schema file.
         * <p/>
         * The {@code cue} element indicates the presence of a cue note.
         * In MusicXML, a cue note is a silent note with no playback.
         * Normal notes that play can be specified as cue size using the {@link Type type} element.
         * A cue note that is specified as full size using the {@link Type type} element will still remain silent.
         *
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Cue
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Damp} classifies the MusicXML element, {@code damp}.
         * <p/>
         * This element is declared by the element type {@code damp} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * Harp damping marks.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Damp
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
             * {@inheritDoc}
             *
             * @return {@link Occurrence#OnlyOne}.
             */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code DampAll} classifies the MusicXML element, {@code damp-all}.
         * <p/>
         * This element is declared by the element type {@code damp-all} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * Harp damping marks.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface DampAll
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
             * {@inheritDoc}
             *
             * @return {@link Occurrence#OnlyOne}.
             */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Dashes} classifies the MusicXML element, {@code dashes}.
         * <p/>
         * This element is declared by the element type {@code dashes} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * Dashes, used for instance with cresc. and dim. marks.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Dashes
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
                Element.Attribute.DashLength.class,
                Element.Attribute.SpaceLength.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.Color.class,
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Defaults} classifies the MusicXML element, {@code defaults}.
         * <p/>
         * This element is declared by the element type {@code defaults} for the entity type {@code score-header} in the note.mod schema file.
         * <p/>
         * Collects score-wide defaults.
         * This includes {@link Scaling scaling} and layout, defined in layout.mod, and default values for the music font, word font, lyric font, and lyric language.
         * The {@link Element.Attribute.Number number} and {@link Element.Attribute.Name name} attributes in {@link LyricFont lyric-font} and {@link LyricLanguage lyric-language} elements are typically used when lyrics are provided in multiple languages.
         * If the {@link Element.Attribute.Number number} and {@link Element.Attribute.Name name} attributes are omitted, the {@link LyricFont lyric-font} and {@link LyricLanguage lyric-language} values apply to all numbers and names.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Defaults
        extends Element
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Scaling.class,
                PageLayout.class,
                SystemLayout.class,
                StaffLayout.class,
                Appearance.class,
                MusicFont.class,
                WordFont.class,
                LyricFont.class,
                LyricLanguage.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Degree} classifies the MusicXML element, {@code degree}.
         * <p/>
         * This element is declared by the element type {@code degree} for the entity type {@code harmony-chord} in the direction.mod schema file.
         * <p/>
         * The {@code degree} element is used to add, alter, or subtract individual notes in the chord.
         * The {@link DegreeValue degree-value} element is a number indicating the degree of the chord (1 for the root, 3 for third, etc).
         * The {@link DegreeAlter degree-alter} element is like the {@link Alter alter} element in notes: 1 for sharp, -1 for flat, etc.
         * The {@link DegreeType degree-type} element can be add, alter, or subtract.
         * If the {@link DegreeType degree-type} is alter or subtract, the {@link DegreeAlter degree-alter} is relative to the {@code degree} already in the chord based on its {@link Kind kind} element.
         * If the {@link DegreeType degree-type} is add, the {@link DegreeAlter degree-alter} is relative to a dominant chord (major and perfect intervals except for a minor seventh).
         * The {@link Element.Attribute.PrintObject print-object} attribute can be used to keep the {@code degree} from printing separately when it has already taken into account in the {@link Element.Attribute.Text text} attribute of the {@link Kind kind} element.
         * The {@link Element.Attribute.PlusMinus plus-minus} attribute is used to indicate if plus and minus symbols should be used instead of sharp and flat symbols to display the degree alteration; it is no by default.
         * <p/>
         * The {@link DegreeValue degree-value} and {@link DegreeType degree-type} {@link Element.Attribute.Text text} attributes specify how the value and type of the {@code degree} should be displayed in a score.
         * The {@link DegreeValue degree-value} {@link Element.Attribute.Symbol symbol} attribute indicates that a symbol should be used in specifying the {@code degree}.
         * If the {@link Element.Attribute.Symbol symbol} attribute is present, the value of the {@link Element.Attribute.Text text} attribute follows the symbol.
         * <p/>
         * A {@link Harmony harmony} of {@link Kind kind} "other" can be spelled explicitly by using a series of {@code degree} elements together with a {@link Root root}.
         *
         * @see Harmony
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Degree
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                DegreeValue.class,
                DegreeAlter.class,
                DegreeType.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code DegreeAlter} classifies the MusicXML element, {@code degree-alter}.
         * <p/>
         * This element is declared by the element type {@code degree-alter} for the element type {@code degree} in the direction.mod schema file.
         *
         * @see Degree
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface DegreeAlter
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.PlusMinus.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code DegreeType} classifies the MusicXML element, {@code degree-type}.
         * <p/>
         * This element is declared by the element type {@code degree-type} for the element type {@code degree} in the direction.mod schema file.
         *
         * @see Degree
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface DegreeType
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Text.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                ADD,
                ALTER,
                SUBTRACT
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code DegreeValue} classifies the MusicXML element, {@code degree-value}.
         * <p/>
         * This element is declared by the element type {@code degree-value} for the element type {@code degree} in the direction.mod schema file.
         *
         * @see Degree
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface DegreeValue
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Symbol.class,
                Element.Attribute.Text.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code DelayedInvertedTurn} classifies the MusicXML element, {@code delayed-inverted-turn}.
         * <p/>
         * This element is declared by the element type {@code delayed-inverted-turn} for the element type {@code ornaments} in the note.mod schema file.
         * <p/>
         * The {@code delayed-inverted-turn} element has the shape which goes down and then up.
         * The {@code delayed-inverted-turn} element indicates turns that are delayed until the end of the current note.
         * If the {@link Element.Attribute.Slash slash} attribute is yes, then a vertical line is used to slash the turn; it is no by default.
         *
         * @see Ornaments
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface DelayedInvertedTurn
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.StartNote.class,
                Element.Attribute.TrillStep.class,
                Element.Attribute.TwoNoteTurn.class,
                Element.Attribute.Accelerate.class,
                Element.Attribute.Beats.class,
                Element.Attribute.SecondBeat.class,
                Element.Attribute.LastBeat.class,
                Element.Attribute.Slash.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code DelayedTurn} classifies the MusicXML element, {@code delayed-turn}.
         * <p/>
         * This element is declared by the element type {@code delayed-turn} for the element type {@code ornaments} in the note.mod schema file.
         * <p/>
         * The {@code delayed-turn} element indicates turns that are delayed until the end of the current note.
         * If the {@link Element.Attribute.Slash slash} attribute is yes, then a vertical line is used to slash the turn; it is no by default.
         *
         * @see Ornaments
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface DelayedTurn
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.StartNote.class,
                Element.Attribute.TrillStep.class,
                Element.Attribute.TwoNoteTurn.class,
                Element.Attribute.Accelerate.class,
                Element.Attribute.Beats.class,
                Element.Attribute.SecondBeat.class,
                Element.Attribute.LastBeat.class,
                Element.Attribute.Slash.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code DetachedLegato} classifies the MusicXML element, {@code detached-legato}.
         * <p/>
         * This element is declared by the element type {@code detached-legato} for the element type {@code articulations} in the note.mod schema file.
         *
         * @see Articulations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface DetachedLegato
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Diatonic} classifies the MusicXML element, {@code diatonic}.
         * <p/>
         * This element is declared by the element type {@code diatonic} for the element type {@code transpose} in the attributes.mod schema file.
         *
         * @see Transpose
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Diatonic
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Direction} classifies the MusicXML element, {@code direction}.
         * <p/>
         * This element is declared by the element type {@code direction} in the direction.mod schema file for the entity type {@code music-data} in the score.mod schema file.
         * <p/>
         * A direction is a musical indication that is not necessarily attached to a specific note.
         * Two or more may be combined to indicate starts and stops of wedges, dashes, etc.
         * For applications where a specific direction is indeed attached to a specific note, the {@code direction} element can be associated with the {@link Note note} element that follows it in score order that is not in a different {@link Voice voice}.
         * <p/>
         * By default, a series of {@link DirectionType direction-type} elements and a series of child elements of a {@link DirectionType direction-type} within a single {@code direction} element follow one another in sequence visually.
         * For a series of {@link DirectionType direction-type} children, non-positional formatting attributes are carried over from the previous element by default.
         * <p/>
         * Directions are generally not note-specific, but instead are associated with a {@link Part part} or the overall score.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Direction
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Placement.class,
                Element.Attribute.Directive.class,
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                DirectionType.class,
                Offset.class,
                Staff.class,
                Sound.class,
                // editorial
                Element.Footnote.class,
                Element.Level.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code DirectionType} classifies the MusicXML element, {@code direction-type}.
         * <p/>
         * This element is declared by the element type {@code direction-type} for the element type {@code direction} in the direction.mod schema file.
         * <p/>
         * Textual direction types may have more than 1 component due to multiple fonts.
         * The {@link Dynamics dynamics} element may also be used in the {@link Notations notations} element, and is defined in the common.mod file.
         * <p/>
         * Entities related to print suggestions apply to the individual {@code direction-type}, not to the overall {@link Direction direction}.
         *
         * @see Direction
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface DirectionType
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Rehearsal.class,
                Segno.class,
                Coda.class,
                Words.class,
                Symbol.class,
                Wedge.class,
                Dynamics.class,
                Dashes.class,
                Bracket.class,
                Pedal.class,
                Metronome.class,
                OctaveShift.class,
                HarpPedals.class,
                Damp.class,
                DampAll.class,
                Eyeglasses.class,
                StringMute.class,
                Scordatura.class,
                Image.class,
                PrincipalVoice.class,
                Percussion.class,
                AccordionRegistration.class,
                StaffDivide.class,
                OtherDirection.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OneOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OneOrMore;
            }
        }

        /**
         * {@code Directive} classifies the MusicXML element, {@code directive}.
         * <p/>
         * This element is declared by the element type {@code directive} for the element type {@code attributes} in the attributes.mod schema file.
         * <p/>
         * Directives are like directions, but can be grouped together with {@link Attributes attributes} for convenience.
         * This is typically used for tempo markings at the beginning of a piece of music.
         * This element has been deprecated in Version 2.0 in favor of the {@link Element.Attribute.Directive directive} attribute for {@link Direction direction} elements.
         * Language names come from ISO 639, with optional country subcodes from ISO 3166.
         *
         * @see Attributes
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Directive
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeY.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.XmlLang.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code DisplayOctave} classifies the MusicXML element, {@code display-octave}.
         * <p/>
         * This element is declared by the element type {@code display-octave} for the element types {@code rest} and {@code unpitched} in the note.mod schema file.
         *
         * @see Rest
         * @see Unpitched
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface DisplayOctave
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code DisplayStep} classifies the MusicXML element, {@code display-step}.
         * <p/>
         * This element is declared by the element type {@code display-step} for the element types {@code rest} and {@code unpitched} in the note.mod schema file.
         *
         * @see Rest
         * @see Unpitched
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface DisplayStep
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code DisplayText} classifies the MusicXML element, {@code display-text}.
         * <p/>
         * This element is declared by the element type {@code display-text} for the element types {@code part-name-display} and {@code part-abbreviation-display} in the common.mod schema file; and for the element type {@code notehead-text} in the note.mod schema file.
         *
         * @see NoteheadText
         * @see PartNameDisplay
         * @see PartAbbreviationDisplay
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface DisplayText
        extends
            Element,
            XML.Schematic.PerType
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Justify.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.Underline.class,
                Element.Attribute.Overline.class,
                Element.Attribute.LineThrough.class,
                Element.Attribute.Rotation.class,
                Element.Attribute.LetterSpacing.class,
                Element.Attribute.LineHeight.class,
                Element.Attribute.XmlLang.class,
                Element.Attribute.XmlSpace.class,
                Element.Attribute.Dir.class,
                Element.Attribute.Enclosure.class,
            };

            /** The array of accepted parent element types. */
            Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                NoteheadText.class,
                PartNameDisplay.class,
                PartAbbreviationDisplay.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }

            @Override
            default Class<? extends AccidentalText> per(final Class<?> type) {
                if (type == PartNameDisplay.class
                    || type == PartAbbreviationDisplay.class)
                    return AccidentalText_PerMany1.class;
                else
                if (type == NoteheadText.class)
                    return AccidentalText_PerNoteheadText.class;

                return null;
            }

            interface AccidentalText_PerMany1
            extends AccidentalText
            {
                @Override
                default java.lang.String occurrence() {
                    return Occurrence.ZeroOrMore;
                }
            }

            interface AccidentalText_PerNoteheadText
            extends AccidentalText
            {
                @Override
                default java.lang.String occurrence() {
                    return Occurrence.OneOrMore;
                }
            }
        }

        /**
         * {@code Distance} classifies the MusicXML element, {@code distance}.
         * <p/>
         * This element is declared by the element type {@code distance} for the element type {@code appearance} in the layout.mod schema file.
         *
         * @see Appearance
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Distance
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
            };

            /** The element definition. */
            java.lang.String Definition = null;

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.LayoutTenths_Values;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Divisions} classifies the MusicXML element, {@code divisions}.
         * <p/>
         * This element is declared by the element type {@code divisions} for the element type {@code attributes} in the attributes.mod schema file.
         * <p/>
         * Musical notation duration is commonly represented as fractions.
         * The {@code divisions} element indicates how many divisions per quarter note are used to indicate a note's duration.
         * For example, if duration = 1 and divisions = 2, this is an eighth note duration.
         * Duration and divisions are used directly for generating sound output, so they must be chosen to take tuplets into account.
         * Using a {@code divisions} element lets us use just one number to represent a duration for each note in the score, while retaining the full power of a fractional representation.
         * For maximum compatibility with Standard MIDI Files, the {@code divisions} value should not exceed 16383.
         *
         * @see Attributes
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Divisions
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Doit} classifies the MusicXML element, {@code doit}.
         * <p/>
         * This element is declared by the element type {@code doit} for the element type {@code articulations} in the note.mod schema file.
         * <p/>
         * The {@code doit} element is an indeterminate slide attached to a single note.
         * Doits come after the main note, going above the pitch.
         *
         * @see Articulations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Doit
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.LineShape.class,
                Element.Attribute.LineType.class,
                Element.Attribute.LineLength.class,
                Element.Attribute.DashLength.class,
                Element.Attribute.SpaceLength.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Dot} classifies the MusicXML element, {@code dot}.
         * <p/>
         * This element is declared by the element type {@code dot} for the element type {@code note} in the note.mod schema file.
         *
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Dot
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Double} classifies the MusicXML element, {@code double}.
         * <p/>
         * This element is declared by the element type {@code double} for the element type {@code transpose} in the attributes.mod schema file.
         *
         * @see Transpose
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Double
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code DoubleTongue} classifies the MusicXML element, {@code double-tongue}.
         * <p/>
         * This element is declared by the element type {@code double-tongue} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code double-tongue} element represents the double tongue symbol (two dots arranged horizontally).
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface DoubleTongue
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code DownBow} classifies the MusicXML element, {@code down-bow}.
         * <p/>
         * This element is declared by the element type {@code down-bow} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code down-bow} element represents the symbol that is used both for down-bowing on bowed instruments, and down-stroke on plucked instruments.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface DownBow
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Duration} classifies the MusicXML element, {@code duration}.
         * <p/>
         * This element is declared by the element type {@code duration} for the element types {@code backup}, {@code figured-bass}, {@code forward}, and {@code note} in the note.mod schema file.
         * <p/>
         * Duration is a positive number specified in division units.
         * This is the intended duration vs. notated duration (for instance, swing eighths vs. even eighths, or differences in dotted notes in Baroque-era music).
         * Differences in duration specific to an interpretation or performance should use the {@link Note note} element's {@link Element.Attribute.Attack attack} and {@link Element.Attribute.Release release} attributes.
         *
         * @see Backup
         * @see FiguredBass
         * @see Forward
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Duration
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Dynamics} classifies the MusicXML element, {@code dynamics}.
         * <p/>
         * This element is declared by the element type {@code dynamics} in the common.mod schema file, for the element type {@code notations} in the note.mod schema file, and {@code direction-type} in the direction.mod schema file.
         * <p/>
         * Dynamics can be associated either with a note or a general musical direction.
         * To avoid inconsistencies between and amongst the letter abbreviations for dynamics (what is sf vs. sfz, standing alone or with a trailing dynamic that is not always piano), we use the actual letters as the names of these dynamic elements.
         * The {@link OtherDynamics other-dynamics} element allows other dynamic marks that are not covered here, but many of those should perhaps be included in a more general musical direction element.
         * Dynamics may also be combined as in &lt;sf/&gt;&lt;mp/&gt;.
         * <p/>
         * These letter dynamic symbols are separated from crescendo, decrescendo, and wedge indications.
         * Dynamic representation is inconsistent in scores.
         * Many things are assumed by the composer and left out, such as returns to original dynamics.
         * Systematic representations are quite complex: for example, Humdrum has at least 3 representation formats related to dynamics.
         * The MusicXML format captures what is in the score, but does not try to be optimal for analysis or synthesis of dynamics.
         *
         * @see DirectionType
         * @see Notations
         * @see OtherDynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Dynamics
        extends
            Analytic,
            XML.Schematic.PerType
        {
            /** The array of accepted parent element types. */
            Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                Notations.class,
                DirectionType.class,
            };

            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.Placement.class,
                Element.Attribute.Underline.class,
                Element.Attribute.Overline.class,
                Element.Attribute.LineThrough.class,
                Element.Attribute.Enclosure.class,
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                P.class,
                PP.class,
                PPP.class,
                PPPP.class,
                PPPPP.class,
                PPPPPP.class,
                F.class,
                FF.class,
                FFF.class,
                FFFF.class,
                FFFFF.class,
                FFFFFF.class,
                MP.class,
                MF.class,
                SF.class,
                SFP.class,
                SFPP.class,
                FP.class,
                RF.class,
                RFZ.class,
                SFZ.class,
                SFFZ.class,
                FZ.class,
                N.class,
                PF.class,
                SFZP.class,
                OtherDynamics.class,
            };

            @Override
            default Class<? extends Dynamics> per(final Class<?> type) {
                if (type == Notations.class)
                    return Dynamics_PerNotations.class;
                else
                if (type == DirectionType.class)
                    return Dynamics_PerDirectionType.class;

                return null;
            }

            interface Dynamics_PerNotations
            extends Dynamics
            {
                @Override
                default java.lang.String occurrence() {
                    return Occurrence.ZeroOrMore;
                }
            }

            interface Dynamics_PerDirectionType
            extends Dynamics
            {
                @Override
                default java.lang.String occurrence() {
                    return Occurrence.OneOrMore;
                }
            }
        }

        /**
         * {@code Effect} classifies the MusicXML element, {@code effect}.
         * <p/>
         * This element is declared by the element type {@code effect} for the element type {@code percussion} in the direction.mod schema file.
         * <p/>
         * The effect element represents pictograms for sound effect percussion instruments.
         * Valid values are anvil, auto horn, bird whistle, cannon, duck call, gun shot, klaxon horn, lions roar, lotus flute, megaphone, police whistle, siren, slide whistle, thunder sheet, wind machine, and wind whistle.
         * The cannon, lotus flute, and megaphone values are in addition to Stone's list.
         *
         * @see Percussion
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Effect
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                ANVIL,
                AUTO__HORN,
                BIRD__WHISTLE,
                CANNON,
                DUCK__CALL,
                GUN__SHOT,
                KLAXON__HORN,
                LIONS__ROAR,
                LOTUS__FLUTE,
                MEGAPHONE,
                POLICE__WHISTLE,
                SIREN,
                SLIDE__WHISTLE,
                THUNDER__SHEET,
                WIND__MACHINE,
                WIND__WHISTLE
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Elevation} classifies the MusicXML element, {@code elevation}.
         * <p/>
         * This element is declared by the element type {@code elevation} for the element type {@code midi-instrument} in the common.mod schema file.
         * <p/>
         * Elevation allows placing of sound in a 3-D space relative to the listener.
         * It is expressed in degrees ranging from -180 to 180.
         * For {@code elevation}, 0 is level with the listener, 90 is directly above, and -90 is directly below.
         *
         * @see MidiInstrument
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Elevation
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Elision} classifies the MusicXML element, {@code elision}.
         * <p/>
         * This element is declared by the element type {@code elision} for the element type {@code lyric} in the note.mod schema file.
         * <p/>
         * The {@code elision} element represents an elision between lyric syllables.
         * The text content specifies the symbol used to display the elision.
         * Common values are a no-break space (Unicode 00A0), an underscore (Unicode 005F), or an undertie (Unicode 203F).
         * If the text content is empty, the {@link Element.Attribute.SMuFL smufl} attribute is used to specify the symbol to use.
         * Its value is a SMuFL canonical glyph name that starts with lyrics.
         * The {@link Element.Attribute.SMuFL smufl} attribute is ignored if the elision glyph is already specified by the text content.
         * If neither text content nor a {@link Element.Attribute.SMuFL smufl} attribute are present, the elision glyph is application-specific.
         *
         * @see Lyric
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Elision
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.SMuFL.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                " ",
                "_",
                "‿"
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Encoder} classifies the MusicXML element, {@code encoder}.
         * <p/>
         * This element is declared by the element type {@code encoder} for the element type {@code encoding} in the identify.mod schema file.
         *
         * @see Encoding
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Encoder
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Encoding} classifies the MusicXML element, {@code encoding}.
         * <p/>
         * This element is declared by the element type {@code encoding} for the element type {@code identification} in the identify.mod schema file.
         * </p>
         * Encoding contains information about who did the digital encoding, when, with what software, and in what aspects.
         * Standard type values for the encoder element are music, words, and arrangement, but other types may be used.
         * The {@link Element.Attribute.Type type} attribute is only needed when there are multiple {@link Encoder encoder} elements.
         * <p/>
         * The {@link Supports supports} element indicates if the {@code encoding} supports a particular MusicXML element.
         * This is recommended for elements like {@link Beam beam}, {@link Stem stem}, and {@link Accidental accidental}, where the absence of an element is ambiguous if you do not know if the encoding supports that element.
         * For Version 2.0, the {@link Supports supports} element is expanded to allow programs to indicate support for particular attributes or particular values.
         * This lets applications communicate, for example, that all system and/or page breaks are contained in the MusicXML file.
         *
         * @see Identification
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Encoding
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                EncodingDate.class,
                Encoder.class,
                Software.class,
                EncodingDescription.class,
                Supports.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code EncodingDate} classifies the MusicXML element, {@code encoding-date}.
         * <p/>
         * This element is declared by the element type {@code encoding-date} for the element type {@code encoding} in the identify.mod schema file.
         *
         * @see Encoding
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface EncodingDate
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.YYYY_MM_DD_Values;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code EncodingDescription} classifies the MusicXML element, {@code encoding-description}.
         * <p/>
         * This element is declared by the element type {@code encoding-description} for the element type {@code encoding} in the identify.mod schema file.
         *
         * @see Encoding
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface EncodingDescription
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Ending} classifies the MusicXML element, {@code ending}.
         * <p/>
         * This element is declared by the element type {@code ending} for the element type {@code barline} in the barline.mod schema file.
         * <p/>
         * Endings refers to multiple (e.g. first and second) endings.
         * Typically, the start {@link Element.Attribute.Type type} is associated with the left barline of the first measure in an ending.
         * The stop and discontinue {@link Element.Attribute.Type types} are associated with the right barline of the last measure in an ending.
         * Stop is used when the ending mark concludes with a downward jog, as is typical for first endings.
         * Discontinue is used when there is no downward jog, as is typical for second endings that do not conclude a piece.
         * The length of the jog can be specified using the {@link Element.Attribute.EndLength end-length} attribute.
         * The {@link Element.Attribute.TextX text-x} and {@link Element.Attribute.TextY text-y} attributes are offsets that specify where the baseline of the start of the ending text appears, relative to the start of the ending line.
         * <p/>
         * The {@link Element.Attribute.Number number} attribute reflects the numeric values of what is under the ending line.
         * Single endings such as "1" or comma-separated multiple endings such as "1, 2" may be used.
         * The {@code ending} element text is used when the text displayed in the ending is different than what appears in the {@link Element.Attribute.Number number} attribute.
         * The {@code print-object} element is used to indicate when an ending is present but not printed, as is often the case for many parts in a full score.
         *
         * @see Barline
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Ending
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Number.class,
                Element.Attribute.Type.class,
                Element.Attribute.EndLength.class,
                Element.Attribute.TextX.class,
                Element.Attribute.TextY.class,
                Element.Attribute.PrintObject.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class
            };

            /** The element definition. */
            java.lang.String Definition = null;

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code EndLine} classifies the MusicXML element, {@code end-line}.
         * <p/>
         * This element is declared by the element type {@code end-line} for the element type {@code lyric} in the common.mod schema file.
         *
         * @see Lyric
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface EndLine
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code EndParagraph} classifies the MusicXML element, {@code end-paragraph}.
         * <p/>
         * This element is declared by the element type {@code end-paragraph} for the element type {@code lyric} in the common.mod schema file.
         *
         * @see Lyric
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface EndParagraph
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Ensemble} classifies the MusicXML element, {@code ensemble}.
         * <p/>
         * This element is declared by the element type {@code ensemble} for the element type {@code score-instrument} in the score.mod schema file.
         *
         * @see ScoreInstrument
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Ensemble
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code ExceptVoice} classifies the MusicXML element, {@code except-voice}.
         * <p/>
         * This element is declared by the element type {@code except-voice} for the element types {@code beat-repeat} and {@code slash} in the attributes.mod schema file.
         *
         * @see MeasureStyle
         * @see BeatRepeat
         * @see Slash
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface ExceptVoice
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Extend} classifies the MusicXML element, {@code extend}.
         * <p/>
         * This element is declared by the element type {@code extend} for the element types {@code figure} and {@code lyric} in the note.mod schema file.
         * <p/>
         * The {@code extend} element represents lyric word extension / melisma lines as well as figured bass extensions.
         * The optional {@link Element.Attribute.Type type} and {@code position} attributes are added in Version 3.0 to provide better formatting control.
         *
         * @see Figure
         * @see Lyric
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Extend
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.Color.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                " ",
                "_",
                "‿"
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Eyeglasses} classifies the MusicXML element, {@code eyeglasses}.
         * <p/>
         * This element is declared by the element type {@code eyeglasses} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * Eyeglasses, common in commercial music.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Eyeglasses
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
             * {@inheritDoc}
             *
             * @return {@link Occurrence#OnlyOne}.
             */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code F} classifies the MusicXML element, {@code f}.
         * <p/>
         * This element is declared by the element type {@code f} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface F
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Falloff} classifies the MusicXML element, {@code falloff}.
         * <p/>
         * This element is declared by the element type {@code falloff} for the element type {@code articulations} in the note.mod schema file.
         * <p/>
         * The {@code falloff} element is an indeterminate slide attached to a single note.
         * Falloffs come after the main note, going below the pitch.
         *
         * @see Articulations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Falloff
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.LineShape.class,
                Element.Attribute.LineType.class,
                Element.Attribute.LineLength.class,
                Element.Attribute.DashLength.class,
                Element.Attribute.SpaceLength.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Feature} classifies the MusicXML element, {@code feature}.
         * <p/>
         * This element is declared by the element type {@code feature} for the element type {@code grouping} in the direction.mod schema file.
         *
         * @see Grouping
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Feature
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Fermata} classifies the MusicXML element, {@code fermata}.
         * <p/>
         * This element is declared by the element type {@code fermata} in the common.mod schema file, for the element types {@code barline} in the barline.mod schema file; and for the element type {@code notations} in the note.mod schema file.
         * <p/>
         * Fermata element can be applied both to notes and to barlines.
         * The {@code fermata} text content represents the shape of the fermata sign and may be normal, angled, square, double-angled, double-square, double-dot, half-curve, curlew, or an empty java.lang.String.
         * An empty {@code fermata} element represents a normal fermata.
         * The {@code fermata} {@link Element.Attribute.Type type} is upright if not specified.
         *
         * @see Barline
         * @see Notations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Fermata
        extends
            Analytic,
            XML.Schematic.PerType
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeY.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.ID.class,
            };

            /** The array of accepted parent element types. */
            Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                Barline.class,
                Notations.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                "",
                ANGLED,
                CURLEW,
                DOUBLE_ANGLED,
                DOUBLE_DOT,
                DOUBLE_SQUARE,
                HALF_CURVE,
                NORMAL,
                SQUARE
            };

            @Override
            default Class<?extends Fermata> per(final Class<?> type) {
                if (type == Barline.class)
                    return Fermata_PerBarline.class;
                else
                if (type == Notations.class)
                    return Fermata_PerNotations.class;

                return null;
            }

            interface Fermata_PerBarline
            extends Fermata
            {
                @Override
                default java.lang.String occurrence() {
                    return Occurrence.ZeroOrOneOrTwo;
                }
            }

            interface Fermata_PerNotations
            extends Fermata
            {
                @Override
                default java.lang.String occurrence() {
                    return Occurrence.ZeroOrMore;
                }
            }
        }

        /**
         * {@code FF} classifies the MusicXML element, {@code ff}.
         * <p/>
         * This element is declared by the element type {@code ff} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface FF
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code FFF} classifies the MusicXML element, {@code fff}.
         * <p/>
         * This element is declared by the element type {@code fff} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface FFF
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code FFFF} classifies the MusicXML element, {@code ffff}.
         * <p/>
         * This element is declared by the element type {@code ffff} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface FFFF
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code FFFFF} classifies the MusicXML element, {@code fffff}.
         * <p/>
         * This element is declared by the element type {@code fffff} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface FFFFF
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code FFFFFF} classifies the MusicXML element, {@code ffffff}.
         * <p/>
         * This element is declared by the element type {@code ffffff} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface FFFFFF
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Fifths} classifies the MusicXML element, {@code fifths}.
         * <p/>
         * This element is declared by the element type {@code fifths} for the element type {@code key} in the attributes.mod schema file.
         *
         * @see Key
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Fifths
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Figure} classifies the MusicXML element, {@code figure}.
         * <p/>
         * This element is declared by the element type {@code figure} for the element type {@code figured-bass} in the note.mod schema file.
         *
         * @see FiguredBass
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Figure
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Prefix.class,
                FigureNumber.class,
                Suffix.class,
                Extend.class,
                // editorial
                Element.Footnote.class,
                Element.Level.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code FiguredBass} classifies the MusicXML element, {@code figured-bass}.
         * <p/>
         * This element is declared by the element type {@code figured-bass} in the note.mod schema file for the entity type {@code music-data} in the score.mod schema file.
         * <p/>
         * Figured bass elements take their position from the first regular note (not a grace note or chord note) that follows in score order.
         * The optional {@link Duration duration} element is used to indicate changes of figures under a note.
         * <p/>
         * {@link Figure Figures} are ordered from top to bottom.
         * A {@link FigureNumber figure-number} is a number.
         * Values for {@link Prefix prefix} and {@link Suffix suffix} include plus and the accidental values sharp, flat, natural, double-sharp, flat-flat, and sharp-sharp.
         * {@link Suffix Suffixes} include both symbols that come after the figure number and those that overstrike the figure number.
         * The {@link Suffix suffix} values slash, back-slash, and vertical are used for slashed numbers indicating chromatic alteration.
         * The orientation and display of the slash usually depends on the figure number.
         * The {@link Prefix prefix} and {@link Suffix suffix} elements may contain additional values for symbols specific to particular figured bass styles.
         * The value of {@link Element.Attribute.Parentheses parentheses} is "no" if not present.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface FiguredBass
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Parentheses.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.PrintObject.class,
                Element.Attribute.PrintDot.class,
                Element.Attribute.PrintSpacing.class,
                Element.Attribute.PrintLyric.class,
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Figure.class,
                Duration.class,
                // editorial
                Element.Footnote.class,
                Element.Level.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code FigureNumber} classifies the MusicXML element, {@code figure-number}.
         * <p/>
         * This element is declared by the element type {@code figure-number} for the element types {@code figure} in the note.mod schema file.
         *
         * @see Figure
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface FigureNumber
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Fingering} classifies the MusicXML element, {@code fingering}.
         * <p/>
         * This element is declared by the element type {@code fingering} in the common.mod schema file, for the element type {@code key} in the attributes.mod schema file; and for the element type {@code frame-note} in the direction.mod schema file.
         * <p/>
         * The {@code fingering} element can be used either in a {@link Technical technical} element for a {@link Note note} or in a {@link Frame frame} element as part of a chord symbol.
         * <p/>
         * Fingering is typically indicated 1,2,3,4,5.
         * Multiple fingerings may be given, typically to substitute fingerings in the middle of a note.
         * The substitution and alternate values are "no" if the attribute is not present.
         * For guitar and other fretted instruments, the {@code fingering} element represents the fretting finger; the {@link Pluck pluck} element represents the plucking finger.
         *
         * @see FrameNote
         * @see Key
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Fingering
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Substitution.class,
                Element.Attribute.Alternate.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;
        }

        /**
         * {@code Fingernails} classifies the MusicXML element, {@code fingernails}.
         * <p/>
         * This element is declared by the element type {@code fingernails} for the element type {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code fingernails} element is used in notation for harp and other plucked string instruments.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Fingernails
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code FirstFret} classifies the MusicXML element, {@code first-fret}.
         * <p/>
         * This element is declared by the element type {@code first-fret} for the element type {@code frame} in the direction.mod schema file.
         * <p/>
         * The {@code first-fret} indicates which fret is shown in the top space of the {@link Frame frame}; it is fret 1 if the element is not present.
         * The optional {@link Element.Attribute.Text text} attribute indicates how this is represented in the fret diagram, while the {@link Element.Attribute.Location location} attribute indicates whether the text appears to the left or right of the frame.
         *
         * @see Frame
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface FirstFret
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Text.class,
                Element.Attribute.Location.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Flip} classifies the MusicXML element, {@code flip}.
         * <p/>
         * This element is declared by the element type {@code flip} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code flip} element represents the flip symbol used in brass notation.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Flip
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Footnote} classifies the MusicXML element, {@code footnote}.
         * <p/>
         * This element is declared by the element type {@code footnote} in the common.mod schema file.
         * <p/>
         * Footnote is used to specify editorial information, while {@link Voice voice} is used to distinguish between multiple voices (what MuseData calls tracks) in individual {@link Part parts}.
         * This element is used throughout the different MusicXML DTD modules.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Footnote
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Justify.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.Underline.class,
                Element.Attribute.Overline.class,
                Element.Attribute.LineThrough.class,
                Element.Attribute.Rotation.class,
                Element.Attribute.LetterSpacing.class,
                Element.Attribute.LineHeight.class,
                Element.Attribute.XmlLang.class,
                Element.Attribute.XmlSpace.class,
                Element.Attribute.Dir.class,
                Element.Attribute.Enclosure.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Forward} classifies the MusicXML element, {@code forward}.
         * <p/>
         * This element is declared by the element type {@code forward} in the note.mod schema file, for the entity type {@code music-data} in the score.mod schema file.
         * <p/>
         * The {@code forward} element is required to coordinate multiple voices in one {@link Part part}, including music on multiple staves.
         * The {@code forward} element is generally used within voices and staves.
         * {@link Duration} values should always be positive, and should not cross measure boundaries or mid-measure changes in the {@link Divisions divisions} value.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Forward
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Duration.class,
                Staff.class,
                // editorial
                Element.Footnote.class,
                Element.Level.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code FP} classifies the MusicXML element, {@code fp}.
         * <p/>
         * This element is declared by the element type {@code fp} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface FP
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Frame} classifies the MusicXML element, {@code frame}.
         * <p/>
         * This element is declared by the element type {@code frame} for the element type {@code harmony} in the direction.mod schema file.
         * <p/>
         * The {@code frame} element represents a frame or fretboard diagram used together with a chord symbol.
         * The representation is based on the NIFF guitar grid with additional information.
         * The {@link FrameStrings frame-strings} and {@link FrameFrets frame-frets} elements give the overall size of the {@code frame} in vertical lines (strings) and horizontal spaces (frets).
         * <p/>
         * The {@code frame} element's {@link Element.Attribute.Unplayed unplayed} attribute indicates what to display above a string that has no associated {@link FrameNote frame-note} element.
         * Typical values are x and the empty string.
         * If the attribute is not present, the display of the {@link Element.Attribute.Unplayed unplayed} string is application-defined.
         *
         * @see Harmony
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Frame
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Height.class,
                Element.Attribute.Width.class,
                Element.Attribute.Unplayed.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class, // valign-image
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                FrameStrings.class,
                FrameFrets.class,
                FirstFret.class,
                FrameNote.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code FrameFrets} classifies the MusicXML element, {@code frame-frets}.
         * <p/>
         * This element is declared by the element type {@code frame-frets} for the element type {@code frame} in the direction.mod schema file.
         *
         * @see Frame
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface FrameFrets
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code FrameNote} classifies the MusicXML element, {@code frame-note}.
         * <p/>
         * This element is declared by the element type {@code frame-note} for the element type {@code frame} in the direction.mod schema file.
         * <p/>
         * The {@code frame-note} element represents each note included in the {@link Frame frame}.
         * The definitions for {@link String string}, {@link Fret fret}, and {@link Fingering fingering} are found in the common.mod file.
         * An open string will have a {@link Fret fret} value of 0, while a muted string will not be associated with a {@code frame-note} element.
         *
         * @see Frame
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface FrameNote
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                String.class,
                Fret.class,
                Fingering.class,
                Barre.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OneOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OneOrMore;
            }
        }

        /**
         * {@code FrameStrings} classifies the MusicXML element, {@code frame-strings}.
         * <p/>
         * This element is declared by the element type {@code frame-strings} for the element type {@code frame} in the direction.mod schema file.
         *
         * @see Frame
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface FrameStrings
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Fret} classifies the MusicXML element, {@code fret}.
         * <p/>
         * This element is declared by the element type {@code fret} in the common.mod schema file, and for the element type {@code frame-note} in the direction.mod schema file.
         * <p/>
         * Fret is used with tablature notation and chord symbols.
         * Fret numbers start with 0 for an open string and 1 for the first fret.
         *
         * @see FrameNote
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Fret
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                // font
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                // color
                Element.Attribute.Color.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;
        }

        /**
         * {@code Function} classifies the MusicXML element, {@code function}.
         * <p/>
         * This element is declared by the element type {@code function} for the entity type {@code harmony-chord} in the direction.mod schema file.
         *
         * @see Harmony
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Function
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code FZ} classifies the MusicXML element, {@code fz}.
         * <p/>
         * This element is declared by the element type {@code fz} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface FZ
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Glass} classifies the MusicXML element, {@code glass}.
         * <p/>
         * This element is declared by the element type {@code glass} for the element type {@code percussion} in the direction.mod schema file.
         * <p/>
         * The glass element represents pictograms for glass percussion instruments.
         * Valid values are glass harmonica, glass harp, and wind chimes.
         * The {@link Element.Attribute.SMuFL smufl} attribute is used to distinguish different SMuFL glyphs for wind chimes in the chimes pictograms range, including those made of materials other than glass.
         *
         * @see Percussion
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Glass
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.SMuFL.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                GLASS__HARMONICA,
                GLASS__HARP,
                WIND__CHIMES
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Glissando} classifies the MusicXML element, {@code glissando}.
         * <p/>
         * This element is declared by the element type {@code glissando} for the element type {@code notations} in the note.mod schema file.
         * <p/>
         * Glissando and {@link Slide slide} elements both indicate rapidly moving from one pitch to the other so that individual notes are not discerned.
         * The distinction is similar to that between NIFF's glissando and portamento elements.
         * A glissando sounds the half notes in between the slide and defaults to a wavy line.
         * The optional text for a {@code glissando} is printed alongside the line.
         *
         * @see Notations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Glissando
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
                Element.Attribute.LineType.class,
                Element.Attribute.DashLength.class,
                Element.Attribute.SpaceLength.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Glyph} classifies the MusicXML element, {@code glyph}.
         * <p/>
         * This element is declared by the element type {@code glyph} for the element type {@code appearance} in the layout.mod schema file.
         *
         * @see Appearance
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Glyph
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
            };

            /** The element definition. */
            java.lang.String Definition = null;

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Golpe} classifies the MusicXML element, {@code golpe}.
         * <p/>
         * This element is declared by the element type {@code golpe} for the element type {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code golpe} element represents the golpe symbol that is used for tapping the pick guard in guitar music.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Golpe
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Grace} classifies the MusicXML element, {@code grace}.
         * <p/>
         * This element is declared by the element type {@code grace} for the element type {@code note} in the note.mod schema file.
         * <p/>
         * The {@code grace} element indicates the presence of a grace note.
         * The {@link Element.Attribute.Slash slash} attribute for a grace note is yes for slashed eighth notes.
         * The other grace note attributes come from MuseData sound suggestions.
         * The {@link Element.Attribute.StealTimePrevious steal-time-previous} attribute indicates the percentage of time to steal from the previous note for the grace note.
         * The {@link Element.Attribute.StealTimeFollowing steal-time-following} attribute indicates the percentage of time to steal from the following note for the grace note, as for appoggiaturas.
         * The {@link Element.Attribute.MakeTime make-time} attribute indicates to make time, not steal time; the units are in real-time divisions for the grace note.
         *
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Grace
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.StealTimePrevious.class,
                Element.Attribute.StealTimeFollowing.class,
                Element.Attribute.MakeTime.class,
                Element.Attribute.Slash.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Group} classifies the MusicXML element, {@code group}.
         * <p/>
         * This element is declared by the element type {@code group} for the element type {@code score-part} in the score.mod schema file.
         * <p/>
         * The {@code group} element allows the use of different versions of the part for different purposes.
         * Typical values include score, parts, sound, and data.
         * Ordering information that is directly encoded in MuseData can be derived from the ordering within a MusicXML score or opus.
         *
         * @see ScorePart
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Group
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                DATA,
                PARTS,
                SCORE,
                SOUND
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code GroupAbbreviation} classifies the MusicXML element, {@code group-abbreviation}.
         * <p/>
         * This element is declared by the element type {@code group-abbreviation} for the element type {@code part-group} in the score.mod schema file.
         *
         * @see PartGroup
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface GroupAbbreviation
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Justify.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code groupAbbreviationDisplay} classifies the MusicXML element, {@code group-abbreviation-display}.
         * <p/>
         * This element is declared by the element type {@code group-abbreviation-display} for the element type {@code part-group} in the score.mod schema file.
         *
         * @see PartGroup
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface GroupAbbreviationDisplay
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.PrintObject.class
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                DisplayText.class,
                AccidentalText.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code GroupBarline} classifies the MusicXML element, {@code group-barline}.
         * <p/>
         * This element is declared by the element type {@code group-barline} for the element type {@code part-group} in the score.mod schema file.
         *
         * @see PartGroup
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface GroupBarline
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Color.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Grouping} classifies the MusicXML element, {@code grouping}.
         * <p/>
         * This element is declared by the element type {@code grouping} in the direction.mod schema file.
         * <p/>
         * The {@code grouping} element is used for musical analysis.
         * When the element {@link Element.Attribute.Type type} is "start" or "single", it usually contains one or more {@link Feature feature} elements.
         * The {@link Element.Attribute.Number number} attribute is used for distinguishing between overlapping and hierarchical {@code groupings}.
         * The {@link Element.Attribute.MemberOf member-of} attribute allows for easy distinguishing of what {@code grouping} elements are in what hierarchy.
         * {@link Feature} elements contained within a "stop" {@link Element.Attribute.Type type} of {@code grouping} may be ignored.
         * <p/>
         * This element is flexible to allow for non-standard analyses.
         * Future versions of the MusicXML format may add elements that can represent more standardized categories of analysis data, allowing for easier data sharing.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Grouping
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
                Element.Attribute.MemberOf.class,
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Feature.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code GroupName} classifies the MusicXML element, {@code group-name}.
         * <p/>
         * This element is declared by the element type {@code group-name} for the element type {@code part-group} in the score.mod schema file.
         *
         * @see PartGroup
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface GroupName
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Justify.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code groupNameDisplay} classifies the MusicXML element, {@code group-name-display}.
         * <p/>
         * This element is declared by the element type {@code group-name-display} for the element type {@code part-group} in the score.mod schema file.
         *
         * @see PartGroup
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface GroupNameDisplay
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.PrintObject.class
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                DisplayText.class,
                AccidentalText.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code GroupSymbol} classifies the MusicXML element, {@code group-symbol}.
         * <p/>
         * This element is declared by the element type {@code group-symbol} for the element type {@code part-group} in the score.mod schema file.
         *
         * @see PartGroup
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface GroupSymbol
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.Color.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code GroupTime} classifies the MusicXML element, {@code group-time}.
         * <p/>
         * This element is declared by the element type {@code group-time} for the element type {@code part-group} in the score.mod schema file.
         *
         * @see PartGroup
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface GroupTime
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code HalfMuted} classifies the MusicXML element, {@code half-muted}.
         * <p/>
         * This element is declared by the element type {@code half-muted} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code half-muted} element represents the half-muted symbol, which looks like a circle with a plus sign inside.
         * The {@link Element.Attribute.SMuFL smufl} attribute can be used to distinguish different SMuFL glyphs that have a similar appearance such as brassMuteHalfClosed and guitarHalfOpenPedal.
         * If not present, the default glyph is brassMuteHalfClosed.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface HalfMuted
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.SMuFL.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code HammerOn} classifies the MusicXML element, {@code hammer-on}.
         * <p/>
         * This element is declared by the element type {@code hammer-on} for the element type {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code hammer-on} element is used in guitar and fretted instrument notation.
         * Since a single slur can be marked over many notes, the {@code hammer-on} element is separate so the individual pair of notes can be specified.
         * The element content can be used to specify how the hammer-on should be notated.
         * An empty element leaves this choice up to the application.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface HammerOn
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Handbell} classifies the MusicXML element, {@code handbell}.
         * <p/>
         * This element is declared by the element type {@code handbell} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code handbell} element represents notation for various techniques used in handbell and handchime music.
         * Valid values are belltree, damp, echo, gyro, hand martellato, mallet lift, mallet table, martellato, martellato lift, muted martellato, pluck lift, and swing.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Handbell
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[]{
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                BELLTREE,
                DAMP,
                ECHO,
                GYRO,
                HAND__MARTELLATO,
                MALLET__LIFT,
                MALLET__TABLE,
                MARTELLATO,
                MARTELLATO__LIFT,
                MUTED__MARTELLATO,
                PLUCK__LIFT,
                SWING
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code HarmonClosed} classifies the MusicXML element, {@code harmon-closed}.
         * <p/>
         * This element is declared by the element type {@code harmon-closed} for the element type {@code harmon-mute} in the note.mod schema file.
         * <p/>
         *
         * @see HarmonMute
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface HarmonClosed
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Location.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                HALF,
                NO,
                YES
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Harmonic} classifies the MusicXML element, {@code harmonic}.
         * <p/>
         * This element is declared by the element type {@code harmonic} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code harmonic} element indicates natural and artificial harmonics.
         * Natural harmonics usually notate the base pitch rather than the sounding pitch.
         * Allowing the type of pitch to be specified, combined with controls for appearance/playback differences, allows both the notation and the sound to be represented.
         * Artificial harmonics can add a notated touching-pitch; the pitch or fret at which the string is touched lightly to produce the harmonic.
         * Artificial pinch harmonics will usually not notate a touching pitch.
         * The attributes for the {@code harmonic} element refer to the use of the circular harmonic symbol, typically but not always used with natural harmonics.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Harmonic
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.PrintObject.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Natural.class,
                Artificial.class,
                BasePitch.class,
                TouchingPitch.class,
                SoundingPitch.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code HarmonMute} classifies the MusicXML element, {@code harmon-mute}.
         * <p/>
         * This element is declared by the element type {@code harmon-mute} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code harmon-mute} element represents the symbols used for harmon mutes in brass notation.
         * The {@link HarmonClosed harmon-closed} element represents whether the mute is closed, open, or half-open.
         * Valid element values are yes, no, and half.
         * The optional {@link Element.Attribute.Location location} attribute indicates which portion of the symbol is filled in when the element value is half.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface HarmonMute
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                HarmonClosed.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Harmony} classifies the MusicXML element, {@code harmony}.
         * <p/>
         * This element is declared by the element type {@code harmony} in the direction.mod schema file.
         * <p/>
         * The {@code harmony} elements are based on Humdrum's **harm encoding, extended to support chord symbols in popular music as well as functional harmony analysis in classical music.
         * <p/>
         * If there are alternate harmonies possible, this can be specified using multiple {@code harmony} elements differentiated by {@link Element.Attribute.Type type}.
         * Explicit harmonies have all notes present in the music; implied have some notes missing but implied; alternate represents alternate analyses.
         * <p/>
         * The {@code harmony} object may be used for analysis or for chord symbols.
         * The {@link Element.Attribute.PrintObject print-object} attribute controls whether or not anything is printed due to the {@code harmony} element.
         * The {@link Element.Attribute.PrintFrame print-frame} attribute controls printing of a frame or fretboard diagram.
         * The {@code print-style} entity sets the default for the {@code harmony}, but individual elements can override this with their own {@code print-style} values.
         * <p/>
         * A {@code harmony} element can contain many stacked chords (e.g. V of II).
         * A sequence of {@code harmony-chord} entities is used for this type of secondary function, where V of II would be represented by a {@code harmony-chord} with a V function followed by a {@code harmony-chord} with a II function.
         * <p/>
         * Harmony indications and general print and sound suggestions are, likewise to directions, not necessarily attached to particular {@link Note note} elements.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Harmony
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.PrintFrame.class,
                Element.Attribute.PrintObject.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Frame.class,
                Offset.class,
                Staff.class,
                // harmony-chord
                Root.class,
                Function.class,
                Kind.class,
                Inversion.class,
                Bass.class,
                Degree.class,
                // editorial
                Element.Footnote.class,
                Element.Level.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return null.
            */
            @Override
            default java.lang.String occurrence() {
                return null;
            }
        }

        /**
         * {@code HarpPedals} classifies the MusicXML element, {@code harp-pedals}.
         * <p/>
         * This element is declared by the element type {@code harp-pedals} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * The {@code harp-pedals} element is used to create harp pedal diagrams.
         * The {@link PedalStep pedal-step} and {@link PedalAlter pedal-alter} elements use the same values as the {@link Step step} and {@link Alter alter} elements.
         * For easiest reading, the {@link PedalTuning pedal-tuning} elements should follow standard harp pedal order, with {@link PedalStep pedal-step} values of D, C, B, E, F, G, and A.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface HarpPedals
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                PedalTuning.class,
            };

            /**
             * {@inheritDoc}
             *
             * @return {@link Occurrence#OnlyOne}.
             */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Haydn} classifies the MusicXML element, {@code haydn}.
         * <p/>
         * This element is declared by the element type {@code haydn} for the element type {@code ornaments} in the note.mod schema file.
         * <p/>
         * The {@code haydn} element represents the Haydn ornament.
         * This is defined in SMuFL as ornamentHaydn.
         *
         * @see Ornaments
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Haydn
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.StartNote.class,
                Element.Attribute.TrillStep.class,
                Element.Attribute.TwoNoteTurn.class,
                Element.Attribute.Accelerate.class,
                Element.Attribute.Beats.class,
                Element.Attribute.SecondBeat.class,
                Element.Attribute.LastBeat.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Heel} classifies the MusicXML element, {@code heel}.
         * <p/>
         * This element is declared by the element type {@code heel} for the element type {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code heel} element is used with organ pedals.
         * The {@link Element.Attribute.Substitution substitution} value is "no" if the attribute is not present.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Heel
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Substitution.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Hole} classifies the MusicXML element, {@code hole}.
         * <p/>
         * This element is declared by the element type {@code hole} for the element type {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code hole} element represents the symbols used for woodwind and brass fingerings as well as other notations.
         * The content of the optional {@link HoleType hole-type} element indicates what the hole symbol represents in terms of instrument fingering or other techniques.
         * The {@link HoleClosed hole-closed} element represents whether the hole is closed, open, or half-open.
         * Valid element values are yes, no, and half.
         * The optional {@link Element.Attribute.Location location} attribute indicates which portion of the hole is filled in when the element value is half.
         * The optional {@link HoleShape hole-shape} element indicates the shape of the hole symbol; the default is a circle.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Hole
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                HoleType.class,
                HoleClosed.class,
                HoleShape.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code HoleClosed} classifies the MusicXML element, {@code hole-closed}.
         * <p/>
         * This element is declared by the element type {@code hole-closed} for the element type {@code hole} in the note.mod schema file.
         *
         * @see Hole
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface HoleClosed
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Location.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                HALF,
                NO,
                YES
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code HoleShape} classifies the MusicXML element, {@code hole-shape}.
         * <p/>
         * This element is declared by the element type {@code hole-shape} for the element type {@code hole} in the note.mod schema file.
         *
         * @see Hole
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface HoleShape
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code HoleType} classifies the MusicXML element, {@code hole-type}.
         * <p/>
         * This element is declared by the element type {@code hole-type} for the element type {@code hole} in the note.mod schema file.
         *
         * @see Hole
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface HoleType
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Humming} classifies the MusicXML element, {@code humming}.
         * <p/>
         * This element is declared by the element type {@code humming} for the element type {@code lyric} in the common.mod schema file.
         *
         * @see Lyric
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Humming
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Identification} classifies the MusicXML element, {@code identification}.
         * <p/>
         * This element is declared by the element type {@code identification} for the entity type {@code score-header} in the score.mod schema file; and for the element type {@code score-part} in the identify.mod schema file.
         * <p/>
         * Identification contains basic metadata about the score.
         * It includes the information in MuseData headers that may apply at a score-wide, movement-wide, or part-wide level.
         * The {@link Creator creator}, {@link Rights rights}, {@link Source source}, and {@link Relation relation} elements are based on Dublin Core.
         *
         * @see ScorePart
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Identification
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                    Creator.class,
                    Rights.class,
                    Encoding.class,
                    Source.class,
                    Relation.class,
                    Miscellaneous.class,
                };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Image} classifies the MusicXML element, {@code image}.
         * <p/>
         * This element is declared by the element type {@code image} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * The {@code image} element is used to include graphical images in a score.
         * The required {@link Element.Attribute.Source source} attribute is the URL for the image file.
         * The required {@link Element.Attribute.Type type} attribute is the MIME type for the image file format.
         * Typical choices include application/postscript, image/gif, image/jpeg, image/png, and image/tiff.
         * The optional {@link Element.Attribute.Height height} and {@link Element.Attribute.Width width} attributes are used to size and scale an image.
         * The image should be scaled independently in X and Y if both {@link Element.Attribute.Height height} and {@link Element.Attribute.Width width} are specified.
         * If only one attribute is specified, the image should be scaled proportionally to fit in the specified dimension.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Image
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Source.class,
                Element.Attribute.Type.class,
                Element.Attribute.Height.class,
                Element.Attribute.Width.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class, // valign-image
                Element.Attribute.ID.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Instrument} classifies the MusicXML element, {@code instrument}.
         * <p/>
         * This element is declared by the element type {@code instrument} for the entity type {@code note} in the note.mod schema file.
         * <p/>
         * If multiple {@link ScoreInstrument score-instrument}s are specified on a {@link ScorePart score-part}, there should be an {@code instrument} element for each {@link Note note} in the {@link Part part}.
         * The {@link Element.Attribute.ID id} attribute is an IDREF back to the {@link ScoreInstrument score-instrument} ID.
         *
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Instrument
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code InstrumentAbbreviation} classifies the MusicXML element, {@code instrument-abbreviation}.
         * <p/>
         * This element is declared by the element type {@code instrument-abbreviation} for the element type {@code score-instrument} in the score.mod schema file.
         *
         * @see ScoreInstrument
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface InstrumentAbbreviation
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code InstrumentName} classifies the MusicXML element, {@code instrument-name}.
         * <p/>
         * This element is declared by the element type {@code instrument-name} for the element type {@code score-instrument} in the score.mod schema file.
         *
         * @see ScoreInstrument
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface InstrumentName
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Instruments} classifies the MusicXML element, {@code instruments}.
         * <p/>
         * This element is declared by the element type {@code instruments} for the element {@code attributes} in the attributes.mod schema file.
         * <p/>
         * Instruments are only used if more than one instrument is represented in the part (e.g., oboe I and II where they play together most of the time).
         * If absent, a value of 1 is assumed.
         *
         * @see Attributes
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Instruments
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code InstrumentSound} classifies the MusicXML element, {@code instrument-sound}.
         * <p/>
         * This element is declared by the element type {@code instrument-sound} for the element type {@code score-instrument} in the score.mod schema file.
         *
         * @see ScoreInstrument
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface InstrumentSound
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Interchangeable} classifies the MusicXML element, {@code interchangeable}.
         * <p/>
         * This element is declared by the element type {@code interchangeable} for the element type {@code time} in the attributes.mod schema file.
         *
         * @see Time
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Interchangeable
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Symbol.class,
                Element.Attribute.Separator.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                TimeRelation.class,
                Beats.class,
                BeatType.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Inversion} classifies the MusicXML element, {@code inversion}.
         * <p/>
         * This element is declared by the element type {@code inversion} for the entity type {@code harmony-chord} in the direction.mod schema file.
         * <p/>
         * Inversion is a number indicating which inversion is used: 0 for root position, 1 for first inversion, etc.
         *
         * @see Harmony
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Inversion
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code InvertedMordent} classifies the MusicXML element, {@code inverted-mordent}.
         * <p/>
         * This element is declared by the element type {@code inverted-mordent} for the element type {@code ornaments} in the note.mod schema file.
         * <p/>
         * The {@link Element.Attribute.Long long} attribute for the {@code inverted-mordent} element is "no" by default.
         * The {@code inverted-mordent} element represents the mordent sign without the vertical line
         * The choice of which mordent is inverted differs between MusicXML and SMuFL.
         * The {@link Element.Attribute.Approach approach} and {@link Element.Attribute.Departure departure} attributes are used for compound ornaments, indicating how the beginning and ending of the ornament look relative to the main part of the mordent.
         *
         * @see Ornaments
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface InvertedMordent
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Long.class,
                Element.Attribute.Approach.class,
                Element.Attribute.Departure.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.StartNote.class,
                Element.Attribute.TrillStep.class,
                Element.Attribute.TwoNoteTurn.class,
                Element.Attribute.Accelerate.class,
                Element.Attribute.Beats.class,
                Element.Attribute.SecondBeat.class,
                Element.Attribute.LastBeat.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code InvertedTurn} classifies the MusicXML element, {@code inverted-turn}.
         * <p/>
         * This element is declared by the element type {@code inverted-turn} for the element type {@code ornaments} in the note.mod schema file.
         * <p/>
         * The {@code inverted-turn} element has the shape which goes down and then up.
         * If the {@link Element.Attribute.Slash slash} attribute is yes, then a vertical line is used to slash the turn; it is no by default.
         *
         * @see Ornaments
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface InvertedTurn
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.StartNote.class,
                Element.Attribute.TrillStep.class,
                Element.Attribute.TwoNoteTurn.class,
                Element.Attribute.Accelerate.class,
                Element.Attribute.Beats.class,
                Element.Attribute.SecondBeat.class,
                Element.Attribute.LastBeat.class,
                Element.Attribute.Slash.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code InvertedVerticalTurn} classifies the MusicXML element, {@code inverted-vertical-turn}.
         * <p/>
         * This element is declared by the element type {@code inverted-vertical-turn} for the element type {@code ornaments} in the note.mod schema file.
         *
         * @see Ornaments
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface InvertedVerticalTurn
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.StartNote.class,
                Element.Attribute.TrillStep.class,
                Element.Attribute.TwoNoteTurn.class,
                Element.Attribute.Accelerate.class,
                Element.Attribute.Beats.class,
                Element.Attribute.SecondBeat.class,
                Element.Attribute.LastBeat.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code IPA} classifies the MusicXML element, {@code ipa}.
         * <p/>
         * This element is declared by the element type {@code ipa} for the element type {@code play} in the common.mod schema file.
         *
         * @see Play
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface IPA
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Key} classifies the MusicXML element, {@code key}.
         * <p/>
         * This element is declared by the element type {@code key} for the element type {@code attributes} in the attributes.mod schema file.
         * <p/>
         * Traditional key signatures are represented by the number of flats and sharps, plus an optional mode for major/minor/mode distinctions.
         * Negative numbers are used for flats and positive numbers for sharps, reflecting the key's placement within the circle of fifths (hence the element name).
         * A {@link Cancel cancel} element indicates that the old key signature should be cancelled before the new one appears.
         * This will always happen when changing to C major or A minor and need not be specified then.
         * The {@link Cancel cancel} value matches the {@link Fifths fifths} value of the cancelled key signature (e.g., a cancel of -2 will provide an explicit cancellation for changing from B flat major to F major).
         * The optional {@link Element.Attribute.Location location} attribute indicates where a key signature cancellation appears relative to a new key signature: to the left, to the right, or before the barline and to the left.
         * It is left by default.
         * For mid-measure key elements, a {@link Cancel cancel} {@link Element.Attribute.Location location} of before-barline should be treated like a {@link Cancel cancel} {@link Element.Attribute.Location location} of left.
         * <p/>
         * Non-traditional key signatures can be represented using the Humdrum/Scot concept of a list of altered tones.
         * The {@link KeyStep key-step} and {@link KeyAlter key-alter} elements are represented the same way as the {@link Step step} and {@link Alter alter} elements are in the {@link Pitch pitch} element in the note.mod file.
         * The optional {@link KeyAccidental key-accidental} element is represented the same way as the {@link Accidental accidental} element in the note.mod file.
         * It is used for disambiguating microtonal accidentals.
         * The different element names indicate the different meaning of altering notes in a scale versus altering a sounding pitch.
         * <p/>
         * Valid {@link Mode mode} values include major, minor, dorian, phrygian, lydian, mixolydian, aeolian, ionian, locrian, and none.
         * <p/>
         * The optional {@link Element.Attribute.Number number} attribute refers to staff numbers, from top to bottom on the system.
         * If absent, the key signature applies to all staves in the part.
         * <p/>
         * The optional list of {@link KeyOctave key-octave} elements is used to specify in which octave each element of the key signature appears.
         * The content specifies the octave value using the same values as the {@link DisplayOctave display-octave} element.
         * The {@link Element.Attribute.Number number} attribute is a positive integer that refers to the key signature element in left-to-right order.
         * If the {@link Element.Attribute.Cancel cancel} attribute is set to yes, then this number refers to the canceling key signature specified by the {@link Cancel cancel} element in the parent {@code key} element.
         * The {@link Element.Attribute.Cancel cancel} attribute cannot be set to yes if there is no corresponding {@link Cancel cancel} element within the parent {@code key} element.
         * It is no by default.
         * <p/>
         * Key signatures appear at the start of each system unless the {@link Element.Attribute.PrintObject print-object} attribute has been set to "no".
         *
         * @see Attributes
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Key
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Number.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeY.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.PrintObject.class,
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Cancel.class,
                Fifths.class,
                Mode.class,
                KeyStep.class,
                KeyAlter.class,
                KeyAccidental.class,
                KeyOctave.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code KeyAccidental} classifies the MusicXML element, {@code key-accidental}.
         * <p/>
         * This element is declared by the element type {@code key-accidental} for the element type {@code key} in the attributes.mod schema file.
         *
         * @see Accidental
         * @see Key
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface KeyAccidental
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.SMuFL.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.Accidental_Values;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code KeyAlter} classifies the MusicXML element, {@code key-alter}.
         * <p/>
         * This element is declared by the element type {@code key-alter} for the element type {@code key} in the attributes.mod schema file.
         *
         * @see Key
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface KeyAlter
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code KeyAlter} classifies the MusicXML element, {@code key-octave}.
         * <p/>
         * This element is declared by the element type {@code key-octave} for the element type {@code key} in the attributes.mod schema file.
         *
         * @see Key
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface KeyOctave
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Number.class,
                Element.Attribute.Cancel.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code KeyStep} classifies the MusicXML element, {@code key-step}.
         * <p/>
         * This element is declared by the element type {@code key-step} for the element type {@code key} in the attributes.mod schema file.
         *
         * @see Key
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface KeyStep
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Kind} classifies the MusicXML element, {@code kind}.
         * <p/>
         * This element is declared by the element type {@code kind} for the entity type {@code harmony-chord} in the direction.mod schema file.
         * <p/>
         * Kind indicates the type of chord.
         * {@link Degree Degree} elements can then add, subtract, or alter from these starting points.
         * Values include:
         * <p/>
         * Triads:<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;major (major third, perfect fifth)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;minor (minor third, perfect fifth)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;augmented (major third, augmented fifth)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;diminished (minor third, diminished fifth)<br/>
         * Sevenths:<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;dominant (major triad, minor seventh)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;major-seventh (major triad, major seventh)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;minor-seventh (minor triad, minor seventh)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;diminished-seventh<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(diminished triad, diminished seventh)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;augmented-seventh<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(augmented triad, minor seventh)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;half-diminished<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(diminished triad, minor seventh)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;major-minor<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(minor triad, major seventh)<br/>
         * Sixths:<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;major-sixth (major triad, added sixth)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;minor-sixth (minor triad, added sixth)<br/>
         * Ninths:<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;dominant-ninth (dominant-seventh, major ninth)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;major-ninth (major-seventh, major ninth)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;minor-ninth (minor-seventh, major ninth)<br/>
         * 11ths (usually as the basis for alteration):<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;dominant-11th (dominant-ninth, perfect 11th)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;major-11th (major-ninth, perfect 11th)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;minor-11th (minor-ninth, perfect 11th)<br/>
         * 13ths (usually as the basis for alteration):<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;dominant-13th (dominant-11th, major 13th)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;major-13th (major-11th, major 13th)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;minor-13th (minor-11th, major 13th)<br/>
         * Suspended:<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;suspended-second (major second, perfect fifth)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;suspended-fourth (perfect fourth, perfect fifth)<br/>
         * Functional sixths:<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;Neapolitan<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;Italian<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;French<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;German<br/>
         * Other:<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;pedal (pedal-point bass)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;power (perfect fifth)<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;Tristan
         * <p/>
         * The "other" {@code kind} is used when the harmony is entirely composed of add elements.
         * The "none" {@code kind} is used to explicitly encode absence of chords or functional harmony.
         * <p/>
         * The attributes are used to indicate the formatting of the symbol.
         * Since the {@code kind} element is the constant in all the {@code harmony-chord} entities that can make up a polychord, many formatting attributes are here.
         * <p/>
         * The {@link Element.Attribute.UseSymbols use-symbols} attribute is yes if the {@code kind} should be represented when possible with harmony symbols rather than letters and numbers.
         * These symbols include:
         * <p/>
         * &nbsp;&nbsp;&nbsp;&nbsp;major: a triangle, like Unicode 25B3<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;minor: -, like Unicode 002D<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;augmented: +, like Unicode 002B<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;diminished: °, like Unicode 00B0<br/>
         * &nbsp;&nbsp;&nbsp;&nbsp;half-diminished: ø, like Unicode 00F8
         * <p/>
         * For the major-minor {@code kind}, only the minor symbol is used when {@link Element.Attribute.UseSymbols use-symbols} is yes.
         * The major symbol is set using the {@link Element.Attribute.Symbol symbol} attribute in the {@link DegreeValue degree-value} element.
         * The corresponding {@link DegreeAlter degree-alter} value will usually be 0 in this case.
         * <p/>
         * The {@link Element.Attribute.Text text} attribute describes how the {@code kind} should be spelled in a score.
         * If {@link Element.Attribute.UseSymbols use-symbols} is yes, the value of the {@link Element.Attribute.Text text} attribute follows the symbol.
         * The {@link Element.Attribute.StackDegrees stack-degrees} attribute is yes if the {@link Degree degree} elements should be stacked above each other.
         * The {@link Element.Attribute.ParenthesesDegrees parentheses-degrees} attribute is yes if all the {@link Degree degrees} should be in parentheses.
         * The {@link Element.Attribute.BracketDegrees bracket-degrees} attribute is yes if all the {@link Degree degrees} should be in a bracket.
         * If not specified, these values are implementation-specific.
         * The alignment attributes are for the entire {@code harmony-chord} entity of which this {@code kind} element is a part.
         *
         * @see Harmony
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Kind
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.UseSymbols.class,
                Element.Attribute.Text.class,
                Element.Attribute.StackDegrees.class,
                Element.Attribute.ParenthesesDegrees.class,
                Element.Attribute.BracketDegrees.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Laughing} classifies the MusicXML element, {@code laughing}.
         * <p/>
         * This element is declared by the element type {@code laughing} for the element type {@code lyric} in the common.mod schema file.
         *
         * @see Lyric
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Laughing
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code LeftDivider} classifies the MusicXML element, {@code left-divider}.
         * <p/>
         * This element is declared by the element type {@code left-divider} for the element type {@code system-dividers} in the layout.mod schema file.
         *
         * @see SystemDividers
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface LeftDivider
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.PrintObject.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class
            };

            /** The element definition. */
            java.lang.String Definition = null;

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code LeftMargin} classifies the MusicXML element, {@code left-margin}.
         * <p/>
         * This element is declared by the element type {@code left-margin} in the layout.mod schema file.
         * <p/>
         * Margin elements are included within many of the larger layout elements.
         *
         * @see PageMargins
         * @see SystemMargins
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface LeftMargin
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.LayoutTenths_Values;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Level} classifies the MusicXML element, {@code level}.
         * <p/>
         * This element is declared by the element type {@code level} in the common.mod schema file.
         * <p/>
         * Level is used to specify editorial information, while {@link Voice voice} is used to distinguish between multiple voices (what MuseData calls tracks) in individual {@link Part parts}.
         * This element is used throughout the different MusicXML DTD modules.
         * If the {@link Element.Attribute.Reference reference} attribute for the {@code level} element is yes, this indicates editorial information that is for display only and should not affect playback.
         * For instance, a modern edition of older music may set {@link Element.Attribute.Reference reference}="yes" on the attributes containing the music's original clef, key, and time signature.
         * It is no by default.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Level
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Reference.class,
                Element.Attribute.Parentheses.class,
                Element.Attribute.Bracket.class,
                Element.Attribute.Size.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Line} classifies the MusicXML element, {@code line}.
         * <p/>
         * This element is declared by the element type {@code line} for the element type {@code clef} in the attributes.mod schema file.
         * <p/>
         * Line numbers are counted from the bottom of the staff.
         * Standard values are 2 for the G sign (treble clef), 4 for the F sign (bass clef), 3 for the C sign (alto clef) and 5 for TAB (on a 6-line staff).
         *
         * @see Clef
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Line
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code LineWidth} classifies the MusicXML element, {@code line-width}.
         * <p/>
         * This element is declared by the element type {@code line-width} for the element type {@code appearance} in the layout.mod schema file.
         *
         * @see Appearance
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface LineWidth
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
            };

            /** The element definition. */
            java.lang.String Definition = null;

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.LayoutTenths_Values;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Link} classifies the MusicXML element, {@code link}.
         * <p/>
         * This element is declared by the element type {@code link} in the link.mod schema file for the entity type {@code music-data} in the score.mod schema file.
         * <p/>
         * The {@link Element.Attribute.Element_ element} and {@link Element.Attribute.Position position} attributes are new as of Version 2.0.
         * They allow for links to be positioned at higher resolution than the level of music-data elements.
         * When no {@link Element.Attribute.Element_ element} and {@link Element.Attribute.Position position} attributes are present, the {@code link} element refers to the next sibling element in the MusicXML file.
         * The {@link Element.Attribute.Element_ element} attribute specifies an element type for a descendant of the next sibling element that is not a {@code link} or {@link Bookmark bookmark}.
         * The {@link Element.Attribute.Position position} attribute specifies the position of this descendant element, where the first position is 1.
         * The {@link Element.Attribute.Position position} attribute is ignored if the {@link Element.Attribute.Element_ element} attribute is not present.
         * For instance, an {@link Element.Attribute.Element_ element} value of "beam" and a {@link Element.Attribute.Position position} value of "2" defines the {@code link} to refer to the second {@link Beam beam} descendant of the next sibling element that is not a {@code link} or {@link Bookmark bookmark}.
         * This is equivalent to an XPath test of [.//beam[2]] done in the context of the sibling element.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Link
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Name.class,
                Element.Attribute.Element_.class,
                Element.Attribute.Position.class,
                Element.Attribute.XmlnsXlink.class,
                Element.Attribute.XlinkHref.class,
                Element.Attribute.XlinkType.class,
                Element.Attribute.XlinkRole.class,
                Element.Attribute.XlinkTitle.class,
                Element.Attribute.XlinkShow.class,
                Element.Attribute.XlinkActuate.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class
            };

            /** The element definition. */
            java.lang.String Definition = null;

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Lyric} classifies the MusicXML element, {@code lyric}.
         * <p/>
         * This element is declared by the element type {@code lyric} for the element type {@code note} in the note.mod schema file.
         * <p/>
         * Text underlays for lyrics, based on Humdrum with support for other formats.
         * The {@code lyric} {@link Element.Attribute.Number number} indicates multiple lines, though a name can be used as well (as in Finale's verse/chorus/section specification).
         * Word extensions are represented using the {@link Extend extend} element.
         * Hyphenation is indicated by the {@link Syllabic syllabic} element, which can be single, begin, end, or middle.
         * These represent single-syllable words, word-beginning syllables, word-ending syllables, and mid-word syllables.
         * Multiple syllables on a single note are separated by {@link Elision elision} elements.
         * A hyphen in the {@link Text text} element should only be used for an actual hyphenated word.
         * Two {@link Text text} elements that are not separated by an {@link Elision elision} element are part of the same syllable, but may have different text formatting.
         * <p/>
         * {@link Humming Humming} and {@link Laughing laughing} representations are taken from Humdrum.
         * The {@link EndLine end-line} and {@link EndParagraph end-paragraph} elements come from RP-017 for Standard MIDI File Lyric meta-events; they help facilitate lyric display for Karaoke and similar applications.
         * Language names for {@link Text text} elements come from ISO 639, with optional country subcodes from ISO 3166.
         * {@link Element.Attribute.Justify Justification} is center by default; {@link Element.Attribute.Placement placement} is below by default.
         * The {@link Element.Attribute.PrintObject print-object} attribute can override a note's {@link Element.Attribute.PrintLyric print-lyric} attribute in cases where only some lyrics on a note are printed, as when lyrics for later verses are printed in a block of text rather than with each note.
         * The {@link Element.Attribute.TimeOnly time-only} attribute precisely specifies which lyrics are to be sung which time through a repeated section.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Lyric
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Number.class,
                Element.Attribute.Name.class,
                Element.Attribute.Justify.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.Placement.class,
                Element.Attribute.Color.class,
                Element.Attribute.PrintObject.class,
                Element.Attribute.TimeOnly.class,
                Element.Attribute.ID.class
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Syllabic.class,
                Text.class,
                Elision.class,
                Extend.class,
                Laughing.class,
                Humming.class,
                EndLine.class,
                EndParagraph.class,
                // editorial
                Element.Footnote.class,
                Element.Level.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code LyricFont} classifies the MusicXML element, {@code lyric-font}.
         * <p/>
         * This element is declared by the element type {@code lyric-font} for the element type {@code defaults} in the score.mod schema file.
         *
         * @see Defaults
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface LyricFont
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Number.class,
                Element.Attribute.Name.class,
                // font
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code LyricLanguage} classifies the MusicXML element, {@code lyric-language}.
         * <p/>
         * This element is declared by the element type {@code lyric-language} for the element type {@code defaults} in the score.mod schema file.
         *
         * @see Defaults
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface LyricLanguage
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Number.class,
                Element.Attribute.Name.class,
                Element.Attribute.XmlLang.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Measure} classifies the MusicXML element, {@code measure}.
         * <p/>
         * This element is declared by the element type {@code measure} for the element types {@code score-partwise} and {@code score-timewise} in the score.mod schema file.
         * <p/>
         * The {@link Element.Attribute.Implicit implicit} attribute is set to "yes" for measures where the {@code measure} {@link Element.Attribute.Number number} should never appear, such as pickup measures and the last half of mid-measure repeats.
         * The value is "no" if not specified.
         * <p/>
         * The {@link Element.Attribute.NonControlling non-controlling} attribute is intended for use in multimetric music like the Don Giovanni minuet.
         * If set to "yes", the left barline in this measure does not coincide with the left barline of measures in other parts.
         * The value is "no" if not specified.
         * <p/>
         * In partwise files, the {@link Element.Attribute.Number number} attribute should be the same for measures in different parts that share the same left {@link Barline barline}.
         * While the {@link Element.Attribute.Number number} attribute is often numeric, it does not have to be.
         * Non-numeric values are typically used together with the {@link Element.Attribute.Implicit implicit} or {@link Element.Attribute.NonControlling non-controlling} attributes being set to "yes".
         * For a pickup measure, the {@link Element.Attribute.Number number} attribute is typically set to "0" and the {@link Element.Attribute.Implicit implicit} attribute is typically set to "yes".
         * <p/>
         * If {@code measure} numbers are not unique within a {@link Part part}, this can cause problems for conversions between partwise and timewise formats.
         * The {@link Element.Attribute.Text text} attribute allows specification of displayed {@code measure} numbers that are different than what is used in the {@link Element.Attribute.Number number} attribute.
         * This attribute is ignored for measures where the {@link Element.Attribute.Implicit implicit} attribute is set to "yes".
         * The {@link Element.Attribute.Text text} attribute for a {@code measure} element has at least one character.
         * Further details about measure numbering can be specified using the {@link MeasureNumbering measure-numbering} element defined in the direction.mod file.
         * <p/>
         * Measure {@link Element.Attribute.Width width} is specified in tenths.
         * These are the global tenths specified in the {@link Scaling scaling} element, not local tenths as modified by the {@link StaffSize staff-size} element.
         * The {@link Element.Attribute.Width width} covers the entire {@code measure} from {@link Barline barline} or {@link System system} start to {@link Barline barline} or {@link System system} end.
         *
         * @see ScorePartwise
         * @see ScoreTimewise
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Measure
        extends
            Analytic,
            XML.Schematic.PerType
        {
            /** The array of accepted parent element types. */
            Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                ScorePartwise.class,
                ScoreTimewise.class,
            };

            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                    Element.Attribute.Number.class,
                    Element.Attribute.Text.class,
                    Element.Attribute.Implicit.class,
                    Element.Attribute.NonControlling.class,
                    Element.Attribute.Width.class,
                    Element.Attribute.ID.class
                };

            @Override
            default Class<? extends Measure> per(final Class<?> type) {
                if (type == ScorePartwise.class)
                    return Measure_PerScorePartwise.class;
                else
                if (type == ScoreTimewise.class)
                    return Measure_PerScoreTimewise.class;

                return null;
            }

            interface Measure_PerScorePartwise
            extends Measure
            {
                Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                    Element.Note.class,
                    Element.Backup.class,
                    Element.Forward.class,
                    Element.Direction.class,
                    Element.Attributes.class,
                    Element.Harmony.class,
                    Element.FiguredBass.class,
                    Element.Print.class,
                    Element.Sound.class,
                    Element.Barline.class,
                    Element.Grouping.class,
                    Element.Link.class,
                    Element.Bookmark.class,
                };
            }

            interface Measure_PerScoreTimewise
            extends Measure
            {
                Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                    Part.class,
                };
            }
        }

        /**
         * {@code MeasureDistance} classifies the MusicXML element, {@code measure-distance}.
         * <p/>
         * This element is declared by the element type {@code measure-distance} for the element type {@code measure-layout} in the layout.mod schema file.
         *
         * @see MeasureLayout
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MeasureDistance
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.LayoutTenths_Values;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code MeasureLayout} classifies the MusicXML element, {@code measure-layout}.
         * <p/>
         * This element is declared by the element type {@code measure-layout} in the layout.mod schema file, for the element type {@code print} in the direction.mod schema file.
         * <p/>
         * Measure layout includes the horizontal distance from the previous measure.
         * This value is only used for systems where there is horizontal whitespace in the middle of a system, as in systems with codas.
         * To specify the measure width, use the {@link Element.Attribute.Width width} attribute of the {@link Measure measure} element.
         *
         * @see Print
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MeasureLayout
        extends Element
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                MeasureDistance.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code MeasureNumbering} classifies the MusicXML element, {@code measure-numbering}.
         * <p/>
         * This element is declared by the element type {@code measure-numbering} for the element type {@code print} in the direction.mod schema file.
         * <p/>
         * The {@code measure-numbering} element describes how measure numbers are displayed on this part.
         * Values may be none, measure, or system.
         * The {@link Element.Attribute.Number number} attribute from the {@link Measure measure} element is used for printing.
         * {@link Measure Measures} with an {@link Element.Attribute.Implicit implicit} attribute set to "yes" never display a measure number, regardless of the {@code measure-numbering} setting.
         *
         * @see Print
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MeasureNumbering
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                MEASURE,
                NONE,
                SYSTEM
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code MeasureRepeat} classifies the MusicXML element, {@code measure-repeat}.
         * <p/>
         * This element is declared by the element type {@code measure-repeat} for the element type {@code measure-style} in the attributes.mod schema file.
         * <p/>
         * The {@code measure-repeat} element is used for both single and multiple measure repeats.
         * The text of the element indicates the number of measures to be repeated in a single pattern.
         * The {@link Element.Attribute.Slashes slashes} attribute specifies the number of slashes to use in the repeat sign.
         * It is 1 if not specified.
         * Both the start and the stop of the {@code measure-repeat} must be specified.
         *
         * @see MeasureStyle
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MeasureRepeat
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Slashes.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code MeasureStyle} classifies the MusicXML element, {@code measure-style}.
         * <p/>
         * This element is declared by the element type {@code measure-style} for the element type {@code attributes} in the attributes.mod schema file.
         * <p/>
         * A {@code measure-style} indicates a special way to print partial to multiple measures within a part.
         * This includes multiple rests over several measures, repeats of beats, single, or multiple measures, and use of slash notation.
         * <p/>
         * The {@link MeasureRepeat measure-repeat} and {@link BeatRepeat beat-repeat} element specify a notation style for repetitions.
         * The actual music being repeated needs to be repeated within the MusicXML file.
         * These elements specify the notation that indicates the repeat.
         * <p/>
         * The {@link MultipleRest multiple-rest} and {@link MeasureRepeat measure-repeat} symbols indicate the number of measures covered in the element content.
         * The {@link BeatRepeat beat-repeat} and {@link Slash slash} elements can cover partial measures.
         * All but the {@link MultipleRest multiple-rest} element use a {@link Element.Attribute.Type type} attribute to indicate starting and stopping the use of the style.
         * The optional {@link Element.Attribute.Number number} attribute specifies the staff number from top to bottom on the system, as with {@link Clef clef}.
         * <p/>
         * The {@link SlashType slash-type} and {@link SlashDot slash-dot} elements are optional children of the {@link BeatRepeat beat-repeat} and {@link Slash slash} elements.
         * They have the same values as the {@link Type type} and {@link Dot dot} elements, and define what the beat is for the display of repetition marks.
         * If not present, the beat is based on the current time signature.
         * <p/>
         * The {@link ExceptVoice except-voice} element is used to specify a combination of slash notation and regular notation.
         * Any {@link Note note} elements that are in voices specified by the {@link ExceptVoice except-voice} elements are displayed in normal notation, in addition to the slash notation that is always displayed.
         *
         * @see Attributes
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MeasureStyle
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Number.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                MultipleRest.class,
                MeasureRepeat.class,
                BeatRepeat.class,
                Slash.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Membrane} classifies the MusicXML element, {@code membrane}.
         * <p/>
         * This element is declared by the element type {@code membrane} for the element type {@code percussion} in the direction.mod schema file.
         * <p/>
         * The membrane element represents pictograms for membrane percussion instruments.
         * Valid values are bass drum, bass drum on side, bongos, Chinese tomtom, conga drum, cuica, goblet drum, Indo-American tomtom, Japanese tomtom, military drum, snare drum, snare drum snares off, tabla, tambourine, tenor drum, timbales, and tomtom.
         *
         * @see Percussion
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Membrane
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                BASS__DRUM,
                BASS__DRUM__ON__SIDE,
                BONGOS,
                CHINESE__TOMTOM,
                CONGA__DRUM,
                CUICA,
                GOBLET__DRUM,
                INDO_AMERICAN__TOMTOM,
                JAPANESE__TOMTOM,
                MILITARY__DRUM,
                SNARE__DRUM,
                SNARE__DRUM__SNARES__OFF,
                TABLA,
                TAMBOURINE,
                TENOR__DRUM,
                TIMBALES,
                TOMTOM
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Metal} classifies the MusicXML element, {@code metal}.
         * <p/>
         * This element is declared by the element type {@code metal} for the element type {@code percussion} in the direction.mod schema file.
         * <p/>
         * The metal element represents pictograms for metal percussion instruments.
         * Valid values are agogo, almglocken, bell, bell plate, bell tree, brake drum, cencerro, chain rattle, Chinese cymbal, cowbell, crash cymbals, crotale, cymbal tongs, domed gong, finger cymbals, flexatone, gong, hi-hat, high-hat cymbals, handbell, jaw harp, jingle bells, musical saw, shell bells, sistrum, sizzle cymbal, sleigh bells, suspended cymbal, tam tam, tam tam with beater, triangle, and Vietnamese hat.
         * The hi-hat value refers to a pictogram like Stone's high-hat cymbals, but without the long vertical line at the bottom.
         *
         * @see Percussion
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Metal
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                AGOGO,
                ALMGLOCKEN,
                BELL,
                BELL__PLATE,
                BELL__TREE,
                BRAKE__DRUM,
                CENCERRO,
                CHAIN__RATTLE,
                CHINESE__CYMBAL,
                COWBELL,
                CRASH__CYMBALS,
                CROTALE,
                CYMBAL__TONGS,
                DOMED__GONG,
                FINGER__CYMBALS,
                FLEXATONE,
                GONG,
                HI_HAT,
                HIGH_HAT__CYMBALS,
                HANDBELL,
                JAW__HARP,
                JINGLE__BELLS,
                MUSICAL__SAW,
                SLEIGH__BELLS,
                SISTRUM,
                SIZZLE__CYMBAL,
                SLEIGH__BELLS,
                SUSPENDED__CYMBAL,
                TAM__TAM,
                TAM__TAM__WITH__BEATER,
                TRIANGLE,
                VIETNAMESE__HAT
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Metronome} classifies the MusicXML element, {@code metronome}.
         * <p/>
         * This element is declared by the element type {@code metronome} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * Metronome marks and other metric relationships.
         * <p/>
         * The {@link BeatUnit beat-unit} values are the same as for a {@link Type type} element, and the {@link BeatUnitDot beat-unit-dot} works like the {@link Dot dot} element.
         * The {@link BeatUnitTied beat-unit-tied} type indicates a {@link BeatUnit beat-unit} that is tied to the preceding {@link BeatUnit beat-unit}.
         * The {@link PerMinute per-minute} element can be a number, or a text description including numbers.
         * The {@link Element.Attribute.Parentheses parentheses} attribute indicates whether or not to put the metronome mark in parentheses; its value is no if not specified.
         * If a font is specified for the {@link PerMinute per-minute} element, it overrides the font specified for the overall {@code metronome} element.
         * This allows separate specification of a music font for {@link BeatUnit beat-unit} and a text font for the numeric value in cases where a single {@code metronome} font is not used.
         * <p/>
         * The {@link MetronomeNote metronome-note} and {@link MetronomeRelation metronome-relation} elements allow for the specification of metric modulations and other metric relationships, such as swing tempo marks where two eighths are equated to a quarter note / eighth note triplet.
         * If the {@link MetronomeArrows metronome-arrows} element is present, it indicates that metric modulation arrows are displayed on both sides of the metronome mark.
         * The {@link MetronomeType metronome-type}, {@link MetronomeBeam metronome-beam}, {@link MetronomeDot metronome-dot}, and {@link MetronomeTied metronome-tied} elements work like the {@link Type type}, {@link Beam beam}, {@link Dot dot}, and {@link Tied tied} elements.
         * The {@link MetronomeTuplet metronome-tuplet} element uses the same element structure as the {@link TimeModification time-modification} element along with some attributes from the {@link Tuplet tuplet} element.
         * The {@link MetronomeRelation metronome-relation} element describes the relationship symbol that goes between the two sets of {@link MetronomeNote metronome-note} elements.
         * The currently allowed value is equals, but this may expand in future versions.
         * If the element is empty, the equals value is used.
         * The {@link MetronomeRelation metronome-relation} and the following set of {@link MetronomeNote metronome-note} elements are optional to allow display of an isolated Grundschlagnote.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Metronome
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Parentheses.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.Justify.class,
                Element.Attribute.ID.class
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                BeatUnit.class,
                BeatUnitDot.class,
                BeatUnitTied.class,
                PerMinute.class,
                MetronomeArrows.class,
                MetronomeNote.class,
                MetronomeRelation.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code MetronomeArrows} classifies the MusicXML element, {@code metronome-arrows}.
         * <p/>
         * This element is declared by the element type {@code metronome-arrows} for the element type {@code metronome} in the direction.mod schema file.
         *
         * @see Metronome
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MetronomeArrows
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code MetronomeBeam} classifies the MusicXML element, {@code metronome-beam}.
         * <p/>
         * This element is declared by the element type {@code metronome-beam} for the element type {@code metronome-note} in the direction.mod schema file.
         *
         * @see MetronomeNote
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MetronomeBeam
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Number.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code MetronomeDot} classifies the MusicXML element, {@code metronome-dot}.
         * <p/>
         * This element is declared by the element type {@code metronome-dot} for the element type {@code metronome-note} in the direction.mod schema file.
         *
         * @see MetronomeNote
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MetronomeDot
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code MetronomeNote} classifies the MusicXML element, {@code metronome-note}.
         * <p/>
         * This element is declared by the element type {@code metronome-note} for the element type {@code metronome} in the direction.mod schema file.
         *
         * @see Metronome
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MetronomeNote
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                MetronomeType.class,
                MetronomeDot.class,
                MetronomeBeam.class,
                MetronomeTied.class,
                MetronomeTuplet.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OneOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OneOrMore;
            }
        }

        /**
         * {@code MetronomeRelation} classifies the MusicXML element, {@code metronome-relation}.
         * <p/>
         * This element is declared by the element type {@code metronome-relation} for the element type {@code metronome-note} in the direction.mod schema file.
         *
         * @see MetronomeNote
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MetronomeRelation
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code MetronomeTied} classifies the MusicXML element, {@code metronome-tied}.
         * <p/>
         * This element is declared by the element type {@code metronome-tied} for the element type {@code metronome-note} in the direction.mod schema file.
         *
         * @see MetronomeNote
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MetronomeTied
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code MetronomeTuplet} classifies the MusicXML element, {@code metronome-tuplet}.
         * <p/>
         * This element is declared by the element type {@code metronome-tuplet} for the element type {@code metronome-note} in the direction.mod schema file.
         *
         * @see MetronomeNote
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MetronomeTuplet
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Bracket.class,
                Element.Attribute.ShowNumber.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                ActualNotes.class,
                NormalNotes.class,
                NormalType.class,
                NormalDot.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code MetronomeType} classifies the MusicXML element, {@code metronome-type}.
         * <p/>
         * This element is declared by the element type {@code metronome-type} for the element type {@code metronome-note} in the direction.mod schema file.
         *
         * @see MetronomeNote
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MetronomeType
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code MF} classifies the MusicXML element, {@code mf}.
         * <p/>
         * This element is declared by the element type {@code mf} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MF
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Miscellaneous} classifies the MusicXML element, {@code miscellaneous}.
         * <p/>
         * This element is declared by the element type {@code miscellaneous} for the element type {@code identification} in the identify.mod schema file.
         * <p/>
         * If a program has other metadata not yet supported in the MusicXML format, it can go in the miscellaneous area.
         *
         * @see Identification
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Miscellaneous
        extends Element
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Element.MiscellaneousField.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code MiscellaneousField} classifies the MusicXML element, {@code miscellaneous-field}.
         * <p/>
         * This element is declared by the element type {@code miscellaneous-field} for the element type {@code miscellaneous} in the identify.mod schema file.
         *
         * @see Miscellaneous
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MiscellaneousField
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Name.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code MidiBank} classifies the MusicXML element, {@code midi-bank}.
         * <p/>
         * This element is declared by the element type {@code midi-bank} for the element type {@code midi-instrument} in the common.mod schema file.
         * <p/>
         * MIDI 1.0 bank numbers range from 1 to 16,384.
         *
         * @see MidiInstrument
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MidiBank
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code MidiChannel} classifies the MusicXML element, {@code midi-channel}.
         * <p/>
         * This element is declared by the element type {@code midi-channel} for the element type {@code midi-instrument} in the common.mod schema file.
         * <p/>
         * MIDI 1.0 channel numbers range from 1 to 16.
         *
         * @see MidiInstrument
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MidiChannel
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code MidiDevice} classifies the MusicXML element, {@code midi-device}.
         * <p/>
         * This element is declared by the element type {@code midi-device} in the common.mod schema file, for the element type {@code score-part} in the score.mod schema file; and for the element type {@code sound} in the direction.mod schema file.
         * <p/>
         * The {@code midi-device} content corresponds to the DeviceName meta event in Standard MIDI Files.
         * The optional {@link Element.Attribute.Port port} attribute is a number from 1 to 16 that can be used with the unofficial MIDI port (or cable) meta event.
         * Unlike the DeviceName meta event, there can be multiple {@code midi-device} elements per MusicXML part starting in MusicXML 3.0.
         * The optional {@link Element.Attribute.ID id} attribute refers to the {@link ScoreInstrument score-instrument} assigned to this device.
         * If missing, the device assignment affects all {@link ScoreInstrument score-instrument} elements in the {@link ScorePart score-part}.
         *
         * @see ScorePart
         * @see Sound
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MidiDevice
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Port.class,
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code MidiInstrument} classifies the MusicXML element, {@code midi-instrument}.
         * <p/>
         * This element is declared by the element type {@code midi-instrument} in the common.mod schema file, for the element type {@code score-part} in the score.mod schema file; and for the element type {@code sound} in the direction.mod schema file.
         * <p/>
         * The {@code midi-instrument} element can be a part of either the {@link ScoreInstrument score-instrument} element at the start of a {@link Part part}, or the {@link Sound sound} element within a {@link Part part}.
         * The {@link Element.Attribute.ID id} attribute refers to the {@link ScoreInstrument score-instrument} affected by the change.
         *
         * @see ScorePart
         * @see Sound
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MidiInstrument
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                MidiChannel.class,
                MidiName.class,
                MidiBank.class,
                MidiProgram.class,
                MidiUnpitched.class,
                Volume.class,
                Pan.class,
                Elevation.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code MidiName} classifies the MusicXML element, {@code midi-name}.
         * <p/>
         * This element is declared by the element type {@code midi-name} for the element type {@code midi-instrument} in the common.mod schema file.
         * <p/>
         * MIDI names correspond to ProgramName meta-events within a Standard MIDI File.
         *
         * @see MidiInstrument
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MidiName
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code MidiProgram} classifies the MusicXML element, {@code midi-program}.
         * <p/>
         * This element is declared by the element type {@code midi-program} for the element type {@code midi-instrument} in the common.mod schema file.
         * <p/>
         * MIDI 1.0 program numbers range from 1 to 128.
         *
         * @see MidiInstrument
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MidiProgram
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code MidiUnpitched} classifies the MusicXML element, {@code midi-unpitched}.
         * <p/>
         * This element is declared by the element type {@code midi-unpitched} for the element type {@code midi-instrument} in the common.mod schema file.
         * <p/>
         * For unpitched instruments, specify a MIDI 1.0 note number ranging from 1 to 128.
         * It is usually used with MIDI banks for percussion.
         * Note that MIDI 1.0 note numbers are generally specified from 0 to 127 rather than the 1 to 128 numbering used in this element.
         *
         * @see MidiInstrument
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MidiUnpitched
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Millimeters} classifies the MusicXML element, {@code millimeters}.
         * <p/>
         * This element is declared by the element type {@code millimeters} for the element type {@code scaling} in the layout.mod schema file.
         *
         * @see Scaling
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Millimeters
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Mode} classifies the MusicXML element, {@code mode}.
         * <p/>
         * This element is declared by the element type {@code mode} for the element type {@code key} in the attributes.mod schema file.
         *
         * @see Key
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Mode
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                AEOLIAN,
                DORIAN,
                LOCRIAN,
                LYDIAN,
                NONE,
                IONIAN,
                MAJOR,
                MINOR,
                MIXOLYDIAN,
                PHRYGIAN
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Mordent} classifies the MusicXML element, {@code mordent}.
         * <p/>
         * This element is declared by the element type {@code mordent} for the element type {@code ornaments} in the note.mod schema file.
         * <p/>
         * The {@link Element.Attribute.Long long} attribute for the {@code mordent} element is "no" by default.
         * The {@code mordent} element represents the mordent sign with the vertical line.
         * The choice of which mordent is inverted differs between MusicXML and SMuFL.
         * The {@link Element.Attribute.Approach approach} and {@link Element.Attribute.Departure departure} attributes are used for compound ornaments, indicating how the beginning and ending of the ornament look relative to the main part of the mordent.
         *
         * @see Ornaments
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Mordent
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Long.class,
                Element.Attribute.Approach.class,
                Element.Attribute.Departure.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.StartNote.class,
                Element.Attribute.TrillStep.class,
                Element.Attribute.TwoNoteTurn.class,
                Element.Attribute.Accelerate.class,
                Element.Attribute.Beats.class,
                Element.Attribute.SecondBeat.class,
                Element.Attribute.LastBeat.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code MovementNumber} classifies the MusicXML element, {@code movement-number}.
         * <p/>
         * This element is declared by the element type {@code movement-number} for the entity type {@code score-header} in the score.mod schema file.
         * <p/>
         * Movements are optionally identified by number and {@link MovementTitle title}.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MovementNumber
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code MovementTitle} classifies the MusicXML element, {@code movement-title}.
         * <p/>
         * This element is declared by the element type {@code movement-title} for the entity type {@code score-header} in the score.mod schema file.
         * <p/>
         * Movements are optionally identified by {@link MovementNumber number} and title.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MovementTitle
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code MP} classifies the MusicXML element, {@code mp}.
         * <p/>
         * This element is declared by the element type {@code mp} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MP
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code MultipleRest} classifies the MusicXML element, {@code multiple-rest}.
         * <p/>
         * This element is declared by the element type {@code multiple-rest} for the element type {@code measure-style} in the attributes.mod schema file.
         * <p/>
         * The text of the {@code multiple-rest} element indicates the number of measures in the multiple rest.
         * Multiple rests may use the 1-bar / 2-bar / 4-bar rest symbols, or a single shape.
         * The {@link Element.Attribute.UseSymbols use-symbols} attribute indicates which to use; it is no if not specified.
         *
         * @see MeasureStyle
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MultipleRest
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.UseSymbols.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code MusicFont} classifies the MusicXML element, {@code music-font}.
         * <p/>
         * This element is declared by the element type {@code music-font} for the element type {@code defaults} in the score.mod schema file.
         *
         * @see Defaults
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface MusicFont
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                // font
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Mute} classifies the MusicXML element, {@code mute}.
         * <p/>
         * This element is declared by the element type {@code mute} for the element type {@code play} in the common.mod schema file.
         * <p/>
         * The {@code mute} element represents muting for different instruments, including brass, winds, and strings.
         * The on and off values are used for undifferentiated mutes.
         * The remaining values represent specific mutes: straight, cup, harmon-no-stem, harmon-stem, bucket, plunger, hat, solotone, practice, stop-mute, stop-hand, echo, and palm.
         *
         * @see Play
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Mute
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                BUCKET,
                CUP,
                ECHO,
                HARMON_NO_STEM,
                HARMON_STEM,
                HAT,
                OFF,
                ON,
                PALM,
                PLUNGER,
                PRACTICE,
                SOLOTONE,
                STOP_HAND,
                STOP_MUTE,
                STRAIGHT
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code N} classifies the MusicXML element, {@code n}.
         * <p/>
         * This element is declared by the element type {@code n} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface N
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Natural} classifies the MusicXML element, {@code natural}.
         * <p/>
         * This element is declared by the element type {@code natural} for the entity type {@code technical} in the note.mod schema file.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Natural
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code NonArpeggiate} classifies the MusicXML element, {@code non-arpeggiate}.
         * <p/>
         * This element is declared by the element type {@code non-arpeggiate} for the element type {@code notations} in the note.mod schema file.
         * <p/>
         * The {@code non-arpeggiate} element indicates that this note is at the top or bottom of a bracket indicating to not arpeggiate these notes.
         * Since this does not involve playback, it is only used on the top or bottom notes, not on each note as for the arpeggiate element.
         *
         * @see Notations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface NonArpeggiate
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.Placement.class,
                Element.Attribute.Color.class,
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code NormalDot} classifies the MusicXML element, {@code normal-dot}.
         * <p/>
         * This element is declared by the element type {@code normal-dot} in the common.mod schema file, for the element types {@code time-modification} in the note.mod schema file, and {@code metronome-tuplet} in the direction.mode schema file.
         * <p/>
         * This element is used both in the {@link TimeModification time-modification} and {@link MetronomeTuplet metronome-tuplet} elements.
         * If the {@link NormalNotes normal-notes} type is different than the current note type (e.g., a quarter note within an eighth note triplet), then the {@link NormalNotes normal-notes} type (e.g. eighth) is specified in the {@link NormalType normal-type} and {@code normal-dot} elements.
         *
         * @see MetronomeTuplet
         * @see NormalNotes
         * @see NormalType
         * @see TimeModification
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface NormalDot
        extends Analytic
        {
            /** The array of accepted parent element types. */
            Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                TimeModification.class,
                MetronomeTuplet.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code NormalNotes} classifies the MusicXML element, {@code normal-notes}.
         * <p/>
         * This element is declared by the element type {@code normal-notes} in the common.mod schema file, for the element types {@code time-modification} in the note.mod schema file, and {@code metronome-tuplet} in the direction.mode schema file.
         * <p/>
         * This element is used both in the {@link TimeModification time-modification} and {@link MetronomeTuplet metronome-tuplet} elements.
         * The {@link ActualNotes actual-notes} element describes how many notes are played in the time usually occupied by the number of {@code normal-notes}.
         * If the {@code normal-notes} type is different than the current note type (e.g., a quarter note within an eighth note triplet), then the {@code normal-notes} type (e.g. eighth) is specified in the {@link NormalType normal-type} and {@link NormalDot normal-dot} elements.
         * The content of the {@code normal-notes} element is a non-negative integer.
         *
         * @see ActualNotes
         * @see MetronomeTuplet
         * @see NormalDot
         * @see NormalType
         * @see TimeModification
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface NormalNotes
        extends Analytic
        {
            /** The array of accepted parent element types. */
            Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                TimeModification.class,
                MetronomeTuplet.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code NormalType} classifies the MusicXML element, {@code normal-type}.
         * <p/>
         * This element is declared by the element type {@code normal-type} in the common.mod schema file, for the element types {@code time-modification} in the note.mod schema file, and {@code metronome-tuplet} in the direction.mode schema file.
         * <p/>
         * This element is used both in the {@link TimeModification time-modification} and {@link MetronomeTuplet metronome-tuplet} elements.
         * If the {@link NormalNotes normal-notes} type is different than the current note type (e.g., a quarter note within an eighth note triplet), then the {@link NormalNotes normal-notes} type (e.g. eighth) is specified in the {@code normal-type} and {@link NormalDot normal-dot} elements.
         *
         * @see MetronomeTuplet
         * @see NormalDot
         * @see NormalNotes
         * @see TimeModification
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface NormalType
        extends Analytic
        {
            /** The array of accepted parent element types. */
            Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                TimeModification.class,
                MetronomeTuplet.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Notations} classifies the MusicXML element, {@code notations}.
         * <p/>
         * This element is declared by the element type {@code notations} for the element type {@code note} in the note.mod schema file.
         * <p/>
         * Notations are musical notations, not XML notations.
         * Multiple {@code notations} are allowed in order to represent multiple editorial levels.
         * The {@link Element.Attribute.PrintObject print-object} attribute, added in Version 3.0, allows {@code notations} to represent details of performance technique, such as fingerings, without having them appear in the score.
         *
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Notations
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.PrintObject.class,
                Element.Attribute.ID.class
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Tied.class,
                Slur.class,
                Tuplet.class,
                Glissando.class,
                Slide.class,
                Ornaments.class,
                Technical.class,
                Articulations.class,
                Dynamics.class,
                Fermata.class,
                Arpeggiate.class,
                NonArpeggiate.class,
                AccidentalMark.class,
                OtherNotation.class,
                // editorial
                Element.Footnote.class,
                Element.Level.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Note} classifies the MusicXML element, {@code note}.
         * <p/>
         * This element is declared by the element type {@code note} in the note.mod schema file for the entity type {@code music-data} in the score.mod schema file.
         * <p/>
         * Notes are the most common type of MusicXML data.
         * The MusicXML format keeps the MuseData distinction between elements used for sound information and elements used for notation information (e.g., {@link Tie tie} is used for {@link Sound sound}, {@link Tied tied} for {@link Notations notations}).
         * Thus grace notes do not have a {@link Duration duration} element.
         * Cue notes have a {@link Duration duration} element, as do {@link Forward forward} elements, but no {@link Tie tie} elements.
         * Having these two types of information available can make interchange considerably easier, as some programs handle one type of information much more readily than the other.
         * <p/>
         * The {@code position} and {@code printout} entities for printing suggestions are defined in the common.mod file.
         * <p/>
         * The {@link Element.Attribute.PrintLeger print-leger} attribute is used to indicate whether leger lines are printed.
         * Notes without leger lines are used to indicate indeterminate high and low notes.
         * By default, it is set to yes.
         * If {@link Element.Attribute.PrintObject print-object} is set to no, {@link Element.Attribute.PrintLeger print-leger} is interpreted to also be set to no if not present.
         * This attribute is ignored for rests.
         * <p/>
         * The {@link Element.Attribute.Dynamics dynamics} and {@link Element.Attribute.EndDynamics end-dynamics} attributes correspond to MIDI 1.0's Note On and Note Off velocities, respectively.
         * They are expressed in terms of percentages of the default forte value (90 for MIDI 1.0).
         * <p/>
         * The {@link Element.Attribute.Attack attack} and {@link Element.Attribute.Release release} attributes are used to alter the starting and stopping time of the note from when it would otherwise occur based on the flow of durations - information that is specific to a performance.
         * They are expressed in terms of divisions, either positive or negative.
         * A note that starts a tie should not have a {@link Element.Attribute.Release release} attribute, and a note that stops a tie should not have an {@link Element.Attribute.Attack attack} attribute.
         * The {@link Element.Attribute.Attack attack} and {@link Element.Attribute.Release release} attributes are independent of each other.
         * The {@link Element.Attribute.Attack attack} attribute only changes the starting time of a note, and the {@link Element.Attribute.Release release} attribute only changes the stopping time of a note.
         * <p/>
         * If a note is played only particular times through a repeat, the {@code time-only} entity shows which times to play the note.
         * <p/>
         * The {@link Element.Attribute.Pizzicato pizzicato} attribute is used when just this note is sounded pizzicato, vs. the {@link Element.Attribute.Pizzicato pizzicato} [in the {@link Sound sound}] element which changes overall playback between pizzicato and arco.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Note
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.PrintLeger.class,
                Element.Attribute.Dynamics.class,
                Element.Attribute.EndDynamics.class,
                Element.Attribute.Attack.class,
                Element.Attribute.Release.class,
                Element.Attribute.Pizzicato.class,
                Element.Attribute.ID.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.PrintObject.class,
                Element.Attribute.PrintDot.class,
                Element.Attribute.PrintSpacing.class,
                Element.Attribute.PrintLyric.class,
                Element.Attribute.TimeOnly.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Grace.class,
                Tie.class,
                Cue.class,
                Duration.class,
                Instrument.class,
                Type.class,
                Dot.class,
                Accidental.class,
                TimeModification.class,
                Stem.class,
                Notehead.class,
                NoteheadText.class,
                Staff.class,
                Beam.class,
                Notations.class,
                Lyric.class,
                Play.class,
                // full-note
                Chord.class,
                Pitch.class,
                Unpitched.class,
                Rest.class,
                // editorial-voice
                Footnote.class,
                Level.class,
                Voice.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Notehead} classifies the MusicXML element, {@code notehead}.
         * <p/>
         * This element is declared by the element type {@code notehead} for the element type {@code note} in the note.mod schema file.
         * <p/>
         * The {@code notehead} element indicates shapes other than the open and closed ovals associated with note durations.
         * The element value can be slash, triangle, diamond, square, cross, x, circle-x, inverted triangle, arrow down, arrow up, circled, slashed, back slashed, normal, cluster, circle dot, left triangle, rectangle, other, or none.
         * <p/>
         * For shape note music, the element values do, re, mi, fa, fa up, so, la, and ti are also used, corresponding to Aikin's 7-shape system.
         * The fa up shape is typically used with upstems; the fa shape is typically used with downstems or no stems.
         * <p/>
         * The arrow shapes differ from triangle and inverted triangle by being centered on the stem.
         * Slashed and back slashed notes include both the normal notehead and a slash.
         * The triangle shape has the tip of the triangle pointing up; the inverted triangle shape has the tip of the triangle pointing down.
         * The left triangle shape is a right triangle with the hypotenuse facing up and to the left.
         * <p/>
         * The other {@code notehead} covers noteheads other than those listed here.
         * It is usually used in combination with the {@link Element.Attribute.SMuFL smufl} attribute to specify a particular SMuFL notehead.
         * The {@link Element.Attribute.SMuFL smufl} attribute may be used with any {@code notehead} value to help specify the appearance of symbols that share the same MusicXML semantics.
         * Its value is a SMuFL canonical glyph name.
         * Noteheads in the SMuFL "Note name noteheads" range (U+E150–U+E1AF) should not use the {@link Element.Attribute.SMuFL smufl} attribute or the "other" value, but instead use the {@link NoteheadText notehead-text} element.
         * <p/>
         * For the enclosed shapes, the default is to be hollow for half notes and longer, and filled otherwise.
         * The {@link Element.Attribute.Filled filled} attribute can be set to change this if needed.
         * <p/>
         * If the {@link Element.Attribute.Parentheses parentheses} attribute is set to yes, the notehead is parenthesized.
         * It is no by default.
         *
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Notehead
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Filled.class,
                Element.Attribute.Parentheses.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.SMuFL.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Lambda.combineArrays(
                java.lang.String[].class,
                new java.lang.String[] {
                    ARROW__DOWN,
                    ARROW__UP,
                    BACK__SLASHED,
                    CIRCLED,
                    CIRCLE_X,
                    CIRCLE__DOT,
                    CLUSTER,
                    CROSS,
                    DIAMOND,
                    DO,
                    FA,
                    FA__UP,
                    INVERTED__TRIANGLE,
                    LA,
                    LEFT__TRIANGLE,
                    MI,
                    NONE,
                    NORMAL,
                    OTHER,
                    RE,
                    RECTANGLE,
                    SLASH,
                    SLASHED,
                    SO,
                    SQUARE,
                    TI,
                    TRIANGLE,
                    X
                },
                Entity.PCDATA_Literal);

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code NoteheadText} classifies the MusicXML element, {@code notehead-text}.
         * <p/>
         * This element is declared by the element type {@code notehead-text} in the note.mod schema file.
         * <p/>
         * The {@code notehead-text} element indicates text that is displayed inside a notehead, as is done in some educational music.
         * It is not needed for the numbers used in tablature or jianpu notation.
         * The presence of a TAB or jianpu clefs is sufficient to indicate that numbers are used.
         * The {@link DisplayText display-text} and {@link AccidentalText accidental-text} elements allow display of fully formatted text and accidentals.
         *
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface NoteheadText
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                DisplayText.class,
                AccidentalText.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code NoteSize} classifies the MusicXML element, {@code note-size}.
         * <p/>
         * This element is declared by the element type {@code note-size} for the element type {@code appearance} in the layout.mod schema file.
         *
         * @see Appearance
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface NoteSize
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
            };

            /** The element definition. */
            java.lang.String Definition = null;

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Octave} classifies the MusicXML element, {@code octave}.
         * <p/>
         * This element is declared by the element type {@code octave} for the element type {@code pitch} in the note.mod schema file.
         * <p/>
         * The {@code octave} element is represented by the numbers 0 to 9, where 4 indicates the octave started by middle C.
         *
         * @see Pitch
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Octave
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code OctaveChange} classifies the MusicXML element, {@code octave-change}.
         * <p/>
         * This element is declared by the element type {@code octave-change} for the element type {@code transpose} in the attributes.mod schema file.
         *
         * @see Transpose
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface OctaveChange
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code OctaveShift} classifies the MusicXML element, {@code octave-shift}.
         * <p/>
         * This element is declared by the element type {@code octave-shift} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * Octave shifts indicate where notes are shifted up or down from their true pitched values because of printing difficulty.
         * Thus a treble clef line noted with 8va will be indicated with an {@code octave-shift} down from the {@link Pitch pitch} data indicated in the {@link Note notes}.
         * A {@link Element.Attribute.Size size} of 8 indicates one octave; a {@link Element.Attribute.Size size} of 15 indicates two octaves.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface OctaveShift
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
                Element.Attribute.Size.class,
                Element.Attribute.DashLength.class,
                Element.Attribute.SpaceLength.class,
                Element.Attribute.ID.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
             * {@inheritDoc}
             *
             * @return {@link Occurrence#OnlyOne}.
             */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Offset} classifies the MusicXML element, {@code offset}.
         * <p/>
         * This element is declared by the element type {@code offset} for the element type {@code direction} in the direction.mod schema file.
         * <p/>
         * An {@code offset} is represented in terms of divisions, and indicates where the direction will appear relative to the current musical location.
         * This affects the visual appearance of the direction.
         * If the {@link Element.Type.Sound sound} attribute is "yes", then the offset affects playback too.
         * If the {@link Element.Type.Sound sound} attribute is "no", then any sound associated with the {@link Direction direction} takes effect at the current location.
         * The {@link Element.Type.Sound sound} attribute is "no" by default for compatibility with earlier versions of the MusicXML format.
         * If an element within a {@link Direction direction} includes a {@link Element.Attribute.DefaultX default-x} attribute, the {@code offset} value will be ignored when determining the appearance of that element.
         *
         * @see Direction
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Offset
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Sound.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Open} classifies the MusicXML element, {@code open}.
         * <p/>
         * This element is declared by the element type {@code open} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code open} element represents the open symbol, which looks like a circle.
         * The {@link Element.Attribute.SMuFL smufl} attribute can be used to distinguish different SMuFL glyphs that have a similar appearance such as brassMuteOpen and guitarOpenPedal.
         * If not present, the default glyph is brassMuteOpen.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Open
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.SMuFL.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code OpenString} classifies the MusicXML element, {@code open-string}.
         * <p/>
         * This element is declared by the element type {@code open-string} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code open-string} element represents the zero-shaped open string symbol.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface OpenString
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Opus} classifies the MusicXML element, {@code opus}.
         * <p/>
         * This element is declared by the element type {@code opus} for the element type {@code work} in the score.mod schema file.
         *
         * @see Work
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Opus
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                // document-attributes
                Element.Attribute.Version.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Ornaments} classifies the MusicXML element, {@code ornaments}.
         * <p/>
         * This element is declared by the element type {@code ornaments} for the element type {@code notations} in the note.mod schema file.
         * <p/>
         * Ornaments can be any of several types, followed optionally by accidentals.
         * The {@link AccidentalMark accidental-mark} element's content is represented the same as an {@link Accidental accidental} element, but with a different name to reflect the different musical meaning.
         *
         * @see Notations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Ornaments
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.ID.class
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                TrillMark.class,
                Turn.class,
                DelayedTurn.class,
                InvertedTurn.class,
                DelayedInvertedTurn.class,
                VerticalTurn.class,
                InvertedVerticalTurn.class,
                Shake.class,
                WavyLine.class,
                Mordent.class,
                InvertedMordent.class,
                Schleifer.class,
                Tremolo.class,
                Haydn.class,
                OtherOrnament.class,
                AccidentalMark.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code OtherAppearance} classifies the MusicXML element, {@code other-appearance}.
         * <p/>
         * This element is declared by the element type {@code other-appearance} for the element type {@code appearance} in the layout.mod schema file.
         *
         * @see Appearance
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface OtherAppearance
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
            };

            /** The element definition. */
            java.lang.String Definition = null;

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code OtherArticulation} classifies the MusicXML element, {@code other-articulation}.
         * <p/>
         * This element is declared by the element type {@code other-articulation} for the element type {@code articulations} in the note.mod schema file.
         * <p/>
         * The {@code other-articulation} element is used to define any articulations not yet in the MusicXML format.
         * The {@link Element.Attribute.SMuFL smufl} attribute can be used to specify a particular articulation, allowing application interoperability without requiring every SMuFL articulation to have a MusicXML element equivalent.
         * Using the {@code other-articulation} element without the {@link Element.Attribute.SMuFL smufl} attribute allows for extended representation, though without application interoperability.
         *
         * @see Articulations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface OtherArticulation
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.SMuFL.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code OtherDirection} classifies the MusicXML element, {@code other-direction}.
         * <p/>
         * This element is declared by the element type {@code other-direction} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * The {@code other-direction} element is used to define any direction symbols not yet in the MusicXML format.
         * The {@link Element.Attribute.SMuFL smufl} attribute can be used to specify a particular direction symbol, allowing application interoperability without requiring every SMuFL glyph to have a MusicXML element equivalent.
         * Using the {@code other-direction} type without the {@link Element.Attribute.SMuFL smufl} attribute allows for extended representation, though without application interoperability.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface OtherDirection
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.PrintObject.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.SMuFL.class,
                Element.Attribute.ID.class,
                Element.Attribute.ID.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code OtherDynamics} classifies the MusicXML element, {@code other-dynamics}.
         * <p/>
         * This element is declared by the element type {@code other-dynamics} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface OtherDynamics
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.SMuFL.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code OtherNotation} classifies the MusicXML element, {@code other-notation}.
         * <p/>
         * This element is declared by the element type {@code other-notation} for the element type {@code notations} in the note.mod schema file.
         * <p/>
         * The {@code other-notation} element is used to define any notations not yet in the MusicXML format.
         * It handles notations where more specific extension elements such as {@link OtherDynamics other-dynamics} and {@link OtherTechnical other-technical} are not appropriate.
         * The {@link Element.Attribute.SMuFL smufl} attribute can be used to specify a particular notation, allowing application interoperability without requiring every SMuFL glyph to have a MusicXML element equivalent.
         * Using the {@code other-notation} element without the {@link Element.Attribute.SMuFL smufl} attribute allows for extended representation, though without application interoperability.
         *
         * @see Notations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface OtherNotation
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
                Element.Attribute.ID.class,
                Element.Attribute.PrintObject.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.SMuFL.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code OtherOrnament} classifies the MusicXML element, {@code other-ornament}.
         * <p/>
         * This element is declared by the element type {@code other-ornament} for the element type {@code ornaments} in the note.mod schema file.
         * <p/>
         * The {@code other-ornament} element is used to define any ornaments not yet in the MusicXML format.
         * The {@link Element.Attribute.SMuFL smufl} attribute can be used to specify a particular ornament, allowing application interoperability without requiring every SMuFL ornament to have a MusicXML element equivalent.
         * Using the {@code other-ornament} element without the {@link Element.Attribute.SMuFL smufl} attribute allows for extended representation, though without application interoperability.
         *
         * @see Ornaments
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface OtherOrnament
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.SMuFL.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code OtherPercussion} classifies the MusicXML element, {@code other-percussion}.
         * <p/>
         * This element is declared by the element type {@code other-percussion} for the element type {@code percussion} in the direction.mod schema file.
         * <p/>
         * The other-percussion element represents percussion pictograms not defined elsewhere.
         *
         * @see Percussion
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface OtherPercussion
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.SMuFL.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code OtherPlay} classifies the MusicXML element, {@code other-play}.
         * <p/>
         * This element is declared by the element type {@code other-play} for the element type {@code play} in the common.mod schema file.
         *
         * @see Play
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface OtherPlay
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code OtherTechnical} classifies the MusicXML element, {@code other-technical}.
         * <p/>
         * This element is declared by the element type {@code other-technical} for the element type {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code other-technical} element is used to define any technical indications not yet in the MusicXML format.
         * The {@link Element.Attribute.SMuFL smufl} attribute can be used to specify a particular glyph, allowing application interoperability without requiring every SMuFL technical indication to have a MusicXML element equivalent.
         * Using the {@code other-technical} element without the {@link Element.Attribute.SMuFL smufl} attribute allows for extended representation, though without application interoperability.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface OtherTechnical
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.SMuFL.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code P} classifies the MusicXML element, {@code p}.
         * <p/>
         * This element is declared by the element type {@code p} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface P
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code PageHeight} classifies the MusicXML element, {@code page-height}.
         * <p/>
         * This element is declared by the element type {@code page-height} for the element type {@code page-layout} in the layout.mod schema file.
         *
         * @see PageLayout
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PageHeight
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.LayoutTenths_Values;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code PageLayout} classifies the MusicXML element, {@code page-layout}.
         * <p/>
         * This element is declared by the element type {@code page-layout} in the layout.mod schema file for the element type {@code defaults} in the score.mod schema file.
         * <p/>
         * Page layout can be defined both in score-wide {@link Defaults defaults} and in the {@link Print print} element.
         * Page margins are specified either for both even and odd pages, or via separate odd and even page number values.
         * The {@link Element.Attribute.Type type} is not needed when used as part of a {@link Print print} element.
         * If omitted when used in the {@link Defaults defaults} element, "both" is the default.
         *
         * @see Defaults
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PageLayout
        extends Element
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                PageHeight.class,
                PageWidth.class,
                PageMargins.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code PageMargins} classifies the MusicXML element, {@code page-margins}.
         * <p/>
         * This element is declared by the element type {@code page-margins} for the element type {@code page-layout} in the layout.mod schema file.
         *
         * @see PageLayout
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PageMargins
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                LeftMargin.class,
                RightMargin.class,
                TopMargin.class,
                BottomMargin.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code PageWidth} classifies the MusicXML element, {@code page-width}.
         * <p/>
         * This element is declared by the element type {@code page-width} for the element type {@code page-layout} in the layout.mod schema file.
         *
         * @see PageLayout
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PageWidth
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.LayoutTenths_Values;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Pan} classifies the MusicXML element, {@code pan}.
         * <p/>
         * This element is declared by the element type {@code pan} for the element type {@code midi-instrument} in the common.mod schema file.
         * <p/>
         * Pan allows placing of sound in a 3-D space relative to the listener.
         * It is expressed in degrees ranging from -180 to 180.
         * For {@code pan}, 0 is straight ahead, -90 is hard left, 90 is hard right, and -180 and 180 are directly behind the listener.
         *
         * @see MidiInstrument
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Pan
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Part} classifies the MusicXML element, {@code part}.
         * <p/>
         * This element is declared by the element type {@code part} for the element types {@code score-partwise} and {@code score-timewise} in the score.mod schema file.
         * <p/>
         * In either format, the {@code part} element has an {@link Element.Attribute.ID id} attribute that is an IDREF back to a {@link ScorePart score-part} in the {@link PartList part-list}.
         * {@link Measure Measures} have a required {@link Element.Attribute.Number number} attribute (going from partwise to timewise, measures are grouped via the number).
         *
         * @see ScorePartwise
         * @see ScoreTimewise
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Part
        extends
            Analytic,
            XML.Schematic.PerType
        {
            /** The array of accepted parent element types. */
            Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                ScorePartwise.class,
                ScoreTimewise.class,
            };

            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.ID.class,
            };

            @Override
            default Class<? extends Part> per(final Class<?> type) {
                if (type == ScorePartwise.class)
                    return Part_PerScorePartwise.class;
                else
                if (type == ScoreTimewise.class)
                    return Part_PerScoreTimewise.class;

                return null;
            }

            interface Part_PerScorePartwise
            extends Part
            {
                Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                    Measure.class,
                };
            }

            interface Part_PerScoreTimewise
            extends Part
            {
                Class<? extends Element>[] Elements = Measure.Measure_PerScoreTimewise.Elements;
            }
        }

        /**
         * {@code PartAbbreviation} classifies the MusicXML element, {@code part-abbreviation}.
         * <p/>
         * This element is declared by the element type {@code part-abbreviation} for the element type {@code score-part} in the score.mod schema file.
         * <p/>
         * The {@code part-abbreviation} indicates the abbreviated version of the name of the musical part.
         * The {@code part-abbreviation} will precede the other systems.
         * The formatting attributes for this element is deprecated in Version 2.0 in favor of the new {@link PartAbbreviationDisplay part-abbreviation-display} element.
         *
         * @see ScorePart
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PartAbbreviation
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.PrintObject.class,
                Element.Attribute.Justify.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code PartAbbreviationDisplay} classifies the MusicXML element, {@code part-abbreviation-display}.
         * <p/>
         * This element is declared by the element type {@code part-abbreviation-display} in the common.mod schema file; and for the element type {@code print} in the direction.mod schema file.
         * <p/>
         * Formatting specified in the {@code part-abbreviation-display} element overrides the formatting specified in the {@link PartAbbreviation part-abbreviation} element.
         *
         * @see Print
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PartAbbreviationDisplay
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.PrintObject.class
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                DisplayText.class,
                AccidentalText.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code PartGroup} classifies the MusicXML element, {@code part-group}.
         * <p/>
         * This element is declared by the element type {@code part-group} for the element type {@code part-list} in the score.mod schema file.
         * <p/>
         * The part-group element indicates groupings of parts in the score, usually indicated by braces and brackets.
         * Braces that are used for multi-staff parts should be defined in the attributes element for that part.
         * The part-group start element appears before the first score-part in the group.
         * The part-group stop element appears after the last score-part in the group.
         * </p>
         * The number attribute is used to distinguish overlapping and nested part-groups, not the sequence of groups.
         * As with parts, groups can have a name and abbreviation.
         * Formatting attributes for group-name and group-abbreviation are deprecated in Version 2.0 in favor of the new group-name-display and group-abbreviation-display elements.
         * Formatting specified in the group-name-display and group-abbreviation-display elements overrides formatting specified in the group-name and group-abbreviation elements, respectively.
         * <p/>
         * The group-symbol element indicates how the symbol for a group is indicated in the score.
         * Values include none, brace, line, bracket, and square; the default is none.
         * The group-barline element indicates if the group should have common barlines.
         * Values can be yes, no, or Mensurstrich.
         * The group-time element indicates that the displayed time signatures should stretch across all parts and staves in the group.
         * Values for the child elements are ignored at the stop of a group.
         * <p/>
         * A part-group element is not needed for a single multi-staff part.
         * By default, multi-staff parts include a brace symbol and (if appropriate given the bar-style) common barlines.
         * The symbol formatting for a multi-staff part can be more fully specified using the part-symbol element, defined in the attributes.mod file.
         *
         * @see PartList
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PartGroup
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                GroupName.class,
                GroupNameDisplay.class,
                GroupAbbreviation.class,
                GroupAbbreviationDisplay.class,
                GroupSymbol.class,
                GroupBarline.class,
                GroupTime.class,
                // editorial
                Element.Footnote.class,
                Element.Level.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code PartList} classifies the MusicXML element, {@code part-list}.
         * <p/>
         * This element is declared by the element type {@code part-list} for the entity type {@code score-header} in the score.mod schema file.
         * </p>
         * The {@code part-list} identifies the different musical parts in this movement.
         * Each {@link Part part} has an ID that is used later within the musical data.
         * Since parts may be encoded separately and combined later, {@link Identification identification} elements are present at both the score and {@link ScorePart score-part} levels.
         * There must be at least one {@link ScorePart score-part}, combined as desired with {@link PartGroup part-group} elements that indicate braces and brackets.
         * {@link Part Parts} are ordered from top to bottom in a score based on the order in which they appear in the {@code part-list}.
         *
         * @see ScorePartwise
         * @see ScoreTimewise
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PartList
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                PartGroup.class,
                ScorePart.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code PartName} classifies the MusicXML element, {@code part-name}.
         * <p/>
         * This element is declared by the element type {@code part-name} for the element type {@code score-part} in the score.mod schema file.
         * <p/>
         * The {@code part-name} indicates the full name of the musical part.
         * The {@code part-name} will often precede the first system.
         * The formatting attributes for this element is deprecated in Version 2.0 in favor of the new {@link PartNameDisplay part-name-display} element.
         *
         * @see ScorePart
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PartName
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.PrintObject.class,
                Element.Attribute.Justify.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code PartNameDisplay} classifies the MusicXML element, {@code part-name-display}.
         * <p/>
         * This element is declared by the element type {@code part-name-display} in the common.mod schema file; and for the element type {@code print} in the direction.mod schema file.
         * <p/>
         * Formatting specified in the {@code part-abbreviation-display} element overrides the formatting specified in the {@link PartName part-name} element.
         *
         * @see Print
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PartNameDisplay
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.PrintObject.class
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                DisplayText.class,
                AccidentalText.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;
        }

        /**
         * {@code PartSymbol} classifies the MusicXML element, {@code part-symbol}.
         * <p/>
         * This element is declared by the element type {@code part-symbol} for the element type {@code attributes} in the attributes.mod schema file.
         * <p/>
         * The {@link PartSymbol part-symbol} element indicates how a symbol for a multi-staff part is indicated in the score.
         * Values include none, brace, line, bracket, and square; brace is the default.
         * The {@link Element.Attribute.TopStaff top-staff} and {@link Element.Attribute.BottomStaff bottom-staff} attributes are used when the brace does not extend across the entire part.
         * For example, in a 3-staff organ part, the {@link Element.Attribute.TopStaff top-staff} will typically be 1 for the right hand, while the {@link Element.Attribute.BottomStaff bottom-staff} will typically be 2 for the left hand.
         * Staff 3 for the pedals is usually outside the brace.
         * By default, the presence of a {@link PartSymbol part-symbol} element that does not extend across the entire part also indicates a corresponding change in the common barlines within a part.
         *
         * @see Attributes
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PartSymbol
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.TopStaff.class,
                Element.Attribute.BottomStaff.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeY.class,
                Element.Attribute.Color.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                BRACE,
                BRACKET,
                LINE,
                NONE,
                SQUARE
            };

            /**
             * Returns {@link Constant.MusicXML#BRACE}.
             *
             * @return {@link Constant.MusicXML#BRACE}.
             */
            @Override
            default java.lang.String defaultValue() {
                return BRACE;
            }

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Pedal} classifies the MusicXML element, {@code pedal}.
         * <p/>
         * This element is declared by the element type {@code pedal} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * The {@code pedal} element represents piano pedal marks.
         * In MusicXML 3.1 this includes sostenuto as well as damper pedal marks.
         * The start {@link Element.Attribute.Type type} indicates the start of a damper pedal, while the sostenuto {@link Element.Attribute.Type type} indicates the start of a sostenuto pedal.
         * The change, continue, and stop {@link Element.Attribute.Type types} can be used with either the damper or sostenuto pedal.
         * The soft pedal is not included here because there is no special symbol or graphic used for it beyond what can be specified with {@link Words words} and {@link Bracket bracket} elements.
         * <p/>
         * The {@link Element.Attribute.Line line} attribute is yes if pedal lines are used.
         * The {@link Element.Attribute.Sign sign} attribute is yes if Ped, Sost, and * signs are used.
         * For MusicXML 2.0 compatibility, the {@link Element.Attribute.Sign sign} attribute is yes by default if the {@link Element.Attribute.Line line} attribute is no, and is no by default if the {@link Element.Attribute.Line line} attribute is yes.
         * If the {@link Element.Attribute.Sign sign} attribute is set to yes and the {@link Element.Attribute.Type type} is start or sostenuto, the {@link Element.Attribute.Abbreviated abbreviated} attribute is yes if the short P and S signs are used, and no if the full Ped and Sost signs are used.
         * It is no by default.
         * Otherwise the {@link Element.Attribute.Abbreviated abbreviated} attribute is ignored.
         * <p/>
         * The change and continue {@link Element.Attribute.Type types} are used when the {@link Element.Attribute.Line line} attribute is yes.
         * The change {@link Element.Attribute.Type type} indicates a pedal lift and retake indicated with an inverted V marking.
         * The continue {@link Element.Attribute.Type type} allows more precise formatting across system breaks and for more complex pedaling lines.
         * The alignment attributes are ignored if the {@link Element.Attribute.Line line} attribute is yes.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Pedal
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
                Element.Attribute.Line.class,
                Element.Attribute.Sign.class,
                Element.Attribute.Abbreviated.class,
                Element.Attribute.ID.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code PedalAlter} classifies the MusicXML element, {@code pedal-alter}.
         * <p/>
         * This element is declared by the element type {@code pedal-alter} for the element type {@code pedal-tuning} in the direction.mod schema file.
         *
         * @see PedalTuning
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PedalAlter
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code PedalStep} classifies the MusicXML element, {@code pedal-step}.
         * <p/>
         * This element is declared by the element type {@code pedal-step} for the element type {@code pedal-tuning} in the direction.mod schema file.
         *
         * @see PedalTuning
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PedalStep
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code PedalTuning} classifies the MusicXML element, {@code pedal-tuning}.
         * <p/>
         * This element is declared by the element type {@code pedal-tuning} for the element type {@code harp-pedals} in the direction.mod schema file.
         *
         * @see HarpPedals
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PedalTuning
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                PedalStep.class,
                PedalAlter.class,
            };

            /**
             * {@inheritDoc}
             *
             * @return {@link Occurrence#OneOrMore}.
             */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OneOrMore;
            }
        }

        /**
         * {@code Percussion} classifies the MusicXML element, {@code percussion}.
         * <p/>
         * This element is declared by the element type {@code percussion} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * The {@code percussion} element is used to define percussion pictogram symbols.
         * Definitions for these symbols can be found in Kurt Stone's "Music Notation in the Twentieth Century" on pages 206-212 and 223.
         * Some values are added to these based on how usage has evolved in the 30 years since Stone's book was published.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Percussion
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.Enclosure.class,
                Element.Attribute.ID.class
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Glass.class,
                Metal.class,
                Wood.class,
                Pitched.class,
                Membrane.class,
                Effect.class,
                Timpani.class,
                Beater.class,
                Stick.class,
                StickLocation.class,
                OtherPercussion.class,
            };

            /**
             * {@inheritDoc}
             *
             * @return {@link Occurrence#OneOrMore}.
             */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OneOrMore;
            }
        }

        /**
         * {@code PerMinute} classifies the MusicXML element, {@code per-minute}.
         * <p/>
         * This element is declared by the element type {@code per-minute} for the element type {@code metronome} in the direction.mod schema file.
         *
         * @see Metronome
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PerMinute
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                // font
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code PF} classifies the MusicXML element, {@code pf}.
         * <p/>
         * This element is declared by the element type {@code pf} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PF
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Pitch} classifies the MusicXML element, {@code pitch}.
         * <p/>
         * This element is declared by the element type {@code pitch} for the entity type {@code full-note} in the note.mod schema file.
         * <p/>
         * Pitch is represented as a combination of the step of the diatonic scale, the chromatic alteration, and the octave.
         *
         * @see Alter
         * @see Note
         * @see Octave
         * @see Step
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Pitch
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Step.class,
                Alter.class,
                Octave.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Pitched} classifies the MusicXML element, {@code pitched}.
         * <p/>
         * This element is declared by the element type {@code pitched} for the element type {@code percussion} in the direction.mod schema file.
         * <p/>
         * The {@code pitched} element represents pictograms for pitched percussion instruments.
         * Valid values are celesta, chimes, glockenspiel, lithophone, mallet, marimba, steel drums, tubaphone, tubular chimes, vibraphone, and xylophone.
         * The chimes and tubular chimes values distinguish the single-line and double-line versions of the pictogram.
         * The {@link Element.Attribute.SMuFL smufl} attribute is used to distinguish different SMuFL glyphs for a particular pictogram within the tuned mallet percussion pictograms range.
         *
         * @see Percussion
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Pitched
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.SMuFL.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                CELESTA,
                CHIMES,
                GLOCKENSPIEL,
                LITHOPHONE,
                MALLET,
                MARIMBA,
                STEEL__DRUMS,
                TUBAPHONE,
                TUBULAR__CHIMES,
                VIBRAPHONE,
                XYLOPHONE
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Play} classifies the MusicXML element, {@code play}.
         * <p/>
         * This element is declared by the element type {@code play} in the common.mod schema file, for the element type {@code note} in the note.mod schema file; and for the element type {@code sound} in the direction.mod schema file.
         * <p/>
         * The {@code play} element, new in Version 3.0, specifies playback techniques to be used in conjunction with the {@link InstrumentSound instrument-sound} element.
         * When used as part of a {@link Sound sound} element, it applies to all notes going forward in score order.
         * In multi-instrument parts, the affected instrument should be specified using the {@link Element.Attribute.ID id} attribute.
         * When used as part of a {@link Note note} element, it applies to the current note only.
         *
         * @see Note
         * @see Sound
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Play
        extends
            Analytic,
            XML.Schematic.PerType
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                IPA.class,
                Mute.class,
                SemiPitched.class,
                OtherPlay.class,
            };

            /** The array of accepted parent elements. */
            Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                Note.class,
                Sound.class,
            };

            @Override
            default Class<? extends Play> per(final Class<?> type) {
                if (type == Note.class)
                    return Play_PerNote.class;
                else
                if (type == Sound.class)
                    return Play_PerSound.class;

                return null;
            }

            interface Play_PerNote
            extends Play
            {
                @Override
                default java.lang.String occurrence() {
                    return Occurrence.ZeroOrOne;
                }
            }

            interface Play_PerSound
            extends Play
            {
                @Override
                default java.lang.String occurrence() {
                    return Occurrence.ZeroOrMore;
                }
            }
        }

        /**
         * {@code Plop} classifies the MusicXML element, {@code plop}.
         * <p/>
         * This element is declared by the element type {@code plop} for the element type {@code articulations} in the note.mod schema file.
         * <p/>
         * The {@code plop} element is an indeterminate slide attached to a single note.
         * Plops come before the main note, coming from above the pitch.
         *
         * @see Articulations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Plop
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.LineShape.class,
                Element.Attribute.LineType.class,
                Element.Attribute.LineLength.class,
                Element.Attribute.DashLength.class,
                Element.Attribute.SpaceLength.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Pluck} classifies the MusicXML element, {@code pluck}.
         * <p/>
         * This element is declared by the element type {@code pluck} for the element type {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code pluck} element is used to specify the plucking fingering on a fretted instrument, where the {@link Fingering fingering} element refers to the fretting fingering.
         * Typical values are p, i, m, a for pulgar/thumb, indicio/index, medio/middle, and anular/ring fingers.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Pluck
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                A,
                C,
                I,
                M,
                P
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code PP} classifies the MusicXML element, {@code pp}.
         * <p/>
         * This element is declared by the element type {@code pp} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PP
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code PPP} classifies the MusicXML element, {@code ppp}.
         * <p/>
         * This element is declared by the element type {@code ppp} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PPP
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code PPPP} classifies the MusicXML element, {@code pppp}.
         * <p/>
         * This element is declared by the element type {@code pppp} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PPPP
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code PPPPP} classifies the MusicXML element, {@code ppppp}.
         * <p/>
         * This element is declared by the element type {@code ppppp} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PPPPP
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code PPPPPP} classifies the MusicXML element, {@code pppppp}.
         * <p/>
         * This element is declared by the element type {@code pppppp} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PPPPPP
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code PreBend} classifies the MusicXML element, {@code pre-bend}.
         * <p/>
         * This element is declared by the element type {@code pre-bend} for the element type {@code bend} in the note.mod schema file.
         *
         * @see Bend
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PreBend
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Prefix} classifies the MusicXML element, {@code prefix}.
         * <p/>
         * This element is declared by the element type {@code prefix} for the element types {@code figure} in the note.mod schema file.
         *
         * @see Figure
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Prefix
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Lambda.combineArrays(
                java.lang.String[].class,
                new java.lang.String[] {
                    PLUS,
                },
                Entity.Accidental_Values);

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code PrincipalVoice} classifies the MusicXML element, {@code principal-voice}.
         * <p/>
         * This element is declared by the element type {@code principal-voice} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * The principal-voice element represents principal and secondary voices in a score, either for analysis or for square bracket symbols that appear in a score.
         * The symbol attribute indicates the type of symbol used at the start of the principal-voice.
         * Valid values are Hauptstimme, Nebenstimme, plain (for a plain square bracket), and none.
         * The content of the principal-voice element is used for analysis and may be any text value.
         * When used for analysis separate from any printed score markings, the symbol attribute should be set to "none".
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PrincipalVoice
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Symbol.class,
                Element.Attribute.ID.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Print} classifies the MusicXML element, {@code print}.
         * <p/>
         * This element is declared by the element type {@code print} in the direction.mod schema file.
         * <p/>
         * The {@code print} element contains general printing parameters, including the layout elements defined in the layout.mod file.
         * The {@link PartNameDisplay part-name-display} and {@link PartAbbreviationDisplay part-abbreviation-display} elements used in the score.mod file may also be used here to change how a {@link Part part} name or abbreviation is displayed over the course of a piece.
         * They take effect when the current {@link Measure measure} or a succeeding {@link Measure measure} starts a new system.
         * <p/>
         * The {@link Element.Attribute.NewSystem new-system} and {@link Element.Attribute.NewPage new-page} attributes indicate whether to force a system or page break, or to force the current music onto the same system or page as the preceding music.
         * Normally this is the first music data within a {@link Measure measure}.
         * If used in multi-part music, they should be placed in the same positions within each {@link Part part}, or the results are undefined.
         * The {@link Element.Attribute.PageNumber page-number} attribute sets the number of a new page; it is ignored if {@link Element.Attribute.NewPage new-page} is not "yes".
         * Version 2.0 adds a {@link Element.Attribute.BlankPage blank-page} attribute.
         * This is a positive integer value that specifies the number of blank pages to insert before the current {@link Measure measure}.
         * It is ignored if {@link Element.Attribute.NewPage new-page} is not "yes".
         * These blank pages have no music, but may have text or images specified by the {@link Credit credit} element.
         * This is used to allow a combination of pages that are all text, or all text and images, together with pages of music.
         * <p/>
         * {@link Element.Attribute.StaffSpacing Staff spacing} between multiple staves is measured in tenths of staff lines (e.g. 100 = 10 staff lines).
         * This is deprecated as of Version 1.1; the {@link StaffLayout staff-layout} element should be used instead.
         * If both are present, the {@link StaffLayout staff-layout} values take priority.
         * <p/>
         * Layout elements in a {@code print} statement only apply to the current page, system, staff, or measure.
         * Music that follows continues to take the default values from the layout included in the {@link Defaults defaults} element.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Print
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                    Element.Attribute.StaffSpacing.class,
                    Element.Attribute.NewSystem.class,
                    Element.Attribute.NewPage.class,
                    Element.Attribute.BlankPage.class,
                    Element.Attribute.PageNumber.class,
                    Element.Attribute.ID.class
                };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                PageLayout.class,
                SystemLayout.class,
                StaffLayout.class,
                MeasureLayout.class,
                MeasureNumbering.class,
                PartNameDisplay.class,
                PartAbbreviationDisplay.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code PullOff} classifies the MusicXML element, {@code pull-off}.
         * <p/>
         * This element is declared by the element type {@code pull-off} for the element type {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code pull-off} element is used in guitar and fretted instrument notation.
         * Since a single slur can be marked over many notes, the {@code pull-off} element is separate so the individual pair of notes can be specified.
         * The element content can be used to specify how the pull-off should be notated.
         * An empty element leaves this choice up to the application.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface PullOff
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Rehearsal} classifies the MusicXML element, {@code rehearsal}.
         * <p/>
         * This element is declared by the element type {@code rehearsal} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * Language is Italian ("it") by default.
         * Enclosure is square by default.
         * Left justification is assumed if not specified.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Rehearsal
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Justify.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.Underline.class,
                Element.Attribute.Overline.class,
                Element.Attribute.LineThrough.class,
                Element.Attribute.Rotation.class,
                Element.Attribute.LetterSpacing.class,
                Element.Attribute.LineHeight.class,
                Element.Attribute.XmlLang.class,
                Element.Attribute.XmlSpace.class,
                Element.Attribute.Dir.class,
                Element.Attribute.Enclosure.class,
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OneOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OneOrMore;
            }
        }

        /**
         * {@code Relation} classifies the MusicXML element, {@code relation}.
         * <p/>
         * This element is declared by the element type {@code relation} for the element type {@code identification} in the identify.mod schema file.
         * <p/>
         * A related resource for the music that is encoded.
         * This is similar to the Dublin Core relation element.
         * Standard type values are music, words, and arrangement, but other types may be used.
         *
         * @see Identification
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Relation
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                ARRANGEMENT,
                MUSIC,
                WORDS
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Release} classifies the MusicXML element, {@code release}.
         * <p/>
         * This element is declared by the element type {@code release} for the element type {@code bend} in the note.mod schema file.
         *
         * @see Bend
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Release
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Repeat} classifies the MusicXML element, {@code repeat}.
         * <p/>
         * This element is declared by the element type {@code repeat} for the element type {@code barline} in the barline.mod schema file.
         * <p/>
         * Repeat marks.
         * The start of the {@code repeat} has a forward {@link Element.Attribute.Direction direction} while the end of the {@code repeat} has a backward {@link Element.Attribute.Direction direction}.
         * Backward repeats that are not part of an {@link Ending ending} can use the {@link Element.Attribute.Times times} attribute to indicate the number of times the repeated section is played.
         * The {@link Element.Attribute.Winged winged} attribute indicates whether the {@code repeat} has winged extensions that appear above and below the barline.
         * The straight and curved values represent single wings, while the double-straight and double-curved values represent double wings.
         * The none value indicates no wings and is the default.
         *
         * @see Barline
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Repeat
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Direction.class,
                Element.Attribute.Times.class,
                Element.Attribute.Winged.class,
            };

            /** The element definition. */
            java.lang.String Definition = null;

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Rest} classifies the MusicXML element, {@code rest}.
         * <p/>
         * This element is declared by the element type {@code rest} for the entity type {@code full-note} in the note.mod schema file.
         * <p/>
         * The {@code rest} element indicates notated rests or silences.
         * Rest elements are usually empty, but placement on the staff can be specified using {@link DisplayStep display-step} and {@link DisplayOctave display-octave} elements.
         * If the {@link Element.Attribute.Measure measure} attribute is set to yes, it indicates this is a complete measure rest.
         *
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Rest
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Measure.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                DisplayStep.class,
                DisplayOctave.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code RF} classifies the MusicXML element, {@code rf}.
         * <p/>
         * This element is declared by the element type {@code rf} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface RF
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code RFZ} classifies the MusicXML element, {@code rfz}.
         * <p/>
         * This element is declared by the element type {@code rfz} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface RFZ
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code RightDivider} classifies the MusicXML element, {@code right-divider}.
         * <p/>
         * This element is declared by the element type {@code right-divider} for the element type {@code system-dividers} in the layout.mod schema file.
         *
         * @see SystemDividers
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface RightDivider
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.PrintObject.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class
            };

            /** The element definition. */
            java.lang.String Definition = null;

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code RightMargin} classifies the MusicXML element, {@code right-margin}.
         * <p/>
         * This element is declared by the element type {@code right-margin} in the layout.mod schema file.
         * <p/>
         * Margin elements are included within many of the larger layout elements.
         *
         * @see PageMargins
         * @see SystemMargins
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface RightMargin
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.LayoutTenths_Values;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Rights} classifies the MusicXML element, {@code rights}.
         * <p/>
         * This element is declared by the element type {@code rights} for the element type {@code identification} in the identify.mod schema file.
         * <p/>
         * Rights is borrowed from Dublin Core.
         * It contains copyright and other intellectual property notices.
         * Words, music, and derivatives can have different types, so multiple {@code rights} tags with different {@link Element.Attribute.Type type} attributes are supported.
         * Standard type values are music, words, and arrangement, but other types may be used.
         * The {@link Element.Attribute.Type type} attribute is only needed when there are multiple {@code rights} elements.
         *
         * @see Identification
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Rights
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Root} classifies the MusicXML element, {@code root}.
         * <p/>
         * This element is declared by the element type {@code root} for the entity type {@code harmony-chord} in the direction.mod schema file.
         * <p/>
         * A {@code root} is a pitch name like C, D, E, where a {@link Function function} is an indication like I, II, III.
         * Root is generally used with pop chord symbols, function with classical functional harmony.
         * It is an either/or choice to avoid data inconsistency.
         * Function requires that the {@link Key key} be specified in the encoding.
         * <p/>
         * The {@code root} element has a {@link RootStep root-step} and optional {@link RootAlter root-alter} similar to the {@link Step step} and {@link Alter alter} elements in a {@link Pitch pitch}, but renamed to distinguish the different musical meanings.
         * The {@link RootStep root-step} {@link Element.Attribute.Text text} attribute indicates how the {@code root} should appear in a score if not using the element contents.
         * In some chord styles, this will include the {@link RootAlter root-alter} information as well.
         * In that case, the {@link Element.Attribute.PrintObject print-object} attribute of the {@link RootAlter root-alter} element can be set to no.
         * The {@link RootAlter root-alter} {@link Element.Attribute.Location location} attribute indicates whether the alteration should appear to the left or the right of the {@link RootStep root-step}; it is right by default.
         *
         * @see Harmony
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Root
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                RootStep.class,
                RootAlter.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code RootAlter} classifies the MusicXML element, {@code root-alter}.
         * <p/>
         * This element is declared by the element type {@code root-alter} for the element type {@code root} in the direction.mod schema file.
         *
         * @see Root
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface RootAlter
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Location.class,
                Element.Attribute.PrintObject.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code RootStep} classifies the MusicXML element, {@code root-step}.
         * <p/>
         * This element is declared by the element type {@code root-step} for the element type {@code root} in the direction.mod schema file.
         *
         * @see Root
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface RootStep
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Text.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Scaling} classifies the MusicXML element, {@code scaling}.
         * <p/>
         * This element is declared by the element type {@code scaling} in the layout.mod schema file for the element type {@code defaults} in the score.mod schema file.
         * <p/>
         * Margins, page sizes, and distances are all measured in tenths to keep MusicXML data in a consistent coordinate system as much as possible.
         * The translation to absolute units is done in the {@code scaling} element, which specifies how many millimeters are equal to how many tenths.
         * For a staff height of 7 mm, {@link Millimeters millimeters} would be set to 7 while {@link Tenths tenths} is set to 40.
         * The ability to set a formula rather than a single scaling factor helps avoid roundoff errors.
         *
         * @see Defaults
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Scaling
        extends Element
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Millimeters.class,
                Tenths.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Schleifer} classifies the MusicXML element, {@code schleifer}.
         * <p/>
         * This element is declared by the element type {@code schleifer} for the element type {@code ornaments} in the note.mod schema file.
         * <p/>
         * The name for this ornament is based on the German, to avoid confusion with the more common {@link Slide slide} element defined earlier.
         *
         * @see Ornaments
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Schleifer
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Scoop} classifies the MusicXML element, {@code scoop}.
         * <p/>
         * This element is declared by the element type {@code scoop} for the element type {@code articulations} in the note.mod schema file.
         * <p/>
         * The {@code scoop} element is an indeterminate slide attached to a single note.
         * Scoops come before the main note, coming from below the pitch.
         *
         * @see Articulations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Scoop
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.LineShape.class,
                Element.Attribute.LineType.class,
                Element.Attribute.LineLength.class,
                Element.Attribute.DashLength.class,
                Element.Attribute.SpaceLength.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Scordatura} classifies the MusicXML element, {@code scordatura}.
         * <p/>
         * This element is declared by the element type {@code scordatura} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * Scordatura string tunings are represented by a series of {@link Accord accord} elements.
         * The {@link TuningStep tuning-step}, {@link TuningAlter tuning-alter}, and {@link TuningOctave tuning-octave} elements are also used with the {@link StaffTuning staff-tuning} element, and are defined in the common.mod file.
         * Strings are numbered from high to low.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Scordatura
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.ID.class
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Accord.class,
            };

            /**
             * {@inheritDoc}
             *
             * @return {@link Occurrence#OnlyOne}.
             */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code ScoreInstrument} classifies the MusicXML element, {@code score-instrument}.
         * <p/>
         * This element is declared by the element type {@code score-instrument} for the element type {@code score-part} in the score.mod schema file.
         * </p>
         * The {@code score-instrument} element allows for multiple instruments per {@link ScorePart score-part}.
         * As with the {@link ScorePart score-part} element, each {@code score-instrument} has a required ID attribute, a name, and an optional abbreviation.
         * The {@link InstrumentName instrument-name} and {@link InstrumentAbbreviation instrument-abbreviation} are typically used within a software application, rather than appearing on the printed page of a score.
         * <p/>
         * A {@code score-instrument} element is also required if the score specifies MIDI 1.0 channels, banks, or programs.
         * An initial {@link MidiInstrument midi-instrument} assignment can also be made here.
         *
         * @see ScorePart
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface ScoreInstrument
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                InstrumentName.class,
                InstrumentAbbreviation.class,
                InstrumentSound.class,
                Solo.class,
                Ensemble.class,
                VirtualInstrument.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code ScorePart} classifies the MusicXML element, {@code score-part}.
         * <p/>
         * This element is declared by the element type {@code score-part} for the element type {@code part-list} in the score.mod schema file.
         * </p>
         * Each MusicXML part corresponds to a track in a Standard MIDI Format 1 file.
         * The {@link ScoreInstrument score-instrument} elements are used when there are multiple instruments per track.
         * The {@link MidiDevice midi-device} element is used to make a MIDI device or port assignment for the given track or specific MIDI instruments.
         * Initial {@link MidiInstrument midi-instrument} assignments may be made here as well.
         * <p/>
         * The {@link PartNameDisplay part-name-display} and {@link PartAbbreviationDisplay part-abbreviation-display} elements are defined in the common.mod file, as they can be used within both the {@link ScorePart score-part} and {@link Print print} elements.
         *
         * @see PartList
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface ScorePart
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Identification.class,
                PartName.class,
                PartNameDisplay.class,
                PartAbbreviation.class,
                PartAbbreviationDisplay.class,
                Group.class,
                ScoreInstrument.class,
                MidiDevice.class,
                MidiInstrument.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code ScorePartwise} classifies the MusicXML element, {@code score-partwise}.
         * <p/>
         * This element is declared by the element type {@code score-partwise} in the score.mod schema file for the entity types {@code partwise} and {@code timewise} in the partwise.dtd and timewise.dtd schema files.
         * </p>
         * The score is the root element for the DTD.
         * It includes the {@code score-header} entity, followed either by a series of {@link Part parts} with {@link Measure measures} inside ({@code score-partwise}) or a series of {@link Measure measures} with {@link Part parts} inside ({@link ScoreTimewise score-timewise}).
         * Having distinct top-level elements for partwise and timewise scores makes it easy to ensure that an XSLT stylesheet does not try to transform a document already in the desired format.
         * The {@code document-attributes} entity includes the {@link Element.Attribute.Version version} attribute and is defined in the common.mod file.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface ScorePartwise
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                // document-attributes
                Element.Attribute.Version.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Work.class,
                MovementNumber.class,
                MovementTitle.class,
                Identification.class,
                Defaults.class,
                Credit.class,
                PartList.class,
                Part.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code ScoreTimewise} classifies the MusicXML element, {@code score-timewise}.
         * <p/>
         * This element is declared by the element type {@code score-timewise} in the score.mod schema file for the entity types {@code partwise} and {@code timewise} in the partwise.dtd and timewise.dtd schema files.
         * </p>
         * The score is the root element for the DTD.
         * It includes the {@code score-header} entity, followed either by a series of {@link Part parts} with {@link Measure measures} inside ({@link ScorePartwise score-partwise}) or a series of {@link Measure measures} with {@link Part parts} inside ({@code score-timewise}).
         * Having distinct top-level elements for partwise and timewise scores makes it easy to ensure that an XSLT stylesheet does not try to transform a document already in the desired format.
         * The {@code document-attributes} entity includes the {@link Element.Attribute.Version version} attribute and is defined in the common.mod file.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface ScoreTimewise
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                // document-attributes
                Element.Attribute.Version.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Work.class,
                MovementNumber.class,
                MovementTitle.class,
                Identification.class,
                Defaults.class,
                Credit.class,
                PartList.class,
                Measure.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Segno} classifies the MusicXML element, {@code segno}.
         * <p/>
         * This element is declared by the element type {@code segno} for the element type {@code direction-type} in the direction.mod schema file; and for the element type {@code barline} in the barline.mod schema file.
         * <p/>
         * Segno sign can be associated with a measure or a general musical direction.
         * These are visual indicators only; a {@link Sound sound} element is also needed to guide playback applications reliably.
         * The exact glyph can be specified with the {@link Element.Attribute.SMuFL smufl} attribute using a SMuFL canonical glyph name that starts with segno.
         *
         * @see Barline
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Segno
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.ID.class,
                Element.Attribute.SMuFL.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OneOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OneOrMore;
            }
        }

        /**
         * {@code SemiPitched} classifies the MusicXML element, {@code semi-pitched}.
         * <p/>
         * This element is declared by the element type {@code semi-pitched} for the element type {@code play} in the common.mod schema file.
         * <p/>
         * The {@code semi-pitched} element represents categories of indefinite pitch for percussion instruments.
         * Values are high, medium-high, medium, medium-low, low, and very-low.
         *
         * @see Play
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface SemiPitched
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                HIGH,
                LOW,
                MEDIUM,
                MEDIUM_HIGH,
                MEDIUM_LOW,
                VERY_LOW
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code SenzaMisura} classifies the MusicXML element, {@code senza-misura}.
         * <p/>
         * This element is declared by the element type {@code senza-misura} for the element type {@code time} in the attributes.mod schema file.
         *
         * @see Time
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface SenzaMisura
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code SF} classifies the MusicXML element, {@code sf}.
         * <p/>
         * This element is declared by the element type {@code sf} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface SF
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code SFFZ} classifies the MusicXML element, {@code sffz}.
         * <p/>
         * This element is declared by the element type {@code sffz} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface SFFZ
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code SFP} classifies the MusicXML element, {@code sfp}.
         * <p/>
         * This element is declared by the element type {@code sfp} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface SFP
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code SFPP} classifies the MusicXML element, {@code sfpp}.
         * <p/>
         * This element is declared by the element type {@code sfpp} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface SFPP
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code SFZ} classifies the MusicXML element, {@code sfz}.
         * <p/>
         * This element is declared by the element type {@code sfz} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface SFZ
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code SFZP} classifies the MusicXML element, {@code sfzp}.
         * <p/>
         * This element is declared by the element type {@code sfzp} for the element type {@code dynamics} in the common.mod schema file.
         *
         * @see Dynamics
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface SFZP
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Shake} classifies the MusicXML element, {@code shake}.
         * <p/>
         * This element is declared by the element type {@code shake} for the element type {@code ornaments} in the note.mod schema file.
         *
         * @see Ornaments
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Shake
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.StartNote.class,
                Element.Attribute.TrillStep.class,
                Element.Attribute.TwoNoteTurn.class,
                Element.Attribute.Accelerate.class,
                Element.Attribute.Beats.class,
                Element.Attribute.SecondBeat.class,
                Element.Attribute.LastBeat.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Sign} classifies the MusicXML element, {@code sign}.
         * <p/>
         * This element is declared by the element type {@code sign} for the element type {@code clef} in the attributes.mod schema file.
         *
         * @see Clef
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Sign
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                C,
                F,
                G,
                JIANPU,
                NONE,
                PERCUSSION,
                TAB
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Slash} classifies the MusicXML element, {@code slash}.
         * <p/>
         * This element is declared by the element type {@code slash} for the element type {@code measure-style} in the attributes.mod schema file.
         * <p/>
         * The {@code slash} element is used to indicate that slash notation is to be used.
         * If the slash is on every beat, {@link Element.Attribute.UseStems use-stems} is no (the default).
         * To indicate rhythms but not pitches, {@link Element.Attribute.UseStems use-stems} is set to yes.
         * The {@link Element.Attribute.Type type} attribute indicates whether this is the start or stop of a slash notation style.
         * The {@link Element.Attribute.UseDots use-dots} attribute works as for the {@link BeatRepeat beat-repeat} element, and only has effect if {@link Element.Attribute.UseStems use-stems} is no.
         *
         * @see MeasureStyle
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Slash
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.UseDots.class,
                Element.Attribute.UseStems.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                SlashType.class,
                SlashDot.class,
                ExceptVoice.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code SlashDot} classifies the MusicXML element, {@code slash-dot}.
         * <p/>
         * This element is declared by the element type {@code slash-dot} for the element types {@code beat-repeat} and {@code slash} in the attributes.mod schema file.
         * <p/>
         * It has the same value as the {@link Dot dot} element, and defines what the beat is for the display of repetition marks.
         * If not present, the beat is based on the current time signature.
         *
         * @see MeasureStyle
         * @see BeatRepeat
         * @see Slash
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface SlashDot
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code SlashType} classifies the MusicXML element, {@code slash-type}.
         * <p/>
         * This element is declared by the element type {@code slash-type} for the element types {@code beat-repeat} and {@code slash} in the attributes.mod schema file.
         * <p/>
         * It has the same value as the {@link Type type} element, and defines what the beat is for the display of repetition marks.
         * If not present, the beat is based on the current time signature.
         *
         * @see MeasureStyle
         * @see BeatRepeat
         * @see Slash
         * @see Type
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface SlashType
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.Type_Values;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Slide} classifies the MusicXML element, {@code slide}.
         * <p/>
         * This element is declared by the element type {@code slide} for the element type {@code notations} in the note.mod schema file.
         * <p/>
         * {@link Glissando Glissando} and {@code slide} elements both indicate rapidly moving from one pitch to the other so that individual notes are not discerned.
         * The distinction is similar to that between NIFF's glissando and portamento elements.
         * A slide is continuous between two notes and defaults to a solid line.
         * The optional text for a {@code slide} is printed alongside the line.
         *
         * @see Notations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Slide
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
                Element.Attribute.LineType.class,
                Element.Attribute.DashLength.class,
                Element.Attribute.SpaceLength.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Accelerate.class,
                Element.Attribute.Beats.class,
                Element.Attribute.FirstBeat.class,
                Element.Attribute.LastBeat.class,
                Element.Attribute.ID.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Slur} classifies the MusicXML element, {@code slur}.
         * <p/>
         * This element is declared by the element type {@code slur} for the element type {@code notations} in the note.mod schema file.
         * <p/>
         * Slur elements are empty.
         * Most slurs are represented with two elements: one with a start {@link Element.Attribute.Type type}, and one with a stop {@link Element.Attribute.Type type}.
         * Slurs can add more elements using a continue {@link Element.Attribute.Type type}.
         * This is typically used to specify the formatting of cross-system slurs, or to specify the shape of very complex slurs.
         *
         * @see Notations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Slur
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
                Element.Attribute.LineType.class,
                Element.Attribute.DashLength.class,
                Element.Attribute.SpaceLength.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.Placement.class,
                Element.Attribute.Orientation.class,
                Element.Attribute.BezierX.class,
                Element.Attribute.BezierY.class,
                Element.Attribute.BezierX2.class,
                Element.Attribute.BezierY2.class,
                Element.Attribute.BezierOffset.class,
                Element.Attribute.BezierOffset2.class,
                Element.Attribute.Color.class,
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Smear} classifies the MusicXML element, {@code smear}.
         * <p/>
         * This element is declared by the element type {@code smear} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code smear} element represents the tilde-shaped smear symbol used in brass notation.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Smear
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code SnapPizzicato} classifies the MusicXML element, {@code snap-pizzicato}.
         * <p/>
         * This element is declared by the element type {@code snap-pizzicato} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code snap-pizzicato} element represents the snap pizzicato symbol.
         * This is a circle with a line, where the line comes inside the circle.
         * It is distinct from the thumb-position symbol, where the line does not come inside the circle.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface SnapPizzicato
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code SoftAccent} classifies the MusicXML element, {@code soft-accent}.
         * <p/>
         * This element is declared by the element type {@code soft-accent} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code soft-accent} element indicates a soft accent that is not as heavy as a normal accent.
         * It is often notated as  &lt;&gt;.
         * It can be combined with other articulations to implement the entire SMuFL Articulation Supplement range.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface SoftAccent
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Software} classifies the MusicXML element, {@code software}.
         * <p/>
         * This element is declared by the element type {@code software} for the element type {@code encoding} in the identify.mod schema file.
         *
         * @see Encoding
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Software
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Solo} classifies the MusicXML element, {@code solo}.
         * <p/>
         * This element is declared by the element type {@code solo} for the element type {@code score-instrument} in the score.mod schema file.
         *
         * @see ScoreInstrument
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Solo
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Sound} classifies the MusicXML element, {@code sound}.
         * <p/>
         * This element is declared by the element type {@code sound} for the element type {@code direction} in the direction.mod schema file.
         * <p/>
         * The {@code sound} element contains general playback parameters.
         * They can stand alone within a part/measure, or be a component element within a {@link Direction direction}.
         * <p/>
         * {@link Element.Attribute.Tempo Tempo} is expressed in quarter notes per minute.
         * If 0, the sound-generating program should prompt the user at the time of compiling a sound (MIDI) file.
         * <p/>
         * {@link Element.Attribute.Dynamics Dynamics} (or MIDI velocity) are expressed as a percentage of the default forte value (90 for MIDI 1.0).
         * <p/>
         * {@link Element.Attribute.Dacapo Dacapo} indicates to go back to the beginning of the movement.
         * When used it always has the value "yes".
         * <p/>
         * {@link Element.Attribute.Segno Segno} and {@link Element.Attribute.Dalsegno dalsegno} are used for backwards jumps to a {@link Segno segno} sign; {@link Element.Attribute.Coda coda} and {@link Element.Attribute.Tocoda tocoda} are used for forward jumps to a {@link Coda coda} sign.
         * If there are multiple jumps, the value of these parameters can be used to name and distinguish them.
         * If {@link Element.Attribute.Segno Segno} or {@link Element.Attribute.Coda coda} is used, the {@link Element.Attribute.Divisions divisions} attribute can also be used to indicate the number of divisions per quarter note.
         * Otherwise sound and MIDI generating programs may have to recompute this.
         * <p/>
         * By default, a {@link Element.Attribute.Dalsegno dalsegno} or {@link Element.Attribute.Dacapo Dacapo} attribute indicates that the jump should occur the first time through, while a {@link Element.Attribute.Tocoda tocoda} attribute indicates the jump should occur the second time through.
         * The time that jumps occur can be changed by using the {@link Element.Attribute.TimeOnly time-only} attribute.
         * <p/>
         * {@link Element.Attribute.ForwardRepeat Forward-repeat} is used when a forward repeat sign is implied, and usually follows a bar line.
         * When used it always has the value of "yes".
         * <p/>
         * The {@link Element.Attribute.Fine fine} attribute follows the final note or rest in a movement with a da capo or dal segno direction.
         * If numeric, the value represents the actual duration of the final note or rest, which can be ambiguous in written notation and different among parts and voices.
         * The value may also be "yes" to indicate no change to the final duration.
         * <p/>
         * If the {@code sound} element applies only particular times through a repeat, the {@link Element.Attribute.TimeOnly time-only} attribute indicates which times to apply the {@code sound} element.
         * The value is a comma-separated list of positive integers arranged in ascending order, indicating which times through the repeated section that the element applies.
         * <p/>
         * {@link Element.Attribute.Pizzicato Pizzicato} in a {@code sound} element effects all following notes.
         * Yes indicates pizzicato, no indicates arco.
         * <p/>
         * The {@link Element.Attribute.Pan pan} and {@link Element.Attribute.Elevation elevation} attributes are deprecated in Version 2.0.
         * The {@link Pan pan} and {@link Elevation elevation} elements in the {@link MidiInstrument midi-instrument} element should be used instead.
         * The meaning of the {@link Element.Attribute.Pan pan} and {@link Element.Attribute.Elevation elevation} attributes is the same as for the {@link Pan pan} and {@link Elevation elevation} elements.
         * If both are present, the {@link MidiInstrument midi-instrument} elements take priority.
         * <p/>
         * The {@link Element.Attribute.DamperPedal damper-pedal}, {@link Element.Attribute.SoftPedal soft-pedal}, and {@link Element.Attribute.SostenutoPedal sostenuto-pedal} attributes effect playback of the three common piano pedals and their MIDI controller equivalents.
         * The yes value indicates the pedal is depressed; no indicates the pedal is released.
         * A numeric value from 0 to 100 may also be used for half pedaling.
         * This value is the percentage that the pedal is depressed.
         * A value of 0 is equivalent to no, and a value of 100 is equivalent to yes.
         * <p/>
         * MIDI devices, MIDI instruments, and playback techniques are changed using the {@link MidiDevice midi-device}, {@link MidiInstrument midi-instrument}, and {@link Play play} elements defined in the common.mod file.
         * When there are multiple instances of these elements, they should be grouped together by instrument using the {@link Element.Attribute.ID id} attribute values.
         * <p/>
         * The {@link Offset offset} element is used to indicate that the sound takes place offset from the current score position.
         * If the {@code sound} element is a child of a {@link Direction direction} element, the {@code sound} {@link Offset offset} element overrides the {@link Direction direction} {@link Offset offset} element if both elements are present.
         * Note that the offset reflects the intended musical position for the change in sound.
         * It should not be used to compensate for latency issues in particular hardware configurations.
         *
         * @see Direction
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Sound
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Tempo.class,
                Element.Attribute.Dynamics.class,
                Element.Attribute.Dacapo.class,
                Element.Attribute.Segno.class,
                Element.Attribute.Dalsegno.class,
                Element.Attribute.Coda.class,
                Element.Attribute.Tocoda.class,
                Element.Attribute.Divisions.class,
                Element.Attribute.ForwardRepeat.class,
                Element.Attribute.Fine.class,
                Element.Attribute.Pizzicato.class,
                Element.Attribute.Pan.class,
                Element.Attribute.Elevation.class,
                Element.Attribute.DamperPedal.class,
                Element.Attribute.SoftPedal.class,
                Element.Attribute.SostenutoPedal.class,
                Element.Attribute.TimeOnly.class,
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                MidiDevice.class,
                MidiInstrument.class,
                Play.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code SoundingPitch} classifies the MusicXML element, {@code sounding-pitch}.
         * <p/>
         * This element is declared by the element type {@code sounding-pitch} for the entity type {@code technical} in the note.mod schema file.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface SoundingPitch
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Source} classifies the MusicXML element, {@code source}.
         * <p/>
         * This element is declared by the element type {@code source} for the element type {@code identification} in the identify.mod schema file.
         * <p/>
         * The source for the music that is encoded.
         * This is similar to the Dublin Core source element.
         *
         * @see Identification
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Source
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Spiccato} classifies the MusicXML element, {@code spiccato}.
         * <p/>
         * This element is declared by the element type {@code spiccato} for the element type {@code articulations} in the note.mod schema file.
         * <p/>
         * The {@code spiccato} element is used for a stroke articulation, as opposed to a dot or a wedge.
         *
         * @see Articulations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Spiccato
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Staccatissimo} classifies the MusicXML element, {@code staccatissimo}.
         * <p/>
         * This element is declared by the element type {@code staccatissimo} for the element type {@code articulations} in the note.mod schema file.
         * <p/>
         * The {@code staccatissimo} element is used for a wedge articulation, as opposed to a dot or a stroke.
         *
         * @see Articulations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Staccatissimo
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Staccato} classifies the MusicXML element, {@code staccato}.
         * <p/>
         * This element is declared by the element type {@code staccato} for the element type {@code articulations} in the note.mod schema file.
         * <p/>
         * The {@code staccato} element is used for a dot articulation, as opposed to a stroke or a wedge.
         *
         * @see Articulations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Staccato
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Staff} classifies the MusicXML element, {@code staff}.
         * <p/>
         * This element is declared by the element type {@code staff} in the common.mod schema file, for the element types {@code direction} and {@code harmony} in the direction.mod schema file, and {@code forward} and {@code note} in the note.mod schema file.
         * <p/>
         * Staff assignment is only needed for music notated on multiple staves.
         * Used by both notes and directions.
         * Staff values are numbers, with 1 referring to the top-most staff in a part.
         *
         * @see Direction
         * @see Forward
         * @see Harmony
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Staff
        extends
            Analytic,
            XML.Schematic.PerType
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeY.class,
                Element.Attribute.Placement.class,
                Element.Attribute.Color.class,
                Element.Attribute.ID.class,
                Element.Attribute.StartNote.class,
                Element.Attribute.TrillStep.class,
                Element.Attribute.TwoNoteTurn.class,
                Element.Attribute.Accelerate.class,
                Element.Attribute.Beats.class,
                Element.Attribute.SecondBeat.class,
                Element.Attribute.LastBeat.class,
            };

            /** The array of accepted parent element types. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Barline.class,
                Ornaments.class,
            };

            @Override
            default Class<? extends Staff> per(final Class<?> type) {
                if (type == Barline.class ||
                    type == Forward.class)
                    return Staff_PerMany1.class;
                else
                if (type == Ornaments.class)
                    return Staff_PerOrnaments.class;

                return null;
            }

            interface Staff_PerMany1
            extends Staff
            {
                @Override
                default java.lang.String occurrence() {
                    return Occurrence.ZeroOrOne;
                }
            }

            interface Staff_PerOrnaments
            extends Staff
            {
                @Override
                default java.lang.String occurrence() {
                    return Occurrence.ZeroOrMore;
                }
            }
        }

        /**
         * {@code StaffDetails} classifies the MusicXML element, {@code staff-details}.
         * <p/>
         * This element is declared by the element type {@code staff-details} for the element type {@code attributes} in the attributes.mod schema file.
         * <p/>
         * The {@code staff-details} element is used to indicate different types of staves.
         * The {@link StaffType staff-type} element can be ossia, cue, editorial, regular, or alternate.
         * An alternate staff indicates one that shares the same musical data as the prior staff, but displayed differently (e.g., treble and bass clef, standard notation and tab).
         * The {@link StaffLines staff-lines} element specifies the number of lines for a non 5-line staff.
         * The {@link StaffTuning staff-tuning} and {@link Capo capo} elements are used to specify tuning when using tablature notation.
         * The optional {@link Element.Attribute.Number number} attribute specifies the staff number from top to bottom on the system, as with {@link Clef clef}.
         * The optional {@link Element.Attribute.ShowFrets show-frets} attribute indicates whether to show tablature frets as numbers (0, 1, 2) or letters (a, b, c).
         * The default choice is numbers.
         * The {@link Element.Attribute.PrintObject print-object} attribute is used to indicate when a staff is not printed in a part, usually in large scores where empty parts are omitted.
         * It is yes by default.
         * If {@link Element.Attribute.PrintSpacing print-spacing} is yes while {@link Element.Attribute.PrintObject print-object} is no, the score is printed in cutaway format where vertical space is left for the empty part.
         *
         * @see Attributes
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface StaffDetails
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Number.class,
                Element.Attribute.ShowFrets.class,
                Element.Attribute.PrintObject.class,
                Element.Attribute.PrintSpacing.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                StaffType.class,
                StaffLines.class,
                StaffTuning.class,
                Capo.class,
                StaffSize.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code StaffDistance} classifies the MusicXML element, {@code staff-distance}.
         * <p/>
         * This element is declared by the element type {@code staff-distance} for the element type {@code staff-layout} in the layout.mod schema file.
         *
         * @see StaffLayout
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface StaffDistance
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.LayoutTenths_Values;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code StaffDivide} classifies the MusicXML element, {@code staff-divide}.
         * <p/>
         * This element is declared by the element type {@code staff-divide} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * The {@code staff-divide} element is used for staff division symbols.
         * The down, up, and up-down {@link Element.Attribute.Type type} values correspond to SMuFL code points U+E00B, U+E00C, and U+E00D respectively.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface StaffDivide
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.ID.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code StaffLayout} classifies the MusicXML element, {@code staff-layout}.
         * <p/>
         * This element is declared by the element type {@code staff-layout} in the layout.mod schema file, for the element type {@code defaults} in the score.mod schema file.
         * <p/>
         * Staff layout includes the vertical distance from the bottom line of the previous staff in this system to the top line of the staff specified by the {@link Element.Attribute.Number number} attribute.
         * The optional {@link Element.Attribute.Number number} attribute refers to staff numbers within the part, from top to bottom on the system.
         * A value of 1 is assumed if not present.
         * When used in the {@link Defaults defaults} element, the values apply to all parts.
         * This value is ignored for the first staff in a system.
         *
         * @see Defaults
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface StaffLayout
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Number.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                StaffDistance.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code StaffLines} classifies the MusicXML element, {@code staff-lines}.
         * <p/>
         * This element is declared by the element type {@code staff-lines} for the element type {@code staff-details} in the attributes.mod schema file.
         *
         * @see StaffDetails
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface StaffLines
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code StaffSize} classifies the MusicXML element, {@code staff-size}.
         * <p/>
         * This element is declared by the element type {@code staff-size} for the element type {@code staff-details} in the attributes.mod schema file.
         * <p/>
         * The {@code staff-size} element indicates how large a staff space is on this staff, expressed as a percentage of the work's default scaling.
         * Values less than 100 make the staff space smaller while values over 100 make the staff space larger.
         * A {@link StaffType staff-type} of cue, ossia, or editorial implies a {@code staff-size} of less than 100, but the exact value is implementation-dependent unless specified here.
         * Staff size affects staff height only, not the relationship of the staff to the left and right margins.
         *
         * @see StaffDetails
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface StaffSize
        extends Element
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code StaffTuning} classifies the MusicXML element, {@code staff-tuning}.
         * <p/>
         * This element is declared by the element type {@code staff-tuning} for the element {@code staff-details} in the attributes.mod schema file.
         * <p/>
         * The {@link TuningStep tuning-step}, {@link TuningAlter tuning-alter}, and {@link TuningOctave tuning-octave} elements are defined in the common.mod file.
         * Staff lines are numbered from bottom to top.
         *
         * @see StaffDetails
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface StaffTuning
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Line.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                TuningStep.class,
                TuningAlter.class,
                TuningOctave.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code StaffType} classifies the MusicXML element, {@code staff-type}.
         * <p/>
         * This element is declared by the element type {@code staff-type} for the element type {@code staff-details} in the attributes.mod schema file.
         *
         * @see StaffDetails
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface StaffType
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                ALTERNATE,
                CUE,
                EDITORIAL,
                OSSIA,
                REGULAR
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Staves} classifies the MusicXML element, {@code staves}.
         * <p/>
         * This element is declared by the element type {@code staves} for the element type {@code attributes} in the attributes.mod schema file.
         * <p/>
         * Staves are used if there is more than one staff represented in the given part (e.g., 2 staves for typical piano parts).
         * If absent, a value of 1 is assumed.
         * Staves are ordered from top to bottom in a part in numerical order, with staff 1 above staff 2.
         *
         * @see Attributes
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Staves
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Stem} classifies the MusicXML element, {@code stem}.
         * <p/>
         * This element is declared by the element type {@code stem} for the element type {@code note} in the note.mod schema file.
         * <p/>
         * Stems can be down, up, none, or double.
         * For down and up stems, the position attributes can be used to specify stem length.
         * The relative values specify the end of the stem relative to the program default.
         * Default values specify an absolute end stem position.
         * Negative values of relative-y that would flip a stem instead of shortening it are ignored.
         * A stem element associated with a rest refers to a stemlet.
         *
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Stem
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.Color.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                DOUBLE,
                DOWN,
                NONE,
                UP
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Step} classifies the MusicXML element, {@code step}.
         * <p/>
         * This element is declared by the element type {@code step} for the element type {@code pitch} in the note.mod schema file.
         * <p/>
         * The {@code step} element uses the English letters A through G.
         *
         * @see Pitch
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Step
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Stick} classifies the MusicXML element, {@code stick}.
         * <p/>
         * This element is declared by the element type {@code stick} for the element type {@code percussion} in the direction.mod schema file.
         * <p/>
         * The {@code stick} element represents pictograms where the material in the stick, mallet, or beater is included.
         * Valid values for {@link StickType stick-type} are bass drum, double bass drum, glockenspiel, gum, hammer, superball, timpani, wound, xylophone, and yarn.
         * Valid values for {@link StickMaterial stick-material} are soft, medium, hard, shaded, and x.
         * The shaded and x values reflect different uses for brass, wood, and steel core beaters of different types.
         * The {@link Element.Attribute.Tip tip} attribute represents the direction in which the tip of a stick points.
         * The {@link Element.Attribute.Parentheses parentheses} and {@link Element.Attribute.DashedCircle dashed-circle} attributes indicate the presence of these marks around the round beater part of a pictogram.
         * Values for these attributes are "no" if not present.
         *
         * @see Percussion
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Stick
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Tip.class,
                Element.Attribute.Parentheses.class,
                Element.Attribute.DashedCircle.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                StickType.class,
                StickMaterial.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code StickLocation} classifies the MusicXML element, {@code stick-location}.
         * <p/>
         * This element is declared by the element type {@code stick-location} for the element type {@code percussion} in the direction.mod schema file.
         * <p/>
         * The stick-location element represents pictograms for the location of sticks, beaters, or mallets on cymbals, gongs, drums, and other instruments.
         * Valid values are center, rim, cymbal bell, and cymbal edge.
         *
         * @see Percussion
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface StickLocation
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                CENTER,
                CYMBAL__BELL,
                CYMBAL__EDGE,
                RIM
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code StickMaterial} classifies the MusicXML element, {@code stick-material}.
         * <p/>
         * This element is declared by the element type {@code stick-material} for the element type {@code stick} in the direction.mod schema file.
         * <p/>
         * Valid values for stick-material are soft, medium, hard, shaded, and x.
         *
         * @see Stick
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface StickMaterial
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                HARD,
                MEDIUM,
                SHADED,
                SOFT,
                STICK_MATERIAL,
                X
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code StickType} classifies the MusicXML element, {@code stick-type}.
         * <p/>
         * This element is declared by the element type {@code stick-type} for the element type {@code stick} in the direction.mod schema file.
         * <p/>
         * Valid values for stick-type are bass drum, double bass drum, glockenspiel, gum, hammer, superball, timpani, wound, xylophone, and yarn.
         *
         * @see Stick
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface StickType
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                BASS__DRUM,
                DOUBLE__BASS__DRUM,
                GLOCKENSPIEL,
                GUM,
                HAMMER,
                SUPERBALL,
                TIMPANI,
                WOUND,
                XYLOPHONE,
                YARN
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Stopped} classifies the MusicXML element, {@code stopped}.
         * <p/>
         * This element is declared by the element type {@code stopped} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code stopped} element represents the stopped symbol, which looks like a plus sign.
         * The {@link Element.Attribute.SMuFL smufl} attribute can be used to distinguish different SMuFL glyphs that have a similar appearance such as handbellsMalletBellSuspended and guitarClosePedal.
         * If not present, the default glyph is brassMuteClosed.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Stopped
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.SMuFL.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Stress} classifies the MusicXML element, {@code stress}.
         * <p/>
         * This element is declared by the element type {@code stress} for the element types {@code technical} in the note.mod schema file.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Stress
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code String} classifies the MusicXML element, {@code string}.
         * <p/>
         * This element is declared by the element type {@code string} in the common.mod schema file; and for the element type {@code frame-note} in the direction.mod schema file.
         * <p/>
         * String is used with tablature notation and chord symbols.
         * String numbers start with 1 for the highest pitched full-length string.
         * The {@code string} element can also be used in regular notation.
         *
         * @see FrameNote
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface String
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;
        }

        /**
         * {@code StringMute} classifies the MusicXML element, {@code string-mute}.
         * <p/>
         * This element is declared by the element type {@code string-mute} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * String mute on and mute off symbols.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface StringMute
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.ID.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
             * {@inheritDoc}
             *
             * @return {@link Occurrence#OnlyOne}.
             */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code StrongAccent} classifies the MusicXML element, {@code accent}.
         * <p/>
         * This element is declared by the element type {@code strong-accent} for the element type {@code articulations} in the note.mod schema file.
         *
         * @see Articulations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface StrongAccent
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.Type.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Suffix} classifies the MusicXML element, {@code suffix}.
         * <p/>
         * This element is declared by the element type {@code suffix} for the element types {@code figure} in the note.mod schema file.
         *
         * @see Figure
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Suffix
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Lambda.combineArrays(
                java.lang.String[].class,
                new java.lang.String[] {
                    PLUS,
                    SLASH,
                    BACK_SLASH,
                    VERTICAL,
                },
                Entity.Accidental_Values);

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Supports} classifies the MusicXML element, {@code supports}.
         * <p/>
         * This element is declared by the element type {@code supports} for the element type {@code encoding} in the identify.mod schema file.
         *
         * @see Encoding
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Supports
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Element_.class,
                Element.Attribute.Attribute_.class,
                Element.Attribute.Value.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Syllabic} classifies the MusicXML element, {@code syllabic}.
         * <p/>
         * This element is declared by the element type {@code syllabic} for the entity type {@code lyric} in the note.mod schema file.
         *
         * @see Lyric
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Syllabic
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                BEGIN,
                END,
                MIDDLE,
                SINGLE
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Symbol} classifies the MusicXML element, {@code symbol}.
         * <p/>
         * This element is declared by the element type {@code symbol} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * The {@code symbol} element specifies a musical symbol using a canonical SMuFL glyph name.
         * It is used when an occasional musical symbol is interspersed into text.
         * It should not be used in place of semantic markup, such as metronome marks that mix text and symbols.
         * Left justification is assumed if not specified.
         * Enclosure is none by default.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Symbol
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Justify.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.Underline.class,
                Element.Attribute.Overline.class,
                Element.Attribute.LineThrough.class,
                Element.Attribute.Rotation.class,
                Element.Attribute.LetterSpacing.class,
                Element.Attribute.LineHeight.class,
                Element.Attribute.Dir.class,
                Element.Attribute.Enclosure.class,
                Element.Attribute.ID.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OneOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OneOrMore;
            }
        }

        /**
         * {@code SystemDistance} classifies the MusicXML element, {@code system-distance}.
         * <p/>
         * This element is declared by the element type {@code system-distance} for the element type {@code system-layout} in the layout.mod schema file.
         *
         * @see SystemLayout
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface SystemDistance
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.LayoutTenths_Values;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code SystemDividers} classifies the MusicXML element, {@code system-dividers}.
         * <p/>
         * This element is declared by the element type {@code system-dividers} for the element type {@code system-layout} in the layout.mod schema file.
         * <p/>
         * The {@code system-dividers} element indicates the presence or absence of system dividers (also known as system separation marks) between systems displayed on the same page.
         * Dividers on the left and right side of the page are controlled by the {@link LeftDivider left-divider} and {@link RightDivider right-divider} elements respectively.
         * The default vertical position is half the {@link SystemDistance system-distance} value from the top of the system that is below the divider.
         * The default horizontal position is the left and right system margin, respectively.
         * <p/>
         * When used in the {@link Print print} element, the {@code system-dividers} element affects the dividers that would appear between the current system and the previous system.
         *
         * @see SystemLayout
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface SystemDividers
        extends Element
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                LeftDivider.class,
                RightDivider.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code SystemLayout} classifies the MusicXML element, {@code system-layout}.
         * <p/>
         * This element is declared by the element type {@code system-layout} in the layout.mod schema file for the element type {@code defaults} in the score.mod schema file.
         * <p/>
         * A system is a group of staves that are read and played simultaneously.
         * System layout includes left and right margins, the vertical distance from the previous system, and the presence or absence of system dividers.
         * <p/>
         * Margins are relative to the page margins.
         * Positive values indent and negative values reduce the margin size.
         * The {@link SystemDistance system distance} is measured from the bottom line of the previous system to the top line of the current system.
         * It is ignored for the first system on a page.
         * The {@link TopSystemDistance top system distance} is measured from the page's top margin to the top line of the first system.
         * It is ignored for all but the first system on a page.
         * <p/>
         * Sometimes the sum of measure widths in a system may not equal the system width specified by the layout elements due to roundoff or other errors.
         * The behavior when reading MusicXML files in these cases is application-dependent.
         * For instance, applications may find that the system layout data is more reliable than the sum of the measure widths, and adjust the measure widths accordingly.
         * <p/>
         * When used in the layout element, the {@code system-layout} element defines a default appearance for all systems in the score.
         * When used in the {@link Print print} element, the system layout element affects the appearance of the current system only.
         * All other systems use the default values provided in the {@link Defaults defaults} element.
         * If any child elements are missing from the {@code system-layout} element in a {@link Print print} element, the values from the {@link Defaults defaults} element are used there as well.
         *
         * @see Defaults
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface SystemLayout
        extends Element
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                SystemMargins.class,
                SystemDistance.class,
                TopSystemDistance.class,
                SystemDividers.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code SystemMargins} classifies the MusicXML element, {@code system-margins}.
         * <p/>
         * This element is declared by the element type {@code system-margins} for the element type {@code system-layout} in the layout.mod schema file.
         *
         * @see SystemLayout
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface SystemMargins
        extends Element
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                LeftMargin.class,
                RightMargin.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Tap} classifies the MusicXML element, {@code tap}.
         * <p/>
         * This element is declared by the element type {@code tap} for the element type {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code tap} element indicates a tap on the fretboard.
         * The text content allows specification of the notation; + and T are common choices.
         * If the element is empty, the {@link Element.Attribute.Hand hand} attribute is used to specify the symbol to use.
         * The left and right values refer to the SMuFL guitarLeftHandTapping and guitarRightHandTapping glyphs respectively.
         * The {@link Element.Attribute.Hand hand} attribute is ignored if the {@code tap} glyph is already specified by the text content.
         * If neither text content nor the {@link Element.Attribute.Hand hand} attribute are present, the display is application-specific.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Tap
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Hand.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                T,
                _PLUS
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Technical} classifies the MusicXML element, {@code technical}.
         * <p/>
         * This element is declared by the element type {@code technical} for the element type {@code notations} in the note.mod schema file.
         * <p/>
         * Technical indications give performance information for individual instruments.
         *
         * @see Notations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Technical
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.ID.class
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                    UpBow.class,
                    DownBow.class,
                    Harmonic.class,
                    OpenString.class,
                    ThumbPosition.class,
                    Fingering.class,
                    Pluck.class,
                    DoubleTongue.class,
                    TripleTongue.class,
                    Stopped.class,
                    SnapPizzicato.class,
                    Fret.class,
                    String.class,
                    HammerOn.class,
                    PullOff.class,
                    Bend.class,
                    Tap.class,
                    Heel.class,
                    Toe.class,
                    Fingernails.class,
                    Hole.class,
                    Arrow.class,
                    Handbell.class,
                    BrassBend.class,
                    Flip.class,
                    Smear.class,
                    Open.class,
                    HalfMuted.class,
                    HarmonMute.class,
                    Golpe.class,
                    OtherTechnical.class,
                };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Tenths} classifies the MusicXML element, {@code tenths}.
         * <p/>
         * This element is declared by the element type {@code tenths} for the element type {@code scaling} in the layout.mod schema file.
         * <p/>
         * Everything is measured in tenths of staff space.
         * Tenths are then scaled to millimeters within the {@link Scaling scaling} element, used in the {@link Defaults defaults} element at the start of a score.
         * Individual staves can apply a scaling factor to adjust staff size.
         * When a MusicXML element or attribute refers to tenths, it means the global {@code tenths} defined by the {@link Scaling scaling} element, not the local tenths as adjusted by the {@link StaffSize staff-size} element.
         *
         * @see Scaling
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Tenths
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.LayoutTenths_Values;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Tenuto} classifies the MusicXML element, {@code tenuto}.
         * <p/>
         * This element is declared by the element type {@code tenuto} for the element type {@code articulations} in the note.mod schema file.
         *
         * @see Articulations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Tenuto
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Text} classifies the MusicXML element, {@code text}.
         * <p/>
         * This element is declared by the element type {@code text} for the element type {@code lyric} in the note.mod schema file.
         *
         * @see Lyric
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Text
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[]{
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Underline.class,
                Element.Attribute.Overline.class,
                Element.Attribute.LineThrough.class,
                Element.Attribute.Rotation.class,
                Element.Attribute.LetterSpacing.class,
                Element.Attribute.XmlLang.class,
                Element.Attribute.Dir.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code ThumbPosition} classifies the MusicXML element, {@code thumb-position}.
         * <p/>
         * This element is declared by the element type {@code thumb-position} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code thumb-position} element represents the thumb position symbol.
         * This is a circle with a line, where the line does not come within the circle.
         * It is distinct from the snap pizzicato symbol, where the line comes inside the circle.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface ThumbPosition
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Tie} classifies the MusicXML element, {@code duration}.
         * <p/>
         * This element is declared by the element type {@code duration} for the element types {@code note} in the note.mod schema file.
         * <p/>
         * The {@code tie} element represents the tie sound.
         *
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Tie
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.TimeOnly.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOneOrTwo}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOneOrTwo;
            }
        }

        /**
         * {@code Tied} classifies the MusicXML element, {@code tied}.
         * <p/>
         * This element is declared by the element type {@code tied} for the element type {@code notations} in the note.mod schema file.
         * <p/>
         * The {@code tied} element represents the notated tie.
         * <p/>
         * The {@link Element.Attribute.Number number} attribute is rarely needed to disambiguate ties, since note pitches will usually suffice.
         * The attribute is implied rather than defaulting to 1 as with most elements.
         * It is available for use in more complex tied notation situations.
         * <p/>
         * Ties that join two notes of the same pitch together should be represented with a {@code tied} element on the first note with {@link Element.Attribute.Type type}="start" and a {@code tied} element on the second note with {@link Element.Attribute.Type type}="stop".
         * This can also be done if the two notes being tied are enharmonically equivalent, but have different {@link Step step} values.
         * It is not recommended to use {@code tied} elements to join two notes with enharmonically inequivalent pitches.
         * <p/>
         * Ties that indicate that an instrument should be undamped are specified with a single {@code tied} element with {@link Element.Attribute.Type type}="let-ring".
         * <p/>
         * Ties that are visually attached to only one note, other than undamped ties, should be specified with two {@code tied} elements on the same note, first {@link Element.Attribute.Type type}="start" then {@link Element.Attribute.Type type}="stop".
         * This can be used to represent ties into or out of repeated sections or codas.
         *
         * @see Notations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Tied
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[]{
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
                Element.Attribute.LineType.class,
                Element.Attribute.DashLength.class,
                Element.Attribute.SpaceLength.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.Placement.class,
                Element.Attribute.Orientation.class,
                Element.Attribute.BezierX.class,
                Element.Attribute.BezierY.class,
                Element.Attribute.BezierX2.class,
                Element.Attribute.BezierY2.class,
                Element.Attribute.BezierOffset.class,
                Element.Attribute.BezierOffset2.class,
                Element.Attribute.Color.class,
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Time} classifies the MusicXML element, {@code time}.
         * <p/>
         * This element is declared by the element type {@code time} for the element type {@code attributes} in the attributes.mod schema file.
         * <p/>
         * Time signatures are represented by two elements.
         * The {@link Beats beats} element indicates the number of beats, as found in the numerator of a time signature.
         * The {@link BeatType beat-type} element indicates the beat unit, as found in the denominator of a time signature.
         * <p/>
         * Multiple pairs of {@link Beats beats} and {@link BeatType beat-type} elements are used for composite time signatures with multiple denominators, such as 2/4 + 3/8.
         * A composite such as 3+2/8 requires only one {@link Beats beats}/{@link BeatType beat-type} pair.
         * <p/>
         * The {@link Interchangeable interchangeable} element is used to represent the second in a pair of interchangeable dual time signatures, such as the 6/8 in 3/4 (6/8).
         * A separate {@link Element.Attribute.Symbol symbol} attribute value is available compared to the {@link Time time} element's {@link Element.Attribute.Symbol symbol} attribute, which applies to the first of the dual time signatures.
         * The {@link TimeRelation time-relation} element indicates the symbol used to represent the interchangeable aspect of the time signature.
         * Valid values are parentheses, bracket, equals, slash, space, and hyphen.
         * <p/>
         * A {@link SenzaMisura senza-misura} element explicitly indicates that no time signature is present.
         * The optional element content indicates the symbol to be used, if any, such as an X.
         * The {@code time} element's {@link Element.Attribute.Symbol symbol} attribute is not used when a {@link SenzaMisura senza-misura} element is present.
         * <p/>
         * The {@link Element.Attribute.PrintObject print-object} attribute allows a time signature to be specified but not printed, as is the case for excerpts from the middle of a score.
         * The value is "yes" if not present.
         * The optional {@link Element.Attribute.Number number} attribute refers to staff numbers within the part, from top to bottom on the system.
         * If absent, the time signature applies to all staves in the part.
         *
         * @see Attributes
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Time
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Number.class,
                Element.Attribute.Symbol.class,
                Element.Attribute.Separator.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeY.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.PrintObject.class,
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Beats.class,
                BeatType.class,
                Interchangeable.class,
                SenzaMisura.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code TimeModification} classifies the MusicXML element, {@code time-modification}.
         * <p/>
         * This element is declared by the element type {@code time-modification} for the element type {@code note} in the note.mod schema file.
         * <p/>
         * Time modification indicates tuplets, double-note tremolos, and other durational changes.
         * A {@code time-modification} element shows how the cumulative, sounding effect of tuplets and double-note tremolos compare to the written note type represented by the {@link Type type} and {@link Dot dot} elements.
         * The child elements are defined in the common.mod file.
         * Nested tuplets and other notations that use more detailed information need both the {@code time-modification} and {@link Tuplet tuplet} elements to be represented accurately.
         *
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface TimeModification
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                ActualNotes.class,
                NormalNotes.class,
                NormalType.class,
                NormalDot.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code TimeRelation} classifies the MusicXML element, {@code time-relation}.
         * <p/>
         * This element is declared by the element type {@code time-relation} for the element type {@code time} in the attributes.mod schema file.
         *
         * @see Time
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface TimeRelation
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                BRACKET,
                EQUALS,
                HYPHEN,
                PARENTHESES,
                SLASH,
                SPACE
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Timpani} classifies the MusicXML element, {@code timpani}.
         * <p/>
         * This element is declared by the element type {@code timpani} for the element type {@code percussion} in the direction.mod schema file.
         * <p/>
         * The timpani element represents the timpani pictogram.
         *
         * @see Percussion
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Timpani
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Toe} classifies the MusicXML element, {@code toe}.
         * <p/>
         * This element is declared by the element type {@code toe} for the element type {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code toe} element is used with organ pedals.
         * The {@link Element.Attribute.Substitution substitution} value is "no" if the attribute is not present.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Toe
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Substitution.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code TopMargin} classifies the MusicXML element, {@code top-margin}.
         * <p/>
         * This element is declared by the element type {@code top-margin} in the layout.mod schema file.
         * <p/>
         * Margin elements are included within many of the larger layout elements.
         *
         * @see PageMargins
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface TopMargin
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.LayoutTenths_Values;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code TopSystemDistance} classifies the MusicXML element, {@code top-system-distance}.
         * <p/>
         * This element is declared by the element type {@code top-system-distance} for the element type {@code system-layout} in the layout.mod schema file.
         *
         * @see SystemLayout
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface TopSystemDistance
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.LayoutTenths_Values;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code TouchingPitch} classifies the MusicXML element, {@code touching-pitch}.
         * <p/>
         * This element is declared by the element type {@code touching-pitch} for the entity type {@code technical} in the note.mod schema file.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface TouchingPitch
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Transpose} classifies the MusicXML element, {@code transpose}.
         * <p/>
         * This element is declared by the element type {@code transpose} for the element type {@code attributes} in the attributes.mod schema file.
         * <p/>
         * If the part is being encoded for a transposing instrument in written vs. concert pitch, the transposition must be encoded in the {@code transpose} element.
         * The {@code transpose} element represents what must be added to the written pitch to get the correct sounding pitch.
         * <p/>
         * The transposition is represented by chromatic steps (required) and three optional elements: diatonic pitch steps, octave changes, and doubling an octave down.
         * The {@link Chromatic chromatic} and {@link OctaveChange octave-change} elements are numeric values added to the encoded pitch data to create the sounding pitch.
         * The {@link Diatonic diatonic} element is also numeric and allows for correct spelling of enharmonic transpositions.
         * <p/>
         * The optional {@link Element.Attribute.Number number} attribute refers to staff numbers, from top to bottom on the system.
         * If absent, the transposition applies to all staves in the part.
         * Per-staff transposition is most often used in parts that represent multiple instruments.
         *
         * @see Attributes
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Transpose
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Number.class,
                Element.Attribute.ID.class,
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                Diatonic.class,
                Chromatic.class,
                OctaveChange.class,
                Double.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Tremolo} classifies the MusicXML element, {@code tremolo}.
         * <p/>
         * This element is declared by the element type {@code tremolo} for the element type {@code ornaments} in the note.mod schema file.
         * <p/>
         * The tremolo ornament can be used to indicate either single-note, double-note, or unmeasured tremolos.
         * Single-note tremolos use the single {@link Element.Attribute.Type type}, double-note tremolos use the start and stop {@link Element.Attribute.Type type}s, and unmeasured tremolos use the unmeasured {@link Element.Attribute.Type type}.
         * The default is "single" for compatibility with Version 1.1.
         * The text of the element indicates the number of tremolo marks and is an integer from 0 to 8.
         * Note that the number of attached beams is not included in this value, but is represented separately using the beam element.
         * The value should be 0 for unmeasured tremolos.
         * <p/>
         * When using double-note tremolos, the duration of each note in the tremolo should correspond to half of the notated type value.
         * A {@link TimeModification time-modification} element should also be added with an {@link ActualNotes actual-notes} value of 2 and a {@link NormalNotes normal-notes} value of 1.
         * If used within a tuplet, this 2/1 ratio should be multiplied by the existing tuplet ratio.
         * <p/>
         * The {@link Element.Attribute.SMuFL smufl} attribute specifies the glyph to use from the SMuFL tremolos range for an unmeasured tremolo.
         * It is ignored for other tremolo types.
         * The SMuFL buzzRoll glyph is used by default if the attribute is missing.
         * <p/>
         * Using repeater beams for indicating tremolos is deprecated as of MusicXML 3.0.
         *
         * @see Ornaments
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Tremolo
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.SMuFL.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code TrillMark} classifies the MusicXML element, {@code trill-mark}.
         * <p/>
         * This element is declared by the element type {@code trill-mark} for the element type {@code notations} in the note.mod schema file.
         * <p/>
         *
         * @see Notations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface TrillMark
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.StartNote.class,
                Element.Attribute.TrillStep.class,
                Element.Attribute.TwoNoteTurn.class,
                Element.Attribute.Accelerate.class,
                Element.Attribute.Beats.class,
                Element.Attribute.SecondBeat.class,
                Element.Attribute.LastBeat.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code TripleTongue} classifies the MusicXML element, {@code triple-tongue}.
         * <p/>
         * This element is declared by the element type {@code triple-tongue} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code triple-tongue} element represents the triple tongue symbol (three dots arranged horizontally).
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface TripleTongue
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code TuningAlter} classifies the MusicXML element, {@code tuning-alter}.
         * <p/>
         * This element is declared by the element type {@code tuning-alter} in the common.mod schema file, for the element type {@code staff-tuning} in the attribute.mod schema file, and {@code accord} in the direction.mod schema file.
         * <p/>
         * The {@code tuning-alter} element is represented like the {@link Alter alter} element,
         *
         * @see Accord
         * @see Alter
         * @see StaffTuning
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface TuningAlter
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code TuningOctave} classifies the MusicXML element, {@code tuning-octave}.
         * <p/>
         * This element is declared by the element type {@code tuning-octave} in the common.mod schema file, for the element type {@code staff-tuning} in the attribute.mod schema file, and {@code accord} in the direction.mod schema file.
         * <p/>
         * The {@code tuning-octave} element is represented like the {@link Octave octave} element,
         *
         * @see Accord
         * @see Octave
         * @see StaffTuning
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface TuningOctave
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code TuningStep} classifies the MusicXML element, {@code tuning-step}.
         * <p/>
         * This element is declared by the element type {@code tuning-step} in the common.mod schema file, for the element type {@code staff-tuning} in the attribute.mod schema file, and {@code accord} in the direction.mod schema file.
         * <p/>
         * The {@code tuning-step} element is represented like the {@link Step step} element,
         *
         * @see Accord
         * @see StaffTuning
         * @see Step
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface TuningStep
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                A,
                B,
                C,
                D,
                E,
                F,
                G
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Tuplet} classifies the MusicXML element, {@code tuplet}.
         * <p/>
         * This element is declared by the element type {@code tuplet} for the element type {@code notations} in the note.mod schema file.
         * <p/>
         * A {@code tuplet} element is present when a tuplet is to be displayed graphically, in addition to the sound data provided by the {@link TimeModification time-modification} elements.
         * The {@link Element.Attribute.Number number} attribute is used to distinguish nested tuplets.
         * The {@link Element.Attribute.Bracket bracket} attribute is used to indicate the presence of a bracket.
         * If unspecified, the results are implementation-dependent.
         * The {@link Element.Attribute.LineShape line-shape} attribute is used to specify whether the bracket is straight or in the older curved or slurred style.
         * It is straight by default.
         * <p/>
         * Whereas a {@link TimeModification time-modification} element shows how the cumulative, sounding effect of tuplets and double-note tremolos compare to the written note type, the {@code tuplet} element describes how this is displayed.
         * The {@code tuplet} element also provides more detailed representation information than the {@link TimeModification time-modification} element, and is needed to represent nested tuplets and other complex tuplets accurately.
         * The {@link TupletActual tuplet-actual} and {@link TupletNormal tuplet-normal} elements provide optional full control over tuplet specifications.
         * Each allows the number and note type (including dots) describing a single tuplet.
         * If any of these elements are absent, their values are based on the {@link TimeModification time-modification} element.
         * <p/>
         * The {@link Element.Attribute.ShowNumber show-number} attribute is used to display either the number of actual notes, the number of both actual and normal notes, or neither.
         * It is actual by default.
         * The {@link Element.Attribute.ShowType show-type} attribute is used to display either the actual type, both the actual and normal types, or neither.
         * It is none by default.
         *
         * @see Notations
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Tuplet
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
                Element.Attribute.Bracket.class,
                Element.Attribute.ShowNumber.class,
                Element.Attribute.ShowType.class,
                Element.Attribute.LineShape.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.Placement.class,
                Element.Attribute.ID.class
            };

            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                TupletActual.class,
                TupletNormal.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code TupletActual} classifies the MusicXML element, {@code tuplet-actual}.
         * <p/>
         * This element is declared by the element type {@code tuplet-actual} for the element type {@code tuplet} in the note.mod schema file.
         *
         * @see Tuplet
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface TupletActual
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                TupletNumber.class,
                TupletType.class,
                TupletDot.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code TupletDot} classifies the MusicXML element, {@code tuplet-dot}.
         * <p/>
         * This element is declared by the element type {@code tuplet-dot} for the element types {@code tuplet-actual} and {@code tuplet-normal} in the note.mod schema file.
         *
         * @see TupletActual
         * @see TupletNormal
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface TupletDot
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[]{
                // font
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                // color
                Element.Attribute.Color.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code TupletNormal} classifies the MusicXML element, {@code tuplet-normal}.
         * <p/>
         * This element is declared by the element type {@code tuplet-normal} for the element type {@code tuplet} in the note.mod schema file.
         *
         * @see Tuplet
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface TupletNormal
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                TupletNumber.class,
                TupletType.class,
                TupletDot.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code TupletNumber} classifies the MusicXML element, {@code tuplet-number}.
         * <p/>
         * This element is declared by the element type {@code tuplet-number} for the element types {@code tuplet-actual} and {@code tuplet-normal} in the note.mod schema file.
         *
         * @see TupletActual
         * @see TupletNormal
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface TupletNumber
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                // font
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                // color
                Element.Attribute.Color.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code TupletType} classifies the MusicXML element, {@code tuplet-type}.
         * <p/>
         * This element is declared by the element type {@code tuplet-type} for the element types {@code tuplet-actual} and {@code tuplet-normal} in the note.mod schema file.
         *
         * @see TupletActual
         * @see TupletNormal
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface TupletType
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                // font
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                // color
                Element.Attribute.Color.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Turn} classifies the MusicXML element, {@code turn}.
         * <p/>
         * This element is declared by the element type {@code turn} for the element type {@code ornaments} in the note.mod schema file.
         * <p/>
         * The {@code turn} element is the normal turn shape which goes up then down.
         * If the {@link Element.Attribute.Slash slash} attribute is yes, then a vertical line is used to slash the turn; it is no by default.
         *
         * @see Ornaments
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Turn
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.StartNote.class,
                Element.Attribute.TrillStep.class,
                Element.Attribute.TwoNoteTurn.class,
                Element.Attribute.Accelerate.class,
                Element.Attribute.Beats.class,
                Element.Attribute.SecondBeat.class,
                Element.Attribute.LastBeat.class,
                Element.Attribute.Slash.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code Transpose} classifies the MusicXML element, {@code transpose}.
         * <p/>
         * This element is declared by the element type {@code transpose} for the element type {@code attributes} in the attributes.mod schema file.
         * <p/>
         * Type indicates the graphic note type.
         * Valid values (from shortest to longest) are 1024th, 512th, 256th, 128th, 64th, 32nd, 16th, eighth, quarter, half, whole, breve, long, and maxima.
         * The {@link Element.Attribute.Size size} attribute indicates full, cue, grace-cue, or large size.
         * The default is full for regular notes, grace-cue for notes that contain both grace and cue elements, and cue for notes that contain either a cue or a grace element, but not both.
         *
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Type
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Size.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.Type_Values;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Unpitched} classifies the MusicXML element, {@code unpitched}.
         * <p/>
         * This element is declared by the element type {@code unpitched} for the entity type {@code full-note} in the note.mod schema file.
         * <p/>
         * The {@code unpitched} element indicates musical elements that are notated on the staff but lack definite pitch, such as unpitched percussion and speaking voice.
         * Like notes, it uses {@link Step step} and {@link Octave octave} elements to indicate placement on the staff, following the current clef.
         * If percussion clef is used, the {@link DisplayStep display-step} and {@link DisplayOctave display-octave} elements are interpreted as if in treble clef, with a G in octave 4 on line 2.
         * If not present, the note is placed on the middle line of the staff, generally used for a one-line staff.
         *
         * @see Note
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Unpitched
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                DisplayStep.class,
                DisplayOctave.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code Unstress} classifies the MusicXML element, {@code unstress}.
         * <p/>
         * This element is declared by the element type {@code unstress} for the element types {@code technical} in the note.mod schema file.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Unstress
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code UpBow} classifies the MusicXML element, {@code up-bow}.
         * <p/>
         * This element is declared by the element type {@code up-bow} for the element types {@code technical} in the note.mod schema file.
         * <p/>
         * The {@code up-bow} element represents the symbol that is used both for up-bowing on bowed instruments, and up-stroke on plucked instruments.
         *
         * @see Technical
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface UpBow
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code VerticalTurn} classifies the MusicXML element, {@code vertical-turn}.
         * <p/>
         * This element is declared by the element type {@code vertical-turn} for the element type {@code ornaments} in the note.mod schema file.
         * <p/>
         * The {@code vertical-turn} element has the shape arranged vertically going from upper left to lower right.
         *
         * @see Ornaments
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface VerticalTurn
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class,
                Element.Attribute.StartNote.class,
                Element.Attribute.TrillStep.class,
                Element.Attribute.TwoNoteTurn.class,
                Element.Attribute.Accelerate.class,
                Element.Attribute.Beats.class,
                Element.Attribute.SecondBeat.class,
                Element.Attribute.LastBeat.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrMore;
            }
        }

        /**
         * {@code VirtualInstrument} classifies the MusicXML element, {@code virtual-instrument}.
         * <p/>
         * This element is declared by the element type {@code virtual-instrument} for the element type {@code score-instrument} in the score.mod schema file.
         *
         * @see ScoreInstrument
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface VirtualInstrument
        extends Analytic
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                VirtualLibrary.class,
                VirtualName.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code VirtualLibrary} classifies the MusicXML element, {@code virtual-library}.
         * <p/>
         * This element is declared by the element type {@code virtual-library} for the element type {@code virtual-instrument} in the score.mod schema file.
         *
         * @see VirtualInstrument
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface VirtualLibrary
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code VirtualName} classifies the MusicXML element, {@code virtual-name}.
         * <p/>
         * This element is declared by the element type {@code virtual-name} for the element type {@code virtual-instrument} in the score.mod schema file.
         *
         * @see VirtualInstrument
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface VirtualName
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Voice} classifies the MusicXML element, {@code voice}.
         * <p/>
         * This element is declared by the element type {@code voice} in the common.mod schema file.
         * <p/>
         * Voice is used to distinguish between multiple voices (what MuseData calls tracks) in individual parts.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Voice
        extends Analytic
        {
            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Volume} classifies the MusicXML element, {@code volume}.
         * <p/>
         * This element is declared by the element type {@code volume} for the element type {@code midi-instrument} in the common.mod schema file.
         * <p/>
         * The {@code volume} value is a percentage of the maximum ranging from 0 to 100, with decimal values allowed.
         * This corresponds to a scaling value for the MIDI 1.0 channel volume controller.
         *
         * @see MidiInstrument
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Volume
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code WavyLine} classifies the MusicXML element, {@code wavy-line}.
         * <p/>
         * This element is declared by the element type {@code wavy-line} in the common.mod schema file, for the element types {@code barline} in the barline.mod schema file; and for the element type {@code ornaments} in the note.mod schema file.
         * <p/>
         * Wavy-line element can be applied both to notes and to barlines.
         * Wavy lines are one way to indicate trills; when used with a barline element, they should always have {@link Element.Attribute.Type type}="continue" set.
         *
         * @see Barline
         * @see Ornaments
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface WavyLine
        extends
            Analytic,
            XML.Schematic.PerType
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeY.class,
                Element.Attribute.Placement.class,
                Element.Attribute.Color.class,
                Element.Attribute.ID.class,
                Element.Attribute.StartNote.class,
                Element.Attribute.TrillStep.class,
                Element.Attribute.TwoNoteTurn.class,
                Element.Attribute.Accelerate.class,
                Element.Attribute.Beats.class,
                Element.Attribute.SecondBeat.class,
                Element.Attribute.LastBeat.class,
            };

            /** The array of accepted parent element types. */
            Class<? extends Element>[] Types = (Class<? extends Element>[]) new Class<?>[] {
                Barline.class,
                Ornaments.class,
            };

            @Override
            default Class<? extends WavyLine> per(final Class<?> type) {
                if (type == Barline.class)
                    return WavyLine_PerBarline.class;
                else
                if (type == Ornaments.class)
                    return WavyLine_PerOrnaments.class;

                return null;
            }

            interface WavyLine_PerBarline
            extends WavyLine
            {
                @Override
                default java.lang.String occurrence() {
                    return Occurrence.ZeroOrOne;
                }
            }

            interface WavyLine_PerOrnaments
            extends WavyLine
            {
                @Override
                default java.lang.String occurrence() {
                    return Occurrence.ZeroOrMore;
                }
            }
        }

        /**
         * {@code Wedge} classifies the MusicXML element, {@code wedge}.
         * <p/>
         * This element is declared by the element type {@code wedge} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * Wedge spread is measured in tenths of staff line space.
         * The {@link Element.Attribute.Type type} is crescendo for the start of a {@code wedge} that is closed at the left side, and diminuendo for the start of a {@code wedge} that is closed on the right side.
         * Spread values at the start of a crescendo {@code wedge} or end of a diminuendo {@code wedge} are ignored.
         * The {@link Element.Attribute.Niente niente} attribute is yes if a circle appears at the point of the {@code wedge}, indicating a crescendo from nothing or diminuendo to nothing.
         * It is no by default, and used only when the {@link Element.Attribute.Type type} is crescendo, or the {@link Element.Attribute.Type type} is stop for a {@code wedge} that began with a diminuendo type.
         * The {@link Element.Attribute.LineType line-type} is solid by default.
         * The continue type is used for formatting {@code wedge}s over a system break, or for other situations where a single {@code wedge} is divided into multiple segments.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Wedge
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Type.class,
                Element.Attribute.Number.class,
                Element.Attribute.Spread.class,
                Element.Attribute.Niente.class,
                Element.Attribute.LineType.class,
                Element.Attribute.DashLength.class,
                Element.Attribute.SpaceLength.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.Color.class,
                Element.Attribute.ID.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code WithBar} classifies the MusicXML element, {@code with-bar}.
         * <p/>
         * This element is declared by the element type {@code with-bar} for the element type {@code bend} in the note.mod schema file.
         *
         * @see Bend
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface WithBar
        extends Analytic
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.Placement.class
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Wood} classifies the MusicXML element, {@code wood}.
         * <p/>
         * This element is declared by the element type {@code wood} for the element type {@code percussion} in the direction.mod schema file.
         * <p/>
         * The wood element represents pictograms for wood percussion instruments.
         * Valid values are bamboo scraper, board clapper, cabasa, castanets, castanets with handle, claves, football rattle, guiro, log drum, maraca, maracas, quijada, rainstick, ratchet, reco-reco, sandpaper blocks, slit drum, temple block, vibraslap, whip, and wood block.
         * The maraca and maracas values distinguish the one- and two-maraca versions of the pictogram.
         *
         * @see Percussion
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Wood
        extends Analytic
        {
            /** The array of accepted values. */
            java.lang.String[] Values = new java.lang.String[] {
                BAMBOO__SCRAPER,
                BOARD__CLAPPER,
                CABASA,
                CASTANETS,
                CASTANETS__WITH__HANDLE,
                CLAVES,
                FOOTBALL__RATTLE,
                GUIRO,
                LOG__DRUM,
                MARACA,
                MARACAS,
                QUIJADA,
                RAINSTICK,
                RATCHET,
                RECO_RECO,
                SANDPAPER__BLOCKS,
                SLIT__DRUM,
                TEMPLE__BLOCK,
                VIBRASLAP,
                WHIP,
                WOOD__BLOCK
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OnlyOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OnlyOne;
            }
        }

        /**
         * {@code WordFont} classifies the MusicXML element, {@code word-font}.
         * <p/>
         * This element is declared by the element type {@code word-font} for the element type {@code defaults} in the score.mod schema file.
         *
         * @see Defaults
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface WordFont
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                // font
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Empty;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code Words} classifies the MusicXML element, {@code words}.
         * <p/>
         * This element is declared by the element type {@code words} for the element type {@code direction-type} in the direction.mod schema file.
         * <p/>
         * The {@code words} element specifies a standard text direction.
         * Left justification is assumed if not specified.
         * Language is Italian ("it") by default.
         * Enclosure is none by default.
         *
         * @see DirectionType
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Words
        extends Element
        {
            /** The array of supported attributes. */
            Class<? extends Element.Attribute>[] Attributes = (Class<? extends Element.Attribute>[]) new Class<?>[] {
                Element.Attribute.Justify.class,
                Element.Attribute.DefaultX.class,
                Element.Attribute.DefaultY.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.RelativeX.class,
                Element.Attribute.FontFamily.class,
                Element.Attribute.FontStyle.class,
                Element.Attribute.FontSize.class,
                Element.Attribute.FontWeight.class,
                Element.Attribute.Color.class,
                Element.Attribute.HAlign.class,
                Element.Attribute.VAlign.class,
                Element.Attribute.Underline.class,
                Element.Attribute.Overline.class,
                Element.Attribute.LineThrough.class,
                Element.Attribute.Rotation.class,
                Element.Attribute.LetterSpacing.class,
                Element.Attribute.LineHeight.class,
                Element.Attribute.XmlLang.class,
                Element.Attribute.XmlSpace.class,
                Element.Attribute.Dir.class,
                Element.Attribute.Enclosure.class,
                Element.Attribute.ID.class,
            };

            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#OneOrMore}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.OneOrMore;
            }
        }

        /**
         * {@code Work} classifies the MusicXML element, {@code work}.
         * <p/>
         * This element is declared by the element type {@code work} for the entity type {@code score-header} in the score.mod schema file.
         * <p/>
         * Works are optionally identified by {@link WorkNumber number} and {@link WorkTitle title}.
         * The {@code work} element also may indicate a link to the {@link Opus opus} document that composes multiple movements into a collection.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Work
        extends Element
        {
            /** The array of supported elements. */
            Class<? extends Element>[] Elements = (Class<? extends Element>[]) new Class<?>[] {
                WorkNumber.class,
                WorkTitle.class,
                Opus.class,
            };

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code WorkNumber} classifies the MusicXML element, {@code work-number}.
         * <p/>
         * This element is declared by the element type {@code work-number} for the element type {@code work} in the score.mod schema file.
         *
         * @see Work
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface WorkNumber
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }

        /**
         * {@code WorkTitle} classifies the MusicXML element, {@code work-title}.
         * <p/>
         * This element is declared by the element type {@code work-title} for the element type {@code work} in the score.mod schema file.
         *
         * @see Work
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface WorkTitle
        extends Element
        {
            /** The array of accepted values. */
            java.lang.String[] Values = Entity.PCDATA_Literal;

            /**
            * {@inheritDoc}
            *
            * @return {@link Occurrence#ZeroOrOne}.
            */
            @Override
            default java.lang.String occurrence() {
                return Occurrence.ZeroOrOne;
            }
        }
    }

    /**
     * {@code Element} classifies all MusicXML entities referenced inside element or attribute class definitions.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Entity
    {
        /** The XML entity literal, CDATA. */
        java.lang.String[] CDATA_Literal = new java.lang.String[] {
            CDATA
        };

        /** The XML entity literal, ID. */
        java.lang.String[] ID_Literal = new java.lang.String[] {
            Constant.MusicXML.Entity.ID
        };

        /** The XML entity literal, IDREF. */
        java.lang.String[] IDREF_Literal = new java.lang.String[] {
            Constant.MusicXML.Entity.IDREF
        };

        /** The XML entity literal, NMTOKEN. */
        java.lang.String[] NMTOKEN_Literal = new java.lang.String[] {
            NMTOKEN
        };

        /** The XML entity literal, PCDATA. */
        java.lang.String[] PCDATA_Literal = new java.lang.String[] {
            PCDATA
        };

        /** The values for {@code above-below} entity type is declared in the common.mod schema file. */
        java.lang.String[] AboveBelow_Values = new java.lang.String[] {
            ABOVE,
            BELOW
        };

        /** The values for {@code accidental} and {@code key-accidental} element types are declared in the note.mod schema file. */
        java.lang.String[] Accidental_Values = new java.lang.String[] {
            ARROW_DOWN,
            ARROW_UP,
            DOUBLE_SHARP,
            DOUBLE_SHARP_DOWN,
            DOUBLE_SHARP_UP,
            DOUBLE_SLASH_FLAT,
            FLAT,
            FLAT_1,
            FLAT_2,
            FLAT_3,
            FLAT_4,
            FLAT_DOWN,
            FLAT_FLAT,
            FLAT_FLAT_DOWN,
            FLAT_FLAT_UP,
            FLAT_UP,
            KORON,
            NATURAL,
            NATURAL_DOWN,
            NATURAL_FLAT,
            NATURAL_SHARP,
            NATURAL_UP,
            OTHER,
            QUARTER_FLAT,
            QUARTER_SHARP,
            SLASH_FLAT,
            SLASH_QUARTER_SHARP,
            SLASH_SHARP,
            SHARP,
            SHARP_1,
            SHARP_2,
            SHARP_3,
            SHARP_5,
            SHARP_DOWN,
            SHARP_SHARP,
            SHARP_UP,
            SORI,
            THREE_QUARTERS_FLAT,
            THREE_QUARTERS_SHARP,
            TRIPLE_FLAT,
            TRIPLE_SHARP
        };

        /** The values for {@code tuplet} and {@code metronome-tuplet} elements are declared and used separately in the note.mod and direction.mod schema files. */
        java.lang.String[] ActualBothNone_Values = new java.lang.String[] {
            ACTUAL,
            BOTH,
            NONE
        };

        /** The values for {@code beam-level} entity type is declared in the common.mod schema file. */
        java.lang.String[] BeamLevel_Values = new java.lang.String[] {
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8"
        };

        /** The values for {@code enclosure-shape} entity type is declared in the common.mod schema file. */
        java.lang.String[] EnclosureShape_Values = new java.lang.String[] {
            BRACKET,
            CIRCLE,
            DECAGON,
            DIAMOND,
            HEPTAGON,
            HEXAGON,
            NONAGON,
            NONE,
            OCTAGON,
            OVAL,
            PENTAGON,
            RECTANGLE,
            SQUARE,
            TRIANGLE
        };

        /** The values for {@code layout-tenths} entity type is declared in the common.mod schema file. */
        java.lang.String[] LayoutTenths_Values = PCDATA_Literal;

        /** The values for {@code left-right} entity type is declared in the common.mod schema file. */
        java.lang.String[] LeftRight_Values = new java.lang.String[] {
            LEFT,
            RIGHT
        };

        /** The values for {@code number-level} entity type is declared in the common.mod schema file. */
        java.lang.String[] NumberLevel_Values = new java.lang.String[] {
            "1",
            "2",
            "3",
            "4",
            "5",
            "6"
        };

        /** The values for {@code number-of-lines} entity type is declared in the common.mod schema file. */
        java.lang.String[] NumberOfLines_Values = new java.lang.String[] {
            "0",
            "1",
            "2",
            "3"
        };

        /** The values for {@code orientation} entity type is declared in the common.mod schema file. */
        java.lang.String[] Orientation_Values = new java.lang.String[] {
            OVER,
            UNDER
        };

        /** The values for {@code smufl-glyph-name} entity type is declared in the common.mod schema file. */
        java.lang.String[] SMuFLGlyphName_Values = NMTOKEN_Literal;

        /** The values for {@code start-stop} entity type is declared in the common.mod schema file. */
        java.lang.String[] StartStop_Values = new java.lang.String[] {
            START,
            STOP
        };

        /** The values for {@code start-stop-continue} entity type is declared in the common.mod schema file. */
        java.lang.String[] StartStopContinue_Values = new java.lang.String[] {
            CONTINUE,
            START,
            STOP
        };

        /** The values for {@code start-stop-single} entity type is declared in the common.mod schema file. */
        java.lang.String[] StartStopSingle_Values = new java.lang.String[] {
            SINGLE,
            START,
            STOP
        };

        /** The values for {@code symbol-size} entity type is declared in the common.mod schema file. */
        java.lang.String[] SymbolSize_Values = new java.lang.String[] {
            CUE,
            FULL,
            GRACE_CUE,
            LARGE
        };

        /** The values for {@code tenths} entity type is declared in the common.mod schema file. */
        java.lang.String[] Tenths_Values = CDATA_Literal;

        /** The values for {@code tied-type} entity type is declared in the common.mod schema file. */
        java.lang.String[] TiedType_Values = new java.lang.String[] {
            CONTINUE,
            LET_RING,
            START,
            STOP
        };

        /** The values for {@code tremolo-type} entity type is declared in the common.mod schema file. */
        java.lang.String[] TremoloType_Values = new java.lang.String[] {
            SINGLE,
            START,
            STOP,
            UNMEASURED
        };

        /** The values for {@code top-bottom} entity type is declared in the common.mod schema file. */
        java.lang.String[] TopBottom_Values = new java.lang.String[] {
            BOTTOM,
            TOP
        };

        /** The values for {@code top-direction} entity type is declared in the direction.mod schema file. */
        java.lang.String[] TopDirection_Values = new java.lang.String[] {
            DOWN,
            LEFT,
            NORTHEAST,
            NORTHWEST,
            RIGHT,
            SOUTHEAST,
            SOUTHWEST,
            UP
        };

        /** The values for {@code slash-type} and {@code type} element types are declared in the note.mod schema file. */
        java.lang.String[] Type_Values = new java.lang.String[] {
            BREVE,
            EIGHTH,
            FIVE_HUNDRED_TWELVTH,
            HALF,
            LONG,
            MAXIMA,
            HUNDRED_TWENTY_EIGHTH,
            QUARTER,
            SIXTEENTH,
            SIXTY_FOURTH,
            THIRTY_SECOND,
            THOUSAND_TWENTY_FOURTH,
            TWO_HUNDRED_FIFTY_SIXTH,
            WHOLE
        };

        /** The values for {@code up-down} entity type is declared in the common.mod schema file. */
        java.lang.String[] UpDown_Values = new java.lang.String[] {
            DOWN,
            UP
        };

        /** The values for {@code valign} entity type is declared in the common.mod schema file. */
        java.lang.String[] VAlign_Values = new java.lang.String[] {
            BASELINE,
            BOTTOM,
            MIDDLE,
            TOP
        };

        /** The values for {@code valign-image} entity type is declared in the common.mod schema file. */
        java.lang.String[] VAlignImage_Values = new java.lang.String[] {
            BOTTOM,
            MIDDLE,
            TOP
        };

        /** The values for {@code yes-no} entity type is declared in the common.mod schema file. */
        java.lang.String[] YesNo_Values = new java.lang.String[] {
            NO,
            YES
        };

        /** The values for {@code yes-no-number} entity type is declared in the common.mod schema file. */
        java.lang.String[] YesNoNumber_Values = NMTOKEN_Literal;

        /** The values for {@code yyyy-mm-dd} entity type is declared in the common.mod schema file. */
        java.lang.String[] YYYY_MM_DD_Values = PCDATA_Literal;
    }

    /**
     * {@code Handler} classifies the document handler for MusicXML documents.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Handler
    extends Document.Handler
    {
        /**
         * {@code Analytic} is an implementation of a basic MusicXML document handler that only accepts MusicXML elements and attributes and filters out the cosmetic attributes.
         * <p/>
         * This class implementation is in progress.
         *
         * @see Basic
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        class Analytic
        extends Basic
        {
            /** The analytic score part elements. */
            private static final
            Cache.Table AnalyticLevel2Elements
            = new Cache.Table(
                WORK,
                MOVEMENT_TITLE,
                MOVEMENT_NUMBER,
                IDENTIFICATION,
                PART_LIST
                );

            /** The analytic music data elements. */
            private static final
            Cache.Table AnalyticMusicElements
            = new Cache.Table(
                ATTRIBUTES,
                BACKUP,
                BARLINE,
                BOOKMARK,
                DIRECTION,
                FIGURED_BASS,
                FORWARD,
                GROUPING,
                HARMONY,
                LINK,
                NOTE,
                SOUND
                );

            /** The analytic cosmetic attributes. */
            private static final
            Cache.Table AnalyticUnacceptedAttributes
            = new Cache.Table(
                BEZIER_OFFSET,
                BEZIER_OFFSET2,
                BEZIER_X,
                BEZIER_X2,
                BEZIER_Y,
                BEZIER_Y2,
                COLOR,
                DASH_LENGTH,
                DEFAULT_X,
                DEFAULT_Y,
                FONT_FAMILY,
                FONT_SIZE,
                FONT_STYLE,
                FONT_WEIGHT,
                HEIGHT,
                LETTER_SPACING,
                LINE_HEIGHT,
                PRINT_DOT,
                PRINT_LYRIC,
                PRINT_OBJECT,
                PRINT_SPACING,
                RELATIVE_X,
                RELATIVE_Y,
                SPACE_LENGTH,
                WIDTH,
                XML_SPACE
                );

            /**
             * Creates an analytic MusicXML document handler with the specified document.
             *
             * @param document the document.
             */
            public
            Analytic(org.w3c.dom.Document document) {
                super(document);
            }

            /**
             * Returns true if the specified element tag name is acceptable at the current parser state; otherwise returns false.
             *
             * @param depth the parser depth.
             * @param stack the parser stack of visited elements.
             * @param qName the element tag name.
             *
             * @return true if the element is acceptable, and false otherwise.
             */
            protected
            boolean isAcceptableElement(
                final int depth,
                final LinkedList<Node> stack,
                final java.lang.String qName
                ) {
                if (depth != stack.size())
                    return false;

                switch (depth) {
                    // ---------- Level 1
                    // acceptable if element is <score-partwise> or <score-timewise>
                    case 0:
                        return qName.equals(SCORE_PARTWISE) ||
                               qName.equals(SCORE_TIMEWISE);

                    // ---------- Level 2
                    // acceptable if element is <work>, <movement-title>, <movement-number>, <identification> or <part-list>
                    //         or if element is <part> or <measure> depending on the the score type
                    case 1:
                        java.lang.String parent = ((org.w3c.dom.Element) stack.peek()).getTagName();
                        return AnalyticLevel2Elements.contains(qName) ||

                               (parent.equals(SCORE_PARTWISE) &&
                               qName.equals(PART) ||

                               (parent.equals(SCORE_TIMEWISE) &&
                               qName.equals(MEASURE)));

                    // ---------- Level 3
                    // acceptable if element is <work-title> or <work-number> and the parent element is <work>
                    //         or if element is <creator> or <rights> and the parent element is <identification>
                    //         or if element is <score-part> and the parent element is <part-list>
                    //         or if element is <measure> and the parent element is <part> or vice versa
                    case 2:
                        parent = ((org.w3c.dom.Element) stack.peek()).getTagName();
                        return (parent.equals(WORK) &&
                               (qName.equals(WORK_TITLE) ||
                               qName.equals(WORK_NUMBER))) ||

                               (parent.equals(IDENTIFICATION) &&
                               (qName.equals(CREATOR) || qName.equals(RIGHTS))) ||

                               (parent.equals(PART_LIST) &&
                               qName.equals(SCORE_PART)) ||

                               ((parent.equals(PART) &&
                               qName.equals(MEASURE)) ||

                               (parent.equals(MEASURE) &&
                               qName.equals(PART)));

                    // ---------- Level 4
                    // acceptable if element is <part-name> or <score-instrument> and the parent element is <score-part>
                    //         or if element is not <print> and the parent element is <measure> or <part>
                    case 3:
                        parent = ((org.w3c.dom.Element) stack.peek()).getTagName();
                        return (parent.equals(SCORE_PART) &&
                               (qName.equals(PART_NAME) ||
                               qName.equals(SCORE_INSTRUMENT))) ||

                               ((parent.equals(MEASURE) || parent.equals(PART)) &&
                               AnalyticMusicElements.contains(qName));

                    // ---------- Other levels
                    // acceptable if level is 5 and element is <instrument-name> or <instrument-sound> and the parent element is <score-instrument>
                    //         or if the level-4th ancestor of element is an acceptable music data element
                    default:
                        return (depth == 4 &&
                               ((org.w3c.dom.Element) stack.peek()).getTagName().equals(SCORE_INSTRUMENT) &&
                               (qName.equals(INSTRUMENT_NAME) ||
                               qName.equals(INSTRUMENT_SOUND))) ||

                               AnalyticMusicElements.contains(((org.w3c.dom.Element) stack.get(3)).getTagName());
                }
            }

            /**
             * Returns true if the specified element tag name and attribute name are mutually supported at the current parser state; otherwise returns false.
             *
             * @param depth the parser depth.
             * @param stack the parser stack of visited elements.
             * @param qName the element tag name.
             * @param aName the attribute name.
             *
             * @return true if the element attribute is acceptable, and false otherwise.
             */
            protected
            boolean isAcceptableAttribute(
                final int depth,
                final LinkedList<Node> stack,
                final java.lang.String qName,
                final java.lang.String aName
                ) {
                switch (depth) {
                    // ---------- Level 2
                    // acceptable if element is <part> and attribute name is 'id'
                    //            or element is <measure> and attribute name is 'number'
                    case 1:
                        return (qName.equals(PART) &&
                               aName.equals(Constant.MusicXML.ID)) ||

                               (qName.equals(MEASURE) &&
                               aName.equals(NUMBER));

                    // ---------- Level 3
                    // acceptable if element is <creator> and attribute name is 'type'
                    //            or element is <score-part> and attribute name is 'id'
                    case 2:
                        return (qName.equals(CREATOR) &&
                               aName.equals(TYPE)) ||

                               (qName.equals(SCORE_PART) &&
                               aName.equals(Constant.MusicXML.ID)) ||

                               (qName.equals(MEASURE) &&
                               aName.equals(NUMBER)) ||

                               (qName.equals(PART) &&
                               aName.equals(Constant.MusicXML.ID));

                    // ---------- Level 4
                    // acceptable if element is <score-instrument> and attribute name is 'id'
                    //            or element is <sound> and attribute name is 'tempo'
                    case 3:
                        return (qName.equals(SCORE_INSTRUMENT) &&
                               aName.equals(Constant.MusicXML.ID)) ||

                               (qName.equals(SOUND) &&
                               aName.equals(TEMPO));

                    // ---------- Other levels
                    // filter out the unacceptable attributes
                    default:
                        return !AnalyticUnacceptedAttributes.contains(aName);
                }
            }

            /**
             * {@inheritDoc}
             * <p/>
             * This implementation only processes the accepted element texts.
             *
             * @param ch the characters.
             * @param start the start position in the character array.
             * @param length the number of characters to use from the character array.
             *
             * @throws IllegalStateException if the document is closed.
             */
            @Override
            public void characters(final char[] ch, final int start, final int length) throws SAXException {
                if (closed)
                    throw new IllegalStateException();

                if (stack.isEmpty() || stack.size() != depth)
                    return;

                final java.lang.String parent = ((org.w3c.dom.Element) stack.peek()).getTagName();

                // If characters are for an acceptable elements at...
                if ((
                   // ---------- Level 3
                   (depth == 2 &&
                   (parent.equals(MOVEMENT_TITLE) ||
                   parent.equals(MOVEMENT_NUMBER))) ||

                   // ---------- Level 4
                   (depth == 3 &&
                   (parent.equals(WORK_TITLE) ||
                   parent.equals(WORK_NUMBER) ||
                   parent.equals(CREATOR) ||
                   parent.equals(RIGHTS))) ||

                   // ---------- Level 5
                   (depth == 4 &&
                   parent.equals(PART_NAME)) ||

                   // ---------- Level 6
                   (depth == 5 &&
                   (parent.equals(INSTRUMENT_NAME) ||
                   parent.equals(INSTRUMENT_SOUND))) ||

                   // ---------- Level 6 or higher
                   (depth >= 5 &&
                   (((org.w3c.dom.Element) stack.get(1)).getTagName().equals(PART) ||
                   ((org.w3c.dom.Element) stack.get(1)).getTagName().equals(MEASURE))))) {

                    // Append the characters to the element at the top of the stack
                    stack.peek().appendChild(document.createTextNode(new java.lang.String(ch, start, length)));
                }
            }

            /**
             * {@inheritDoc}
             * <p/>
             * This implementation only accepts the standard MusicXML document at their correct location and bypasses any other element.
             *
             * @param uri the namespace URI.
             * @param localName the local name.
             * @param qName the qualified name.
             * @param attributes the element attributes.
             *
             * @throws IllegalStateException if the document is closed.
             */
            @Override
            public void startElement(final java.lang.String uri, final java.lang.String localName, final java.lang.String qName, final Attributes attributes) throws SAXException {
                if (closed)
                    throw new IllegalStateException();

                // If the element is acceptable...
                if (isAcceptableElement(depth, stack, qName)) {

                    // Create a placeholder element
                    final org.w3c.dom.Element e = document.createElement(qName);

                    // Remove the unacceptable attributes from the element
                    for (int i = 0; i < attributes.getLength(); i++) {
                        final java.lang.String aName = attributes.getQName(i);
                        if (isAcceptableAttribute(depth, stack, qName, aName))
                            e.setAttribute(aName, attributes.getValue(i));
                    }

                    // Push the element to the stack
                    stack.push(e);
                }

                depth++;
            }
        }

        /**
         * {@code Basic} is an implementation of a basic document handler that only accepts MusicXML elements and attributes.
         *
         * @see DocumentHandler.Basic
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        class Basic
        extends DocumentHandler.Basic
        {
            /**
             * Creates a basic MusicXML document handler with the specified document.
             *
             * @param document the document.
             */
            public
            Basic(org.w3c.dom.Document document) {
                super(document);
            }
        }

        /**
         * {@code Standard} is an implementation of a standard document handler that only accepts MusicXML elements and attributes.
         * <p/>
         * This class implementation is in progress.
         *
         * @see DocumentHandler.Standard
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        class Standard
        extends DocumentHandler.Standard
        implements Handler
        {
            /**
             * Creates a standard MusicXML document handler with the specified document.
             *
             * @param document the document.
             */
            public
            Standard(org.w3c.dom.Document document) {
                super(document);
            }
        }
    }

    /**
     * {@code RequiredAttribute} classifies all required MusicXML element attributes.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface RequiredAttribute
    extends Element.Attribute
    {
        @Override
        default boolean isRequired() {
            return true;
        }
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
    implements XML.Validation
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
        @Override
        public abstract
        boolean isPassed();
    }
}