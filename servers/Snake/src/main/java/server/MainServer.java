package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.google.gson.Gson;

import data.Connection;
import data.GameServerInfo;
import data.Party;
import data.Request;
import data.Response;

public class MainServer extends Server {

	private HashMap<Integer, GameServerSocket> gameServers;
	private ArrayList<String> gameQueue;
	private int queueSize;
	private int gameServerSize;
	private ServerSocket serverSocket;

	// constructor with port
	public MainServer(int port) {
		super();
		queueSize = 2;
		gameServerSize = 2;
		gameQueue = new ArrayList<String>();
		gameServers = new HashMap<Integer, GameServerSocket>();
		try {
			serverSocket = new ServerSocket(4999);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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
	protected void addToGameQueue(String id) {
		System.out.println("Adding: " + id);
		gameQueue.add(id);
		if (gameQueue.size() >= queueSize) {
			ArrayList<String> tempQueue = (ArrayList<String>) gameQueue.clone();
			gameQueue.clear();
			try {
				GameServerSocket gameServer = balanceLoad();

				System.out.println("Full queue");
				Gson g = new Gson();
				Party party = new Party();
				for (String userName : tempQueue) {
					System.out.println(hash(userName));
					party.IDMap.put(hash(userName), userName);
				}
				Request request = new Request("party", g.toJson(party));
				gameServer.connection.outputStream.writeUTF(g.toJson(request));
				gameServer.connection.inputStream.readUTF();
				
				
				System.out.println("before ClientId loop");
				for (String s : clientSockets.keySet()) {
					System.out.println("client socket key " + s);
				}
				for (String ClientID : tempQueue) {
					GameServerInfo gsi = new GameServerInfo("127.0.0.1", gameServer.port, hash(ClientID));
					Response r = new Response("joinGameServer", "Info to join gameserver", g.toJson(gsi));
					System.out.println("ClientId " + ClientID);
					sendMessage(ClientID, r);
				}
				System.out.println("after ClientId loop");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private String hash(String userName) {
		char[] tempString = new char[userName.length()];
		for (int i = 0; i < userName.length(); ++i) {
			tempString[i] = userName.charAt(i);
		}
		return Integer.toString(Arrays.hashCode(tempString));
	}

	private GameServerSocket balanceLoad() throws IOException {

		GameServerSocket gss;
		for (int i : gameServers.keySet()) {
			gss = gameServers.get(i);
			if (gss.boards < gameServerSize) {
				gss.boards++;
				return gss;
			}
		}
		int port = gameServers.size() + 5001;
		System.out.println("port = " + port);
		GameServer g = new GameServer("127.0.0.1", port, queueSize);
		(new Thread(g)).start();
		System.out.println("HERE 1");
		Socket socket = serverSocket.accept();
		System.out.println("HERE 2");
		gss = new GameServerSocket(new Connection(socket), port);
		gameServers.put(port, gss);
		System.out.println(gss.connection.inputStream.readUTF());
		return gss;
	}
}
