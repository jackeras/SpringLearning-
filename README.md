# SpringLearning-
SpringLearning



# 一、SpringIOC的学习

2020.1.21更新

## 实验一：通过IOC容器创建对象，并为属性赋值

1.导包

核心容器

​	1)spring-beans-4.0.0.RELEASE.jar

​	2)spring-context-4.0.0.RELEASE.jar

​	3)spring-core-4.0.0.RELEASE.jar

​	4)spring-expression-4.0.0.RELEASE.jar

Spring运行时候依赖一个日志包，没有就报错

​	5)commons-logging-1.1.3.jar

2.写配置

​	1)Spring的配置文件中，集合了Spring的IOC容器管理的所有组件；

​	2)创建一个Spring Bean Configuration File（Spring的bean配置文件）

​	3)

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

4.存在的几个问题：

1）src：源码包开始的路径，初始路径的开始

​	所有源码包里面的东西都会被合并放在类路径里面，

​			java：/bin/

​			web：/WEB-INF/

2)导包，不能漏了依赖

3)先导包在创建配置文件

4)Spring的容器接管了标志了s的类

5.几个细节：

1)ApplicationContext可以用接口

​		ClassPathXmlApplicationContext或者是FileSystemXmlApplicationContext

2)给容器中注册了恶一个组件，我们也从容器中按照id拿到了这个组件的对象

​		组件的创建工作是容器完成的

​		person什么时候创建的？

​		容器中的对象的创建是在氢气创建完成的时候就创建了。

3)同一个组件在ioc容器中是单实例的，容器启动完成都已经创建好了

4)容器中如果没有这个组件，获取组件？

```
org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'person03' is defined
```

5)ioc在创建这个组件对象的时候，（property）会利用setter方法为javabean属性进行复制

6)javabean的属性名是有什么决定的？getter/setter方法是属性名，set去掉后面那一串首字母小写就是属性名

​		private String lastName

​		所有的getter/setter都自动生成



