package com.inrange.trackapplication.module.orders;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inrange.trackapplication.AdminHomeActivity;
import com.inrange.trackapplication.Constants;
import com.inrange.trackapplication.InrangeDataHandler;
import com.inrange.trackapplication.MainActivity;
import com.inrange.trackapplication.R;
import com.inrange.trackapplication.dto.Customer;
import com.inrange.trackapplication.dto.Role;
import com.inrange.trackapplication.dto.UserlistResponse;
import com.inrange.trackapplication.model.UserViewModel;
import com.inrange.trackapplication.module.home.UserHomeActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomerFragment extends Fragment {

    private static final String ARG_USER_FLAG = "ARG_USER_FLAT";
    private OnListFragmentInteractionListener mListener;
    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private boolean isCustomer;

    public CustomerFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CustomerFragment newInstance(boolean isCustomer) {
        CustomerFragment fragment = new CustomerFragment();
        Bundle extras = new Bundle();
        extras.putBoolean(ARG_USER_FLAG,isCustomer);
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            isCustomer = getArguments().getBoolean(ARG_USER_FLAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getCustomers().observe(this, this::populateResponses);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyCustomerRecyclerViewAdapter(new ArrayList<>(), mListener));
        }
        return view;
    }

    void populateResponses(UserlistResponse response) {

        if (isCustomer) {
            recyclerView.setAdapter(new MyCustomerRecyclerViewAdapter(getCustomers(response.getContent(), Constants.Roles.ROLE_CUSTOMER), mListener));
        }else{
            recyclerView.setAdapter(new MyCustomerRecyclerViewAdapter(getCustomers(response.getContent(), Constants.Roles.ROLE_COURIER), mListener));
        }
    }

    List<Customer> getCustomers(List<Customer> users, String frole){

        List<Customer> customers = new ArrayList<>();
        for (Customer customer : users) {
            for (Role role : customer.getRoles()) {
                if (role.getName().equals(frole)) {
                    customers.add(customer);
                }
            }
        }
        return customers;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Customer item);
    }
}
