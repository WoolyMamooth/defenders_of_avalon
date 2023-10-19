package com.mygdx.game.screens;

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
        this.mainMenuButton=new LoadScreenButton(this,new Texture("buttons/menu_active.jpg"),new Texture("buttons/menu.jpg"),1,"mainMenu");
        this.pickMapButton=new PickMapButton(this, new Texture("buttons/pick_map_active.jpg"),new Texture("buttons/pick_map.jpg"),2,new TDMap());

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        game.batch.begin();
        renderButton(this.mainMenuButton);
        renderButton(this.pickMapButton);
        game.batch.end();
    }


}
