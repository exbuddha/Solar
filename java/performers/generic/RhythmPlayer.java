package performers.generic;

import performance.Awareness;
import performance.Instance;

/**
 * {@code RhythmPlayer} is the base class for all performer types that deal with instruments that are performed in order to generate rhythm in music.
 * <p>
 * Unless the subject instrument is an electronic one, this classification refers to instruments for which a membrane is struck to project instantaneous, or negligibly instantaneous, sound.
 * <p>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class RhythmPlayer
extends Player
{
    /**
     * Creates a rhythm instrument player.
     */
    protected
    RhythmPlayer() {
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