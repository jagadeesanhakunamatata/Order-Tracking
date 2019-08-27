package com.inrange.trackapplication.module.orders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inrange.trackapplication.CodeSnippet;
import com.inrange.trackapplication.Constants;
import com.inrange.trackapplication.R;
import com.inrange.trackapplication.dto.Order;

import java.io.Serializable;

public class OrderDetailActivity extends AppCompatActivity  implements OnMapReadyCallback {

    private TextView tvHeader, tvDistance,tvFromName, tvFrom, tvToName, tvTo, tvPriority, tvDescription,tvClosedStatus,tvClosedDate;
    private ImageView ivBack,ivPriority;
    private GoogleMap mMap;
    private LinearLayout llDirections, llLocations, llCall,llCompleted;
    private Order mOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ivBack = findViewById(R.id.activity_order_detail_iv_back);
        tvHeader = findViewById(R.id.activity_order_detail_tv_header);
        tvDistance = findViewById(R.id.activity_order_detail_tv_distance);
        llDirections = findViewById(R.id.activity_order_detail_ll_directions);
        llLocations = findViewById(R.id.activity_order_detail_ll_locations);
        tvFromName = findViewById(R.id.activity_order_detail_tv_from_name);
        tvFrom = findViewById(R.id.activity_order_detail_tv_from);
        tvToName = findViewById(R.id.activity_order_detail_tv_to_name);
        tvTo = findViewById(R.id.activity_order_detail_tv_to);
        llCall = findViewById(R.id.activity_order_detail_ll_call);
        ivPriority = findViewById(R.id.activity_order_detail_iv_priority);
        tvPriority = findViewById(R.id.activity_order_detail_tv_priority);
        tvDescription = findViewById(R.id.activity_order_detail_tv_description);
        llCompleted = findViewById(R.id.activity_order_detail_ll_completed);
        tvClosedStatus = findViewById(R.id.activity_order_detail_tv_status);
        tvClosedDate = findViewById(R.id.activity_order_detail_tv_statusdate);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mOrder = (Order) getIntent().getExtras().getSerializable(Constants.BundleKey.ORDER);
        populateOrder();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void populateOrder(){
        if(null != mOrder){
            tvHeader.setText(mOrder.getDescription());
            tvFrom.setText(mOrder.getFromAddress());
            tvTo.setText(mOrder.getToAddress());
            if(null != mOrder.getAddressTo() && null != mOrder.getAddressTo().getName()){
                tvToName.setText(mOrder.getAddressTo().getName());
            }
            if(null != mOrder.getAddressFrom() && null != mOrder.getAddressFrom().getName()){
                tvFromName.setText(mOrder.getAddressFrom().getName());
            }

            if (mOrder.getIscancelled()) {
                if (null != mOrder.getCanceledDate()) {
                    tvClosedDate.setText(CodeSnippet.getDateStringFromDate(mOrder.getCanceledDate(), Constants.DateFormat.ASSIGNMENT_CLOSED_DATE_FORMAT));
                }
                tvClosedStatus.setText("cancelled on ");
                tvClosedStatus.setTextColor(getResources().getColor(R.color.offline_color));
                llCompleted.setVisibility(View.VISIBLE);
            } else if (mOrder.getIscompleted()) {
                if (null != mOrder.getCompletedDate()) {
                    tvClosedDate.setText(CodeSnippet.getDateStringFromDate(mOrder.getCompletedDate(), Constants.DateFormat.ASSIGNMENT_CLOSED_DATE_FORMAT));
                }
                tvClosedStatus.setText("Completed on ");
                tvClosedStatus.setTextColor(getResources().getColor(R.color.availablecolor));
                llCompleted.setVisibility(View.VISIBLE);
            } else {
                llCompleted.setVisibility(View.GONE);
            }

            if(null != mOrder.getPriority())
            tvPriority.setText(mOrder.getPriority().toUpperCase());
            if (null != mOrder.getPriority() && mOrder.getPriority().toUpperCase().equals("HIGH")) {
                ivPriority.setColorFilter(ContextCompat.getColor(this, R.color.riskcolor), PorterDuff.Mode.SRC_IN);
            } else if (null != mOrder.getPriority() && mOrder.getPriority().toUpperCase().equals("MEDIUM")) {
                ivPriority.setColorFilter(ContextCompat.getColor(this, R.color.mediumcolor), PorterDuff.Mode.SRC_IN);
            } else if (null != mOrder.getPriority() && mOrder.getPriority().toUpperCase().equals("LOW")) {
                ivPriority.setColorFilter(ContextCompat.getColor(this, R.color.lowcolor), PorterDuff.Mode.SRC_IN);
            }

//            if (0.0 != mOrder.getDistance()) {
//                tvDistance.setText(roundTwoDecimals(mOrdergetDistance()) + " km");
//            } else {
//                tvDistance.setVisibility(View.GONE);
//            }

            llDirections.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + mOrder.getAddressTo().getAddress());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            });

            llCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (null != mOrder && null != mOrder.getAddressTo() && null != mOrder.getAddressTo().getPhone()) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + mOrder.getAddressTo().getPhone()));//change the number
                        if (ActivityCompat.checkSelfPermission(OrderDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(callIntent);
                    }
                }
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if(null != mOrder && null != mOrder.getAddressFrom() && null != mOrder.getAddressTo().getLatitude() && null != mOrder.getAddressTo().getLongitude()){
            LatLng sydney = new LatLng(mOrder.getAddressTo().getLatitude(), mOrder.getAddressTo().getLongitude());
            mMap.addMarker(new MarkerOptions().position(sydney).title(mOrder.getAddressTo().getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_ice_marker)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12));
        }
    }
}
