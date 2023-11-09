package com.mygdx.game.screens.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.TDGame;
import com.mygdx.game.screens.ChooseMapScreen;
import com.mygdx.game.screens.MainMenuScreen;
import com.mygdx.game.screens.MenuScreen;

public class LoadScreenButton extends MenuButton {
    String screenType;
    TDGame game;
    public LoadScreenButton(TDGame game, Texture activeTexture, Texture inactiveTexture, int position, String screenType) {
        super(activeTexture, inactiveTexture, position);
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

