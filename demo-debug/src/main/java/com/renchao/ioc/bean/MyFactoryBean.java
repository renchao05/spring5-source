package com.renchao.ioc.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author ren_chao
 */
public class MyFactoryBean implements FactoryBean<Bean1> {

	@Override
	public Bean1 getObject() throws Exception {
		return new Bean1();
	}

	@Override
	public Class<?> getObjectType() {
		return Bean1.class;
	}
}
