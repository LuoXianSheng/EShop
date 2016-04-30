package com.newer.eshop.goods;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.bean.Address;
import com.newer.eshop.bean.Cart;
import com.newer.eshop.bean.Goods;
import com.newer.eshop.bean.SubmitOrder;
import com.newer.eshop.me.address.SelectAddressActivity;
import com.newer.eshop.me.order.SubmitResultActivity;
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;

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
    private Address address;//收货地址
    private String[] goodsids;//商品ID
    private String[] counts;//商品数量
    private String phone;//用户手机号码
    private Handler handler;
    private float sum = 0;//商品价格
    private int count = 0;//商品计件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_buy_layout);
        initToolbar();
        initView();
        initData();//获取本地数据
        getData();//请求网络数据
    }

    private void initData() {
        Intent intent = getIntent();
        goodsids = intent.getStringExtra("goodsids").split(",");//获取立即购买时所有购买的商品的ID
        counts = intent.getStringExtra("counts").split(",");//获取立即购买时所购买商品的数量
        SharedPreferences preferences = getSharedPreferences(App.USER_SP_NAME, MODE_PRIVATE);
        phone = preferences.getString("phone", "");
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
        int type = 1;
        NetConnection.buyGoods(this, url, phone, type, goodsid.substring(0, goodsid.lastIndexOf(",")) + "]",
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
                if (address != null) {
                    tvName.setText(address.getName());
                    tvPhone.setText(address.getPhone() + "");
                    tvAddress.setText(address.getAddress());
                }
                tvTotal.setText("总共：" + count + "件，合计：" + sum);
                adapter.notifyDataSetChanged();
            }
        };
    }


    @Override
    public void succeseful(String str) {
        try {
            JSONObject object = new JSONObject(str);
            String status = object.getString("status");
            if (status.equals(App.STATUS_SUCCESS)) {
                Gson gson = new Gson();
                ArrayList<Address> addresses = gson.fromJson(object.getString("addresses"),
                        new TypeToken<ArrayList<Address>>(){}.getType());
                ArrayList<Goods> goodses = gson.fromJson(object.getString("data"),
                        new TypeToken<ArrayList<Goods>>(){}.getType());
                for (int i = 0; i < goodses.size();  i++) {
                    sum += goodses.get(i).getPrice();
                    count++;
                    list.add(new Cart(goodses.get(i), Integer.valueOf(counts[i])));
                }
                if (addresses != null)
                    address = addresses.get(0);
                handler.sendEmptyMessage(1);
            } else if (status.equals("2")) {//提交订单成功
                startActivityForResult(new Intent(GoodsBuy.this, SubmitResultActivity.class), 2);
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
            case R.id.goods_buy_address://选择收货地址
                startActivityForResult(new Intent(GoodsBuy.this, SelectAddressActivity.class)
                        .putExtra("phone", phone), App.REQUESTCODE);
                break;
            case R.id.goods_buy_submit://提交订单
                submit();
                break;
        }
    }

    /**
     * 提交订单
     */
    private void submit() {
        ArrayList<Goods> goodses = new ArrayList<>();
        ArrayList<Integer> count = new ArrayList<>();//把数组转成集合
        for (int i = 0; i < goodsids.length; i++) {
            goodses.add(new Goods(Integer.valueOf(goodsids[i])));
            count.add(Integer.valueOf(counts[i]));
        }
        Gson gson = new Gson();
        SubmitOrder order = new SubmitOrder();
        order.setPhone(phone);
        order.setGoodses(goodses);
        order.setCount(count);
        String data = gson.toJson(order);
        NetConnection.submitOrder(this, App.SERVICE_URL + "/submitorder", data, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == App.REQUESTCODE && resultCode == RESULT_OK) {
            address = new Address();
            address.setId(data.getIntExtra("id", -1));
            address.setName(data.getStringExtra("name"));
            address.setPhone(data.getStringExtra("phone"));
            address.setAddress(data.getStringExtra("address"));
            tvName.setText(address.getName());
            tvPhone.setText(address.getPhone());
            tvAddress.setText(address.getAddress());
        } else {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK);
    }
}
