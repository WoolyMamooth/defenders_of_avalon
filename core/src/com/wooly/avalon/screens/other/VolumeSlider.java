package com.wooly.avalon.screens.other;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.screens.buttons.Button;

public class VolumeSlider extends Slider{
    private static class VolumeDownButton extends Button{
        public VolumeDownButton(Coordinate position) {
            super(position, TDGame.fetchTexture("white_square"), TDGame.fetchTexture("red_square"));
        }
        @Override
        public void onClick() {
            TDGame.musicHandler.decreaseVolume();
        }
    }
    private static class VolumeUpButton extends Button{
        public VolumeUpButton(Coordinate position) {
            super(position, TDGame.fetchTexture("white_square"), TDGame.fetchTexture("red_square"));
        }
        @Override
        public void onClick() {
            TDGame.musicHandler.increaseVolume();
        }
    }
    public VolumeSlider(Coordinate position) {
        super(position,TDGame.musicHandler.getNumberOfIncrements(),new VolumeDownButton(position), new VolumeUpButton(position));
    }
    @Override
    public void draw(SpriteBatch batch) {
        currentState=TDGame.musicHandler.getVolume();
        super.draw(batch);
    }
}
