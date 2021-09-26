package com.team7.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.team7.util.ColumnField;
import com.team7.util.MetaModel;

public class ObjectSetter {

	private static Logger log = Logger.getLogger(ObjectSetter.class);

	public boolean setObject(Object obj, Connection conn) {
		MetaModel<?> model = MetaModel.of(obj.getClass());

		// pk and fields are separated to allow for the insertion component (if we get
		// generated values working)
		// this limits us to not be able to use composite keys
		String primaryKey = model.getPrimaryKey().getColumnName() + " " + model.getPrimaryKey().getSQLType() + " PRIMARY KEY " + ",";
		String pkName = model.getPrimaryKey().getColumnName() + ",";
		String pkValue = model.getPrimaryKey().getFieldValue(obj) + ",";
		String fields = "";
		String fieldNames = "";

		// COLUMNS SECTION
		// builds fields and fieldNames
		for (ColumnField f : model.getColumns()) {
			fields += f.getColumnName() + " " + f.getSQLType() + ",";
			fieldNames += f.getColumnName() + ",";
		}

		// builds values
		String values = "";
		
		for (ColumnField f : model.getColumns()) {
			values += f.getFieldValue(obj) + ",";
		}

		// TRIMMING (comma removal)
		// trim fields and fieldNames
		if (fields.length() > 2) {
			fields = fields.substring(0, fields.length() - 1);
			fieldNames = fieldNames.substring(0, fieldNames.length() - 1);

			// trims comma from primary key in case that's all the object has
		} else {
			primaryKey = primaryKey.substring(0, primaryKey.length() - 1);
			pkName = pkName.substring(0, pkName.length() - 1);
		}

		// trim values
		if (values.length() > 2) {
			values = values.substring(0, values.length() - 1);

			// trims comma from primary key in case that's all the object has
		} else {
			pkValue = pkValue.substring(0, pkValue.length() - 1);
		}

		/*
		 * CREATING THE SQL
		 *
		 * (opting not to use preparedstatement vary command length; less secure because
		 * of the potential for injection, but this expedites this example)
		 */
		log.info("Attempting to create table if needed...");
		// effectively prepares statement but with expandable length
		String tableCreate = "CREATE TABLE IF NOT EXISTS " + model.getSimpleClassName() + " (" + primaryKey + fields
				+ ")";

		String recordInsert = "INSERT INTO " + model.getSimpleClassName() + " (" + pkName + fieldNames + ") VALUES ("
				+ pkValue + values + ");";

		try {
			PreparedStatement tblcrt = conn.prepareStatement(tableCreate);
			PreparedStatement insrt = conn.prepareStatement(recordInsert);
			log.info("inserting record to table for " + model.getSimpleClassName());
			
			tblcrt.executeUpdate();
			insrt.executeUpdate(); 
			

		} catch (SQLException e) {
			log.warn("unable to add record");
			
			return false;
		}

		return true; 

	}

}
