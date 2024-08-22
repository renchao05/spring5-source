package com.renchao.aop.jdk_proxy;

/**
 * 增加InvocationHandler接口，抽取目标对象
 *
 * @author ren_chao
 * @since 2024-08-22
 */
public class JDKProxy_V2 {
	public static void main(String[] args) {
		TargetImpl target = new TargetImpl();
		$Proxy0 proxy = new $Proxy0(new InvocationHandler() {
			@Override
			public void invoke() {
				// 功能增强
				System.out.println("前置通知 >>>>>");
				// 调用目标方法
				target.foo();
			}
		});
		proxy.foo();

	}

	static class $Proxy0 implements Target {
		private final InvocationHandler ih;

		public $Proxy0(InvocationHandler ih) {
			this.ih = ih;
		}

		@Override
		public void foo() {
			ih.invoke();
		}
	}

	interface InvocationHandler {
		void invoke();
	}

	static class TargetImpl implements JDKProxy_V1.Target {
		@Override
		public void foo() {
			System.out.println("目标方法执行。。。");
		}
	}

	interface Target {
		void foo();
	}


}
