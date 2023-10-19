package com.mygdx.game.screens.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.screens.ChooseMapScreen;
import com.mygdx.game.screens.MainMenuScreen;
import com.mygdx.game.screens.MenuScreen;

public class LoadScreenButton extends MenuButton {
    String screenType;
    public LoadScreenButton(MenuScreen screen, Texture activeTexture, Texture inactiveTexture, int position,String screenType) {
        super(screen, activeTexture, inactiveTexture, position);
        this.screenType=screenType;
    }

    @Override
    public void onClick() {
        System.out.println("PLAY BUTTON CLICKED");
        screen.dispose();
        MenuScreen screenToLoad = null;
        switch (screenType) {
            case "mainMenu":
                screenToLoad = new MainMenuScreen(screen.game);
                break;
            case "chooseMap":
                screenToLoad = new ChooseMapScreen(screen.game);
                break;
            default:
                screenToLoad = new MainMenuScreen(screen.game);
                System.out.println("your button is configured wrong");
        }
        screen.game.setScreen(screenToLoad);
    }
}

