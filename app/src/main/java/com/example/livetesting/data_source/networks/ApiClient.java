package com.example.livetesting.data_source.networks;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static ApiClient mInstance;
    private static Retrofit retrofit;

    ApiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiUtils.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // create a singleTone function
    public static synchronized ApiClient getInstance() {
        if (mInstance == null) {
            mInstance = new ApiClient();
        }
        return mInstance;
    }

    public static ApiServices getApiServices() {
        return retrofit.create(ApiServices.class);
    }
}
