package system;

import static system.Constant.TaskConfigurationErrCode;
import static system.Constant.TaskInstantiationErrCode;
import static system.Constant.TaskNotFound;
import static system.Constant.TaskNotFoundErrCode;
import static system.Constant.Observation.SystemCmd;
import static system.Constant.Observation.SystemTaskPkg;
import static system.Constant.Observation.TaskCmd;

import java.util.Arrays;
import java.util.function.Function;

/**
 * {@code Observation} contains the main executable of the Java application and defines other related instances.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Observation
{
    /**
     * Runs the application.
     * <p/>
     * This implementation chain calls {@link #configure(String...)} using the specified command-line arguments, re-applies the same arguments to the returned task agent, and runs the task.
     *
     * @param args the command arguments.
     */
    static
    void execute(
        final String... args
        ) {
        configure(args)
        .apply(args)
        .run();
    }

    /**
     * Removes .java and .class from the end of the specified name.
     *
     * @param name the name.
     *
     * @return the qualified class name.
     */
    static
    String className(
        final String name
        ) {
        return name.endsWith(".java")
               ? name.substring(0, name.length() - 5)
               : name.endsWith(".class")
                 ? name.substring(0, name.length() - 6)
                 : name;
    }

    /**
     * Configures the Java application system with the specified parameters and returns the appropriate task based on application parameters.
     * <p/>
     * This implementation returns the {@link Static#Systematization} task agent (the output of {@link #system} method) if there are no command-line parameters available; otherwise it returns the {@link Static#Imagination} task agent.
     *
     * @param params command-line parameters.
     *
     * @return the application task agent.
     */
    static
    Function<String[], ? extends Runnable> configure(
        final String... params
        ) {
        if (Static.params == null)
            synchronized (Static.params) {
                if (Static.params == null)
                    Static.params = params;
            }

        return params.length == 0
               ? Static.Systematization
               : Static.Imagination;
    }

    /**
     * Returns the system task based on the provided command-line arguments.
     * <p/>
     * If the system task cannot be properly instantiated, the system task will exit with the {@link Constant#TaskInstantiationErrCode} error code.
     * If the system task cannot be properly configured, the system task will exit with the {@link Constant#TaskConfigurationErrCode} error code.
     * If the task is not found or the configuration step returns a null task, the system task will exit with the {@link Constant#TaskNotFoundErrCode} error code.
     *
     * @param args command-line arguments.
     *
     * @return the system.
     */
    static
    Runnable system(
        final String... args
        ) {
        return () -> {
            if (args.length > 2 && args[1].equalsIgnoreCase(TaskCmd)) {
                final String[] inputs = args.length > 3
                                        ? Arrays.copyOfRange(args, 3, args.length)
                                        : null;

                Agent<String, Task> config = null;
                try {
                    config = (Agent<String, Task>) Class.forName(args[2].startsWith(SystemTaskPkg) ? "" : SystemTaskPkg + className(args[2])).newInstance();
                }
                catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                    java.lang.System.exit(TaskInstantiationErrCode);
                }
                finally {
                    java.lang.System.runFinalization();
                }

                Task task = null;
                try {
                    task = config.apply(inputs);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    java.lang.System.exit(TaskConfigurationErrCode);
                }
                finally {
                    java.lang.System.runFinalization();
                }

                if (task == null) {
                    java.lang.System.out.println(TaskNotFound);
                    java.lang.System.runFinalization();
                    java.lang.System.exit(TaskNotFoundErrCode);
                }

                task.run();
            }
        };
    }

    /**
     * {@code Agent} classifies functional interfaces that are self-maintained units of logic intended to be called at the highest level of execution.
     * These units of logic, also called task agents, provide a setup method as configuration step similar to the traditional command-line entries that accept a list of arguments.
     * <p/>
     * This class implementation is in progress.
     *
     * @param <I> the parameter type.
     * @param <O> the runnable type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Agent<I, O extends Runnable>
    extends Function<I[], O>
    {
        /**
         * Returns the array of parameters.
         *
         * @return the parameters.
         */
        I[] getParameters();
    }

    /**
     * {@code Static} encapsulates the main threads available as system applications.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    enum Static
    implements
        Agent<String, Task>,
        System
    {
        /** System-specific tasks. */
        Systematization((final String[] args) -> Observation::system),

        /** Application data-intensive tasks. */
        Imagination((final String[] args) -> args.length > 0 && args[0].equalsIgnoreCase(SystemCmd)
                                             ? Systematization
                                             : () -> {}),

        /** Application and system migration tasks. */
        Migration((String[] args) -> () -> {}),

        /** Application audio-intensive tasks. */
        Sonification((final String[] args) -> () -> {}),

        /** Application video-intensive tasks. */
        Visualization((final String[] args) -> () -> {});

        /** The system parameters. */
        private static
        String[] params = new String[] {};

        // find out what operating system is running
        // validate application name and init file
        // find the corresponding .{Static.app}/{Static.init} file, containing full system-specific path to the runtime directory (Static.dir)
        // check directory exists

        /** The system application name. */
        public static
        String app = "solar";

        /** The system application initialization file. */
        public static
        String init = "system.json";

        /** The system application directory. */
        public
        String dir;

        /** The configuration task agent. */
        private final
        Function<String[], Task> config;

        /**
         * Creates a static system task agent with the specified configuration task agent.
         *
         * @param config the configuration task agent.
         */
        Static(
            final Function<String[], Task> config
            ) {
            this.config = config;
        }

        /**
         * Returns a new task agent from this configuration task agent that is reconfigured with the specified parameters.
         *
         * @param params the parameters.
         *
         * @return the task agent.
         */
        public
        Agent<String, Task> configure(
            final String... params
            ) {
            return new Agent<String, Task>() {
                Task task;

                @Override
                public Task apply(String[] dup) {
                    if (task == null)
                        synchronized (this) {
                            if (task == null)
                                task = config.apply(getParameters());
                        }

                    return task;
                }

                @Override
                public String[] getParameters() {
                    return params;
                }
            };
        }

        /**
         * Attempts to reconfigure the system task with the system parameters.
         *
         * @param dup the duplicate parameters. (never used internally)
         *
         * @return the reconfigured system task.
         */
        @Override
        public Task apply(String[] dup) {
            return config.apply(getParameters());
        }

        /**
         * Attempts to re-run the system task agent after reconfiguration with the system parameters.
         */
        @Override
        public void run() {
            apply(null).run();
        }

        /**
         * Returns the system parameters.
         *
         * @return the system parameters.
         */
        @Override
        public String[] getParameters() {
            return params;
        }
    }

    /**
     * {@code System} classifies complex processes that require to be adjusted to an underlying system or properly initialized by a model of execution at runtime.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface System
    extends Task
    {}

    /**
     * {@code Task} classifies a single unit of logic in the system.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Task
    extends Runnable
    {}
}