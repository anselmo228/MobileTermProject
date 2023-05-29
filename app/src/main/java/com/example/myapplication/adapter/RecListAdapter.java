package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.OnViewHolderItemClickListener;
import com.example.myapplication.R;
import com.example.myapplication.ViewHolderRec;
import com.example.myapplication.info.RecInfo;

import java.util.ArrayList;

public class RecListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<RecInfo> items = new ArrayList<RecInfo>();
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private int prePosition = -1;

    public RecListAdapter(ArrayList<RecInfo> items){this.items = items;}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_list, parent, false);
        return new ViewHolderRec(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,@SuppressLint("RecyclerView") final int position) {
        ViewHolderRec viewHolderRec = (ViewHolderRec) holder;
        RecInfo recInfo = items.get(position);

        float rating = recInfo.getPoint();

        viewHolderRec.setRating(rating);

        viewHolderRec.onBind(items.get(position),position,selectedItems);
        viewHolderRec.setOnViewHolderItemClickListener(new OnViewHolderItemClickListener() {
            @Override
            public void onViewHolderItemClick() {
                if (selectedItems.get(position)) {
                    selectedItems.delete(position);
                } else {
                    selectedItems.delete(prePosition);
                    selectedItems.put(position, true);
                }
                if (prePosition != -1) notifyItemChanged(prePosition);
                notifyItemChanged(position);
                prePosition = position;
            }
        });
    }

    @Override
    public int getItemCount(){return items.size();}
    void addItem(RecInfo data){items.add(data);}
}
