package server;

import java.util.ArrayList;
import java.util.HashMap;

import data.*;

import java.io.*;

public abstract class Server {
	// initialize socket and input stream

	protected HashMap<Integer, RunnableSocket> clientSockets;
	protected ArrayList<String> commands;
	protected RequestHandler requestHandler;
	protected MailBox requests;

	public Server() {
		this.requestHandler = new RequestHandler();
		requests = new MailBox();
		clientSockets = new HashMap<Integer, RunnableSocket>();
	}

	public void addClient(RunnableSocket socket) {
		clientSockets.put(socket.getID(), socket);
	}

	public void removeClient(Integer id) {
		RunnableSocket s = clientSockets.remove(id);
		try {
			s.getSocket().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addRequest(Request request) {
		requests.addRequest(request);
	}

	public void sendMessage(int id, Response response) {
		clientSockets.get(id).sendMessage(response);
	}


	protected abstract void addToGameQueue(int id);
	
	
}