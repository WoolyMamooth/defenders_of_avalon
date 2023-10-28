package com.mygdx.game.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.screens.buttons.Clickable;
import com.mygdx.game.units.DrawableUnit;
import com.mygdx.game.units.enemies.Enemy;
import com.mygdx.game.units.enemies.EnemySpawner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TDMap {
    // TODO
    // make an array of buildable spaces for towers
    int mapID;
    Texture backgroundTexture;
    Path path; // defines the path enemies take
    String[] enemiesToSpawn; // defines the enemies that will be spawned
    int enemyCounter=0; // keeps track of which enemy should be spawned next
    List<Enemy> enemies; // keeps track of the enemies on the field
    EnemySpawner spawner; // object that spawns enemies, we set its coords to the first element in path
    TowerSpace[] towerSpaces; // defines the places where the player will be able to build towers
    public static int lastTowerID=0; // keeps track of the id of towers
    private int playerHP=100; //if it reaches 0 we load LostScreen

    public TDMap(int mapID, Texture backgroundTexture, Path path, String[] enemiesToSpawn,TowerSpace[] towerSpaces) {
        this.mapID=mapID;
        this.backgroundTexture = backgroundTexture;
        this.path=path;
        this.enemiesToSpawn=enemiesToSpawn;
        this.spawner=new EnemySpawner(path.getCoordinate(0));
        this.enemies=new ArrayList<>();
        this.towerSpaces=towerSpaces;
    }

    //returns the background texture, used in GameScreen.show
    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    //creates a a new enemy of the type determined by enemiesToSpawn
    //spawn location is path.coordinates[0]
    public void spawnNextEnemy(){
        Enemy enemy=spawner.spawnEnemy(enemyCounter,enemiesToSpawn[enemyCounter]);
        enemies.add(enemy);
        enemyCounter++;
    }

    //enemy update function
    public boolean updateEnemies(){
    /*
    1.  calls the move function of every enemy in the enemies list
    2.  checks for dead enemies
    3.  if an enemy reached the end of the path their damageToPlayer value
        is subtracted from playerHP, when playerHP reaches 0 the player loses
        returns true if the player lost
    Used in GameScreen.render
    */
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

    //returns a list of every enemies texture so we can draw them
    //used in GameScreen.render
    public List<DrawableUnit> getAllEnemyTextures() {
        List<DrawableUnit> enemyList=new ArrayList<>();
        for (Enemy enemy: enemies) {
            Coordinate enemyPos=enemy.getPosition();
            enemyList.add(new DrawableUnit(enemy.getTexture(),new Coordinate(enemyPos.x(),enemyPos.y())));
        }
        return enemyList;
    }

    public void drawAllTowers(){
        for (TowerSpace towerspace:towerSpaces) {
            towerspace.draw();
            if (towerspace.isActive() && Gdx.input.isTouched()) {
                towerspace.onClick();
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //frees up the memory
    public void dispose(){
        backgroundTexture.dispose();
        path=null;
        enemiesToSpawn=null;
        for (Enemy enemy:enemies) {
            enemy.dispose();
        }
        for (TowerSpace towerspace:towerSpaces) {
            towerspace.dispose();
        }
        spawner=null;
        System.gc();
    }
}
