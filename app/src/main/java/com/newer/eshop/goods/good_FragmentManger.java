package com.newer.eshop.goods;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2016/4/21.
 */
public class good_FragmentManger extends FragmentPagerAdapter{

    public good_FragmentManger(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new good_product_Fragment();
        }else if(position==1){
            return new good_detail_Fragment();
        }else{
            return new good_conmemt_Fragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
