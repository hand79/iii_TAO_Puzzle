package com.ya803g2.jdbc.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class Tester {

	public static void main(String[] args) {
		Gson gson = new Gson();

		Map<String, String> map = new HashMap<>();
		map.put("pic", "ext");
		Table ttt = new Table("test", map, "no");

		System.out.println(ttt.getInsertSql());
		System.out.println(gson.toJson(ttt));

		try {
//			List<Table> tables = InitTableData
//					.getTables("{'name':'member','blob':{'photo':'mime'},'pkColumn':'no'};{'name':'runningad','blob':{'adpic':'ext'},'pkColumn':'no'};{'name':'shop','blob':{'pic':'picmime'},'pkColumn':'no'};{'name':'shopproduct','blob':{'pic1':'pmime1', 'pic2':'pmime2', 'pic3':'pmime3'},'pkColumn':'no'};{'name':'caseproduct','blob':{'pic1':'pmime1', 'pic2':'pmime2', 'pic3':'pmime3'},'pkColumn':'no'};");
			List<Table> tables = InitTableData
					.getTables(new File("F:\\iii\\eclipse_workspace\\eslipse_ee_luna_web_workspace\\JDBCUtility\\res\\InitDate.txt"));
			for (Table t : tables) {
				System.out.println(t.getUpdateSql());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
