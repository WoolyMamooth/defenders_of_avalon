package com.mygdx.game.units.towers;

public class TowerUpgrade {
    public String stat; //determines the stat of the tower this upgrades
    int level=0; //current level of the upgrade
    int maxLevel; //maximum level this can reach
    private int costOfNextLevel;
    private float costScaling;
    private float increasePerLevel;
    public TowerUpgrade(String stat, int maxLevel,float increasePerLevel,int costOfFirstLevel, float costScaling) {
        this.stat = stat;
        this.maxLevel = maxLevel;
        this.increasePerLevel=increasePerLevel;
        this.costOfNextLevel=costOfFirstLevel;
        this.costScaling=costScaling;
    }

    /**
     * Increases the level of the upgrade.
     * @return false if it is already maxed out, true otherwise
     */
    public boolean levelUp(){
        if(!isMaxed()){
            level++;
            costOfNextLevel*=costScaling; //increase the cost for the next level
            System.out.println(stat+" leveled up");
            return true;
        }
        System.out.println(stat+" is already maxed");
        return false;
    }
    public int getCost(){
        return costOfNextLevel;
    }
    public float getIncrease(){
        return increasePerLevel;
    }
    public boolean isMaxed(){
        return (level>=maxLevel);
    }
    public int getLevel() {
        return level;
    }
    public int getMaxLevel() {
        return maxLevel;
    }
}
