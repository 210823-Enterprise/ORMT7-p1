package com.team7.test;

import com.team7.mappers.ObjectRemover;
import com.team7.util.ConnectionUtil;

public class Driver {
	
	public static void main(String[] args) {
		//set
		
		Object Fuccboi = new Object();
		boolean good=false;
		good=ObjectRemover.deleteObject(Fuccboi,ConnectionUtil.getConnection());
	}
	
}
