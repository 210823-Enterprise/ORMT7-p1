package com.team7.util;

import java.lang.reflect.Field;

import com.team7.annotations.Column;

public class ColumnField {

//	@Column
	// private String name; (// how do I determine if this is a VARCHAR or NUMERIC
	// or SERIAL PRIMARY KEY?

	private Field field;

	public ColumnField(Field field) {

		if (field.getAnnotation(Column.class) == null) {
			// If the field object that we pass through DOESN't have the column annotation,
			// then it returns null
			throw new IllegalStateException(
					"Cannot create ColumnField Object! Provided field " + getName() + " is not annotated with @Column");
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

	//TODO make this inherited or from util class (try to only write it once)
	public String getSQLType() {
		String type = field.getType().getSimpleName();
		
		String retVal; //return type string
		switch (type) {
		case "int":
		case "Integer":
			retVal = "int";
			break;
		case "String":
			retVal = "varchar(250)";
			break;
		default :
			retVal = "varchar(10)"; //type not found TODO put log here
		}
		
		return retVal;
	}

}