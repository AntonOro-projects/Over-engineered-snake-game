package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import data.Request;
import data.Response;

public class RunnableSocket implements Runnable {

	private Socket socket;
	private Server server;
	private int id;
	private String userName;
	private DataInputStream in;
	private DataOutputStream out;

	public RunnableSocket(Socket socket, Server server, int id) {
		this.socket = socket;
		this.server = server;
		this.id = id;
		this.userName = null;
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
				String requestString = in.readUTF();
				Gson gson = new Gson();
				Request request = gson.fromJson(requestString, Request.class);
				request.id = id;
				server.addRequest(request);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Integer getID() {
		return id;
	}

	public void endConnection() {
		server.removeClient(id);
	}

	public Socket getSocket() {
		return socket;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
