package com.tao.member.controller;

public class LocnoAreaConverter {
	
	public static Integer locnoToArea(Integer locno){
		
		if ((locno!=null) && (locno.toString().length() !=0)){
			if (locno >= 1 && locno <= 48){
				return 1; 
			} else if (locno >= 49 && locno <= 61){
				return 2;
			} else if (locno >= 62 && locno <= 77){
				return 3;
			} else if (locno >= 78 && locno <= 95){
				return 4;
			} else if (locno >= 96 && locno <= 124){
				return 5;
			} else if (locno >= 125 && locno <= 183){
				return 6;
			} else if (locno >= 184 && locno <= 240){
				return 7;
			} else if (locno >= 241 && locno <= 313){
				return 8;
			} else if (locno >= 314 && locno <= 355){
				return 9;
			} else {
				return 10;
			}
			
		} else {
			System.out.println("unvalid locno input");
			return null;
		}
		
		
	}
	
	public static Integer[] areaToLocno(Integer area){
		Integer[] locRange = new Integer[2];
		
		if (area >= 1 && area <= 10) {
			if (area == 1) {
				locRange[0] = 1;
				locRange[1] = 48;
			} else if (area == 2) {
				locRange[0] = 49;
				locRange[1] = 61;
			} else if (area == 3) {
				locRange[0] = 62;
				locRange[1] = 77;
			} else if (area == 4) {
				locRange[0] = 78;
				locRange[1] = 95;
			} else if (area == 5) {
				locRange[0] = 96;
				locRange[1] = 124;
			} else if (area == 6) {
				locRange[0] = 125;
				locRange[1] = 183;
			} else if (area == 7) {
				locRange[0] = 184;
				locRange[1] = 240;
			} else if (area == 8) {
				locRange[0] = 241;
				locRange[1] = 313;
			} else if (area == 9) {
				locRange[0] = 314;
				locRange[1] = 355;
			} else if (area == 10) {
				locRange[0] = 1;
				locRange[1] = 355;
			}
			return locRange;
			
		} else {
			System.out.println("unvalid Area input");
			return null;
		}
		
	}

}
