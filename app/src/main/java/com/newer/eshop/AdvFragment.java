package com.newer.eshop;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Mr_LUO on 2016/4/21.
 */
public class AdvFragment extends Fragment {

    private String imgPath;

    public AdvFragment(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_adv_fragment, null);
        ImageLoader.getInstance().displayImage(imgPath, (ImageView) view);
        return view;
    }
}
