package com.newer.eshop.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.newer.eshop.R;

/**
 * Created by Administrator on 2016/4/21.
 */
public class GoodsActivity extends AppCompatActivity{

    ViewPager pager;
    TextView textview1;
    TextView textview2;
    TextView textview3;
    ImageButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        initId();
        good_FragmentManger manger=new good_FragmentManger(getSupportFragmentManager());
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

}
