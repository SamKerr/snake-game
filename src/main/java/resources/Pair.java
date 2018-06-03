package main.java.resources;

/*
* This allows me to plot coordinates
* It is in the form (y,x) as this is more appropriate for 2d arrays
* */
public class Pair<Y, X> {
    public final Y y;
    public final X x;
    public Pair(Y y, X x) {
        this.y = y;
        this.x = x;
    }

    public boolean isEqual(Pair<Y, X> other){
        return this.x == other.x && this.y == other.y;
    }
}