package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GradeListAdapter extends RecyclerView.Adapter<GradeListAdapter.ViewHolder> {
    private ArrayList<ReviewActivity.ReviewInfo> items = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }

    GradeListAdapter(ArrayList<ReviewActivity.ReviewInfo> list){
        items = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.grade_list,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        ReviewActivity.ReviewInfo data = items.get(position);
        holder.name.setText(data.getName());
    }
    @Override
    public int getItemCount(){
        return items.size();
    }
}
