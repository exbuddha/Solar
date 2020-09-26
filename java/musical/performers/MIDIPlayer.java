package musical.performers;

import musical.instruments.MusicalInstrument;

/**
 * {@code MIDIPlayer} is a representation of a standard MIDI player device.
 * <p/>
 * This base class exceptionally inherits the {@link MusicalInstrument} class and does not define a change graph.
 * The reason for this design decision is that generally all MIDI instruments are considered to be just electronic circuits and don't require to encapsulate any knowledge for human parts or their simulated interaction space.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class MIDIPlayer
extends MusicalInstrument
{
    @Override
    public <T extends Part> PartCreator<T> createPart(Class<T> partClass) { return null; }

    @Override
    public <T extends Part> PartFinder<T> findPart(Class<T> partClass) { return null; }

    @Override
    public <T extends Part> PartListFinder<T> findParts(Class<T> partClass) { return null; }

    /**
     * {@code Graph} classifies the state graph for the device's electronic parts or events.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected
    interface Graph
    extends State.Graph
    {}
}