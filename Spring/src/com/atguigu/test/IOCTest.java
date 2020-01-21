package com.atguigu.test;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.bean.Person;

class IOCTest {
	
	/**
	 * ���ڵļ������⣺
	 * 1��src��Դ�����ʼ��·������ʼ·���Ŀ�ʼ
	 * 	����Դ�������Ķ������ᱻ�ϲ�������·�����棬
	 * 	java./bin/
	 * 	web./WEB-INF/
	 * 2)����������©������
	 * 3)�ȵ����ڴ��������ļ���
	 * 4)Spring�������ӹ��˱�־��s����
	 * 
	 * ����ϸ�ڣ�
	 * 1)ApplicationContext�����ýӿ�
	 * ClassPathXmlApplicationContext������FileSystemXmlApplicationContext
	 * 2)��������ע���˶�һ�����������Ҳ�������а���id�õ����������Ķ���
	 * 		����Ĵ���������������ɵ�
	 * 		personʲôʱ�򴴽��ģ�
	 * 		�����еĶ���Ĵ�����������������ɵ�ʱ��ʹ����ˡ�
	 * 3)ͬһ�������ioc�������ǵ�ʵ���ģ�����������ɶ��Ѿ���������
	 * 4)���������û������������ȡ�����
	 * org.springframework.beans.factory.NoSuchBeanDefinitionException: 
	 * No bean named 'person03' is defined
	 * 5)ioc�ڴ��������������ʱ�򣬣�property��������setter����Ϊjavabean���Խ��и���
	 * 6)javabean������������ʲô�����ģ�getter/setter��������������setȥ��������һ������ĸСд����������
	 * 		private String lastName
	 * 		���е�getter/setter���Զ�����
	 */

	@Test
	public void test() {
		ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc.xml");//ioc�����������ļ�����·��֮�¡�
		System.out.println("�����������...");
		Person bean = (Person) ioc.getBean("person01");
		Object bean2 = ioc.getBean("person01");
		System.out.println(bean == bean2);
		
		System.out.println("==============");
		Object bean3 = ioc.getBean("person03");
	}

}
