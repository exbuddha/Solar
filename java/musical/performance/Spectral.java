package musical.performance;

/**
 * {@code Spectral} classifies all visible colors.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
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
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
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
        Iterable<? extends musical.performance.system.Type<? extends Spectral>> filter(
            Number... codes
            );
    }
}