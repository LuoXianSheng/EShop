package com.newer.eshop.goods;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newer.eshop.AdvFragment;
import com.newer.eshop.AdvVPagerAdapter;
import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.bean.Goods;
import com.newer.eshop.bean.MyEvent;
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Enumeration;


public class good_product_Fragment extends Fragment implements HttpDataListener,View.OnClickListener{


     ViewPager pager;
     int path;
     TextView good_name,good_price,good_type,good_id,good_sell;
     Button add_btn,delete_btn;
     EditText count_text;
     ArrayList<String> list;
     AdvVPagerAdapter adapter;
     ArrayList<Fragment> arrayList;
     Goods goods;
     int count=1;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0) {
                good_name.setText("商品名称:" + goods.getName());
                good_price.setText("商品价格:" + goods.getPrice());
                good_type.setText("商品类型:" + goods.getTypeId());
                good_sell.setText("商品售量:" + goods.getSell());
                good_id.setText("商品编号:" + goods.getId());
                for (int i = 0; i < list.size(); i++) {
                    arrayList.add(new AdvFragment(list.get(i)));
                }
                adapter.notifyDataSetChanged();
            }
        }
    };

    public good_product_Fragment(int path){
        this.path=path;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    /**
     * 初始化ID
     */
    private void initID(View view) {
        good_id=(TextView)view.findViewById(R.id.goods_produt_id);
        good_sell=(TextView)view.findViewById(R.id.goods_produt_sell);
        good_name=(TextView)view.findViewById(R.id.goods_produt_name);
        good_price=(TextView)view.findViewById(R.id.goods_produt_price);
        good_type=(TextView)view.findViewById(R.id.goods_produt_type);
        pager=(ViewPager)view.findViewById(R.id.good_fragment_product);
        add_btn=(Button)view.findViewById(R.id.good_fragment_load);
        delete_btn=(Button)view.findViewById(R.id.good_fragment_close);
        add_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
        count_text=(EditText)view.findViewById(R.id.good_fragment_count);
        list=new ArrayList<>();
        arrayList=new ArrayList<>();
        adapter=new AdvVPagerAdapter(getFragmentManager(),arrayList);
        pager.setOffscreenPageLimit(5);
        pager.setAdapter(adapter);
    }

    /**
     * 添加了一个弹出式的菜单
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_good_product, container, false);
        initID(view);
        NetConnection.getOneGoods(getContext(),"http://192.168.191.1:8080/Eshop/onegoods?goodsId=" + path, this);
        return view;
    }

    @Override
    public void succeseful(String str) {
        Gson gson;
        try {
            JSONObject object=new JSONObject(str);
            if(object.getString("status").equals(App.STATUS_SUCCESS)){
                gson=new Gson();
                goods=gson.fromJson(object.getString("data"), Goods.class);
                String[] src=goods.getImage_path().split(",");
                for (int i = 1; i < src.length; i++) {
                    list.add("http://192.168.191.1:8080/Eshop/images/" + src[i] + ".jpg");
                }
                Message message=new Message();
                message.obj=list;
                message.what=0;
                handler.sendMessage(message);
            }else{
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
        if(v.getId()==R.id.good_fragment_load){
            ++count;
        }else{
            if(count<=1){
                return;
            }else {
                --count;
            }
        }
        count_text.setText("" + count);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void bb(MyEvent event) {
        if ("Add".equals(event.getAction())) {
            MyEvent myEvent = new MyEvent();
            myEvent.setData(count + "");
            myEvent.setAction("getCount");
            EventBus.getDefault().post(myEvent);
        } else if ("buy".equals(event.getAction())) {
            MyEvent myEvent = new MyEvent();
            myEvent.setData(count + "");
            myEvent.setAction("toBuy");
            EventBus.getDefault().post(myEvent);
        }
        Log.e("获取count:", count + "");
    }
}
