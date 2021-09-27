package com.team7.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.team7.util.MetaModel;

public class ObjectRemover {

	private static Logger log = Logger.getLogger(ObjectRemover.class);

	public static boolean deleteObject(Object obj, Connection conn) {
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

}
