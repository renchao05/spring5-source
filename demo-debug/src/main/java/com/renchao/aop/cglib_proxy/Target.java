package com.renchao.aop.cglib_proxy;

/**
 * @author ren_chao
 * @since 2024-08-23
 */
public class Target {
	public void save() {
		System.out.println("调用目标方法。。。。save()");
	}
	public void save(int i) {
		System.out.println("调用目标方法。。。。save(int i)  ::" + i);
	}

	public void save(long l) {
		System.out.println("调用目标方法。。。。save(long l)  ::" + l);
	}
}
