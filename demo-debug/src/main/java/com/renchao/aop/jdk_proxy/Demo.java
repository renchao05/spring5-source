package com.renchao.aop.jdk_proxy;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 可以通过Alibaba的Arthas反编译查看代理对象源代码
 *
 * @author ren_chao
 * @since 2024-08-22
 */
public class Demo {
	public static void main(String[] args) throws IOException {
		TargetImpl target = new TargetImpl();
		ClassLoader classLoader = Demo.class.getClassLoader();

		Target t = (Target) Proxy.newProxyInstance(classLoader, new Class[]{Target.class}, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("前置增强 >>>>");
				Object result = method.invoke(target, args);
				System.out.println("后置增强 >>>>");
				return result;
			}
		});

		t.foo();

		// 打印代理对象名称，可以通过Alibaba的Arthas反编译查看源代码
		System.out.println(t);

		// 程序别结束，然后利用Arthas反编译
		System.in.read();
	}


	static class TargetImpl implements JDKProxy_V1.Target {
		@Override
		public void foo() {
			System.out.println("目标方法执行。。。");
		}
	}

	interface Target {
		void foo();
	}


}
