package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import data.Request;
import data.Response;

public class RunnableSocket implements Runnable {

	private Socket socket;
	private Server server;
	private String id;
	private DataInputStream in;
	private DataOutputStream out;
	private boolean print;

	public RunnableSocket(Socket socket, Server server, String id) {
		this.socket = socket;
		this.server = server;
		this.id = id;
		print = true;
		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		
		while (true) {
			try {
				
//				if (in.available() > 0) {
					String requestString = in.readUTF();
					Gson gson = new Gson();
					Request request = gson.fromJson(requestString, Request.class);
					request.id = id;
					server.addRequest(request);
//				} else {
//					Thread.sleep((long) 500.0);
//				}
				
			} catch (IOException e) {
				if (print) {
					e.printStackTrace();
					print = false;
			}
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
			} 
		}
	}

	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}

	public void endConnection() {
		server.removeClient(id);
	}

	public Socket getSocket() {
		return socket;
	}

	

	public void sendMessage(Response response) {
		Gson gson = new Gson();
		try {
			System.out.println(response.type);
			out.writeUTF(gson.toJson(response));
			System.out.println("Efter sendMessage i RunnableSocket");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
