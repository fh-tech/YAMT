package org.itp1.yamtlib.util;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class YamtUtils {

    public interface CheckedFunction<T, R, E extends Exception >{
        R apply(T t) throws E;
    }

    public interface CheckedBiFunction<T, Q, R, E extends Exception >{
        R apply(T t, Q q) throws E;
    }

    public static <T,R,E extends Exception> Function<T,R> sneakyThrow(CheckedFunction<T, R, E> fn){
        return (t) -> {
            try {
                return fn.apply(t);
            }catch (Exception e){
                throw new RuntimeException(e.getMessage(), e);
            }
        };
    }

    public static <T, Q, R, E extends Exception> BiConsumer<T, Q> sneakyThrow(CheckedBiFunction<T, Q, R, E> fn){
        return (T t, Q q) -> {
            try {
                fn.apply(t, q);
            }catch (Exception e){
                throw new RuntimeException(e.getMessage(), e);
            }
        };
    }

    public static <T,R,E extends Exception> Function<T,R> handleException(CheckedFunction<T, R, E> fn, Consumer<E> handle){
        return (T t) -> {
            try {
                return fn.apply(t);
            }catch (RuntimeException e){
                throw e;
            }catch (Exception e){
                handle.accept((E) e);
                return null;
            }
        };
    }

}
