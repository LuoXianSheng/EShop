package com.newer.eshop.classify;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.etsy.android.grid.StaggeredGridView;
import com.google.gson.Gson;
import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.bean.Goods;
import com.newer.eshop.goods.GoodsActivity;
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClassifyResultActivity extends AppCompatActivity implements HttpDataListener {

    private StaggeredGridView gridView;
    private ArrayList<Goods> list;
    private ClassifyResultGridAdapter adapter;
    private int type_1, type_2;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify_result);

        Intent intent = getIntent();
        type_1 = intent.getIntExtra("type_1", -1);
        type_2 = intent.getIntExtra("type_2", -1);

        initView();
    }

    private void initView() {
        handler = new Handler();
        gridView = (StaggeredGridView) findViewById(R.id.classifyresult_grid_view);
        list = new ArrayList<>();
        adapter = new ClassifyResultGridAdapter(this, list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClassifyResultActivity.this, GoodsActivity.class);
                intent.putExtra("goodsId", list.get(position).getId());
                startActivity(intent);
            }
        });
        getData();// 请求网络数据
    }

    private void getData() {
        NetConnection.getClassifyResult(App.SERVICE_URL + "/classifyresult", type_1, type_2, this);
    }

    @Override
    public void succeseful(String str) {
        System.out.println("----------" + str);
        try {
            JSONArray array = new JSONArray(str);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Goods goods = new Goods();
                goods.setId(object.getInt("id"));
                goods.setName(object.getString("name"));
                goods.setSell(object.getInt("sell"));
                goods.setPrice(object.getInt("price"));
                goods.setImage_path(object.getString("image_path"));
                list.add(goods);
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loser(String str) {

    }
}
