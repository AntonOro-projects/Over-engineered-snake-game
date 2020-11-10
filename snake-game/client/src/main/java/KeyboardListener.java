import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardListener implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent event) {
        switch(event.getCode()) {
            case UP:
                ComMod.sendDirection("UP");
                break;
            case LEFT:
                ComMod.sendDirection("LEFT");
                break;
            case RIGHT:
                ComMod.sendDirection("RIGHT");
                break;
            case DOWN:
                ComMod.sendDirection("DOWN");
                break;
            default:
                // DO nothing
                break;
        }

    }
}
