package com.mygdx.game.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.units.enemies.Enemy;
import com.mygdx.game.units.enemies.EnemySpawner;

import java.util.ArrayList;
import java.util.List;

public class TDMap {
    int mapID;
    Texture backgroundTexture;
    Path path; // defines the path enemies take
    String[] enemiesToSpawn; // defines the enemies that will be spawned
    int enemyCounter=0; // keeps track of which enemy should be spawned next
    Float[] enemiesSpawnDelay; // defines the delay between enemy spawns
    float timeSinceLastSpawn=0; // keeps track of how much time has passed since we last spawned a new enemy
    List<Enemy> enemies; // keeps track of the enemies on the field
    EnemySpawner spawner; // object that spawns enemies, we set its coords to the first element in path
    TowerSpace[] towerSpaces; // defines the places where the player will be able to build towers
    public static int lastTowerID=0; // keeps track of the id of towers
    private int playerHP=100; // if it reaches 0 we load LostScreen
    private int playerGold=100; // used to build towers

    public TDMap(int mapID, Texture backgroundTexture, Path path, String[] enemiesToSpawn,Float[] enemiesSpawnDelay,TowerSpace[] towerSpaces) {
        this.mapID=mapID;
        this.backgroundTexture = backgroundTexture;
        this.path=path;
        this.enemiesToSpawn=enemiesToSpawn;
        this.enemiesSpawnDelay=enemiesSpawnDelay;
        this.spawner=new EnemySpawner(path.getCoordinate(0));
        this.enemies=new ArrayList<>();
        this.towerSpaces=towerSpaces;
    }

    //returns the background texture, used in GameScreen.show
    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }
    /**
     * Checks if a new enemy should be spawned and spawns them if they should, returns true if an enemy was spawned
     */
    public boolean trySpawn(float timeSinceLastFrame){
        if(enemyCounter>=enemiesToSpawn.length) return false; //return if they have already all been spawned
        timeSinceLastSpawn+=timeSinceLastFrame;
        if(timeSinceLastSpawn>=enemiesSpawnDelay[enemyCounter]){
            timeSinceLastSpawn=0;
            spawnNextEnemy();
            return true;
        }
        return false;
    }
    /**
     * Creates a new enemy of the type determined by enemiesToSpawn
     * spawn location is path.coordinates[0]
     */
    private void spawnNextEnemy(){
        Enemy enemy=spawner.spawnEnemy(enemyCounter,enemiesToSpawn[enemyCounter]);
        enemies.add(enemy);
        enemyCounter++;
    }

    /**
     * Updates both enemies and towers in this order.
     * @param timeSinceLastFrame
     * @return 0 normally, 1 if player lost, 2 if player won.
     */
    public int update(float timeSinceLastFrame){
        updateEnemies();
        updateTowers(timeSinceLastFrame);

        //triggers if player loses the game
        if(playerHP<=0){
            System.out.println("PLAYER LOST THE GAME");
            return 1;
        }else if(enemyCounter>=enemiesToSpawn.length && enemies.isEmpty()){
            System.out.println("PLAYER WON THE GAME");
            return 2;
        }
        return 0;
    }

    /**
     * 1.  calls the move function of every enemy in the enemies list
     * 2.  checks for dead enemies
     * 3.  if an enemy reached the end of the path their damageToPlayer value
     *    is subtracted from playerHP, when playerHP reaches 0 the player loses
     *    returns true if the player lost
     * Used in GameScreen.render
     */
    private void updateEnemies(){
        List<Enemy> shouldBeDeleted=new ArrayList<>();
        for (Enemy enemy:enemies) {
            int damage=enemy.update(path); //damage enemy deals to player at end of path, this also moves the enemy
            if(damage>0){
                playerHP-=damage;
                shouldBeDeleted.add(enemy);
                System.out.println("Enemy "+enemy.getSpawnID()+" has reached the end and will be deleted");
            }else if(enemy.getCurrentHp()<=0){
                enemy.die();
                shouldBeDeleted.add(enemy);
                System.out.println("Enemy "+enemy.getSpawnID()+" has died and will be deleted");
            }
        }
        //we remove the enemy from the list when they reach the end of the path
        //or if their health reached zero
        for(Enemy enemy:shouldBeDeleted){
            enemies.remove(enemy);
        }
    }

    /**
     * Moves projectiles
     * Resets targetting on each tower
     */
    private void updateTowers(float delta){
        for (TowerSpace towerspace:towerSpaces) {
            towerspace.update(enemies,delta);
        }
    }

    public void draw(SpriteBatch batch){
        drawAllTowers(batch);
        drawAllEnemies(batch);
    }
    private void drawAllEnemies(SpriteBatch batch){
        for (Enemy enemy:enemies) {
            enemy.draw(batch);
        }
    }
    private void drawAllTowers(SpriteBatch batch){
        for (TowerSpace towerspace:towerSpaces) {
            towerspace.draw(batch);
        }
    }
    public int getPlayerHP() {
        return playerHP;
    }
    public void setPlayerHP(int playerHP) {
        this.playerHP = playerHP;
    }
    public int getPlayerGold() {
        return playerGold;
    }
    public void setPlayerGold(int playerGold) {
        this.playerGold = playerGold;
    }

    /**
     * Frees up the memory.
     */
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
