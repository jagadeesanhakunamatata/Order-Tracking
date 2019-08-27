package com.inrange.trackapplication;

import com.inrange.trackapplication.dto.LoginResponse;

public class InrangeDataHandler {

    private static String mToken;
    private static LoginResponse mloginResponse;

    public static void setAuthToken(String token) {
        mToken = token;
    }

    public static String getAuthToken() {
        return "Bearer "+mToken;
    }

    public static LoginResponse getLoginResponse() {
        return mloginResponse;
    }

    public static void setLoginResponse(LoginResponse mloginResponse) {
        InrangeDataHandler.mloginResponse = mloginResponse;
    }
}
