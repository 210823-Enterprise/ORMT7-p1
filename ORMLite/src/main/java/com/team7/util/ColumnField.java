package com.team7.util;

import java.lang.reflect.Field;

import com.team7.annotations.Column;

public class ColumnField {

    private Field field;

    public ColumnField(Field field) {

        if (field.getAnnotation(Column.class) == null) {
            // If the field object that we pass through DOESN't have the column annotation,
            // then it returns null
            throw new IllegalStateException(
                    "Cannot create ColumnField Object! Provided field " + getName() + " is not annotated with @Column");
            //TODO loggers
        }

        this.field = field;

    }

    public String getName() {
        return field.getName();
    }

    public Class<?> getType() {
        return field.getType();
    }

    public String getColumnName() {
        return field.getAnnotation(Column.class).columnName();

    }

    //TODO make this inherited or from util class (try to only write it once)
    public String getSQLType() {
        String type = field.getType().getSimpleName();
        
        String retVal; //return type string
        switch (type) {
        case "int":
        case "Integer":
            retVal = "integer";
            break;
        case "String":
            retVal = "varchar(250)";
            break;
        default :
            retVal = "varchar(10)"; //type not found TODO put log here
        }
        
        return retVal;
    }
    
    public String getFieldValue(Object o) {
        try {
            field.setAccessible(true);
            //surround with single quotes for varchar
            return (field.getType().getSimpleName().equals("String") ? "'" + field.get(o).toString() + "'" : field.get(o).toString());
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            // TODO logs
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return "ColumnField [field=" + field.toGenericString() + "]";
    }
}