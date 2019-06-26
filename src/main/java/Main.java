package main.java;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.plaf.ColorUIResource;
import java.io.IOException;

public class Main extends Application {

    private static final int BOARDWIDTH = 50;
    private static final int BOARDHEIGHT = 50;
    private static final int RECTSIZE = 10;
    private Game game;
    private Rectangle[][] rectBoard;
    private Game ga;
    private Timeline clock;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        //set up grid
        primaryStage.setTitle("Java Snake!");
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/MainScreen.fxml"));
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, BOARDHEIGHT*(RECTSIZE+2), BOARDWIDTH*(RECTSIZE+2));
        scene.getStylesheets().add(getClass().getResource("/main/resources/styles.css").toExternalForm());
        primaryStage.setScene(scene);

        rectBoard = new Rectangle[BOARDHEIGHT][BOARDWIDTH];

        for (int y = 0; y < BOARDHEIGHT; y++) {
            for (int x = 0; x < BOARDWIDTH; x++) {
                Rectangle rect = new Rectangle(RECTSIZE, RECTSIZE);
                rect.getStyleClass().add("background-rect");
                grid.add(rect, x, y, 1, 1);
                rectBoard[y][x] = rect;
            }
        }

        //play game
        game = new Game(BOARDWIDTH, BOARDHEIGHT);
        play(scene);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            game.changeDirecton(key);
        });
        primaryStage.show();
    }


    private void play(Scene scene) {
        clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            refreshBoard(scene);
        }), new KeyFrame(Duration.seconds(0.2)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }



    /*
     *  This triggers the game.move method, updating the data model
     *  It then re-styles the scene
     * */
    public void refreshBoard(Scene scene){
        if (game.isOver()) clock.stop();

        game.move();

        GridPane grid = (GridPane) scene.getRoot();
        ObservableList<Node> children = grid.getChildren();
        children.stream().forEach(rect -> {
            rect.getStyleClass().removeAll("background-rect", "snake-rect", "food-rect");
            rect.getStyleClass().add("background-rect");
        });

        game.getSnakeCoords().stream().forEach(pair -> {
            rectBoard[pair.y][pair.x].getStyleClass().removeAll("background-rect", "snake-rect", "food-rect");
            rectBoard[pair.y][pair.x].getStyleClass().add("snake-rect");
        });

        Pair food = game.getFood();
        rectBoard[food.y][food.x].getStyleClass().removeAll("background-rect", "snake-rect", "food-rect");
        rectBoard[food.y][food.x].getStyleClass().add("food-rect");
    }
}
