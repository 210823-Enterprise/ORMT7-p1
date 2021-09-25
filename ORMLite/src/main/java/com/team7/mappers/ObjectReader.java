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

	public boolean readFromDb(Class<?> obj, Connection conn, Map<String, String> cont) {
		
		log.info("Searching based on input parameters.");
		
		MetaModel<?> metaknight = MetaModel.of(obj);
		
		String sql = "SELECT * FROM " + metaknight.getTableName() + " WHERE";
		
		boolean success = false;
		
		int col = 1;
		
		List<ColumnField> parthenon = metaknight.getColumns();
		
		for (ColumnField yub : parthenon) {
			
			if (cont.containsKey(yub.getColumnName()) == true) {
				
				log.info("Contains key: " + yub.getColumnName());
				
				sql = sql + " " + yub.getColumnName() + " = '" + cont.get(yub.getColumnName()) + "'";
				
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
					System.out.println(rsmeta.getColumnName(i) + ": " + rs.getObject(i));
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
		return success;
	}

	public boolean readPrimaryKey(Class<?> obj, Connection conn, Map<String, String> cont) {
		log.info("Getting data based on primary key.");
		MetaModel<?> metaknight = MetaModel.of(obj);
		log.info("Metamodel created.");
		String sql = "SELECT * FROM " + metaknight.getTableName() + " WHERE "
				+ metaknight.getPrimaryKey().getColumnName() + " = " + cont.get(metaknight.getPrimaryKey().getName());
		boolean success = false;

		sql += ";";
		try {
			log.info("Executing statement.");
			PreparedStatement stmt = conn.prepareStatement(sql);
			success = stmt.execute();
			log.info(stmt + " successfully executed.");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				log.info("Getting result set meta data.");
				ResultSetMetaData rsmeta = rs.getMetaData();
				log.info("Data obtained.");
				log.info("Printing columns and values.");
				for (int i = 1; i <= rsmeta.getColumnCount(); i++) {
					System.out.println(rsmeta.getColumnName(i) + ": " + rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Something happenned." + e);
		} catch (NullPointerException e) {
			log.warn("Empty parameter." + e);
		}
		log.info("Exiting readPrimaryKey method.");
		return success;
	}

	public Map<String, String> getEntry(Class<?> obj, Connection conn, Object ki) {
		log.info("Getting columns of specified primary key.");
		Map<String, String> ret = new HashMap<String, String>();
		MetaModel<?> meta = MetaModel.of(obj);
		log.info("Created meta model.");
		String sql = "SELECT * FROM " + meta.getTableName() + " WHERE " + meta.getPrimaryKey().getColumnName() + " = "
				+ ki;
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
}