package com.example.cs360_nii_tagoe_option3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.WeightViewHolder> {
    private Context context;
    private ArrayList<WeightHolder> weightList;
    private DataBase db;
    private RefreshListener refreshListener;

    public interface RefreshListener {
        void onRefresh();
    }

    public WeightAdapter(Context context, ArrayList<WeightHolder> weightList, DataBase db, RefreshListener refreshListener) {
        this.context = context;
        this.weightList = weightList;
        this.db = db;
        this.refreshListener = refreshListener;
    }

    @NonNull
    @Override
    public WeightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_weight, parent, false);
        return new WeightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeightViewHolder holder, int position) {
        WeightHolder weightHolder = weightList.get(position);
        holder.tvItemDate.setText(weightHolder.getDate());
        holder.tvItemWeight.setText(weightHolder.getWeight());

        holder.btnDeleteItem.setOnClickListener(v -> {
            boolean deleted = db.deleteWeight(weightHolder.getId());
            if(deleted){
                refreshListener.onRefresh();
            }
        });
    }

    @Override
    public int getItemCount() {
        return weightList.size();
    }

    public class WeightViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemDate, tvItemWeight;
        Button btnDeleteItem;

        public WeightViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemDate = itemView.findViewById(R.id.tvItemDate);
            tvItemWeight = itemView.findViewById(R.id.tvItemWeight);
            btnDeleteItem = itemView.findViewById(R.id.btnDeleteItem);
        }
    }
}
