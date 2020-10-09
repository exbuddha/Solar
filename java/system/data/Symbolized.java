package system.data;

import system.Type;

import static system.data.Constant.BaseImplementationRestricted;
import static system.data.Constant.StandardObjectInoperable;

import java.util.function.Function;

/**
 * {@code Symbolized} classifies data types that can be symbolized by comparable types.
 *
 * @param <T> the symbol type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Symbolized<T extends Comparable<T>>
{
    /**
     * Compares the two symbolized instances based on their symbol values and returns a negative integer if the first non-null symbol is less than the second symbol, zero if both symbols are equal, and a positive integer if the non-null first symbol is less than the second symbol.
     * <p/>
     * This implementation returns zero if both instances are null, and {@link Integer#MAX_VALUE} or -{@link Integer#MAX_VALUE} appropriately in cases where one instance is null and the other is non-null; otherwise, if both instances are non-null the value of {@link Comparable#compareTo(Object)} is returned.
     *
     * @param s1 the first symbolized instance.
     * @param s2 the second symbolized instance.
     *
     * @return a negative integer, zero, or a positive integer as this symbolized instance is less than, equal to, or greater than the specified object.
     */
    default
    int compareSymbols(final Symbolized<T> s1, final Symbolized<T> s2) {
        return s1 == null
               ? (s2 == null
                  ? 0
                  : -Integer.MAX_VALUE)
               : (s2 == null
                  ? Integer.MAX_VALUE
                  : s1.getSymbol().compareTo(s2.getSymbol()));
    }

    /**
     * Sets the symbol of the data.
     * <p/>
     * This implementation throws an {@code UnsupportedOperationException}.
     *
     * @param symbol the symbol.
     */
    default
    void setSymbol(
        T symbol
        ) {
        throw new UnsupportedOperationException(BaseImplementationRestricted);
    }

    /**
     * Returns true if the specified symbol is different from the symbol of this data; and false otherwise.
     * <p/>
     * Different symbols are those that are not mutually null and are not equal.
     * <p/>
     * This implementation relies on the {@link #equals(Object)} method to discern whether the two symbols are different or not.
     *
     * @param symbol the symbol.
     *
     * @return true if symbols are different, and false otherwise.
     */
    default
    boolean symbolIsDifferent(
        final T symbol
        ) {
        return Lambda.areNonNullAndNonEqual(symbol, getSymbol());
    }

    /**
     * Returns the symbol of the data.
     *
     * @return the symbol.
     */
    T getSymbol();

    /**
     * {@code Instance} is the default implementation of symbolized units in music.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    class Instance<T extends Symbolized<String>>
    implements
        Comparable<Symbolized<String>>,
        Symbolized<String>,
            Type<T>
    {
        /** The symbol. */
        protected
        String symbol;

        /**
         * Creates a symbolized instance with the specified string symbol.
         *
         * @param symbol the symbol.
         */
        public
        Instance(
            final String symbol
            ) {
            setSymbol(symbol);
        }

        /**
         * Compares this symbolized instance with the specified instance and returns a negative integer if this instance's symbol is less than the specified instance's symbol, zero if they are equal, and a positive integer otherwise.
         *
         * @param obj the other symbolized instance.
         *
         * @return a negative integer, zero, or a positive integer as this symbolized instance is less than, equal to, or greater than the specified instance.
         *
         * @see #compareSymbols(Symbolized, Symbolized)
         */
        @Override
        public int compareTo(final Symbolized<String> obj) {
            return compareSymbols(this, obj);
        }

        /**
         * Returns true if the specified symbolized type has the same symbol as this instance; otherwise returns false.
         *
         * @param type the other data type.
         *
         * @return if the symbols are the same, and false otherwise.
         *
         * @see #compareSymbols(Symbolized, Symbolized)
         */
        @Override
        public boolean is(final system.Type<? super T> type) {
            return type instanceof Instance && compareSymbols(this, (Instance<T>) type) == 0;
        }

        /**
         * {@inheritDoc}
         *
         * @return the symbol.
         */
        @Override
        public String getSymbol() {
            return symbol;
        }

        /**
         * {@inheritDoc}
         *
         * @param symbol the symbol.
         */
        @Override
        public void setSymbol(final String symbol) {
            this.symbol = symbol;
        }
    }

    /**
     * {@code Singleton} classifies a symbolized data that does not allow its symbol to be set.
     *
     * @param <T> the symbol type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Singleton<T extends Comparable<T>>
    extends Symbolized<T>
    {
        /**
         * This implementation throws an {@code UnsupportedOperationException} unless the specified symbol is equal to the singleton symbol.
         *
         * @param symbol the symbol.
         *
         * @throws UnsupportedOperationException if the specified symbol is not equal to the singleton symbol.
         *
         * @see Symbolized#symbolIsDifferent(T)
         */
        @Override
        default void setSymbol(final T symbol) {
            if (symbolIsDifferent(symbol))
                throw new UnsupportedOperationException(StandardObjectInoperable);
        }
    }
}