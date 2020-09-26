package musical.performers.generic;

import musical.performance.Awareness;
import musical.performance.Instance;

/**
 * {@code WindPlayer} is the base class for all performer types that deal with instruments that are performed by interacting with air in order to generate music.
 * <p/>
 * Unless the subject instrument is an electronic one, this classification refers to instruments for which a resonating tubular part is attached to the mouth piece, or that part that moves air, that amplifies and projects sound.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class WindPlayer
extends Player
{
    /**
     * Creates a wind instrument player.
     */
    protected
    WindPlayer() {
        super(null);
    }

    @Override
    public void accept(Instance t) {}

    @Override
    public boolean test(Instance t) { return false; }

    @Override
    public <T extends Part> PartCreator<T> createPart(Class<T> partClass) { return null; }

    @Override
    public <T extends Part> PartFinder<T> findPart(Class<T> partClass) { return null; }

    @Override
    public <T extends Part> PartListFinder<T> findParts(Class<T> partClass) { return null; }

    @Override
    protected <T extends Part> Class<? extends T> getDefaultConcreteSubclass(Class<T> partClass) { return null; }

    @Override
    public Awareness getOrchestration() { return null; }

    @Override
    public void initialize() {}
}