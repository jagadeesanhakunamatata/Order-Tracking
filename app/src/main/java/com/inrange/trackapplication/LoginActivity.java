package com.inrange.trackapplication;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.inrange.trackapplication.R;
import com.inrange.trackapplication.dto.LoginRequest;
import com.inrange.trackapplication.dto.LoginResponse;
import com.inrange.trackapplication.dto.Role;
import com.inrange.trackapplication.model.UserViewModel;
import com.inrange.trackapplication.module.home.HomeActivity;
import com.inrange.trackapplication.module.home.UserHomeActivity;
import com.inrange.trackapplication.snippet.PermissionHandler;

import java.util.List;


public class LoginActivity extends AppCompatActivity implements LoginView{

    private LoginPresenter mPresenter;
    private UserViewModel userViewModel;
    private PermissionHandler mPermissionHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter = LoginPresenterImpl.getInstance(this);
        findViewById(R.id.tvlogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();
            }
        });
        ((TextInputEditText)findViewById(R.id.etpassword)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    login();
                    return true;
                }
                return false;
            }
        });
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getLogin().observe(this,this::validateResponse);

        mPermissionHandler = PermissionHandler.getInstance(new PermissionHandler.PermissionHandleListener() {
            @Override
            public void onPermissionGranted(int requestCode) {
//                mLogInPresenter.onLocationPermissionGranted();
            }

            @Override
            public void onPermissionDenied(int requestCode) {

            }
        });
        mPermissionHandler.checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, 1,
                "Enable Location Permission");
    }

    void login(){
        LoginRequest request = new LoginRequest();
        request.setUsername(((TextInputEditText)findViewById(R.id.etusername)).getText().toString());
//        if(request.getUsername().toLowerCase().equals("customer")){
//            request.setPassword("$2a$08$u4eRExB5CAPAGD3CX83Ld.n16SfecMsw5xJOK9Jy676PnPynpiifG");
//        }else if(request.getUsername().toLowerCase().equals("courier")){
//            request.setPassword("$2a$08$u4eRExB5CAPAGD3CX83Ld.n16SfecMsw5xJOK9Jy676PnPynpiifG");
//        }else if(request.getUsername().toLowerCase().equals("admin")){
//            request.setPassword("$2a$08$u4eRExB5CAPAGD3CX83Ld.n16SfecMsw5xJOK9Jy676PnPynpiifG");
//        }
        request.setPassword(((TextInputEditText)findViewById(R.id.etpassword)).getText().toString());
        userViewModel.getLogin(request);
    }
    
    void validateResponse(LoginResponse response){

        if(null != response && null != response) {
            InrangeDataHandler.setLoginResponse(response);
            InRangeApplication.getApplication().setSharedBooleanData(Constants.AppKey.IS_LOGGED_IN, true);
            InRangeApplication.getApplication().setSharedStringData(Constants.AppKey.USERNAME, ((TextInputEditText)findViewById(R.id.etusername)).getText().toString());
            InRangeApplication.getApplication().setSharedStringData(Constants.AppKey.PWD, ((TextInputEditText)findViewById(R.id.etpassword)).getText().toString());
            navigateToObservationPage();
            navigateToObservationPage();
        } else {

            Toast.makeText(this,"Invalid Username & password",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void navigateToObservationPage() {
        InRangeApplication.getApplication().setSharedBooleanData(Constants.SharedKeys.IS_LOGIN_COMPLETED, true);
//        if(BuildConfig.FLAVOR.equals("user")) {
//            Intent intent = new Intent(this, UserHomeActivity.class);
//            startActivity(intent);
//        } else if(BuildConfig.FLAVOR.equals("delivery")){
//            Intent intent = new Intent(this, UserHomeActivity.class);
//            startActivity(intent);
//
//        } else if(BuildConfig.FLAVOR.equals("admin")){
//
//        }
        for (Role role :
                InrangeDataHandler.getLoginResponse().getRoles()) {
            if (role.getName().equals(Constants.Roles.ROLE_COURIER)) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else if (role.getName().equals(Constants.Roles.ROLE_CUSTOMER)) {
                Intent intent = new Intent(this, UserHomeActivity.class);
                startActivity(intent);
            }else if (role.getName().equals(Constants.Roles.ROLE_ADMIN)) {
                Intent intent = new Intent(this, AdminHomeActivity.class);
                startActivity(intent);
            }
        }
        finish();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(LoginActivity.this,msg, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
        finish();
    }
}
