package com.team7.util;

import java.lang.reflect.Field;

import com.team7.annotations.Column;

public class ColumnField {
	
//	@Column
	//private String name; (// how do I determine if this is a VARCHAR or NUMERIC or SERIAL PRIMARY KEY?

	private Field field;
	
	public ColumnField(Field field) {
		
		if (field.getAnnotation(Column.class) == null) {
			// If the field object that we pass through DOESN't have the column annotation, then it returns null
			throw new IllegalStateException("Cannot create ColumnField Object! Provided field " + getName() + " is not annotated with @Column");
		}
		
		this.field = field;
		
	}
	
	public String getName() {
		return field.getName();
	}
	
	public Class<?> getType() {
		return field.getType();
	}
	
	public String getColumnName() {
		return field.getAnnotation(Column.class).columnName();
		
	}
	
	public String getSQLType() {
		
	}
	
	
}