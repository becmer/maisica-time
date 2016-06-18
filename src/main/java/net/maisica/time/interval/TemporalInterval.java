package net.maisica.time.interval;

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
import net.maisica.time.span.TemporalSpan;

public interface TemporalInterval<T extends Temporal & Comparable<? super T>> extends Interval<T> {

    public default Duration toDuration() {
        return Duration.between(getStart(), getEnd());
    }

    public TemporalSpan<T> toSpan();

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
                        if (t.compareTo(getEnd()) < 0) {
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
