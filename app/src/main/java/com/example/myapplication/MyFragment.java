package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyFragment extends Fragment {

    public interface Listener{
        void onReceived(int channel);
    }
    private Listener listener;
    private static final String ARG_POSITION = "position";
    private int position;

    //점심과 저녁 중 어느 화면을 보는지를 액티비티에 보내려고 만들었습니다.
    public static MyFragment newInstance(int position) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(getActivity() != null && getActivity() instanceof Listener){
            listener = (Listener) getActivity();
        }
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
        //점심
        if (position == 0) {
            // 첫 번째 페이지에 대응하는 UI를 구성
            rootView = inflater.inflate(R.layout.fragment_lunch, container, false);
            // 첫 번째 페이지에 필요한 UI 요소들을 rootView에서 찾아서 처리
            // ...

            ArrayList<MenuInfo> items = new ArrayList<MenuInfo>();

            RecyclerView menuList = rootView.findViewById(R.id.nut_lun);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(rootView.getContext(),
                    3, GridLayoutManager.VERTICAL, false);
            MenuListAdapter adapter = new MenuListAdapter(items);
            menuList.setLayoutManager(gridLayoutManager);
            menuList.setAdapter(adapter);

            //메뉴 입력: items.add(new MenuInfo("이름","칼로리","탄수화물","단백질","지방"));
            items.add(new MenuInfo(
                    "lunch",
                    "cal:100",
                    "car:200",
                    "pro:300",
                    "fat:400"));
            items.add(new MenuInfo(
                    "lunch2",
                    "cal:100",
                    "car:200",
                    "pro:300",
                    "fat:400"));
            items.add(new MenuInfo(
                    "lunch3",
                    "cal:100",
                    "car:200",
                    "pro:300",
                    "fat:400"));
            items.add(new MenuInfo(
                    "lunch4",
                    "cal:100",
                    "car:200",
                    "pro:300",
                    "fat:400"));

        } else {//저녁
            // 두 번째 페이지에 대응하는 UI를 구성
            rootView = inflater.inflate(R.layout.fragment_dinner, container, false);
            // 두 번째 페이지에 필요한 UI 요소들을 rootView에서 찾아서 처리
            // ...

            ArrayList<MenuInfo> items = new ArrayList<MenuInfo>();

            RecyclerView menuList = rootView.findViewById(R.id.nut_din);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(rootView.getContext(),
                    3, GridLayoutManager.VERTICAL, false);
            MenuListAdapter adapter = new MenuListAdapter(items);
            menuList.setLayoutManager(gridLayoutManager);
            menuList.setAdapter(adapter);

            //메뉴 입력: items.add(new MenuInfo("이름","칼로리","탄수화물","단백질","지방"));
            items.add(new MenuInfo(
                    "food",
                    "cal:100",
                    "car:200",
                    "pro:300",
                    "fat:400"
            ));
            items.add(new MenuInfo(
                    "food2",
                    "cal:100",
                    "car:200",
                    "pro:300",
                    "fat:400"
            ));
            items.add(new MenuInfo(
                    "food3",
                    "cal:100",
                    "car:200",
                    "pro:300",
                    "fat:400"
            ));
            items.add(new MenuInfo(
                    "food4",
                    "cal:100",
                    "car:200",
                    "pro:300",
                    "fat:400"
            ));
        }
        return rootView;
    }
    @Override
    public void setUserVisibleHint(boolean isVisible){
        if(isVisible){//점심: 1 >> 0, 저녁: 0 >> 1
            Log.d("MyFragment", "if, "+position);
        }else{
            Log.d("MyFragment", "else, "+position);
        }
        if(listener != null) listener.onReceived(position);
        super.setUserVisibleHint(isVisible);
    }
}
