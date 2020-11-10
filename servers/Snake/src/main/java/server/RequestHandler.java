package server;

import com.google.gson.Gson;

import data.*;
import database.Database;
import org.json.JSONObject;

/*
Class for handling initial requests from client to the main server.
 */
public class RequestHandler {
    protected Gson g;
    protected Database db;

    public RequestHandler() {
        this.g = new Gson();
        try {
            this.db = new Database("snekdb");
            if (this.db != null) {
                System.out.println("after db init, try");
            } else {
                System.out.println("db is null");
            }
        } catch (Exception e) {
            System.out.println("Could not initialize the database");
        }
    }

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
        }
    }

    protected void login(Request req, Server server) {
        try {
            AuthenticationData reqData = g.fromJson(req.jsonData, AuthenticationData.class);
            JSONObject dbResponse = this.db.verifyUser(reqData.getUserName(), reqData.getPwHash());
            System.out.println(dbResponse);
            Response res;
            if (dbResponse.get("success").equals(true)) {
                System.out.println("User: " + reqData.getUserName() + " logged in.");
                server.clientSockets.get(req.id).setID(reqData.getUserName());
                server.clientSockets.put(reqData.getUserName(), server.clientSockets.get(req.id));
                server.clientSockets.remove(req.id);
                res = new Response("login", "Successfully logged in.", g.toJson(new RequestSuccess(true)));
            } else {
                System.out.println("User: " + reqData.getUserName() + " failed to log in.");
                res = new Response("login", "Unsuccessful login attempt.", g.toJson(new RequestSuccess(false)));
            }

            server.sendMessage(reqData.getUserName(), res);
        } catch (NullPointerException e) {
            System.out.println("Nullpointerexception at database.");
            server.sendMessage(req.id,
                    new Response("register", "Nullpointerexception at database", g.toJson(new RequestSuccess(false))));
        }
    }

    protected void register(Request req, Server server) {
        try {
            AuthenticationData reqData = g.fromJson(req.jsonData, AuthenticationData.class);
            JSONObject dbResponse = this.db.createUser(reqData.getUserName(), reqData.getPwHash());
            Response res;
            if (dbResponse.get("success").equals(true)) {
                System.out.println("The account: " + reqData.getUserName() + " is now registered.");
                res = new Response("register", "Successfully registered the account.",
                        g.toJson(new RequestSuccess(true)));
            } else {
                res = new Response("register", "Couldn't register the account.", g.toJson(new RequestSuccess(false)));
            }
            server.sendMessage(req.id, res);
        } catch (NullPointerException e) {
            System.out.println("Nullpointerexception at database.");
            server.sendMessage(req.id,
                    new Response("register", "Nullpointerexception at database", g.toJson(new RequestSuccess(false))));
        }
    }

    protected void getHighScore(Request req, Server server) {
        try {
            AuthenticationData reqData = g.fromJson(req.jsonData, AuthenticationData.class); // <--- TODO behöver ändras
            JSONObject dbResponse = this.db.getUserHighscore(reqData.getUserName(), 1);
            Response res;
            if (dbResponse.get("success").equals(true)) {
                System.out.println("The account: " + reqData.getUserName() + " is now registered.");
                res = new Response("register", "Successfully registered the account.", "");
            } else {
                res = new Response("register", "Couldn't register the account.", "");
            }
            server.sendMessage(req.id, res);
        } catch (NullPointerException e) {
            System.out.println("Nullpointerexception at database.");
            server.sendMessage(req.id,
                    new Response("register", "Nullpointerexception at database", g.toJson(new RequestSuccess(false))));
        }
    }

    protected void startGame(Request req, Server server) {
        System.out.println("Added the client to a waiting queue.");
        server.addToGameQueue(req.id);
    }
}
