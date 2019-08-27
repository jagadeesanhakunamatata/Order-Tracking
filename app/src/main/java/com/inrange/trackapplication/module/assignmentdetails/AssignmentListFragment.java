package com.inrange.trackapplication.module.assignmentdetails;

import android.Manifest;
import android.content.Context;
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

import com.inrange.trackapplication.GeocodingLocation;
import com.inrange.trackapplication.R;
import com.inrange.trackapplication.dto.Assignment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AssignmentListFragment extends Fragment implements LocationListener {

    private static final String ASSIGNMENTS = "ASSIGNMENTS";
    private static final String TYPEOFLIST = "TYPEOFLIST";
    private OnListFragmentInteractionListener mListener;
    private List<Assignment> mAssignmentList;
    private RecyclerView mRecyclerView;
    private AssignmentRecyclerViewAdapter mAssignmentAdapter;
    private String mType = "";
    private boolean hasTimerRun = true;
    private Runnable timerRunnable;
    private Handler timerHandler;
    private LocationManager locationManager;
    private String provider;

    public AssignmentListFragment() {

    }

    public static AssignmentListFragment newInstance(List<Assignment> assignments, String typeOfList) {
        AssignmentListFragment fragment = new AssignmentListFragment();
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
            mAssignmentList = (List<Assignment>) getArguments().getSerializable(ASSIGNMENTS);
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
        mAssignmentAdapter = new AssignmentRecyclerViewAdapter(getContext(), mAssignmentList, mListener);
        mRecyclerView.setAdapter(mAssignmentAdapter);

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
            locationManager.requestLocationUpdates(provider, 100, 1, (LocationListener) this);
            startTimer();
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


    void startTimer() {
        hasTimerRun = true;
        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            public void run() {

                if (hasTimerRun) {
                    onTimeChanged();
                    timerHandler.postDelayed(timerRunnable, 1000);
                }
            }
        };

        timerHandler.postDelayed(timerRunnable, 1000);
    }

    public void currentLocationChanged(Location location) {

//        new GetLatLongfromAddress(location).execute(mAssignmentList);
        for (Assignment assignment :
                mAssignmentList) {
            GeocodingLocation.getAddressFromLocation(assignment.getLocation(), getContext(), new GeocoderHandler());
        }
    }

    public void onTimeChanged() {

        Calendar currentCalender = Calendar.getInstance();
        currentCalender.add(Calendar.MINUTE, 30);
        if (null != mAssignmentList && mAssignmentList.size() > 0) {
            for (Assignment assignment : mAssignmentList) {
                String alert = "";
                if (null != assignment.getStartDate() && assignment.getStartDate().getTime() < currentCalender.getTimeInMillis()) {

                    if (assignment.getStartDate().getTime() > Calendar.getInstance().getTimeInMillis()) {
                        alert = "Starts in " + String.valueOf((assignment.getStartDate().getTime() - Calendar.getInstance().getTimeInMillis()) / 60000) + " Mins";
                    } else if (assignment.getIsrecurrence() && null != assignment.getClosedDate()
                            && assignment.getStartDate().before(Calendar.getInstance().getTime()) && assignment.getClosedDate().after(Calendar.getInstance().getTime())) {
                        Calendar startCalender = Calendar.getInstance();
                        startCalender.setTime(assignment.getStartDate());
                        startCalender.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE));
                        if (startCalender.getTimeInMillis() < currentCalender.getTimeInMillis() && startCalender.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
                            alert = "Starts in " + String.valueOf((startCalender.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 60000) + " Mins";
                        }
                    }

                    if (null == assignment.getTimeMessage() || !assignment.getTimeMessage().equals(alert)) {
                        assignment.setTimeMessage(alert);
                        mAssignmentAdapter.notifyItemChanged(mAssignmentList.indexOf(assignment));
                    }
                }
            }
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

        mAssignmentAdapter.getFilter().filter(query);
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
                        for (int i = 0; i < mAssignmentList.size(); i++) {
                            if (null != mAssignmentList.get(i).getLocation() && mAssignmentList.get(i).getLocation().equals(bundle.getString("address"))) {
                                mAssignmentList.get(i).setDistance(distance(location.getLatitude(),
                                        location.getLongitude(), bundle.getDouble("lat"), bundle.getDouble("lon"), "K"));
                                if (null == mAssignmentList.get(i).getDistanceMessage() ||
                                        !mAssignmentList.get(i).getDistanceMessage().equals(roundTwoDecimals(mAssignmentList.get(i).getDistance()) + " km")) {
                                    mAssignmentList.get(i).setDistanceMessage(roundTwoDecimals(mAssignmentList.get(i).getDistance()) + " km");
                                    mAssignmentAdapter.notifyItemChanged(i);
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


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Assignment item);
    }
}
