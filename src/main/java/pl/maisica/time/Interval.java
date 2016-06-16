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
package pl.maisica.time;

import java.io.Serializable;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.Objects;
import java.util.stream.Stream;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class Interval<T extends Temporal & Comparable<? super T>> implements Serializable {

    public static <T extends Temporal & Comparable<? super T>> Interval<T> of(final T start, final T end) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
        if (end.compareTo(start) < 0) {
            throw new IllegalArgumentException("end is before start");
        }
        return new Interval<>(start, end);
    }

    private final T start;
    private final T end;

    private Interval(final T start, final T end) {
        this.start = start;
        this.end = end;
    }

    public T getStart() {
        return start;
    }

    public T getEnd() {
        return end;
    }

    public Interval<T> withStart(final T start) {
        return start.equals(this.start) ? this : new Interval<>(start, end);
    }

    public Interval<T> withEnd(final T end) {
        return end.equals(this.end) ? this : new Interval<>(start, end);
    }

    public Duration toDuration() {
        return Duration.between(start, end);
    }
    
    public Span<T> toSpan() {
        return Span.of(start, toDuration());
    }

    public boolean isEmpty() {
        return start.equals(end);
    }

    public boolean contains(final T o) {
        return o.compareTo(start) >= 0 && o.compareTo(end) < 0;
    }

    public boolean encloses(final Interval<T> interval) {
        return interval.getStart().compareTo(start) >= 0 && interval.getEnd().compareTo(end) <= 0;
    }

    public boolean abuts(final Interval<T> interval) {
        return interval.getEnd().equals(start) ^ interval.getStart().equals(end);
    }

    public boolean overlaps(final Interval<T> interval) {
        return (interval.getEnd().compareTo(start) > 0 && interval.getStart().compareTo(end) < 0) || equals(interval);
    }

    public Interval<T> overlap(final Interval<T> interval) {
        if (!overlaps(interval)) {
            return null;
        }
        final T thatStart = interval.getStart();
        final T thatEnd = interval.getEnd();
        final T maxStart = Stream.of(start, thatStart).max(Comparable::compareTo).get();
        final T minEnd = Stream.of(end, thatEnd).min(Comparable::compareTo).get();
        // don't create duplicates
        if (maxStart == start && minEnd == end) {
            return this;
        } else if (maxStart.equals(thatStart) && minEnd.equals(thatEnd)) {
            return interval;
        } else {
            return new Interval<>(maxStart, minEnd);
        }
    }

    public Interval<T> gap(final Interval<T> interval) {
        final T thatStart = interval.getStart();
        final T thatEnd = interval.getEnd();
        if (thatStart.compareTo(end) > 0) {
            return new Interval<>(end, thatStart);
        } else if (thatEnd.compareTo(start) < 0) {
            return new Interval<>(thatEnd, start);
        } else {
            return null;
        }
    }

    public Interval<T> join(final Interval<T> interval) {
        final T thatStart = interval.getStart();
        final T thatEnd = interval.getEnd();
        final T minStart = Stream.of(start, thatStart).min(Comparable::compareTo).get();
        final T maxEnd = Stream.of(end, thatEnd).max(Comparable::compareTo).get();
        // don't create duplicates
        if (minStart == start && maxEnd == end) {
            return this;
        } else if (minStart.equals(thatStart) && maxEnd.equals(thatEnd)) {
            return interval;
        } else {
            return new Interval<>(minStart, maxEnd);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.start);
        hash = 29 * hash + Objects.hashCode(this.end);
        return hash;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        final Interval<?> that = (Interval<?>) o;
        if (!Objects.equals(this.start, that.start)) {
            return false;
        }
        if (!Objects.equals(this.end, that.end)) {
            return false;
        }
        return true;
    }

}
