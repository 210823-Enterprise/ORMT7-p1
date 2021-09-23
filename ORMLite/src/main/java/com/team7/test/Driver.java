package com.team7.test;

import java.sql.Connection;
import java.util.List;

import com.team7.mappers.ObjectReader;
import com.team7.mappers.ObjectSetter;
import com.team7.util.ColumnField;
import com.team7.util.Configuration;
import com.team7.util.ConnectionUtil;
import com.team7.util.MetaModel;
import com.team7.test.Testr;

public class Driver {
	
	public static void main(String[] args) {
		//set
		Connection conn = ConnectionUtil.getConnection();
		ObjectReader read = new ObjectReader();
		Configuration cfg = new Configuration();
		cfg.addAnnotatedClass(Testr.class);
		for (MetaModel<?> metamodel : cfg.getMetaModels()) {
			
			System.out.printf("Printing metamodel for class: %s\n ", metamodel.getClassName()); // %s is a place holder
			
			List<ColumnField> columnFields = metamodel.getColumns();
			
			for (ColumnField cf : columnFields) {
					
				System.out.printf("Found a column field named %s of type %s, which maps to the DB column %s\n", cf.getName(), cf.getType(), cf.getColumnName());
				
			}
		}
		System.out.println(Testr.class);
		read.readFromDb(Testr.class, conn);
		
	}
	
}
