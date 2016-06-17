package pl.maisica.time;

@FunctionalInterface
public interface IntervalFactory<T extends Comparable<? super T>, U extends Interval<T>> {
    
    public U createInterval(T start, T end);

}
