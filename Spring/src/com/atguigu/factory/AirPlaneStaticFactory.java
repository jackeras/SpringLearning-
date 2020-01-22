package com.atguigu.factory;

import com.atguigu.bean.AirPlane;

/*
 * ��̬����
 */

public class AirPlaneStaticFactory {

	//AirPlaneStaticFactory.getAirPlane()
	public static AirPlane getAirPlane(String jzName) {
		System.out.println("AirPlaneStaticFactory...������ɻ�");
		AirPlane airPlane = new AirPlane();
		airPlane.setFdj("̫��");
		airPlane.setFjsName("lfy");
		airPlane.setJzName(jzName);
		airPlane.setPersonNum(300);
		airPlane.setYc("198.98m");
		return airPlane;
	}
}
