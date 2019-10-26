package main.java;

public class Pair implements Cloneable {
    public int y;
    public int x;
    public Pair(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public boolean isEqual(Pair other){
        return this.x == other.x && this.y == other.y;
    }

    public Pair clone(){
        return new Pair(y,x);
    }
}