package main.java.com.javasnake;


import main.java.resources.Direction;
import main.java.resources.Pair;

import java.util.ArrayList;

public class Snake {
    //each pair is <y,x> as thats how indexing works
    private SnakeBody _body;
    private Direction _direction;

    private final int _boardHeight;
    private final int _boardWidth;

    public Snake(int boardWidth, int boardHeight){
        _boardWidth = boardWidth;
        _boardHeight = boardHeight;
        int middleX = boardWidth / 2;
        int middleY = boardHeight / 2;
        _body = new SnakeBody(new Pair<>(middleY, middleX));
        _direction = Direction.RIGHT;
    }

    public void moveDirection(){
        //remember that 0,0 pair is top left corner
        switch (_direction){
            case UP:
                incrimentBody(-1,0);
                break;
            case DOWN:
                incrimentBody(1, 0);
                break;
            case LEFT:
                incrimentBody(0, -1);
                break;
            case RIGHT:
                incrimentBody(0, 1);
                break;
            default: break;
        }

    }

    private void incrimentBody(int yIncriment, int xIncriment){

        Pair<Integer, Integer> head = _body.get_head()._coordinates;
        int yOrd = head.y + yIncriment;
        int xOrd = head.x + xIncriment;
        _body.moveSnakeForward(_body.get_tail(), new Pair<>(yOrd, xOrd));
    }


    public Boolean checkIfSnakeCrashed(){
        if(_body.isSelfCollision() || _body.collidesWithWall(_boardWidth, _boardHeight)){
            return true;
        }
        else return false;
    }

    public SnakeBody getBodyAsNodes(){
        return _body;
    }

    public ArrayList<Pair<Integer, Integer>> getBodyAsCoordinates(){
        return _body.get_fullBody();
    }

    public  Pair<Integer, Integer> getHeadCoordinates() {return _body.get_head()._coordinates; }

    public void set_direction(Direction direction){
        _direction = direction;
    }

    public ArrayList<Integer> getBodyYs(){
        return _body.getYs();
    }

    public ArrayList<Integer> getBodyXs() { return _body.getXs(); }


}
