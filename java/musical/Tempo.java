package musical;

/**
 * {@code Tempo} classifies all tempo types in musical data.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Tempo
{
    /**
     * Returns the beat unit of the tempo.
     *
     * @return the beat unit.
     */
    public
    Duration beat();

    /**
     * Returns the number of beats per minute.
     *
     * @return the beats per minute.
     */
    public
    short bpm();
}