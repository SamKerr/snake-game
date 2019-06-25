package snake;

import java.util.ArrayList;

/*
* This models the snake body as a linked list
* Although it is totally feasable to use a standard ArrayList
* I just think its very idiomatic of snake
* */
public class SnakeBody {

    private int _size;
    private Node _tail;
    private Node _head;
    private boolean _dead = false;

    public SnakeBody(Pair<Integer, Integer> head){
        _head = new Node(head);
        _tail = _head;
        _size = 1;
    }

    public void snakeGrow(Pair<Integer, Integer> newTail){
        Node newTailNode = new Node(newTail);
        _tail.setPrevious(newTailNode);
        _tail = newTailNode;
        _size++;
    }

    public void moveSnakeForward(Node currentN, Pair<Integer, Integer> location){

        for (Node n: get_allNodes()
             ) {
            if(n._coordinates == location) _dead = true;
        }

        if(currentN._next != null){
            currentN._coordinates = currentN._next._coordinates;
            moveSnakeForward(currentN._next, location);

            if(currentN._previous == null) _tail = currentN;
        }
        else {
            currentN._coordinates = location;
            _head = currentN;
        }
    }

    public Integer size(){
        return _size;
    }

    public Node get_head(){
        return _head;
    }

    public Node get_tail(){
        return _tail;
    }

    public Boolean isSelfCollision(){
        int counter = 0;
        for (Node n: get_allNodes()
             ) {
                if(n._coordinates == _head._coordinates) counter++;
        }
        if(counter > 1 || _dead) return true;
        else return false;
    }

    public Boolean collidesWithWall(int maxX , int maxY){
        int headY = _head.getCoordinates().y;
        int headX = _head.getCoordinates().x;
        return (headX == 0 || headX == maxX - 1 || headY == 0 || headY == maxY - 1);
    }

    public ArrayList<Integer> getXs(){
        ArrayList<Integer> xOrds = new ArrayList<>(_size);
        for (Pair<Integer, Integer> node: get_fullBody()
             ) {
            xOrds.add(node.x);
        }
        return xOrds;
    }

    public ArrayList<Integer> getYs(){
        ArrayList<Integer> yOrds = new ArrayList<>(_size);
        for (Pair<Integer, Integer> node: get_fullBody()
                ) {
            yOrds.add(node.y);
        }
        return yOrds;
    }

    public ArrayList<Pair<Integer, Integer>> get_fullBody(){
        ArrayList<Pair<Integer, Integer>> allCoordinates = new ArrayList<>(size());
        for (Node n: get_allNodes()
             ) {
            allCoordinates.add(n._coordinates);
        }
        return allCoordinates;
    }

    private ArrayList<Node> get_allNodes(){
        ArrayList<Node> fullBodyIterable = new ArrayList<>(size());
        return _tail.getAllNextNodes(fullBodyIterable);
    }
}
