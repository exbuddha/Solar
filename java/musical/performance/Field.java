package musical.performance;

/**
 * {@code Field} classifies the field of propagation for all instruments and their parts as an abstract layer of connection to the aural field. (space)
 * <P>
 * It defines an imaginary area, or surface, around the instrument marking the end point of purposeful choice for the performer type.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
interface Field
extends
    Group,
    Unit
{
    /**
     * {@code Propagation} represents the transference of sonic energy to the aural field.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public abstract
    class Propagation
    implements Production<musical.Sound>
    {}
}