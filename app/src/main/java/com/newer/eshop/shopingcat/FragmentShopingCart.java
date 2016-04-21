package com.newer.eshop.shopingcat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newer.eshop.R;

/**
 * Created by Mr_LUO on 2016/4/21.
 */
public class FragmentShopingCart extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopingcart, null);
        return view;
    }
}
