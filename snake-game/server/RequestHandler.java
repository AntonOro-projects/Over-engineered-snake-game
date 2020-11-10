package server;

import com.google.gson.Gson;

import data.Connection;
import data.GameServerInfo;
import data.Request;
import data.Response;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/*
Class for handling initial requests from client to the main server.
 */
public class RequestHandler {
    protected Gson g;
    protected String dbAddress;
    protected int dbPort;

    public RequestHandler() {
        this.g = new Gson();
    }

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
            default:
                return "Invalid request type";
        }
    }

    private Connection connectToDatabase() {
        Socket dbSocket = null;
        DataInputStream dbInput = null;
        DataOutputStream dbOutput = null;

        try {
            dbSocket = new Socket(dbAddress, dbPort);
        } catch (IOException i) {
            System.out.println("Couldn't establish a connection with the database.");
        }
        System.out.println("RequestHandler connecting to database: " + dbAddress + " on port " + dbPort);
        // Initialize dbSocketInputStream
        try {
            dbInput = new DataInputStream(dbSocket.getInputStream());
        } catch (IOException e) {
            System.out.println(e);
        }

        // Initialize dbSocketOutputStream
        try {
            dbOutput = new DataOutputStream(dbSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e);
        }

        return new Connection(dbSocket, dbInput, dbOutput);
    }

    boolean isValidConnection(Connection con) {
        // Check that all of the connection components initialized properly
        return con.socket != null && con.inputStream != null && con.outputStream != null;
    }

    protected String login(Request req, Server server) {
        Connection dbCon = connectToDatabase();

        // Check if connection failed to initialize correctly
        if (!isValidConnection(dbCon)) {
            return "Failed to connect to database";
        }

        // API call to database
        return "";
    }

    protected String register(Request req, Server server) {
        Connection dbCon = connectToDatabase();

        // Check if connection failed to initialize correctly
        if (!isValidConnection(dbCon)) {
            return "Failed to connect to database";
        }

        // API call to database
        return "";
    }

    protected String getHighScore(Request req, Server server) {
        Connection dbCon = connectToDatabase();

        // Check if connection failed to initialize correctly
        if (!isValidConnection(dbCon)) {
            return "Failed to connect to database";
        }

        // API call to database
        return "";
    }


    protected String startGame(Request req, Server server){
    	server.addToGameQueue(req.id);
//        System.out.println("We are in the startGame function at mainserver");
//        // Retrieve free gameserver address and port
//        GameServerInfo gsi = new GameServerInfo();
//        gsi.address="127.0.0.1";
//        gsi.port=5001;
//        Response r = new Response("joinGameServer","Info to join gameserver",g.toJson(gsi));
//        server.sendMessage(req.id,r);
//        System.out.println("After sending back a response from requesthandler");
//        // User wants to join a game queue

        return "";
    }
}
