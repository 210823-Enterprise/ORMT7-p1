package com.team7.test;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.team7.mappers.ObjectReader;
import com.team7.util.ConnectionUtil;

public class Driver {
	
	public static void main(String[] args) {
		//set
		Connection conn = ConnectionUtil.getConnection();
		ObjectReader read = new ObjectReader();
		Map<String, Map<String, String>> yub = read.getAll(Testr.class, conn);
		System.out.println(yub.keySet());
		Map<String, String> a = new HashMap<String, String>();
		a.put("test_username", "noah");
		yub = read.readFromDb(Testr.class, conn, a);
		for(Map.Entry<String, Map<String, String>> entry : yub.entrySet())
		{
			System.out.println(yub.entrySet());
		}
	}
	
}
