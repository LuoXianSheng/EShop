package com.newer.eshop.classify;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.newer.eshop.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Mr_LUO on 2016/4/21.
 */
public class FragmentClassify extends Fragment implements AdapterView.OnItemClickListener {

    private FragmentManager manager;
    private RightFragment rightFragment;
    private ListView listView;
    private ArrayList<String> data;
    private ArrayAdapter<String> adapter;
    private HashMap<Integer, Fragment> fragments = new HashMap<>();
    FragmentTransaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classify, null);
        listView = (ListView) view.findViewById(R.id.classify_listview);

        data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("test" + i);
        }
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        manager = getFragmentManager();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        transaction = manager.beginTransaction();
        hideAll(transaction);
        if (fragments.containsKey(position)) {
            Toast.makeText(getContext(), "已经建了", Toast.LENGTH_SHORT).show();
            transaction.show(fragments.get(position));
        } else {
            Toast.makeText(getContext(), "新创建", Toast.LENGTH_SHORT).show();
            rightFragment = new RightFragment("Test" + position);
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
}
