package com.mygdx.game.screens;

import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.TDGame;
import com.mygdx.game.screens.buttons.*;

public class ChooseMapScreen extends MenuScreen {
    MenuButton mainMenuButton,pickMapButton;

    public ChooseMapScreen(TDGame game){
        super(game);
        System.out.println("LOADING ChooseMapScreen");
        this.mainMenuButton=new LoadScreenButton(this.game,TDGame.fetchTexture("buttons/menu_active"),TDGame.fetchTexture("buttons/menu"),1,"mainMenu");
        this.pickMapButton=new PickMapButton(this.game,TDGame.fetchTexture("buttons/pick_map_active"),TDGame.fetchTexture("buttons/pick_map"),2,0);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        game.batch.begin();
        renderButton(this.mainMenuButton);
        renderButton(this.pickMapButton);
        game.batch.end();
    }

    @Override
    public void dispose() {
        mainMenuButton.dispose();
        pickMapButton.dispose();
    }
}
