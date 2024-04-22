package com.wooly.avalon.screens.other;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.screens.buttons.Button;

public abstract class Slider extends UIElement{
    Button downButton;
    Button upButton;
    Texture whiteSquare;
    int elementGap=5, numberOfIncrements, segmentWidth;
    /**Update this before drawing**/
    int currentState=0;
    public Slider(Coordinate position,int numberOfIncrements, Button downButton, Button upButton){
        super(position);
        this.numberOfIncrements=numberOfIncrements;
        whiteSquare=TDGame.fetchTexture("white_square");
        segmentWidth =whiteSquare.getWidth();

        //center position on the screen
        this.position=position.subtract(new Coordinate((elementGap+ segmentWidth)*(this.numberOfIncrements)*0.5f,0));

        this.downButton=downButton;
        this.downButton.setPosition(this.position);
        this.upButton=upButton;
        this.upButton.setPosition(this.position.add(
                new Coordinate((numberOfIncrements + 1) * segmentWidth + (numberOfIncrements + 1) * elementGap, 0))
        );
    }
    @Override
    public void draw(SpriteBatch batch) {
        downButton.drawCheckClick(batch);
        float x= downButton.position.x()+elementGap+ segmentWidth;
        float y= downButton.position.y();
        for (int i = 0; i < currentState; i++) {
            batch.draw(whiteSquare,x,y);
            x+=elementGap+ segmentWidth;
        }
        batch.setColor(1,1,1,0.3f);
        for (int i = currentState; i < numberOfIncrements; i++) {
            batch.draw(whiteSquare,x,y);
            x+=elementGap+ segmentWidth;
        }
        batch.setColor(Color.WHITE);
        upButton.drawCheckClick(batch);
    }
}
