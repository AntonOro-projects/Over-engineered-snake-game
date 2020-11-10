package Communication;

import java.util.Arrays;
import com.google.gson.Gson;

import data.Request;

public class Client {
	private ClientCon con;

	// constructor to put ip address and port
	public Client(String address, int port) {
		con = new ClientCon(address, port);
		con.connectToMain();
	}

	public void sendRequest(String type, Object data) {
		if (Arrays.asList(Request.TYPES).contains(type)) {
			Gson gson = new Gson();
			con.send(gson.toJson(new Request(type, gson.toJson(data))));
		} else {
			System.err.println("Invalid type.");
		}
	}

	public static void main(String args[]) {
		new Client("127.0.0.1", 5000);

	}

}
