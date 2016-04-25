package com.newer.eshop.classify;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.bean.GoodsClassify;
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RightFragment extends Fragment implements HttpDataListener, AdapterView.OnItemClickListener {

    private int type_1;
    private ArrayList<GoodsClassify> list;
    private ClassifyGridAdapter adapter;
    private Handler handler = new Handler();

    public RightFragment(int type_1) {
        this.type_1 = type_1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.right_gridview);
        list = new ArrayList<>();
        adapter = new ClassifyGridAdapter(getContext(), list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        getClassifyData();
        return view;
    }

    private void getClassifyData() {
        NetConnection.getClassifyData(getContext(), App.GOODSCLASSIFY_URL, type_1, this);
    }

    @Override
    public void succeseful(final String str) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONArray array = new JSONArray(str);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        GoodsClassify classify = new GoodsClassify();
                        classify.setName(object.getString("name"));
                        classify.setImgPath(object.getString("imgPath"));
                        list.add(classify);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void loser(String str) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ClassifyResultActivity.class);
        intent.putExtra("type_1", type_1);
        intent.putExtra("type_2", position + 1);
        startActivity(intent);
    }
}
