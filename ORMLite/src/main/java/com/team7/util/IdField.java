package com.team7.util;

import java.lang.reflect.Field;

import com.team7.annotations.Id;

public class IdField {

	private Field field;

	public IdField(Field field) {
		if (field.getAnnotation(Id.class) == null) {
			throw new IllegalStateException(
					"Cannot create IdField object! Provided field, " + getName() + "is not annotated with @Id");
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
		return field.getAnnotation(Id.class).columnName();
	}
	
	public String getFieldValue(Object o) {
		try {
			field.setAccessible(true);
			return field.get(o).toString();
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO logs
			e.printStackTrace();
			return null;
		}
	}

	// TODO make this inherited or from util class (try to only write it once)
	public String getSQLType() {
		String type = field.getType().getSimpleName();

		String retVal; // return type string
		switch (type) {
		case "int":
		case "Integer":
			retVal = "int";
			break;
		case "String":
			retVal = "varchar(250)";
			break;
		default:
			retVal = "varchar(10)"; // type not found TODO put log here
		}

		return retVal;
	}

}
