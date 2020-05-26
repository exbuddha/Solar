package performers;

import java.util.Collection;

import instruments.Lute;
import musical.Score.Interpreter;
import performance.Performer;

/**
 * {@code LutePlayer} is a representation of a performer who has knowledge of performing a lute instrument.
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
     */
    public abstract
    class ChangeGraph
    extends Performer.ChangeGraph
    {}

    /**
     * Groups right-oriented interaction classes for all lute players.
     */
    protected abstract
    class Right
    {}
}