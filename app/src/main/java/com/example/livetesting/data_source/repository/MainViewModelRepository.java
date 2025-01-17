package com.example.livetesting.data_source.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.livetesting.data_source.models.CategoriesResponse;
import com.example.livetesting.data_source.networks.ApiClient;
import com.example.livetesting.data_source.networks.ApiServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModelRepository {
    private static MainViewModelRepository mInstance;
    private final ApiServices apiServices;
    // initialized
    MainViewModelRepository() {
        ApiClient.getInstance();
        apiServices = ApiClient.getApiServices();
    }

    // checked and return the single tone object
    public static synchronized MainViewModelRepository getInstance() {
        if (mInstance == null) {
            mInstance = new MainViewModelRepository();
        }
        return mInstance;
    }
    // call category api and enqueue Categories and return the categories list
    public LiveData<List<CategoriesResponse.categories>> fetchCategories(MutableLiveData<String> error) {
        MutableLiveData<List<CategoriesResponse.categories>> categoryList = new MutableLiveData<>();
        apiServices.getCategories().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CategoriesResponse> call, @NonNull Response<CategoriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("ApiResonse", "" + response.body().getCategoriesList());
                    categoryList.setValue(response.body().getCategoriesList());
                } else {
                    categoryList.setValue(null);
                    error.postValue(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoriesResponse> call, @NonNull Throwable throwable) {
                categoryList.setValue(null);
                error.postValue(throwable.getLocalizedMessage());
            }
        });

        return categoryList;
    }

    // call sub category api and enqueue subCategories and return the sub categories list
    public LiveData<List<CategoriesResponse.categories>> fetchSubCategories(MutableLiveData<String> error, int pc_id) {
        MutableLiveData<List<CategoriesResponse.categories>> subCategoriesList = new MutableLiveData<>();
        apiServices.getSubCategorise(pc_id).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<CategoriesResponse> call, @NonNull Response<CategoriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e("ApiResonse", "" + response.body().getCategoriesList());
                    subCategoriesList.setValue(response.body().getCategoriesList());
                } else {
                    subCategoriesList.setValue(null);
                    error.postValue(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategoriesResponse> call, @NonNull Throwable throwable) {
                subCategoriesList.setValue(null);
                error.postValue(throwable.getLocalizedMessage());
            }
        });

        return subCategoriesList;
    }
}
