package com.renchao.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.testfixture.servlet.MockHttpServletRequest;
import org.springframework.web.testfixture.servlet.MockHttpServletResponse;
import org.springframework.web.testfixture.servlet.MockServletConfig;
import org.springframework.web.testfixture.servlet.MockServletContext;

import java.nio.charset.StandardCharsets;

/**
 * @author ren_chao
 * @since 2024-08-28
 */
public class Demo01 {
	public static void main(String[] args) throws Exception {
		GenericWebApplicationContext context = new GenericWebApplicationContext();
		context.registerBean(RequestMappingHandlerMapping.class);
		context.registerBean(MyController.class);
		context.refresh();

		// 解析 @RequestMapping 以及派生注解，生成路径与控制器方法的映射关系, 在初始化时生成
		RequestMappingHandlerMapping handlerMapping = context.getBean(RequestMappingHandlerMapping.class);

		// 映射结果
		System.out.println("=================映射结果");
		handlerMapping.getHandlerMethods().forEach((k, v) -> System.out.println(k + "=" + v));

		DispatcherServlet servlet = new DispatcherServlet(context);
		servlet.init(new MockServletConfig(new MockServletContext(""), "test"));

		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/test2");
		request.setParameter("name", "张三");
		MockHttpServletResponse response = new MockHttpServletResponse();

		servlet.service(request, response);

		System.out.println("响应结果===========================");
		System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
	}


	@Controller
	static class MyController {
		@GetMapping("/test1")
		public ModelAndView test1() {
			System.out.println("test1()");
			return null;
		}

		@PostMapping("/test2")
		@ResponseBody
		public String test2(@RequestParam("name") String name) {
			System.out.println("test2::: " + name);
			return "==========test2 msg==========";
		}

		@PutMapping("/test3")
		public ModelAndView test3(String token) {
			System.out.println("test3::: " + token);
			return null;
		}

		@RequestMapping("/test4")
		public String test4() {
			System.out.println("==========test4");
			return "==========test4";
		}
	}



}
