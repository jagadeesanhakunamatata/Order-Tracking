package com.inrange.trackapplication;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inrange.trackapplication.dto.AddressTo;
import com.inrange.trackapplication.dto.Customer;
import com.inrange.trackapplication.dto.Order;
import com.inrange.trackapplication.model.OrderViewModel;
import com.inrange.trackapplication.module.home.UserHomeActivity;

public class NewOrderActivity extends AppCompatActivity {

    private OrderViewModel ordersViewModel;

    private EditText editText, etAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        editText = findViewById(R.id.edt_comment);
        etAddress = findViewById(R.id.edt_address);
        Button btnSubmit = (Button) findViewById(R.id.buttonSubmit);
        Button btnCancel = (Button) findViewById(R.id.buttonCancel);

//        etAddress.setText(getIntent().getExtras().getString("address"));

        ordersViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        btnSubmit.setOnClickListener(v -> {
            Order order = new Order();
            AddressTo to = new AddressTo();
            to.setAddress(etAddress.getText().toString());
            order.setAddressTo(to);
            Customer customer = new Customer();
            customer.setCourier(InrangeDataHandler.getLoginResponse().getCourier());
            customer.setCustomer(InrangeDataHandler.getLoginResponse().getCustomer());
            customer.setEmail(InrangeDataHandler.getLoginResponse().getEmail());
            customer.setEnabled(InrangeDataHandler.getLoginResponse().getEnabled());
            customer.setFullname(InrangeDataHandler.getLoginResponse().getFullname());
            customer.setId(InrangeDataHandler.getLoginResponse().getId());
            customer.setRoles(InrangeDataHandler.getLoginResponse().getRoles());
            order.setCustomer(customer);
            order.setDescription(editText.getText().toString());
            ordersViewModel.addOrder(order).observe(NewOrderActivity.this, NewOrderActivity.this::updateAddResponse);

        });

        btnCancel.setOnClickListener(v -> finish());
    }

    void updateAddResponse(Order order) {
//        if (null != order) {
            Toast.makeText(this, "Order Submitted successfully", Toast.LENGTH_SHORT).show();
            finish();
//        } else {
//            Toast.makeText(this, "Order Submitted failed", Toast.LENGTH_SHORT).show();
//        }
    }
}
