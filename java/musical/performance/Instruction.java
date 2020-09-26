package musical.performance;

/**
 * {@code Instruction} is an intermediate data type created by a performer's ASM logic to translate an instance, or multiple ones, in the score part (or parts) into a set of physical interactions appropriate for making the necessary changes in the aural field according to the needs of the score.
 * <p/>
 * Conceptually, it needs to be capable of maintaining multiple many-to-many relationships across snapshot units in performance.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Instruction
implements
    Production<Group>,
    musical.performance.system.Type<Instruction>
{}