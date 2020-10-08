package system.data;

import java.util.function.Supplier;

/**
 * {@code Cache} classifies addressable containers of any data that are themselves retrievable by type information.
 * <p/>
 * This class declares high-level methods for loading from and retrieving from arbitrarily complex addresses.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Cache
{
    /**
     * Loads the cache from the specified address.
     * <p/>
     * The address in this context refers to external resources where the cache is populated from.
     *
     * @param address the address.
     */
    public
    void load(
        Object... address
        );

    /**
     * Retrieves cached data with the specified type from the cache.
     *
     * @param type the data type.
     * @return the cached data.
     */
    public
    Data<?> retrieve(
        Object... type
        );

    /**
     * {@code Data} classifies cache data by an arbitrary type.
     * <p/>
     * This class implementation is in progress.
     *
     * @param <T> the data type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Data<T>
    extends Supplier<T>
    {
        /**
         * Returns the data class.
         *
         * @return the data class.
         */
        public
        Class<? extends T> getType();
    }

    /**
     * {@code Table} is a boolean n-tree dictionary used for string key matching.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    class Table
    extends Dictionary<Boolean>
    {
        /**
         * Creates a dictionary with the specified keys.
         *
         * @param keys the keys.
         */
        public
        Table(
            final String... keys
            ) {
            super();
            if (keys.length > 0)
                map(keys).to(true);
        }

        /**
         * Adds the key to the dictionary.
         *
         * @param key the key.
         */
        public
        void add(
            final String key
            ) {
            add(key, 0, root, true);
        }

        /**
         * Returns true if the dictionary contains the specified key, and false otherwise.
         *
         * @param key the key.
         * @return true if the key is in the dictionary, and false otherwise.
         */
        public
        boolean contains(
            final String key
            ) {
            return find(key, 0, root, false) != null;
        }
    }
}