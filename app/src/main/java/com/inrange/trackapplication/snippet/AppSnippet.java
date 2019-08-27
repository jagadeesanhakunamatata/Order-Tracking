package com.inrange.trackapplication.snippet;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class AppSnippet {
    /**
     * @return True if walkthrough is completed, else return False
     */
    public static boolean isWalkThroughCompleted() {
        return false;
    }

    /**
     * @return True if profile setup is completed, else return False
     */
    public static boolean isProfileSetupCompleted() {
        return false;
    }

    /**
     * @return True if login is completed, else return False
     */
    public static boolean isLoginCompleted() {
        return false;
    }

    /**
     * @return True if otp verification is completed, else return False
     */
    public static boolean isOtpVerificationCompleted() {
        return false;
    }

    public static LayoutInflater getLayoutInflater(Context context) {
        return LayoutInflater.from(context);
    }

    public static void setTheme(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(getThemeFlags());
    }

    public static int getThemeFlags() {
        return
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    }

    public static void hideBottomNavigation(Activity activity) {
        if (activity != null) {
            final View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
    }


    public static String replaceEmpty(String value) {
        return value == null || value.equals("") ? "UnKnown" : value;
    }

    public static void generateNoteOnSD(String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "OnewayDriver");
            if (!root.exists()) {
                root.mkdirs();
            }
            File out;
            OutputStreamWriter outStreamWriter = null;
            FileOutputStream outStream = null;

            out = new File(root, sFileName);

            if (out.exists() == false) {
                out.createNewFile();
            }

            outStream = new FileOutputStream(out, true);
            outStreamWriter = new OutputStreamWriter(outStream);

            outStreamWriter.append(sBody + "----" + CodeSnippet.getDateStringFromLong(System.currentTimeMillis(), CustomDateFormats.TEMP_DATE,
                    CustomDateFormats.INDIA_TIMEZONE));
            outStreamWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getNoteOnSD(String sFileName) {
        String text = "";
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "OnewayDriver");
            if (!root.exists()) {
                root.mkdirs();
            }
            File out;
            out = new File(root, sFileName);

            if (out.exists()) {

                try {
                    BufferedReader br = new BufferedReader(new FileReader(out));
                    String line;

                    while ((line = br.readLine()) != null) {
                        text += line;
                        text += '\n';
                    }
                    br.close();
                    out.delete();
                } catch (IOException e) {
                    //You'll need to add proper error handling here
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }


    public static void applyDateTime(String date, TextView dateView, TextView timeView) {

        timeView.setText(CodeSnippet.formatDateString(date, CustomDateFormats.SERVER_DATE_FORMAT,
                CustomDateFormats.HH_MM_AA,
                CustomDateFormats.TIME_ZONE_GMT, CustomDateFormats.INDIA_TIMEZONE));

        dateView.setText(CodeSnippet.formatDateString(date, CustomDateFormats.SERVER_DATE_FORMAT,
                CustomDateFormats.MMM_DD,
                CustomDateFormats.TIME_ZONE_GMT, CustomDateFormats.INDIA_TIMEZONE));
    }

    public static void hideOnlyBottomNavigation(Activity activity) {
        if (activity != null) {
            final View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(/*View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | */View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    public static void disableOnClick(final View view) {
        view.setEnabled(false);
        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                view.setEnabled(true);
            }
        }.start();
    }


    private static String getDeviceId(Context context) {
        String deviceId;
        deviceId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return deviceId;
    }

    private static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }
}