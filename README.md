# 一、SpringIOCd学习

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

```java
<bean id="person01" class="com.atguigu.bean.Person">
		<property name="lastName" value="张三"></property>
		<property name="age" value="18"></property>
		<property name="email" value="zhangsan@atgui.com"></property>
		<property name="gender" value="男"></property>
	  </bean>
```

3.测试

```java
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

```java
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

## 实验三：通过构造器为bean的属性赋值（index,type属性介绍）通过p名称空间为bean赋值

1.通过构造器为bean的属性赋值（index,type属性介绍）

1）在Person类中，创建有参构造器

```java
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

```java
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

```java
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

**index,type属性介绍：**

index可以起到定位的作用

```java
<!-- 可以省略name属性，但是要严格按照构造器参数的位置 -->
	<bean id="person04" class="com.atguigu.bean.Person">
		<constructor-arg value="小花"></constructor-arg>
		<constructor-arg value="18"></constructor-arg>
		<!-- index="3",为参数指定索引，从0开始 -->
		<constructor-arg value="男" index="2"></constructor-arg>
		<constructor-arg value="xiaohua@atgui.com"></constructor-arg>
	</bean>
```

**带来的问题：**

如果有构造器重载的情况下，ioc配置里，可能产生混乱。例如：

```java
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

```java
<bean id="person05" class="com.atguigu.bean.Person">
		<!--ioc容器只能大概根据某种规则分配，但结果往往是不理想的-->
		<constructor-arg value="小丽"></constructor-arg>
		<constructor-arg value="18"></constructor-arg>
		<constructor-arg value="男"></constructor-arg>
	</bean>
```

可以通过type来指定：

```java
<bean id="person05" class="com.atguigu.bean.Person">
		<constructor-arg value="小丽"></constructor-arg>
		<constructor-arg value="18" index="1" type="java.lang.Integer"></constructor-arg>
		<constructor-arg value="男"></constructor-arg>
	</bean>
```

2.通过p名称空间为bean赋值

名称空间，在XML中名称空间是用来防止标签重复的。

```java
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

```java
<bean id="person06" class="com.atguigu.bean.Person"
		p:age="18" p:email="xiaoming@atgui.com" p:lastName="哈哈" p:gender="男">
	</bean>
```

## 实验四：正确的为各种属性赋值

**1、测试使用null值** 

默认引用类型就是null，基本类型是默认值

```java
<bean id="person01" class="com.atguigu.bean.Person">
</bean>
```

```java
@Test
	public void test04() {
		Object bean = ioc.getBean("person01");
		System.out.println(bean);
	}
```

![image-20200122104747518](C:\Users\试用\AppData\Roaming\Typora\typora-user-images\image-20200122104747518.png)



**错误赋值null：**

```java
<bean id="person01" class="com.atguigu.bean.Person">
     	<!-- lastName="null" -->
     	<property name="lastName" value="null"></property>
     </bean>
```

```java
@Test
	public void test04() {
		Person bean = (Person) ioc.getBean("person01");
		System.out.println(bean.getLastName() == null);
	}
```

![image-20200122105151002](C:\Users\试用\AppData\Roaming\Typora\typora-user-images\image-20200122105151002.png)

**正确赋值null：在property下用null标签赋值**

```java
<bean id="person01" class="com.atguigu.bean.Person">
     	<!-- lastName="null" -->
     	<property name="lastName" >
     		<!-- 进行复杂的赋值 -->
     		<null></null>
     	</property>
     </bean>
```

**2、引用类型赋值（引用其他bean、引用内部bean）**

1）引用其他bean

定义一个car类

```java
<bean id="car01" class="com.atguigu.bean.Car">
     	<property name="carName" value="宝马"></property>
     	<property name="color" value="绿色"></property>
     	<property name="price" value="30000"></property>
     </bean>
```

在person01中使用ref标签，来引用car01类

```java
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

