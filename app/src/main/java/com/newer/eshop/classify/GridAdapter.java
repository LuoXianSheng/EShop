package com.newer.eshop.classify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.bean.GoodsClassify;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Mr_LUO on 2016/4/22.
 */
public class GridAdapter extends BaseAdapter {

    private Context context;
    private List<GoodsClassify> list;

    public GridAdapter(Context context, List<GoodsClassify> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.classify_right_grid_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.right_grid_item_img);
            holder.name = (TextView) convertView.findViewById(R.id.right_grid_item_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(App.SERVICE_CLASSIFY_IMAGES_URL + "/" + list.get(position).getImgPath(), holder.img, App.initOptions());
        holder.name.setText(list.get(position).getName());
        return convertView;
    }

    private class ViewHolder {
        public ImageView img;
        public TextView name;
    }
}
