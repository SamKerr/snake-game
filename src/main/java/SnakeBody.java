package main.java;

import java.util.ArrayList;

public class SnakeBody {

    private boolean isDead;
    private ArrayList<Pair> nodes;


    public SnakeBody(Pair head){
        nodes = new ArrayList<>(1);
        nodes.add(head);
    }

    public void snakeGrow(Pair newTail){
        nodes.add(newTail);
    }

    public Integer size(){
        return nodes.size();
    }

    public Pair get_head(){
        return nodes.get(0);
    }

    public Pair get_tail(){
        return nodes.get(size()-1);
    }

    public boolean collision(int maxX, int maxY){
        isDead |= isSelfCollision() || collidesWithWall(maxX, maxY);
        return isDead;
    }

    private Boolean isSelfCollision() {
        Pair head = nodes.get(0);
        boolean collides = false;
        for (int i = 1; i < size(); i++) {
           collides |= head.isEqual(nodes.get(i));
        }
        return collides;
    }

    private Boolean collidesWithWall(int maxX , int maxY){
        Pair head = nodes.get(0);
        return (head.x < 0 || head.x >= maxX || head.y < 0 || head.y >= maxY);
    }

    public ArrayList<Pair> getNodes(){
        return nodes;
    }

    public ArrayList<Integer> getXs(){
        ArrayList<Integer> xOrds = new ArrayList<>(nodes.size());
        for (Pair node: getNodes()) {
            xOrds.add(node.x);
        }
        return xOrds;
    }

    public ArrayList<Integer> getYs(){
        ArrayList<Integer> yOrds = new ArrayList<>(nodes.size());
        for (Pair node: getNodes()) {
            yOrds.add(node.y);
        }
        return yOrds;
    }
}
