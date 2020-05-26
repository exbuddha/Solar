package system;

/**
 * {@code Data} is the super-type for all data types.
 */
public
interface Data
{
    /**
     * {@code Hierarchical} classifies structures that are constructed, or nested, in hierarchical manner.
     */
    public
    interface Hierarchical
    extends Data
    {
        /**
         * {@code Domain} classifies arbitrarily located collections of data points within hierarchical structures.
         */
        public
        interface Domain
        extends Hierarchical
        {
            /**
             * Returns a domain type, set to start according to the specified coordinates of this domain type.
             *
             * @param start the start point coordinates.
             * @return the domain type.
             */
            public
            Domain from(
                Number... start
                );

            /**
             * Returns a domain type, set to end according to the specified coordinates of this domain type.
             *
             * @param end the end point coordinates.
             * @return the domain type.
             */
            public
            Domain until(
                Number... end
                );
        }
    }

    /**
     * {@code Interpretable} classifies sequences of characters that are logically interpretable.
     */
    public
    interface Interpretable
    extends CharSequence
    {
        /**
         * {@code Document} represents all textual data documents.
         */
        public abstract
        class Document
        implements
            Interpretable,
            Writable
        {
            /**
             * Returns the document character sequence.
             *
             * @return the document character sequence.
             */
            public abstract
            CharSequence getCharSequence();

            @Override
            public char charAt(final int index) {
                return getCharSequence().charAt(index);
            }

            @Override
            public int length() {
                return getCharSequence().length();
            }

            @Override
            public CharSequence subSequence(final int start, final int end) {
                return getCharSequence().subSequence(start, end);
            }

            @Override
            public String toString() {
                return getCharSequence().toString();
            }

            /**
             * {@code Filter} represents data types that are constructed in order to filter out parts of documents during traversal.
             * <p>
             * Filters are structures that are in charge of determining whether or not a document part is eligible for the application of filtering.
             * If a filter is designed to use the nested {@link Condition} class it must provide functionality to bind document data to appropriate match conditions.
             */
            public static abstract
            class Filter
            {
                /**
                 * {@code Condition} represents conditional match-based data that are expressed as boolean predicates.
                 */
                public static abstract
                class Condition
                implements
                    java.util.function.BiPredicate<Match, Boolean>,
                    Conditional
                {
                    /**
                     * Evaluates that the condition has the specified polarity towards the specified criteria.
                     * <p>
                     * By convention, true polarity indicates existence of match and false polarity indicates non-existence of match.
                     *
                     * @param criteria the criteria.
                     * @param polarity the polarity.
                     */
                    @Override
                    public abstract
                    boolean test(
                        Match criteria,
                        Boolean polarity
                        );

                    /**
                     * Evaluates that the condition is true for the specified criteria.
                     *
                     * @param criteria the criteria.
                     */
                    public
                    boolean test(
                        final Match criteria
                        ) {
                        return test(criteria, true);
                    }

                    /**
                     * {@code Result} represents all condition match results.
                     */
                    public static abstract
                    class Result
                    extends Match
                    implements java.util.function.UnaryOperator<Boolean>
                    {
                        /**
                         * Attempts to force the condition match result in data to reflect the specified polarity.
                         * <p>
                         * By convention, true polarity indicates existence of match and false polarity indicates non-existence of match.
                         *
                         * @param polarity the polarity.
                         * @return true if the attempt was successful, false otherwise.
                         */
                        @Override
                        public abstract
                        Boolean apply(
                            Boolean polarity
                            );
                    }
                }

                /**
                 * {@code Conditional} classifies data types that logically define the criteria for expressing document filters.
                 */
                public
                interface Conditional
                {
                    /**
                     * {@code Operator} classifies conditional data types that are composed of one of many conditional data types.
                     */
                    public
                    interface Operator
                    extends Conditional
                    {
                        /**
                         * Returns the list of conditionals that comprise the operator.
                         *
                         * @return the list of conditionals.
                         */
                        public
                        java.util.List<Conditional> getConditions();
                    }
                }

                /**
                 * {@code Predictive} classifies conditional data types that are predictive with respect to a target element, or elements, in document.
                 * <p>
                 * Predictive conditionals require performing some extra work outside of the logically discerned boundary of their target element.
                 * This is due to the fact that, by design, predictive conditionals rely on data for which the traversal (search) algorithm will not have a record by default at the time of visiting the target element.
                 */
                public
                interface Predictive
                extends Conditional
                {}

                /**
                 * {@code Expression} represents conditional operators as boolean predicates.
                 */
                public static abstract
                class Expression
                implements Conditional
                {
                    /** The root of expression tree. */
                    protected
                    Conditional root;

                    /**
                     * Returns the root conditional element in the expression tree.
                     *
                     * @return the root of expression.
                     */
                    public
                    Conditional getRoot() {
                        return root;
                    }

                    /**
                     * Sets the root conditional element in the expression tree.
                     *
                     * @param root the root element.
                     */
                    public
                    void setRoot(
                        final Conditional root
                        ) {
                        this.root = root;
                    }

                    /**
                     * {@code And} represents the logical 'and' operator in conditional expressions.
                     */
                    public static abstract
                    class And
                    implements Conditional.Operator
                    {}

                    /**
                     * {@code Or} represents the logical 'or' operator in conditional expressions.
                     */
                    public static abstract
                    class Or
                    implements Conditional.Operator
                    {}
                }

                /**
                 * {@code Match} represents all data types that make up conditions in expressions.
                 */
                public static abstract
                class Match
                {
                    /**
                     * Returns true if match is found and false otherwise.
                     *
                     * @param subjects the optional subjects, such as XML nodes or JSON elements.
                     * @return true if match is found and false otherwise.
                     */
                    public abstract
                    boolean holds(
                        Object... subjects
                        );
                }
            }

            /**
             * {@code Handler} classifies functional interfaces for processing entire, or parts of, documents via parser handling methodologies.
             */
            public
            interface Handler
            {}
        }
    }

    /**
     * {@code Sequential} classifies individually identified data elements within hierarchical documents providing wrapper functionality such as chain calls.
     *
     * @param <T> the data element type.
     */
    public
    interface Sequential<T>
    extends Hierarchical
    {
        /**
         * Returns a sequential type after applying the specified coordinates to this sequential type.
         *
         * @param coords the coordinates.
         * @return the located sequential type.
         */
        public
        Sequential<T> at(
            T coords
            );
    }

    /**
     * {@code Writable} classifies data types that can be written to a secondary medium such as disk or memory.
     */
    public
    interface Writable
    extends Data
    {
        /**
         * Writes the content to the output stream.
         *
         * @param outputStream the stream.
         * @throws Exception if an error occurs.
         */
        public abstract
        void write(
            java.io.OutputStream outputStream
            )
        throws Exception;
    }
}