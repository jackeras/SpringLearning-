package com.atguigu.test;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.bean.Book;
import com.atguigu.bean.Car;
import com.atguigu.bean.Person;

class IOCTest {
	
	//private ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc1.xml");
	private ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc2.xml");
	
	@Test
	public void test05() {
		Person person01 = (Person) ioc.getBean("person02");
		
		Car car = person01.getCar();
		System.out.println(car);
		List<Book> books = person01.getBooks();
		System.out.println(books);
		
		/*
		 * �ڲ�bean�ǲ�����id����ȡ��
		 * org.springframework.beans.factory.NoSuchBeanDefinitionException:
		 *  No bean named 'carInner' is defined
		 */
		System.out.println("========");
		//Object bean = ioc.getBean("carInner");
		//System.out.println(bean);
		
		
		Map<String,Object> maps = person01.getMaps();
		System.out.println(maps);
	}
	
	/**
	 *ʵ��4����ȷ��Ϊ�������Ը�ֵ
	 * 		����ʹ��nullֵ��Ĭ���������;���null������������Ĭ��ֵ
	 * 
	 */
	@Test
	public void test04() {
		Person bean = (Person) ioc.getBean("person01");
		System.out.println(bean.getLastName() == null);
		System.out.println("person��car"+bean.getCar());
		Car bean5 = (Car)ioc.getBean("car01");
		
		bean5.setCarName("haha ");
		
		System.out.println("���޸��������е�car�����car����û��"+bean.getCar());
		Car car =bean.getCar();
		
		System.out.println(bean5==car);
		
	}
	
	
	
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
	public void test03() {
		//Person bean = ioc.getBean(Person.class);
		//System.out.println(bean);
		Person bean2 = ioc.getBean("person02",Person.class);
		System.out.println(bean2);
		
		Person bean4 = ioc.getBean("person06",Person.class);
		System.out.println(bean4);
	}
	
	
	
	
	
	
	
	/*
	 * ʵ��2������bean�����ʹ�IOC�����л�ȡbean��ʵ����
	 * ���IOC������������͵�bean�ж�������Ҿͻᱨ��
	 * org.springframework.beans.factory.NoUniqueBeanDefinitionException: 
	 * No qualifying bean of type [com.atguigu.bean.Person] is defined: 
	 * expected single matching bean but found 2: person01,person02
	 */
	@Test
	public void test02() {
		//Person bean = ioc.getBean(Person.class);
		//System.out.println(bean);
		Person bean2 = ioc.getBean("person02",Person.class);
		System.out.println(bean2);
	}
	
	
	
	@Test
	public void test() {
		//ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc.xml");//ioc�����������ļ�����·��֮�¡�
		System.out.println("�����������...");
		Person bean = (Person) ioc.getBean("person01");
		Object bean2 = ioc.getBean("person01");
		System.out.println(bean == bean2);
		
		System.out.println("==============");
		//Object bean3 = ioc.getBean("person03");
	}

}
