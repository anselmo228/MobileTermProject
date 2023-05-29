package com.example.myapplication;

import android.animation.ValueAnimator;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.info.RecInfo;

public class ViewHolderRec extends RecyclerView.ViewHolder {
    TextView name, cal, car, pro, fat;
    RatingBar star;
    LinearLayout nut, recommendLayout;
    OnViewHolderItemClickListener onViewHolderItemClickListener;

    public ViewHolderRec(@NonNull View itemView){
        super(itemView);
        name = itemView.findViewById(R.id.name);
        cal = itemView.findViewById(R.id.cal);
        car = itemView.findViewById(R.id.car);
        pro = itemView.findViewById(R.id.pro);
        fat = itemView.findViewById(R.id.fat);
        recommendLayout = itemView.findViewById(R.id.recommend_layout);
        star = itemView.findViewById(R.id.star);
        nut = itemView.findViewById(R.id.nut);

        recommendLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                onViewHolderItemClickListener.onViewHolderItemClick();
            }
        });
    }

    public void onBind(RecInfo data, int position, SparseBooleanArray selectedItems){
        name.setText(data.getName());
        cal.setText("칼로리: "+Float.toString(data.getCal()));
        car.setText("탄수화물: "+Float.toString(data.getCar()));
        pro.setText("단백질: "+Float.toString(data.getPro()));
        fat.setText("지방: "+Float.toString(data.getFat()));
        changeVisibility(selectedItems.get(position));
    }
    private void changeVisibility(final boolean isExpanded){
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0,320) : ValueAnimator.ofInt(320,0);
        va.setDuration(500);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                nut.getLayoutParams().height = (int) animation.getAnimatedValue();
                nut.requestLayout();
                nut.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            }
        });
        va.start();
    }
    public void setOnViewHolderItemClickListener(OnViewHolderItemClickListener onViewHolderItemClickListener){
        this.onViewHolderItemClickListener = onViewHolderItemClickListener;
    }
    public void setRating(float rating){
        star.setRating(rating);
    }
}
