package system.data;

/**
 * {@code Contextual} classifies data types that offer functionality within their own scope by one or many contexts.
 * <p/>
 * The implementation of this class is a matter trusted to the role of the enclosing class and the logical language it supports.
 * Normally, there is one or a few instances of this class within the enclosing class, also called the provider class, and each one defines a set of methods that makes progressive chained calls possible.
 * Each chain method can either return the same contextual instance after applying its own contextual meaning to the context type returned by the instance, or it can return a brand new contextual object.
 * At the bottom of the chain call a subject method is called which will provide the end result using the available context carried up to that point.
 *
 * @param <T> the context type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Contextual<T extends Context>
implements Context
{
    /**
     * Returns the context set by this contextual instance.
     *
     * @return the context.
     */
    protected abstract
    T getContext();
}