package main.java.com.javasnake;

import main.java.resources.BoardChars;
import main.java.resources.Direction;
import main.java.resources.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


/*
* This class models a game of snake
* It prints the snake board to terminal
* As ASCII text
* */
public class Game implements KeyListener {

    private Snake _snake;
    private char[][] _board;

    private final int _maxY;
    private final int _maxX;

    private Pair<Integer, Integer> _food;

    private Boolean _isOver;

    //KeyListener things
    {
        JTextField keyText = new JTextField(1);
        keyText.addKeyListener(this);
        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        JTextField textField = new JTextField();
        textField.addKeyListener(this);
        contentPane.add(textField, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
    }

    public Game(int boardWidth, int boardHeight){
        _maxX  = boardWidth;
        _maxY  = boardHeight;
        _snake = new Snake(boardWidth, boardHeight);
        _board = new char[boardHeight][boardWidth];

        int randomI = ThreadLocalRandom.current().nextInt(1, boardHeight - 1);
        int randomJ = ThreadLocalRandom.current().nextInt(1, boardWidth - 1);
        _food = new Pair<>(randomI, randomJ);
        refreshBoard();
    }

/*
    * Find all snake x,y locations
    * Remove these from the candidate possitions
    * Pick a random remaining x and y
    * */
    public void generateFood(){
        ArrayList<Integer> spaceYs = new ArrayList<>();
        for (int y = 1; y <_maxY - 1; y++) {
            if(!_snake.getBodyYs().contains(y)){
                spaceYs.add(y);
            }
        }

        ArrayList<Integer> spaceXs = new ArrayList<>();
        for (int x = 1; x <_maxX - 1 ; x++) {
            if(!_snake.getBodyXs().contains(x)){
                spaceXs.add(x);
            }
        }

        int randomY = ThreadLocalRandom.current().nextInt(0, spaceYs.size());
        int randomX = ThreadLocalRandom.current().nextInt(0, spaceXs.size());
        _food = new Pair<>(randomY, randomX);
    }

    /*
    * If snakeHead is at food location then move snake forward
    * Add one to the tail of the snake
    * */
    public void moveAndMaybeEat(Pair<Integer, Integer> food){

        if(_snake.getHeadCoordinates().isEqual(food)) {

            Pair<Integer, Integer> lastBody = _snake.getBodyAsNodes().get_tail()._coordinates;
            _snake.moveDirection();
            _snake.getBodyAsNodes().snakeGrow(lastBody);
            generateFood();

        }
        else {
            _snake.moveDirection();
        }
    }

    /*
    *  This is triggered after every delay in the Main while loop
    *  Sets each char in the board
    * */
    public void refreshBoard(){

        moveAndMaybeEat(_food);

        for (int i = 0; i < _maxY; i++) {
            for (int j = 0; j < _maxX ; j++) {

                if(i==0 || i == _maxY - 1 || j == 0 || j == _maxX - 1){
                    _board[i][j] = BoardChars.WALL.charecter();
                }
                else{
                    _board[i][j] = BoardChars.SPACE.charecter();
                }
            }
        }

        for (Pair<Integer, Integer> coordinates: _snake.getBodyAsCoordinates()) {
            int y = coordinates.y;
            int x = coordinates.x;
            _board[y][x] = BoardChars.SNAKE.charecter();
        }

        _board[_food.y][_food.x] = BoardChars.FOOD.charecter();
    }

    public void drawBoard(){
        clearScreen();
        for (char[] row: _board) {
            System.out.println(row);
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

    public Boolean isOver(){
        _isOver = _snake.checkIfSnakeCrashed();
        return _isOver;
    }

    public void changeDirecton(KeyEvent keyEvent){
        int keyCode = keyEvent.getKeyCode();
        switch (keyCode){
            case KeyEvent.VK_UP:
                _snake.set_direction(Direction.UP);
                break;

            case KeyEvent.VK_RIGHT:
                _snake.set_direction(Direction.RIGHT);
                break;

            case KeyEvent.VK_DOWN:
                _snake.set_direction(Direction.DOWN);
                break;

            case KeyEvent.VK_LEFT:
                _snake.set_direction(Direction.LEFT);
                break;

             default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) { changeDirecton(keyEvent); }

    @Override
    public void keyPressed(KeyEvent keyEvent) { changeDirecton(keyEvent); }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        //Do nothing when key released
    }

}
