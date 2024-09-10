package com.renchao.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.testfixture.servlet.MockHttpServletRequest;
import org.springframework.web.testfixture.servlet.MockHttpServletResponse;
import org.springframework.web.testfixture.servlet.MockServletConfig;
import org.springframework.web.testfixture.servlet.MockServletContext;

import java.nio.charset.StandardCharsets;

/**
 * @author ren_chao
 * @since 2024-08-28
 */
public class Demo04_Exception {
	public static void main(String[] args) throws Exception {
		GenericWebApplicationContext context = new GenericWebApplicationContext();
		context.registerBean(RequestMappingHandlerAdapter.class);
		context.registerBean(MyController.class);
		context.registerBean(MyControllerAdvice.class);
		context.refresh();

		DispatcherServlet servlet = new DispatcherServlet(context);
		// 由 Tomcat 实例化
		servlet.init(new MockServletConfig(new MockServletContext(""), "exception"));

		System.out.println("==============Exception===========");
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/exception");
		request.setParameter("code", "1");
		// request.setParameter("code", "0");
		MockHttpServletResponse response = new MockHttpServletResponse();
		servlet.service(request, response);
		System.out.println("Exception响应结果===========================");
		System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));

	}



	@Controller
	static class MyController {

		@GetMapping("/exception")
		@ResponseBody
		public String test01(@RequestParam("code") Integer code) {
			if (code == 1) {
				throw new MyException();
			}
			if (code == 0) {
				throw new MyException0();
			}
			System.out.println("exception::: ");
			return "==========exception msg==========";
		}

		@ExceptionHandler(MyException0.class)
		@ResponseBody
		public String testException() {

			System.out.println("发生了异常：MyException0");

			return "Exception：：：MyException0";
		}

	}

	@ControllerAdvice
	static class MyControllerAdvice {

		@ExceptionHandler(MyException.class)
		@ResponseBody
		public String testException() {

			System.out.println("发生了异常：MyException");

			return "Exception：：：MyException";
		}

	}

	static class MyException extends RuntimeException {
	}
	static class MyException0 extends RuntimeException {
	}






}
