package performance;

/**
 * {@code Field} classifies the field of propagation for all instruments and their parts as an abstract layer of connection to the aural field. (space)
 * <P>
 * It defines an imaginary area, or surface, around the instrument marking the end point of purposeful choice for the performer type.
 */
public
interface Field
extends
    Group,
    Unit
{
    /**
     * {@code Propagation} represents the transference of sonic energy in the aural field.
     */
    public abstract
    class Propagation
    implements Production<Field>
    {}
}