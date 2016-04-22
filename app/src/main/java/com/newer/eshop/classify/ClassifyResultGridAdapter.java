package com.newer.eshop.classify;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.bean.Goods;
import com.newer.eshop.bean.GoodsClassify;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Mr_LUO on 2016/4/22.
 */
public class ClassifyResultGridAdapter extends BaseAdapter {

    private Context context;
    private List<Goods> list;

    public ClassifyResultGridAdapter(Context context, List<Goods> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.classifyresult_grid_item, null);
            holder.img = (ImageView) convertView.findViewById(R.id.classifyreuslt_grid_item_img);
            holder.name = (TextView) convertView.findViewById(R.id.classifyreuslt_grid_item_name);
            holder.sell = (TextView) convertView.findViewById(R.id.classifyreuslt_grid_item_sell);
            holder.price = (TextView) convertView.findViewById(R.id.classifyreuslt_grid_item_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String str = list.get(position).getImage_path();
        String imgPath = str.substring(0, str.indexOf(",")) + ".png";
        Log.e("SSSSSS", imgPath);
        ImageLoader.getInstance().displayImage(App.SERVICE_IMAGES_URL + imgPath, holder.img, App.initOptions());
        holder.name.setText(list.get(position).getName());
        holder.sell.setText(list.get(position).getSell() + "");
        holder.price.setText(list.get(position).getPrice() + "");
        return convertView;
    }

    private class ViewHolder {
        public ImageView img;
        public TextView name;
        public TextView sell;
        public TextView price;
    }
}
