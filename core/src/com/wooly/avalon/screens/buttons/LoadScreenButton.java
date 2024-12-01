package com.wooly.avalon.screens.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.screens.ChooseMapScreen;
import com.wooly.avalon.screens.MainMenuScreen;
import com.wooly.avalon.screens.MenuScreen;
import com.wooly.avalon.screens.SettingsScreen;
import com.wooly.avalon.screens.UnitSetupScreen;

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
        MenuScreen screenToLoad;
        switch (screenType) {
            case "mainMenu":
                screenToLoad = new MainMenuScreen(game);
                break;
            case "chooseMap":
                screenToLoad = new ChooseMapScreen(game);
                break;
            case "shop":
                screenToLoad=new UnitSetupScreen(game);
                break;
            case "settings":
                screenToLoad=new SettingsScreen(game);
                break;
            default:
                screenToLoad = new MainMenuScreen(game);
        }
        game.setScreen(screenToLoad);
        dispose();
    }
}

