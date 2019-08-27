package com.inrange.trackapplication.module.menu;

import android.app.Activity;
import android.support.v4.content.ContextCompat;

import com.inrange.trackapplication.Constants;
import com.inrange.trackapplication.R;
import com.inrange.trackapplication.dto.MenuItemDto;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sathish on 27/6/18.
 */

class MenuModelImpl implements MenuModel, Constants.MenuItems {

    private MenuModelListener mMenuModelListener;

    private MenuModelImpl(MenuModelListener menuModelListener) {
        mMenuModelListener = menuModelListener;
    }

    public static MenuModel getInstance(MenuModelListener menuModelListener) {
        return new MenuModelImpl(menuModelListener);
    }

    @Override
    public List<MenuItemDto> getMenuList() {

        Activity activity = mMenuModelListener.getActivity();
        int[] menuTitleList = allMenus;

        List<MenuItemDto> menuItemDtoList = new ArrayList<>();

        for (int i = 0; i < menuTitleList.length; i++) {
            int menuItem = menuTitleList[i];
            int icon = -1; // default
            String name = "";
            switch (menuItem) {
                case ORDERS:
                        icon = R.drawable.ic_orders;
                    name = "Orders";
                    break;
                case ORDERS_MAP_VIEW:
                        icon = android.R.drawable.ic_dialog_map;
                    name = "Orders in Map View";
                    break;
                case SETTINGS:
                    icon = R.drawable.settings_us;
                    name = "Settings";
                    break;
                case ABOUT:
                    icon = R.drawable.about_us;
                    name = "About us";
                    break;
            }
            if (icon != -1) {
                menuItemDtoList.add(new MenuItemDto(menuItem, name, ContextCompat.getDrawable(activity, icon), i == 0));
            }
        }
        return menuItemDtoList;
    }

    interface MenuModelListener {
        Activity getActivity();
    }
}
