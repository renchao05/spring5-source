package com.renchao.annotation.pack04;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author ren_chao
 * @since 2024-08-22
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		// 可以直接对BeanDefinitionRegistry进行操作
		registry.registerBeanDefinition("bean041", new RootBeanDefinition(Bean041.class));
	}
}
