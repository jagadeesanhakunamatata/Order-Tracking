package com.inrange.trackapplication;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inrange.trackapplication.dto.GetOrdersResponse;
import com.inrange.trackapplication.dto.Order;
import com.inrange.trackapplication.model.OrderViewModel;
import com.inrange.trackapplication.module.orders.CustomerFragment;
import com.inrange.trackapplication.module.orders.OrderListFragment;
import com.inrange.trackapplication.module.orders.OrderPresenter;
import com.inrange.trackapplication.module.orders.OrdersFragment;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity {

    private OrdersFragment.OnFragmentInteractionListener mListener;
    private TabLayout mTabLayout;
    private CustomViewPager mViewPager;
    private int mCurrentTabPosition;
    private OrderPresenter mOrderPresenter;
    public OrderViewModel ordersViewModel;
    private boolean isTabInitiated;
    private OrderListFragment ordersFragment;
    private CustomerFragment customersFragment;
    private CustomerFragment salesmanFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(null != getSupportActionBar()){
            getSupportActionBar().setElevation(0);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        mTabLayout = (TabLayout) findViewById(R.id.activity_admin_tab_layout);
        mViewPager = (CustomViewPager) findViewById(R.id.activity_admin_custom_view_pager);

        mTabLayout.setupWithViewPager(mViewPager);
//        mOrderPresenter = OrderPresenterImpl.getInstance(this);

        ordersViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        ordersViewModel.getOrders().observe(this,this::updateOrders);

        setupViewPager();
        setupTabIcons();

        mTabLayout.addOnTabSelectedListener(tabSelectedListener);

        mTabLayout.getTabAt(1).getCustomView().setAlpha(0.5f);
        mTabLayout.getTabAt(2).getCustomView().setAlpha(0.5f);
    }


    void updateOrders(GetOrdersResponse response){
        if(null != response && null != response.getOrder() && response.getOrder().size() > 0){
            populateOrderList(response.getOrder());
        }
    }


    private void setupViewPager() {
        String[] tabTitles = getResources().getStringArray(R.array.assignment_tab_title);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        ordersFragment = OrderListFragment.newInstance(new ArrayList<>(), "Orders");
        customersFragment = CustomerFragment.newInstance(true);
        salesmanFragment = CustomerFragment.newInstance(false);

        adapter.addFrag(ordersFragment, "Orders");
        adapter.addFrag(customersFragment, "Customers");
        adapter.addFrag(salesmanFragment, "Sales Men");
        mViewPager.setAdapter(adapter);
    }


    TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mCurrentTabPosition = tab.getPosition();
            mTabLayout.getTabAt(tab.getPosition()).getCustomView().setAlpha(1f);
            invalidateOptionsMenu();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            mTabLayout.getTabAt(tab.getPosition()).getCustomView().setAlpha(0.5f);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private void setupTabIcons() {

        String[] tabTitles = getResources().getStringArray(R.array.admin_tab_title);
        for (int i = 0; i < tabTitles.length; i++) {
            LinearLayout tabLinearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_tab_field, null);
            TextView tabTitle = (TextView) tabLinearLayout.findViewById(R.id.tabContent);
            tabTitle.setText("  " + tabTitles[i]);
            mTabLayout.getTabAt(i).setCustomView(tabTitle);
        }
    }

    public void populateOrderList(List<Order> orders) {
        ordersFragment.updateOrders(orders);
    }

}
