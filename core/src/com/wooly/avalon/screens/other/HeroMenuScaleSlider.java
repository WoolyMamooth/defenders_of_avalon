package com.wooly.avalon.screens.other;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.screens.buttons.Button;
import com.wooly.avalon.units.heroes.Hero;

public class HeroMenuScaleSlider extends Slider{
    private static class ScaleDownButton extends Button{
        public ScaleDownButton(Coordinate position) {
            super(position, TDGame.fetchTexture("white_square"), TDGame.fetchTexture("red_square"));
        }
        @Override
        public void onClick() {
            Hero.menuScale-=0.1f;
            if (Hero.menuScale<1) Hero.menuScale=1f;
        }
    }
    private static class ScaleUpButton extends Button{
        public ScaleUpButton(Coordinate position) {
            super(position, TDGame.fetchTexture("white_square"), TDGame.fetchTexture("red_square"));
        }
        @Override
        public void onClick() {
            Hero.menuScale+=0.1f;
            if (Hero.menuScale>2) Hero.menuScale=2f;
        }
    }
    public HeroMenuScaleSlider(Coordinate position) {
        super(position, 10, new ScaleDownButton(position), new ScaleUpButton(position));
    }

    @Override
    public void draw(SpriteBatch batch) {
        currentState= (int)((Hero.menuScale-1)*10);
        super.draw(batch);
    }
}
