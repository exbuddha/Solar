package musical;

import java.util.Comparator;

/**
 * {@code Symbolized} classifies data types that can be symbolized by comparable types.
 *
 * @param <T> the symbol type.
 */
public
interface Symbolized<T extends Comparable<T>>
extends Comparator<Symbolized<T>>
{
    @Override
    public default int compare(final Symbolized<T> o1, final Symbolized<T> o2) {
        return o1 == o2
               ? 0
               : o1 == null
                 ? (o2 == null
                   ? 0
                   : -compare(o2, o1))
                 : (o2 == null
                   ? -1
                   : o1.getSymbol().compareTo(o2.getSymbol()));
    }

    /**
     * Returns the symbol of the data.
     *
     * @return the symbol.
     */
    public
    T getSymbol();

    /**
     * Sets the symbol of the data.
     *
     * @param symbol the symbol.
     */
    public
    void setSymbol(
        T symbol
        );

    /**
     * {@code Instance} is the default implementation of symbolized units in music.
     */
    public
    class Instance<T extends Symbolized<String>>
    implements
        Comparable<Symbolized<String>>,
        Symbolized<String>,
        system.Type<T>
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

        @Override
        public int compareTo(final Symbolized<String> obj) {
            return compare(this, obj);
        }

        @Override
        public String getSymbol() {
            return symbol;
        }

        @Override
        public boolean is(final system.Type<T> type) {
            return type instanceof Instance &&
                   compare(this, (Instance<T>) type) == 0;
        }

        @Override
        public void setSymbol(final String symbol) {
            this.symbol = symbol;
        }
    }
}