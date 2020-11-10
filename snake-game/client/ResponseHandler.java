package client;

import com.google.gson.Gson;
import data.GameServerInfo;
import data.Response;

public class ResponseHandler {
	private ClientCon con;
	private Gson gson;

	public ResponseHandler(ClientCon con) {
		this.gson = new Gson();
		this.con = con;
	}

	public void handleResponse(Response response) {
		switch (response.type) {
			case "gameState":
				this.GameState(response);
				break;
			case "joinGameServer":
				this.joinGameServer(response);
				break;
			default:
				System.err.println("Invalid response received.");
		}
	}

	private void GameState(Response response) {

	}

	private void joinGameServer(Response response) {
		GameServerInfo info = gson.fromJson(response.jsonData, GameServerInfo.class);
		System.out.println("Before connec to game");
		con.connectToGame(info.address, info.port);
		System.out.println("After connect to game");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		con.currContext = ConContext.GAME;
	}
}
