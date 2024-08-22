package com.renchao.ioc.bean;

import java.util.List;

public class Bean3 {
	private Bean1 bean1;
	private Bean2 bean2;
	private List<Bean1> list;
	private String testStr;
	private Integer testInt;

	public Bean1 getBean1() {
		return bean1;
	}

	public void setBean1(Bean1 bean1) {
		this.bean1 = bean1;
	}

	public Bean2 getBean2() {
		return bean2;
	}

	public void setBean2(Bean2 bean2) {
		this.bean2 = bean2;
	}

	public List<Bean1> getList() {
		return list;
	}

	public void setList(List<Bean1> list) {
		this.list = list;
	}

	public String getTestStr() {
		return testStr;
	}

	public void setTestStr(String testStr) {
		this.testStr = testStr;
	}

	public Integer getTestInt() {
		return testInt;
	}

	public void setTestInt(Integer testInt) {
		this.testInt = testInt;
	}
}
