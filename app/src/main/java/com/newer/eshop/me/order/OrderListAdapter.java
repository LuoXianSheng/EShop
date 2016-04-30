package com.newer.eshop.me.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newer.eshop.App;
import com.newer.eshop.R;
import com.newer.eshop.bean.Order;
import com.newer.eshop.myview.PinnedSectionListView;
import com.newer.eshop.tools.Tools;
import com.nostra13.universalimageloader.core.ImageLoader;

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
		ViewHolder1 holder1 = null;
		ViewHolder2 holder2 = null;
		int type = getItemViewType(position);
		if(convertView==null){
			if (type == 1) {
				holder1 = new ViewHolder1();
				convertView = LayoutInflater.from(context).inflate(R.layout.goods_buy_list_item, null);
				holder1.img = (ImageView) convertView.findViewById(R.id.goods_buy_list_item_img);
				holder1.name = (TextView) convertView.findViewById(R.id.goods_buy_list_item_name);
				holder1.id = (TextView) convertView.findViewById(R.id.goods_buy_list_item_id);
				holder1.price = (TextView) convertView.findViewById(R.id.goods_buy_list_item_price);
				holder1.count = (TextView) convertView.findViewById(R.id.goods_buy_list_item_count);
				convertView.setTag(holder1);
			} else {
				holder2 = new ViewHolder2();
				convertView = LayoutInflater.from(context).inflate(R.layout.me_order_list_head, null);
				holder2.title = (TextView) convertView.findViewById(R.id.order_date);
				holder2.count = (TextView) convertView.findViewById(R.id.order_count);
				convertView.setTag(holder2);
			}

		}else{
			if (type == 1)
				holder1 = (ViewHolder1) convertView.getTag();
			else
				holder2 = (ViewHolder2) convertView.getTag();
		}
		Order order = list.get(position);
		if (order.getType() == Order.SECTION) {
			if (type == 0) {
				holder2.title.setText("订单时间：" + Tools.DateFormat(order.getDate()));
				holder2.count.setText("共计3件商品 合计1200.0元");
			}
		}else{
			if (type == 1) {
				holder1.name.setText(order.getName());
				holder1.id.setText("型号：" + order.getGoodsid());
				holder1.price.setText("单价：" + order.getPrice());
				holder1.count.setText("x" + order.getCount());
				String imgPath = order.getImage_path().split(",")[0];
				ImageLoader.getInstance().displayImage(App.SERVICE_IMAGES_URL + imgPath + ".jpg",
						holder1.img, App.initOptions());
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

	class ViewHolder1 {
		public ImageView img;
		public TextView name;
		public TextView id;
		public TextView price;
		public TextView count;
	}

	class ViewHolder2 {
		public TextView title;
		public TextView count;
	}
}
