package com.team7.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.team7.util.ColumnField;
import com.team7.util.MetaModel;

public class ObjectUpdater {
	private static Logger log = Logger.getLogger(ObjectSetter.class);
	
	public boolean updateObjectByPrimaryKey(Object obj, Connection conn) {
		MetaModel<?> model = MetaModel.of(obj.getClass());
		
		//create setting string component
		String contents = "";
		
		for (ColumnField f : model.getColumns()) {
			contents += f.getColumnName() + " = " + f.getFieldValue(obj) + ", ";
		}
		
		//return failure if there are no columns
		if (contents.length() < 2) {
			log.info("There were no columns to update for table" + model.getTableName());
			return false;
			
			//trim string
		} else {
			contents = contents.substring(0, contents.length() - 2);
		}
		
		//update tableName to object values where pk in table == pk in object
		String sql = "UPDATE " + model.getTableName() + " SET " + contents + " " + " WHERE " + model.getPrimaryKey().getColumnName() + " = " + model.getPrimaryKey().getFieldValue(obj); 
		
		try {
			PreparedStatement update = conn.prepareStatement(sql);
			
			log.info("Attempting update to table " + model.getTableName() + " at ID: " + model.getPrimaryKey().getFieldValue(obj));
			
			update.executeUpdate();
			
			log.info("Updated " + model.getTableName() + " at ID: " + model.getPrimaryKey().getFieldValue(obj));
			return true;
			
		} catch (SQLException e) {
			log.error("failed to update table: " + model.getTableName());
			
			return false;
		}
		
		
		
	}
}
