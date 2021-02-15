package xyz.lucaci32u4.command.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.ORDERED;
import static java.util.Spliterator.SIZED;

public class Utils {
    public static <A, B, R> Stream<R> zipStreams(@NotNull Stream<A> streamA, @NotNull Stream<B> streamB, @NotNull BiFunction<? super A, ? super B, R> function) {
        boolean isParallel = streamA.isParallel() || streamB.isParallel();
        Spliterator<A> splitrA = streamA.spliterator();
        Spliterator<B> splitrB = streamB.spliterator();
        Iterator<A> itrA = Spliterators.iterator(splitrA);
        Iterator<B> itrB = Spliterators.iterator(splitrB);
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<R>(Math.min(splitrA.estimateSize(), splitrB.estimateSize()),  splitrA.characteristics() & splitrB.characteristics() & (ORDERED | SIZED)) {
            public boolean tryAdvance(Consumer<? super R> action) {
                if (itrA.hasNext() && itrB.hasNext()) {
                    action.accept(function.apply(itrA.next(), itrB.next()));
                    return true;
                } else {
                    return false;
                }
            }
        }, isParallel);
    }
}
