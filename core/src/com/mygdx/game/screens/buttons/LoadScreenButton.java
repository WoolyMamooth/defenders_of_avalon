package com.mygdx.game.screens.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.TDGame;
import com.mygdx.game.maps.Coordinate;
import com.mygdx.game.screens.ChooseMapScreen;
import com.mygdx.game.screens.MainMenuScreen;
import com.mygdx.game.screens.MenuScreen;

public class LoadScreenButton extends Button {
    String screenType;
    TDGame game;
    public LoadScreenButton(TDGame game, Texture activeTexture, Texture inactiveTexture, Coordinate position, String screenType) {
        super(position, activeTexture, inactiveTexture);
        this.game=game;
        this.screenType=screenType;
    }

    @Override
    public void onClick() {
        System.out.println("PLAY BUTTON CLICKED");
        MenuScreen screenToLoad;
        switch (screenType) {
            case "mainMenu":
                screenToLoad = new MainMenuScreen(game);
                break;
            case "chooseMap":
                screenToLoad = new ChooseMapScreen(game);
                break;
            default:
                screenToLoad = new MainMenuScreen(game);
                System.out.println("your button is configured wrong");
        }
        game.setScreen(screenToLoad);
        dispose();
    }
}

