package com.example.emergency_service.classes;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.ListView;
import android.widget.MediaController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PlayMedia implements MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl{

    private Context context;
    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    private ListView listView;
    private boolean isShowing;

    public PlayMedia(Context context) {
        this.context = context;
        setShowing(false);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaController = new MediaController(context);
    }

    public PlayMedia(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
        setShowing(true);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaController = new MediaController(context);
    }

    public void Stop(){
        if (mediaController.isShowing()){
            mediaController.hide();
        }
        if (isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    public void Start(int position){

        FileInputStream fis = null;
        try {
            try{
                FileManager fileManager = new FileManager(context);
                File file = new File(fileManager.getListFileCall().get(position));
                fis = new FileInputStream(file);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(fis.getFD());
                mediaPlayer.prepare();

            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                    }
                }
            }
        }catch (IOException e){
            e.getMessage();
        }
        mediaPlayer.start();
    }

    public void Start(File file){

        FileInputStream fis = null;
        try {
            try{
                fis = new FileInputStream(file);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(fis.getFD());
                mediaPlayer.prepare();

            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                    }
                }
            }
        }catch (IOException e){
            e.getMessage();
        }
        mediaPlayer.start();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaController.setMediaPlayer(this);
        mediaController.setEnabled(true);
        if (getShowing()) {
            mediaController.setAnchorView(listView);
            mediaController.show(0);
        }
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return mediaPlayer.getAudioSessionId();
    }

    public boolean getShowing() {
        return isShowing;
    }

    public void setShowing(boolean showing) {
        isShowing = showing;
    }

    public void onDestroy() {
        Stop();
        mediaPlayer.release();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public MediaController getMediaController() {
        return mediaController;
    }
}
