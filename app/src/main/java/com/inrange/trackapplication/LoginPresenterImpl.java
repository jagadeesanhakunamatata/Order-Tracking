package com.inrange.trackapplication;

import com.inrange.trackapplication.dto.LoginRequest;
import com.inrange.trackapplication.dto.LoginResponse;

import java.util.logging.Handler;

public class LoginPresenterImpl implements LoginPresenter, LoginListener {
    private static LoginView loginView;
    private final LoginModel mLoginModel;
    private Handler mHandler;

    public static LoginPresenter getInstance(LoginView view) {
        return new LoginPresenterImpl(view);
    }

    LoginPresenterImpl(LoginView view) {

        loginView = view;
        mLoginModel = LoginModelImpl.getInstance(this);
    }

    @Override
    public void updateLoginResponse(LoginResponse response) {
//        if (null != response && null != response.getToken()) {
//            loginView.navigateToObservationPage();
//            InrangeDataHandler.setAuthToken(response.getToken());
//        }
    }

    @Override
    public void getLogin(String username, String password) {

        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        mLoginModel.getLogin(request);
    }

    @Override
    public void doAutoLogin() {
        LoginRequest request = new LoginRequest();
        request.setUsername(InRangeApplication.getApplication().getSharedStringData(Constants.SharedKeys.LOGIN_USER_NAME));
        request.setPassword(InRangeApplication.getApplication().getSharedStringData(Constants.SharedKeys.LOGIN_USER_PASSWORD));
        mLoginModel.getLogin(request);
    }

    @Override
    public void failureMsg(String msg) {

        loginView.showMessage(msg);
    }
}
