package com.renchao.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.testfixture.servlet.MockHttpServletRequest;
import org.springframework.web.testfixture.servlet.MockHttpServletResponse;
import org.springframework.web.testfixture.servlet.MockServletConfig;
import org.springframework.web.testfixture.servlet.MockServletContext;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

/**
 * @author ren_chao
 * @since 2024-08-28
 */
public class Demo03_RequestMappingHandlerAdapter {
	public static void main(String[] args) throws Exception {
		GenericWebApplicationContext context = new GenericWebApplicationContext();
		context.registerBean(RequestMappingHandlerMapping.class);
		context.registerBean(RequestMappingHandlerAdapter.class);
		context.registerBean(MyController.class);

		context.refresh();

		DispatcherServlet servlet = new DispatcherServlet(context);
		// 由 Tomcat 实例化
		servlet.init(new MockServletConfig(new MockServletContext(""), "test"));

		System.out.println("==============test01===========");
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/test01");
		request.setParameter("name", "张三");
		MockHttpServletResponse response = new MockHttpServletResponse();
		servlet.service(request, response);
		System.out.println("test01响应结果===========================");
		System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));

	}



	@Controller
	static class MyController {

		@GetMapping("/test01")
		@ResponseBody
		public String test01(@RequestParam("name") String name) {
			System.out.println("test2::: " + name);
			return "==========test2 msg==========";
		}

		// 会调用对应的返回值处理器（CallableMethodReturnValueHandler），然后再由返回值处理器调用ServletAPI
		// 这里Demo因为不是在Tomcat容器中，所以会报错。
		// 异步请求处理的关键在于你的控制器方法的返回类型。以下几种情况会触发异步处理：
		// Callable<V>: 返回一个Callable对象。Spring MVC会在后台线程执行这个Callable。
		// DeferredResult<T>: 返回一个DeferredResult对象，可以在后台线程设置结果，完成请求的处理。
		// WebAsyncTask<V>: 允许配置超时、回调等，同时也是返回一个Callable对象。
		// ListenableFuture<V>: 也是一种异步结果，可以用来处理异步任务。
		@GetMapping("/async-callable")
		public Callable<String> asyncCallable() {
			return () -> {
				// 模拟长时间任务
				Thread.sleep(10000);
				return "异步结果.........";
			};
		}

	}



}
