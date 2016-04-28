package com.newer.eshop.me.address;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.bean.Address;
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectAddressActivity extends AppCompatActivity implements HttpDataListener,
        AdapterView.OnItemClickListener, View.OnClickListener{

    private ListView listView;
    private TextView add;
    private SelectAddressListAdapter adapter;
    private ArrayList<Address> list;
    private Handler handler;
    private String phone;//用户手机号码
    private int idx;//默认收货地址索引
    private boolean isNull;//标志收货地址是否为空

    private TextView tvManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_select_address);

        initView();
        getData();//获取网络数据
    }

    private void getData() {
        NetConnection.getAddress(this, App.SERVICE_URL + "/getaddress", phone, 0, this);
    }

    private void initView() {
        Toolbar bar = (Toolbar) findViewById(R.id.select_toolbar);
        tvManager = (TextView) bar.findViewById(R.id.select_address_manager);
        tvManager.setOnClickListener(this);
        setSupportActionBar(bar);
        listView = (ListView) findViewById(R.id.me_select_listview);
        add = (TextView) findViewById(R.id.me_select_add);
        list = new ArrayList<>();
        adapter = new SelectAddressListAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        add.setOnClickListener(this);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                adapter.notifyDataSetChanged();
            }
        };
        phone = getIntent().getStringExtra("phone");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == App.REQUESTCODE && resultCode == RESULT_OK) {
            /**
             * 这里的回调会有两种情况：
             * 1、新增界面新增地址成功时回调
             * 2、管理界面回调：重新设置默认地址后当前界面也要随之修改
             */
            Address address = new Address();
            address.setId(data.getIntExtra("id", -1));
            address.setPhone(data.getStringExtra("phone"));
            address.setName(data.getStringExtra("name"));
            address.setAddress(data.getStringExtra("address"));
            isNull = data.getBooleanExtra("isNull", false);
            if (isNull) {
                address.setType(1);//只有一个地址，则把当前地址作为默认地址
                isNull = false;//不为空了
            }
            list.add(address);
            adapter.notifyDataSetChanged();
        } else {
            list.clear();//如果是从管理界面
            getData();
        }
    }

    @Override
    public void succeseful(String str) {
        try {
            JSONObject object = new JSONObject(str);
            if (object.getString(App.STATUS).equals(App.STATUS_SUCCESS)) {
                isNull = false;
                JSONArray array = object.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.getJSONObject(i);
                    Address address = new Address();
                    address.setId(o.getInt("id"));
                    address.setPhone(o.getString("phone"));
                    address.setName(o.getString("name"));
                    address.setAddress(o.getString("address"));
                    int type = o.getInt("type");
                    if (type == 1) {
                        idx = type;
                    }
                    address.setType(type);
                    list.add(address);
                }
                handler.sendEmptyMessage(1);
            } else {
                isNull = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loser(String str) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Address address = list.get(position);
        Intent intent = new Intent();
        intent.putExtra("id", address.getId());
        intent.putExtra("name", address.getName());
        intent.putExtra("phone", address.getPhone());
        intent.putExtra("address", address.getAddress());
        setResult(RESULT_OK, intent);//把选中的地址返回给下单界面
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("phone", phone);
        intent.putExtra("isNull", isNull);
        if (v.getId() == R.id.me_select_add) {
            intent.setClass(SelectAddressActivity.this, MeAddressAddActivity.class);//打开新建地址界面
            startActivityForResult(intent, App.REQUESTCODE);
        } else {
            intent.setClass(SelectAddressActivity.this, AddressManagerActivity.class);//打开地址管理界面
            startActivityForResult(intent, 2);//请求码：2
        }
    }
}
