package main.java;


import java.util.ArrayList;

public class Snake {
    //each pair is <y,x> as thats how indexing works
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

    private void incrimentBody(int yIncriment, int xIncriment){
        ArrayList<Pair> nodes = body.getNodes();
        for (int i = body.size()-1; i > 0; i--) {
            nodes.set(i, nodes.get(i-1).clone());
        }
        Pair head = body.get_head();
        head.x += xIncriment;
        head.y += yIncriment;
    }

    public Boolean checkIfSnakeCrashed(){
        return body.collision(boardWidth, boardHeight);
    }

    public SnakeBody getBodyAsNodes(){
        return body;
    }

    public void setDirection(Direction direction){this.direction = direction;}

    public ArrayList<Pair> getBodyAsCoordinates(){return body.getNodes();}

    public  Pair getHeadCoordinates() {return body.get_head(); }

    public ArrayList<Integer> getBodyYs(){
        return body.getYs();
    }

    public ArrayList<Integer> getBodyXs() { return body.getXs(); }


}
