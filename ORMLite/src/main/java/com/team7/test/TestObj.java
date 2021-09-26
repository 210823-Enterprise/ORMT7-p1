package com.team7.test;

import com.team7.annotations.Column;
import com.team7.annotations.Entity;
import com.team7.annotations.Getter;
import com.team7.annotations.Id;
import com.team7.annotations.Setter;

@Entity(tableName = "test")
public class TestObj {
	@Id(columnName = "id")
	@Column(columnName = "id")
	private int id;
	@Column(columnName = "name")
	private String name;
	
	public TestObj(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public TestObj() {
		super();
	}
	@Getter(name = "id")
	public int getId() {
		return id;
	}
	@Setter(name = "id")
	public void setId(int id) {
		this.id = id;
	}
	@Getter(name = "name")
	public String getName() {
		return name;
	}
	@Setter(name = "name")
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "TestObj [id=" + id + ", name=" + name + "]";
	}
	
	
	
}
