package com.example.emergency_service;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Call_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        String value = getIntent().getStringExtra("msg");

        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
    }
}
