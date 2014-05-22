/* @Author: Christian Joseph Dalisay
 * @Created: 5/13/14
 * @used for Referencing Preferences
 */
package com.example.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class HelperSharedPreferences {

	protected static String PREFS_NAME = "my_pref";

    public static class SharedPreferencesKeys{
        public static final String key1="key1";
        public static final String key2="key2";
    }
    
    public static void createPreferences(Context context) {
    	SharedPreferences pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    	 Editor editor = pref.edit();
    	 editor.commit();
    	 System.out.println("Preferences created");
    }
    
    
    public static void putSharedPreferencesInt(Context context, String key, int value){
        SharedPreferences preferences=context.getSharedPreferences(PREFS_NAME, 0);
        Editor edit=preferences.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static void putSharedPreferencesBoolean(Context context, String key, boolean val){
        SharedPreferences preferences=context.getSharedPreferences(PREFS_NAME, 0);
        Editor edit=preferences.edit();
        edit.putBoolean(key, val);
        edit.commit();
    }

    public static void putSharedPreferencesString(Context context, String key, String val){
        SharedPreferences preferences=context.getSharedPreferences(PREFS_NAME, 0);
        Editor edit=preferences.edit();
        edit.putString(key, val);
        edit.commit();
    }

    public static void putSharedPreferencesFloat(Context context, String key, float val){
        SharedPreferences preferences=context.getSharedPreferences(PREFS_NAME, 0);
        Editor edit=preferences.edit();
        edit.putFloat(key, val);
        edit.commit();
    }

    public static void putSharedPreferencesLong(Context context, String key, long val){
        SharedPreferences preferences=context.getSharedPreferences(PREFS_NAME, 0);
        Editor edit=preferences.edit();
        edit.putLong(key, val);
        edit.commit();
    }

    public static long getSharedPreferencesLong(Context context, String key, long _default){
        SharedPreferences preferences=context.getSharedPreferences(PREFS_NAME, 0);
        return preferences.getLong(key, _default);
    }

    public static float getSharedPreferencesFloat(Context context, String key, float _default){
        SharedPreferences preferences=context.getSharedPreferences(PREFS_NAME, 0);
        return preferences.getFloat(key, _default);
    }

    public static String getSharedPreferencesString(Context context, String key, String _default){
        SharedPreferences preferences=context.getSharedPreferences(PREFS_NAME, 0);
        return preferences.getString(key, _default);
    }

    public static int getSharedPreferencesInt(Context context, String key, int _default){
        SharedPreferences preferences=context.getSharedPreferences(PREFS_NAME, 0);
        return preferences.getInt(key, _default);
    }

    public static boolean getSharedPreferencesBoolean(Context context, String key, boolean _default){
        SharedPreferences preferences=context.getSharedPreferences(PREFS_NAME, 0);
        return preferences.getBoolean(key, _default);
    }
}