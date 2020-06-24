package performers.generic;

import java.util.Collection;

import instruments.MusicalInstrument;
import performance.Instruction;
import performance.Interpreted;
import performance.Performer;
import performance.Interaction.Physical.Performance;
import performance.system.Type;

/**
 * {@code Player} is the base class for all hub classes in this package that are in charge of selecting appropriate concrete classes and instantiating generic performer types per score needs.
 * <p>
 * The subclasses of this class, also known as hub classes, collectively represent an alternatively angled access point for the classification of musical instruments in the Hornbostel-Sachs method.
 * They act as designated interfaces that contain units of logic for finalizing the implementation of all performer types and for statically creating instances of those types at runtime.
 * An end user works directly with the hub classes to communicate to the underlying instrument of choice represented by that hub class.
 * This design eliminates the necessity for handling large amounts of existing corner cases in individual performer styles and the language of their instruments, hides implementation details, and brings all similar instrument types under one umbrella for seamless communication for generating performance instructions.
 * <p>
 * There are many forms of overlap existing among instrument types in this categorization due to the composite nature of instruments with regards the parts that consist their instrument body.
 * Whenever these forms of overlap occur the collision is resolved by taking into account the most fundamental aspects of interaction and part types for that specific instrument.
 * For example, the piano player and the mbira player are both categorized as board players but the dulcimer player is considered to be an string player because of the immediate connection that exists between the strings and the performer fingers during performance for the former two.
 * This class also support all electronic instruments that cannot clearly be given a category from the available hub classes in the package.
 * For such instruments, a wrapping functionality, named {@link #initialize()}, creates the electronic instrument with a known hub class type as an interface for accessing the features of the subject instrument.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Player
extends Performer<MusicalInstrument>
{
    /**
     * Creates a player for the specified instrument.
     *
     * @param instrument the instrument.
     */
    protected
    Player(
        final MusicalInstrument instrument
        ) {
        super((Collection<Class<? extends BodyPart>>) null, instrument, null);
    }

    @Override
    public performance.State apply(performance.State t, Class<? extends Type<Performance>> u) { return null; }

    @Override
    public Iterable<? extends Instruction> perform(performance.Snapshot.Instance<?> situation, Interpreted event) { return null; }

    /**
     * Initializes the instrument.
     */
    public abstract
    void initialize();
}