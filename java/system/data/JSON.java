package system.data;

import java.io.InputStream;
import java.io.OutputStream;

import exceptions.InvalidJSONException;
import system.Data.Hierarchical;
import system.Data.Interpretable;

/**
 * {@code JSON} represents a JSON document.
 * <p>
 * It also holds static members for generating JSON elements from an input stream or character sequence, writing elements to output streams, and traversing them.
 * The static methods in this class are thread-safe.
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
     * <p>
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
     * <p>
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
    public String toString() {
        return getCharSequence().toString();
    }

    /**
     * {@code Array} classifies all JSON array elements.
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
        public default
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
        public
        Iterable<Element> getElements();

        /** This implementation returns {@code Array}. */
        @Override
        public default
        ValueType getValueType() {
            return ValueType.Array;
        }
    }

    /**
     * {@code Element} classifies all JSON data elements.
     */
    public
    interface Element
    extends CharSequence
    {
        /**
         * Returns the start index of the element within JSON string.
         *
         * @return the element's start index.
         */
        public
        int getStart();

        /**
         * Returns the end index of the element within JSON string.
         * <p>
         * The element's end index is equal to the element's start index plus its length.
         *
         * @return the element's end index.
         */
        public
        int getEnd();

        /**
         * Returns the element value type.
         *
         * @return the element value type.
         */
        public
        ValueType getValueType();

        /**
         * {@code ValueType} categorizes the types of JSON data elements.
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
     */
    public static abstract
    class Filter
    extends Document.Filter
    {
        /**
         * {@code Match} represents all data types that make up the conditional expressions for matching elements within JSON documents.
         */
        public static abstract
        class Match
        extends Document.Filter.Match
        {
            /**
             * {@code Ancestor} represents all match types that target JSON element ancestor conditions.
             */
            public static abstract
            class Ancestor
            extends Match
            {}

            /**
             * {@code Child} represents all match types that target JSON element child conditions.
             */
            public static abstract
            class Child
            extends Descendant
            {}

            /**
             * {@code Descendant} represents all match types that target JSON element descendant conditions.
             */
            public static abstract
            class Descendant
            extends Match
            {}

            /**
             * {@code Depth} represents all match types that target JSON element depth conditions.
             */
            public static abstract
            class Depth
            extends Element
            {}

            /**
             * {@code Element} represents all match types that target JSON element conditions.
             */
            public static abstract
            class Element
            extends Match
            {}

            /**
             * {@code Index} represents all match types that target JSON element index conditions.
             */
            public static abstract
            class Index
            extends Element
            {}

            /**
             * {@code Key} represents all match types that target JSON element key conditions.
             */
            public static abstract
            class Key
            extends Element
            {}

            /**
             * {@code Parent} represents all match types that target JSON element parent conditions.
             */
            public static abstract
            class Parent
            extends Ancestor
            {}

            /**
             * {@code Sibling} represents all match types that target JSON element sibling conditions.
             */
            public static abstract
            class Sibling
            extends Match
            {}

            /**
             * {@code Type} represents all match types that target JSON element type conditions.
             */
            public static abstract
            class Type
            extends Element
            {}

            /**
             * {@code Value} represents all match types that target JSON element value conditions.
             */
            public static abstract
            class Value
            extends Element
            {}
        }
    }

    /**
     * {@code Handler} represents callback handlers for processing all, or parts of, JSON documents during parsing.
     * <p>
     * By convention, the generic type of this class defines the handler's result element type.
     *
     * @param <T> the document node type.
     */
    public static abstract
    class Handler<T extends Element>
    implements Document.Handler
    {}

    /**
     * {@code Locator} classifies a simple lookup interface for finding individual JSON elements.
     */
    public
    interface Locator
    {
        public
        Element getElement();
    }

    /**
     * {@code Object} classifies all JSON object elements.
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
        public default
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
        public
        Iterable<Element> at(
            String key
            );

        /** This implementation returns {@code Object}. */
        @Override
        public default
        ValueType getValueType() {
            return ValueType.Object;
        }
    }

    /**
     * {@code ObjectElement} classifies all JSON object key-value pairs.
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
        public
        CharSequence key();

        /**
         * Returns the start index of the element key within JSON string.
         *
         * @return the element key's start index.
         */
        public
        int keyStart();

        /**
         * Returns the end index of the element key within JSON string.
         * <p>
         * The key's end index is equal to the key's start index plus its length.
         *
         * @return the element key's end index.
         */
        public
        short keyEnd();

        /** This implementation returns null. */
        @Override
        public default
        ValueType getValueType() {
            return null;
        }
    }

    /**
     * {@code Score} represents an experimental form of JSON object translated from and equivalent to a MusicXML score.
     */
    public static abstract
    class Score
    extends JSON
    implements
        musical.Notation,
        performance.system.Type<musical.Notation>
    {}
}