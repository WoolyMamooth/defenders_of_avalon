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
    protected Texture background;
    protected BitmapFont font;
    protected Color backgroundColor;
    protected Color textColor;
    protected float textOffsetX,textOffsetY;
    protected float textWidth, textHeight;

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

        this.height=height;
        this.width=width;

        textOffsetX=10;
        textOffsetY=height*0.7f;

        this.backgroundColor=backgroundColor;
        this.textColor=textColor;

        background=fetchTexture("white_square");

        GlyphLayout layout = new GlyphLayout(font, text);
        textWidth = layout.width;
        textHeight=layout.height;
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
