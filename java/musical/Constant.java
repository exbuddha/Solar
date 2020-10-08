package musical;

import music.system.data.Constant.MusicXML;

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
        String C = "C";
        String F = "F";
        String G = "G";
        String Percussion = "Perc";
        String TAB = "TAB";
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
        String GraceSym = duration("0", "/8");
        String OctupleWholeSym = duration("8", "1");
        String QuadrupleWholeSym = duration("4", "1");
        String DoubleWholeSym = duration("2", "1");
        String WholeSym = duration("1", "1");
        String HalfSym = duration("1", "2");
        String QuarterSym = duration("1", "4");
        String EighthSym = duration("1", "8");
        String SixteenthSym = duration("1", "16");
        String ThirtySecondSym = duration("1", "32");
        String SixtyFourthSym = duration("1", "64");
        String HundredTwentyEighthSym = duration("1", "128");
        String TwoHundredFiftySixthSym = duration("1", "256");
        String FiveHundredTwelfthSym = duration("1", "512");
        String OneThousandTwentyFourthSym = duration("1", "1024");

        // Stem adjustments
        String UnitSym = "1";
        String DottedSym = duration("3", "2");
        String DoubleDottedSym = duration("7", "4");
        String TripleDottedSym = duration("15", "8");

        String TripletSym = duration("1", "3");
        String QuintupletSym = duration("1", "5");
        String SextupletSym = duration("1", "6");
        String SeptupletSym = duration("1", "7");

        String DupletSym = duration("3", "2");
        String QuadrupletSym = duration("3", "4");

        String MultiplierSym = " * ";

        // Adjusted stem types
        String DottedOctupleWholeSym = adjusted(OctupleWholeSym, DottedSym);
        String DoubleDottedOctupleWholeSym = adjusted(OctupleWholeSym, DoubleDottedSym);
        String TripleDottedOctupleWholeSym = adjusted(OctupleWholeSym, TripleDottedSym);
        String DottedQuadrupleWholeSym = adjusted(QuadrupleWholeSym, DottedSym);
        String DoubleDottedQuadrupleWholeSym = adjusted(QuadrupleWholeSym, DoubleDottedSym);
        String TripleDottedQuadrupleWholeSym = adjusted(QuadrupleWholeSym, TripleDottedSym);
        String DottedDoubleWholeSym = adjusted(DoubleWholeSym, DottedSym);
        String DoubleDottedDoubleWholeSym = adjusted(DoubleWholeSym, DoubleDottedSym);
        String TripleDottedDoubleWholeSym = adjusted(DoubleWholeSym, TripleDottedSym);
        String DottedWholeSym = adjusted(WholeSym, DottedSym);
        String DoubleDottedWholeSym = adjusted(WholeSym, DoubleDottedSym);
        String TripleDottedWholeSym = adjusted(WholeSym, TripleDottedSym);
        String DottedHalfSym = adjusted(HalfSym, DottedSym);
        String DoubleDottedHalfSym = adjusted(HalfSym, DoubleDottedSym);
        String TripleDottedHalfSym = adjusted(HalfSym, TripleDottedSym);
        String DottedQuarterSym = adjusted(QuarterSym, DottedSym);
        String DoubleDottedQuarterSym = adjusted(QuarterSym, DoubleDottedSym);
        String TripleDottedQuarterSym = adjusted(QuarterSym, TripleDottedSym);
        String DottedEighthSym = adjusted(EighthSym, DottedSym);
        String DoubleDottedEighthSym = adjusted(EighthSym, DoubleDottedSym);
        String TripleDottedEighthSym = adjusted(EighthSym, TripleDottedSym);
        String DottedSixteenthSym = adjusted(SixteenthSym, DottedSym);
        String DoubleDottedSixteenthSym = adjusted(SixteenthSym, DoubleDottedSym);
        String TripleDottedSixteenthSym = adjusted(SixteenthSym, TripleDottedSym);
        String DottedThirtySecondSym = adjusted(ThirtySecondSym, DottedSym);
        String DoubleDottedThirtySecondSym = adjusted(ThirtySecondSym, DoubleDottedSym);
        String TripleDottedThirtySecondSym = adjusted(ThirtySecondSym, TripleDottedSym);
        String DottedSixtyFourthSym = adjusted(SixtyFourthSym, DottedSym);
        String DoubleDottedSixtyFourthSym = adjusted(SixtyFourthSym, DoubleDottedSym);
        String TripleDottedSixtyFourthSym = adjusted(SixtyFourthSym, TripleDottedSym);
        String DottedHundredTwentyEighthSym = adjusted(HundredTwentyEighthSym, DottedSym);
        String DoubleDottedHundredTwentyEighthSym = adjusted(HundredTwentyEighthSym, DoubleDottedSym);
        String TripleDottedHundredTwentyEighthSym = adjusted(HundredTwentyEighthSym, TripleDottedSym);
        String DottedTwoHundredFiftySixthSym = adjusted(TwoHundredFiftySixthSym, DottedSym);
        String DoubleDottedTwoHundredFiftySixthSym = adjusted(TwoHundredFiftySixthSym, DoubleDottedSym);
        String TripleDottedTwoHundredFiftySixthSym = adjusted(TwoHundredFiftySixthSym, TripleDottedSym);
        String DottedFiveHundredTwelfthSym = adjusted(FiveHundredTwelfthSym, DottedSym);
        String DoubleDottedFiveHundredTwelfthSym = adjusted(FiveHundredTwelfthSym, DoubleDottedSym);
        String TripleDottedFiveHundredTwelfthSym = adjusted(FiveHundredTwelfthSym, TripleDottedSym);
        String DottedOneThousandTwentyFourthSym = adjusted(OneThousandTwentyFourthSym, DottedSym);
        String DoubleDottedOneThousandTwentyFourthSym = adjusted(OneThousandTwentyFourthSym, DoubleDottedSym);
        String TripleDottedOneThousandTwentyFourthSym = adjusted(OneThousandTwentyFourthSym, TripleDottedSym);

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
        String PerfectUnisonSym = "P1";
        String QuarterToneSym = null;
        String MinorSecondSym = "m2";
        String MajorSecondSym = "M2";
        String MinorThirdSym = "m3";
        String MajorThirdSym = "M3";
        String PerfectFourthSym = "P4";
        String AugmentedFourthSym = "A4";
        String DiminishedFifthSym = "d5";
        String PerfectFifthSym = "P5";
        String MinorSixthSym = "m6";
        String MajorSixthSym = "M6";
        String MinorSeventhSym = "m7";
        String MajorSeventhSym = "M7";
        String PerfectOctaveSym = "P8";

        String CentsSym = "cents";

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
        String ChromaticName = "Chromatic";
        String MajorName = "Major";
        String DorianName = "Dorian";
        String PhrygianName = "Phrygian";
        String LydianName = "Major";
        String MixolydianName = "Mixolydian";
        String MinorName = "Minor";
        String LocrianName = "Locrian";
        String WholeToneName = "Whole Tone";
        String MajorPentatonicName = "Major Pentatonic";
        String MinorPentatonicName = "Minor Pentatonic";
        String EgyptianName = "Egyptian";
        String BluesMajorName = "Blues Major";
        String BluesMinorName = "Blues Minor";
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
        String C_1Sym = adjusted(Pitch.CSym, Octave.NegativeFirstSym);
        String C_1sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.NegativeFirstSym);
        String D_1fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.NegativeFirstSym);
        String D_1Sym = adjusted(Pitch.DSym, Octave.NegativeFirstSym);
        String D_1sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.NegativeFirstSym);
        String E_1fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.NegativeFirstSym);
        String E_1Sym = adjusted(Pitch.ESym, Octave.NegativeFirstSym);
        String F_1Sym = adjusted(Pitch.FSym, Octave.NegativeFirstSym);
        String F_1sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.NegativeFirstSym);
        String G_1fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.NegativeFirstSym);
        String G_1Sym = adjusted(Pitch.GSym, Octave.NegativeFirstSym);
        String G_1sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.NegativeFirstSym);
        String A_1fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.NegativeFirstSym);
        String A_1Sym = adjusted(Pitch.ASym, Octave.NegativeFirstSym);
        String A_1sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.NegativeFirstSym);
        String B_1fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.NegativeFirstSym);
        String B_1Sym = adjusted(Pitch.BSym, Octave.NegativeFirstSym);

        // Octave 0
        String C0Sym = adjusted(Pitch.CSym, Octave.ZerothSym);
        String C0sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.ZerothSym);
        String D0fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.ZerothSym);
        String D0Sym = adjusted(Pitch.DSym, Octave.ZerothSym);
        String D0sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.ZerothSym);
        String E0fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.ZerothSym);
        String E0Sym = adjusted(Pitch.ESym, Octave.ZerothSym);
        String F0Sym = adjusted(Pitch.FSym, Octave.ZerothSym);
        String F0sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.ZerothSym);
        String G0fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.ZerothSym);
        String G0Sym = adjusted(Pitch.GSym, Octave.ZerothSym);
        String G0sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.ZerothSym);
        String A0fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.ZerothSym);
        String A0Sym = adjusted(Pitch.ASym, Octave.ZerothSym);
        String A0sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.ZerothSym);
        String B0fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.ZerothSym);
        String B0Sym = adjusted(Pitch.BSym, Octave.ZerothSym);

        // Octave 1
        String C1Sym = adjusted(Pitch.CSym, Octave.FirstSym);
        String C1sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.FirstSym);
        String D1fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.FirstSym);
        String D1Sym = adjusted(Pitch.DSym, Octave.FirstSym);
        String D1sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.FirstSym);
        String E1fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.FirstSym);
        String E1Sym = adjusted(Pitch.ESym, Octave.FirstSym);
        String F1Sym = adjusted(Pitch.FSym, Octave.FirstSym);
        String F1sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.FirstSym);
        String G1fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.FirstSym);
        String G1Sym = adjusted(Pitch.GSym, Octave.FirstSym);
        String G1sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.FirstSym);
        String A1fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.FirstSym);
        String A1Sym = adjusted(Pitch.ASym, Octave.FirstSym);
        String A1sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.FirstSym);
        String B1fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.FirstSym);
        String B1Sym = adjusted(Pitch.BSym, Octave.FirstSym);

        // Octave 2
        String C2Sym = adjusted(Pitch.CSym, Octave.SecondSym);
        String C2sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.SecondSym);
        String D2fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.SecondSym);
        String D2Sym = adjusted(Pitch.DSym, Octave.SecondSym);
        String D2sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.SecondSym);
        String E2fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.SecondSym);
        String E2Sym = adjusted(Pitch.ESym, Octave.SecondSym);
        String F2Sym = adjusted(Pitch.FSym, Octave.SecondSym);
        String F2sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.SecondSym);
        String G2fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.SecondSym);
        String G2Sym = adjusted(Pitch.GSym, Octave.SecondSym);
        String G2sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.SecondSym);
        String A2fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.SecondSym);
        String A2Sym = adjusted(Pitch.ASym, Octave.SecondSym);
        String A2sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.SecondSym);
        String B2fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.SecondSym);
        String B2Sym = adjusted(Pitch.BSym, Octave.SecondSym);

        // Octave 3
        String C3Sym = adjusted(Pitch.CSym, Octave.ThirdSym);
        String C3sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.ThirdSym);
        String D3fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.ThirdSym);
        String D3Sym = adjusted(Pitch.DSym, Octave.ThirdSym);
        String D3sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.ThirdSym);
        String E3fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.ThirdSym);
        String E3Sym = adjusted(Pitch.ESym, Octave.ThirdSym);
        String F3Sym = adjusted(Pitch.FSym, Octave.ThirdSym);
        String F3sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.ThirdSym);
        String G3fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.ThirdSym);
        String G3Sym = adjusted(Pitch.GSym, Octave.ThirdSym);
        String G3sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.ThirdSym);
        String A3fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.ThirdSym);
        String A3Sym = adjusted(Pitch.ASym, Octave.ThirdSym);
        String A3sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.ThirdSym);
        String B3fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.ThirdSym);
        String B3Sym = adjusted(Pitch.BSym, Octave.ThirdSym);

        // Octave 4
        String C4Sym = adjusted(Pitch.CSym, Octave.FourthSym);
        String C4sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.FourthSym);
        String D4fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.FourthSym);
        String D4Sym = adjusted(Pitch.DSym, Octave.FourthSym);
        String D4sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.FourthSym);
        String E4fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.FourthSym);
        String E4Sym = adjusted(Pitch.ESym, Octave.FourthSym);
        String F4Sym = adjusted(Pitch.FSym, Octave.FourthSym);
        String F4sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.FourthSym);
        String G4fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.FourthSym);
        String G4Sym = adjusted(Pitch.GSym, Octave.FourthSym);
        String G4sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.FourthSym);
        String A4fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.FourthSym);
        String A4Sym = adjusted(Pitch.ASym, Octave.FourthSym);
        String A4sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.FourthSym);
        String B4fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.FourthSym);
        String B4Sym = adjusted(Pitch.BSym, Octave.FourthSym);

        // Octave 5
        String C5Sym = adjusted(Pitch.CSym, Octave.FifthSym);
        String C5sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.FifthSym);
        String D5fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.FifthSym);
        String D5Sym = adjusted(Pitch.DSym, Octave.FifthSym);
        String D5sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.FifthSym);
        String E5fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.FifthSym);
        String E5Sym = adjusted(Pitch.ESym, Octave.FifthSym);
        String F5Sym = adjusted(Pitch.FSym, Octave.FifthSym);
        String F5sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.FifthSym);
        String G5fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.FifthSym);
        String G5Sym = adjusted(Pitch.GSym, Octave.FifthSym);
        String G5sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.FifthSym);
        String A5fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.FifthSym);
        String A5Sym = adjusted(Pitch.ASym, Octave.FifthSym);
        String A5sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.FifthSym);
        String B5fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.FifthSym);
        String B5Sym = adjusted(Pitch.BSym, Octave.FifthSym);

        // Octave 6
        String C6Sym = adjusted(Pitch.CSym, Octave.SixthSym);
        String C6sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.SixthSym);
        String D6fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.SixthSym);
        String D6Sym = adjusted(Pitch.DSym, Octave.SixthSym);
        String D6sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.SixthSym);
        String E6fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.SixthSym);
        String E6Sym = adjusted(Pitch.ESym, Octave.SixthSym);
        String F6Sym = adjusted(Pitch.FSym, Octave.SixthSym);
        String F6sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.SixthSym);
        String G6fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.SixthSym);
        String G6Sym = adjusted(Pitch.GSym, Octave.SixthSym);
        String G6sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.SixthSym);
        String A6fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.SixthSym);
        String A6Sym = adjusted(Pitch.ASym, Octave.SixthSym);
        String A6sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.SixthSym);
        String B6fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.SixthSym);
        String B6Sym = adjusted(Pitch.BSym, Octave.SixthSym);

        // Octave 7
        String C7Sym = adjusted(Pitch.CSym, Octave.SeventhSym);
        String C7sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.SeventhSym);
        String D7fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.SeventhSym);
        String D7Sym = adjusted(Pitch.DSym, Octave.SeventhSym);
        String D7sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.SeventhSym);
        String E7fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.SeventhSym);
        String E7Sym = adjusted(Pitch.ESym, Octave.SeventhSym);
        String F7Sym = adjusted(Pitch.FSym, Octave.SeventhSym);
        String F7sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.SeventhSym);
        String G7fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.SeventhSym);
        String G7Sym = adjusted(Pitch.GSym, Octave.SeventhSym);
        String G7sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.SeventhSym);
        String A7fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.SeventhSym);
        String A7Sym = adjusted(Pitch.ASym, Octave.SeventhSym);
        String A7sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.SeventhSym);
        String B7fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.SeventhSym);
        String B7Sym = adjusted(Pitch.BSym, Octave.SeventhSym);

        // Octave 8
        String C8Sym = adjusted(Pitch.CSym, Octave.EighthSym);
        String C8sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.EighthSym);
        String D8fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.EighthSym);
        String D8Sym = adjusted(Pitch.DSym, Octave.EighthSym);
        String D8sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.EighthSym);
        String E8fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.EighthSym);
        String E8Sym = adjusted(Pitch.ESym, Octave.EighthSym);
        String F8Sym = adjusted(Pitch.FSym, Octave.EighthSym);
        String F8sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.EighthSym);
        String G8fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.EighthSym);
        String G8Sym = adjusted(Pitch.GSym, Octave.EighthSym);
        String G8sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.EighthSym);
        String A8fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.EighthSym);
        String A8Sym = adjusted(Pitch.ASym, Octave.EighthSym);
        String A8sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.EighthSym);
        String B8fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.EighthSym);
        String B8Sym = adjusted(Pitch.BSym, Octave.EighthSym);

        // Octave 9
        String C9Sym = adjusted(Pitch.CSym, Octave.NinthSym);
        String C9sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.NinthSym);
        String D9fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.NinthSym);
        String D9Sym = adjusted(Pitch.DSym, Octave.NinthSym);
        String D9sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.NinthSym);
        String E9fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.NinthSym);
        String E9Sym = adjusted(Pitch.ESym, Octave.NinthSym);
        String F9Sym = adjusted(Pitch.FSym, Octave.NinthSym);
        String F9sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.NinthSym);
        String G9fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.NinthSym);
        String G9Sym = adjusted(Pitch.GSym, Octave.NinthSym);
        String G9sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.NinthSym);
        String A9fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.NinthSym);
        String A9Sym = adjusted(Pitch.ASym, Octave.NinthSym);
        String A9sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.NinthSym);
        String B9fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.NinthSym);
        String B9Sym = adjusted(Pitch.BSym, Octave.NinthSym);

        // Octave 10
        String C10Sym = adjusted(Pitch.CSym, Octave.TenthSym);
        String C10sSym = adjusted(Pitch.CSym, Accidental.SharpSym, Octave.TenthSym);
        String D10fSym = adjusted(Pitch.DSym, Accidental.FlatSym, Octave.TenthSym);
        String D10Sym = adjusted(Pitch.DSym, Octave.TenthSym);
        String D10sSym = adjusted(Pitch.DSym, Accidental.SharpSym, Octave.TenthSym);
        String E10fSym = adjusted(Pitch.ESym, Accidental.FlatSym, Octave.TenthSym);
        String E10Sym = adjusted(Pitch.ESym, Octave.TenthSym);
        String F10Sym = adjusted(Pitch.FSym, Octave.TenthSym);
        String F10sSym = adjusted(Pitch.FSym, Accidental.SharpSym, Octave.TenthSym);
        String G10fSym = adjusted(Pitch.GSym, Accidental.FlatSym, Octave.TenthSym);
        String G10Sym = adjusted(Pitch.GSym, Octave.TenthSym);
        String G10sSym = adjusted(Pitch.GSym, Accidental.SharpSym, Octave.TenthSym);
        String A10fSym = adjusted(Pitch.ASym, Accidental.FlatSym, Octave.TenthSym);
        String A10Sym = adjusted(Pitch.ASym, Octave.TenthSym);
        String A10sSym = adjusted(Pitch.ASym, Accidental.SharpSym, Octave.TenthSym);
        String B10fSym = adjusted(Pitch.BSym, Accidental.FlatSym, Octave.TenthSym);
        String B10Sym = adjusted(Pitch.BSym, Octave.TenthSym);

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

        /**
         * {@code Accidental} holds all commonly known accidental symbols in music.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Accidental
        {
            String DoubleFlatSym = "bb";
            String DoubleSharpSym = "##";
            String FlatSym = "b";
            String NaturalSym = "";
            String NaturalFlatSym = " b";
            String NaturalSharpSym = " #";
            String SharpSym = "#";
        }

        /**
         * {@code Dynamics} holds all commonly known dynamics symbols in music.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Dynamics
        {
            String ForteName = "Forte";
            String ForteSym = MusicXML.Dynamics.F;
            String FortissimoName = "Fortissimo";
            String FortissimoSym = MusicXML.Dynamics.FF;
            String FortississimoName = "Fortississimo";
            String FortississimoSym = MusicXML.Dynamics.FFF;
            String MezzoForteName = "Forte";
            String MezzoForteSym = MusicXML.Dynamics.MF;
            String MezzoPianoName = "Mezzo-piano";
            String MezzoPianoSym = MusicXML.Dynamics.MP;
            String PianoName = "Piano";
            String PianoSym = MusicXML.Dynamics.P;
            String PianissimoName = "Pianossimo";
            String PianissimoSym = MusicXML.Dynamics.PP;
            String PianississimoName = "Pianossissimo";
            String PianississimoSym = MusicXML.Dynamics.PPP;
        }

        /**
         * {@code Octave} holds all commonly known octave symbols in music.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Octave
        {
            String NegativeSecondSym = "-2";
            String NegativeFirstSym = "-1";
            String ZerothSym = "0";
            String FirstSym = "1";
            String SecondSym = "2";
            String ThirdSym = "3";
            String FourthSym = "4";
            String FifthSym = "5";
            String SixthSym = "6";
            String SeventhSym = "7";
            String EighthSym = "8";
            String NinthSym = "9";
            String TenthSym = "10";

            String NullOctaveSym = "";
        }

        /**
         * {@code Pitch} holds all commonly known pitch symbols in music.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Pitch
        {
            char ASym = 'A';
            char BSym = 'B';
            char CSym = 'C';
            char DSym = 'D';
            char ESym = 'E';
            char FSym = 'F';
            char GSym = 'G';
        }
    }
}