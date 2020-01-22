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
		 * 内部bean是不能用id来获取的
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
	 *实验4：正确的为各种属性赋值
	 * 		测试使用null值、默认引用类型就是null，基本类型是默认值
	 * 
	 */
	@Test
	public void test04() {
		Person bean = (Person) ioc.getBean("person01");
		System.out.println(bean.getLastName() == null);
		System.out.println("person的car"+bean.getCar());
		Car bean5 = (Car)ioc.getBean("car01");
		
		bean5.setCarName("haha ");
		
		System.out.println("我修改了容器中的car，你的car变了没？"+bean.getCar());
		Car car =bean.getCar();
		
		System.out.println(bean5==car);
		
	}
	
	
	
	/**
	 * 存在的几个问题：
	 * 1）src：源码包开始的路径，初始路径的开始
	 * 	所有源码包里面的东西都会被合并放在类路径里面，
	 * 	java./bin/
	 * 	web./WEB-INF/
	 * 2)导包，不能漏了依赖
	 * 3)先导包在创建配置文件，
	 * 4)Spring的容器接管了标志了s的类
	 * 
	 * 几个细节：
	 * 1)ApplicationContext可以用接口
	 * ClassPathXmlApplicationContext或者是FileSystemXmlApplicationContext
	 * 2)给容器中注册了恶一个组件，我们也从容器中按照id拿到了这个组件的对象
	 * 		组件的创建工作是容器完成的
	 * 		person什么时候创建的？
	 * 		容器中的对象的床架是在氢气创建完成的时候就创建了。
	 * 3)同一个组件在ioc容器中是单实例的，容器启动完成都已经创建好了
	 * 4)容器中如果没有这个组件，获取组件？
	 * org.springframework.beans.factory.NoSuchBeanDefinitionException: 
	 * No bean named 'person03' is defined
	 * 5)ioc在创建这个组件对象的时候，（property）会利用setter方法为javabean属性进行复制
	 * 6)javabean的属性名是有什么决定的？getter/setter方法是属性名，set去掉后面那一串首字母小写就是属性名
	 * 		private String lastName
	 * 		所有的getter/setter都自动生成
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
	 * 实验2：根据bean的类型从IOC容器中获取bean的实例★
	 * 如果IOC容器中这个类型的bean有多个，查找就会报错
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
		//ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc.xml");//ioc容器的配置文件在类路径之下。
		System.out.println("容器启动完成...");
		Person bean = (Person) ioc.getBean("person01");
		Object bean2 = ioc.getBean("person01");
		System.out.println(bean == bean2);
		
		System.out.println("==============");
		//Object bean3 = ioc.getBean("person03");
	}

}
