package com.newer.eshop.goods;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class good_product_Fragment extends Fragment implements HttpDataListener{

     GoodsActivity good;
     ViewPager pager;
     int path;
     TextView good_name;
     TextView good_price;
     TextView good_type;
     TextView good_id;
     TextView good_sell;
     ArrayList<String> list;
     AdvVPagerAdapter adapter;
     ArrayList<Fragment> arrayList;
     Goods goods;

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
                    System.out.println(list.get(i));
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
        list=new ArrayList<>();
        arrayList=new ArrayList<>();
        adapter=new AdvVPagerAdapter(getFragmentManager(),arrayList);
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
        Button b = (Button)view.findViewById(R.id.good_fragment_load);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                good = (GoodsActivity) getActivity();
                View view1 = View.inflate(good, R.layout.goods_fragment_popub, null);
                final PopupWindow window = new PopupWindow(view1, 400, 200);
                if (v.getId() == R.id.good_fragment_load) {
                    window.showAsDropDown(view1);
                    window.showAtLocation(view1, Gravity.CENTER, 20, 20);
                } else if (v.getId() == R.id.good_fragment_close) {
                    window.dismiss();
                }
            }
        });
        NetConnection.getOneGoods("http://192.168.191.1:8080/Eshop/onegoods?goodsId="+path , this);
        return view;
    }

    @Override
    public void succeseful(String str) {
        System.out.println(str);
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
                System.out.println("请先连接网络!");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loser(String str) {
    }
}
