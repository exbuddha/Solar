package music.system.data;

public
interface Clockable<T>
extends
    Convertible<T>,
    Visualized
{
    interface Complex {}

    interface Definitive extends Elementary {}

    interface Element {}

    interface Elementary {}

    interface Pendular<T> extends Clockable<T> {}

    interface Rotational<S extends Regressor> extends Pendular<S> {}

    interface Progressive<S extends Progressor, R extends Progressor> extends Elementary, Complex {}

    interface Systematic<S extends Progressor, W extends System, R extends Progressor> extends Progressive<S, R>, Templative {}

    interface ProgressiveDataPoint<X extends Number, Y extends Progressor, Z extends Progressor>
    extends music.system.data.DataPoint<X, Y, Z>
    {}

    interface Progressor extends Element, Undefinitive {}

    interface Regressional<S extends Regressor, U, R extends Progressor> extends Pendular<U>, Regressive<S, R> {}

    interface Regressive<S extends Regressor, R extends Progressor> extends Elementary {}

    interface Regressor extends Progressor, Definitive {}

    interface Sinusoidal<T, S extends Progressor> extends Spiral<T> {}

    interface Spiral<T> extends Clockable<T> {}

    interface System {}

    interface Templator extends Progressor, Templative {}

    interface Templative {}

    interface TemplativeProgression<T, V, S extends Progressor> extends Sinusoidal<T, S> {}

    interface TemplativeRegression<T, V, S extends Regressor> extends TemplativeProgression<T, V, S> {}

    interface Undefinitive extends Elementary {}
}