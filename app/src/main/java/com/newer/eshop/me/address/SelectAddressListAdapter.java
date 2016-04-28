package com.newer.eshop.me.address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newer.eshop.R;
import com.newer.eshop.bean.Address;

import java.util.List;

/**
 * Created by Mr_LUO on 2016/4/27.
 */
public class SelectAddressListAdapter extends BaseAdapter {

    private Context context;
    private List<Address> list;

    public SelectAddressListAdapter(Context context, List<Address> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.me_address_select_list_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.select_list_item_name);
            holder.phone = (TextView) convertView.findViewById(R.id.select_list_item_phone);
            holder.address = (TextView) convertView.findViewById(R.id.select_list_item_address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(list.get(position).getName());
        holder.phone.setText(list.get(position).getPhone() + "");
        if (list.get(position).getType() == 1) {
            holder.address.setText("[默认地址] " + list.get(position).getAddress());
        } else {
            holder.address.setText(list.get(position).getAddress());
        }

        return convertView;
    }

    class ViewHolder {
        public TextView name;
        public TextView phone;
        public TextView address;
    }
}
