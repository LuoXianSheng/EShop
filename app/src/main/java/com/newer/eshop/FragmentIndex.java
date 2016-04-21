package com.newer.eshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Mr_LUO on 2016/4/21.
 */
public class FragmentIndex extends Fragment {

    private ArrayList<Fragment> advList;
    private SwipeRefreshLayout refresh;
    private ArrayList<String> list;
    private ListView listView;

    private String[] advPaths = new String[3];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, null);
        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        listView = (ListView) view.findViewById(R.id.index_listview);
        list = new ArrayList<>();
        listView.addHeaderView(setListViewHander(), null, false);
        listView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list));
        return view;
    }

    private View setListViewHander() {
        advList = new ArrayList<>();
        advList.add(new AdvFragment(advPaths[0]));
        advList.add(new AdvFragment(advPaths[1]));
        advList.add(new AdvFragment(advPaths[2]));
        View view = LayoutInflater.from(getContext()).inflate(R.layout.index_adv_viewpager, null);
        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.advVPager);
        mViewPager.getParent().requestDisallowInterceptTouchEvent(false);
        mViewPager.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, 600));
        mViewPager.setAdapter(new AdvVPagerAdapter(getFragmentManager(), advList));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()  {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case 0:
                    case 2:
                        refresh.setEnabled(true);
                        break;
                    case 1:
                        refresh.setEnabled(false);
                        break;
                }
            }
        });
        return mViewPager;
    }
}
