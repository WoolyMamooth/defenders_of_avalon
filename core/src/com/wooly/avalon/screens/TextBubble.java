package com.wooly.avalon.screens;

import static com.wooly.avalon.TDGame.fetchFont;
import static com.wooly.avalon.TDGame.fetchTexture;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.wooly.avalon.maps.Coordinate;

public class TextBubble {
    BitmapFont font;
    GlyphLayout layout;
    Coordinate position;
    Color textColor;
    Color backgroundColor=null;
    Texture backgroundTexture;
    boolean hasBackgound=false;
    float backgroundWidth,backgroundHeight;

    /**
     *
     * @param position
     * @param text
     * @param fontsize
     * @param textColor
     * @param width desired width of the text
     */
    public TextBubble(Coordinate position, String text, int fontsize, Color textColor,float width) {
        this.position = position;
        font=fetchFont(fontsize);
        layout=new GlyphLayout(font,text,textColor,width,-1,true);
        this.textColor=textColor;
    }
    public TextBubble(Coordinate position, String text, int fontsize, Color textColor, float width, Color backgroundColor){
        this(position,text,fontsize,textColor,width);
        this.backgroundColor=backgroundColor;
        backgroundTexture=fetchTexture("white_square");
        hasBackgound=true;
        backgroundWidth= layout.width+20; //+20 padding
        backgroundHeight= layout.height+20;
    }
    public void draw(SpriteBatch batch){
        if(hasBackgound){
            batch.setColor(backgroundColor);
            batch.draw(backgroundTexture,position.x(),position.y(),backgroundWidth,backgroundHeight);
        }
        font.setColor(textColor);
        font.draw(batch,layout, position.x()+10, position.y()+10+layout.height);

        batch.setColor(Color.WHITE);
    }
}
