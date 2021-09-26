package com.team7.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.team7.mappers.ObjectRemover;
import com.team7.mappers.ObjectSetter;
import com.team7.util.ConnectionUtil;

public class Driver {
	
	public static void main(String[] args) {
		//set
		
		Connection conn = ConnectionUtil.getConnection();
		
		TestObj testMan = new TestObj(3,"Bob");
		System.out.println(ObjectRemover.deleteObject(testMan, conn));
	}
	
}
