package com.atguigu.factory;

import com.atguigu.bean.AirPlane;

/*
 * ʵ������
 */
public class AirPlaneInstanceFactory {

	//new AirPlaneInstanceFactory().getAirPlane();
	public  AirPlane getAirPlane(String jzName) {
		System.out.println("AirPlaneInstanceFactory...������ɻ�");
		AirPlane airPlane = new AirPlane();
		airPlane.setFdj("̫��");
		airPlane.setFjsName("lfy");
		airPlane.setJzName(jzName);
		airPlane.setPersonNum(300);
		airPlane.setYc("198.98m");
		return airPlane;
	}
}
