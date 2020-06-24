package system;

/**
 * {@code Constant} holds all system-related messages and constants.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public final
class Constant
{
    // Error messages
    static final String TaskNotFound = "Task not found.";

    // Error codes
    static final byte TaskConfigurationErrCode = (byte) 128;
    static final byte TaskInstantiationErrCode = (byte) 128;
    static final byte TaskNotFoundErrCode = (byte) 128;

    /**
     * {@code Observation} holds all commonly known names in the Java application system.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Observation
    {
        // Application commands and default package names
        static final String TaskCmd = "task";
        static final String SystemCmd = "system";
        static final String SystemTaskPkg = "system.task.";
    }
}