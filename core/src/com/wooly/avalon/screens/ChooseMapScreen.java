package com.wooly.avalon.screens;

import static com.wooly.avalon.TDGame.place;
import static com.wooly.avalon.maps.MapLoader.MAPDATA_PATH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.MapLoader;
import com.wooly.avalon.screens.buttons.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChooseMapScreen extends MenuScreen {
    Clickable mainMenuButton;
    List<PickMapButton> mapButtons=new ArrayList<>();

    public ChooseMapScreen(TDGame game){
        super(game);
        System.out.println("LOADING ChooseMapScreen");

        Coordinate pos=centerButton(1);
        this.mainMenuButton=new LoadScreenButton(this.game,TDGame.fetchTexture("buttons/menu_active"),
                TDGame.fetchTexture("buttons/menu"),
                place(pos.x(), pos.y()),"mainMenu");

        loadMapButtons(1);

        System.gc();
    }
    private void loadMapButtons(){
        FileHandle mapdataFolder=Gdx.files.local("mapdata");
        System.out.println(Gdx.files.getLocalStoragePath()+mapdataFolder.name());
        int i=2;
        for (FileHandle file :mapdataFolder.list()) {
            System.out.println("button"+(i-2));
            mapButtons.add(new PickMapButton(game,i-2,centerButton(i),
                    file.readString().split("\n")[0].trim(), 45, Color.WHITE,Color.BLACK,1000,64));
            i++;
        }
        System.out.println(i-2+" maps loaded");
    }
    private void loadMapButtons(int a){
        for (int i = 0; i < 5; i++) {
            FileHandle file=Gdx.files.internal(MAPDATA_PATH+i+".tsv");
            mapButtons.add(new PickMapButton(game,i,centerButton(i+2),
                    file.readString().split("\n")[0].trim(),45,Color.WHITE,Color.BLACK,1000,64));
        }
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        game.batch.begin();
        renderButton(this.mainMenuButton);
        for (PickMapButton button:mapButtons) {
            renderButton(button);
        }
        game.batch.end();
    }

    @Override
    public void dispose() {
        mainMenuButton.dispose();
        for (PickMapButton button:mapButtons) {
            button.dispose();
        }
    }
}
