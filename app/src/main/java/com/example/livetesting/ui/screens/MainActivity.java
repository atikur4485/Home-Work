package com.example.livetesting.ui.screens;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livetesting.ui.adapter.RcvAdapter;
import com.example.livetesting.data_source.models.CategoriesResponse;
import com.example.livetesting.ui.view_model.MainViewModel;
import com.example.livetesting.ui.view_model.MainViewModelProviderFactory;
import com.example.livetesting.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressLint("NotifyDataSetChanged")
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private final ArrayList<CategoriesResponse.categories> categoriesList = new ArrayList<>();
    private RcvAdapter adapter;
    private boolean isItemClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // initialized the view model
        viewModel = new ViewModelProvider(this, new MainViewModelProviderFactory()).get(MainViewModel.class);
        // item touch helper for drag and drop recycler item and swipe to delete the item
        new ItemTouchHelper(getSimpleCallBack(categoriesList)).attachToRecyclerView(binding.rcvId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // hist the api and observe the list
        getCategoriesList();
        // hit the api and observe the api error
        viewModel.getError().observe(MainActivity.this, this::showError);
        // this function is populate the data in the recycler view
        populateRecyclerView();
        // handle the back pressed
        getBackPressedHandler();
    }

    // initialize the list
    private void getCategoriesList() {
        // call the api and observe the categories list
        viewModel.getCategories().observe(MainActivity.this, categories -> {
            categoriesList.clear();
            categoriesList.addAll(getSortedList(categories));
            adapter.notifyDataSetChanged();
        });
    }

    // this function are initialized the recycler adapter and populate data to the recycler view
    private void populateRecyclerView() {
        binding.rcvId.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RcvAdapter(categoriesList, categories -> {
            // check the child count is above to 0 that case hit the api otherwise do nothing
            if (categories.getChild_count() > 0) {
                viewModel.getSubCategories(categories.getPc_id()).observe(MainActivity.this, list -> {
                    categoriesList.clear();
                    categoriesList.addAll(getSortedList(list));
                    adapter.notifyDataSetChanged();
                    isItemClicked = true;
                });
            }
        });
        binding.rcvId.setAdapter(adapter); // set the adapter
    }

    // this function are return the simple call back
    private ItemTouchHelper.SimpleCallback getSimpleCallBack(List<CategoriesResponse.categories> categoriesList) {
        return new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            // move recycler item worked is hear
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
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

    //  handle the back pressed
    private void getBackPressedHandler() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // if item is clicked that case call the same api other wise go back or
                if (isItemClicked) {
                    getCategoriesList();
                    isItemClicked = false;
                } else {
                    this.setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }

            }
        });
    }

    // show the api error
    private void showError(String error) {
        Snackbar.make(binding.getRoot(), "The Error is :" + error, 2000).show();
    }

    //Sort the list items in ascending order (A...Z) and return it
    public List<CategoriesResponse.categories> getSortedList(List<CategoriesResponse.categories> categoriesList) {

        categoriesList.sort((categories, categories1) -> categories.getName().compareTo(categories1.getName()));
        return categoriesList;
    }
}