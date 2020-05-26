package system.data;

/**
 * {@code Reflective} classifies all matrix operations.
 */
public
interface Reflective
{
    public static final
    Object[] Ambiguity = null;

    public static final
    Object[] Empty = new Object[] {};

    /**
     * Returns the transposed coordinates of the specified coordinates cropped by the sub-coordination.
     *
     * @param offset the starting row.
     * @param dimension the number of rows.
     * @param start the starting column.
     * @param end the ending column.
     * @param coords the coordinates.
     * @return the transposed coordinates.
     */
    public static
    Object[] transpose(
        final int offset,
        final int dimension,
        final int start,
        final int end,
        final Object... coords
        ) {
        if (coords.length == 0)
            return Empty;

        Object[] transposed = Ambiguity;
        if (coords[0].getClass().isArray()) {
            // one- or multi-dimensional matrix
            transposed = new Object[dimension];
            for (int i = offset; i < offset + dimension; i++) {
                transposed[i] = new Object[end - start];
                for (int j = start; j < end; ((Object[]) transposed[i])[j] = ((Object[]) coords[j++])[i]);
            }
        }
        else {
            // one-dimentional matrix
            transposed = new Object[] { new Object[coords.length] };
            for (int j = 0; j < coords.length; ((Object[]) transposed[0])[j] = coords[j++])
                if (coords[j].getClass().isArray()) {
                    transposed = Ambiguity;
                    break;
                }
        }

        return transposed;
    }

    public static
    Object[] transpose(
        final int offset,
        final int dimension,
        final Object... coords
        ) {
        if (coords.length == 0)
            return Empty;

        return transpose(offset, dimension, 0, coords.length, coords);
    }

    public static
    Object[] transpose(
        final int dimension,
        final Object... coords
        ) {
        if (coords.length == 0)
            return Empty;

        return transpose(0, dimension, coords);
    }

    public static
    Object[] transpose(
        final Object... coords
        ) {
        if (coords.length == 0)
            return Empty;

        return transpose(((Object[]) coords[0]).length, coords);
    }

    /**
     * Transforms the specified coordinates.
     *
     * @param coords the coordinates.
     * @return the transformed coordinates.
     */
    public
    Object[] transform(
        Object... coords
        );

    public
    interface Translation
    extends Reflective
    {
        @Override
        public default
        Object[] transform(final Object... coords) {
            if (coords.length == 0)
                return Empty;

            Object[] translated = new Object[coords.length];
            for (int i = 0; i < coords.length; i++)
                if (coords[i].getClass().isArray()) {
                    translated[i] = new Object[((Object[]) coords[i]).length];
                    for (int j = 0; j < ((Object[]) coords[i]).length; ((Object[]) translated[i])[j] = translate(((Object[]) coords[i])[j++]));
                }
                else
                    translated[i] = translate(coords[i]);

            return translated;
        }

        public abstract
        Object translate(
            final Object coord
            );
    }

    public
    interface Transposition
    extends Reflective
    {
        @Override
        public default
        Object[] transform(final Object... coords) {
            return transpose(coords);
        }
    }
}