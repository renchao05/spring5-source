package com.renchao.aop.unit_demo;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

/**
 * 动态通知调用
 *
 * @author ren_chao
 * @since 2024-08-29
 */
public class Demo07_DynamicAdvice {
    public static void main(String[] args) throws NoSuchMethodException {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(MyAnnotationAwareAspectJAutoProxyCreator.class);
        context.registerBean(MyAspect.class);
        context.refresh();

        MyAnnotationAwareAspectJAutoProxyCreator creator = context.getBean(MyAnnotationAwareAspectJAutoProxyCreator.class);
        List<Advisor> list = creator.findEligibleAdvisors(T1.class, "target");
        list.forEach(System.out::println);

        T1 t1 = new T1();
        ProxyFactory factory = new ProxyFactory(t1);
        factory.addAdvisors(list);
        T1 proxy = (T1) factory.getProxy();
        proxy.m1(100);

        System.out.println("=================");
        // MethodBeforeAdviceInterceptor 普通环绕通知
        // InterceptorAndDynamicMethodMatcher 对应动态通知before2，里面包含通知和切面信息
        List<Object> interceptionList = factory.getInterceptorsAndDynamicInterceptionAdvice(T1.class.getMethod("m1", int.class), T1.class);
        interceptionList.forEach(System.out::println);
    }

    @Aspect
    static class MyAspect {
        // 静态通知调用，不带参数绑定，执行时不需要切点
        @Before("execution(* m1(..))")
        public void before1() {
            System.out.println("before1");
        }

        // 动态通知调用，需要参数绑定，执行时还需要切点对象
        @Before("execution(* m1(..)) && args(x)")
        public void before2(int x) {
            System.out.printf("before2(%d)%n", x);
        }
    }


    static class T1 {
        public void m1(int i) {
            System.out.println("T1 目标方法 m1 。。。。。。。。。。::" + i);
        }
    }

    static class MyAnnotationAwareAspectJAutoProxyCreator extends AnnotationAwareAspectJAutoProxyCreator {
        @Override
        protected List<Advisor> findEligibleAdvisors(Class<?> beanClass, String beanName) {
            return super.findEligibleAdvisors(beanClass, beanName);
        }
    }


}
