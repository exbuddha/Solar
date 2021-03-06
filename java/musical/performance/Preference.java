package musical.performance;

/**
 * {@code Preference} classifies a score or weight assignment method for interactions, techniques, or practices that is used by the action selection algorithm in a performance to narrow down the selection space and lighten the performance graph by pruning off the unwanted, impossible, or less feasible paths as the performance progresses over time.
 * <p/>
 * This task is achieved by descriptive pattern matching interfaces for working with sequences of desired and/or unwanted state types.
 * The descriptiveness here is relating to the predictive quality of the work involved in properly using the high level sub-types of this interface at suitable points during action selection.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Preference
extends
    Group,
    musical.performance.system.Type<Preference>,
    Unit
{
    /**
     * {@code Method} classifies all preference types that provide a method to be called reflectively in order to generate collections of graph connections, or connectives, which in turn can be applied to the change or the performance graph in order to carry out their work.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Method
    extends Preference
    {
        /**
         * Applies the specified method of the given preference type with the specified arguments and returns the resulting connective.
         *
         * @param preference the preference type.
         * @param method the method name.
         * @param args the method arguments.
         * @return the connective.
         */
        Performer.Coordination.Connective thru(
            musical.performance.system.Type<Preference> preference,
            String method,
            Object... args
            );
    }
}