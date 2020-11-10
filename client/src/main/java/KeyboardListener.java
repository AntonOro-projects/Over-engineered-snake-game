import com.google.gson.Gson;

import Communication.ClientCon;
import data.Request;
import data.SnakeAction;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardListener implements EventHandler<KeyEvent> {

	private ClientCon com;
	
	public KeyboardListener(ClientCon com) {
		this.com = com;
	}
	
    @Override
    public void handle(KeyEvent event) {
        Gson gson = new Gson();
        Request testRequest;
        System.err.println(event.getCode().toString());
        switch(event.getCode()) {
            case UP:
                testRequest = new Request("gameAction", gson.toJson(new SnakeAction("down")));
                break;
            case LEFT:
            	testRequest = new Request("gameAction", gson.toJson(new SnakeAction("left")));
                break;
            case RIGHT:
            	testRequest = new Request("gameAction", gson.toJson(new SnakeAction("right")));
                break;
            case DOWN:
                testRequest = new Request("gameAction", gson.toJson(new SnakeAction("up")));
                break;
            default:
                testRequest = new Request("","");
                break;
        }
        com.send(gson.toJson(testRequest));

    }
}
