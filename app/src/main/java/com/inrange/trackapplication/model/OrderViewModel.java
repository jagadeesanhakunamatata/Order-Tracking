package com.inrange.trackapplication.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.inrange.trackapplication.Constants;
import com.inrange.trackapplication.InRangeApplication;
import com.inrange.trackapplication.dto.GetOrdersResponse;
import com.inrange.trackapplication.dto.LoginRequest;
import com.inrange.trackapplication.dto.LoginResponse;
import com.inrange.trackapplication.dto.Order;
import com.inrange.trackapplication.dto.SignupRequest;
import com.inrange.trackapplication.retrofit.ApiClient;
import com.inrange.trackapplication.retrofit.BaseListener;
import com.inrange.trackapplication.retrofit.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderViewModel extends ViewModel {

    public Context context;
    public Object modelListener;

    private MutableLiveData<GetOrdersResponse> mGetOrdersResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<Order> mAddOrdersResponseLiveData = new MutableLiveData<>();

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


    public OrderViewModel() {

    }

    public LiveData<GetOrdersResponse> getOrders() {

        Call<GetOrdersResponse> call = ApiClient.getEndPointService().getOrders();
        execute(call, new Callback<GetOrdersResponse>() {
            @Override
            public void onResponse(Call<GetOrdersResponse> call, Response<GetOrdersResponse> response) {

                mGetOrdersResponseLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<GetOrdersResponse> call, Throwable t) {
//                mListener.failureMsg(t.toString());
            }
        });
        return mGetOrdersResponseLiveData;
    }

    public LiveData<Order> addOrder(Order order) {

        Call<Order> call = ApiClient.getEndPointService().addOrder(order);
        execute(call, new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {

                mAddOrdersResponseLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
//                mListener.failureMsg(t.toString());
            }
        });
        return mAddOrdersResponseLiveData;
    }
}