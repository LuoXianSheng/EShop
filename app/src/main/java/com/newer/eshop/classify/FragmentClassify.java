package com.newer.eshop.classify;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.bean.ClassifyTitle;
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Mr_LUO on 2016/4/21.
 */
public class FragmentClassify extends Fragment implements AdapterView.OnItemClickListener, HttpDataListener {

    private FragmentManager manager;
    private RightFragment rightFragment;
    private ListView listView;
    private ArrayList<ClassifyTitle> data;
    private LeftListAdapter adapter;
    private HashMap<Integer, Fragment> fragments = new HashMap<>();
    private FragmentTransaction transaction;
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classify, null);
        listView = (ListView) view.findViewById(R.id.classify_listview);

        data = new ArrayList<>();
        adapter = new LeftListAdapter(getContext(), data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        manager = getFragmentManager();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                adapter.notifyDataSetChanged();
                listView.performItemClick(listView.getAdapter().getView(0, null, null),
                        0, listView.getItemIdAtPosition(0));//模拟list点击
            }
        };
        getClassifyData();//获取网络数据
        return view;
    }

    private void getClassifyData() {
        NetConnection.getFirstClassifyTitle(getContext(), App.SERVICE_URL + "/getclassifytitle", this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        adapter.changeSelected(position);
        transaction = manager.beginTransaction();
        hideAll(transaction);
        if (fragments.containsKey(position)) {
            transaction.show(fragments.get(position));
        } else {
            rightFragment = new RightFragment(position + 1);
            fragments.put(position, rightFragment);
            transaction.add(R.id.classify_right_layout, rightFragment);
        }
        transaction.commit();
    }

    /**
     * 隐藏所有碎片
     * @param transaction
     */
    public void hideAll(FragmentTransaction transaction) {
        Set<Integer> set = fragments.keySet();
        for (Integer i : set) {
            if (fragments.get(i) != null) transaction.hide(fragments.get(i));
        }
    }

    @Override
    public void succeseful(String str) {
        Log.e("classify", str);
        try {
            JSONObject object = new JSONObject(str);
            if (object.getString(App.STATUS).equals(App.STATUS_SUCCESS)) {
                Gson gson = new Gson();
                ArrayList<ClassifyTitle> list = gson.fromJson(object.getString("data"),
                        new TypeToken<ArrayList<ClassifyTitle>>(){}.getType());
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        data.add(list.get(i));
                    }
                }
                handler.sendEmptyMessage(1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loser(String str) {

    }
}
