package pl.maisica.time;

import java.time.Duration;
import java.time.temporal.Temporal;

public interface TemporalInterval<T extends Temporal & Comparable<? super T>> extends Interval<T> {
    
    public default Duration toDuration() {
        return Duration.between(getStart(), getEnd());
    }

}
