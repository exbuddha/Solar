package instruments;

/**
 * {@code UprightPiano} classifies the most common form of an upright piano.
 * <p>
 * Upright piano doesn't provide access to the strings in performance.
 */
public abstract
class UprightPiano
extends Piano
{
    /**
     * Creates a upright piano instrument with the default first key note (A0), number of keys (88), and pedals. (soft, sostenuto, sustain)
     */
    public
    UprightPiano() {
        super(getDefaultFirstKeyNote(), getDefaultNumberOfKeys(), getDefaultPedalTypes());
    }
}