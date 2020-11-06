package com.example.emergency_service.classes;

import android.content.Context;
import android.media.MediaRecorder;
import android.util.Log;

public class AudioRecord {

    private static final String TAG = "myLogs";

    private MediaRecorder mediaRecorder;
    public Boolean IsRecord;
    private Context context;

    public AudioRecord(Context context) {
        this.context = context;
        IsRecord = false;
    }

    public void recordStart(String value) {
        try {
            FileManager fileManager = new FileManager(context);
            String fileName = fileManager.getNowFile(value);

            mediaRecorder = new MediaRecorder();
            audioPrepared(fileName);
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public void recordStop() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            IsRecord = true;
            context = null;
        }
    }

    private void audioPrepared(String value){
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(value);
    }

    public MediaRecorder getMediaRecorder() {
        return mediaRecorder;
    }

    public void setMediaRecorder(MediaRecorder mediaRecorder) {
        this.mediaRecorder = mediaRecorder;
    }

    public Boolean getRecord() {
        return IsRecord;
    }

    public void setRecord(Boolean record) {
        IsRecord = record;
    }
}
