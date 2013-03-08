/*
 * @author: Shivani Nayar <shivani.nayar@asu.edu>
 * @date: Dec'12
 * 
 */

package com.project.DatabaseManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
public class CommunicationThread extends Thread {
	
	private final Socket socket;
	public final InputStream inputStream;
	private final OutputStream outputStream;
	public CommunicationThread(Socket sock) {
		socket = sock;
		InputStream tmpIn = null;
		OutputStream tmpOut = null;
		try {
			//---creates the inputstream and outputstream objects
			// for reading and writing through the sockets---
			tmpIn = socket.getInputStream();
			tmpOut = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		inputStream = tmpIn;
		outputStream = tmpOut;
	}
	public void run() {
		//---buffer store for the stream---
		byte[] buffer = new byte[1024];
		//---bytes returned from read()---
		int bytes;
		//---keep listening to the InputStream until an
		// exception occurs---
		while (true) {
			try {
				Object obj = inputStream.read();
				if(obj != null)
					DatabaseManager.storeResult(obj);
			/*	Object obj = (Object) new ObjectInputStream(inputStream).readObject();
				if(obj != null){
					DatabaseManager.storeResult(obj);
					this.cancel();
				}*/
				//---read from the inputStream---
				//bytes = inputStream.read(buffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//---call this from the main activity to
	// send data to the remote device---
	public void write(byte[] bytes) {
		try {
			outputStream.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object read(Object obj) {
		try {
			obj = inputStream.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(obj != null)
			DatabaseManager.storeResult(obj);
		return obj;
	}
	
	//---call this from the main activity to
	// shutdown the connection---
	public void cancel() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}