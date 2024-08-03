package com.renchao.ioc.post.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("========= MyBeanDefinitionRegistryPostProcessor #### postProcessBeanFactory");
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		// 在BeanDefinitionRegistryPostProcessor中注册BeanDefinitionRegistryPostProcessor
		// 验证 invokeBeanFactoryPostProcessors
		AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
				.genericBeanDefinition(My02BeanDefinitionRegistryPostProcessor.class)
						.setScope("singleton").getBeanDefinition();
		registry.registerBeanDefinition("my02BeanDefinitionRegistryPostProcessor", beanDefinition);
		System.out.println("========= MyBeanDefinitionRegistryPostProcessor #### postProcessBeanDefinitionRegistry");
	}
}
