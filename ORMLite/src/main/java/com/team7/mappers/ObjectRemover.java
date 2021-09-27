package com.team7.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.team7.util.MetaModel;

public class ObjectRemover {

	private static Logger log = Logger.getLogger(ObjectRemover.class);

	public boolean deleteObject(Object obj, Connection conn) {
		MetaModel<?> model = MetaModel.of(obj.getClass());

		String sql = "DELETE FROM " + model.getTableName() + " WHERE " + model.getPrimaryKey().getColumnName() + "="
				+ model.getPrimaryKey().getFieldValue(obj);

		try {
			log.info("Attempting to " + sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			log.info("Successfully deleted " + model.getPrimaryKey().getFieldValue(obj));
		} catch (SQLException e) {
			log.error("Unable to delete item, SQL error");
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean deleteObject(Object obj, Connection conn, Map<String,String> sqlWhere) {
		MetaModel<?> model = MetaModel.of(obj.getClass());	
		String sql = "DELETE FROM " + model.getTableName() + " WHERE ";
		//assemble the query
		Iterator<Map.Entry<String, String>> iterator = sqlWhere.entrySet().iterator();
		while (iterator.hasNext()){
			Map.Entry<String, String> entry = iterator.next();
			sql += entry.getKey() + " = '" + entry.getValue() + "'";
			if (iterator.hasNext()) {
				sql += " AND ";
			}
		}
		
		for(int i=0; i<sqlWhere.size();i++) {
			sqlWhere.entrySet().iterator();
		}
		try {
			log.info("Attempting to " + sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			log.info("Successfully deleted");
		} catch (SQLException e) {
			log.error("Unable to delete item, SQL error");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}