package com.atguigu.bean;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Person {

	//��������ֱ��ʹ��
	//<property name="lastName" value="����"></property>�Զ���������װ��
	private String lastName="С��";
	private Integer age;
	private String gender;
	private String email;
	
	private Car car;
	
	private List<Book> books;
	
	private Map<String,Object> maps;
	
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public Map<String, Object> getMaps() {
		return maps;
	}

	public void setMaps(Map<String, Object> maps) {
		this.maps = maps;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	private Properties properties;
	
	public String getLastName() {
		return lastName;
	}
	
	public Person(String lastName, Integer age, String gender) {
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		System.out.println("���������Ĺ�����������age");
	}
	
	public Person(String lastName,String email, String gender) {
		this.lastName = lastName;
		this.email = email;
		this.gender = gender;
		System.out.println("���������Ĺ�����������email");
	}
	
	public Person(String lastName, Integer age, String gender, String email) {
		super();
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.email = email;
		System.out.println("�вι�����������");
	}

	public Person() {
		super();
		System.out.println("person�����ˡ�����");
	}


	public void setLastName(String lastName) {
		System.out.println("setLastName...."+lastName);
		this.lastName = lastName;
		
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Person [lastName=" + lastName + ", age=" + age + ", gender=" + gender + ", email=" + email + ", car="
				+ car + ", books=" + books + ", maps=" + maps + ", properties=" + properties + ", getLastName()="
				+ getLastName() + ", getAge()=" + getAge() + ", getGender()=" + getGender() + ", getEmail()="
				+ getEmail() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
}
