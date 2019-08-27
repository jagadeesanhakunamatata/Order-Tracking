package com.inrange.trackapplication.module.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.mtp.MtpDevice;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.inrange.trackapplication.Constants;
import com.inrange.trackapplication.R;
import com.inrange.trackapplication.module.menu.MenuFragment;
import com.inrange.trackapplication.module.menu.MenuHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements MainView, MenuFragment.MenuFragmentListener, View.OnClickListener, LocationListener {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final int IMPORT_SCREEN_REQ_CODE = 12;
    private MainPresenter mMainPresenter;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private MenuFragment mMenuFragment;
    private Fragment mCurrentFragment;
    private boolean isBackFromQueue;
    private ImageView mToolbarIv, mCloseIv;
    //    private GPSTracker mGpsTracker;
    private TextView mToolbarTv;
    private int mTitle;

    private FragmentListener fragmentListener;
    private Socket socket;
    private String provider;

    private LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mMainPresenter = MainPresenterImpl.getInstance(this);

        initiateDrawerLayout();

        mToolbar = (Toolbar) findViewById(R.id.activity_home_toolbar);
        mToolbarIv = (ImageView) mToolbar.findViewById(R.id.custom_toolbar_navigation_iv);
        mToolbarTv = (TextView) mToolbar.findViewById(R.id.ctvTitle);
        mCloseIv = (ImageView) mToolbar.findViewById(R.id.custom_toolbar_cancel_iv);

        mToolbarIv.setVisibility(View.VISIBLE);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

//        mGpsTracker = new GPSTracker(this, locationUpdateListener);

        mToolbarIv.setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 100, 1, (android.location.LocationListener) this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void initiateDrawerLayout() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        mMenuFragment = new MenuFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_home_navigation_fragment_container,
                mMenuFragment).commit();

    }


    @Override
    public void onMenuClick(final int menuId) {
        closeDrawer();

        final Fragment fragment = MenuHelper.getSelectedFragment(menuId);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                replaceFragment(fragment);
                updateToolbarTitle(menuId);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSameMenuSelected() {
        closeDrawer();
    }

    public void updateToolbarTitle(int title) {
        mTitle = title;
        mToolbarTv.setText(title);
    }

    public void replaceFragment(final Fragment fragment) {
        Log.d("Test Time", Calendar.getInstance().getTimeInMillis() + "");
        if (fragment != null) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mCurrentFragment = fragment;
                    int isCommitted = getSupportFragmentManager().beginTransaction().replace(R.id.activity_home_fragment_container,
                            fragment).commitAllowingStateLoss();
                    Log.d("Test Time", Calendar.getInstance().getTimeInMillis() + " " + isCommitted);
                }
            });
        }
    }

    private void closeDrawer() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mDrawerLayout.closeDrawer(Gravity.START, true);
            }
        });
    }

    private void openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custom_toolbar_navigation_iv:
                openDrawer();
                break;
            case R.id.custom_toolbar_cancel_iv:
                onCancelSelectionClick();
                break;
        }
    }

    private void onCancelSelectionClick() {
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onLocationChanged(Location location) {

        JSONObject object = new JSONObject();
        try {
            object.put("location",location);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("currentstatus",object);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}


