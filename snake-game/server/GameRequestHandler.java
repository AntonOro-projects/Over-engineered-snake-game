package server;

import data.Request;

public class GameRequestHandler extends RequestHandler {
    public GameRequestHandler() {
        super();
    }

    @Override
    public String handleRequest(Request request, Server server) {

        switch (request.type) {
            case "login":
                return this.login(request, server);
            case "register":
                return this.register(request, server);
            case "startGame":
                return this.startGame(request, server);
            case "getHighScore":
                return this.getHighScore(request, server);
            case "gameAction":
                return this.gameAction(request);
            default:
                return "Invalid request type";
        }
    }

    public void handleAll() {

    }

    private String gameAction(Request request) {
        System.out.println("REERERE");
        return "";
    }

}