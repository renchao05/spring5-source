package com.renchao.aop.unit_demo;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.annotation.*;

/**
 * 切点匹配
 * @author ren_chao
 * @since 2024-08-27
 */
public class Demo02_Pointcut {
	public static void main(String[] args) throws NoSuchMethodException {
		// 方法匹配
		AspectJExpressionPointcut pointcut01 = new AspectJExpressionPointcut();
		pointcut01.setExpression("execution(* m1())");
		System.out.println(pointcut01.matches(T1.class.getMethod("m1"), T1.class));
		System.out.println(pointcut01.matches(T1.class.getMethod("m2"), T1.class));

		System.out.println("=================");
		// 注解匹配
		AspectJExpressionPointcut pointcut02 = new AspectJExpressionPointcut();
		pointcut02.setExpression("@annotation(com.renchao.aop.unit_demo.Demo_Pointcut.MyAnnotation)");
		System.out.println(pointcut02.matches(T1.class.getMethod("m1"), T1.class));
		System.out.println(pointcut02.matches(T1.class.getMethod("m2"), T1.class));


	}

	static class T1 {
		public void m1() {
			System.out.println("目标方法 m1 ...");
		}
		@MyAnnotation
		public void m2() {
			System.out.println("目标方法 m2 ...");
		}
	}

	@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface MyAnnotation {
	}
}