```java
<property name="car">
     		<!-- 对象我们可以使用bean标签创建 car = new Car(); -->
     		<bean class="com.atguigu.bean.Car">
     			<property name="carName" value="自行车"></property>
     		</bean>
</property>
```

```java
@Test
	public void test05() {
		Person person01 = (Person) ioc.getBean("person01");
		Car car = person01.getCar();
		System.out.println(car);
	}
```

![image-20200122111515451](C:\Users\试用\AppData\Roaming\Typora\typora-user-images\image-20200122111515451.png)

**3.集合类型赋值（List、Map、Properties）**

1）list赋值（外部book，引用外部book）

```java
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

```java
@Test
	public void test05() {
		Person person01 = (Person) ioc.getBean("person02");

		Car car = person01.getCar();
		System.out.println(car);
		List<Book> books = person01.getBooks();
		System.out.println(books);
}
```

**问题：内部bean写id，有没有用？**

内部bean写id，不能被外部获取到，只能在内部使用

例如：peroson01中，id叫carInner的内部类。

```java
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

```java
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

**会报错：**

```java
/*
	* org.springframework.beans.factory.NoSuchBeanDefinitionException:
	*  No bean named 'carInner' is defined
*/
```

2)map赋值

```java
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

3)Properties赋值

```java
<property name="properties">
		<!-- properties = new Properties();所有的k=v都是String -->
			<props>
				<!-- k=v都是string,值直接写在标签体中 -->
				<prop key="username">root</prop>
				<prop key="password">123456</prop>
			</props>
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
	
		Map<String,Object> maps = person01.getMaps();
		System.out.println(maps);
		System.out.println("==================");
		System.out.println(person01.getProperties());
}
```

![image-20200122153654152](F:\code\SpringLearning-\image\image-20200122153654152.png)

**4.util名称空间创建集合类型的bean**

```java
<!-- 相当于new LinkedHashMap() -->
	<util:map id="myMap">
		<!-- 添加元素 -->
				<entry key="key01" value="张三"></entry>
				<entry key="key02" value="18"></entry>
				<entry key="key03" value-ref="book01"></entry>
				<entry key="key04">
					<bean class="com.atguigu.bean.Car">
						<property name="carName" value="宝马"></property>
					</bean>				
				</entry>
				<entry key="key05">
					<value>李四</value>
				</entry>
				 <entry key="key05">
						<map></map>
				</entry>
	</util:map>
```

```java
<bean id="person03" class="com.atguigu.bean.Person">
		<!--  <property name="maps" ref=></property>-->
		<property name="maps" ref="myMap"></property>
</bean>
```

```java
@Test
	public void test06() {
		Person person03 = (Person)ioc.getBean("person03");
		Map<String,Object> maps = person03.getMaps();
		System.out.println(maps);
	}
```

![image-20200122155543287](F:\code\SpringLearning-\image\image-20200122155543287.png)

**5.级联属性赋值**

级联属性可以修改属性的属性，注意：原来的bean的值可能会被修改

```java
<bean id="person04" class="com.atguigu.bean.Person">
	<!-- 为car赋值的时候，改变car的价格 -->
		<property name="car" ref="car01"></property>
		<property name="car.price" value="900000"></property>
</bean>
```

```java
@Test
	public void test06() {
		Person person04 = (Person)ioc.getBean("person04");
		Object car = ioc.getBean("car01");
		System.out.println("容器中的car："+car);
		System.out.println("Person中的car："+person04.getCar());
	}
```



## 实验六：通过继承实现bean配置信息的重用

```java
<bean id="person05" class="com.atguigu.bean.Person">
		<property name="lastName" value="张三"></property>
		<property name="age" value="18"></property>
		<property name="gender" value="男"></property>
		<property name="email" value="zhangsan@atgui.com"></property>
</bean>
	<!-- parent:指定当前bean的配置信息继承于哪个 -->
<bean id="person06"  parent="person05">
		<property name="lastName" value ="李四"></property>
</bean>
```

```java
@Test
	public void test07() {
		Person person06 = (Person)ioc.getBean("person06");
		System.out.println(person06);
	}
```

