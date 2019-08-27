package com.inrange.trackapplication.widgets;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.inrange.trackapplication.R;
import com.wang.avi.AVLoadingIndicatorView;


public class CustomProgressbar extends Dialog {

    private static CustomProgressbar customProgressbar;

    private String mMessage;
    private AVLoadingIndicatorView mProgressLoader;

    public CustomProgressbar(Context context) {
        super(context);
        mMessage = null;
        createProgressBar(context);
    }

    public static CustomProgressbar getProgressbar(Context context) {
        if (null == customProgressbar) {
            customProgressbar = new CustomProgressbar(context);
        }
        return customProgressbar;
    }

    private void createProgressBar(Context context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getAppProgressbar(context));
        Window window = getWindow();
        window.setDimAmount(0f);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    private View getAppProgressbar(Context context) {

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setBackgroundResource(android.R.color.transparent);
        mProgressLoader = (AVLoadingIndicatorView) LayoutInflater.from(context).inflate(R.layout.layout_progressbar, null);
        linearLayout.addView(mProgressLoader);

        return linearLayout;
    }

    public static boolean isProgessBarShowing() {
        return customProgressbar != null && customProgressbar.isShowing();
    }

    public AVLoadingIndicatorView getLoader() {
        return mProgressLoader;
    }

    @Override
    public void dismiss() {
        super.dismiss();
//        customProgressbar.dismiss();
        customProgressbar = null;
    }
}