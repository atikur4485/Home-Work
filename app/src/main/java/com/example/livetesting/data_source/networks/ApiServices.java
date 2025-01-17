package com.example.livetesting.data_source.networks;

import com.example.livetesting.data_source.models.CategoriesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {
    @GET(ApiUtils.bodyUrlForCategory)
    Call<CategoriesResponse> getCategories();
    // call for sub catagory
    @GET(ApiUtils.bodyUrlForCategory)
    Call<CategoriesResponse> getSubCategorise(@Query("parent_id")int pc_id);
}
