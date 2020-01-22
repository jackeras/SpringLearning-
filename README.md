# SpringLearning-
SpringLearning



# 一、SpringIOC的学习

2020.1.21更新

## 实验一：通过IOC容器创建对象，并为属性赋值★

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

------

2020.1.22更新

## 实验二：根据bean的类型从IOC容器中获取bean的实例★

如果IOC容器中这个类型的bean有多个，查找就会报错

```java
@Test
	public void test02() {
		Person bean = ioc.getBean(Person.class);//用bean类型获取bean实例，但是ioc容器中，有两个这种类型。所以会报错
		System.out.println(bean);
		
		Person bean2 = ioc.getBean("person02",Person.class);//通过getBean的同类名方法来解决，查找bean类型和有类名称的实例
		System.out.println(bean2);
	}
```

```java
org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type [com.atguigu.bean.Person] is defined:expected single matching bean but found 2: person01,person02
```

## 实验3：通过构造器为bean的属性赋值（index,type属性介绍）通过p名称空间为bean赋值

1.通过构造器为bean的属性赋值（index,type属性介绍）

1）在Person类中，创建有参构造器

```
public Person(String lastName, Integer age, String gender, String email) {
		super();
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.email = email;
		System.out.println("有参构造器。。。");
	}
```

2）在配置文件ioc.xml中,配置有参构造器类并赋值（其中constructor-arg为属性赋值）

```
<bean id="person03" class="com.atguigu.bean.Person">
		<!-- 调用有参构造器进行创建对象并赋值 -->
		<!-- public Person(String lastName, Integer age, String gender, String email) -->
		<constructor-arg name="lastName" value="小明"></constructor-arg>
		<constructor-arg name="email" value="xiaoming@atgui.com"></constructor-arg>
		<constructor-arg name="gender" value="男"></constructor-arg>
		<constructor-arg name="age" value="18"></constructor-arg>
	</bean>
```

3）在IOCTest文件中，测试有参构造器

```
@Test
	public void test03() {
		//Person bean = ioc.getBean(Person.class);
		//System.out.println(bean);
		Person bean2 = ioc.getBean("person02",Person.class);
		System.out.println(bean2);

		Person bean3 = ioc.getBean("person03",Person.class);
		System.out.println(bean3);
}
```

#### index,type属性介绍：

index可以起到定位的作用

```
<!-- 可以省略name属性，但是要严格按照构造器参数的位置 -->
	<bean id="person04" class="com.atguigu.bean.Person">
		<constructor-arg value="小花"></constructor-arg>
		<constructor-arg value="18"></constructor-arg>
		<!-- index="3",为参数指定索引，从0开始 -->
		<constructor-arg value="男" index="2"></constructor-arg>
		<constructor-arg value="xiaohua@atgui.com"></constructor-arg>
	</bean>
```

#### 带来的问题：

如果有构造器重载的情况下，ioc配置里，可能产生混乱。例如：

```
public Person(String lastName, Integer age, String gender) {
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		System.out.println("三个参数的构造器。。。age");
	}
	
public Person(String lastName,String email, String gender) {
	this.lastName = lastName;
	this.email = email;
	this.gender = gender;
	System.out.println("三个参数的构造器。。。email");
}
```

```
<bean id="person05" class="com.atguigu.bean.Person">
		<!--ioc容器只能大概根据某种规则分配，但结果往往是不理想的-->
		<constructor-arg value="小丽"></constructor-arg>
		<constructor-arg value="18"></constructor-arg>
		<constructor-arg value="男"></constructor-arg>
	</bean>
```

可以通过type来指定：

```
<bean id="person05" class="com.atguigu.bean.Person">
		<constructor-arg value="小丽"></constructor-arg>
		<constructor-arg value="18" index="1" type="java.lang.Integer"></constructor-arg>
		<constructor-arg value="男"></constructor-arg>
	</bean>
```

2.通过p名称空间为bean赋值

名称空间，在XML中名称空间是用来防止标签重复的。

```
<!--  如何区分别标签name？通过添加前缀来区分
		<book>
			<b:name>西游记</b:name>
			<price>19.98</price>
			<author>
				<a:name>吴承恩</a:name>
				<gender>男</gender>
			</author>
		</book>
		
		带前缀的标签<c:forEach> <jsp:forward>
	-->
```

1）导入名称空间。选中Namespaces，并勾选p名称空间

![image-20200122102621729](C:\Users\试用\AppData\Roaming\Typora\typora-user-images\image-20200122102621729.png)

2）使用P名称空间赋值

```
<bean id="person06" class="com.atguigu.bean.Person"
		p:age="18" p:email="xiaoming@atgui.com" p:lastName="哈哈" p:gender="男">
	</bean>
```



## 实验4：正确的为各种属性赋值

## util名称空间创建集合类型的bean、级联属性赋值。

1、测试使用null值 

默认引用类型就是null，基本类型是默认值

```
<bean id="person01" class="com.atguigu.bean.Person">
</bean>
```

```
@Test
	public void test04() {
		Object bean = ioc.getBean("person01");
		System.out.println(bean);
	}
```

![image-20200122104747518](C:\Users\试用\AppData\Roaming\Typora\typora-user-images\image-20200122104747518.png)



#### 错误赋值null：

