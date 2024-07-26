package com.renchao.ioc.bean;

import org.springframework.beans.factory.annotation.Autowired;

public class Bean2 {

	@Autowired
	public Bean1 bean1;

	public Bean2() {
		System.out.println("Bean2实例化");
	}


}
