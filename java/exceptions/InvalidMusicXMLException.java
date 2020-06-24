package exceptions;

import org.xml.sax.SAXException;

import music.system.data.MusicXML.Validation;

/**
 * Signals that an exception occurred when parsing a MusicXML file, converting a document from partwise to timewise format, or processing the music data in the document.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class InvalidMusicXMLException
extends SAXException
{
    /** The validation. */
    protected final
    Validation validation;

    /**
     * Constructs an {@code InvalidMusicXMLException} with the specified detail message and cause.
     *
     * @param message the message.
     * @param cause the cause.
     */
    public
    InvalidMusicXMLException(
        final String message,
        final Exception cause
        ) {
        super(message, cause);
        validation = null;
    }

    /**
     * Constructs an {@code InvalidMusicXMLException} with the specified detail message.
     *
     * @param message the message.
     */
    public
    InvalidMusicXMLException(
        final String message
        ) {
        super(message);
        validation = null;
    }

    /**
     * Constructs an {@code InvalidMusicXMLException} with the specified detail message, cause, and validation.
     *
     * @param message the message.
     * @param cause the cause.
     * @param validation the validation.
     */
    public
    InvalidMusicXMLException(
        final String message,
        final Exception cause,
        final Validation validation
        ) {
        super(message, cause);
        this.validation = validation;
    }

    /**
     * Constructs an {@code InvalidMusicXMLException} with the specified detail message and validation.
     *
     * @param message the message.
     * @param validation the validation.
     */
    public
    InvalidMusicXMLException(
        final String message,
        final Validation validation
        ) {
        this(message, null, validation);
    }

    /**
     * Returns the validation.
     *
     * @return the validation.
     */
    public
    Validation getValidation() {
        return validation;
    }
}