package com.newer.eshop.goods;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/21.
 */
public class good_FragmentManger extends FragmentPagerAdapter{

     ArrayList<Fragment> list;
    public good_FragmentManger(FragmentManager fm,ArrayList<Fragment> list)
    {
        super(fm);
        this.list=list;
    }
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }
    @Override
    public int getCount() {
        return 3;
    }
}
