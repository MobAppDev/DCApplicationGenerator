/*
 * 
 * @author Shivani Nayar (shivani.nayar@asu.edu)
 * @Version December,2012
 * 
 */
package com.project.DatabaseManager;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.SlidingDrawer;

import com.project.XMLManager.XMLParser;

public class DatabaseManager {
	private static String TABLENAME = ""; 
	public static InetAddress serverAddress;
	public static Socket socket;
	public static CommunicationThread comThread;
	public static ObjectInputStream ois;
	public static ArrayList<String> list_values = new ArrayList<String>();
	
	private static String createDBString(){
		StringBuilder sb = new StringBuilder(Constants.getInstance().DBCREATE);
		sb.append(TABLENAME).append("(");
		
		for(int i =0; i<XMLParser.fieldsArray.size(); i++){
			if(i>0)
				sb.append(", ");
			FieldInfo fieldInfo = new FieldInfo();
  		    fieldInfo = XMLParser.fieldsArray.get(i);
  		    sb.append(fieldInfo.getDbName()).append(" ").append(fieldInfo.getType());
  		}
		sb.append(" )");
		return sb.toString();
	}
	
	public static void createDatabase(){
		String createStr = Constants.getInstance().DBCREATE_ACTION + createDBString();
		sendToServer(createStr);
	}
	
	public static void addRowData(){
		StringBuilder sb = new StringBuilder(Constants.getInstance().DBINSERT_ACTION);
		sb.append(Constants.getInstance().DBINSERT).append(TABLENAME).append(" values(");
		
		for(int i =0; i<XMLParser.fieldsArray.size(); i++){
			if(i>0){
				sb.append(",");
			}if(XMLParser.fieldsArray.get(i).getType().equals("integer")){
				sb.append(Integer.parseInt(XMLParser.fieldsArray.get(i).getData()));
			}else{
				sb.append("'").append(XMLParser.fieldsArray.get(i).getData()).append("'");
			}
   		}
		sb.append(")");
		sendToServer(sb.toString());
	}
	
	public static void getData(){
		StringBuilder sb = new StringBuilder(Constants.getInstance().DBGETDATA_ACTION);
		sb.append(Constants.getInstance().CLIENTNAME).append(":").append(Constants.getInstance().DBGETDATA).append(TABLENAME);
		sendToServer(sb.toString());
	//	new GetDataSocketTask().execute();

	}
	
	public static void sendToServer(String message) {
		byte[] theByteArray = message.getBytes();
        new WriteToServerTask().execute(theByteArray);		
	}
	
	public static void setTABLENAME(String tABLENAME) {
		TABLENAME = tABLENAME;
	}
	public static String getTABLENAME() {
		return TABLENAME;
	}
	
	public static void startThread(){
		new CreateCommThreadTask().execute();
	}
	
	public static void stopThread(){
		new CloseSocketTask().execute();
	}

	public static void storeResult(Object obj) {
		ResultSet rs = (ResultSet)obj;
		list_values = new ArrayList<String>();
		 try {
			while(rs.next()){
				 list_values.add(rs.getString("first_name")+ " " + rs.getString("last_name"));
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
}

class CreateCommThreadTask extends AsyncTask
<Void, Integer, Void> {
	@Override
	protected Void doInBackground(Void... params) {
		try {
			//---create a socket---
			DatabaseManager.serverAddress =	InetAddress.getByName(Constants.getInstance().INETADDRESS_CST);
			//--remember to change the IP address above to match your own--
			DatabaseManager.socket = new Socket(DatabaseManager.serverAddress, Constants.getInstance().IP_PORT);
			DatabaseManager.comThread = new CommunicationThread(DatabaseManager.socket);
			DatabaseManager.sendToServer(Constants.getInstance().CLIENTNAME);
			//*****To be used to delete the table
			//String tableName = "table:" + DatabaseManager.getTABLENAME();
			//DatabaseManager.sendToServer(tableName);
			DatabaseManager.createDatabase();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			Log.d("Sockets", e.getLocalizedMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Log.d("Sockets", e.getLocalizedMessage());
		}
		return null;
	}
}
class WriteToServerTask extends AsyncTask
<byte[], Void, Void> {
	protected Void doInBackground(byte[]...data) {
		DatabaseManager.comThread.write(data[0]);
		return null;
	}
}
class CloseSocketTask extends AsyncTask
<Void, Void, Void> {
	@Override
	protected Void doInBackground(Void... params) {
		try {
			DatabaseManager.socket.close();
		} catch (IOException e) {
			Log.d("Sockets", e.getLocalizedMessage());
		}
		return null;
	}
}

class GetDataSocketTask extends AsyncTask
<Void, Void, Void> {
	@Override
	protected Void doInBackground(Void... params) {
		Object obj = null;
		try {
			obj = DatabaseManager.comThread.inputStream.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(obj != null)
			DatabaseManager.storeResult(obj);
		
		return null;
	}
}