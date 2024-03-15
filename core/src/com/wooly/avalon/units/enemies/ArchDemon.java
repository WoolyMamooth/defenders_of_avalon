package com.wooly.avalon.units.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.wooly.avalon.maps.Coordinate;
import com.wooly.avalon.maps.Path;
import com.wooly.avalon.maps.TDMap;

public class ArchDemon extends Enemy{
    TDMap map;
    float summonDelay,summonMaxDelay;
    String summonName;
    int summonLocationNumber, summonPerLocation;
    Coordinate[] summonLocations; //where the demons will be summoned
    int[] summonLocationIDs; //needed so they know which way to go when they are summoned
    /**
     * Summons demons on different places on the track.
     * @param spawnID
     * @param texture
     * @param position
     * @param health
     * @param armor
     * @param magicResistance
     * @param movementSpeed
     * @param damageToPlayer
     * @param damage
     * @param damageType
     * @param goldDropped
     */
    public ArchDemon(int spawnID, Texture texture, Coordinate position, int health, int armor, int magicResistance, float movementSpeed, int damageToPlayer, int damage, String damageType, int goldDropped, TDMap map) {
        super(spawnID, texture, position, health, armor, magicResistance, movementSpeed, damageToPlayer, damage, damageType, goldDropped);
        this.map=map;
        summonMaxDelay=20;
        summonDelay=summonMaxDelay;
        summonLocationNumber=3;
        summonPerLocation=4;
        summonName="demon";
        summonLocations=new Coordinate[summonLocationNumber];
        summonLocationIDs=new int[summonLocationNumber];

        Path path=map.getPath();
        int pathLength=path.getLength();
        pathLength=pathLength-(pathLength/4);
        int summonLocationDistance=pathLength/summonLocationNumber;
        int totalSummonLocationDistance=0;
        for (int i = 0; i < summonLocationNumber; i++) {
            summonLocations[i]=path.getCoordinate(totalSummonLocationDistance);
            summonLocationIDs[i]=totalSummonLocationDistance;
            totalSummonLocationDistance+=summonLocationDistance;
        }
    }
    private void summon(){
        System.out.println("SUMMONING");
        for (int i = 0; i < summonLocationNumber; i++) {
            System.out.println("Location: "+i);
            for (int j = 0; j < summonPerLocation; j++) {
                System.out.println("Demon: "+ j);
                Coordinate summonPlace=summonLocations[i];
                map.addExtraEnemy(
                        summonName,
                        summonPlace,
                        summonLocationIDs[i]);
            }
        }
    }

    @Override
    public int update(Path path, float timeSinceLastFrame) {
        summonDelay-=timeSinceLastFrame;
        if(summonDelay<=0){
            summon();
            summonDelay=summonMaxDelay;
        }
        return super.update(path, timeSinceLastFrame);
    }
}
