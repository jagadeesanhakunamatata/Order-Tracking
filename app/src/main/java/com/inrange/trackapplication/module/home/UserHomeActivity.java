package com.inrange.trackapplication.module.home;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.inrange.trackapplication.InRangeApplication;
import com.inrange.trackapplication.InrangeDataHandler;
import com.inrange.trackapplication.LoginActivity;
import com.inrange.trackapplication.NewOrderActivity;
import com.inrange.trackapplication.R;
import com.inrange.trackapplication.dto.AddressTo;
import com.inrange.trackapplication.dto.Customer;
import com.inrange.trackapplication.dto.Order;
import com.inrange.trackapplication.model.OrderViewModel;
import com.inrange.trackapplication.module.orders.OrderListFragment;
import com.inrange.trackapplication.module.orders.OrderPresenter;
import com.inrange.trackapplication.module.orders.OrderPresenterImpl;
import com.inrange.trackapplication.module.orders.OrderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class UserHomeActivity extends AppCompatActivity implements OrderView, LocationListener, OnMapReadyCallback {

    private OrderListFragment orderListFragment;
    private OrderPresenter mOrderPresenter;
    private LinearLayout llMap;
    private View flList;
    private View fab;
    private Socket socket;
    private LocationManager locationManager;
    private String provider = "network";
    private OrderViewModel ordersViewModel;
    private GoogleMap mMap;

    private HashMap<String, Marker> mapMarkers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        llMap = findViewById(R.id.activity_home_ll_map);
        flList = findViewById(R.id.activity_home_fl_list);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        socketinit();
        orderListFragment = OrderListFragment.newInstance(new ArrayList<Order>(), "");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_home_fl_list, orderListFragment, "switchListScreen");
        ft.commit();
        mOrderPresenter = OrderPresenterImpl.getInstance(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
//        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates("network", 100, 1, this);

        ordersViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final AlertDialog dialogBuilder = new AlertDialog.Builder(UserHomeActivity.this).create();
//                LayoutInflater inflater = UserHomeActivity.this.getLayoutInflater();
//                View dialogView = inflater.inflate(R.layout.custom_dialog, null);
//
//                final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
//                final EditText etAddress = (EditText) dialogView.findViewById(R.id.edt_address);
//                Button btnSubmit = (Button) dialogView.findViewById(R.id.buttonSubmit);
//                Button btnCancel = (Button) dialogView.findViewById(R.id.buttonCancel);
//
//                if(null != locationManager.getLastKnownLocation(provider)) {
//                    try {
//                        AddressTo to = new AddressTo();
//                        etAddress.setText(getAddress(locationManager.getLastKnownLocation(provider), to));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                btnCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialogBuilder.dismiss();
//                    }
//                });
//                btnSubmit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if(null != socket && socket.connected()) {
//                            socket.emit("userrequest","OrderRequest");
//                        }
//                        Order order = new Order();
//                        AddressTo to = new AddressTo();
//                        to.setAddress(etAddress.getText().toString());
//                        order.setAddressTo(to);
//                        Customer customer = new Customer();
//                        customer.setCourier(InrangeDataHandler.getLoginResponse().getCourier());
//                        customer.setCustomer(InrangeDataHandler.getLoginResponse().getCustomer());
//                        customer.setEmail(InrangeDataHandler.getLoginResponse().getEmail());
//                        customer.setEnabled(InrangeDataHandler.getLoginResponse().getEnabled());
//                        customer.setFullname(InrangeDataHandler.getLoginResponse().getFullname());
//                        customer.setId(InrangeDataHandler.getLoginResponse().getId());
//                        customer.setRoles(InrangeDataHandler.getLoginResponse().getRoles());
//                        order.setCustomer(customer);
//                        order.setDescription(editText.getText().toString());
//                        ordersViewModel.addOrder(order).observe(UserHomeActivity.this,UserHomeActivity.this::updateAddResponse);
//                        dialogBuilder.dismiss();
//                    }
//                });
//
//                dialogBuilder.setView(dialogView);
//                dialogBuilder.show();

                if (ActivityCompat.checkSelfPermission(UserHomeActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(UserHomeActivity.this,
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                if (null != locationManager.getLastKnownLocation(provider)) {
//                    try {
//                        AddressTo to = new AddressTo();
//                        String address = getAddress(locationManager.getLastKnownLocation(provider), to);
                        Intent intent = new Intent(UserHomeActivity.this, NewOrderActivity.class);
//                        intent.putExtra("address", address);
                        startActivity(intent);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }

            }
        });
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setTitle("InRange");
        }
    }

    void updateAddResponse(Order order) {
        if (null != order) {
            Toast.makeText(this, "Order Submitted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Order Submitted failed", Toast.LENGTH_SHORT).show();
        }
    }

    String getAddress(Location location, AddressTo to) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = "";
        if (addresses.size() > 0) {
            address = addresses.get(0).getAddressLine(0) + "\n";
            to.setAddress(addresses.get(0).getAddressLine(0));

            address += addresses.get(0).getLocality() + "\n";
            address += addresses.get(0).getAdminArea() + "\n";
            to.setCity(addresses.get(0).getAdminArea());

            address += addresses.get(0).getCountryName() + "\n";
            to.setCity(addresses.get(0).getCountryName());

            address += addresses.get(0).getPostalCode() + "\n";
            to.setPostalCode(Integer.valueOf(addresses.get(0).getPostalCode()));

            address += addresses.get(0).getFeatureName();
        }
        return address;
    }

    void switchListScreen() {
        llMap.setVisibility(View.GONE);
        flList.setVisibility(View.VISIBLE);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("My Orders");
        }
    }

    void switchToMap() {
        llMap.setVisibility(View.VISIBLE);
        flList.setVisibility(View.GONE);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setTitle("InRange");
        }
    }

    @Override
    public void onBackPressed() {
        if (flList.getVisibility() == View.VISIBLE) {
            switchToMap();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void populateOrderList(List<Order> orders) {

        orderListFragment.updateOrders(orders);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_my_orders:
                switchListScreen();
                break;
            case android.R.id.home:
                switchToMap();
                break;
            case R.id.item_logout:
                Intent intent = new Intent(UserHomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    void socketinit() {

        try {
            IO.Options options = new IO.Options();
            options.query = "token="+InrangeDataHandler.getLoginResponse().getId()+"&from=user";
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

//                        Toast.makeText(UserHomeActivity.this, data, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        socket.on("servicestop", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];

//                        Toast.makeText(UserHomeActivity.this, data, Toast.LENGTH_SHORT).show();
//                        if(mapMarkers.containsKey(data)){
//                            mapMarkers.get(data).remove();
//                            mapMarkers.remove(data);
//                        }
                    }
                });
            }
        });
        socket.on("currentstatus", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];

                        try {
                            double lat = data.getDouble("latitude");
                            double lon = data.getDouble("longitude");
                            String userid = data.getString("userid");
                            String username = data.getString("username");

                            LatLng latLng = new LatLng(lat, lon);
                            if(mapMarkers.containsKey(userid)){
                                mapMarkers.get(userid).setPosition(latLng);
                            }else {
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(latLng);
                                markerOptions.title(username);
                                int height = 25;
                                int width = 25;
                                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_ice_marker);
                                Bitmap b = bitmapdraw.getBitmap();
                                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_ice_marker));
                                markerOptions.getPosition();
                                Marker marker = mMap.addMarker(markerOptions);
                                mapMarkers.put(userid, marker);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("currentstatus", data.toString());

//                        Toast.makeText(UserHomeActivity.this, data.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

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
