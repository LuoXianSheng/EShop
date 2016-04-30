package com.newer.eshop.goods;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newer.eshop.App;
import com.newer.eshop.MainActivity;
import com.newer.eshop.R;
import com.newer.eshop.account.LoginActivity;
import com.newer.eshop.bean.MyEvent;
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/21.
 */
public class GoodsActivity extends AppCompatActivity implements HttpDataListener{

     good_product_Fragment product;
     ViewPager pager;
     TextView textview1,textview2,textview3;
     ImageButton button;
     ArrayList<Fragment> list;
     int goodsId;
     String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_goods);
        EventBus.getDefault().register(this);

        Intent intent=getIntent();
        goodsId=intent.getIntExtra("goodsId", -1);
        SharedPreferences sharedPreferences=getSharedPreferences("login_user_im",MODE_PRIVATE);
        name=sharedPreferences.getString("phone",null);
        initId();
        good_FragmentManger manger=new good_FragmentManger(getSupportFragmentManager()
                ,list);
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(manger);
        pager.setCurrentItem(0);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               if(position==0){
                   textview1.setTextColor(getResources().getColor(R.color.color_shopcar));
                   textview2.setTextColor(getResources().getColor(R.color.color_shadow));
                   textview3.setTextColor(getResources().getColor(R.color.color_shadow));
               }else if(position==1){
                   textview2.setTextColor(getResources().getColor(R.color.color_shopcar));
                   textview1.setTextColor(getResources().getColor(R.color.color_shadow));
                   textview3.setTextColor(getResources().getColor(R.color.color_shadow));
               }else if(position==2){
                   textview3.setTextColor(getResources().getColor(R.color.color_shopcar));
                   textview1.setTextColor(getResources().getColor(R.color.color_shadow));
                   textview2.setTextColor(getResources().getColor(R.color.color_shadow));
               }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化ID
     */
    private void initId() {
        pager=(ViewPager)findViewById(R.id.goods_pages);
        textview1=(TextView)findViewById(R.id.good_text_product);
        textview2=(TextView)findViewById(R.id.good_text_detail);
        textview3=(TextView)findViewById(R.id.good_text_comment);
        button=(ImageButton)findViewById(R.id.goods_shopcar);
        list=new ArrayList<>();
        list.add(new good_product_Fragment(goodsId));
        list.add(new good_detail_Fragment());
        list.add(new good_conmemt_Fragment(goodsId));
    }



    /**
     * 三个textview的点击事件，切换碎片
     * @param view
     */
    public void changes(View view){
        if(view.getId()==R.id.good_text_product){
            pager.setCurrentItem(0);
        }else if(view.getId()==R.id.good_text_detail){
            pager.setCurrentItem(1);
        }else if(view.getId()==R.id.good_text_comment){
            pager.setCurrentItem(2);
        }else if(view.getId()==R.id.goods_shopcar){
            Intent intent=new Intent(GoodsActivity.this,GoodsCarActivity.class);
            startActivity(intent);
        }

    }
    /**
     * 将商品加入购物车
     */
    public void ShopAddCar(View view){
        checkUser();
        MyEvent myEvent = new MyEvent();
        myEvent.setAction("Add");
        EventBus.getDefault().post(myEvent);
    }

    public void Buy(View v) {
        checkUser();
        MyEvent myEvent = new MyEvent();
        myEvent.setAction("buy");
        Log.e("商品界面", "点击了购买");
        EventBus.getDefault().post(myEvent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void a(MyEvent myEvent) {
        String data = myEvent.getData();
        Log.e("拿到count", data + "");
        if ("getCount".equals(myEvent.getAction())) {
            NetConnection.SendService(GoodsActivity.this, "http://192.168.191.1:8080/Eshop/tocart",
                    String.valueOf(goodsId), name, data, this);
        } else if ("toBuy".equals(myEvent.getAction())) {
            Log.e("eshop", "准备跳转");
            Intent intent = new Intent(GoodsActivity.this, GoodsBuy.class);
            intent.putExtra("goodsids", goodsId + ",");
            intent.putExtra("counts", data + ",");
            startActivity(intent);
        }
    }

    @Override
    public void succeseful(String str) {
        try {
            JSONObject object=new JSONObject(str);
            if(object.getString("status").equals(App.STATUS_SUCCESS)){
                EventBus.getDefault().post("加入购物车成功!");
            }else{
                EventBus.getDefault().post("加入失败!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void msg(String msg) {
        if (!msg.equals("get"))
        Toast.makeText(GoodsActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loser(String str) {
        System.out.println("发给服务器失败!");
    }

    public void checkUser() {
        SharedPreferences preferences = getSharedPreferences("login_user_im", MODE_PRIVATE);
        String token = preferences.getString("Mytoken", "");
        if ("".equals(token)) {
            startActivity(new Intent(GoodsActivity.this, LoginActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    public void onclikdestroy(View view){
        finish();
    }
}
