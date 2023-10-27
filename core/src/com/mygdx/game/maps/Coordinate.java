package com.mygdx.game.maps;

public class Coordinate {

    private float coord_x;
    private float coord_y;

    public Coordinate(float x, float y) {
        this.coord_x = x;
        this.coord_y = y;
    }

    public float x() {
        return this.coord_x;
    }

    public float y() {
        return this.coord_y;
    }

    public Coordinate subtract(Coordinate other){
        return new Coordinate(this.coord_x-other.x(),this.coord_y-other.y());
    }

    public Coordinate add(Coordinate other){
        return new Coordinate(this.coord_x+other.x(),this.coord_y+other.y());
    }

    public Coordinate multiplyByScalar(float scalar){
        return new Coordinate(this.coord_x*scalar,this.coord_y*scalar);
    }

    @Override
    public String toString() {
        return "(" + coord_x + ", " + coord_y + ")";
    }
}
