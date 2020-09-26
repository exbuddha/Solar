package musical.performance;

import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * {@code State} classifies the state of body or instrument parts.
 * <p/>
 * This implementation enforces the use of reflection in a designed mechanism for classification of snapshot instances.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface State
extends musical.performance.system.Type<State>
{
    /**
     * {@code Graph} classifies graph structures that define performance-specific states for parts in relation to many other instrument part types.
     * <p/>
     * The aim of this interface is to provide sufficiently abstracted relational configurations among instrument parts in order to represent more well-defined forms of states with higher granularity.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Graph
    extends State
    {
        /**
         * Returns an iterable of edges for the state graph.
         *
         * @return the iterable of edges.
         */
        Iterable<Edge> getEdges();

        /**
         * {@code Edge} classifies edges of the state graph.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Edge
        extends Supplier<State>
        {
            /**
             * Returns the unit vertex A.
             *
             * @return the A vertex.
             */
            Unit getA();

            /**
             * Returns the unit vertex B.
             *
             * @return the B vertex.
             */
            Unit getB();
        }
    }

    /**
     * {@code Machine} classifies predefined state machines as bi-functions that are in charge of translating performance states of instruments and their parts unto the same space where those states originate from.
     * <p/>
     * These highly abstracted functional interfaces statically define the behavior of performance units that have physical states.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Machine
    extends BiFunction<State, Class<? extends musical.performance.system.Type<Interaction.Physical.Performance>>, State>
    {}
}