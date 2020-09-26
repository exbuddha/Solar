package system.data;

import java.io.InputStream;
import java.io.OutputStream;

import exceptions.InvalidJSONException;
import musical.Notation;
import org.w3c.dom.Node;
import system.data.Format.Hierarchical;
import system.data.Format.Interpretable;

/**
 * {@code JSON} represents a JSON document.
 * <p/>
 * It also holds static members for generating JSON elements from an input stream or character sequence, writing elements to output streams, and traversing them.
 * The static methods in this class are thread-safe.
 * <p/>
 * This class implementation is in progress.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class JSON
extends Interpretable.Document
implements Hierarchical
{
    /** The JSON string. */
    private
    CharSequence string;

    /** The JSON object. */
    private
    Element object;

    /**
     * Creates a JSON object from the specified input stream.
     * <p/>
     * This implementation assumes that input stream is a standard JSON object string.
     *
     * @param stream the JSON input stream.
     */
    public
    JSON(
        final InputStream stream
        ) {
        super();
    }

    /**
     * Creates a JSON object from the specified input.
     * <p/>
     * This implementation assumes that input is a standard JSON object string.
     *
     * @param input the JSON string.
     */
    public
    JSON(
        final CharSequence input
        ) {
        super();
        string = input;
    }

    /**
     * Creates an empty JSON object.
     */
    public
    JSON() {
        super();
    }

    /**
     * Converts the specified scientific number to the smallest number type.
     *
     * @param n the scientific number.
     * @return the number type.
     */
    public static
    Number toNumber(
        final String n
        ) {
        return null;
    }

    /**
     * Converts the specified number to scientific number.
     *
     * @param n the number.
     * @return the scientific number.
     */
    public static
    String toScientific(
        final Number n
        ) {
        return null;
    }

    /**
     * Returns the element representing the JSON object.
     *
     * @return the JSON object.
     */
    public
    Element getObject() {
        return object;
    }

    /**
     * Reads the specified characters as JSON data performing syntactic evaluation.
     *
     * @param chars the characters.
     * @throws InvalidJSONException if an invalid character is encountered.
     */
    synchronized
    public
    void read(
        char... chars
        )
    throws InvalidJSONException {}

    /**
     * Writes the JSON document to the specified output stream using the indentation amount.
     *
     * @param outputStream the output stream.
     * @param indent indentation amount.
     */
    public
    void write(
        final OutputStream outputStream,
        final Byte indent
        ) {}

    @Override
    public void write(final OutputStream stream) {
        write(stream, null);
    }

    @Override
    public CharSequence getCharSequence() {
        return string == null ? "" : string;
    }

    @Override
    public Class<?> getOrderClass() {
        return Element.class;
    }

    @Override
    public Domain search() {
        return null;
    }

    @Override
    public String toString() {
        return getCharSequence().toString();
    }

    /**
     * {@code Array} classifies all JSON array elements.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Array
    extends Element
    {
        /**
         * Returns the array element at the specified index, or null if index is out of bounds.
         *
         * @param index the index.
         * @return the array element, or null if element doesn't exist.
         */
        default
        Element at(
            final int index
            ) {
            if (index > 0) {
                int i = 0;
                for (Element e : getElements())
                    if (i++ == index)
                        return e;
            }

            return null;
        }

        /**
         * Returns the list of elements in this array.
         *
         * @return the list of elements.
         */
        Iterable<Element> getElements();

        /** This implementation returns {@code Array}. */
        @Override
        default
        ValueType getValueType() {
            return ValueType.Array;
        }
    }

    /**
     * {@code Element} classifies all JSON data elements.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Element
    extends
        CharSequence,
        Interpretable.Document.Element
    {
        /**
         * Returns the start index of the element within JSON string.
         *
         * @return the element's start index.
         */
        int getStart();

        /**
         * Returns the end index of the element within JSON string.
         * <p/>
         * The element's end index is equal to the element's start index plus its length.
         *
         * @return the element's end index.
         */
        int getEnd();

        /**
         * Returns the element value type.
         *
         * @return the element value type.
         */
        ValueType getValueType();

        /**
         * {@code ValueType} categorizes the types of JSON data elements.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
        enum ValueType
        {
            /** JSON array value type. */
            Array,

            /** JSON double numeric value type. */
            Double,

            /** JSON 'false' value type. */
            False,

            /** JSON integer numeric value type. */
            Integer,

            /** JSON 'null' value type. */
            Null,

            /** JSON object value type. */
            Object,

            /** JSON scientific numeric value type. */
            Scientific,

            /** JSON string value type. */
            String,

            /** JSON 'true' value type. */
            True;

            /**
             * Returns true if the element type is boolean, and false otherwise.
             *
             * @return true if the element type is boolean, and false otherwise.
             */
            public
            boolean isBoolean() {
                return this == False ||
                       this == True;
            }

            /**
             * Returns true if the element type is numeric, and false otherwise.
             *
             * @return true if the element type is numeric, and false otherwise.
             */
            public
            boolean isNumeric() {
                return this == Double ||
                       this == Integer ||
                       this == Scientific;
            }

            /**
             * Returns true if the element type supports inner children, and false otherwise.
             *
             * @return true if the element type supports inner children, and false otherwise.
             */
            public
            boolean supportsChildren() {
                return this == Array ||
                       this == Object;
            }
        }
    }

    /**
     * {@code Filter} represents data types that are constructed in order to filter out parts of JSON documents during traversal.
     * <p/>
     * This class implementation is empty.
     *
     * @see Document.Filter
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class Filter
    extends Document.Filter
    {
        /**
         * {@code Match} represents all data types that make up the conditional expressions for matching elements within JSON documents.
         * <p/>
         * This class implementation is empty.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public static abstract
        class Match
        extends Document.Filter.Match
        {
            /**
             * {@code Ancestor} represents all match types that target JSON element ancestor conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Ancestor
            extends Match
            {}

            /**
             * {@code Child} represents all match types that target JSON element child conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Child
            extends Descendant
            {}

            /**
             * {@code Descendant} represents all match types that target JSON element descendant conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Descendant
            extends Match
            {}

            /**
             * {@code Depth} represents all match types that target JSON element depth conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Depth
            extends Element
            {}

            /**
             * {@code Element} represents all match types that target JSON element conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Element
            extends Match
            {}

            /**
             * {@code Index} represents all match types that target JSON element index conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Index
            extends Element
            {}

            /**
             * {@code Key} represents all match types that target JSON element key conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Key
            extends Element
            {}

            /**
             * {@code Parent} represents all match types that target JSON element parent conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Parent
            extends Ancestor
            {}

            /**
             * {@code Sibling} represents all match types that target JSON element sibling conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Sibling
            extends Match
            {}

            /**
             * {@code Type} represents all match types that target JSON element type conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Type
            extends Element
            {}

            /**
             * {@code Value} represents all match types that target JSON element value conditions.
             * <p/>
             * This class implementation is empty.
             *
             * @since 1.8
             * @author Alireza Kamran
             */
            public static abstract
            class Value
            extends Element
            {}
        }
    }

    /**
     * {@code Handler} represents callback handlers for processing all, or parts of, JSON documents during parsing.
     * <p/>
     * By design, the generic type of this class defines the handler's result element type.
     *
     * @param <T> the document node type.
     */
    public static abstract
    class Handler<T extends Element>
    implements Document.Handler
    {}

    /**
     * {@code Locator} classifies a simple lookup interface for finding individual JSON elements.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Locator
    {
        Element getElement();
    }

    /**
     * {@code Object} classifies all JSON object elements.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface Object
    extends Array
    {
        /**
         * Returns the object element at the specified index.
         *
         * @param index the index.
         * @return the object element.
         */
        default
        ObjectElement at(
            final int index
            ) {
            return (ObjectElement) Array.super.at(index);
        }

        /**
         * Returns the iterable of object elements with the specified key.
         *
         * @param key the key.
         * @return the iterable of object elements.
         */
        Iterable<Element> at(
            String key
            );

        /** This implementation returns {@code Object}. */
        @Override
        default
        ValueType getValueType() {
            return ValueType.Object;
        }
    }

    /**
     * {@code ObjectElement} classifies all JSON object key-value pairs.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface ObjectElement
    extends Element
    {
        /**
         * Returns the element key.
         *
         * @return the element key.
         */
        CharSequence key();

        /**
         * Returns the start index of the element key within JSON string.
         *
         * @return the element key's start index.
         */
        int keyStart();

        /**
         * Returns the end index of the element key within JSON string.
         * <p/>
         * The key's end index is equal to the key's start index plus its length.
         *
         * @return the element key's end index.
         */
        short keyEnd();

        /** This implementation returns null. */
        @Override
        default
        ValueType getValueType() {
            return null;
        }
    }

    /**
     * {@code Score} represents an experimental form of JSON object translated from and equivalent to a MusicXML score.
     * <p/>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public static abstract
    class Score
    extends JSON
    implements
        Notation,
        musical.performance.system.Type<Notation>
    {}
}