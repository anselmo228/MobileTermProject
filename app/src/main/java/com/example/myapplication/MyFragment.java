package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.MenuListAdapter;
import com.example.myapplication.info.MenuInfo;

import java.util.ArrayList;

public class MyFragment extends Fragment {

    public interface Listener {
        void onReceived(int channel);
    }

    private Listener listener;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof Listener) {
            listener = (Listener) getActivity();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        if (position == 0) {
            rootView = inflater.inflate(R.layout.fragment_lunch, container, false);
            // ...

            ArrayList<MenuInfo> lunch500List = new ArrayList<MenuInfo>();

            RecyclerView menuList = rootView.findViewById(R.id.nut_lun);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext(),
                    LinearLayoutManager.VERTICAL, false);
            MenuListAdapter adapter = new MenuListAdapter(lunch500List);
            menuList.setLayoutManager(linearLayoutManager);
            menuList.setAdapter(adapter);

            // 메뉴 입력: lunch500List.add(new MenuInfo("이름","칼로리","탄수화물","단백질","지방"));
            lunch500List.add(new MenuInfo(
                    "lunch",
                    100,
                    200,
                    300,
                    400));
            lunch500List.add(new MenuInfo(
                    "lunch2",
                    100,
                    200,
                    300,
                    400));
            lunch500List.add(new MenuInfo(
                    "lunch3",
                    100,
                    200,
                    300,
                    400));
            lunch500List.add(new MenuInfo(
                    "lunch4",
                    100,
                    200,
                    300,
                    400));
        } else if (position == 1) {
            rootView = inflater.inflate(R.layout.fragment_lunch600, container, false);
            // ...

            ArrayList<MenuInfo> lunch600List = new ArrayList<MenuInfo>();

            RecyclerView menuList = rootView.findViewById(R.id.nut_lun600);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext(),
                    LinearLayoutManager.VERTICAL, false);
            MenuListAdapter adapter = new MenuListAdapter(lunch600List);
            menuList.setLayoutManager(linearLayoutManager);
            menuList.setAdapter(adapter);

            // 메뉴 입력: lunch600List.add(new MenuInfo("이름","칼로리","탄수화물","단백질","지방"));
            lunch600List.add(new MenuInfo(
                    "lunch600",
                    100,
                    200,
                    300,
                    400));
            lunch600List.add(new MenuInfo(
                    "lunch600-2",
                    100,
                    200,
                    300,
                    400));
            lunch600List.add(new MenuInfo(
                    "lunch600-3",
                    100,
                    200,
                    300,
                    400));
            lunch600List.add(new MenuInfo(
                    "lunch600-4",
                    100,
                    200,
                    300,
                    400));
        } else {
            rootView = inflater.inflate(R.layout.fragment_dinner, container, false);
            // ...

            ArrayList<MenuInfo> dinnerList = new ArrayList<MenuInfo>();

            RecyclerView menuList = rootView.findViewById(R.id.nut_din);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext(),
                    LinearLayoutManager.VERTICAL, false);
            MenuListAdapter adapter = new MenuListAdapter(dinnerList);
            menuList.setLayoutManager(linearLayoutManager);
            menuList.setAdapter(adapter);

            // 메뉴 입력: dinnerList.add(new MenuInfo("이름","칼로리","탄수화물","단백질","지방"));
            dinnerList.add(new MenuInfo(
                    "dinner",
                    100,
                    200,
                    300,
                    400));
            dinnerList.add(new MenuInfo(
                    "dinner2",
                    100,
                    200,
                    300,
                    400));
            dinnerList.add(new MenuInfo(
                    "dinner3",
                    100,
                    200,
                    300,
                    400));
            dinnerList.add(new MenuInfo(
                    "dinner4",
                    100,
                    200,
                    300,
                    400));
        }

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisible) {
        if (isVisible) { // 점심500:0, 점심600:1, 저녁:2
            Log.d("MyFragment", "position: " + position);
        }
        if (listener != null) listener.onReceived(position);
        super.setUserVisibleHint(isVisible);
    }
}
