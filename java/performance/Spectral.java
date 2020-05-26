package performance;

/**
 * {@code Spectral} classifies all visible colors.
 */
public
interface Spectral
{
    /**
     * Decomposes the color into iterable of color codes.
     *
     * @return the decomposed color codes.
     */
    public
    Iterable<? extends Short> decompose();

    /**
     * {@code Filter} classifies spectral types that filter other similar types.
     */
    public
    interface Filter
    extends Spectral
    {
        /**
         * Filters the specified color codes into an iterable of color types.
         *
         * @param codes the color codes.
         * @return the filtered color types.
         */
        public
        Iterable<? extends performance.system.Type<? extends Spectral>> filter(
            Number... codes
            );
    }
}