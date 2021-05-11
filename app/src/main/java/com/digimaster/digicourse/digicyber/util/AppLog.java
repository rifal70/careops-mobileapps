package com.digimaster.digicourse.digicyber.util;

import android.util.Log;

//import com.crashlytics.android.Crashlytics;


/**
 * @author sudarsono
 */
public class AppLog {

    public static void e(String tag, String message) {
//        if(BuildConfig.DEBUG) {
            Log.e(tag, message);
//        } else {
//            Crashlytics.log(tag + ": " + message);
//        }
    }

    public static void i(String tag, String message) {
//        if(BuildConfig.DEBUG) {
            Log.i(tag, message);
//        }
    }

    public static void d(String tag, String message) {
//        if(BuildConfig.DEBUG) {
            Log.d(tag, message);
//        }
    }

    public static void w(String tag, String message) {
//        if(BuildConfig.DEBUG) {
            Log.w(tag, message);
//        } else {
//            Crashlytics.log(message);
//        }
    }

    public static void v(String tag, String message) {
//        if(BuildConfig.DEBUG) {
            Log.v(tag, message);
//        }
    }

    public static void printStackTrace(Exception e) {
//        if(BuildConfig.DEBUG) {
            e.printStackTrace();
//        } else {
//            Crashlytics.logException(e);
//        }
    }
}
