package instruments;

import java.util.Arrays;

/**
 * {@code ElectricPiano} classifies the most common form of an electric piano instrument.
 * <p>
 * Electric piano only has a sustain pedal and doesn't have, or provide, access to the strings.
 */
public abstract
class ElectricPiano
extends Piano
{
    /**
     * Creates an electric piano instrument with the default first key note (A0), number of keys (88), and a sustain pedal.
     */
    public
    ElectricPiano() {
        super(getDefaultFirstKeyNote(), getDefaultNumberOfKeys(), Arrays.asList(performance.Constant.Piano.SustainPedal));
    }
}