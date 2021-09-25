package com.team7.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team7.test.Testr;
import com.team7.util.ColumnField;
import com.team7.util.MetaModel;

public class ObjectReader {
	public ObjectReader()
	{
		super();
	}
	public boolean readFromDb(Class<?> obj, Connection conn, Map<String, String> cont)
	{
		MetaModel<?> metaknight = MetaModel.of(obj);
		String sql = "SELECT * FROM " + metaknight.getTableName() + " WHERE";
		boolean success = false;
		int col = 1;
		List<ColumnField> parthenon = metaknight.getColumns();
		for(ColumnField yub : parthenon)
		{
			if(cont.containsKey(yub.getColumnName()) == true)
			{
				sql = sql + " " + yub.getColumnName() + " = '" + cont.get(yub.getColumnName()) + "'";
				if(col < cont.size())
				{
					sql = sql + " AND";
				}
			}
			col++;
		}
		sql += ";";
		try
		{
			PreparedStatement stmt = conn.prepareStatement(sql);
			success = stmt.execute();
			ResultSet rs = stmt.executeQuery();
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
	public boolean readPrimaryKey(Class<?> obj, Connection conn, Map<String, String> cont)
	{
		MetaModel<?> metaknight = MetaModel.of(obj);
		String sql = "SELECT * FROM " + metaknight.getTableName() + " WHERE " + metaknight.getPrimaryKey().getColumnName() +
				" = " + cont.get(metaknight.getPrimaryKey().getName());
		boolean success = false;
		
		sql += ";";
		try
		{
			PreparedStatement stmt = conn.prepareStatement(sql);
			success = stmt.execute();
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				System.out.println("yub");
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
	public Map<String, String> getEntry(Class<?> obj, Connection conn, int ki)
	{
		Map<String, String> ret = new HashMap<String, String>();
		MetaModel<?> meta = MetaModel.of(obj);
		String sql = "SELECT * FROM " + meta.getTableName() + " WHERE " + meta.getPrimaryKey().getColumnName() + " = " + ki;
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				ResultSetMetaData rsmeta = rs.getMetaData();
				for(int i = 1; i <= rsmeta.getColumnCount(); i++)
				{
					ret.put(rsmeta.getColumnName(i), rs.getString(rsmeta.getColumnName(i)));
				}
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return ret;
	}
}
