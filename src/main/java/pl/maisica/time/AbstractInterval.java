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

import java.util.Objects;
import java.util.stream.Stream;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public abstract class AbstractInterval<T extends Comparable<? super T>, U extends AbstractInterval<T, U>> implements Interval<T> {

    private final T start;
    private final T end;

    public AbstractInterval(final T start, final T end) {
        this.start = Objects.requireNonNull(start, "start");
        this.end = Objects.requireNonNull(end, "end");
        if (end.compareTo(start) < 0) {
            throw new IllegalArgumentException("end is before start");
        }
    }

    protected abstract IntervalFactory<T, U> getFactory();

    @Override
    public final T getStart() {
        return start;
    }

    @Override
    public final T getEnd() {
        return end;
    }

    @Override
    public final U withStart(final T start) {
        return getFactory().createInterval(start, end);
    }

    @Override
    public final U withEnd(final T end) {
        return getFactory().createInterval(start, end);
    }

    @Override
    public final boolean isEmpty() {
        return start.equals(end);
    }

    @Override
    public final boolean contains(final T o) {
        return o.compareTo(start) >= 0 && o.compareTo(end) < 0;
    }

    @Override
    public final boolean encloses(final Interval<T> interval) {
        return interval.getStart().compareTo(start) >= 0 && interval.getEnd().compareTo(end) <= 0;
    }

    @Override
    public final boolean abuts(final Interval<T> interval) {
        return interval.getEnd().equals(start) ^ interval.getStart().equals(end);
    }

    @Override
    public final boolean overlaps(final Interval<T> interval) {
        return (interval.getEnd().compareTo(start) > 0 && interval.getStart().compareTo(end) < 0) || equals(interval);
    }

    @Override
    public final U overlap(final Interval<T> interval) {
        return !overlaps(interval) ? null : getFactory().createInterval(
                Stream.of(start, interval.getStart()).max(Comparable::compareTo).get(),
                Stream.of(end, interval.getEnd()).min(Comparable::compareTo).get());
    }

    @Override
    public final U gap(final Interval<T> interval) {
        final T thatStart = interval.getStart();
        final T thatEnd = interval.getEnd();
        if (thatStart.compareTo(end) > 0) {
            return getFactory().createInterval(end, thatStart);
        } else if (thatEnd.compareTo(start) < 0) {
            return getFactory().createInterval(thatEnd, start);
        } else {
            return null;
        }
    }

    @Override
    public final U join(final Interval<T> interval) {
        return getFactory().createInterval(
                Stream.of(start, interval.getStart()).min(Comparable::compareTo).get(),
                Stream.of(end, interval.getEnd()).max(Comparable::compareTo).get());
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
        final AbstractInterval<?, ?> that = (AbstractInterval<?, ?>) o;
        if (!Objects.equals(this.start, that.start)) {
            return false;
        }
        if (!Objects.equals(this.end, that.end)) {
            return false;
        }
        return true;
    }

}
