package server;

import data.Party;
import data.Request;
import data.Response;

public class GameRequestHandler extends RequestHandler {
    public GameRequestHandler() {
        super();
    }

    @Override
    public void handleRequest(Request request, Server server) {

        switch (request.type) {
            case "login":
                this.login(request, server);
                return;
            case "register":
                this.register(request, server);
                return;
            case "startGame":
                this.startGame(request, server);
                return;
            case "getHighScore":
                this.getHighScore(request, server);
                return;
            case "gameAction":
                this.gameAction(request, server);
                return;
            case "key":
            	this.key(request, server);
            	return;
        }
    }

    private void key(Request request, Server server) {
    	GameServer gs = (GameServer) server;
    	String currID = request.id;
    	
    	for (Party party : gs.parties.values()) {
    		if (party.IDMap.containsKey(request.jsonData)) {
    			RunnableSocket socket = server.clientSockets.get(currID);
    			String newID = party.IDMap.get(request.jsonData);
    			socket.setID(newID);
    			server.clientSockets.remove(currID);
    			server.clientSockets.put(newID, socket);
    	    	server.sendMessage(newID, new Response("confirmation", "", ""));
    			break;
    		}
    	}
	}

	public void handleAll() {

    }

    private void gameAction(Request request, Server server) {
    	
        server.addSnakeAction(request);
    }

    @Override
    protected void startGame(Request req, Server server){
    	
        server.addToGameQueue(req.id);
    }
}