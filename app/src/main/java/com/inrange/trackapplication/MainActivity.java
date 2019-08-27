package com.inrange.trackapplication;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.support.v7.widget.SearchView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inrange.trackapplication.dto.GetOrdersResponse;
import com.inrange.trackapplication.dto.Order;
import com.inrange.trackapplication.model.OrderViewModel;
import com.inrange.trackapplication.module.assignmentdetails.AssignmentFragment;
import com.inrange.trackapplication.module.assignmentdetails.AssignmentListFragment;
import com.inrange.trackapplication.dto.Assignment;
import com.inrange.trackapplication.module.home.HomeActivity;
import com.inrange.trackapplication.module.home.UserHomeActivity;
import com.inrange.trackapplication.module.orders.OrderListFragment;
import com.inrange.trackapplication.module.orders.OrdersFragment;
import com.inrange.trackapplication.snippet.PermissionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private OrderListFragment orderListFragment;
    private OrdersFragment ordersFragment;
    private ImageView ivSearch;
    private TextView tvList, tvMap;
    private Switch aSwitch;
    private SearchView svHeader;
    private GoogleMap mMap;
    private LinearLayout llMap;
    private List<Order> mOrders;
    private PermissionHandler mPermissionHandler;
    private Socket socket;
    private String provider = "network";
    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ivSearch = findViewById(R.id.activity_assignment_iv_search);
        tvList = findViewById(R.id.activity_assignment_tv_list);
        tvMap = findViewById(R.id.activity_assignment_tv_map);
        aSwitch = findViewById(R.id.activity_assignment_sw_head);
        svHeader = findViewById(R.id.activity_assignment_sv_header);
        llMap = findViewById(R.id.activity_assignment_ll_map);
        tvList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSwitch.setChecked(false);
            }
        });
        tvMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aSwitch.setChecked(true);
            }
        });
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvList.setAlpha(0.5f);
                    tvMap.setAlpha(1f);
                    llMap.setVisibility(View.VISIBLE);
                    updateOrders(mOrders);
                } else {
                    tvMap.setAlpha(0.5f);
                    tvList.setAlpha(1f);
                    llMap.setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.activity_order_detail_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        orderListFragment = OrderListFragment.newInstance(new ArrayList<Order>(), "");
        ordersFragment = OrdersFragment.newInstance();
        switchListScreen();
        switchSearchScreen();
        findViewById(R.id.activity_assignment_fl_DownAlert).setVisibility(View.VISIBLE);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findViewById(R.id.activity_assignment_fl_DownAlert).setVisibility(View.GONE);
                findViewById(R.id.activity_assignment_fl_MachineObservation).setVisibility(View.VISIBLE);
                svHeader.setVisibility(View.VISIBLE);
                tvList.setVisibility(View.GONE);
                tvMap.setVisibility(View.GONE);
                aSwitch.setVisibility(View.GONE);
                ivSearch.setVisibility(View.GONE);
                svHeader.setFocusable(true);
                svHeader.setIconified(false);
                svHeader.requestFocusFromTouch();
            }
        });
        svHeader.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                orderListFragment.filterList(s);
                return false;
            }
        });
        svHeader.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                findViewById(R.id.activity_assignment_fl_DownAlert).setVisibility(View.VISIBLE);
                findViewById(R.id.activity_assignment_fl_MachineObservation).setVisibility(View.GONE);
                svHeader.setVisibility(View.GONE);
                tvList.setVisibility(View.VISIBLE);
                tvMap.setVisibility(View.VISIBLE);
                aSwitch.setVisibility(View.VISIBLE);
                ivSearch.setVisibility(View.VISIBLE);
                return false;
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mPermissionHandler = PermissionHandler.getInstance(new PermissionHandler.PermissionHandleListener() {
            @Override
            public void onPermissionGranted(int requestCode) {
            }

            @Override
            public void onPermissionDenied(int requestCode) {

            }
        });
        mPermissionHandler.checkMultiplePermission(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE}, 1,
                "");



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
//        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(provider, 100, 1,  this);
        socketinit();

    }

    void updateOrders(GetOrdersResponse response){

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    void switchListScreen() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_assignment_fl_MachineObservation, orderListFragment, "switchListScreen");
        ft.commit();
    }

    void switchSearchScreen() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_assignment_fl_DownAlert, ordersFragment, "switchSearchScreen");
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (svHeader.getVisibility() == View.GONE) {
            super.onBackPressed();
        } else {

            findViewById(R.id.activity_assignment_fl_DownAlert).setVisibility(View.VISIBLE);
            findViewById(R.id.activity_assignment_fl_MachineObservation).setVisibility(View.GONE);
            svHeader.setVisibility(View.GONE);
            tvList.setVisibility(View.VISIBLE);
            tvMap.setVisibility(View.VISIBLE);
            aSwitch.setVisibility(View.VISIBLE);
            ivSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_logout:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public void updateOrders(List<Order> orders) {
        mOrders = orders;

        orderListFragment.updateOrders(orders);
        if (null != mMap) {
            mMap.clear();
            LatLng latLng = null;
            for (Order order : orders) {
                if (null != order.getAddressTo() && null != order.getAddressTo().getLatitude() && null != order.getAddressTo().getLongitude()) {
                    latLng = new LatLng(order.getAddressTo().getLatitude(), order.getAddressTo().getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(order.getAddressTo().getName());
                    int height = 25;
                    int width = 25;
                    BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.ic_ice_marker);
                    Bitmap b=bitmapdraw.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_ice_marker));
                    markerOptions.getPosition();
                    mMap.addMarker(markerOptions);
                }
            }

            if(null != latLng) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
            }
        }
    }


    void socketinit() {

        try {
            IO.Options options = new IO.Options();
            options.query = "token="+InrangeDataHandler.getLoginResponse().getId()+"&from=delivery";
            options.reconnection = true;
            socket = IO.socket("http://99.226.247.195:4000", options);
            socket.connect();
            socket.emit("join", "1");
        } catch (URISyntaxException e) {
            e.printStackTrace();

        }
        socket.on("welcome", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];

//                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        socket.on("userrequest", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];

//                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                        Log.d("userrequest", data.toString());
                        ordersFragment.ordersViewModel.getOrders();
                    }
                });
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

//        Toast.makeText(this,location.toString(),Toast.LENGTH_SHORT).show();
        if(null != socket && socket.connected()) {
            JSONObject object = new JSONObject();
            try {
                object.put("latitude", location.getLatitude());
                object.put("longitude", location.getLongitude());
                object.put("userid", InrangeDataHandler.getLoginResponse().getId());
                object.put("username", InrangeDataHandler.getLoginResponse().getUsername());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            socket.emit("currentstatus", object);
        }

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
