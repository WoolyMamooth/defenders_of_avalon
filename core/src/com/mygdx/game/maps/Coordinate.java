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
    /**
     * Returns a new Coordinate with the x and y of other subtracted from the x and y of this.
     * @param other
     * @return
     */
    public Coordinate subtract(Coordinate other){
        return new Coordinate(this.coord_x-other.x(),this.coord_y-other.y());
    }
    /**
     * Returns a new Coordinate with the x and y of the two originals added together.
     * @param other
     * @return
     */
    public Coordinate add(Coordinate other){
        return new Coordinate(this.coord_x+other.x(),this.coord_y+other.y());
    }

    /**
     * Returns a new Coordinate with x and y multiplied by the given scalar value.
     * @param scalar
     * @return
     */
    public Coordinate multiplyByScalar(float scalar){
        return new Coordinate(this.coord_x*scalar,this.coord_y*scalar);
    }

    /**
     * Here we treat the coordinate as a normalized vector, used for precise movement in Enemy
     */
    public Coordinate normalize() {
        float length = (float) Math.sqrt(coord_x * coord_x + coord_y * coord_y);
        if (length != 0) {
            return new Coordinate(coord_x / length, coord_y / length);
        } else {
            // Handle division by zero or very small values
            return new Coordinate(0, 0);
        }
    }

    public float distanceFrom(Coordinate other) {
        if(other==null) return 0f;
        float deltaX = other.x() - this.x();
        float deltaY = other.y() - this.y();
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    @Override
    public String toString() {
        return "(" + coord_x + ", " + coord_y + ")";
    }
}
