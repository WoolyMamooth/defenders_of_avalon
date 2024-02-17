package com.wooly.avalon.screens.buttons;

import static com.wooly.avalon.TDGame.fetchFont;
import static com.wooly.avalon.TDGame.fetchTexture;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.maps.Coordinate;

public abstract class CustomButton extends Clickable{
    protected String text;
    protected GlyphLayout layout;
    protected Texture background;
    protected BitmapFont font;
    protected Color backgroundColor;
    protected Color textColor;
    protected float textOffsetX,textOffsetY;

    /**
     * Button with custom text. Uses TDGame.fetchFont() to generate font.
     * @param position
     * @param text
     * @param fontsize
     * @param textColor
     */
    public CustomButton(Coordinate position,String text,int fontsize, Color textColor, Color backgroundColor,float width,float height) {
        super(position);
        this.text=text;
        font=fetchFont(fontsize);
        layout = new GlyphLayout(font, text,textColor,width,-1,true);
        //I wanted to make this use the TextBubble class, but then we'd have to store 2 of them and alternate
        //based on if the button is active or not so honestly it's easier to just leave this class be
        //changing it wouldn't simplify anything.
        this.height=height;
        this.width= layout.width+20;

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
            font.draw(batch, text, position.x()+textOffsetX, position.y()+textOffsetY,width,-1,false);
        }else{
            batch.setColor(textColor);
            batch.draw(background, position.x(), position.y(),width,height);
            font.setColor(backgroundColor);
            font.draw(batch, text, position.x()+textOffsetX, position.y()+textOffsetY,width,-1,false);
        }
        batch.setColor(Color.WHITE);
    }
    @Override
    public void dispose() {

    }
}
