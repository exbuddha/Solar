package system.data;

/**
 * {@code Format} is the super-type for all data formats in the system.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Format
{
    /**
     * Returns an intermediary domain type capable of performing search on the domain.
     *
     * @return the intermediary searchable domain type.
     */
    Hierarchical.Domain search();

    /**
     * {@code Hierarchical} classifies structures that are constructed, or nested, in hierarchical manner.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Hierarchical
    extends
        Format,
        system.data.Ordered
    {
        /**
         * {@code Domain} classifies arbitrarily located collections of data points within hierarchical structures.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Domain
        extends Hierarchical
        {
            /**
             * Returns a domain type set to start according to the specified coordinates of this domain type.
             *
             * @param start the starting point coordinates.
             *
             * @return the domain type.
             */
            Domain from(
                Number... start
                );

            /**
             * Returns a domain type set to end according to the specified coordinates of this domain type.
             *
             * @param end the ending point coordinates.
             *
             * @return the domain type.
             */
            Domain until(
                Number... end
                );
        }
    }

    /**
     * {@code Interpretable} classifies sequences of characters that are logically interpretable.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Interpretable
    extends CharSequence
    {
        /**
         * {@code Document} represents all textual data documents.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        abstract
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

            /**
             * {@inheritDoc}
             * <p/>
             * This implementation calls {@link #getCharSequence()} internally.
             *
             * @param index the character index.
             *
             * @return the character.
             */
            @Override
            public char charAt(final int index) {
                return getCharSequence().charAt(index);
            }

            /**
             * {@inheritDoc}
             * <p/>
             * This implementation calls {@link #getCharSequence()} internally.
             *
             * @return the character sequence length.
             */
            @Override
            public int length() {
                return getCharSequence().length();
            }

            /**
             * {@inheritDoc}
             * <p/>
             * This implementation calls {@link #getCharSequence()} internally.
             *
             * @param start the subsequence start index. (inclusive)
             * @param start the subsequence end index. (exclusive)
             *
             * @return the character subsequence.
             */
            @Override
            public CharSequence subSequence(final int start, final int end) {
                return getCharSequence().subSequence(start, end);
            }

            /**
             * {@inheritDoc}
             * <p/>
             * This implementation calls {@link #getCharSequence()} internally.
             *
             * @return the character sequence as string.
             */
            @Override
            public String toString() {
                return getCharSequence().toString();
            }

            /**
             * {@code Element} classifies all document elements.
             * <p/>
             * Document elements are the uniquely recognized atomic units in documents that are meaningful in logical contexts.
             * They are commonly associated with all data types within a specific documents.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public
            interface Element
            {}

            /**
             * {@code ElementPart} classifies all document element parts.
             * <p/>
             * A document element part can be any arbitrary section of an element that can stand alone as a recognized piece in that element.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public
            interface ElementPart
            {}

            /**
             * {@code Filter} represents data types that are constructed in order to filter out parts of documents during traversal.
             * <p/>
             * Filters are structures that are in charge of determining whether or not a document part is eligible for the application of filtering.
             * If a filter is designed to use the nested {@link Condition} class it must provide functionality to bind document data to appropriate match conditions.
             * <p/>
             * Using document filters implies the need for an additional layer of abstraction around the logic for parsing or post-processing documents and their elements.
             * It is possible to apply filtering in a more efficient way; therefore, this class should be used only when there is a requirement to represent such units of logic in a object-oriented design that allows maintaining the state of filters and their match conditions.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Filter
            {
                /**
                 * {@code Condition} represents conditional match-based data that are expressed as boolean predicates.
                 *
                 * @since 1.8
                 * @author Alireza Kamran
                 */
                public static abstract
                class Condition
                implements
                    java.util.function.BiPredicate<Match, Boolean>,
                    Conditional
                {
                    /**
                     * Evaluates that the condition has the specified polarity towards the specified criteria.
                     * <p/>
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
                     *
                     * @return the result.
                     */
                    public
                    boolean test(
                        final Match criteria
                        ) {
                        return test(criteria, true);
                    }
                }

                /**
                 * {@code Conditional} classifies data types that logically define the criteria for expressing document filters.
                 *
                 * @since 1.8
                 * @author Alireza Kamran
                 */
                public
                interface Conditional
                {
                    /**
                     * {@code Operator} classifies conditional data types that are composed of one of many conditional data types.
                     *
                     * @since 1.8
                     * @author Alireza Kamran
                     */
                    interface Operator
                    extends Conditional
                    {
                        /**
                         * Returns the list of conditionals that comprise the operator.
                         *
                         * @return the list of conditionals.
                         */
                        java.util.List<Conditional> getConditions();
                    }
                }

                /**
                 * {@code Predictive} classifies conditional data types that are predictive with respect to a target element, or elements, in document.
                 * <p/>
                 * Predictive conditionals require performing some extra work outside of the logically discerned boundary of their target element.
                 * This is due to the fact that, by design, predictive conditionals rely on data for which the traversal (search) algorithm will not have a record by default at the time of visiting the target element.
                 */
                public
                interface Predictive
                extends Conditional
                {}

                /**
                 * {@code Expression} represents conditional operators as boolean predicates.
                 *
                 * @since 1.8
                 * @author Alireza Kamran
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
                     *
                     * @since 1.8
                     * @author Alireza Kamran
                     */
                    public static abstract
                    class And
                    implements Conditional.Operator
                    {}

                    /**
                     * {@code Or} represents the logical 'or' operator in conditional expressions.
                     *
                     * @since 1.8
                     * @author Alireza Kamran
                     */
                    public static abstract
                    class Or
                    implements Conditional.Operator
                    {}
                }

                /**
                 * {@code Match} represents all data types that make up conditions in expressions.
                 *
                 * @since 1.8
                 * @author Alireza Kamran
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

                    /**
                     * {@code Result} represents all condition match results.
                     *
                     * @since 1.8
                     * @author Alireza Kamran
                     */
                    public abstract
                    class Result
                    extends Match
                    implements java.util.function.UnaryOperator<Boolean>
                    {
                        /**
                         * Attempts to force the condition match result in data to reflect the specified polarity.
                         * <p/>
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
            }

            /**
             * {@code Handler} classifies functional interfaces for processing entire, or parts of, documents via parser handling methodologies.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public
            interface Handler
            {
                /**
                 * Returns the handler document.
                 *
                 * @return the document.
                 */
                Object getDocument();

                /**
                 * Returns true if the handler is closed and accepts no input; otherwise returns false.
                 *
                 * @return true if the handler is closed, and false otherwise.
                 */
                boolean isClosed();
            }
        }
    }

    /**
     * {@code Sequential} classifies individually identified data elements within larger scopes providing wrapper functionality or chained call capability.
     *
     * @param <T> the data element type.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Sequential<T>
    {
        /**
         * Returns a sequential type after applying the specified coordinates to this sequential type.
         *
         * @param coords the coordinates. (data element)
         *
         * @return the located sequential type.
         */
        Sequential<T> at(
            T coords
            );
    }

    /**
     * {@code Writable} classifies data types that can be written to a secondary medium such as disk or memory.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Writable
    {
        /**
         * Writes the content to the output stream.
         *
         * @param outputStream the stream.
         * @throws Exception if an error occurs.
         */
        void write(
            java.io.OutputStream outputStream
            )
        throws Exception;
    }
}