package com.renchao.ioc.bean;

import org.springframework.beans.factory.annotation.Autowired;

public class Bean1 {

	@Autowired
	public Bean2 bean2;

	public Bean1() {
		System.out.println("Bean1实例化");
	}

	public void m1() {
		System.out.println("Bean1  # m1 执行。。。。。。");
	}
}
