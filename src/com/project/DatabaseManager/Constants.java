/*
 * 
 * @author Shivani Nayar (shivani.nayar@asu.edu)
 * @Version December,2012
 * 
 */
package com.project.DatabaseManager;

public class Constants {
	private static Constants instance = null;
	
	public final String DBCREATE_ACTION 	= "createTable:";
	public final String DBINSERT_ACTION 	= "insert:";
	public final String DBGETDATA_ACTION 	= "get:";
	public final String DBGETDATA			= "select * from ";
	public final String CLIENTNAME 			= "client:DBTHREAD";
	public final String DBCREATE 			= "create table if not exists ";
	public final String DBINSERT			= "insert into ";
	public final String INETADDRESS_HOME 	= "192.168.10.193";
	public final String INETADDRESS_CST 	= "10.140.112.63";
	public final int IP_PORT 				= 2020; 
	
	
	
	public static Constants getInstance(){
		if(instance == null)
			instance = new Constants();
		return instance;
	}
}
