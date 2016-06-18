/*
 * Copyright 2016 Kamil Becmer <kamil.becmer at maisica.pl>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.maisica.time.interval;

/**
 * An interface for immutable interval between two temporals.
 * <p>
 * An interval represents the part of the continuum between inclusive start and exclusive end. The end temporal is always greater than or equal to the start
 * temporal.
 * </p>
 * <p>
 * Intervals are not comparable. Implementations must be immutable, except you know what you are doing.
 * </p>
 *
 * @param <T> the temporal-on-continuum type, must be comparable
 * @see net.maisica.time.span.Span
 */
public interface Interval<T extends Comparable<? super T>> {

    /**
     * Gets the start of this interval, inclusive.
     *
     * @return the start of the interval
     */
    public T getStart();

    /**
     * Gets the end of this interval, exclusive.
     *
     * @return the end of the interval
     */
    public T getEnd();

    /**
     * Creates a new interval with the specified start.
     *
     * @param start the start for the new interval, not null
     * @return an interval with end from this interval and the specified start
     * @throws IllegalArgumentException if the resulting interval has end before start
     */
    public Interval<T> withStart(T start);

    /**
     * Creates a new interval with the specified end.
     *
     * @param end the end for the new interval, not null
     * @return an interval with the start from this interval and the specified end
     * @throws IllegalArgumentException if the resulting interval has end before start
     */
    public Interval<T> withEnd(T end);

    /**
     * Checks if this interval's start and end are equals.
     * <p>
     * The result is true if the start are equal to end, so this interval does not contain anything.
     * </p>
     *
     * @return true if this interval has end equal to start
     */
    public boolean isEmpty();

    /**
     * Checks if this interval contains the specified temporal.
     * <p>
     * The result is true if the temporal is equal or after the start and before the end. An empty interval does not contain anything.
     * </p>
     *
     * @param o the temporal, not null
     * @return true if this interval contains the temporal
     */
    public boolean contains(final T o);

    /**
     * Checks if this interval encloses the specified interval.
     * <p>
     * The result is true if the start of the specified interval is contained in this interval, and the end is contained or equal to the end of this interval.
     * An empty interval contains an equal empty interval, but no other intervals.
     * </p>
     *
     * @param interval the interval, not null
     * @return true if this interval contains the other interval
     */
    public boolean encloses(final Interval<T> interval);

    /**
     * Checks if this interval abuts the specified interval.
     * <p>
     * The result is true if the intervals have exactly one temporal in common. An equal intervals does not abuts.
     * </p>
     *
     * @param interval the interval, not null
     * @return true if this interval abuts the other interval
     */
    public boolean abuts(final Interval<T> interval);

    /**
     * Checks if this interval overlaps the specified interval.
     * <p>
     * The result is true if the intervals shares some common part of the continuum. An empty interval overlaps an equal empty interval.
     * </p>
     *
     * @param interval the interval, not null
     * @return true if the intervals overlap
     */
    public boolean overlaps(final Interval<T> interval);

    /**
     * Gets the overlap between this and specified interval.
     * <p>
     * The result is null if the intervals shares no common part of the continuum. Otherwise returns an interval with smallest common temporal as start and
     * largest common temporal as end.
     * </p>
     *
     * @param interval the interval, not null
     * @return the overlap interval, null if no overlap
     */
    public Interval<T> overlap(Interval<T> interval);

    /**
     * Gets the gap between this and specified interval.
     * <p>
     * The result is null if the intervals does not have any gap between, i.e. they abuts or overlaps. Otherwise returns an interval with start at end of the
     * preceding interval and end at start of the following interval.
     * </p>
     *
     * @param interval the interval, not null
     * @return the gap interval, null if no gap
     */
    public Interval<T> gap(Interval<T> interval);

    /**
     * Gets the join of this and specified interval.
     * <p>
     * The result is an interval with smallest temporal as start and largest temporal as end, both contained in any of the intervals.
     * </p>
     *
     * @param interval the interval. not null
     * @return the joined interval
     */
    public Interval<T> join(Interval<T> interval);

}
