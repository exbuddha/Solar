package performers.generic;

import system.Data.Interpretable;

/**
 * {@code Conductor} is the base class for all generic conductor types dealing with performer orchestration and generation of performance data.
 * <p>
 * The concrete subclasses of this class represent and implement the knowledge of music conduction as a whole.
 */
public abstract
class Conductor
extends performance.Conductor<Interpretable>
{}