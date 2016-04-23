package com.newer.eshop.goods;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.newer.eshop.R;

public class GoodsCarActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_car);
        initID();
        //listView.setAdapter();
    }

    /**
     * 初始化ID
     */
    private void initID() {
        listView=(ListView)findViewById(R.id.goods_shopcar_list);
    }

    /**
     * 商品适配器
     */
     class ShaoMyadapter extends BaseAdapter{



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
