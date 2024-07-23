package com.renchao.ioc;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class Demo01_BeanFactory {
	public static void main(String[] args) {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// bean 的定义（class, scope, 初始化, 销毁）
		AbstractBeanDefinition beanDefinition =
				BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
		beanFactory.registerBeanDefinition("config", beanDefinition);
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

		@Bean
		public Bean4 bean4() {
			return new Bean4();
		}
	}

	static class Bean1 {

	}

	static class Bean2 {

	}

	static class Bean3 {

	}

	static class Bean4 {

	}



}

