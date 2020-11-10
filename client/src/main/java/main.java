import com.google.gson.Gson;

import Communication.Client;
import Communication.ClientCon;
import Communication.ConContext;
import data.AuthenticationData;
import data.Request;
import data.RequestSuccess;
import data.Response;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;


public class main extends Application {
    static GraphicsContext context = null;
    static GridPane grid = null;
    static Scene scene = null;
    private ClientCon com;
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
        
        com = new ClientCon("127.0.0.1", 5000);
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
                	Gson gson = new Gson();
//                    boolean status = true; // sendRequest does not support boolean
//                    com.sendRequest("register", new SignUp(userName, password));
                	AuthenticationData a = new AuthenticationData(userName, password);
                    Request testRequest = new Request("register", gson.toJson(a));
                    com.send(gson.toJson(testRequest));
                    Response res = com.receive();
                    RequestSuccess s = gson.fromJson(res.jsonData, RequestSuccess.class);
                    
                    if(s.value) {
//                        grid.getChildren().clear();
//                        grid.add(connect, 0, 0);
                    	System.out.println("Seger åt kung och fosterland");
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
//                boolean status = true; // send request does not support boolean
//                com.sendRequest("login", new SignIn(usernameInput.getText(), passwordInput.getText()));
            	String userName = usernameInput.getText();
                String password = passwordInput.getText();
                Gson gson = new Gson();
                AuthenticationData a = new AuthenticationData(userName, password);
                Request testRequest = new Request("login", gson.toJson(a));
                com.send(gson.toJson(testRequest));
                Response res = com.receive();
                RequestSuccess s = gson.fromJson(res.jsonData, RequestSuccess.class);
                if(s.value) {
                    grid.getChildren().clear();
                    grid.add(connect,0,0);
                } else {
                	System.out.println("PANIK");
                }

            }
        });


        grid.add(usernameInput,0,0);
        grid.add(passwordInput,0,1);

        scene = new Scene(grid, 640, 640);

        connect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	System.out.println("Tryckte på Connect-knapp");
//                boolean status = true;
//                com.sendRequest("startGame", new String("Vet inte riktigt vad som ska finnas här"));
            	Gson gson = new Gson();
                Request testRequest = new Request("startGame", "");
                com.send(gson.toJson(testRequest));
                try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                Response response = com.receive();
                
                
                if (com.currContext == ConContext.GAME) {
                	System.out.println("Skickar request till GameServer");
                    Request r1 = new Request("startGame", "");
                    com.send(gson.toJson(r1));
                }
                
//                response = com.receive();
//                RequestSuccess s = gson.fromJson(response.jsonData, RequestSuccess.class);
                
                if(true) {
                    scene.setRoot(stackPane);
                    (new Thread(new UpdateInterface(scene, com))).start();
                }
            }
        });

        //scene.getStylesheets().add("stylesheet.css");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }


    public static void generateBoard(Board gameBoard) {
    	context.clearRect(0, 0, 1000, 1000);
        context.strokeRect(0,0,50*20, 50*20);
        
        for (Snake snake: gameBoard.getSnakes()) {
            if(snake.isAlive()){
                context.setFill(Color.BLACK);
                //System.out.println(snake.getHead());
                for (Point2D bodyPart:snake.getBody()){
                    context.fillRect(bodyPart.getX()*20, bodyPart.getY()*20, 20, 20);
                    System.out.print("  " + bodyPart.getX());
                }
                System.out.println();
            }
        }

        for (Fruit fruit:gameBoard.getFruit()) {
            context.setFill(Color.GREEN);
            context.fillRect(fruit.getPos().getX()*20, fruit.getPos().getY()*20, 20, 20);
        }

        System.out.println("Done repainting");
    }

    public static void showEndGameScreen(Board data) {
        grid.getChildren().clear();
        Label GameOverText = new Label("GAME FINISHED");
        grid.add(GameOverText, 0, 0);
        int row = 1;
//        Map<Integer,String> m = new TreeMap<Integer,String>(Collections.reverseOrder());
        for(Snake s: data.getSnakes()){
        	Snake max = s;
        	for (Snake z : data.getSnakes()) {
        		if (z.getScore() > max.getScore()) {
        			max = z;
        		}
        	}
        	grid.add(new Label(max.getID()), 0, row);
            grid.add(new Label(Integer.toString(max.getScore())), 1, row);
            row++;
            max.score = -1;
        }

//        for (Map.Entry<Integer,String> score: m.entrySet()) {
//            grid.add(new Label(score.getValue().toString()), 0, row);
//            grid.add(new Label(score.getKey().toString()), 1, row);
//            row++;
//        }
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