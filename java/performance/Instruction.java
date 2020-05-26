package performance;

/**
 * {@code Instruction} is an intermediate data type used in a performer's ASM logic to translate an instance in the score part (or parts) into a set of physical interactions appropriate for making the necessary changes in the aural field according to the needs of the score.
 * <p>
 * Conceptually, it needs to be capable of maintaining multiple many-to-many relationships across snapshot units in performance.
 */
public abstract
class Instruction
implements
    Production<Group>,
    performance.system.Type<Instruction>
{}