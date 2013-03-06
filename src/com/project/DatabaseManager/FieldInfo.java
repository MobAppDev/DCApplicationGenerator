/*
 * 
 * @author Shivani Nayar (shivani.nayar@asu.edu)
 * @Version December,2012
 * 
 */
package com.project.DatabaseManager;

import java.util.ArrayList;

public class FieldInfo 
{
	private String dbName= "", type = "", name = "", hint = "", data = "", inputType = "", fieldType = ""; 
	private Boolean required = false;
	private ArrayList<String> values = new ArrayList<String>();
	
	public ArrayList<String> getValues() {
		return values;
	}

	public FieldInfo() {
		super();
	}

	public FieldInfo(String dbName, String type, String name, String hint, String inputType, String fieldType, Boolean required,ArrayList<String> values) {
		this.dbName = dbName;
		this.type = type;
		this.name = name;
		this.hint = hint;
		this.inputType = inputType;
		this.required = required;
		this.fieldType = fieldType;
		this.values = values;
	}

	public String getFieldType() {
		return fieldType;
	}

	public String getDbName() {
		return dbName;
	}
	
	public String getType() {
		return type;
	}
	
	public String getFieldName() {
		return name;
	}
	
	public String getHint() {
		return hint;
	}
	
	public Boolean getRequired() {
		return required;
	}

	public String getInputType() {
		return inputType;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
