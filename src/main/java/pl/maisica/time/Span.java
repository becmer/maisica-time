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
import java.time.temporal.TemporalUnit;
import java.util.Objects;
import java.util.stream.Stream;

/**
 *
 * @author Kamil Becmer <kamil.becmer at maisica.pl>
 */
public final class Span<T extends Temporal & Comparable<? super T>> implements Serializable {

    public static <T extends Temporal & Comparable<? super T>> Span<T> of(final T start, final Duration duration) {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(duration, "duration");
        if (duration.isNegative()) {
            throw new IllegalArgumentException("duration is negative");
        }
        return new Span<>(start, duration);
    }

    private final T start;
    private final Duration duration;

    private Span(final T start, final Duration duration) {
        this.start = start;
        this.duration = duration;
    }

    public T getStart() {
        return start;
    }

    public Duration getDuration() {
        return duration;
    }

    @SuppressWarnings("unchecked")
    public T computeEnd() {
        return (T) start.plus(duration);
    }

    public Span<T> withStart(final T start) {
        return start.equals(this.start) ? this : new Span<>(start, duration);
    }

    public Span<T> withDuration(final Duration duration) {
        return duration.equals(this.duration) ? this : new Span<>(start, duration);
    }

    public long length(final TemporalUnit unit) {
        return unit.between(start, start.plus(duration));
    }

    @SuppressWarnings("unchecked")
    public T interpolate(final double position, final double total, final TemporalUnit unit) {
        return (T) start.plus(Math.round(position / total * (double) length(unit)), unit);
    }

    public double interpolate(final T position, final double total, final TemporalUnit unit) {
        return start.until(position, unit) / (double) length(unit) * total;
    }

    @SuppressWarnings("unchecked")
    public Stream<T> quantize(final Duration quant) {
        return Stream.iterate(start, quant::addTo)
                .limit(duration.toNanos() / quant.toNanos())
                .map(t -> (T) t);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.start);
        hash = 53 * hash + Objects.hashCode(this.duration);
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
        final Span<?> that = (Span<?>) o;
        if (!Objects.equals(this.start, that.start)) {
            return false;
        }
        if (!Objects.equals(this.duration, that.duration)) {
            return false;
        }
        return true;
    }

}
