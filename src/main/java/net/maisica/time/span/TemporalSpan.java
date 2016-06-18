package net.maisica.time.span;

import java.time.Duration;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.stream.Stream;

public interface TemporalSpan<T extends Temporal & Comparable<? super T>> extends Span<T, Duration> {

    @SuppressWarnings("unchecked")
    @Override
    public default T computeEnd() {
        return (T) getStart().plus(getDuration());
    }

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
    public default Stream<T> quantize(final Duration quant) {
        return Stream.iterate(getStart(), quant::addTo)
                .limit(getDuration().toNanos() / quant.toNanos())
                .map(t -> (T) t);
    }

}
