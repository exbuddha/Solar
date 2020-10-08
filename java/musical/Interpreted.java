package musical;

import java.util.List;

import exceptions.UnsupportedClefException;
import system.data.Fraction;

/**
 * {@code Interpreted} classifies the entity in music capable of translating elements in the score to intermediary types that relate to instances of music.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Interpreted
{
    /**
     * Finds the effective performance instrument name or names of this instance, or returns an empty list if data is not found.
     *
     * @return the list of instrument names, or an empty list if data is not found.
     */
    public
    List<String> findInstrumentName();

    /**
     * Finds the scale of the effective key signature of this instance, or returns null if data is not found.
     *
     * @return the effective key signature, or null if data is not found.
     */
    public
    Scale findKey();

    /**
     * Finds the clef type of the effective staff or staffs, ordered from top to bottom, that define the system of this instance, or returns null if data is not found; or throws an {@code UnsupportedClefException} if a clef type is unknown.
     *
     * @return the effective clef or clefs, or null if data is not found.
     * @throws UnsupportedClefException if a clef type is unknown.
     */
    public
    List<Clef> findSystem();

    /**
     * Finds the effective tempo of this instance, or returns null if data is not found.
     *
     * @return the effective tempo, or null if data is not found.
     */
    public
    Tempo findTempo();

    /**
     * Finds the effective time signature of this instance, or returns null if data is not found.
     *
     * @return the effective time signature, or null if data is not found.
     */
    public
    Fraction findTimeSignature();
}