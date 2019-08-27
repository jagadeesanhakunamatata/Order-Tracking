package com.inrange.trackapplication.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.inrange.trackapplication.Constants;
import com.inrange.trackapplication.InRangeApplication;
import com.inrange.trackapplication.dto.LoginRequest;
import com.inrange.trackapplication.dto.LoginResponse;
import com.inrange.trackapplication.dto.SignupRequest;
import com.inrange.trackapplication.dto.UserlistResponse;
import com.inrange.trackapplication.retrofit.ApiClient;
import com.inrange.trackapplication.retrofit.BaseListener;
import com.inrange.trackapplication.retrofit.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

    public class UserViewModel extends ViewModel {

        public Context context;
        public Object modelListener;

        private MutableLiveData<LoginResponse> mLoginResponseLiveData = new MutableLiveData<>();
        private MutableLiveData<LoginResponse> mSignupResponseLiveData = new MutableLiveData<>();
        private MutableLiveData<UserlistResponse> mUserlistResponseLiveData = new MutableLiveData<>();

        private <T> void execute(Call<T> call, final Callback listener) {

            Utils.showProgressbar(context);
            if (InRangeApplication.getApplication().hasNetworkConnection()) {
                call.enqueue(new Callback<T>() {
                                 @Override
                                 public void onResponse(Call<T> call, Response<T> response) {
                                     Utils.dismissProgressbar(context);
                                     listener.onResponse(call, response);
                                 }

                                 @Override
                                 public void onFailure(Call<T> call, Throwable t) {
//                                 CustomProgressbar.getProgressbar(context).dismiss();
                                     Utils.dismissProgressbar(context);
                                     listener.onFailure(call, t);
                                     if (null != modelListener && modelListener instanceof BaseListener) {
                                         ((BaseListener) modelListener).failureMsg(t.getLocalizedMessage());
                                     }
                                 }
                             }

                );
            } else {

                if (null != modelListener && modelListener instanceof BaseListener) {
                    ((BaseListener) modelListener).failureMsg(Constants.AlertMessage.NO_INTERNET_CONNECTION);
                }
            }
        }

        private <T> void executewithoutProgress(Call<T> call, final Callback<T> listener) {
            if (InRangeApplication.getApplication().hasNetworkConnection()) {
                call.enqueue(new Callback<T>() {
                                 @Override
                                 public void onResponse(Call<T> call, Response<T> response) {
                                     listener.onResponse(call, response);
                                 }

                                 @Override
                                 public void onFailure(Call<T> call, Throwable t) {
                                     listener.onFailure(call, t);
                                 }
                             }

                );
            }
        }


        public UserViewModel() {

        }

        public LiveData<LoginResponse> getLogin(final LoginRequest request) {

            Call<LoginResponse> call = ApiClient.getEndPointService().getLogin(request);
            execute(call, new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    mLoginResponseLiveData.postValue(response.body());
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.e("LoginResponse",t.getLocalizedMessage());
                }
            });
            return mLoginResponseLiveData;
        }

        public LiveData<LoginResponse> getSignup(final SignupRequest request) {

            Call<LoginResponse> call = ApiClient.getEndPointService().getSignup(request);
            execute(call, new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    mLoginResponseLiveData.postValue(response.body());
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
//                mListener.failureMsg(t.toString());
                }
            });
            return mLoginResponseLiveData;
        }

        public LiveData<UserlistResponse> getCustomers() {

            Call<UserlistResponse> call = ApiClient.getEndPointService().getCustomers();
            execute(call, new Callback<UserlistResponse>() {
                @Override
                public void onResponse(Call<UserlistResponse> call, Response<UserlistResponse> response) {

                    mUserlistResponseLiveData.postValue(response.body());
                }

                @Override
                public void onFailure(Call<UserlistResponse> call, Throwable t) {
//                mListener.failureMsg(t.toString());
                }
            });
            return mUserlistResponseLiveData;
        }

        public LiveData<LoginResponse> getLogin() {
            return mLoginResponseLiveData;
        }


        public LiveData<LoginResponse> getSignup() {
            return mSignupResponseLiveData;
        }
    }