package com.inrange.trackapplication.snippet;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

public class PermissionHandler {
    public static PermissionHandler sPermissionHandler = new PermissionHandler();
    private PermissionHandler.PermissionHandleListener mPermissionHandleListener;

    public PermissionHandler() {
    }

    public static PermissionHandler getInstance(PermissionHandler.PermissionHandleListener permissionHandleListener) {
        sPermissionHandler.mPermissionHandleListener = permissionHandleListener;
        return sPermissionHandler;
    }

    public void checkPermission(final Activity activity, final String permission, final int requestCode, String message) {
        if (Build.VERSION.SDK_INT <= 22) {
            this.mPermissionHandleListener.onPermissionGranted(requestCode);
        } else if (ContextCompat.checkSelfPermission(activity, permission) != 0) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                AlertDialog mPermissionsAlertDialog = (new AlertDialog.Builder(activity)).setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PermissionHandler.this.requestPermission(activity, permission, requestCode);
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
                mPermissionsAlertDialog.show();
            } else {
                this.requestPermission(activity, permission, requestCode);
            }
        } else {
            this.mPermissionHandleListener.onPermissionGranted(requestCode);
        }
    }

    public void checkMultiplePermission(final Activity activity, final String[] permission, final int requestCode, String message) {
        if (Build.VERSION.SDK_INT <= 22) {
            this.mPermissionHandleListener.onPermissionGranted(requestCode);
        } else {
            for(final String aPermission : permission){
                if (ContextCompat.checkSelfPermission(activity, aPermission) != 0) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, aPermission)) {
                        AlertDialog mPermissionsAlertDialog = (new AlertDialog.Builder(activity)).setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                PermissionHandler.this.requestMultiplePermission(activity, permission, requestCode);
                            }
                        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).create();
                        mPermissionsAlertDialog.show();
                    } else {
                        this.requestMultiplePermission(activity, permission, requestCode);
                    }
                } else {
                    this.mPermissionHandleListener.onPermissionGranted(requestCode);
                }
            }
        }


    }

    private void requestMultiplePermission(Activity activity, String[] permission, int requestCode) {
        ActivityCompat.requestPermissions(activity, permission, requestCode);
    }

    private void requestPermission(Activity activity, String permission, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == 0) {
            this.mPermissionHandleListener.onPermissionGranted(requestCode);
        } else {
            this.mPermissionHandleListener.onPermissionDenied(requestCode);
        }

    }

    public interface PermissionHandleListener {
        void onPermissionGranted(int requestCode);

        void onPermissionDenied(int requestCode);
    }
}

