package com.renchao.aop.unit_demo;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.MergedAnnotations;

import java.lang.annotation.*;
import java.lang.reflect.Method;

/**
 * 自定义 切点匹配
 * @author ren_chao
 * @since 2024-08-27
 */
public class Demo_Pointcut_Custom {
	public static void main(String[] args) throws NoSuchMethodException {
		// 自定义
		StaticMethodMatcherPointcut pointcut = new StaticMethodMatcherPointcut() {
			@Override
			public boolean matches(Method method, Class<?> targetClass) {
				MergedAnnotations annotations = MergedAnnotations.from(method);
				if (annotations.isPresent(MyAnnotation.class)) {
					return true;
				}
				annotations = MergedAnnotations.from(targetClass, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY);
				if (annotations.isPresent(MyAnnotation.class)) {
					return true;
				}
				return false;
			}
		};

		System.out.println(pointcut.matches(T1.class.getMethod("m1"), T1.class));
		System.out.println(pointcut.matches(T1.class.getMethod("m2"), T1.class));

		System.out.println(pointcut.matches(T2.class.getMethod("m1"), T2.class));
		System.out.println(pointcut.matches(T3.class.getMethod("m1"), T3.class));


	}

	@MyAnnotation
	interface I {
	}
	@MyAnnotation
	static class P {
	}


	static class T3 extends P {
		public void m1() {
			System.out.println("目标方法 m1 ...");
		}
	}

	static class T2 implements I {
		public void m1() {
			System.out.println("目标方法 m1 ...");
		}
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

	@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface MyAnnotation {
	}
}
