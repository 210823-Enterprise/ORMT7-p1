package com.team7.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.team7.exceptions.NoColumnsException;
import com.team7.util.ColumnField;
import com.team7.util.ForeignKeyField;
import com.team7.util.MetaModel;

public class ObjectSetter {

	private static Logger log = Logger.getLogger(ObjectSetter.class);

	public boolean setObject(Object obj, Connection conn) {
		MetaModel<?> model = MetaModel.of(obj.getClass());

		// pk and fields are separated to allow for the insertion component (if we get
		// generated values working)
		// this limits us to not be able to use composite keys
		String primaryKey = (model.getPrimaryKey() != null ? model.getPrimaryKey().getColumnName() + " " + model.getPrimaryKey().getSQLType() + " PRIMARY KEY " + "," : "");
		String pkName = (model.getPrimaryKey() != null ? model.getPrimaryKey().getColumnName() + "," : "");
		String pkValue = (model.getPrimaryKey() != null ? model.getPrimaryKey().getFieldValue(obj) + "," : "");
		
		String fields = "";
		String fieldNames = "";
		String values = "";
		
		String foreignKeys = "";
		String foreignKeyNames = "";
		String foreignKeyValues = "";
		String foreignConstraints = "";

		// COLUMNS SECTION
		// builds fields, fieldNames, and values
		for (ColumnField f : model.getColumns()) {
			fields += f.getColumnName() + " " + f.getSQLType() + ",";
			fieldNames += f.getColumnName() + ",";
			values += f.getFieldValue(obj) + ",";
		}
		
		//the delete on cascade action is chosen as the default to maintain referential integrity
		for (ForeignKeyField f : model.getForeignKeys()) {
			foreignKeys += f.getColumnName() + " " + f.getSQLType() + ",";
			foreignKeyNames += f.getColumnName() + ",";
			foreignKeyValues += f.getFieldValue(obj) + ",";
			foreignConstraints += " FOREIGN KEY (" + f.getColumnName() + ") REFERENCES " + f.getReferenceTable() + "(" + f.getReferencedKey() +") " + "ON DELETE CASCADE,";
		}

		// TRIMMING (comma removal)
		//defend against empty/unannotated classes
		if (fields.length() == 0 && foreignKeys.length() == 0 && primaryKey.length() == 0) {
			log.warn("class " + model.getSimpleClassName() + " has not been annotated");
			throw new NoColumnsException("the class attempted to be persisted has no annotated fields");
			
		// trim column related strings
		} else if (fields.length() > 0 && foreignKeys.length() == 0) {
			fields = fields.substring(0, fields.length() - 1);
			fieldNames = fieldNames.substring(0, fieldNames.length() - 1);
			values = values.substring(0, values.length() - 1);

			// trims comma from primary key in case that's all the object has
		} else if (fields.length() == 0 && foreignKeys.length() == 0) {
			primaryKey = primaryKey.substring(0, primaryKey.length() - 1);
			pkName = pkName.substring(0, pkName.length() - 1);
			pkValue = pkValue.substring(0, pkValue.length() - 1);
			
		}
		
		// foreign keys will always be last in this
		if (foreignKeys.length() > 0) {
			foreignKeyNames = foreignKeyNames.substring(0, foreignKeyNames.length() - 1);
			foreignKeyValues = foreignKeyValues.substring(0, foreignKeyValues.length() - 1);
			foreignConstraints = foreignConstraints.substring(0, foreignConstraints.length() - 1);
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
				+ foreignKeys + foreignConstraints + ")";
		
		String recordInsert = "INSERT INTO " + model.getSimpleClassName() + " (" + pkName + fieldNames + foreignKeyNames + ") VALUES ("
				+ pkValue + values + foreignKeyValues + ");";

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
