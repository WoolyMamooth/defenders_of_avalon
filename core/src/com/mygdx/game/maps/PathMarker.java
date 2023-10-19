package com.mygdx.game.maps;

import com.badlogic.gdx.graphics.Texture;

public class PathMarker {
    // Path markers define the turningpoints in the path that enemies take.
    Coordinate coordinate;

    public PathMarker(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
