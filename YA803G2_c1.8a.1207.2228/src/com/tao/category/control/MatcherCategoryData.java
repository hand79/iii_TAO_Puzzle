package com.tao.category.control;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherCategoryData {

	public static boolean matchNoNumber(String catno) {
		// TODO Auto-generated method stub
		String input = catno;
		Pattern pattern = Pattern.compile("^[0-9]*$");
		Matcher m = pattern.matcher(input);
		if (m.find()) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean matchNameFormat(String catname) {
		// TODO Auto-generated method stub
		String input = catname;
		Pattern pattern = Pattern.compile("^[\\p{InCJKUnifiedIdeographs}]*$");
		Matcher m = pattern.matcher(input);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

}
