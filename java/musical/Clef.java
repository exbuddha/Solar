package musical;

import system.data.Symbolized;
import system.Type;

/**
 * {@code Clef} represents a standard musical clef.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
enum Clef
implements
    Symbolized<String>,
    Type<Enum<Clef>>
{
    /** The standard clef. */
    Standard(),

    /** The C-clef. (Alto/Tenor) */
    C(Constant.Clef.C, Standard),

    /** The F-clef. (Bass) */
    F(Constant.Clef.F, Standard),

    /** The G-clef. (Treble) */
    G(Constant.Clef.G, Standard),

    /** The percussion clef. */
    Percussion(Constant.Clef.Percussion),

    /** The TAB clef. */
    TAB(Constant.Clef.TAB);

    /** The clef symbol. */
    private
    String symbol;

    /** The clef type. */
    private final
    Clef type;

    /**
     * Creates a clef of the specified symbol and type.
     *
     * @param symbol the clef symbol.
     * @param type the clef type.
     */
    Clef(
        final String symbol,
        final Clef type
        ) {
        this.symbol = symbol;
        this.type = type;
    }

    /**
     * Creates a clef with the specified symbol.
     *
     * @param symbol the symbol.
     */
    Clef(
        final String symbol
        ) {
        this(symbol, null);
    }

    /**
     * Creates a clef with null symbol and type.
     */
    Clef() {
        this(null, null);
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public boolean is(final Type<? extends Enum<Clef>> type) {
        if (type == null)
            return false;

        if (type == this)
            return true;

        return this.type != null && this.type.is(type);
    }

    @Override
    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }
}