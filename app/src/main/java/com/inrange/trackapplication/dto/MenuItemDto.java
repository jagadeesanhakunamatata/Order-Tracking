package com.inrange.trackapplication.dto;

import android.graphics.drawable.Drawable;

/**
 * Created by hms on 10/2/17.
 */

public class MenuItemDto {

    private int menuId;
    private String title;
    private Drawable selectedDrawable;
    private boolean isSelected;

    public MenuItemDto(int menuId, String title, Drawable selectedDrawable, boolean isSelected) {
        this.menuId = menuId;
        this.title = title;
        this.selectedDrawable = selectedDrawable;
        this.isSelected = isSelected;
    }

    public MenuItemDto(int menuFooter) {
        menuId = menuFooter;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getSelectedDrawable() {
        return selectedDrawable;
    }

    public void setSelectedDrawable(Drawable selectedDrawable) {
        this.selectedDrawable = selectedDrawable;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
