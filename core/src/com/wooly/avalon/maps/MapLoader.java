package com.wooly.avalon.maps;

import static com.wooly.avalon.TDGame.SCREEN_BOT_RIGHT;
import static com.wooly.avalon.TDGame.SCREEN_CENTER;
import static com.wooly.avalon.TDGame.SCREEN_HEIGHT;
import static com.wooly.avalon.TDGame.SCREEN_TOP_LEFT;
import static com.wooly.avalon.TDGame.TEXTURE_EXTENSION;
import static com.wooly.avalon.TDGame.place;
import static com.wooly.avalon.TDGame.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.TDGame;
import com.wooly.avalon.units.heroes.ArthurPendragon;
import com.wooly.avalon.units.heroes.Hero;
import com.wooly.avalon.units.heroes.Mordred;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

public class MapLoader {
    //responsible for loading in the map data
    //still needs a lot of work
    TDGame game;
    String mapdataPath="mapdata/map"; //add mapID and .tsv to the end
    /**
     * Keeps track of what data is in which line of the data file.
     */
    Dictionary<String,Integer> mapdataLines=new Hashtable<>();
    /**
     * The data from the map is stored here, and the different "load" functions read
     * from here.
     */
    String[] mapdata;
    /**
     * Responsible for setting the difficulty of the game. The levels are:
     * 0-Easy,
     * 1-Normal,
     * 2-Hard,
     * 3-Extreme,
     * 4-Nightmare
     */
    String mapdataDelimiter="\t";
    public static int GAME_DIFFICULTY =4;
    public MapLoader(TDGame game) {
        this.game=game;

        mapdataLines.put("mapName",0);
        mapdataLines.put("pathX",1);
        mapdataLines.put("pathY",2);
        mapdataLines.put("enemyNumber",3);
        mapdataLines.put("enemyName",4);
        mapdataLines.put("enemyDelay",5);
        mapdataLines.put("groupDelay",6);
        mapdataLines.put("towerX",7);
        mapdataLines.put("towerY",8);
    }

    public TDMap getMap(int mapID){
        System.out.println("MAPLOADER LOADING");
        FileHandle fileHandle=Gdx.files.local(mapdataPath+mapID+".tsv");
        mapdata=fileHandle.readString().split("\n");
        System.out.println(Arrays.toString(mapdata));

        Texture backgroundTexture= loadBackgroundTexture(mapID); //loads texture for background
        Path path = loadPath(); //loads path that enemies will follow
        String[] enemiesToSpawn=loadEnemiesToSpawn(); //loads enemies that will be spawned
        Float[] enemiesSpawnDelay=loadSpawnDelay(); //loads the delay between enemy spawns
        TowerSpace[] towerSpaces=loadTowerLocations(); //loads the buildable spaces
        if(Objects.equals(player.getEquippedHero(), "None")) {
            return new TDMap(mapID, backgroundTexture, path, enemiesToSpawn, enemiesSpawnDelay, towerSpaces);
        }else{
            Hero hero=loadHero(player.getEquippedHero());
            return new TDMap(mapID, backgroundTexture, path, enemiesToSpawn, enemiesSpawnDelay, towerSpaces,hero);
        }
    }
    private Hero loadHero(String heroName) {
        Coordinate spawnPos=SCREEN_CENTER;
        //TODO
        switch (heroName){
            case "Mordred":
                return new Mordred(spawnPos);
            case "Arthur":
            default:
                return new ArthurPendragon(spawnPos);
        }
    }
    private Texture loadBackgroundTexture(int mapID){
        System.out.println("TEXTURE: map_assets/backgrounds/"+mapID+TEXTURE_EXTENSION);
        return TDGame.fetchTexture("map_assets/backgrounds/"+mapID);
    }
    private Path loadPath(){
        //slight issue: top left corner is (0,SCREEN_HEIGHT), not (0,0)
        //so we subtract the y coordinate so it's not flipped
        String[] xData=mapdata[mapdataLines.get("pathX")].split(mapdataDelimiter);
        String[] yData=mapdata[mapdataLines.get("pathY")].split(mapdataDelimiter);
        Coordinate[] coords=new Coordinate[xData.length];
        for (int i = 0; i < xData.length; i++) {
            coords[i]=new Coordinate(Float.parseFloat(xData[i]),SCREEN_HEIGHT-Float.parseFloat(yData[i]));
        }
        Path path=new Path(coords);
        System.out.println("PATH:"+path);
        return path;
    }
    private String[] loadEnemiesToSpawn(){
        String[] enemyNumbersStr=mapdata[mapdataLines.get("enemyNumber")].split(mapdataDelimiter);
        String[] enemyNames=mapdata[mapdataLines.get("enemyName")].split(mapdataDelimiter);
        List<String> spawns=new ArrayList<String>();
        for (int i = 0; i < enemyNames.length; i++) {
            for (int j = 0; j < Float.parseFloat(enemyNumbersStr[i]); j++) {
                spawns.add(enemyNames[i].trim());
            }
        }
        String[] res= new String[spawns.size()];
        for (int i = 0; i < res.length; i++) {
            res[i]= spawns.get(i);
        }
        return res;
    }
    private  Float[] loadSpawnDelay(){
        String[] enemyNumbersStr=mapdata[mapdataLines.get("enemyNumber")].split(mapdataDelimiter);

        String[] delaysStr=mapdata[mapdataLines.get("enemyDelay")].split(mapdataDelimiter);
        String[] groupDelays=mapdata[mapdataLines.get("groupDelay")].split(mapdataDelimiter);

        List<String> delays=new ArrayList<>();
        for (int i = 0; i < enemyNumbersStr.length; i++) {
            delays.add(groupDelays[i]);
            for (int j = 1; j < Float.parseFloat(enemyNumbersStr[i]); j++) {
                delays.add(delaysStr[i]);
            }
        }

        Float[] res=new Float[delays.size()];
        for (int i = 0; i < res.length; i++) {
            res[i]= Float.valueOf(delays.get(i));
        }

        return res;
    }
    private TowerSpace towerSpaceByCoordinate(Coordinate coordinate){
        return new TowerSpace(coordinate,TDGame.fetchTexture("towers/towerButtons/actives/towerspace"),TDGame.fetchTexture("towers/towerButtons/inactives/towerspace"));
    }
    private TowerSpace[] loadTowerLocations(){
        String[] xData=mapdata[mapdataLines.get("towerX")].split(mapdataDelimiter);
        String[] yData=mapdata[mapdataLines.get("towerY")].split(mapdataDelimiter);
        TowerSpace[] spaces= new TowerSpace[xData.length];
        for (int i = 0; i < xData.length; i++) {
            spaces[i]=towerSpaceByCoordinate(new Coordinate(Float.parseFloat(xData[i]),SCREEN_HEIGHT-Float.parseFloat(yData[i])));
        }
        return spaces;
    }
}
