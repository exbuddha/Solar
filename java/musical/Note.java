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
import static system.data.Constant.OperationImpossible;
import static system.data.Constant.OrderOutOfRange;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import music.system.data.Clockable;
import music.system.data.Delta;
import music.system.data.Ordered;
import musical.Spectrum.Modulus;
import system.data.Invertible;
import system.data.Inverting;
import system.data.Lambda;
import system.data.Operable;
import system.data.Symbolized;
import system.data.Lambda.BinaryLocator;

/**
 * {@code Note} represents the musical note of a certain octave and pitch.
 * <p>
 * Notes are Java number types and represent their relative number as a {@code float} matching the standard MIDI note numbers.
 * <p>
 * A note with null octave is considered to be a pitch type, for which some functionality will fail.
 * Notes must have a pitch.
 * Pitch symbols only support single characters.
 * Notes can also have accidentals and adjustments measured in cents.
 * <p>
 * Double-sharp and double-flat accidentals are not supported.
 * They are maintained in the subclass {@link Scale.Accidental}.
 * <p>
 * Note adjustment is accounted for in all operations and comparisons.
 * <p>
 * This class defines all standard note in classical music as static objects called singletons.
 * Singletons are note types that will clone automatically, when operated on, into intermediary note types that can automatically diverge back to a singleton when possible.
 * This guarantees that operating on singletons will always return a singleton if there is one available that matches the result.
 * The intermediary note types are called standard notes.
 * <p>
 * Methods in this class implementation are not thread-safe.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class Note
extends Number
implements
    Adjustable,
    Adjusting<Note, Number>,
    Clockable<Note>,
    Cloneable,
    Comparable<Note>,
    Invertible,
    Inverting<Note>,
    Localizable,
    Modulus,
    NoteType,
    Operable<Number>,
    Ordered<Float>,
    Symbolized<String>,
    Unadjustable,
    Unadjusting<Note>,
    Unit
{
    /** A constant holding the maximum value a {@code Note} can have, 1527. */
    public static final
    short MAX_VALUE = 1527;

    /** A constant holding the minimum value a {@code Note} can have, -1524. */
    public static final
    short MIN_VALUE = -1524;

    /** The {@code Class} instance representing the type {@code Note}. */
    public static final
    Class<Note> TYPE = Note.class;

    public static final
    Singleton C_1 = new Singleton(0F, C_1Sym, (byte) -1, Pitch.C, 8.18F);

    public static final
    Singleton C_1s = new Singleton(1F, C_1sSym, (byte) -1, Pitch.C, Sharp, 8.66F);

    public static final
    Singleton D_1f = new Singleton(1F, D_1fSym, (byte) -1, Pitch.D, Flat, 8.66F);

    public static final
    Singleton D_1 = new Singleton(2F, D_1Sym, (byte) -1, Pitch.D, 9.18F);

    public static final
    Singleton D_1s = new Singleton(3F, D_1sSym, (byte) -1, Pitch.D, Sharp, 9.72F);

    public static final
    Singleton E_1f = new Singleton(3F, E_1fSym, (byte) -1, Pitch.E, Flat, 9.72F);

    public static final
    Singleton E_1 = new Singleton(4F, E_1Sym, (byte) -1, Pitch.E, 10.30F);

    public static final
    Singleton F_1 = new Singleton(5F, F_1Sym, (byte) -1, Pitch.F, 10.91F);

    public static final
    Singleton F_1s = new Singleton(6F, F_1sSym, (byte) -1, Pitch.F, Sharp, 11.56F);

    public static final
    Singleton G_1f = new Singleton(6F, G_1fSym, (byte) -1, Pitch.G, Flat, 11.56F);

    public static final
    Singleton G_1 = new Singleton(7F, G_1Sym, (byte) -1, Pitch.G, 12.25F);

    public static final
    Singleton G_1s = new Singleton(8F, G_1sSym, (byte) -1, Pitch.G, Sharp, 12.98F);

    public static final
    Singleton A_1f = new Singleton(8F, A_1fSym, (byte) -1, Pitch.A, Flat, 12.98F);

    public static final
    Singleton A_1 = new Singleton(9F, A_1Sym, (byte) -1, Pitch.A, 13.75F);

    public static final
    Singleton A_1s = new Singleton(10F, A_1sSym, (byte) -1, Pitch.A, Sharp, 14.57F);

    public static final
    Singleton B_1f = new Singleton(10F, B_1fSym, (byte) -1, Pitch.B, Flat, 14.57F);

    public static final
    Singleton B_1 = new Singleton(11F, B_1Sym, (byte) -1, Pitch.B, 15.43F);

    public static final
    Singleton C0 = new Singleton(12F, C0Sym, (byte) 0, Pitch.C, 16.35F);

    public static final
    Singleton C0s = new Singleton(13F, C0sSym, (byte) 0, Pitch.C, Sharp, 17.32F);

    public static final
    Singleton D0f = new Singleton(13F, D0fSym, (byte) 0, Pitch.D, Flat, 17.32F);

    public static final
    Singleton D0 = new Singleton(14F, D0Sym, (byte) 0, Pitch.D, 18.35F);

    public static final
    Singleton D0s = new Singleton(15F, D0sSym, (byte) 0, Pitch.D, Sharp, 19.45F);

    public static final
    Singleton E0f = new Singleton(15F, E0fSym, (byte) 0, Pitch.E, Flat, 19.45F);

    public static final
    Singleton E0 = new Singleton(16F, E0Sym, (byte) 0, Pitch.E, 20.60F);

    public static final
    Singleton F0 = new Singleton(17F, F0Sym, (byte) 0, Pitch.F, 21.83F);

    public static final
    Singleton F0s = new Singleton(18F, F0sSym, (byte) 0, Pitch.F, Sharp, 23.12F);

    public static final
    Singleton G0f = new Singleton(18F, G0fSym, (byte) 0, Pitch.G, Flat, 23.12F);

    public static final
    Singleton G0 = new Singleton(19F, G0Sym, (byte) 0, Pitch.G, 24.50F);

    public static final
    Singleton G0s = new Singleton(20F, G0sSym, (byte) 0, Pitch.G, Sharp, 25.96F);

    public static final
    Singleton A0f = new Singleton(20F, A0fSym, (byte) 0, Pitch.A, Flat, 25.96F);

    public static final
    Singleton A0 = new Singleton(21F, A0Sym, (byte) 0, Pitch.A, 27.50F);

    public static final
    Singleton A0s = new Singleton(22F, A0sSym, (byte) 0, Pitch.A, Sharp, 29.14F);

    public static final
    Singleton B0f = new Singleton(22F, B0fSym, (byte) 0, Pitch.B, Flat, 29.14F);

    public static final
    Singleton B0 = new Singleton(23F, B0Sym, (byte) 0, Pitch.B, 30.87F);

    public static final
    Singleton C1 = new Singleton(24F, C1Sym, (byte) 1, Pitch.C, 32.70F);

    public static final
    Singleton C1s = new Singleton(25F, C1sSym, (byte) 1, Pitch.C, Sharp, 34.65F);

    public static final
    Singleton D1f = new Singleton(25F, D1fSym, (byte) 1, Pitch.D, Flat, 34.65F);

    public static final
    Singleton D1 = new Singleton(26F, D1Sym, (byte) 1, Pitch.D, 36.71F);

    public static final
    Singleton D1s = new Singleton(27F, D1sSym, (byte) 1, Pitch.D, Sharp, 38.89F);

    public static final
    Singleton E1f = new Singleton(27F, E1fSym, (byte) 1, Pitch.E, Flat, 38.89F);

    public static final
    Singleton E1 = new Singleton(28F, E1Sym, (byte) 1, Pitch.E, 41.20F);

    public static final
    Singleton F1 = new Singleton(29F, F1Sym, (byte) 1, Pitch.F, 43.65F);

    public static final
    Singleton F1s = new Singleton(30F, F1sSym, (byte) 1, Pitch.F, Sharp, 46.25F);

    public static final
    Singleton G1f = new Singleton(30F, G1fSym, (byte) 1, Pitch.G, Flat, 46.25F);

    public static final
    Singleton G1 = new Singleton(31F, G1Sym, (byte) 1, Pitch.G, 49.00F);

    public static final
    Singleton G1s = new Singleton(32F, G1sSym, (byte) 1, Pitch.G, Sharp, 51.91F);

    public static final
    Singleton A1f = new Singleton(32F, A1fSym, (byte) 1, Pitch.A, Flat, 51.91F);

    public static final
    Singleton A1 = new Singleton(33F, A1Sym, (byte) 1, Pitch.A, 55.00F);

    public static final
    Singleton A1s = new Singleton(34F, A1sSym, (byte) 1, Pitch.A, Sharp, 58.27F);

    public static final
    Singleton B1f = new Singleton(34F, B1fSym, (byte) 1, Pitch.B, Flat, 58.27F);

    public static final
    Singleton B1 = new Singleton(35F, B1Sym, (byte) 1, Pitch.B, 61.74F);

    public static final
    Singleton C2 = new Singleton(36F, C2Sym, (byte) 2, Pitch.C, 65.41F);

    public static final
    Singleton C2s = new Singleton(37F, C2sSym, (byte) 2, Pitch.C, Sharp, 69.30F);

    public static final
    Singleton D2f = new Singleton(37F, D2fSym, (byte) 2, Pitch.D, Flat, 69.30F);

    public static final
    Singleton D2 = new Singleton(38F, D2Sym, (byte) 2, Pitch.D, 73.42F);

    public static final
    Singleton D2s = new Singleton(39F, D2sSym, (byte) 2, Pitch.D, Sharp, 77.78F);

    public static final
    Singleton E2f = new Singleton(39F, E2fSym, (byte) 2, Pitch.E, Flat, 77.78F);

    public static final
    Singleton E2 = new Singleton(40F, E2Sym, (byte) 2, Pitch.E, 82.41F);

    public static final
    Singleton F2 = new Singleton(41F, F2Sym, (byte) 2, Pitch.F, 87.31F);

    public static final
    Singleton F2s = new Singleton(42F, F2sSym, (byte) 2, Pitch.F, Sharp, 92.50F);

    public static final
    Singleton G2f = new Singleton(42F, G2fSym, (byte) 2, Pitch.G, Flat, 92.50F);

    public static final
    Singleton G2 = new Singleton(43F, G2Sym, (byte) 2, Pitch.G, 98.00F);

    public static final
    Singleton G2s = new Singleton(44F, G2sSym, (byte) 2, Pitch.G, Sharp, 103.83F);

    public static final
    Singleton A2f = new Singleton(44F, A2fSym, (byte) 2, Pitch.A, Flat, 103.83F);

    public static final
    Singleton A2 = new Singleton(45F, A2Sym, (byte) 2, Pitch.A, 110.00F);

    public static final
    Singleton A2s = new Singleton(46F, A2sSym, (byte) 2, Pitch.A, Sharp, 116.54F);

    public static final
    Singleton B2f = new Singleton(46F, B2fSym, (byte) 2, Pitch.B, Flat, 116.54F);

    public static final
    Singleton B2 = new Singleton(47F, B2Sym, (byte) 2, Pitch.B, 123.47F);

    public static final
    Singleton C3 = new Singleton(48F, C3Sym, (byte) 3, Pitch.C, 130.81F);

    public static final
    Singleton C3s = new Singleton(49F, C3sSym, (byte) 3, Pitch.C, Sharp, 138.59F);

    public static final
    Singleton D3f = new Singleton(49F, D3fSym, (byte) 3, Pitch.D, Flat, 138.59F);

    public static final
    Singleton D3 = new Singleton(50F, D3Sym, (byte) 3, Pitch.D, 146.83F);

    public static final
    Singleton D3s = new Singleton(51F, D3sSym, (byte) 3, Pitch.D, Sharp, 155.56F);

    public static final
    Singleton E3f = new Singleton(51F, E3fSym, (byte) 3, Pitch.E, Flat, 155.56F);

    public static final
    Singleton E3 = new Singleton(52F, E3Sym, (byte) 3, Pitch.E, 164.81F);

    public static final
    Singleton F3 = new Singleton(53F, F3Sym, (byte) 3, Pitch.F, 174.61F);

    public static final
    Singleton F3s = new Singleton(54F, F3sSym, (byte) 3, Pitch.F, Sharp, 185.00F);

    public static final
    Singleton G3f = new Singleton(54F, G3fSym, (byte) 3, Pitch.G, Flat, 185.00F);

    public static final
    Singleton G3 = new Singleton(55F, G3Sym, (byte) 3, Pitch.G, 196.00F);

    public static final
    Singleton G3s = new Singleton(56F, G3sSym, (byte) 3, Pitch.G, Sharp, 207.65F);

    public static final
    Singleton A3f = new Singleton(56F, A3fSym, (byte) 3, Pitch.A, Flat, 207.65F);

    public static final
    Singleton A3 = new Singleton(57F, A3Sym, (byte) 3, Pitch.A, 220.00F);

    public static final
    Singleton A3s = new Singleton(58F, A3sSym, (byte) 3, Pitch.A, Sharp, 233.08F);

    public static final
    Singleton B3f = new Singleton(58F, B3fSym, (byte) 3, Pitch.B, Flat, 233.08F);

    public static final
    Singleton B3 = new Singleton(59F, B3Sym, (byte) 3, Pitch.B, 246.94F);

    public static final
    Singleton C4 = new Singleton(60F, C4Sym, (byte) 4, Pitch.C, 261.63F);

    public static final
    Singleton C4s = new Singleton(61F, C4sSym, (byte) 4, Pitch.C, Sharp, 277.18F);

    public static final
    Singleton D4f = new Singleton(61F, D4fSym, (byte) 4, Pitch.D, Flat, 277.18F);

    public static final
    Singleton D4 = new Singleton(62F, D4Sym, (byte) 4, Pitch.D, 293.66F);

    public static final
    Singleton D4s = new Singleton(63F, D4sSym, (byte) 4, Pitch.D, Sharp, 311.13F);

    public static final
    Singleton E4f = new Singleton(63F, E4fSym, (byte) 4, Pitch.E, Flat, 311.13F);

    public static final
    Singleton E4 = new Singleton(64F, E4Sym, (byte) 4, Pitch.E, 329.63F);

    public static final
    Singleton F4 = new Singleton(65F, F4Sym, (byte) 4, Pitch.F, 349.23F);

    public static final
    Singleton F4s = new Singleton(66F, F4sSym, (byte) 4, Pitch.F, Sharp, 369.99F);

    public static final
    Singleton G4f = new Singleton(66F, G4fSym, (byte) 4, Pitch.G, Flat, 369.99F);

    public static final
    Singleton G4 = new Singleton(67F, G4Sym, (byte) 4, Pitch.G, 392.00F);

    public static final
    Singleton G4s = new Singleton(68F, G4sSym, (byte) 4, Pitch.G, Sharp, 415.30F);

    public static final
    Singleton A4f = new Singleton(68F, A4fSym, (byte) 4, Pitch.A, Flat, 415.30F);

    public static final
    Singleton A4 = new Singleton(69F, A4Sym, (byte) 4, Pitch.A, 440F);

    public static final
    Singleton A4s = new Singleton(70F, A4sSym, (byte) 4, Pitch.A, Sharp, 466.16F);

    public static final
    Singleton B4f = new Singleton(70F, B4fSym, (byte) 4, Pitch.B, Flat, 466.16F);

    public static final
    Singleton B4 = new Singleton(71F, B4Sym, (byte) 4, Pitch.B, 493.88F);

    public static final
    Singleton C5 = new Singleton(72F, C5Sym, (byte) 5, Pitch.C, 523.25F);

    public static final
    Singleton C5s = new Singleton(73F, C5sSym, (byte) 5, Pitch.C, Sharp, 554.37F);

    public static final
    Singleton D5f = new Singleton(73F, D5fSym, (byte) 5, Pitch.D, Flat, 554.37F);

    public static final
    Singleton D5 = new Singleton(74F, D5Sym, (byte) 5, Pitch.D, 587.33F);

    public static final
    Singleton D5s = new Singleton(75F, D5sSym, (byte) 5, Pitch.D, Sharp, 622.25F);

    public static final
    Singleton E5f = new Singleton(75F, E5fSym, (byte) 5, Pitch.E, Flat, 622.25F);

    public static final
    Singleton E5 = new Singleton(76F, E5Sym, (byte) 5, Pitch.E, 659.26F);

    public static final
    Singleton F5 = new Singleton(77F, F5Sym, (byte) 5, Pitch.F, 698.46F);

    public static final
    Singleton F5s = new Singleton(78F, F5sSym, (byte) 5, Pitch.F, Sharp, 739.99F);

    public static final
    Singleton G5f = new Singleton(78F, G5fSym, (byte) 5, Pitch.G, Flat, 739.99F);

    public static final
    Singleton G5 = new Singleton(79F, G5Sym, (byte) 5, Pitch.G, 783.99F);

    public static final
    Singleton G5s = new Singleton(80F, G5sSym, (byte) 5, Pitch.G, Sharp, 830.61F);

    public static final
    Singleton A5f = new Singleton(80F, A5fSym, (byte) 5, Pitch.A, Flat, 830.61F);

    public static final
    Singleton A5 = new Singleton(81F, A5Sym, (byte) 5, Pitch.A, 880.00F);

    public static final
    Singleton A5s = new Singleton(82F, A5sSym, (byte) 5, Pitch.A, Sharp, 932.33F);

    public static final
    Singleton B5f = new Singleton(82F, B5fSym, (byte) 5, Pitch.B, Flat, 932.33F);

    public static final
    Singleton B5 = new Singleton(83F, B5Sym, (byte) 5, Pitch.B, 987.77F);

    public static final
    Singleton C6 = new Singleton(84F, C6Sym, (byte) 6, Pitch.C, 1046.50F);

    public static final
    Singleton C6s = new Singleton(85F, C6sSym, (byte) 6, Pitch.C, Sharp, 1108.73F);

    public static final
    Singleton D6f = new Singleton(85F, D6fSym, (byte) 6, Pitch.D, Flat, 1108.73F);

    public static final
    Singleton D6 = new Singleton(86F, D6Sym, (byte) 6, Pitch.D, 1174.66F);

    public static final
    Singleton D6s = new Singleton(87F, D6sSym, (byte) 6, Pitch.D, Sharp, 1244.51F);

    public static final
    Singleton E6f = new Singleton(87F, E6fSym, (byte) 6, Pitch.E, Flat, 1244.51F);

    public static final
    Singleton E6 = new Singleton(88F, E6Sym, (byte) 6, Pitch.E, 1318.51F);

    public static final
    Singleton F6 = new Singleton(89F, F6Sym, (byte) 6, Pitch.F, 1396.91F);

    public static final
    Singleton F6s = new Singleton(90F, F6sSym, (byte) 6, Pitch.F, Sharp, 1479.98F);

    public static final
    Singleton G6f = new Singleton(90F, G6fSym, (byte) 6, Pitch.G, Flat, 1479.98F);

    public static final
    Singleton G6 = new Singleton(91F, G6Sym, (byte) 6, Pitch.G, 1567.98F);

    public static final
    Singleton G6s = new Singleton(92F, G6sSym, (byte) 6, Pitch.G, Sharp, 1661.22F);

    public static final
    Singleton A6f = new Singleton(92F, A6fSym, (byte) 6, Pitch.A, Flat, 1661.22F);

    public static final
    Singleton A6 = new Singleton(93F, A6Sym, (byte) 6, Pitch.A, 1760.00F);

    public static final
    Singleton A6s = new Singleton(94F, A6sSym, (byte) 6, Pitch.A, Sharp, 1864.66F);

    public static final
    Singleton B6f = new Singleton(94F, B6fSym, (byte) 6, Pitch.B, Flat, 1864.66F);

    public static final
    Singleton B6 = new Singleton(95F, B6Sym, (byte) 6, Pitch.B, 1975.53F);

    public static final
    Singleton C7 = new Singleton(96F, C7Sym, (byte) 7, Pitch.C, 2093.00F);

    public static final
    Singleton C7s = new Singleton(97F, C7sSym, (byte) 7, Pitch.C, Sharp, 2217.46F);

    public static final
    Singleton D7f = new Singleton(97F, D7fSym, (byte) 7, Pitch.D, Flat, 2217.46F);

    public static final
    Singleton D7 = new Singleton(98F, D7Sym, (byte) 7, Pitch.D, 2349.32F);

    public static final
    Singleton D7s = new Singleton(99F, D7sSym, (byte) 7, Pitch.D, Sharp, 2489.02F);

    public static final
    Singleton E7f = new Singleton(99F, E7fSym, (byte) 7, Pitch.E, Flat, 2489.02F);

    public static final
    Singleton E7 = new Singleton(100F, E7Sym, (byte) 7, Pitch.E, 2637.02F);

    public static final
    Singleton F7 = new Singleton(101F, F7Sym, (byte) 7, Pitch.F, 2793.83F);

    public static final
    Singleton F7s = new Singleton(102F, F7sSym, (byte) 7, Pitch.F, Sharp, 2959.96F);

    public static final
    Singleton G7f = new Singleton(102F, G7fSym, (byte) 7, Pitch.G, Flat, 2959.96F);

    public static final
    Singleton G7 = new Singleton(103F, G7Sym, (byte) 7, Pitch.G, 3135.96F);

    public static final
    Singleton G7s = new Singleton(104F, G7sSym, (byte) 7, Pitch.G, Sharp, 3322.44F);

    public static final
    Singleton A7f = new Singleton(104F, A7fSym, (byte) 7, Pitch.A, Flat, 3322.44F);

    public static final
    Singleton A7 = new Singleton(105F, A7Sym, (byte) 7, Pitch.A, 3520.00F);

    public static final
    Singleton A7s = new Singleton(106F, A7sSym, (byte) 7, Pitch.A, Sharp, 3729.31F);

    public static final
    Singleton B7f = new Singleton(106F, B7fSym, (byte) 7, Pitch.B, Flat, 3729.31F);

    public static final
    Singleton B7 = new Singleton(107F, B7Sym, (byte) 7, Pitch.B, 3951.07F);

    public static final
    Singleton C8 = new Singleton(108F, C8Sym, (byte) 8, Pitch.C, 4186.01F);

    public static final
    Singleton C8s = new Singleton(109F, C8sSym, (byte) 8, Pitch.C, Sharp, 4434.92F);

    public static final
    Singleton D8f = new Singleton(109F, D8fSym, (byte) 8, Pitch.D, Flat, 4434.92F);

    public static final
    Singleton D8 = new Singleton(110F, D8Sym, (byte) 8, Pitch.D, 4698.64F);

    public static final
    Singleton D8s = new Singleton(111F, D8sSym, (byte) 8, Pitch.D, Sharp, 4978.03F);

    public static final
    Singleton E8f = new Singleton(111F, E8fSym, (byte) 8, Pitch.E, Flat, 4978.03F);

    public static final
    Singleton E8 = new Singleton(112F, E8Sym, (byte) 8, Pitch.E, 5274.04F);

    public static final
    Singleton F8 = new Singleton(113F, F8Sym, (byte) 8, Pitch.F, 5587.65F);

    public static final
    Singleton F8s = new Singleton(114F, F8sSym, (byte) 8, Pitch.F, Sharp, 5919.91F);

    public static final
    Singleton G8f = new Singleton(114F, G8fSym, (byte) 8, Pitch.G, Flat, 5919.91F);

    public static final
    Singleton G8 = new Singleton(115F, G8Sym, (byte) 8, Pitch.G, 6271.93F);

    public static final
    Singleton G8s = new Singleton(116F, G8sSym, (byte) 8, Pitch.G, Sharp, 6644.88F);

    public static final
    Singleton A8f = new Singleton(116F, A8fSym, (byte) 8, Pitch.A, Flat, 6644.88F);

    public static final
    Singleton A8 = new Singleton(117F, A8Sym, (byte) 8, Pitch.A, 7040.00F);

    public static final
    Singleton A8s = new Singleton(118F, A8sSym, (byte) 8, Pitch.A, Sharp, 7458.62F);

    public static final
    Singleton B8f = new Singleton(118F, B8fSym, (byte) 8, Pitch.B, Flat, 7458.62F);

    public static final
    Singleton B8 = new Singleton(119F, B8Sym, (byte) 8, Pitch.B, 7902.13F);

    public static final
    Singleton C9 = new Singleton(120F, C9Sym, (byte) 9, Pitch.C, 8372.02F);

    public static final
    Singleton C9s = new Singleton(121F, C9sSym, (byte) 9, Pitch.C, Sharp, 8869.84F);

    public static final
    Singleton D9f = new Singleton(121F, D9fSym, (byte) 9, Pitch.D, Flat, 8869.84F);

    public static final
    Singleton D9 = new Singleton(122F, D9Sym, (byte) 9, Pitch.D, 9397.27F);

    public static final
    Singleton D9s = new Singleton(123F, D9sSym, (byte) 9, Pitch.D, Sharp, 9956.06F);

    public static final
    Singleton E9f = new Singleton(123F, E9fSym, (byte) 9, Pitch.E, Flat, 9956.06F);

    public static final
    Singleton E9 = new Singleton(124F, E9Sym, (byte) 9, Pitch.E, 10548.08F);

    public static final
    Singleton F9 = new Singleton(125F, F9Sym, (byte) 9, Pitch.F, 11175.30F);

    public static final
    Singleton F9s = new Singleton(126F, F9sSym, (byte) 9, Pitch.F, Sharp, 11839.82F);

    public static final
    Singleton G9f = new Singleton(126F, G9fSym, (byte) 9, Pitch.G, Flat, 11839.82F);

    public static final
    Singleton G9 = new Singleton(127F, G9Sym, (byte) 9, Pitch.G, 12543.85F);

    public static final
    Singleton G9s = new Singleton(128F, G9sSym, (byte) 9, Pitch.G, Sharp, 13289.75F);

    public static final
    Singleton A9f = new Singleton(128F, A9fSym, (byte) 9, Pitch.A, Flat, 13289.75F);

    public static final
    Singleton A9 = new Singleton(129F, A9Sym, (byte) 9, Pitch.A, 14080F);

    public static final
    Singleton A9s = new Singleton(130F, A9sSym, (byte) 9, Pitch.A, Sharp, 14917.24F);

    public static final
    Singleton B9f = new Singleton(130F, B9fSym, (byte) 9, Pitch.B, Flat, 14917.24F);

    public static final
    Singleton B9 = new Singleton(131F, B9Sym, (byte) 9, Pitch.B, 15804.27F);

    public static final
    Singleton C10 = new Singleton(132F, C10Sym, (byte) 10, Pitch.C, 16744.04F);

    public static final
    Singleton C10s = new Singleton(133F, C10sSym, (byte) 10, Pitch.C, Sharp, 17739.69F);

    public static final
    Singleton D10f = new Singleton(133F, D10fSym, (byte) 10, Pitch.D, Flat, 17739.69F);

    public static final
    Singleton D10 = new Singleton(134F, D10Sym, (byte) 10, Pitch.D, 18794.55F);

    public static final
    Singleton D10s = new Singleton(135F, D10sSym, (byte) 10, Pitch.D, Sharp, 19912.13F);

    public static final
    Singleton E10f = new Singleton(135F, E10fSym, (byte) 10, Pitch.E, Flat, 19912.13F);

    public static final
    Singleton E10 = new Singleton(136F, E10Sym, (byte) 10, Pitch.E, 21096.16F);

    public static final
    Singleton F10 = new Singleton(137F, F10Sym, (byte) 10, Pitch.F, 22350.61F);

    public static final
    Singleton F10s = new Singleton(138F, F10sSym, (byte) 10, Pitch.F, Sharp, 23679.64F);

    public static final
    Singleton G10f = new Singleton(138F, G10fSym, (byte) 10, Pitch.G, Flat, 23679.64F);

    public static final
    Singleton G10 = new Singleton(139F, G10Sym, (byte) 10, Pitch.G, 25087.71F);

    public static final
    Singleton G10s = new Singleton(140F, G10sSym, (byte) 10, Pitch.G, Sharp, 26579.5F);

    public static final
    Singleton A10f = new Singleton(140F, A10fSym, (byte) 10, Pitch.A, Flat, 26579.5F);

    public static final
    Singleton A10 = new Singleton(141F, A10Sym, (byte) 10, Pitch.A, 28160F);

    public static final
    Singleton A10s = new Singleton(142F, A10sSym, (byte) 10, Pitch.A, Sharp, 29834.48F);

    public static final
    Singleton B10f = new Singleton(142F, B10fSym, (byte) 10, Pitch.B, Flat, 29834.48F);

    public static final
    Singleton B10 = new Singleton(143F, B10Sym, (byte) 10, Pitch.B, 31608.53F);

    /** The base 10 logarithm of 2. */
    private static final
    float Log2Base10 = (float) Math.log10(2);

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

    /** The note adjustment.<p>The number of cents added to, or subtracted from, the note to slightly alter its pitch. */
    protected
    short adjustment;

    /**
     * Creates a note with the specified symbol, octave, pitch, accidental, and adjustment (in cents), and adjusts the note.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave and {@link Number#shortValue()} on the adjustment unless it is null, where it will be set to zero.
     * Null octaves are allowed.
     *
     * @param symbol the symbol.
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     * @param adjustment the adjustment.
     *
     * @see #adjust()
     */
    public
    Note(
        final String symbol,
        final Number octave,
        final Pitch pitch,
        final Accidental accidental,
        final Number adjustment
        ) {
        super();
        this.symbol = symbol;
        if (octave != null)
            this.octave = octave.byteValue();
        this.pitch = pitch;
        this.accidental = accidental == null
                          ? Natural
                          : accidental;

        this.adjustment = adjustment == null
                          ? 0
                          : adjustment.shortValue();

        adjust();
    }

    /**
     * Creates a note with the specified symbol, octave, pitch, accidental, and adjustment (in cents), and adjusts the note.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave and {@link Number#shortValue()} on the adjustment unless it is null, where it will be set to zero.
     * Null octaves are allowed.
     *
     * @param symbol the symbol.
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     * @param adjustment the adjustment.
     *
     * @see #adjust()
     */
    public
    Note(
        final String symbol,
        final Octave octave,
        final Pitch pitch,
        final Accidental accidental,
        final Number adjustment
        ) {
        this(symbol, (Byte) Octave.orderOf(octave), pitch, accidental, adjustment);
    }

    /**
     * Creates a note with the specified symbol, octave, pitch, and accidental, and adjusts the note.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave.
     * Null octaves are allowed.
     *
     * @param symbol the symbol.
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     *
     * @see #adjust()
     */
    public
    Note(
        final String symbol,
        final Number octave,
        final Pitch pitch,
        final Accidental accidental
        ) {
        this(symbol, octave, pitch, accidental, 0);
    }

    /**
     * Creates a note with the specified symbol, octave, pitch, and accidental, and adjusts the note.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave.
     * Null octaves are allowed.
     *
     * @param symbol the symbol.
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     *
     * @see #adjust()
     */
    public
    Note(
        final String symbol,
        final Octave octave,
        final Pitch pitch,
        final Accidental accidental
        ) {
        this(symbol, octave, pitch, accidental, 0);
    }

    /**
     * Creates a natural note with the specified symbol, octave, pitch, and adjustment (in cents), and adjusts the note.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave and {@link Number#shortValue()} on the adjustment.
     * Null octaves are allowed.
     *
     * @param symbol the symbol.
     * @param octave the octave.
     * @param pitch the pitch.
     * @param adjustment the adjustment.
     *
     * @see #adjust()
     */
    public
    Note(
        final String symbol,
        final Number octave,
        final Pitch pitch,
        final Number adjustment
        ) {
        this(symbol, octave, pitch, (Accidental) null, adjustment);
    }

    /**
     * Creates a natural note with the specified symbol, octave, pitch, and adjustment (in cents), and adjusts the note.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave and {@link Number#shortValue()} on the adjustment.
     * Null octaves are allowed.
     *
     * @param symbol the symbol.
     * @param octave the octave.
     * @param pitch the pitch.
     * @param adjustment the adjustment.
     *
     * @see #adjust()
     */
    public
    Note(
        final String symbol,
        final Octave octave,
        final Pitch pitch,
        final Number adjustment
        ) {
        this(symbol, (Byte) Octave.orderOf(octave), pitch, adjustment);
    }

    /**
     * Creates a natural note with the specified symbol, octave, and pitch.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave.
     * Null octaves are allowed.
     *
     * @param symbol the symbol.
     * @param octave the octave.
     * @param pitch the pitch.
     */
    public
    Note(
        final String symbol,
        final Number octave,
        final Pitch pitch
        ) {
        super();
        this.symbol = symbol;
        if (octave != null)
            this.octave = octave.byteValue();
        this.pitch = pitch;
        accidental = Natural;
        adjustment = 0;
    }

    /**
     * Creates a natural note with the specified symbol, octave, and pitch.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave.
     * Null octaves are allowed.
     *
     * @param symbol the symbol.
     * @param octave the octave.
     * @param pitch the pitch.
     */
    public
    Note(
        final String symbol,
        final Octave octave,
        final Pitch pitch
        ) {
        this(symbol, (Byte) Octave.orderOf(octave), pitch);
    }

    /**
     * Creates a note, as a pitch type, with null octave and the specified symbol, pitch, accidental, and adjustment (in cents), and adjusts the note.
     * <p>
     * This implementation calls {@link Number#shortValue()} on the adjustment unless it is null, where it will be set to zero.
     * Null octaves are allowed.
     *
     * @param symbol the symbol.
     * @param pitch the pitch.
     * @param accidental the accidental.
     * @param adjustment the adjustment.
     *
     * @see #adjust()
     */
    public
    Note(
        final String symbol,
        final Pitch pitch,
        final Accidental accidental,
        final Number adjustment
        ) {
        this(symbol, (Number) null, pitch, accidental, adjustment);
    }

    /**
     * Creates a note, as a pitch type, with null octave and the specified symbol, pitch, and accidental, and adjusts the note, and adjusts the note.
     *
     * @param symbol the symbol.
     * @param pitch the pitch.
     * @param accidental the accidental.
     *
     * @see #adjust()
     */
    public
    Note(
        final String symbol,
        final Pitch pitch,
        final Accidental accidental
        ) {
        this(symbol, (Byte) null, pitch, accidental);
    }

    /**
     * Creates a natural note, as a pitch type, with null octave and the specified symbol, pitch, and adjustment (in cents), and adjusts the note.
     * <p>
     * This implementation calls {@link Number#shortValue()} on the adjustment unless it is null, where it will be set to zero.
     * Null octaves are allowed.
     *
     * @param symbol the symbol.
     * @param pitch the pitch.
     * @param adjustment the adjustment.
     *
     * @see #adjust()
     */
    public
    Note(
        final String symbol,
        final Pitch pitch,
        final Number adjustment
        ) {
        this(symbol, (Number) null, pitch, Natural, adjustment);
    }

    /**
     * Creates a natural note, as a pitch type, with null octave and the specified symbol and pitch.
     *
     * @param symbol the symbol.
     * @param pitch the pitch.
     */
    public
    Note(
        final String symbol,
        final Pitch pitch
        ) {
        this(symbol, (Byte) null, pitch);
    }

    /**
     * Creates a note with the specified octave, pitch, accidental, adjustment (in cents), and null symbol, and adjusts the note.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave and {@link Number#shortValue()} on the adjustment unless it is null, where it will be set to zero.
     * Null octaves are allowed.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     * @param adjustment the adjustment.
     *
     * @see #adjust()
     */
    public
    Note(
        final Number octave,
        final Pitch pitch,
        final Accidental accidental,
        final Number adjustment
        ) {
        this(null, octave, pitch, accidental, adjustment);
    }

    /**
     * Creates a note with the specified octave, pitch, accidental, adjustment (in cents), and null symbol, and adjusts the note.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave and {@link Number#shortValue()} on the adjustment unless it is null, where it will be set to zero.
     * Null octaves are allowed.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     * @param adjustment the adjustment.
     *
     * @see #adjust()
     */
    public
    Note(
        final Octave octave,
        final Pitch pitch,
        final Accidental accidental,
        final Number adjustment
        ) {
        this(null, octave, pitch, accidental, adjustment);
    }

    /**
     * Creates a note with the specified octave, pitch, accidental, and null symbol, and adjusts the note.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave.
     * Null octaves are allowed.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     *
     * @see #adjust()
     */
    public
    Note(
        final Number octave,
        final Pitch pitch,
        final Accidental accidental
        ) {
        this(null, octave, pitch, accidental);
    }

    /**
     * Creates a note with the specified octave, pitch, accidental, and null symbol, and adjusts the note.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave.
     * Null octaves are allowed.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     *
     * @see #adjust()
     */
    public
    Note(
        final Octave octave,
        final Pitch pitch,
        final Accidental accidental
        ) {
        this(null, octave, pitch, accidental);
    }

    /**
     * Creates a note, as pitch type, with null octave and the specified pitch, accidental, adjustment (in cents), and null symbol, and adjusts the note.
     * <p>
     * This implementation calls {@link Number#shortValue()} on the adjustment unless it is null, where it will be set to zero.
     * Null octaves are allowed.
     *
     * @param pitch the pitch.
     * @param accidental the accidental.
     * @param adjustment the adjustment.
     *
     * @see #adjust()
     */
    public
    Note(
        final Pitch pitch,
        final Accidental accidental,
        final Number adjustment
        ) {
        this(null, (Number) null, pitch, accidental, adjustment);
    }

    /**
     * Creates a natural note, as pitch type, with null octave and the specified pitch and adjustment (in cents), and null symbol, and adjusts the note.
     * <p>
     * This implementation calls {@link Number#shortValue()} on the adjustment unless it is null, where it will be set to zero.
     * Null octaves are allowed.
     *
     * @param pitch the pitch.
     * @param accidental the accidental.
     * @param adjustment the adjustment.
     *
     * @see #adjust()
     */
    public
    Note(
        final Pitch pitch,
        final Number adjustment
        ) {
        this(pitch, Natural, adjustment);
    }

    /**
     * Creates a natural note with the specified octave, pitch, and adjustment (in cents), and null symbol, and adjusts the note.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave and {@link Number#shortValue()} on the adjustment.
     * Null octaves are allowed.
     *
     * @param symbol the symbol.
     * @param octave the octave.
     * @param pitch the pitch.
     * @param adjustment the adjustment.
     *
     * @see #adjust()
     */
    public
    Note(
        final Number octave,
        final Pitch pitch,
        final Number adjustment
        ) {
        this(null, octave, pitch, Natural, adjustment);
    }

    /**
     * Creates a natural note with the specified octave, pitch, and adjustment (in cents), and null symbol, and adjusts the note.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave and {@link Number#shortValue()} on the adjustment.
     * Null octaves are allowed.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param adjustment the adjustment.
     *
     * @see #adjust()
     */
    public
    Note(
        final Octave octave,
        final Pitch pitch,
        final Number adjustment
        ) {
        this(null, octave, pitch, Natural, adjustment);
    }

    /**
     * Creates a natural note with the specified octave and pitch, and null symbol.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave.
     * Null octaves are allowed.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     */
    public
    Note(
        final Number octave,
        final Pitch pitch
        ) {
        this(null, octave, pitch);
    }

    /**
     * Creates a natural note with the specified octave and pitch, and null symbol.
     * <p>
     * This implementation calls {@link Number#byteValue()} on the octave.
     * Null octaves are allowed.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     */
    public
    Note(
        final Octave octave,
        final Pitch pitch
        ) {
        this(null, octave, pitch);
    }

    /**
     * Creates a note, as pitch type, with null octave and the specified pitch and accidental, and null symbol, and adjusts the note.
     *
     * @param pitch the pitch.
     * @param accidental the accidental.
     *
     * @see #adjust()
     */
    public
    Note(
        final Pitch pitch,
        final Accidental accidental
        ) {
        this((Byte) null, pitch, accidental);
    }

    /**
     * Creates a natural note, as a pitch type, with null octave and the specified pitch, and null symbol.
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
     * Creates a note with the specified symbol and from the specified string value, and adjusts the note.
     * <p>
     * The string value must be a valid note symbol, such as "A", "A4", "Eb", or "Eb-1".
     *
     * @param symbol the symbol.
     * @param value the value.
     *
     * @throws NullPointerException if the value is null.
     * @throws IllegalArgumentException if the value is not a valid note symbol.
     *
     * @see #adjust()
     */
    public
    Note(
        final String symbol,
        final String value
        ) {
        super();
        this.symbol = symbol;
        set(this, value);
    }

    /**
     * Creates a note with the specified symbol, number, and sharp preference flag, and adjusts the note.
     * <p>
     * If the sharp flag is true and the note is not a natural note, the sharp note will be created; otherwise the flat note is created.
     * <p>
     * This constructor performs an unsafe cast from {@code float} to {@code short}.
     *
     * @param the symbol.
     * @param number the number.
     * @param sharp the sharp preference flag.
     *
     * @throws IllegalArgumentException if the number is less than zero.
     */
    public
    Note(
        final String symbol,
        float number,
        final boolean sharp
        ) {
        super();
        if (number < 0)
            throw new IllegalArgumentException();

        final byte octave = (byte) ((short) number / 12 - 1);
        number -= (octave + 1) * 12;

        final Pitch pitch = Pitch.withOrder((byte) number);
        final byte diff = (byte) (number - pitch.order);
        final byte adjustment = (byte) ((number - diff) * 100);
        if (diff == 1)
            if (sharp) {
                this.octave = octave;
                this.pitch = pitch;
                accidental = Sharp;
                this.adjustment = adjustment;
            }
            else {
                this.octave = octave;
                this.pitch = Pitch.withOrder((byte) (number + 1));
                accidental = Flat;
                this.adjustment = adjustment;
            }
        else {
            this.octave = octave;
            this.pitch = pitch;
            this.adjustment = adjustment;
        }

        this.symbol = symbol;
    }

    /**
     * Creates a note with the specified symbol and number, and with sharp preference flag set to true, and adjusts the note.
     * <p>
     * This constructor performs an unsafe cast from {@code float} to {@code short}.
     *
     * @param the symbol.
     * @param number the number.
     *
     * @throws IllegalArgumentException if the number is less than zero.
     */
    public
    Note(
        final String symbol,
        float number
        ) {
        this(symbol, number, true);
    }

    /**
     * Creates a note with the specified number and sharp preference flag, and null symbol, and adjusts the note.
     * <p>
     * If the sharp flag is true and the note is not a natural note, the sharp note will be created; otherwise the flat note is created.
     * <p>
     * This constructor performs an unsafe cast from {@code float} to {@code short}.
     *
     * @param number the number.
     * @param sharp the sharp preference flag.
     *
     * @throws IllegalArgumentException if the number is less than zero.
     */
    public
    Note(
        float number,
        final boolean sharp
        ) {
        this(null, number, sharp);
    }

    /**
     * Creates a note with the specified number, and with sharp preference flag set to true, and null symbol, and adjusts the note.
     * <p>
     * This constructor performs an unsafe cast from {@code float} to {@code short}.
     *
     * @param number the number.
     *
     * @throws IllegalArgumentException if the number is less than zero.
     */
    public
    Note(
        float number
        ) {
        this(number, true);
    }

    /**
     * Creates a note from the specified string value and with a symbol equal to the same value, and adjusts the note.
     * <p>
     * The string value must be a valid note symbol, such as "A", "A4", "Eb", or "Eb-1".
     *
     * @param value the value.
     *
     * @throws NullPointerException if the value is null.
     * @throws IllegalArgumentException if the value is not a valid note symbol.
     *
     * @see #adjust()
     */
    public
    Note(
        final String value
        ) {
        this(value, value);
    }

    /**
     * Creates an empty note.
     */
    private
    Note() {
        super();
    }

    /**
     * Returns the number for a note with the specified octave, pitch, and accidental.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     *
     * @return the note number.
     *
     * @throws NullPointerException if the pitch or accidental is null.
     */
    public static
    short number(
        final byte octave,
        final Pitch pitch,
        final Accidental accidental
        ) {
        return number(octave, (short) (pitch.order + accidental.getOrder()));
    }

    /**
     * Returns the number for a natural note with the specified octave and pitch.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     *
     * @return the note number.
     *
     * @throws NullPointerException if the pitch is null.
     */
    public static
    short number(
        final byte octave,
        final Pitch pitch
        ) {
        return number(octave, pitch, Natural);
    }

    /**
     * Returns the number for a note with the specified octave and pitch-and-accidental order.
     * <p>
     * The pitch-and-accidental order is the sum of the pitch order and the accidental order.
     * The pitch order for C is 0, D is 2, E is 4, F is 5, G is 7, A is 9, and B is 11.
     * The accidental order for sharp is 1, flat is -1, and natural is 0.
     *
     * @param octave the octave.
     * @param order the pitch-and-accidental order.
     *
     * @return the note number.
     */
    public static
    short number(
        final byte octave,
        final short order
        ) {
        return (short) ((octave + 1) * 12 + (order + (1 - order / 12) * 12) % 12);
    }

    /**
     * Returns the number for a note with the specified frequency.
     * <p>
     * If the frequency is negative, NaN is returned.
     *
     * @param freq the frequency.
     *
     * @return the note number.
     */
    public static
    float number(
        final float freq
        ) {
        return (float) (69 + 12 * Math.log(freq / 440) / Log2Base10);
    }

    /**
     * Rounds the number away from zero and returns it.
     *
     * @param number the number.
     *
     * @return the rounded number.
     */
    private static
    int round(
        final float number
        ) {
        return (int) (Math.signum(number) * Math.round(Math.abs(number) / 100));
    }

    /**
     * Sets instance variables from the specified string value.
     *
     * @param instance the instance.
     * @param value the value.
     *
     * @throws NullPointerException if the value is null.
     * @throws IllegalArgumentException if the value is not a valid note symbol.
     */
    protected static
    void set(
        final Note instance,
        String value
        ) {
        value = value.trim();
        instance.pitch = Pitch.valueOf(value.charAt(0));

        if (value.length() == 1) {
            instance.accidental = Natural;
            return;
        }

        switch (value.charAt(1)) {
        case 'b':
            instance.accidental = Flat;
            break;

        case '#':
            instance.accidental = Sharp;
            break;

        case '-':
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            instance.accidental = Natural;
            instance.octave = Byte.parseByte(value.substring(1));
            return;

        default:
            throw new IllegalArgumentException();
        }

        if (value.length() > 2)
            instance.octave = Byte.parseByte(value.substring(2));
    }

    /**
     * Returns the singleton note with the specified octave, pitch, and accidental, or null if none is found.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     *
     * @return the singleton note.
     *
     * @throws NullPointerException if the pitch or accidental is null.
     */
    public static
    Note tune(
        final Number octave,
        final Pitch pitch,
        final Accidental accidental
        ) {
        if (octave == null)
            return null;

        return new BinaryLocator<Note>(null, Singleton.Order, new Comparator<Note>() {
                                                                  @Override
                                                                  public int compare(Note n, final Note singleton) {
                                                                      return ((Singleton) singleton).diff(octave.shortValue(), pitch, accidental);
                                                                  }
                                                              }).result(null);
    }

    /**
     * Returns the singleton note with the specified octave, pitch, and accidental, or null if none is found.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     *
     * @return the singleton note.
     *
     * @throws NullPointerException if the pitch or accidental is null.
     */
    public static
    Note tune(
        final Octave octave,
        final Pitch pitch,
        final Accidental accidental
        ) {
        if (octave == null)
            return null;

        return new BinaryLocator<Note>(null, Singleton.Order, new Comparator<Note>() {
                                                                  @Override
                                                                  public int compare(Note n, final Note singleton) {
                                                                      return ((Singleton) singleton).diff(octave.getOrder(), pitch, accidental);
                                                                  }
                                                              }).result(null);
    }

    /**
     * Returns the singleton natural note with the specified octave and pitch, or null if none is found.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     *
     * @return the singleton note.
     *
     * @throws NullPointerException if the pitch is null.
     */
    public static
    Note tune(
        final Number octave,
        final Pitch pitch
        ) {
        return tune(octave, pitch, Natural);
    }

    /**
     * Returns the singleton natural note with the specified octave and pitch, or null if none is found.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     *
     * @return the singleton note.
     *
     * @throws NullPointerException if the pitch is null.
     */
    public static
    Note tune(
        final Octave octave,
        final Pitch pitch
        ) {
        return tune(octave, pitch, Natural);
    }

    /**
     * Returns the singleton note in the 4th octave, and with the specified pitch and accidental, or null if none is found.
     *
     * @param pitch the pitch.
     * @param accidental the accidental.
     *
     * @return the singleton note.
     *
     * @throws NullPointerException if the pitch is null.
     */
    public static
    Note tune(
        final Pitch pitch,
        final Accidental accidental
        ) {
        return tune(4, pitch, accidental);
    }

    /**
     * Returns the singleton natural note in the 4th octave and with the specified pitch, or null if none is found.
     *
     * @param pitch the pitch.
     * @param accidental the accidental.
     *
     * @return the singleton note.
     *
     * @throws NullPointerException if the pitch is null.
     */
    public static
    Note tune(
        final Pitch pitch
        ) {
        return tune(4, pitch, Natural);
    }

    /**
     * Returns the equivalent singleton note of the specified note, or null if none is found.
     *
     * @param note the note.
     *
     * @return the singleton note.
     *
     * @throws NullPointerException if the note pitch or accidental is null.
     */
    public static
    Note tune(
        final Note note
        ) {
        return note == null
               ? null
               : note instanceof Singleton
                 ? note
                 : tune(note.octave, note.pitch, note.accidental);
    }

    /**
     * Returns the singleton note with the specified number and sharp preference flag, or null if none is found.
     * <p>
     * The number must be whole.
     * <p>
     * When if flag is set to true and the singleton has an accidental, the sharp version is returned; otherwise the flat version is returned.
     *
     * @param number the number.
     * @param sharp the sharp preference flag.
     *
     * @return the singleton note.
     *
     * @throws NullPointerException if the number is null.
     */
    public static
    Note tune(
        final Number number,
        final boolean sharp
        ) {
        final Integer i = new BinaryLocator<Note>(null, Singleton.Order, new Comparator<Note>() {
                                                                             @Override
                                                                             public int compare(Note n, final Note singleton) {
                                                                                 return (int) (((Singleton) singleton).number - number.floatValue());
                                                                             }
                                                                         }).position(null);

        if (i == null)
            return null;

        final Note tune = Singleton.Order[i];
        return tune.accidental == Natural
               ? tune
               : sharp
                 ? tune.accidental == Flat
                   ? Singleton.Order[i - 1]
                   : tune
                 : tune.accidental == Sharp
                   ? Singleton.Order[i + 1]
                   : tune;
    }

    /**
     * Returns the singleton note with the specified number, or null if none is found.
     * <p>
     * This implementation prefers sharp notes when there is an accidental.
     *
     * @param number the number.
     *
     * @return the singleton note.
     *
     * @throws NullPointerException if the number is null.
     */
    public static
    Note tune(
        final Number number
        ) {
        return tune(number, true);
    }

    /**
     * Returns the singleton note with the specified symbol, or null if none is found.
     *
     * @param symbol the symbol.
     *
     * @return the singleton note.
     */
    public static
    Note tune(
        final String symbol
        ) {
        return new BinaryLocator<Note>(null, Singleton.Order, new Comparator<Note>() {
                                                                  @Override
                                                                  public int compare(Note n, final Note singleton) {
                                                                      return ((Singleton) singleton).symbol.compareTo(symbol);
                                                                  }
                                                              }).result(null);
    }

    /**
     * Returns the singleton reference note, A4.
     *
     * @return the singleton reference note, A4.
     */
    public static
    Note tune() {
        return A4;
    }

    /**
     * Creates and returns a note for the specified frequency, and sharp preference flag; or returns null if the frequency is non-positive.
     *
     * @param freq the frequency.
     * @param sharp the sharp preference flag.
     *
     * @return the note.
     */
    public static
    Note withFrequency(
        final float freq,
        final boolean sharp
        ) {
        if (freq <= 0)
            return null;

        final float number = (float) (69 + 12 * Math.log(freq / 440) / Log2Base10);
        final short round = (short) round(number);
        final Note note = new Note(round, sharp);
        note.setAdjustment((short) ((number - round) * 100));

        return note;
    }

    /**
     * Creates and returns a note for the specified frequency, or null if the frequency is less than zero.
     *
     * @param freq the frequency.
     *
     * @return the note.
     */
    public static
    Note withFrequency(
        final float freq
        ) {
        return withFrequency(freq, true);
    }

    /**
     * Adds the specified cents to this interval.
     *
     * @param cents the cents.
     *
     * @throws NullPointerException if the cents is null.
     * @throws UnsupportedOperationException if this interval is a singleton and the cents value is not equal to zero.
     *
     * @see #add(Number)
     */
    public
    void add(
        final Cents cents
        ) {
        add(cents.getOrder());
    }

    /**
     * Adds the specified semitones to this interval.
     *
     * @param semitones the semitones.
     *
     * @throws NullPointerException if the semitones is null.
     * @throws UnsupportedOperationException if this interval is a singleton and the semitones value is not equal to zero.
     *
     * @see #add(Number)
     */
    public
    void add(
        final Semitones semitones
        ) {
        add(semitones.getCents());
    }

    /**
     * Returns the singleton equivalent to this note using the specified comparator, or itself if none is found.
     * <p>
     * This implementation calls {@link Comparator#compare(Object, Object)} with this note as the first argument and the singletons as second arguments of that method.
     *
     * @param comparator the comparator.
     *
     * @return the equivalent singleton or this note.
     */
    public
    Note distinct(
        final Comparator<Note> comparator
        ) {
        return (Standard) new Lambda.BinaryLocator<Note>(this, Singleton.Order, comparator).result(this);
    }

    /**
     * Returns the singleton equivalent to this note using the specified preferred sharp flag, or itself if none is found.
     *
     * @param sharp the preferred sharp flag.
     *
     * @return the equivalent singleton or this note.
     *
     * @see #distinct(Comparator)
     */
    public
    Note distinct(
        final boolean sharp
        ) {
        return distinct(new Comparator<Note>() {
            @Override
            public int compare(final Note n1, final Note n2) {
                return n1.compareTo(n2) == 0 && n1.accidental == Natural ||
                       (sharp
                        ? n1.accidental == Sharp && n2.accidental == Sharp
                        : n1.accidental == Flat && n2.accidental == Flat)
                       ? 0
                       : 1;
            }
        });
    }

    /**
     * Returns the singleton equivalent to this note or itself if none is found.
     *
     * @return the equivalent singleton or this note.
     */
    public
    Note distinct() {
        return (Standard) new Lambda.BinaryComparableLocator<Note>(this, Singleton.Order).result(this);
    }

    /**
     * Returns true if the specified note has the same octave, pitch, and accidental, as this note ignoring adjustments; otherwise return false.
     *
     * @param note the note.
     *
     * @return true if notes are equal ignoring adjustments, and false otherwise.
     *
     * @throws NullPointerException if the note is null.
     */
    public
    boolean equalsIgnoreAdjustment(
        final Note note
        ) {
        return Lambda.areNullOrEqual(octave, note.octave) &&
               pitch == note.pitch &&
               accidental == note.accidental;
    }

    /**
     * Returns true if the specified note has the same pitch, accidental, and adjustment as this note; otherwise return false.
     *
     * @param note the note.
     *
     * @return true if notes are equal ignoring octaves, and false otherwise.
     *
     * @throws NullPointerException if the note is null.
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
     * Returns true if the specified note has the same number as this note; otherwise returns false.
     * <p>
     * This implementation calls {@link #compareTo(Note)} internally.
     *
     * @param note the note.
     *
     * @return true if the notes are equal ignoring pitch variations, and false otherwise.
     *
     * @see #compareTo(Object)
     */
    public
    boolean equalsIgnorePitch(
        final Note note
        ) {
        return compareTo(note) == 0;
    }

    /**
     * Returns true if the specified note has the same relative note number in its own octave, and adjustment as this note; otherwise returns false.
     *
     * @param note the note.
     *
     * @return true if the notes are equal ignoring pitch and octave variations, and false otherwise.
     *
     * @throws NullPointerException if the note or its pitch or accidental is null.
     */
    public
    boolean equalsIgnorePitchAndOctave(
        final Note note
        ) {
        return getDistance(pitch, accidental, adjustment) == getDistance(note.pitch, note.accidental, note.adjustment);
    }

    /**
     * Returns a string representation of the note adjustment amount.
     *
     * @return the adjustment amount.
     */
    protected
    String getAdjustmentString() {
        return (adjustment > 0 ? "+" : "-") + adjustment;
    }

    /**
     * Returns a descriptive string representation of the note adjustment.
     *
     * @return the adjustment suffix.
     */
    protected
    String getAdjustmentSuffix() {
        return (adjustment == 0 ? "" : "[" + getAdjustmentString() + " cents]");
    }

    /**
     * Returns the distance from the specified note values in cents.
     * <p>
     * If the octave of this note or the specified octave is null, octaves will not be accounted for in the calculation.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param accidental the accidental.
     * @param adjustment the adjustment.
     *
     * @return the distance.
     *
     * @throws NullPointerException if the specified pitch or accidental is null.
     */
    public
    float getDistance(
        final Byte octave,
        final Pitch pitch,
        final Accidental accidental,
        final short adjustment
        ) {
        final float cents = (pitch.order - this.pitch.order) * 100 +
                            accidental.cents - this.accidental.cents +
                            adjustment - this.adjustment;

        return octave == null || this.octave == null
               ? cents
               : (octave - this.octave) * 1200 + cents;
    }

    /**
     * Returns the distance from the specified natural note values in cents.
     * <p>
     * If the octave of this note or the specified octave is null, octaves will not be accounted for in the calculation.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     * @param adjustment the adjustment.
     *
     * @return the distance.
     *
     * @throws NullPointerException if specified pitch is null.
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
     * Returns the distance from the specified natural note values in cents.
     * <p>
     * If the octave of this note or the specified octave is null, octaves will not be accounted for in the calculation.
     *
     * @param octave the octave.
     * @param pitch the pitch.
     *
     * @return the distance.
     *
     * @throws NullPointerException if the specified pitch is null.
     */
    public
    float getDistance(
        final Byte octave,
        final Pitch pitch
        ) {
        return getDistance(octave, pitch, Accidental.Natural, (short) 0);
    }

    /**
     * Returns the distance from the specified note values in cents.
     * <p>
     * The note octave is not accounted for in the calculation.
     *
     * @param pitch the pitch.
     * @param accidental the accidental.
     * @param adjustment the adjustment.
     *
     * @return the distance.
     *
     * @throws NullPointerException if specified pitch or accidental is null.
     */
    public
    float getDistance(
        final Pitch pitch,
        final Accidental accidental,
        final short adjustment
        ) {
        return getDistance(null, pitch, accidental, adjustment);
    }

    /**
     * Returns the distance from the specified natural note values in cents.
     * <p>
     * The note octave is not accounted for in the calculation.
     *
     * @param pitch the pitch.
     * @param adjustment the adjustment.
     *
     * @return the distance.
     *
     * @throws NullPointerException if specified pitch is null.
     */
    public
    float getDistance(
        final Pitch pitch,
        final short adjustment
        ) {
        return getDistance(null, pitch, adjustment);
    }

    /**
     * Returns the distance from the specified natural pitch in cents.
     * <p>
     * The note octave is not accounted for in the calculation.
     *
     * @param pitch the pitch.
     *
     * @return the distance.
     *
     * @throws NullPointerException if the specified pitch is null.
     */
    public
    float getDistance(
        final Pitch pitch
        ) {
        return getDistance(octave, pitch, Accidental.Natural, (short) 0);
    }

    /**
     * Returns the distance from the specified note in cents.
     * <p>
     * If the octave of this note or the specified note is null, octaves will not be accounted for in the calculation.
     *
     * @param note the note.
     *
     * @return the distance.
     *
     * @throws NullPointerException if the note or its pitch or accidental is null.
     */
    public
    float getDistance(
        final Note note
        ) {
        return getDistance(note.octave, note.pitch, note.accidental, note.adjustment);
    }

    /**
     * Returns the frequency of the note.
     *
     * @return the note frequency.
     *
     * @throws IllegalStateException if the octave is null.
     */
    public
    float getFrequency() {
        if (octave == null)
            throw new IllegalStateException();

        return (float) (Math.pow(2, (getNumber() - 69) / 12D)) * 440;
    }

    /**
     * Returns the note number.
     * <p>
     * If the octave is null, the relative order of the note is returned.
     * <p>
     * This implementation rounds the adjustment away from zero.
     *
     * @return the note number.
     */
    public
    float getNumber() {
        final float adj = (accidental.cents + adjustment) / 100F;
        final int round = round(adj);
        final int order = pitch.order + round;
        return octave == null ? 0 : (octave + 1) * 12 + (order + (1 - order / 12) * 12) % 12 + adj - round;
    }

    /**
     * Creates and returns a data point representing this note.
     * <p>
     * This implementation returns a data point with {@code x} mapped to the octave, {@code y} mapped to the pitch, and {@code z} mapped to the accidental.
     *
     * @return the note data point.
     */
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
                return Octave.valueOf(Note.this.getOctave());
            }

            @Override
            public Tabular<?> getPitch() {
                return Note.this.getPitch();
            }

            @Override
            public boolean is(final system.data.Type<? extends Note> type) {
                if (type instanceof Note) {
                    final Note note = (Note) type;
                    return ((getOctave() == null && note.getOctave() == null) || (getOctave() != null && getOctave().getOrder().equals(note.getOctave()))) &&
                           getPitch().equals(note.getPitch()) &&
                           getAccidental().equals(note.getAccidental());
                }

                return false;
            }

            @Override
            public Byte x() {
                return octave;
            }

            @Override
            public Pitch y() {
                return pitch;
            }

            @Override
            public Accidental z() {
                return accidental;
            }
        };
    }

    /**
     * Returns a string representation of the note excluding the octave.
     *
     * @return the note string without the octave.
     */
    public
    String toStringIgnoreOctave() {
        return pitch.toString() + accidental.toString() + getAdjustmentSuffix();
    }

    /**
     * Adds the specified number of cents to the note and adjusts the note.
     * <p>
     * This implementation performs unsafe casts from {@code float} and {@code int} to {@code short}.
     *
     * @param n the number of cents.
     *
     * @throws NullPointerException if the number is null.
     *
     * @see #adjust()
     */
    @Override
    public void add(final Number n) {
        short cents = n.shortValue();

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
            final short order = (short) getNumber();
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
     * Adjusts the note, adding the adjustment amount to the note if it is larger than a semitones, and converting uncommon pitch-accidental combinations to the common form.
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
                if (accidental == Sharp) {
                    pitch = Pitch.C;
                    accidental = Natural;
                }
                break;

            case C:
                if (accidental == Flat) {
                    pitch = Pitch.B;
                    accidental = Natural;
                }
                break;

            case E:
                if (accidental == Sharp) {
                    pitch = Pitch.F;
                    accidental = Natural;
                }
                break;

            case F:
                if (accidental == Flat) {
                    pitch = Pitch.E;
                    accidental = Natural;
                }
            }
    }

    /**
     * Adjusts this note with the specified adjustments, in cents, and returns the adjusted note.
     *
     * @param adjustments the adjustments.
     *
     * @return the adjusted note.
     */
    @Override
    public Note adjusted(final Number... adjustments) {
        if (adjustments.length == 0)
            return this;

        // Adjust in increments of 1200 cents to avoid overflows
        for (Number adjustment : adjustments)
            if (adjustment != null) {
                int adj = adjustment.intValue();
                if (adj > 1200) {
                    final int octaves = adj / 1200;
                    if (octave != null)
                        octave = (byte) (octave.byteValue() + octaves);
                    adj -= octaves * 1200;
                }

                this.adjustment += adj;
                adjust();
            }

        return this;
    }

    /**
     * This implementation throws an {@code UnsupportedOperationException}.
     */
    @Override
    public Duration by(final Number n) {
        throw new UnsupportedOperationException(OperationImpossible);
    }

    /**
     * Creates and returns a copy of this note.
     *
     * @return a copy of note.
     */
    @Override
    public Note clone() {
        return new Note(symbol, octave, pitch, accidental, adjustment);
    }

    /**
     * Compares this note with the specified note and returns a negative integer if this note is less than the note, zero if they are equal, and a positive integer otherwise.
     * If the specified note is null, {@link Integer#MAX_VALUE} will be returned.
     *
     * @param note the note.
     *
     * @return a negative integer if this fraction is less than the fraction, zero if they are equal, and a positive integer otherwise; or {@link Integer#MAX_VALUE} if the note is null.
     *
     * @see #getDistance(Note)
     */
    @Override
    public int compareTo(final Note note) {
        return note == null
               ? Integer.MAX_VALUE
               : (int) getDistance(note);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation is return this note.
     */
    @Override
    public Note convert() {
        return this;
    }

    /**
     * This implementation throws an {@code UnsupportedOperationException}.
     */
    @Override
    public void divide(final Number n) {
        throw new UnsupportedOperationException(OperationImpossible);
    }

    /**
     * Returns the note number as a {@code double}.
     *
     * @return the numeric value represented by this note after conversion to type {@code double}.
     */
    @Override
    public double doubleValue() {
        return getOrder().doubleValue();
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
            return Lambda.areNullOrEqual(octave, note.octave) &&
                   pitch == note.pitch &&
                   accidental.equals(note.accidental) &&
                   adjustment == note.adjustment;
        }

        return obj instanceof Number && getNumber() == ((Number) obj).floatValue() ||
               (obj != null && obj.equals(this));
    }

    /**
     * Returns the note order.
     * <p>
     * The note order is equal to the note number.
     *
     * @return the note order.
     *
     * @see #getNumber()
     */
    @Override
    public Float getOrder() {
        return getNumber();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns the {@code Float} class.
     *
     * @return the {@code Float} class.
     */
    @Override
    public Class<Float> getOrderClass() {
        return Float.class;
    }

    /**
     * Returns the hash code.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(octave, pitch.order, accidental.cents, adjustment);
    }

    /**
     * Returns the note number as a {@code int}.
     *
     * @return the numeric value represented by this note after conversion to type {@code int}.
     */
    @Override
    public int intValue() {
        return getOrder().intValue();
    }

    /**
     * Inverts the note by rotating its accidental, and returns this note.
     *
     * @return the inverted note.
     */
    @Override
    public void invert() {
        if (accidental != Natural) {
            final byte semitones = accidental.getSemitones();
            pitch = Pitch.withOrder((byte) (pitch.order - Integer.signum(semitones) * 2));
            accidental = Accidental.withSemitone((byte) -semitones);
        }
    }

    /**
     * Inverts and returns this note by rotating its accidental, and returns this note.
     *
     * @return the inverted note.
     *
     * @see #invert()
     */
    @Override
    public Note inverted() {
        invert();
        return this;
    }

    /**
     * Returns true if the specified note type is equal to this note; otherwise returns false.
     *
     * @param type the note type.
     *
     * @return true if type is equal to this note, and false otherwise.
     */
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
     * Returns the note number as a {@code float}.
     *
     * @return the numeric value represented by this note after conversion to type {@code float}.
     */
    @Override
    public float floatValue() {
        return getOrder();
    }

    /**
     * Returns the note number as a {@code long}.
     *
     * @return the numeric value represented by this note after conversion to type {@code long}.
     */
    @Override
    public long longValue() {
        return getOrder().longValue();
    }

    /**
     * Subtracts the specified number of cents from this note and returns this note.
     * <p>
     * If this is a standard note the equivalent singleton will be returned, when one exists.
     *
     * @param n the number.
     *
     * @return the subtracted note or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the number is null.
     */
    @Override
    public Note minus(final Number n) {
        subtract(n);
        return this;
    }

    /**
     * This implementation throws an {@code UnsupportedOperationException}.
     */
    @Override
    public void multiply(final Number n) {
        throw new UnsupportedOperationException(OperationImpossible);
    }

    /**
     * Adds the specified number of cents to this note and returns this note.
     * <p>
     * If this is a standard note the equivalent singleton will be returned, when one exists.
     *
     * @param n the number.
     *
     * @return the added note or its equivalent singleton if this is a standard type.
     *
     * @throws NullPointerException if the number is null.
     */
    @Override
    public Note plus(final Number n) {
        add(n);
        return this;
    }

    /**
     * Subtracts the specified amount of cents from the note and adjusts the note.
     * <p>
     * This implementation performs unsafe casts from {@code int} to {@code short}.
     *
     * @param cents the cents.
     *
     * @throws NullPointerException if the number is null.
     *
     * @see #adjust()
     */
    @Override
    public void subtract(final Number n) {
        add(n.shortValue());
    }

    /**
     * This implementation throws an {@code UnsupportedOperationException}.
     */
    @Override
    public Note times(final Number n) {
        throw new UnsupportedOperationException(OperationImpossible);
    }

    /**
     * Returns a string representation of the note.
     *
     * @return the note string.
     */
    @Override
    public String toString() {
        return pitch.toString() + accidental.toString() + octave.toString() + getAdjustmentSuffix();
    }

    /**
     * Sets the adjustment to zero.
     */
    @Override
    public void unadjust() {
        adjustment = 0;
    }

    /**
     * Unadjusts and returns the note.
     *
     * @return the unadjusted note.
     *
     * @see #unadjust()
     */
    @Override
    public Note unadjusted() {
        unadjust();
        return this;
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
    short getAdjustment() {
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
     * Returns the symbol of the note.
     *
     * @return the symbol.
     */
    @Override
    public String getSymbol() {
        return symbol;
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
     * Sets the octave of the note.
     *
     * @param octave the octave.
     */
    public
    void setOctave(
        final Octave octave
        ) {
        this.octave = octave.getOrder();
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
     * Sets the symbol of the note.
     *
     * @param symbol the symbol.
     */
    @Override
    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    /**
     * {@code Accidental} represents all note accidentals.
     * <p>
     * This class defines the standard accidentals: sharp, flat, and natural.
     * Double-sharp and double-flat accidentals are defined in {@link Scale.Accidental}.
     * <p>
     * Cloning a static standard accidental returns itself.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class Accidental
    implements
        AccidentalType,
        Adjusting<Accidental, Number>,
        Comparable<Accidental>,
        Delta<Byte>,
        Modulus.Local<Byte>,
        Standardized<Accidental>,
        Supporting<Pitch>,
        Symbolized<String>,
        Unit
    {
        /** The flat accidental. */
        public static final
        Accidental Flat = new Standard(FlatSym, (short) -100);

        /** The natural accidental. */
        public static final
        Accidental Natural = new Standard(NaturalSym, (short) 0);

        /** The sharp accidental. */
        public static final
        Accidental Sharp = new Standard(SharpSym, (short) 100);

        /** The accidental symbol. */
        protected
        String symbol;

        /** The width of the accidental interval in cents. */
        protected final
        short cents;

        /** The accidental order. (semitones) */
        private final
        byte order;

        /**
         * Creates an accidental with the specified symbol and cents.
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
            order = getSemitones();
        }

        /**
         * Returns the accidental with the specified semitone, or null if the semitones value is out of range.
         *
         * @param semitone the semitone.
         *
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

        /**
         * Returns the accidental with the specified symbol, or null if the symbol is not a valid accidental symbol.
         *
         * @param symbol the symbol.
         *
         * @return true if the accidental of the symbol.
         */
        public static
        Accidental withSymbol(
            final String symbol
            ) {
            return symbol.equals(Sharp.symbol)
                   ? Sharp
                   : symbol.equals(Flat.symbol)
                     ? Flat
                     : symbol.equals(Natural.symbol)
                       ? Natural
                       : null;
        }

        /**
         * Returns the standard accidental adjusted by the specified adjustments as whole semitone amounts.
         *
         * @param adjustments the adjustments.
         *
         * @return the adjusted standard accidental.
         */
        @Override
        public Accidental adjusted(final Number... adjustments) {
            byte semitones = getSemitones();
            for (final Number adj : adjustments)
                if (adj != null)
                    semitones += adj instanceof Interval
                                 ? ((Interval) adj).getSemitones()
                                 : adj.byteValue();

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

        /** {@inheritDoc} */
        @Override
        public Accidental clone() {
            return new Accidental(symbol, cents);
        }

        /** {@inheritDoc} */
        @Override
        public int compareTo(final Accidental accidental) {
            return cents - accidental.cents;
        }

        /**
         * {@inheritDoc}
         * <p>
         * This implementation returns this accidental.
         */
        @Override
        public Accidental convert() {
            return this;
        }

        /**
         * Returns true if the note has the same amount of cents as the specified numeric object, and false otherwise.
         *
         * @param obj the object.
         *
         * @return true if the interval is equal to the object, and false otherwise.
         */
        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof AccidentalType)
                return cents == ((AccidentalType) obj).getCents();

            if (obj instanceof Number)
                return cents == ((Number) obj).shortValue();

            return obj != null && obj.equals(this);
        }

        /**
         * Returns the order of the accidental.
         *
         * @return the order.
         */
        @Override
        public Byte getOrder() {
            return order;
        }

        /**
         * {@inheritDoc}
         * <p>
         * This implementation returns the {@code Byte} class.
         *
         * @return the {@code Byte} class.
         */
        @Override
        public Class<Byte> getOrderClass() {
            return Byte.class;
        }

        /**
         * {@inheritDoc}
         * <p>
         * This implementation returns the {@code Byte} class.
         *
         * @return the {@code Byte} class.
         */
        @Override
        public Class<Byte> getUnit() {
            return Byte.class;
        }

        /**
         * Returns true if this accidental is the same runtime instance of the specified type; otherwise returns false.
         *
         * @param type the other accidental type.
         *
         * @return true if the runtime instances are the same, or false otherwise.
         */
        @Override
        public boolean is(final system.data.Type<? extends AccidentalType> type) {
            return this == type;
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

        /**
         * Returns true if the specified pitch supports this accidental; otherwise returns false.
         * <p>
         * This implementation returns false if the pitch is null.
         *
         * @param pitch the pitch.
         *
         * @return true if the pitch supports this accidental.
         */
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

        /**
         * Returns the cents in the accidental.
         *
         * @return the cents.
         */
        @Override
        public
        short getCents() {
            return cents;
        }

        /**
         * Returns the semitones in the accidental.
         *
         * @return the semitones.
         */
        @Override
        public byte getSemitones() {
            return (byte) (cents / 100);
        }

        /**
         * Returns the symbol of the accidental.
         *
         * @return the symbol.
         */
        @Override
        public String getSymbol() {
            return symbol;
        }

        /**
         * Sets the symbol of the accidental.
         *
         * @param symbol the symbol.
         */
        @Override
        public void setSymbol(final String symbol) {
            this.symbol = symbol;
        }

        /**
         * {@code Standard} represents all standard accidentals.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        protected static
        class Standard
        extends Accidental
        {
            /**
             * Creates a standard accidental with the specified symbol and cents.
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

            /**
             * Creates and returns a copy of this standard accidental, or itself if it is a singleton.
             *
             * @return the copy of the standard accidental or the singleton.
             */
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
     * {@code Dynamics} represents the note dynamics.
     * <p>
     * In music, dynamics normally refers to the volume of a sound or note, but can also refer to every aspect of the execution of a given piece, either stylistic (staccato, legato, etc.) or functional. (velocity)
     * <p>
     * This class defines the well-known note dynamics in classical music as statically defined standard types.
     * Cloning a static standard dynamics returns itself.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class Dynamics
    implements
        Adjusting<Dynamics, Number>,
        ArticulationType,
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
         * Creates a dynamics with the specified symbol and velocity and null name.
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
         *
         * @return true if dynamics is standard, and false otherwise.
         */
        public static
        boolean isStandard(
            final Dynamics dynamics
            ) {
            return dynamics == PPP ||
                   dynamics == PP ||
                   dynamics == P ||
                   dynamics == MP ||
                   dynamics == MF ||
                   dynamics == F ||
                   dynamics == FF ||
                   dynamics == FFF;
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
         * Adjusts this dynamics by the specified adjustment velocities and returns the adjusted dynamics.
         *
         * @param adjustments the adjustments.
         *
         * @return the adjusted dynamics.
         */
        @Override
        public Dynamics adjusted(final Number... adjustments) {
            byte velocity = this.velocity;
            for (final Number adj : adjustments)
                if (adj != null) {
                    final byte amount = adj.byteValue();
                    velocity += amount;
                    if (velocity < Standard.Order[0].velocity)
                        velocity = amount > 0
                                   ? Standard.Order[Standard.Order.length - 1].velocity
                                   : Standard.Order[0].velocity;
                }

            return withVelocity(velocity);
        }

        /** {@inheritDoc} */
        @Override
        public Dynamics clone() {
            return new Dynamics(symbol, name, velocity);
        }

        /** {@inheritDoc} */
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

        /**
         * Returns the symbol of the dynamics.
         *
         * @return the symbol.
         */
        @Override
        public String getSymbol() {
            return symbol;
        }

        /**
         * Returns true if this dynamics is equal to the specified type; otherwise returns false.
         *
         * @return true if the dynamics are equal.
         */
        @Override
        public boolean is(final system.data.Type<? extends Dynamics> type) {
            return equals(type);
        }

        /**
         * Sets the symbol of the dynamics.
         *
         * @param the symbol.
         */
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

            /** The array of dynamics singletons. (descending) */
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

            /**
             * Creates and returns a copy of this standard dynamics, or itself if it is a singleton.
             *
             * @return the copy of the standard dynamics or the singleton.
             */
            @Override
            public Standard clone() {
                return isStandard(this)
                       ? this
                       : new Standard(symbol, name, velocity);
            }
        }
    }

    /**
     * {@code Group} represents an arbitrary grouping of musical notes.
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
        List<Note> accompaniment;

        /**
         * Creates a note group starting with the specified notes.
         *
         * @param notes the notes.
         */
        public
        Group(
            final Note... notes
            ) {
            super();
            if (notes.length > 0) {
                set(this, notes[0]);
                adjust();

                if (notes.length > 1)
                    createAccompaniment(notes.length - 1);

                for (int i = 1; i < notes.length; accompaniment.add(notes[++i]));
            }
        }

        /**
         * Returns true if the specified note is in the group; otherwise returns false.
         *
         * @param note the note.
         *
         * @return true if the note is in the group, and false otherwise.
         */
        public
        boolean contains(
            final Note note
            ) {
            return equals(note) || accompaniment != null && accompaniment.contains(note);
        }

        /**
         * Creates the accompaniment with the specified initial capacity.
         * <p>
         * This implementation uses an {@code ArrayList}.
         *
         * @param initialCapacity the initial capacity.
         */
        protected
        void createAccompaniment(
            final int initialCapacity
            ) {
            accompaniment = new ArrayList<Note>(initialCapacity);
        }

        /**
         * Sets the specified instance equal to the specified note and returns true.
         *
         * @param instance the instance.
         * @param note the note.
         *
         * @return true.
         */
        protected
        boolean set(
            final Note instance,
            final Note note
            ) {
            instance.octave = note.octave;
            instance.pitch = note.pitch;
            instance.accidental = note.accidental;
            instance.adjustment = note.adjustment;
            return true;
        }

        /**
         * Adds the specified note to the group and returns true.
         *
         * @param note the note.
         *
         * @return true.
         */
        @Override
        public boolean add(final Note note) {
            if (pitch == null)
                return set(this, note);

            if (accompaniment == null)
                createAccompaniment(1);

            return accompaniment.add(note);
        }

        /**
         * Adds all the notes in the specified collection to the group, and returns true if the group changes; otherwise returns false.
         *
         * @param the notes.
         *
         * @return true if the group is changed, and false otherwise.
         */
        @Override
        public boolean addAll(final Collection<? extends Note> notes) {
            if (notes == null)
                return false;

            int size = size();
            notes.forEach(this::add);
            return size < size();
        }

        /**
         * Clears the group.
         */
        @Override
        public void clear() {
            pitch = null;
            if (accompaniment != null)
                accompaniment.clear();
        }

        /**
         * Returns true if the group contains the specified object; otherwise returns false.
         *
         * @param obj the object.
         *
         * @return true if the object is in the group, and false otherwise.
         */
        @Override
        public boolean contains(final Object obj) {
            return obj instanceof Note &&
                   contains((Note) obj);
        }

        /**
         * Returns true if the group contains all of the specified notes; otherwise returns false.
         *
         * @param the notes.
         *
         * @return true if the group contains all the note, and false otherwise.
         */
        @Override
        public boolean containsAll(final Collection<?> notes) {
            if (notes == null)
                return false;

            for (final Object note : notes)
                if (!contains(note))
                    return false;

            return true;
        }

        /**
         * Returns true if the group is empty; otherwise returns false.
         *
         * @return true if the group is empty, and false otherwise.
         */
        @Override
        public boolean isEmpty() {
            return pitch != null;
        }

        /**
         * Creates and returns an iterator for the notes in the group.
         *
         * @return the iterator.
         */
        @Override
        public Iterator<Note> iterator() {
            return new Iterator<Note>() {
                int i = 0;

                @Override
                public boolean hasNext() {
                    return i < size();
                }

                @Override
                public Note next() {
                    if (i >= size())
                        throw new NoSuchElementException();

                    if (i == 0) {
                        i = 1;
                        return Group.this;
                    }

                    return accompaniment.get(i++ - 1);
                }
            };
        }

        /**
         * Removes the specified object from the group, and returns true if the group changes; otherwise returns false.
         *
         * @param the object.
         *
         * @return true if the object is removed, and false otherwise.
         */
        @Override
        public boolean remove(final Object obj) {
            if (isEmpty())
                return false;

            final int size = size();
            if (accompaniment != null)
                for (int i = 0; i < accompaniment.size(); i++)
                    if (accompaniment.get(i).equals(obj))
                        accompaniment.remove(i--);

            if (equals(obj))
                pitch = null;

            if (!(accompaniment == null || accompaniment.isEmpty()))
                set(this, accompaniment.remove(0));

            return size > size();
        }

        /**
         * Removes all the specified notes from the group, and returns true if the group changes; otherwise returns false.
         *
         * @param the notes.
         *
         * @return true if a note is removed, and false otherwise.
         */
        @Override
        public boolean removeAll(final Collection<?> notes) {
            final int size = size();
            notes.forEach((final Object note) -> remove(note));
            return size > size();
        }

        /**
         * Retains all the specified notes in the group and removes the others, and returns true if the group changes; otherwise returns false.
         *
         * @param the notes.
         *
         * @return true if a note is removed, and false otherwise.
         */
        @Override
        public boolean retainAll(final Collection<?> notes) {
            final int size = size();
            forEach((final Note note) -> { if (!notes.contains(note)) remove(note); });
            return size > size();
        }

        /**
         * Returns the size of the group.
         *
         * @return the size.
         */
        @Override
        public int size() {
            return pitch == null
                   ? 0
                   : accompaniment == null
                     ? 1
                     : accompaniment.size() + 1;
        }

        /**
         * This implementation throws an {@code UnsupportedOperationException}.
         */
        @Override
        public boolean supports(Chord instance) {
            throw new UnsupportedOperationException();
        }

        /**
         * Returns an array containing all the notes in the group.
         *
         * @return the array of notes.
         */
        @Override
        public Object[] toArray() {
            return toArray(new Note[size()]);
        }

        /**
         * Returns an array of the specified array type containing all the notes in the group.
         *
         * @return the array of notes.
         */
        @Override
        public <T> T[] toArray(T[] array) {
            if (array == null)
                return null;

            if (isEmpty())
                return array;

            if (size() > array.length)
                array = (T[]) Array.newInstance(array.getClass().getComponentType(), 0);

            array[0] = (T) this;
            final int size = size();
            int i;
            for (i = 1; i < array.length && i < size; i++)
                array[i] = (T) accompaniment.get(i - 1);

            if (i < array.length)
                for (; i < array.length; array[i++] = null);

            return array;
        }

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
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Octave
    extends
        Comparable<Octave>,
        Ordered<Byte>,
        OctaveType,
        Symbolized<String>,
        Unit
    {
        /** Octave -2. */
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

        /** Octave -1. */
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

        /** Octave 0. */
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

        /** Octave 1. */
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

        /** Octave 2. */
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

        /** Octave 3. */
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

        /** Octave 4. */
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

        /** Octave 5. */
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

        /** Octave 6. */
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

        /** Octave 7. */
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

        /** Octave 8. */
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

        /** Octave 9. */
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

        /** Octave 10. */
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

        /** Null octave. */
        public final Octave Null = new Octave() {
            @Override
            public Byte getOrder() {
                return null;
            }

            @Override
            public String getSymbol() {
                return NullOctaveSym;
            }
        };

        /**
         * Returns the octave type with the specified number, or null if the number is null.
         * <p>
         * The accepted range is [-2, 10].
         *
         * @param n the number.
         *
         * @return the octave type.
         *
         * @throws IllegalArgumentException if the number is out of range.
         */
        static
        Octave valueOf(
            final Number n
            ) {
            if (n == null)
                return null;

            switch (n.byteValue()) {
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
                throw new IllegalArgumentException(OrderOutOfRange);
            }
        }

        /**
         * Returns the order of the specified octave, or null if the octave is null.
         *
         * @param octave the octave.
         *
         * @return the order.
         */
        static
        Byte orderOf(
            final Octave octave
            ) {
            return octave == null
                   ? null
                   : octave.getOrder();
        }

        /**
         * Compares this instance with the specified octave, and returns a negative integer if this instance is less than the octave, zero if they are equal, and a positive integer otherwise.
         * If this instance is not null and the specified octave is null, {@link Integer#MAX_VALUE} will be returned.
         * If this instance if null and the specified octave is not null, {@link Integer#MIN_VALUE} will be returned.
         *
         * @param octave the octave.
         *
         * @return a negative integer if this octave is less than the fraction, zero if they are equal, and a positive integer otherwise.
         */
        @Override
        default int compareTo(final Octave octave) {
            return octave == null
                   ? this == Null
                     ? 0
                     : Integer.MAX_VALUE
                   : this == Null
                     ? Integer.MIN_VALUE
                     : getOrder() - octave.getOrder();
        }

        /**
         * {@inheritDoc}
         * <p>
         * This implementation return the {@code Byte} class.
         *
         * @return the {@code Byte} class.
         */
        @Override
        default Class<Byte> getOrderClass() {
            return Byte.class;
        }

        /**
         * Returns true if this octave is the same runtime instance of the specified type; otherwise returns false.
         *
         * @param type the other octave type.
         *
         * @return true if the runtime instances are the same, or false otherwise.
         */
        @Override
        default boolean is(final system.data.Type<? extends OctaveType> type) {
            return this == type;
        }
    }

    /**
     * {@code Pitch} represents one of the seven standard pitches in Western classical music: A, B, C, D, E, F, and G.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    enum Pitch
    implements
        Adjusting<Pitch, Number>,
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

        /** The array of pitches sorted by their order. */
        private static final
        Pitch[] Order
        = new Pitch[] { C, C, D, D, E, F, F, G, G, A, A, B };

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

        /**
         * Returns true if the specified character is a valid pitch name; otherwise returns false.
         *
         * @param symbol the pitch symbol.
         *
         * @return true if the symbol is a valid pitch, and false otherwise.
         */
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

        /**
         * Returns the pitch with the specified symbol.
         *
         * @param symbol the symbol.
         *
         * @return the pitch.
         */
        public static
        Pitch valueOf(
            final char symbol
            ) {
            return valueOf(Character.toString(Character.toUpperCase(symbol)));
        }

        /**
         * Returns the pitch for the specified order.
         *
         * @param order the order.
         *
         * @return the pitch.
         */
        public static
        Pitch withOrder(
            final byte order
            ) {
            return Order[order % 12];
        }

        /**
         * Adjusts this pitch by the specified adjustments, as semitones, and returns this pitch.
         *
         * @param adjustments the adjustments.
         *
         * @return the adjusted pitch.
         */
        @Override
        public Pitch adjusted(final Number... adjustments) {
            short order = this.order;
            for (final Number adj : adjustments)
                if (adj != null) {
                    order += adj.shortValue();
                    order -= (order / 12) * 12;
                }

            return withOrder((byte) order);
        }

        /**
         * This implementation returns this pitch.
         */
        @Override
        public PitchType convert() {
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

        /**
         * {@inheritDoc}
         * <p>
         * This implementation returns the {@code Byte} class.
         *
         * @return the {@code Byte} class.
         */
        @Override
        public Class<Byte> getOrderClass() {
            return Byte.class;
        }

        /**
         * Returns the pitch symbol.
         *
         * @return the symbol.
         */
        @Override
        public String getSymbol() {
            return name();
        }

        /**
         * Returns true if this pitch is the same runtime instance of the specified type; otherwise returns false.
         *
         * @param type the other pitch type.
         *
         * @return true if the runtime instances are the same, or false otherwise.
         */
        @Override
        public boolean is(final system.data.Type<? extends PitchType> type) {
            return type == this;
        }

        /**
         * Returns true if this pitch supports the specified accidental; otherwise returns false.
         * <p>
         * This implementation returns false if the accidental is null.
         *
         * @param accidental the accidental.
         *
         * @return true if the pitch supports this accidental.
         */
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

        /**
         * Returns the pitch symbol.
         *
         * @return the symbol.
         */
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
                ProgressiveDataPoint<Byte, Regressor, Regressor>,
                Modulus,
                music.system.Type<Note>
            {
                Modulus.Local<?> getAccidental();

                /**
                 * This implementation returns {@link Octave#Null}.
                 *
                 * @return the null octave.
                 */
                default
                Octave getOctave() {
                    return Octave.Null;
                }

                Modulus.Tabular<?> getPitch();
            }
        }
    }

    /**
     * {@code Step} classifies categorizes note increment steps that are well-defined in the context of note transformation.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Step
    extends
        Ordered<Short>,
        Templator,
        Unit
    {
        /** The full step. */
        public static final
        Step Full = new Step() {
            @Override
            public Short getOrder() {
                return (byte) 200;
            }

            @Override
            public Interval getInterval() {
                return Interval.MajorSecond;
            }
        };

        /** The half step. */
        public static final
        Step Half = new Step() {
            @Override
            public Short getOrder() {
                return (byte) 100;
            }

            @Override
            public Interval getInterval() {
                return Interval.MinorSecond;
            }
        };

        /** The quarter step. */
        public static final
        Step Quarter = new Step() {
            @Override
            public Short getOrder() {
                return (byte) 50;
            }

            @Override
            public Interval getInterval() {
                return Interval.QuarterTone;
            }
        };

        /** The eighth step. */
        public static final
        Step Eighth = new Step() {
            Interval interval = new Interval((short) 25);

            @Override
            public Short getOrder() {
                return (byte) 25;
            }

            @Override
            public Interval getInterval() {
                return interval;
            }
        };

        /** The tenor step. */
        public static final
        Step Tenor = new Step() {
            Interval interval = new Interval((short) 10);

            @Override
            public Short getOrder() {
                return (byte) 10;
            }

            @Override
            public Interval getInterval() {
                return interval;
            }
        };

        /**
         * {@inheritDoc}
         * <p>
         * This implementation returns the {@code Short} class.
         */
        @Override
        default Class<Short> getOrderClass() {
            return Short.class;
        }

        /**
         * Returns the interval represented by this step.
         *
         * @return the interval.
         */
        Interval getInterval();
    }

    /**
     * {@code Singleton} represents all standard notes in classical music.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    private static final
    class Singleton
    extends Standard
    implements
        Operable.Locked<Number>,
        Symbolized.Singleton<String>
    {
        /** A constant holding the maximum value a {@code Singleton} can have, 143. */
        public static final
        short MAX_VALUE = 143;

        /** A constant holding the minimum value a {@code Singleton} can have, zero. */
        public static final
        short MIN_VALUE = 0;

        /** The {@code Class} instance representing the type {@code Singleton}. */
        public static final
        Class<Note.Singleton> TYPE = Note.Singleton.class;

        /** The array of note singletons. (ascending) */
        public static final
        Note.Singleton[] Order
        = new Note.Singleton[] {
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

        /** The instantiation counter. */
        private static
        short counter = 0;

        /** The singleton index. */
        private final
        short order;

        /** The note number. */
        public final
        float number;

        /** The note frequency. */
        public final
        float freq;

        /**
         * Creates a singleton note with the specified number, symbol, octave, pitch, accidental, and frequency.
         *
         * @param number the number.
         * @param symbol the symbol.
         * @param octave the octave.
         * @param pitch the pitch.
         * @param accidental the accidental.
         * @param freq the frequency.
         */
        private
        Singleton(
            final float number,
            final String symbol,
            final byte octave,
            final Pitch pitch,
            final Accidental accidental,
            final float freq
            ) {
            super(octave, pitch, accidental);
            this.number = number;
            this.freq = freq;
            this.symbol = symbol;
            order = counter++;
        }

        /**
         * Creates a singleton note with the specified number, symbol, octave, pitch, and frequency.
         *
         * @param number the number.
         * @param symbol the symbol.
         * @param octave the octave.
         * @param pitch the pitch.
         * @param freq the frequency.
         */
        private
        Singleton(
            final float number,
            final String symbol,
            final byte octave,
            final Pitch pitch,
            final float freq
            ) {
            this(number, symbol, octave, pitch, Accidental.Natural, freq);
        }

        /**
         * Returns the difference in cents between the singleton and the specified note values.
         *
         * @param octave the octave.
         * @param pitch the pitch.
         * @param accidental the accidental.
         *
         * @return the difference with the note values.
         *
         * @throws NullPointerException if the pitch or accidental is null.
         */
        public
        int diff(
            final short octave,
            final Pitch pitch,
            final Accidental accidental
            ) {
            return (this.octave - octave) * 1200 +
                   (this.pitch.order - pitch.order) * 100 +
                   this.accidental.cents - accidental.cents;
        }

        /**
         * Clones this singleton and adjusts the copy with the specified adjustments, as cents, and returns it or the equivalent singleton if one exists.
         * <p>
         * This implementation calls {@link Number#shortValue()} on all the adjustment values.
         *
         * @param adjustments the adjustments.
         *
         * @return the adjusted standard note or its equivalent singleton.
         *
         * @see Note#adjusted(Number[])
         */
        @Override
        public Standard adjusted(final Number... adjustments) {
            return clone().adjusted(adjustments);
        }

        /**
         * Returns the singleton equivalent to this note using the specified preferred sharp flag, or itself if none is found.
         *
         * @param sharp the preferred sharp flag.
         *
         * @return the equivalent singleton or this singleton.
         */
        @Override
        public Note.Singleton distinct(final boolean sharp) {
            return sharp
                   ? accidental == Flat && Order[order - 1].accidental == Sharp
                     ? Order[order - 1]
                     : this
                   : accidental == Sharp && Order[order + 1].accidental == Flat
                       ? Order[order + 1]
                     : this;
        }

        /**
         * Returns this singleton.
         *
         * @return the singleton.
         */
        @Override
        public Note.Singleton distinct() {
            return this;
        }

        /**
         * Clones this singleton and inverts the copy and returns it or the equivalent singleton if one exists.
         *
         * @return the inverted standard note or its equivalent singleton.
         *
         * @see Note#inverted()
         */
        @Override
        public Standard inverted() {
            return clone().inverted();
        }

        /**
         * Clones this singleton and subtracts the specified number of cents from the copy and returns it or the equivalent singleton if one exists.
         *
         * @param n the number.
         *
         * @return the subtracted standard note or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         *
         * @see Note#minus(Number)
         */
        @Override
        public Standard minus(final Number n) {
            return clone().minus(n);
        }

        /**
         * Clones this singleton and adds the specified number of cents to the copy and returns it or the equivalent singleton if one exists.
         *
         * @param n the number.
         *
         * @return the added standard note or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         */
        @Override
        public Standard plus(final Number n) {
            return clone().plus(n);
        }

        /**
         * {@inheritDoc}
         * <p>
         * This implementation is empty.
         */
        @Override
        public void unadjust() {}

        /**
         * {@inheritDoc}
         * <p>
         * This implementation returns this singleton.
         *
         * @return the singleton.
         */
        @Override
        public Note.Singleton unadjusted() {
            return this;
        }

        /** {@inheritDoc} */
        @Override
        public float getFrequency() {
            return freq;
        }

        /**
         * Returns the note number.
         *
         * @return the note number.
         */
        @Override
        public float getNumber() {
            return number;
        }
    }

    /**
     * {@code Standard} represents all standard notes.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static
    class Standard
    extends Note
    {
        /** The {@code Class} instance representing the type {@code Standard}. */
        public static final
        Class<Standard> TYPE = Standard.class;

        /**
         * Creates a standard note equivalent to the specified note and with the specified symbol and adjustment, and adjusts the note.
         *
         * @param symbol the symbol.
         * @param note the note.
         * @param adjustment the adjustment.
         *
         * @throws NullPointerException if the note is null.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final String symbol,
            final Note note,
            final Number adjustment
            ) {
            super();
            this.symbol = symbol;
            octave = note.octave;
            pitch = note.pitch;
            accidental = note.accidental;
            this.adjustment = adjustment == null
                    ? 0
                    : adjustment.shortValue();

            adjust();
        }

        /**
         * Creates a standard note equivalent to the specified note and with the specified symbol.
         *
         * @param symbol the symbol.
         * @param note the note.
         *
         * @throws NullPointerException if the note is null.
         */
        public
        Standard(
            final String symbol,
            final Note note
            ) {
            super();
            this.symbol = symbol;
            octave = note.octave;
            pitch = note.pitch;
            accidental = note.accidental;
        }

        /**
         * Creates a standard note equivalent to the specified note and with null symbol.
         *
         * @param note the note.
         *
         * @throws NullPointerException if the note is null.
         */
        public
        Standard(
            final Note note
            ) {
            this((String) null, note);
        }

        /**
         * Creates a standard note with the specified symbol, octave, pitch, accidental, and adjustment (in cents), and adjusts the note.
         * <p>
         * This implementation calls {@link Number#byteValue()} on the octave and {@link Number#shortValue()} on the adjustment unless it is null, where it will be set to zero.
         * Null octaves are allowed.
         *
         * @param symbol the symbol.
         * @param octave the octave.
         * @param pitch the pitch.
         * @param accidental the accidental.
         * @param adjustment the adjustment.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final String symbol,
            final Number octave,
            final Pitch pitch,
            final Accidental accidental,
            final Number adjustment
            ) {
            super(symbol, octave, pitch, accidental, adjustment);
        }

        /**
         * Creates a standard note with the specified symbol, octave, pitch, accidental, and adjustment (in cents), and adjusts the note.
         * <p>
         * This implementation calls {@link Number#byteValue()} on the octave and {@link Number#shortValue()} on the adjustment unless it is null, where it will be set to zero.
         * Null octaves are allowed.
         *
         * @param symbol the symbol.
         * @param octave the octave.
         * @param pitch the pitch.
         * @param accidental the accidental.
         * @param adjustment the adjustment.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final String symbol,
            final Octave octave,
            final Pitch pitch,
            final Accidental accidental,
            final Number adjustment
            ) {
            this(symbol, (Byte) Octave.orderOf(octave), pitch, accidental, adjustment);
        }

        /**
         * Creates a standard note with the specified symbol, octave, pitch, and accidental, and adjusts the note.
         * <p>
         * This implementation calls {@link Number#byteValue()} on the octave.
         * Null octaves are allowed.
         *
         * @param symbol the symbol.
         * @param octave the octave.
         * @param pitch the pitch.
         * @param accidental the accidental.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final String symbol,
            final Number octave,
            final Pitch pitch,
            final Accidental accidental
            ) {
            this(symbol, octave, pitch, accidental, 0);
        }

        /**
         * Creates a standard note with the specified symbol, octave, pitch, and accidental, and adjusts the note.
         * <p>
         * This implementation calls {@link Number#byteValue()} on the octave.
         * Null octaves are allowed.
         *
         * @param symbol the symbol.
         * @param octave the octave.
         * @param pitch the pitch.
         * @param accidental the accidental.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final String symbol,
            final Octave octave,
            final Pitch pitch,
            final Accidental accidental
            ) {
            this(symbol, octave, pitch, accidental, 0);
        }

        /**
         * Creates a standard natural note with the specified symbol, octave, pitch, and adjustment (in cents), and adjusts the note.
         * <p>
         * This implementation calls {@link Number#byteValue()} on the octave and {@link Number#shortValue()} on the adjustment.
         * Null octaves are allowed.
         *
         * @param symbol the symbol.
         * @param octave the octave.
         * @param pitch the pitch.
         * @param adjustment the adjustment.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final String symbol,
            final Number octave,
            final Pitch pitch,
            final Number adjustment
            ) {
            this(symbol, octave, pitch, (Accidental) null, adjustment);
        }

        /**
         * Creates a standard natural note with the specified symbol, octave, pitch, and adjustment (in cents), and adjusts the note.
         * <p>
         * This implementation calls {@link Number#byteValue()} on the octave and {@link Number#shortValue()} on the adjustment.
         * Null octaves are allowed.
         *
         * @param symbol the symbol.
         * @param octave the octave.
         * @param pitch the pitch.
         * @param adjustment the adjustment.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final String symbol,
            final Octave octave,
            final Pitch pitch,
            final Number adjustment
            ) {
            this(symbol, (Byte) Octave.orderOf(octave), pitch, adjustment);
        }

        /**
         * Creates a standard natural note with the specified symbol, octave, and pitch.
         * <p>
         * This implementation calls {@link Number#byteValue()} on the octave.
         * Null octaves are allowed.
         *
         * @param symbol the symbol.
         * @param octave the octave.
         * @param pitch the pitch.
         */
        public
        Standard(
            final String symbol,
            final Number octave,
            final Pitch pitch
            ) {
            super(symbol, octave, pitch);
        }

        /**
         * Creates a standard natural note with the specified symbol, octave, and pitch.
         * <p>
         * This implementation calls {@link Number#byteValue()} on the octave.
         * Null octaves are allowed.
         *
         * @param symbol the symbol.
         * @param octave the octave.
         * @param pitch the pitch.
         */
        public
        Standard(
            final String symbol,
            final Octave octave,
            final Pitch pitch
            ) {
            this(symbol, (Byte) Octave.orderOf(octave), pitch);
        }

        /**
         * Creates a standard note, as a pitch type, with null octave and the specified symbol, pitch, accidental, and adjustment (in cents), and adjusts the note.
         * <p>
         * This implementation calls {@link Number#shortValue()} on the adjustment unless it is null, where it will be set to zero.
         * Null octaves are allowed.
         *
         * @param symbol the symbol.
         * @param pitch the pitch.
         * @param accidental the accidental.
         * @param adjustment the adjustment.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final String symbol,
            final Pitch pitch,
            final Accidental accidental,
            final Number adjustment
            ) {
            this(symbol, (Number) null, pitch, accidental, adjustment);
        }

        /**
         * Creates a standard note, as a pitch type, with null octave and the specified symbol, pitch, and accidental, and adjusts the note, and adjusts the note.
         *
         * @param symbol the symbol.
         * @param pitch the pitch.
         * @param accidental the accidental.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final String symbol,
            final Pitch pitch,
            final Accidental accidental
            ) {
            this(symbol, (Byte) null, pitch, accidental);
        }

        /**
         * Creates a standard natural note, as a pitch type, with null octave and the specified symbol, pitch, and adjustment (in cents), and adjusts the note.
         * <p>
         * This implementation calls {@link Number#shortValue()} on the adjustment unless it is null, where it will be set to zero.
         * Null octaves are allowed.
         *
         * @param symbol the symbol.
         * @param pitch the pitch.
         * @param adjustment the adjustment.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final String symbol,
            final Pitch pitch,
            final Number adjustment
            ) {
            this(symbol, (Number) null, pitch, Natural, adjustment);
        }

        /**
         * Creates a standard natural note, as a pitch type, with null octave and the specified symbol and pitch.
         *
         * @param symbol the symbol.
         * @param pitch the pitch.
         */
        public
        Standard(
            final String symbol,
            final Pitch pitch
            ) {
            this(symbol, (Byte) null, pitch);
        }

        /**
         * Creates a standard note with the specified octave, pitch, accidental, adjustment (in cents), and null symbol, and adjusts the note.
         * <p>
         * This implementation calls {@link Number#byteValue()} on the octave and {@link Number#shortValue()} on the adjustment unless it is null, where it will be set to zero.
         * Null octaves are allowed.
         *
         * @param octave the octave.
         * @param pitch the pitch.
         * @param accidental the accidental.
         * @param adjustment the adjustment.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final Number octave,
            final Pitch pitch,
            final Accidental accidental,
            final Number adjustment
            ) {
            this(null, octave, pitch, accidental, adjustment);
        }

        /**
         * Creates a standard note with the specified octave, pitch, accidental, adjustment (in cents), and null symbol, and adjusts the note.
         * <p>
         * This implementation calls {@link Number#byteValue()} on the octave and {@link Number#shortValue()} on the adjustment unless it is null, where it will be set to zero.
         * Null octaves are allowed.
         *
         * @param octave the octave.
         * @param pitch the pitch.
         * @param accidental the accidental.
         * @param adjustment the adjustment.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final Octave octave,
            final Pitch pitch,
            final Accidental accidental,
            final Number adjustment
            ) {
            this(null, octave, pitch, accidental, adjustment);
        }

        /**
         * Creates a standard note with the specified octave, pitch, accidental, and null symbol, and adjusts the note.
         * <p>
         * This implementation calls {@link Number#byteValue()} on the octave.
         * Null octaves are allowed.
         *
         * @param octave the octave.
         * @param pitch the pitch.
         * @param accidental the accidental.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final Number octave,
            final Pitch pitch,
            final Accidental accidental
            ) {
            this(null, octave, pitch, accidental);
        }

        /**
         * Creates a standard note with the specified octave, pitch, accidental, and null symbol, and adjusts the note.
         * <p>
         * This implementation calls {@link Number#byteValue()} on the octave.
         * Null octaves are allowed.
         *
         * @param octave the octave.
         * @param pitch the pitch.
         * @param accidental the accidental.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final Octave octave,
            final Pitch pitch,
            final Accidental accidental
            ) {
            this(null, octave, pitch, accidental);
        }

        /**
         * Creates a standard note, as pitch type, with null octave and the specified pitch, accidental, adjustment (in cents), and null symbol, and adjusts the note.
         * <p>
         * This implementation calls {@link Number#shortValue()} on the adjustment unless it is null, where it will be set to zero.
         * Null octaves are allowed.
         *
         * @param pitch the pitch.
         * @param accidental the accidental.
         * @param adjustment the adjustment.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final Pitch pitch,
            final Accidental accidental,
            final Number adjustment
            ) {
            this(null, (Number) null, pitch, accidental, adjustment);
        }

        /**
         * Creates a standard natural note, as pitch type, with null octave and the specified pitch and adjustment (in cents), and null symbol, and adjusts the note.
         * <p>
         * This implementation calls {@link Number#shortValue()} on the adjustment unless it is null, where it will be set to zero.
         * Null octaves are allowed.
         *
         * @param pitch the pitch.
         * @param accidental the accidental.
         * @param adjustment the adjustment.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final Pitch pitch,
            final Number adjustment
            ) {
            this(pitch, Natural, adjustment);
        }

        /**
         * Creates a standard natural note with the specified octave, pitch, and adjustment (in cents), and null symbol, and adjusts the note.
         * <p>
         * This implementation calls {@link Number#byteValue()} on the octave and {@link Number#shortValue()} on the adjustment.
         * Null octaves are allowed.
         *
         * @param symbol the symbol.
         * @param octave the octave.
         * @param pitch the pitch.
         * @param adjustment the adjustment.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final Number octave,
            final Pitch pitch,
            final Number adjustment
            ) {
            this(null, octave, pitch, Natural, adjustment);
        }

        /**
         * Creates a standard natural note with the specified octave, pitch, and adjustment (in cents), and null symbol, and adjusts the note.
         * <p>
         * This implementation calls {@link Number#byteValue()} on the octave and {@link Number#shortValue()} on the adjustment.
         * Null octaves are allowed.
         *
         * @param octave the octave.
         * @param pitch the pitch.
         * @param adjustment the adjustment.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final Octave octave,
            final Pitch pitch,
            final Number adjustment
            ) {
            this(null, octave, pitch, Natural, adjustment);
        }

        /**
         * Creates a standard natural note with the specified octave and pitch, and null symbol.
         * <p>
         * This implementation calls {@link Number#byteValue()} on the octave.
         * Null octaves are allowed.
         *
         * @param octave the octave.
         * @param pitch the pitch.
         */
        public
        Standard(
            final Number octave,
            final Pitch pitch
            ) {
            this(null, octave, pitch);
        }

        /**
         * Creates a standard natural note with the specified octave and pitch, and null symbol.
         * <p>
         * This implementation calls {@link Number#byteValue()} on the octave.
         * Null octaves are allowed.
         *
         * @param octave the octave.
         * @param pitch the pitch.
         */
        public
        Standard(
            final Octave octave,
            final Pitch pitch
            ) {
            this(null, octave, pitch);
        }

        /**
         * Creates a standard note, as pitch type, with null octave and the specified pitch and accidental, and null symbol, and adjusts the note.
         *
         * @param pitch the pitch.
         * @param accidental the accidental.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final Pitch pitch,
            final Accidental accidental
            ) {
            this((Byte) null, pitch, accidental);
        }

        /**
         * Creates a standard natural note, as a pitch type, with null octave and the specified pitch, and null symbol.
         *
         * @param pitch the pitch.
         */
        public
        Standard(
            final Pitch pitch
            ) {
            this((Byte) null, pitch);
        }

        /**
         * Creates a standard note with the specified symbol, number, and sharp preference flag, and adjusts the note.
         * <p>
         * If the sharp flag is true and the note is not a natural note, the sharp note will be created; otherwise the flat note is created.
         * <p>
         * This constructor performs an unsafe cast from {@code float} to {@code short}.
         *
         * @param the symbol.
         * @param number the number.
         * @param sharp the sharp preference flag.
         *
         * @throws IllegalArgumentException if the number is less than zero.
         */
        public
        Standard(
            final String symbol,
            float number,
            final boolean sharp
            ) {
            super(symbol, number, sharp);
        }

        /**
         * Creates a standard note with the specified symbol and number, and with sharp preference flag set to true, and adjusts the note.
         * <p>
         * This constructor performs an unsafe cast from {@code float} to {@code short}.
         *
         * @param the symbol.
         * @param number the number.
         *
         * @throws IllegalArgumentException if the number is less than zero.
         */
        public
        Standard(
            final String symbol,
            float number
            ) {
            this(symbol, number, true);
        }

        /**
         * Creates a standard note with the specified number and sharp preference flag, and null symbol, and adjusts the note.
         * <p>
         * If the sharp flag is true and the note is not a natural note, the sharp note will be created; otherwise the flat note is created.
         * <p>
         * This constructor performs an unsafe cast from {@code float} to {@code short}.
         *
         * @param number the number.
         * @param sharp the sharp preference flag.
         *
         * @throws IllegalArgumentException if the number is less than zero.
         */
        public
        Standard(
            float number,
            final boolean sharp
            ) {
            this(null, number, sharp);
        }

        /**
         * Creates a standard note with the specified number, and with sharp preference flag set to true, and null symbol, and adjusts the note.
         * <p>
         * This constructor performs an unsafe cast from {@code float} to {@code short}.
         *
         * @param number the number.
         *
         * @throws IllegalArgumentException if the number is less than zero.
         */
        public
        Standard(
            float number
            ) {
            this(number, true);
        }

        /**
         * Creates a standard note with the specified symbol and from the specified string value, and adjusts the note.
         * <p>
         * The string value must be a valid note symbol, such as "A", "A4", "Eb", or "Eb-1".
         *
         * @param symbol the symbol.
         * @param value the value.
         *
         * @throws NullPointerException if the value is null.
         * @throws IllegalArgumentException if the value is not a valid note symbol.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final String symbol,
            final String value
            ) {
            super(symbol, value);
        }

        /**
         * Creates a standard note from the specified string value and with a symbol equal to the same value, and adjusts the note.
         * <p>
         * The string value must be a valid note symbol, such as "A", "A4", "Eb", or "Eb-1".
         *
         * @param value the value.
         *
         * @throws NullPointerException if the value is null.
         * @throws IllegalArgumentException if the value is not a valid note symbol.
         *
         * @see Note#adjust()
         */
        public
        Standard(
            final String value
            ) {
            this(value, value);
        }

        /**
         * Adjusts this standard note with the specified adjustments, as cents, and returns this standard note or the equivalent singleton if one exists.
         * <p>
         * This implementation calls {@link Number#shortValue()} on all the adjustment values.
         *
         * @param adjustments the adjustments.
         *
         * @return the adjusted standard note or its equivalent singleton.
         *
         * @see Note#adjusted(Number[])
         */
        @Override
        public Standard adjusted(final Number... adjustments) {
            return (Standard) super.adjusted(adjustments).distinct();
        }

        /**
         * Creates and returns a copy of this standard note.
         *
         * @return the copy of the standard note.
         */
        @Override
        public Standard clone() {
            return new Standard(symbol, octave, pitch, accidental, adjustment);
        }

        /**
         * Inverts and returns this standard note or its equivalent singleton.
         *
         * @return the inverted standard note or its equivalent singleton.
         *
         * @see Note#inverted()
         */
        @Override
        public Standard inverted() {
            return (Standard) super.inverted().distinct();
        }

        /**
         * Subtracts the specified number of cents from this standard note and returns this standard note or its equivalent singleton, when one exists.
         *
         * @param n the number.
         *
         * @return the subtracted standard note or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         *
         * @see Note#minus(Number)
         */
        @Override
        public Standard minus(final Number n) {
            return (Standard) super.minus(n).distinct();
        }

        /**
         * Adds the specified number of cents to this note and returns this note or its equivalent singleton, when one exists.
         *
         * @param n the number.
         *
         * @return the added standard note or its equivalent singleton.
         *
         * @throws NullPointerException if the number is null.
         *
         * @see Note#plus(Number)
         */
        @Override
        public Standard plus(final Number n) {
            return (Standard) super.plus(n).distinct();
        }

        /**
         * Unadjusts and returns the standard note.
         *
         * @return the unadjusted standard note.
         *
         * @see Note#unadjusted()
         */
        @Override
        public Note unadjusted() {
            return super.unadjusted().distinct();
        }
    }
}