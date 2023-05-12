package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private int position;

    public static MyFragment newInstance(int position) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        if (position == 0) {
            // 첫 번째 페이지에 대응하는 UI를 구성
            rootView = inflater.inflate(R.layout.fragment_lunch, container, false);
            // 첫 번째 페이지에 필요한 UI 요소들을 rootView에서 찾아서 처리
            // ...
        } else {
            // 두 번째 페이지에 대응하는 UI를 구성
            rootView = inflater.inflate(R.layout.fragment_dinner, container, false);
            // 두 번째 페이지에 필요한 UI 요소들을 rootView에서 찾아서 처리
            // ...
        }
        return rootView;
    }
}
