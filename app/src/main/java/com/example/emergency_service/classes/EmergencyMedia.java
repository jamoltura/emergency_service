package com.example.emergency_service.classes;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import com.example.emergency_service.R;
import com.example.emergency_service.interfaces.OnCompilePlay;

import java.io.File;
import java.io.InputStream;

public class EmergencyMedia {

    final String recordAudio = Manifest.permission.RECORD_AUDIO;
    final int MY_RECORDAUDIO = 1;

    private AudioRecord audioRecord;
    private PlayMedia playMedia;
    private Context context;
    private File file;
    private OnCompilePlay onCompilePlay;

    public EmergencyMedia(Context context) {
        this.context = context;
    }

    public void startOnlyMedia(ListView listView, int position){
        playMedia = new PlayMedia(context, listView);
        playMedia.getMediaPlayer().setOnCompletionListener(completionListener_list);
        playMedia.Start(position);
    }

    public void startOnlyMedia(File file){
        this.file = file;
        playMedia = new PlayMedia(context);
        playMedia.getMediaPlayer().setOnCompletionListener(completionListener_wrong);
        playMedia.Start(file);
    }

    public void startMediaAndRecord(File file, String value){
        this.file = file;
        playMedia = new PlayMedia(context);
        playMedia.getMediaPlayer().setOnCompletionListener(completionListener_record);
        if(getPermission(recordAudio)){
            audioRecord = new AudioRecord(context);
            audioRecord.recordStart(value);
        }

        playMedia.Start(file);
    }

    public void setOnCompilePlay(OnCompilePlay onCompilePlay){
        this.onCompilePlay = onCompilePlay;
    }

    MediaPlayer.OnCompletionListener completionListener_list = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            onStop();
            onCompilePlay.OnCompilePlaying();
        }
    };

    MediaPlayer.OnCompletionListener completionListener_wrong = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            onStop();
            onCompilePlay.OnCompilePlaying();
        }
    };

    MediaPlayer.OnCompletionListener completionListener_record = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            onStop();
            onCompilePlay.OnCompilePlaying();
        }
    };

    public void onDestroy(){
        onStop();
    }

    private void onStop(){
        if (playMedia.getMediaController().isShowing()){
            playMedia.getMediaController().hide();
        }

        if (playMedia.getMediaPlayer().isPlaying()){
            playMedia.getMediaPlayer().stop();
        }

        playMedia.getMediaPlayer().reset();

        if (file != null) {
            file.delete();
        }

        if (audioRecord != null) {
            audioRecord.recordStop();
        }
    }

    private Boolean getPermission(String value){
        return (ContextCompat.checkSelfPermission(context, value) == PackageManager.PERMISSION_GRANTED);
    }

    public AudioRecord getAudioRecord() {
        return audioRecord;
    }

    public PlayMedia getPlayMedia() {
        return playMedia;
    }
}
