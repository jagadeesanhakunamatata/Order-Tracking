package com.inrange.trackapplication.module.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.inrange.trackapplication.Constants;


public class MenuHelper implements Constants.MenuItems {

    public static Fragment getSelectedFragment(int menuId) {

        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (menuId) {
            case ORDERS:
//                fragment = new HomeFragment();
//                bundle.putString(Constants.BundleKey.IS_TO_SHOW, Constants.FileType.FILE_TYPE_IMAGE);
//                fragment.setArguments(bundle);
                break;

            case ORDERS_MAP_VIEW:
                break;

            case SETTINGS:
                break;

            case ABOUT:
                break;
            default:
                break;
        }
        return fragment;
    }
}