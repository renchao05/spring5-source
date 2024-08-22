package com.renchao.ioc;

import com.renchao.ioc.bean.Bean1;
import com.renchao.ioc.bean.Bean2;
import com.renchao.ioc.bean.Bean3;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class Demo02_ApplicationContext {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("b02.xml");
		// ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("b01.xml");
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

		Arrays.stream(context.getBeanFactory().getBeanDefinitionNames()).forEach(System.out::println);

	}

	@Configuration
	static class Config {
		@Bean
		public Bean1 bean1() {
			return new Bean1();
		}

		@Bean
		public Bean2 bean2() {
			return new Bean2();
		}

		@Bean
		public Bean3 bean3() {
			return new Bean3();
		}

	}

}
