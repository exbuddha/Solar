package performers.generic;

import performance.Awareness;
import performance.Instance;

/**
 * {@code BoardPlayer} is the base class for all performer types that deal with instruments that are performed by interacting with a board containing smaller parts in order to generate music.
 * <p>
 * Unless the subject instrument is an electronic one, this classification refers to instruments for which a resonating part is attached to the board that amplifies and projects sound.
 */
public
class BoardPlayer
extends Player
{
    /**
     * Creates a boarded instrument player.
     */
    protected
    BoardPlayer() {
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