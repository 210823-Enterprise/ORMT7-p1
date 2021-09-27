package com.team7.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.team7.util.IdField;
import com.team7.util.MetaModel;

public class ObjectRemover {
	
	private static Logger log = Logger.getLogger(ObjectRemover.class);

	public static boolean deleteObject(Object obj, Connection conn) {
		MetaModel<?> model = MetaModel.of(obj.getClass());

		String sql = "DELETE FROM " + model.getTableName() + " WHERE " + model.getPrimaryKey().getColumnName() + "= ?";
		IdField f = model.getPrimaryKey();
		int indexType = -1; // -1 for something wrong, 0 for string, 1 for int

		if (f.getSQLType().equals("varchar(250)")) {
			indexType = 0;
		} else if (f.getSQLType().equals("int")) {
			indexType = 1;
		}
		String pkey = f.getFieldValue(obj);
		System.out.println(sql);

		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			switch (indexType) {
			case 0:
				stmt.setString(1, pkey);
				System.out.println(stmt);
				stmt.execute();
				break;
			case 1:
				stmt.setInt(1, Integer.valueOf(pkey));
				System.out.println(stmt);
				stmt.execute();
				break;
			case -1:
				log.warn("Issue with Removal Typing");
				return false;
			}
		} catch (SQLException e) {
			log.error("Unable to delete item, SQL error");
			e.printStackTrace();
			return false;
		}

		return true;

	}

}
