package com.example.livetesting.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livetesting.R;
import com.example.livetesting.data_source.models.CategoriesResponse;
import com.example.livetesting.databinding.RcvItemDesignBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RcvAdapter extends RecyclerView.Adapter<RcvAdapter.ViewHolder> {
    private final List<CategoriesResponse.categories> categoriesList;
    // custom item click listener for provide categories object
    private final RcvItemClickListener itemClickListener;

    public RcvAdapter(List<CategoriesResponse.categories> categoriesList, RcvItemClickListener itemClickListener) {
        this.categoriesList = categoriesList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_item_design, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.nameTvId.setText(getSortedList(categoriesList).get(position));
        holder.itemView.setOnClickListener(view -> itemClickListener.onClicked(categoriesList.get(position)));
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RcvItemDesignBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RcvItemDesignBinding.bind(itemView);
        }
    }
    //Sort the list items in ascending order (A...Z) and return it
    public List<String> getSortedList(List<CategoriesResponse.categories> categoriesList) {
        List<String> sortedList = new ArrayList<>();
        for (CategoriesResponse.categories categories : categoriesList) {
            sortedList.add(categories.getName());
        }
        Collections.sort(sortedList);
        return sortedList;
    }
}