![image-20200122161247032](F:\code\SpringLearning-\image\image-20200122161247032.png)

## 实验七：通过abstract属性创建一个模板bean

```java
<!-- abstract="true" 这个bean的配置是一个抽象的，不能获取它的实例，只能被别人用来继承 -->
	<bean id="person05" class="com.atguigu.bean.Person" abstract="true">
		<property name="lastName" value="张三"></property>
		<property name="age" value="18"></property>
		<property name="gender" value="男"></property>
		<property name="email" value="zhangsan@atgui.com"></property>
	</bean>
```

```java
@Test
	public void test07() {
		Person person06 = (Person)ioc.getBean("person05");
		System.out.println(person06);
	}
```

**报错：**

```java
org.springframework.beans.factory.BeanIsAbstractException: 
		Error creating bean with name 'person05': 
		Bean definition is abstract
```

## 实验八：bean之间的依赖

1）没有建立依赖之前的bean创建顺序：

```java
<!-- 原来是按照配置顺序创建的bean的 -->
	<!-- 改变bean的创建顺序 -->
	<!-- 实验8：bean之间的依赖 （只是改变创建顺序-->
	<bean id="car" class="com.atguigu.bean.Car"></bean>
	<bean id="person" class="com.atguigu.bean.Person" ></bean>
	<bean id="book" class="com.atguigu.bean.Book"></bean>
```

![image-20200122162906512](F:\code\SpringLearning-\image\image-20200122162906512.png)

2）创建依赖后bean的创建顺序：

```java
<!-- 原来是按照配置顺序创建的bean的 -->
	<!-- 改变bean的创建顺序 -->
	<!-- 实验8：bean之间的依赖 （只是改变创建顺序-->
	<bean id="car" class="com.atguigu.bean.Car" depends-on="person,book"></bean>
	<bean id="person" class="com.atguigu.bean.Person" ></bean>
	<bean id="book" class="com.atguigu.bean.Book"></bean>
```

![image-20200122162953234](F:\code\SpringLearning-\image\image-20200122162953234.png)

## 实验九：测试bean的作用域，分别创建单实例和多实例的bean★

bean的作用域:指定bean是否单实例，xxx：默认是单实例的	
scope属性：
	prototype：多实例的
		1)容器启动默认不会去创建多实例bean
		2）获取的时候创建这个bean
		3）每次获取都会创建一个新的对象
	singleton：单实例的（默认）
		1）在容器启动完成之前就已经创建好对象，保存在容器中了
		2)任何获取都是获取之前创建好的那个对象
	request：在web环境下，同一次请求创建一个bean实例（没用）
	session：在web环境下，同一次会话创建一个bean实例（没用）

**1.singleton**

```java
<bean id="book" class="com.atguigu.bean.Book"></bean>
```

```java
@Test
	public void test08() {
		System.out.println("容器启动完成。。。");
		Object bean = ioc.getBean("book");
		Object bean2 = ioc.getBean("book");
		System.out.println(bean == bean2);
		Object bean = ioc.getBean("book");
		Object bean2 = ioc.getBean("book");
		System.out.println(bean == bean2);
	}
```

![image-20200122164344530](F:\code\SpringLearning-\image\image-20200122164344530.png)

scope=“singleton”结论：

1）在容器启动完成之前就已经创建好对象，保存在容器中了
2）任何获取都是获取之前创建好的那个对象

**2.prototype（多实例）**

```java
<bean id="book" class="com.atguigu.bean.Book" scope="prototype"></bean>
```

```java
@Test
	public void test08() {
		System.out.println("容器启动完成。。。");
//		Object bean = ioc.getBean("book");
//		Object bean2 = ioc.getBean("book");
//		System.out.println(bean == bean2);
		Object bean = ioc.getBean("book");
		Object bean2 = ioc.getBean("book");
		System.out.println(bean == bean2);
	}
```

