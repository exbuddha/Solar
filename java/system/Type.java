package system;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Function;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.NullType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;

import system.data.JSON;
import system.data.XML;

/**
 * {@code Type} classifies singularly associable data types that are of, or can be of, other data types, their own kind, super-kind, or sub-kind.
 * <p/>
 * One aim of this interface is to generate context by inheritance and comparison.
 * This class extends the standard Java reflection {@code Type} only to allow its subtypes to be included in the performer change graph along with Java class types.
 *
 * @param <T> the data type.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
interface Type<T>
extends java.lang.reflect.Type
{
    /**
     * Returns the contextual type resolver for this data type.
     * <p/>
     * Type resolver is a function that accepts an object and return a data type contextually associated with this type.
     * <p/>
     * This implementation returns null.
     *
     * @return the contextual type resolver.
     */
    default
    Function<Object, Type<? super T>> contextualTypeResolver() {
        return null;
    }

    /**
     * Returns true if the specified object is of this type; otherwise returns false.
     *
     * @param obj the object.
     *
     * @return true if the object is of this type, and false otherwise.
     *
     * @see #contextualTypeResolver()
     */
    default
    boolean is(
        final Object obj
        ) {
        final Function<Object, Type<? super T>> resolver = contextualTypeResolver();
        return resolver != null && is(resolver.apply(obj));
    }

    /**
     * Returns true if the specified data type is of this type; otherwise returns false.
     *
     * @param type the other data type.
     *
     * @return true if the date type is of this type, and false otherwise.
     */
    boolean is(
        final Type<? super T> type
        );

    /**
     * {@code Null} classifies null data types inherently lacking any specific significance other than locally defined representations that are usually high-level concepts themselves.
     * <p/>
     * This class extends the standard Java {@code NullType} only to be distinguished from {@link Type} instances.
     * It is not intended to be regularly used as null type within the Java programming language and as a result all of its overridden methods return null.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Null
    extends
        NullType,
        java.lang.reflect.Type
    {
        /**
         * {@code Element} specifies null data types addressable in JSON and XML documents.
         * <p/>
         * The subtypes of this interface are meant to be used internally as pseudo-data but not as regular {@code NullType} instances in the Java programming language.
         * <p/>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        interface Element
        extends
            JSON.Element,
            XML.Element,
            Null
        {
            /**
             * Returns an intermediary element type with the specified JSON element as the target.
             *
             * @param target the target element.
             *
             * @return the intermediary element.
             */
            static Element of(JSON.Element target) { return null; }

            /**
             * Returns an intermediary element type with the specified XML node as the target.
             *
             * @param target the target node.
             *
             * @return the intermediary node.
             */
            static Element of(XML.Element target) { return null; }

            @Override
            default char charAt(int index) { return 0; }

            @Override
            default int getEnd() { return 0; }

            @Override
            default int getStart() { return 0; }

            @Override
            default ValueType getValueType() { return null; }

            @Override
            default int length() { return 0; }

            @Override
            default Object object() { return null; }

            @Override
            default CharSequence subSequence(int start, int end) { return null; }
        }

        @Override
        default <R, P> R accept(TypeVisitor<R, P> v, P p) {
            return null;
        }

        @Override
        default <A extends Annotation> A getAnnotation(Class<A> annotationType) {
            return null;
        }

        @Override
        default List<? extends AnnotationMirror> getAnnotationMirrors() {
            return null;
        }

        @Override
        default <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) {
            return null;
        }

        @Override
        default TypeKind getKind() {
            return null;
        }
    }
}