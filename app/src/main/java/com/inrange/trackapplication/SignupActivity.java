package com.inrange.trackapplication;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.inrange.trackapplication.dto.LoginResponse;
import com.inrange.trackapplication.dto.Role;
import com.inrange.trackapplication.dto.SignupRequest;
import com.inrange.trackapplication.model.UserViewModel;
import com.inrange.trackapplication.module.home.UserHomeActivity;

public class SignupActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    EditText etUsername, etpwd, etcpwd,etemail,etmobile;
    TextView tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getLogin().observe(this, this::validateResponse);
        etUsername = findViewById(R.id.activity_signup_et_username);
        etpwd = findViewById(R.id.activity_signup_et_pwd);
        etcpwd = findViewById(R.id.activity_signup_et_cpwd);
        etemail = findViewById(R.id.activity_signup_et_email);
        etmobile = findViewById(R.id.activity_signup_et_mobile);
        tvSignup = findViewById(R.id.activity_signup_tv_signup);
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupRequest req = new SignupRequest();
                req.setUsername(etUsername.getText().toString());
                req.setFullname(etUsername.getText().toString());
                req.setPassword(etpwd.getText().toString());
                req.setEmail(etemail.getText().toString());
                Role role = new Role();
                if (BuildConfig.FLAVOR.equals("user")) {
                    req.setCustomer(true);
                    req.setCourier(false);
                    role.setName(Constants.Roles.ROLE_CUSTOMER);
                } else if (BuildConfig.FLAVOR.equals("delivery")) {
                    req.setCourier(true);
                    req.setCustomer(false);
                    role.setName(Constants.Roles.ROLE_COURIER);
                } else if (BuildConfig.FLAVOR.equals("admin")) {
                    req.setCustomer(false);
                    req.setCourier(false);
                    role.setName(Constants.Roles.ROLE_ADMIN);
                }
                req.setEnabled(true);
                req.setId(1);
                req.getRoles().add(role);
                userViewModel.getSignup(req);
            }
        });
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    void validateResponse(LoginResponse response) {

        if (null != response && null != response) {
            navigateToLoginPage();
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void navigateToLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
