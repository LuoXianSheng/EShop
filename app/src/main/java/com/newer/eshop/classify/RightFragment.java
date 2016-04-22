package com.newer.eshop.classify;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class RightFragment extends Fragment implements HttpDataListener {

    private int type_1;
    private ArrayList<GoodsClassify> list;
    private GridAdapter adapter;
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
        adapter = new GridAdapter(getContext(), list);
        gridView.setAdapter(adapter);

        getClassifyData();
        return view;
    }

    private void getClassifyData() {
        NetConnection.getClassifyData(App.GOODSCLASSIFY_URL, type_1, this);
    }

    @Override
    public void succeseful(final String str) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                System.out.println("+++++" + str);
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

}