![image-20200122164548189](F:\code\SpringLearning-\image\image-20200122164548189.png)

scope=“prototype”结论：

1）容器启动默认不会去创建多实例bean
2）获取的时候才会去创建这个bean
3）每次获取都会创建一个新的对象

## 实验五：配置通过静态工厂方法创建的bean、实例工厂方法创建的bean、FactoryBean★

工厂模式：工厂帮我们创建对象；有一个专门帮我们创建对象的类，这个类就是工厂
					AirPlaneFactory.getAirPlane(String jzName);

**1)静态工厂**

静态工厂：工厂本身不用创建对象；通过静态方法调用，对象 = 工厂类.工厂方法名（）；

factory-method="getAirPlane"指定哪个方法是工厂方法
		class:指定静态工厂全类名
		factory-method：指定工厂方法
		constructor-arg:可以为方法传参

```java
<bean id="airPlane01" class="com.atguigu.factory.AirPlaneStaticFactory" 
		factory-method="getAirPlane">
		<!-- 可以为方法指定参数 -->
		<constructor-arg value="李四"></constructor-arg>
</bean>
```

```java
/*
 * 静态工厂
 */
public class AirPlaneStaticFactory {
//AirPlaneStaticFactory.getAirPlane()
	public static AirPlane getAirPlane(String jzName) {
		AirPlane airPlane = new AirPlane();
		airPlane.setFdj("太行");
		airPlane.setFjsName("lfy");
		airPlane.setJzName(jzName);
		airPlane.setPersonNum(300);
		airPlane.setYc("198.98m");
		return airPlane;
	}
}
```

```java
@Test
	public void test09() {
	Object bean = ioc.getBean("airPlane01");
	System.out.println(bean);
	System.out.println("容器启动完成。。。。");
}
```

![image-20200122185808216](F:\code\SpringLearning-\image\image-20200122185808216.png)

**2)实例工厂**

factory-method:指定这个实例工厂中哪个方法是工厂方法

factory-bean:指定当前对象创建使用哪个工厂 
		1.先配置出实例工厂对象
		2.配置我们要创建的AirPlane使用哪个工厂创建
			1）factory-bean:指定使用哪个工厂实例
			2）factory-method：使用哪个工厂方法

实例工厂：工厂本身需要创建对象，
				工厂类  工厂对象 = new工厂类（）；
				工厂对象.getAirPlane("张三")

```java
//new AirPlaneInstanceFactory().getAirPlane();
	public  AirPlane getAirPlane(String jzName) {
		System.out.println("AirPlaneInstanceFactory...正在造飞机");
		AirPlane airPlane = new AirPlane();
		airPlane.setFdj("太行");
		airPlane.setFjsName("lfy");
		airPlane.setJzName(jzName);
		airPlane.setPersonNum(300);
		airPlane.setYc("198.98m");
		return airPlane;
	}
```

```java
<bean id="airPlane02" class="com.atguigu.bean.AirPlane" 
		factory-bean="AirPlaneInstanceFactory"  
		factory-method="getAirPlane">
		<constructor-arg value="王五"></constructor-arg>
</bean>
```

```java
@Test
	public void test09() {
		//Object bean = ioc.getBean("airPlane01");
		//System.out.println(bean);
		Object bean = ioc.getBean("airPlane02");
		System.out.println("容器启动完成。。。。"+bean);
	}
```

![image-20200122190914267](F:\code\SpringLearning-\image\image-20200122190914267.png)

3）FactoryBean

