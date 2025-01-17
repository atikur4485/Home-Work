package com.example.livetesting.ui.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import kotlin.reflect.KClass;

public class MainViewModelProviderFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull KClass<T> modelClass, @NonNull CreationExtras extras) {
        return (T) MainViewModel.getInstance();
    }
}
