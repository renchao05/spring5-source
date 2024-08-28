package com.renchao.aop.unit_demo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.*;
import org.springframework.aop.support.DefaultPointcutAdvisor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 代码实现 高级切面转低级切面
 * @author ren_chao
 * @since 2024-08-28
 */
public class Demo05_AspectToAdvisor {
	public static void main(String[] args) {
		List<Advisor> advisors = getAdvisorList(new MyAspect());
		advisors.forEach(System.out::println);

	}

	/**
	 * 高级切面转低级切面
	 */
	public static List<Advisor> getAdvisorList(Object o) {
		AspectInstanceFactory factory = new SingletonAspectInstanceFactory(o);
		// 1. 高级切面转低级切面类
		List<Advisor> list = new ArrayList<>();
		for (Method method : o.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(Before.class)) {
				// 解析切点
				AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
				pointcut.setExpression(method.getAnnotation(Before.class).value());
				// 通知类
				AspectJMethodBeforeAdvice advice = new AspectJMethodBeforeAdvice(method, pointcut, factory);
				// 切面
				list.add(new DefaultPointcutAdvisor(pointcut, advice));
			} else if (method.isAnnotationPresent(After.class)) {
				// 解析切点
				AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
				pointcut.setExpression(method.getAnnotation(After.class).value());
				// 通知类
				AspectJAfterAdvice advice = new AspectJAfterAdvice(method, pointcut, factory);
				// 切面
				list.add(new DefaultPointcutAdvisor(pointcut, advice));
			} else if (method.isAnnotationPresent(AfterReturning.class)) {
				// 解析切点
				AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
				pointcut.setExpression(method.getAnnotation(AfterReturning.class).value());
				// 通知类
				AspectJAfterReturningAdvice advice = new AspectJAfterReturningAdvice(method, pointcut, factory);
				// 切面
				list.add(new DefaultPointcutAdvisor(pointcut, advice));
			} else if (method.isAnnotationPresent(AfterThrowing.class)) {
				// 解析切点
				AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
				pointcut.setExpression(method.getAnnotation(AfterThrowing.class).value());
				// 通知类
				AspectJAfterThrowingAdvice advice = new AspectJAfterThrowingAdvice(method, pointcut, factory);
				// 切面
				list.add(new DefaultPointcutAdvisor(pointcut, advice));
			} else if (method.isAnnotationPresent(Around.class)) {
				// 解析切点
				AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
				pointcut.setExpression(method.getAnnotation(Around.class).value());
				// 通知类
				AspectJAroundAdvice advice = new AspectJAroundAdvice(method, pointcut, factory);
				// 切面
				list.add(new DefaultPointcutAdvisor(pointcut, advice));
			}
		}
		return list;
	}


	static class MyAspect {
		@Before("execution(* m1())")
		public void before1() {
			System.out.println("before1");
		}

		@Before("execution(* m1())")
		public void before2() {
			System.out.println("before2");
		}

		@After("execution(* m1())")
		public void after() {
			System.out.println("after");
		}

		@AfterReturning("execution(* m1())")
		public void afterReturning() {
			System.out.println("afterReturning");
		}

		@AfterThrowing("execution(* m1())")
		public void afterThrowing(Exception e) {
			System.out.println("afterThrowing " + e.getMessage());
		}

		@Around("execution(* m1())")
		public Object around(ProceedingJoinPoint pjp) throws Throwable {
			try {
				System.out.println("around...before");
				return pjp.proceed();
			} finally {
				System.out.println("around...after");
			}
		}
	}
	

}
