package com.inrange.trackapplication.retrofit;

import com.inrange.trackapplication.Constants;
import com.inrange.trackapplication.dto.GetOrdersResponse;
import com.inrange.trackapplication.dto.LoginRequest;
import com.inrange.trackapplication.dto.LoginResponse;
import com.inrange.trackapplication.dto.Order;
import com.inrange.trackapplication.dto.SignupRequest;
import com.inrange.trackapplication.dto.UserlistResponse;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by HMSPL on 01/11/17.
 */

public class ApiClient {

    private static Retrofit retrofit = null;
    private static APIClientService appRetrofitService;

    public static APIClientService getEndPointService() {

        if (null == appRetrofitService) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(3, TimeUnit.MINUTES);
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

            // Can be Level.BASIC, Level.HEADERS, or Level.BODY
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.networkInterceptors().add(httpLoggingInterceptor);

            Retrofit retrofit = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
                retrofit = new Retrofit.Builder()
                        .client(builder.connectTimeout(3, TimeUnit.MINUTES)
                                .readTimeout(3,TimeUnit.MINUTES).build())
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }

            appRetrofitService = retrofit.create(APIClientService.class);
        }
        return appRetrofitService;
    }

    public interface APIClientService {

        @POST("users/login")
        Call<LoginResponse> getLogin(@Body LoginRequest request);

        @POST("auth/register")
        Call<LoginResponse> getSignup(@Body SignupRequest request);

        @GET("orders")
        Call<GetOrdersResponse> getOrders();

        @POST("orders")
        Call<Order> addOrder(@Body Order order);

        @GET("users")
        Call<UserlistResponse> getCustomers();

        @GET("users")
        Call<UserlistResponse> getCouriers();


    }
}
