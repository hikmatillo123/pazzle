package com.hikmatillo.game;

import android.content.Context;
import android.content.SharedPreferences;

public class AppCashe {
    private  static final String TIME_KEY ="time_key";
    private  static final String COUNT_KEY ="count_key";
    private  static AppCashe cashe;
    private static SharedPreferences preferences;

    private AppCashe(Context context){
        preferences = context.getSharedPreferences("Puzzle_15",Context.MODE_PRIVATE);
    }
    public static void init(Context context) {
        if (cashe == null) {
            cashe = new AppCashe(context);

        }
    }


    public static AppCashe getObject() {
        return cashe;
    }

    public void saveTime(String  timeCount){
        preferences.edit().putString(TIME_KEY,timeCount).apply();
    }
       public String  getTime(){
        return preferences.getString(TIME_KEY,"0");
       }
       public void saveStep(Integer saveStep){
        preferences.edit().putInt(COUNT_KEY,saveStep).apply();
       }
      public Integer getStep(){
        return preferences.getInt(COUNT_KEY,0);
       }

}











