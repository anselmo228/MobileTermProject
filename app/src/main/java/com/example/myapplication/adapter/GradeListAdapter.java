package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.info.ReviewInfo;

import java.util.ArrayList;

public class GradeListAdapter extends RecyclerView.Adapter<GradeListAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }
    public interface OnRecordEventListener{
        void onRatingBarChange(ReviewInfo item, float value, int position);
    }
    private ArrayList<ReviewInfo> items = new ArrayList<>();
    private OnRecordEventListener mClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private RatingBar star;

        ViewHolder(View itemView, OnRecordEventListener mClickListener){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            star = itemView.findViewById(R.id.star);

            star.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    if(b){
                        int position = getAdapterPosition();
                        GradeListAdapter.this.mClickListener.onRatingBarChange(items.get(getLayoutPosition()), v, position);
//                        items.get(getLayoutPosition()).setRating(v);
                    }
                }
            });
        }
    }
    public GradeListAdapter(ArrayList<ReviewInfo> list, OnRecordEventListener listener){
        items = list;
        mClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.grade_list,parent,false);
        ViewHolder vh = new ViewHolder(view, mClickListener);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        ReviewInfo data = items.get(position);
        holder.name.setText(data.getName());
    }
    @Override
    public int getItemCount(){
        return items.size();
    }
}



