```
<bean id="person01" class="com.atguigu.bean.Person">
     	<!-- lastName="null" -->
     	<property name="lastName" value="null"></property>
     </bean>
```

```
@Test
	public void test04() {
		Person bean = (Person) ioc.getBean("person01");
		System.out.println(bean.getLastName() == null);
	}
```

![image-20200122105151002](C:\Users\试用\AppData\Roaming\Typora\typora-user-images\image-20200122105151002.png)

#### 正确赋值null：在property下用null标签赋值

```
<bean id="person01" class="com.atguigu.bean.Person">
     	<!-- lastName="null" -->
     	<property name="lastName" >
     		<!-- 进行复杂的赋值 -->
     		<null></null>
     	</property>
     </bean>
```

2、引用类型赋值（引用其他bean、引用内部bean）

1）引用其他bean

定义一个car类

```
<bean id="car01" class="com.atguigu.bean.Car">
     	<property name="carName" value="宝马"></property>
     	<property name="color" value="绿色"></property>
     	<property name="price" value="30000"></property>
     </bean>
```

在person01中使用ref标签，来引用car01类

```
 <bean id="person01" class="com.atguigu.bean.Person">
     	<!-- lastName="null" -->
     	<property name="lastName" >
     		<!-- 进行复杂的赋值 -->
     		<null></null>
     	</property>
     	<!-- ref,代表引用外面的一个值 -->
     	<property name="car" ref="car01"></property>
     </bean>
```

2）引用内部bean

```
<property name="car">
     		<!-- 对象我们可以使用bean标签创建 car = new Car(); -->
     		<bean class="com.atguigu.bean.Car">
     			<property name="carName" value="自行车"></property>
     		</bean>
</property>
```

```
@Test
	public void test05() {
		Person person01 = (Person) ioc.getBean("person01");
		Car car = person01.getCar();
		System.out.println(car);
	}
```

![image-20200122111515451](C:\Users\试用\AppData\Roaming\Typora\typora-user-images\image-20200122111515451.png)

3.集合类型赋值（List、Map、Properties）

1）list赋值（外部book，引用外部book）

```
<bean id="person02" class="com.atguigu.bean.Person">
		<!-- 如何为list类型辅助 -->
		<property name="books">
			<!-- books = new ArrayList<Book>(); -->
			<list>
				<!-- list标签体中添加每一个元素 -->
				<bean id="book000x" class="com.atguigu.bean.Book" p:bookName="西游记"></bean>
				<!-- 引用外部一个元素 -->
				<ref bean="book01"></ref>
			</list>
		</property>
	</bean>
```

```
@Test
	public void test05() {
		Person person01 = (Person) ioc.getBean("person02");

		Car car = person01.getCar();
		System.out.println(car);
		List<Book> books = person01.getBooks();
		System.out.println(books);
}
```

#### 问题：内部bean写id，有没有用？

内部bean写id，不能被外部获取到，只能在内部使用

例如：peroson01中，id叫carInner的内部类。

```
<bean id="person01" class="com.atguigu.bean.Person">
     	<!-- lastName="null" -->
     	<property name="lastName" >
     		<!-- 进行复杂的赋值 -->
     		<null></null>
     	</property>
     	<!-- ref,代表引用外面的一个值 
     					car = ioc,getBean("car01") -->
     	<!--  <property name="car" ref="car01"></property>-->
     	<property name="car">
     		<!-- 对象我们可以使用bean标签创建 car = new Car(); -->
     		<!-- 内部bean写id，不能被外部获取到，只能在内部使用 -->
     		<bean id="carInner" class="com.atguigu.bean.Car">
     			<property name="carName" value="自行车"></property>
     		</bean>
     	</property>
     </bean>
```

```
@Test
	public void test05() {
		Person person01 = (Person) ioc.getBean("person02");
		

		Car car = person01.getCar();
		System.out.println(car);
		List<Book> books = person01.getBooks();
		System.out.println(books);
	
		System.out.println("========");
		Object bean = ioc.getBean("carInner");
		System.out.println(bean);
}
```

##### 会报错：

```
/*
	* org.springframework.beans.factory.NoSuchBeanDefinitionException:
	*  No bean named 'carInner' is defined
*/
```

2)map赋值

```
<property name="maps">
			<!-- maps = new LinkedHashMap<>(); -->
			<map>
				<!-- 一个entry代表一个键值对 -->
				<entry key="key01" value="张三"></entry>
				<entry key="key02" value="18"></entry>
				<entry key="key03" value-ref="book01"></entry>
				<entry key="key04">
					<bean class="com.atguigu.bean.Car">
						<property name="carName" value="宝马"></property>
					</bean>				
				</entry>
				<!--  <entry key="key05">
						<map></map>
						</entry>
				-->
			</map>
</property>
```

```java
@Test
	public void test05() {
		Person person01 = (Person) ioc.getBean("person02");

		Car car = person01.getCar();
		System.out.println(car);
		List<Book> books = person01.getBooks();
		System.out.println(books);
	
		System.out.println("========");
		//调用map
		Map<String,Object> maps = person01.getMaps();
		System.out.println(maps);
}
```

![image-20200122114803233](C:\Users\试用\AppData\Roaming\Typora\typora-user-images\image-20200122114803233.png)