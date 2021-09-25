package com.team7.mappers;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.team7.util.MetaModel;

public class ObjectUpdater {
	private static Logger log = Logger.getLogger(ObjectSetter.class);
	
	public boolean updateObject(Object obj, Connection conn) {
		MetaModel<?> model = MetaModel.of(obj.getClass());
		
		//obtain info from object
		String pkName = model.getPrimaryKey().getColumnName();
		
		//update tableName to object values where pk in table == pk in object
		
	}
}
