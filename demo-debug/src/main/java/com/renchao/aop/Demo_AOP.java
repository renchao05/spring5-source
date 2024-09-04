package com.renchao.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;

/**
 * @author ren_chao
 * @since 2024-08-30
 */
public class Demo_AOP {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("bean1", new RootBeanDefinition(Bean1.class));
        beanFactory.registerBeanDefinition("bean2", new RootBeanDefinition(Bean2.class));
        beanFactory.registerBeanDefinition("myAspect", new RootBeanDefinition(MyAspect.class));
        beanFactory.registerBeanDefinition("myAspect2", new RootBeanDefinition(MyAspect2.class));
        beanFactory.registerBeanDefinition("config", new RootBeanDefinition(Config.class));
		new ConfigurationClassPostProcessor().postProcessBeanFactory(beanFactory);
        beanFactory.registerBeanDefinition("autowiredAnnotationBeanPostProcessor",
                new RootBeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
        beanFactory.registerBeanDefinition("annotationAwareAspectJAutoProxyCreator",
                new RootBeanDefinition(AnnotationAwareAspectJAutoProxyCreator.class));
        beanFactory.addBeanPostProcessors(beanFactory.getBeansOfType(BeanPostProcessor.class).values());

		Bean1 bean = beanFactory.getBean(Bean1.class);
        bean.m2();
        System.out.println(bean.getBean2());

    }

    @Aspect
    static class MyAspect {
        @Before("execution(* m1(..))")
        public void before1() {
            System.out.println("before1");
        }

        @Before("execution(* m2(..))")
        public void before2() {
            System.out.println("before2");
        }

    }

    @Aspect
    static class MyAspect2 {
        @After("execution(* m3(..))")
        public void After1() {
            System.out.println("After1");
        }

    }

	@Configuration
	static class Config {
		@Bean // 低级切面
		public Advisor myAdvisor() {
			AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
			pointcut.setExpression("execution(* m2())");

			MethodInterceptor advice = invocation -> {
				System.out.println("myAdvice before...");
				Object result = invocation.proceed();
				System.out.println("myAdvice after...");
				return result;
			};

			return new DefaultPointcutAdvisor(pointcut, advice);
		}

	}



	static class Bean1 {
        @Autowired
        private Bean2 bean2;
        public void m2() {
            System.out.println("Bean1 目标方法 m1 。。。。。。。。。。::");
        }

		public Bean2 getBean2() {
			return bean2;
		}

		public void setBean2(Bean2 bean2) {
			this.bean2 = bean2;
		}
	}


    static class Bean2 {
        @Autowired
        private Bean1 bean1;
    }
}

