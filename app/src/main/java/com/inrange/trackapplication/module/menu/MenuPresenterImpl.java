package com.inrange.trackapplication.module.menu;

import android.app.Activity;
import com.inrange.trackapplication.Constants;


class MenuPresenterImpl implements MenuPresenter, MenuModelImpl.MenuModelListener {

    private static Activity mActivity;
    private MenuModel mMenuModel;
    private MenuView mMenuView;

    private MenuPresenterImpl(MenuView menuView) {
        mMenuView = menuView;
        mMenuModel = MenuModelImpl.getInstance(this);
    }

    public static MenuPresenter getInstance(MenuView menuView, Activity activity) {
        mActivity = activity;
        return new MenuPresenterImpl(menuView);
    }

    @Override
    public void onCreateMenu() {
        mMenuView.initMenuAdapter(mMenuModel.getMenuList());
//        if (DataHandler.getInstance().isPhoto()) {
//            mMenuView.setInitialSelection(Constants.MenuItems.PHOTOS);
//        } else if (DataHandler.getInstance().isVideo()) {
//            mMenuView.setInitialSelection(Constants.MenuItems.VIDEOS);
//        } else {
//            mMenuView.setInitialSelection(Constants.MenuItems.REPORTER);
//        }
    }

    @Override
    public Activity getActivity() {
        return mActivity;
    }
}
