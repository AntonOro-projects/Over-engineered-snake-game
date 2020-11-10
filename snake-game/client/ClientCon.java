package client;

import java.net.*;
import java.util.*;
import java.io.*;

import com.google.gson.Gson;

import data.Connection;
import data.Request;
import data.Response;

public class ClientCon {
    // Sockets and data streams for main server connection
    private String mAddress;
    private int mPort;

    private Gson gson;

    private Map<ConContext, Connection> connections;
    public ConContext currContext;

    public ResponseHandler responseHandler;

    // constructor to put ip address and port
    public ClientCon(String address, int port) {
        this.mAddress = address;
        this.mPort = port;
        this.currContext = ConContext.MAIN;
        gson = new Gson();

        this.connections = new HashMap<ConContext, Connection>();

        connections.put(ConContext.MAIN, new Connection(null, null, null));
        connections.put(ConContext.GAME, new Connection(null, null, null));

        responseHandler = new ResponseHandler(this);
    }

    public void connectToMain() {
        // establish a connection
        try {
            connections.get(ConContext.MAIN).socket = new Socket(mAddress, mPort);
            System.out.println("Connecting to " + mAddress + " on port " + mPort);

            // takes input from terminal
            connections.get(ConContext.MAIN).inputStream = new DataInputStream(
                    connections.get(ConContext.MAIN).socket.getInputStream());

            // sends output to the socket
            connections.get(ConContext.MAIN).outputStream = new DataOutputStream(
                    connections.get(ConContext.MAIN).socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public void connectToGame(String address, int port) {
        // establish a connection
        try {
            connections.get(ConContext.GAME).socket = new Socket(address, port);
            System.out.println("Connecting to " + address + " on port " + port);

            // takes input from terminal
            connections.get(ConContext.GAME).inputStream = new DataInputStream(
                    connections.get(ConContext.GAME).socket.getInputStream());

            // sends output to the socket
            connections.get(ConContext.GAME).outputStream = new DataOutputStream(
                    connections.get(ConContext.GAME).socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public String send(String jsonMessage) {
        // Send the jsonMessage
        try {
            connections.get(currContext).outputStream.writeUTF(jsonMessage);
        } catch (IOException i) {
            System.out.println(i);
        }

        return "";
    }

    Response receive() {
        // Send the jsonMessage
        try {
            System.out.println("Before receive inputstream");
            String jsonText = connections.get(currContext).inputStream.readUTF();
            System.out.println("After receive inputstream: " + jsonText);
            return gson.fromJson(jsonText, Response.class);
        } catch (IOException i) {
            System.out.println(i);
            return new Response("", "", "");
        }
    }

    public static void main(String args[]) {
        Gson gson = new Gson();
        ClientCon client = new ClientCon("127.0.0.1", 5000);
        client.connectToMain();
        Request testRequest = new Request("startGame", "");
        client.send(gson.toJson(testRequest));
        Response response = client.receive();

        client.responseHandler.handleResponse(response);
        if (client.currContext == ConContext.GAME) {
            Request r1 = new Request("gameAction", "");
            client.send(gson.toJson(r1));
        }

        while (true) {
            // #busyloop
        }
    }
}