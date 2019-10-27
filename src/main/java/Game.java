package main.java;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    private Snake snake;
    private Pair food;
    private Random randomGenerator;

    private final int boardHeight;
    private final int boardWidth;

    public Game(int boardWidth, int boardHeight){
        snake = new Snake(boardWidth, boardHeight);
        randomGenerator = new Random(0);
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        food = getRandomPair(boardHeight-1, boardWidth-1);

    }

    /**
     * Generates food that cannot be where the snake currently is
     */
    private void generateFood(){
        ArrayList<Integer> availableYs = new ArrayList<>();
        for (int y = 0; y <  boardHeight- 1; y++) {
            if(!snake.getBodyYs().contains(y)){
                availableYs.add(y);
            }
        }

        ArrayList<Integer> availableXs = new ArrayList<>();
        for (int x = 0; x < boardWidth- 1 ; x++) {
            if(!snake.getBodyXs().contains(x)){
                availableXs.add(x);
            }
        }
        Pair foodIndex = getRandomPair(availableYs.size(), availableXs.size());
        food = new Pair(availableYs.get(foodIndex.y), availableXs.get(foodIndex.x));
    }


    private Pair getRandomPair(int yBound, int xBound){
        return new Pair(randomGenerator.nextInt(yBound), randomGenerator.nextInt(xBound));
    }

    /**
     * If snake is eating
     *      Move snake normally
     *      Add the last element of snake before moving to new snakes body
     * else move normally
     */
    public void move(){
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

    /**
     * Called upon keyboard input from player
     * Decides direction to set snakes movements to
     * @param keyEvent
     */
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

    public ArrayList<Pair> getSnakeCoords(){
        return snake.getBodyAsCoordinates();
    }

    public Pair getFood() {
        return food;
    }

    public Boolean isOver() {
        return snake.checkIfSnakeCrashed();
    }

    public int getScore(){
        return snake.getBodyAsCoordinates().size();
    }
}
