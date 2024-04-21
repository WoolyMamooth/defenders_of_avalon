package com.wooly.avalon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.Objects;

public class MusicHandler {
    Music currentTrack;
    String current;
    int volume=5, volumeIncrement =1;
    /** Stops currently playing music, then
     * loads specified mp3 file and starts playing it in a loop.
     */
    public void playMusic(String name){
        //don't switch if the same one is playing
        if(Objects.equals(name, current)) return;

        stopMusic();

        currentTrack = Gdx.audio.newMusic(Gdx.files.internal("music/"+name+".mp3"));
        currentTrack.setLooping(true);
        setVolume(volume);
        currentTrack.play();
        current=name;
        System.out.println("MUSIC HANDLER PLAYING "+name+".mp3");
    }
    /**
     * Stops currently playing track and unloads the file.
     */
    public void stopMusic(){
        if(currentTrack!=null){
            currentTrack.stop();
            currentTrack.dispose();
        }
    }
    public void increaseVolume(){
        volume+=volumeIncrement;
        if (volume>10){
            volume=10;
        }
        setVolume(volume);
    }
    public void decreaseVolume(){
        volume-=volumeIncrement;
        if (volume<0){
            volume=0;
        }
        setVolume(volume);
    }

    public void setVolume(int volume) {
        this.volume = volume;
        if(currentTrack!=null) {
            currentTrack.setVolume(volume / 10f);
        }
    }

    public int getVolume() {
        return volume;
    }
    public int getNumberOfIncrements(){
        return 10/volumeIncrement;
    }
}
