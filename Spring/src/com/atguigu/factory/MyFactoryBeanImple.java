package com.atguigu.factory;

import java.util.UUID;

import org.springframework.beans.factory.FactoryBean;

import com.atguigu.bean.Book;

/*
 * ʵ����FactoryBean��ڵ�����Spring������ʶ�Ĺ�����
 * Spring���Զ��ĵ��ù�����������ʵ��
 * 
 * 1.��дһ��FactoryBean��ʵ����
 * 2.��spring�����ļ��н���ע��
 */

public class MyFactoryBeanImple implements FactoryBean<Book>{
	/*
	 * getObject:��������
	 * 			���ش�������
	 */
	@Override
	public Book getObject() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("MyFactoryBeanImple..���㴴�����󡣡���");
		Book book = new Book();
		book.setBookName(UUID.randomUUID().toString());
		return book;
	}
	/*
	 * ���ش����Ķ��������
	 * Spring���Զ��������������ȷ�ϴ����Ķ�����ʲô����
	 * 
	 */
	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * isSingleton�ǵ�����
	 * false:���ǵ���
	 * true:�ǵ���
	 */
	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}
}
