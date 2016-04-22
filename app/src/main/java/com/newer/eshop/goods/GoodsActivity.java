package com.newer.eshop.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.newer.eshop.MainActivity;
import com.newer.eshop.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/21.
 */
public class GoodsActivity extends AppCompatActivity{

     ViewPager pager;
     TextView textview1;
     TextView textview2;
     TextView textview3;
     ImageButton button;
     ArrayList<Fragment> list;
     int goodsId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        Intent intent=getIntent();
        goodsId=intent.getIntExtra("goodsId", -1);
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
            Intent intent=new Intent(GoodsActivity.this,MainActivity.class);
            startActivity(intent);
        }

    }

}
