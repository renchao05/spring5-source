package com.renchao.aop.jdk_proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * InvocationHandler接口增加 Method 参数，动态调用方法
 * @author ren_chao
 * @since 2024-08-22
 */
public class JDKProxy_V3 {
	public static void main(String[] args) {
		TargetImpl target = new TargetImpl();
		$Proxy0 proxy = new $Proxy0(new InvocationHandler() {
			@Override
			public void invoke(Method method, Object[] args) throws Throwable {
				// 功能增强
				System.out.println("前置通知 >>>>>");
				// 调用目标方法
				method.invoke(target, args);
			}
		});
		proxy.foo();
		proxy.bar();

	}

	static class $Proxy0 implements Target {
		private final InvocationHandler ih;

		public $Proxy0(InvocationHandler ih) {
			this.ih = ih;
		}

		@Override
		public void foo() {
			try {
				Method method = Target.class.getMethod("foo");
				ih.invoke(method, new Object[0]);
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void bar() {
			try {
				Method method = Target.class.getMethod("bar");
				ih.invoke(method, new Object[0]);
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}

	}

	interface InvocationHandler {
		void invoke(Method method, Object[] args) throws Throwable;
	}

	static class TargetImpl implements Target {
		@Override
		public void foo() {
			System.out.println("目标方法foo.....");
		}

		@Override
		public void bar() {
			System.out.println("目标方法bar.....");
		}
	}


	interface Target {
		void foo();

		void bar();
	}


}
