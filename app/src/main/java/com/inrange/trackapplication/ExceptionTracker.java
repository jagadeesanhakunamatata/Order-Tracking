package com.inrange.trackapplication;

//import com.crashlytics.android.Crashlytics;

/**
 * Created by hmspl on 7/1/15.
 */
public class ExceptionTracker {

    public static void track(Exception exception) {
//        Crashlytics.logException(exception);
        exception.printStackTrace();
    }

    public static void track(String message) {
        //Crashlytics.log(message);
    }
}