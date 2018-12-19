package com.tuya.api.mytiaozhuang;

import android.content.Context;
import android.content.SharedPreferences;

class CacheUtils {

    public static boolean getBoolean(Context context,String key){
         SharedPreferences sp=context.getSharedPreferences("at",Context.MODE_PRIVATE);

        return  sp.getBoolean(key,false);
    }
    public static void   setBoolean(Context context, String key){
        SharedPreferences sp=context.getSharedPreferences("at",Context.MODE_PRIVATE);
       SharedPreferences.Editor ed=sp.edit();
       ed.putBoolean(key,true);
       ed.commit();

    }
}
