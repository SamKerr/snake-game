package main.java;

import javax.swing.*;
import java.awt.*;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static javafx.scene.input.KeyCode.UP;


/*
* This class models a game of snake
* It prints the snake board to terminal
* As ASCII text
* */
public class Game {

    private Snake snake;
    private char[][] board;

    private final int maxY;
    private final int maxX;

    private Pair food;

    private Boolean isOver;

    public Game(int boardWidth, int boardHeight){
        maxX = boardWidth;
        maxY = boardHeight;
        snake = new Snake(boardWidth, boardHeight);
        board = new char[boardHeight][boardWidth];

        int randomI = ThreadLocalRandom.current().nextInt(1, boardHeight - 1);
        int randomJ = ThreadLocalRandom.current().nextInt(1, boardWidth - 1);
        food = new Pair(randomI, randomJ);
        refreshBoard();
    }

/*
    * Find all snake x,y locations
    * Remove these from the candidate possitions
    * Pick a random remaining x and y
    * */
    public void generateFood(){
        ArrayList<Integer> spaceYs = new ArrayList<>();
        for (int y = 1; y < maxY - 1; y++) {
            if(!snake.getBodyYs().contains(y)){
                spaceYs.add(y);
            }
        }

        ArrayList<Integer> spaceXs = new ArrayList<>();
        for (int x = 1; x < maxX - 1 ; x++) {
            if(!snake.getBodyXs().contains(x)){
                spaceXs.add(x);
            }
        }

        int randomY = ThreadLocalRandom.current().nextInt(0, spaceYs.size());
        int randomX = ThreadLocalRandom.current().nextInt(0, spaceXs.size());
        food = new Pair(randomY, randomX);
    }

    /*
    * If snakeHead is at food location then move snake forward
    * Add one to the tail of the snake
    * */
    public void moveAndMaybeEat(Pair food){

        if(snake.getHeadCoordinates().isEqual(food)) {
            Pair lastBody = snake.getBodyAsNodes().get_tail().clone();
            snake.moveDirection();
            snake.getBodyAsNodes().snakeGrow(lastBody);
            generateFood();

        }
        else {
            snake.moveDirection();
        }
    }

    /*
    *  This is triggered after every delay in the Main while loop
    *  Sets each char in the board
    * */
    public void refreshBoard(){
        moveAndMaybeEat(food);
        for (int i = 0; i < maxY; i++) {
            for (int j = 0; j < maxX; j++) {
                if(i==0 || i == maxY - 1 || j == 0 || j == maxX - 1){
                    board[i][j] = BoardChars.WALL.charecter();
                }
                else{
                    board[i][j] = BoardChars.SPACE.charecter();
                }
            }
        }

        for (Pair coordinates: snake.getBodyAsCoordinates()) {
            int y = coordinates.y;
            int x = coordinates.x;
            board[y][x] = BoardChars.SNAKE.charecter();
        }

        board[food.y][food.x] = BoardChars.FOOD.charecter();
    }

    public void drawBoard(){
        clearScreen();
        for (char[] row: board) {
            System.out.println(row);
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

    public Boolean isOver(){
        isOver = snake.checkIfSnakeCrashed();
        return isOver;
    }

    public void changeDirecton(KeyEvent keyEvent){
        KeyCode keyCode = keyEvent.getCode();
        switch (keyCode){
            case UP:
                snake.setDirection(Direction.UP);
                break;

            case RIGHT:
                snake.setDirection(Direction.RIGHT);
                break;

            case DOWN:
                snake.setDirection(Direction.DOWN);
                break;

            case LEFT:
                snake.setDirection(Direction.LEFT);
                break;

             default:
                break;
        }
    }
}
