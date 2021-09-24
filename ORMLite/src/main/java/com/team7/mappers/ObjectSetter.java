package com.team7.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.team7.util.ColumnField;
import com.team7.util.MetaModel;

public class ObjectSetter {
	
	private static Logger log = Logger.getLogger(ObjectSetter.class);

	public boolean setObject(Object obj, Connection conn){
		MetaModel<?> model = MetaModel.of(obj.getClass());

		String fields = model.getPrimaryKey().getColumnName() + " " + model.getPrimaryKey().getSQLType()+ ",";
		
		for (ColumnField f : model.getColumns()) {
			fields += f.getColumnName() + " " + f.getSQLType() + ",";
		}
		
		if (fields.length() > 2) {
			fields = fields.substring(0, fields.length() - 1); //trims comma
		}
		
		log.info("Attempting to create table if needed...");
		//effectively prepares statement but with expandable length 
		String sql = "CREATE TABLE IF NOT EXISTS " + model.getSimpleClassName() + " (" + fields + ")";
		
		System.out.println(sql);
		
		boolean success = false;
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			System.out.println(stmt);
			success = stmt.execute(); //may want batch here after adding insert
			
			System.out.println(success);
		} catch (SQLException e) {
			System.out.println("unable to add table");
			e.printStackTrace();
		}
		
		return success;

	}

}
