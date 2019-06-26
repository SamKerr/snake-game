package main.java;


import java.util.ArrayList;

public class Snake {
    private SnakeBody body;
    private Direction direction;

    private final int boardHeight;
    private final int boardWidth;

    public Snake(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        int middleX = boardWidth / 2;
        int middleY = boardHeight / 2;
        body = new SnakeBody(new Pair(middleY, middleX));
        direction = Direction.RIGHT;
    }

    /**
     * Called by Game.move
     * This updates the snakes model of Pairs - the snakes locations
     * Works from 0,0 is top-left
     */
    public void moveDirection(){
        switch (direction){
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

    /**
     * Head moves based on incriement and directions
     *      Incriment decided by moveDirection
     *      direction decided by keyboard input in Game.changeDirection
     * All nodes just move to location of the previous in a chain-like pattern
     *
     * @param yIncriment change in y for head
     * @param xIncriment change in x for head
     */
    private void incrimentBody(int yIncriment, int xIncriment){
        ArrayList<Pair> nodes = body.getNodes();
        for (int i = body.size()-1; i > 0; i--) {
            nodes.set(i, nodes.get(i-1).clone());
        }
        Pair head = body.get_head();
        head.y += yIncriment;
        head.x += xIncriment;
    }

    /**
     * Checks all cases for crash
     *      1) collides with wall
     *      2) head collides with another part of snake
     * @return
     *      True => There was a collision => Game over
     *      False => There was NOT a collision
     */
    public Boolean checkIfSnakeCrashed(){
        return body.collision(boardWidth, boardHeight);
    }

    public SnakeBody getBodyAsNodes(){
        return body;
    }

    public void setDirection(Direction direction){this.direction = direction;}

    public ArrayList<Pair> getBodyAsCoordinates(){return body.getNodes();}

    public  Pair getHeadCoordinates() {return body.get_head(); }

    public ArrayList<Integer> getBodyYs(){return body.getYs();}

    public ArrayList<Integer> getBodyXs() { return body.getXs();}


}
