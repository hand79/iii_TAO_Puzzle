package com.tao.searchresult.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputChecker {

	public static boolean checkKeyword(String keyword) {
		// 檢查是否為中文 英文與數字格式
		String input = keyword;
		Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5_a-zA-Z0-9]+$");

		Matcher m = pattern.matcher(input);

		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	
}
