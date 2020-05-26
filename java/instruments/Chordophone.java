package instruments;

import musical.Note;

/**
 * {@code Chordophone} classifies instruments in which sound is primarily produced by the vibration of a string, or strings, stretched between fixed points.
 */
public abstract
class Chordophone
extends MusicalInstrument
{
    /**
     * {@code String} classifies a vibrating string in musical instruments that is fixed at both ends with a specific tuning.
     */
    @Uniformed(group = Chordophone.class)
    protected abstract
    class String
    extends CompositePart
    implements Tunable
    {
        /** The string tuning note. */
        protected final
        Note tuning;

        /**
         * Creates a string with the specified tuning.
         *
         * @param tuning the note tune.
         */
        public
        String(
            final Note tuning
            ) {
            this.tuning = tuning;
        }

        @Override
        public boolean is(final system.Type<Part> type) {
            return type instanceof String;
        }

        /**
         * Returns the note that the string is tuned to.
         *
         * @return the tuning note.
         */
        @Override
        public Note getTune() {
            return tuning;
        }

        /**
         * {@code Point} classifies an interaction point on a chordophone string with a specific distance from an end point, conventionally the nut.
         */
        @Uniformed(group = String.class)
        protected abstract
        class Point
        extends AtomicPart
        implements
            Comparable<Point>,
            Tunable
        {
            /** The relative distance from end point. */
            protected final
            float distance;

            /**
             * Creates a point on the string at the specified relative distance from the top end of the string or the instrument nut. (0 being the nut point and 1 being the bridge point)
             *
             * @param distance the relative distance from the top end.
             */
            protected
            Point(
                final Number distance
                ) {
                this.distance = distance.floatValue();
            }

            @Override
            public int compareTo(final Point point) {
                return (int) Math.signum(getDistance() - point.getDistance());
            }

            @Override
            public boolean is(final system.Type<Part> type) {
                return type instanceof Point;
            }

            /**
             * Returns the resulting frequency of active part of the string when the point is pressed or touched.
             *
             * @return the frequency.
             */
            protected abstract
            Float getFrequency();

            /**
             * Returns the resulting note when the point is pressed or touched.
             *
             * @return the note.
             */
            @Override
            public Note getTune() {
                return null;
            }

            /**
             * Returns the relative distance of the point from top end point.
             *
             * @return the distance.
             */
            protected
            Float getDistance() {
                return distance;
            }
        }
    }
}