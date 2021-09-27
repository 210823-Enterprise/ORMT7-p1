package com.team7.util;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import com.team7.annotations.Column;
import com.team7.annotations.Entity;
import com.team7.annotations.Foreign;
import com.team7.annotations.Id;
import com.team7.exceptions.NoColumnsException;

public class MetaModel<T> {

	private Class<T> clazz;
	private IdField primarykeyField = null;
	private List<ColumnField> columnFields;
	private List<ForeignKeyField> foreignKeyFields;
	private boolean columnsSet = false;
	private boolean foreignKeysSet = false;
	private boolean primaryKeySet = false;

	// of() method to take in a class and transform it to a meta model
	public static <T> MetaModel<T> of(Class<T> clazz) {

		// first we have to check that it's marked with the Entity annotation
		if (clazz.getAnnotation(Entity.class) == null) {
			throw new IllegalStateException("Cannot create Metamodel object! Provided class " + clazz.getName()
					+ " is not annotated with @Entity");
		}
		return new MetaModel<>(clazz);
	}

	public MetaModel(Class<T> clazz) {
		this.clazz = clazz;
		this.columnFields = new LinkedList<>(); // altered to fill columns (may not work, TODO test it)
		this.foreignKeyFields = new LinkedList<>();
	}

	public MetaModel(Class<T> clazz, IdField primarykeyField, List<ColumnField> columnFields,
			List<ForeignKeyField> foreignKeyFields) {
		super();
		this.clazz = clazz;
		this.primarykeyField = primarykeyField;
		this.columnFields = columnFields;
		this.foreignKeyFields = foreignKeyFields;
	}

	// class name is com.revature.MyClass
	public String getClassName() {
		return clazz.getName();
	}

	// simple class name is just MyClass
	public String getSimpleClassName() {
		return clazz.getSimpleName();
	}

	public String getTableName() {
		Entity table = this.clazz.getAnnotation(Entity.class);
		return table.tableName();
	}

	// this just makes the program a little more efficient
	private void setPrimaryKey() {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Id primaryKey = field.getAnnotation(Id.class);
			if (primaryKey != null) {
				primarykeyField = new IdField(field);
			}
		}

		primaryKeySet = true;
	}

	public IdField getPrimaryKey() {

		if (!primaryKeySet)
			setPrimaryKey();

		if (primarykeyField != null) {
			return primarykeyField;
		} else {
			return null;
		}
	}

	private void setColumns() {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Column column = field.getAnnotation(Column.class);
			if (column != null) {
				columnFields.add(new ColumnField(field));
			}
		}

		columnsSet = true;
	}

	public List<ColumnField> getColumns() {

		if (!columnsSet)
			setColumns();

		return columnFields;
	}

	private void setForeignKeys() {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Foreign column = field.getAnnotation(Foreign.class);
			if (column != null) {
				foreignKeyFields.add(new ForeignKeyField(field));
			}
		}

		foreignKeysSet = true;
	}

	public List<ForeignKeyField> getForeignKeys() {

		if (!foreignKeysSet)
			setForeignKeys();

		return foreignKeyFields;

	}
}
