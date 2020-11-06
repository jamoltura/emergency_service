package com.example.emergency_service.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FileManager {

    private static final String TAG = "myLogs";

    private Context context;

    // Поный пут к приложении ////////////
    private String pathApp;

    // Полный пут к папкам ////////////////
    private String pathCallAudio;

    //  Свойства /////////////////////////
    private ArrayList<String> listFileCall= new ArrayList<String>();

    // Имя удаленый файл /////////////////
    private String nameDelFile;


    public FileManager(Context context) {
        this.context = context;
        init();
    }

    public void init(){

        setPathApp(context.getFilesDir().getAbsolutePath());

        pathCallAudio = _addDirectory(getPathApp() + getCallAudio());

        setNameDelFile("");

        if (IsDirectory(getPathCallAudio())) {
            listFileCall();
        }
    }

    private String _addDirectory(String value) throws SecurityException{
        if (IsDirectory(value)) {
            return value;
        }
        return "";
    }

    private Boolean IsDirectory(String value) throws SecurityException{
        File theDirTemp = new File(value);
        if (!theDirTemp.exists()) {
            theDirTemp.mkdir();
        }
        return theDirTemp.exists();
    }

    /////////////////
    //  File Call
    /////////////////

    private void listFileCall() {

        listFileCall.clear();

        int count = countFileCall();

        for (int i = 0; count > i; i++) {
            listFileCall.add(get_FilePath(getPathCallAudio(), i));
        }
    }

    public int countFileCall(){
        return countFile(getPathCallAudio());
    }

    /////////////////////////// private functions ///////////////////////////

    private String get_FilePath(String put, int ind) {

        File file = new File(put);
        File[] files = file.listFiles();

        try {
            if (files[ind].isFile()) {
                return files[ind].getAbsolutePath();
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception error : " + e.getMessage());
        }
        return null;
    }

    private String get_FileName(String put, int ind) {

        File file = new File(put);
        File[] files = file.listFiles();

        try {
            if (files[ind].isFile()) {
                return files[ind].getName();
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception error : " + e.getMessage());
        }
        return null;
    }

    public boolean delFile(int index){

        int count = countFileCall();

        if (count > 0){
            File file = new File(getListFileCall().get(index));
            if (file.exists()){
                setNameDelFile(file.getName());
                return file.delete();
            }
        }

        return false;
    }

    public int delAllFile(){

        int count = countFileCall();

        int count_del = 0;

        if (count > 0){
            for (int i = 0; i < count; i++) {
                File file = new File(getListFileCall().get(i));
                if (file.exists()) {
                    if(file.delete()){
                        count_del++;
                    }
                }
            }
        }
        return count_del;
    }

    private int countFile(String value){
        File file = new File(value);
        File[] files = file.listFiles();
        return files.length;
    }

    public String getNowFile(String value){

        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy k:mm:ss");

        Date date = Calendar.getInstance().getTime();
        String date_str = format.format(date);

        return getPathCallAudio()+ "/" + value + "_" + date_str + ".3gp";
    }

    public static File getTempFile(Context context){
        return new File(context.getCacheDir(), "temp.3gp");
    }

    public String getPathApp() {
        return pathApp;
    }

    public void setPathApp(String pathApp) {
        this.pathApp = pathApp;
    }

    public String getPathCallAudio() {
        return pathCallAudio;
    }

    public void setPathCallAudio(String pathCallAudio) {
        this.pathCallAudio = pathCallAudio;
    }

    public ArrayList<String> getListFileCall() {
        return listFileCall;
    }

    public void setListFileCall(ArrayList<String> listFileCall) {
        this.listFileCall = listFileCall;
    }

    public String getCallAudio() {
        return "/CallAudio";
    }

    public String getNameDelFile() {
        return nameDelFile;
    }

    public void setNameDelFile(String nameDelFile) {
        this.nameDelFile = nameDelFile;
    }
}
