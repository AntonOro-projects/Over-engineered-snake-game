package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector implements Runnable {

	private Server server;
	private int port;
	private int counter;

	public Connector(Server server, int port) {
		this.server = server;
		this.port = port;
		this.counter = 0;
	}

	public void run() {
		ServerSocket serverSocket = null;
		System.out.println("Connector started");
		try {

			serverSocket = new ServerSocket(port);
			while (true) {

				System.out.println("Waiting for a client ...");

				Socket socket = serverSocket.accept();
				RunnableSocket rs = new RunnableSocket(socket, server, generateID());
				(new Thread(rs)).start();
				server.addClient(rs);
				System.out.println("Client accepted");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int generateID() {
		return ++counter;
	}

}
