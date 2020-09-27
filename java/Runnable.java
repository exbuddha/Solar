/**
 * {@code Runnable} contains the main executable.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Runnable
extends java.lang.Runnable
{
    /**
     * The main executable.
     *
     * @param args the command-line arguments.
     */
    static void main(final String... args) {
        system.Observation.execute(args);
    }

    /**
     * Runs the main executable thread.
     */
    @Override
    default void run() {
        main();
    }
}