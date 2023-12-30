package com.mygdx.game.screens.buttons;

import static com.mygdx.game.TDGame.fetchFont;
import static com.mygdx.game.TDGame.fetchTexture;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.maps.Coordinate;

public abstract class CustomButton extends Clickable{
    protected String text;
    Texture background;
    BitmapFont font;
    Color backgroundColor;
    Color textColor;
    float textOffsetX,textOffsetY;

    /**
     * Button with custom text. Uses TDGAme.fetchFont() to generate font.
     * @param position
     * @param text
     * @param fontsize
     * @param textColor
     */
    public CustomButton(Coordinate position,String text,int fontsize, Color textColor, Color backgroundColor,float width,float height) {
        super(position);
        this.text=text;
        font=fetchFont(fontsize);

        this.height=height;
        this.width=width;

        textOffsetX=10;
        textOffsetY=height*0.7f;

        this.backgroundColor=backgroundColor;
        this.textColor=textColor;

        background=fetchTexture("white_square");
    }
    @Override
    public void draw(SpriteBatch batch) {
        if(!isActive()) {
            batch.setColor(backgroundColor);
            batch.draw(background, position.x(), position.y(),width,height);
            font.setColor(textColor);
            font.draw(batch, text, position.x()+textOffsetX, position.y()+textOffsetY);
        }else{
            batch.setColor(textColor);
            batch.draw(background, position.x(), position.y(),width,height);
            font.setColor(backgroundColor);
            font.draw(batch, text, position.x()+textOffsetX, position.y()+textOffsetY);
        }
        batch.setColor(Color.WHITE);
    }
    @Override
    public void dispose() {

    }
}
