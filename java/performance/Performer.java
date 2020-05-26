package performance;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import musical.Score;
import system.data.Graph;

/**
 * {@code Performer} classifies a human that can make decisions about what interactions to perform on a musical instrument in order to produce certain changes in the aural field.
 * It is the superclass for all instrument-specific performer classes that are in charge of providing knowledge, regarding their respective instrument, to the ASM in order to facilitate branching out and narrowing down the performance execution graph.
 * <p>
 * All concrete subclasses of the {@code Performer} class must declare a constructor that takes two parameters: an {@link Instrument} and an {@link Score.Interpreter} type.
 *
 * @param <I> the instrument type.
 */
public abstract
class Performer<I extends Unit>
extends Human
implements
    Consumer<Instance>,
    Predicate<Instance>
{
    /** The instrument. */
    protected final
    I instrument;

    /** The interpreter. */
    protected final
    Score.Interpreter interpreter;

    /** The performance style. */
    protected
    Interaction.Physical.Performance.Style<?> style;

    /** The change graph. */
    protected
    ChangeGraph changeGraph;

    /**
     * Creates a performer with the specified body part orientation map, instrument, and interpreter.
     * <p>
     * If a body part class is non-orientational, it is created regardless of the orientations it maps to.
     * Otherwise, if the mapped collection is null or empty, both orientations will be created; and if it is not empty, only the specified orientations will be created.
     *
     * @param bodyPartOrientationMap the map of body part classes to their orientation.
     * @param instrument the instrument.
     * @param interpreter the interpreter.
     */
    protected
    Performer(
        final Map<Class<? extends BodyPart>, Collection<Orientation>> bodyPartOrientationMap,
        final I instrument,
        final Score.Interpreter interpreter
        ) {
        super(bodyPartOrientationMap);
        this.instrument = instrument;
        this.interpreter = interpreter;
    }

    /**
     * Creates a performer with the specified body part classes, instrument, and interpreter.
     * <p>
     * If a body part class is orientational both orientations will be created.
     * If a body part class is null it will be ignored.
     *
     * @param bodyParts the collection of body part classes.
     * @param instrument the instrument.
     * @param interpreter the interpreter.
     */
    protected
    Performer(
        final Collection<Class<? extends BodyPart>> bodyParts,
        final I instrument,
        final Score.Interpreter interpreter
        ) {
        super(bodyParts);
        this.instrument = instrument;
        this.interpreter = interpreter;
    }

    @Override
    public boolean is(final system.Type<Instrument> type) {
        return type instanceof Performer;
    }

    /**
     * Returns an iterable of instruction types that provide all performance possibilities for the specified event in music given the situation depicted by the specified snapshot instance.
     *
     * @param situation the snapshot instance.
     * @param event the event.
     * @return the iterable of instructions.
     */
    public abstract
    Iterable<? extends Instruction> perform(Snapshot.Instance<?> situation, Interpreted event);

    /**
     * Returns the change graph.
     *
     * @return the change graph.
     */
    public
    ChangeGraph getChangeGraph() {
        return changeGraph;
    }

    /**
     * Returns the interpreter.
     *
     * @return the interpreter.
     */
    public
    Score.Interpreter getInterpreter() {
        return interpreter;
    }

    /**
     * Sets the change graph.
     *
     * @param changeGraph the change graph.
     */
    public
    void setChangeGraph(
        final ChangeGraph changeGraph
        ) {
        this.changeGraph = changeGraph;
    }

    /**
     * Sets the performance style.
     *
     * @param style the performance style.
     */
    public
    void setStyle(
        final Interaction.Physical.Performance.Style<?> style
        ) {
        this.style = style;
    }

    /**
     * {@code ASM} classifies performer's awareness of physical interactions that make up a performance graph.
     */
    protected static abstract
    class ASM
    extends Score.Interpreter
    implements Interpreted
    {
        /**
         * Creates an action selection model for the specified score.
         *
         * @param score the score.
         */
        public
        ASM(
            final Score score
            ) {
            score.super(score.findInterpreter().document, score.findInterpreter().index);
        }
    }

    /**
     * {@code ChangeGraph} is a data structure connecting interaction classes, and their relevant meta-data, to each other for making action selection possible during performance.
     */
    public static abstract
    class ChangeGraph
    extends Graph<Null, Production<? extends Unit>>
    {
        /**
         * Creates an empty change graph.
         */
        protected
        ChangeGraph() {
            super();
        }
    }

    /**
     * {@code Coordination} classifies performer's awareness of instrument-specific changes.
     *
     * @param <T> the graph data type.
     */
    public static abstract
    class Coordination<T extends Graph<?,?>>
    extends Instruction
    {
        /**
         * Returns the connective for the specified vertices in a change graph.
         *
         * @param vertices the vertices.
         * @return the connective.
         */
        public abstract
        Connective connective(
            T.Vertex<?>... vertices
            );

        /**
         * {@code Connective} classifies special data types that represent all or many connections, or disconnections, between vertices of graphs as a collection of existing, or non-existing, edges.
         * <p>
         * The aim of this interface is to provide a clean method for applying preferences to the main two types of graphs that each performer type handles in order to carry out the task of instruction generation.
         */
        public
        interface Connective
        extends
            Collection<Graph.Edge<?>>,
            Preference.Method
        {}
    }

    /**
     * {@code PerformanceGraph} is a data structure interconnecting instructions that collectively represent all performance possibilities for a score interpretation.
     */
    public abstract
    class PerformanceGraph
    extends Graph<Snapshot, Instruction>
    implements Performed
    {
        /**
         * Creates an empty performance graph.
         */
        protected
        PerformanceGraph() {
            super();
        }
    }

    public
    interface Performed
    extends
        system.Data.Sequential<Instance>,
        system.Unit
    {
        @Override
        public
        Performed at(
            Instance performance
            );
    }
}