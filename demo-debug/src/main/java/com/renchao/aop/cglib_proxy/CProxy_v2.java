package com.renchao.aop.cglib_proxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 在 Cglib 中由 Cglib 运行时直接生成字节码
 * 利用 MethodProxy
 * MethodProxy 是利用 FastClass 内部没有反射调用
 *
 * @author ren_chao
 * @since 2024-08-23
 */
public class CProxy_v2 extends Target {
	private static final Method save0;
	private static final Method save1;
	private static final Method save2;
	private static final MethodProxy save0Proxy;
	private static final MethodProxy save1Proxy;
	private static final MethodProxy save2Proxy;

	static {
		try {
			save0 = Target.class.getMethod("save");
			save1 = Target.class.getMethod("save", int.class);
			save2 = Target.class.getMethod("save", long.class);
			save0Proxy = MethodProxy.create(Target.class, CProxy_v2.class, "()V", "save", "saveSuper");
			save1Proxy = MethodProxy.create(Target.class, CProxy_v2.class, "(I)V", "save", "saveSuper");
			save2Proxy = MethodProxy.create(Target.class, CProxy_v2.class, "(J)V", "save", "saveSuper");
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	MethodInterceptor methodInterceptor;

	public CProxy_v2(MethodInterceptor methodInterceptor) {
		this.methodInterceptor = methodInterceptor;
	}

	// 原始功能的方法
	public void saveSuper() {
		super.save();
	}

	public void saveSuper(int i) {
		super.save(i);
	}

	public void saveSuper(long l) {
		super.save(l);
	}

	// 下面是带增强功能的方法
	@Override
	public void save() {
		try {
			methodInterceptor.intercept(this, save0, new Object[0], save0Proxy);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void save(int i) {
		try {
			methodInterceptor.intercept(this, save1, new Object[]{i}, save1Proxy);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void save(long l) {
		try {
			methodInterceptor.intercept(this, save2, new Object[]{l}, save2Proxy);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}
