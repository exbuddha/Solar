package system.data;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * {@code Graph} classifies the graph data structure as a group of interconnected vertices and edges.
 * <p>
 * This class implementation is in progress.
 *
 * @param <V> the vertex type.
 * @param <E> the edge type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public abstract
class Graph<V, E>
{
    /** The graph vertices. */
    protected final
    Set<Vertex<V>> vertices;

    /** The graph edges. */
    protected final
    Set<Edge<E>> edges;

    /**
     * Creates a graph with the specified initial capacities and load factors for the two hash sets containing graph vertices and edges.
     * <p>
     * This constructor uses the {@link HashSet}.
     *
     * @param verticesInitialCapacity the initial capacity of the vertices hash set.
     * @param verticesLoadFactor the load factor of the vertices hash set.
     * @param edgesInitialCapacity the initial capacity of the edges hash set.
     * @param edgesLoadFactor the load factor of the edges hash set.
     * @throws IllegalArgumentException if an initial capacity is less than zero, or if a load factor is non-positive.
     */
    public
    Graph(
        final int verticesInitialCapacity,
        final float verticesLoadFactor,
        final int edgesInitialCapacity,
        final float edgesLoadFactor
        ) {
        vertices = new HashSet<Vertex<V>>(verticesInitialCapacity, verticesLoadFactor);
        edges = new HashSet<Edge<E>>(edgesInitialCapacity, edgesLoadFactor);
    }

    /**
     * Creates a graph with the specified initial capacity and load factor for both hash sets containing graph vertices and edges.
     * <p>
     * This constructor uses the {@link HashSet}.
     *
     * @param initialCapacity the initial capacity of both vertices and edges hash set.
     * @param loadFactor the load factor of both vertices and edges hash set.
     * @throws IllegalArgumentException if the initial capacity is less than zero, or if the load factor is non-positive.
     */
    public
    Graph(
        final int initialCapacity,
        final float loadFactor
        ) {
        this(initialCapacity, loadFactor, initialCapacity, loadFactor);
    }

    /**
     * Creates an empty graph with load factor 1.
     * <p>
     * This constructor uses the {@link HashSet}.
     */
    public
    Graph() {
        this(0, 1F);
    }

    /**
     * {@code Edge} classifies an edge in graph data structure that connects vertices and carries a data object.
     * <p>
     * This class implementation is in progress.
     *
     * @param <T> the data object type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class Edge<T>
    implements system.data.Type<Edge<?>>
    {
        /** The connected vertex. */
        protected
        Vertex<?> a, b;

        /** The data. */
        protected
        T data;

        /**
         * Returns vertex A.
         *
         * @return vertex A.
         */
        public
        Vertex<?> getA() {
            return a;
        }

        /**
         * Returns vertex B.
         *
         * @return vertex B.
         */
        public
        Vertex<?> getB() {
            return b;
        }

        /**
         * Returns the data associated with the edge.
         *
         * @return the data.
         */
        public
        T getData() {
            return data;
        }

        /**
         * Sets vertex A.
         *
         * @param a vertex A.
         */
        public
        void setA(
            final Vertex<?> a
            ) {
            this.a = a;
        }

        /**
         * Sets vertex B.
         *
         * @param b vertex B.
         */
        public
        void setB(
            final Vertex<?> b
            ) {
            this.b = b;
        }

        /**
         * Sets the data associated with the edge.
         *
         * @param data the data.
         */
        public
        void setData(
            final T data
            ) {
            this.data = data;
        }
    }

    /**
     * {@code Edge} classifies entries of the graph as {@link Map.Entry} types.
     * <p>
     * This class implementation is in progress.
     *
     * @param <V> the vertex type.
     * @param <E> the edge type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Entry<V, E>
    extends Map.Entry<V, Set<E>>
    {
        /**
         * Returns the edge type.
         *
         * @return the edge type.
         */
        public
        Class<? extends Edge<?>> getEdgeType();

        /**
         * Returns the vertex type.
         *
         * @return the vertex type.
         */
        public
        Class<? extends Vertex<?>> getVertexType();
    }

    /**
     * {@code Vertex} classifies a vertex in graph data structure with an attached object.
     * <p>
     * This class implementation is in progress.
     *
     * @param <T> the object type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class Vertex<T>
    implements system.data.Type<Vertex<?>>
    {
        /** The object. */
        protected
        T object;

        /**
         * Returns the object attached to the vertex.
         *
         * @return the object.
         */
        public
        T getObject() {
            return object;
        }

        /**
         * Sets the object attached to the vertex.
         *
         * @param object the object.
         */
        public
        void setObject(
            final T object
            ) {
            this.object = object;
        }
    }
}