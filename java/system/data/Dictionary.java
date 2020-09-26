package system.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * {@code Dictionary} is an n-tree dictionary for one-to-one mapping of string keys to value objects.
 *
 * @param <T> the type of value objects.
 *
 * @since 1.8
 * @author Alireza Kamran
 */
public
class Dictionary<T>
{
    /** The root node character. */
    private static final
    Character HEAD = null;

    /** The root node of the n-tree. */
    protected final
    Letter<T> root;

    /**
     * Creates an empty dictionary map.
     */
    public
    Dictionary() {
        root = new Letter<T>(HEAD);
    }

    /**
     * Called by {@link #add(String, Object)} to add key and value to the dictionary.
     *
     * @param key the key.
     * @param index the index of character in key that is currently being added.
     * @param prevLetter the previous letter node added to the map.
     * @param value the value.
     */
    protected
    void add(
        final String key,
        final int index,
        final Letter<T> prevLetter,
        final T value
        ) {
        if (index < key.length()) {
            final char ch = key.charAt(index);
            Letter<T> letter;
            synchronized (prevLetter.nextLetters) {
                letter = next(ch, prevLetter.nextLetters, false);
                if (letter == null) {
                    letter = new Letter<>(ch);
                    prevLetter.nextLetters.add(letter);
                }
            }

            add(key, index + 1, letter, value);
        }
        else
            prevLetter.value = value;
    }

    /**
     * Adds the key and value to the dictionary.
     * <p/>
     * This method is thread-safe.
     *
     * @param key the key.
     * @param value the value.
     */
    public
    void add(
        final String key,
        final T value
        ) {
        add(key, 0, root, value);
    }

    /**
     * Called by {@link #find(String)} to retrieve a value from the dictionary.
     *
     * @param key the key.
     * @param index the index of character in key that is currently being retrieved.
     * @param prevLetter the previous letter node found in the map.
     * @param ignoreCase flag that indicates whether search should ignore character case.
     * @return the value, or null if key is null or isn't in the dictionary.
     */
    protected
    T find(
        final String key,
        final int index,
        final Letter<T> prevLetter,
        final boolean ignoreCase
        ) {
        if (key == null)
            return null;

        if (index < key.length()) {
            final Letter<T> letter = next(key.charAt(index), prevLetter.nextLetters, ignoreCase);
            if (letter == null)
                return null;

            return find(key, index + 1, letter, ignoreCase);
        }

        return prevLetter.value;
    }

    /**
     * Returns the value for the specified key in the dictionary, or null if the key doesn't exist.
     *
     * @param key the key.
     * @param ignoreCase flag that indicates whether search should ignore character case.
     * @return the value for the key in dictionary, or null if it doesn't exist.
     */
    public
    T find(
        final String key,
        final boolean ignoreCase
        ) {
        return find(key, 0, root, ignoreCase);
    }

    /**
     * Returns the value for the specified key in the dictionary, or null if the key doesn't exist.
     *
     * @param key the key.
     * @return the value for the key in dictionary, or null if it doesn't exist.
     */
    public
    T find(
        final String key
        ) {
        return find(key, 0, root, false);
    }

    /**
     * Returns true if the dictionary is empty.
     *
     * @return true if the dictionary is empty.
     */
    public
    boolean isEmpty() {
        return root.nextLetters.isEmpty();
    }

    /**
     * Creates and returns an intermediary {@link MultiKeyMapper} object for mapping multiple keys to a single value object.
     *
     * @param keys the keys.
     *
     * @return the mapper.
     */
    public
    MultiKeyMapper<T> map(
        final String... keys
        ) {
        return value -> {
            for (final String key : keys)
                add(key, value);

            return this;
        };
    }

    /**
     * Called by {@link #find(String)} and {@link #add(String, Object)} to find the next letter node with the specified character.
     *
     * @param ch the character.
     * @param list the list of next letters in the n-tree.
     * @param start the index of the starting letter in the n-tree.
     * @param length the number of next letters in the list to lookup.
     * @param ignoreCase the flag indicating if search should ignore the character case.
     *
     * @return the next letter node, or null if not found.
     */
    private
    Letter<T> next(
        final char ch,
        final List<Letter<T>> list,
        final int start,
        int length,
        final boolean ignoreCase
        ) {
        Iterator<Letter<T>> it = list.listIterator(start);
        Letter<T> letter;
        if (ignoreCase)
            while (length-- > 0 && it.hasNext()) {
                letter = it.next();
                if (Character.toLowerCase(letter.ch) == Character.toLowerCase(ch))
                    return letter;
            }
        else
            while (length-- > 0 && it.hasNext()) {
                letter = it.next();
                if (letter.ch == ch)
                    return letter;
            }

        return null;
    }

    /**
     * Called by {@link #find(String)} and {@link #add(String, Object)} to find the next letter node with the specified character.
     *
     * @param ch the character.
     * @param list the list of next letters in the n-tree.
     * @param ignoreCase the flag indicating if search should ignore the character case.
     * @return the next letter node or null if not found.
     */
    private
    Letter<T> next(
        final char ch,
        final List<Letter<T>> list,
        final boolean ignoreCase
        ) {
        return next(ch, list, 0, list.size(), ignoreCase);
    }

    /**
     * {@code Letter} is a representation of a single node in the n-tree dictionary.
     *
     * @param <S> The type of the value objects.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    private final
    class Letter<S>
    {
        /** The node character. */
        public final
        char ch;

        /** The node value. */
        public
        S value;

        /** List of next letter nodes. */
        public final
        List<Letter<S>> nextLetters;

        /**
         * Creates a letter node with the specified character.
         *
         * @param ch the character.
         */
        public
        Letter(
            final char ch
            ) {
            this.ch = ch;
            nextLetters = new ArrayList<>(1);
        }
    }

    /**
     * {@code MultiKeyMapper} classifies an intermediary object used for mapping multiple keys to a single value object.
     *
     * @param <S> the type of value object.
     *
     * @since 1.8
     * @author Alireza Kamran
     */
    public
    interface MultiKeyMapper<S>
    {
        /**
         * Maps to the specified value and returns this dictionary.
         *
         * @param value the value.
         *
         * @return the dictionary.
         */
        Dictionary<S> to(
            S value
            );
    }
}