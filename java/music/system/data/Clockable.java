package music.system.data;

/**
 * {@code Clockable} classifies all musical data types that are visualized and convertible on rotating axes and systems, such as the musical notes, scales, and chord systems.
 * <p>
 * This interface defines all major small- and large-scale data types that are in charge of transformation within clockable data systems.
 *
 * @param <T> the clockable data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Clockable<T>
extends
    Convertible<T>,
    Visualized
{
    /**
     * {@code Complex} is the defining operative for relations that are not elementary all the time.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Complex
    {}

    /**
     * {@code Definitive} is the defining operative for relations that are with no degrees of freedom.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Definitive
    extends Elementary
    {}

    /**
     * {@code Element} is the most basic unit of data in the domain of clockable systems.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Element
    {}

    /**
     * {@code Elementary} is the defining operative for relations that are in the most simplified form.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Elementary
    {}

    /**
     * {@code Pendular} is the attribute of clockable axes that resemble motion of pendulums.
     *
     * @param <T> the pendular data type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Pendular<T>
    extends Clockable<T>
    {}

    /**
     * {@code Progressive} is the defining operative for cognitions and conversions that are the most expansive with respect to their two-dimensional elements.
     *
     * @param <S> the first progressive type.
     * @param <R> the second progressive type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Progressive<S extends Progressor, R extends Progressor>
    extends
        Elementary,
        Complex
    {}

    /**
     * {@code } classifies all data points in clockable systems that can be ordered numerically on a progressive axis and also relate to another progressive data type.
     *
     * @param <X> the number type.
     * @param <Y> the first progressive data type.
     * @param <Z> the second progressive data type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface ProgressiveDataPoint<X extends Number, Y extends Progressor, Z extends Progressor>
    extends music.system.DataPoint<X, Y, Z>
    {}

    /**
     * {@code Progressor} is the next well-defined non-normalized bit of data in clockable systems after {@link Regressor} in line of thought.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Progressor
    extends
        Element,
        Undefinitive
    {}

    /**
     * {@code Regressional} classifies conversional operations that expansively accept a regressor and a free object.
     *
     * @param <S> the regressor data type.
     * @param <U> the pendular free data type.
     * @param <R> the well-known progressive data type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Regressional<S extends Regressor, U, R extends Progressor>
    extends
        Pendular<U>,
        Regressive<S, R>
    {}

    /**
     * {@code Regressive} is the defining operative for cognitions and conversions that are the most restrictive with respect to their two-dimensional elements.
     *
     * @param <S> the regressive data type.
     * @param <R> the progressive data type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Regressive<S extends Regressor, R extends Progressor>
    extends Elementary
    {}

    /**
     * {@code Regressor} is the simplest well-defined normalized bit of data in clockable systems.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Regressor
    extends
        Progressor,
        Definitive
    {}

    /**
     * {@code Rotational} classifies pendular axes of regressors, as simplified/linear systems that support restrictive data forms.
     *
     * @param <S> the restrictive pendular data type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Rotational<S extends Regressor>
    extends Pendular<S>
    {}

    /**
     * {@code Sinusoidal} is the attribute of spiral axes that resemble repetitive motions.
     *
     * @param <T> the spiral data type.
     * @param <S> the progressive data type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Sinusoidal<T, S extends Progressor>
    extends Spiral<T>
    {}

    /**
     * {@code Spiral} is the attribute of clockable axes that resemble motion of spirals.
     *
     * @param <T> the clockable data type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Spiral<T>
    extends Clockable<T>
    {}

    /**
     * {@code System} classifies a collectively meaningful system of elements with arbitrary degree of complexity.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface System
    {}

    /**
     * {@code Systematic} is the defining operative for cognitions and conversions that are system-based with respect to their progressive elements.
     *
     * @param <S> the progressive data type.
     * @param <W> the system type.
     * @param <R> the systematic data type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Systematic<S extends Progressor, W extends System, R extends Progressor>
    extends
        Progressive<S, R>,
        Templative
    {}

    /**
     * {@code Templative} is the defining operative for cognitions and conversions that are totally free with respect to their n-dimensional elements.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Templative
    {}

    /**
     * {@code TemplativeProgression} classifies tabular three-dimensional functional systems that are expansive in nature.
     *
     * @param <T> the clockable generic data type.
     * @param <V> the value type.
     * @param <S> the well-known progressive data type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface TemplativeProgression<T, V, S extends Progressor>
    extends Sinusoidal<T, S>
    {}

    /**
     * {@code TemplativeRegression} classifies tabular three-dimensional functional systems that are restrictive in nature.
     *
     * @param <T> the clockable generic data type.
     * @param <V> the value type.
     * @param <S> the well-known regressive data type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface TemplativeRegression<T, V, S extends Regressor>
    extends TemplativeProgression<T, V, S>
    {}

    /**
     * {@code Templator} is the most freely defined bit of data in clockable systems distinct from the two: {@link Regressor} and {@link Progressor}.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Templator
    extends
        Progressor,
        Templative
    {}

    /**
     * {@code Undefinitive} is the defining operative for relations that are with some degree of freedom.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Undefinitive
    extends Elementary
    {}
}