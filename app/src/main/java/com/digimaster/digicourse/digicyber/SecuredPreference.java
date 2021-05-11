package com.digimaster.digicourse.digicyber;

import android.content.Context;
import android.content.SharedPreferences;

import com.digimaster.digicourse.digicyber.util.AppException;
import com.digimaster.digicourse.digicyber.util.Encryption;

/**
 * Created by Teke on 05/02/2018.
 */

public class SecuredPreference {
    private Context context;
    private String name;

    public SecuredPreference(Context context, String preferenceName) {
        this.context = context;
        this.name = preferenceName;
    }

    private String get(String key) throws AppException {
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(name,
                    Context.MODE_PRIVATE);
            String encryptedValue = sharedPref.getString(key, null);
            Encryption ps = new Encryption();
            return ps.decrypt(encryptedValue);
        } catch (Exception e) {
            throw new AppException("PREFERENCE", e.getMessage());
        }
    }

    public String getString(String key, String defaultValue) throws AppException {
        String value = get(key);
        if(value.isEmpty()) {
            value = defaultValue;
        }
        return value;
    }

    public double getDouble(String key, double defaultValue) throws AppException {
        String value = get(key);
        double result = defaultValue;
        if(!value.isEmpty()) {
            result = Double.parseDouble(value);
        }
        return result;
    }

    public long getLong(String key, long defaultValue) throws AppException {
        String value = get(key);
        long result = defaultValue;
        if(!value.isEmpty()) {
            result = Long.parseLong(value);
        }
        return result;
    }

    public float getFloat(String key, float defaultValue) throws AppException {
        String value = get(key);
        float result = defaultValue;
        if(!value.isEmpty()) {
            result = Float.parseFloat(value);
        }
        return result;
    }

    public int getInt(String key, int defaultValue) throws AppException {
        String value = get(key);
        int result = defaultValue;
        if(!value.isEmpty()) {
            result = Integer.parseInt(value);
        }
        return result;
    }

    public boolean getBoolean(String key, boolean defaultValue) throws AppException {
        String value = get(key);
        boolean result = defaultValue;
        if(!value.isEmpty()) {
            result = value.equals("1");
        }
        return result;
    }


    public void put(String key, String value) throws AppException {
        try {
            Encryption ps = new Encryption();
            String encryptedValue = ps.encrypt(value);
            SharedPreferences sharedPref = context.getSharedPreferences(name,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(key, encryptedValue);
            editor.apply();
        } catch (Exception e) {
            throw new AppException("PREFERENCE", e.getMessage());
        }
    }

    public void put(String key, int value) throws AppException {
        put(key, String.valueOf(value));
    }

    public void put(String key, boolean value) throws AppException{
        put(key, value ? "1" : "0");
    }

    public void put(String key, long value) throws AppException{
        put(key, String.valueOf(value));
    }

    public void put(String key, double value) throws AppException{
        put(key, String.valueOf(value));
    }

    public void put(String key, float value) throws AppException{
        put(key, String.valueOf(value));
    }

    public void put(String[] key, String[] value) throws AppException{
        put(key, value);
    }

    public void clear() {
        SharedPreferences sharedPref = context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }

    public SharedPreferences getPreference() {
        return context.getSharedPreferences(name,
                Context.MODE_PRIVATE);
    }

}
