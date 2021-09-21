package com.team7.test;

import com.team7.annotations.Column;
import com.team7.annotations.Entity;
import com.team7.annotations.Id;

@Entity(tableName = "Tester")
public class Testr {
	@Id(columnName="test_id")
	private int id;
	
	@Column(columnName="test_username")
	private String testUsername;
	
	@Column(columnName="test_password")
	private String testPassword;
	public Testr()
	{
		super();
	}
}
