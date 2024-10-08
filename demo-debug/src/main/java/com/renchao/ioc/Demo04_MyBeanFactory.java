package com.renchao.ioc;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 简单模拟 DefaultSingletonBeanRegistry 解决循环依赖
 *
 * @author ren_chao
 * @since 2024-08-09
 */
public class Demo04_MyBeanFactory extends DefaultSingletonBeanRegistry {
	/** 模拟 Bean 定义 */
	private final Map<String, Class<?>> beanClassMap = new HashMap<>();

	public static void main(String[] args) {
		Demo04_MyBeanFactory factory = new Demo04_MyBeanFactory();
		factory.registerBeanInfo("bean1", Bean1.class);
		factory.registerBeanInfo("bean2", Bean2.class);

		Bean1 bean1 = (Bean1) factory.getBean("bean1");

		System.out.println(bean1);
		System.out.println(bean1.bean2);
	}


	public Object getBean(String name) {
		Object bean = getSingleton(name);
		if (bean != null) {
			return bean;
		}
		return getSingleton(name, () -> createBean(name));
	}


	/**
	 * Bean 简单的生命周期
	 * 实例化 -> 属性注入 -> Bean创建完成
	 */
	public Object createBean(String name) {
		Class<?> aClass = beanClassMap.get(name);
		// 实例化
		Object o = BeanUtils.instantiateClass(aClass);

		// 放入原始bean到三级缓存
		addSingletonFactory(name, () -> getEarlyBean(o));

		// 属性注入
		propertyInjection(o);

		// 其他逻辑，如AOP

		Object singleton = getSingleton(name, false);
		return singleton != null ? singleton : o;
	}

	/**
	 * 模拟属性注入，为了简单，这里会对对象的所有属性都进行注入
	 */
	public void propertyInjection(Object o) {
		Class<?> aClass = o.getClass();
		Field[] fields = aClass.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			Object bean = getBean(fieldName);
			try {
				field.setAccessible(true);
				field.set(o, bean);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 获取早期 Bean
	 */
	public Object getEarlyBean(Object o) {
		// 其他逻辑，如 AOP
		return o;
	}


	/**
	 * 注册Bean定义信息
	 */
	public void registerBeanInfo(String name, Class<?> clazz) {
		beanClassMap.put(name, clazz);
	}



	static class Bean1 {
		public Bean2 bean2;
	}

	static class Bean2 {
		public Bean1 bean1;
	}
}
