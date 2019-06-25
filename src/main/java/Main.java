package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static final int DELAY = 120;
    private static final int BOARDWIDTH = 50;
    private static final int BOARDHEIGHT = 30;

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
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/MainScreen.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
