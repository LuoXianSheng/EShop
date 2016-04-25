package com.newer.eshop;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Mr_LUO on 2016/4/21.
 */
public class FragmentIndex extends Fragment {

    private ArrayList<Fragment> advList;
    private SwipeRefreshLayout refresh;
    private ArrayList<String> list;
    private ListView listView;

    private String[] advPaths = {"http://192.168.191.1:8080/Eshop/images/image1.jpg",
            "http://192.168.191.1:8080/Eshop/images/image2.jpg",
            "http://192.168.191.1:8080/Eshop/images/image3.jpg"};

    private StaggeredGridView gridView;
    private Handler handler;
    private ArrayList<String> data;
    private int viewPagerIndex = 0;
    private Toolbar mToolbar;
    private int heightPixels;

    public FragmentIndex(int heightPixels) {
        this.heightPixels = heightPixels;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, null);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "点击了ToolBar", Toast.LENGTH_SHORT).show();
            }
        });

        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        gridView = (StaggeredGridView) view.findViewById(R.id.grid_view);
        data = new ArrayList<>();
        for (int i = 0; i < 20 ; i++) {
            data.add("http://192.168.191.1:8080/Eshop/images/q.jpg");
            data.add("http://192.168.191.1:8080/Eshop/images/w.jpg");
            data.add("http://192.168.191.1:8080/Eshop/images/e.jpg");
        }
        gridView.addHeaderView(setListViewHander());
        MyAdapter adapter = new MyAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                switch (scrollState) {
//                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
//                        if (gridView.getFirstVisiblePosition() == 0) {
//                            isTop = true;
//                            new Thread(new A()).start();
//                        } else {
//                            isTop = false;
//                        }
//                        break;
//                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        return view;
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder= new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.index_grid_item, null);
                holder.img = (DynamicHeightImageView) convertView.findViewById(R.id.index_grid_img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position % 2 == 0) {
                holder.img.setHeightRatio(0.8);
            } else if (position % 3 == 0){
                holder.img.setHeightRatio(1.5);
            } else if (position % 1.5 == 1) {
                holder.img.setHeightRatio(1.2);
            } else if (position % 1.1 == 1) {
                holder.img.setHeightRatio(0.9);
            } else {
                holder.img.setHeightRatio(1);
            }
            ImageLoader.getInstance().displayImage(data.get(position), holder.img, App.initOptions());
            return convertView;
        }
    }
    class ViewHolder {
        public DynamicHeightImageView img;
    }

    private View setListViewHander() {
        advList = new ArrayList<>();
        advList.add(new AdvFragment(advPaths[0]));
        advList.add(new AdvFragment(advPaths[1]));
        advList.add(new AdvFragment(advPaths[2]));
        View view = LayoutInflater.from(getContext()).inflate(R.layout.index_adv_viewpager, null);
        final ViewPager mViewPager = (ViewPager) view.findViewById(R.id.advVPager);
        mViewPager.getParent().requestDisallowInterceptTouchEvent(false);
        mViewPager.setLayoutParams(new StaggeredGridView.LayoutParams(
                StaggeredGridView.LayoutParams.MATCH_PARENT, heightPixels / 4));
        mViewPager.setAdapter(new AdvVPagerAdapter(getFragmentManager(), advList));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
//        handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                if (++viewPagerIndex >= 3) {
//                    viewPagerIndex = 0;
//                }
//                mViewPager.setCurrentItem(viewPagerIndex);
//            }
//        };
//        new Thread(new A()).start();
        return mViewPager;
    }

    boolean isTop = true;
    class A implements Runnable {

        @Override
        public void run() {
            while (isTop) {
                handler.sendEmptyMessage(1);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
