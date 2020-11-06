package com.example.emergency_service;

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

public class Call_Activity extends AppCompatActivity implements OnCompilePlay {

    private EmergencyMedia media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        getSupportActionBar().hide();

        String value = getIntent().getStringExtra("msg");

        TextView textView_name = (TextView) findViewById(R.id.text_name);

        switch (value){
            case "101": textView_name.setText(R.string.servis_101);
                break;
            case "102": textView_name.setText(R.string.servis_102);
                break;
            case "103": textView_name.setText(R.string.servis_103);
                break;
            case "104": textView_name.setText(R.string.servis_104);
                break;
            case "1050": textView_name.setText(R.string.servis_1050);
                break;
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
