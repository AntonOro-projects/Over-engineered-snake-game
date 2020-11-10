package server;

import java.util.ArrayList;


import com.google.gson.Gson;

import data.GameServerInfo;

import data.Request;
import data.Response;

public class MainServer extends Server {

	
	
	private ArrayList<Integer> gameQueue;
	private int queueSize;


	// constructor with port
	public MainServer(int port) {
		super();
		queueSize = 4;
		gameQueue = new ArrayList<Integer>();
		Connector connector = new Connector(this, port);
		(new Thread(connector)).start();

		// takes input from the client socket

		// reads message from client until "Over" is sent

		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (Request request : requests.fetchAll()) {
				System.out.println("Request in the handleRequest loop: " + request.type);
				requestHandler.handleRequest(request, this);
			}

			System.out.println("Number of Main server clients: " + clientSockets.size());

		}
	}

	public static void main(String args[]) {
		MainServer server = new MainServer(5000);
	}



	@Override
	protected void addToGameQueue(int id) {
		System.out.println("Adding: " + id);
		gameQueue.add(id);
		if (gameQueue.size() == queueSize) {
			System.out.println("Full queue");
			Gson g = new Gson();
			GameServerInfo gsi = new GameServerInfo();
	        gsi.address="127.0.0.1";
	        gsi.port=5001;
	        Response r = new Response("joinGameServer","Info to join gameserver",g.toJson(gsi));
			for (Integer ClientID : gameQueue) {
				sendMessage(ClientID,r);
			}
			gameQueue.clear();
		}
		
		
	} 
} 

