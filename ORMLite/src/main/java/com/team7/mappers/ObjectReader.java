package com.team7.mappers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.team7.util.MetaModel;

public class ObjectReader {
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
					System.out.println(rsmeta.getColumnName(i)+ ": " + rs.getObject(i));
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
