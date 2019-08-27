package com.inrange.trackapplication.module.home;


import android.location.Location;

public interface MainPresenter {


    void onGpsTrackerConnected();

    void locationUpdated(Location location);

}
