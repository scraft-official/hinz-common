package me.hinsinger.projects.hinz.common.factory;

import java.lang.reflect.Constructor;

public class ConstructorFactory {
	
	public static <T> DefaultConstructorFactory<T> of(Class<T> clazz) {
		return new DefaultConstructorFactory<>(clazz);
	}
	
	public static <T, A> MonoConstructorFactory<T, A> of(Class<T> clazz, Class<A> param1) {
		return new MonoConstructorFactory<T, A>(clazz);
	}
	
	public static <T, A, B> BiConstructorFactory<T, A, B> of(Class<T> clazz, Class<A> param1, Class<B> param2) {
		return new BiConstructorFactory<T, A, B>(clazz);
	}
	
	public static <T, A, B, C> TriConstructorFactory<T, A, B, C> of(Class<T> clazz, Class<A> param1, Class<B> param2, Class<C> param3) {
		return new TriConstructorFactory<T, A, B, C>(clazz);
	}
	
	public static <T, A, B, C, D> TetraConstructorFactory<T, A, B, C, D> of(Class<T> clazz, Class<A> param1, Class<B> param2, Class<C> param3, Class<D> param4) {
	    return new TetraConstructorFactory<>(clazz);
	}

	public static <T, A, B, C, D, E> PentaConstructorFactory<T, A, B, C, D, E> of(Class<T> clazz, Class<A> param1, Class<B> param2, Class<C> param3, Class<D> param4, Class<E> param5) {
	    return new PentaConstructorFactory<>(clazz);
	}

	public static <T, A, B, C, D, E, F> HexaConstructorFactory<T, A, B, C, D, E, F> of(Class<T> clazz, Class<A> param1, Class<B> param2, Class<C> param3, Class<D> param4, Class<E> param5, Class<F> param6) {
	    return new HexaConstructorFactory<>(clazz);
	}
	
	public static <T> T create(Class<T> clazz) {
	    return new DefaultConstructorFactory<>(clazz).create();
	}

	public static <T, A> T create(Class<T> clazz, A a) {
	    return new MonoConstructorFactory<>(clazz).create(a);
	}

	public static <T, A, B> T create(Class<T> clazz, A a, B b) {
	    return new BiConstructorFactory<>(clazz).create(a, b);
	}

	public static <T, A, B, C> T create(Class<T> clazz, A a, B b, C c) {
	    return new TriConstructorFactory<>(clazz).create(a, b, c);
	}

	public static <T, A, B, C, D> T create(Class<T> clazz, A a, B b, C c, D d) {
	    return new TetraConstructorFactory<>(clazz).create(a, b, c, d);
	}

	public static <T, A, B, C, D, E> T create(Class<T> clazz, A a, B b, C c, D d, E e) {
	    return new PentaConstructorFactory<>(clazz).create(a, b, c, d, e);
	}

	public static <T, A, B, C, D, E, F> T create(Class<T> clazz, A a, B b, C c, D d, E e, F f) {
	    return new HexaConstructorFactory<>(clazz).create(a, b, c, d, e, f);
	}

	
	private static <T> T createInstance(Constructor<T> constructor, Object... params) {
		try { return constructor.newInstance(params); }
		catch (Exception e) { throw new RuntimeException(e); }
	}
	
	private static <T> Constructor<T> getConstructorOf(Class<T> clazz, Class<?>... params) {
		try { return clazz.getConstructor(params); } 
		catch (Exception e) { throw new RuntimeException(e); }
	}
	
	public static class DefaultConstructorFactory<T> {
		private Constructor<T> constructor;
		
		protected DefaultConstructorFactory(Class<T> clazz) {
			this.constructor = getConstructorOf(clazz);
		}
		
		public T create() {
			return createInstance(constructor);
		}
	}
	
	public static class MonoConstructorFactory<T, A> {
		private Constructor<T> constructor;
		
		protected MonoConstructorFactory(Class<T> clazz) {
			this.constructor = getConstructorOf(clazz);
		}
		
		public T create(A a) {
			return createInstance(constructor, a);
		}
	}
	
	public static class BiConstructorFactory<T, A, B> {
		private Constructor<T> constructor;
		
		protected BiConstructorFactory(Class<T> clazz) {
			this.constructor = getConstructorOf(clazz);
		}
		
		public T create(A a, B b) {
			return createInstance(constructor, a, b);
		}
	}
	
	public static class TriConstructorFactory<T, A, B, C> {
		private Constructor<T> constructor;
		
		protected TriConstructorFactory(Class<T> clazz) {
			this.constructor = getConstructorOf(clazz);
		}
		
		public T create(A a, B b, C c) {
			return createInstance(constructor, a, b, c);
		}
	}
	
	public static class TetraConstructorFactory<T, A, B, C, D> {
		private Constructor<T> constructor;
		
		protected TetraConstructorFactory(Class<T> clazz) {
			this.constructor = getConstructorOf(clazz);
		}
		
		public T create(A a, B b, C c, D d) {
			return createInstance(constructor, a, b, c, d);
		}
	}
	
	public static class PentaConstructorFactory<T, A, B, C, D, E> {
		private Constructor<T> constructor;
		
		protected PentaConstructorFactory(Class<T> clazz) {
			this.constructor = getConstructorOf(clazz);
		}
		
		public T create(A a, B b, C c, D d, E e) {
			return createInstance(constructor, a, b, c, d, e);
		}
	}
	
	public static class HexaConstructorFactory<T, A, B, C, D, E, F> {
		private Constructor<T> constructor;
		
		protected HexaConstructorFactory(Class<T> clazz) {
			this.constructor = getConstructorOf(clazz);
		}
		
		public T create(A a, B b, C c, D d, E e, F f) {
			return createInstance(constructor, a, b, c, d, e, f);
		}
	}
}
