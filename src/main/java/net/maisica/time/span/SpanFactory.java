package net.maisica.time.span;

@FunctionalInterface
public interface SpanFactory<T extends Comparable<? super T>, D extends Comparable<? super D>, U extends Span<T, D>> {

    public U createSpan(T start, D duration);
    
}
