package musical;

import static musical.Constant.Note.*;
import static musical.Constant.Note.Accidental.FlatSym;
import static musical.Constant.Note.Accidental.NaturalSym;
import static musical.Constant.Note.Accidental.SharpSym;
import static musical.Constant.Note.Dynamics.*;
import static musical.Constant.Note.Octave.*;
import static musical.Note.Accidental.Flat;
import static musical.Note.Accidental.Natural;
import static musical.Note.Accidental.Sharp;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import music.system.data.Clockable;
import music.system.data.Delta;
import music.system.data.Ordered;
import musical.Spectrum.Modulus;
import system.data.Symbolized;

/**
 * {@code Note} represents the musical note of a certain octave and pitch.
 * <p>
 * The instances of this class must satisfy the conditions below in order to benefit from all the functionality presented in this class:
 * <ul>
 * <li>Notes must have a pitch.
 * <li>A note that has a null octave is considered to be a pitch type, for which some functionality will fail.
 * <li>Notes can also have accidentals and adjustments.
 * <li>Pitch has a separate identity from accidental in this class.
 * The A-sharp note defines 'A' for pitch and 'sharp' for accidental.
 * <li>Pitch symbols only support single characters.
 * <li>Double-sharp and double-flat accidentals are not supported.
 * This information is maintained by the subclass {@code Scale.Accidental}.
 * Note objects are meant to only represent the tune of instrument sounds or notes in a score.
 * <li>Note adjustment is accounted for in all operations and comparisons.
 * </ul>
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class Note
implements
    Adjustable,
    Adjusting<Note, Interval>,
    Clockable<Note>,
    Cloneable,
    Comparable<Note>,
    Localizable,
    Modulus,
    NoteType,
    Ordered<Float>,
    Symbolized<String>,
    Unit
{
    public static final
    Standard C_1 = new Standard(0F, C_1Sym, (byte) -1, Pitch.C, 8.18F);

    public static final
    Standard C_1s = new Standard(1F, C_1sSym, (byte) -1, Pitch.C, Sharp, 8.66F);

    public static final
    Standard D_1f = new Standard(1F, D_1fSym, (byte) -1, Pitch.D, Flat, 8.66F);

    public static final
    Standard D_1 = new Standard(2F, D_1Sym, (byte) -1, Pitch.D, 9.18F);

    public static final
    Standard D_1s = new Standard(3F, D_1sSym, (byte) -1, Pitch.D, Sharp, 9.72F);

    public static final
    Standard E_1f = new Standard(3F, E_1fSym, (byte) -1, Pitch.E, Flat, 9.72F);

    public static final
    Standard E_1 = new Standard(4F, E_1Sym, (byte) -1, Pitch.E, 10.30F);

    public static final
    Standard F_1 = new Standard(5F, F_1Sym, (byte) -1, Pitch.F, 10.91F);

    public static final
    Standard F_1s = new Standard(6F, F_1sSym, (byte) -1, Pitch.F, Sharp, 11.56F);

    public static final
    Standard G_1f = new Standard(6F, G_1fSym, (byte) -1, Pitch.G, Flat, 11.56F);

    public static final
    Standard G_1 = new Standard(7F, G_1Sym, (byte) -1, Pitch.G, 12.25F);

    public static final
    Standard G_1s = new Standard(8F, G_1sSym, (byte) -1, Pitch.G, Sharp, 12.98F);

    public static final
    Standard A_1f = new Standard(8F, A_1fSym, (byte) -1, Pitch.A, Flat, 12.98F);

    public static final
    Standard A_1 = new Standard(9F, A_1Sym, (byte) -1, Pitch.A, 13.75F);

    public static final
    Standard A_1s = new Standard(10F, A_1sSym, (byte) -1, Pitch.A, Sharp, 14.57F);

    public static final
    Standard B_1f = new Standard(10F, B_1fSym, (byte) -1, Pitch.B, Flat, 14.57F);

    public static final
    Standard B_1 = new Standard(11F, B_1Sym, (byte) -1, Pitch.B, 15.43F);

    public static final
    Standard C0 = new Standard(12F, C0Sym, (byte) 0, Pitch.C, 16.35F);

    public static final
    Standard C0s = new Standard(13F, C0sSym, (byte) 0, Pitch.C, Sharp, 17.32F);

    public static final
    Standard D0f = new Standard(13F, D0fSym, (byte) 0, Pitch.D, Flat, 17.32F);

    public static final
    Standard D0 = new Standard(14F, D0Sym, (byte) 0, Pitch.D, 18.35F);

    public static final
    Standard D0s = new Standard(15F, D0sSym, (byte) 0, Pitch.D, Sharp, 19.45F);

    public static final
    Standard E0f = new Standard(15F, E0fSym, (byte) 0, Pitch.E, Flat, 19.45F);

    public static final
    Standard E0 = new Standard(16F, E0Sym, (byte) 0, Pitch.E, 20.60F);

    public static final
    Standard F0 = new Standard(17F, F0Sym, (byte) 0, Pitch.F, 21.83F);

    public static final
    Standard F0s = new Standard(18F, F0sSym, (byte) 0, Pitch.F, Sharp, 23.12F);

    public static final
    Standard G0f = new Standard(18F, G0fSym, (byte) 0, Pitch.G, Flat, 23.12F);

    public static final
    Standard G0 = new Standard(19F, G0Sym, (byte) 0, Pitch.G, 24.50F);

    public static final
    Standard G0s = new Standard(20F, G0sSym, (byte) 0, Pitch.G, Sharp, 25.96F);

    public static final
    Standard A0f = new Standard(20F, A0fSym, (byte) 0, Pitch.A, Flat, 25.96F);

    public static final
    Standard A0 = new Standard(21F, A0Sym, (byte) 0, Pitch.A, 27.50F);

    public static final
    Standard A0s = new Standard(22F, A0sSym, (byte) 0, Pitch.A, Sharp, 29.14F);

    public static final
    Standard B0f = new Standard(22F, B0fSym, (byte) 0, Pitch.B, Flat, 29.14F);

    public static final
    Standard B0 = new Standard(23F, B0Sym, (byte) 0, Pitch.B, 30.87F);

    public static final
    Standard C1 = new Standard(24F, C1Sym, (byte) 1, Pitch.C, 32.70F);

    public static final
    Standard C1s = new Standard(25F, C1sSym, (byte) 1, Pitch.C, Sharp, 34.65F);

    public static final
    Standard D1f = new Standard(25F, D1fSym, (byte) 1, Pitch.D, Flat, 34.65F);

    public static final
    Standard D1 = new Standard(26F, D1Sym, (byte) 1, Pitch.D, 36.71F);

    public static final
    Standard D1s = new Standard(27F, D1sSym, (byte) 1, Pitch.D, Sharp, 38.89F);

    public static final
    Standard E1f = new Standard(27F, E1fSym, (byte) 1, Pitch.E, Flat, 38.89F);

    public static final
    Standard E1 = new Standard(28F, E1Sym, (byte) 1, Pitch.E, 41.20F);

    public static final
    Standard F1 = new Standard(29F, F1Sym, (byte) 1, Pitch.F, 43.65F);

    public static final
    Standard F1s = new Standard(30F, F1sSym, (byte) 1, Pitch.F, Sharp, 46.25F);

    public static final
    Standard G1f = new Standard(30F, G1fSym, (byte) 1, Pitch.G, Flat, 46.25F);

    public static final
    Standard G1 = new Standard(31F, G1Sym, (byte) 1, Pitch.G, 49.00F);

    public static final
    Standard G1s = new Standard(32F, G1sSym, (byte) 1, Pitch.G, Sharp, 51.91F);

    public static final
    Standard A1f = new Standard(32F, A1fSym, (byte) 1, Pitch.A, Flat, 51.91F);

    public static final
    Standard A1 = new Standard(33F, A1Sym, (byte) 1, Pitch.A, 55.00F);

    public static final
    Standard A1s = new Standard(34F, A1sSym, (byte) 1, Pitch.A, Sharp, 58.27F);

    public static final
    Standard B1f = new Standard(34F, B1fSym, (byte) 1, Pitch.B, Flat, 58.27F);

    public static final
    Standard B1 = new Standard(35F, B1Sym, (byte) 1, Pitch.B, 61.74F);

    public static final
    Standard C2 = new Standard(36F, C2Sym, (byte) 2, Pitch.C, 65.41F);

    public static final
    Standard C2s = new Standard(37F, C2sSym, (byte) 2, Pitch.C, Sharp, 69.30F);

    public static final
    Standard D2f = new Standard(37F, D2fSym, (byte) 2, Pitch.D, Flat, 69.30F);

    public static final
    Standard D2 = new Standard(38F, D2Sym, (byte) 2, Pitch.D, 73.42F);

    public static final
    Standard D2s = new Standard(39F, D2sSym, (byte) 2, Pitch.D, Sharp, 77.78F);

    public static final
    Standard E2f = new Standard(39F, E2fSym, (byte) 2, Pitch.E, Flat, 77.78F);

    public static final
    Standard E2 = new Standard(40F, E2Sym, (byte) 2, Pitch.E, 82.41F);

    public static final
    Standard F2 = new Standard(41F, F2Sym, (byte) 2, Pitch.F, 87.31F);

    public static final
    Standard F2s = new Standard(42F, F2sSym, (byte) 2, Pitch.F, Sharp, 92.50F);

    public static final
    Standard G2f = new Standard(42F, G2fSym, (byte) 2, Pitch.G, Flat, 92.50F);

    public static final
    Standard G2 = new Standard(43F, G2Sym, (byte) 2, Pitch.G, 98.00F);

    public static final
    Standard G2s = new Standard(44F, G2sSym, (byte) 2, Pitch.G, Sharp, 103.83F);

    public static final
    Standard A2f = new Standard(44F, A2fSym, (byte) 2, Pitch.A, Flat, 103.83F);

    public static final
    Standard A2 = new Standard(45F, A2Sym, (byte) 2, Pitch.A, 110.00F);

    public static final
    Standard A2s = new Standard(46F, A2sSym, (byte) 2, Pitch.A, Sharp, 116.54F);

    public static final
    Standard B2f = new Standard(46F, B2fSym, (byte) 2, Pitch.B, Flat, 116.54F);

    public static final
    Standard B2 = new Standard(47F, B2Sym, (byte) 2, Pitch.B, 123.47F);

    public static final
    Standard C3 = new Standard(48F, C3Sym, (byte) 3, Pitch.C, 130.81F);

    public static final
    Standard C3s = new Standard(49F, C3sSym, (byte) 3, Pitch.C, Sharp, 138.59F);

    public static final
    Standard D3f = new Standard(49F, D3fSym, (byte) 3, Pitch.D, Flat, 138.59F);

    public static final
    Standard D3 = new Standard(50F, D3Sym, (byte) 3, Pitch.D, 146.83F);

    public static final
    Standard D3s = new Standard(51F, D3sSym, (byte) 3, Pitch.D, Sharp, 155.56F);

    public static final
    Standard E3f = new Standard(51F, E3fSym, (byte) 3, Pitch.E, Flat, 155.56F);

    public static final
    Standard E3 = new Standard(52F, E3Sym, (byte) 3, Pitch.E, 164.81F);

    public static final
    Standard F3 = new Standard(53F, F3Sym, (byte) 3, Pitch.F, 174.61F);

    public static final
    Standard F3s = new Standard(54F, F3sSym, (byte) 3, Pitch.F, Sharp, 185.00F);

    public static final
    Standard G3f = new Standard(54F, G3fSym, (byte) 3, Pitch.G, Flat, 185.00F);

    public static final
    Standard G3 = new Standard(55F, G3Sym, (byte) 3, Pitch.G, 196.00F);

    public static final
    Standard G3s = new Standard(56F, G3sSym, (byte) 3, Pitch.G, Sharp, 207.65F);

    public static final
    Standard A3f = new Standard(56F, A3fSym, (byte) 3, Pitch.A, Flat, 207.65F);

    public static final
    Standard A3 = new Standard(57F, A3Sym, (byte) 3, Pitch.A, 220.00F);

    public static final
    Standard A3s = new Standard(58F, A3sSym, (byte) 3, Pitch.A, Sharp, 233.08F);

    public static final
    Standard B3f = new Standard(58F, B3fSym, (byte) 3, Pitch.B, Flat, 233.08F);

    public static final
    Standard B3 = new Standard(59F, B3Sym, (byte) 3, Pitch.B, 246.94F);

    public static final
    Standard C4 = new Standard(59F, C4Sym, (byte) 4, Pitch.C, 261.63F);

    public static final
    Standard C4s = new Standard(60F, C4sSym, (byte) 4, Pitch.C, Sharp, 277.18F);

    public static final
    Standard D4f = new Standard(61F, D4fSym, (byte) 4, Pitch.D, Flat, 277.18F);

    public static final
    Standard D4 = new Standard(62F, D4Sym, (byte) 4, Pitch.D, 293.66F);

    public static final
    Standard D4s = new Standard(63F, D4sSym, (byte) 4, Pitch.D, Sharp, 311.13F);

    public static final
    Standard E4f = new Standard(63F, E4fSym, (byte) 4, Pitch.E, Flat, 311.13F);

    public static final
    Standard E4 = new Standard(64F, E4Sym, (byte) 4, Pitch.E, 329.63F);

    public static final
    Standard F4 = new Standard(65F, F4Sym, (byte) 4, Pitch.F, 349.23F);

    public static final
    Standard F4s = new Standard(66F, F4sSym, (byte) 4, Pitch.F, Sharp, 369.99F);

    public static final
    Standard G4f = new Standard(66F, G4fSym, (byte) 4, Pitch.G, Flat, 369.99F);

    public static final
    Standard G4 = new Standard(67F, G4Sym, (byte) 4, Pitch.G, 392.00F);

    public static final
    Standard G4s = new Standard(68F, G4sSym, (byte) 4, Pitch.G, Sharp, 415.30F);

    public static final
    Standard A4f = new Standard(68F, A4fSym, (byte) 4, Pitch.A, Flat, 415.30F);

    public static final
    Standard A4 = new Standard(69F, A4Sym, (byte) 4, Pitch.A, 440F);

    public static final
    Standard A4s = new Standard(70F, A4sSym, (byte) 4, Pitch.A, Sharp, 466.16F);

    public static final
    Standard B4f = new Standard(70F, B4fSym, (byte) 4, Pitch.B, Flat, 466.16F);

    public static final
    Standard B4 = new Standard(71F, B4Sym, (byte) 4, Pitch.B, 493.88F);

    public static final
    Standard C5 = new Standard(72F, C5Sym, (byte) 5, Pitch.C, 523.25F);

    public static final
    Standard C5s = new Standard(73F, C5sSym, (byte) 5, Pitch.C, Sharp, 554.37F);

    public static final
    Standard D5f = new Standard(73F, D5fSym, (byte) 5, Pitch.D, Flat, 554.37F);

    public static final
    Standard D5 = new Standard(74F, D5Sym, (byte) 5, Pitch.D, 587.33F);

    public static final
    Standard D5s = new Standard(75F, D5sSym, (byte) 5, Pitch.D, Sharp, 622.25F);

    public static final
    Standard E5f = new Standard(75F, E5fSym, (byte) 5, Pitch.E, Flat, 622.25F);

    public static final
    Standard E5 = new Standard(76F, E5Sym, (byte) 5, Pitch.E, 659.26F);

    public static final
    Standard F5 = new Standard(77F, F5Sym, (byte) 5, Pitch.F, 698.46F);

    public static final
    Standard F5s = new Standard(78F, F5sSym, (byte) 5, Pitch.F, Sharp, 739.99F);

    public static final
    Standard G5f = new Standard(78F, G5fSym, (byte) 5, Pitch.G, Flat, 739.99F);

    public static final
    Standard G5 = new Standard(79F, G5Sym, (byte) 5, Pitch.G, 783.99F);

    public static final
    Standard G5s = new Standard(80F, G5sSym, (byte) 5, Pitch.G, Sharp, 830.61F);

    public static final
    Standard A5f = new Standard(80F, A5fSym, (byte) 5, Pitch.A, Flat, 830.61F);

    public static final
    Standard A5 = new Standard(81F, A5Sym, (byte) 5, Pitch.A, 880.00F);

    public static final
    Standard A5s = new Standard(82F, A5sSym, (byte) 5, Pitch.A, Sharp, 932.33F);

    public static final
    Standard B5f = new Standard(82F, B5fSym, (byte) 5, Pitch.B, Flat, 932.33F);

    public static final
    Standard B5 = new Standard(83F, B5Sym, (byte) 5, Pitch.B, 987.77F);

    public static final
    Standard C6 = new Standard(84F, C6Sym, (byte) 6, Pitch.C, 1046.50F);

    public static final
    Standard C6s = new Standard(85F, C6sSym, (byte) 6, Pitch.C, Sharp, 1108.73F);

    public static final
    Standard D6f = new Standard(85F, D6fSym, (byte) 6, Pitch.D, Flat, 1108.73F);

    public static final
    Standard D6 = new Standard(86F, D6Sym, (byte) 6, Pitch.D, 1174.66F);

    public static final
    Standard D6s = new Standard(87F, D6sSym, (byte) 6, Pitch.D, Sharp, 1244.51F);

    public static final
    Standard E6f = new Standard(87F, E6fSym, (byte) 6, Pitch.E, Flat, 1244.51F);

    public static final
    Standard E6 = new Standard(88F, E6Sym, (byte) 6, Pitch.E, 1318.51F);

    public static final
    Standard F6 = new Standard(89F, F6Sym, (byte) 6, Pitch.F, 1396.91F);

    public static final
    Standard F6s = new Standard(90F, F6sSym, (byte) 6, Pitch.F, Sharp, 1479.98F);

    public static final
    Standard G6f = new Standard(90F, G6fSym, (byte) 6, Pitch.G, Flat, 1479.98F);

    public static final
    Standard G6 = new Standard(91F, G6Sym, (byte) 6, Pitch.G, 1567.98F);

    public static final
    Standard G6s = new Standard(92F, G6sSym, (byte) 6, Pitch.G, Sharp, 1661.22F);

    public static final
    Standard A6f = new Standard(92F, A6fSym, (byte) 6, Pitch.A, Flat, 1661.22F);

    public static final
    Standard A6 = new Standard(93F, A6Sym, (byte) 6, Pitch.A, 1760.00F);

    public static final
    Standard A6s = new Standard(94F, A6sSym, (byte) 6, Pitch.A, Sharp, 1864.66F);

    public static final
    Standard B6f = new Standard(94F, B6fSym, (byte) 6, Pitch.B, Flat, 1864.66F);

    public static final
    Standard B6 = new Standard(95F, B6Sym, (byte) 6, Pitch.B, 1975.53F);

    public static final
    Standard C7 = new Standard(96F, C7Sym, (byte) 7, Pitch.C, 2093.00F);

    public static final
    Standard C7s = new Standard(97F, C7sSym, (byte) 7, Pitch.C, Sharp, 2217.46F);

    public static final
    Standard D7f = new Standard(97F, D7fSym, (byte) 7, Pitch.D, Flat, 2217.46F);

    public static final
    Standard D7 = new Standard(98F, D7Sym, (byte) 7, Pitch.D, 2349.32F);

    public static final
    Standard D7s = new Standard(99F, D7sSym, (byte) 7, Pitch.D, Sharp, 2489.02F);

    public static final
    Standard E7f = new Standard(99F, E7fSym, (byte) 7, Pitch.E, Flat, 2489.02F);

    public static final
    Standard E7 = new Standard(100F, E7Sym, (byte) 7, Pitch.E, 2637.02F);

    public static final
    Standard F7 = new Standard(101F, F7Sym, (byte) 7, Pitch.F, 2793.83F);

    public static final
    Standard F7s = new Standard(102F, F7sSym, (byte) 7, Pitch.F, Sharp, 2959.96F);

    public static final
    Standard G7f = new Standard(102F, G7fSym, (byte) 7, Pitch.G, Flat, 2959.96F);

    public static final
    Standard G7 = new Standard(103F, G7Sym, (byte) 7, Pitch.G, 3135.96F);

    public static final
    Standard G7s = new Standard(104F, G7sSym, (byte) 7, Pitch.G, Sharp, 3322.44F);

    public static final
    Standard A7f = new Standard(104F, A7fSym, (byte) 7, Pitch.A, Flat, 3322.44F);

    public static final
    Standard A7 = new Standard(105F, A7Sym, (byte) 7, Pitch.A, 3520.00F);

    public static final
    Standard A7s = new Standard(106F, A7sSym, (byte) 7, Pitch.A, Sharp, 3729.31F);

    public static final
    Standard B7f = new Standard(106F, B7fSym, (byte) 7, Pitch.B, Flat, 3729.31F);

    public static final
    Standard B7 = new Standard(107F, B7Sym, (byte) 7, Pitch.B, 3951.07F);

    public static final
    Standard C8 = new Standard(108F, C8Sym, (byte) 8, Pitch.C, 4186.01F);

    public static final
    Standard C8s = new Standard(109F, C8sSym, (byte) 8, Pitch.C, Sharp, 4434.92F);

    public static final
    Standard D8f = new Standard(109F, D8fSym, (byte) 8, Pitch.D, Flat, 4434.92F);

    public static final
    Standard D8 = new Standard(110F, D8Sym, (byte) 8, Pitch.D, 4698.64F);

    public static final
    Standard D8s = new Standard(111F, D8sSym, (byte) 8, Pitch.D, Sharp, 4978.03F);

    public static final
    Standard E8f = new Standard(111F, E8fSym, (byte) 8, Pitch.E, Flat, 4978.03F);

    public static final
    Standard E8 = new Standard(112F, E8Sym, (byte) 8, Pitch.E, 5274.04F);

    public static final
    Standard F8 = new Standard(113F, F8Sym, (byte) 8, Pitch.F, 5587.65F);

    public static final
    Standard F8s = new Standard(114F, F8sSym, (byte) 8, Pitch.F, Sharp, 5919.91F);

    public static final
    Standard G8f = new Standard(114F, G8fSym, (byte) 8, Pitch.G, Flat, 5919.91F);

    public static final
    Standard G8 = new Standard(115F, G8Sym, (byte) 8, Pitch.G, 6271.93F);

    public static final
    Standard G8s = new Standard(116F, G8sSym, (byte) 8, Pitch.G, Sharp, 6644.88F);

    public static final
    Standard A8f = new Standard(116F, A8fSym, (byte) 8, Pitch.A, Flat, 6644.88F);

    public static final
    Standard A8 = new Standard(117F, A8Sym, (byte) 8, Pitch.A, 7040.00F);

    public static final
    Standard A8s = new Standard(118F, A8sSym, (byte) 8, Pitch.A, Sharp, 7458.62F);

    public static final
    Standard B8f = new Standard(118F, B8fSym, (byte) 8, Pitch.B, Flat, 7458.62F);

    public static final
    Standard B8 = new Standard(119F, B8Sym, (byte) 8, Pitch.B, 7902.13F);

    public static final
    Standard C9 = new Standard(120F, C9Sym, (byte) 9, Pitch.C, 8372.02F);

    public static final
    Standard C9s = new Standard(121F, C9sSym, (byte) 9, Pitch.C, Sharp, 8869.84F);

    public static final
    Standard D9f = new Standard(121F, D9fSym, (byte) 9, Pitch.D, Flat, 8869.84F);

    public static final
    Standard D9 = new Standard(122F, D9Sym, (byte) 9, Pitch.D, 9397.27F);

    public static final
    Standard D9s = new Standard(123F, D9sSym, (byte) 9, Pitch.D, Sharp, 9956.06F);

    public static final
    Standard E9f = new Standard(123F, E9fSym, (byte) 9, Pitch.E, Flat, 9956.06F);

    public static final
    Standard E9 = new Standard(124F, E9Sym, (byte) 9, Pitch.E, 10548.08F);

    public static final
    Standard F9 = new Standard(125F, F9Sym, (byte) 9, Pitch.F, 11175.30F);

    public static final
    Standard F9s = new Standard(126F, F9sSym, (byte) 9, Pitch.F, Sharp, 11839.82F);

    public static final
    Standard G9f = new Standard(126F, G9fSym, (byte) 9, Pitch.G, Flat, 11839.82F);

    public static final
    Standard G9 = new Standard(127F, G9Sym, (byte) 9, Pitch.G, 12543.85F);

    public static final
    Standard G9s = new Standard(128F, G9sSym, (byte) 9, Pitch.G, Sharp, 13289.75F);

    public static final
    Standard A9f = new Standard(128F, A9fSym, (byte) 9, Pitch.A, Flat, 13289.75F);

    public static final
    Standard A9 = new Standard(129F, A9Sym, (byte) 9, Pitch.A, 14080F);

    public static final
    Standard A9s = new Standard(130F, A9sSym, (byte) 9, Pitch.A, Sharp, 14917F);

    public static final
    Standard B9f = new Standard(130F, B9fSym, (byte) 9, Pitch.B, Flat, 14917F);

    public static final
    Standard B9 = new Standard(131F, B9Sym, (byte) 9, Pitch.B, 15804F);

    public static final
    Standard C10 = new Standard(131F, C10Sym, (byte) 10, Pitch.C, 16744F);

    public static final
    Standard C10s = new Standard(132F, C10sSym, (byte) 10, Pitch.C, Sharp, 17740F);

    public static final
    Standard D10f = new Standard(132F, D10fSym, (byte) 10, Pitch.D, Flat, 17740F);

    public static final
    Standard D10 = new Standard(133F, D10Sym, (byte) 10, Pitch.D, 18795F);

    public static final
    Standard D10s = new Standard(134F, D10sSym, (byte) 10, Pitch.D, Sharp, 19912F);

    public static final
    Standard E10f = new Standard(134F, E10fSym, (byte) 10, Pitch.E, Flat, 19912F);

    public static final
    Standard E10 = new Standard(135F, E10Sym, (byte) 10, Pitch.E, 21096F);

    public static final
    Standard F10 = new Standard(136F, F10Sym, (byte) 10, Pitch.F, 22351F);

    public static final
    Standard F10s = new Standard(137F, F10sSym, (byte) 10, Pitch.F, Sharp, 23680F);

    public static final
    Standard G10f = new Standard(137F, G10fSym, (byte) 10, Pitch.G, Flat, 23680F);

    public static final
    Standard G10 = new Standard(138F, G10Sym, (byte) 10, Pitch.G, 25088F);

    public static final
    Standard G10s = new Standard(139F, G10sSym, (byte) 10, Pitch.G, Sharp, 26580F);

    public static final
    Standard A10f = new Standard(139F, A10fSym, (byte) 10, Pitch.A, Flat, 26580F);

    public static final
    Standard A10 = new Standard(140F, A10Sym, (byte) 10, Pitch.A, 28160F);

    public static final
    Standard A10s = new Standard(141F, A10sSym, (byte) 10, Pitch.A, Sharp, 29834F);

    public static final
    Standard B10f = new Standard(141F, B10fSym, (byte) 10, Pitch.B, Flat, 29834F);

    public static final
    Standard B10 = new Standard(142F, B10Sym, (byte) 10, Pitch.B, 31609F);

    /** The note symbol. */
    protected
    String symbol;

    /** The note octave. */
    protected
    Byte octave;

    /** The note pitch. */
    protected
    Pitch pitch;

    /** The note accidental. */
    protected
    Accidental accidental;

    /** The number of cents added to, or subtracted from, the note to slightly alter its pitch. */
    protected
    short adjustment;

    /**
     * Creates a note with the specified octave, pitch, accidental, and adjustment (in cents), and adjusts the note.
     * <p>
     * Adjustment is the process in which {@code adjustment} values outside of the accepted range [-100, 100] are corrected and the remainder cents are added to, or subtracted from, the note pitch and octave.
     * Notes with uncommon pitch-and-accidental combinations, such as B#, are converted to their common form in the same process.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave; and converts null adjustment to 0, otherwise calls {@link Number#shortValue()} on it.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     * @param adjustment the adjustment. (in cents)
     */
    public
    Note(
        final Number octave,
        final Pitch pitch,
        final Accidental accidental,
        final Number adjustment
        ) {
        if (octave != null)
            this.octave = octave.byteValue();
        this.pitch = pitch;
        this.accidental = accidental;
        this.adjustment = adjustment == null
                          ? 0
                          : adjustment.shortValue();
        adjust();
    }

    public
    Note(
        final Octave octave,
        final Pitch pitch,
        final Accidental accidental,
        final Number adjustment
        ) {
        if (octave != null)
            this.octave = octave.getOrder().byteValue();
        this.pitch = pitch;
        this.accidental = accidental;
        this.adjustment = adjustment == null
                          ? 0
                          : adjustment.shortValue();
        adjust();
    }

    /**
     * Creates a note with the specified octave, pitch, and accidental, and adjusts the note.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     */
    public
    Note(
        final Number octave,
        final Pitch pitch,
        final Accidental accidental
        ) {
        this(octave, pitch, accidental, 0);
    }

    public
    Note(
        final Octave octave,
        final Pitch pitch,
        final Accidental accidental
        ) {
        this(octave, pitch, accidental, 0);
    }

    /**
     * Creates a natural note with the specified octave and pitch.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     */
    public
    Note(
        final Number octave,
        final Pitch pitch
        ) {
        this(octave, pitch, Natural);
    }

    public
    Note(
        final Octave octave,
        final Pitch pitch
        ) {
        this(octave, pitch, Natural);
    }

    /**
     * Creates a pitch type with the specified accidental and adjusts the note.
     *
     * @param pitch the pitch.
     * @param accidental the accidental.
     */
    public
    Note(
        final Pitch pitch,
        final Accidental accidental
        ) {
        this((Byte) null, pitch, accidental);
    }

    /**
     * Creates a natural pitch type.
     *
     * @param pitch the pitch.
     */
    public
    Note(
        final Pitch pitch
        ) {
        this((Byte) null, pitch);
    }

    /**
     * Creates a null note.
     */
    public
    Note() {
        this(null);
    }

    public static final
    short number(byte octave, Pitch pitch, Accidental accidental) {
        return (short) ((octave + 1) * 12 + (pitch.order + accidental.getOrder()) % 12);
    }

    public static final
    short number(byte octave, Pitch pitch) {
        return (short) ((octave + 1) * 12 + pitch.order);
    }

    public static final
    Note tune(Number octave, Pitch pitch, Accidental accidental) {
        if (octave == null)
            return tune(4, pitch, accidental);

        return tune(number(octave.byteValue(), pitch, accidental));
    }

    public static final
    Note tune(Number octave, Pitch pitch) {
        return tune(octave, pitch, Accidental.Natural);
    }

    public static final
    Note tune(Pitch pitch) {
        return tune(4, pitch);
    }

    public static final
    Note tune(Note note) {
        if (note instanceof Standard)
            return note;

        return tune(note.octave, note.pitch, note.accidental);
    }

    public static final
    Note tune(
        final Number number,
        final boolean sharp
        ) {
        if (number == null)
            return null;

        int i = number.intValue();
        if (i < 0 || i >= Standard.Order.length)
            return null;

        i = number.intValue();
        final short octave = (short) (i / 12 - 1);
        final byte pitch = (byte) (i - (octave + 1) * 12);

        i = 0;
        Note tune = Standard.Order[0];
        for (; octave > tune.octave && ++i < Standard.Order.length; tune = Standard.Order[i]);

        for (int diff = pitch - tune.pitch.order; (diff < 0 || diff > 1) && ++i < Standard.Order.length; diff = pitch - tune.pitch.order)
            tune = Standard.Order[i];

        return i < Standard.Order.length
               ? (i == 1 || i == 3 || i == 6 || i == 8 || i == 10)
                 ? sharp
                   ? i + 1 < Standard.Order.length
                     ? Standard.Order[i + 1]
                     : null
                   : Standard.Order[i]
                 : Standard.Order[i]
               : null;
    }

    public static final
    Note tune(
        final Number number
        ) {
        return tune(number, true);
    }

    public static final
    Note tune(
        final CharSequence symbol
        ) {
        if (symbol == null)
            return tune();

        final int length = Accidental.isValidNaturalSymbol(symbol.charAt(symbol.length() - 1))
                           ? symbol.length() - 1
                           : symbol.length();

        if (length > 0)
            if (Pitch.isValid(symbol.charAt(0))) {
               if (length == 1)
                    return tune(Pitch.valueOf(symbol.toString()));

                switch (symbol.charAt(length - 1)) {
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '0':
                    return tune(Byte.parseByte(symbol.subSequence(1, length - 1).toString()), Pitch.valueOf(symbol.charAt(0)));

                default:
                    return Accidental.isValidSingleSymbol(symbol.charAt(length - 1))
                           ? tune(Byte.parseByte(symbol.subSequence(1, length - 2).toString()), Pitch.valueOf(symbol.charAt(0)), Accidental.withSymbol(symbol.subSequence(length - (Accidental.isValidDoubleSymbol(symbol.charAt(length - 2)) ? 3 : 2), length - 1)))
                           : null;
                }
            }
            else
                return null;

        return tune();
    }

    public static final
    Note tune() {
        return Standard.A4;
    }

    public static
    Note withNumber(
        final short number,
        final boolean sharp
        ) {
        if (number < 0)
            return null;

        final short octave = (short) (number / 12 - 1);
        switch (number - (octave + 1) * 12) {
            case 1:
            case 3:
            case 6:
            case 8:
            case 10:
                return sharp
                       ? new Note(octave, Pitch.withOrder((byte) number), Sharp)
                       : new Note(octave, Pitch.withOrder((byte) (number + 1)), Flat);

            default:
                return new Note(octave, Pitch.withOrder((byte) number));
        }
    }

    /**
     * Creates and returns a new note for the specified MIDI note number, or null if number is less than 0.
     * <p>
     * This implementation creates sharp notes.
     *
     * @param number the note MIDI number.
     * @return the note, or null is number is less than 0.
     */
    public static
    Note withNumber(
        final short number
        ) {
        if (number < 0)
            return null;

        final short octave = (short) (number / 12 - 1);
        switch (number - (octave + 1) * 12) {
            case 1:
            case 3:
            case 6:
            case 8:
            case 10:
                return new Note(octave, Pitch.withOrder((byte) number), Sharp);

            default:
                return new Note(octave, Pitch.withOrder((byte) number));
        }
    }

    /**
     * Adds the specified amount of cents to the note.
     * <p>
     * This implementation performs unsafe casts from float and int to short.
     *
     * @param cents the cents.
     */
    public
    void add(
        short cents
        ) {
        // Add the octave amount and continue with the remainder
        int amount = cents / 1200;
        if (amount != 0) {
            octave = (byte) (octave + amount);
            cents %= 1200;
        }

        // Add the semitones amount and continue with the remainder
        amount = cents / 100;
        if (amount != 0) {
            // Correct pitch
            final short order = getOrder().shortValue();
            final short newOrder = (short) (order + amount);
            pitch = Pitch.withOrder((byte) ((newOrder + ((1 - (newOrder / 12)) * 12)) % 12));

            // Correct accidental making sure that the semitones remainder is within range [-1, 1]
            accidental = Accidental.withSemitone((byte) ((amount - (pitch.order - order) + 12) % 12));

            // Correct octave if the result note has crossed over the octave line
            if (amount > 0) {
                if (pitch.order + accidental.cents / 100F < order)
                    octave = (byte) (octave + 1);
            }
            else
                if (pitch.order + accidental.cents / 100F > order)
                    octave = (byte) (octave - 1);

            cents %= 100;
        }

        adjustment = cents;
        adjust();
    }

    /**
     * Adds the specified interval to the note.
     * <p>
     * This implementation performs an unsafe cast from int to short.
     *
     * @param interval the interval.
     */
    public
    void add(
        final Interval interval
        ) {
        add((short) interval.getCents());
    }

    /**
     * Returns true if the specified note has the same pitch, accidental, and adjustment as this note, and false otherwise.
     *
     * @param note the note.
     * @return true if notes are equal ignoring octaves, and false otherwise.
     */
    public
    boolean equalsIgnoreOctave(
        final Note note
        ) {
        return pitch == note.pitch &&
               accidental == note.accidental &&
               adjustment == note.adjustment;
    }

    /**
     * Returns true if the specified note has the same frequency as this note, and false otherwise.
     * <p>
     * This implementation calls {@link #compareTo(Note)} internally.
     *
     * @param note the note.
     * @return true if the notes are equal ignoring pitch variations, and false otherwise.
     */
    public
    boolean equalsIgnorePitch(
        final Note note
        ) {
        return compareTo(note) == 0;
    }

    /**
     * Returns true if the specified note has the same relative note number in its own octave, and adjustment as this note, and false otherwise.
     *
     * @param note the note.
     * @return true if the notes are equal ignoring pitch and octave variations, and false otherwise.
     */
    public
    boolean equalsIgnorePitchAndOctave(
        final Note note
        ) {
        return (getNumber() - note.getNumber()) % 12 + adjustment - note.adjustment == 0;
    }

    /**
     * Returns a string representation of the note adjustment.
     *
     * @return a string representation of the note adjustment.
     */
    protected
    String getAdjustmentString() {
        return (adjustment > 0 ? "+" : "") + adjustment;
    }

    /**
     * Returns the distance from the specified note in cents, or throws a {@code NullPointerException} if pitch or accidental is null.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     * @param adjustment the adjustment.
     * @return the distance in cents.
     * @throws NullPointerException if pitch or accidental is null.
     */
    public
    float getDistance(
        final Byte octave,
        final Pitch pitch,
        final Accidental accidental,
        final short adjustment
        ) {
        return (octave == null ||
                this.octave == null
                ? 0
                : (octave - this.octave) * 1200) +
                (pitch.order - this.pitch.order) * 100 +
                accidental.cents - this.accidental.cents +
                adjustment - this.adjustment;
    }

    /**
     * Returns the distance from the specified natural note in cents, or throws a {@code NullPointerException} if pitch or accidental is null.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param adjustment the adjustment.
     * @return the distance in cents.
     * @throws NullPointerException if pitch or accidental is null.
     */
    public
    float getDistance(
        final Byte octave,
        final Pitch pitch,
        final short adjustment
        ) {
        return getDistance(octave, pitch, Accidental.Natural, adjustment);
    }

    /**
     * Returns the distance from the specified natural note in cents, or throws a {@code NullPointerException} if pitch or accidental is null.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @return the distance in cents.
     * @throws NullPointerException if pitch or accidental is null.
     */
    public
    float getDistance(
        final Byte octave,
        final Pitch pitch
        ) {
        return getDistance(octave, pitch, Accidental.Natural, (short) 0);
    }

    /**
     * Returns the distance from the specified note type in cents, or throws a {@code NullPointerException} if pitch or accidental is null.
     * <p>
     * If this note has a defined octave, the same octave will be used; otherwise null is used.
     *
     * @param pitch the pitch.
     * @param accidental the accidental.
     * @param adjustment the adjustment.
     * @return the distance in cents.
     * @throws NullPointerException if pitch or accidental is null.
     */
    public
    float getDistance(
        final Pitch pitch,
        final Accidental accidental,
        final short adjustment
        ) {
        return getDistance(octave == null ? null : octave, pitch, accidental, adjustment);
    }

    /**
     * Returns the distance from the specified natural note type in cents, or throws a {@code NullPointerException} if pitch or accidental is null.
     * <p>
     * If this note has a defined octave, the same octave will be used; otherwise null is used.
     *
     * @param pitch the pitch.
     * @param adjustment the adjustment.
     * @return the distance in cents.
     * @throws NullPointerException if pitch or accidental is null.
     */
    public
    float getDistance(
        final Pitch pitch,
        final short adjustment
        ) {
        return getDistance(octave == null ? null : octave, pitch, Accidental.Natural, adjustment);
    }

    /**
     * Returns the distance from the specified natural note type in cents, or throws a {@code NullPointerException} if pitch or accidental is null.
     * <p>
     * If this note has a defined octave, the same octave will be used; otherwise null is used.
     *
     * @param pitch the pitch.
     * @return the distance in cents.
     * @throws NullPointerException if pitch or accidental is null.
     */
    public
    float getDistance(
        final Pitch pitch
        ) {
        return getDistance(octave == null ? null : octave, pitch, Accidental.Natural, (short) 0);
    }

    /**
     * Returns the distance from the specified note in cents.
     * <p>
     * If the {@code octave} of one of the notes is null, octaves will not be accounted for in the calculation.
     * This implementation includes the {@code adjustment} value in the calculation.
     *
     * @param note the note.
     * @return the distance in cents.
     */
    public
    float getDistance(
        final Note note
        ) {
        return getDistance(note.octave, note.pitch, note.accidental, note.adjustment);
    }

    /**
     * Returns the frequency of the note.
     * <p>
     * This implementation includes the {@code adjustment} value in the calculation.
     *
     * @return the frequency.
     */
    public
    float getFrequency() {
        return (float) (Math.pow(2F, (getNumber() - 69) / 12) * 440);
    }

    /**
     * Returns the note number based on the MIDI system.
     * <p>
     * By convention, the MIDI note number is between 0 to 127 covering the range from C-1 to G9, however this method returns values as large as {@code float} type for practical reasons.
     * <p>
     * This implementation includes the {@code adjustment} value in the calculation.
     *
     * @return the MIDI note number.
     */
    public
    float getNumber() {
        return (octave + 1) * 12 + pitch.order + (accidental.cents + adjustment) / 100F;
    }

    /**
     * Inverts the note by rotating its accidental, and returns this note.
     *
     * @return the inverted note.
     */
    public
    Note invert() {
        if (accidental != Natural) {
            final byte semitones = accidental.getSemitones();
            pitch = Pitch.withOrder((byte) (pitch.order - Integer.signum(semitones) * 2));
            accidental = Accidental.withSemitone((byte) -semitones);
        }

        return this;
    }

    public
    Scientific.System.DataPoint newDataPoint() {
        return new Scientific.System.DataPoint()
        {
            @Override
            public Note convert() {
                return Note.this;
            }

            @Override
            public Local<?> getAccidental() {
                return Note.this.getAccidental();
            }

            @Override
            public Octave getOctave() {
                return Octave.withOrder(Note.this.getOctave());
            }

            @Override
            public Tabular<?> getPitch() {
                return Note.this.getPitch();
            }

            @Override
            public boolean is(system.data.Type<? extends Note> type) {
                return x().equals(getPitch()) &&
                       y().equals(getAccidental());
            }

            @Override
            public Tabular<?> x() {
                return getPitch();
            }

            @Override
            public Local<?> y() {
                return getAccidental();
            }

            @Override
            public Octave z() {
                return getOctave();
            }
        };
    }

    /**
     * Returns a string representation of the note ignoring octave.
     *
     * @return a string representation of the note ignoring octave.
     */
    public
    String toStringIgnoreOctave() {
        return pitch.toString() + accidental.toString() + (adjustment == 0 ? "" : " (" + getAdjustmentString() + " cents)");
    }

    /**
     * Adjusts the note, converts uncommon pitch-accidental combinations to the common form, and returns this note.
     */
    @Override
    public void adjust() {
        if (octave != null && (adjustment < -100 || adjustment > 100)) {
            final short adj = adjustment;
            adjustment = 0;
            add(adj);
        }

        if (accidental == null)
            accidental = Natural;
        else
            switch (pitch) {
            case B:
                if (accidental.equals(Sharp)) {
                    pitch = Pitch.C;
                    accidental = Natural;
                }
                break;

            case C:
                if (accidental.equals(Flat)) {
                    pitch = Pitch.B;
                    accidental = Natural;
                }
                break;

            case E:
                if (accidental.equals(Sharp)) {
                    pitch = Pitch.F;
                    accidental = Natural;
                }
                break;

            case F:
                if (accidental.equals(Flat)) {
                    pitch = Pitch.E;
                    accidental = Natural;
                }
            }
    }

    /**
     * Adjusts this note using the specified adjustments, in cents, and returns the adjusted note.
     *
     * @param adjustments the adjustments.
     *
     * @return the adjusted note.
     */
    public
    Note adjusted(
        final Number... adjustments
        ) {
        if (adjustments.length == 0)
            return this;

        // TODO - Adjust in increments of 1200 cents to avoid overflows
        for (final Number adjustment : adjustments)
            if (adjustment != null) {
                this.adjustment += adjustment.shortValue();
                adjust();
            }

        return this;
    }

    @Override
    public Note adjusted(final Interval... adjustments) {
        return adjusted((Number[]) adjustments);
    }

    /**
     * Creates and returns a deep copy of this note.
     *
     * @return a deep copy of this note.
     */
    @Override
    public Note clone() {
        if (this instanceof Standard)
            return this;

        final Note note = new Note(octave.byteValue(), pitch, accidental.clone(), adjustment);
        note.symbol = symbol;
        return note;
    }

    /**
     * Returns 0 if the specified note has the same amount of cents as this note, -1 if this note has a lower amount, and 1 otherwise.
     * <p>
     * This implementation includes the {@code adjustment} value in the calculation.
     *
     * @param note the note.
     * @return 0 if the notes has the same amount of cents, -1 if this note has a lower amount, and 1 otherwise.
     */
    @Override
    public int compareTo(final Note note) {
        return (int) Math.signum(getDistance(note));
    }

    @Override
    public Note convert() {
        return this;
    }

    /**
     * Returns true if the specified object is a note and has the same octave, pitch, accidental, and adjustment as this note, and false otherwise.
     *
     * @param obj the object.
     * @return true if the note is equal to the specified object, and false otherwise.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Note) {
            final Note note = (Note) obj;
            return octave.equals(note.octave) &&
                   pitch == note.pitch &&
                   accidental.equals(note.accidental) &&
                   adjustment == note.adjustment;
        }

        return false;
    }

    /**
     * Returns the note order.
     * <p>
     * Note order is the pitch order corrected by the amount of semitones in {@code adjustment}.
     * The octave of a note does not affect the note order.
     *
     * @return the note order.
     */
    @Override
    public Float getOrder() {
        final double order = pitch.order + accidental.getSemitones() + adjustment / 100F;
        return (float) ((order + ((1 - (order / 12)) * 12)) % 12);
    }

    @Override
    public Class<Float> getOrderClass() {
        return Float.class;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(octave, pitch.order, accidental.cents, adjustment);
    }

    @Override
    public boolean is(final system.data.Type<? extends NoteType> type) {
        if (type instanceof Note) {
            final Note note = (Note) type;
            return note.octave == octave &&
                   note.pitch.order == pitch.order &&
                   note.accidental == accidental &&
                   note.adjustment == adjustment;
        }

        return false;
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void setSymbol(String symbol) {}

    @Override
    public String toString() {
        return pitch.toString() + accidental + octave + (adjustment == 0 ? "" : " (" + getAdjustmentString() + " cents)");
    }

    /**
     * Returns the accidental of the note.
     *
     * @return the accidental.
     */
    public
    Accidental getAccidental() {
        return accidental;
    }

    /**
     * Returns the adjustment of the note.
     *
     * @return the adjustment.
     */
    public
    Short getAdjustment() {
        return adjustment;
    }

    /**
     * Returns the octave of the note.
     *
     * @return the octave.
     */
    public
    Byte getOctave() {
        return octave;
    }

    /**
     * Returns the pitch of the note.
     *
     * @return the pitch.
     */
    public
    Pitch getPitch() {
        return pitch;
    }

    /**
     * Sets the accidental of the note.
     * <p>
     * This implementation does not adjust the result and retains the accidental even if it produces an uncommon note.
     *
     * @param accidental the accidental.
     */
    public
    void setAccidental(
        final Accidental accidental
        ) {
        this.accidental = accidental;
    }

    /**
     * Sets the adjustment of the note. (in cents)
     * <p>
     * This implementation converts null to 0, calls {@link Number#shortValue()} on the adjustment, and does not adjust the note.
     *
     * @param adjustment the adjustment.
     */
    public
    void setAdjustment(
        final Number adjustment
        ) {
        this.adjustment = adjustment == null
                        ? 0
                        : adjustment.shortValue();
    }

    /**
     * Sets the octave of the note.
     *
     * @param octave the octave.
     */
    public
    void setOctave(
        final Byte octave
        ) {
        this.octave = octave;
    }

    /**
     * Sets the pitch of the note.
     * <p>
     * This method does not adjust the result and retains the pitch even if it produces an uncommon note.
     *
     * @param pitch the pitch.
     */
    public
    void setPitch(
        final Pitch pitch
        ) {
        this.pitch = pitch;
    }

    /**
     * <b>Wikipedia:</b> In music, an accidental is a note whose pitch (or pitch class) is not a member of a scale or mode indicated by the most recently applied key signature.
     * In musical notation, the sharp, flat, and natural symbols are used to mark such notes, and the symbols may themselves be called accidental.
     * <p>
     * This class defines the standard accidentals: sharp, flat, and natural.
     * Double-sharp and double-flat accidentals are defined in {@link Scale.Accidental}.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class Accidental
    implements
        AccidentalType,
        Adjusting<Accidental, Number>,
        Cloneable,
        Comparable<Accidental>,
        Delta<Byte>,
        Modulus.Local<Byte>,
        Standardized<Accidental>,
        Supporting<Pitch>,
        Symbolized<String>,
        Unit
    {
        /** The flat accidental. */
        public static final Accidental Flat = new Standard(FlatSym, (short) -100);

        /** The natural accidental. */
        public static final Accidental Natural= new Standard(NaturalSym, (short) 0);

        /** The sharp accidental. */
        public static final Accidental Sharp = new Standard(SharpSym, (short) 100);

        /** The accidental symbol. */
        protected
        String symbol;

        /** The width of the accidental interval in cents. */
        protected final
        short cents;

        /**
         * Creates an accidental with the specified symbol, semitones, and cents.
         *
         * @param symbol the symbol.
         * @param cents the cents.
         */
        protected
        Accidental(
            final String symbol,
            final short cents
            ) {
            this.symbol = symbol;
            this.cents = cents;
        }

        /**
         * Returns the accidental for the specified semitone, or null if the semitones value is out of range.
         *
         * @param semitone the semitone.
         * @return the accidental.
         */
        public static
        Accidental withSemitone(
            final byte semitone
            ) {
            switch (semitone) {
                case 1:
                    return Sharp;

                case -1:
                    return Flat;

                case 0:
                    return Natural;
            }

            return null;
        }

        public static
        Accidental withSymbol(
            final CharSequence symbol
            ) {
            return null;
        }

        /**
         * Returns the adjusted accidental by the specified adjustments as semitone amounts. (not cents)
         */
        @Override
        public Accidental adjusted(final Number... adjustments) {
            byte semitones = getSemitones();
            for (int i = 0; i < adjustments.length; i++)
                if (adjustments[i] != null)
                    semitones += adjustments[i] instanceof Interval
                                 ? ((Interval )adjustments[i]).getSemitones()
                                 : adjustments[i].byteValue();

            if (semitones == 0)
                return this;

            if (this == Sharp)
                switch(semitones) {
                    case 1:
                        return Scale.Accidental.DoubleSharp;

                    case -1:
                        return Natural;

                    case -2:
                        return Flat;

                    case -3:
                        return Scale.Accidental.DoubleFlat;
                }
            else
                if (this == Flat)
                    switch(semitones) {
                        case 3:
                            return Scale.Accidental.DoubleSharp;

                        case 2:
                            return Sharp;

                        case 1:
                            return Natural;

                        case -1:
                            return Scale.Accidental.DoubleFlat;
                    }
                else
                    if (this == Natural)
                        switch(semitones) {
                            case 2:
                                return Scale.Accidental.DoubleSharp;

                            case 1:
                                return Sharp;

                            case -1:
                                return Flat;

                            case -2:
                                return Scale.Accidental.DoubleFlat;
                        }
                    else
                        if (this == Scale.Accidental.DoubleSharp)
                            switch(semitones) {
                                case -1:
                                    return Sharp;

                                case -2:
                                    return Natural;

                                case -3:
                                    return Flat;

                                case -4:
                                    return Scale.Accidental.DoubleFlat;
                            }
                        else
                            if (this == Scale.Accidental.DoubleFlat)
                                switch(semitones) {
                                    case 4:
                                        return Scale.Accidental.DoubleSharp;

                                    case 3:
                                        return Sharp;

                                    case 2:
                                        return Natural;

                                    case 1:
                                        return Flat;
                                }
            return null;
        }

        @Override
        public Accidental clone() {
            return isStandard(this)
                   ? ((Standard) this).clone()
                   : new Accidental(symbol, cents);
        }

        @Override
        public int compareTo(final Accidental accidental) {
            return cents - accidental.cents;
        }

        @Override
        public Accidental convert() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean equals(final Object obj) {
            return obj instanceof Accidental &&
                   ((obj instanceof Standard
                     && this == obj)
                   || ((Accidental) obj).cents == cents);
        }

        /**
         * Returns the order of the accidental.
         *
         * @return the order.
         */
        @Override
        public Byte getOrder() {
            return getSemitones();
        }

        @Override
        public Class<Byte> getOrderClass() {
            return Byte.class;
        }

        @Override
        public Class<Byte> getUnit() {
            return Byte.class;
        }

        @Override
        public boolean is(final system.data.Type<? extends AccidentalType> type) {
            return type == this;
        }

        /**
         * Returns true if the specified accidental is standard, and false otherwise.
         *
         * @param accidental the accidental.
         * @return true if accidental is standard, and false otherwise.
         */
        @Override
        public boolean isStandard(final Accidental accidental) {
            return accidental instanceof Standard;
        }

        public static
        boolean isValidDoubleSymbol(
            final char symbol
            ) {
            return isValidSingleSymbol(symbol) ||
                   isValidNaturalSymbol(symbol);
        }

        public static
        boolean isValidNaturalSymbol(
            final char symbol
            ) {
            return (!NaturalSym.isEmpty() && symbol == NaturalSym.charAt(0)) ||
                   symbol == ' ';
        }

        public static
        boolean isValidSingleSymbol(
            final char symbol
            ) {
            return symbol == SharpSym.charAt(0) ||
                   symbol == FlatSym.charAt(0) ||
                   isValidNaturalSymbol(symbol);
        }

        @Override
        public boolean supports(final Pitch pitch) {
            if (this == Natural)
                return pitch != null;

            switch (pitch) {
                case B:
                    return this == Flat;

                case C:
                    return this == Sharp;

                case E:
                    return this == Flat;

                case F:
                    return this == Sharp;
            }

            return true;
        }

        @Override
        public String getSymbol() {
            return symbol;
        }

        @Override
        public void setSymbol(final String symbol) {
            this.symbol = symbol;
        }

        /**
         * Returns the cents in the accidental.
         *
         * @return the cents.
         */
        public
        short getCents() {
            return cents;
        }

        public
        byte getSemitones() {
            return (byte) Math.round(cents / 100F);
        }

        /**
         * {@code Standard} represents all standard accidentals.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        protected static
        class Standard
        extends Accidental
        {
            /**
             * Creates a standard accidental with the specified symbol, semitones, and cents.
             *
             * @param symbol the symbol.
             * @param cents the cents.
             */
            protected
            Standard(
                final String symbol,
                final short cents
                ) {
                super(symbol, cents);
            }

            @Override
            public Standard clone() {
                return this == Sharp ||
                       this == Flat ||
                       this == Natural
                       ? this
                       : new Standard(symbol, cents);
            }
        }

    }

    /**
     * In music, dynamics normally refers to the volume of a sound or note, but can also refer to every aspect of the execution of a given piece, either stylistic (staccato, legato, etc.) or functional. (velocity)
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class Dynamics
    implements
        Adjusting<Dynamics, Number>,
        Cloneable,
        Comparable<Dynamics>,
        Symbolized<String>,
        music.system.Type<Dynamics>
    {
        /** Pianississimo. (very very soft) */
        public static
        Standard PPP = new Standard(PianississimoSym, PianississimoName, (byte) 16);

        /** Pianissimo. (very soft) */
        public static
        Standard PP = new Standard(PianissimoSym, PianissimoName, (byte) 33);

        /** Piano. (soft) */
        public static
        Standard P = new Standard(PianoSym, PianoName, (byte) 49);

        /** Mezzo-piano. (moderately soft) */
        public static
        Standard MP = new Standard(MezzoPianoSym, MezzoPianoName, (byte) 64);

        /** Mezzo-forte. (moderately loud) */
        public static
        Standard MF = new Standard(MezzoForteSym, MezzoForteName, (byte) 80);

        /** Forte. (loud) */
        public static
        Standard F = new Standard(ForteSym, ForteName, (byte) 96);

        /** Fortissimo. (very loud) */
        public static
        Standard FF = new Standard(FortissimoSym, FortissimoName, (byte) 112);

        /** Fortississimo. (very very loud) */
        public static
        Standard FFF = new Standard(FortississimoSym, FortississimoName, (byte) 127);

        /** The dynamics symbol. */
        protected
        String symbol;

        /** The dynamics name. */
        protected final
        String name;

        /** The dynamics velocity. (loudness) */
        protected final
        byte velocity;

        /**
         * Creates a dynamics with the specified symbol, name, and velocity.
         *
         * @param symbol the symbol.
         * @param name the name.
         * @param velocity the velocity.
         */
        protected
        Dynamics(
            final String symbol,
            final String name,
            final byte velocity
            ) {
            this.symbol = symbol;
            this.name = name;
            this.velocity = velocity;
        }

        /**
         * Creates a dynamics with the specified symbol and velocity.
         *
         * @param symbol the symbol.
         * @param velocity the velocity.
         */
        protected
        Dynamics(
            final String symbol,
            final byte velocity
            ) {
            this(symbol, null, velocity);
        }

        /**
         * Returns true if the specified dynamics is standard, and false otherwise.
         *
         * @param dynamics the dynamics.
         * @return true if dynamics is standard, and false otherwise.
         */
        public static
        boolean isStandard(
            final Dynamics dynamics
            ) {
            return dynamics instanceof Standard;
        }

        /**
         * Returns the dynamics instance with the velocity amount nearest to the specified velocity.
         *
         * @param velocity the velocity.
         *
         * @return the nearest dynamics.
         */
        public static
        Dynamics withVelocity(
            final byte velocity
            ) {
            if (velocity <= Standard.Order[0].velocity || Standard.Order.length == 1)
                return Standard.Order[0];

            int i = Standard.Order.length - 1;
            if (velocity >= Standard.Order[i].velocity)
                return Standard.Order[i];

            for (i = 1; i < Standard.Order.length && Standard.Order[i].velocity < velocity; i++);
            return Standard.Order[i].velocity - velocity <= velocity - Standard.Order[i - 1].velocity
                   ? Standard.Order[i]
                   : Standard.Order[i - 1];
        }

        /**
         * Returns the adjusted dynamics by the specified adjustments as velocity amounts. (byte)
         */
        @Override
        public Dynamics adjusted(Number... adjustments) {
            byte velocity = this.velocity;
            for (int i = 0; i < adjustments.length; i++)
                if (adjustments[i] != null) {
                    final byte amount = adjustments[i].byteValue();
                    velocity += amount;
                    if (velocity < Standard.Order[0].velocity)
                        velocity = amount > 0
                                   ? Standard.Order[Standard.Order.length - 1].velocity
                                   : Standard.Order[0].velocity;
                }

            return withVelocity(velocity);
        }

        @Override
        public Dynamics clone() {
            return isStandard(this)
                   ? (this == PPP ||
                     this == PP ||
                     this == P ||
                     this == MP ||
                     this == MF ||
                     this == F ||
                     this == FF ||
                     this == FFF
                     ? this
                     : new Standard(symbol, name, velocity))
                   : new Dynamics(symbol, name, velocity);
        }

        @Override
        public int compareTo(final Dynamics dynamics) {
            return velocity - dynamics.velocity;
        }

        /**
         * Returns true if the specified object is the same instance as this dynamics, or has a non-null symbol similar to this dynamics.
         *
         * @param obj the object.
         */
        @Override
        public boolean equals(final Object obj) {
            return this == obj ||
                   (obj instanceof Dynamics &&
                   symbol != null &&
                   symbol.equalsIgnoreCase(((Dynamics) obj).symbol));
        }

        @Override
        public String getSymbol() {
            return symbol;
        }

        @Override
        public boolean is(final system.data.Type<? extends Dynamics> type) {
            return equals(type);
        }

        @Override
        public void setSymbol(final String symbol) {
            this.symbol = symbol;
        }

        /**
         * Returns the name of the dynamics.
         *
         * @return the name.
         */
        public
        String getName() {
            return name;
        }

        /**
         * {@code Standard} represents all standard dynamics.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        protected static
        class Standard
        extends Dynamics
        {
            /**
             * Creates a standard dynamics with the specified symbol, name, and velocity.
             *
             * @param symbol the symbol.
             * @param name the name.
             * @param velocity the velocity.
             */
            protected
            Standard(
                final String symbol,
                final String name,
                final byte velocity
                ) {
                super(symbol, name, velocity);
            }

            public static final
            Standard[] Order
            = new Standard[] {
                PPP,
                PP,
                P,
                MP,
                MF,
                F,
                FF,
                FFF
            };
        }
    }

    /**
     * {@code Group} represents an arbitrary grouping of musical notes.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class Group
    extends Note
    implements
        Set<Note>,
        Supporting<Chord>
    {
        /** The group accompaniment. */
        protected
        LinkedList<Note> accompaniment;

        /**
         * Creates a note group starting with the specified note.
         *
         * @param octave the octave.
         * @param pitch the pitch.
         * @param accidental the accidental.
         * @param adjustment the adjustment. (in cents)
         */
        public
        Group(
            final Number octave,
            final Pitch pitch,
            final Accidental accidental,
            final Number adjustment
            ) {
            super(octave, pitch, accidental, adjustment);
        }

        @Override
        public boolean add(final Note note) {
            if (equals(note) || accompaniment.contains(note))
                return false;

            return accompaniment.add(note);
        }

        @Override
        public boolean addAll(final Collection<? extends Note> notes) {
            if (notes == null)
                return false;

            synchronized (this) {
                int size = size();
                notes.forEach(this::add);
                return size < size();
            }
        }

        /**
         * Returns the readjusted note group from the starting note using the specified adjustments.
         * <p>
         * This implementation creates a new note group from the first value in {@code adjustments} or re-uses the starting note's adjustment; and calls {@link #adjust()} on all notes in the group.
         *
         * @param adjustments the adjustments.
         */
        @Override
        public Note adjusted(final Number... adjustments) {
            final Group group = new Group(octave, pitch, accidental, adjustments.length == 0 ? adjustment : adjustments[0])
            {
                @Override
                public int size() {
                    return adjustments.length + 1;
                }
            };

            if (adjustments.length > 0) {
                group.accompaniment = new LinkedList<Note>();
                for (int i = 1; i < adjustments.length; i++) {
                    final Note note = new Note(octave, pitch, accidental, adjustments[i]);
                    note.adjust();
                }
            }

            group.adjust();
            return group;
        }

        @Override
        public void clear() {
            accompaniment.clear();
        }

        @Override
        public boolean contains(final Object obj) {
            return obj instanceof Note &&
                   (equals((Note) obj) ||
                   accompaniment.contains(obj));
        }

        @Override
        public boolean containsAll(Collection<?> c) { return false; }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Iterator<Note> iterator() { return null; }

        @Override
        public boolean remove(Object o) {return false;}

        @Override
        public boolean removeAll(Collection<?> c) {return false;}

        @Override
        public boolean retainAll(Collection<?> c) {return false;}

        @Override
        public int size() {
            return accompaniment == null
                   ? 1
                   : accompaniment.size() + 1;
        }

        @Override
        public boolean supports(Chord instance) {return false;}

        @Override
        public Object[] toArray() { return null; }

        @Override
        public <T> T[] toArray(T[] a) { return null; }

        /**
         * Returns the note group accompaniment.
         * <p>
         * This implementation returns null if no accompaniment is added.
         *
         * @return the list of accompanied notes.
         */
        public
        List<Note> getAccompaniment() {
            return accompaniment;
        }
    }

    /**
     * {@code Octave} categorizes all standard octaves.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Octave
    extends
        Ordered<Byte>,
        OctaveType,
        Symbolized<String>,
        Unit
    {
        public final Octave NegativeSecond = new Octave() {
            @Override
            public Byte getOrder() {
                return (byte) -2;
            }

            @Override
            public String getSymbol() {
                return NegativeSecondSym;
            }
        };

        public final Octave NegativeFirst = new Octave() {
            @Override
            public Byte getOrder() {
                return (byte) -1;
            }

            @Override
            public String getSymbol() {
                return NegativeFirstSym;
            }
        };

        public final Octave Zeroth = new Octave() {
            @Override
            public Byte getOrder() {
                return (byte) 0;
            }

            @Override
            public String getSymbol() {
                return ZerothSym;
            }
        };

        public final Octave First = new Octave() {
            @Override
            public Byte getOrder() {
                return (byte) 1;
            }

            @Override
            public String getSymbol() {
                return FirstSym;
            }
        };

        public final Octave Second = new Octave() {
            @Override
            public Byte getOrder() {
                return (byte) 2;
            }

            @Override
            public String getSymbol() {
                return SecondSym;
            }
        };

        public final Octave Third = new Octave() {
            @Override
            public Byte getOrder() {
                return (byte) 3;
            }

            @Override
            public String getSymbol() {
                return ThirdSym;
            }
        };

        public final Octave Fourth = new Octave() {
            @Override
            public Byte getOrder() {
                return (byte) 4;
            }

            @Override
            public String getSymbol() {
                return FourthSym;
            }
        };

        public final Octave Fifth = new Octave() {
            @Override
            public Byte getOrder() {
                return (byte) 5;
            }

            @Override
            public String getSymbol() {
                return FifthSym;
            }
        };

        public final Octave Sixth = new Octave() {
            @Override
            public Byte getOrder() {
                return (byte) 6;
            }

            @Override
            public String getSymbol() {
                return SixthSym;
            }
        };

        public final Octave Seventh = new Octave() {
            @Override
            public Byte getOrder() {
                return (byte) 7;
            }

            @Override
            public String getSymbol() {
                return SeventhSym;
            }
        };

        public final Octave Eighth = new Octave() {
            @Override
            public Byte getOrder() {
                return (byte) 8;
            }

            @Override
            public String getSymbol() {
                return NinthSym;
            }
        };

        public final Octave Ninth = new Octave() {
            @Override
            public Byte getOrder() {
                return (byte) 9;
            }

            @Override
            public String getSymbol() {
                return NinthSym;
            }
        };

        public final Octave Tenth = new Octave() {
            @Override
            public Byte getOrder() {
                return (byte) 10;
            }

            @Override
            public String getSymbol() {
                return TenthSym;
            }
        };

        public final Octave Null = null;

        static
        Octave valueOf(
            final Class<? extends Octave> octave
            ) {
            if (octave.getClass().equals(Fourth.getClass()))
                return Fourth;
            else
            if (octave.getClass().equals(Third.getClass()))
                return Third;
            else
            if (octave.getClass().equals(Fifth.getClass()))
                return Fifth;
            else
            if (octave.getClass().equals(Sixth.getClass()))
                return Sixth;
            else
            if (octave.getClass().equals(Second.getClass()))
                return Second;
            else
            if (octave.getClass().equals(Seventh.getClass()))
                return Seventh;
            else
            if (octave.getClass().equals(Eighth.getClass()))
                return Eighth;
            else
            if (octave.getClass().equals(First.getClass()))
                return First;
            else
            if (octave.getClass().equals(Zeroth.getClass()))
                return Zeroth;
            else
            if (octave.getClass().equals(Ninth.getClass()))
                return Ninth;
            else
            if (octave.getClass().equals(NegativeFirst.getClass()))
                return NegativeFirst;
            else
            if (octave.getClass().equals(Tenth.getClass()))
                return Tenth;
            else
            if (octave.getClass().equals(NegativeSecond.getClass()))
                return NegativeSecond;

            return Null;
        }

        static
        Octave withOrder(
            final Number order
            ) {
            switch (order.byteValue()) {
                case -2:
                    return NegativeSecond;

                case -1:
                    return NegativeFirst;

                case 0:
                    return Zeroth;

                case 1:
                    return First;

                case 2:
                    return Second;

                case 3:
                    return Third;

                case 4:
                    return Fourth;

                case 5:
                    return Fifth;

                case 6:
                    return Sixth;

                case 7:
                    return Seventh;

                case 8:
                    return Eighth;

                case 9:
                    return Ninth;

                case 10:
                    return Tenth;

                default:
                    throw new IllegalArgumentException("order is out of range.");
            }
        }

        @Override
        default Class<Byte> getOrderClass() {
            return Byte.class;
        }

        @Override
        default boolean is(final system.data.Type<? extends OctaveType> type) {
            return type == this;
        }
    }

    /**
     * {@code Pitch} represents one of the seven standard pitches in Western classical music: A, B, C, D, E, F, and G.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    enum Pitch
    implements
        Adjusting<Pitch, Interval>,
        Modulus.Tabular<Byte>,
        PitchType,
        Supporting<Accidental>,
        Symbolized<String>,
        Unit
    {
        /** The A pitch. (9) */
        A((byte) 9),

        /** The B pitch. (11) */
        B((byte) 11),

        /** The C pitch. (0) */
        C((byte) 0),

        /** The D pitch. (2) */
        D((byte) 2),

        /** The E pitch. (4) */
        E((byte) 4),

        /** The F pitch. (5) */
        F((byte) 5),

        /** The G pitch. (7) */
        G((byte) 7);

        /** The pitch order.<p>The pitch order must be between 0 and 11. */
        public final
        byte order;

        /**
         * Creates a pitch with the specified order.
         *
         * @param order the order.
         */
        private
        Pitch(
            final byte order
            ) {
            this.order = order;
        }

        public static
        boolean isValid(
            final char symbol
            ) {
            final char upper = Character.toUpperCase(symbol);
            for (Pitch pitch : values())
                if (pitch.name().charAt(0) == upper)
                    return true;

            return false;
        }

        public static
        Pitch valueOf(
            final char symbol
            ) {
            return valueOf(Character.toString(Character.toUpperCase(symbol)));
        }

        /**
         * Returns the pitch for the specified order, or throws an {@code IllegalArgumentException} if order is out of range.
         * <p>
         * The pitch order must be between 0 and 11. (inclusive)
         *
         * @param order the order.
         * @return the pitch.
         * @throws IllegalArgumentException if order is out of range.
         */
        public static
        Pitch withOrder(
            final byte order
            ) {
            switch (order) {
            case 0:
            case 1:
                return C;

            case 2:
            case 3:
                return D;

            case 4:
                return E;

            case 5:
            case 6:
                return F;

            case 7:
            case 8:
                return G;

            case 9:
            case 10:
                return A;

            case 11:
                return B;

            default:
                throw new IllegalArgumentException("Pitch order must be between 0 and 11.");
            }
        }

        /**
         * Adjusts this pitch by the specified adjustments, as semitones, and returns this pitch.
         *
         * @param adjustments the adjustments.
         *
         * @return the adjusted pitch.
         */
        public
        Pitch adjusted(
            final Number... adjustments
            ) {
            if (adjustments.length == 0)
                return this;

            short order = this.order;
            for (int i = 0; i < adjustments.length; i++)
                if (adjustments[i] != null) {
                    order += adjustments[i].shortValue();
                    order -= (order / 12) * 12;
                }

            return order == this.order
                   ? this
                   : withOrder((byte) order);
        }

        /**
         * Adjusts this pitch by the specified adjustment intervals, as semitones, and returns this pitch.
         *
         * @param adjustments the adjustment intervals.
         *
         * @return the adjusted pitch.
         */
        @Override
        public Pitch adjusted(final Interval... adjustments) {
            Number[] semitones = new Number[adjustments.length];
            for (int i = 0; i < adjustments.length; semitones[i] = adjustments[i++].getSemitones());
            return adjusted(semitones);
        }

        @Override
        public PitchType convert() {
            // TODO - Requires context in order to be converted
            return this;
        }

        /**
         * Returns the order of the pitch.
         *
         * @return the order.
         */
        @Override
        public Byte getOrder() {
            return order;
        }

        @Override
        public Class<Byte> getOrderClass() {
            return Byte.class;
        }

        @Override
        public String getSymbol() {
            return name();
        }

        @Override
        public boolean is(final system.data.Type<? extends PitchType> type) {
            return type == this;
        }

        @Override
        public boolean supports(final Accidental accidental) {
            switch(this) {
            case B:
                return accidental != Sharp;

            case C:
                return accidental != Flat;

            case E:
                return accidental != Sharp;

            case F:
                return accidental != Flat;
            }

            return true;
        }

        @Override
        public String toString() {
            return getSymbol();
        }
    }

    /**
     * {@code Scientific} classifies all scientific note system in music theory.
     * <p>
     * This class implementation is in progress.
     *
     * @param <E> the first numerically ordered data type.
     * @param <X> the second numerically ordered data type.
     * @param <Y> the regressor data type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Scientific<E extends Ordered<? extends Number>, X extends Ordered<? extends Number>, Y extends Regressor>
    extends TemplativeRegression<E, X, Y>
    {
        /**
         * {@code Progression} classifies progressive operations in music theory.
         * <p>
         * This class implementation is in progress.
         *
         * @param <X> the first progressor data type.
         * @param <Y> the second progressor data type.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Progression<X extends Progressor, Y extends Progressor>
        extends Progressive<X, Y>
        {}

        /**
         * {@code Regression} classifies regressive operations in music theory.
         * <p>
         * This class implementation is in progress.
         *
         * @param <X> the regressor data type.
         * @param <Y> the progressor data type.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface Regression<X extends Regressor, Y extends Progressor>
        extends Regressive<X, Y>
        {}

        /**
         * {@code System} classifies systems of notes.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        interface System
        extends Scientific<Note, Octave, Modulus>
        {
            /**
             * {@code DataPoint} classifies all data points representing notes in a system.
             * <p>
             * This class implementation is in progress.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public
            interface DataPoint
            extends
                Clockable<Note>,
                music.system.DataPoint<Modulus.Tabular<?>, Modulus.Local<?>, Octave>,
                Modulus,
                music.system.Type<Note>
            {
                public
                Modulus.Local<?> getAccidental();

                public default
                Octave getOctave() {
                    return Octave.Null;
                }

                public
                Modulus.Tabular<?> getPitch();
            }
        }
    }

    /**
     * {@code Spective} classifies categorizes that are well-defined in the context of note transformation.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Spective
    extends
        Ordered<Short>,
        Templator,
        Unit
    {
        @Override
        public default Class<Short> getOrderClass() {
            return Short.class;
        }

        public
        Interval getInterval();

        public final static Spective Full = new Spective()
        {
            @Override
            public Short getOrder() {
                return (byte) 200;
            }

            @Override
            public Interval getInterval() {
                return Interval.MajorSecond;
            }
        };

        public final static Spective Half = new Spective()
        {
            @Override
            public Short getOrder() {
                return (byte) 100;
            }

            @Override
            public Interval getInterval() {
                return Interval.MinorSecond;
            }
        };

        public final static Spective Quarter = new Spective()
        {
            @Override
            public Short getOrder() {
                return (byte) 50;
            }

            @Override
            public Interval getInterval() {
                return Interval.QuarterTone;
            }
        };

        public final static Spective Eighth = new Spective()
        {
            private
            Interval kind;

            @Override
            public Short getOrder() {
                return (byte) 25;
            }

            @Override
            public Interval getInterval() {
                if (kind == null)
                    synchronized (this) {
                        if (kind == null)
                            kind = new Interval((short) 25);
                    }

                return kind;
            }
        };

        public final static Spective Tenor = new Spective()
        {
            private
            Interval kind;

            @Override
            public Short getOrder() {
                return (byte) 10;
            }

            @Override
            public Interval getInterval() {
                if (kind == null)
                    synchronized (this) {
                        if (kind == null)
                            kind = new Interval((short) 10);
                    }

                return kind;
            }
        };
    }

    /**
     * {@code Standard} represents all classical standard notes.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected static final
    class Standard
    extends Note
    implements Scientific.System
    {
        public static final
        Standard[] Order
        = new Standard[] {
            C_1,
            C_1s, D_1f,
            D_1,
            D_1s, E_1f,
            E_1,
            F_1,
            F_1s, G_1f,
            G_1,
            G_1s, A_1f,

            A_1,
            A_1s, B_1f,
            B_1,
            C0,
            C0s, D0f,
            D0,
            D0s, E0f,
            E0,
            F0,
            F0s, G0f,
            G0,
            G0s, A0f,

            A0,
            A0s, B0f,
            B0,
            C1,
            C1s, D1f,
            D1,
            D1s, E1f,
            E1,
            F1,
            F1s, G1f,
            G1,
            G1s, A1f,

            A1,
            A1s, B1f,
            B1,
            C2,
            C2s, D2f,
            D2,
            D2s, E2f,
            E2,
            F2,
            F2s, G2f,
            G2,
            G2s, A2f,

            A2,
            A2s, B2f,
            B2,
            C3,
            C3s, D3f,
            D3,
            D3s, E3f,
            E3,
            F3,
            F3s, G3f,
            G3,
            G3s, A3f,

            A3,
            A3s, B3f,
            B3,
            C4,
            C4s, D4f,
            D4,
            D4s, E4f,
            E4,
            F4,
            F4s, G4f,
            G4,
            G4s, A4f,

            A4,
            A4s, B4f,
            B4,
            C5,
            C5s, D5f,
            D5,
            D5s, E5f,
            E5,
            F5,
            F5s, G5f,
            G5,
            G5s, A5f,

            A5,
            A5s, B5f,
            B5,
            C6,
            C6s, D6f,
            D6,
            D6s, E6f,
            E6,
            F6,
            F6s, G6f,
            G6,
            G6s, A6f,

            A6,
            A6s, B6f,
            B6,
            C7,
            C7s, D7f,
            D7,
            D7s, E7f,
            E7,
            F7,
            F7s, G7f,
            G7,
            G7s, A7f,

            A7,
            A7s, B7f,
            B7,
            C8,
            C8s, D8f,
            D8,
            D8s, E8f,
            E8,
            F8,
            F8s, G8f,
            G8,
            G8s, A8f,

            A8,
            A8s, B8f,
            B8,
            C8,
            C9s, D9f,
            D9,
            D9s, E9f,
            E9,
            F9,
            F9s, G9f,
            G9,
            G9s, A9f,

            A9,
            A9s, B9f,
            B9,
            C9,
            C10s, D10f,
            D10,
            D10s, E10f,
            E10,
            F10,
            F10s, G10f,
            G10,
            G10s, A10f,

            A10,
            A10s, B10f,
            B10,
        };

        public final
        float number;

        private final
        float freq;

        private Standard(float number, String symbol, byte octave, Pitch pitch, Accidental accidental, float freq) {
            super(octave, pitch, accidental);
            this.number = number;
            this.freq = freq;
            setSymbol(symbol);
        }

        private Standard(float number, String symbol, byte octave, Pitch pitch, float freq) {
            this(number, symbol, octave, pitch, Accidental.Natural, freq);
        }

        @Override
        public float getFrequency() {
            return freq;
        }

        @Override
        public float getNumber() {
            return number;
        }
    }
}