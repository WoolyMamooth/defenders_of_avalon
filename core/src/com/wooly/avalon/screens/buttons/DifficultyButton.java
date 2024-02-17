package com.wooly.avalon.screens.buttons;

import com.badlogic.gdx.graphics.Color;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.MapLoader;

public class DifficultyButton extends CustomButton{
    Coordinate originalPosition;
    /**
     * Used to cycle between the different levels of difficulty.
     *
     * @param position
     * @param fontsize
     * @param backgroundColor
     * @param width
     * @param height
     */
    public DifficultyButton(Coordinate position, int fontsize, Color backgroundColor, float width, float height) {
        super(position, "Easy", fontsize, Color.GREEN, backgroundColor, width, height);
        originalPosition=position;
        swapText();
    }
    private void swapText(){
        switch (MapLoader.GAME_DIFFICULTY){
            case 0:
                textColor=Color.GREEN;
                text="Easy";
                break;
            case 1:
                textColor=Color.CYAN;
                text="Normal";
                break;
            case 2:
                textColor=Color.WHITE;
                text="Hard";
                break;
            case 3:
                textColor=Color.YELLOW;
                text="Extreme";
                break;
            case 4:
                textColor=Color.RED;
                text="Nightmare";
                break;
        }
        layout.setText(font,text);
        this.width= layout.width+20;
        position=originalPosition.subtract(new Coordinate((width*0.1f),0));
    }
    @Override
    public void onClick() {
        MapLoader.GAME_DIFFICULTY++;
        if(MapLoader.GAME_DIFFICULTY >4){
            MapLoader.GAME_DIFFICULTY =0;
        }
        swapText();
    }
}
