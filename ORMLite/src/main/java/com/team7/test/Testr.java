package com.team7.test;

import com.team7.annotations.Column;
import com.team7.annotations.Entity;
import com.team7.annotations.Foreign;
import com.team7.annotations.Id;

@Entity(tableName = "testr")
public class Testr {
	@Id(columnName="test_id")
	private int id;
	
	@Column(columnName = "test_username")@Foreign(columnName="test_username", reference = "ugh")
	private String testUsername;
	
	@Column(columnName="test_password")
	private String testPassword;
}
