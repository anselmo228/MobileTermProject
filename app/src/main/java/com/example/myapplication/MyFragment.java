package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.MenuListAdapter;
import com.example.myapplication.info.MenuInfo;
import com.example.myapplication.pages.LoginActivity;
import com.example.myapplication.pages.TodaysmenuActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class MyFragment extends Fragment {

    public interface Listener {
        void onReceived(int channel);
    }

    private Listener listener;
    private static final String ARG_POSITION = "position";
    private int position;
    private String responseText;
    private String menuResult;
    private HashMap<String, String> nutritionInfo = new HashMap<>();
    ArrayList<MenuInfo> lunch500List;
    RecyclerView lunch500, lunch600, dinner;

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
        responseText = ((TodaysmenuActivity)getActivity()).responseText;

        if (position == 0) {
            rootView = inflater.inflate(R.layout.fragment_lunch, container, false);
            // ...

            lunch500List = new ArrayList<MenuInfo>();

            lunch500 = rootView.findViewById(R.id.nut_lun);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext(),
                    LinearLayoutManager.VERTICAL, false);
            MenuListAdapter adapter = new MenuListAdapter(lunch500List);
            lunch500.setLayoutManager(linearLayoutManager);
            lunch500.setAdapter(adapter);

            // 메뉴 입력: lunch500List.add(new MenuInfo("이름","칼로리","탄수화물","단백질","지방"));
            lunch500List.add(new MenuInfo(
                    "lunch", 100, 200, 300, 400));
            lunch500List.add(new MenuInfo(
                    "lunch2", 100, 200, 300, 400));
            lunch500List.add(new MenuInfo(
                    "lunch3", 100, 200, 300, 400));
            lunch500List.add(new MenuInfo(
                    "lunch4", 100, 200, 300, 400));
            requestMenu();

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
                    "lunch600", 100, 200, 300, 400));
            lunch600List.add(new MenuInfo(
                    "lunch600-2", 100, 200, 300, 400));
            lunch600List.add(new MenuInfo(
                    "lunch600-3", 100, 200, 300, 400));
            lunch600List.add(new MenuInfo(
                    "lunch600-4", 100, 200, 300, 400));
            requestMenu();

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
                    "dinner", 100, 200, 300, 400));
            dinnerList.add(new MenuInfo(
                    "dinner2", 100, 200, 300, 400));
            dinnerList.add(new MenuInfo(
                    "dinner3", 100, 200, 300, 400));
            dinnerList.add(new MenuInfo(
                    "dinner4", 100, 200, 300, 400));
            requestMenu();
        }

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisible) {
        if (isVisible) { // 점심500:0, 점심600:1, 저녁:2
            Log.d("MyFragment", "isVisible position: " + position);
        }
        if (listener != null) listener.onReceived(position);
        super.setUserVisibleHint(isVisible);
    }

    private void requestMenu() {
        String mld;
        if (position == 0) {
            mld = "lunch500";
        } else if (position == 1) {
            mld = "lunch600";
        } else {
            mld = "dinner";
        }

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        new MyFragment.ReviewNetworkTask().execute("https://mobile.gach0n.com/get_meal.php?session_id="
                + URLEncoder.encode(responseText) + "&date=2023-05-23&mld=" + mld);
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MyFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    private class ReviewNetworkTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                    br.close();
                }
                conn.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.equals("Response(False) : fail")) {
                Toast.makeText(getActivity().getApplicationContext(), "식단을 가져오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
            } else if (result.equals("wrong session") || result.equals("session expired")) {
                Toast.makeText(getActivity().getApplicationContext(), "세션이 만료되었습니다.", Toast.LENGTH_SHORT).show();
                new LoginActivity();
            } else {
                Log.d("MyFragment", "Retrieved menu: " + result);
                menuResult = result;
                String[] menuItems = result.split(",");
                ArrayList<String> menus = new ArrayList<>();

                for (String menuItem : menuItems) {
                    menus.add(menuItem.trim());
                    new MenuRequestTask(menuItem.trim()).execute("https://mobile.gach0n.com/get_nutrient.php?session_id="
                            + URLEncoder.encode(responseText) + "&menu=" + URLEncoder.encode(menuItem.trim()));
                    Log.d("MyFragment", "Menu requested:" + menuItem.trim());
                }
            }
        }
    }

    private class MenuRequestTask extends AsyncTask<String, Void, String> {
        private String menu;

        MenuRequestTask(String menu) {
            this.menu = menu;
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";

            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                    br.close();
                }
                conn.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.equals("fail")) {
                Log.d("post", "Menu request failed: "+menu);
                nutritionInfo.put(menu, "1n분<br>kcal:0<br>fat:0<br>carbohydrate:0<br>protein:0<br>");
            } else if (result.isEmpty()) {
                Log.d("post", "Menu request is empty.");
            } else {
                // Save the response in the HashMap
                nutritionInfo.put(menu, result);
                Log.d("post", "Menu request result: " + nutritionInfo);
            }
            if(position == 0){
                //lunch500
                ArrayList<MenuInfo> list = new ArrayList<>();
                list.add(new MenuInfo("onpost", 100, 200, 300, 400));
                list.add(new MenuInfo("onpost2", 100, 200, 300, 400));
                PutMenu(lunch500, list);
                nutritionInfo.forEach((key, value)->{
                    Log.d("put", position+" "+key+" "+value);
                });
            }else if(position == 1){
                //lunch600
                nutritionInfo.forEach((key, value)->{
                    Log.d("put", position+" key "+key+" value "+value);
                });
            } else{
                //dinner
                nutritionInfo.forEach((key, value)->{
                    Log.d("put", position+" key "+key+" value "+value);
                });
            }
        }
    }
    private void PutMenu(RecyclerView recyclerView, ArrayList<MenuInfo> list){
        MenuListAdapter adapter = new MenuListAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}