package system;

import java.util.function.Function;

/**
 * {@code Observation} contains the main executable.
 */
public
interface Observation
{
    /**
     * The main executable.
     * <p>
     * This implementation chain calls {@link #configure(String...)} using {@code args}, re-applies same input to the result, and runs the final runnable.
     *
     * @param args the command-line arguments.
     */
    public static void main(final String[] args) {
        configure(args)
        .apply(args)
        .run();
    }

    /**
     * Removes .java and .class from the end of the specified name.
     *
     * @param name the name.
     * @return the qualified class name.
     */
    public static
    String className(
        final String name
        ) {
        return name.endsWith(".java")
               ? name.substring(0, name.length() - 5)
               : (name.endsWith(".class")
                 ? name.substring(0, name.length() - 6)
                 : name);
    }

    /**
     * Configures the Java system.
     * <p>
     * This implementation returns {@link #system}.
     *
     * @param params command-line parameters.
     * @return the system generation function.
     */
    public static
    Function<String[], ? extends Runnable> configure(
        final String... params
        ) {
        if (Static.args == null)
            synchronized (Static.args) {
                if (Static.args == null)
                    Static.args = params;
            }

        return params.length == 0
               ? Static.Systematization
               : Static.Imagination;
    }

    /**
     * Returns the system runnable.
     *
     * @param args command-line arguments.
     * @return the system.
     */
    public static
    Runnable system(
        final String... args
        ) {
        return new Runnable()
        {
            @Override
            public void run() {
                if (args.length > 2 && args[1].equalsIgnoreCase("task")) {
                    final String[] inputs
                    = args.length > 3
                    ? new String[args.length - 3]
                    : null;

                    if (inputs != null)
                        for (int i = 3; i < args.length; i++)
                            inputs[i - 3] = args[i];

                    try {
                        ((Agent<String, Task>) Class.forName("system.task." + className(args[2])).newInstance())
                        .apply(inputs)
                        .run();
                    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    /**
     * {@code Agent} classifies functional interfaces that are self-maintained units of logic intended to be called at the highest level of execution.
     * These units of logic provide a setup method as preparation step similar to the traditional command-line entries that accept a list of arguments.
     *
     * @param <I> the argument type.
     * @param <O> the runnable type.
     */
    public
    interface Agent<I, O extends Runnable>
    extends Function<I[], O>
    {
        /**
         * Returns the array of arguments.
         *
         * @return the array of arguments.
         */
        public
        String[] getArguments();
    }

    /**
     * {@code Static} encapsulates the main threads available as system applications.
     */
    public
    enum Static
    implements
        Agent<String, Task>,
        Task
    {
        /** System-specific tasks. */
        Systematization
        ((final String[] args) -> Observation::system),

        /** Data-driven music performance tasks. */
        Imagination
        ((final String[] args)
        -> args.length > 0 &&
        args[0].equalsIgnoreCase("system")
        ? Systematization
        : new Task()
        {
            @Override
            public void run() {}
         }),

        /** Application and system migration tasks. */
        Migration
        ((String[] args) -> null),

        /** Application audio-intensive tasks. */
        Sonification
        ((final String[] args) -> new Task()
        {
            @Override
            public void run() {}
         }),

        /** Application video-intensive tasks. */
        Visualization
        ((final String[] args) -> new Task()
        {
            @Override
            public void run() {}
         });

        private static
        String[] args;

        // find out what operating system is running
        // find the corresponding .{Static.app}/{Static.init} file, containing full system-specific path to the runtime directory (Static.dir)
        // check directory exists

        public static
        String app = "solar";

        public static
        String init = "system.json";

        public
        String dir;

        private final
        Function<String[], Task> config;

        private
        Static(
            final Function<String[], Task> config
            ) {
            this.config = config;
        }

        Agent<String, Task> configure(
            final String... args
            ) {
            return new Agent<String, Task>()
            {
                Task task;

                @Override
                public Task apply(String[] dup) {
                    if (task == null)
                        synchronized (this) {
                            if (task == null)
                                task = config.apply(getArguments());
                        }

                    return task;
                }

                @Override
                public String[] getArguments() {
                    return args;
                }
            };
        }

        @Override
        public Task apply(String[] dup) {
            return config.apply(getArguments());
        }

        @Override
        public void run() {
            apply(null).run();
        }

        @Override
        public String[] getArguments() {
            return args;
        }
    }

    /**
     * {@code System} classifies complex processes that require to be adjusted to an underlying system or properly initialized by a model of execution at runtime.
     */
    public
    interface System
    extends Task
    {}

    /**
     * {@code Task} classifies a single unit of logic in the system.
     */
    public
    interface Task
    extends Runnable
    {}
}