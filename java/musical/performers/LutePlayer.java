package musical.performers;

import java.util.Collection;

import musical.instruments.Lute;
import musical.Score.Interpreter;
import musical.performance.Performer;

/**
 * {@code LutePlayer} is a representation of a performer who has knowledge of performing a lute instrument.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class LutePlayer<I extends Lute>
extends Performer<I>
{
    /**
     * Creates a lute player with the specified body parts, lute instrument, and interpreter.
     *
     * @param bodyParts the body parts collection.
     * @param instrument the lute instrument.
     * @param interpreter the interpreter.
     */
    protected
    LutePlayer(
        final Collection<Class<? extends BodyPart>> bodyParts,
        final I instrument,
        final Interpreter interpreter
        ) {
        super(bodyParts, instrument, interpreter);
    }

    /**
     * {@code ChangeGraph} is a representation of lute player's performance knowledge.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class ChangeGraph
    extends Performer.ChangeGraph
    {}

    /**
     * Groups right-oriented interaction classes for all lute players.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    protected abstract
    class Right
    {}
}