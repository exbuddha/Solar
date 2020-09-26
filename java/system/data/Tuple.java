package system.data;

/**
 * {@code Tuple} represents all tuple data types in the system.
 * <p/>
 * This class implementation is in progress.
 *
 * @param <A> the tuple's first data type.
 * @param <B> the tuple's second data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class Tuple<A, B>
{
    public
    A a;

    public
    B b;

    public
    Tuple(
        A a,
        B b
    ) {
        super();
        this.a = a;
        this.b = b;
    }
}