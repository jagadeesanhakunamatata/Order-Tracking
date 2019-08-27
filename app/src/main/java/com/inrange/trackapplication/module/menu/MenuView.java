package com.inrange.trackapplication.module.menu;

import android.view.View;

import com.inrange.trackapplication.dto.MenuItemDto;

import java.util.List;

/**
 * Created by hms on 10/2/17.
 */

interface MenuView {
    void initMenuAdapter(List<MenuItemDto> menuList);

    void setInitialSelection(int menuId);
}
