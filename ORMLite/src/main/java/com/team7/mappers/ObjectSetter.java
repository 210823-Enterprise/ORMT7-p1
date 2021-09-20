package com.team7.mappers;

import java.lang.reflect.Field;
import java.sql.Connection;

import com.team7.util.ColumnField;
import com.team7.util.MetaModel;

public class ObjectSetter {

	public boolean setObject(Object obj, Connection conn) {
		MetaModel<?> model = MetaModel.of(obj.getClass());

		String primaryKey = model.getPrimaryKey().getName();

		String fields = "";
		for (ColumnField f : model.getColumns()) {
			fields += f.getColumnName() + " " + f.getSQLType();
		}

		String sql = "CREATE TABLE IF NOT EXISTS " + model.getSimpleClassName() + " (" + fields + ");";

	}

}
