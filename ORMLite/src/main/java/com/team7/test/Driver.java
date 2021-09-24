package com.team7.test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Map<String, String> val = read.getEntry(Testr.class, conn, 1);
		MetaModel<?> meta = MetaModel.of(Testr.class);
		for(ColumnField c : meta.getColumns())
		{
			System.out.println(val.get(c.getColumnName()));
		}
		read.readPrimaryKey(Testr.class, conn);
	}
	
}
