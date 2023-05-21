package com.example.myapplication;

import android.animation.ValueAnimator;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//선언한 MenuInfo를 펼치고 접을 수 있는 텍스트뷰로 만들어줍니다.
public class ViewHolderMenu extends RecyclerView.ViewHolder{
    TextView name;
    TextView cal;
    TextView car;
    TextView pro;
    TextView fat;
    LinearLayout nut, linearLayout;

    OnViewHolderItemClickListener onViewHolderItemClickListener;

    public ViewHolderMenu(@NonNull View itemView){
        super(itemView);

        name = itemView.findViewById(R.id.foodName);
        cal = itemView.findViewById(R.id.cal);
        car = itemView.findViewById(R.id.car);
        pro = itemView.findViewById(R.id.pro);
        fat = itemView.findViewById(R.id.fat);
        linearLayout = itemView.findViewById(R.id.linearLayout);
        nut = itemView.findViewById(R.id.nut);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onViewHolderItemClickListener.onViewHolderItemClick();
            }
        });
    }

    public void onBind(MenuInfo data, int position, SparseBooleanArray selectedItems){
        name.setText(data.getName());
        cal.setText(data.getCal());
        car.setText(data.getCar());
        pro.setText(data.getPro());
        fat.setText(data.getFat());
        changeVisibility(selectedItems.get(position));
    }

    private void changeVisibility(final boolean isExpanded){
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0,320) : ValueAnimator.ofInt(320, 0);
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
}
