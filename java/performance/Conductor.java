package performance;

import java.util.function.Consumer;
import java.util.function.Supplier;

import musical.Score;
import system.Data.Interpretable;

/**
 * {@code Conductor} represents an arbitrarily complex method of music conduction and defines a set of static members for organization of performance analysis.
 */
public abstract
class Conductor<T>
implements
    Consumer<T>,
    performance.system.Type<Conductor<? super Interpretable>>,
    system.Unit
{
    /**
     * Conducts a score.
     * <p>
     * By convention, this is the first method called in {@link ExecutionModel#run()}.
     *
     * @throws Exception if an error occurs.
     */
    protected abstract
    void conduct()
    throws Exception;

    /**
     * {@code Coordination} classifies conductor's awareness of performance-specific changes.
     */
    public static abstract
    class Coordination
    implements Production<Performer<?>>
    {}

    /**
     * {@code ExecutionModel} classifies the logical model for conducting a music score.
     */
    public static abstract
    class ExecutionModel
    extends Conductor<CharSequence>
    implements
        system.Observation.Task,
        system.Type.Null
    {
        /**
         * Ends the thread.
         * <p>
         * This method is called after {@link #error(Exception)} and before {@link #run()} exits with code 0.
         */
        protected abstract
        void end();

        /**
         * Processes an exception thrown in the thread.
         * <p>
         * This method is called after {@link #conduct()} throws an exception.
         *
         * @param e the exception.
         */
        protected abstract
        void error(
            Exception e
            );

        /**
         * Exits out of the thread with the specified code.
         * <p>
         * This implementation calls {@link System#exit(int)} internally.
         *
         * @param code the exit code.
         */
        protected
        void exit(
            final int code
            ) {
            System.exit(code);
        }

        /**
         * Exits out of the thread with code 0.
         * <p>
         * This is the last method called in {@link #run()}.
         * <p>
         * This implementation calls {@link #exit(int)} with code 0.
         */
        protected
        void exit() {
            exit(0);
        }

        @Override
        public boolean is(final system.Type<Conductor<? super Interpretable>> type) {
            return type instanceof ExecutionModel;
        }

        @Override
        public void run() {
            try {
                conduct();
            }
            catch (final Exception e) {
                error(e);
            }
            finally {
                end();
            }

            exit();
        }
    }

    /**
     * {@code Orchestration} represents a group of performers forming an interface to the complete timetable of their interactions in performance.
     * <p>
     * This superclass represents alternatively ordered views of the performance snapshots produced and maintained by instances of a {@code Performer} class's performance graph.
     * It can be considered as an organized product in a conducting model at the highest echelon of classification, capable of maintaining many groups of instances in a performance with multiple degrees of resolution.
     */
    public abstract
    class Orchestration
    extends Coordination
    implements Awareness
    {
        /**
         * {@code Section} represents orchestral sections defined as groups of instruments.
         */
        public abstract
        class Section
        implements Instrument.Grouping
        {}
    }

    /**
     * {@code Performance} classifies a complete conducting performance.
     * <p>
     * This superclass represents styles of performance maintained by instances of a {@code Performer} class's performance graph as suppliers of advancement routines for significant performer-instrument combinations.
     * It is the most self-sustained unit of intelligent data in performance.
     */
    protected static abstract
    class Performance
    extends Instruction
    implements
        Consumer<Score.Interpreter>,
        Supplier<Runnable>
    {
        public abstract
        class Score
        extends system.data.JSON.Score
        {}
    }
}