package com.atguigu.test;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.bean.Person;

class IOCTest {

	@Test
	public void test() {
		ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc.xml");
		Person bean = (Person) ioc.getBean("person01");
		System.out.println(bean);
	}

}
