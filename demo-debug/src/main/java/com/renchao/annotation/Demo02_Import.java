package com.renchao.annotation;

import com.renchao.annotation.pack04.Bean041;
import com.renchao.annotation.pack04.Config;
import com.renchao.annotation.pack04.MyImportBeanDefinitionRegistrar;
import com.renchao.annotation.pack04.MyImportSelector;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.Import;

/**
 * @author ren_chao
 * @since 2024-08-22
 */
// @Import(Bean041.class) // 导入普通java类
// @Import(Config.class) // 导入配置类
// @Import(MyImportSelector.class) // 使用 ImportSelector
@Import(MyImportBeanDefinitionRegistrar.class) // 使用 ImportBeanDefinitionRegistrar
public class Demo02_Import {
	public static void main(String[] args) {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		beanFactory.registerBeanDefinition("demo01_Import", new RootBeanDefinition(Demo02_Import.class));

		// ConfigurationClassPostProcessor 负责解析@ComponentScan、@Bean、@Import、@ImportResource
		new ConfigurationClassPostProcessor().postProcessBeanFactory(beanFactory);

		beanFactory.preInstantiateSingletons();

		for (String name : beanFactory.getSingletonNames()) {
			System.out.println(name);
		}
	}


}
