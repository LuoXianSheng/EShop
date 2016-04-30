package com.newer.eshop.classify;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newer.eshop.R;
import com.newer.eshop.bean.ClassifyTitle;

import java.util.List;

/**
 * Created by Mr_LUO on 2016/4/24.
 */
public class LeftListAdapter extends BaseAdapter {

    private Context context;
    private List<ClassifyTitle> list;
    private int mSelect = 0;   //选中项

    public LeftListAdapter(Context context, List<ClassifyTitle> list) {
        this.context = context;
        this.list = list;
    }

    public void changeSelected(int positon){ //刷新方法
        if(positon != mSelect){
            mSelect = positon;
            notifyDataSetChanged();
        }
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
            convertView = LayoutInflater.from(context).inflate(R.layout.classify_left_list_item, null);
            holder.tv = (TextView) convertView.findViewById(R.id.classify_left_list_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (mSelect == position) {
            convertView.setBackgroundColor(Color.parseColor("#ffefefef"));
        } else {
            convertView.setBackgroundColor(Color.WHITE);
        }
        holder.tv.setText(list.get(position).getTitle());
        return convertView;
    }

    private class ViewHolder {
        public TextView tv;
    }
}
