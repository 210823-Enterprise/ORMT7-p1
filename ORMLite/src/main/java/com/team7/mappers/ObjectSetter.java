package com.team7.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.team7.util.ColumnField;
import com.team7.util.MetaModel;

public class ObjectSetter {

	public int setObject(Object obj, Connection conn){
		MetaModel<?> model = MetaModel.of(obj.getClass());

		String fields = model.getPrimaryKey().getColumnName() + " " + model.getPrimaryKey().getSQLType()+ ",";
		
		for (ColumnField f : model.getColumns()) {
			fields += f.getColumnName() + " " + f.getSQLType() + ",";
		}
		
		if (fields.length() > 2) {
			fields = fields.substring(0, fields.length() - 1); //trims comma
		}
		
		String sql = "CREATE TABLE IF NOT EXISTS " + model.getSimpleClassName() + " (" + fields + ");";;
		
		int retVal = 0;
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			retVal = stmt.executeUpdate(); //may want batch here after adding insert
		} catch (SQLException e) {
			System.out.println("unable to add table");
			e.printStackTrace();
		}
		
		return retVal;

	}

}
