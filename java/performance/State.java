package performance;

import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * {@code State} classifies the state of body or instrument parts.
 * <p>
 * This implementation enforces the use of reflection in a designed mechanism for classification of snapshot instances.
 */
public
interface State
extends performance.system.Type<State>
{
    /**
     * {@code Graph} classifies graph structures that define performance-specific states for parts in relation to many other instrument part types.
     * <p>
     * The aim of this interface is to provide sufficiently abstracted relational configurations among instrument parts in order to represent more well-defined forms of states with higher granularity.
     */
    public
    interface Graph
    extends State
    {
        public
        Iterable<Edge> getEdges();

        /**
         * {@code Edge} classifies edges of the state graph.
         */
        public
        interface Edge
        extends Supplier<State>
        {
            public
            Unit getA();

            public
            Unit getB();
        }
    }

    /**
     * {@code Machine} classifies predefined state machines as bi-functions that are in charge of translating performance states of instruments and their parts unto the same space where those states originate from.
     * <p>
     * These highly abstracted functional interfaces statically define the behavior of performance units that have physical states.
     */
    public
    interface Machine
    extends BiFunction<State, Class<? extends performance.system.Type<Interaction.Physical.Performance>>, State>
    {}
}