import Communication.ClientCon;
import Communication.ConContext;
import data.Response;
import javafx.scene.Scene;
import java.util.concurrent.TimeUnit;
import com.google.gson.Gson;

public class UpdateInterface implements Runnable {

	private ClientCon clientCon;
    public UpdateInterface(Scene scene, ClientCon com) {
        scene.setOnKeyReleased(new KeyboardListener(com) );
        this.clientCon = com;
    }

    @Override
    public void run() {
    	System.out.println("Run start");
        boolean running = true;
        int i = 0;
        Gson gson = new Gson();
        while (running) {
            //System.out.println("Looping...");
            
            Response res = clientCon.receive();
            
            if (!res.type.equals("gameState")) {
            	continue;
            }
            //System.out.println("In GameState-branch");
            Board board = gson.fromJson(res.jsonData, Board.class);
            System.out.println(res.jsonData);
            
            //board = null; // delete this line and uncomment the 2 above to test things.
          
            // System.out.println("Game over: " + board.isGameOver());
            if (board.gameOver) {
                // End game
                running = false;
                clientCon.currContext = ConContext.MAIN;
                main.showEndGameScreen(board);
            }
            System.out.println(res.jsonData);
            main.generateBoard(board);

            
        }
    }






}
