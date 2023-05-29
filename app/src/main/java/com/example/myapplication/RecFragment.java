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

import com.example.myapplication.adapter.RecListAdapter;
import com.example.myapplication.info.MenuInfo;
import com.example.myapplication.info.RecInfo;

import java.util.ArrayList;

public class RecFragment extends Fragment {
    public interface Listener{
        void onReceived(int channel);
    }
    private Listener listener;
    private static final String ARG_POSITION = "position";
    private int position;

    public static RecFragment newInstance(int position) {
        RecFragment fragment = new RecFragment();
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
            rootView = inflater.inflate(R.layout.fragment_rec_lunch, container, false);
            // ...

            ArrayList<RecInfo> items = new ArrayList<RecInfo>();
            ArrayList<MenuInfo> menus = new ArrayList<MenuInfo>();

            RecyclerView recList = rootView.findViewById(R.id.nut_lun);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext(),
                    LinearLayoutManager.VERTICAL, false);
            RecListAdapter adapter = new RecListAdapter(items);
            recList.setLayoutManager(linearLayoutManager);
            recList.setAdapter(adapter);

            // 메뉴 입력: items.add(new MenuInfo("이름","칼로리","탄수화물","단백질","지방"));
            //RecInfo(String name, float point, Context context, ArrayList<MenuInfo> menuList)
            //메뉴 입력: items.add(new RecInfo("이름",점수,this,메뉴리스트);
            menus.add(new MenuInfo(
                    "lunch",
                    100,
                    200,
                    300,
                    400));
            menus.add(new MenuInfo(
                    "lunch2",
                    100,
                    200,
                    300,
                    400));
            menus.add(new MenuInfo(
                    "lunch3",
                    100,
                    200,
                    300,
                    400));
            menus.add(new MenuInfo(
                    "lunch4",
                    100,
                    200,
                    300,
                    400));
            items.add(new RecInfo("학생식당",(float)3.5, getActivity().getApplicationContext(), menus));
            items.add(new RecInfo("예술식당",(float)4.2, getActivity().getApplicationContext(), menus));
            items.add(new RecInfo("대학식당",(float)1.5, getActivity().getApplicationContext(), menus));
        }else {
            rootView = inflater.inflate(R.layout.fragment_rec_dinner, container, false);
            // ...

            ArrayList<RecInfo> items = new ArrayList<RecInfo>();
            ArrayList<MenuInfo> menus = new ArrayList<MenuInfo>();

            RecyclerView recList = rootView.findViewById(R.id.nut_din);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext(),
                    LinearLayoutManager.VERTICAL, false);
            RecListAdapter adapter = new RecListAdapter(items);
            recList.setLayoutManager(linearLayoutManager);
            recList.setAdapter(adapter);

            // 메뉴 입력: items.add(new MenuInfo("이름","칼로리","탄수화물","단백질","지방"));
            menus.add(new MenuInfo(
                    "lunch",
                    100,
                    200,
                    300,
                    400));
            menus.add(new MenuInfo(
                    "lunch2",
                    100,
                    200,
                    300,
                    400));
            menus.add(new MenuInfo(
                    "lunch3",
                    100,
                    200,
                    300,
                    400));
            menus.add(new MenuInfo(
                    "lunch4",
                    100,
                    200,
                    300,
                    400));
            items.add(new RecInfo("학생식당",(float)3.5, getActivity().getApplicationContext(), menus));
            items.add(new RecInfo("예술식당",(float)4.2, getActivity().getApplicationContext(), menus));
            items.add(new RecInfo("대학식당",(float)1.5, getActivity().getApplicationContext(), menus));
        }

        return rootView;
    }
    @Override
    public void setUserVisibleHint(boolean isVisible){
        if(isVisible){
            Log.d("RecFragment","position "+position);
            super.setUserVisibleHint(isVisible);
        }
    }
}
