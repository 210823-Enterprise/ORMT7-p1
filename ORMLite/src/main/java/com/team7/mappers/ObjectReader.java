package com.team7.mappers;

import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.team7.util.ColumnField;
import com.team7.util.ForeignKeyField;
import com.team7.util.MetaModel;

public class ObjectReader {

	private static Logger log = Logger.getLogger(ObjectReader.class);

	public Map<String, Map<String, String>> readFromDb(Class<?> obj, Connection conn, Map<String, String> cont) { 
		//Need to change return so that it will return Map<String, Map<STring, String>> so it can return values from more than one column.
		
		log.info("Searching based on input parameters.");
		Map<String, String> ret = new HashMap<String, String>();
		Map<String, Map<String, String>> multi = new HashMap<String, Map<String, String>>();
		MetaModel<?> metaknight = MetaModel.of(obj);
		String sql = "SELECT * FROM " + metaknight.getTableName() + " WHERE";
		
		int col = 1;
		
		List<ColumnField> parthenon = metaknight.getColumns();
		
		for (ColumnField yub : parthenon) {
			
			if (cont.containsKey(yub.getColumnName()) == true) {
				
				log.info("Contains column: " + yub.getColumnName());
				
				sql = sql + " " + yub.getColumnName() + " = " + (yub.getType().getSimpleName().equals("String") ? "'" + cont.get(yub.getColumnName()) + "'" : cont.get(yub.getColumnName()));
				
				if (col < cont.size()) {
					sql = sql + " AND";
				}
			}
			col++;
		}
		List<ForeignKeyField> japan = metaknight.getForeignKeys();
		for(ForeignKeyField canada : japan)
		{
			if(cont.containsKey(canada.getColumnName()) == true)
			{
				log.info("Contains foreign key: " + canada.getColumnName());
				sql = sql + " " + canada.getColumnName() + " = " + (canada.getType().getSimpleName().equals("String") ? "'" + cont.get(canada.getColumnName()) + "'"  : cont.get(canada.getColumnName()));
				if(col < cont.size())
				{
					sql = sql + " AND";
				}
			}
			col++;
		}
		if(cont.containsKey(metaknight.getPrimaryKey().getName()))
		{
			sql = sql + " " + metaknight.getPrimaryKey().getName() + " = " + (metaknight.getPrimaryKey().getType().getSimpleName().equals("String") ? "'" + cont.get(metaknight.getPrimaryKey().getName()) + "'" : cont.get(metaknight.getPrimaryKey().getName()));
		}
		sql += ";";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			log.info("Attempting to execute statement.");
			log.info("Success");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				log.info("Searching through result set.");
				ResultSetMetaData rsmeta = rs.getMetaData();
				for (int i = 1; i <= rsmeta.getColumnCount(); i++) {
					ret.put(rsmeta.getColumnName(i), rs.getString(rsmeta.getColumnName(i)));
					log.info("printing object and value.");
				}
				multi.put(metaknight.getPrimaryKey().getColumnName(), ret);
			}
		} catch (SQLException e) {
			log.warn("Failed to deal with prepared statement.");
			e.printStackTrace();
		} catch (NullPointerException e) {
			log.warn("Out of bounds. " + e);
		}
		log.info("ending readFromDb method.");
		return multi;
	}

	public Map<String, Map<String, String>> getEntryById(Class<?> obj, Connection conn, String id) {
		log.info("Getting columns of specified primary key.");
		Map<String, String> ret = new HashMap<String, String>();
		Map<String, Map<String, String>> multi = new HashMap<String, Map<String, String>>();
		MetaModel<?> meta = MetaModel.of(obj);
		log.info("Created meta model.");
		String sql = "SELECT * FROM " + meta.getTableName() + " WHERE " + meta.getPrimaryKey().getColumnName() + " = "
				+ id;
		try {
			log.info("Creating statement.");
			Statement stmt = conn.createStatement();
			log.info("Executing statement: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			log.info("Success.");
			while (rs.next()) {
				log.info("Getting result set meta data.");
				ResultSetMetaData rsmeta = rs.getMetaData();
				log.info("Success, assigning values to map.");
				for (int i = 1; i <= rsmeta.getColumnCount(); i++) {
					ret.put(rsmeta.getColumnName(i), rs.getString(rsmeta.getColumnName(i)));
				}
				multi.put(ret.get(meta.getPrimaryKey().getColumnName()), ret);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Something went rong, and I'm noot talking about the spelling of the word." + e);
		} catch (NullPointerException e) {
			log.warn("There is nothing there. " + e);
		}
		log.info("Exiting getEntry method.");
		return multi;
	}
	public Map<String, Map<String, String>> getAll(Class<?> obj, Connection conn)
	{
		log.info("Starting getAll method, Creating meta model.");
		MetaModel<?> metaknight = MetaModel.of(obj);
		String sql = "SELECT * FROM " + metaknight.getTableName();
		Map<String, String> ret = new HashMap<String, String>();
		Map<String, Map<String, String>> multi = new HashMap<String, Map<String, String>>();
		int count = 0;
		try
		{
			log.info("Creating statement.");
			Statement stmt = conn.createStatement();
			log.info("Executing statement: " + sql);
			ResultSet rs = stmt.executeQuery(sql);
			log.info("Success.");
			while (rs.next()) {
				log.info("Getting result set meta data.");
				ResultSetMetaData rsmeta = rs.getMetaData();
				log.info("Success, assigning values to map.");
				for (int i = 1; i <= rsmeta.getColumnCount(); i++) {
					ret.put(rsmeta.getColumnName(i), rs.getString(rsmeta.getColumnName(i)));
				}
				boolean join = false;
				Annotation[] a = obj.getAnnotations();
				for(Annotation laz : a)
				{
					if(laz.getClass().toString().equals("Id.class"));
					{
						join = true;
					}
				}
				if(join == true)
				{
					multi.put(ret.get(metaknight.getPrimaryKey().getColumnName()), ret);
				}
				else
				{
					multi.put(String.valueOf(count), ret);
					count++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Something went rong, and I'm noot talking about the spelling of the word." + e);
		} catch (NullPointerException e) {
			log.warn("There is nothing there. " + e);
		}
		log.info("Exiting getAll method.");
		return multi;
	}
}