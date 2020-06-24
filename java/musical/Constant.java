package musical;

import music.system.data.MusicXML;

/**
 * {@code Constant} holds all commonly accepted symbols and names in music theory.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public final
class Constant
{
    /**
     * {@code Clef} holds all commonly known clef symbols in music.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Clef
    {
        static final String C = "C";
        static final String F = "F";
        static final String G = "G";
        static final String Percussion = "Perc";
        static final String TAB = "TAB";
    }

    /**
     * {@code Duration} holds all commonly known note duration or stem symbols in music.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Duration
    extends system.data.Constant.Fraction
    {
        // Simple stem types
        static final String GraceSym = duration("0", "/8");
        static final String OctupleWholeSym = duration("8", "1");
        static final String QuadrupleWholeSym = duration("4", "1");
        static final String DoubleWholeSym = duration("2", "1");
        static final String WholeSym = duration("1", "1");
        static final String HalfSym = duration("1", "2");
        static final String QuarterSym = duration("1", "4");
        static final String EighthSym = duration("1", "8");
        static final String SixteenthSym = duration("1", "16");
        static final String ThirtySecondSym = duration("1", "32");
        static final String SixtyFourthSym = duration("1", "64");
        static final String HundredTwentyEighthSym = duration("1", "128");
        static final String TwoHundredFiftySixthSym = duration("1", "256");

        // Stem adjustments
        static final String UnitSym = "1";
        static final String DottedSym = duration("3", "2");
        static final String DoubleDottedSym = duration("7", "4");
        static final String TripleDottedSym = duration("15", "8");

        static final String TripletSym = duration("1", "3");
        static final String QuintupletSym = duration("1", "5");
        static final String SextupletSym = duration("1", "6");
        static final String SeptupletSym = duration("1", "7");

        static final String DupletSym = duration("3", "2");
        static final String QuadrupletSym = duration("3", "4");

        static final String MultiplierSym = " * ";

        // Adjusted stem types
        static final String DottedOctupleWholeSym = adjusted(OctupleWholeSym, DottedSym);
        static final String DoubleDottedOctupleWholeSym = adjusted(OctupleWholeSym, DoubleDottedSym);
        static final String TripleDottedOctupleWholeSym = adjusted(OctupleWholeSym, TripleDottedSym);
        static final String DottedQuadrupleWholeSym = adjusted(QuadrupleWholeSym, DottedSym);
        static final String DoubleDottedQuadrupleWholeSym = adjusted(QuadrupleWholeSym, DoubleDottedSym);
        static final String TripleDottedQuadrupleWholeSym = adjusted(QuadrupleWholeSym, TripleDottedSym);
        static final String DottedDoubleWholeSym = adjusted(DoubleWholeSym, DottedSym);
        static final String DoubleDottedDoubleWholeSym = adjusted(DoubleWholeSym, DoubleDottedSym);
        static final String TripleDottedDoubleWholeSym = adjusted(DoubleWholeSym, TripleDottedSym);
        static final String DottedWholeSym = adjusted(WholeSym, DottedSym);
        static final String DoubleDottedWholeSym = adjusted(WholeSym, DoubleDottedSym);
        static final String TripleDottedWholeSym = adjusted(WholeSym, TripleDottedSym);
        static final String DottedHalfSym = adjusted(HalfSym, DottedSym);
        static final String DoubleDottedHalfSym = adjusted(HalfSym, DoubleDottedSym);
        static final String TripleDottedHalfSym = adjusted(HalfSym, TripleDottedSym);
        static final String DottedQuarterSym = adjusted(QuarterSym, DottedSym);
        static final String DoubleDottedQuarterSym = adjusted(QuarterSym, DoubleDottedSym);
        static final String TripleDottedQuarterSym = adjusted(QuarterSym, TripleDottedSym);
        static final String DottedEighthSym = adjusted(EighthSym, DottedSym);
        static final String DoubleDottedEighthSym = adjusted(EighthSym, DoubleDottedSym);
        static final String TripleDottedEighthSym = adjusted(EighthSym, TripleDottedSym);
        static final String DottedSixteenthSym = adjusted(SixteenthSym, DottedSym);
        static final String DoubleDottedSixteenthSym = adjusted(SixteenthSym, DoubleDottedSym);
        static final String TripleDottedSixteenthSym = adjusted(SixteenthSym, TripleDottedSym);
        static final String DottedThirtySecondSym = adjusted(ThirtySecondSym, DottedSym);
        static final String DoubleDottedThirtySecondSym = adjusted(ThirtySecondSym, DoubleDottedSym);
        static final String TripleDottedThirtySecondSym = adjusted(ThirtySecondSym, TripleDottedSym);
        static final String DottedSixtyFourthSym = adjusted(SixtyFourthSym, DottedSym);
        static final String DoubleDottedSixtyFourthSym = adjusted(SixtyFourthSym, DoubleDottedSym);
        static final String TripleDottedSixtyFourthSym = adjusted(SixtyFourthSym, TripleDottedSym);
        static final String DottedHundredTwentyEighthSym = adjusted(HundredTwentyEighthSym, DottedSym);
        static final String DoubleDottedHundredTwentyEighthSym = adjusted(HundredTwentyEighthSym, DoubleDottedSym);
        static final String TripleDottedHundredTwentyEighthSym = adjusted(HundredTwentyEighthSym, TripleDottedSym);
        static final String DottedTwoHundredFiftySixthSym = adjusted(TwoHundredFiftySixthSym, DottedSym);
        static final String DoubleDottedTwoHundredFiftySixthSym = adjusted(TwoHundredFiftySixthSym, DoubleDottedSym);
        static final String TripleDottedTwoHundredFiftySixthSym = adjusted(TwoHundredFiftySixthSym, TripleDottedSym);

        /**
         * Adjusts the duration symbol by concatenating the simple duration, multiplier, and adjustment symbols.
         *
         * @param simple the simple duration symbol.
         * @param adjustment the adjustment symbol.
         *
         * @return the adjusted duration symbol.
         */
        static
        String adjusted(
            final String simple,
            final String adjustment
            ) {
            return simple + MultiplierSym + adjustment;
        }

        /**
         * Constructs the duration symbol by concatenating the two fraction part symbols with the divider symbol in the middle.
         *
         * @param numerator the numerator symbol.
         * @param denominator the denominator symbol.
         *
         * @return the duration symbol.
         */
        static
        String duration(
            final String numerator,
            final String denominator
            ) {
            return numerator + DividerSym + denominator;
        }
    }

    /**
     * {@code Interval} holds all commonly known interval symbols in music.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Interval
    {
        static final String PerfectUnisonSym = "P1";
        static final String QuarterToneSym = null;
        static final String MinorSecondSym = "m2";
        static final String MajorSecondSym = "M2";
        static final String MinorThirdSym = "m3";
        static final String MajorThirdSym = "M3";
        static final String PerfectFourthSym = "P4";
        static final String AugmentedFourthSym = "A4";
        static final String DiminishedFifthSym = "d5";
        static final String PerfectFifthSym = "P5";
        static final String MinorSixthSym = "m6";
        static final String MajorSixthSym = "M6";
        static final String MinorSeventhSym = "m7";
        static final String MajorSeventhSym = "M7";
        static final String PerfectOctaveSym = "P8";

        static final String CentsSym = "cents";

        /**
         * Adjusts the interval cents by concatenating the amount with a single whitespace and the cents symbol.
         *
         * @param cents the cents.
         *
         * @return the adjusted interval string.
         */
        static
        String adjusted(
            final short cents
            ) {
            return cents + " " + CentsSym;
        }
    }

    /**
     * {@code Scale} holds all commonly known scale names in music.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Scale
    {
        static final String ChromaticName = "Chromatic";
        static final String MajorName = "Major";
        static final String DorianName = "Dorian";
        static final String PhrygianName = "Phrygian";
        static final String LydianName = "Major";
        static final String MixolydianName = "Mixolydian";
        static final String MinorName = "Minor";
        static final String LocrianName = "Locrian";
        static final String WholeToneName = "Whole Tone";
        static final String MajorPentatonicName = "Major Pentatonic";
        static final String MinorPentatonicName = "Minor Pentatonic";
        static final String EgyptianName = "Egyptian";
        static final String BluesMajorName = "Blues Major";
        static final String BluesMinorName = "Blues Minor";
    }

    /**
     * {@code Note} holds all commonly known note-related symbols in music.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Note
    {
        // Octave -1
        static final String C_1Sym = adjusted(Pitch.CSym, Octave.NegativeFirstSym);
        static final String C_1sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.NegativeFirstSym);
        static final String D_1fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.NegativeFirstSym);
        static final String D_1Sym = adjusted(Pitch.DSym, Octave.NegativeFirstSym);
        static final String D_1sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.NegativeFirstSym);
        static final String E_1fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.NegativeFirstSym);
        static final String E_1Sym = adjusted(Pitch.ESym, Octave.NegativeFirstSym);
        static final String F_1Sym = adjusted(Pitch.FSym, Octave.NegativeFirstSym);
        static final String F_1sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.NegativeFirstSym);
        static final String G_1fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.NegativeFirstSym);
        static final String G_1Sym = adjusted(Pitch.GSym, Octave.NegativeFirstSym);
        static final String G_1sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.NegativeFirstSym);
        static final String A_1fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.NegativeFirstSym);
        static final String A_1Sym = adjusted(Pitch.ASym, Octave.NegativeFirstSym);
        static final String A_1sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.NegativeFirstSym);
        static final String B_1fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.NegativeFirstSym);
        static final String B_1Sym = adjusted(Pitch.BSym, Octave.NegativeFirstSym);

        // Octave 0
        static final String C0Sym = adjusted(Pitch.CSym, Octave.ZerothSym);
        static final String C0sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.ZerothSym);
        static final String D0fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.ZerothSym);
        static final String D0Sym = adjusted(Pitch.DSym, Octave.ZerothSym);
        static final String D0sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.ZerothSym);
        static final String E0fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.ZerothSym);
        static final String E0Sym = adjusted(Pitch.ESym, Octave.ZerothSym);
        static final String F0Sym = adjusted(Pitch.FSym, Octave.ZerothSym);
        static final String F0sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.ZerothSym);
        static final String G0fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.ZerothSym);
        static final String G0Sym = adjusted(Pitch.GSym, Octave.ZerothSym);
        static final String G0sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.ZerothSym);
        static final String A0fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.ZerothSym);
        static final String A0Sym = adjusted(Pitch.ASym, Octave.ZerothSym);
        static final String A0sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.ZerothSym);
        static final String B0fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.ZerothSym);
        static final String B0Sym = adjusted(Pitch.BSym, Octave.ZerothSym);

        // Octave 1
        static final String C1Sym = adjusted(Pitch.CSym, Octave.FirstSym);
        static final String C1sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.FirstSym);
        static final String D1fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.FirstSym);
        static final String D1Sym = adjusted(Pitch.DSym, Octave.FirstSym);
        static final String D1sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.FirstSym);
        static final String E1fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.FirstSym);
        static final String E1Sym = adjusted(Pitch.ESym, Octave.FirstSym);
        static final String F1Sym = adjusted(Pitch.FSym, Octave.FirstSym);
        static final String F1sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.FirstSym);
        static final String G1fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.FirstSym);
        static final String G1Sym = adjusted(Pitch.GSym, Octave.FirstSym);
        static final String G1sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.FirstSym);
        static final String A1fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.FirstSym);
        static final String A1Sym = adjusted(Pitch.ASym, Octave.FirstSym);
        static final String A1sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.FirstSym);
        static final String B1fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.FirstSym);
        static final String B1Sym = adjusted(Pitch.BSym, Octave.FirstSym);

        // Octave 2
        static final String C2Sym = adjusted(Pitch.CSym, Octave.SecondSym);
        static final String C2sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.SecondSym);
        static final String D2fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.SecondSym);
        static final String D2Sym = adjusted(Pitch.DSym, Octave.SecondSym);
        static final String D2sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.SecondSym);
        static final String E2fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.SecondSym);
        static final String E2Sym = adjusted(Pitch.ESym, Octave.SecondSym);
        static final String F2Sym = adjusted(Pitch.FSym, Octave.SecondSym);
        static final String F2sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.SecondSym);
        static final String G2fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.SecondSym);
        static final String G2Sym = adjusted(Pitch.GSym, Octave.SecondSym);
        static final String G2sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.SecondSym);
        static final String A2fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.SecondSym);
        static final String A2Sym = adjusted(Pitch.ASym, Octave.SecondSym);
        static final String A2sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.SecondSym);
        static final String B2fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.SecondSym);
        static final String B2Sym = adjusted(Pitch.BSym, Octave.SecondSym);

        // Octave 3
        static final String C3Sym = adjusted(Pitch.CSym, Octave.ThirdSym);
        static final String C3sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.ThirdSym);
        static final String D3fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.ThirdSym);
        static final String D3Sym = adjusted(Pitch.DSym, Octave.ThirdSym);
        static final String D3sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.ThirdSym);
        static final String E3fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.ThirdSym);
        static final String E3Sym = adjusted(Pitch.ESym, Octave.ThirdSym);
        static final String F3Sym = adjusted(Pitch.FSym, Octave.ThirdSym);
        static final String F3sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.ThirdSym);
        static final String G3fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.ThirdSym);
        static final String G3Sym = adjusted(Pitch.GSym, Octave.ThirdSym);
        static final String G3sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.ThirdSym);
        static final String A3fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.ThirdSym);
        static final String A3Sym = adjusted(Pitch.ASym, Octave.ThirdSym);
        static final String A3sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.ThirdSym);
        static final String B3fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.ThirdSym);
        static final String B3Sym = adjusted(Pitch.BSym, Octave.ThirdSym);

        // Octave 4
        static final String C4Sym = adjusted(Pitch.CSym, Octave.FourthSym);
        static final String C4sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.FourthSym);
        static final String D4fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.FourthSym);
        static final String D4Sym = adjusted(Pitch.DSym, Octave.FourthSym);
        static final String D4sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.FourthSym);
        static final String E4fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.FourthSym);
        static final String E4Sym = adjusted(Pitch.ESym, Octave.FourthSym);
        static final String F4Sym = adjusted(Pitch.FSym, Octave.FourthSym);
        static final String F4sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.FourthSym);
        static final String G4fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.FourthSym);
        static final String G4Sym = adjusted(Pitch.GSym, Octave.FourthSym);
        static final String G4sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.FourthSym);
        static final String A4fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.FourthSym);
        static final String A4Sym = adjusted(Pitch.ASym, Octave.FourthSym);
        static final String A4sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.FourthSym);
        static final String B4fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.FourthSym);
        static final String B4Sym = adjusted(Pitch.BSym, Octave.FourthSym);

        // Octave 5
        static final String C5Sym = adjusted(Pitch.CSym, Octave.FifthSym);
        static final String C5sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.FifthSym);
        static final String D5fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.FifthSym);
        static final String D5Sym = adjusted(Pitch.DSym, Octave.FifthSym);
        static final String D5sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.FifthSym);
        static final String E5fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.FifthSym);
        static final String E5Sym = adjusted(Pitch.ESym, Octave.FifthSym);
        static final String F5Sym = adjusted(Pitch.FSym, Octave.FifthSym);
        static final String F5sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.FifthSym);
        static final String G5fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.FifthSym);
        static final String G5Sym = adjusted(Pitch.GSym, Octave.FifthSym);
        static final String G5sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.FifthSym);
        static final String A5fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.FifthSym);
        static final String A5Sym = adjusted(Pitch.ASym, Octave.FifthSym);
        static final String A5sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.FifthSym);
        static final String B5fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.FifthSym);
        static final String B5Sym = adjusted(Pitch.BSym, Octave.FifthSym);

        // Octave 6
        static final String C6Sym = adjusted(Pitch.CSym, Octave.SixthSym);
        static final String C6sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.SixthSym);
        static final String D6fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.SixthSym);
        static final String D6Sym = adjusted(Pitch.DSym, Octave.SixthSym);
        static final String D6sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.SixthSym);
        static final String E6fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.SixthSym);
        static final String E6Sym = adjusted(Pitch.ESym, Octave.SixthSym);
        static final String F6Sym = adjusted(Pitch.FSym, Octave.SixthSym);
        static final String F6sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.SixthSym);
        static final String G6fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.SixthSym);
        static final String G6Sym = adjusted(Pitch.GSym, Octave.SixthSym);
        static final String G6sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.SixthSym);
        static final String A6fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.SixthSym);
        static final String A6Sym = adjusted(Pitch.ASym, Octave.SixthSym);
        static final String A6sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.SixthSym);
        static final String B6fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.SixthSym);
        static final String B6Sym = adjusted(Pitch.BSym, Octave.SixthSym);

        // Octave 7
        static final String C7Sym = adjusted(Pitch.CSym, Octave.SeventhSym);
        static final String C7sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.SeventhSym);
        static final String D7fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.SeventhSym);
        static final String D7Sym = adjusted(Pitch.DSym, Octave.SeventhSym);
        static final String D7sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.SeventhSym);
        static final String E7fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.SeventhSym);
        static final String E7Sym = adjusted(Pitch.ESym, Octave.SeventhSym);
        static final String F7Sym = adjusted(Pitch.FSym, Octave.SeventhSym);
        static final String F7sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.SeventhSym);
        static final String G7fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.SeventhSym);
        static final String G7Sym = adjusted(Pitch.GSym, Octave.SeventhSym);
        static final String G7sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.SeventhSym);
        static final String A7fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.SeventhSym);
        static final String A7Sym = adjusted(Pitch.ASym, Octave.SeventhSym);
        static final String A7sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.SeventhSym);
        static final String B7fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.SeventhSym);
        static final String B7Sym = adjusted(Pitch.BSym, Octave.SeventhSym);

        // Octave 8
        static final String C8Sym = adjusted(Pitch.CSym, Octave.EighthSym);
        static final String C8sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.EighthSym);
        static final String D8fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.EighthSym);
        static final String D8Sym = adjusted(Pitch.DSym, Octave.EighthSym);
        static final String D8sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.EighthSym);
        static final String E8fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.EighthSym);
        static final String E8Sym = adjusted(Pitch.ESym, Octave.EighthSym);
        static final String F8Sym = adjusted(Pitch.FSym, Octave.EighthSym);
        static final String F8sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.EighthSym);
        static final String G8fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.EighthSym);
        static final String G8Sym = adjusted(Pitch.GSym, Octave.EighthSym);
        static final String G8sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.EighthSym);
        static final String A8fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.EighthSym);
        static final String A8Sym = adjusted(Pitch.ASym, Octave.EighthSym);
        static final String A8sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.EighthSym);
        static final String B8fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.EighthSym);
        static final String B8Sym = adjusted(Pitch.BSym, Octave.EighthSym);

        // Octave 9
        static final String C9Sym = adjusted(Pitch.CSym, Octave.NinthSym);
        static final String C9sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.NinthSym);
        static final String D9fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.NinthSym);
        static final String D9Sym = adjusted(Pitch.DSym, Octave.NinthSym);
        static final String D9sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.NinthSym);
        static final String E9fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.NinthSym);
        static final String E9Sym = adjusted(Pitch.ESym, Octave.NinthSym);
        static final String F9Sym = adjusted(Pitch.FSym, Octave.NinthSym);
        static final String F9sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.NinthSym);
        static final String G9fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.NinthSym);
        static final String G9Sym = adjusted(Pitch.GSym, Octave.NinthSym);
        static final String G9sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.NinthSym);
        static final String A9fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.NinthSym);
        static final String A9Sym = adjusted(Pitch.ASym, Octave.NinthSym);
        static final String A9sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.NinthSym);
        static final String B9fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.NinthSym);
        static final String B9Sym = adjusted(Pitch.BSym, Octave.NinthSym);

        // Octave 10
        static final String C10Sym = adjusted(Pitch.CSym, Octave.TenthSym);
        static final String C10sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.TenthSym);
        static final String D10fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.TenthSym);
        static final String D10Sym = adjusted(Pitch.DSym, Octave.TenthSym);
        static final String D10sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.TenthSym);
        static final String E10fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.TenthSym);
        static final String E10Sym = adjusted(Pitch.ESym, Octave.TenthSym);
        static final String F10Sym = adjusted(Pitch.FSym, Octave.TenthSym);
        static final String F10sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.TenthSym);
        static final String G10fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.TenthSym);
        static final String G10Sym = adjusted(Pitch.GSym, Octave.TenthSym);
        static final String G10sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.TenthSym);
        static final String A10fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.TenthSym);
        static final String A10Sym = adjusted(Pitch.ASym, Octave.TenthSym);
        static final String A10sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.TenthSym);
        static final String B10fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.TenthSym);
        static final String B10Sym = adjusted(Pitch.BSym, Octave.TenthSym);

        /**
         * {@code Accidental} holds all commonly known accidental symbols in music.
         */
        interface Accidental
        {
            static final String DoubleFlatSym = "bb";
            static final String DoubleSharpSym = "##";
            static final String FlatSym = "b";
            static final String NaturalSym = "";
            static final String NaturalFlatSym = " b";
            static final String NaturalSharpSym = " #";
            static final String SharpSym = "#";
        }

        /**
         * {@code Dynamics} holds all commonly known dynamics symbols in music.
         */
        interface Dynamics
        {
            static final String ForteName = "Forte";
            static final String ForteSym = MusicXML.Constant.Dynamics.F;
            static final String FortissimoName = "Fortissimo";
            static final String FortissimoSym = MusicXML.Constant.Dynamics.FF;
            static final String FortississimoName = "Fortississimo";
            static final String FortississimoSym = MusicXML.Constant.Dynamics.FFF;
            static final String MezzoForteName = "Forte";
            static final String MezzoForteSym = MusicXML.Constant.Dynamics.MF;
            static final String MezzoPianoName = "Mezzo-piano";
            static final String MezzoPianoSym = MusicXML.Constant.Dynamics.MP;
            static final String PianoName = "Piano";
            static final String PianoSym = MusicXML.Constant.Dynamics.P;
            static final String PianissimoName = "Pianossimo";
            static final String PianissimoSym = MusicXML.Constant.Dynamics.PP;
            static final String PianississimoName = "Pianossissimo";
            static final String PianississimoSym = MusicXML.Constant.Dynamics.PPP;
        }

        /**
         * {@code Octave} holds all commonly known octave symbols in music.
         */
        interface Octave
        {
            static final String NegativeSecondSym = "-2";
            static final String NegativeFirstSym = "-1";
            static final String ZerothSym = "0";
            static final String FirstSym = "1";
            static final String SecondSym = "2";
            static final String ThirdSym = "3";
            static final String FourthSym = "4";
            static final String FifthSym = "5";
            static final String SixthSym = "6";
            static final String SeventhSym = "7";
            static final String EighthSym = "8";
            static final String NinthSym = "9";
            static final String TenthSym = "10";
        }

        /**
         * {@code Pitch} holds all commonly known pitch symbols in music.
         */
        interface Pitch
        {
            static final char ASym = 'A';
            static final char BSym = 'B';
            static final char CSym = 'C';
            static final char DSym = 'D';
            static final char ESym = 'E';
            static final char FSym = 'F';
            static final char GSym = 'G';
        }

        /**
         * Adjusts the note symbol by concatenating the pitch, accidental, and octave symbols.
         *
         * @param pitch the pitch symbol.
         * @param accidental the accidental symbol.
         * @param octave the octave symbol.
         *
         * @return the adjusted note symbol.
         */
        static
        String adjusted(
            final char pitch,
            final String accidental,
            final String octave
            ) {
            return pitch + accidental + octave;
        }

        /**
         * Adjusts the note symbol by concatenating the pitch and octave symbols.
         *
         * @param pitch the pitch symbol.
         * @param octave the octave symbol.
         *
         * @return the adjusted note symbol.
         */
        static
        String adjusted(
            final char pitch,
            final String octave
            ) {
            return pitch + octave;
        }
    }
}