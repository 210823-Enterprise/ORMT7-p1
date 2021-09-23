package com.team7.mappers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.team7.util.ColumnField;
import com.team7.util.MetaModel;

public class ObjectReader {
	public ObjectReader()
	{
		super();
	}
	public boolean readFromDb(Class<?> obj, Connection conn)
	{
		MetaModel<?> metaknight = MetaModel.of(obj);
		String sql = "SELECT * FROM " + metaknight.getSimpleClassName() + " WHERE";
		boolean success = false;
		int col = 0;
		List<ColumnField> parthenon = metaknight.getColumns();
		for(ColumnField yub : parthenon)
		{
			sql = sql + " " + yub.getColumnName() + " = " + yub.getName();
			if(col < metaknight.getColumns().size())
			{
				sql = sql + " AND";
			}
			col++;
		}
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
