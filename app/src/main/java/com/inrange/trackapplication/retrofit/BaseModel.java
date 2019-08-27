package com.inrange.trackapplication.retrofit;

import android.content.Context;

import com.inrange.trackapplication.Constants;
import com.inrange.trackapplication.InRangeApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jaga on 11/3/2017.
 */
public class BaseModel {

    public Context context;
    public Object modelListener;

    public <T> void execute(Call<T> call, final Callback listener) {

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

            ((BaseListener) modelListener).failureMsg(Constants.AlertMessage.NO_INTERNET_CONNECTION);
        }
    }

    public <T> void executewithoutProgress(Call<T> call, final Callback<T> listener) {
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
}

