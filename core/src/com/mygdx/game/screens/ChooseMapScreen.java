package com.mygdx.game.screens;

import static com.mygdx.game.TDGame.TEXTURE_EXTENSION;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.TDGame;
import com.mygdx.game.maps.TDMap;
import com.mygdx.game.screens.buttons.*;

public class ChooseMapScreen extends MenuScreen {
    MenuButton mainMenuButton,pickMapButton;

    public ChooseMapScreen(TDGame game){
        super(game);
        System.out.println("LOADING ChooseMapScreen");
        this.mainMenuButton=new LoadScreenButton(this.game,new Texture("buttons/menu_active"+TEXTURE_EXTENSION),new Texture("buttons/menu"+TEXTURE_EXTENSION),1,"mainMenu");
        this.pickMapButton=new PickMapButton(this.game, new Texture("buttons/pick_map_active"+TEXTURE_EXTENSION),new Texture("buttons/pick_map"+TEXTURE_EXTENSION),2,0);

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
