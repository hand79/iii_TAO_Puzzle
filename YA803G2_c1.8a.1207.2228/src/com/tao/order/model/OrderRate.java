package com.tao.order.model;

public class OrderRate {
	public static String getRate(int score){
		if(score ==0){
			return "Ãaµû";
		}
		if(score ==1){
			return "´¶µû";
		}
		if(score ==2){
			return "¦nµû";
		}
		return null;
	}
}
