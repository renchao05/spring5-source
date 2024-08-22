package com.renchao.aop.jdk_proxy;

/**
 * @author ren_chao
 * @since 2024-08-22
 */
public class TargetImpl implements Target {
	@Override
	public void foo() {
		System.out.println("目标方法执行。。。");
	}
}
