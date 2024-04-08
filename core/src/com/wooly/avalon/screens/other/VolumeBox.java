package com.wooly.avalon.screens.other;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.screens.buttons.Button;

public class VolumeBox extends UIElement{
    VolumeDownButton volumeDownButton;
    VolumeUpButton volumeUpButton;
    Texture whiteSquare;
    int elementGap=5,volumeIndicatorLength,volumeSegmentWidth;
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
    public VolumeBox(Coordinate position) {
        this.position=position;
        volumeIndicatorLength=TDGame.musicHandler.getNumberOfIncrements();

        whiteSquare=TDGame.fetchTexture("white_square");
        volumeSegmentWidth=whiteSquare.getWidth();

        volumeDownButton= new VolumeDownButton(this.position);
        volumeUpButton= new VolumeUpButton(this.position.add(
                new Coordinate((volumeIndicatorLength + 1) * volumeSegmentWidth + (volumeIndicatorLength + 1) * elementGap, 0)
        ));
    }
    @Override
    public void draw(SpriteBatch batch) {
        volumeDownButton.drawCheckClick(batch);
        float x=volumeDownButton.position.x()+elementGap+volumeSegmentWidth;
        float y=volumeDownButton.position.y();
        for (int i = 0; i < TDGame.musicHandler.getVolume(); i++) {
            batch.draw(whiteSquare,x,y);
            x+=elementGap+volumeSegmentWidth;
        }
        batch.setColor(1,1,1,0.3f);
        for (int i = TDGame.musicHandler.getVolume(); i < volumeIndicatorLength; i++) {
            batch.draw(whiteSquare,x,y);
            x+=elementGap+volumeSegmentWidth;
        }
        batch.setColor(Color.WHITE);
        volumeUpButton.drawCheckClick(batch);
    }
}
