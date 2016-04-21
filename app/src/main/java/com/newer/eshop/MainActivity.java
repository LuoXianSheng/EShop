package com.newer.eshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.newer.eshop.classify.FragmentClassify;
import com.newer.eshop.me.FragmentMe;
import com.newer.eshop.shopingcat.FragmentShopingCart;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_index, tv_classify, tv_shopingcart, tv_me;
    private Fragment fragmentIndex, fragmentClassify, fragmentShopingCart, fragmentMe;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        manager = getSupportFragmentManager();
        tv_index = (TextView) findViewById(R.id.main_tv_index);
        tv_classify = (TextView) findViewById(R.id.main_tv_classify);
        tv_shopingcart = (TextView) findViewById(R.id.main_tv_shopingcart);
        tv_me = (TextView) findViewById(R.id.main_tv_me);

        tv_index.setOnClickListener(this);
        tv_classify.setOnClickListener(this);
        tv_shopingcart.setOnClickListener(this);
        tv_me.setOnClickListener(this);

        tv_index.performClick();//模拟一次点击相当于点击了第一项
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = manager.beginTransaction();//开启一个事物
        hideAllFragment(transaction);
        resetAllSelector();
        switch (v.getId()) {
            case R.id.main_tv_index:
                if (fragmentIndex == null) {
                    fragmentIndex = new FragmentIndex();
                    transaction.add(R.id.main_frameLayout, fragmentIndex);
                } else {
                    transaction.show(fragmentIndex);
                }
                break;
            case R.id.main_tv_classify:
                if (fragmentClassify == null) {
                    fragmentClassify = new FragmentClassify();
                    transaction.add(R.id.main_frameLayout, fragmentClassify);
                } else {
                    transaction.show(fragmentClassify);
                }
                break;
            case R.id.main_tv_shopingcart:
                if (fragmentShopingCart == null) {
                    fragmentShopingCart = new FragmentShopingCart();
                    transaction.add(R.id.main_frameLayout, fragmentShopingCart);
                } else {
                    transaction.show(fragmentShopingCart);
                }
                break;
            case R.id.main_tv_me:
                if (fragmentMe == null) {
                    fragmentMe = new FragmentMe();
                    transaction.add(R.id.main_frameLayout, fragmentMe);
                } else {
                    transaction.show(fragmentMe);
                }
                break;
        }
        transaction.commit();//提交事物
    }

    /**
     * 隐藏所有Fragment
     * @param transaction 碎片事物
     */
    public void hideAllFragment(FragmentTransaction transaction) {
        if (fragmentIndex != null) transaction.hide(fragmentIndex).commit();
        if (fragmentClassify != null) transaction.hide(fragmentClassify).commit();
        if (fragmentShopingCart != null) transaction.hide(fragmentShopingCart).commit();
        if (fragmentMe != null) transaction.hide(fragmentMe).commit();
    }

    /**
     * 重置所有选择器
     */
    public void resetAllSelector() {
        tv_index.setSelected(false);
        tv_classify.setSelected(false);
        tv_shopingcart.setSelected(false);
        tv_me.setSelected(false);
    }
}
