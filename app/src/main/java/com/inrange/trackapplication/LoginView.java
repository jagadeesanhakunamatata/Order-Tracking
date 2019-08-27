package com.inrange.trackapplication;

public interface LoginView{

    void navigateToObservationPage();

    void showMessage(String msg);
}

interface LoginPresenter {

    void getLogin(String username, String password);

    void doAutoLogin();
}
