package com.renchao.aop.cglib_proxy;

import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;

/**
 * 在 Cglib 中由 Cglib 运行时直接生成字节码
 *
 * @author ren_chao
 * @since 2024-08-23
 */
public class CProxy_v1 extends Target {
	private static final Method save0;
	private static final Method save1;
	private static final Method save2;

	static {
		try {
			save0 = Target.class.getMethod("save");
			save1 = Target.class.getMethod("save", int.class);
			save2 = Target.class.getMethod("save", long.class);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	MethodInterceptor methodInterceptor;

	public CProxy_v1(MethodInterceptor methodInterceptor) {
		this.methodInterceptor = methodInterceptor;
	}

	@Override
	public void save() {
		try {
			methodInterceptor.intercept(this, save0, new Object[0], null);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void save(int i) {
		try {
			methodInterceptor.intercept(this, save1, new Object[]{i}, null);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void save(long l) {
		try {
			methodInterceptor.intercept(this, save2, new Object[]{l}, null);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}
