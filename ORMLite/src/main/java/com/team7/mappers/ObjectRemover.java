package com.team7.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.team7.util.ConnectionUtil;
import com.team7.util.MetaModel;

public class ObjectRemover {
	
	public static boolean deleteObject(Object obj, Connection conn){
		MetaModel<?> model = MetaModel.of(obj.getClass());
		
		String sql = "DELETE FROM " + model.getClassName() + " WHERE " + model.getPrimaryKey().getColumnName() + "= ?";
		System.out.println(sql);
		
		boolean success = false;
		
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, model.getPrimaryKey().toString());
			System.out.println(stmt);
			success = stmt.execute();
			
			System.out.println(success);
		} catch (SQLException e) {
			System.out.println("unable to add table");
			e.printStackTrace();
		}
		
		return success;
		
	}
	
}
