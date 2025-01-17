package com.example.livetesting.ui.adapter;

import com.example.livetesting.data_source.models.CategoriesResponse;

public interface RcvItemClickListener {
    void onClicked(CategoriesResponse.categories categories);
}
