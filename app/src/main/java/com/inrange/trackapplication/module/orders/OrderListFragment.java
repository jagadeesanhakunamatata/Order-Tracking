package com.inrange.trackapplication.module.orders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inrange.trackapplication.Constants;
import com.inrange.trackapplication.GeocodingLocation;
import com.inrange.trackapplication.R;
import com.inrange.trackapplication.dto.Order;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderListFragment extends Fragment implements LocationListener, OrdersRecyclerViewAdapter.OnListFragmentInteractionListener {

    private static final String ASSIGNMENTS = "ASSIGNMENTS";
    private static final String TYPEOFLIST = "TYPEOFLIST";
    private OrdersRecyclerViewAdapter.OnListFragmentInteractionListener mListener;
    private List<Order> mOrderList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private OrdersRecyclerViewAdapter mOrderAdapter;
    private String mType = "";
    private boolean hasTimerRun = true;
    private Runnable timerRunnable;
    private Handler timerHandler;
    private LocationManager locationManager;
    private String provider;

    public OrderListFragment() {

    }

    public static OrderListFragment newInstance(List<Order> assignments, String typeOfList) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ASSIGNMENTS, new ArrayList<>(assignments));
        bundle.putString(TYPEOFLIST, typeOfList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            List<Order> orders = (List<Order>) getArguments().getSerializable(ASSIGNMENTS);
            if(null != orders && orders.size() > 0){
                mOrderList = orders;
            }
            mType = getArguments().getString(TYPEOFLIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment_list, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mOrderAdapter = new OrdersRecyclerViewAdapter(getContext(), mOrderList, this);
        mRecyclerView.setAdapter(mOrderAdapter);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    /* Request updates at startup */
    @Override
    public void onResume() {
        super.onResume();
        if (mType.toUpperCase().equals(getResources().getStringArray(R.array.assignment_tab_title)[0].toUpperCase())) {

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
//            locationManager.requestLocationUpdates(provider, 100, 1, (LocationListener) this);
        }
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates((LocationListener) this);
        stopTimer();
    }

    void stopTimer() {

        hasTimerRun = false;
        try {
            timerHandler.removeCallbacks(timerRunnable);
        } catch (Exception ex) {
        }
    }

    public void currentLocationChanged(Location location) {

//        new GetLatLongfromAddress(location).execute(mOrderList);
        for (Order assignment :
                mOrderList) {
            GeocodingLocation.getAddressFromLocation(assignment.getToAddress(), getContext(), new GeocoderHandler());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocationChanged(location);
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

    public void filterList(String query) {

        mOrderAdapter.getFilter().filter(query);
    }

    @Override
    public void onListFragmentInteraction(Order item) {

        Intent intent = new Intent(getActivity(),OrderDetailActivity.class);
        intent.putExtra(Constants.BundleKey.ORDER,item);
        startActivity(intent);
    }

    public void updateOrders(List<Order> orders) {

        mOrderList = orders;
        mOrderAdapter = new OrdersRecyclerViewAdapter(getContext(), mOrderList, this);
        if(null != mRecyclerView) {
            mRecyclerView.setAdapter(mOrderAdapter);
        }
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    if (bundle.containsKey("lat") && bundle.containsKey("lon")) {
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        Location location = locationManager.getLastKnownLocation(provider);
                        for (int i = 0; i < mOrderList.size(); i++) {
                            if (null != mOrderList.get(i).getToAddress() && mOrderList.get(i).getToAddress().equals(bundle.getString("address"))) {
                                mOrderList.get(i).setDistance(distance(location.getLatitude(),
                                        location.getLongitude(), bundle.getDouble("lat"), bundle.getDouble("lon"), "K"));
                                if (null == mOrderList.get(i).getDistanceMessage() ||
                                        !mOrderList.get(i).getDistanceMessage().equals(roundTwoDecimals(mOrderList.get(i).getDistance()) + " km")) {
                                    mOrderList.get(i).setDistanceMessage(roundTwoDecimals(mOrderList.get(i).getDistance()) + " km");
                                    mOrderAdapter.notifyItemChanged(i);
                                }
                            }
                        }
                    }
                    break;
                default:
                    locationAddress = null;
            }
        }
    }


    double roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.#");
        return Double.valueOf(twoDForm.format(d));
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit == "K") {
                dist = dist * 1.609344;
            } else if (unit == "N") {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }
}
