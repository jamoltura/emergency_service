package com.example.emergency_service;

import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.emergency_service.classes.CallAdapter;

public class Play_Activity extends AppCompatActivity {

    private CallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        getSupportActionBar().setTitle(R.string.play_list);

        ListView listView = (ListView) findViewById(R.id.list);
        adapter = new CallAdapter(listView, this);
    }

    @Override
    protected void onDestroy() {
        adapter.onDestroy();
        super.onDestroy();
    }
}
