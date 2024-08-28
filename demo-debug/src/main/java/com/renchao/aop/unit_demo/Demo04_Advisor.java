package com.renchao.aop.unit_demo;

import org.aopalliance.intercept.MethodInterceptor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

/**
 * @author ren_chao
 * @since 2024-08-28
 */
public class Demo04_Advisor {
	public static void main(String[] args) {
		GenericApplicationContext context = new GenericApplicationContext();
		context.registerBean("myAspect", MyAspect.class);
		context.registerBean("config", Config.class);
		context.registerBean(ConfigurationClassPostProcessor.class);
		context.registerBean(MyAnnotationAwareAspectJAutoProxyCreator.class);
		context.refresh();

		MyAnnotationAwareAspectJAutoProxyCreator creator = context.getBean(MyAnnotationAwareAspectJAutoProxyCreator.class);
		// 找到与T1匹配的所有低价切面Advisor
		List<Advisor> advisors = creator.findEligibleAdvisors(T1.class, "t1");
		advisors.forEach(System.out::println);
		System.out.println("=======================");

		T1 t1 = new T1();
		T2 t2 = new T2();

		// 判断对象是否有资格被代理，如果有，则返回对应的代理对象，如果没有，返回原对象
		// 内部也会调用findEligibleAdvisors方法
		Object o1 = creator.wrapIfNecessary(t1, "t1", "t1");
		Object o2 = creator.wrapIfNecessary(t2, "t2", "t2");

		// 因为T1有资格代理，会返回代理对象，这里是false
		System.out.println(t1 == o1);
		// 因为T2没有资格代理，会返回原对象，这里是true
		System.out.println(t2 == o2);
	}

	/**
	 * 高级切面，最终都会转换为低级切面
	 */
	@Aspect
	static class MyAspect {
		@Before("execution(* m1())")
		public void before1() {
			System.out.println("MyAspect  before1...");
		}

		@Before("execution(* m1())")
		public void before2() {
			System.out.println("MyAspect  before2...");
		}
	}


	@Configuration
	static class Config {
        @Bean // 低级切面
        public Advisor myAdvisor() {
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
            pointcut.setExpression("execution(* m1())");

			MethodInterceptor advice = invocation -> {
				System.out.println("myAdvice before...");
				Object result = invocation.proceed();
				System.out.println("myAdvice after...");
				return result;
			};

            return new DefaultPointcutAdvisor(pointcut, advice);
        }

	}

	/**
	 * 通过重写方法，以访问受保护的方法
	 */
	static class MyAnnotationAwareAspectJAutoProxyCreator extends AnnotationAwareAspectJAutoProxyCreator {
		@Override
		public Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
			return super.wrapIfNecessary(bean, beanName, cacheKey);
		}

		@Override
		public List<Advisor> findEligibleAdvisors(Class<?> beanClass, String beanName) {
			return super.findEligibleAdvisors(beanClass, beanName);
		}
	}




	static class T1 {
		public void m1() {
			System.out.println("目标方法 m1 ...");
		}

		public void m2() {
			System.out.println("目标方法 m2 ...");
		}
	}

	static class T2 {
		public void m3() {
			System.out.println("目标方法 m2 ...");
		}
	}

}
