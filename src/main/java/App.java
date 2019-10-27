package main.java;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.Scanner;

public class App extends Application {

    private static final int BOARDWIDTH = 15;
    private static final int BOARDHEIGHT = 15;
    private static final int RECTSIZE = 18;
    private Game game;
    private Rectangle[][] rectBoard;

    private Stage primaryStage;
    private Timeline clock;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.setTitle("Java Snake!");

        GridPane grid = new GridPane();

        Scene scene = new Scene(grid, BOARDHEIGHT*(RECTSIZE+1), BOARDWIDTH*(RECTSIZE+1));
        scene.getStylesheets().add(getClass().getResource("/main/resources/styles.css").toExternalForm());
        primaryStage.setScene(scene);

        //stores all rectangles conveniently as there is no API to access GridPane by index
        rectBoard = new Rectangle[BOARDHEIGHT][BOARDWIDTH];

        //set up grid and style all to background
        for (int y = 0; y < BOARDHEIGHT; y++) {
            for (int x = 0; x < BOARDWIDTH; x++) {
                Rectangle rect = new Rectangle(RECTSIZE, RECTSIZE);
                rect.getStyleClass().add("background-rect");
                grid.add(rect, x, y, 1, 1);
                rectBoard[y][x] = rect;
            }
        }

        game = new Game(BOARDWIDTH, BOARDHEIGHT);
        play(scene);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            game.changeDirecton(key);
        });
        primaryStage.show();
    }


    /**
     * This starts the clock
     * @param scene
     */
    private void play(Scene scene) {
        clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            refreshBoard(scene);
        }), new KeyFrame(Duration.seconds(0.2)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }



    /**
     *  This triggers the game.move method, updating the data model
     *  It then re-styles the scene
     * */
    public void refreshBoard(Scene scene){
        game.move();



        if (game.isOver()) {
            gameOver();
            return;
        }

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

    private void gameOver(){
        clock.stop();

        String scoresPath = "scores.txt";
        final Stage dialog = new Stage();
        dialog.setTitle("Game Over!");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);

        GridPane grid = new GridPane();
        grid.add(new Text("Well Played!"), 1, 0, 3,1);
        grid.add(new Text("Score: " + game.getScore()), 0 , 1,1,1);

        String maxScorersName = null;
        int maxScore = 0;
        try{
            File file = new File(scoresPath);
            Scanner scanner = new Scanner(file);
            String st;
            while ((scanner.hasNextLine())) {
                st = scanner.nextLine();
                String[] nameScore = st.split(",");
                int score = Integer.parseInt(nameScore[1]);
                String name = nameScore[0];
                if(score>maxScore){
                    maxScore = score;
                    maxScorersName = name;
                }
            }
        } catch (Exception e ){
            e.printStackTrace();
        }

        if(game.getScore() >= maxScore || maxScorersName == null){
            grid.add(new Text("Your Current High Score: " + game.getScore()), 0 , 2,3,1);
        } else {
            grid.add(new Text("Current High Score: " + maxScore + " By " + maxScorersName), 0 , 2,3,1);
        }
        grid.add(new Text("Name: "), 0, 3, 1,1);
        TextField nameField = new TextField();
        grid.add(nameField, 2, 3, 2,1);
        Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    File file = new File(scoresPath);
                    BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
                    bw.append(nameField.getCharacters().toString()).append(",").append(String.valueOf(game.getScore())).append("\n");
                    bw.close();
                    System.exit(0);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        grid.add(saveButton, 3, 3, 1,1);
        Scene dialogScene = new Scene(grid, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();

    }
}
