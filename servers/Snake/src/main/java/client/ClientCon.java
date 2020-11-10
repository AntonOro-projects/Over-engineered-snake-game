package client;

import java.net.*;
import java.util.*;
import java.io.*;

import com.google.gson.Gson;

import data.*;

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

        connections.put(ConContext.MAIN, new Connection());
        connections.put(ConContext.GAME, new Connection());

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

    public static void test1(String x){
        Gson gson = new Gson();
        ClientCon client = new ClientCon("127.0.0.1", 5000);
        client.connectToMain();
        // Login
        AuthenticationData a = new AuthenticationData(x,"pass123");
        Request testRequest = new Request("login", gson.toJson(a));
        client.send(gson.toJson(testRequest));
        Response res = client.receive();
        RequestSuccess s = gson.fromJson(res.jsonData,RequestSuccess.class);
        if(s.value){
            System.out.println("Successful login");
        }
        else{
            System.out.println("Couldn't login");
        }


        // Register
        a = new AuthenticationData(x,"pass123");
        testRequest = new Request("register", gson.toJson(a));
        client.send(gson.toJson(testRequest));
        res = client.receive();
        s = gson.fromJson(res.jsonData,RequestSuccess.class);
        if(s.value){
            System.out.println("Successful register");
        }
        else{
            System.out.println("Couldn't register");
        }

        // Login
        a = new AuthenticationData(x,"pass123");
        testRequest = new Request("login", gson.toJson(a));
        client.send(gson.toJson(testRequest));
        res = client.receive();
        s = gson.fromJson(res.jsonData,RequestSuccess.class);
        if(s.value){
            System.out.println("Successful login");
        }
        else{
            System.out.println("Couldn't login");
        }

        // startGame
        testRequest = new Request("startGame", "");
        client.send(gson.toJson(testRequest));
        Response response = client.receive();

        client.responseHandler.handleResponse(response);
        if (client.currContext == ConContext.GAME) {
            Request r1 = new Request("gameAction", "");
            client.send(gson.toJson(r1));
        }

        while (true) {
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        test1("wiktor");

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

        //while (true) {
        //    // #busyloop
        //}

//        Gson gson = new Gson();
//        ClientCon client = new ClientCon("127.0.0.1", 5000);
//        client.connectToMain();
//        AuthenticationData a = new AuthenticationData("wiktor","pass123");
//        Request testRequest = new Request("login", gson.toJson(a));
//        client.send(gson.toJson(testRequest));
        while (true) {
            try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}