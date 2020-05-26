package exceptions;

import org.xml.sax.SAXException;

import music.system.data.MusicXML;

/**
 * Signals that an exception occurred when parsing a MusicXML file, converting a document from partwise to timewise format, or processing the music data in the document.
 */
public
class InvalidMusicXMLException
extends SAXException
{
    /** The validation object. */
    protected final
    MusicXML.Validation validation;

    /**
     * Constructs an {@code InvalidMusicXMLException} with the specified detail message and original exception.
     *
     * @param message the message.
     * @param e the original exception.
     */
    public
    InvalidMusicXMLException(
        final String message,
        final Exception e
        ) {
        super(message, e);
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
     * Constructs an {@code InvalidMusicXMLException} with the specified detail message and validation object.
     *
     * @param message the message.
     * @param validation the validation object.
     */
    public
    InvalidMusicXMLException(
        final String message,
        final MusicXML.Validation validation
        ) {
        super(message);
        this.validation = validation;
    }

    /**
     * Returns the validation object.
     *
     * @return the validation object.
     */
    public
    MusicXML.Validation getValidation() {
        return validation;
    }
}