import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;


public class main extends Application {
    static GraphicsContext context = null;
    static GridPane grid = null;
    static Scene scene = null;

    static Button connect = null;

    static Label errorMessage = null;

    @Override
    public void start(Stage stage) throws Exception {

        Canvas canvas = new Canvas(50*20, 50*20);
        context = canvas.getGraphicsContext2D();
        StackPane stackPane = new StackPane();
        canvas.setId("canvas");
        stackPane.getChildren().add(canvas);

        Button signUp = new Button("Sign up");
        Button signIn = new Button("Sign in");

        connect = new Button("Connect");

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(signIn,0,2);
        grid.add(signUp,1,2);

        TextField usernameInput = new TextField();
        PasswordField passwordInput = new PasswordField();

        ComMod com = new ComMod();
        signUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String userName = usernameInput.getText();
                String password = passwordInput.getText();
                if (userName.length() == 0 || password.length() == 0) {
                    grid.getChildren().remove(errorMessage);
                    errorMessage = new Label("Please enter both username and password");
                    grid.add(errorMessage, 0, 3, 2, 1);
                }
                else {
                    boolean status = com.sendSignUp(usernameInput.getText(), passwordInput.getText());
                    if(status) {
                        grid.getChildren().clear();
                        grid.add(connect, 0, 0);
                    }
                    else {
                        errorMessage = new Label("Something went wrong");
                        grid.add(errorMessage, 0, 3, 2, 1);
                    }
                }
            }
        });

        signIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean status = com.sendSignIn(usernameInput.getText(), passwordInput.getText());
                if(status) {
                    grid.getChildren().clear();
                    grid.add(connect,0,0);
                }

            }
        });


        grid.add(usernameInput,0,0);
        grid.add(passwordInput,0,1);

        scene = new Scene(grid, 640, 640);

        connect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean status = com.sendConnect();
                if(status) {
                    scene.setRoot(stackPane);
                    (new Thread(new UpdateInterface(scene))).start();
                }
            }
        });

        scene.getStylesheets().add("stylesheet.css");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }


    public static void generateBoard(Data gameBoard) {
        context.strokeRect(0,0,50*20, 50*20);

        for (Snake snake: gameBoard.getSnakes()) {
            context.setFill(Color.BLACK);
            for (Point bodyPart:snake.getBody()){
                context.fillRect(bodyPart.getX()*20, bodyPart.getY()*20, 20, 20);
            }
        }

        for (Fruit fruit:gameBoard.getFruit()) {
            context.setFill(Color.GREEN);
            context.fillRect(fruit.getPos().getX()*20, fruit.getPos().getY()*20, 20, 20);
        }

        System.out.println("Done repainting");
    }

    public static void showEndGameScreen(Data data) {
        grid.getChildren().clear();
        Label GameOverText = new Label("GAME FINISHED");
        grid.add(GameOverText, 0, 0);
        int row = 1;
        for (Integer score: data.scorelList) {
            grid.add(new Label(score.toString()), 0, row);
            row++;
        }
        Button exitGame = new Button("Exit game");
        grid.add(exitGame, 0, row);
        exitGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                grid.getChildren().clear();
                grid.add(connect,0,0);
            }
        });

        scene.setRoot(grid);
    }



    public static void main(String[] args) {
        launch();
    }
}