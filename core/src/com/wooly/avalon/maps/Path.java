package com.wooly.avalon.maps;

import java.util.Arrays;

public class Path {
    // These define the turningpoints in the path that enemies take.
    Coordinate[] coordinates;

    public Path(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinate getCoordinate(int position) {
        return coordinates[position];
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public int length() {
        return coordinates.length;
    }

    @Override
    public String toString() {
        return "Path{" +
                "coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
