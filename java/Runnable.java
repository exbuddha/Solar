public interface Runnable extends java.lang.Runnable {
    @Override
    public default void run() {
        system.Observation.main(null);
    }
}