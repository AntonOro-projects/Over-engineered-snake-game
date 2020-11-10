package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

import data.Connection;
import data.Party;
import data.Request;
import data.RequestSuccess;
import data.Response;
import data.SnakeAction;
import module3.Logic;

public class GameServer extends Server implements Runnable {

	private int port;
	private String address;
	private Connection mainServerConnection;
	public HashMap<Integer, ArrayList<String>> queues;
	public HashMap<Integer, MailBox<SnakeAction>> mailBoxes;
	public HashMap<Integer, Party> parties;
	public HashMap<String, Integer> userNameToCounter;
	private int counter;
	private int size;

	// constructor with port

	public GameServer(String address, int port, int size) {
		super();
		this.size = size;
		this.port = port;
		this.address = address;
		this.mailBoxes = new HashMap<Integer, MailBox<SnakeAction>>();
		this.parties = new HashMap<Integer, Party>();
		this.queues = new HashMap<Integer, ArrayList<String>>();
		this.userNameToCounter = new HashMap<String, Integer>();
		counter = 0;
	}

	public static void main(String args[]) {
		new GameServer("127.0.0.1", 5001, 2);
	}

	@Override
	protected void addToGameQueue(String id) {
		ArrayList<String> currentQueue = queues.get(userNameToCounter.get(id));
		currentQueue.add(id);
		
		if (currentQueue.size() >= size) {
			// ArrayList<String> snakeList = (ArrayList<String>) gameQueue.subList(0,2);
			ArrayList<String> snakeList = new ArrayList<String>();
			for (int i = 0; i < size; i++) {
				String snake = currentQueue.get(0);
				snakeList.add(snake);
//				Response response = new Response("gameConnect", "", gson.toJson(new RequestSuccess(true)));
//				sendMessage(snake, response);
				currentQueue.remove(0);
			}
			System.out.println("before create logic");
			Logic logic = new Logic(mailBoxes.get(userNameToCounter.get(id)), this, snakeList);
			System.out.println("before thread logic");
			(new Thread(logic)).start();
			System.out.println("started logic");

		}
	}

	public void addSnakeAction(Request request) {
		Gson gson = new Gson();
		SnakeAction sa = gson.fromJson(request.jsonData, SnakeAction.class);
		sa.id = request.id;
		mailBoxes.get(userNameToCounter.get(request.id)).addItem(sa);
	}
	
	@Override
	public void run() {
		Gson gson = new Gson();
		try {
			Socket s = new Socket("127.0.0.1", 4999);
			mainServerConnection = new Connection(s);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		requestHandler = new GameRequestHandler();
		Connector connector = new Connector(this, port);
		(new Thread(connector)).start();
		try {
			mainServerConnection.outputStream.writeUTF("Connector created");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// takes input from the client socket

		// reads message from client until "Over" is sent
		
		while (true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				if (mainServerConnection.inputStream.available() > 0) {
					Request request = gson.fromJson(mainServerConnection.inputStream.readUTF(), Request.class);
					if (!request.type.equals("party")) {
						System.out.println("Panic! Something other than a party was sent from main server to game server!");
					} else {
						Party party = gson.fromJson(request.jsonData, Party.class);
						addParty(party);
						mainServerConnection.outputStream.writeUTF("confirmed");
					}
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (Request request : requests.fetchAll()) {
				requestHandler.handleRequest(request, this);
			}

			
			System.out.println("Number of Game server clients: " + clientSockets.size());
			for (String i : clientSockets.keySet()) {
				System.out.println("socket id:" + i);
			}
		}

		// System.out.println("Closing connection");
	}

	private void addParty(Party party) {
		mailBoxes.put(++counter, new MailBox<SnakeAction>());
		parties.put(counter, party);
		queues.put(counter, new ArrayList<String>());
		for (String userName : party.IDMap.values()) {
			userNameToCounter.put(userName, counter);
		}
	}

	public void endGame(int partyID){
		for(String player : parties.get(partyID).IDMap.values()){
			try{
				clientSockets.get(player).getSocket().close();
			}catch(IOException e){
				System.out.println("Failed to close socket");
			}
			clientSockets.remove(player);
		}

		queues.remove(partyID);
		mailBoxes.remove(partyID);
		parties.remove(partyID);
	}
}