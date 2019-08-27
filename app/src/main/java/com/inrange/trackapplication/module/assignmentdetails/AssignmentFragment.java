package com.inrange.trackapplication.module.assignmentdetails;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inrange.trackapplication.CustomViewPager;
import com.inrange.trackapplication.R;
import com.inrange.trackapplication.ViewPagerAdapter;
import com.inrange.trackapplication.dto.Assignment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AssignmentFragment extends Fragment implements AssignmentView {
    private OnFragmentInteractionListener mListener;
    private TabLayout mTabLayout;
    private CustomViewPager mViewPager;
    private int mCurrentTabPosition;
    private AssignmentPresenter mAssignmentPresenter;


    public static AssignmentFragment newInstance() {
        AssignmentFragment fragment = new AssignmentFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment, container, false);
        TabLayout tabLayout = view.findViewById(R.id.fragment_assignment_tab_layout);

        mTabLayout = (TabLayout) view.findViewById(R.id.fragment_assignment_tab_layout);
        mViewPager = (CustomViewPager) view.findViewById(R.id.fragment_assignment_custom_view_pager);

        mTabLayout.setupWithViewPager(mViewPager);
        mAssignmentPresenter = AssignmentPresenterImpl.getInstance(this);
        return view;
    }

    TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mCurrentTabPosition = tab.getPosition();
            mTabLayout.getTabAt(tab.getPosition()).getCustomView().setAlpha(1f);
            getActivity().invalidateOptionsMenu();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            mTabLayout.getTabAt(tab.getPosition()).getCustomView().setAlpha(0.5f);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private void setupViewPager(List<Assignment> assignments) {
        String[] tabTitles = getResources().getStringArray(R.array.assignment_tab_title);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        AssignmentListFragment activeAssignmentFragment = AssignmentListFragment.newInstance(getActivieAssignments(assignments), tabTitles[0]);
        AssignmentListFragment upcomingAssignmentFragment = AssignmentListFragment.newInstance(getUpcomingAssignments(assignments), tabTitles[1]);
        AssignmentListFragment completedAssignmentFragment = AssignmentListFragment.newInstance(getCompletedAssignments(assignments), tabTitles[2]);

        adapter.addFrag(activeAssignmentFragment, tabTitles[0]);
        adapter.addFrag(upcomingAssignmentFragment, tabTitles[1]);
        adapter.addFrag(completedAssignmentFragment, tabTitles[2]);
        mViewPager.setAdapter(adapter);
    }

    List<Assignment> getActivieAssignments(List<Assignment> masterAssignments) {
        List<Assignment> assignments = new ArrayList<>();
        Calendar tomoCalendar = Calendar.getInstance();
        tomoCalendar.set(tomoCalendar.get(Calendar.YEAR),tomoCalendar.get(Calendar.MONTH),tomoCalendar.get(Calendar.DATE),0,0,0);
        tomoCalendar.add(Calendar.DATE,1);
        for (Assignment assignment : masterAssignments) {
            if (null != assignment && !assignment.getIscancelled()) {
                if (!assignment.getIsrecurrence() && !assignment.getIscompleted() && null != assignment.getStartDate()
                        && ( assignment.getStartDate().getTime() < tomoCalendar.getTimeInMillis() )) {
                    assignments.add(assignment);
                } else if (assignment.getIsrecurrence() && null != assignment.getClosedDate() && null != assignment.getStartDate()
                        && assignment.getStartDate().before(Calendar.getInstance().getTime()) && assignment.getClosedDate().after(Calendar.getInstance().getTime())) {
                    assignment.setIscompleted(false);
                    assignments.add(assignment);
                }
            }
        }
        return assignments;
    }

    List<Assignment> getUpcomingAssignments(List<Assignment> masterAssignments) {
        List<Assignment> assignments = new ArrayList<>();
        Calendar tomoCal = Calendar.getInstance();
        tomoCal.add(Calendar.DATE,1);
        tomoCal.set(tomoCal.get(Calendar.YEAR),tomoCal.get(Calendar.MONTH),tomoCal.get(Calendar.DATE),0,0,0);
        for (Assignment assignment : masterAssignments) {
            if (null != assignment && !assignment.getIscompleted() && !assignment.getIscancelled() &&
                    (null == assignment.getStartDate() || assignment.getStartDate().getTime() > tomoCal.getTimeInMillis())) {
                assignments.add(assignment);
            }
        }
        return assignments;
    }

    List<Assignment> getCompletedAssignments(List<Assignment> masterAssignments) {
        List<Assignment> assignments = new ArrayList<>();
        for (Assignment assignment : masterAssignments) {
            if (null != assignment && assignment.getIscancelled()) {
                assignments.add(assignment);
            } else if (assignment.getIscompleted() && !assignment.getIsrecurrence()) {
                assignments.add(assignment);
            } else if (assignment.getIscompleted() && assignment.getIsrecurrence() && null != assignment.getClosedDate() && assignment.getClosedDate().before(Calendar.getInstance().getTime())) {
                assignments.add(assignment);
            }
        }
        return assignments;
    }

    private void setupTabIcons() {

        String[] tabTitles = getResources().getStringArray(R.array.assignment_tab_title);
        for (int i = 0; i < tabTitles.length; i++) {
            LinearLayout tabLinearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_tab_field, null);
            TextView tabTitle = (TextView) tabLinearLayout.findViewById(R.id.tabContent);
            tabTitle.setText("  " + tabTitles[i]);
            mTabLayout.getTabAt(i).setCustomView(tabTitle);
        }
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void populateAssignmentList(List<Assignment> assignments) {
        setupViewPager(assignments);
        setupTabIcons();


        mTabLayout.addOnTabSelectedListener(tabSelectedListener);

        mTabLayout.getTabAt(1).getCustomView().setAlpha(0.5f);
        mTabLayout.getTabAt(2).getCustomView().setAlpha(0.5f);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
