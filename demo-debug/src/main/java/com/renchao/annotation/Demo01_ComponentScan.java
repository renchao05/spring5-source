package com.renchao.annotation;

import com.renchao.annotation.pack02.Pack02;
import com.renchao.annotation.pack03.Bean032;
import com.renchao.annotation.pack03.CustomAnnotation;
import com.renchao.annotation.pack03.MyTypeFilter;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.FilterType;

/**
 * @author ren_chao
 * @since 2024-08-22
 */
// @ComponentScan
// @ComponentScan("com.renchao.annotation.pack01")
// @ComponentScan(basePackageClasses = {Pack02.class})
@ComponentScan(basePackages = {"com.renchao.annotation.pack03"},
		// 通过自定义注解解析扫描 Bean031
		includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = CustomAnnotation.class),
		// 排除Bean032
		excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Bean032.class),
				// 自定义过滤器，根据MyTypeFilter的逻辑过滤
				@ComponentScan.Filter(type = FilterType.CUSTOM, classes = MyTypeFilter.class)}
)
public class Demo01_ComponentScan {
	public static void main(String[] args) {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		beanFactory.registerBeanDefinition("demo01_ComponentScan", new RootBeanDefinition(Demo01_ComponentScan.class));

		// ConfigurationClassPostProcessor 负责解析@ComponentScan、@Bean、@Import、@ImportResource
		new ConfigurationClassPostProcessor().postProcessBeanFactory(beanFactory);

		beanFactory.preInstantiateSingletons();

		for (String name : beanFactory.getSingletonNames()) {
			System.out.println(name);
		}
	}

}
