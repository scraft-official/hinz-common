package me.hinsinger.hinz.common.function;

public interface TriFunction<A, B, C, OUT> {
	public OUT apply(A a, B b, C c);
}
