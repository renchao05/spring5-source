package com.renchao.ioc;

import com.renchao.ioc.bean.Bean1;
import com.renchao.ioc.bean.Bean2;
import com.renchao.ioc.bean.Bean3;
import com.renchao.ioc.bean.MyFactoryBean;
import com.renchao.ioc.post.bean.MyInstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

public class Demo01_BeanFactory {
	public static void main(String[] args) {
		test03AutowiredAnnotationBeanPostProcessor();
		// test02Supplier();
		// test02InstantiationAwareBeanPostProcessor();
		// test02FactoryBean();
		// test01();
	}

	private static void test03AutowiredAnnotationBeanPostProcessor() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		beanFactory.registerSingleton("bean2", new Bean2());

		AbstractBeanDefinition beanDefinition =
				BeanDefinitionBuilder.genericBeanDefinition(BeanDependBean2.class).setScope("singleton").getBeanDefinition();
		beanFactory.registerBeanDefinition("beanDependBean2", beanDefinition);
		beanFactory.registerBeanDefinition("autowiredAnnotationBeanPostProcessor",
				new RootBeanDefinition(AutowiredAnnotationBeanPostProcessor.class));

		beanFactory.addBeanPostProcessor(beanFactory.getBean(AutowiredAnnotationBeanPostProcessor.class));
		BeanDependBean2 beanDependBean2 = beanFactory.getBean(BeanDependBean2.class);
		System.out.println(beanDependBean2.bean2);
	}

	private static void test02Supplier() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		AbstractBeanDefinition beanDefinition =
				BeanDefinitionBuilder.genericBeanDefinition(Bean1.class).setScope("singleton").getBeanDefinition();
		beanDefinition.setInstanceSupplier(Bean1::new);
		beanFactory.registerBeanDefinition("bean1", beanDefinition);
		Bean1 bean = beanFactory.getBean(Bean1.class);
		System.out.println(bean);
	}

	private static void test02InstantiationAwareBeanPostProcessor() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		AbstractBeanDefinition beanDefinition =
				BeanDefinitionBuilder.genericBeanDefinition(Bean1.class).setScope("singleton").getBeanDefinition();
		beanFactory.registerBeanDefinition("bean1", beanDefinition);
		beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());
		Bean1 bean = beanFactory.getBean(Bean1.class);
		bean.m1();
		System.out.println(bean);
	}

	private static void test02FactoryBean() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		AbstractBeanDefinition beanDefinition =
				BeanDefinitionBuilder.genericBeanDefinition(MyFactoryBean.class).setScope("singleton").getBeanDefinition();
		beanFactory.registerBeanDefinition("myFactoryBean", beanDefinition);
		Object bean = beanFactory.getBean("myFactoryBean");
		System.out.println(bean);
	}

	private static void test01() {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// bean 的定义（class, scope, 初始化, 销毁）
		AbstractBeanDefinition beanDefinition =
				BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
		beanFactory.registerBeanDefinition("config", beanDefinition);
		// 给 BeanFactory 添加一些常用的后处理器 BeanFactoryPostProcessor 和 BeanPostProcessor
		// AnnotatedBeanDefinitionReader 构造器也调用了该方法
		AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);

		// BeanFactory 后处理器主要功能，补充一些 bean 定义
		// 需要调用才能生效
		System.out.println("===========BeanFactory 后处理器");
		beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
			System.out.println(beanFactoryPostProcessor);
			beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
		});

		// Bean 后处理器, 针对 bean 的生命周期的各个阶段提供扩展, 例如 @Autowired @Resource ...
		// 需要先注册，在bean实例化的时候由容器调用
		System.out.println("\n===========Bean 后处理器");
		beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanPostProcessor -> {
			System.out.println(beanPostProcessor);
			beanFactory.addBeanPostProcessor(beanPostProcessor);
		});
		// 准备好所有单例
		System.out.println("\n准备好所有单例");
		beanFactory.preInstantiateSingletons();
		System.out.println("=======================");

		Arrays.stream(beanFactory.getBeanDefinitionNames()).forEach(System.out::println);
		Bean1 bean = beanFactory.getBean(Bean1.class);
		System.out.println(bean.bean2);
	}

	static class BeanDependBean2 {

		// @Autowired
		private Bean2 bean2;

		public BeanDependBean2(Bean2 bean2) {
			this.bean2 = bean2;
		}

		public Bean2 getBean2() {
			return bean2;
		}

		public void setBean2(Bean2 bean2) {
			this.bean2 = bean2;
		}
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

