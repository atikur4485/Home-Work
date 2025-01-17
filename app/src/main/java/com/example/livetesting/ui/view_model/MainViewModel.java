package com.example.livetesting.ui.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.livetesting.data_source.models.CategoriesResponse;
import com.example.livetesting.data_source.repository.MainViewModelRepository;

import java.util.List;

public class MainViewModel extends ViewModel {
    private final MainViewModelRepository modelRepository;
    private static MainViewModel mInstance;
    private final MutableLiveData<String> error;
    // initialized
    MainViewModel() {
        modelRepository = MainViewModelRepository.getInstance();
        error = new MutableLiveData<>();
    }
    // checked and return the single tone object
    public static synchronized MainViewModel getInstance() {
        if (mInstance == null) {
            mInstance = new MainViewModel();
        }
        return mInstance;
    }

    // fetch categories data
    public LiveData<List<CategoriesResponse.categories>> getCategories() {
        return modelRepository.fetchCategories(error);
    }

    // fetch sub categories
    public LiveData<List<CategoriesResponse.categories>> getSubCategories(int pc_id) {
        return modelRepository.fetchSubCategories(error, pc_id);
    }

    // fetch error
    public MutableLiveData<String> getError() {
        return error;
    }
}
