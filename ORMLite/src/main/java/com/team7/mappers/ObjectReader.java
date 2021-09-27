package com.team7.mappers;

import org.apache.log4j.Logger;
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
import com.team7.util.MetaModel;

public class ObjectReader {

	private static Logger log = Logger.getLogger(ObjectReader.class);

	public Map<String, String> readFromDb(Class<?> obj, Connection conn, Map<String, String> cont) { 
		//Need to change return so that it will return Map<String, Map<STring, String>> so it can return values from more than one column.
		
		log.info("Searching based on input parameters.");
		Map<String, String> ret = new HashMap();
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
		sql += ";";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			log.info("Attempting to execute statement.");
			success = stmt.execute();
			log.info("Success");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				log.info("Searching through result set.");
				ResultSetMetaData rsmeta = rs.getMetaData();
				for (int i = 1; i <= rsmeta.getColumnCount(); i++) {
					ret.pu(rsmeta.getColumnName(i), rs.getObject(i)); //TODO return string map instead
					log.info("printing object and value.");
				}
			}
		} catch (SQLException e) {
			log.warn("Failed to deal with prepared statement.");
			e.printStackTrace();
		} catch (NullPointerException e) {
			log.warn("Out of bounds. " + e);
		}
		log.info("ending readFromDb method.");
		return ret;
	}

	public Map<String, String> getEntryById(Class<?> obj, Connection conn, String id) {
		log.info("Getting columns of specified primary key.");
		Map<String, String> ret = new HashMap<String, String>();
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Something went rong, and I'm noot talking about the spelling of the word." + e);
		} catch (NullPointerException e) {
			log.warn("There is nothing there. " + e);
		}
		log.info("Exiting getEntry method.");
		return ret;
	}
	public Map<String, String> getAll(Class<?> obj, Connection conn)
	{
		log.info("Starting getAll method, Creating meta model.");
		MetaModel<?> metaknight = MotaModel.of(obj);
		String sql = "SELECT * FROM " + metaknight.getTableName();
		Map<String, String> ret = new HashMap();
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Something went rong, and I'm noot talking about the spelling of the word." + e);
		} catch (NullPointerException e) {
			log.warn("There is nothing there. " + e);
		}
		log.info("Exiting getAll method.");
		return ret;
	}
}