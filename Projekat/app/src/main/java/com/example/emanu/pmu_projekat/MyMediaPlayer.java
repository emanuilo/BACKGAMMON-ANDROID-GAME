package com.example.emanu.pmu_projekat;

import android.media.MediaPlayer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by emanu on 3/27/2018.
 */

public class MyMediaPlayer implements Serializable{
    private MediaPlayer mediaPlayer;

    public MyMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void setLooping(boolean b) {
        mediaPlayer.setLooping(b);
    }

    public void start() throws IllegalStateException {
        mediaPlayer.start();
    }

    public void pause() throws IllegalStateException {
        mediaPlayer.pause();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    private void readObject(final ObjectInputStream objectInputStream){

    }

    private void writeObject(final ObjectOutputStream objectOutputStream){

    }

}
