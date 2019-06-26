package main.java;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {

    private static final int DELAY = 120;
    private static final int BOARDWIDTH = 50;
    private static final int BOARDHEIGHT = 50;
    private static final int RECTSIZE = 10;
    private Game game;


    public static void main(String[] args) {

        launch(args);
//        System.out.println("Welcome to ASCII snake!\nHave fun!\n");
//        Game g = new Game(BOARDWIDTH, BOARDHEIGHT);
//        while (!g.isOver()){
//            g.refreshBoard();
//            g.drawBoard();
//
//            try {
//                Thread.sleep(DELAY);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("Game over!");
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        //set up grid
        primaryStage.setTitle("Java Snake!");
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/MainScreen.fxml"));
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, BOARDHEIGHT*RECTSIZE, BOARDWIDTH*RECTSIZE);
        scene.getStylesheets().add(getClass().getResource("/main/resources/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        for (int y = 0; y < BOARDHEIGHT; y++) {
            for (int x = 0; x < BOARDWIDTH; x++) {
                Rectangle rect = new Rectangle(RECTSIZE, RECTSIZE);
                rect.getStyleClass().add("background-rect");
                grid.add(rect, y, x, 1, 1);
            }
        }

        //play game
        game = new Game(BOARDWIDTH, BOARDHEIGHT);
        play();


        //
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            game.changeDirecton(key);
        });
        primaryStage.show();
    }


    private void play() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            System.out.println(e.toString());
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
