package com.tao.member.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputChecker {

	public static boolean checkEmail(String email) {
		// 檢查是否為email格式
		String input = email;
		Pattern pattern = Pattern.compile("\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?");

		Matcher m = pattern.matcher(input);

		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkId(String name) {
		// 檢查是否有非法字元, 未檢查長度

		String input = name;
		Pattern pattern = Pattern.compile("^\\w+$");
		Matcher m = pattern.matcher(input);

		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkNames(String name) {
		// TODO Auto-generated method stub
		String input = name;
		Pattern pattern = Pattern
				.compile("[^\\x00-\\x40\\x5B-\\x60\\x7B-\\x7F]+$");
		Matcher m = pattern.matcher(input);

		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkIdNum(Integer type, String idnum) {
		// 檢查是否為身分證字號 或者 統一編號 格式

		String input = idnum;
		Integer inputType = type;
		Matcher m = null;
		Pattern pattern = null;				
		
		if (inputType == 0){
			pattern = Pattern.compile("^[a-zA-Z][0-9]{9}$");
		} else {
			pattern = Pattern.compile("^[0-9]{8}$");
		}
		
		m = pattern.matcher(input);

		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkAddr(String addr) {
		// TODO Auto-generated method stub
		String input = addr;
		Pattern pattern = Pattern
				.compile("^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$");
		Matcher m = pattern.matcher(input);

		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checktel(String tel) {
		// TODO Auto-generated method stub
		String input = tel;
		Pattern pattern = Pattern
				.compile("^[0-9]{10}$");
		Matcher m = pattern.matcher(input);

		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkgender(String gender) {
		// TODO Auto-generated method stub
		String input = gender;
		Pattern pattern = Pattern
				.compile("[MFmf]{1}");
		Matcher m = pattern.matcher(input);

		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checklocno(Integer locno) {
		// TODO Auto-generated method stub

		if ((locno >= 1) && (locno <= 355)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checktype(Integer type) {
		// TODO Auto-generated method stub
		if ((type == 0) || (type == 1)) {
			return true;
		} else {
			return false;
		}
	}
	
}
