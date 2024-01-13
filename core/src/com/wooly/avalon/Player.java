package com.wooly.avalon;


import java.util.Arrays;

public class Player {
    String[] unlockedTowers;
    String[] unlockedHeroes;
    String[] equippedTowers;
    String equippedHero;

    /**
     * Keeps track of all data related to the player. ex.: unlocked towers.
     * TODO implement saving player data and loading the save
     */
    public Player() {
        unlockedTowers=new String[]{"archer","barracks","None","None"};
        unlockedHeroes=new String[]{"Mordred","Arthur","None"};

        loadData();
    }

    //TODO loads in the unlocked towers of the player
    public void loadData(){
        //unlockedTowers= search database for towers player has bought
        //unlockedHeroes= search database for heroes player has bought

        //automatically equip the first 4 unlocked towers and the first unlocked hero
        //since every player gets these for free
        this.equippedTowers = new String[4];
        for (int i = 0; i < 4; i++) {
            equippedTowers[i]=unlockedTowers[i];
        }
        this.equippedHero = unlockedHeroes[0];
    }

    public String[] getEquippedTowers() {
        return equippedTowers;
    }

    public String getEquippedHero() {
        return equippedHero;
    }

    @Override
    public String toString() {
        return "Player{" +
                "unlockedTowers=" + Arrays.toString(unlockedTowers) +
                ", unlockedHeroes=" + Arrays.toString(unlockedHeroes) +
                ", equippedTowers=" + Arrays.toString(equippedTowers) +
                ", equippedHero='" + equippedHero + '\'' +
                '}';
    }
}
