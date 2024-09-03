package com.renchao.mvc;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.testfixture.servlet.MockHttpServletRequest;
import org.springframework.web.testfixture.servlet.MockHttpServletResponse;
import org.springframework.web.testfixture.servlet.MockServletConfig;
import org.springframework.web.testfixture.servlet.MockServletContext;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ren_chao
 * @since 2024-08-28
 */
public class Demo01_MappingAndAdapter {
	public static void main(String[] args) throws Exception {
		GenericWebApplicationContext context = new GenericWebApplicationContext();
		// 将URL映射到以bean名称为URL的处理器，如 /test01 -> MyController(/test01)
		// 初始化时是查找所有bean名称以/开头的bean作为Handler
		context.registerBean(BeanNameUrlHandlerMapping.class);
		context.registerBean("/test01", MyController.class);

		context.registerBean(MyHandlerMapping.class);
		context.refresh();

		DispatcherServlet servlet = new DispatcherServlet(context);
		// 由 Tomcat 实例化
		servlet.init(new MockServletConfig(new MockServletContext(""), "test"));

		System.out.println("==============test01=====走的BeanNameUrlHandlerMapping======");
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/test01");
		request.setParameter("name", "张三");
		MockHttpServletResponse response = new MockHttpServletResponse();
		servlet.service(request, response);
		System.out.println("test01响应结果===========================");
		System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));

		System.out.println("\n==============test02=====走的MyHandlerMapping======");
		request = new MockHttpServletRequest("GET", "/test02");
		request.setParameter("name", "李四");
		response = new MockHttpServletResponse();
		servlet.service(request, response);
		System.out.println("test02响应结果===========================");
		System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));

	}

	// 实现 Controller 是为了匹配 SimpleControllerHandlerAdapter
	// SimpleControllerHandlerAdapter 是直接调用 Controller 的 handleRequest 方法
	static class MyController implements Controller {
		@Override
		public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
			response.setCharacterEncoding("utf-8");
			String uri = new UrlPathHelper().resolveAndCacheLookupPath(request);
			String name = request.getParameter("name");
			response.getWriter().print(String.format(">>>>> 请求成功。。。:: uri[%s],name[%s]", uri, name));
			return null;
		}
	}


	/**
	 * 也可以自定义一个HandlerAdapter,来配合对应的MyHandlerMapping
	 * 这里就直接使用 Controller ，利用 SimpleControllerHandlerAdapter 来处理了
	 */
	static class MyHandlerMapping implements HandlerMapping, ApplicationContextAware {
		private ApplicationContext applicationContext;
		private final Map<String, Controller> mapping = new HashMap<>();

		@Override

		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			this.applicationContext = applicationContext;
			initMapping();
		}

		@Override
		public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
			String uri = new UrlPathHelper().resolveAndCacheLookupPath(request);
			Controller handler = mapping.get(uri);
			return new HandlerExecutionChain(handler);
		}

		private void initMapping() {
			MyController controller = applicationContext.getBean(MyController.class);
			// 这里为了简单，只使用MyController一个，可以根据需要的逻辑定义
			mapping.put("/test02", controller);
			mapping.put("/test03", controller);
		}


	}






}
