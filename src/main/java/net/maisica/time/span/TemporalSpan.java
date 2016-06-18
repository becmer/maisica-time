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
package net.maisica.time.span;

import java.time.Duration;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Comparator;
import java.util.Objects;
import java.util.Spliterator;
import static java.util.Spliterator.*;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.maisica.time.interval.TemporalInterval;

public interface TemporalSpan<T extends Temporal & Comparable<? super T>> extends Span<T, Duration> {

    @SuppressWarnings("unchecked")
    @Override
    public default T computeEnd() {
        return (T) getStart().plus(getDuration());
    }

    public TemporalInterval<T> toInterval();

    public default long length(final TemporalUnit unit) {
        return unit.between(getStart(), computeEnd());
    }

    @SuppressWarnings("unchecked")
    public default T interpolate(final double position, final double total, final TemporalUnit unit) {
        return (T) getStart().plus(Math.round(position / total * (double) length(unit)), unit);
    }

    public default double interpolate(final T position, final double total, final TemporalUnit unit) {
        return getStart().until(position, unit) / (double) length(unit) * total;
    }

    @SuppressWarnings("unchecked")
    public default Stream<T> stream(final TemporalAmount step) {
        Objects.requireNonNull(step, "step");
        final TemporalUnit unsupportedUnit = step.getUnits().stream().filter(u -> !getStart().isSupported(u)).findFirst().orElse(null);
        if (unsupportedUnit != null) {
            throw new UnsupportedTemporalTypeException(String.format("Unsupported unit: %s", unsupportedUnit));
        }
        
        final Spliterator<T> spliterator = Stream.iterate(getStart(), step::addTo).map(t -> (T) t).spliterator();
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<T>(Long.MAX_VALUE, DISTINCT | IMMUTABLE | NONNULL | ORDERED | SORTED) {

            private boolean valid = true;

            @Override
            public boolean tryAdvance(final Consumer<? super T> consumer) {
                if (valid) {
                    final boolean hadNext = spliterator.tryAdvance(t -> {
                        if (Duration.between(getStart(), t).compareTo(getDuration()) < 0) {
                            consumer.accept(t);
                        } else {
                            valid = false;
                        }
                    });
                    return hadNext && valid;
                }
                return false;
            }

            @Override
            public Comparator<? super T> getComparator() {
                return null;
            }

        }, false);
    }

}