FactoryBean(是Spring规定的一个借口）只要是这个借口的实现类，
			Spring都认为是一个工厂
			1.ioc容器启动的时候不会创建实例 
			2.FactoryBean:获取的时候才创建对象

```java
<bean id="myFactoryBeanImple" class="com.atguigu.factory.MyFactoryBeanImple"></bean>
```

```java
public class MyFactoryBeanImple implements FactoryBean<Book>{
	/*
	 * getObject:工厂方法
	 * 			返回创建对象
	 */
	@Override
	public Book getObject() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MyFactoryBeanImple..帮你创建对象。。。");
		Book book = new Book();
		book.setBookName(UUID.randomUUID().toString());
		return book;
	}
	/*
	 * 返回创建的对象的类型
	 * Spring会自动调用这个方法来确认创建的对象是什么类型
	 * 
	 */
	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * isSingleton是单例吗？
	 * false:不是单例
	 * true:是单例
	 */
	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}
}
```

```java
@Test
	public void test10() {
		Object bean = ioc.getBean("myFactoryBeanImple");
		Object bean2 = ioc.getBean("myFactoryBeanImple");
		System.out.println(bean == bean2);
	}
```

![image-20200122192646149](C:\Users\试用\AppData\Roaming\Typora\typora-user-images\image-20200122192646149.png)

## 实验十：创建带有生命周期方法的bean

2020.1.23更新

**1.单例Bean生命周期：**

​	 		（容器启动）构造器---->初始化方法---->（容器关闭）销毁方法

1）先往xml中加入销毁方法和初始化方法

```java
<bean id="book01" class="com.atguigu.bean.Book"
		destroy-method="myDestory" init-method="myInit" scope="prototype"></bean>
```

2）Book类中加入初始化方法和销毁方法

```java
public void myInit() {
		System.out.println("这是图书的初始化方法");
	}
public void myDestory() {
	System.out.println("这是图书的销毁方法");
}
```

3）运行空测试类（需要使用ConfigurableApplicationContext才能有close（）方法：

```
ConfigurableApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");

@Test
	public void test() {
		ioc.close();
	}
```

![image-20200130191659531](F:\code\SpringLearning-\image\image-20200130191659531.png)

​	结果可以看出，单实例在容器启动时创建，并初始化，容器关闭时销毁

**2.多实例Bean生命周期：**

​		获取bean（构造器---->初始化方法----->容器关闭不会调用bean的销毁方法

1）先往xml中加入销毁方法和初始化方法

```java
<bean id="book01" class="com.atguigu.bean.Book"
		destroy-method="myDestory" init-method="myInit" scope="prototype"></bean>
```

2）Book类中加入初始化方法和销毁方法

```java
public void myInit() {
		System.out.println("这是图书的初始化方法");
	}
public void myDestory() {
	System.out.println("这是图书的销毁方法");
}
```

3）运行测试类（需要使用ConfigurableApplicationContext才能有close（）方法：

```
ConfigurableApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");

@Test
	public void test() {
		Object bean = ioc.getBean("book01");
		System.out.println("容器关闭");
		ioc.close();
	}
```

![image-20200130191929108](F:\code\SpringLearning-\image\image-20200130191929108.png)

​	结果可以看出，多实例在被调用时创建，并初始化，容器关闭时不会调用销毁方法。

## 实验十一：测试bean的后置处理器

后置处理器：
	  （容器启动）构造器-----后置处理器before，------初始化方法----后置处理器after----bean初始化完成
	  无论bean是否有初始化方法：后置处理器都会默认有，还会继续工作

1.后置处理器MybeanPostProcessor类

```java
package com.atguigu.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor{
@Override
public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
// TODO Auto-generated method stub
System.out.println("【"+beanName+"】bean初始化方法调用完了。。。。。AfterInitialization。。。");
//初始化之后返回的bean，返回的是什么，容器中保存的就是什么
return bean;
}
@Override
public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
// TODO Auto-generated method stub
System.out.println("【"+beanName+"】bean将要调用初始化方法了。。。。");
//返回传入的bean
return bean;
	}
}
```

2.在xml中注册

```java
<bean id="beanPostProcessor" class="com.atguigu.bean.MyBeanPostProcessor"></bean>
```

![image-20200130194507999](F:\code\SpringLearning-\image\image-20200130194507999.png)

​	结果显示：后置处理器before会在初始化方法前调用，而后置处理器After会在初始化方法之后调用

## 实验十二：引用外部属性文件★







2020.1.25更新



