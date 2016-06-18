package net.maisica.time.interval;

public interface Interval<T extends Comparable<? super T>> {
    
    public T getStart();
    
    public T getEnd();
    
    public Interval<T> withStart(T start);
    
    public Interval<T> withEnd(T end);
    
    public boolean isEmpty();
    
    public boolean contains(final T o);
    
    public boolean encloses(final Interval<T> interval);
    
    public boolean abuts(final Interval<T> interval);
    
    public boolean overlaps(final Interval<T> interval);
    
    public Interval<T> overlap(Interval<T> interval);
    
    public Interval<T> gap(Interval<T> gap);
    
    public Interval<T> join(Interval<T> interval);
    
}
