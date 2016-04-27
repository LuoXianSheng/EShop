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

public class AddressManagerActivity extends AppCompatActivity implements View.OnClickListener, HttpDataListener {

    private ListView listView;
    private Button btnAdd;
    private AddressListAdapter adapter;
    private ArrayList<Address> list;
    private String phone;//用户手机号码
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manager);

        initToolBar();
        initView();
        getNetData();//获取网络数据
    }

    private void getNetData() {
        NetConnection.getAddress(this, App.SERVICE_URL + "/getaddress", phone, this);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.me_address_listview);
        btnAdd = (Button) findViewById(R.id.me_address_add_newAddress);
        btnAdd.setOnClickListener(this);
        list = new ArrayList<>();
        adapter = new AddressListAdapter(this, list);
        listView.setAdapter(adapter);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                adapter.notifyDataSetChanged();
            }
        };
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
        startActivityForResult(intent, App.REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == App.REQUESTCODE && resultCode == RESULT_OK) {
            Address address = new Address();
            address.setName(data.getStringExtra("name"));
            address.setPhone(data.getStringExtra("phone"));
            address.setAddress(data.getStringExtra("address"));
            list.add(address);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void succeseful(String str) {
        try {
            JSONObject object = new JSONObject(str);
            if (object.getString(App.STATUS).equals(App.STATUS_SUCCESS)) {
                JSONArray array = new JSONArray(object.getString("data"));
                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.getJSONObject(i);
                    Address address = new Address(o.getInt("id"), o.getString("phone"),
                            o.getString("name"), o.getString("address"), o.getInt("type"));
                    list.add(address);
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
