package com.team7.mappers;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.team7.connection.ConnectionFactory;
import com.team7.util.ColumnField;
import com.team7.util.ConnectionUtil;
import com.team7.util.MetaModel;

public class ObjectSetter {

	public boolean setObject(Object obj, Connection conn){
		MetaModel<?> model = MetaModel.of(obj.getClass());

		String fields = model.getPrimaryKey().getColumnName() + " " + model.getPrimaryKey().getSQLType()+ ",";
		
		for (ColumnField f : model.getColumns()) {
			fields += f.getColumnName() + " " + f.getSQLType() + ",";
		}
		
		if (fields.length() > 2) {
			fields = fields.substring(0, fields.length() - 1); //trims comma
		}
		
//		String sql = "CREATE TABLE IF NOT EXISTS " + model.getSimpleClassName() + " (" + fields + ")";
		
		String sql = "CREATE TABLE IF NOT EXISTS public.doggo (id int,name varchar(250),age int)";
		
		System.out.println(sql);
		
		boolean success = false;
		
		Connection connect = ConnectionUtil.getConnection();
		
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
		
		/**
		 * changed branch
		 */
		
	}
	public static boolean readFromDb(Object obj, Connection conn)
	{
		MetaModel<?> metaknight = MetaModel.of(obj.getClass());
		String sql = "SELECT * FROM users";
		boolean success = false;
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				ResultSetMetaData rsmeta = rs.getMetaData();
				for(int i = 1; i <= rsmeta.getColumnCount(); i++)
				{
					System.out.println(rs.getObject(i));
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return success;
	}
}
