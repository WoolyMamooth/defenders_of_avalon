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
    String text;
    int align=-1;
    boolean wrap=true;
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
        this.text=text;
        font=fetchFont(fontsize);
        layout=new GlyphLayout(font,text,textColor,width,-1,true); //halign -1 is left align, 0 is center, 1 is right (i think)
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
        font.draw(batch,layout, position.x()+10, position.y()+10+layout.height); //+10 for margin

        batch.setColor(Color.WHITE);
    }
    public void setText(String text){
        this.text=text;
        layout=new GlyphLayout(font,text,textColor, layout.width, align,wrap);
    }

    /**
     * -1=left, 0=center, 1=right
     */
    public void setAlign(int align){
        layout=new GlyphLayout(font,text,textColor, layout.width, align,wrap);
    }
    public void setWrap(boolean wrap){
        this.wrap=wrap;
        layout=new GlyphLayout(font,text,textColor, layout.width, align,wrap);
    }
    public float getWidth(){
        return layout.width;
    }
    public float getHeight(){
        return layout.height;
    }
}
