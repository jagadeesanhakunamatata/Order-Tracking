package com.inrange.trackapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class InRangeApplication  extends Application {


    private static InRangeApplication performanceHubApplication;

    private SharedPreferences sharedPreferences;
    private static final String SHARED_FILE = "inrange";

    @Override
    public void onCreate() {
        super.onCreate();
        initApp();
    }

    public static InRangeApplication getApplication() {
        return performanceHubApplication;
    }


    private void initApp() {

        performanceHubApplication = InRangeApplication.this;
        sharedPreferences = getSharedPreferences(SHARED_FILE, Context.MODE_PRIVATE);

//        Fabric.with(getApplicationContext(), new Crashlytics());
    }

    private void disableReportOnDebugMode() {

        /*// Set up Crashlytics, disabled for debug builds
        Crashlytics crashlytics = new Crashlytics.Builder().core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build();
        // Initialize Fabric with the debug-disabled crashlytics.
        Fabric.with(this, crashlytics);*/
    }

    public void setSharedIntData(String key, int value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getSharedIntData(String key) {
        return getSharedPreferences().getInt(key, 0);
    }

    public void setSharedStringData(String key, String value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(key, value);
        editor.commit();
    }

    public String getSharedStringData(String key) {
        return getSharedPreferences().getString(key, null);
    }

    public long getSharedLongData(String key) {
        return getSharedPreferences().getLong(key, 0);
    }

    public void setSharedLongData(String key, long value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putLong(key, value);
        editor.commit();
    }

    public WindowManager getWindowManagerData(Activity activity) {
        return activity.getWindowManager();
    }

    public void setSharedBooleanData(String key, boolean value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getSharedBooleanData(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    private SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

    public ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public LocationManager getLocationManager() {
        return (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * Checking the internet connectivity
     *
     * @return true if the connection is available otherwise false
     */
    public boolean hasNetworkConnection() {

        ConnectivityManager cm = getConnectivityManager();
        boolean valid = false;

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnectedOrConnecting()) {
            valid = true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnectedOrConnecting()) {
            valid = true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            valid = true;
        }

        return valid;
    }

//    public boolean hasFingerPrintFeature() {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(getApplicationContext());
//            return fingerprintManager.isHardwareDetected();
//        }
//        return false;
//    }

    public boolean hasGpsConnection() {
        return getLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void clearAllData() {
        getEditor().clear().commit();
    }

    public TelephonyManager getTelephonyManager() {
        return (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
    }

    public WindowManager getWindowManager() {
        return getWindowManager();
    }

    private class SystemTimer extends Timer {

        private long mCurrentSystemTime;

        public SystemTimer(long currentSystemTime) {
            mCurrentSystemTime = currentSystemTime;
        }

        public void schedule() {
            super.schedule(new TimerTask() {
                @Override
                public void run() {
                    mCurrentSystemTime++;
                }
            }, 0, 1);

        }

        public void setTime(long currentSystemTime) {
            mCurrentSystemTime = currentSystemTime;
        }

        public long getSystemTime() {
            return mCurrentSystemTime;
        }

    }

    private SystemTimer mSystemTimer;

    public void setSystemTime(String serverTime) {
        long millis = System.currentTimeMillis();
//        long serverTimeInMs = CodeSnippet.getDateFromDateString(serverTime,
//                Constants.DateFormat.SERVER_FORMAT, Constants.DateFormat.GMT).getTime();
        if (null == mSystemTimer) {
            mSystemTimer = new SystemTimer(millis);
            mSystemTimer.schedule();
        } else {
            mSystemTimer.setTime(millis);
        }
    }

}
