package com.newer.eshop.me.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newer.eshop.R;
import com.newer.eshop.bean.Order;
import com.newer.eshop.myview.PinnedSectionListView;

import java.util.ArrayList;

public class OrderListAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
	private ArrayList<Order> list;
	private Context context;

	public OrderListAdapter(Context context, ArrayList<Order> list){
		this.context=context;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Order getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGrop) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		int type = getItemViewType(position);
		if(convertView==null){
			if (type == 1) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.goods_buy_list_item, null);
				holder.img = (ImageView) convertView.findViewById(R.id.goods_buy_list_item_img);
				holder.name = (TextView) convertView.findViewById(R.id.goods_buy_list_item_name);
				holder.id = (TextView) convertView.findViewById(R.id.goods_buy_list_item_id);
				holder.price = (TextView) convertView.findViewById(R.id.goods_buy_list_item_price);
				holder.count = (TextView) convertView.findViewById(R.id.goods_buy_list_item_count);
			} else {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.me_order_list_head, null);
				holder.title = (TextView) convertView.findViewById(R.id.order_title);
			}
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		Order order = list.get(position);
		if (order.getType() == Order.SECTION) {
			if (type == 0) {
				holder.title.setText("标题标题");
			}
		}else{
			if (type == 1) {
				holder.name.setText("我是名字");
				holder.id.setText("型号：");
				holder.price.setText("单价：");
				holder.count.setText("x");
			}
		}
		return convertView;
	}
	@Override
	public boolean isItemViewTypePinned(int viewType) {
		return viewType == Order.SECTION;//0是标题，1是内容
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}
	@Override
	public int getItemViewType(int position) {
		return list.get(position).getType();
	}

	class ViewHolder {
		public TextView title;

		public ImageView img;
		public TextView name;
		public TextView id;
		public TextView price;
		public TextView count;
	}


}
