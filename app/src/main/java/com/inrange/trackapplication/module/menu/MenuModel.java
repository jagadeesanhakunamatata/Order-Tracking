package com.inrange.trackapplication.module.menu;


import com.inrange.trackapplication.dto.MenuItemDto;

import java.util.List;

/**
 * Created by hms on 10/2/17.
 */

interface MenuModel {

    List<MenuItemDto> getMenuList();
}
