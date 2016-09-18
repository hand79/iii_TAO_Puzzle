package com.tao.acc.control;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherData {

	public static boolean matchEmail(String email) {
		// TODO Auto-generated method stub
		String input = email;
		
		Pattern pattern = Pattern.compile("\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

		Matcher m = pattern.matcher(input);

		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean matchAccount(String acc) {
		// TODO Auto-generated method stub

		String input = acc;
		Pattern pattern = Pattern.compile("^\\w+$");
		Matcher m = pattern.matcher(input);

		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean matchNick(String nick) {
		// TODO Auto-generated method stub
		String input = nick;
		Pattern pattern = Pattern
				.compile("[^\\x00-\\x40\\x5B-\\x60\\x7B-\\x7F]+$");
		Matcher m = pattern.matcher(input);

		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean matchAccpw(String accpw) {
		// TODO Auto-generated method stub
		String input=accpw;
		Pattern pattern=Pattern.compile("^[A-Za-z0-9]+$");
		Matcher m = pattern.matcher(input);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

}
