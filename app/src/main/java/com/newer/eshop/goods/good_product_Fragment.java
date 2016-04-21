package com.newer.eshop.goods;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import com.newer.eshop.R;
import com.newer.eshop.net.HttpDataListener;
import com.newer.eshop.net.NetConnection;


public class good_product_Fragment extends Fragment implements HttpDataListener{

     GoodsActivity good;
     ViewPager pager;
     final String path="http://192.168.191.1:8080/Eshop/onegoods?goodsId=1001";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initID();
    }

    /**
     * 初始化ID
     */
    private void initID() {

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
        Button b = (Button)view.findViewById(R.id.good_fragment_load);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                good=(GoodsActivity)getActivity();
               View view1=View.inflate(good,R.layout.goods_fragment_popub,null);
                final PopupWindow window=new PopupWindow(view1,400,200);
                if(v.getId()==R.id.good_fragment_load){
                    window.showAsDropDown(view1);
                    window.showAtLocation(view1, Gravity.CENTER, 20, 20);
                }else if(v.getId()==R.id.good_fragment_close){
                    window.dismiss();
                }
            }
        });
        NetConnection.getOneGoods(path, this);
        return view;
    }

    @Override
    public void succeseful(String str) {
        System.out.println(str);
    }

    @Override
    public void loser(String str) {

    }
}
