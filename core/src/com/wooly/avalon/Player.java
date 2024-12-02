package com.wooly.avalon;

import static com.wooly.avalon.maps.MapLoader.MAP_NUMBER;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.wooly.avalon.maps.MapLoader;
import com.wooly.avalon.units.heroes.Hero;

import java.util.Arrays;
import java.util.Objects;

public class Player {
    String dataFileName="playerdata.tsv";
    FileHandle fileHandle;
    /**
     * An array with the names of every implemented hero.
     */
    public String[] existingHeroes={"Arthur","Mordred","Merlin"};
    /**
     * An array with the names of every implemented tower.
     */
    public String[] existingTowers={"archer","barracks","wizard","farm","ballista","paladins","priest"};
    String[] unlockedTowers;
    String[] unlockedHeroes;
    String[] equippedTowers;
    String equippedHero;
    private int stardust; //global currency used to purchase towers and heroes, gained from completing maps
    int[] mapStars=new int[MAP_NUMBER]; //keeps track of what difficulty the player has defeated on what maps
    /**
     * Keeps track of all data related to the player. ex.: unlocked towers.
     */
    public Player() {
        fileHandle=Gdx.files.local(dataFileName);
        try {
            loadData();
        }catch (GdxRuntimeException exception){
            //this happens on first install because the file hasn't been created yet on specific device
            createSavefile();
            loadData();
        }
    }

    public void createSavefile(){
        fileHandle.writeString("0\t0\t5\t10",false); //stardust, difficulty, music volume, menu scale
        fileHandle.writeString("\nArthur",true); //base unlocked hero
        for (int i = 1; i < existingHeroes.length; i++) { //make the rest unlockable
            fileHandle.writeString("\tNone",true);
        }
        fileHandle.writeString("\narcher\tbarracks\twizard",true); //base unlocked towers
        for (int i = 3; i < existingTowers.length; i++) { //make the rest unlockable
            fileHandle.writeString("\tNone",true);
        }
        //equipped units
        fileHandle.writeString("\nArthur",true);
        fileHandle.writeString("\narcher\tbarracks\twizard\tNone\n",true);

        //map stars
        for (int i = 0; i < MAP_NUMBER; i++) {
            fileHandle.writeString("0\t",true);
        }
    }

    /**
     * Loads stardust amount, unlocked and equipped towers, heroes into memory.
     */
    public void loadData(){
        if(!Gdx.files.isLocalStorageAvailable()){
            System.out.println("NO LOCAL STORAGE AVAILABLE");
            return;
        }
        String dataPath=Gdx.files.getLocalStoragePath()+dataFileName;
        System.out.println("LOADING Player data from "+dataPath);

        //loads player info into memory
        String[] datafile =fileHandle.readString().split("\n");
        System.out.println(datafile);

        stardust=Integer.parseInt(datafile[0].split("\t")[0]);
        MapLoader.GAME_DIFFICULTY=Integer.parseInt(datafile[0].split("\t")[1]);
        TDGame.musicHandler.setVolume(Integer.parseInt(datafile[0].split("\t")[2]));
        Hero.menuScale=Integer.parseInt(datafile[0].split("\t")[3])/10f;

        unlockedHeroes=datafile[1].split("\t");
        unlockedTowers=datafile[2].split("\t");
        equippedHero=datafile[3].split("\t")[0];
        equippedTowers=datafile[4].split("\t");

        String[] stars=datafile[5].split("\t");
        for (int i = 0; i < MAP_NUMBER; i++) {
            if(stars[i] == null){
                mapStars[i]=0;
                continue;
            }
            mapStars[i]=Integer.parseInt(stars[i]);
        }
    }
    /**
     * Writes to playerdata.txt to save unlocked towers, heroes and stardust.
     */
    public void saveData(){
        fileHandle.writeString(stardust +"\t"+ MapLoader.GAME_DIFFICULTY+"\t"+TDGame.musicHandler.getVolume()+"\t"+(int)(Hero.menuScale*10),false); //delete all info
        fileHandle.writeString("\n",true);
        for (String hero:unlockedHeroes) {
            fileHandle.writeString(hero,true);
            fileHandle.writeString("\t",true);
        }
        fileHandle.writeString("\n",true);
        for (String tower:unlockedTowers) {
            fileHandle.writeString(tower,true);
            fileHandle.writeString("\t",true);
        }
        fileHandle.writeString("\n",true);
        fileHandle.writeString(equippedHero,true);
        fileHandle.writeString("\n",true);
        for (String tower:equippedTowers) {
            fileHandle.writeString(tower,true);
            fileHandle.writeString("\t",true);
        }
        fileHandle.writeString("\n",true);
        for (int map:mapStars) {
            fileHandle.writeString(map+"\t",true);
        }
    }

    /**
     * Subtracts cost from the Players stardust. Returns false if not enough, true otherwise.
     */
    public boolean spendStardust(int cost){
        if(cost>stardust){
            System.out.println("Not enough stardust");
            return false;
        }
        stardust-=cost;
        return true;
    }
    /**
     * Player unlocks given hero.
     * @param name
     */
    public void unlockHero(String name){
        for (int i = 0; i < unlockedHeroes.length; i++) {
            if(Objects.equals(unlockedHeroes[i], "None")){
                unlockedHeroes[i]=name;
                return;
            }
        }
        //this should never run
        System.out.println("All heroes unlocked");
    }
    /**
     * Player unlocks given tower.
     * @param name
     */
    public void unlockTower(String name){
        for (int i = 0; i < unlockedTowers.length; i++) {
            if(Objects.equals(unlockedTowers[i], "None")){
                unlockedTowers[i]=name;
                return;
            }
        }
        //this should never run
        System.out.println("All towers unlocked");
    }
    public void equipHero(String name, boolean unequip){
        if(unequip){
            equippedHero="None";
        }else{
            equippedHero=name;
        }
    }

    /**
     * Equips a tower to the first empty slot found and returns true, if no empty slot is found returns false.
     * @param name
     * @param unequip if true instead unequips the tower
     * @return
     */
    public boolean equipTower(String name, boolean unequip){
        if(unequip){
            for (int i = 0; i < equippedTowers.length; i++) {
                if(Objects.equals(equippedTowers[i], name)){
                    equippedTowers[i]="None";
                    return true;
                }
            }
        }else{
            for (int i = 0; i < equippedTowers.length; i++) {
                if(Objects.equals(equippedTowers[i], "None")){
                    equippedTowers[i]=name;
                    return true;
                }
            }
        }
        return false;
    }
    public int getStarCount(int mapID){
        return mapStars[mapID];
    }
    public boolean updateStarCount(int mapID, int stars){
        if(stars > mapStars[mapID]){
            mapStars[mapID] = stars;
            return true;
        }
        return false;
    }
    public String[] getEquippedTowers() {
        return equippedTowers;
    }
    public String getEquippedHero() {
        return equippedHero;
    }
    public String[] getUnlockedTowers() {
        return unlockedTowers;
    }
    public String[] getUnlockedHeroes() {
        return unlockedHeroes;
    }
    public int getStardust() {
        return stardust;
    }
    public void gainStardust(int amount){
        stardust+=amount;
    }
    @Override
    public String toString() {
        return "Player{" +
                "stardust="+stardust+
                ", unlockedTowers=" + Arrays.toString(unlockedTowers) +
                ", unlockedHeroes=" + Arrays.toString(unlockedHeroes) +
                ", equippedTowers=" + Arrays.toString(equippedTowers) +
                ", equippedHero='" + equippedHero + '\'' +
                '}';
    }
}
