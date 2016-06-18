package net.maisica.time.span;

public interface Span<T extends Comparable<? super T>, U extends Comparable<? super U>> {
    
    public T getStart();
    
    public U getDuration();
    
    public T computeEnd();
    
    public Span<T, U> withStart(T start);
    
    public Span<T, U> withDuration(U duration);

}
