package system.data;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.NullType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;

/**
 * {@code Type} classifies singularly associable data types that are of, or can be of, other data types of their own kind, super-kind, or sub-kind.
 * <p>
 * One aim of this interface is to generate context by inheritance and comparison.
 * <p>
 * This class implementation is in progress.
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
     * Returns true if the specified data type is of this type, and false otherwise.
     *
     * @param type the other type.
     * @return true if the specified date type is of this type, and false otherwise.
     */
    boolean is(
        Type<? extends T> type
        );

    /**
     * {@code Null} classifies null data types inherently lacking any specific significance other than locally defined representations that are usually high-level concepts themselves.
     * <p>
     * This class implementation is in progress.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    interface Null
    extends
        NullType,
        java.lang.reflect.Type
    {
        @Override
        default <R, P> R accept(TypeVisitor<R, P> v, P p) { return null; }

        @Override
        default <A extends Annotation> A getAnnotation(Class<A> annotationType) { return null; }

        @Override
        default List<? extends AnnotationMirror> getAnnotationMirrors() { return null; }

        @Override
        default <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) { return null; }

        @Override
        default TypeKind getKind() { return null; }

        /**
         * {@code Element} specifies null data types addressable in JSON and XML documents.
         * <p>
         * This class implementation is in progress.
         *
         * @since 1.8
         * @author Alireza Kamran
         */
        public
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
             * @return the intermediary element.
             */
            static Element of(JSON.Element target) { return null; }

            /**
             * Returns an intermediary node type with the specified XML node as the target.
             *
             * @param target the target node.
             * @return the intermediary node.
             */
            static Element of(XML.Element target) { return null; }

            @Override
            public default char charAt(int index) { return 0; }

            @Override
            public default int getEnd() { return 0; }

            @Override
            public default int getStart() { return 0; }

            @Override
            public default ValueType getValueType() { return null; }

            @Override
            public default int length() { return 0; }

            @Override
            public default Object object() { return null; }

            @Override
            public default CharSequence subSequence(int start, int end) { return null; }
        }
    }
}