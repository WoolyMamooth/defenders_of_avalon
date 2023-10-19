package com.mygdx.game.maps;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.units.enemies.Enemy;
import com.mygdx.game.units.enemies.EnemySpawner;

public class TDMap {
    // TODO
    // make a path variable
    // make an array of buildable spaces
    int mapID;
    Texture backgroundTexture;
    PathMarker[] path;
    Enemy[] enemies;
    String[] enemiesToSpawn;
    int enemyCounter;
    EnemySpawner spawner;

    public TDMap(int mapID) {
        this.backgroundTexture = loadTexture("map_assets/backgrounds/"+mapID+".jpg");
        this.mapID=mapID;
        path=new PathMarker[]{}; //TODO
        spawner=new EnemySpawner(path[0].coordinate);
    }

    private Texture loadTexture(String texturePath){
        return new Texture(texturePath);
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public Enemy spawnNextEnemy(){
        Enemy enemy=spawner.spawnEnemy(enemiesToSpawn[enemyCounter]);
        enemyCounter++;
        return enemy;
    }

    public void moveEnemies(){
        for (Enemy enemy:enemies) {
            enemy.move(path);
        }
    }
}
