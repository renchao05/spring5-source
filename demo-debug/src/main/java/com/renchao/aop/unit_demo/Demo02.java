package com.renchao.aop.unit_demo;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * @author ren_chao
 * @since 2024-08-27
 */
public class Demo02 {
	public static void main(String[] args) {
		// 目标对象
		Target target = new Target();

		// 切点
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("execution(* m1())");

		// 通知
		MethodInterceptor advice = invocation -> {
			System.out.println("前置增强 before...");
			// 调用目标
			Object result = invocation.proceed();
			System.out.println("后置增强 after...");
			return result;
		};

		// 组成切面
		DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor(pointcut, advice);

		// 获取代理对象
		ProxyFactory factory = new ProxyFactory();
		factory.setTarget(target);
		factory.addAdvisor(pointcutAdvisor);

		// 默认都是Cglib 只有设置了接口，且proxyTargetClass为false 才使用JDK代理
		factory.setInterfaces(target.getClass().getInterfaces());
		factory.setProxyTargetClass(false);

		I t = (I) factory.getProxy();
		System.out.println(t.getClass());
		System.out.println("===============");
		t.m1();
		System.out.println("===============");
		t.m2();

	}

	static class Target implements I {
		@Override
		public void m1() {
			System.out.println("目标方法 m1 ...");
		}

		@Override
		public void m2() {
			System.out.println("目标方法 m2 ...");
		}
	}

	interface I {
		void m1();
		void m2();
	}

}
