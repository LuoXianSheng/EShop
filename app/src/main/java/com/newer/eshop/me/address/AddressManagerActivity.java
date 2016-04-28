package com.newer.eshop.me.address;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.bean.Address;
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddressManagerActivity extends AppCompatActivity
        implements View.OnClickListener, HttpDataListener, AddressListAdapter.MyListener {

    private ListView listView;
    private Button btnAdd;
    private AddressListAdapter adapter;
    private ArrayList<Address> list;
    private String phone;//用户手机号码
    private Handler handler;
    private boolean isNull;//标志收货地址是否为空

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manager);

        initToolBar();
        initView();
        getNetData();//获取网络数据
    }

    private void getNetData() {
        NetConnection.getAddress(this, App.SERVICE_URL + "/getaddress", phone, 0, this);//请求网络获取所有收货地址
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.me_address_listview);
        btnAdd = (Button) findViewById(R.id.me_address_add_newAddress);
        btnAdd.setOnClickListener(this);
        list = new ArrayList<>();
        adapter = new AddressListAdapter(this, list, this);
        listView.setAdapter(adapter);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                adapter.notifyDataSetChanged();
            }
        };
        phone = getIntent().getStringExtra("phone");
        isNull = getIntent().getBooleanExtra("isNull", false);
    }

    private void initToolBar() {
        Toolbar bar = (Toolbar) findViewById(R.id.address_toolbar);
        bar.setTitle("");
        setSupportActionBar(bar);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(AddressManagerActivity.this, MeAddressAddActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("isNull", isNull);
        startActivityForResult(intent, App.REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == App.REQUESTCODE && resultCode == RESULT_OK) {
            Address address = new Address();
            address.setId(data.getIntExtra("id", -1));
            address.setName(data.getStringExtra("name"));
            address.setPhone(data.getStringExtra("phone"));
            address.setAddress(data.getStringExtra("address"));
            isNull = data.getBooleanExtra("isNull", false);
            if (isNull) {
                address.setType(1);
                isNull = false;
            }
            list.add(address);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void succeseful(String str) {
        try {
            if (str.length() <- 0) return;
            JSONObject object = new JSONObject(str);
            String status = object.getString(App.STATUS);
            if (status.equals(App.STATUS_SUCCESS)) {
                isNull = false;
                JSONArray array = new JSONArray(object.getString("data"));
                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.getJSONObject(i);
                    Address address = new Address(o.getInt("id"), o.getString("phone"),
                            o.getString("name"), o.getString("address"), o.getInt("type"));
                    list.add(address);
                }
                handler.sendEmptyMessage(1);
            } else if (status.equals(App.STATUS_LOSE)) {
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
        setResult(RESULT_OK);
    }

    @Override
    public void callBackCheckBox(int position) {
        int old = adapter.getIdx();
        if (old != position) {
            adapter.getMap().get(old).setChecked(false);
            adapter.setIdx(position);
            adapter.getMap().get(position).setChecked(true);
            NetConnection.updateAddressForType(AddressManagerActivity.this, App.SERVICE_URL + "/updatefortype",
                    list.get(old).getId() + "", list.get(position).getId() + "", this);
        }
    }

    @Override
    public void callBackEdit(int position) {

    }

    @Override
    public void callBackDelete(int position) {

    }
}
