package Communication;

import com.google.gson.Gson;
import data.GameServerInfo;
import data.Request;
import data.Response;

public class ResponseHandler {
	private ClientCon con;
	private Gson gson;

	public ResponseHandler(ClientCon con) {
		this.gson = new Gson();
		this.con = con;
	}

	public boolean handleResponse(Response response) {
		System.out.println("handle response type: " + response.type);
		switch (response.type) {
			case "gameState":
				this.GameState(response);
				return true;
			case "joinGameServer":
				this.joinGameServer(response);
				return false;
			case "login":
				return true;
			case "gameConnect":
				return true;
			case "register":
				return true;
			case "confirmation":
				return true;
			default:
				System.err.println("Invalid response received.");
				return false;
		}
	}

	private void GameState(Response response) {

	}

	private void joinGameServer(Response response) {
		Gson gson = new Gson();
		GameServerInfo info = gson.fromJson(response.jsonData, GameServerInfo.class);
		System.out.println("Before connec to game");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		con.connectToGame(info.address, info.port);
		System.out.println("After connect to game");

		con.currContext = ConContext.GAME;
		con.send(gson.toJson(new Request("key", info.key)));
	}
}
