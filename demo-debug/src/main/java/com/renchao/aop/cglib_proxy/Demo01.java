package com.renchao.aop.cglib_proxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author ren_chao
 * @since 2024-08-23
 */
public class Demo01 {
	public static void main(String[] args) {
		Target target = new Target();
		// 反射调用
//		CProxy proxy = getProxy1(target);

		// 内部无反射
		CProxy_v2 proxy = getProxy2(target);

		proxy.save();
		proxy.save(5);
		proxy.save(666L);

	}

	/**
	 * 简单，利用反射
	 */
	private static CProxy_v1 getProxy1(Target target) {
		return new CProxy_v1(new MethodInterceptor() {
			@Override
			public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
				System.out.println("前置增强。。。。。");
				// 反射调用
				Object result = method.invoke(target, args);
				System.out.println("后置增强。。。。。");
				return result;
			}
		});
	}

	/**
	 * 利用 MethodProxy
	 */
	private static CProxy_v2 getProxy2(Target target) {
		return new CProxy_v2(new MethodInterceptor() {
			@Override
			public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
				System.out.println("前置增强。。。。。");
				// 内部无反射，结合目标使用
//				Object result = methodProxy.invoke(target, args);
				// 内部无反射，结合代理使用
				Object result = methodProxy.invokeSuper(proxy, args);
				System.out.println("后置增强。。。。。");
				return result;
			}
		});
	}


}
