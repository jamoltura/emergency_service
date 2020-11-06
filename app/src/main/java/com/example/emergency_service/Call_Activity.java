package com.example.emergency_service;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.emergency_service.classes.EmergencyMedia;
import com.example.emergency_service.classes.FileManager;
import com.example.emergency_service.interfaces.OnCompilePlay;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Call_Activity extends AppCompatActivity implements OnCompilePlay {

    private static final String TAG = "myLogs";

    private EmergencyMedia media;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        getSupportActionBar().hide();

        String value = getIntent().getStringExtra("msg");

        TextView textView_name = (TextView) findViewById(R.id.text_name);

        ImageView img_logo = (ImageView) findViewById(R.id.img_logo);

        switch (value){
            case "101": textView_name.setText(R.string.servis_101);
                img_logo.setImageResource(R.drawable.pojarniy);
                break;
            case "102": textView_name.setText(R.string.servis_102);
                img_logo.setImageResource(R.drawable.mvd);
                break;
            case "103": textView_name.setText(R.string.servis_103);
                img_logo.setImageResource(R.drawable.med);
                break;
            case "104": textView_name.setText(R.string.servis_104);
                img_logo.setImageResource(R.drawable.gaz);
                break;
            case "1050": textView_name.setText(R.string.servis_1050);
                img_logo.setImageResource(R.drawable.fvv);
            break;
            default: textView_name.setText(R.string.servis_worn);
                img_logo.setImageResource(R.drawable.worn);
        }

        TextView textView = (TextView) findViewById(R.id.text_number);
        textView.setText(value);

        InputStream inputStream = null;

        if (getValidNumber(value)){
            inputStream = getResources().openRawResource(R.raw.botnew);
            File file = FileManager.getTempFile(getApplicationContext());

            try {
                copy(inputStream, file);
            }catch (IOException e){
                Toast.makeText(this, "Ошибка!!!", Toast.LENGTH_SHORT).show();
            }

            media = new EmergencyMedia(getApplicationContext());
            media.setOnCompilePlay(this);
            media.startMediaAndRecord(file, value);
        }else {
            inputStream = getResources().openRawResource(R.raw.wrong_number);
            File file = FileManager.getTempFile(getApplicationContext());

            try {
                copy(inputStream, file);
            }catch (IOException e){
                Toast.makeText(this, "Ошибка!!!", Toast.LENGTH_SHORT).show();
            }

            media = new EmergencyMedia(getApplicationContext());
            media.setOnCompilePlay(this);
            media.startMediaAndRecord(file, value);
        }

        ImageView img_call_stop = (ImageView) findViewById(R.id.img_call_stop);
        img_call_stop.setOnClickListener(click_btn);

        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            try {
                setTexttime();
            } catch (ParseException e) {
                Log.d(TAG, e.getMessage());
            }
        }
    };

    private void setTexttime() throws ParseException {
        TextView textView = (TextView) findViewById(R.id.text_time);

        SimpleDateFormat format = new SimpleDateFormat("mm:ss");

        Calendar cal_s = Calendar.getInstance();

        if (textView.getText().toString().isEmpty()) {
            Date date = new Date();
            date.setTime(0);
            cal_s.setTime(Objects.requireNonNull(format.parse(format.format(date))));
            textView.setText(format.format(cal_s.getTime()));
        }else {
            String value = textView.getText().toString();
            cal_s.setTime(Objects.requireNonNull(format.parse(value)));
            cal_s.roll(Calendar.SECOND, true);
            textView.setText(format.format(cal_s.getTime()));
        }
    }

    private boolean getValidNumber(String value){
        return value.equals("101") || value.equals("102") || value.equals("103") || value.equals("104") || value.equals("1050");
    }

    private void copy(InputStream in, File target) throws IOException {

        OutputStream out = new FileOutputStream(target);

        byte[] buf = new byte[1024];
        int len;

        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.flush();
        out.close();
        in.close();
    }

    @Override
    public void OnCompilePlaying() {
        finish();
    }

    @Override
    public void onBackPressed() {
        media.onDestroy();
        timer.cancel();
        super.onBackPressed();
    }

    View.OnClickListener click_btn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            media.onDestroy();
            finish();
        }
    };
}
