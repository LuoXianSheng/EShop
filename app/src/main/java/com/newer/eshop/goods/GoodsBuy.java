package com.newer.eshop.goods;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.bean.Cart;
import com.newer.eshop.bean.Goods;
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/26.
 */
public class GoodsBuy extends AppCompatActivity implements HttpDataListener, View.OnClickListener {

    private TextView tvName, tvPhone, tvAddress, tvTotal;
    private ListView listView;
    private Button btnSubmit;
    private GoodsBuyListAdapter adapter;
    private ArrayList<Cart> list;

    private String[] goodsids;
    private String[] counts;
    private String phone;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_buy_layout);
        initToolbar();
        initView();
        initData();
        getData();//获取数据
    }

    private void initData() {
        Intent intent = getIntent();
        goodsids = intent.getStringExtra("goodsids").split(",");
        counts = intent.getStringExtra("counts").split(",");
        SharedPreferences preferences = getSharedPreferences(App.USER_SP_NAME, MODE_PRIVATE);
        phone = preferences.getString("phone", "");
        tvName.setText(preferences.getString("name", ""));
        tvPhone.setText(phone);
        tvAddress.setText(preferences.getString("address", "(还没有收货地址，请新建!)"));
    }

    private void getData() {
        String url = App.SERVICE_URL + "/buy";
        StringBuilder goodsid = new StringBuilder();
        StringBuilder count = new StringBuilder();
        goodsid.append("[");
        count.append("[");
        for (int i = 0; i < goodsids.length; i++) {
            goodsid.append("{\"goodsid\":").append(goodsids[i]).append("},");
            count.append("{\"count\":").append(goodsids[i]).append("},");
        }
        NetConnection.buyGoods(this,url, phone, goodsid.substring(0, goodsid.lastIndexOf(",")) + "]",
                count.substring(0, count.lastIndexOf(",")) + "]", this);
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.buy_toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }

    private void initView() {
        tvName = (TextView) findViewById(R.id.goods_buy_name);
        tvPhone = (TextView) findViewById(R.id.goods_buy_phone);
        tvAddress = (TextView) findViewById(R.id.goods_buy_address);
        tvTotal = (TextView) findViewById(R.id.goods_buy_total);
        listView = (ListView) findViewById(R.id.goods_buy_listview);
        btnSubmit = (Button) findViewById(R.id.goods_buy_submit);
        list = new ArrayList<>();
        adapter = new GoodsBuyListAdapter(this, list);
        listView.setAdapter(adapter);
        tvAddress.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                tvTotal.setText("总共：" + bundle.getInt("count") + "件，合计：" + bundle.getFloat("sum"));
                adapter.notifyDataSetChanged();
            }
        };
    }


    @Override
    public void succeseful(String str) {
        float sum = 0;//商品价格
        int count = 0;//商品计件
        try {
            JSONObject object = new JSONObject(str);
            if (object.getString("status").equals(App.STATUS_SUCCESS)) {
                ArrayList<Goods> goodses;
                Gson gson = new Gson();
                goodses = gson.fromJson(object.getString("data"), new TypeToken<ArrayList<Goods>>(){}.getType());
                for (int i = 0; i < goodses.size();  i++) {
                    sum += goodses.get(i).getPrice();
                    count++;
                    list.add(new Cart(goodses.get(i), Integer.valueOf(counts[i])));
                }
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putFloat("sum", sum);
                bundle.putInt("count", count);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loser(String str) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_buy_address:

                break;
            case R.id.goods_buy_submit:

                break;
        }
    }
}
