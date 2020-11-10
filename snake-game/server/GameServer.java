package server;

import data.Request;

public class GameServer extends Server {

    // constructor with port
    public GameServer(String address, int port) {
        super();
        requestHandler = new GameRequestHandler();
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
		    	requestHandler.handleRequest(request, this);
		    }

		    if (clientSockets.size() == 5) {
		        break;
		    }
			System.out.println("Number of Game server clients: " + clientSockets.size());
			for(int i : clientSockets.keySet()){
				System.out.println("socket id:" + i);
			}
		}

		System.out.println("Closing connection");
    }

    public static void main(String args[]) {
        new GameServer("127.0.0.1", 5001);
    }

	@Override
	protected void addToGameQueue(int id) {
		// TODO Auto-generated method stub
		
	}
}