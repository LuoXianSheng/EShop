package com.newer.eshop.goods;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newer.eshop.App;
import com.newer.eshop.MainActivity;
import com.newer.eshop.R;
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
     Integer count;

     android.os.Handler handler=new android.os.Handler(){
         @Override
         public void handleMessage(Message msg) {
            if(msg.what==0){
                count=(Integer)msg.obj;
            }
         }
     };

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
        list.add(new good_conmemt_Fragment());
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
        EventBus.getDefault().post("get");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void a(Integer count) {
        NetConnection.SendService(GoodsActivity.this, "http://192.168.191.1:8080/Eshop/tocart", String.valueOf(goodsId), name, this);
        Message message=new Message();
        message.what=0;
        message.obj=count;
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 立即购买
     * @param view
     */

    public void Buy(View view){
        Intent intent=new Intent();
        intent.putExtra("id", goodsId);
        intent.putExtra("count", count);
        System.out.println("你好!" + count);
        intent.setClass(GoodsActivity.this,GoodsBuy.class);
        startActivity(intent);
    }
}
