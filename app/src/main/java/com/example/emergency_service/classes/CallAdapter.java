package com.example.emergency_service.classes;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.example.emergency_service.Play_Activity;
import com.example.emergency_service.R;
import com.example.emergency_service.interfaces.OnCompilePlay;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class CallAdapter implements OnCompilePlay {

    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private FileManager fileManager;
    private Play_Activity play_activity;
    private EmergencyMedia media;


    private static final String TAG = "myLogs";

    // имена атрибутов для Map
    final String IMAGE = "image";
    final String PHONE = "phone";
    final String TIME = "time";


    // картинки для отображения динамики
    final int isxod = android.R.drawable.sym_call_outgoing;


    public CallAdapter(ListView listView, Play_Activity play_activity) {
        this.listView = listView;
        this.play_activity = play_activity;

        fileManager = new FileManager(play_activity.getApplicationContext());

        createAdapter();
    }

    public void onDestroy(){
        stop();
    }

    private void createAdapter(){
        ArrayList<String> restemp = fileManager.getListFileCall();

        ArrayList<String> res = new ArrayList<String>();

        int count = restemp.size();

        for (int i = 0; i < count; i++){
            File file = new File(restemp.get(i));
            String name = file.getName();
            res.add(name);
        }

        String[] phones = new String[res.size()];
        phones = res.toArray(phones);

        // Упаковываем данные
        ArrayList<HashMap<String, Object>> data = new ArrayList<>(
                phones.length);

        HashMap<String, Object> map;

        for (int i = 0; i < phones.length; i++) {

            map = new HashMap<>();

            String[] str = phones[i].split(Pattern.quote("."));
            String[] dataString = str[0].split("_");

            map.put(IMAGE, isxod);


            map.put(PHONE, dataString[0]);
            map.put(TIME, dataString[1]);
            data.add(map);
        }

        // Массив имен атрибутов, из которых будут читаться данные
        String[] from = {IMAGE, PHONE, TIME};

        // Массив идентификаторов компонентов, в которые будем вставлять данные
        int[] to = {R.id.ivImg, R.id.tvPhone, R.id.tvTime};


        // создаем адаптер
        simpleAdapter = new SimpleAdapter(play_activity, data, R.layout.item,
                from, to);

        listView.setAdapter(simpleAdapter);
        //  listView.setItemsCanFocus(false);

        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (media != null){
                    if (!media.getPlayMedia().getMediaController().isShowing()){
                        media.getPlayMedia().getMediaController().show();
                    }
                } else {
                    media = new EmergencyMedia(play_activity);
                    media.setOnCompilePlay(getCallAdapter());
                    ListView listView = (ListView) play_activity.findViewById(R.id.list);
                    media.startOnlyMedia(listView, position);
                }
            }
        });
    }

    private CallAdapter getCallAdapter(){
        return this;
    }

    @Override
    public void OnCompilePlaying() {
        stop();
    }

    private void stop(){

        if (media != null) {
            if (media.getPlayMedia().isPlaying()){
                media.getPlayMedia().Stop();
            }
            media.onDestroy();
            media = null;
        }
    }
}
