package com.renchao.aop.unit_demo;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ren_chao
 * @since 2024-08-29
 */
public class Demo06_InvocationChain {
    public static void main(String[] args) throws Throwable {
        List<MethodInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new Advice1());
        interceptors.add(new Advice2());


        MyMethodInvocation invocation = new MyMethodInvocation(new T1(), T1.class.getMethod("m1"), new Object[0], interceptors);
        Object proceed = invocation.proceed();
        System.out.println("执行完成：：" + proceed);
    }

    static class Advice1 implements MethodInterceptor {
        @Nullable
        @Override
        public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
            System.out.println("Advice1 前置通知。。。。");
            Object result = invocation.proceed();
            System.out.println("Advice1 后置通知。。。。");
            return result;
        }
    }
    static class Advice2 implements MethodInterceptor {
        @Nullable
        @Override
        public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
            System.out.println("Advice2 前置通知。。。。");
            Object result = invocation.proceed();
            System.out.println("Advice2 后置通知。。。。");
            return result;
        }
    }

    static class MyMethodInvocation implements MethodInvocation {
        private final Object target;
        private final Method method;
        private final Object[] args;
        private final List<MethodInterceptor> interceptors;

        private int count = 0;

        public MyMethodInvocation(Object target, Method method, Object[] args, List<MethodInterceptor> interceptors) {
            this.target = target;
            this.method = method;
            this.args = args;
            this.interceptors = interceptors;
        }

        @Nonnull
        @Override
        public Object[] getArguments() {
            return args;
        }

        @Nullable
        @Override
        public Object proceed() throws Throwable {
            if (count >= interceptors.size()) {
                count = 0;
                return method.invoke(target, args);
            }
            return interceptors.get(count++).invoke(this);
        }

        @Nullable
        @Override
        public Object getThis() {
            return target;
        }

        @Nonnull
        @Override
        public AccessibleObject getStaticPart() {
            return method;
        }

        @Nonnull
        @Override
        public Method getMethod() {
            return method;
        }
    }


    static class T1 {
        public void m1() {
            System.out.println("T1 目标方法 m1 。。。。。。。。。。");
        }
    }


}
