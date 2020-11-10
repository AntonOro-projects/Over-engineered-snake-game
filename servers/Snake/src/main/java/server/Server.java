package server;

import java.util.ArrayList;
import java.util.HashMap;

import data.*;

import java.io.*;

public abstract class Server {
	// initialize socket and input stream

	protected HashMap<String, RunnableSocket> clientSockets;
	protected ArrayList<String> commands;
	protected RequestHandler requestHandler;
	protected MailBox<Request> requests;

	public Server() {
		this.requestHandler = new RequestHandler();
		requests = new MailBox<Request>();
		clientSockets = new HashMap<String, RunnableSocket>();
	}

	public void addClient(RunnableSocket socket) {
		clientSockets.put(socket.getID(), socket);
	}

	public void removeClient(String id) {
		RunnableSocket s = clientSockets.remove(id);
		try {
			s.getSocket().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addRequest(Request request) {
		requests.addItem(request);
	}

	public void sendMessage(String id, Response response) {
		System.out.println("send message client id " + id);
		if (clientSockets.get(id) == null)
		{
			System.out.println("null client hashmap");
		}
		else {
			System.out.println("found client id hashmap");
		}
		clientSockets.get(id).sendMessage(response);
	}


	protected abstract void addToGameQueue(String id);
	public void addSnakeAction(Request request) {
		
	}
	
}