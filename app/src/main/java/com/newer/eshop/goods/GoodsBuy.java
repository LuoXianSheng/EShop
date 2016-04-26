package com.newer.eshop.goods;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.newer.eshop.R;
import com.newer.eshop.bean.Goods;
import com.newer.eshop.net.HttpDataListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/26.
 */
public class GoodsBuy extends AppCompatActivity implements HttpDataListener{

    ImageButton imagebtn,imagecar;
    TextView text_addras,text_name,text_phone,text_shop_name,text_shop_id,text_shop_price,text_shop_count;
    Button button;
    ArrayList<Goods> goods_list;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.goods_buy_layout);
        reuqestDate();
        //listView.setAdapter();
        initID();
    }


    private void reuqestDate() {
        Intent intent=getIntent();
        int i=intent.getIntExtra("id",-1);
        int j=intent.getIntExtra("count",-2);
        //NetConnection.
    }
    private void initID(){
        imagebtn=(ImageButton)findViewById(R.id.goods_buy_back);
        imagecar=(ImageButton)findViewById(R.id.goods_buy_shopcar);
        text_addras=(TextView)findViewById(R.id.goods_buy_addras);
        text_name=(TextView)findViewById(R.id.goods_buy_name);
        text_phone=(TextView)findViewById(R.id.goods_buy_phone);
//        text_shop_name=(TextView)findViewById(R.id.goods_buy_shapname);
//        text_shop_price=(TextView)findViewById(R.id.goods_buy_price);
//        text_shop_id=(TextView)findViewById(R.id.goods_buy_shapid);
//        text_shop_count=(TextView)findViewById(R.id.goods_buy_count);
        button=(Button)findViewById(R.id.goods_buy_dingdan);
    }

    @Override
    public void succeseful(String str) {

    }

    @Override
    public void loser(String str) {

    }

    class BuyAdapter extends BaseAdapter{



        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
