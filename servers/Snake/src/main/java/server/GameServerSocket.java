package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import data.Connection;

public class GameServerSocket {

	public Connection connection;
	public int boards;
	
	public int port;
	

	public GameServerSocket(Connection connection, int port) {
		this.connection = connection;
		boards = 1;
		this.port = port;
	}
}
