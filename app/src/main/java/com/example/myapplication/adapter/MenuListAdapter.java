package com.example.myapplication.adapter;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.info.MenuInfo;
import com.example.myapplication.OnViewHolderItemClickListener;
import com.example.myapplication.R;
import com.example.myapplication.ViewHolderMenu;

import java.util.ArrayList;

//각 메뉴의 영양정보를 펼치고 접는 부분입니다.
public class MenuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MenuInfo> items = new ArrayList<MenuInfo>();
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private int prePosition = -1;

    public MenuListAdapter(ArrayList<MenuInfo> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list, parent, false);
        return new ViewHolderMenu(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        ViewHolderMenu viewHolderMenu = (ViewHolderMenu) holder;
        viewHolderMenu.onBind(items.get(position), position, selectedItems);
        viewHolderMenu.setOnViewHolderItemClickListener(new OnViewHolderItemClickListener() {
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
    public int getItemCount() {
        return items.size();
    }

    void addItem(MenuInfo data) {
        items.add(data);
    }
}