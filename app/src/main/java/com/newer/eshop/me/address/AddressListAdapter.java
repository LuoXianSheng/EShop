package com.newer.eshop.me.address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.newer.eshop.R;
import com.newer.eshop.bean.Address;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mr_LUO on 2016/4/27.
 */
public class AddressListAdapter extends BaseAdapter {

    private Context context;
    private List<Address> list;
    private MyListener listener;//获取点击的position的监听
    private HashMap<Integer, CheckBox> map;
    private int idx;

    public AddressListAdapter(Context context, List<Address> list, MyListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        map = new HashMap<>();
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public HashMap<Integer, CheckBox> getMap() {
        return map;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.me_address_list_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.address_list_item_name);
            holder.phone = (TextView) convertView.findViewById(R.id.address_list_item_phone);
            holder.address = (TextView) convertView.findViewById(R.id.address_list_item_address);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.address_list_item_checkbox);
            holder.edit = (Button) convertView.findViewById(R.id.address_list_item_edit);
            holder.delete = (Button) convertView.findViewById(R.id.address_list_item_delete);
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    listener.callBackCheckBox(position);
                }
            });
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.callBackEdit(position);
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.callBackDelete(position);
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(list.get(position).getName());
        holder.phone.setText(list.get(position).getPhone());
        map.put(position, holder.checkBox);
        if (list.get(position).getType() == 1) {
            idx = position;//记录当前选择的position
            map.get(position).setChecked(true);
            holder.address.setText("[默认地址]" + list.get(position).getAddress());
        } else {
            holder.address.setText(list.get(position).getAddress());
        }
        return convertView;
    }

    class ViewHolder {
        public TextView name;
        public TextView phone;
        public TextView address;
        public CheckBox checkBox;
        public Button edit;
        public Button delete;
    }

    public interface MyListener {
        void callBackCheckBox(int position);
        void callBackEdit(int position);
        void callBackDelete(int position);
    }
}
