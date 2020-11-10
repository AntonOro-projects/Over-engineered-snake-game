import javafx.scene.Scene;
import java.util.concurrent.TimeUnit;

public class UpdateInterface implements Runnable {

    public UpdateInterface(Scene scene) {
        scene.setOnKeyReleased(new KeyboardListener() );
    }

    @Override
    public void run() {
        ComMod comMod = new ComMod();
        boolean running = true;
        int i = 0;
        while (running) {
            System.out.println("Looping...");
            Data board = comMod.update();

            i++;
            if (i == 5) {
                board.gameOver = true;
            }

            System.out.println("Game over: " + board.isGameOver());
            if (board.isGameOver()) {
                // End game
                running = false;
                main.showEndGameScreen(board);
            }
            main.generateBoard(board);

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }






}
