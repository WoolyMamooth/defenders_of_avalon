package com.mygdx.game.maps;

import static com.mygdx.game.TDGame.SCREEN_BOT_RIGHT;
import static com.mygdx.game.TDGame.SCREEN_HEIGHT;
import static com.mygdx.game.TDGame.SCREEN_TOP_LEFT;
import static com.mygdx.game.TDGame.TEXTURE_EXTENSION;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.TDGame;

public class MapLoader {
    //responsible for loading in the map data
    //still needs a lot of work

    TDGame game;
    public MapLoader(TDGame game) {
        this.game=game;
    }

    public TDMap getMap(int mapID){
        System.out.println("MAPLOADER LOADING");
        Texture backgroundTexture= loadBackgroundTexture(mapID); //loads texture for background
        Path path = loadPath(mapID); //loads path that enemies will follow
        String[] enemiesToSpawn=loadEnemiesToSpawn(mapID); //loads enemies that will be spawned
        TowerSpace[] towerSpaces=loadTowerLocations(mapID); //loads the buildable spaces
        return new TDMap(mapID,backgroundTexture,path,enemiesToSpawn,towerSpaces);
    }

    private Texture loadBackgroundTexture(int mapID){
        System.out.println("TEXTURE: map_assets/backgrounds/"+mapID+TEXTURE_EXTENSION);
        return new Texture("map_assets/backgrounds/"+mapID+TEXTURE_EXTENSION);
    }

    private Path loadPath(int mapID){
        //TODO search database for path/paths of map by ID
        //for now we just return a path from top left to bottom right
        //slight issue: top left corner is (0,SCREEN_HEIGHT), not (0,0)
        //so we subtract the y coordinate so it's not flipped
        Path path=new Path(new Coordinate[]{
                new Coordinate(SCREEN_TOP_LEFT.x(), SCREEN_TOP_LEFT.y()),
                new Coordinate(543, SCREEN_HEIGHT-497),
                new Coordinate(1122, SCREEN_HEIGHT-658),
                new Coordinate(1691, SCREEN_HEIGHT-820),
                new Coordinate(1919, SCREEN_HEIGHT-839),
                new Coordinate(SCREEN_BOT_RIGHT.x(), SCREEN_BOT_RIGHT.y())
        });
        System.out.println("PATH:"+path);
        return path;
    }

    private String[] loadEnemiesToSpawn(int mapID){
        //TODO search database for enemies to spawn by ID
        System.out.println("ENEMIES: default");
        return new String[]{ //for now we just return a red square
                "red_square"
        };
    }

    private TowerSpace towerSpaceByCoordinate(Coordinate coordinate){
        return new TowerSpace(game,coordinate,new Texture("towers/towerButtons/actives/towerspace"+TEXTURE_EXTENSION),new Texture("towers/towerButtons/inactives/towerspace"+TEXTURE_EXTENSION));
    }
    private TowerSpace[] loadTowerLocations(int mapID){
        //TODO search database for tower locations
        System.out.println("TOWERSPACES: default");
        return new TowerSpace[]{
            towerSpaceByCoordinate(new Coordinate(200,200)),
            towerSpaceByCoordinate(new Coordinate(400,400)),
            towerSpaceByCoordinate(new Coordinate(1000,400))
        };
    }
}
