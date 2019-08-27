package com.inrange.trackapplication.retrofit;

import android.content.Context;

import com.inrange.trackapplication.CodeSnippet;
import com.inrange.trackapplication.Constants;
import com.inrange.trackapplication.widgets.CustomProgressbar;

import java.util.Calendar;

public class Utils {

    public static void showProgressbar(Context context) {

        try {
            CustomProgressbar progressbar = CustomProgressbar.getProgressbar(context);
            progressbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissProgressbar(Context context) {

        if (CustomProgressbar.isProgessBarShowing()) {
            CustomProgressbar.getProgressbar(context).dismiss();
        }
    }

}
