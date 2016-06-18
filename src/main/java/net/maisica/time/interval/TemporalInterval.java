package net.maisica.time.interval;

import java.time.Duration;
import java.time.temporal.Temporal;
import net.maisica.time.span.TemporalSpan;

public interface TemporalInterval<T extends Temporal & Comparable<? super T>> extends Interval<T> {
    
    public default Duration toDuration() {
        return Duration.between(getStart(), getEnd());
    }
    
    public TemporalSpan<T> toSpan();

}
