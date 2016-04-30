package com.newer.eshop.me.order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.account.LoginActivity;
import com.newer.eshop.bean.Order;
import com.newer.eshop.myview.PinnedSectionListView;
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllOrderActivity extends AppCompatActivity implements HttpDataListener {

    private boolean hasHeaderAndFooter;
    private boolean isFastScroll;
    private boolean addPadding;
    private boolean isShadowVisible = true;
    private PinnedSectionListView listview;
    private OrderListAdapter adapter;
    private ArrayList<Order> list;
    private Handler handler;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putBoolean("isFastScroll", isFastScroll);
        outState.putBoolean("addPadding", addPadding);
        outState.putBoolean("isShadowVisible", isShadowVisible);
        outState.putBoolean("hasHeaderAndFooter", hasHeaderAndFooter);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        isFastScroll = savedInstanceState.getBoolean("isFastScroll");
        addPadding = savedInstanceState.getBoolean("addPadding");
        isShadowVisible = savedInstanceState.getBoolean("isShadowVisible");
        hasHeaderAndFooter = savedInstanceState.getBoolean("hasHeaderAndFooter");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_all_order);
        initToolBar();
        initView();
        initializePadding();
        getNetData();
    }

    /**
     * 获取网络数据
     */
    private void getNetData() {
        if (checkUser()) {//检测用户是否登录
            String phone = getSharedPreferences(App.USER_SP_NAME, MODE_PRIVATE).getString("phone", "");
            NetConnection.getAllOrder(this, App.SERVICE_URL + "/getallorder", phone, this);
        }
    }

    private void initToolBar() {
        Toolbar bar = (Toolbar) findViewById(R.id.order_toolbar);
        ImageView back = (ImageView) bar.findViewById(R.id.all_toolvar_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bar.setTitle("");
        setSupportActionBar(bar);
    }

    private OnItemClickListener getListenerForListView() {
        return new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position).getType() == Order.ITEM) {
                    Toast.makeText(AllOrderActivity.this, " ," + position, Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void initView() {
        listview = (PinnedSectionListView) findViewById(R.id.pinnedSectionListView1);
        list = new ArrayList<>();
        adapter = new OrderListAdapter(this, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(getListenerForListView());
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                adapter.notifyDataSetChanged();
            }
        };
    }

    private void initializePadding() {
        float density = getResources().getDisplayMetrics().density;
        int padding = addPadding ? (int) (16 * density) : 0;
        listview.setPadding(padding, padding, padding, padding);
    }

    /**
     * 检测用户是否登录
     */
    public boolean checkUser() {
        SharedPreferences preferences = getSharedPreferences(App.USER_SP_NAME, MODE_PRIVATE);
        String token = preferences.getString("Mytoken", "");
        if ("".equals(token)) {
            startActivityForResult(new Intent(this, LoginActivity.class), 1);
            return false;
        }
        return true;
    }

    @Override
    public void succeseful(String str) {
        try {
            JSONObject object = new JSONObject(str);
            if (object.getString(App.STATUS).equals(App.STATUS_SUCCESS)) {
                Gson gson = new Gson();
                ArrayList<Order> data = gson.fromJson(object.getString("data"), new TypeToken<ArrayList<Order>>(){}.getType());
                for (int i = data.size() - 1; i >= 0; i--) {
                    list.add(data.get(i));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Log.e("all", "回调");
            list.clear();
            adapter.notifyDataSetChanged();
            getNetData();
        }
    }
}
