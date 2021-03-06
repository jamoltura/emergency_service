package com.example.emergency_service;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.Layout;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.emergency_service.classes.EmergencyMedia;
import com.example.emergency_service.classes.FileManager;

import java.io.*;

public class MainActivity extends AppCompatActivity {

    final String recordAudio = Manifest.permission.RECORD_AUDIO;
    final int MY_RECORDAUDIO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!getPermission(recordAudio)){
            RequestPermission(recordAudio, MY_RECORDAUDIO);
        }

        ImageView img_one = (ImageView) findViewById(R.id.img_one);
        img_one.setOnClickListener(click_one);

        ImageView img_two = (ImageView) findViewById(R.id.img_two);
        img_two.setOnClickListener(click_two);

        ImageView img_three = (ImageView) findViewById(R.id.img_three);
        img_three.setOnClickListener(click_three);

        ImageView img_four = (ImageView) findViewById(R.id.img_four);
        img_four.setOnClickListener(click_four);

        ImageView img_five = (ImageView) findViewById(R.id.img_five);
        img_five.setOnClickListener(click_five);

        ImageView img_six = (ImageView) findViewById(R.id.img_six);
        img_six.setOnClickListener(click_six);

        ImageView img_seven = (ImageView) findViewById(R.id.img_seven);
        img_seven.setOnClickListener(click_seven);

        ImageView img_eight = (ImageView) findViewById(R.id.img_eight);
        img_eight.setOnClickListener(click_eight);

        ImageView img_nine = (ImageView) findViewById(R.id.img_nine);
        img_nine.setOnClickListener(click_nine);

        ImageView img_zero = (ImageView) findViewById(R.id.img_zero);
        img_zero.setOnClickListener(click_zero);

        ImageView img_zvezd = (ImageView) findViewById(R.id.img_zvezd);
        img_zvezd.setOnClickListener(click_zvesd);

        ImageView img_reshet = (ImageView) findViewById(R.id.img_reshet);
        img_reshet.setOnClickListener(click_reshet);

        ImageView img_call = (ImageView) findViewById(R.id.img_call);
        img_call.setOnClickListener(click_call);

        final ImageButton img_backspace = (ImageButton) findViewById(R.id.img_backspace);
        img_backspace.setOnClickListener(click_backspaspce);

        img_backspace.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                img_backspace.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int width = img_backspace.getWidth();

                TextView textView = (TextView) findViewById(R.id.text_call);
                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) textView.getLayoutParams();
                params1.setMargins(width, 0,0,0);
                textView.setLayoutParams(params1);
            }
        });
    }

    View.OnClickListener click_one = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNumber("1");
        }
    };

    View.OnClickListener click_two = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNumber("2");
        }
    };

    View.OnClickListener click_three = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNumber("3");
        }
    };

    View.OnClickListener click_four = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNumber("4");
        }
    };

    View.OnClickListener click_five = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNumber("5");
        }
    };

    View.OnClickListener click_six = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNumber("6");
        }
    };

    View.OnClickListener click_seven = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNumber("7");
        }
    };

    View.OnClickListener click_eight = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNumber("8");
        }
    };

    View.OnClickListener click_nine = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNumber("9");
        }
    };

    View.OnClickListener click_zero = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNumber("0");
        }
    };

    View.OnClickListener click_zvesd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNumber("*");
        }
    };

    View.OnClickListener click_reshet = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addNumber("#");
        }
    };


    View.OnClickListener click_call = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!getValidNumber()){
                Intent intent = new Intent(v.getContext(), Call_Activity.class);
                intent.putExtra("msg", getNumber());
                startActivity(intent);
                TextView text_call = (TextView) findViewById(R.id.text_call);
                text_call.setText("");
            }
        }
    };

    View.OnClickListener click_backspaspce = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            subNumber();
        }
    };

    private void addNumber(String value){
        TextView text_call = (TextView) findViewById(R.id.text_call);
        String valueEnd = text_call.getText().toString().concat(value);
        text_call.setText(valueEnd);
    }

    private void subNumber(){
        TextView text_call = (TextView) findViewById(R.id.text_call);
        String value = text_call.getText().toString();
        int count = value.length();
        if (count > 0){
           String valueEnd = value.substring(0, count - 1);
           text_call.setText(valueEnd);
        }
    }

    private boolean getValidNumber(){
        String value = getNumber();
        return value.isEmpty();
    }

    private String getNumber(){
        TextView text_call = (TextView) findViewById(R.id.text_call);
        return text_call.getText().toString();
    }

    private Boolean getPermission(String value){
        return (ContextCompat.checkSelfPermission(this, value) == PackageManager.PERMISSION_GRANTED);
    }

    private void RequestPermission(String value, int result){
        ActivityCompat.requestPermissions(this, new String[]{value}, result);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_RECORDAUDIO){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (!getPermission(recordAudio)){
                    RequestPermission(recordAudio, MY_RECORDAUDIO);
                }
            }
        }
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:{
                Intent intent = new Intent(getApplicationContext(), Play_Activity.class);
                startActivity(intent);
            }
        }
        return true;
    }
}
