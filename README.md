# SpringLearning-
SpringLearning



# 一、SpringIOC的学习

2020.1.21更新

## 实验一：HelloWorld

1.导包
核心容器
	1)spring-beans-4.0.0.RELEASE.jar
	2)spring-context-4.0.0.RELEASE.jar
	3)spring-core-4.0.0.RELEASE.jar
	4)spring-expression-4.0.0.RELEASE.jar
Spring运行时候依赖一个日志包，没有就报错
	5)commons-logging-1.1.3.jar
2.写配置
	1)Spring的配置文件中，集合了Spring的IOC容器管理的所有组件；
	2)创建一个Spring Bean Configuration File（Spring的bean配置文件）
	3)

```
<bean id="person01" class="com.atguigu.bean.Person">
		<property name="lastName" value="张三"></property>
		<property name="age" value="18"></property>
		<property name="email" value="zhangsan@atgui.com"></property>
		<property name="gender" value="男"></property>
	  </bean>
```

3.测试

```
@Test
	public void test() {
		ApplicationContext ioc = new ClassPathXmlApplicationContext("ioc.xml");
		Person bean = (Person) ioc.getBean("person01");
		System.out.println(bean);
	}
```



