package com.inrange.trackapplication;

import android.os.Handler;

import com.inrange.trackapplication.dto.LoginRequest;
import com.inrange.trackapplication.dto.LoginResponse;
import com.inrange.trackapplication.retrofit.ApiClient;
import com.inrange.trackapplication.retrofit.BaseListener;
import com.inrange.trackapplication.retrofit.BaseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginModelImpl extends BaseModel implements LoginModel {
    private final LoginListener mListener;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private String mMachineID;
    private String mDate;
    private String mShiftID;

    public static LoginModel getInstance(LoginListener listener) {
        return new LoginModelImpl(listener);
    }

    LoginModelImpl(LoginListener listener) {
        mListener = listener;
    }

    @Override
    public void getLogin(final LoginRequest request) {

        Call<LoginResponse> call = ApiClient.getEndPointService().getLogin(request);
        execute(call, new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(null != response && null != response.body()) {
                    mListener.updateLoginResponse(response.body());
                    InRangeApplication.getApplication().setSharedBooleanData(Constants.SharedKeys.IS_LOGIN_COMPLETED, true);
                    InRangeApplication.getApplication().setSharedStringData(Constants.SharedKeys.LOGIN_USER_NAME, request.getUsername());
                    InRangeApplication.getApplication().setSharedStringData(Constants.SharedKeys.LOGIN_USER_PASSWORD, request.getPassword());
                } else {

                    mListener.failureMsg("Invalid Username & password");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mListener.failureMsg(t.toString());
            }
        });
    }
}

interface LoginListener extends BaseListener {

    void updateLoginResponse(LoginResponse response);
}
