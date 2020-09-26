package musical.instruments;

import java.util.Arrays;

/**
 * {@code ElectricPiano} classifies the most common form of an electric piano instrument.
 * <p/>
 * An electric piano only has a sustain pedal and does not have, or provide, access to the strings.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
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
        super(getDefaultFirstKeyNote(), getDefaultNumberOfKeys(), Arrays.asList(musical.performance.Constant.Piano.SustainPedalName));
    }
}