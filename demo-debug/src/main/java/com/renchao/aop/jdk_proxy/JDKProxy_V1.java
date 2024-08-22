package com.renchao.aop.jdk_proxy;

/**
 * 最简单版本
 * @author ren_chao
 * @since 2024-08-22
 */
public class JDKProxy_V1 {
	public static void main(String[] args) {
		$Proxy0 proxy = new $Proxy0();
		proxy.foo();

	}

	static class $Proxy0 implements Target {
		@Override
		public void foo() {
			// 功能增强
			System.out.println("前置通知 >>>>>");
			// 调用目标方法
			new TargetImpl().foo();
		}
	}

}
