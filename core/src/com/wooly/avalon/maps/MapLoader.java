package com.wooly.avalon.maps;

import static com.wooly.avalon.TDGame.SCREEN_BOT_RIGHT;
import static com.wooly.avalon.TDGame.SCREEN_CENTER;
import static com.wooly.avalon.TDGame.SCREEN_HEIGHT;
import static com.wooly.avalon.TDGame.SCREEN_TOP_LEFT;
import static com.wooly.avalon.TDGame.TEXTURE_EXTENSION;
import static com.wooly.avalon.TDGame.place;
import static com.wooly.avalon.TDGame.player;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.units.heroes.ArthurPendragon;
import com.wooly.avalon.units.heroes.Hero;

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
        Float[] enemiesSpawnDelay=loadSpawnDelay(mapID); //loads the delay between enemy spawns
        TowerSpace[] towerSpaces=loadTowerLocations(mapID); //loads the buildable spaces
        if(player.getEquippedHero()=="None") {
            return new TDMap(mapID, backgroundTexture, path, enemiesToSpawn, enemiesSpawnDelay, towerSpaces);
        }else{
            Hero hero=loadHero(player.getEquippedHero());
            return new TDMap(mapID, backgroundTexture, path, enemiesToSpawn, enemiesSpawnDelay, towerSpaces,hero);
        }
    }

    private Hero loadHero(String heroName) {
        //TODO
        switch (heroName){
            case "Arthur":
            default:
                return new ArthurPendragon(SCREEN_CENTER);
        }
    }

    private Texture loadBackgroundTexture(int mapID){
        System.out.println("TEXTURE: map_assets/backgrounds/"+mapID+TEXTURE_EXTENSION);
        return TDGame.fetchTexture("map_assets/backgrounds/"+mapID);
    }
    private Path loadPath(int mapID){
        //TODO search database for path/paths of map by ID
        //for now we just return a path from top left to bottom right
        //slight issue: top left corner is (0,SCREEN_HEIGHT), not (0,0)
        //so we subtract the y coordinate so it's not flipped
        Path path=new Path(new Coordinate[]{
                new Coordinate(SCREEN_TOP_LEFT.x(), SCREEN_TOP_LEFT.y()),
                place(543, SCREEN_HEIGHT-497),
                place(1122, SCREEN_HEIGHT-658),
                place(1691, SCREEN_HEIGHT-820),
                place(1919, SCREEN_HEIGHT-839),
                place(SCREEN_BOT_RIGHT.x(), SCREEN_BOT_RIGHT.y())
        });
        System.out.println("PATH:"+path);
        return path;
    }
    private String[] loadEnemiesToSpawn(int mapID){
        //TODO search database for enemies to spawn by ID
        System.out.println("ENEMIES: default");
        String[] spawns= new String[100];
        for (int i = 0; i < 100; i++) {
            if(i%10==0){
                spawns[i]="ogre";
            }else {
                spawns[i]="goblin";
            }
        }
        return spawns;
    }
    private  Float[] loadSpawnDelay(int mapID){
        //TODO search database for spawn delay
        System.out.println("ENEMY DELAYS: default");
        Float[] delays= new Float[100];
        for (int i = 0; i < 100; i++) {
            if(i%10==0){
                delays[i]=10f;
            }else {
                delays[i] = 1f;
            }
        }
        return delays;
    }
    private TowerSpace towerSpaceByCoordinate(Coordinate coordinate){
        return new TowerSpace(coordinate,TDGame.fetchTexture("towers/towerButtons/actives/towerspace"),TDGame.fetchTexture("towers/towerButtons/inactives/towerspace"));
    }
    private TowerSpace[] loadTowerLocations(int mapID){
        //TODO search database for tower locations
        System.out.println("TOWERSPACES: default");
        //Check loadPath for info on SCREEN_HEIGHT-x issue
        return new TowerSpace[]{
            towerSpaceByCoordinate(place(100,SCREEN_HEIGHT-300)),
            towerSpaceByCoordinate(place(300,SCREEN_HEIGHT-510)),
            towerSpaceByCoordinate(place(900,SCREEN_HEIGHT-600))
        };
    }
}
