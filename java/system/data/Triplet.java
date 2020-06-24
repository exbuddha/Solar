package system.data;

/**
 * {@code Triplet} represents all triple data types in the system.
 * <p>
 * This class implementation is in progress.
 *
 * @param <A> the triplet's first data type.
 * @param <B> the triplet's second data type.
 * @param <C> the triplet's third data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class Triplet<A, B, C>
extends Tuple<A, B>
{
    public
    C c;

    public
    Triplet(
        A a,
        B b,
        C c
    ) {
        super(a, b);
        this.c = c;
    }
}