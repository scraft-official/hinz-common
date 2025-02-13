package me.hinsinger.projects.hinz.common.function;

public interface TetraFunction<A, B, C, D, OUT> {
	public OUT apply(A a, B b, C c, D d);
}
