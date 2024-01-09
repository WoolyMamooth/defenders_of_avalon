package com.mygdx.game.units.heroes;

import static com.mygdx.game.TDGame.fetchTexture;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.maps.Coordinate;

public class ArthurPendragon extends Hero{
    /**
     * @param position
     */
    public ArthurPendragon(Coordinate position) {
        super(fetchTexture("heroes/arthur"), position, 100, 100, 10, 10, 10, 1, 200, "physical");
    }
}
