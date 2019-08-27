package com.inrange.trackapplication.module.orders;

import android.arch.lifecycle.ViewModelProviders;
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
import com.inrange.trackapplication.MainActivity;
import com.inrange.trackapplication.R;
import com.inrange.trackapplication.ViewPagerAdapter;
import com.inrange.trackapplication.dto.GetOrdersResponse;
import com.inrange.trackapplication.dto.Order;
import com.inrange.trackapplication.model.OrderViewModel;
import com.inrange.trackapplication.model.UserViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class OrdersFragment extends Fragment implements OrderView {
    private OnFragmentInteractionListener mListener;
    private TabLayout mTabLayout;
    private CustomViewPager mViewPager;
    private int mCurrentTabPosition;
    private OrderPresenter mOrderPresenter;
    public OrderViewModel ordersViewModel;
    private boolean isTabInitiated;


    public static OrdersFragment newInstance() {
        OrdersFragment fragment = new OrdersFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignment, container, false);
        TabLayout tabLayout = view.findViewById(R.id.fragment_assignment_tab_layout);

        mTabLayout = (TabLayout) view.findViewById(R.id.fragment_assignment_tab_layout);
        mViewPager = (CustomViewPager) view.findViewById(R.id.fragment_assignment_custom_view_pager);

        mTabLayout.setupWithViewPager(mViewPager);
//        mOrderPresenter = OrderPresenterImpl.getInstance(this);

        ordersViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        ordersViewModel.getOrders().observe(this,this::updateOrders);
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

    private void setupViewPager(List<Order> Orders) {
        String[] tabTitles = getResources().getStringArray(R.array.assignment_tab_title);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        OrderListFragment activeOrderFragment = OrderListFragment.newInstance(getActivieOrders(Orders), tabTitles[0]);
        OrderListFragment upcomingOrderFragment = OrderListFragment.newInstance(getUpcomingOrders(Orders), tabTitles[1]);
        OrderListFragment completedOrderFragment = OrderListFragment.newInstance(getCompletedOrders(Orders), tabTitles[2]);

        adapter.addFrag(activeOrderFragment, tabTitles[0]);
        adapter.addFrag(upcomingOrderFragment, tabTitles[1]);
        adapter.addFrag(completedOrderFragment, tabTitles[2]);
        mViewPager.setAdapter(adapter);
    }

    List<Order> getActivieOrders(List<Order> masterOrders) {
        List<Order> Orders = new ArrayList<>();
        Calendar tomoCalendar = Calendar.getInstance();
        tomoCalendar.set(tomoCalendar.get(Calendar.YEAR), tomoCalendar.get(Calendar.MONTH), tomoCalendar.get(Calendar.DATE), 0, 0, 0);
        tomoCalendar.add(Calendar.DATE, 1);
        for (Order Order : masterOrders) {
            if (null != Order && !Order.getIscancelled() && !Order.getIscompleted()) {
                Orders.add(Order);
            }
        }
        return Orders;
    }

    List<Order> getUpcomingOrders(List<Order> masterOrders) {
        List<Order> Orders = new ArrayList<>();
        Calendar tomoCalendar = Calendar.getInstance();
        tomoCalendar.set(tomoCalendar.get(Calendar.YEAR), tomoCalendar.get(Calendar.MONTH), tomoCalendar.get(Calendar.DATE), 0, 0, 0);
        tomoCalendar.add(Calendar.DATE, 1);
        for (Order Order : masterOrders) {
            if (null != Order && !Order.getIscancelled() && !Order.getIscompleted()) {
                Orders.add(Order);
            }
        }
        return Orders;
    }

    List<Order> getCompletedOrders(List<Order> masterOrders) {
        List<Order> Orders = new ArrayList<>();
        for (Order Order : masterOrders) {
            if (null != Order && Order.getIscompleted()) {
                Orders.add(Order);
            }
        }
        return Orders;
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

    void updateOrders(GetOrdersResponse response){
        if(null != response && null != response.getOrder() && response.getOrder().size() > 0){
            populateOrderList(response.getOrder());
        }
    }

    @Override
    public void populateOrderList(List<Order> orders) {
        if(!isTabInitiated) {
            setupViewPager(orders);
            setupTabIcons();

            mTabLayout.addOnTabSelectedListener(tabSelectedListener);

            mTabLayout.getTabAt(1).getCustomView().setAlpha(0.5f);
            mTabLayout.getTabAt(2).getCustomView().setAlpha(0.5f);
            isTabInitiated = true;
        }else{
            if(((ViewPagerAdapter)mViewPager.getAdapter()).getCount() == 3){
                try {
                    ((OrderListFragment) ((ViewPagerAdapter) mViewPager.getAdapter()).getItem(0)).updateOrders(getActivieOrders(orders));
                    ((OrderListFragment) ((ViewPagerAdapter) mViewPager.getAdapter()).getItem(1)).updateOrders(getUpcomingOrders(orders));
                    ((OrderListFragment) ((ViewPagerAdapter) mViewPager.getAdapter()).getItem(2)).updateOrders(getCompletedOrders(orders));
                }catch (Exception ex){

                }
            }
        }
        ((MainActivity) getActivity()).updateOrders(orders);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
