package com.wooly.avalon.units.heroes;

import static com.wooly.avalon.TDGame.fetchTexture;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;

public class Merlin extends RangedHero{
    public Merlin(Coordinate position) {
        super(fetchTexture("heroes/merlin/merlin"),
                position,"Merlin","description here",
                75f, 70, 0, 10, 8, 1.4f, 200,
                "merlin_magic",220, "magic");
        setAbilities(new HeroAbility[]{
                new DummyAbility(),
                new DummyAbility(),
                new DummyAbility()
        });
    }
    private class DummyAbility extends HeroAbility{
        public DummyAbility(){
            super("Dummy",fetchTexture("enemies/red_square"),0);
            setDescription("This is a dummy ability");
            //TODO make his abilities
        }
    }
}