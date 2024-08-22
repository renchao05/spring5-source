package com.renchao.aop.jdk_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 改用原生InvocationHandler
 * 代理对象继承 Proxy ，Proxy中有InvocationHandler ，代码可以更加简洁些，也更像原生JDK代理
 * 原生JDK代理可以使用Alibaba的Arthas反编译查看
 *
 * @author ren_chao
 * @since 2024-08-22
 */
public class JDKProxy_V6 {
	public static void main(String[] args) {
		TargetImpl target = new TargetImpl();
		$Proxy0 proxy = new $Proxy0(new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				// 功能增强
				System.out.println("前置通知 >>>>>");
				// 调用目标方法
				return method.invoke(target, args);
			}
		});
		proxy.foo();
		proxy.bar();
		System.out.println(proxy.bar("test00"));

	}

	static class $Proxy0 extends Proxy implements Target {
		private static final Method foo;
		private static final Method bar;
		private static final Method bar2;

		static {
			try {
				foo = Target.class.getMethod("foo");
				bar = Target.class.getMethod("bar");
				bar2 = Target.class.getMethod("bar", String.class);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}

		public $Proxy0(InvocationHandler ih) {
			super(ih);
		}

		@Override
		public void foo() {
			try {
				h.invoke(this, foo, new Object[0]);
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void bar() {
			try {
				h.invoke(this, bar, new Object[0]);
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public int bar(String str) {
			try {
				return (int) h.invoke(this, bar2, new Object[]{str});
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}

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

		@Override
		public int bar(String str) {
			System.out.println("目标方法bar.....  " + str);
			return 0;
		}
	}


	interface Target {
		void foo();

		void bar();
		int bar(String str);
	}


}
