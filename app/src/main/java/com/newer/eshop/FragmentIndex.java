package com.newer.eshop;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

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

    private ArrayList<String> data;

    public static DisplayImageOptions initOptions() {
        //初始化显示图片的配置
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, null);
//        refresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        gridView = (StaggeredGridView) view.findViewById(R.id.grid_view);
        data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("http://192.168.191.1:8080/Eshop/images/q.jpg");
            data.add("http://192.168.191.1:8080/Eshop/images/w.jpg");
            data.add("http://192.168.191.1:8080/Eshop/images/e.jpg");
        }
        MyAdapter adapter = new MyAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
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
                holder.img.setHeightRatio(1);
            } else {
                holder.img.setHeightRatio(1.5);
            }
            ImageLoader.getInstance().displayImage(data.get(position), holder.img, initOptions());
            return convertView;
        }
    }
    class ViewHolder {
        public DynamicHeightImageView img;
    }

//    private View setListViewHander() {
//        advList = new ArrayList<>();
//        advList.add(new AdvFragment(advPaths[0]));
//        advList.add(new AdvFragment(advPaths[1]));
//        advList.add(new AdvFragment(advPaths[2]));
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.index_adv_viewpager, null);
//        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.advVPager);
//        mViewPager.getParent().requestDisallowInterceptTouchEvent(false);
//        mViewPager.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, 600));
//        mViewPager.setAdapter(new AdvVPagerAdapter(getFragmentManager(), advList));
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()  {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                switch (state) {
//                    case 0:
//                    case 2:
//                        refresh.setEnabled(true);
//                        break;
//                    case 1:
//                        refresh.setEnabled(false);
//                        break;
//                }
//            }
//        });
//        return mViewPager;
//    }
}
