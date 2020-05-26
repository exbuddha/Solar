package musical;

import music.system.data.MusicXML;

/**
 * {@code Constant} holds all commonly accepted names and descriptions in music theory.
 */
public final
class Constant
{
    /**
     * {@code Clef} holds all commonly known clef symbols in music.
     */
    public
    interface Clef
    {
        final String C = "C";
        final String F = "F";
        final String G = "G";
        final String Percussion = "P";
        final String TAB = "TAB";
    }

    /**
     * {@code Duration} holds all commonly known note duration symbols in music.
     */
    public
    interface Duration
    {
        final String OctupleWhole = "32/1";
        final String QuadrupleWhole = "16/1";
        final String DoubleWhole = "8/1";
        final String Whole = "4/1";
        final String Half = "2/1";
        final String Quarter = "1/1";
        final String Eighth = "1/2";
        final String Sixteenth = "1/4";
        final String ThirtySecond = "1/8";
        final String SixtyFourth = "1/16";
        final String HundredTwentyEighth = "1/32";
        final String TwoHundredFiftySixth = "1/64";
        final String Dotted = ".";
        final String DoubleDotted = "..";
        final String DottedWhole = "4/1 * 3/2";
        final String DoubleDottedWhole = "4/1 * 7/4";
        final String DottedHalf = "2/1 * 3/2";
        final String DoubleDottedHalf = "2/1 * 7/4";
        final String DottedQuarterSym = "1/1 * 3/2";
        final String DoubleDottedQuarterSym = "1/1 * 7/4";
        final String DottedEighthSym = "1/2 * 3/2";
        final String DoubleDottedEighthSym = "1/2 * 7/4";
        final String DottedSixteenthSym = "1/4 * 3/2";
        final String DoubleDottedSixteenthSym = "1/4 * 7/4";
        final String DottedThirtySecondSym = "1/8 * 3/2";
        final String DottedSixtyFourthSym = "1/16 * 3/2";
    }

    /**
     * {@code Interval} holds all commonly known interval symbols in music.
     */
    public
    interface Interval
    {
        final String PerfectUnison = "P1";
        final String MinorSecond = "m2";
        final String MajorSecond = "M2";
        final String MinorThird = "m3";
        final String MajorThird = "M3";
        final String PerfectFourth = "P4";
        final String AugmentedFourth = "A4";
        final String DiminishedFifth = "d5";
        final String PerfectFifth = "P5";
        final String MinorSixth = "m6";
        final String MajorSixth = "M6";
        final String MinorSeventh = "m7";
        final String MajorSeventh = "M7";
        final String PerfectOctave = "P8";
    }

    /**
     * {@code Scale} holds all commonly known scale symbols in music.
     */
    public
    interface Scale
    {
        final String Chromatic = "Chromatic";
        final String Major = "Major";
        final String Dorian = "Dorian";
        final String Phrygian = "Phrygian";
        final String Lydian = "Major";
        final String Mixolydian = "Mixolydian";
        final String Minor = "Minor";
        final String Locrian = "Locrian";
        final String WholeTone = "Whole Tone";
        final String MajorPentatonic = "Major Pentatonic";
        final String MinorPentatonic = "Minor Pentatonic";
        final String Egyptian = "Egyptian";
        final String BluesMajor = "Blues Major";
        final String BluesMinor = "Blues Minor";
    }

    /**
     * {@code Note} holds all commonly known note-related symbols in music.
     */
    public
    interface Note
    {
        /**
         * {@code Accidental} holds all commonly known accidental symbols in music.
         */
        interface Accidental
        {
            final String DoubleFlat = "bb";
            final String DoubleSharp = "##";
            final String Flat = "b";
            final String Natural = "";
            final String NaturalFlat = " b";
            final String NaturalSharp = " #";
            final String Sharp = "#";
        }

        /**
         * {@code Dynamics} holds all commonly known dynamics symbols in music.
         */
        interface Dynamics
        {
            final String ForteName = "Forte";
            final String ForteSym = MusicXML.Constant.Dynamics.F;
            final String FortissimoName = "Fortissimo";
            final String FortissimoSym = MusicXML.Constant.Dynamics.FF;
            final String FortississimoName = "Fortississimo";
            final String FortississimoSym = MusicXML.Constant.Dynamics.FFF;
            final String MezzoForteName = "Forte";
            final String MezzoForteSym = MusicXML.Constant.Dynamics.MF;
            final String MezzoPianoName = "Mezzo-piano";
            final String MezzoPianoSym = MusicXML.Constant.Dynamics.MP;
            final String PianoName = "Piano";
            final String PianoSym = MusicXML.Constant.Dynamics.P;
            final String PianissimoName = "Pianossimo";
            final String PianissimoSym = MusicXML.Constant.Dynamics.PP;
            final String PianississimoName = "Pianossissimo";
            final String PianississimoSym = MusicXML.Constant.Dynamics.PPP;
        }

        /**
         * {@code Pitch} holds all commonly known pitch symbols in music.
         */
        interface Pitch
        {
            final String A = MusicXML.Constant.A;
            final String B = MusicXML.Constant.B;
            final String C = MusicXML.Constant.C;
            final String D = MusicXML.Constant.D;
            final String E = MusicXML.Constant.E;
            final String F = MusicXML.Constant.F;
            final String G = MusicXML.Constant.G;
        }
    }
}