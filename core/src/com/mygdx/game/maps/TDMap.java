package com.mygdx.game.maps;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.units.enemies.DrawableUnit;
import com.mygdx.game.units.enemies.Enemy;
import com.mygdx.game.units.enemies.EnemySpawner;

import java.util.ArrayList;
import java.util.List;

public class TDMap {
    // TODO
    // make an array of buildable spaces
    int mapID;
    Texture backgroundTexture;
    Path path; // defines the path enemies take
    String[] enemiesToSpawn; // defines the enemies that will be spawned
    int enemyCounter=0; // keeps track of which enemy should be spawned next
    List<Enemy> enemies; // keeps track of the enemies on the field
    EnemySpawner spawner; // object that spawns enemies, we set its coords to the first element in path
    private int playerHP=100; //if it reaches 0 we load LostScreen

    public TDMap(int mapID, Texture backgroundTexture, Path path, String[] enemiesToSpawn) {
        this.mapID=mapID;
        this.backgroundTexture = backgroundTexture;
        this.path=path;
        this.enemiesToSpawn=enemiesToSpawn;
        this.spawner=new EnemySpawner(path.getCoordinate(0));
        this.enemies=new ArrayList<>();
    }

    //returns the background texture, used in GameScreen.show
    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    //creates a a new enemy of the type determined by enemiesToSpawn
    //spawn location is path.coordinates[0]
    public Enemy spawnNextEnemy(){
        Enemy enemy=spawner.spawnEnemy(enemyCounter,enemiesToSpawn[enemyCounter]);
        enemies.add(enemy);
        enemyCounter++;
        return enemy;
    }


    //calls the move function of every enemy in the enemies list
    //checks for dead enemies
    //if an enemy reached the end of the path their damageToPlayer value
    //is subtracted from playerHP, when playerHP reaches 0 the player loses
    //returns true if the player lost
    //used in GameScreen.render
    public boolean updateEnemies(){
        List<Integer> shouldBeDeleted=new ArrayList<>();
        for (Enemy enemy:enemies) {
            int damage=enemy.move(path); //damage enemy deals to player at end of path
            if(damage>0){
                playerHP-=damage;
                shouldBeDeleted.add(enemy.getSpawnID());
            }
            if(enemy.getHealth()<=0){
                enemy.die();
                shouldBeDeleted.add(enemy.getSpawnID());
            }
        }
        //we remove the enemy from the list when they reach the end of the path
        //or if their health reached zero
        for(int enemyID:shouldBeDeleted){
            enemies.remove(enemyID);
        }
        //triggers if player loses the game
        if(playerHP<=0){
            System.out.println("PLAYER LOST THE GAME, HP="+playerHP);
            return true;
        }

        return false;
    }

    //returns a list of every enemies textrue so we can draw them
    //used in GameScreen.render
    public List<DrawableUnit> getAllEnemyTextures() {
        List<DrawableUnit> res=new ArrayList<>();
        for (Enemy enemy:enemies) {
            Coordinate enemyPos=enemy.getPosition();
            res.add(new DrawableUnit(enemy.getTexture(),new Coordinate(enemyPos.x(),enemyPos.y())));
        }
        return res;
    }

    public void dispose(){
        backgroundTexture.dispose();
        path=null;
        enemiesToSpawn=null;
        for (Enemy enemy:enemies) {
            enemy.dispose();
        }
        spawner=null;
        System.gc();
    }
}
