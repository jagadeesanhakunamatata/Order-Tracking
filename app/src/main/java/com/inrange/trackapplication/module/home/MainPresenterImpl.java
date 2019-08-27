package com.inrange.trackapplication.module.home;

import android.location.Location;


public class MainPresenterImpl implements MainPresenter, MainModelImpl.MainModelListener {

    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private MainView mMainView;
    private MainModel mMainModel;


    private MainPresenterImpl(MainView mainview) {
        mMainView = mainview;
        mMainModel = MainModelImpl.getInstance(this);
    }

    public static MainPresenter getInstance(MainView mainview) {
        return new MainPresenterImpl(mainview);
    }

    @Override
    public void onGpsTrackerConnected() {

    }

    @Override
    public void locationUpdated(Location location) {
        mMainModel.onSaveUpdatedLocation(location);
    }
}
