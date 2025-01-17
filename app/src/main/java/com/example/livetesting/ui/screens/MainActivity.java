package com.example.livetesting.ui.screens;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livetesting.ui.adapter.RcvAdapter;
import com.example.livetesting.data_source.models.CategoriesResponse;
import com.example.livetesting.ui.view_model.MainViewModel;
import com.example.livetesting.ui.view_model.MainViewModelProviderFactory;
import com.example.livetesting.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressLint("NotifyDataSetChanged")
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private final List<CategoriesResponse.categories> categoriesList = new ArrayList<>();
    private RcvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // initialized the view model
        viewModel = new ViewModelProvider(this, new MainViewModelProviderFactory()).get(MainViewModel.class);
        // call and observe the categories list
        viewModel.getCategories().observe(MainActivity.this, categories -> {
            categoriesList.clear();
            categoriesList.addAll(categories);
            adapter.notifyDataSetChanged();
        });
        // call and observe the api error
        viewModel.getError().observe(MainActivity.this, s -> Log.e("ApiError", s));
        // populate the recycler view
        populateRecyclerView();
    }

    // this function are initialized the recycler adapter and populate data to the recycler view
    private void populateRecyclerView() {
        binding.rcvId.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RcvAdapter(categoriesList, categories -> {
            if (categories.getChild_count() > 0) {
                viewModel.getSubCategories(categories.getPc_id()).observe(MainActivity.this, list -> {
                    categoriesList.clear();
                    categoriesList.addAll(list);
                    adapter.notifyDataSetChanged();
                });
            }
        });
        binding.rcvId.setAdapter(adapter);
        // drag,drop and swap to delete
        new ItemTouchHelper(getSimpleCallBack(categoriesList)).attachToRecyclerView(binding.rcvId);
    }

    // this function are return the simple call back
    private ItemTouchHelper.SimpleCallback getSimpleCallBack(List<CategoriesResponse.categories> categoriesList) {
        return new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            // move recycler item worked is hear
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                Collections.swap(categoriesList, fromPosition, toPosition);
                Objects.requireNonNull(recyclerView.getAdapter()).notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            // swipe left or swipe right to delete item
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int item = viewHolder.getAdapterPosition();
                categoriesList.remove(item);
                adapter.notifyItemRemoved(item);
            }
            
        };
    }

}