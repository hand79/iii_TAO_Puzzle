package com.tao.order.model;

public class OrderRate {
	public static String getRate(int score){
		if(score ==0){
			return "�a��";
		}
		if(score ==1){
			return "����";
		}
		if(score ==2){
			return "�n��";
		}
		return null;
	}
}
